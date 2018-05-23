package com.hector.apptestlove.controler.dao.bean.local;

import java.util.ArrayList;

import javax.ejb.Local;

import com.hector.apptestlove.controler.dto.EstadoGenericoDTO;

@Local
public interface EstadoSolicitudServiceBeanLocal {

	ArrayList<EstadoGenericoDTO> getListEstadoSolicitud()throws Exception;
}
