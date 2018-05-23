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
import com.hector.apptestlove.controler.dto.PreguntaPendienteDTO;
import com.hector.apptestlove.controler.dto.RespuestaPendienteDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;

@Path("/respuestaPendiente")
public class RespuestaPendienteServicio {

	@EJB 
	private FacadeLogicaServiceBean facadeLogicaServiceBean;
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/eliminarRespuestaPendienteJson")
	public Response eliminarRespuestaPendienteJson(RespuestaPendienteDTO respuestaPendienteDTO) throws Exception{
		int resp = 0;
		resp = facadeLogicaServiceBean.getRespuestaPendiente().eliminarRespuestaPendiente(respuestaPendienteDTO);
		if(resp == Constantes.SUCCESS){
			return Response.ok().build();
		}else{
			return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar respuesta pendiente")).build();

		}
	}
	
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/eliminarAllRespuestaPendienteByContactoJson")
	public Response eliminarAllRespuestaPendienteByContactoJson(RespuestaPendienteDTO respuestaPendienteDTO) throws Exception{
		int resp = 0;
		ArrayList<RespuestaPendienteDTO>listaRespuestaPendientes= new ArrayList<RespuestaPendienteDTO>();
		RespuestaPendienteDTO respuestaPendienteDTORecuperadas = null;
		listaRespuestaPendientes = facadeLogicaServiceBean.getRespuestaPendiente().getRespuestaPendienteByEstado(respuestaPendienteDTO.getContacto(), Constantes.ESTADO_RESPUESTA_PENDIENTE);
		if(listaRespuestaPendientes != null){
			for(int i=0;i<listaRespuestaPendientes.size();i++){
				respuestaPendienteDTORecuperadas = new RespuestaPendienteDTO();
				respuestaPendienteDTORecuperadas = listaRespuestaPendientes.get(i);
				resp = facadeLogicaServiceBean.getRespuestaPendiente().eliminarRespuestaPendiente(respuestaPendienteDTORecuperadas);
				if(resp != Constantes.SUCCESS && resp != Constantes.NOT_EXIST){
					return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar respuesta pendiente")).build();
				}
			}
		}
		
		
		listaRespuestaPendientes = facadeLogicaServiceBean.getRespuestaPendiente().getRespuestaPendienteByEstado(respuestaPendienteDTO.getContacto(), Constantes.ESTADO_RESPUESTA_CONFIRMADA);
		if(listaRespuestaPendientes != null){
			for(int i=0;i<listaRespuestaPendientes.size();i++){
				respuestaPendienteDTORecuperadas = new RespuestaPendienteDTO();
				respuestaPendienteDTORecuperadas = listaRespuestaPendientes.get(i);
				resp = facadeLogicaServiceBean.getRespuestaPendiente().eliminarRespuestaPendiente(respuestaPendienteDTORecuperadas);
				if(resp != Constantes.SUCCESS && resp != Constantes.NOT_EXIST){
					return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar respuesta pendiente")).build();
				}
			}
		}
		
		
		listaRespuestaPendientes = facadeLogicaServiceBean.getRespuestaPendiente().getRespuestaPendienteByEstado(respuestaPendienteDTO.getContacto(), Constantes.ESTADO_RESPUESTA_ENVIADA);
		if(listaRespuestaPendientes != null){
			for(int i=0;i<listaRespuestaPendientes.size();i++){
				respuestaPendienteDTORecuperadas = new RespuestaPendienteDTO();
				respuestaPendienteDTORecuperadas = listaRespuestaPendientes.get(i);
				resp = facadeLogicaServiceBean.getRespuestaPendiente().eliminarRespuestaPendiente(respuestaPendienteDTORecuperadas);
				if(resp != Constantes.SUCCESS && resp != Constantes.NOT_EXIST){
					return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar respuesta pendiente")).build();
				}
			}
		}
		
		
		return Response.ok().build();
		
	}
}
