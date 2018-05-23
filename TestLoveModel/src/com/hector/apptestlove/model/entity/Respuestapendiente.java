package com.hector.apptestlove.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
	@NamedQuery(name=Respuestapendiente.FIND_ALL, query="SELECT p FROM Respuestapendiente p"),
	@NamedQuery(name=Respuestapendiente.FIND_BY_ID, query="SELECT p FROM Respuestapendiente p"
			+ " WHERE p.id = :id"),
	@NamedQuery(name=Respuestapendiente.FIND_BY_CONTACTO_ESTADO , query="SELECT p "
		    + "FROM Respuestapendiente p WHERE "
		    + "p.estado = :estado and "
		    + "p.contacto = :contacto"),
    @NamedQuery(name=Respuestapendiente.FIND_RESPUESTA_PREGUNTA_BY_ID , query="SELECT p "
		    + "FROM Respuestapendiente p WHERE p.id = :id"),
    @NamedQuery(name=Respuestapendiente.DELETE_BY_CONTACTO , query="DELETE "
			+ "FROM Respuestapendiente p WHERE p.contacto = :contacto")
})
public class Respuestapendiente implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Respuestapendiente.findAll";
	public static final String FIND_BY_ID = "Respuestapendiente.findById";
	public static final String FIND_BY_CONTACTO_ESTADO = "Respuestapendiente.findByContactoEstado";
	public static final String FIND_RESPUESTA_PREGUNTA_BY_ID = "Respuestapendiente.findRespuestaPreguntaById";
	public static final String DELETE_BY_CONTACTO = "Respuestapendiente.deleteByContacto";


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String respuesta;
	
	private long numero;
	
	private short estado;
	
	private String contacto;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public short getEstado() {
		return estado;
	}
	public void setEstado(short estado) {
		this.estado = estado;
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
	
}
