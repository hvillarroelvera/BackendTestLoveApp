package com.hector.apptestlove.rest;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.PuntuacionDTO;
import com.hector.apptestlove.controler.dto.PuntuacionPendienteDTO;
import com.hector.apptestlove.controler.dto.RespuestaPendienteDTO;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;

@Path("/puntuacionPendiente")
public class PuntuacionPendienteServicio {

	@EJB 
	private FacadeLogicaServiceBean facadeLogicaServiceBean;
	
	@DELETE
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/eliminarPuntuacionPendienteJson")
	public Response eliminarPuntuacionPendienteJson(PuntuacionPendienteDTO puntuacionPendienteDTO) throws Exception{
		int resp = 0;
		resp = facadeLogicaServiceBean.getPuntuacionPendienteLogica().eliminarPuntuacionPendiente(puntuacionPendienteDTO);
		if(resp == Constantes.SUCCESS){
			return Response.ok().build();
		}else{
			return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar puntuacion pendiente")).build();

		}
	}
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/eliminarAllPuntuacionPendienteByContactoJson")
	public Response eliminarAllPuntuacionPendienteByContactoJson(PuntuacionPendienteDTO puntuacionPendienteDTO) throws Exception{
		int resp = 0;
		ArrayList<PuntuacionPendienteDTO>listaPuntuacionPendientes= new ArrayList<PuntuacionPendienteDTO>();
		PuntuacionPendienteDTO puntuacionPendienteDTORecuperadas = null;
		listaPuntuacionPendientes = facadeLogicaServiceBean.getPuntuacionPendienteLogica().getPuntuacionPendienteDTOByEstado(puntuacionPendienteDTO.getContacto(), Constantes.ESTADO_PUNTUACION_PENDIENTE);
		if(listaPuntuacionPendientes != null){
			for(int i=0;i<listaPuntuacionPendientes.size();i++){
				puntuacionPendienteDTORecuperadas = new PuntuacionPendienteDTO();
				puntuacionPendienteDTORecuperadas = listaPuntuacionPendientes.get(i);
				resp = facadeLogicaServiceBean.getPuntuacionPendienteLogica().eliminarPuntuacionPendiente(puntuacionPendienteDTORecuperadas);
				if(resp != Constantes.SUCCESS && resp != Constantes.NOT_EXIST){
					return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar puntuacion pendiente")).build();
				}
			}
		}
	
		
		listaPuntuacionPendientes = facadeLogicaServiceBean.getPuntuacionPendienteLogica().getPuntuacionPendienteDTOByEstado(puntuacionPendienteDTO.getContacto(), Constantes.ESTADO_PUNTUACION_CONFIRMADA);
		if(listaPuntuacionPendientes != null){
			for(int i=0;i<listaPuntuacionPendientes.size();i++){
				puntuacionPendienteDTORecuperadas = new PuntuacionPendienteDTO();
				puntuacionPendienteDTORecuperadas = listaPuntuacionPendientes.get(i);
				resp = facadeLogicaServiceBean.getPuntuacionPendienteLogica().eliminarPuntuacionPendiente(puntuacionPendienteDTORecuperadas);
				if(resp != Constantes.SUCCESS && resp != Constantes.NOT_EXIST){
					return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar puntuacion pendiente")).build();
				}
			}
		}
		
		
		listaPuntuacionPendientes = facadeLogicaServiceBean.getPuntuacionPendienteLogica().getPuntuacionPendienteDTOByEstado(puntuacionPendienteDTO.getContacto(), Constantes.ESTADO_PUNTUACION_ENVIADA);
		if(listaPuntuacionPendientes != null){
			for(int i=0;i<listaPuntuacionPendientes.size();i++){
				puntuacionPendienteDTORecuperadas = new PuntuacionPendienteDTO();
				puntuacionPendienteDTORecuperadas = listaPuntuacionPendientes.get(i);
				resp = facadeLogicaServiceBean.getPuntuacionPendienteLogica().eliminarPuntuacionPendiente(puntuacionPendienteDTORecuperadas);
				if(resp != Constantes.SUCCESS && resp != Constantes.NOT_EXIST){
					return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar puntuacion pendiente")).build();
				}
			}
		}
		
		
		return Response.ok().build();
		
	}
}
