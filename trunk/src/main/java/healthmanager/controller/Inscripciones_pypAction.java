/*
 * inscripciones_pypAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Inscripciones_pyp;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Inscripciones_pypService;
import healthmanager.modelo.service.Pacientes_contratosService;
import healthmanager.modelo.service.Servicios_contratadosService;
import com.framework.util.Util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Inscripciones_pypAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Inscripciones_pypAction.class);

	private Inscripciones_pypService inscripciones_pypService;

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Groupbox groupboxProgramas;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Rows rowsProgramasPyp;
	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxPaciente;
	@View
	private Toolbarbutton btnLimpiar_paciente;

	@View
	private Textbox tbxInfoPaciente;
	@View
	private Textbox tbxSexo;
	@View
	private Datebox dtbxFecha_ncto;
	@View
	private Textbox tbxEdad;
	@View
	private Textbox tbxEstado_civil;
	@View
	private Textbox tbxRaza;
	@View
	private Textbox tbxLugar_ncto;
	@View
	private Textbox tbxBarrio;
	@View
	private Textbox tbxDireccion;
	@View
	private Textbox tbxLocalidad;
	@View
	private Textbox tbxTelefonos;
	@View
	private Textbox tbxOcupacion;
	@View
	private Textbox tbxEducativo;

	private final String[] idsExcluyentes = {};

	private List<Elemento> tipos_inscripcion;
	private List<Inscripciones_pyp> inscripciones;
	private List<Servicios_contratados> servicios;

	@Override
	public void init() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("tipo", "tipo_inscripcion_pyp");
		tipos_inscripcion = getServiceLocator().getServicio(
				ElementoService.class).listar(params);
		listarCombos();
		parametrizarBandboxPaciente();
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo");
		listitem.setLabel("Codigo");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("id_codigo");
		listitem.setLabel("Id_codigo");
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
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		limpiarForm();
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
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			inscripciones_pypService.setLimit("limit 25 offset 0");

			List<Inscripciones_pyp> lista_datos = inscripciones_pypService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Inscripciones_pyp inscripciones_pyp : lista_datos) {
				rowsResultado.appendChild(crearFilas(inscripciones_pyp, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Inscripciones_pyp inscripciones_pyp = (Inscripciones_pyp) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(inscripciones_pyp);
			}
		});
		hbox.appendChild(img);
		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //
				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					guardarInscripciones();
					accionForm(true, "registrar");
				}
				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Inscripciones_pyp inscripciones_pyp = (Inscripciones_pyp) obj;
		try {
			bandboxPaciente.setValue(inscripciones_pyp.getNro_identificacion());
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void parametrizarBandboxPaciente() {
		bandboxPaciente.inicializar(null, btnLimpiar_paciente);
		BandboxRegistrosIMG<Paciente> bandboxRegistrosIMG = new BandboxRegistrosIMG<Paciente>() {

			@Override
			public void renderListitem(Listitem listitem, Paciente registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro
						.getTipo_identificacion()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNro_identificacion()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre1()
						+ (registro.getNombre2().isEmpty() ? "" : " "
								+ registro.getNombre2())
						+ " "
						+ (registro.getApellido1().isEmpty() ? "" : " "
								+ registro.getApellido1())
						+ (registro.getApellido2().isEmpty() ? "" : " "
								+ registro.getApellido2())));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Paciente> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {

				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				parametros.put("limite_paginado", "limit 25 offset 0");

				return Inscripciones_pypAction.this.getServiceLocator()
						.getPacienteService().listar(parametros);

			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Paciente paciente) {

				servicios = getServiciosPypPaciente(paciente);

				if (!servicios.isEmpty()) {
					groupboxProgramas.setVisible(true);
					bandboxPaciente.setValue(paciente.getNro_identificacion());
					tbxInfoPaciente.setValue(paciente.getNombreCompleto());
					tbxDireccion.setValue(paciente.getDireccion());
					String telefono_res = (paciente.getTel_res() != null
							&& !paciente.getTel_res().isEmpty() ? paciente
							.getTel_res() : "");
					String telefono_oficina = (paciente.getTel_oficina() != null
							&& !paciente.getTel_oficina().isEmpty() ? paciente
							.getTel_oficina() : "");

					tbxTelefonos.setValue(telefono_res + " - "
							+ telefono_oficina);
					dtbxFecha_ncto.setValue(paciente.getFecha_nacimiento());
					Departamentos departamentos = new Departamentos(
							paciente.getCodigo_dpto(), "");
					departamentos = getServiceLocator()
							.getDepartamentosService().consultar(departamentos);
					Municipios municipios = new Municipios(
							paciente.getCodigo_municipio(),
							paciente.getCodigo_dpto(), "");
					municipios = getServiceLocator().getMunicipiosService()
							.consultar(municipios);
					tbxLugar_ncto.setValue((municipios != null ? municipios
							.getNombre() : "")
							+ " - "
							+ (departamentos != null ? departamentos
									.getNombre() : ""));

					Pertenencia_etnica etnica = new Pertenencia_etnica();
					etnica.setId(paciente.getPertenencia_etnica());
					etnica = getServiceLocator().getPertenencia_etnicaService()
							.consultar(etnica);
					tbxRaza.setValue("(" + paciente.getPertenencia_etnica()
							+ ") " + (etnica != null ? etnica.getNombre() : ""));

					tbxEstado_civil.setValue(Utilidades.getNombreElemento(
							paciente.getEstado_civil(), "estado_civil",
							getServiceLocator()));

					Ocupaciones ocupaciones = new Ocupaciones();
					ocupaciones.setId(paciente.getCodigo_ocupacion());
					ocupaciones = getServiceLocator().getOcupacionesService()
							.consultar(ocupaciones);
					tbxOcupacion.setValue(ocupaciones != null ? ocupaciones
							.getNombre() : "");

					Nivel_educativo ne = new Nivel_educativo();
					ne.setId(paciente.getCodigo_educativo());
					ne = getServiceLocator().getNivel_educativoService()
							.consultar(ne);
					tbxEducativo.setValue(ne != null ? "(" + ne.getId() + ") "
							+ ne.getNombre() : "");
					tbxSexo.setValue(Utilidades.getNombreElemento(
							paciente.getSexo(), "sexo", getServiceLocator()));
					Map<String, Integer> mapa_edades = Util
							.getEdadYYYYMMDD(paciente.getFecha_nacimiento());

					Integer anios = mapa_edades.get("anios");
					Integer meses = mapa_edades.get("meses");
					Integer dias = mapa_edades.get("dias");

					if (anios.intValue() == 0 && meses.intValue() == 0) {
						tbxEdad.setValue(dias + (dias == 1 ? " día" : " días"));
					} else if (anios.intValue() == 0) {
						tbxEdad.setValue(meses
								+ (meses == 1 ? " mes (" : " meses (")
								+ (dias + (dias == 1 ? " día" : " días")) + ")");
					} else {
						int current_meses = meses.intValue()
								- (anios.intValue() * 12);
						tbxEdad.setValue(anios
								+ (anios == 1 ? " año" : " años")
								+ (current_meses != 0 ? (" y " + current_meses + (current_meses == 1 ? " mes "
										: " meses"))
										: ""));
					}

					// Barrio barrio = new Barrio();
					// barrio.setCodigo_barrio(paciente.getCod_bar());
					// barrio = getServiceLocator().getBarrioService()
					// .consultar(barrio);
					// tbxBarrio.setValue(barrio != null ? ("("
					// + barrio.getCodigo_barrio() + ") " + barrio.getBarrio())
					// : paciente.getCod_bar());
					//
					// if (barrio != null) {
					// Localidad localidad = new Localidad();
					// localidad.setCodigo_localidad(barrio.getCodigo_localidad());
					// localidad =
					// getServiceLocator().getLocalidadService().consultar(localidad);
					//
					// tbxLocalidad.setValue(localidad != null ? ("("
					// + localidad.getCodigo_localidad() + ") " + localidad
					// .getLocalidad()) : barrio.getCodigo_localidad());
					// }
					//
					// Centro_barrio centro_barrio = new Centro_barrio();
					// centro_barrio.setCodigo_barrio(paciente.getCod_bar());
					// centro_barrio =
					// getServiceLocator().getCentro_barrioService().consultar(centro_barrio);

					cargarInscripcionesPaciente(paciente, servicios);
				} else {
					bandbox.setValue("");
					limpiarForm();
					MensajesUtil.mensajeInformacion("Error",
							"El paciente no tiene programas PyP contratados.");
				}
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				bandbox.setValue("");
				limpiarForm();
			}
		};
		bandboxPaciente.setBandboxRegistrosIMG(bandboxRegistrosIMG);
	}

	private Row agregarRow(final Inscripciones_pyp inscripciones_pyp) {

		Row row = new Row();
//		Unidad_funcional unidad_funcional = cargarUnidad(inscripciones_pyp
//				.getId_codigo());

		Cell cell = new Cell();
		Label lbl = new Label("N/D");
		lbl.setId("lblFecha" + inscripciones_pyp.getId_codigo());
		cell.appendChild(lbl);
		row.appendChild(cell);

		cell = new Cell();
		lbl = new Label("N/D");
		lbl.setId("lblNro" + inscripciones_pyp.getId_codigo());
		cell.appendChild(lbl);
		row.appendChild(cell);

		cell = new Cell();
//		cell.appendChild(new Label(unidad_funcional.getNombre()));
		row.appendChild(cell);

		final Listbox lbx = new Listbox();
		lbx.setId("lbx" + inscripciones_pyp.getId_codigo());
		lbx.setMold("select");
		Utilidades.listarElementoListboxFromType(lbx, true, tipos_inscripcion,
				false);
		cell = new Cell();
		cell.appendChild(lbx);
		row.appendChild(cell);

		final Textbox text = new Textbox();
		text.setHflex("1");
		text.setId("txt" + inscripciones_pyp.getId_codigo());
		text.setReadonly(true);

		final Textbox text2 = new Textbox();
		text2.setHflex("1");
		text2.setId("txtpopup" + inscripciones_pyp.getId_codigo());
		text2.setReadonly(text.isReadonly());
		text2.setRows(3);

		final Popup popup = new Popup();
		popup.setId("popup" + inscripciones_pyp.getId_codigo());
		popup.setWidth("300px");
		popup.appendChild(text2);
		text.setPopup(popup);

		popup.addEventListener(Events.ON_OPEN, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				text2.setValue(text.getValue());
			}
		});

		text2.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				text.setValue(text2.getValue());
			}
		});

		cell = new Cell();
		cell.appendChild(text);
		cell.appendChild(popup);
		row.appendChild(cell);

		lbx.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Textbox txt = (Textbox) getFellow("txt"
						+ inscripciones_pyp.getId_codigo());
				Textbox txt2 = (Textbox) getFellow("txtpopup"
						+ inscripciones_pyp.getId_codigo());
				String s = (String) (lbx.getSelectedItem().getValue());
				txt.setReadonly(!s.equals("2"));
				txt2.setReadonly(!s.equals("2"));
			}
		});

		return row;
	}

//	private Unidad_funcional cargarUnidad(String id_servicio) {
//		Unidad_funcional unidad_funcional = new Unidad_funcional();
//		unidad_funcional.setCodigo(id_servicio);
//		return getServiceLocator().getServicio(Unidad_funcionalService.class)
//				.consultar(unidad_funcional);
//	}

	private void cargarInscripcionesPaciente(Paciente paciente,
			List<Servicios_contratados> servicios) {
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("codigo_empresa", paciente.getCodigo_empresa());
		param.put("codigo_sucursal", paciente.getCodigo_sucursal());
		param.put("nro_identificacion", paciente.getNro_identificacion());

		inscripciones = getServiceLocator().getServicio(
				Inscripciones_pypService.class).listar(param);

		if (inscripciones.isEmpty()) {
			inscripciones = new ArrayList<Inscripciones_pyp>();
		}

		if (inscripciones != null) {
			for (Inscripciones_pyp inscripciones_pyp : inscripciones) {
				if (inscripciones_pyp.getEstado().equals("1")) {
					rowsProgramasPyp.appendChild(agregarRow(inscripciones_pyp));

					Label lbl1 = (Label) this.getFellow("lblFecha"
							+ inscripciones_pyp.getId_codigo());
					Label lbl2 = (Label) this.getFellow("lblNro"
							+ inscripciones_pyp.getId_codigo());
					Listbox lbx = (Listbox) this.getFellow("lbx"
							+ inscripciones_pyp.getId_codigo());
					Textbox txt = (Textbox) this.getFellow("txt"
							+ inscripciones_pyp.getId_codigo());
					Textbox txt2 = (Textbox) this.getFellow("txtpopup"
							+ inscripciones_pyp.getId_codigo());

					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

					lbl1.setValue(inscripciones_pyp.getTipo().equals("1")
							&& inscripciones_pyp.getFecha_inscripcion() != null ? sdf
							.format(inscripciones_pyp.getFecha_inscripcion())
							: "N/D");
					lbl2.setValue(inscripciones_pyp.getCodigo() != null ? inscripciones_pyp
							.getCodigo() : "N/D");
					Utilidades.seleccionarListitem(lbx,
							inscripciones_pyp.getTipo());
					txt.setReadonly(!inscripciones_pyp.getTipo().equals("2"));
					txt2.setReadonly(!inscripciones_pyp.getTipo().equals("2"));

					if (inscripciones_pyp.getTipo().equals("2")) {
						txt.setText(inscripciones_pyp.getObservaciones());
					} else {
						txt.setText("");
					}
				}
			}
		}
	}

	private Inscripciones_pyp cargarDatosInscripcion(
			Inscripciones_pyp inscripcion) {
		if (inscripcion != null) {
			Listbox lbx = (Listbox) this.getFellow("lbx"
					+ inscripcion.getId_codigo());

			String tipo = (String) lbx.getSelectedItem().getValue();
			inscripcion.setTipo(tipo);
			Textbox txt = (Textbox) this.getFellow("txt"
					+ inscripcion.getId_codigo());
			if (tipo.equals("2")) {
				inscripcion.setObservaciones(txt.getValue());
			} else if (tipo.equals("1")) {
				inscripcion.setObservaciones("");
				inscripcion.setFecha_inscripcion(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
			}
		}
		return inscripcion;
	}

	private void guardarInscripciones() {
		for (Inscripciones_pyp inscripciones_pyp : inscripciones) {
			//log.info(">>>>>>>>>>>>>>>>>"
					/*+ (!inscripciones_pyp.getEstado().equals("0") && (!inscripciones_pyp
							.getTipo().equals("0") && inscripciones_pyp
							.getCodigo() == null)));*/
			if (!inscripciones_pyp.getEstado().equals("0")
					&& (!inscripciones_pyp.getTipo().equals("0") && inscripciones_pyp
							.getCodigo() == null)) {
				inscripciones_pyp = cargarDatosInscripcion(inscripciones_pyp);
				inscripciones_pypService.guardarDatos(inscripciones_pyp);
			}
		}
	}

	private void limpiarForm() {
		tbxInfoPaciente.setValue("");
		tbxSexo.setValue("");
		dtbxFecha_ncto.setValue(null);
		tbxEdad.setValue("");
		tbxEstado_civil.setValue("");
		tbxRaza.setValue("");
		tbxLugar_ncto.setValue("");
		tbxBarrio.setValue("");
		tbxDireccion.setValue("");
		tbxLocalidad.setValue("");
		tbxTelefonos.setValue("");
		tbxOcupacion.setValue("");
		tbxEducativo.setValue("");
		rowsProgramasPyp.getChildren().clear();
		inscripciones = null;
		servicios = null;
		groupboxProgramas.setVisible(false);
	}

	private List<Servicios_contratados> getServiciosPypPaciente(
			Paciente paciente) {
		List<Servicios_contratados> servicios_paciente = new ArrayList<Servicios_contratados>();
		List<Servicios_contratados> servicios = getServiciosDisponiblesParaPaciente(paciente);
		for (Servicios_contratados servicios_contratados : servicios) {
			if (ServiciosDisponiblesUtils.isServicioPyp(servicios_contratados)) {
				servicios_paciente.add(servicios_contratados);

			}
		}
		return servicios_paciente;
	}

	private List<Servicios_contratados> getServiciosDisponiblesParaPaciente(
			Paciente paciente) {
		List<Servicios_contratados> listado_servicios = new ArrayList<Servicios_contratados>();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("nro_identificacion", paciente.getNro_identificacion());
		params.put("codigo_empresa", paciente.getCodigo_empresa());
		params.put("codigo_sucursal", paciente.getCodigo_sucursal());
		List<Pacientes_contratos> listado_contratos = getServiceLocator()
				.getServicio(Pacientes_contratosService.class).listar(params);
		for (Pacientes_contratos contratos : listado_contratos) {
			params.put("id_contrato", contratos.getId_codigo());
			params.put("codigo_administradora",
					contratos.getCodigo_administradora());
			List<Servicios_contratados> listado_servicios_aux = getServiceLocator()
					.getServicio(Servicios_contratadosService.class).listar(
							params);
			listado_servicios.addAll(listado_servicios_aux);
		}
		return listado_servicios;
	}
}