package com.hector.apptestlove.model.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the pregunta database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Pregunta.FIND_ALL, query="SELECT p FROM Pregunta p"),
	@NamedQuery(name=Pregunta.COUNT, query="SELECT COUNT(p) FROM Pregunta p "
			+ "WHERE p.nomUser = :nomUser"),
    @NamedQuery(name=Pregunta.FIND_ALL_ORDERBY_NUMERO, query="SELECT p FROM Pregunta p "
    		+ "ORDER BY p.numero DESC"),
    @NamedQuery(name=Pregunta.FIND_ALL_BY_NOMUSER, query="SELECT p FROM Pregunta p "
    		+ "WHERE p.nomUser = :nomUser"),
    @NamedQuery(name=Pregunta.FIND_ALL_BY_NUMERO, query="SELECT p FROM Pregunta p "
    	    + "WHERE p.numero = :numero"),
    @NamedQuery(name=Pregunta.FIND_PREGUNTAS_BY_CONTACTO_ESTADO , query="select"
    	    + " t "
    	    + "from Preguntapendiente p, Pregunta t "
    	    + "where p.contacto = :contacto "
    	    + "and p.estado = :estado "
    	    + "and p.numero = t.numero")
})

public class Pregunta implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Pregunta.findAll";
	public static final String COUNT = "Pregunta.count";
	public static final String FIND_ALL_ORDERBY_NUMERO = "Pregunta.findAllOrderByNumero";
	public static final String FIND_ALL_BY_NOMUSER = "Pregunta.findAllByNomUser";
	public static final String FIND_ALL_BY_NUMERO = "Pregunta.findAllByNumero";
	public static final String FIND_PREGUNTAS_BY_CONTACTO_ESTADO = "Pregunta.findPreguntasByContactoEstado";


	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private long numero;

	private String pregunta;
	
	@Column(name="nom_user")
	private String nomUser;

	/*@ManyToOne
	@JoinColumn(name="nom_user")
	private Usuario usuario;

	@OneToOne(mappedBy="pregunta1")
	private Preguntapendiente preguntapendiente1;

	@OneToOne(mappedBy="pregunta2")
	private Preguntapendiente preguntapendiente2;*/

	public Pregunta() {
	}

	

	public long getNumero() {
		return numero;
	}



	public void setNumero(long numero) {
		this.numero = numero;
	}



	public String getPregunta() {
		return this.pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public String getNomUser() {
		return nomUser;
	}

	public void setNomUser(String nomUser) {
		this.nomUser = nomUser;
	}
	
	

	/*public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Preguntapendiente getPreguntapendiente1() {
		return this.preguntapendiente1;
	}

	public void setPreguntapendiente1(Preguntapendiente preguntapendiente1) {
		this.preguntapendiente1 = preguntapendiente1;
	}

	public Preguntapendiente getPreguntapendiente2() {
		return this.preguntapendiente2;
	}

	public void setPreguntapendiente2(Preguntapendiente preguntapendiente2) {
		this.preguntapendiente2 = preguntapendiente2;
	}*/

}