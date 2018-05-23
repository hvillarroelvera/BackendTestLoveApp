package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class PreguntaDTO implements Serializable{

	private String pregunta;
	private long numero;

	
	
	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}
	
	
}
