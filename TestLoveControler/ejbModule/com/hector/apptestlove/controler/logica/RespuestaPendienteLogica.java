package com.hector.apptestlove.controler.logica;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.dao.bean.remote.RespuestaPendienteServiceBeanRemote;
import com.hector.apptestlove.controler.dto.RespuestaPendienteDTO;


@Stateless(name = "RespuestaPendienteLogica",mappedName = "RespuestaPendienteLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class RespuestaPendienteLogica {

	@EJB
	private RespuestaPendienteServiceBeanRemote respuestaPendienteServiceBeanRemote;
	
    public RespuestaPendienteLogica() {
        // TODO Auto-generated constructor stub
    }

    public int insertarRespuestaPendiente(
			RespuestaPendienteDTO respuestaPendienteDTO) throws Exception {
    	return respuestaPendienteServiceBeanRemote.insertarRespuestaPendiente(respuestaPendienteDTO);
    }
    
    public int modificarRespuestaPendiente(
			RespuestaPendienteDTO respuestaPendienteDTO) throws Exception {
    	return respuestaPendienteServiceBeanRemote.modificarRespuestaPendiente(respuestaPendienteDTO);
    }
    
    public int eliminarRespuestaPendiente(
			RespuestaPendienteDTO respuestaPendienteDTO) throws Exception {
    	return respuestaPendienteServiceBeanRemote.eliminarRespuestaPendiente(respuestaPendienteDTO);
    }
    
    public RespuestaPendienteDTO getRespuestaPendienteById(long id)
			throws Exception {
    	return respuestaPendienteServiceBeanRemote.getRespuestaPendienteById(id);
    }
    
    public ArrayList<RespuestaPendienteDTO> getRespuestaPendienteByEstado(String contacto,short estado)
			throws Exception {
    	return respuestaPendienteServiceBeanRemote.getRespuestaPendienteByContactoEstado(contacto,estado);
    }
    
    public int actualizarEstadoRespuestaPendiente(long id, short estado)
			throws Exception {
    	return respuestaPendienteServiceBeanRemote.actualizarEstadoRespuestaPendiente(id, estado);
    }
}
