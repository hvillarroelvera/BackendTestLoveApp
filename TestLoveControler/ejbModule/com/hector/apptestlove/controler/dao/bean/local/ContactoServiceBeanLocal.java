package com.hector.apptestlove.controler.dao.bean.local;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

@Local
public interface ContactoServiceBeanLocal {
	
	ContactoDTO getContactoXUsuario(UsuarioDTO usuario) throws Exception;

    int registrarContacto (UsuarioDTO usuario,ContactoDTO contacto) throws Exception;
	
	int actualizarContacto(ContactoDTO contacto) throws Exception;
	
	ContactoDTO getIdContactoXUsuario(UsuarioDTO usuario,ContactoDTO usuarioContacto) throws Exception;
	
	ContactoDTO getUsuarioXContacto(UsuarioDTO usuarioContacto) throws Exception;
	
	int eliminarContacto(ContactoDTO contactoDTO) throws Exception;
}
