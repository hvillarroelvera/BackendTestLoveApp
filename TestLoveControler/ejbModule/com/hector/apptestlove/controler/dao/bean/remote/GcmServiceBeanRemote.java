package com.hector.apptestlove.controler.dao.bean.remote;

import javax.ejb.Remote;

import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

@Remote
public interface GcmServiceBeanRemote {

	int registrarGcm(GcmDTO gcm,String nom_user) throws Exception;
	
	int existeGcmXUser(String codGcm,String nom_user)throws Exception;
	
	GcmDTO getGcmXUsuario(UsuarioDTO usuario) throws Exception;
	
	int actualizarGcm(GcmDTO gcm,String nom_user) throws Exception;
}
