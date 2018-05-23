package com.hector.apptestlove.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name=Puntuacionpendiente.FIND_ALL, query="SELECT p FROM Puntuacionpendiente p"),
	@NamedQuery(name=Puntuacionpendiente.FIND_BY_ID, query="SELECT p FROM Puntuacionpendiente p"
			+ " WHERE p.id = :id"),
	@NamedQuery(name=Puntuacionpendiente.FIND_BY_CONTACTO_ESTADO , query="SELECT p "
		    + "FROM Puntuacionpendiente p WHERE "
		    + "p.estado = :estado and "
		    + "p.contacto = :contacto"),
	@NamedQuery(name=Puntuacionpendiente.DELETE_BY_CONTACTO , query="DELETE "
			+ "FROM Puntuacionpendiente p WHERE p.contacto = :contacto")
})
public class Puntuacionpendiente implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Puntuacionpendiente.findAll";
	public static final String FIND_BY_ID = "Puntuacionpendiente.findById";
	public static final String FIND_BY_CONTACTO_ESTADO = "Puntuacionpendiente.findByContactoEstado";
	public static final String DELETE_BY_CONTACTO = "Puntuacionpendiente.deleteByContacto";

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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
