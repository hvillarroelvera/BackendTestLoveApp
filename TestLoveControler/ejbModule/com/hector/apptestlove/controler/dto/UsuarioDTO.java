package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class UsuarioDTO implements Serializable {

	private String nom_user;
	private String password;
	private String codGcm;
	public String getNom_user() {
		return nom_user;
	}
	public void setNom_user(String nom_user) {
		this.nom_user = nom_user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCodGcm() {
		return codGcm;
	}
	public void setCodGcm(String codGcm) {
		this.codGcm = codGcm;
	}
	
	
	
}
