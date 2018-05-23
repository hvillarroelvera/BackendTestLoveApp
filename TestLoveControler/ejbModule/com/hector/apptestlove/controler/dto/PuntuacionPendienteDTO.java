package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class PuntuacionPendienteDTO implements Serializable{
	private long id;
	private String contacto;
	private int puntos;
	private short estado;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public short getEstado() {
		return estado;
	}
	public void setEstado(short estado) {
		this.estado = estado;
	}
}
