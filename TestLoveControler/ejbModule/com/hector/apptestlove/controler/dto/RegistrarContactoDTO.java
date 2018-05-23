package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class RegistrarContactoDTO implements Serializable{

	private String nom_user;
	private String contacto;
	private short respuesta;
	
	
	
	public short getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(short respuesta) {
		this.respuesta = respuesta;
	}
	public String getNom_user() {
		return nom_user;
	}
	public void setNom_user(String nom_user) {
		this.nom_user = nom_user;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	
	
	
	
}
