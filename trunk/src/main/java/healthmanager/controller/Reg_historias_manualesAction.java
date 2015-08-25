/*
 * ficha_epidemiologia_n11Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Reg_historias_manuales;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.PacienteService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.util.Parametros_busqueda;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Reg_historias_manualesAction extends ZKWindow {

	public static Logger log = Logger
			.getLogger(Reg_historias_manualesAction.class);

	@View
	private Listbox lbxVias_ingreso;
	@View
	private Toolbarbutton btnLimpiarPaciente;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_identificacion;
	@View
	private Textbox tbxNomPaciente;
	@View
	private Textbox tbxMotivo;
	@View
	private Textbox tbxResumen;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Grid gridResultado;
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Rows rowsResultado;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Textbox tbxAccion;
	@View
	private Toolbarbutton btGuardar;

	@View
	private Datebox dtbxFecha_ingreso1;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private ElementoService elementoService;

	private final String[] idsExcluyentes = { "btCancel", "btGuardar",
			"tbxAccion", "btNew", "dtbxFecha_ingreso",
			"lbxVia_ingreso_defecto", };

	private Long id_current;

	@Override
	public void init() throws Exception {

		listarCombos();
		parametrizarBandbox();
		accionForm(true, "registrar");

	}

	public void listarCombos() {
		listarParameter();
		cargarVias(lbxVias_ingreso);

	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);

		tbxNro_identificacion.setDisabled(false);
		btnLimpiarPaciente.setVisible(true);
		tbxNro_identificacion.setReadonly(false);
		tbxNro_identificacion.setFocus(true);
		tbxNro_identificacion.limpiarSeleccion(true);

		// btFurips.setVisible(false);
		btGuardar.setDisabled(false);
		id_current = null;
	}

	/**
	 * En este metodo se parametriza todos los bandbox
	 *
	 */
	private void parametrizarBandbox() {
		parametrizarBandboxPaciente();
	}

	private void parametrizarBandboxPaciente() {
		tbxNro_identificacion.setTrabajarConParametros(true);
		tbxNro_identificacion.inicializar(tbxNomPaciente, btnLimpiarPaciente);
		tbxNro_identificacion.setMostrarVacio(true);
		tbxNro_identificacion
				.setMensajeVacio("Este paciente no se encuentra en la base de datos..!!");
		tbxNro_identificacion
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado", "limit 25 offset 0");

						return pacienteService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {
						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro
								.getNombreCompleto());

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						// /lbxVia_ingreso.setDisabled(true);
					}
				});

		tbxNro_identificacion
				.setSeleccionarItem(new BandboxRegistrosIMG.ISeleccionarItem() {
					public void accion(Object registro) {
					}
				});
		List<Parametros_busqueda> listado_parametros = new ArrayList<Parametros_busqueda>();
		listado_parametros.add(new Parametros_busqueda("Documento aux",
				"documento"));
		listado_parametros.add(new Parametros_busqueda("Nro identificacion",
				"nro_identificacion"));
		listado_parametros.add(new Parametros_busqueda("Primer nombre",
				"nombre1"));
		listado_parametros.add(new Parametros_busqueda("Segundo nombre",
				"nombre2"));
		listado_parametros.add(new Parametros_busqueda("Primer apellido",
				"apellido1"));
		listado_parametros.add(new Parametros_busqueda("Segundo apellido",
				"apellido2"));
		tbxNro_identificacion.listarParametros(listado_parametros, true);
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			// String value = "nro_identificacion";

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			List<Reg_historias_manuales> lista_datos = getServiceLocator()
					.getServicio(GeneralExtraService.class).listar(
							Reg_historias_manuales.class, parameters);

			rowsResultado.getChildren().clear();

			for (Reg_historias_manuales reg_historias_manuales : lista_datos) {
				rowsResultado.appendChild(crearFilas(reg_historias_manuales,
						this));

			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

			// resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();
		final Reg_historias_manuales reg_historias_manuales = (Reg_historias_manuales) objeto;
		Reg_historias_manuales reg_historias_manuale = new Reg_historias_manuales();

		// consultamos paciente//

		Paciente p = new Paciente();
		p.setNro_identificacion(reg_historias_manuales.getNro_identificacion());
		p.setCodigo_empresa(codigo_empresa);
		p.setCodigo_sucursal(codigo_sucursal);
		p = getServiceLocator().getPacienteService().consultar(p);

		// consulta tabla elementos para extraer descripcion via ingreso//

		String viaI = "via_ingreso";
		Elemento elemento = new Elemento();
		elemento.setCodigo(reg_historias_manuales.getVia_ingreso());
		elemento.setTipo(viaI);
		elemento = getServiceLocator().getElementoService().consultar(elemento);

		reg_historias_manuales
				.setNro_identificacion(reg_historias_manuales != null ? reg_historias_manuales
						.getNro_identificacion() : "");
		reg_historias_manuales.setNro_identificacion(p != null ? p
				.getNro_identificacion() : "");
		reg_historias_manuale = getServiceLocator().getServicio(
				GeneralExtraService.class).consultar(reg_historias_manuale);

		Hbox hbox = new Hbox();
		Space space = new Space();
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(reg_historias_manuales
				.getNro_identificacion() + ""));
		fila.appendChild(new Label(p.getNombreCompleto() + ""));
		fila.appendChild(new Label(elemento.getDescripcion() + ""));
		fila.appendChild(new Label(reg_historias_manuales.getFecha_historia()
				+ ""));
		fila.appendChild(new Label(reg_historias_manuales.getMotivo() + ""));
		fila.appendChild(new Label(reg_historias_manuales.getResumen_historia()
				+ ""));
		// fila.appendChild(new Label(tipo));
		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(reg_historias_manuales);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(reg_historias_manuales);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);
		fila.appendChild(hbox);
		return fila;
	}

	public void eliminarDatos(Object obj) throws Exception {
		Reg_historias_manuales reg_historias_manuales = (Reg_historias_manuales) obj;
		try {
			int result = getServiceLocator().getServicio(
					GeneralExtraService.class).eliminar(reg_historias_manuales);
			if (result == 0) {
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (Exception e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		}
	}

	// Metodo para validar campos del formulario //

	public void cargarVias(Listbox listbox) {
		Listitem listitem;
		String tipo = listbox.getName();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo);

		List<Elemento> elementos = elementoService.listar(parametros);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);

		}
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {

				// Cargamos los componentes //
				Reg_historias_manuales reg_historias_manuales = new Reg_historias_manuales();
				reg_historias_manuales.setId(id_current);
				reg_historias_manuales.setCodigo_centro(centro_atencion_session
						.getCodigo_centro());

				reg_historias_manuales.setCodigo_empresa(empresa
						.getCodigo_empresa());
				reg_historias_manuales.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				reg_historias_manuales
						.setNro_identificacion(tbxNro_identificacion.getValue());
				reg_historias_manuales.setMotivo(tbxMotivo.getValue());
				reg_historias_manuales.setResumen_historia(tbxResumen
						.getValue());
				reg_historias_manuales.setVia_ingreso(lbxVias_ingreso
						.getSelectedItem().getValue().toString());
				reg_historias_manuales.setFecha_historia(new Timestamp(
						dtbxFecha_ingreso1.getValue().getTime()));
				reg_historias_manuales.setCreacion_user(usuarios.getCodigo()
						.toString());
				reg_historias_manuales.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));

				if (tbxAccion.getValue().equalsIgnoreCase("registrar")) {
					getServiceLocator().getServicio(GeneralExtraService.class)
							.crear(reg_historias_manuales);
				} else {
					int result = getServiceLocator().getServicio(
							GeneralExtraService.class).actualizar(
							reg_historias_manuales);
					if (result == 0) {
						throw new ValidacionRunTimeException(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}
				tbxAccion.setValue("modificar");
				id_current = reg_historias_manuales.getId();

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	public boolean validarForm() throws Exception {

		tbxNro_identificacion
				.setStyle("text-transform:uppercase;background-color:white");
		tbxResumen.setStyle("text-transform:uppercase;background-color:white");
		tbxMotivo.setStyle("background-color:white");
		lbxVias_ingreso.setStyle("background-color:white");

		boolean valida = true;

		if (tbxNro_identificacion.getText().trim().equals("")) {

			tbxNro_identificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (tbxMotivo.getText().trim().equals("")) {
			tbxMotivo
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxResumen.getText().trim().equals("")) {
			tbxResumen
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (dtbxFecha_ingreso1.getText().trim().equals("")) {
			dtbxFecha_ingreso1
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (lbxVias_ingreso.getSelectedItem() == null) {
			lbxVias_ingreso.setStyle("background-color:#F6BBBE");
			valida = false;
		} else if (lbxVias_ingreso.getSelectedItem().getValue().equals("")) {
			lbxVias_ingreso.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
	}

	public void mostrarDatos(Object obj) throws Exception {
		Reg_historias_manuales reg_historias_manuales = (Reg_historias_manuales) obj;
		try {
			tbxNro_identificacion
					.setStyle("text-transform:uppercase;background-color:white");
			tbxResumen
					.setStyle("text-transform:uppercase;background-color:white");
			tbxMotivo.setStyle("background-color:white");
			lbxVias_ingreso.setStyle("background-color:white");

			Long id = reg_historias_manuales.getId();
			this.id_current = id;
			Paciente p = new Paciente();
			p.setNro_identificacion(reg_historias_manuales
					.getNro_identificacion());
			p.setCodigo_empresa(codigo_empresa);
			p.setCodigo_sucursal(codigo_sucursal);
			p = getServiceLocator().getPacienteService().consultar(p);
			tbxNomPaciente.setValue(p.getNombreCompleto());
			tbxNro_identificacion.setValue(reg_historias_manuales
					.getNro_identificacion());
			dtbxFecha_ingreso1.setValue(reg_historias_manuales
					.getFecha_historia());
			tbxMotivo.setValue(reg_historias_manuales.getMotivo());
			tbxResumen.setValue(reg_historias_manuales.getResumen_historia());
			// lbxVias_ingreso.setValue(reg_historias_manuales.getFecha_historia());

			Utilidades.seleccionarListitem(lbxVias_ingreso,
					reg_historias_manuales.getVia_ingreso());

			tbxNro_identificacion.setDisabled(true);
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
}