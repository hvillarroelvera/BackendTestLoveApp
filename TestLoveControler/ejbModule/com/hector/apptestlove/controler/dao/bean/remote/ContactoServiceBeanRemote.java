package com.hector.apptestlove.controler.dao.bean.remote;

import javax.ejb.Remote;

import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

@Remote
public interface ContactoServiceBeanRemote {

	ContactoDTO getContactoXUsuario(UsuarioDTO usuario) throws Exception;
	
	int registrarContacto (UsuarioDTO usuario,ContactoDTO contacto) throws Exception;
	
	int actualizarContacto(ContactoDTO contacto) throws Exception;
	
	ContactoDTO getIdContactoXUsuario(UsuarioDTO usuario,ContactoDTO usuarioContacto) throws Exception;
	
	ContactoDTO getUsuarioXContacto(UsuarioDTO usuarioContacto) throws Exception;
	
	int eliminarContacto(ContactoDTO contactoDTO) throws Exception;
}
