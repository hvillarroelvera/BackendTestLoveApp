package com.hector.apptestlove.controler.dao.bean.remote;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.hector.apptestlove.controler.dto.EstadoGenericoDTO;

@Remote
public interface EstadoSessionServiceBeanRemote {

   ArrayList<EstadoGenericoDTO> getListEstadoSession()throws Exception;
	   
}
