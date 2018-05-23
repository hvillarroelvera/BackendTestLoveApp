package com.hector.apptestlove.controler.dao.bean.local;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.UsuarioDTO;


@Local
public interface UsuarioServiceBeanLocal {

	String holaMundo();
	
	int registrarUsuario(UsuarioDTO usuario) throws Exception;
	
	int buscarUsuario(String nomUsuario, String password) throws Exception;
	
	int buscarUsuario(String nomUsuario) throws Exception;
}
