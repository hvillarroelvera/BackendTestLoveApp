package com.hector.apptestlove.controler.logica;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.dao.bean.remote.EstadoSessionServiceBeanRemote;
import com.hector.apptestlove.controler.dto.EstadoGenericoDTO;

@Stateless(name = "EstadoSessionLogica", mappedName = "EstadoSessionLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class EstadoSessionLogica {

	@EJB
	private EstadoSessionServiceBeanRemote estadoSessionServiceBeanRemote;
	
	public ArrayList<EstadoGenericoDTO> getListEstadoSesion()throws Exception{
		return estadoSessionServiceBeanRemote.getListEstadoSession();
	}
}
