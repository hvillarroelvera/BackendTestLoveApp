package com.hector.apptestlove.model.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the solicitud database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name=Solicitud.FIND_ALL, query="SELECT s FROM Solicitud s"),
	@NamedQuery(name=Solicitud.SELECT_X_EMISOR_RECEPTOR, query="SELECT s  FROM Solicitud s "
			+ "WHERE s.id.userEmisor = :emisor AND s.id.userReceptor = :receptor"),
    @NamedQuery(name=Solicitud.COUNT, query="SELECT COUNT(s.id.numero) "
    		+ "FROM Solicitud s WHERE s.id.userEmisor = :emisor AND "
    		+ "s.id.userReceptor = :receptor AND s.estado = :estado AND s.tipo = :tipo"),
    @NamedQuery(name=Solicitud.FIND_BY_NUMERO, query="SELECT s "
    	    		+ "FROM Solicitud s WHERE s.id.numero = :numero"),
    @NamedQuery(name=Solicitud.FIND_BY_EMISOR_ENVIADA, query="SELECT s "
    	    + "FROM Solicitud s WHERE s.id.userEmisor = :emisor AND s.estado = :estado"),
    @NamedQuery(name=Solicitud.FIND_ALL_BY_RECEPTOR_ESTADO_TIPO, query="SELECT s "
    	    + "FROM Solicitud s WHERE "
    	    + "s.id.userReceptor = :receptor AND s.estado = :estado AND s.tipo = :tipo"),
    @NamedQuery(name=Solicitud.FIND_ALL_BY_EMISOR_ESTADO_TIPO, query="SELECT s "
    	    + "FROM Solicitud s WHERE "
    	    + "s.id.userEmisor = :emisor AND s.estado = :estado AND s.tipo = :tipo")
})

public class Solicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_ALL = "Solicitud.findAll";
	public static final String SELECT_X_EMISOR_RECEPTOR = "Solicitud.selectXEmisorReceptor";
	public static final String COUNT = "Solicitud.count";
	public static final String FIND_BY_NUMERO = "Solicitud.findByNumero";
	public static final String FIND_BY_EMISOR_ENVIADA = "Solicitud.findByEmisorEnviada";
	public static final String FIND_ALL_BY_RECEPTOR_ESTADO_TIPO = "Solicitud.findAllByReceptorEstadoTipo";
	public static final String FIND_ALL_BY_EMISOR_ESTADO_TIPO = "Solicitud.findAllByEmisorEstadoTipo";

	
	@EmbeddedId
	private SolicitudPK id;
	
	private short estado;
	
	private short tipo;

	//bi-directional many-to-one association to Estadosolicitud
	/*@ManyToOne
	@JoinColumn(name="estado")
	private Estadosolicitud estadosolicitud;*/

	//bi-directional many-to-one association to Tiposolicitud
	/*@ManyToOne
	@JoinColumn(name="tipo")
	private Tiposolicitud tiposolicitud;*/

	public Solicitud() {
	}

	public SolicitudPK getId() {
		return this.id;
	}

	public void setId(SolicitudPK id) {
		this.id = id;
	}

	public short getEstado() {
		return estado;
	}

	public void setEstado(short estado) {
		this.estado = estado;
	}

	public short getTipo() {
		return tipo;
	}

	public void setTipo(short tipo) {
		this.tipo = tipo;
	}

	
	/*public Estadosolicitud getEstadosolicitud() {
		return this.estadosolicitud;
	}

	public void setEstadosolicitud(Estadosolicitud estadosolicitud) {
		this.estadosolicitud = estadosolicitud;
	}

	public Tiposolicitud getTiposolicitud() {
		return this.tiposolicitud;
	}

	public void setTiposolicitud(Tiposolicitud tiposolicitud) {
		this.tiposolicitud = tiposolicitud;
	}
*/
}