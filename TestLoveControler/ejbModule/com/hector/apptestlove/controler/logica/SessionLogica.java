package com.hector.apptestlove.controler.logica;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.remote.SessionServicebeanRemote;
import com.hector.apptestlove.controler.dto.SessionDTO;
import com.hector.apptestlove.controler.util.Util;

@Stateless(name = "SessionLogica", mappedName = "SessionLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class SessionLogica {

	@EJB
	private SessionServicebeanRemote sessionServiceBean;
	
	public int insertarSession(SessionDTO sessionDTO) throws Exception {
		return sessionServiceBean.insertarSession(sessionDTO);
	}
	
	public int modificarSession(SessionDTO sessionDTO) throws Exception {
		return sessionServiceBean.modificarSession(sessionDTO);
	}
	
	public int eliminarSession(SessionDTO sessionDTO) throws Exception {
		return sessionServiceBean.eliminarSession(sessionDTO);
	}
	
	public SessionDTO getSessionByUsuario(String usuario) throws Exception {
		return sessionServiceBean.getSessionByUsuario(usuario);
	}
	
	public int modificarSessionToEstadoFinalized(String usuario) throws Exception {
		SessionDTO sessionDTO = new SessionDTO();
		sessionDTO = sessionServiceBean.getSessionByUsuario(usuario);
		sessionDTO.setEstado(Constantes.SESSION_FINALIZED);
		return sessionServiceBean.modificarSession(sessionDTO);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int modificarSessionToEstadoStarted(String usuario) throws Exception {
		SessionDTO sessionDTO = new SessionDTO();
		sessionDTO = sessionServiceBean.getSessionByUsuario(usuario);
		sessionDTO.setEstado(Constantes.SESSION_STARTED);
		/*INI Setea fecha*/
		sessionDTO.setUltimaFechaLogin(Util.getFechaHora());
		/*FIN Setea fecha*/
		return sessionServiceBean.modificarSession(sessionDTO);
	}
	
	
}
