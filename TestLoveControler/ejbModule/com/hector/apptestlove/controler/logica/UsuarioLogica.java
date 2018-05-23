package com.hector.apptestlove.controler.logica;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.dao.bean.remote.UsuarioServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;


@Stateless(name = "UsuarioLogica", mappedName = "UsuarioLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class UsuarioLogica {

	@EJB
	private UsuarioServiceBeanRemote usuarioServiceBeanRemote;
	
	public int loguearUsuario(UsuarioDTO usuario) throws Exception{
		System.out.println("INI Logica loguearUsuario");
		return usuarioServiceBeanRemote.buscarUsuario(usuario.getNom_user(), usuario.getPassword());
	}
	
    public int buscarUsuario(UsuarioDTO usuario) throws Exception{
    	System.out.println("INI Logica buscarUsuario");
	   return usuarioServiceBeanRemote.buscarUsuario(usuario.getNom_user());
	}
    
    public int registrarUsuario(UsuarioDTO usuario) throws Exception {
    	System.out.println("INI Logica registrarUsuario");
    	return usuarioServiceBeanRemote.registrarUsuario(usuario);
    }
    
    public String holaMundo() throws Exception {
    	System.out.println("INI Logica holaMundo");
    	return usuarioServiceBeanRemote.holaMundo();
    }
}
