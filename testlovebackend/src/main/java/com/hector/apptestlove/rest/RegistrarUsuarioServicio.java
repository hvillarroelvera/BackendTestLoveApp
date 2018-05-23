package com.hector.apptestlove.rest;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntaPendienteDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.PuntuacionPendienteDTO;
import com.hector.apptestlove.controler.dto.RegistrarUsuarioDTO;
import com.hector.apptestlove.controler.dto.RespuestaPendienteDTO;
import com.hector.apptestlove.controler.dto.SessionDTO;
import com.hector.apptestlove.controler.dto.SolicitudDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;
import com.hector.apptestlove.mensaje.MensajeGcm;


@Path("/usuario")
public class RegistrarUsuarioServicio {

@EJB 
private FacadeLogicaServiceBean facadeLogicaServiceBean;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/registrarWithJson")
	public Response registrarUsuarioJson(RegistrarUsuarioDTO ru) throws Exception{
	System.out.println("******INI registrarUsuarioJson***********");
		int respuesta=0;
		UsuarioDTO usuario=new UsuarioDTO();
		GcmDTO gcm=new GcmDTO();
		SessionDTO sessionDTO = new SessionDTO();
		
		if(facadeLogicaServiceBean.getUsuarioLogica().buscarUsuario(usuario )== Constantes.NOT_SUCCESS){
			
		
		usuario.setNom_user(ru.getNom_user());
		usuario.setPassword(ru.getPassword());
		usuario.setCodGcm(ru.getCodGcm());
		
		gcm.setGcm_codGcm(ru.getCodGcm());
		gcm.setExpirationTime(ru.getExpirationTime());
		gcm.setAppVersion(ru.getAppVersion());
		    respuesta = facadeLogicaServiceBean.getUsuarioLogica().registrarUsuario(usuario);
		
		    /*ini Se inserta session sin fecha y en estado 0*/
		    sessionDTO.setUsuario(ru.getNom_user());
		    sessionDTO.setEstado(Constantes.SESSION_FINALIZED);
		    sessionDTO.setUltimaFechaLogin(null);
		    respuesta = facadeLogicaServiceBean.getSessionLogica().insertarSession(sessionDTO);
		    /*FIN Se inserta session sin fecha y en estado 0*/
		   
		if(respuesta == Constantes.SUCCESS){
			respuesta=facadeLogicaServiceBean.getGcmLogica().registrarGcmLogica(gcm, ru.getNom_user());
			System.out.println("******FIN registrarUsuarioJson***********");
			if(respuesta == Constantes.SUCCESS){
				return Response.ok().build();
			}else{
				return Response.serverError().build();
			}
		}else{
			System.out.println("******FIN registrarUsuarioJson***********");
			return Response.serverError().build();
		}
	  }else{
		  System.out.println("******FIN registrarUsuarioJson***********");
			return Response.serverError().header("Error", new String("El usuario ya existe")).build();
	  }
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/loguearWithJson")
	public Response loguearUsuarioJson(UsuarioDTO u) throws Exception{
		System.out.println("*********INI loguearUsuarioJson********");
		int respuesta=0;
		String gcm = "";
		UsuarioDTO usuario=new UsuarioDTO();
		ArrayList<SolicitudDTO>listaSolicitudesPendientes = new ArrayList<SolicitudDTO>();
		ArrayList<PreguntaDTO>listaPreguntaDTO = new ArrayList<PreguntaDTO>();
		ArrayList<PreguntaPendienteDTO>listaPreguntasPendientes= new ArrayList<PreguntaPendienteDTO>();
		ArrayList<RespuestaPendienteDTO>listaRespuestasPendientes= new ArrayList<RespuestaPendienteDTO>();
		ArrayList<PuntuacionPendienteDTO>listaPuntuacionPendiente= new ArrayList<PuntuacionPendienteDTO>();
		PuntuacionPendienteDTO puntuacionPendienteDTO = new PuntuacionPendienteDTO();
		RespuestaPendienteDTO respuestaPendienteDTO = new RespuestaPendienteDTO();
		PreguntaPendienteDTO preguntaPendienteDTO = new PreguntaPendienteDTO();
		SolicitudDTO solicitudDTO = new SolicitudDTO();
		SolicitudDTO solicitudDTO2 = new SolicitudDTO();
		ContactoDTO contactoDTO = new ContactoDTO();
		PreguntaDTO preguntaDTO = new PreguntaDTO();
		RespuestaPendienteDTO respuestaPreguntaDTO = new RespuestaPendienteDTO();
		MensajeGcm mensajeGcm = new MensajeGcm();
		GcmDTO gcmDTO = new GcmDTO();
		usuario.setNom_user(u.getNom_user());
		usuario.setPassword(u.getPassword());
		
		respuesta=facadeLogicaServiceBean.getUsuarioLogica().loguearUsuario(usuario);
		if(respuesta == Constantes.SUCCESS){
		gcmDTO = facadeLogicaServiceBean.getGcmLogica().getGcmXUsuarioLogica(usuario);
		
		/*INI Se envian solicitudes pendientes*/
		System.out.println("INI Se envian solicitudes pendientes");
			solicitudDTO.setUserReceptor(u.getNom_user());
			solicitudDTO.setEstado(Constantes.ESTADO_SOLICITUD_PENDIENTE);
			solicitudDTO.setTipo(Constantes.TIPO_SOLICITUD_SOL_CONTACTO);
			listaSolicitudesPendientes = facadeLogicaServiceBean.getsolicitudLogica().getAllSolicitudesByReceptorEstadoTipo(solicitudDTO);
			if(listaSolicitudesPendientes != null){
				for(int i=0;i<listaSolicitudesPendientes.size();i++){
					solicitudDTO2 = listaSolicitudesPendientes.get(i);
					mensajeGcm.enviarSolicitud(gcmDTO.getGcm_codGcm(), solicitudDTO2.getUserEmisor());
					facadeLogicaServiceBean.getsolicitudLogica().actualizarSolicitud(solicitudDTO2.getNumero(), Constantes.ESTADO_SOLICITUD_ENVIADA);
				}
			}
			System.out.println("FIN Se envian solicitudes pendientes");

		/*FIN Se envian solicitudes pendientes*/
			
		/*INI Se envian solicitudes aceptadas pendientes*/
			System.out.println("INI Se envian solicitudes aceptadas pendientes");

			solicitudDTO.setUserEmisor(u.getNom_user());
			solicitudDTO.setEstado(Constantes.ESTADO_SOLICITUD_ACEPTADA_PENDIENTE);
			solicitudDTO.setTipo(Constantes.TIPO_SOLICITUD_SOL_CONTACTO);
			listaSolicitudesPendientes = facadeLogicaServiceBean.getsolicitudLogica().getAllSolicitudesByEmisorEstadoTipo(solicitudDTO);
			if(listaSolicitudesPendientes != null){
				for(int i=0;i<listaSolicitudesPendientes.size();i++){
					solicitudDTO2 = listaSolicitudesPendientes.get(i);
					mensajeGcm.enviarSolicitud(gcmDTO.getGcm_codGcm(), solicitudDTO2.getUserEmisor());
					facadeLogicaServiceBean.getsolicitudLogica().actualizarSolicitud(solicitudDTO2.getNumero(), Constantes.ESTADO_SOLICITUD_ACEPTADA_RECIBIDA);
				}
			}
			System.out.println("FIN Se envian solicitudes aceptadas pendientes");
		/*FIN Se envian solicitudes aceptadas pendientes*/
			
		/*INI Se envian solicitudes rechazadas pendientes*/
			System.out.println("INI Se envian solicitudes rechazadas pendientes");

			solicitudDTO.setUserEmisor(u.getNom_user());
			solicitudDTO.setEstado(Constantes.ESTADO_SOLICITUD_RECHAZADA_PENDIENTE);
			solicitudDTO.setTipo(Constantes.TIPO_SOLICITUD_SOL_CONTACTO);
			listaSolicitudesPendientes = facadeLogicaServiceBean.getsolicitudLogica().getAllSolicitudesByEmisorEstadoTipo(solicitudDTO);
			if(listaSolicitudesPendientes != null){
				for(int i=0;i<listaSolicitudesPendientes.size();i++){
					solicitudDTO2 = listaSolicitudesPendientes.get(i);
					mensajeGcm.enviarSolicitud(gcmDTO.getGcm_codGcm(), solicitudDTO2.getUserEmisor());
					facadeLogicaServiceBean.getsolicitudLogica().actualizarSolicitud(solicitudDTO2.getNumero(), Constantes.ESTADO_SOLICITUD_RECHAZADA_RECIBIDA);
				}
			}
			System.out.println("FIN Se envian solicitudes rechazadas pendientes");

		/*FIN Se envian solicitudes rechazadas pendientes*/
		
		/*INI Se envian las preguntas pendientes*/
			System.out.println("INI Se envian las preguntas pendientes");

		
		
			System.out.println("HVV contactoDTO["+contactoDTO.getContacto()+"]");
			listaPreguntasPendientes = facadeLogicaServiceBean.getpreguntaPendienteLogica().getPreguntaPendienteByContactoEstado(usuario.getNom_user(),Constantes.ESTADO_PREGUNTA_PENDIENTE);
			
			if(listaPreguntasPendientes != null){
				System.out.println("HVV listaPreguntasPendientes["+listaPreguntasPendientes.size()+"]");
				for(int i = 0;i<listaPreguntasPendientes.size();i++){
					preguntaPendienteDTO = listaPreguntasPendientes.get(i);
					 /*Se recupera pregunta por su numero*/
					preguntaDTO = facadeLogicaServiceBean.getpreguntaLogica().getPreguntaByNumero(preguntaPendienteDTO.getNumero());
					mensajeGcm.sendGcmMessage(gcmDTO.getGcm_codGcm(),preguntaDTO.getNumero()+":"+preguntaDTO.getPregunta() , Constantes.TIPO_MENSAJE_GCM_PREGUNTA);
					facadeLogicaServiceBean.getpreguntaPendienteLogica().actualizarEstadoPreguntaPendiente(preguntaPendienteDTO.getId(), Constantes.ESTADO_PREGUNTA_ENVIADA);
				}
			}
		
		System.out.println("FIN Se envian las preguntas pendientes");

		/*FIN Se envian las preguntas pendientes*/
		
		/*INI Se envian respuestas de preguntas*/
		System.out.println("INI Se envian respuestas de preguntas");

	
			listaRespuestasPendientes = facadeLogicaServiceBean.getRespuestaPendiente().getRespuestaPendienteByEstado(usuario.getNom_user(),Constantes.ESTADO_RESPUESTA_PENDIENTE);
			if(listaRespuestasPendientes != null){
				for(int i = 0;i<listaRespuestasPendientes.size();i++){
					respuestaPendienteDTO = listaRespuestasPendientes.get(i);
					preguntaDTO = facadeLogicaServiceBean.getpreguntaLogica().getPreguntaByNumero(respuestaPendienteDTO.getNumero());
					mensajeGcm.sendGcmMessage(gcmDTO.getGcm_codGcm(), preguntaDTO.getPregunta()+":"+respuestaPendienteDTO.getRespuesta(), Constantes.TIPO_MENSAJE_GCM_RESPUESTA_PREGUNTA);
					facadeLogicaServiceBean.getRespuestaPendiente().actualizarEstadoRespuestaPendiente(respuestaPendienteDTO.getId(), Constantes.ESTADO_RESPUESTA_ENVIADA);
				}
				
			}
		
		System.out.println("FIN Se envian respuestas de preguntas");

		/*FIN Se envian respuestas de preguntas*/
		
		/*INI Se envia puntuacion*/
		System.out.println("INI Se envia puntuacion");

		
			listaPuntuacionPendiente = facadeLogicaServiceBean.getPuntuacionPendienteLogica().getPuntuacionPendienteDTOByEstado(usuario.getNom_user(),Constantes.ESTADO_PUNTUACION_PENDIENTE);
			if(listaPuntuacionPendiente != null){
				for(int i = 0;i<listaPuntuacionPendiente.size();i++){
					puntuacionPendienteDTO = listaPuntuacionPendiente.get(i);
					mensajeGcm.sendGcmMessage(gcmDTO.getGcm_codGcm(),Integer.toString(puntuacionPendienteDTO.getPuntos()) , Constantes.TIPO_MENSAJE_GCM_PUNTUACION);
					facadeLogicaServiceBean.getPuntuacionPendienteLogica().actualizarEstadoPuntuacionPendiente(puntuacionPendienteDTO.getId(), Constantes.ESTADO_PUNTUACION_ENVIADA);
				}
			}
		
		System.out.println("FIN Se envia puntuacion");

		/*FIN Se envia puntuacion*/
		
		
		System.out.println("*********FIN loguearUsuarioJson********");
		
			respuesta=facadeLogicaServiceBean.getSessionLogica().modificarSessionToEstadoStarted(usuario.getNom_user());
			if(respuesta == Constantes.SUCCESS){
				return Response.ok().build();
				}else{
				return Response.serverError().header("Error", new String("Ha ocurrido un error al cambiar estado de sesion a STARTED")).build();
			}
			
		}else{
			return Response.serverError().build();
		}
	
		
		
		
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/logoutWithJson")
	public Response logoutWithJson(UsuarioDTO u) throws Exception{
		System.out.println("*********INI logoutWithJson********");
		int respuesta=0;
		UsuarioDTO usuario=new UsuarioDTO();
		usuario.setNom_user(u.getNom_user());
		
		respuesta=facadeLogicaServiceBean.getSessionLogica().modificarSessionToEstadoFinalized(usuario.getNom_user());
		System.out.println("*********FIN logoutWithJson********");
		if(respuesta == Constantes.SUCCESS){
			return Response.ok().build();
		}else{
			return Response.serverError().build();
		}
	
		
		
		
	}
}
