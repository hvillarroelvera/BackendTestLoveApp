package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class ContactoDTO implements Serializable{

	private long id_contacto;
	private String contacto;
	private String nomUser;

	
	public String getNomUser() {
		return nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public long getId_contacto() {
		return id_contacto;
	}

	public void setId_contacto(long id_contacto) {
		this.id_contacto = id_contacto;
	}
	
	
}
