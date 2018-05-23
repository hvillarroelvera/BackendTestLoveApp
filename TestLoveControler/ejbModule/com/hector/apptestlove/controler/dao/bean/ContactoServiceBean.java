package com.hector.apptestlove.controler.dao.bean;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.ContactoServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.ContactoServiceBeanRemote;
import com.hector.apptestlove.controler.dao.bean.remote.UsuarioServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
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
 * Session Bean implementation class ContactoServiceBean
 */
@Stateless(name = "ContactoServiceBean", mappedName = "ContactoServiceBean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class ContactoServiceBean implements ContactoServiceBeanRemote, ContactoServiceBeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
    
	@EJB
	private UsuarioServiceBeanRemote usuarioServiceBean;
	
    public ContactoServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ContactoDTO getContactoXUsuario(UsuarioDTO usuario) throws Exception {
		
		System.out.println("INI getContactoXUsuario");
		System.out.println("getContactoXUsuario nomUser["+usuario.getNom_user()+"]");
		ContactoDTO contactoDTO = new ContactoDTO();
		Contacto contacto;
		Query query;
		query = em.createNamedQuery(Contacto.FIND_BY_NOMUSER)
				.setParameter("nomUser", usuario.getNom_user());
		try{
			query.setFirstResult(0).setMaxResults(1).getSingleResult();
			contacto = (Contacto) query.getSingleResult();
			contactoDTO.setContacto(contacto.getContacto());
			contactoDTO.setId_contacto(contacto.getIdContacto());
			contactoDTO.setNomUser(contacto.getNomUser());
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existe Contacto");
			contactoDTO = null;
		}
		return contactoDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int registrarContacto(UsuarioDTO usuario, ContactoDTO contacto)
			throws Exception {
		System.out.println("INI registrarContacto");
		if(usuarioServiceBean.buscarUsuario(usuario.getNom_user()) == Constantes.SUCCESS){
			try{
				Contacto contactoEntity = new Contacto();
				contactoEntity.setNomUser(usuario.getNom_user());
				contactoEntity.setContacto(contacto.getContacto());
				em.persist(contactoEntity);
			}catch(Exception e){
				e.printStackTrace();
				return Constantes.NOT_SUCCESS;
			}
		}
		return Constantes.SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int actualizarContacto(ContactoDTO contacto)
			throws Exception {
		System.out.println("INI actualizarContacto");
		Contacto contactoEntity = new Contacto();
		Query query;
		query = em.createNamedQuery(Contacto.FIND_BY_NOMUSER)
				         .setParameter("nomUser", contacto.getNomUser());
		
		try{
			contactoEntity = (Contacto) query.getSingleResult();
		}catch(NoResultException noResultE){
			System.out.println("No existe contacto");
			noResultE.printStackTrace();
			return Constantes.NOT_EXIST;
		}
		
		if(contactoEntity != null){
			contactoEntity.setContacto(contacto.getContacto());
			return Constantes.SUCCESS;
		}
	  return Constantes.NOT_SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ContactoDTO getIdContactoXUsuario(UsuarioDTO usuario,
			ContactoDTO usuarioContacto) throws Exception {
		System.out.println("INI getIdContactoXUsuario");
		Contacto contactoEntity = new Contacto();
		ContactoDTO contactoDTO = new ContactoDTO();
		Query query;
		query = em.createNamedQuery(Contacto.FIND_BY_NOMUSER_AND_CONTACTO)
				.setParameter("nomUser", usuario.getNom_user())
				.setParameter("contacto", usuarioContacto.getContacto());
		
		try{
			contactoDTO.setId_contacto((long)query.getSingleResult());
		}catch(NoResultException noResultE){
			System.out.println("No existe Contacto");
			//noResultE.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return contactoDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ContactoDTO getUsuarioXContacto(UsuarioDTO usuarioContacto)
			throws Exception {
		System.out.println("INI getUsuarioXContacto");
		System.out.println("getUsuarioXContacto contacto["+usuarioContacto.getNom_user()+"]");
		Contacto contacto = new Contacto();
		ContactoDTO contactoDTO = new ContactoDTO();
	
		Query query;
		query = em.createNamedQuery(Contacto.FIND_BY_CONTACTO)
				.setParameter("contacto", usuarioContacto.getNom_user());
		try{
			query.setFirstResult(0).setMaxResults(1).getSingleResult();
			contacto = (Contacto) query.getSingleResult();
			contactoDTO.setId_contacto(contacto.getIdContacto());
			contactoDTO.setContacto(contacto.getContacto());
			contactoDTO.setNomUser(contacto.getNomUser());
			
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existe Usuario para el contacto");
			contactoDTO = null;
		}
		catch(Exception e){
			e.printStackTrace();
			contactoDTO = null;
		}
		return contactoDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int eliminarContacto(ContactoDTO contactoDTO) throws Exception {
		System.out.println("INI eliminarContacto");
		Contacto contactoEntity = null;
		try{
			contactoEntity = em.find(Contacto.class,contactoDTO.getId_contacto());
			if(contactoEntity != null){
				em.remove(contactoEntity);
			}else{
				return Constantes.NOT_EXIST;

			}
			
		}catch(NoResultException e){
			System.out.println("No existe contacto");
			e.printStackTrace();
			return Constantes.NOT_EXIST;
			}catch(Exception e){
				e.printStackTrace();
				return Constantes.NOT_SUCCESS;
			}
		return Constantes.SUCCESS;
	}

}
