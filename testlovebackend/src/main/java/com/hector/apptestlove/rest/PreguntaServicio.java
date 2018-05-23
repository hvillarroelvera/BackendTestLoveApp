package com.hector.apptestlove.rest;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Variant;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntaPendienteDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.PuntuacionPendienteDTO;
import com.hector.apptestlove.controler.dto.PuntuacionResultadoDTO;
import com.hector.apptestlove.controler.dto.RegistrarContactoDTO;
import com.hector.apptestlove.controler.dto.RespuestaPendienteDTO;
import com.hector.apptestlove.controler.dto.RespuestaPreguntasDTO;
import com.hector.apptestlove.controler.dto.SessionDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.logica.ContactoLogica;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;
import com.hector.apptestlove.controler.logica.GcmLogica;
import com.hector.apptestlove.controler.logica.PreguntasLogica;
import com.hector.apptestlove.mensaje.MensajeGcm;
import com.hector.apptestlove.mensaje.Message;
import com.hector.apptestlove.mensaje.Result;
import com.hector.apptestlove.mensaje.Sender;

@Path("/pregunta")
public class PreguntaServicio {
	
	@EJB 
	private FacadeLogicaServiceBean facadeLogicaServiceBean;

	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/getCountPreguntasXUsuarioWithJson")
	public PreguntasDTO getCountPreguntasXUsuarioJson(UsuarioDTO u) throws Exception{
		
		PreguntasDTO preguntas=new PreguntasDTO();
		UsuarioDTO usuario=new UsuarioDTO();
		usuario.setNom_user(u.getNom_user());
		
		
		
		System.out.println("*********getCountPreguntasXUsuarioJson********");
		
		preguntas.setCantidadPreguntas(facadeLogicaServiceBean.getpreguntaLogica().getCountPreguntasXUsuario(usuario));
		if(preguntas.getCantidadPreguntas()<0 || preguntas.getCantidadPreguntas()==0){
			Response.noContent().build();
		}else{
			Response.ok().build();
		}
		
		return preguntas;
		
	}
	
	@PUT
	//@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/registrarWithJson")
	public Response registrarPreguntaJson(PreguntasDTO preguntas) throws Exception{
	System.out.println("INI registrarWithJson");
	System.out.println("registrarWithJson nomUser["+preguntas.getNom_user()+"]");
	System.out.println("registrarWithJson cantidadPreguntas["+preguntas.getPreguntas().size()+"]");
	System.out.println("registrarWithJson pregunta[0]["+preguntas.getPreguntas().get(0)+"]");
	
	long numeroPregunta=0;
		Response response;
		
		for(int i=0;i<preguntas.getPreguntas().size();i++){
			PreguntaDTO pregunta=new PreguntaDTO();
			UsuarioDTO usuario=new UsuarioDTO();
			
			pregunta=preguntas.getPreguntas().get(i);
			usuario.setNom_user(preguntas.getNom_user());
			
			numeroPregunta=facadeLogicaServiceBean.getpreguntaLogica().insertarPregunta(usuario, pregunta);
		}
		
		if(numeroPregunta!=Constantes.NOT_SUCCESS){
			return Response.ok().header("NumeroPregunta", Long.toString(numeroPregunta)).build();
		}else{
			return Response.serverError().build();
		}
	}
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/getPreguntasXUsuarioWithJson")
	public PreguntasDTO getPreguntasXUsuarioJson(UsuarioDTO u) throws Exception{
		
		PreguntasDTO preguntas;
		UsuarioDTO usuario=new UsuarioDTO();
		usuario.setNom_user(u.getNom_user());
		
		System.out.println("*********getPreguntasXUsuarioWithJson********");
		
		preguntas=facadeLogicaServiceBean.getpreguntaLogica().getPreguntasXUsuario(usuario);
		if(preguntas==null || preguntas.getPreguntas().size()==0){
			Response.noContent().header("Error", new String("No existen preguntas")).build();
		}else{
			Response.ok().build();
		}
		
		return preguntas;
		
	}
	
	
	@POST
	//@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/preguntarWithJson")
	public Response preguntarWithJson(PreguntasDTO preguntas) throws Exception{
		System.out.println("*********preguntarWithJson********");

		PreguntaDTO pregunta=new PreguntaDTO();
		UsuarioDTO usuario=new UsuarioDTO();
		ContactoDTO contactoDTO = new ContactoDTO();
		String statusCode="";
		pregunta=preguntas.getPreguntas().get(0);
		String codGcm="";
		MensajeGcm mensajeGcm = new MensajeGcm();
		SessionDTO sessionDTO = new SessionDTO();

		
		usuario.setNom_user(preguntas.getNom_user());
		codGcm=facadeLogicaServiceBean.getGcmLogica().validacionesGcm(usuario,null,Constantes.TIPO_MENSAJE_GCM_PREGUNTA);
		
		
		if(codGcm.equals("4")){
			
			System.out.println("El codigo gcm ha expirado");
			return Response.serverError().header("Error", new String("El codigo gcm ha expirado")).build();

		}else if(codGcm.equals("5")){
			
			facadeLogicaServiceBean.getpreguntaPendienteLogica().insertarPreguntaPendiente(usuario, preguntas.getPreguntas().get(0),Constantes.ESTADO_PREGUNTA_PENDIENTE);
			System.out.println("El dispositivo emisor es el mismo del receptor,se enviara" +
					"mensaje cuando el contacto inicie sesion");
			return Response.status(Status.CONFLICT).header("Error", "La pregunta no se ha enviado,se enviara cuando usuario inicie sesion").build();

		}else if(!codGcm.equals("")){
			contactoDTO = facadeLogicaServiceBean.getcontactoLogica().getContactoXUsuario(usuario);
			sessionDTO = facadeLogicaServiceBean.getSessionLogica().getSessionByUsuario(contactoDTO.getContacto());
			if(sessionDTO.getEstado() == Constantes.SESSION_STARTED){
				System.out.println("Gcm="+codGcm+",Usuario="+usuario.getNom_user());
				//se envia mensaje
				//facadeLogicaServiceBean.getGcmLogica().sendGcmMessage(codGcm, pregunta.getPregunta(), Constantes.TIPO_MENSAJE_GCM_PREGUNTA);
				mensajeGcm.sendGcmMessage(codGcm, pregunta.getNumero()+":"+pregunta.getPregunta(), Constantes.TIPO_MENSAJE_GCM_PREGUNTA);
				
				 return Response.ok().build();
			}else{
				facadeLogicaServiceBean.getpreguntaPendienteLogica().insertarPreguntaPendiente(usuario, preguntas.getPreguntas().get(0),Constantes.ESTADO_PREGUNTA_PENDIENTE);
				System.out.println("Pregunta no se ha enviado,se ha almacenado en estado PENDIENTE");
				return Response.status(Status.CONFLICT).header("Error", "Pregunta no se ha enviado,se enviara cuando usuario inicie sesion").build();
			}
			
			
		}
		
		return Response.serverError().header("Error", new String("Ha ocurrido un error inesperado")).build();
		
       
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/responderPreguntaWithJson")
	public Response responderPreguntaWithJson(RespuestaPreguntasDTO respuesta) throws Exception{
		System.out.println("*********INI responderPreguntaWithJson********");

		PreguntaDTO pregunta=new PreguntaDTO();
		UsuarioDTO usuario=new UsuarioDTO();
		ContactoDTO contacto = new ContactoDTO();
		SessionDTO sessionDTO = new SessionDTO();
		RespuestaPendienteDTO respuestaPendienteDTO = new RespuestaPendienteDTO();
		PreguntaPendienteDTO preguntaPendienteDTO = new PreguntaPendienteDTO();
		pregunta=respuesta.getPreguntas().get(0);
		String codGcm="";
		MensajeGcm mensajeGcm = new MensajeGcm();
		
		usuario.setNom_user(respuesta.getNom_user());
		contacto = facadeLogicaServiceBean.getcontactoLogica().getUsuarioXContacto(usuario);
		
		codGcm=facadeLogicaServiceBean.getGcmLogica().validacionesGcm(usuario,null,Constantes.TIPO_MENSAJE_GCM_RESPUESTA_PREGUNTA);
		/*INI Se verifica si es pregunta pendiente*/
		preguntaPendienteDTO = facadeLogicaServiceBean.getpreguntaPendienteLogica().getPreguntaPendienteByNumeroEstado(pregunta.getNumero(), Constantes.ESTADO_PREGUNTA_PENDIENTE);
			if(preguntaPendienteDTO != null){
				facadeLogicaServiceBean.getpreguntaPendienteLogica().actualizarEstadoPreguntaPendiente(preguntaPendienteDTO.getId(), Constantes.ESTADO_PREGUNTA_CONFIRMADA);
			}
		/*FIN Se verifica si es pregunta pendiente*/
		
		if(codGcm.equals("4")){
			
			System.out.println("El codigo gcm ha expirado");
			codGcm="";
			return Response.serverError().header("Error", new String("El codigo gcm ha expirado")).build();

		}else if(codGcm.equals("5")){
			
			//Implementar almacenamiento de respuesta
			//ppDelegate.insertarPreguntaPendiente(usuario, preguntas.getPreguntas().get(0));
			System.out.println("El dispositivo emisor es el mismo del receptor,se enviara" +
					"mensaje cuando el contacto inicie sesion");
			codGcm="";
			return Response.status(Status.CONFLICT).header("Error", "Solicitud no se ha enviado,se enviara cuando usuario inicie sesion").build();

		}else if(!codGcm.equals("")){
			
			//se envia mensaje
			sessionDTO = facadeLogicaServiceBean.getSessionLogica().getSessionByUsuario(contacto.getNomUser());
			if(sessionDTO.getEstado() == Constantes.SESSION_STARTED){
				mensajeGcm.sendGcmMessage(codGcm, pregunta.getPregunta()+":"+respuesta.getRespuesta(), Constantes.TIPO_MENSAJE_GCM_RESPUESTA_PREGUNTA);
				return Response.ok().build();
			}else{
				/*Se setea respuesta*/
				respuestaPendienteDTO.setNumero(pregunta.getNumero());
				respuestaPendienteDTO.setEstado(Constantes.ESTADO_RESPUESTA_PENDIENTE);
				respuestaPendienteDTO.setRespuesta(respuesta.getRespuesta());
				respuestaPendienteDTO.setContacto(contacto.getNomUser());
				facadeLogicaServiceBean.getRespuestaPendiente().insertarRespuestaPendiente(respuestaPendienteDTO);
				System.out.println("Respuesta no se ha enviado,se ha almacenado en estado PENDIENTE");
				return Response.status(Status.CONFLICT).header("Error", "Respuesta no se ha enviado,se enviara cuando usuario inicie sesion").build();
			}
			
		}
		
		return Response.serverError().header("Error", new String("Ha ocurrido un error inesperado")).build();
		
       
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/resultadoWithJson")
	public Response resultadoWithJson(RespuestaPreguntasDTO respuesta) throws Exception{
		System.out.println("*********preguntarWithJson********");

		PreguntaDTO pregunta=new PreguntaDTO();
		UsuarioDTO usuario=new UsuarioDTO();
		pregunta=respuesta.getPreguntas().get(0);
		String codGcm="";
		MensajeGcm mensajeGcm = new MensajeGcm();
		
		usuario.setNom_user(respuesta.getNom_user());
		//Correguir validacionesGcm para RESPUESTA_PREGUNTA
		codGcm=facadeLogicaServiceBean.getGcmLogica().validacionesGcm(usuario,null,Constantes.TIPO_MENSAJE_GCM_RESPUESTA_PREGUNTA);
		
		
		if(codGcm.equals("4")){
			
			System.out.println("El codigo gcm ha expirado");
			codGcm="";
			return Response.serverError().header("Error", new String("El codigo gcm ha expirado")).build();

		}else if(codGcm.equals("5")){
			
			//Implementar almacenamiento de respuesta
			//ppDelegate.insertarPreguntaPendiente(usuario, preguntas.getPreguntas().get(0));
			System.out.println("El dispositivo emisor es el mismo del receptor,se enviara" +
					"mensaje cuando el contacto inicie sesion");
			codGcm="";
			return Response.status(Status.CONFLICT).header("Error", "Solicitud no se ha enviado,se enviara cuando usuario inicie sesion").build();

		}else if(!codGcm.equals("")){
			
			//se envia mensaje
			//facadeLogicaServiceBean.getGcmLogica().sendGcmMessage(codGcm, pregunta.getPregunta()+":"+respuesta.getRespuesta(), Constantes.TIPO_MENSAJE_GCM_RESPUESTA_PREGUNTA);
			mensajeGcm.sendGcmMessage(codGcm, pregunta.getPregunta()+":"+respuesta.getRespuesta(), Constantes.TIPO_MENSAJE_GCM_RESPUESTA_PREGUNTA);
			
			 return Response.ok().build();
		}
		
		return Response.serverError().header("Error", new String("Ha ocurrido un error inesperado")).build();
		
       
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/puntuacionWithJson")
	public Response puntuacionWithJson(PuntuacionResultadoDTO PResultado) throws Exception{
		System.out.println("*********puntuacionWithJson********");
		//usuarioServiceBean.holaMundo();
		
		UsuarioDTO usuario=new UsuarioDTO();
		UsuarioDTO usuarioContacto=new UsuarioDTO();
		ContactoDTO UsuariocContacto=new ContactoDTO();
		ContactoDTO idContacto=new ContactoDTO();
		SessionDTO sessionDTO = new SessionDTO();
		PuntuacionPendienteDTO puntuacionPendienteDTO = new PuntuacionPendienteDTO();
		String codGcm="";
		int resp=0;
		MensajeGcm mensajeGcm = new MensajeGcm();
		
		usuario.setNom_user(PResultado.getNom_user());
		UsuariocContacto = facadeLogicaServiceBean.getcontactoLogica().getContactoXUsuario(usuario);
		idContacto=facadeLogicaServiceBean.getcontactoLogica().getIdContactoXUsuario(usuario,UsuariocContacto);
		usuarioContacto.setNom_user(UsuariocContacto.getContacto());
		
		resp = facadeLogicaServiceBean.getpuntuacionLogica().puntuacion(idContacto.getId_contacto(), PResultado);
		System.out.println("puntuacionWithJson,puntos despues de funcion 'puntuacion'"+PResultado.getPuntos());

		
		//Correguir validacionesGcm para RESPUESTA_PREGUNTA
		codGcm=facadeLogicaServiceBean.getGcmLogica().validacionesGcm(usuario,usuarioContacto,Constantes.TIPO_MENSAJE_GCM_PUNTUACION);
		
		
		if(codGcm.equals("4")){
			
			System.out.println("El codigo gcm ha expirado");
			return Response.serverError().header("Error", new String("El codigo gcm ha expirado")).build();

		}else if(codGcm.equals("5")){
			
			//Implementar almacenamiento de respuesta
			//ppDelegate.insertarPreguntaPendiente(usuario, preguntas.getPreguntas().get(0));
			System.out.println("El dispositivo emisor es el mismo del receptor,se enviara" +
					"mensaje cuando el contacto inicie sesion");
			
			return Response.status(Status.CONFLICT).header("Error", "Solicitud no se ha enviado,se enviara cuando usuario inicie sesion").build();

		}else if(!codGcm.equals("")){
			sessionDTO = facadeLogicaServiceBean.getSessionLogica().getSessionByUsuario(UsuariocContacto.getContacto());
			if(sessionDTO.getEstado() == Constantes.SESSION_STARTED){
				//se envia mensaje
				mensajeGcm.sendGcmMessage(codGcm,Integer.toString(PResultado.getPuntos()) , Constantes.TIPO_MENSAJE_GCM_PUNTUACION);
				return Response.ok().header("Puntos",Integer.toString(PResultado.getPuntos())).build();

			}else{
				puntuacionPendienteDTO.setContacto(UsuariocContacto.getContacto());
				puntuacionPendienteDTO.setEstado(Constantes.ESTADO_PUNTUACION_PENDIENTE);
				puntuacionPendienteDTO.setPuntos(PResultado.getPuntos());
				facadeLogicaServiceBean.getPuntuacionPendienteLogica().insertarPuntuacionPendiente(puntuacionPendienteDTO);
				System.out.println("Puntuacion no se ha enviado,se ha almacenado en estado PENDIENTE");
				return Response.status(Status.CONFLICT).header("Error", "Puntuacion no se ha enviado,se enviara cuando usuario inicie sesion").build();
			}
			
		}
		
		return Response.serverError().header("Error", new String("Ha ocurrido un error inesperado")).build();
		
       
	}
	
	@POST
	//@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/eliminarPreguntaJson")
	public Response eliminarPreguntaJson(PreguntaDTO preguntaDTO) throws Exception{
		System.out.println("INI eliminarPreguntaJson");
		int resEliminarPregunta = 0;
		
		resEliminarPregunta = facadeLogicaServiceBean.getpreguntaLogica().eliminarPregunta(preguntaDTO);
		
		if(resEliminarPregunta == Constantes.SUCCESS){
			return Response.ok().build();
		}else if(resEliminarPregunta == Constantes.NOT_EXIST){
			return Response.notModified().header("Error",new String("No existe pregunta,no se ha eliminado")).build();
		}else{
			return Response.serverError().build();
		}
	}
	
	@POST
	//@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/modificarPreguntaJson")
	public Response modificarPreguntaJson(PreguntaDTO preguntaDTO) throws Exception{
		System.out.println("INI modificarPreguntaJson");
		int resModificarPregunta = 0;
		
		resModificarPregunta = facadeLogicaServiceBean.getpreguntaLogica().actualizarPregunta(preguntaDTO);
		if(resModificarPregunta == Constantes.SUCCESS){
			return Response.ok().build();
		}else if(resModificarPregunta == Constantes.NOT_EXIST){
			return Response.notModified().header("Error",new String("No existe pregunta,no se ha modificado")).build();
		}else{
			return Response.serverError().build();
		}
	}
}
