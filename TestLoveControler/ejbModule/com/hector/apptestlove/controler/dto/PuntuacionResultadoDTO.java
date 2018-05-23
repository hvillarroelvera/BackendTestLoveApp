package com.hector.apptestlove.controler.dto;

import java.io.Serializable;

public class PuntuacionResultadoDTO implements Serializable{

	private String nom_user;
	private String resultado;
	private int puntos;
	
	
	
	public int getPuntos() {
		return puntos;
	}
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}
	public String getNom_user() {
		return nom_user;
	}
	public void setNom_user(String nom_user) {
		this.nom_user = nom_user;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
	
}
