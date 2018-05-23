package com.hector.apptestlove.controler.logica;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dao.bean.remote.PuntuacionServiceBeanRemote;
import com.hector.apptestlove.controler.dto.PuntuacionDTO;
import com.hector.apptestlove.controler.dto.PuntuacionResultadoDTO;

@Stateless(name = "PuntuacionLogica", mappedName = "PuntuacionLogica")
@TransactionManagement(value=TransactionManagementType.CONTAINER)
public class PuntuacionLogica {

	@EJB
	private PuntuacionServiceBeanRemote puntuacionServiceBeanRemote;
	
	public int buscarPuntuacion(long id_contacto) throws Exception{
		
		System.out.println("INI Logica buscarPuntuacion");
		return puntuacionServiceBeanRemote.existePuntuacion(id_contacto);
	}
	
	public int insertarPuntuacion (long id_contacto,PuntuacionResultadoDTO puntuacion) throws Exception{
		System.out.println("INI Logica insertarPuntuacion");
		return puntuacionServiceBeanRemote.insertarPuntuacion(id_contacto, puntuacion);
	}
	
	public int getPuntos(long id_contacto) throws Exception{
		System.out.println("INI Logica getPuntos");
		return puntuacionServiceBeanRemote.getPuntos(id_contacto);
	}
	
	public int actualizarPuntuacion (long id_contacto,PuntuacionResultadoDTO puntuacion) throws Exception{
		System.out.println("INI Logica actualizarPuntuacion");
		return puntuacionServiceBeanRemote.actualizarPuntuacion(id_contacto, puntuacion);
	}
	
	public int eliminarPuntuacion(PuntuacionDTO puntuacionDTO) throws Exception {
		System.out.println("INI Logica eliminarPuntuacion");
		return puntuacionServiceBeanRemote.eliminarPuntuacion(puntuacionDTO);
	}
	
   public int puntuacion(long id_contacto,PuntuacionResultadoDTO resultado) throws Exception{
	   System.out.println("INI Logica puntuacion");
	   
		int existePuntuacion=0;
		int respuesta=0;
		int puntosAnterior=0;
		
		
		existePuntuacion=buscarPuntuacion(id_contacto);
		
		if(existePuntuacion == Constantes.SUCCESS){
			
			puntosAnterior=getPuntos(id_contacto);
			puntosAnterior+=calcularPuntos(resultado.getResultado());
			resultado.setPuntos(puntosAnterior);
			respuesta=actualizarPuntuacion(id_contacto,resultado);
		}else{
			resultado.setPuntos(calcularPuntos(resultado.getResultado()));
			respuesta=insertarPuntuacion(id_contacto,resultado);
		}
		
	   return respuesta;
	}
	
	public int calcularPuntos(String respuesta){
		System.out.println("INI Logica calcularPuntos");
		
		int puntos=0;
		
		if(respuesta.equals(Constantes.PUNTUACION_RESPUESTA_CORRECTA)){
			
			puntos+=Constantes.PUNTUACION_RESPUESTA_PUNTOS;
		}else{
			puntos-=Constantes.PUNTUACION_RESPUESTA_PUNTOS;
		}
		
		return puntos;
	}
}
