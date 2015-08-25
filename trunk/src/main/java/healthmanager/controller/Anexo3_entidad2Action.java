/*
 * anexo3_entidadAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Detalle_anexo3;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.Anexo3_entidadService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.Detalle_anexo3Service;
import healthmanager.modelo.service.Hisc_urgenciaService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.MunicipiosService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Anexo3_entidad2Action extends ZKWindow {

	private static Logger log = Logger.getLogger(Anexo3_entidad2Action.class);

	private Anexo3_entidadService anexo3_entidadService;

	// Componentes //

	@View
	private Div divModuloOrdenamiento;

	private Orden_servicio_internoAction orden_servicioAction;

	// ------------agregados-----------------
	@View
	private Textbox tbxDpto;
	@View
	private Textbox tbxMun;
	@View
	private Textbox tbxCodigo_paciente_a3;
	@View
	private Textbox tbxNombre_reporta;
	@View
	private Textbox tbxNro_identificacion_reporta;
	@View
	private Textbox tbxServicio;

	// @View(isMacro = true)
	// private BandboxRegistrosMacro tbxCodigo_medico;
	// @View
	// private Textbox tbxNomPrestador;
	// @View
	// private Toolbarbutton btnLimpiarPrestador;
	@View
	private Textbox tbxNomPaciente;

	// private Toolbarbutton btnLimpiarPaciente;
	@View
	private Textbox tbxDirPac;
	@View
	private Textbox tbxFecNac;
	@View
	private Textbox tbxTelPac;
	@View
	private Textbox tbxEmailPac;

	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	private Prestadores prestador;
	// private Paciente paciente;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Textbox tbxCodigo;
	@View
	private Textbox tbxNumero_solicitud;
	@View
	private Datebox dtbxFecha;

//	private Textbox tbxCodigo_administradora;
	@View
	private Radiogroup rdbCobertura;
	@View
	private Radiogroup rdbOrigen_general;
	@View
	private Radiogroup rdbOrigen_profesional;
	@View
	private Radiogroup rdbOrigen_trabajo;
	@View
	private Radiogroup rdbOrigen_transito;
	@View
	private Radiogroup rdbOrigen_evento;
	@View
	private Radiogroup rdbTipo_servicio;
	@View
	private Radiogroup rdbPrioridad;
	@View
	private Radiogroup rdbUbicacion;
	@View
	private Radiogroup rdbServicio;
	@View
	private Textbox tbxCama;
	@View
	private Textbox tbxGuia_atencion;
	@View
	private Textbox tbxJustificacion;
	@View
	private Textbox tbxCie_p;
	@View
	private Textbox tbxCie_1;
	@View
	private Textbox tbxCie_2;
	@View
	private Textbox tbxCargo_reporta;
	@View
	private Textbox tbxTel_reporta;

//	private Datebox dtbxDelete_date;

	// private Textbox tbxDelete_user;
	@View
	private Textbox tbxAutorizado;
	@View
	private Textbox tbxCodigo_ips;
	@View
	private Textbox tbxCons_ips;
	@View
	private Textbox tbxLeido;
	@View
	private Textbox tbxLeido_rechazado;
	@View
	private Longbox tbxNro_historia;
	@View
	private Textbox tbxNeed_autorizacion;
	@View
	private Textbox tbxCodigo_receta;
	@View
	private Textbox tbxTelePres;
	@View
	private Textbox tbxDirPres;

	@View
	private Textbox tbxNombre_prestador;
	@View
	private Textbox tbxNit_prestador;
	@View
	private Textbox tbxTelefono_prestador;
	@View
	private Textbox tbxDepartamento_prestador;
	@View
	private Textbox tbxMunicipio_prestador;
	@View
	private Textbox tbxPagador_prestador;
	@View
	private Textbox tbxCodigo_prestador;

//	private String codigo_paciente;

	private Admision admision_seleccionada;
	// private String nro_ingreso_admision;

	private final String[] idsExcluyentes = { "tbxAccion", "divModuloOrdenamiento" };

	@Override
	public void init() {
		try {
			listarCombos();
			cargarDatosIniciales();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void cargarDatosIniciales() throws Exception {
		Map parametros = Executions.getCurrent().getArg();
		admision_seleccionada = (Admision) parametros
				.get("admision_seleccionada");
		Long codigo_historia = null;
		Hisc_urgencia hu = new Hisc_urgencia();
		hu.setNro_ingreso(admision_seleccionada.getNro_ingreso());
		hu.setCodigo_empresa(admision_seleccionada.getCodigo_empresa());
		hu.setCodigo_sucursal(admision_seleccionada.getCodigo_sucursal());
		hu.setNro_identificacion(admision_seleccionada.getNro_identificacion());

		hu = getServiceLocator().getServicio(Hisc_urgenciaService.class)
				.consultar(hu);
		if (hu != null) {
			codigo_historia = hu.getCodigo_historia();
		}

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_historia(codigo_historia);
		impresion_diagnostica.setCodigo_sucursal(admision_seleccionada
				.getCodigo_sucursal());
		impresion_diagnostica.setCodigo_empresa(admision_seleccionada
				.getCodigo_empresa());

		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);

		//log.info("impresion_diagnostica>>>>>>>>>>>" + impresion_diagnostica);

		if (impresion_diagnostica != null) {
			tbxCie_p.setValue(impresion_diagnostica.getCie_relacionado1());
			tbxCie_1.setValue(impresion_diagnostica.getCie_relacionado2());
			tbxCie_2.setValue(impresion_diagnostica.getCie_relacionado3());
		}

		// loadComponents();
		onMostrarModuloOrdenamiento();

		Anexo3_entidad anexo3_entidad = new Anexo3_entidad();
		anexo3_entidad.setCodigo_empresa(codigo_empresa);
		anexo3_entidad.setCodigo_sucursal(codigo_sucursal);
		anexo3_entidad.setCodigo_paciente(admision_seleccionada
				.getNro_identificacion());
		anexo3_entidad.setNro_ingreso(admision_seleccionada.getNro_ingreso());
//		anexo3_entidad.setCie_1(impresion_diagnostica.getCie_relacionado2());
//		anexo3_entidad.setCie_2(impresion_diagnostica.getCie_relacionado3());
//		anexo3_entidad.setCie_p(impresion_diagnostica.getCie_relacionado1());
		anexo3_entidad = getServiceLocator().getAnexo3EntidadService()
				.consultar(anexo3_entidad);
		if (anexo3_entidad != null) {
			mostrarDatos(anexo3_entidad);
		} else {
			accionForm(true, "registrar");
		}

		inicializarCampos();

	}

	public void inicializarCampos() {
		Map parametros = Executions.getCurrent().getArg();
		admision_seleccionada = (Admision) parametros
				.get("admision_seleccionada");
		Long codigo_historia = null;
		Hisc_urgencia hu = new Hisc_urgencia();
		hu.setNro_ingreso(admision_seleccionada.getNro_ingreso());
		hu.setCodigo_empresa(admision_seleccionada.getCodigo_empresa());
		hu.setCodigo_sucursal(admision_seleccionada.getCodigo_sucursal());
		hu.setNro_identificacion(admision_seleccionada.getNro_identificacion());

		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setCodigo_historia(codigo_historia);
		impresion_diagnostica.setCodigo_sucursal(admision_seleccionada
				.getCodigo_sucursal());
		impresion_diagnostica.setCodigo_empresa(admision_seleccionada
				.getCodigo_empresa());

		impresion_diagnostica = getServiceLocator().getServicio(
				Impresion_diagnosticaService.class).consultar(
				impresion_diagnostica);

		if (impresion_diagnostica != null) {
			tbxCie_p.setValue(impresion_diagnostica.getCie_relacionado1());
			tbxCie_1.setValue(impresion_diagnostica.getCie_relacionado2());
			tbxCie_2.setValue(impresion_diagnostica.getCie_relacionado3());
		}

		tbxNombre_prestador.setValue(empresa.getNombre_empresa());
		tbxNit_prestador.setText(empresa.getNro_identificacion());
		tbxTelefono_prestador.setText(empresa.getTelefonos());

		// -----------------Paciente----------------------------
		Paciente p = admision_seleccionada.getPaciente();
		if (p != null) {
			Municipios mun1 = new Municipios();
			mun1.setCodigo(empresa.getCodigo_municipio());
			mun1.setCoddep(empresa.getCodigo_dpto());
			mun1 = getServiceLocator().getServicio(MunicipiosService.class)
					.consultar(mun1);
			Departamentos dep1 = new Departamentos();
			dep1.setCodigo(p.getCodigo_dpto());
			dep1 = getServiceLocator().getServicio(DepartamentosService.class)
					.consultar(dep1);
			tbxCodigo_paciente_a3.setValue(p.getNro_identificacion());
//			codigo_paciente = p.getNro_identificacion();
			tbxNomPaciente.setValue(p.getNombre1() + " " + p.getNombre2() + " "
					+ p.getApellido1() + " " + p.getApellido2());
			tbxDirPac.setValue(p.getDireccion() + "");
			tbxFecNac.setValue(new SimpleDateFormat("yyyy-MM-dd").format(p
					.getFecha_nacimiento()));
			tbxTelPac.setValue(p.getTel_oficina() + " " + p.getTel_res());
			tbxDpto.setValue(dep1.getNombre());
			tbxMun.setValue(mun1.getNombre());
		}

		Departamentos dep = new Departamentos();
		dep.setCodigo(empresa.getCodigo_dpto());
		dep = getServiceLocator().getServicio(DepartamentosService.class)
				.consultar(dep);
		tbxDepartamento_prestador.setText(dep.getNombre());
		Municipios mun = new Municipios();
		mun.setCodigo(empresa.getCodigo_municipio());
		mun.setCoddep(empresa.getCodigo_dpto());
		mun = getServiceLocator().getServicio(MunicipiosService.class)
				.consultar(mun);
		tbxMunicipio_prestador.setText(mun.getNombre());
		tbxPagador_prestador.setText(admision_seleccionada.getAdministradora()
				.getNombre());
		tbxCodigo_prestador.setText(admision_seleccionada
				.getCodigo_administradora());

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
		listitem.setValue("numero_solicitud");
		listitem.setLabel("Numero_solicitud");
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
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		valida = orden_servicioAction.validarFormExterno();

		return valida;
	}

	public void buscarInformacionPrestador(Prestadores pre) {
		if (pre.getDireccion() != null) {
			tbxDirPres.setValue(pre.getDireccion());
		}
		if (pre.getTel_oficina() != null || pre.getTel_res() != null) {
			tbxTelePres.setValue(pre.getTel_res() + " - "
					+ prestador.getTel_oficina());
		}

	}

	public void buscarInformacionPaciente(Paciente paciente) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (paciente.getTel_oficina() != null || paciente.getTel_res() != null) {
			tbxTelPac.setValue(paciente.getTel_oficina() + " "
					+ paciente.getTel_res());
		}
		if (paciente.getFecha_nacimiento() != null) {
			tbxFecNac
					.setValue(dateFormat.format(paciente.getFecha_nacimiento()));
		}
		if (paciente.getDireccion() != null) {
			tbxDirPac.setValue(paciente.getDireccion());
		}
	}

	/*
	 * private void parametrizarBandboxPaciente() {
	 * tbxCodigo_paciente2.inicializar(tbxNomPaciente, btnLimpiarPaciente);
	 * tbxCodigo_paciente2 .setBandboxRegistrosIMG(new
	 * BandboxRegistrosIMG<Paciente>() {
	 * 
	 * @Override public void renderListitem(Listitem listitem, Paciente
	 * registro) {
	 * 
	 * // Extraemos valores String nro_identificacion = (String) registro
	 * .getNro_identificacion(); String nombres = (String) registro.getNombre1()
	 * + " " + (String) registro.getNombre2(); String apellidos = (String)
	 * registro.getApellido1() + " " + (String) registro.getApellido2();
	 * 
	 * // Mostramos valores en vista Listcell listcell = new Listcell();
	 * listcell.appendChild(new Label(nro_identificacion));
	 * listitem.appendChild(listcell);
	 * 
	 * listcell = new Listcell(); listcell.appendChild(new Label(nombres));
	 * listitem.appendChild(listcell);
	 * 
	 * listcell = new Listcell(); listcell.appendChild(new Label(apellidos));
	 * listitem.appendChild(listcell); }
	 * 
	 * @Override public List<Paciente> listarRegistros(String valorBusqueda,
	 * Map<String, Object> parametros) { parametros.put("paramTodo", "");
	 * parametros.put("value", "%" + valorBusqueda.toUpperCase().trim() + "%");
	 * parametros.put("codigo_empresa", sucursal.getCodigo_empresa());
	 * parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
	 * getServiceLocator().getPrestadoresService().setLimit(
	 * "limit 25 offset 0");
	 * 
	 * return getServiceLocator().getServicio(
	 * PacienteService.class).listar(parametros); }
	 * 
	 * @Override public boolean seleccionarRegistro(Bandbox bandbox, Textbox
	 * textboxInformacion, Paciente registro) {
	 * 
	 * String nro_identificacion = (String) registro .getNro_identificacion();
	 * String nombre = (String) registro.getNombre1() + " " + (String)
	 * registro.getNombre2() + " " + (String) registro.getApellido1() + " " +
	 * (String) registro.getApellido2();
	 * 
	 * bandbox.setValue(nro_identificacion);
	 * textboxInformacion.setValue(nombre); paciente = registro;
	 * actualizarDatos(); buscarInformacionPaciente(registro); return true; }
	 * 
	 * @Override public void limpiarSeleccion(Bandbox bandbox) { paciente =
	 * null; actualizarDatos(); }
	 * 
	 * }); }
	 */

	// private void parametrizarBandboxPrestador() {
	// tbxCodigo_medico.inicializar(tbxNomPrestador, btnLimpiarPrestador);
	// tbxCodigo_medico
	// .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {
	//
	// @Override
	// public void renderListitem(Listitem listitem,
	// Prestadores registro) {
	//
	// // Extraemos valores
	// String nro_identificacion = (String) registro
	// .getNro_identificacion();
	// String nombres = (String) registro.getNombres();
	// String apellidos = (String) registro.getApellidos();
	//
	// // Mostramos valores en vista
	// Listcell listcell = new Listcell();
	// listcell.appendChild(new Label(nro_identificacion));
	// listitem.appendChild(listcell);
	//
	// listcell = new Listcell();
	// listcell.appendChild(new Label(nombres));
	// listitem.appendChild(listcell);
	//
	// listcell = new Listcell();
	// listcell.appendChild(new Label(apellidos));
	// listitem.appendChild(listcell);
	// }
	//
	// @Override
	// public List<Prestadores> listarRegistros(
	// String valorBusqueda, Map<String, Object> parametros) {
	// parametros.put("paramTodo", "");
	// parametros.put("value", "%"
	// + valorBusqueda.toUpperCase().trim() + "%");
	// parametros.put("codigo_empresa",
	// sucursal.getCodigo_empresa());
	// parametros.put("codigo_sucursal",
	// sucursal.getCodigo_sucursal());
	//
	// getServiceLocator().getPrestadoresService().setLimit(
	// "limit 25 offset 0");
	//
	// return getServiceLocator().getServicio(
	// PrestadoresService.class).listar(parametros);
	// }
	//
	// @Override
	// public boolean seleccionarRegistro(Bandbox bandbox,
	// Textbox textboxInformacion, Prestadores registro) {
	//
	// String nro_identificacion = (String) registro
	// .getNro_identificacion();
	// String nombre = (String) registro.getNombres() + " "
	// + (String) registro.getApellidos();
	//
	// bandbox.setValue(nro_identificacion);
	// textboxInformacion.setValue(nombre);
	// prestador = registro;
	// actualizarDatos();
	// buscarInformacionPrestador(prestador);
	// return true;
	// }
	//
	// @Override
	// public void limpiarSeleccion(Bandbox bandbox) {
	// prestador = null;
	// actualizarDatos();
	// }
	//
	// });
	// }

//	private void actualizarDatos() {
//		try {
//			buscarDatos();
//		} catch (Exception e) {
//			MensajesUtil.mensajeError(e, "", this);
//		}
//	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			anexo3_entidadService.setLimit("limit 25 offset 0");

			List<Anexo3_entidad> lista_datos = anexo3_entidadService
					.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Anexo3_entidad anexo3_entidad : lista_datos) {
				rowsResultado.appendChild(crearFilas(anexo3_entidad, this));
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

		final Anexo3_entidad anexo3_entidad = (Anexo3_entidad) objeto;

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
				mostrarDatos(anexo3_entidad);
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
									eliminarDatos(anexo3_entidad);
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

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //
				Anexo3_entidad anexo3_entidad = new Anexo3_entidad();
				anexo3_entidad.setCodigo_empresa(empresa.getCodigo_empresa());
				anexo3_entidad
						.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				anexo3_entidad.setNumero_solicitud(tbxNumero_solicitud
						.getValue());
				anexo3_entidad.setFecha(new Timestamp(dtbxFecha.getValue()
						.getTime()));

				anexo3_entidad.setCodigo(tbxCodigo.getValue());

				anexo3_entidad.setCodigo_paciente(tbxCodigo_paciente_a3
						.getValue());
				anexo3_entidad.setCobertura(rdbCobertura.getSelectedItem()
						.getValue().toString());
				anexo3_entidad.setOrigen_general(rdbOrigen_general
						.getSelectedItem().getValue().toString());
				anexo3_entidad.setOrigen_profesional(rdbOrigen_profesional
						.getSelectedItem().getValue().toString());
				anexo3_entidad.setOrigen_trabajo(rdbOrigen_trabajo
						.getSelectedItem().getValue().toString());
				anexo3_entidad.setOrigen_transito(rdbOrigen_transito
						.getSelectedItem().getValue().toString());
				anexo3_entidad.setOrigen_evento(rdbOrigen_evento
						.getSelectedItem().getValue().toString());
				anexo3_entidad.setTipo_servicio(rdbTipo_servicio
						.getSelectedItem().getValue().toString());
				anexo3_entidad.setPrioridad(rdbPrioridad.getSelectedItem()
						.getValue().toString());
				anexo3_entidad.setUbicacion(rdbUbicacion.getSelectedItem()
						.getValue().toString());
				anexo3_entidad.setServicio(tbxServicio.getValue());
				anexo3_entidad.setCama(tbxCama.getValue());
				anexo3_entidad.setGuia_atencion(tbxGuia_atencion.getValue());
				anexo3_entidad.setJustificacion(tbxJustificacion.getValue());
				anexo3_entidad.setCie_p(tbxCie_p.getValue());
				anexo3_entidad.setCie_1(tbxCie_1.getValue());
				anexo3_entidad.setCie_2(tbxCie_2.getValue());
				anexo3_entidad.setCargo_reporta(tbxCargo_reporta.getValue());
				anexo3_entidad.setTel_reporta(tbxTel_reporta.getValue());
				anexo3_entidad.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				anexo3_entidad.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				anexo3_entidad.setDelete_date(null);
				anexo3_entidad
						.setCreacion_user(usuarios.getCodigo().toString());
				anexo3_entidad.setUltimo_user(usuarios.getCodigo().toString());
				// anexo3_entidad.setDelete_user(tbxDelete_user.getValue());
				anexo3_entidad.setAutorizado(tbxAutorizado.getValue());
				anexo3_entidad.setCodigo_ips(tbxCodigo_ips.getValue());
				anexo3_entidad.setCons_ips(tbxCons_ips.getValue());
				anexo3_entidad.setLeido(tbxLeido.getValue());
				anexo3_entidad
						.setLeido_rechazado(tbxLeido_rechazado.getValue());
				anexo3_entidad.setNro_historia(tbxNro_historia.getValue());
				anexo3_entidad.setNeed_autorizacion(tbxNeed_autorizacion
						.getValue());
				anexo3_entidad.setCodigo_receta(tbxCodigo_receta.getValue());

				anexo3_entidad.setNombre_reporta(tbxNombre_reporta.getValue());
				anexo3_entidad
						.setNro_identificacion_reporta(tbxNro_identificacion_reporta
								.getValue());
				anexo3_entidad.setNro_ingreso(admision_seleccionada
						.getNro_ingreso());
				anexo3_entidad.setDireccion_reporta(tbxDirPres.getValue());

				// anexo3_entidad.setCel_reporta(tbxCel_reporta.getValue());
				// anexo3_entidad.setEntidad(tbxEntidad.getValue());
				// anexo3_entidad.setTipo_anexo(tbxTipo_anexo.getValue());
				// anexo3_entidad.setEstado(tbxEstado.getValue());

				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("accion", tbxAccion.getValue());
				mapa_datos.put("anexo3_entidad", anexo3_entidad);
				mapa_datos.put("orden_procedimientos",
						orden_servicioAction.obtenerDatos());

				anexo3_entidadService.guardarDatos(mapa_datos);

				tbxCodigo.setValue(anexo3_entidad.getCodigo());
				tbxAccion.setValue("modificar");

				MensajesUtil.mensajeInformacion("Informacion ..",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Anexo3_entidad anexo3_entidad = (Anexo3_entidad) obj;
		try {
			tbxCodigo.setValue(anexo3_entidad.getCodigo());
			tbxNumero_solicitud.setValue(anexo3_entidad.getNumero_solicitud());
			dtbxFecha.setValue(anexo3_entidad.getFecha());
			// tbxCodigo_administradora.setValue(anexo3_entidad
			// .getCodigo_administradora());
			tbxNomPaciente.setValue(anexo3_entidad.getCodigo_paciente());
			Utilidades.seleccionarRadio(rdbCobertura,
					anexo3_entidad.getCobertura());
			Utilidades.seleccionarRadio(rdbOrigen_general,
					anexo3_entidad.getOrigen_general());
			Utilidades.seleccionarRadio(rdbOrigen_profesional,
					anexo3_entidad.getOrigen_profesional());
			Utilidades.seleccionarRadio(rdbOrigen_trabajo,
					anexo3_entidad.getOrigen_trabajo());
			Utilidades.seleccionarRadio(rdbOrigen_transito,
					anexo3_entidad.getOrigen_transito());
			Utilidades.seleccionarRadio(rdbOrigen_evento,
					anexo3_entidad.getOrigen_evento());
			Utilidades.seleccionarRadio(rdbTipo_servicio,
					anexo3_entidad.getTipo_servicio());
			Utilidades.seleccionarRadio(rdbPrioridad,
					anexo3_entidad.getPrioridad());
			Utilidades.seleccionarRadio(rdbUbicacion,
					anexo3_entidad.getUbicacion());
			tbxServicio.setValue(anexo3_entidad.getServicio());
			tbxCama.setValue(anexo3_entidad.getCama());
			tbxGuia_atencion.setValue(anexo3_entidad.getGuia_atencion());
			tbxJustificacion.setValue(anexo3_entidad.getJustificacion());
			tbxCie_p.setValue(anexo3_entidad.getCie_p());
			tbxCie_1.setValue(anexo3_entidad.getCie_1());
			tbxCie_2.setValue(anexo3_entidad.getCie_2());
			tbxCargo_reporta.setValue(anexo3_entidad.getCargo_reporta());
			tbxTel_reporta.setValue(anexo3_entidad.getTel_reporta());
			// dtbxDelete_date
			// .setValue(anexo3_entidad.getDelete_date() != null ?
			// anexo3_entidad
			// .getDelete_date() : new Date());
			// tbxDelete_user.setValue(anexo3_entidad.getDelete_user());
			tbxAutorizado.setValue(anexo3_entidad.getAutorizado());
			tbxCodigo_ips.setValue(anexo3_entidad.getCodigo_ips());
			tbxCons_ips.setValue(anexo3_entidad.getCons_ips());
			tbxLeido.setValue(anexo3_entidad.getLeido());
			tbxLeido_rechazado.setValue(anexo3_entidad.getLeido_rechazado());
			tbxNro_historia.setValue(anexo3_entidad.getNro_historia());
			tbxNeed_autorizacion
					.setValue(anexo3_entidad.getNeed_autorizacion());
			tbxCodigo_receta.setValue(anexo3_entidad.getCodigo_receta());

			tbxNombre_reporta.setValue(anexo3_entidad.getNombre_reporta());
			// tbxCel_reporta.setValue(anexo3_entidad.getCel_reporta());
			// tbxEntidad.setValue(anexo3_entidad.getEntidad());
			// tbxTipo_anexo.setValue(anexo3_entidad.getTipo_anexo());
			// tbxEstado.setValue(anexo3_entidad.getEstado());
			tbxNro_identificacion_reporta.setValue(anexo3_entidad
					.getNro_identificacion_reporta());

			tbxDirPres.setValue(anexo3_entidad.getDireccion_reporta());

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("codigo_orden", anexo3_entidad.getCodigo());

			List<Detalle_anexo3> listado_detalles = getServiceLocator()
					.getServicio(Detalle_anexo3Service.class)
					.listar(parametros);

			for (Detalle_anexo3 detalle_anexo3 : listado_detalles) {
				Detalle_orden detalle_orden = new Detalle_orden();
				detalle_orden.setCodigo_empresa(codigo_empresa);
				detalle_orden.setCodigo_sucursal(codigo_sucursal);
				detalle_orden.setCodigo_cups(detalle_anexo3.getCodigo_cups());
//				detalle_orden.setCodigo_orden(detalle_anexo3.getCodigo_orden());
				detalle_orden.setCodigo_procedimiento(detalle_anexo3
						.getCodigo_procedimiento());
				detalle_orden.setConsecutivo(detalle_anexo3.getConsecutivo());
				detalle_orden.setDescuento(detalle_anexo3.getDescuento());
				detalle_orden.setIncremento(detalle_anexo3.getIncremento());
				detalle_orden.setNombre_procedimiento(detalle_anexo3
						.getNombre_pcd());
				detalle_orden.setRealizado("");
				detalle_orden.setTipo_procedimiento(detalle_anexo3
						.getTipo_procedimiento());
				detalle_orden.setUnidades(detalle_anexo3.getUnidades());
				detalle_orden.setUnidades_realizadas(0);
				detalle_orden.setValor_procedimiento(detalle_anexo3
						.getValor_procedimiento());
				detalle_orden.setValor_real(detalle_anexo3.getValor_real());
				orden_servicioAction.adicionarOrden(detalle_orden,
						detalle_anexo3.getNombre_pcd());
			}
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		admision_seleccionada = (Admision) map
				.get(IVias_ingreso.ADMISION_PACIENTE);

	}

	public void eliminarDatos(Object obj) throws Exception {
		Anexo3_entidad anexo3_entidad = (Anexo3_entidad) obj;
		try {
			int result = anexo3_entidadService.eliminar(anexo3_entidad);
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

	public void onMostrarModuloOrdenamiento() throws Exception {
		if (orden_servicioAction != null)
			orden_servicioAction.detach();
		Map parametros = new HashMap();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("ocultaValor", "");
		parametros.put("admision", admision_seleccionada);
		parametros.put("opcion_formulario", tbxAccion.getValue());
		/* este parametros es para que oculte las vistas */
		parametros.put("ocultarInformacion", "_Ocultar");
		parametros.put("ocultar_consentimiento", "S");
		parametros.put("ocultarEstado", "ocultarEstado");
		orden_servicioAction = (Orden_servicio_internoAction) Executions
				.createComponents("/pages/orden_servicio_interno.zul", null,
						parametros);
		orden_servicioAction.inicializar(this);
		divModuloOrdenamiento.appendChild(orden_servicioAction);
		orden_servicioAction.obtenerBotonImprimir().setVisible(false);
		// cargo_farmacologico = true;
		// }

	}

	public void imprimir() throws Exception {
		if (tbxCodigo.getValue().equals("")) {
			Messagebox.show("El anexo 3 no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("codigo_orden", tbxCodigo.getValue());
		paramRequest.put("name_report", "Anexo3_entidad");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		// window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

}