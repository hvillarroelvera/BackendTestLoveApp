package com.hector.apptestlove.controler.dao.bean.local;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.PuntuacionDTO;
import com.hector.apptestlove.controler.dto.PuntuacionResultadoDTO;

@Local
public interface PuntuacionServiceBeanLocal {
	
	int actualizarPuntuacion (long id_contacto,PuntuacionResultadoDTO puntuacion) throws Exception;

	int existePuntuacion(long id_contacto) throws Exception;

	int insertarPuntuacion(long id_contacto,PuntuacionResultadoDTO puntuacion) throws Exception;

	int getPuntos(long id_contacto) throws Exception;
	
	int eliminarPuntuacion(PuntuacionDTO puntuacionDTO) throws Exception;

}
