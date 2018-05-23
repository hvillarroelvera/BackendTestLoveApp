package com.hector.apptestlove.controler.logica;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;


@Stateless(name = "FacadeLogicaServiceBean",  mappedName = "FacadeLogicaServiceBean")
public class FacadeLogicaServiceBean{

	@EJB
	private UsuarioLogica usuarioLogica;
	
	@EJB
	private GcmLogica gcmLogica;
	
	@EJB
	private ContactoLogica contactoLogica;
	
	@EJB
	private PreguntaPendienteLogica preguntaPendienteLogica;
	
	@EJB
	private PreguntasLogica preguntaLogica;
	
	@EJB
	private PuntuacionLogica puntuacionLogica;
	
	@EJB
	private SolicitudLogica solicitudLogica;
	
	@EJB
	private EstadoSessionLogica estadoSessionLogica;
	
	@EJB
	private EstadoSolicitudLogica estadoSolicitudLogica;
	
	@EJB
	private SessionLogica sessionLogica;
	
	@EJB
	private RespuestaPendienteLogica respuestaPendienteLogica;
	
	@EJB
	private PuntuacionPendienteLogica puntuacionPendienteLogica;
    
	
    public PuntuacionPendienteLogica getPuntuacionPendienteLogica() {
		return puntuacionPendienteLogica;
	}



	public EstadoSessionLogica getEstadoSessionLogica() {
		return estadoSessionLogica;
	}



	public EstadoSolicitudLogica getEstadoSolicitudLogica() {
		return estadoSolicitudLogica;
	}


	public SessionLogica getSessionLogica() {
		return sessionLogica;
	}


	public void setSessionLogica(SessionLogica sessionLogica) {
		this.sessionLogica = sessionLogica;
	}


	public FacadeLogicaServiceBean() {
        // TODO Auto-generated constructor stub
    }

	
	public UsuarioLogica getUsuarioLogica() {
		
		return usuarioLogica;
	}

	
	public GcmLogica getGcmLogica() {
		return gcmLogica;
	}

	
	public ContactoLogica getcontactoLogica() {
		// TODO Auto-generated method stub
		return contactoLogica;
	}

	
	public PreguntaPendienteLogica getpreguntaPendienteLogica() {
		// TODO Auto-generated method stub
		return preguntaPendienteLogica;
	}

	
	public PreguntasLogica getpreguntaLogica() {
		// TODO Auto-generated method stub
		return preguntaLogica;
	}

	
	public PuntuacionLogica getpuntuacionLogica() {
		// TODO Auto-generated method stub
		return puntuacionLogica;
	}

	
	public SolicitudLogica getsolicitudLogica() {
		// TODO Auto-generated method stub
		return solicitudLogica;
	}

	public RespuestaPendienteLogica getRespuestaPendiente(){
		return respuestaPendienteLogica;
	}

}
