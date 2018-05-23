package com.hector.apptestlove.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.PuntuacionDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;

@Path("/puntuacion")
public class PuntuacionServicio {

	@EJB 
	private FacadeLogicaServiceBean facadeLogicaServiceBean;
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/eliminarPuntuacionJson")
	public Response eliminarPuntuacionJson(PuntuacionDTO puntuacionDTO) throws Exception{
		int resp = 0;
		resp = facadeLogicaServiceBean.getpuntuacionLogica().eliminarPuntuacion(puntuacionDTO);
		if(resp == Constantes.SUCCESS || resp == Constantes.NOT_EXIST){
			return Response.ok().build();
		}else{
			return Response.serverError().header("Error", new String("Ha ocurrido un problema al eliminar puntuacion")).build();

		}
	}
}
