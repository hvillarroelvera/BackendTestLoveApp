package com.hector.apptestlove.model.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the estadosesion database table.
 * 
 */
@Entity
@NamedQuery(name=EstadoSession.FIND_ALL, query="SELECT e FROM EstadoSession e")
public class EstadoSession implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "EstadoSession.findAll";

	
	@Id
	@Column(name="id")
	private int id;
	@Column(name="estadoDescripcion")
	private String estadoDescripcion;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEstadoDescripcion() {
		return estadoDescripcion;
	}
	public void setEstadoDescripcion(String estadoDescripcion) {
		this.estadoDescripcion = estadoDescripcion;
	}

	

}