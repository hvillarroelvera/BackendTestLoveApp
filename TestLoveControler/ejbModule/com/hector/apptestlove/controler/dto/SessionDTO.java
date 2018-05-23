package com.hector.apptestlove.controler.dto;

import java.io.Serializable;
import java.util.Calendar;

public class SessionDTO implements Serializable {
    private long id;
	private String usuario;
	private short estado;
	private String ultimaFechaLogin;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public short getEstado() {
		return estado;
	}
	public void setEstado(short estado) {
		this.estado = estado;
	}
	public String getUltimaFechaLogin() {
		return ultimaFechaLogin;
	}
	public void setUltimaFechaLogin(String ultimaFechaLogin) {
		this.ultimaFechaLogin = ultimaFechaLogin;
	}
	
	
}
