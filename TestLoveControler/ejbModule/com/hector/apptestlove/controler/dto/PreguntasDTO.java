package com.hector.apptestlove.controler.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class PreguntasDTO implements Serializable{

	private String nom_user;
	private ArrayList<PreguntaDTO>preguntas;
	private int cantidadPreguntas;
	
	
	
	
	public String getNom_user() {
		return nom_user;
	}
	public void setNom_user(String nom_user) {
		this.nom_user = nom_user;
	}
	public ArrayList<PreguntaDTO> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(ArrayList<PreguntaDTO> preguntas) {
		this.preguntas = preguntas;
	}
	public int getCantidadPreguntas() {
		return cantidadPreguntas;
	}
	public void setCantidadPreguntas(int cantidadPreguntas) {
		this.cantidadPreguntas = cantidadPreguntas;
	}
	public void cleanPreguntas(){
		this.preguntas.clear();
	}
	
}
