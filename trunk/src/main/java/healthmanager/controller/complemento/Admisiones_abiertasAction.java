package healthmanager.controller.complemento;

import healthmanager.controller.Admision_nuevaAction;
import healthmanager.controller.Facturacion_ripsAction;
import healthmanager.controller.GeneralComposer;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.ElementoService;

import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Toolbarbutton;

import com.framework.util.Utilidades;

public class Admisiones_abiertasAction extends GeneralComposer {
	
	@WireVariable
	private ElementoService elementoService;
	
	public enum Modos {
		GENERAR_FACTURA, ADMISION
	}

	@View
	private Listbox listboxAdmisiones;

	private Component parent;
	private Paciente paciente;
	private List<Admision> listado_admisiones;
	private Modos modos;

	@Override
	public void init() throws Exception {
		parent = this.getParent();
		mostrarListado();
	}

	@Override
	public void params(Map<String, Object> map) {
		listado_admisiones = (List<Admision>) map.get("listado_admisiones");
		paciente = (Paciente) map.get("paciente");
		modos = (Modos) map.get("modos");
	}

	public void mostrarListado() {
		listboxAdmisiones.getItems().clear();
		for (final Admision admision : listado_admisiones) {
			Listitem listitem = new Listitem();
			listitem.appendChild(new Listcell(admision.getNro_ingreso()));
			listitem.appendChild(new Listcell(formatFecha.format(admision
					.getFecha_ingreso())));
			listitem.appendChild(new Listcell(Utilidades.getNombreElemento(
					admision.getVia_ingreso(), "via_ingreso", elementoService)));
			Listcell listcell = new Listcell();
			listcell.setStyle("text-align:center");
			Toolbarbutton toolbarbutton = new Toolbarbutton("",
					"/images/si_seleccionado.png");
			if (modos == Modos.GENERAR_FACTURA) {
				toolbarbutton.setVisible(true);
			} else {
				toolbarbutton.setVisible(false);
			}
			toolbarbutton.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event arg0) throws Exception {
							if (parent instanceof Facturacion_ripsAction) {
								((Facturacion_ripsAction) parent)
										.cargarDatosAdmision(admision,
												paciente, false);
								((Facturacion_ripsAction) parent)
										.refrescarInformacion(false);
								Admisiones_abiertasAction.this.detach();
							}

						}
					});
			listcell.appendChild(toolbarbutton);
			listitem.appendChild(listcell);
			listboxAdmisiones.appendChild(listitem);
		}
	}

	public void onClickNuevaAdmision() throws Exception {
		if (modos == Modos.GENERAR_FACTURA) {
			((Facturacion_ripsAction) parent).cargarDatosAdmision(null,
					paciente, false);
		} else if (modos == Modos.ADMISION) {
			((Admision_nuevaAction) parent).seleccionarPacienteAux(paciente);
		}
		this.detach();
	}

	@Override
	public void _despuesIniciar() {

	}

}
