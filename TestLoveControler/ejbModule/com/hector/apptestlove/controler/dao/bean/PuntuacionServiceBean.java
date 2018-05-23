package com.hector.apptestlove.controler.dao.bean;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.PuntuacionServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.PuntuacionServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.PuntuacionDTO;
import com.hector.apptestlove.controler.dto.PuntuacionResultadoDTO;
import com.hector.apptestlove.model.entity.Contacto;
import com.hector.apptestlove.model.entity.Pregunta;
import com.hector.apptestlove.model.entity.Puntuacion;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class PuntuacionServiceBean
 */
@Stateless(name = "PuntuacionServiceBean",mappedName = "PuntuacionServiceBean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class PuntuacionServiceBean implements PuntuacionServiceBeanRemote, PuntuacionServiceBeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
    public PuntuacionServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int actualizarPuntuacion(long id_contacto,
			PuntuacionResultadoDTO puntuacion) throws Exception {
		System.out.println("INI actualizarPuntuacion");
		Puntuacion puntuacionEntity = null;
		
		try{
			puntuacionEntity = em.find(Puntuacion.class, id_contacto);
			if(puntuacionEntity != null){
				puntuacionEntity.setPuntos(puntuacion.getPuntos());
			}else{
				return Constantes.NOT_SUCCESS;

			}
			}catch(Exception e){
				e.printStackTrace();
				return Constantes.NOT_SUCCESS;
			}
			return Constantes.SUCCESS;
	}

	/*Reemplaza a buscarPuntuacion*/
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int existePuntuacion(long id_contacto) throws Exception {
		System.out.println("INI existePuntuacion");
        Puntuacion puntuacionEntity;
		
		try{
			puntuacionEntity = em.find(Puntuacion.class, id_contacto);
			
			}catch(Exception e){
				//e.printStackTrace();
				System.out.println("No existe puntuacion");
				return Constantes.NOT_SUCCESS;
			}
		if(puntuacionEntity != null){
			return Constantes.SUCCESS;
		}else{
			return Constantes.NOT_SUCCESS;
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int insertarPuntuacion(long id_contacto,
			PuntuacionResultadoDTO puntuacion) throws Exception {
		System.out.println("INI insertarPuntuacion");
		Puntuacion puntuacionEntity = new Puntuacion();
		puntuacionEntity.setIdContacto(id_contacto);
		puntuacionEntity.setPuntos(puntuacion.getPuntos());
		try{
			em.persist(puntuacionEntity);

		}catch(Exception e){
			e.printStackTrace();
			return Constantes.NOT_SUCCESS;
		}
		return Constantes.SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int getPuntos(long id_contacto) throws Exception {
		System.out.println("INI getPuntos");
		 Puntuacion puntuacionEntity;
		 try{
		 puntuacionEntity = em.find(Puntuacion.class, id_contacto);
		 if(puntuacionEntity != null){
				return puntuacionEntity.getPuntos();
			}else{
				 System.out.println("No existe puntuacion");

				return Constantes.NOT_SUCCESS;

			}
		 }catch(NoResultException e){
			 System.out.println("No existe puntuacion");
			 return Constantes.NOT_SUCCESS;
		 }
	}

	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int eliminarPuntuacion(PuntuacionDTO puntuacionDTO) throws Exception {
		System.out.println("INI eliminarPuntuacion");
		Puntuacion puntuacionEntity = null;
		try{
			puntuacionEntity = em.find(Puntuacion.class,puntuacionDTO.getIdContacto());
			if(puntuacionEntity != null){
				em.remove(puntuacionEntity);
			}else{
				return Constantes.NOT_EXIST;

			}
			
		}catch(NoResultException e){
			System.out.println("No existe puntuacion");
			e.printStackTrace();
			return Constantes.NOT_EXIST;
			}catch(Exception e){
				e.printStackTrace();
				return Constantes.NOT_SUCCESS;
			}
		return Constantes.SUCCESS;
	}
	
}
