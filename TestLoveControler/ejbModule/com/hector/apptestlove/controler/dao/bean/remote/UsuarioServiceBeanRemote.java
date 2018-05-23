package com.hector.apptestlove.controler.dao.bean.remote;

import javax.ejb.Remote;

import com.hector.apptestlove.controler.dto.UsuarioDTO;

@Remote
public interface UsuarioServiceBeanRemote {

	String holaMundo();
	
	int registrarUsuario(UsuarioDTO usuario) throws Exception;
	
int buscarUsuario(String nomUsuario, String password) throws Exception;
	
	int buscarUsuario(String nomUsuario) throws Exception;

}
