package com.hector.apptestlove.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuarios database table.
 * 
 */
@Entity
@Table(name="usuarios")
@NamedQueries({
	@NamedQuery(name=Usuario.FIND_ALL , query="SELECT u FROM Usuario u"),
	@NamedQuery(name=Usuario.FIND_USUARIO , query="SELECT u.nomUser,u.password "
			+ "FROM Usuario u WHERE u.nomUser=:nomUsuario AND u.password=:passUsuario"),
	@NamedQuery(name=Usuario.FIND_USUARIO_X_NOMUSER , query="SELECT u.nomUser,u.password "
			+ "FROM Usuario u WHERE u.nomUser=:nomUsuario")
})

public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Usuario.findAll";
	public static final String FIND_USUARIO = "Usuario.findUsuario";
	public static final String FIND_USUARIO_X_NOMUSER = "Usuario.findUsuarioXNomUser";
	
	@Id
	@Column(name="nom_user")
	private String nomUser;

	private String password;


	public Usuario() {
	}

	public String getNomUser() {
		return this.nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}