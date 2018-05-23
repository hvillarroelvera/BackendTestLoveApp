package com.hector.apptestlove.controler.dao.bean;

import java.util.ArrayList;
import java.util.List;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.EstadoSessionServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.EstadoSessionServiceBeanRemote;
import com.hector.apptestlove.controler.dto.EstadoGenericoDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.model.entity.EstadoSession;
import com.hector.apptestlove.model.entity.Estadosolicitud;
import com.hector.apptestlove.model.entity.Pregunta;

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
 * Session Bean implementation class EstadoSessionServiceBean
 */
@Stateless(name = "EstadoSessionServiceBean",mappedName = "EstadoSessionServiceBean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class EstadoSessionServiceBean implements EstadoSessionServiceBeanRemote, EstadoSessionServiceBeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
    /**
     * Default constructor. 
     */
    public EstadoSessionServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<EstadoGenericoDTO> getListEstadoSession()
			throws Exception {
		List<EstadoSession> listaEstados;
		EstadoSession estadoSesion;
		ArrayList<EstadoGenericoDTO> listaEstadosGenericos = new ArrayList<EstadoGenericoDTO>();
		EstadoGenericoDTO estadoGenericoDTO;
		Query query;
		query = em.createNamedQuery(EstadoSession.FIND_ALL);
		try{
			listaEstados = query.getResultList();
			
			for(int i=0;i<listaEstados.size();i++){
				estadoGenericoDTO = new EstadoGenericoDTO();
				estadoSesion = new EstadoSession();
				estadoSesion = listaEstados.get(i);
				
				estadoGenericoDTO.setId(estadoSesion.getId());
				estadoGenericoDTO.setEstadoDescripcion(estadoSesion.getEstadoDescripcion());
				
				listaEstadosGenericos.add(estadoGenericoDTO);
			}
			
		}catch(NoResultException e){
			System.out.println("No existen estados Sesion");
			listaEstadosGenericos = null;
			//e.printStackTrace();
		}catch(Exception e ){
			e.printStackTrace();
			listaEstadosGenericos = null;
		}
		
		return listaEstadosGenericos;
	}

}
