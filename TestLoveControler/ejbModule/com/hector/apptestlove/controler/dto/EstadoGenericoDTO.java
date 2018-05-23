package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

import javax.persistence.Column;

public class EstadoGenericoDTO implements Serializable {

	private int id;
    private short estadoCodigo;
	private String estadoDescripcion;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public short getEstadoCodigo() {
		return estadoCodigo;
	}
	public void setEstadoCodigo(short estadoCodigo) {
		this.estadoCodigo = estadoCodigo;
	}
	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}
	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}
	
	
}
