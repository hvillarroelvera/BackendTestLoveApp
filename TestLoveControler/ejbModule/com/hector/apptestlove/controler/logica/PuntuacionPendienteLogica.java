package com.hector.apptestlove.controler.logica;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.dao.bean.remote.PuntuacionPendienteServiceBeanRemote;
import com.hector.apptestlove.controler.dto.PuntuacionPendienteDTO;

@Stateless(name = "PuntuacionPendienteLogica", mappedName = "PuntuacionPendienteLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class PuntuacionPendienteLogica {

	@EJB
	private PuntuacionPendienteServiceBeanRemote puntuacionPendienteServiceBeanRemote;
	
	public int insertarPuntuacionPendiente(
			PuntuacionPendienteDTO puntuacionPendienteDTO) throws Exception {
		return puntuacionPendienteServiceBeanRemote.insertarPuntuacionPendiente(puntuacionPendienteDTO);
	}
	
	public int modificarPuntuacionPendiente(
			PuntuacionPendienteDTO puntuacionPendienteDTO) throws Exception {
		return puntuacionPendienteServiceBeanRemote.modificarPuntuacionPendiente(puntuacionPendienteDTO);
	}
	
	public int eliminarPuntuacionPendiente(
			PuntuacionPendienteDTO puntuacionPendienteDTO) throws Exception {
		return puntuacionPendienteServiceBeanRemote.eliminarPuntuacionPendiente(puntuacionPendienteDTO);
	}
	
	public PuntuacionPendienteDTO getPuntuacionPendienteById(long id)
			throws Exception {
		return puntuacionPendienteServiceBeanRemote.getPuntuacionPendienteById(id);
	}
	
	public int actualizarEstadoPuntuacionPendiente(long id, short estado)
			throws Exception {
		return puntuacionPendienteServiceBeanRemote.actualizarEstadoPuntuacionPendiente(id, estado);
	}
	
	public ArrayList<PuntuacionPendienteDTO> getPuntuacionPendienteDTOByEstado(
			String contacto,short estado) throws Exception {
		return puntuacionPendienteServiceBeanRemote.getPuntuacionPendienteDTOByContactoEstado(contacto,estado);
	}
}
