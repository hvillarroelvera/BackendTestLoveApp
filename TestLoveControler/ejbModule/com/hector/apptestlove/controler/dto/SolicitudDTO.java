package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class SolicitudDTO implements Serializable{

	private long numero;
	private String userEmisor;
	private String userReceptor;
	private short estado;
	private short tipo;
	
	public long getNumero() {
		return numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getUserEmisor() {
		return userEmisor;
	}
	public void setUserEmisor(String userEmisor) {
		this.userEmisor = userEmisor;
	}
	public String getUserReceptor() {
		return userReceptor;
	}
	public void setUserReceptor(String userReceptor) {
		this.userReceptor = userReceptor;
	}
	public short getEstado() {
		return estado;
	}
	public void setEstado(short estado) {
		this.estado = estado;
	}
	public short getTipo() {
		return tipo;
	}
	public void setTipo(short tipo) {
		this.tipo = tipo;
	}
	
	
}
