package com.hector.apptestlove.controler.logica;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.remote.PreguntaServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.PuntuacionResultadoDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.util.Util;
import com.hector.apptestlove.mensaje.Message;
import com.hector.apptestlove.mensaje.Result;
import com.hector.apptestlove.mensaje.Sender;

@Stateless(name = "PreguntasLogica", mappedName = "PreguntasLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class PreguntasLogica {
	
@EJB
private PreguntaServiceBeanRemote preguntaServiceBeanRemote;

	public int getCountPreguntasXUsuario(UsuarioDTO usuario) throws Exception{
		System.out.println("INI Logica getCountPreguntasXUsuario");
		return preguntaServiceBeanRemote.getCountPreguntasXUsuario(usuario);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public long insertarPregunta(UsuarioDTO usuario,PreguntaDTO pregunta) throws Exception{
		System.out.println("INI Logica insertarPregunta");
		System.out.println("Logica insertarPregunta nomUser["+usuario.getNom_user()+"]");
		System.out.println("Logica insertarPregunta pregunta["+pregunta.getPregunta()+"]");
		
		PreguntasDTO preguntas=new PreguntasDTO();
		ArrayList<PreguntaDTO>listap=new ArrayList<PreguntaDTO>();
		PreguntaDTO preguntaUsuario=new PreguntaDTO();
		
		preguntas=getPreguntasXUsuario(usuario);
		if(preguntas != null){
			
			listap=preguntas.getPreguntas();
			
			for(int i=0;i<listap.size();i++){
				preguntaUsuario=listap.get(i);
				
				
				if(preguntaUsuario.getPregunta().equals(pregunta.getPregunta())){
					//Si ya existia pregunta se retorna 0
					System.out.println("Ya existe pregunta,no se almacena");
					return Constantes.NOT_SUCCESS;
				}
			}
		}
		
		//Si no existia pregunta se inserta
		return preguntaServiceBeanRemote.insertarPregunta(usuario, pregunta);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public PreguntasDTO getPreguntasXUsuario(UsuarioDTO usuario) throws Exception{
		System.out.println("INI Logica getPreguntasXUsuario");
		System.out.println("Logica getPreguntasXUsuario nomUser["+usuario.getNom_user()+"]");
		
		PreguntasDTO preguntas=new PreguntasDTO();
		PreguntaDTO pregunta=new PreguntaDTO();
		int cantidadP=0;
		preguntas = preguntaServiceBeanRemote.getPreguntasXUsuario(usuario);
		
		return preguntas;
	}
	
	public PreguntasDTO getPreguntasXUsuarioXNumero(UsuarioDTO usuario,PreguntasDTO preguntasNumero) throws Exception{
		System.out.println("INI Logica getPreguntasXUsuarioXNumero");
		return preguntaServiceBeanRemote.getPreguntasXUsuarioXNumero(usuario, preguntasNumero);
	}
	
	public int actualizarPregunta(PreguntaDTO pregunta) throws Exception{
		System.out.println("INI Logica actualizarPregunta");
		return preguntaServiceBeanRemote
				.actualizarPregunta(pregunta);
	}
	
	public int eliminarPregunta(PreguntaDTO pregunta) throws Exception{
		System.out.println("INI Logica eliminarPregunta");
		return preguntaServiceBeanRemote
				.eliminarPregunta(pregunta);
	}
	
	public PreguntaDTO getPreguntaByNumero(long numero) throws Exception {
		return preguntaServiceBeanRemote.getPreguntaByNumero(numero);
	}
	
	public void sendPreguntaGcmMessage(String codGcm,PreguntaDTO pregunta){
		System.out.println("INI Logica sendPreguntaGcmMessage");
	
		try {
            
            Sender sender = new Sender(Constantes.API_KEY);
            Message message = new Message.Builder()
                    .addData("msg",pregunta.getPregunta())
                    //.addData("other-parameter", "some value")
                    .build();
            Result result = sender.send(message,codGcm,1);
           
            
    		//System.out.println("statusCode="+statusCode);

            
        } catch (IOException ex) {
            ex.printStackTrace();
            
        }
	}
	
	

	
}
