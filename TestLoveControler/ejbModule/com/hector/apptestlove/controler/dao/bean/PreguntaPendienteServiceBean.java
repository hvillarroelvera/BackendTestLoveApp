package com.hector.apptestlove.controler.dao.bean;

import java.util.ArrayList;
import java.util.List;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.PreguntaPendienteServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.ContactoServiceBeanRemote;
import com.hector.apptestlove.controler.dao.bean.remote.PreguntaPendienteServiceBeanRemote;
import com.hector.apptestlove.controler.dao.bean.remote.PreguntaServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntaPendienteDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.model.entity.Pregunta;
import com.hector.apptestlove.model.entity.Preguntapendiente;
import com.hector.apptestlove.model.entity.Solicitud;

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
 * Session Bean implementation class PreguntaPendienteServiceBean
 */
@Stateless(name = "PreguntaPendienteServiceBean", mappedName = "PreguntaPendienteServiceBean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class PreguntaPendienteServiceBean implements PreguntaPendienteServiceBeanRemote, PreguntaPendienteServiceBeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	@EJB
	PreguntaServiceBeanRemote preguntaServiceBean;
	
	@EJB
	ContactoServiceBeanRemote contactoServiceBean;
	
    public PreguntaPendienteServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int insertarPreguntaPendiente(UsuarioDTO usuario,
			PreguntaDTO pregunta,short estado) throws Exception {
		System.out.println("INI insertarPreguntaPendiente");
		PreguntasDTO preguntasUsuario=new PreguntasDTO();
		ArrayList<PreguntaDTO>listap=new ArrayList();
		PreguntaDTO preguntaUsuario=new PreguntaDTO();
		ContactoDTO contactoDTO = new ContactoDTO();
		Preguntapendiente preguntaPendienteEntity = new Preguntapendiente();
		
		preguntasUsuario = preguntaServiceBean.getPreguntasXUsuario(usuario);
		contactoDTO = contactoServiceBean.getContactoXUsuario(usuario);
		listap=preguntasUsuario.getPreguntas();
		
		for(int i=0;i<listap.size();i++){
			preguntaUsuario=listap.get(i);
			
			if(preguntaUsuario.getPregunta().equals(pregunta.getPregunta())){
				preguntaPendienteEntity.setNumero(preguntaUsuario.getNumero());
				preguntaPendienteEntity.setContacto(contactoDTO.getContacto());
				preguntaPendienteEntity.setEstado(estado);
				
				try{
					em.persist(preguntaPendienteEntity);
				}catch(Exception e){
					e.printStackTrace();
					return Constantes.NOT_SUCCESS;
				}
			}
		}
		return Constantes.SUCCESS;
	}

	@SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public PreguntasDTO getNumerosFromPPXContacto(ContactoDTO contacto)
			throws Exception {
		System.out.println("INI getNumerosFromPPXContacto");
		PreguntasDTO preguntas=new PreguntasDTO();
		ArrayList<PreguntaDTO>listap=new ArrayList<PreguntaDTO>();
		PreguntaDTO preguntaDTO = new PreguntaDTO();
		List<Preguntapendiente>listappEntity=new ArrayList<Preguntapendiente>();
		Preguntapendiente preguntaPendiente;
		Query query;
		
		query = em.createNamedQuery(Preguntapendiente.FIND_NUMEROS_BY_CONTACTO)
				        .setParameter("contacto", contacto.getContacto());
		
		try{
			listappEntity = query.getResultList();
			
			for(int i=0;i<listappEntity.size();i++){
				preguntaPendiente = new Preguntapendiente();
				preguntaPendiente = listappEntity.get(i);
				
				preguntaDTO.setNumero(preguntaPendiente.getNumero());
				listap.add(preguntaDTO);
			}
			preguntas.setPreguntas(listap);
		}catch(Exception e){
			//e.printStackTrace();
			System.out.println("No existen numeros de preguntas pendientes por contacto");
		}
		return preguntas;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int eliminarPreguntaPendiente(PreguntaPendienteDTO preguntaPendienteDTO) throws Exception {
		System.out.println("INI eliminarPreguntaPendiente");
		Preguntapendiente preguntaPendiente = null;
		try{
			preguntaPendiente = em.find(Preguntapendiente.class, preguntaPendienteDTO.getId());
			
			if(preguntaPendiente != null){
				em.remove(preguntaPendiente);
			}else{
				System.out.println("No existe preguntaPendiente");
				return Constantes.NOT_EXIST;

			}
			
		}catch(Exception e){
			e.printStackTrace();
			return Constantes.NOT_SUCCESS;
		}
		return Constantes.SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int actualizarEstadoPreguntaPendiente(long id, short estado)
			throws Exception {
		System.out.println("INI actualizarEstadoPreguntaPendiente");
		Preguntapendiente preguntapendiente = null;
		Query query;
		query = em.createNamedQuery(Preguntapendiente.FIND_PREGUNTA_PENDIENTE_BY_ID)
				.setParameter("id", id);
		try{
			preguntapendiente = (Preguntapendiente) query.getSingleResult();
		}catch(NoResultException e){
			System.out.println("No existe Pregunta pendiente");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(preguntapendiente != null){
			/*Se supone que aca se realiza el update automaticamente*/
			preguntapendiente.setEstado(estado);
			/*Se supone que aca se realiza el update automaticamente*/
			return Constantes.SUCCESS;
		}
		return Constantes.NOT_SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<PreguntaPendienteDTO> getPreguntaPendienteByContactoEstado(String contacto,short estado)
			throws Exception {
		System.out.println("INI getPreguntaPendienteByNumeroEstado");
		ArrayList<PreguntaPendienteDTO> listaPreguntasPendientesDTO = new ArrayList<PreguntaPendienteDTO>();
		PreguntaPendienteDTO preguntaPendienteDTO = null;
		Preguntapendiente preguntapendienteEntity = null;
		List<Preguntapendiente>listaPreguntaPendienteEntity = null;
		Query query;
		query = em.createNamedQuery(Preguntapendiente.FIND_BY_CONTACTO_ESTADO)
				.setParameter("estado", estado)
				.setParameter("contacto", contacto);
		try{
			listaPreguntaPendienteEntity = query.getResultList();
			if(!listaPreguntaPendienteEntity.isEmpty()){
				System.out.println("listaPreguntaPendienteEntity no esta vacia");
				for(int i = 0;i<listaPreguntaPendienteEntity.size();i++){
					preguntaPendienteDTO = new PreguntaPendienteDTO();
					preguntapendienteEntity = new Preguntapendiente();
					preguntapendienteEntity = listaPreguntaPendienteEntity.get(i);
					preguntaPendienteDTO.setId(preguntapendienteEntity.getId());
					preguntaPendienteDTO.setNumero(preguntapendienteEntity.getNumero());
					preguntaPendienteDTO.setContacto(preguntapendienteEntity.getContacto());
					preguntaPendienteDTO.setEstado(preguntapendienteEntity.getEstado());
					listaPreguntasPendientesDTO.add(preguntaPendienteDTO);
				}
			}else{
				System.out.println("No existe Pregunta pendiente");
				listaPreguntasPendientesDTO = null;
			}
		}catch(NoResultException e){
			System.out.println("No existe Pregunta pendiente");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	return listaPreguntasPendientesDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public PreguntaPendienteDTO getPreguntaPendienteByNumeroEstado(long numero,
			short estado) throws Exception {
		System.out.println("INI getPreguntaPendienteByNumeroEstado");
		PreguntaPendienteDTO preguntaPendienteDTO = new PreguntaPendienteDTO();
		Preguntapendiente preguntapendienteEntity = null;
		Query query;
		query = em.createNamedQuery(Preguntapendiente.FIND_BY_NUMERO_ESTADO)
				.setParameter("estado", estado)
				.setParameter("numero", numero);
		try{
			preguntapendienteEntity = (Preguntapendiente)query.getSingleResult();
			preguntaPendienteDTO.setId(preguntapendienteEntity.getId());
			preguntaPendienteDTO.setNumero(preguntapendienteEntity.getNumero());
			preguntaPendienteDTO.setContacto(preguntapendienteEntity.getContacto());
			preguntaPendienteDTO.setEstado(preguntapendienteEntity.getEstado());
		}catch(NoResultException e){
			System.out.println("No existe Pregunta pendiente");
			preguntaPendienteDTO = null;
		}catch(Exception e){
			e.printStackTrace();
			preguntaPendienteDTO = null;
		}
		return preguntaPendienteDTO;
	}
	
	
}
