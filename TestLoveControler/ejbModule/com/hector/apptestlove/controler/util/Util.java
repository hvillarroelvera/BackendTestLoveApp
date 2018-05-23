package com.hector.apptestlove.controler.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import com.hector.apptestlove.controler.constantes.Constantes;
import com.hector.apptestlove.controler.dto.GcmDTO;

public class Util {

	
	public String validarGcm(GcmDTO gcmContacto,GcmDTO gcmUsuario)
    {
       
        long expirationTime = gcmContacto.getExpirationTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String expirationDate = sdf.format(new Date(expirationTime));
        if (System.currentTimeMillis() > expirationTime)
        {
        	System.out.println("Registro GCM expirado.["+expirationDate+"]");
           
            return Constantes.EXPIRATE_TIME;
        }
        
        if(gcmContacto.getGcm_codGcm().equals(gcmUsuario.getGcm_codGcm())){
        	return Constantes.DEVICE_IS_EQUAL;
        }


        return gcmContacto.getGcm_codGcm();
    }
	
	public String validarGcm(GcmDTO gcmContacto)
    {
        long expirationTime = gcmContacto.getExpirationTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String expirationDate = sdf.format(new Date(expirationTime));
        if (System.currentTimeMillis() > expirationTime)
        {
        	System.out.println("Registro GCM expirado.["+expirationDate+"]");
           
            return Constantes.EXPIRATE_TIME;
        }
        
      
        return gcmContacto.getGcm_codGcm();
    }
	
	public static String getFechaHora(Calendar calendar){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
		calendar = GregorianCalendar.getInstance();
		return sdf.format(calendar.getTime());
	}
	
	public static Calendar getCalendar(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
		Calendar calendar = new GregorianCalendar();
		return calendar;
	}
	
	public static Calendar getCalendarFromString(String fecha){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");
		Date date = null;;
		try {
			date = sdf.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public static String getFechaHora(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss");	
		Calendar calendar = GregorianCalendar.getInstance();
		return sdf.format(calendar.getTime());
	}
}
