package com.hector.apptestlove.controler.dao.bean.local;

import java.util.ArrayList;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.EstadoGenericoDTO;

@Local
public interface EstadoSessionServiceBeanLocal {

	   ArrayList<EstadoGenericoDTO> getListEstadoSession()throws Exception;

}
