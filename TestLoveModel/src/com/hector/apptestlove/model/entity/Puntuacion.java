package com.hector.apptestlove.model.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the puntuacion database table.
 * 
 */
@Entity
@NamedQuery(name="Puntuacion.findAll", query="SELECT p FROM Puntuacion p")
public class Puntuacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id_contacto")
	private long idContacto;

	private int puntos;

	public Puntuacion() {
	}

	public long getIdContacto() {
		return this.idContacto;
	}

	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}

	public int getPuntos() {
		return this.puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

}