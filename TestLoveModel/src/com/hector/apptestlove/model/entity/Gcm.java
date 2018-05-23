package com.hector.apptestlove.model.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gcm database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Gcm.FIND_ALL, query="SELECT g FROM Gcm g"),
	@NamedQuery(name=Gcm.COUNTXUSER, query="SELECT COUNT(g.nomUser) "
			+ "FROM Usuario u,Gcm g WHERE g.nomUser=:nomUsuario AND u.nomUser=g.nomUser")
})
public class Gcm implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Gcm.findAll";
	public static final String COUNTXUSER = "Gcm.countXUser";

	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="nom_user")
	private String nomUser;

	private int appversion;

	private String codGcm;

	private long expirationtime;


	public Gcm() {
	}

	public String getNomUser() {
		return this.nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

	public int getAppversion() {
		return this.appversion;
	}

	public void setAppversion(int appversion) {
		this.appversion = appversion;
	}

	public String getCodGcm() {
		return this.codGcm;
	}

	public void setCodGcm(String codGcm) {
		this.codGcm = codGcm;
	}

	public long getExpirationtime() {
		return this.expirationtime;
	}

	public void setExpirationtime(long expirationtime) {
		this.expirationtime = expirationtime;
	}

}