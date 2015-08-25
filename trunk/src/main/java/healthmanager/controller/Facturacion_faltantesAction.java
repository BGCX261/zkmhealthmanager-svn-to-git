/*
 * admisionAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.AdmisionService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.interfaces.ISeleccionarComponente;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Facturacion_faltantesAction extends ZKWindow implements
		ISeleccionarComponente {

	private static Logger log = Logger
			.getLogger(Facturacion_faltantesAction.class);

	private AdmisionService admisionService;

	// Componentes //
	@View
	private Textbox tbxValue;
	@View
	private Listbox listboxResultado;
	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_final;
	@View
	private Checkbox chkFiltro_atendidas;
	@View
	private Label lbTotal_admisiones;

	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxAseguradora;

	@View
	private Textbox tbxInfoAseguradora;
	@View
	private Toolbarbutton btnLimpiarAseguradora;

	@View
	private Listbox lbxVia_ingreso;

	@View
	private Listbox listboxProcedimientos;

	private List<String> seleccionados = new ArrayList<String>();

	@Override
	public void init() {
		listarCombos();
		parametrizarBandboxAdministradora();
	}

	public void listarCombos() {
		Utilidades.listarElementoListboxOrdenado(lbxVia_ingreso, true,
				getServiceLocator());
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			// limpiarDatos();
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {

			String value = tbxValue.getValue().toUpperCase().trim();

			lbTotal_admisiones.setValue("");

			if (dtbxFecha_inicial.getValue() == null
					&& dtbxFecha_final.getValue() == null)
				throw new ValidacionRunTimeException(
						"Para realizar la busqueda debe ingresar alguna de las 2 fechas");

			if (lbxVia_ingreso.getSelectedItem().getValue().toString()
					.isEmpty())
				throw new ValidacionRunTimeException(
						"Para realizar la busqueda debe seleccionar la via de ingreso de las admisiones");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("paramTodo", "paramTodo");
			parameters.put("value", value);
			parameters.put("atendida", chkFiltro_atendidas.isChecked());
			// parameters.put("estado", "1");
			parameters.put("via_ingreso", lbxVia_ingreso.getSelectedItem()
					.getValue().toString());

			if (dtbxFecha_inicial.getValue() != null
					&& dtbxFecha_final.getValue() != null) {
				if (dtbxFecha_inicial.getValue().compareTo(
						dtbxFecha_final.getValue()) > 0) {
					throw new ValidacionRunTimeException(
							"La fecha inicial en la busqueda no puede ser mayor a la fecha final");
				}
				parameters.put("fecha_inicial_p", new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				parameters.put("fecha_final_p", new Timestamp(dtbxFecha_final
						.getValue().getTime()));
			} else if (dtbxFecha_inicial.getValue() != null) {
				parameters.put("fecha_inicial_p", new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
			} else if (dtbxFecha_final.getValue() != null) {
				parameters.put("fecha_final_p", new Timestamp(dtbxFecha_final
						.getValue().getTime()));
			}

			Administradora administradora = bandboxAseguradora
					.getRegistroSeleccionado();

			if (administradora != null) {
				parameters.put("codigo_administradora",
						administradora.getCodigo());
			}

			List<Admision> lista_datos = admisionService
					.listarResultados(parameters);
			listboxResultado.getItems().clear();

			for (Admision admision : lista_datos) {
				admision = admisionService.consultar(admision);
				listboxResultado.appendChild(crearFilas(admision, this));
			}

			lbTotal_admisiones.setValue(lista_datos.size() + "");

			listboxResultado.setVisible(true);
			// listboxResultado.setMold("paging");
			// listboxResultado.setPageSize(20);

			listboxResultado.applyProperties();
			listboxResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente)
			throws Exception {
		Listitem fila = new Listitem();

		final Admision admision = (Admision) objeto;

		Hbox hbox = new Hbox();

		Administradora admin = admision.getAdministradora();
		String administradora = "";
		if (admin != null) {
			administradora = admin.getNombre();
		}

		Paciente paciente = admision.getPaciente();
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

		fila.appendChild(new Listcell());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		fila.appendChild(new Listcell(dateFormat.format(admision
				.getFecha_ingreso()) + ""));
		fila.appendChild(new Listcell(admision.getNro_ingreso() + ""));
		fila.appendChild(new Listcell(admision.getNro_identificacion() + ""));
		fila.appendChild(new Listcell(nombre_paciente));

		Elemento elemento = admision.getElemento_via_ingreso();

		fila.appendChild(new Listcell(elemento != null ? elemento.getDescripcion() : "(NO ENCONTRADO)")); 
		fila.appendChild(new Listcell(administradora));
		fila.appendChild(new Listcell(admision.getEstado() + ""));
		fila.appendChild(new Listcell(admision.getAtendida() ? "SI" : "NO"));

		Listcell listcell = new Listcell();
		listcell.appendChild(hbox);

		fila.appendChild(listcell);

		fila.setValue(admision);

		return fila;
	}

	public void eliminarDatos(Object obj) throws Exception {
		Admision admision = (Admision) obj;
		try {
			admision.setDelete_user(getUsuarios().getCodigo()); 
			int result = admisionService.eliminar(admision);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	public void generarReporteSeleccionadas() {
		try {
			log.debug("ejecutando @generarReporteSeleccionadas()");
			List<Admision> listado_admisiones = new ArrayList<Admision>();

			Collection<Listitem> collection_items = listboxResultado
					.getSelectedItems();
			if (collection_items.isEmpty()) {
				collection_items = listboxResultado.getItems();
			}

			for (Listitem listitem : collection_items) {
				Admision admision = (Admision) listitem.getValue();
				listado_admisiones.add(admision);
			}

			if (!listado_admisiones.isEmpty()) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("nombre_reporte", "LISTADO DE ADMISIONES");
				parametros.put("formato", "pdf");
				parametros.put("listado_admisiones", listado_admisiones);
				parametros.put("name_report", "Listado_admisiones");

				Component componente = Executions.createComponents(
						"/pages/printInformes.zul", this, parametros);
				final Window window = (Window) componente;
				window.setMode("modal");
				window.setMaximizable(true);
				window.setMaximized(true);
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void generarFacturacionSeleccionadas() {
		try {
			Set<Listitem> listado_seleccionadas = listboxResultado
					.getSelectedItems();
			if (!listado_seleccionadas.isEmpty()) {
				if (!listboxProcedimientos.getItems().isEmpty()) {
					List<Map<String, Object>> listado_cups = new ArrayList<Map<String, Object>>();

					for (Listitem listitem : listboxProcedimientos.getItems()) {
						listado_cups.add((Map<String, Object>) listitem
								.getValue());
					}

					List<Admision> listado_admisiones = new ArrayList<Admision>();
					for (Listitem listitem : listado_seleccionadas) {
						Admision admision = (Admision) listitem.getValue();
						listado_admisiones.add(admision);
					}

					Map<String, Object> mapa_datos = new HashMap<String, Object>();
					mapa_datos.put("listado_admisiones", listado_admisiones);
					mapa_datos.put("listado_cups", listado_cups);

					admisionService.guardarFacturacionFaltante(mapa_datos);

					MensajesUtil.mensajeInformacion("Informacion",
							"Facturas generadas satisfactoriamente !!!");
				} else {
					throw new ValidacionRunTimeException(
							"Debe ingresar los procedimientos a verificar");
				}
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void parametrizarBandboxAdministradora() {
		bandboxAseguradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		bandboxAseguradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});
	}

	public void openPcd() throws Exception {
		String pages = "/pages/openProcedimientos.zul";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("restringido", "");
		parametros.put("anio", anio + "");
		parametros.put("ocultar_filtro_procedimiento",
				"ocultar_filtro_procedimiento");
		parametros.put("seleccionados", seleccionados);

		// log.info("Paginas: " + pages);

		Component componente = Executions.createComponents(pages, null,
				parametros);
		final OpenProcedimientosAction ventana = (OpenProcedimientosAction) componente;
		ventana.setSeleccionar_componente(this);
		ventana.setWidth("990px");
		ventana.setHeight("400px");
		ventana.setClosable(true);
		ventana.setMaximizable(true);
		ventana.setTitle("CONSULTAR PROCEDIMIENTOS");
		ventana.setMode("modal");
	}

	@Override
	public void onSeleccionar(Map<String, Object> pcd) throws Exception {
		// log.info("@adicionarPcd Ejecutando evento");
		pcd.put("unidades", 1.0);
		final Long id_procedimiento = (Long) pcd.get("id_procedimiento");
		String codigo_cups = (String) pcd.get("codigo_cups");
		String nombre_procedimiento = (String) pcd.get("nombre_procedimiento");

		final Listitem listitem = new Listitem();
		listitem.setValue(pcd);
		listitem.appendChild(new Listcell(codigo_cups));

		Textbox textbox = new Textbox(nombre_procedimiento);
		textbox.setHflex("1");
		textbox.setReadonly(true);

		Listcell listcell = new Listcell();
		listcell.appendChild(textbox);
		listitem.appendChild(listcell);

		Toolbarbutton toolbarbutton = new Toolbarbutton("",
				"/images/borrar.gif");
		listcell = new Listcell();
		listcell.appendChild(toolbarbutton);
		listitem.appendChild(listcell);
		listboxProcedimientos.appendChild(listitem);

		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event arg0) throws Exception {
						listitem.detach();
						seleccionados.remove(id_procedimiento.toString());
					}
				});

		seleccionados.add(id_procedimiento.toString());
	}

}