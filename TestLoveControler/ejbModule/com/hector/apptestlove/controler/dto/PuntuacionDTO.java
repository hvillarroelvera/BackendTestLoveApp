package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class PuntuacionDTO implements Serializable{

	private long idContacto;
	private int puntos;
	
	public long getIdContacto() {
		return idContacto;
	}
	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	
	
}
