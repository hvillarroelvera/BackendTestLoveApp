package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class PreguntaPendienteDTO implements Serializable{

    private long id;
	private long numero;
	private String contacto;
	private short estado;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public short getEstado() {
		return estado;
	}
	public void setEstado(short estado) {
		this.estado = estado;
	}
	
	
}
