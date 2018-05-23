package com.hector.apptestlove.controler.constantes;

public  class Constantes {

	public final static String PERSISTENCE_UNIT_NAME = "MySqlDS";
	public static final int SUCCESS = 1;
	public static final int NOT_SUCCESS = 0;
	public static final int NOT_EXIST = 2;
	
	public static final short SESSION_STARTED = 1;
	public static final short SESSION_FINALIZED = 0;
	
	public static final String API_KEY="AIzaSyCZ3_AMP6PVeqJoADoHgrEkH1ytmd43WKs";
	
	public static final String EXPIRATE_TIME = "4";
	public static final String DEVICE_IS_EQUAL = "5";
	
	public static final String TIPO_MENSAJE_GCM_SOLICITUD_CONTACTO = "solicitudContacto";
	public static final String TIPO_MENSAJE_GCM_PREGUNTA = "pregunta";
	public static final String TIPO_MENSAJE_GCM_RESPUESTA_PREGUNTA = "respuestaPregunta";
	public static final String TIPO_MENSAJE_GCM_RESULTADO_SOLICITUD_CONTACTO = "ResulSolContacto";
    public static final String TIPO_MENSAJE_GCM_PUNTUACION = "puntuacion";

	
	
	public static final short TIPO_SOLICITUD_SOL_CONTACTO = 1;
	public static final short TIPO_SOLICITUD_SOL_CONTACTO_PENDIENTE = 2;
	
	public static final short ESTADO_SOLICITUD_ENVIADA = 1;
	public static final short ESTADO_SOLICITUD_ACEPTADA= 2;
	public static final short ESTADO_SOLICITUD_PENDIENTE= 3;
	public static final short ESTADO_SOLICITUD_ACEPTADA_PENDIENTE= 4;
	public static final short ESTADO_SOLICITUD_ACEPTADA_RECIBIDA= 5;
	public static final short ESTADO_SOLICITUD_RECHAZADA= 6;
	public static final short ESTADO_SOLICITUD_RECHAZADA_PENDIENTE= 7;
	public static final short ESTADO_SOLICITUD_RECHAZADA_RECIBIDA= 8;


	public static final short ESTADO_PREGUNTA_PENDIENTE = 1;
	public static final short ESTADO_PREGUNTA_ENVIADA= 2;
	public static final short ESTADO_PREGUNTA_CONFIRMADA= 3;
	
	public static final short ESTADO_RESPUESTA_PENDIENTE = 1;
	public static final short ESTADO_RESPUESTA_ENVIADA= 2;
	public static final short ESTADO_RESPUESTA_CONFIRMADA= 3;
	
	public static final short ESTADO_PUNTUACION_PENDIENTE = 1;
	public static final short ESTADO_PUNTUACION_ENVIADA= 2;
	public static final short ESTADO_PUNTUACION_CONFIRMADA= 3;
	
	
	public static final String PUNTUACION_RESPUESTA_CORRECTA= "1";
    public static final String PUNTUACION_RESPUESTA_INCORRECTA = "0";
    
    public static final int PUNTUACION_RESPUESTA_PUNTOS = 5;
    
	

}
