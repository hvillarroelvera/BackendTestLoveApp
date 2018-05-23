package com.hector.apptestlove.controler.logica;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.remote.ContactoServiceBeanRemote;
import com.hector.apptestlove.controler.dao.bean.remote.GcmServiceBeanRemote;
import com.hector.apptestlove.controler.dao.bean.remote.UsuarioServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.util.Util;
import com.hector.apptestlove.mensaje.Message;
import com.hector.apptestlove.mensaje.Result;
import com.hector.apptestlove.mensaje.Sender;


@Stateless(name = "GcmLogica", mappedName = "GcmLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class GcmLogica {
	
	@EJB
	private GcmServiceBeanRemote gcmServiceBeanRemote;
	
	@EJB
	private UsuarioServiceBeanRemote usuarioServiceBeanRemote;
	
	@EJB
	private ContactoServiceBeanRemote contactoServiceBeanRemote;
	

	public int registrarGcmLogica(GcmDTO gcm,String nom_user) throws Exception{
		System.out.println("INI Logica registrarGcmLogica");
		return gcmServiceBeanRemote.registrarGcm(gcm, nom_user);
	}
	
	public GcmDTO getGcmXUsuarioLogica(UsuarioDTO usuario) throws Exception{
		System.out.println("INI Logica getGcmXUsuarioLogica");
		return gcmServiceBeanRemote.getGcmXUsuario(usuario);
	}
	
	public int actualizarGcm(GcmDTO gcm,String nom_user) throws Exception{
		System.out.println("INI Logica actualizarGcm");
		return gcmServiceBeanRemote.actualizarGcm(gcm, nom_user);
	}
	
	
	public String validacionesGcm(UsuarioDTO usuario,UsuarioDTO usuarioContacto,String tipo) throws Exception{
		System.out.println("INI Logica validacionesGcm");
		

		
		UsuarioDTO u=new UsuarioDTO();
		UsuarioDTO uContacto=new UsuarioDTO();
		ContactoDTO c=new ContactoDTO();
		GcmDTO gcmContacto=new GcmDTO();
		GcmDTO gcmUsuario=new GcmDTO();
		Util util=new Util();
		String codGcm="";
		
		//Se obtiene gcm del usuario
		gcmUsuario=getGcmXUsuarioLogica(usuario);
		System.out.println("Gcm usuario "+gcmUsuario.getGcm_codGcm());
		
		if(tipo.equals(Constantes.TIPO_MENSAJE_GCM_PREGUNTA)){
			
		//Se obtiene contacto
		c=contactoServiceBeanRemote.getContactoXUsuario(usuario);
		uContacto.setNom_user(c.getContacto());
		
		//Se busca codigo gcm del contacto
		gcmContacto=getGcmXUsuarioLogica(uContacto);
		System.out.println("Gcm contacto "+gcmContacto.getGcm_codGcm());
		
		//Se validan los datos gcm del contacto
		codGcm=util.validarGcm(gcmContacto,gcmUsuario);
		
		}else if(tipo.equals(Constantes.TIPO_MENSAJE_GCM_RESPUESTA_PREGUNTA)){
			System.out.println("Entro a IF Respuesta Pregunta");
			
			c = contactoServiceBeanRemote.getUsuarioXContacto(usuario);
			System.out.println("Contacto recuperado "+c.getNomUser());
			uContacto.setNom_user(c.getNomUser());
			gcmContacto=getGcmXUsuarioLogica(uContacto);
			
			System.out.println("Gcm contacto "+gcmContacto.getGcm_codGcm());
		
			//Se validan los datos gcm del contacto
			codGcm=util.validarGcm(gcmContacto,gcmUsuario);
			System.out.println("Salio de IF Respuesta Pregunta");
		}else if(tipo.equals(Constantes.TIPO_MENSAJE_GCM_SOLICITUD_CONTACTO)){
			
			//Se busca codigo gcm del contacto
			gcmContacto=getGcmXUsuarioLogica(usuarioContacto);
			System.out.println("Gcm contacto "+gcmContacto.getGcm_codGcm());
			
			//Se validan los datos gcm
			codGcm=util.validarGcm(gcmContacto,gcmUsuario);
		}else if(tipo.equals(Constantes.TIPO_MENSAJE_GCM_PUNTUACION)){
			
		//Se obtiene contacto
		//c=contactoL.getContactoXUsuario(usuario);
		//uContacto.setNom_user(c.getContacto());
		
		//Se busca codigo gcm del contacto
		gcmContacto=getGcmXUsuarioLogica(usuarioContacto);
		System.out.println("Gcm contacto "+gcmContacto.getGcm_codGcm());
		
		//Se validan los datos gcm del contacto
		codGcm=util.validarGcm(gcmContacto,gcmUsuario);
		
		}
		System.out.println("Gcm validado "+codGcm);
		
		return codGcm;
	}
	
	public void sendGcmMessage(String codGcm,String mensaje,String tipo){
		System.out.println("INI Logica sendGcmMessage");
		try {
			
			System.out.println("********sendGcmMessage*********,tipo="+tipo);
            Sender sender = new Sender(Constantes.API_KEY);
            System.out.println("sendGcmMessage Carga APIKEY");
            Message message = new Message.Builder()
                    .addData(tipo,mensaje)
                    //.addData("other-parameter", "some value")
                    .build();
            System.out.println("sendGcmMessage Construye mensaje");
            Result result = sender.send(message,codGcm,1);
            System.out.println("sendGcmMessage Envia mensaje");
            
    		System.out.println("Data enviada="+message.toString());

            
        } catch (IOException ex) {
            ex.printStackTrace();
            
        }
	}
	
	public int respuestaByCodGcm(String gcm){
		 if(gcm.equals("4")){
				
				System.out.println("El codigo gcm ha expirado");
				return Constantes.NOT_SUCCESS;
			
			}else if(gcm.equals("5")){
						System.out.println("El dispositivo emisor es el mismo del receptor,se enviara" +
						"solicitud cuando el contacto inicie sesion");
						return Constantes.NOT_SUCCESS;			
			}else if(gcm.equals("")){
				
				System.out.println("El codigo gcm no existe");
				return Constantes.NOT_EXIST;
				
			}else{
				return Constantes.SUCCESS;
			}
	}
}
