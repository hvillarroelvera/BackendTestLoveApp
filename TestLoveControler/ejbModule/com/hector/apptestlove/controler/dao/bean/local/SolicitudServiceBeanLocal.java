package com.hector.apptestlove.controler.dao.bean.local;

import java.util.ArrayList;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.SolicitudDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

@Local
public interface SolicitudServiceBeanLocal {
	
	int insertarSolicitudContacto (UsuarioDTO usuario,UsuarioDTO usuarioContacto,short estado,short tipo) throws Exception;

	int eliminarSolicitudContactoPendiente(UsuarioDTO usuario,UsuarioDTO usuarioContacto) throws Exception;

	boolean existeSolicitudContacto(UsuarioDTO usuario,ContactoDTO contacto) throws Exception;
	
	int actualizarSolicitud(long numero,short estado) throws Exception;
	
	SolicitudDTO getSolicitudContacto(UsuarioDTO usuario) throws Exception;
	
	ArrayList<SolicitudDTO> getAllSolicitudesByReceptorEstadoTipo(SolicitudDTO solicitudDTO)throws Exception;

	ArrayList<SolicitudDTO> getAllSolicitudesByEmisorEstadoTipo(SolicitudDTO solicitudDTO) throws Exception;

}
