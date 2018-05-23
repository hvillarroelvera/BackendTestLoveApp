package com.hector.apptestlove.controler.dao.bean.remote;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.hector.apptestlove.controler.dto.PuntuacionPendienteDTO;


@Remote
public interface PuntuacionPendienteServiceBeanRemote {

	public int insertarPuntuacionPendiente(PuntuacionPendienteDTO puntuacionPendienteDTO)throws Exception;
	public int modificarPuntuacionPendiente(PuntuacionPendienteDTO puntuacionPendienteDTO)throws Exception;
	public int eliminarPuntuacionPendiente(PuntuacionPendienteDTO puntuacionPendienteDTO)throws Exception;
	public PuntuacionPendienteDTO getPuntuacionPendienteById(long id)throws Exception;
	int actualizarEstadoPuntuacionPendiente(long id, short estado)throws Exception;
	ArrayList<PuntuacionPendienteDTO> getPuntuacionPendienteDTOByContactoEstado(String contacto,short estado) throws Exception;
}