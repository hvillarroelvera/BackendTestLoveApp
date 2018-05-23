package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class RespuestaPreguntasDTO extends PreguntasDTO implements Serializable{

	private String respuesta;

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
	
	
}
