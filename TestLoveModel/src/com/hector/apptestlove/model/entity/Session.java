package com.hector.apptestlove.model.entity;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name=Session.FIND_ALL, query="SELECT s FROM Session s"),
	@NamedQuery(name=Session.FIND_NOMUSER , query="SELECT s "
			+ "FROM Session s WHERE s.usuario=:usuario")
})
public class Session implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_ALL = "Session.findAll";
	public static final String FIND_NOMUSER = "Session.findNomUser";

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="usuario")
	private String usuario;
	
	@Column(name="estado")
	private short estado;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="ultimaFechaLogin")
	private Calendar ultimaFechaLogin;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public short getEstado() {
		return estado;
	}
	public void setEstado(short estado) {
		this.estado = estado;
	}
	public Calendar getUltimaFechaLogin() {
		return ultimaFechaLogin;
	}
	public void setUltimaFechaLogin(Calendar ultimaFechaLogin) {
		this.ultimaFechaLogin = ultimaFechaLogin;
	}
	
	
	
}
