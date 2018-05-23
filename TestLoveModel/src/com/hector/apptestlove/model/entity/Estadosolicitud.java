package com.hector.apptestlove.model.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the estadosolicitud database table.
 * 
 */
@Entity
@NamedQuery(name=Estadosolicitud.FIND_ALL, query="SELECT e FROM Estadosolicitud e")
public class Estadosolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Estadosolicitud.findAll";
	
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