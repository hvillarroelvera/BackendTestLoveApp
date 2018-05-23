package com.hector.apptestlove.controler.dao.bean.remote;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.hector.apptestlove.controler.dto.RespuestaPendienteDTO;


@Remote
public interface RespuestaPendienteServiceBeanRemote {

	public int insertarRespuestaPendiente(RespuestaPendienteDTO respuestaPreguntaDTO)throws Exception;
	public int modificarRespuestaPendiente(RespuestaPendienteDTO respuestaPreguntaDTO)throws Exception;
	public int eliminarRespuestaPendiente(RespuestaPendienteDTO respuestaPreguntaDTO)throws Exception;
	public RespuestaPendienteDTO getRespuestaPendienteById(long id)throws Exception;
	int actualizarEstadoRespuestaPendiente(long id, short estado)throws Exception;
	ArrayList<RespuestaPendienteDTO> getRespuestaPendienteByContactoEstado(String contacto,short estado)throws Exception;
}
