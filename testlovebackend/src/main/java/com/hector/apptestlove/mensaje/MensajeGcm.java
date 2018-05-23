package com.hector.apptestlove.mensaje;

import java.io.IOException;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.PreguntaDTO;



public class MensajeGcm {

	public void enviarSolicitud(String codGcm,String nom_user)throws Exception{
		System.out.println("INI Logica enviarSolicitud");
		
		String mensaje=nom_user;
		sendGcmMessage(codGcm, mensaje,Constantes.TIPO_MENSAJE_GCM_SOLICITUD_CONTACTO);
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
