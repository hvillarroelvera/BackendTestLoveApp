package com.hector.apptestlove.controler.dao.bean;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.SessionServicebeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.SessionServicebeanRemote;
import com.hector.apptestlove.controler.dao.bean.remote.UsuarioServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.SessionDTO;
import com.hector.apptestlove.controler.util.Util;
import com.hector.apptestlove.model.entity.Contacto;
import com.hector.apptestlove.model.entity.Session;

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
 * Session Bean implementation class SessionServicebean
 */
@Stateless(name = "SessionServicebean",mappedName = "SessionServicebean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class SessionServicebean implements SessionServicebeanRemote, SessionServicebeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	@EJB
	private UsuarioServiceBeanRemote usuarioServiceBean;
	
    public SessionServicebean() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int insertarSession(SessionDTO sessionDTO) throws Exception {
		System.out.println("INI insertarSession");
		if(usuarioServiceBean.buscarUsuario(sessionDTO.getUsuario()) == Constantes.SUCCESS){
			try{
				Session sessionEntity = new Session();
				sessionEntity.setEstado(sessionDTO.getEstado());
				sessionEntity.setUsuario(sessionDTO.getUsuario());
				if(sessionDTO.getUltimaFechaLogin()!= null){
					
					sessionEntity.setUltimaFechaLogin(Util.getCalendarFromString(sessionDTO.getUltimaFechaLogin()));
				}else{
					sessionEntity.setUltimaFechaLogin(null);
				}
				
				em.persist(sessionEntity);
			}catch(Exception e){
				e.printStackTrace();
				System.out.println("Error,no se ha insertado session");
				return Constantes.NOT_SUCCESS;
			}
		}
		return Constantes.SUCCESS;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int modificarSession(SessionDTO sessionDTO) throws Exception {
		System.out.println("INI modificarSession");
		Session sessionEntity = null;
		Query query;
		query = em.createNamedQuery(Session.FIND_NOMUSER)
				         .setParameter("usuario", sessionDTO.getUsuario());
		
		try{
			sessionEntity = (Session) query.getSingleResult();
		}catch(NoResultException noResultE){
			System.out.println("No existe session");
			noResultE.printStackTrace();
			return Constantes.NOT_EXIST;
		}
		
		if(sessionEntity != null){
			sessionEntity.setUsuario(sessionDTO.getUsuario());
			sessionEntity.setEstado(sessionDTO.getEstado());
			if(sessionDTO.getUltimaFechaLogin()!= null){
				
				sessionEntity.setUltimaFechaLogin(Util.getCalendarFromString(sessionDTO.getUltimaFechaLogin()));
			}else{
				sessionEntity.setUltimaFechaLogin(null);
			}
			return Constantes.SUCCESS;
		}
	  return Constantes.NOT_SUCCESS;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int eliminarSession(SessionDTO sessionDTO) throws Exception {
		System.out.println("INI eliminarSession");
		Session sessionEntity = null;
		try{
			sessionEntity = em.find(Session.class,sessionDTO.getId());
			if(sessionEntity != null){
				em.remove(sessionEntity);

			}else{
				System.out.println("No existe session");
				return Constantes.NOT_EXIST;
			}
		}catch(NoResultException e){
			System.out.println("No existe session");
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
	public SessionDTO getSessionByUsuario(String usuario) throws Exception {
		System.out.println("INI getSession");
		System.out.println("getSession usuario["+usuario+"]");
		SessionDTO sessionDTO = new SessionDTO();
		Session sessionEntity = new Session();
	
		Query query;
		query = em.createNamedQuery(Session.FIND_NOMUSER)
				.setParameter("usuario", usuario);
		try{
			query.setFirstResult(0).setMaxResults(1).getSingleResult();
			sessionEntity = ((Session) query.getSingleResult()) ;
		
			sessionDTO.setId(sessionEntity.getId());
			sessionDTO.setUsuario(sessionEntity.getUsuario());
			sessionDTO.setEstado(sessionEntity.getEstado());
			sessionDTO.setUltimaFechaLogin(Util.getFechaHora(sessionEntity.getUltimaFechaLogin()));
			
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existe Session");
			sessionDTO = null;
		}
		catch(Exception e){
			e.printStackTrace();
			sessionDTO = null;
		}
		return sessionDTO;
	}

}
