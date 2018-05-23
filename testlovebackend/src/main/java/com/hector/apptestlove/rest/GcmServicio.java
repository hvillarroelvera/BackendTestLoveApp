package com.hector.apptestlove.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.RegistrarUsuarioDTO;
import com.hector.apptestlove.controler.dto.RespuestaDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;


@Path("/gcm")
public class GcmServicio {

	@EJB 
	private FacadeLogicaServiceBean facadeLogicaServiceBean;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/getGcmXUsuarioWithJson")
	public GcmDTO getGcmXUsuario(UsuarioDTO usuario) throws Exception{
		
		return facadeLogicaServiceBean.getGcmLogica().getGcmXUsuarioLogica(usuario);
		
	}
	
	
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	@Path("/actualizarGcmWithJson")
	public Response actualizarGcm(RegistrarUsuarioDTO gcmAndUserObject) throws Exception{
		int resulActualizacion = 0;
		GcmDTO gcmDto = new GcmDTO();
		gcmDto.setGcm_codGcm(gcmAndUserObject.getCodGcm());
		gcmDto.setExpirationTime(gcmAndUserObject.getExpirationTime());
		gcmDto.setAppVersion(gcmAndUserObject.getAppVersion());
		resulActualizacion = facadeLogicaServiceBean.getGcmLogica().actualizarGcm(gcmDto,gcmAndUserObject.getNom_user());
		
		if(resulActualizacion == Constantes.SUCCESS){
			return Response.ok().build();
		}else{
			return Response.serverError().header("Error", new String("Error inesperado al actualizar gcm")).build();
		}
		
	}
}
