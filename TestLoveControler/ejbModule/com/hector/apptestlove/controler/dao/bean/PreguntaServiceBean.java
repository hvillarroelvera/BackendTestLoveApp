package com.hector.apptestlove.controler.dao.bean;

import java.util.ArrayList;
import java.util.List;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.PreguntaServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.PreguntaServiceBeanRemote;
import com.hector.apptestlove.controler.dao.bean.remote.UsuarioServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.model.entity.Contacto;
import com.hector.apptestlove.model.entity.Pregunta;

import javax.ejb.EJB;
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
 * Session Bean implementation class PreguntaServiceBean
 */
@Stateless(name = "PreguntaServiceBean", mappedName = "PreguntaServiceBean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class PreguntaServiceBean implements PreguntaServiceBeanRemote, PreguntaServiceBeanLocal {
	
	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	@EJB
	private UsuarioServiceBeanRemote usuarioServiceBean;
	
    public PreguntaServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getCountPreguntasXUsuario(UsuarioDTO usuario)
			throws Exception {
		System.out.println("INI getCountPreguntasXUsuario");
		Query query;
		long cantidadPreguntas = 0;
		query = em.createNamedQuery(Pregunta.COUNT).setParameter("nomUser", usuario.getNom_user());
		try{
			cantidadPreguntas = (long) query.getSingleResult();
			return (int)cantidadPreguntas;
			
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existen preguntas");
			return Constantes.NOT_SUCCESS;
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public long insertarPregunta(UsuarioDTO usuario, PreguntaDTO pregunta)
			throws Exception {
		System.out.println("INI insertarPregunta");
		System.out.println("insertarPregunta pregunta["+pregunta.getPregunta()+"]");
		Pregunta preguntaEntity = new Pregunta();
		Query query;
		long numeroPregunta = 0;
		
		if(usuarioServiceBean.buscarUsuario(usuario.getNom_user())  == Constantes.SUCCESS){
			query = em.createNamedQuery(Pregunta.FIND_ALL_ORDERBY_NUMERO).setFirstResult(0)
					.setMaxResults(1);
			
			try{
				preguntaEntity = (Pregunta) query.getSingleResult();
				numeroPregunta = preguntaEntity.getNumero();
			}catch(NoResultException e){
				System.out.println("No existen Preguntas,se agregara desde indice[0]");
			}
			numeroPregunta = numeroPregunta+1;
			preguntaEntity = new Pregunta();
			preguntaEntity.setNumero(numeroPregunta);
			preguntaEntity.setNomUser(usuario.getNom_user());
			preguntaEntity.setPregunta(pregunta.getPregunta());
			try{
				em.persist(preguntaEntity);
			}catch(Exception e ){
				e.printStackTrace();
				return Constantes.NOT_SUCCESS;
			}
			
			return numeroPregunta;
		}
	  return Constantes.NOT_SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public PreguntasDTO getPreguntasXUsuario(UsuarioDTO usuario)
			throws Exception {
		System.out.println("INI getPreguntasXUsuario");
		System.out.println("getPreguntasXUsuario nomUser["+usuario.getNom_user()+"]");
		
		
		List<Pregunta> preguntas = null;
		Pregunta pregunta;
		PreguntasDTO preguntasDTO = new PreguntasDTO();
		ArrayList<PreguntaDTO>listaPreguntasDTO = new ArrayList<PreguntaDTO>();
		PreguntaDTO preguntaDTO;
		Query query;
		query = em.createNamedQuery(Pregunta.FIND_ALL_BY_NOMUSER)
				.setParameter("nomUser", usuario.getNom_user());
		try{
			preguntas = query.getResultList();
			
			if(!preguntas.isEmpty()){
				System.out.println("getPreguntasXUsuario, Entro a if preguntas!= null ");
				for(int i=0;i<preguntas.size();i++){
					preguntaDTO = new PreguntaDTO();
					pregunta = new Pregunta();
					pregunta = preguntas.get(i);
					
					preguntaDTO.setNumero(pregunta.getNumero());
					preguntaDTO.setPregunta(pregunta.getPregunta());
					
					System.out.println("getPreguntasXUsuario numeropregunta["+preguntaDTO.getNumero()+"]");
					System.out.println("getPreguntasXUsuario pregunta["+preguntaDTO.getPregunta()+"]");
					
					listaPreguntasDTO.add(preguntaDTO);
				}
			}else{
				System.out.println("No existe Pregunta");
				preguntasDTO = null;
				return preguntasDTO;
			}
			
			
			if(listaPreguntasDTO.size() > 0){
				preguntasDTO.setPreguntas(listaPreguntasDTO);
				preguntasDTO.setNom_user(usuario.getNom_user());
				preguntasDTO.setCantidadPreguntas(listaPreguntasDTO.size());
			}else{
				preguntasDTO.setPreguntas(null);
				preguntasDTO.setNom_user(usuario.getNom_user());
				preguntasDTO.setCantidadPreguntas(0);
			}
			
		}catch(NoResultException e){
			System.out.println("No existe Pregunta");
			preguntasDTO = null;
			//e.printStackTrace();
		}catch(Exception e ){
			e.printStackTrace();
			
		}
		
		return preguntasDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public PreguntasDTO getPreguntasXUsuarioXNumero(UsuarioDTO usuario,
			PreguntasDTO preguntasPendientes) throws Exception {
		System.out.println("INI getPreguntasXUsuarioXNumero");
		ArrayList<PreguntaDTO>listapp=new ArrayList<PreguntaDTO>();
		ArrayList<PreguntaDTO>nuevalistapp=new ArrayList<PreguntaDTO>();
		PreguntaDTO preguntaDTO;
		Pregunta preguntaEntity = null;
		
		listapp=preguntasPendientes.getPreguntas();
			
			for(int i=0;i<listapp.size();i++){
				preguntaDTO=new PreguntaDTO();
				preguntaDTO=listapp.get(i);
                
				try{
					preguntaEntity = em.find(Pregunta.class, preguntaDTO.getNumero());
					if(preguntaEntity != null){
						//Pasar datos entity a dto

						preguntaDTO.setPregunta(preguntaEntity.getPregunta());
						return preguntasPendientes;
					}else{
						
					}
					
				}catch(NoResultException e){
					//e.printStackTrace();
					System.out.println("No existen preguntas");
				}
				
				nuevalistapp.add(preguntaDTO);
			}
		preguntasPendientes.cleanPreguntas();
		preguntasPendientes.setPreguntas(nuevalistapp);
		
		return preguntasPendientes;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int actualizarPregunta(PreguntaDTO pregunta)
			throws Exception {
		System.out.println("INI actualizarPregunta");
		Pregunta preguntaEntity = null;
		
		try{
		preguntaEntity = em.find(Pregunta.class, pregunta.getNumero());
		if(preguntaEntity != null){
			preguntaEntity.setPregunta(pregunta.getPregunta());

		}else{
			System.out.println("No existe Pregunta");

			return Constantes.NOT_SUCCESS;

		}
		}catch(NoResultException e){
			System.out.println("No existe Pregunta");
			//e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			return Constantes.NOT_SUCCESS;
		}
		return Constantes.SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int eliminarPregunta(PreguntaDTO pregunta)
			throws Exception {
		System.out.println("INI eliminarPregunta");
		Pregunta preguntaEntity;
		
		try{
			preguntaEntity = em.find(Pregunta.class, pregunta.getNumero());
			if(preguntaEntity != null){
				em.remove(preguntaEntity);
			}else{
				System.out.println("No existe Pregunta");
				return Constantes.NOT_EXIST;

			}
			
		}catch(NoResultException e){
			System.out.println("No existe Pregunta");
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

	public PreguntaDTO getPreguntaByNumero(long numero) throws Exception {
		
		System.out.println("INI getPreguntaByNumero");
		Query query;
		Pregunta pregunta = null;
		PreguntaDTO preguntaDTO = new PreguntaDTO();
		try{
			pregunta = em.find(Pregunta.class, numero);
			preguntaDTO.setNumero(pregunta.getNumero());
			preguntaDTO.setPregunta(pregunta.getPregunta());
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existe Pregunta");
			preguntaDTO = null;
		}
		return preguntaDTO;
	}

}
