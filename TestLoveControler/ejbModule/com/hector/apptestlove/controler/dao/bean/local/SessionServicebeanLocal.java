package com.hector.apptestlove.controler.dao.bean.local;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.SessionDTO;

@Local
public interface SessionServicebeanLocal {

	public int insertarSession(SessionDTO sessionDTO)throws Exception;
	public int modificarSession(SessionDTO sessionDTO)throws Exception;
	public int eliminarSession(SessionDTO sessionDTO)throws Exception;
	public SessionDTO getSessionByUsuario(String usuario)throws Exception;
}
