package com.hector.apptestlove.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the tiposolicitud database table.
 * 
 */
/*@Entity
@NamedQuery(name="Tiposolicitud.findAll", query="SELECT t FROM Tiposolicitud t")
public class Tiposolicitud implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private short id;

	private String descripcion;

	@OneToMany(mappedBy="tiposolicitud")
	private List<Solicitud> solicituds;

	public Tiposolicitud() {
	}

	public short getId() {
		return this.id;
	}

	public void setId(short id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Solicitud> getSolicituds() {
		return this.solicituds;
	}

	public void setSolicituds(List<Solicitud> solicituds) {
		this.solicituds = solicituds;
	}

	public Solicitud addSolicitud(Solicitud solicitud) {
		getSolicituds().add(solicitud);
		solicitud.setTiposolicitud(this);

		return solicitud;
	}

	public Solicitud removeSolicitud(Solicitud solicitud) {
		getSolicituds().remove(solicitud);
		solicitud.setTiposolicitud(null);

		return solicitud;
	}

}*/