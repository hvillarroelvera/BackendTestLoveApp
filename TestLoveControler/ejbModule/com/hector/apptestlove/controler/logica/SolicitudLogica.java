package com.hector.apptestlove.controler.logica;


import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.remote.SolicitudServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.SolicitudDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.util.Util;

@Stateless(name = "SolicitudLogica", mappedName = "SolicitudLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class SolicitudLogica {
	
	@EJB
	private SolicitudServiceBeanRemote solicitudServiceBeanRemote;
	
	@EJB
	private GcmLogica gcmLogica;
	

	public int insertarSolicitudContacto (UsuarioDTO usuario,UsuarioDTO usuarioContacto,short estado,short tipo) throws Exception{
		System.out.println("INI Logica insertarSolicitudContacto");
		return solicitudServiceBeanRemote.insertarSolicitudContacto(usuario, usuarioContacto,estado, tipo);
	}
	
	public int eliminarSolicitudContactoPendiente(UsuarioDTO usuario,UsuarioDTO usuarioContacto) throws Exception{
		System.out.println("INI Logica eliminarSolicitudContactoPendiente");
		return solicitudServiceBeanRemote.eliminarSolicitudContactoPendiente(usuario, usuarioContacto);
	}
	public int actualizarSolicitud(long numero,short estado) throws Exception{
		System.out.println("INI Logica actualizarSolicitud");
		return solicitudServiceBeanRemote.actualizarSolicitud(numero, estado);
	}
	
	public boolean existeSolicitudContacto(UsuarioDTO usuario,ContactoDTO contacto) throws Exception{
		System.out.println("INI Logica existeSolicitudContacto");
		return solicitudServiceBeanRemote.existeSolicitudContacto(usuario, contacto);
	}
	
	public SolicitudDTO getSolicitudContacto(UsuarioDTO usuario) throws Exception{
		System.out.println("INI Logica getSolicitudContacto");
		return solicitudServiceBeanRemote.getSolicitudContacto(usuario);
	}
	
	public ArrayList<SolicitudDTO> getAllSolicitudesByReceptorEstadoTipo(SolicitudDTO solicitudDTO) throws Exception{
		return solicitudServiceBeanRemote.getAllSolicitudesByReceptorEstadoTipo(solicitudDTO);
	}
	
	public ArrayList<SolicitudDTO> getAllSolicitudesByEmisorEstadoTipo(SolicitudDTO solicitudDTO) throws Exception{
		return solicitudServiceBeanRemote.getAllSolicitudesByEmisorEstadoTipo(solicitudDTO);
	}
	
}
