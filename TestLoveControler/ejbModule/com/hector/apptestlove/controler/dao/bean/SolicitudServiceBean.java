package com.hector.apptestlove.controler.dao.bean;

import java.util.ArrayList;
import java.util.List;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.local.SolicitudServiceBeanLocal;
import com.hector.apptestlove.controler.dao.bean.remote.SolicitudServiceBeanRemote;
import com.hector.apptestlove.controler.dao.bean.remote.UsuarioServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.SolicitudDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.model.entity.Solicitud;
import com.hector.apptestlove.model.entity.SolicitudPK;

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
 * Session Bean implementation class Solicitud
 */
@Stateless(mappedName = "Solicitud")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class SolicitudServiceBean implements SolicitudServiceBeanRemote, SolicitudServiceBeanLocal {

	@PersistenceContext(unitName=Constantes.PERSISTENCE_UNIT_NAME)
	private EntityManager em;
	
	@EJB
	private UsuarioServiceBeanRemote usuarioServiceBeanRemote;
    /**
     * Default constructor. 
     */
    public SolicitudServiceBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int insertarSolicitudContacto(UsuarioDTO usuario,
			UsuarioDTO usuarioContacto,short estado, short tipo) throws Exception {
		System.out.println("INI insertarSolicitudContacto");
		Solicitud solicitudEntity = new Solicitud();
		SolicitudPK SolicitudPK = new SolicitudPK();
		if(usuarioServiceBeanRemote.buscarUsuario(usuario.getNom_user()) == Constantes.SUCCESS){
			SolicitudPK.setUserEmisor(usuario.getNom_user());
			SolicitudPK.setUserReceptor(usuarioContacto.getNom_user());
			solicitudEntity.setEstado(estado);
			solicitudEntity.setTipo(tipo);
			solicitudEntity.setId(SolicitudPK);
			
			try{
				em.persist(solicitudEntity);
				}catch(Exception e){
					e.printStackTrace();
					return Constantes.NOT_SUCCESS;
				}
		}
		return Constantes.SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int eliminarSolicitudContactoPendiente(UsuarioDTO usuario,
			UsuarioDTO usuarioContacto) throws Exception {
		System.out.println("INI eliminarSolicitudContactoPendiente");
		Solicitud solicitudEntity;
		Query query;
		try{
			query = em.createNamedQuery(Solicitud.SELECT_X_EMISOR_RECEPTOR).setParameter("emisor", usuario.getNom_user())
			.setParameter("receptor", usuarioContacto.getNom_user());
			solicitudEntity = (Solicitud) query.getSingleResult();
			em.remove(solicitudEntity);
		}catch(NoResultException e){
			System.out.println("No existe Solicitud");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			return Constantes.NOT_SUCCESS;
		}
		
		return Constantes.SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeSolicitudContacto(UsuarioDTO usuario,
			ContactoDTO contacto) throws Exception {
		System.out.println("INI existeSolicitudContacto");
		long countSolicitud = 0;
		
		try{
		countSolicitud = (long)em.createNamedQuery(Solicitud.COUNT)
				.setParameter("emisor", usuario.getNom_user())
				.setParameter("receptor", contacto.getContacto())
				.setParameter("estado", Constantes.ESTADO_SOLICITUD_ENVIADA)
				.setParameter("tipo", Constantes.TIPO_SOLICITUD_SOL_CONTACTO)
				.getSingleResult();
		System.out.println("Cantidad solicitudes ["+countSolicitud+"]");
		}catch(NoResultException e){
			System.out.println("No existe Solicitud");
			//e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if( countSolicitud == Constantes.SUCCESS){
			return true;
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public int actualizarSolicitud(long numero, short estado) throws Exception {
		System.out.println("INI actualizarSolicitud");
		Solicitud solicitud = new Solicitud();
		Query query;
		query = em.createNamedQuery(Solicitud.FIND_BY_NUMERO).setParameter("numero", numero);
		try{
		solicitud = (Solicitud) query.getSingleResult();
		}catch(NoResultException e){
			System.out.println("No existe Solicitud");
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		if(solicitud != null){
			/*Se supone que aca se realiza el update automaticamente*/
			solicitud.setEstado(estado);
			/*Se supone que aca se realiza el update automaticamente*/
			return Constantes.SUCCESS;
		}
		return Constantes.NOT_SUCCESS;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SolicitudDTO getSolicitudContacto(UsuarioDTO usuario)
			throws Exception {
		System.out.println("INI getSolicitudContacto");
		Query query;
		Solicitud solicitudEntiy = new Solicitud();
		SolicitudDTO solicitudDTO = new SolicitudDTO();
		try{
			query = em.createNamedQuery(Solicitud.FIND_BY_EMISOR_ENVIADA)
				.setParameter("emisor", usuario.getNom_user())
				.setParameter("estado", Constantes.ESTADO_SOLICITUD_ENVIADA);
			solicitudEntiy = (Solicitud) query.getSingleResult();
			
			solicitudDTO.setNumero(solicitudEntiy.getId().getNumero());
			solicitudDTO.setUserEmisor(solicitudEntiy.getId().getUserEmisor());
			solicitudDTO.setUserReceptor(solicitudEntiy.getId().getUserReceptor());
			solicitudDTO.setEstado(solicitudEntiy.getEstado());
			solicitudDTO.setTipo(solicitudEntiy.getTipo());
		}catch(NoResultException e){
			System.out.println("No existe Solicitud");
			solicitudDTO = null;
			//e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return solicitudDTO;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public ArrayList<SolicitudDTO> getAllSolicitudesByReceptorEstadoTipo(
			SolicitudDTO solicitudDTO) throws Exception {
		System.out.println("INI getAllSolicitudesByReceptorEstadoTipo");
		Solicitud solicitudEntity;
		List<Solicitud>listaEntitySolicitudes;
		ArrayList<SolicitudDTO>listaSolicitudesDTO = new ArrayList<SolicitudDTO>();
		SolicitudDTO solicitudDTO2;
		Query query;
		try{
			query = em.createNamedQuery(Solicitud.FIND_ALL_BY_RECEPTOR_ESTADO_TIPO)
					.setParameter("receptor", solicitudDTO.getUserReceptor())
					.setParameter("estado", solicitudDTO.getEstado())
					.setParameter("tipo", solicitudDTO.getTipo());
			listaEntitySolicitudes = query.getResultList();
			
			if(!listaEntitySolicitudes.isEmpty()){
				for(int i=0;i<listaEntitySolicitudes.size();i++){
					solicitudEntity = new Solicitud();
					solicitudDTO2 = new SolicitudDTO();
					solicitudEntity = listaEntitySolicitudes.get(i);
					solicitudDTO2.setNumero(solicitudEntity.getId().getNumero());
					solicitudDTO2.setUserEmisor(solicitudEntity.getId().getUserEmisor());
					solicitudDTO2.setUserReceptor(solicitudEntity.getId().getUserReceptor());
					solicitudDTO2.setTipo(solicitudEntity.getTipo());
					solicitudDTO2.setEstado(solicitudEntity.getEstado());
					listaSolicitudesDTO.add(solicitudDTO2);
				}
			}else{
				System.out.println("No existe Solicitud");
				listaSolicitudesDTO = null;
			}
			
				
		}catch(NoResultException e){
			System.out.println("No existe Solicitud");
			listaSolicitudesDTO = null;
			//e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			listaSolicitudesDTO = null;
		}
		return listaSolicitudesDTO;
		}

	@Override
	public ArrayList<SolicitudDTO> getAllSolicitudesByEmisorEstadoTipo(
			SolicitudDTO solicitudDTO) throws Exception {
		System.out.println("INI getAllSolicitudesByEmisorEstadoTipo");
		Solicitud solicitudEntity;
		List<Solicitud>listaEntitySolicitudes;
		ArrayList<SolicitudDTO>listaSolicitudesDTO = new ArrayList<SolicitudDTO>();
		SolicitudDTO solicitudDTO2;
		Query query;
		try{
			query = em.createNamedQuery(Solicitud.FIND_ALL_BY_EMISOR_ESTADO_TIPO)
					.setParameter("emisor", solicitudDTO.getUserEmisor())
					.setParameter("estado", solicitudDTO.getEstado())
					.setParameter("tipo", solicitudDTO.getTipo());
			listaEntitySolicitudes = query.getResultList();
			
			if(!listaEntitySolicitudes.isEmpty()){
				for(int i=0;i<listaEntitySolicitudes.size();i++){
					solicitudEntity = new Solicitud();
					solicitudDTO2 = new SolicitudDTO();
					solicitudEntity = listaEntitySolicitudes.get(i);
					solicitudDTO2.setNumero(solicitudEntity.getId().getNumero());
					solicitudDTO2.setUserEmisor(solicitudEntity.getId().getUserEmisor());
					solicitudDTO2.setUserReceptor(solicitudEntity.getId().getUserReceptor());
					solicitudDTO2.setTipo(solicitudEntity.getTipo());
					solicitudDTO2.setEstado(solicitudEntity.getEstado());
					listaSolicitudesDTO.add(solicitudDTO2);
				}
			}else{
				System.out.println("No existe Solicitud");
				listaSolicitudesDTO = null;
			}
			
				
		}catch(NoResultException e){
			System.out.println("No existe Solicitud");
			listaSolicitudesDTO = null;
			//e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
			listaSolicitudesDTO = null;
		}
		return listaSolicitudesDTO;
	}
	
	
	

}
