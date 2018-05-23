package com.hector.apptestlove.rest;

import java.net.URI;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.RegistrarContactoDTO;
import com.hector.apptestlove.controler.dto.RespuestaDTO;
import com.hector.apptestlove.controler.dto.SessionDTO;
import com.hector.apptestlove.controler.dto.SolicitudDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.logica.ContactoLogica;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;
import com.hector.apptestlove.controler.util.Util;
import com.hector.apptestlove.dao.SolicitudContactoPendienteDAO;
import com.hector.apptestlove.mensaje.MensajeGcm;

@Path("/contacto")
public class ContactoServicio {

@EJB 
private FacadeLogicaServiceBean facadeLogicaServiceBean;
	
	@PUT
	//@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/registrarWithJson")
	public Response registrarContactoJson(RegistrarContactoDTO c) throws Exception{
	
		int resp=0;
		Response response;
		GcmDTO gcm=new GcmDTO();
		MensajeGcm mensajeGcm = new MensajeGcm();
		
		ContactoDTO contacto=new ContactoDTO();
		UsuarioDTO usuario=new UsuarioDTO();
		SolicitudDTO solicitudDTO = new SolicitudDTO();
		SessionDTO sessionDTO = new SessionDTO();
		
		contacto.setContacto(c.getContacto());
		usuario.setNom_user(c.getNom_user());
		
		//Se recupera solicitud
		solicitudDTO = facadeLogicaServiceBean.getsolicitudLogica().getSolicitudContacto(usuario);
		
		if(c.getRespuesta()==Constantes.SUCCESS){
			
			if(facadeLogicaServiceBean.getsolicitudLogica().existeSolicitudContacto(usuario, contacto)){
				
				//Actualiza solicitud a ACEPTADA
				facadeLogicaServiceBean.getsolicitudLogica().actualizarSolicitud(solicitudDTO.getNumero(), Constantes.ESTADO_SOLICITUD_ACEPTADA);
				//Se registra contacto
				resp=facadeLogicaServiceBean.getcontactoLogica().registrarContacto(usuario, contacto);
				
				if(resp == Constantes.SUCCESS){
					/*Se verifica sesion started*/
					sessionDTO = facadeLogicaServiceBean.getSessionLogica().getSessionByUsuario(usuario.getNom_user());
					if(sessionDTO.getEstado() == Constantes.SESSION_STARTED){
						//Actualiza solicitud a ACEPTADA RECIBIDA
						facadeLogicaServiceBean.getsolicitudLogica().actualizarSolicitud(solicitudDTO.getNumero(), Constantes.ESTADO_SOLICITUD_ACEPTADA_RECIBIDA);
						//Se envia mensaje gcm de exito a usuario que envio la solicitud
						gcm=facadeLogicaServiceBean.getGcmLogica().getGcmXUsuarioLogica(usuario);
						//facadeLogicaServiceBean.getGcmLogica().sendGcmMessage(gcm.getGcm_codGcm(), contacto.getContacto(), Constantes.TIPO_MENSAJE_GCM_RESULTADO_SOLICITUD_CONTACTO);
						mensajeGcm.sendGcmMessage(gcm.getGcm_codGcm(), contacto.getContacto(), Constantes.TIPO_MENSAJE_GCM_RESULTADO_SOLICITUD_CONTACTO);
						
						return Response.ok().build();
					}else{
						//Actualiza solicitud a ACEPTADA PENDIENTE
						facadeLogicaServiceBean.getsolicitudLogica().actualizarSolicitud(solicitudDTO.getNumero(), Constantes.ESTADO_SOLICITUD_ACEPTADA_PENDIENTE);
						System.out.println("Resultado solicitud no se ha enviado,se ha almacenado en estado PENDIENTE RECIBIDA");
						return Response.status(Status.CONFLICT).header("Error", "Resultado solicitud no se ha enviado,se enviara cuando usuario inicie sesion").build();
					}
                    
				}else{
					return Response.serverError().header("Error", new String("No se ha registrado el contacto")).build();

				}
				
			}else{
				
				return Response.status(Status.NO_CONTENT).header("Error",new String("No existe solicitud de contacto")).build();
			}
			
			
			
		}else{
			//Actualiza solicitud a RECHAZADA
			facadeLogicaServiceBean.getsolicitudLogica().actualizarSolicitud(solicitudDTO.getNumero(), Constantes.ESTADO_SOLICITUD_RECHAZADA);
			sessionDTO = facadeLogicaServiceBean.getSessionLogica().getSessionByUsuario(usuario.getNom_user());
			if(sessionDTO.getEstado() == Constantes.SESSION_STARTED){
				//Actualiza solicitud a RECHAZADA RECIBIDA
				facadeLogicaServiceBean.getsolicitudLogica().actualizarSolicitud(solicitudDTO.getNumero(), Constantes.ESTADO_SOLICITUD_RECHAZADA_RECIBIDA);
				//Se envia mensaje gcm de no exito a usuario que envio la solicitud
				gcm=facadeLogicaServiceBean.getGcmLogica().getGcmXUsuarioLogica(usuario);
				//facadeLogicaServiceBean.getGcmLogica().sendGcmMessage(gcm.getGcm_codGcm(),Integer.toString(Constantes.NOT_SUCCESS), Constantes.TIPO_MENSAJE_GCM_RESULTADO_SOLICITUD_CONTACTO);
				mensajeGcm.sendGcmMessage(gcm.getGcm_codGcm(),Integer.toString(Constantes.NOT_SUCCESS), Constantes.TIPO_MENSAJE_GCM_RESULTADO_SOLICITUD_CONTACTO);
				
				return Response.ok().build();
				}else{
				//Actualiza solicitud a RECHAZADA PENDIENTE
				facadeLogicaServiceBean.getsolicitudLogica().actualizarSolicitud(solicitudDTO.getNumero(), Constantes.ESTADO_SOLICITUD_RECHAZADA_PENDIENTE);
				System.out.println("Resultado solicitud no se ha enviado,se ha almacenado en estado RECHAZADA PENDIENTE");
				return Response.status(Status.CONFLICT).header("Error", "Resultado solicitud no se ha enviado,se enviara cuando usuario inicie sesion").build();
			}
			
			
		}
	
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/getContactoXUsuarioWithJson")
	public Response getContactoXUsuarioJson(UsuarioDTO u) throws Exception{
		
		ContactoDTO contacto=new ContactoDTO();
		UsuarioDTO usuario=new UsuarioDTO();
		usuario.setNom_user(u.getNom_user());
		
		
		
		System.out.println("*********getContactoXUsuarioJson********");
		
		contacto=facadeLogicaServiceBean.getcontactoLogica().getContactoXUsuario(usuario);
		
		if(contacto==null){
			return Response.noContent().build();
		}else{
			System.out.println("getContactoXUsuarioJson,contacto="+contacto.getContacto());
			return Response.ok().header("Contacto",contacto.getContacto())
					.header("IdContacto", Long.toString(contacto.getId_contacto())).build();
		}
		
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/enviarSolicitudWithJson")
	public Response enviarSolicitudJson(RegistrarContactoDTO c) throws Exception{
		
	
		MensajeGcm mensajeGcm = new MensajeGcm();
		
		int resBuscarUsuario=0;
		String codGcm="";
		UsuarioDTO usuario=new UsuarioDTO();
		UsuarioDTO usuarioContacto=new UsuarioDTO();
		SessionDTO sessionDTO = new SessionDTO();
		
		//Asignando usuario y contacto a objetos Usuario para buscarlos en bd
		
		usuario.setNom_user(c.getNom_user());
		usuarioContacto.setNom_user(c.getContacto());
		
		if(facadeLogicaServiceBean.getcontactoLogica().getUsuarioXContacto(usuarioContacto) == null){
			
		
		resBuscarUsuario=facadeLogicaServiceBean.getUsuarioLogica().buscarUsuario(usuario);
		
		if(resBuscarUsuario==Constantes.SUCCESS){
			
			
			codGcm=facadeLogicaServiceBean.getGcmLogica().validacionesGcm(usuario,usuarioContacto,Constantes.TIPO_MENSAJE_GCM_SOLICITUD_CONTACTO);
			
            if(codGcm.equals("4")){
				
				System.out.println("El codigo gcm ha expirado");
				return Response.serverError().header("Error", new String("El codigo gcm ha expirado")).build();
			
			}else if(codGcm.equals("5")){
				
				facadeLogicaServiceBean.getsolicitudLogica().insertarSolicitudContacto(usuario, usuarioContacto,Constantes.ESTADO_SOLICITUD_PENDIENTE,Constantes.TIPO_SOLICITUD_SOL_CONTACTO);
				System.out.println("El dispositivo emisor es el mismo del receptor,se enviara" +
						"solicitud cuando el contacto inicie sesion");
				codGcm="";
				return Response.status(Status.CONFLICT).header("Error", "Solicitud no se ha enviado,se enviara cuando usuario inicie sesion").build();
				
			}else if(codGcm.equals("")){
				
				System.out.println("El codigo gcm no existe");
				return Response.serverError().header("Error", new String("El codigo gcm no existe")).build();
				
			}else{
				
				sessionDTO = facadeLogicaServiceBean.getSessionLogica().getSessionByUsuario(c.getContacto());
				if(sessionDTO.getEstado() == Constantes.SESSION_STARTED){
					facadeLogicaServiceBean.getsolicitudLogica().insertarSolicitudContacto(usuario, usuarioContacto,Constantes.ESTADO_SOLICITUD_ENVIADA,Constantes.TIPO_SOLICITUD_SOL_CONTACTO);
					System.out.println("Gcm="+codGcm+",Usuario="+usuario.getNom_user());
					//Se comenta debido a error de ejecucion desde controlador
					//facadeLogicaServiceBean.getcontactoLogica().enviarSolicitud(codGcm, c.getNom_user());
					
					mensajeGcm.enviarSolicitud(codGcm, c.getNom_user());
				
					return Response.ok().build();
				}else{
					facadeLogicaServiceBean.getsolicitudLogica().insertarSolicitudContacto(usuario, usuarioContacto,Constantes.ESTADO_SOLICITUD_PENDIENTE,Constantes.TIPO_SOLICITUD_SOL_CONTACTO);
					System.out.println("Solicitud no se ha enviado,se ha almacenado en estado PENDIENTE");
					return Response.status(Status.CONFLICT).header("Error", "Solicitud no se ha enviado,se enviara cuando usuario inicie sesion").build();
				}
				
				
			}
			
		  }else{
			return Response.noContent().build();
		  }
		}else{
			System.out.println("Contacto ya es amigo de otro usuario,no se puede enviar solicitud de contacto");
			return Response.serverError().header("Error", "Contacto ya es amigo de otro usuario,no se puede enviar solicitud de contacto").build();
		}
		
	
		
	}
	
	@POST
	//@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/eliminarContactoJson")
	public Response eliminarContactoJson(ContactoDTO contactoDTO) throws Exception{
		System.out.println("INI eliminarContactoJson");
		int resEliminarContacto = 0;
		resEliminarContacto = facadeLogicaServiceBean.getcontactoLogica().eliminarContacto(contactoDTO);
		
		if(resEliminarContacto == Constantes.SUCCESS || resEliminarContacto == Constantes.NOT_EXIST){
			return Response.ok().build();
		}else{
			return Response.serverError().header("Error",new String("Ha ocurrido un error inesperado,no se ha eliminado")).build();
		}
	}
	
	@POST
	//@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/modificarContactoJson")
	public Response modificarContactoJson(ContactoDTO contactoDTO) throws Exception{
		System.out.println("INI eliminarContactoJson");
		int resModificarContacto = 0;
		
		resModificarContacto = facadeLogicaServiceBean.getcontactoLogica().actualizarContacto(contactoDTO);
		if(resModificarContacto == Constantes.SUCCESS){
			return Response.ok().build();
		}else if(resModificarContacto == Constantes.NOT_EXIST){
			return Response.notModified().header("Error",new String("No existe contacto,no se ha modificado")).build();
		}else{
			return Response.serverError().header("Error", new String("Ha ocurrido un error inesperado")).build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/getSolicitudWithJson")
	public SolicitudDTO getSolicitudJson(UsuarioDTO usuarioDTO) throws Exception{
		
		UsuarioDTO usuario=new UsuarioDTO();
	
		SolicitudDTO solicitudDTO = new SolicitudDTO();
		
		//Asignando usuario y contacto a objetos Ucontacto.setContacto(c.getContacto());
		usuario.setNom_user(usuarioDTO.getNom_user());
		//Se recupera solicitud
		solicitudDTO = facadeLogicaServiceBean.getsolicitudLogica().getSolicitudContacto(usuario);
		if(solicitudDTO == null){
			Response.noContent().header("Error", "No existe solicitud").build();
		}
		return solicitudDTO;
	}
	
	@POST
	//@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/eliminarSolicitudContactoJson")
	public Response eliminarSolicitudContactoJson(SolicitudDTO solicitudDTO) throws Exception{
		System.out.println("INI eliminarContactoJson");
		int resEliminarSolicitud = 0;
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		UsuarioDTO contactoUsuarioDTO = new UsuarioDTO();
		
		usuarioDTO.setNom_user(solicitudDTO.getUserEmisor());
		contactoUsuarioDTO.setNom_user(solicitudDTO.getUserReceptor());
		
		resEliminarSolicitud = facadeLogicaServiceBean.getsolicitudLogica().eliminarSolicitudContactoPendiente(usuarioDTO, contactoUsuarioDTO);
		
		if(resEliminarSolicitud == Constantes.SUCCESS){
			return Response.ok().build();
		}else if(resEliminarSolicitud == Constantes.NOT_EXIST){
			return Response.notModified().header("Error",new String("No existe contacto,no se ha eliminado")).build();
		}else{
			return Response.serverError().build();
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/reenviarSolicitudJson")
	public Response reenviarSolicitudJson(RegistrarContactoDTO c) throws Exception{
		
	
		MensajeGcm mensajeGcm = new MensajeGcm();
		
		int resBuscarUsuario=0;
		String codGcm="";
		UsuarioDTO usuario=new UsuarioDTO();
		UsuarioDTO usuarioContacto=new UsuarioDTO();
		
		//Asignando usuario y contacto a objetos Usuario para buscarlos en bd
		
		usuario.setNom_user(c.getNom_user());
		usuarioContacto.setNom_user(c.getContacto());
		
		
		resBuscarUsuario=facadeLogicaServiceBean.getUsuarioLogica().buscarUsuario(usuario);
		
		if(resBuscarUsuario==Constantes.SUCCESS){
			
			
			codGcm=facadeLogicaServiceBean.getGcmLogica().validacionesGcm(usuario,usuarioContacto,Constantes.TIPO_MENSAJE_GCM_SOLICITUD_CONTACTO);
			
            if(codGcm.equals("4")){
				
				System.out.println("El codigo gcm ha expirado");
				return Response.serverError().header("Error", new String("El codigo gcm ha expirado")).build();
			
			}else if(codGcm.equals("5")){
				facadeLogicaServiceBean.getsolicitudLogica().insertarSolicitudContacto(usuario, usuarioContacto,Constantes.ESTADO_SOLICITUD_PENDIENTE,Constantes.TIPO_SOLICITUD_SOL_CONTACTO);

						System.out.println("El dispositivo emisor es el mismo del receptor,se enviara" +
						"solicitud cuando el contacto inicie sesion");
				codGcm="";
				return Response.status(Status.CONFLICT).header("Error", "Solicitud no se ha enviado,se enviara cuando usuario inicie sesion").build();
				
			}else if(codGcm.equals("")){
				
				System.out.println("El codigo gcm no existe");
				return Response.serverError().header("Error", new String("El codigo gcm no existe")).build();
				
			}else{
				System.out.println("Gcm="+codGcm+",Usuario="+usuario.getNom_user());
				mensajeGcm.enviarSolicitud(codGcm, c.getNom_user());
			
				return Response.ok().build();
				
			}
			
		}else{
			return Response.noContent().header("Error", new String("No existe usuario")).build();
		}
	}
}
