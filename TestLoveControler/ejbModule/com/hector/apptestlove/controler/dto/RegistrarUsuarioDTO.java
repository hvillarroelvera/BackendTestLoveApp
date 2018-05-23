package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class RegistrarUsuarioDTO implements Serializable{

	private String nom_user;
	private String password;
	private String codGcm;
	private long expirationTime;
	private int appVersion;
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
	public long getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(long expirationTime) {
		this.expirationTime = expirationTime;
	}
	public int getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(int appVersion) {
		this.appVersion = appVersion;
	}
	
	
	
}
