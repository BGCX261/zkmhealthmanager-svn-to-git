/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paquetes_servicios;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Paquetes_serviciosService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.framework.interfaces.ISeleccionarComponente;
import com.framework.util.MensajesUtil;

/**
 * @author ferney
 * 
 */
public class OpenPaquetesServiciosAction extends ZKWindow {

	private static Logger log = Logger
			.getLogger(OpenPaquetesServiciosAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@View
	private Textbox tbxValue;
	@View
	private Listbox listboxResultado;

	private List<Paquetes_servicios> lista_paquetes;
	private Map<String, Object> parametrosAction;

	private ISeleccionarComponente seleccionar_componente;

	private Map<String, String> seleccionados = new HashMap<String, String>();

	@Override
	public void init() {
		listboxResultado.setAttribute("org.zkoss.zul.listbox.rod", true);
		try {
			initOpenProcedimiento();
		} catch (Exception e) {

		}
	}

	@SuppressWarnings("unchecked")
	public void initOpenProcedimiento() throws Exception {
		try {
			listboxResultado.setAttribute("org.zkoss.zul.listbox.rod", true);
			parametrosAction = (Map<String, Object>) Executions.getCurrent()
					.getArg();
			if (parametrosAction == null) {
				parametrosAction = new HashMap<String, Object>();
			}

			seleccionados.clear();
			if (parametrosAction.get("seleccionados") != null) {
				List<String> lista_seleccionado = (List<String>) parametrosAction
						.get("seleccionados");
				for (String seleccion : lista_seleccionado) {
					seleccionados.put(seleccion, seleccion);
				}
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String value = tbxValue.getValue().toUpperCase().trim();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paramTodo", "");
			parameters.put("value", value);

			getServiceLocator().getProcedimientosService().setLimit(
					"limit 25 offset 0");

			lista_paquetes = getServiceLocator().getServicio(
					Paquetes_serviciosService.class).listar(parameters);

			log.info("parametros de busqueda ===> " + parameters);

			listboxResultado.getItems().clear();

			int i = 0;
			for (Paquetes_servicios paquetes_servicios : lista_paquetes) {
				listboxResultado.appendChild(crearFilas(paquetes_servicios,
						this, i));
				i++;
			}

			listboxResultado.setMold("paging");
			listboxResultado.setPageSize(20);

			listboxResultado.applyProperties();
			listboxResultado.invalidate();
			listboxResultado.setVisible(true);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente, int index)
			throws Exception {
		final Listitem fila = new Listitem();

		final Paquetes_servicios paquetes_servicios = (Paquetes_servicios) objeto;

		final Map<String, Object> pcd = new HashMap<String, Object>();
		pcd.put("paquetes_servicios", paquetes_servicios);

		String nombre_paquete = "";

		Procedimientos procedimiento = new Procedimientos();
		procedimiento.setId_procedimiento(new Long(paquetes_servicios
				.getId_procedimiento_principal()));
		procedimiento = getServiceLocator().getProcedimientosService()
				.consultar(procedimiento);
		if (procedimiento != null) {
			nombre_paquete = procedimiento.getDescripcion();
		}

		Elemento elemento = new Elemento();
		elemento.setCodigo(paquetes_servicios.getVia_ingreso());
		elemento.setTipo("via_ingreso");
		elemento = getServiceLocator().getServicio(ElementoService.class)
				.consultar(elemento);

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Listcell(paquetes_servicios.getId() + ""));
		fila.appendChild(new Listcell(procedimiento != null ? procedimiento
				.getCodigo_cups() : ""));
		fila.appendChild(new Listcell(nombre_paquete));
		fila.appendChild(new Listcell(elemento != null ? elemento
				.getDescripcion() : paquetes_servicios.getVia_ingreso()));
		fila.appendChild(new Listcell(paquetes_servicios.getValor() + ""));

		fila.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if (!fila.isDisabled()) {
					if (getParent() instanceof Action) {
						((Action) getParent()).adicionarPcd(pcd);
					} else if (getParent() instanceof ZKWindow) {
						((ZKWindow) getParent()).adicionarPcd(pcd);
					}

					if (seleccionar_componente != null) {
						seleccionar_componente.onSeleccionar(pcd);
					}
					fila.setDisabled(true);
					fila.setTooltiptext("Este procedimiento ya se encuentra seleccionado");
				}
			}
		});

		if (seleccionados.containsKey(paquetes_servicios.getId() + "")) {
			fila.setDisabled(true);
			fila.setSelected(true);
			fila.setTooltiptext("Este procedimiento ya se encuentra seleccionado");
		}

		return fila;
	}

	public ISeleccionarComponente getSeleccionar_componente() {
		return seleccionar_componente;
	}

	/*
	 * ADVERTENCIA: Este metodo se esta utilizando en reflexion asi que a la
	 * hora de cambiar el nombre, cambiar en donde se esta llamando
	 * 
	 * Metodo *
	 * com.framework.macros.manuales_tarifarios.ManualesTarifariosMacro.openPcd
	 */
	public void setSeleccionar_componente(
			ISeleccionarComponente seleccionar_componente) {
		this.seleccionar_componente = seleccionar_componente;
	}

}
