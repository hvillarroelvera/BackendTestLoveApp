package com.hector.apptestlove.controler.dao.bean.local;

import java.util.ArrayList;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntaPendienteDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;

@Local
public interface PreguntaPendienteServiceBeanLocal {
	
	int insertarPreguntaPendiente (UsuarioDTO usuario,PreguntaDTO pregunta,short estado) throws Exception;

	PreguntasDTO getNumerosFromPPXContacto(ContactoDTO contacto) throws Exception;

	int eliminarPreguntaPendiente(PreguntaPendienteDTO preguntaPendienteDTO) throws Exception;
	
	int actualizarEstadoPreguntaPendiente(long numero,short estado) throws Exception;

	ArrayList<PreguntaPendienteDTO> getPreguntaPendienteByContactoEstado(String contacto,short estado) throws Exception;
	
	PreguntaPendienteDTO getPreguntaPendienteByNumeroEstado(long numero,short estado)throws Exception;

}
