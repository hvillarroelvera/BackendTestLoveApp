package com.hector.apptestlove.rest;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntaPendienteDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;

@Path("/preguntaPendiente")
public class PreguntaPendienteServicio {

	@EJB 
	private FacadeLogicaServiceBean facadeLogicaServiceBean;
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/eliminarAllPreguntaPendienteByContactoJson")
	public Response eliminarAllPreguntaPendienteByContactoJson(PreguntaPendienteDTO preguntaPendienteDTO) throws Exception{
		int resp = 0;
		ArrayList<PreguntaPendienteDTO>listaPreguntasPendientes= new ArrayList<PreguntaPendienteDTO>();
		PreguntaPendienteDTO preguntaPendienteDTORecuperadas = null;
		listaPreguntasPendientes = facadeLogicaServiceBean.getpreguntaPendienteLogica().getPreguntaPendienteByContactoEstado(preguntaPendienteDTO.getContacto(), Constantes.ESTADO_PREGUNTA_PENDIENTE);
		if(listaPreguntasPendientes != null){
			for(int i=0;i<listaPreguntasPendientes.size();i++){
				preguntaPendienteDTORecuperadas = new PreguntaPendienteDTO();
				preguntaPendienteDTORecuperadas = listaPreguntasPendientes.get(i);
				resp = facadeLogicaServiceBean.getpreguntaPendienteLogica().eliminarPreguntaPendiente(preguntaPendienteDTORecuperadas);
				if(resp != Constantes.SUCCESS && resp != Constantes.NOT_EXIST){
					return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar pregunta pendiente")).build();
				}
			}
		}
		
		
		listaPreguntasPendientes = facadeLogicaServiceBean.getpreguntaPendienteLogica().getPreguntaPendienteByContactoEstado(preguntaPendienteDTO.getContacto(), Constantes.ESTADO_PREGUNTA_CONFIRMADA);
		if(listaPreguntasPendientes != null){
			for(int i=0;i<listaPreguntasPendientes.size();i++){
				preguntaPendienteDTORecuperadas = new PreguntaPendienteDTO();
				preguntaPendienteDTORecuperadas = listaPreguntasPendientes.get(i);
				resp = facadeLogicaServiceBean.getpreguntaPendienteLogica().eliminarPreguntaPendiente(preguntaPendienteDTORecuperadas);
				if(resp != Constantes.SUCCESS && resp != Constantes.NOT_EXIST){
					return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar pregunta pendiente")).build();
				}
			}
		}
		
		
		listaPreguntasPendientes = facadeLogicaServiceBean.getpreguntaPendienteLogica().getPreguntaPendienteByContactoEstado(preguntaPendienteDTO.getContacto(), Constantes.ESTADO_PREGUNTA_ENVIADA);
		if(listaPreguntasPendientes != null){
			for(int i=0;i<listaPreguntasPendientes.size();i++){
				preguntaPendienteDTORecuperadas = new PreguntaPendienteDTO();
				preguntaPendienteDTORecuperadas = listaPreguntasPendientes.get(i);
				resp = facadeLogicaServiceBean.getpreguntaPendienteLogica().eliminarPreguntaPendiente(preguntaPendienteDTORecuperadas);
				if(resp != Constantes.SUCCESS && resp != Constantes.NOT_EXIST){
					return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar pregunta pendiente")).build();
				}
			}
		}
		
		
		return Response.ok().build();
		
	}
}
