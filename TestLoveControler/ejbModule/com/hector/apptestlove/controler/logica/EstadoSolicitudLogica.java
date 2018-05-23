package com.hector.apptestlove.controler.logica;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.dao.bean.remote.EstadoSolicitudServiceBeanRemote;
import com.hector.apptestlove.controler.dto.EstadoGenericoDTO;

@Stateless(name = "EstadoSolicitudLogica", mappedName = "EstadoSolicitudLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class EstadoSolicitudLogica {

	@EJB
	private EstadoSolicitudServiceBeanRemote estadoSolicitudServiceBeanRemote;
	
	public ArrayList<EstadoGenericoDTO> getListEstadoSolicitud()throws Exception{
		return estadoSolicitudServiceBeanRemote.getListEstadoSolicitud();
	}
}
