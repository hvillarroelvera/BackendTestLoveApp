package com.hector.apptestlove.controler.dao.bean.remote;

import javax.ejb.Remote;

import com.hector.apptestlove.controler.dto.SessionDTO;

@Remote
public interface SessionServicebeanRemote {

	public int insertarSession(SessionDTO sessionDTO)throws Exception;
	public int modificarSession(SessionDTO sessionDTO)throws Exception;
	public int eliminarSession(SessionDTO sessionDTO)throws Exception;
	public SessionDTO getSessionByUsuario(String usuario)throws Exception;
}
