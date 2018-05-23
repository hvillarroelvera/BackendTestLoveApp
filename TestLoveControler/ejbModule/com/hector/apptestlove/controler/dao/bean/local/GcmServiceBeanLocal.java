package com.hector.apptestlove.controler.dao.bean.local;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

@Local
public interface GcmServiceBeanLocal {

	int registrarGcm(GcmDTO gcm,String nom_user) throws Exception;
	
	int existeGcmXUser(String codGcm,String nom_user)throws Exception;
	
	GcmDTO getGcmXUsuario(UsuarioDTO usuario) throws Exception;

	int actualizarGcm(GcmDTO gcm,String nom_user) throws Exception;

}
