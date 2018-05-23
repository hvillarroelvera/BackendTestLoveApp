package com.hector.apptestlove.controler.dao.bean;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.UsuarioServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.UsuarioServiceBeanRemote;
import com.hector.apptestlove.controler.dto.UsuarioDTO;


import com.hector.apptestlove.model.entity.Usuario;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagementType;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Session Bean implementation class UsuarioServiceBean
 */
@Stateless(name = "UsuarioServiceBean", mappedName = "UsuarioServiceBean")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class UsuarioServiceBean implements UsuarioServiceBeanLocal,UsuarioServiceBeanRemote {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
    public UsuarioServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String holaMundo() {
		// TODO Auto-generated method stub
		return "Hola mundoooooooooooooo";
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int registrarUsuario(UsuarioDTO usuario) throws Exception {
		System.out.println("INI registrarUsuario");
	Usuario usuarioEntity = new Usuario();
		usuarioEntity.setNomUser(usuario.getNom_user());
		usuarioEntity.setPassword(usuario.getPassword());
		try{
		em.persist(usuarioEntity);
		}catch(Exception e){
			e.printStackTrace();
			return Constantes.NOT_SUCCESS;
		}
		return Constantes.SUCCESS;
	}

	/*Este metodo reemplaza a loguearUsuario*/
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int buscarUsuario(String nomUsuario, String password) throws Exception {
		System.out.println("INI buscarUsuario");
		Query query;
		query = em.createNamedQuery(Usuario.FIND_USUARIO)
				.setParameter("nomUsuario", nomUsuario)
				.setParameter("passUsuario", password);
		try{
			query.setFirstResult(0).setMaxResults(1).getSingleResult();
			return Constantes.SUCCESS;
			
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existe Usuario");
			return Constantes.NOT_SUCCESS;
		}
		
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int buscarUsuario(String nomUsuario) throws Exception {
		System.out.println("INI buscarUsuario");
		Query query;
		query = em.createNamedQuery(Usuario.FIND_USUARIO_X_NOMUSER)
				.setParameter("nomUsuario", nomUsuario);
		try{
			query.setFirstResult(0).setMaxResults(1).getSingleResult();
			return Constantes.SUCCESS;
			
		}catch(NoResultException e){
			//e.printStackTrace();
			System.out.println("No existe Usuario");
			return Constantes.NOT_SUCCESS;
		}
	}

	

}
