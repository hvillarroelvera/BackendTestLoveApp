package com.hector.apptestlove.controler.dao.bean;

import java.util.ArrayList;
import java.util.List;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.PuntuacionPendienteServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.PuntuacionPendienteServiceBeanRemote;
import com.hector.apptestlove.controler.dto.PuntuacionPendienteDTO;
import com.hector.apptestlove.controler.dto.RespuestaPendienteDTO;
import com.hector.apptestlove.model.entity.Puntuacionpendiente;
import com.hector.apptestlove.model.entity.Respuestapendiente;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class PuntuacionPendienteServiceBean
 */
@Stateless(name = "PuntuacionPendienteServiceBean",mappedName = "PuntuacionPendienteServiceBean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class PuntuacionPendienteServiceBean implements PuntuacionPendienteServiceBeanRemote, PuntuacionPendienteServiceBeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
    public PuntuacionPendienteServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public int insertarPuntuacionPendiente(
			PuntuacionPendienteDTO puntuacionPendienteDTO) throws Exception {
		System.out.println("INI insertarPuntuacionPendiente");

		try{
			Puntuacionpendiente puntuacionpendiente = new Puntuacionpendiente();
			puntuacionpendiente.setContacto(puntuacionPendienteDTO.getContacto());
			puntuacionpendiente.setEstado(puntuacionPendienteDTO.getEstado());
			puntuacionpendiente.setPuntos(puntuacionPendienteDTO.getPuntos());
			em.persist(puntuacionpendiente);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Error,no se ha insertado puntuacionpendiente");
			return Constantes.NOT_SUCCESS;
		}
	
	return Constantes.SUCCESS;
	}

	@Override
	public int modificarPuntuacionPendiente(
			PuntuacionPendienteDTO puntuacionPendienteDTO) throws Exception {
		System.out.println("INI modificarPuntuacionPendiente");
		Puntuacionpendiente puntuacionpendiente = null;
		Query query;
		query = em.createNamedQuery(Puntuacionpendiente.FIND_BY_ID)
				.setParameter("id",puntuacionPendienteDTO.getId());
		
		try{
			puntuacionpendiente = (Puntuacionpendiente) query.getSingleResult();
			if(puntuacionpendiente != null){
				puntuacionpendiente.setContacto(puntuacionPendienteDTO.getContacto());
				puntuacionpendiente.setEstado(puntuacionPendienteDTO.getEstado());
				puntuacionpendiente.setPuntos(puntuacionPendienteDTO.getPuntos());
				return Constantes.SUCCESS;
			}
		}catch(NoResultException noResultE){
			System.out.println("No existe Puntuacionpendiente");
			noResultE.printStackTrace();
			return Constantes.NOT_EXIST;
		}
		
		
	  return Constantes.NOT_SUCCESS;
	}

	@Override
	public int eliminarPuntuacionPendiente(
			PuntuacionPendienteDTO puntuacionPendienteDTO) throws Exception {
		System.out.println("INI eliminarPuntuacionPendiente");
		Puntuacionpendiente puntuacionpendiente = null;
		try{
			puntuacionpendiente = em.find(Puntuacionpendiente.class,puntuacionPendienteDTO.getId());
			if(puntuacionpendiente != null){
				em.remove(puntuacionpendiente);

			}else{
				System.out.println("No existe Puntuacionpendiente");
				return Constantes.NOT_EXIST;
			}
		}catch(NoResultException e){
			System.out.println("No existe Puntuacionpendiente");
			e.printStackTrace();
			return Constantes.NOT_EXIST;
			}catch(Exception e){
				e.printStackTrace();
				return Constantes.NOT_SUCCESS;
			}
		return Constantes.SUCCESS;
	}

	@Override
	public PuntuacionPendienteDTO getPuntuacionPendienteById(long id)
			throws Exception {
		System.out.println("INI getPuntuacionPendienteById");
		PuntuacionPendienteDTO puntuacionPendienteDTO = new PuntuacionPendienteDTO();
		Puntuacionpendiente puntuacionpendiente = new Puntuacionpendiente();
	
		Query query;
		query = em.createNamedQuery(Puntuacionpendiente.FIND_BY_ID)
				.setParameter("id", id);
		try{
			query.setFirstResult(0).setMaxResults(1).getSingleResult();
			puntuacionpendiente = ((Puntuacionpendiente) query.getSingleResult()) ;
			puntuacionPendienteDTO.setId(puntuacionpendiente.getId());
			puntuacionPendienteDTO.setContacto(puntuacionpendiente.getContacto());
			puntuacionPendienteDTO.setEstado(puntuacionpendiente.getEstado());
			puntuacionPendienteDTO.setPuntos(puntuacionpendiente.getPuntos());
			
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existe puntuacionpendiente");
			puntuacionPendienteDTO = null;
		}
		catch(Exception e){
			e.printStackTrace();
			puntuacionPendienteDTO = null;
		}
		return puntuacionPendienteDTO;
	}

	@Override
	public int actualizarEstadoPuntuacionPendiente(long id, short estado)
			throws Exception {
		System.out.println("INI actualizarEstadoPuntuacionPendiente");
		Puntuacionpendiente puntuacionpendiente = null;
		Query query;
		query = em.createNamedQuery(Puntuacionpendiente.FIND_BY_ID)
				.setParameter("id", id);
		try{
			puntuacionpendiente = (Puntuacionpendiente) query.getSingleResult();
		}catch(NoResultException e){
			System.out.println("No existe puntuacionpendiente");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(puntuacionpendiente != null){
			/*Se supone que aca se realiza el update automaticamente*/
			puntuacionpendiente.setEstado(estado);
			/*Se supone que aca se realiza el update automaticamente*/
			return Constantes.SUCCESS;
		}
		return Constantes.NOT_SUCCESS;
	}

	@Override
	public ArrayList<PuntuacionPendienteDTO> getPuntuacionPendienteDTOByContactoEstado(
			String contacto,short estado) throws Exception {
		System.out.println("INI getPuntuacionPendienteDTOByEstado");
		ArrayList<PuntuacionPendienteDTO> listaPuntuacionPendienteDTO = new ArrayList<PuntuacionPendienteDTO>();
		PuntuacionPendienteDTO puntuacionPendienteDTO = null;
		Puntuacionpendiente puntuacionpendienteEntity = null;
		List<Puntuacionpendiente>listapuntuacionpendienteEntity = null;
		Query query;
		query = em.createNamedQuery(Puntuacionpendiente.FIND_BY_CONTACTO_ESTADO)
				.setParameter("estado", estado)
				.setParameter("contacto", contacto);
		try{
			listapuntuacionpendienteEntity = query.getResultList();
			if(!listapuntuacionpendienteEntity.isEmpty()){
				System.out.println("listapuntuacionpendienteEntity no esta vacia");
				for(int i = 0;i<listapuntuacionpendienteEntity.size();i++){
					puntuacionPendienteDTO = new PuntuacionPendienteDTO();
					puntuacionpendienteEntity = new Puntuacionpendiente();
					puntuacionpendienteEntity = listapuntuacionpendienteEntity.get(i);
					puntuacionPendienteDTO.setId(puntuacionpendienteEntity.getId());
					puntuacionPendienteDTO.setContacto(puntuacionpendienteEntity.getContacto());
					puntuacionPendienteDTO.setEstado(puntuacionpendienteEntity.getEstado());
					puntuacionPendienteDTO.setPuntos(puntuacionpendienteEntity.getPuntos());
					listaPuntuacionPendienteDTO.add(puntuacionPendienteDTO);
				}
			}else{
				System.out.println("No existe puntuacionpendiente");
				listaPuntuacionPendienteDTO = null;
			}
		}catch(NoResultException e){
			System.out.println("No existe puntuacionpendiente");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	return listaPuntuacionPendienteDTO;
	}

}
