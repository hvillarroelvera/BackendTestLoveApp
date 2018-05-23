package com.hector.apptestlove.controler.dao.bean;

import java.util.ArrayList;
import java.util.List;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.RespuestaPendienteServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.RespuestaPendienteServiceBeanRemote;
import com.hector.apptestlove.controler.dto.PreguntaPendienteDTO;
import com.hector.apptestlove.controler.dto.RespuestaPendienteDTO;
import com.hector.apptestlove.controler.dto.SessionDTO;
import com.hector.apptestlove.controler.util.Util;
import com.hector.apptestlove.model.entity.Preguntapendiente;
import com.hector.apptestlove.model.entity.Respuestapendiente;
import com.hector.apptestlove.model.entity.Session;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class RespuestaPreguntaServiceBean
 */
@Stateless(name = "RespuestaPendienteServiceBean",mappedName = "RespuestaPendienteServiceBean")

public class RespuestaPendienteServiceBean implements RespuestaPendienteServiceBeanRemote, RespuestaPendienteServiceBeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
    public RespuestaPendienteServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)

	public int insertarRespuestaPendiente(
			RespuestaPendienteDTO respuestaPendienteDTO) throws Exception {
		System.out.println("INI insertarRespuestaPendiente");

		try{
			Respuestapendiente respuestaPendiente = new Respuestapendiente();
			respuestaPendiente.setRespuesta(respuestaPendienteDTO.getRespuesta());
			respuestaPendiente.setEstado(respuestaPendienteDTO.getEstado());
			respuestaPendiente.setNumero(respuestaPendienteDTO.getNumero());
			respuestaPendiente.setContacto(respuestaPendienteDTO.getContacto());
			em.persist(respuestaPendiente);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error,no se ha insertado respuestPendiente");
			return Constantes.NOT_SUCCESS;
		}
	
	return Constantes.SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)

	public int modificarRespuestaPendiente(
			RespuestaPendienteDTO respuestaPendienteDTO) throws Exception {
		System.out.println("INI modificarRespuestaPendiente");
		Respuestapendiente respuestaPendiente = null;
		Query query;
		query = em.createNamedQuery(Respuestapendiente.FIND_BY_ID)
				.setParameter("id",respuestaPendienteDTO.getId());
		
		try{
			respuestaPendiente = (Respuestapendiente) query.getSingleResult();
			if(respuestaPendiente != null){
				respuestaPendiente.setRespuesta(respuestaPendienteDTO.getRespuesta());
				respuestaPendiente.setEstado(respuestaPendienteDTO.getEstado());
				respuestaPendiente.setNumero(respuestaPendienteDTO.getNumero());
				return Constantes.SUCCESS;
			}
		}catch(NoResultException noResultE){
			System.out.println("No existe RESPUESTA Pendiente");
			noResultE.printStackTrace();
			return Constantes.NOT_EXIST;
		}
		
		
	  return Constantes.NOT_SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)

	public int eliminarRespuestaPendiente(
			RespuestaPendienteDTO respuestaPendienteDTO) throws Exception {
		System.out.println("INI eliminarRespuestaPendiente");
		Respuestapendiente respuestaPendiente = null;
		try{
			respuestaPendiente = em.find(Respuestapendiente.class,respuestaPendienteDTO.getId());
			if(respuestaPendiente != null){
				em.remove(respuestaPendiente);

			}else{
				System.out.println("No existe respuesta pendiente");
				return Constantes.NOT_EXIST;
			}
		}catch(NoResultException e){
			System.out.println("No existe respuesta pendiente");
			e.printStackTrace();
			return Constantes.NOT_EXIST;
			}catch(Exception e){
				e.printStackTrace();
				return Constantes.NOT_SUCCESS;
			}
		return Constantes.SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)

	public RespuestaPendienteDTO getRespuestaPendienteById(long id)
			throws Exception {
		System.out.println("INI getRespuestaPendienteById");
		RespuestaPendienteDTO respuestaPendienteDTO = new RespuestaPendienteDTO();
		Respuestapendiente respuestapendienteEntity = new Respuestapendiente();
	
		Query query;
		query = em.createNamedQuery(Respuestapendiente.FIND_BY_ID)
				.setParameter("id", id);
		try{
			query.setFirstResult(0).setMaxResults(1).getSingleResult();
			respuestapendienteEntity = ((Respuestapendiente) query.getSingleResult()) ;
			
			respuestaPendienteDTO.setId(respuestapendienteEntity.getId());
			respuestaPendienteDTO.setRespuesta(respuestapendienteEntity.getRespuesta());
			respuestaPendienteDTO.setEstado(respuestapendienteEntity.getEstado());
			respuestaPendienteDTO.setNumero(respuestapendienteEntity.getNumero());
			
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existe respuesta pendiente");
			respuestaPendienteDTO = null;
		}
		catch(Exception e){
			e.printStackTrace();
			respuestaPendienteDTO = null;
		}
		return respuestaPendienteDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<RespuestaPendienteDTO> getRespuestaPendienteByContactoEstado(String contacto,short estado)
			throws Exception {
		System.out.println("INI getRespuestaPendienteByEstado");
		ArrayList<RespuestaPendienteDTO> listaRespuestaPendienteDTO = new ArrayList<RespuestaPendienteDTO>();
		RespuestaPendienteDTO RespuestaPendienteDTO = null;
		Respuestapendiente respuestaPendienteEntity = null;
		List<Respuestapendiente>listaRespuestapendienteEntity = null;
		Query query;
		query = em.createNamedQuery(Respuestapendiente.FIND_BY_CONTACTO_ESTADO)
				.setParameter("estado", estado)
				.setParameter("contacto", contacto);
		try{
			listaRespuestapendienteEntity = query.getResultList();
			if(!listaRespuestapendienteEntity.isEmpty()){
				System.out.println("listaRespuestapendienteEntity no esta vacia");
				for(int i = 0;i<listaRespuestapendienteEntity.size();i++){
					RespuestaPendienteDTO = new RespuestaPendienteDTO();
					respuestaPendienteEntity = new Respuestapendiente();
					respuestaPendienteEntity = listaRespuestapendienteEntity.get(i);
					RespuestaPendienteDTO.setId(respuestaPendienteEntity.getId());
					RespuestaPendienteDTO.setEstado(respuestaPendienteEntity.getEstado());
					RespuestaPendienteDTO.setRespuesta(respuestaPendienteEntity.getRespuesta());
					RespuestaPendienteDTO.setNumero(respuestaPendienteEntity.getNumero());
					listaRespuestaPendienteDTO.add(RespuestaPendienteDTO);
				}
			}else{
				System.out.println("No existe Respuesta pendiente");
				listaRespuestaPendienteDTO = null;
			}
		}catch(NoResultException e){
			System.out.println("No existe Respuesta pendiente");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	return listaRespuestaPendienteDTO;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int actualizarEstadoRespuestaPendiente(long id, short estado)
			throws Exception {
		System.out.println("INI actualizarEstadoRespuestaPendiente");
		Respuestapendiente respuestaPendiente = null;
		Query query;
		query = em.createNamedQuery(Respuestapendiente.FIND_RESPUESTA_PREGUNTA_BY_ID)
				.setParameter("id", id);
		try{
			respuestaPendiente = (Respuestapendiente) query.getSingleResult();
		}catch(NoResultException e){
			System.out.println("No existe Pregunta pendiente");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(respuestaPendiente != null){
			/*Se supone que aca se realiza el update automaticamente*/
			respuestaPendiente.setEstado(estado);
			/*Se supone que aca se realiza el update automaticamente*/
			return Constantes.SUCCESS;
		}
		return Constantes.NOT_SUCCESS;
	}
    
}
