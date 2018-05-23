package com.hector.apptestlove.model.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the solicitud database table.
 * 
 */
@Embeddable
public class SolicitudPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long numero;

	private String userEmisor;

	private String userReceptor;

	public SolicitudPK() {
	}
	public long getNumero() {
		return this.numero;
	}
	public void setNumero(long numero) {
		this.numero = numero;
	}
	public String getUserEmisor() {
		return this.userEmisor;
	}
	public void setUserEmisor(String userEmisor) {
		this.userEmisor = userEmisor;
	}
	public String getUserReceptor() {
		return this.userReceptor;
	}
	public void setUserReceptor(String userReceptor) {
		this.userReceptor = userReceptor;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof SolicitudPK)) {
			return false;
		}
		SolicitudPK castOther = (SolicitudPK)other;
		return 
			this.numero==castOther.numero
			&& this.userEmisor.equals(castOther.userEmisor)
			&& this.userReceptor.equals(castOther.userReceptor);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		//hash = hash * prime + this.numero;
		hash = hash * prime + this.userEmisor.hashCode();
		hash = hash * prime + this.userReceptor.hashCode();
		
		return hash;
	}
}