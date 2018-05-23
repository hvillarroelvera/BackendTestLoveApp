package com.hector.apptestlove.controler.dao.bean.local;

import java.util.ArrayList;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.PuntuacionPendienteDTO;

@Local
public interface PuntuacionPendienteServiceBeanLocal {

	public int insertarPuntuacionPendiente(PuntuacionPendienteDTO puntuacionPendienteDTO)throws Exception;
	public int modificarPuntuacionPendiente(PuntuacionPendienteDTO puntuacionPendienteDTO)throws Exception;
	public int eliminarPuntuacionPendiente(PuntuacionPendienteDTO puntuacionPendienteDTO)throws Exception;
	public PuntuacionPendienteDTO getPuntuacionPendienteById(long id)throws Exception;
	int actualizarEstadoPuntuacionPendiente(long id, short estado)throws Exception;
	ArrayList<PuntuacionPendienteDTO> getPuntuacionPendienteDTOByContactoEstado(String contacto,short estado) throws Exception;
}
