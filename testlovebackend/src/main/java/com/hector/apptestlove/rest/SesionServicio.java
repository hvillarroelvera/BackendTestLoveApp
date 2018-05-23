package com.hector.apptestlove.rest;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.hector.apptestlove.controler.dto.SessionDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;

@Path("/sesion")
public class SesionServicio {

	@EJB 
	private FacadeLogicaServiceBean facadeLogicaServiceBean;
	
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/getSesionByUsuarioWithJson")
	public SessionDTO getSesionByUsuarioWithJson(UsuarioDTO u) throws Exception{
		
		SessionDTO sesionDTO = new SessionDTO();
		sesionDTO = facadeLogicaServiceBean.getSessionLogica().getSessionByUsuario(u.getNom_user());
		if(sesionDTO != null){
			Response.ok().build();
			return sesionDTO;
		}else{
			Response.serverError().header("Error", new String("No existe sesion para el usuario")).build();
		    return null;
		}
		
	}
}
