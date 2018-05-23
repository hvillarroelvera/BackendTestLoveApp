package com.hector.apptestlove.controler.logica;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.remote.ContactoServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;


@Stateless(name = "ContactoLogica", mappedName = "ContactoLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class ContactoLogica {
	
	@EJB
	private ContactoServiceBeanRemote contactoServiceBean;
	
	@EJB
	private UsuarioLogica usuarioLogica;
	
	@EJB
	private GcmLogica gcmLogica;

	public int registrarContacto(UsuarioDTO usuario,ContactoDTO contacto) throws Exception{
		System.out.println("INI Logica registrarContacto");
		System.out.println("Logica registrarContacto nomUsuario["+usuario.getNom_user()+
				"],passUsuario["+usuario.getPassword()+"]");
	    if(usuarioLogica.buscarUsuario(usuario)
	    		== Constantes.SUCCESS){
	    	
			return contactoServiceBean.registrarContacto(usuario, contacto);
	    }
	  return Constantes.NOT_SUCCESS;
	}
	
	public ContactoDTO getContactoXUsuario(UsuarioDTO usuario)throws Exception{
		System.out.println("INI Logica getContactoXUsuario");
		return contactoServiceBean.getContactoXUsuario(usuario);
	}
	
	
	public void enviarSolicitud(String codGcm,String nom_user)throws Exception{
		System.out.println("INI Logica enviarSolicitud");
		
		String mensaje=nom_user;
		gcmLogica.sendGcmMessage(codGcm, mensaje,Constantes.TIPO_MENSAJE_GCM_SOLICITUD_CONTACTO);
	}
	
	public ContactoDTO getIdContactoXUsuario(UsuarioDTO usuario,ContactoDTO usuarioContacto) throws Exception{
		System.out.println("INI Logica getIdContactoXUsuario");
		return contactoServiceBean.getIdContactoXUsuario(usuario, usuarioContacto);
	}
	
	public ContactoDTO getUsuarioXContacto(UsuarioDTO usuarioContacto)throws Exception{
		System.out.println("INI Logica getUsuarioXContacto");
		return contactoServiceBean.getUsuarioXContacto(usuarioContacto);
	}
	
	public int eliminarContacto(ContactoDTO contactoDTO) throws Exception {
		System.out.println("INI Logica eliminarContacto");
		return contactoServiceBean.eliminarContacto(contactoDTO);
	}
	
	public int actualizarContacto(ContactoDTO contacto)throws Exception {
		System.out.println("INI Logica actualizarContacto");
		return contactoServiceBean.actualizarContacto(contacto);
	}
}
