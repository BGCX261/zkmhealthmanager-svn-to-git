package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.AdmisionService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.util.MensajesUtil;

public class Facturacion_rips_abiertasAction extends ZKWindow {

	private AdmisionService admisionService;

	@View
	private Textbox tbxBusqueda;

	@View
	private Datebox dtbxFecha_inicial;

	@View
	private Datebox dtbxFecha_final;

	@View
	private Listbox lbxVias_ingreso;

	@View
	private Toolbarbutton btnFiltro_ingreso;
	@View
	private Checkbox chkConfigFacturasCapitadas;
	@View
	private Checkbox chkConfigFacturasAgrupada;
	@View
	private Checkbox chkConfigFacturasEventos;

	@View
	private Listbox listboxAdmisiones;

	@View
	private Popup popupViasIngreso;

	private Component componente;

	@Override
	public void init() throws Exception {
		componente = getParent();
		cargarVias(lbxVias_ingreso);
	}

	private void cargarVias(Listbox listbox) {

		Listitem listitem;
		String tipo = listbox.getName();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo);
		parametros.put("orden_descripcion", "orden_descripcion");

		List<Elemento> elementos = getServiceLocator().getElementoService()
				.listar(parametros);

		for (Elemento elemento : elementos) {
			if (!elemento.getCodigo().equals("23")
					&& !elemento.getCodigo().equals("24")) {
				listitem = new Listitem();
				listitem.setValue(elemento.getCodigo());
				listitem.setLabel(elemento.getDescripcion());
				listitem.setSelected(true);
				listbox.appendChild(listitem);
			}
		}
	}

	public Listitem crearFilas(Object objeto, Component comp) throws Exception {
		final Listitem fila = new Listitem();

		final Admision admision = (Admision) objeto;
		Hbox hbox = new Hbox();

		Prestadores prestador = admision.getPrestadores();

		Paciente paciente = admision.getPaciente();
		Elemento elemento = admision.getElemento_via_ingreso();

		String nombre_paciente = "";
		if (paciente != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(paciente.getNombre1());
			sb.append(!paciente.getNombre2().isEmpty() ? " "
					+ paciente.getNombre2() : "");
			sb.append(!paciente.getApellido1().isEmpty() ? " "
					+ paciente.getApellido1() : "");
			sb.append(!paciente.getApellido2().isEmpty() ? " "
					+ paciente.getApellido2() : "");
			nombre_paciente = sb.toString().toUpperCase();
		}

		fila.appendChild(new Listcell(admision.getNro_ingreso()));
		fila.appendChild(new Listcell(admision.getNro_identificacion()));
		fila.appendChild(new Listcell(nombre_paciente));
		fila.appendChild(new Listcell((prestador != null ? prestador
				.getNombres() + " " + prestador.getApellidos() : "")));
		fila.appendChild(new Listcell(elemento != null ? elemento
				.getDescripcion() : admision.getVia_ingreso()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		fila.appendChild(new Listcell(dateFormat.format(admision
				.getFecha_ingreso()) + ""));

		String fact = "";
		if (admision.getEstado().equals("1")) {
			fact = "Activo";
		} else if (admision.getEstado().equals("2")) {
			fact = "Facturada";
		} else if (admision.getEstado().equals("3")) {
			fact = "Cancelada";
		}

		fila.appendChild(new Listcell(fact));

		Toolbarbutton toolbarbutton = new Toolbarbutton();
		toolbarbutton.setImage("/images/lock.gif");
		toolbarbutton.setTooltiptext("Cerrar admision");
		toolbarbutton.setStyle("cursor: pointer");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						if (componente instanceof Facturacion_ripsAction) {
							Admision admision_aux = getServiceLocator()
									.getAdmisionService().consultar(admision);
							((Facturacion_ripsAction) componente)
									.mostrarDatosAdmision(admision_aux);
							onClose();
						}
					}
				});
		hbox.appendChild(toolbarbutton);

		Listcell celda = new Listcell();
		celda.appendChild(hbox);
		fila.appendChild(celda);

		return fila;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			btnFiltro_ingreso.setImage("/images/filtro.png");
			String value = tbxBusqueda.getValue().toUpperCase().trim();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			parameters.put("paramTodo", "paramTodo");
			parameters.put("value", value);
			parameters.put("estado", "1");
			parameters.put("codigo_centro",
					centro_atencion_session.getCodigo_centro());

			if (value.isEmpty()) {
				if (dtbxFecha_inicial.getValue() != null
						&& dtbxFecha_final.getValue() != null) {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							"yyyy-MM-dd");
					String fecha1 = dateFormat.format(dtbxFecha_inicial
							.getValue());
					String fecha2 = dateFormat.format(dtbxFecha_final
							.getValue());

					if (dateFormat.parse(fecha1).getTime() > dateFormat.parse(
							fecha2).getTime()) {
						throw new ValidacionRunTimeException(
								"La fecha inicial en la busqueda no puede ser mayor a la fecha final");
					}
					parameters.put("fecha_inicial_p", new Timestamp(
							dtbxFecha_inicial.getValue().getTime()));
					parameters.put("fecha_final_p", new Timestamp(
							dtbxFecha_final.getValue().getTime()));
				} else if (dtbxFecha_inicial.getValue() != null) {
					parameters.put("fecha_inicial_p", new Timestamp(
							dtbxFecha_inicial.getValue().getTime()));
				} else if (dtbxFecha_final.getValue() != null) {
					parameters.put("fecha_final_p", new Timestamp(
							dtbxFecha_final.getValue().getTime()));
				}
			}

			if (lbxVias_ingreso.getSelectedItems().size() > 0) {
				List<String> listado_vias = new ArrayList<String>();
				for (Listitem listitem : lbxVias_ingreso.getSelectedItems()) {
					listado_vias.add((String) listitem.getValue());
				}
				btnFiltro_ingreso.setImage("/images/filtro1.png");
				parameters.put("vias_ingreso", listado_vias);
				parameters.put("limite_paginado", "limit 500 offset 0");

				List<Admision> listado = getServiceLocator()
						.getAdmisionService().listarResultados(parameters);
				listboxAdmisiones.getItems().clear();

				for (Admision admision : listado) {
					if (admisionService.verificarServicios(admision)) {
						listboxAdmisiones
								.appendChild(crearFilas(admision, this));
					}
				}
			} else {
				MensajesUtil
						.mensajeAlerta(
								"Seleccionar via de ingreso",
								"Para hacer la busqueda de admisiones abiertas debe seleccionar por lo menos una via de ingreso",
								new EventListener<Event>() {

									@Override
									public void onEvent(Event arg0)
											throws Exception {
										popupViasIngreso
												.open(btnFiltro_ingreso);
									}
								});
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	/**
	 * Este metodo me permite filtrar los tipos de facturas
	 * 
	 * @author Luis Miguel
	 * */
	public List<String> getFiltrosFacturacion() {
		List<String> list_in = new ArrayList<String>();
		if (chkConfigFacturasCapitadas.isChecked()) {
			list_in.add("CAP");
		}
		if (chkConfigFacturasAgrupada.isChecked()) {
			list_in.add("AGR");
		}
		if (chkConfigFacturasEventos.isChecked()) {
			list_in.add("IND");
		}
		if (list_in.isEmpty()) {
			list_in.add("NA");
		}
		return list_in;
	}

}
