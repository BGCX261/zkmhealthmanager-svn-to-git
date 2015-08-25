package healthmanager.controller;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.util.MensajesUtil;

public class ComunicadoAction extends ZKWindow {

	@View private Textbox tbxComunicado;
	@View private Toolbarbutton tbnEnviar;
	
	@Override
	public void init() throws Exception {

	}

	public void onEnviarComunicado(){
		if(!tbxComunicado.getValue().trim().isEmpty()){
			EventQueue<Event> eq = EventQueues.lookup("interactive",
					EventQueues.APPLICATION, false);
			if (eq != null) {
				eq.publish(new Event(IConstantes.PARAM_EVENTQUEUE_COMUNICADO, tbnEnviar,
						tbxComunicado.getValue()));
				MensajesUtil.mensajeInformacion("Informacion", "Mensaje enviado");  
			}else{
				MensajesUtil.mensajeAlerta("Advertencia",
						"No ha iniciado ningún usuario");
			}
		}else{
			MensajesUtil.mensajeAlerta("Advertencia",
					"Mensaje no puede estar vacio");
		}
	}
}
