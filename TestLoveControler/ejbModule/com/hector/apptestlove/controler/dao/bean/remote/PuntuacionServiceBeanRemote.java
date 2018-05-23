package com.hector.apptestlove.controler.dao.bean.remote;

import javax.ejb.Remote;

import com.hector.apptestlove.controler.dto.PuntuacionDTO;
import com.hector.apptestlove.controler.dto.PuntuacionResultadoDTO;

@Remote
public interface PuntuacionServiceBeanRemote {
	
	int actualizarPuntuacion (long id_contacto,PuntuacionResultadoDTO puntuacion) throws Exception;

	int existePuntuacion(long id_contacto) throws Exception;
	
	int insertarPuntuacion(long id_contacto,PuntuacionResultadoDTO puntuacion) throws Exception;
	
	int getPuntos(long id_contacto) throws Exception;
	
	int eliminarPuntuacion(PuntuacionDTO puntuacionDTO) throws Exception;
}
