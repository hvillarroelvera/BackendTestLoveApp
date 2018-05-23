package com.hector.apptestlove.controler.logica;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.dao.bean.remote.PreguntaPendienteServiceBeanRemote;
import com.hector.apptestlove.controler.dto.ContactoDTO;
import com.hector.apptestlove.controler.dto.GcmDTO;
import com.hector.apptestlove.controler.dto.PreguntaDTO;
import com.hector.apptestlove.controler.dto.PreguntaPendienteDTO;
import com.hector.apptestlove.controler.dto.PreguntasDTO;
import com.hector.apptestlove.controler.dto.UsuarioDTO;
import com.hector.apptestlove.controler.util.Util;

@Stateless(name = "PreguntaPendienteLogica", mappedName = "PreguntaPendienteLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class PreguntaPendienteLogica {
	
	@EJB
	private PreguntaPendienteServiceBeanRemote preguntaPendienteServiceBeanRemote;
	

	public int insertarPreguntaPendiente (UsuarioDTO usuario,PreguntaDTO pregunta,short estado) throws Exception{
		System.out.println("INI Logica insertarPreguntaPendiente");
		return preguntaPendienteServiceBeanRemote
				.insertarPreguntaPendiente(usuario, pregunta,estado);
	}
	
	
	public int eliminarPreguntaPendiente(PreguntaPendienteDTO preguntaPendienteDTO) throws Exception{
		System.out.println("INI Logica eliminarPreguntaPendiente");
		return preguntaPendienteServiceBeanRemote.eliminarPreguntaPendiente(preguntaPendienteDTO);
	}
	
	public int actualizarEstadoPreguntaPendiente(long numero,short estado) throws Exception{
		System.out.println("INI Logica actualizarEstadoPreguntaPendiente");
		return preguntaPendienteServiceBeanRemote.actualizarEstadoPreguntaPendiente(numero, estado);
	}
	
	public ArrayList<PreguntaPendienteDTO> getPreguntaPendienteByContactoEstado(String contacto,short estado)
			throws Exception {
		return preguntaPendienteServiceBeanRemote.getPreguntaPendienteByContactoEstado(contacto,estado);
	}
	
	public PreguntaPendienteDTO getPreguntaPendienteByNumeroEstado(long numero,
			short estado) throws Exception {
		return preguntaPendienteServiceBeanRemote.getPreguntaPendienteByNumeroEstado(numero, estado);
	}
	
	/*
	public void enviarPreguntaPendiente(UsuarioDTO usuario) throws Exception{
		System.out.println("INI Logica enviarPreguntaPendiente");
		
		PreguntasDTO preguntas=new PreguntasDTO();
		ContactoDTO contacto=new ContactoDTO();
		GcmDTO gcm=new GcmDTO();
		Util util=new Util(); 
		String codGcm="";
		
		
		contacto.setContacto(usuario.getNom_user());
		
		preguntas=this.getPreguntasPendientes(usuario, contacto);
		
		if(preguntas.getPreguntas().size() > 0){
			
			gcm=gcmLogica.getGcmXUsuarioLogica(usuario);
			
			codGcm=util.validarGcm(gcm);
			
			
			if(codGcm.equals("4")){
				
				System.out.println("El codigo gcm ha expirado");
			
			}else if(codGcm.equals("")){
				System.out.println("El codigo gcm no existe");
			}else{
				
				
				for(int i=0;i<preguntas.getPreguntas().size();i++){
					PreguntaDTO pregunta=new PreguntaDTO();
					pregunta=preguntas.getPreguntas().get(i);
					
					preguntasLogica.sendPreguntaGcmMessage(codGcm, pregunta);
					
				}
				
			}
			
		}else{
			System.out.println("No existen preguntas");
		}
		
	}*/
	
}
