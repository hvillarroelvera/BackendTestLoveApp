package com.hector.apptestlove.controler.dao.bean;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.GcmServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.GcmServiceBeanRemote;
import com.hector.apptestlove.controler.dto.GcmDTO;


import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.model.entity.Gcm;

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
 * Session Bean implementation class GcmServiceBean
 */
@Stateless(name = "GcmServiceBean", mappedName = "GcmServiceBean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class GcmServiceBean implements GcmServiceBeanRemote, GcmServiceBeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
    public GcmServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int registrarGcm(GcmDTO gcm, String nom_user) throws Exception {
		System.out.println("INI registrarGcm");
		Gcm gcmEntity = new Gcm();
		gcmEntity.setCodGcm(gcm.getGcm_codGcm());
		gcmEntity.setNomUser(nom_user);
		gcmEntity.setExpirationtime(gcm.getExpirationTime());
		gcmEntity.setAppversion(gcm.getAppVersion());
		try{
		em.persist(gcmEntity);
		}catch(Exception e){
			return Constantes.NOT_SUCCESS;
		}
		return Constantes.SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int existeGcmXUser(String codGcm, String nom_user) throws Exception {
		System.out.println("INI existeGcmXUser");
		Gcm gcmEntity = new Gcm();
		Query query;
		long cantidadCount=0;
		query = em.createNamedQuery(Gcm.COUNTXUSER);
		query.setParameter("nomUsuario", nom_user);
		try{
			cantidadCount =(long) query.getSingleResult();
			return (int) cantidadCount;
			
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existe Gcm");
			return (int) cantidadCount;
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public GcmDTO getGcmXUsuario(UsuarioDTO usuario) throws Exception {
		System.out.println("INI getGcmXUsuario");
		Gcm gcmEntity = null;
		GcmDTO gcmDTO = null;
		try{
			gcmEntity = em.find(Gcm.class, usuario.getNom_user());
			if(gcmEntity != null){
				gcmDTO = new GcmDTO();
				gcmDTO.setGcm_codGcm(gcmEntity.getCodGcm());
				gcmDTO.setAppVersion(gcmEntity.getAppversion());
				gcmDTO.setExpirationTime(gcmEntity.getExpirationtime());
			}
			
			
		}catch(NoResultException e){
			System.out.println("No existe Gcm");
			//e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return gcmDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int actualizarGcm(GcmDTO gcm, String nom_user) throws Exception {
		System.out.println("INI actualizarGcm");
		Gcm gcmEntity = null;
		try{
			gcmEntity = em.find(Gcm.class, nom_user);
			if(gcmEntity != null){
				gcmEntity.setCodGcm(gcm.getGcm_codGcm());
				gcmEntity.setAppversion(gcm.getAppVersion());
				gcmEntity.setExpirationtime(gcm.getExpirationTime());
			}else{
				return Constantes.SUCCESS;
			}
			
		}catch(NoResultException e){
			System.out.println("No existe Gcm");
			e.printStackTrace();
			return Constantes.SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return Constantes.NOT_SUCCESS;
		}
		return Constantes.SUCCESS;
	}

}
