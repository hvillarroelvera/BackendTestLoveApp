package com.hector.apptestlove.model.entity;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@NamedQueries({
	@NamedQuery(name=Preguntapendiente.FIND_ALL, query="SELECT p FROM Preguntapendiente p"),
	@NamedQuery(name=Preguntapendiente.FIND_NUMEROS_BY_CONTACTO , query="SELECT p "
			+ "FROM Preguntapendiente p WHERE p.contacto = :contacto"),
	@NamedQuery(name=Preguntapendiente.FIND_PREGUNTA_PENDIENTE_BY_ID , query="SELECT p "
		    + "FROM Preguntapendiente p WHERE p.id = :id"),
	@NamedQuery(name=Preguntapendiente.FIND_BY_CONTACTO_ESTADO , query="SELECT p "
		    + "FROM Preguntapendiente p WHERE "
		    + "p.estado = :estado and "
		    + "p.contacto = :contacto"),
	@NamedQuery(name=Preguntapendiente.FIND_BY_NUMERO_ESTADO , query="SELECT p "
		    + "FROM Preguntapendiente p WHERE "
		    + "p.estado = :estado and "
		    + "p.numero = :numero"),
    @NamedQuery(name=Preguntapendiente.DELETE_BY_CONTACTO , query="DELETE "
			+ "FROM Preguntapendiente p WHERE p.contacto = :contacto")
})

public class Preguntapendiente implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Preguntapendiente.findAll";
	public static final String FIND_NUMEROS_BY_CONTACTO = "Preguntapendiente.findNumerosByContacto";
	public static final String FIND_PREGUNTA_PENDIENTE_BY_ID = "Preguntapendiente.findPreguntaPendienteById";
	public static final String FIND_BY_CONTACTO_ESTADO = "Preguntapendiente.findByContactoEstado";
	public static final String FIND_BY_NUMERO_ESTADO = "Preguntapendiente.findByNumeroEstado";
	public static final String DELETE_BY_CONTACTO = "Preguntapendiente.deleteByContacto";

	
	@Id
	private long id;
	
	private long numero;

	private String contacto;
	
	private short estado;


	public Preguntapendiente() {
	}

	
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public String getContacto() {
		return this.contacto;
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