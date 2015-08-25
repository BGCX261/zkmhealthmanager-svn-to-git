package healthmanager.controller.complemento;

import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class VentanaCancelacion extends Window {
	private static final long serialVersionUID = -8673350097872609741L;
	private Textbox textbox;
	private Button button;
	private Label label_mensaje;

	private Button button_cancelar;

	public VentanaCancelacion(String titulo, Page pagina) {
		this.setVisible(true);
		this.setHeight("170px");
		this.setWidth("500px");
		this.setBorder("normal");
		this.setTitle(titulo);

		Groupbox groupbox = new Groupbox();
		groupbox.setMold("3d");
		groupbox.appendChild(new Caption("Motivo de cancelacion"));
		groupbox.setClosable(false);
		groupbox.setHeight("160px");

		textbox = new Textbox();
		textbox.setRows(3);
		textbox.setHflex("1");

		Hlayout hlayout = new Hlayout();
		label_mensaje = new Label();
		hlayout.appendChild(label_mensaje);
		groupbox.appendChild(hlayout);

		hlayout = new Hlayout();
		hlayout.appendChild(textbox);
		groupbox.appendChild(hlayout);

		hlayout = new Hlayout();
		hlayout.setStyle("float:right");
		button = new Button();
		button.setMold("trendy");
		button.setLabel("Aceptar");

		hlayout.appendChild(button);

		button_cancelar = new Button();
		button_cancelar.setMold("trendy");
		button_cancelar.setLabel("Cancelar");
		hlayout.appendChild(button_cancelar);

		button_cancelar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						VentanaCancelacion.this.onClose();
					}
				});

		groupbox.appendChild(hlayout);

		this.appendChild(groupbox);
		this.setPage(pagina);

	}

	public String getValor() {
		return textbox.getValue();
	}

	public void setMensaje(String mensaje) {
		label_mensaje.setValue(mensaje);
	}

	public void setEvento(EventListener<? extends Event> listener) {
		button.addEventListener(Events.ON_CLICK, listener);
	}

}
