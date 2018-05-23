package com.hector.apptestlove.controler.dao.bean.remote;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

@Remote
public interface PreguntaServiceBeanRemote {
	
	int getCountPreguntasXUsuario(UsuarioDTO usuario) throws Exception;
	
	long insertarPregunta (UsuarioDTO usuario,PreguntaDTO pregunta) throws Exception;
	
	PreguntasDTO getPreguntasXUsuario(UsuarioDTO usuario) throws Exception;
	
	PreguntasDTO getPreguntasXUsuarioXNumero(UsuarioDTO usuario,PreguntasDTO preguntasPendientes) throws Exception;

	int actualizarPregunta(PreguntaDTO pregunta) throws Exception;

	int eliminarPregunta(PreguntaDTO pregunta) throws Exception;
	
	PreguntaDTO getPreguntaByNumero(long numero) throws Exception;
	
	}
