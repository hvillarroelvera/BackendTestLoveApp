package com.hector.apptestlove.rest;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.hector.apptestlove.controler.dao.bean.remote.UsuarioServiceBeanRemote;
import com.hector.apptestlove.controler.logica.FacadeLogicaServiceBean;


@Path("/hello")
public class HelloWorldService {
	
	@EJB 
	private FacadeLogicaServiceBean facadeLogicaServiceBean;


	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) throws Exception {
		System.out.println(facadeLogicaServiceBean.getUsuarioLogica().holaMundo());
		String output = "Jersey dice : "+msg;
		
		return Response.status(200).entity(output).build();
 
	}
 
}