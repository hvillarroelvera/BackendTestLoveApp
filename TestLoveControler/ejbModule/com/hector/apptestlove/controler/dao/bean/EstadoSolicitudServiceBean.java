package com.hector.apptestlove.controler.dao.bean;

import java.util.ArrayList;
import java.util.List;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.EstadoSolicitudServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.EstadoSolicitudServiceBeanRemote;
import com.hector.apptestlove.controler.dto.EstadoGenericoDTO;
import com.hector.apptestlove.model.entity.EstadoSession;
import com.hector.apptestlove.model.entity.Estadosolicitud;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class EstadoSolicitudServiceBean
 */
@Stateless(name = "EstadoSolicitudServiceBean",mappedName = "EstadoSolicitudServiceBean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class EstadoSolicitudServiceBean implements EstadoSolicitudServiceBeanRemote, EstadoSolicitudServiceBeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public EstadoSolicitudServiceBean() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<EstadoGenericoDTO> getListEstadoSolicitud()
			throws Exception {
		List<Estadosolicitud> listaEstados;
		Estadosolicitud estadoSolicitud;
		ArrayList<EstadoGenericoDTO> listaEstadosGenericos = new ArrayList<EstadoGenericoDTO>();
		EstadoGenericoDTO estadoGenericoDTO;
		Query query;
		query = em.createNamedQuery(Estadosolicitud.FIND_ALL);
		try{
			listaEstados = query.getResultList();
			
			for(int i=0;i<listaEstados.size();i++){
				estadoGenericoDTO = new EstadoGenericoDTO();
				estadoSolicitud = new Estadosolicitud();
				estadoSolicitud = listaEstados.get(i);
				
				estadoGenericoDTO.setId(estadoSolicitud.getId());
				estadoGenericoDTO.setEstadoDescripcion(estadoSolicitud.getEstadoDescripcion());
				
				listaEstadosGenericos.add(estadoGenericoDTO);
			}
			
		}catch(NoResultException e){
			System.out.println("No existen estados Solicitud");
			listaEstadosGenericos = null;
			//e.printStackTrace();
		}catch(Exception e ){
			e.printStackTrace();
			listaEstadosGenericos = null;
		}
		
		return listaEstadosGenericos;
	}

    
}
