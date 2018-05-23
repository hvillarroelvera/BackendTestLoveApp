package com.hector.apptestlove.model.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the contacto database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Contacto.FIND_ALL , query="SELECT c FROM Contacto c"),
	@NamedQuery(name=Contacto.FIND_BY_CONTACTO , query="SELECT c "
			+ "FROM Contacto c WHERE c.contacto=:contacto"),
	@NamedQuery(name=Contacto.FIND_BY_NOMUSER , query="SELECT c "
			+ "FROM Contacto c WHERE c.nomUser=:nomUser"),
	@NamedQuery(name=Contacto.FIND_BY_NOMUSER_AND_CONTACTO , query="SELECT c.idContacto "
			+ "FROM Contacto c WHERE c.nomUser=:nomUser AND c.contacto = :contacto")
})
public class Contacto implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Contacto.findAll";
	public static final String FIND_BY_CONTACTO = "Contacto.findByContacto";
	public static final String FIND_BY_NOMUSER = "Contacto.findByNomUser";
	public static final String FIND_BY_NOMUSER_AND_CONTACTO = "Contacto.findByNomUserAndContacto";


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_contacto")
	private long idContacto;

	private String contacto;

	@Column(name="nom_user")
	private String nomUser;

	public Contacto() {
	}

	public long getIdContacto() {
		return this.idContacto;
	}

	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}

	public String getContacto() {
		return this.contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getNomUser() {
		return this.nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

}