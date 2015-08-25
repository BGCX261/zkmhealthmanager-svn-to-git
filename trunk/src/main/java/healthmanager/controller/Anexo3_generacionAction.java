package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Anexo3_entidad;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Detalle_anexo3;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Enfermera_signos;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Anexo3_entidadService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.Detalle_anexo3Service;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.His_triageService;
import healthmanager.modelo.service.Hisc_urgenciaService;
import healthmanager.modelo.service.Impresion_diagnosticaService;
import healthmanager.modelo.service.MunicipiosService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IVias_ingreso;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Anexo3_generacionAction extends ZKWindow {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(Anexo3_generacionAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	private Orden_servicio_internoAction orden_servicioAction;
	private Anexo3_entidadService anexo3_entidadService;

	// --------------campos de la otra vista del anexo3_entidad2
	@View
	private Textbox tbxNombre_prestador;
	@View
	private Textbox tbxNit_prestador;
	@View
	private Textbox tbxTelefono_prestador;
	@View
	private Textbox tbxCodigo_paciente_a3;
	@View
	private Textbox tbxDirPac;
	@View
	private Textbox tbxFecNac;
	@View
	private Textbox tbxTelPac;
	@View
	private Textbox tbxDpto;
	@View
	private Textbox tbxMun;
	@View
	private Textbox tbxDepartamento_prestador;
	@View
	private Textbox tbxMunicipio_prestador;
	@View
	private Textbox tbxCodigo_prestador;
	@View
	private Textbox tbxPagador_prestador;
	@View
	private Textbox tbxCodigo;
	@View
	private Textbox tbxNumero_solicitud;
	@View
	private Datebox dtbxFecha;
	@View
	private Textbox tbxNomPaciente;
	@View
	private Radiogroup rdbCobertura;
	@View
	private Radiogroup rdbOrigen_general;
	@View
	private Radiogroup rdbOrigen_profesional;
	@View
	private Radiogroup rdbOrigen_trabajo;
	@View
	private Textbox tbxServicio;
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
	private Radiogroup rdbTipoIdPac;
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
	private Textbox tbxNombre_reporta;
	@View
	private Textbox tbxNro_identificacion_reporta;
	@View
	private Textbox tbxDirPres;
	@View
	private Textbox tbxAccion;
	@View
	private Div divModuloOrdenamiento;

	// /-------------fin de los campos ----------------------
	@View
	private Auxheader auxheaderAsistencial;

	@View
	private Grid gridResultado;
	@View
	private Datebox dtxFecha;
	@View
	private Textbox tbxValueAdmision;
	@View
	private Textbox tbxVia_ingreso;
	@View
	private Groupbox groupboxConsultar;
	@View
	private Borderlayout groupboxEditar;

	private Admision admision_seleccionada;

	private final String[] idsExcluyentes = { "tbxAccion",
			"divModuloOrdenamiento" };

	@View
	private Div divAsistencial;

	@View
	private Caption captionConsultar;

	private Map<String, String> vias;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() throws Exception {
		// se inicializa la fecha
		dtxFecha.setValue(new Date());

		// consultamos las vias de ingreso
		List<Elemento> listado_vias = getServiceLocator().getServicio(
				ElementoService.class).listar("via_ingreso");
		vias = new HashMap<String, String>();
		for (Elemento elemento : listado_vias) {
			vias.put(elemento.getCodigo(), elemento.getDescripcion());
		}
		parametrizarResultadoPaginado();
		// consultamos la informacion
		// de manera automatica
		// NOTA: antes de buscar tienen que estar cargadas las vias de ingreso
		buscarDatos();
		// cargarDatosIniciales();
	}

	@Override
	public void params(Map<String, Object> map) {
		tbxVia_ingreso.setValue((String) map.get(IVias_ingreso.VIA_INGRESO));
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsultar.setVisible(!sw);
		groupboxEditar.setVisible(sw);
		// log.info("Accion limpiar paciente");
		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			// log.info("Antes de limpiar");
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Admision> listarResultados(
					Map<String, Object> parametros) {
				List<Admision> listado = getServiceLocator()
						.getAdmisionService().listarResultados(parametros);
				// log.info("listado ====> " + listado.size());
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = getServiceLocator().getAdmisionService()
						.totalResultados(parametros);
				// log.info("total ====> " + total);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				Admision admision = (Admision) dato;
				if (admision.getRealizo_triage()) {
					His_triage historia = new His_triage();
					historia.setCodigo_empresa(admision.getCodigo_empresa());
					historia.setCodigo_sucursal(admision.getCodigo_sucursal());
					historia.setNro_ingreso(admision.getNro_ingreso());
					historia.setIdentificacion(admision.getNro_identificacion());
					historia = getServiceLocator().getServicio(
							His_triageService.class).consultar(historia);
					if (historia != null
							&& historia.getAdmitido().equalsIgnoreCase("S")) {
						return crearFila(admision);
					}
				} else {
					return crearFila(admision);
				}
				return null;
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado, 7);
	}

	public void buscarDatos() throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("fecha", new SimpleDateFormat("yyyy-MM-dd")
					.format(dtxFecha.getValue()));

			List<String> vias_ingreso = new ArrayList<String>();
			vias_ingreso.add(IVias_ingreso.URGENCIA);
			vias_ingreso.add(IVias_ingreso.HOSPITALIZACIONES);

			parameters.put("vias_ingreso", vias_ingreso);
			parameters.put("atendida", true);
			parameters.put("paramTodo", "");
			parameters.put("value", "%"
					+ tbxValueAdmision.getValue().toUpperCase().trim() + "%");
			parameters.put("codigo_centro",
					centro_atencion_session.getCodigo_centro());
			parameters
					.put("order",
							"fecha_ingreso asc,creacion_date,apellido1,apellido2,nombre1,nombre2");

			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFila(final Admision admision) {
		final Row fila = new Row();
		Hlayout hbox = new Hlayout();

		Paciente paciente = admision.getPaciente();

		String apellidos = (paciente != null ? paciente.getApellido1() + " "
				+ paciente.getApellido2() : "");
		String nombres = (paciente != null ? paciente.getNombre1() + " "
				+ paciente.getNombre2() : "");
		String descripcion = vias.get(admision.getVia_ingreso());
		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(admision.getNro_ingreso() + ""));
		fila.appendChild(new Label(admision.getNro_identificacion() + ""));
		fila.appendChild(new Label(apellidos));
		fila.appendChild(new Label(nombres));
		fila.appendChild(new Label(descripcion != null ? descripcion : ""));
		fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd HH:mm")
				.format(admision.getFecha_ingreso())));

		Toolbarbutton boton_mostrar = new Toolbarbutton();
		boton_mostrar.setImage("/images/mostrar_info.png");
		boton_mostrar.setTooltiptext("Ver informacion");
		boton_mostrar.setStyle("cursor: pointer");
		boton_mostrar.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarInformacion(admision);
			}
		});
		hbox.setValign("middle");
		hbox.appendChild(boton_mostrar);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;

		try {
			FormularioUtil.validarCamposObligatorios(dtbxFecha);
		} catch (Exception e) {
			valida = false;
			e.printStackTrace();
		}

		if (valida) {
			valida = orden_servicioAction.validarFormExterno();
		}

		return valida;
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
				anexo3_entidad.setCodigo_administradora(admision_seleccionada
						.getCodigo_administradora());

				// anexo3_entidad.setCel_reporta(tbxCel_reporta.getValue());
				// anexo3_entidad.setEntidad(tbxEntidad.getValue());
				// anexo3_entidad.setTipo_anexo(tbxTipo_anexo.getValue());
				// anexo3_entidad.setEstado(tbxEstado.getValue());

				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("accion", tbxAccion.getValue());
				mapa_datos.put("anexo3_entidad", anexo3_entidad);

				Map<String, Object> mapa_ordenes = orden_servicioAction
						.obtenerDatos();

				mapa_datos.put("lista_detalle",
						mapa_ordenes.get("lista_detalle"));
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

	private void mostrarInformacion(Admision admision) throws Exception {
		admision = getServiceLocator().getAdmisionService().consultar(admision);
		Anexo3_entidad anexo3_entidad = generarAnexo3(admision);
		admision_seleccionada = admision;
		mostrarDatos(anexo3_entidad);
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

	public void mostrarDatos(Anexo3_entidad anexo3_entidad) throws Exception {
		try {
			cargarDatosIniciales(admision_seleccionada);
			onMostrarModuloOrdenamiento();
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

			if (!listado_detalles.isEmpty()) {
				orden_servicioAction.limpiar();
				for (Detalle_anexo3 detalle_anexo3 : listado_detalles) {
					Detalle_orden detalle_orden = new Detalle_orden();
					detalle_orden.setCodigo_empresa(codigo_empresa);
					detalle_orden.setCodigo_sucursal(codigo_sucursal);
					detalle_orden.setCodigo_cups(detalle_anexo3
							.getCodigo_cups());
					// detalle_orden.setCodigo_orden(detalle_anexo3.getCodigo_orden());
					detalle_orden.setCodigo_procedimiento(detalle_anexo3
							.getCodigo_procedimiento());
					detalle_orden.setConsecutivo(detalle_anexo3
							.getConsecutivo());
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
			}
			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private Anexo3_entidad generarAnexo3(Admision admision) throws Exception {
		Anexo3_entidad anexo3_entidad = new Anexo3_entidad();
		anexo3_entidad.setCodigo_empresa(admision.getCodigo_empresa());
		anexo3_entidad.setCodigo_sucursal(admision.getCodigo_sucursal());
		anexo3_entidad.setCodigo_administradora(admision
				.getCodigo_administradora());
		anexo3_entidad.setNro_ingreso(admision.getNro_ingreso());
		anexo3_entidad.setCodigo_paciente(admision.getNro_identificacion());
		Anexo3_entidad anexo3_entidad_aux = getServiceLocator()
				.getAnexo3EntidadService().consultar(anexo3_entidad);

		if (anexo3_entidad_aux == null) {
			Historia_clinica historia_clinica = new Historia_clinica();
			historia_clinica.setCodigo_empresa(empresa.getCodigo_empresa());
			historia_clinica.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			historia_clinica.setNro_ingreso(admision.getNro_ingreso());
			historia_clinica.setVia_ingreso(admision.getVia_ingreso());
			historia_clinica.setNro_identificacion(admision
					.getNro_identificacion());
			historia_clinica = getServiceLocator().getServicio(
					GeneralExtraService.class).consultar(historia_clinica);

			Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
			impresion_diagnostica.setCodigo_empresa(codigo_empresa);
			impresion_diagnostica.setCodigo_sucursal(codigo_sucursal);
			impresion_diagnostica
					.setCodigo_historia(historia_clinica != null ? historia_clinica
							.getCodigo_historia() : null);
			impresion_diagnostica = getServiceLocator().getServicio(
					Impresion_diagnosticaService.class).consultar(
					impresion_diagnostica);

			anexo3_entidad.setCodigo_empresa(empresa.getCodigo_empresa());
			anexo3_entidad.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			anexo3_entidad.setNumero_solicitud("");
			anexo3_entidad.setFecha(admision.getFecha_atencion());
			anexo3_entidad.setCodigo_paciente(admision.getNro_identificacion());
			anexo3_entidad.setCobertura(admision.getPaciente()
					.getTipo_afiliado());
			anexo3_entidad.setOrigen_general("1");
			anexo3_entidad.setOrigen_profesional("N");
			anexo3_entidad.setOrigen_trabajo("N");
			anexo3_entidad.setOrigen_transito("N");
			anexo3_entidad.setOrigen_evento("N");
			anexo3_entidad.setTipo_servicio("N");
			anexo3_entidad.setPrioridad("N");
			anexo3_entidad.setUbicacion(admision.getVia_ingreso().equals(
					IVias_ingreso.URGENCIA) ? "3" : "2");
			anexo3_entidad.setServicio(admision.getVia_ingreso().equals(
					IVias_ingreso.URGENCIA) ? "SERVICIO DE URGENCIA"
					: "SERVICIO DE HOSPITALIZAcion ");
			anexo3_entidad.setCama("");
			anexo3_entidad.setGuia_atencion("");
			anexo3_entidad.setJustificacion("");
			anexo3_entidad
					.setCie_p(impresion_diagnostica != null ? impresion_diagnostica
							.getCie_principal() : "Z000");
			anexo3_entidad
					.setCie_1(impresion_diagnostica != null ? impresion_diagnostica
							.getCie_relacionado1() : "");
			anexo3_entidad
					.setCie_2(impresion_diagnostica != null ? impresion_diagnostica
							.getCie_relacionado2() : "");

			Usuarios usuarios_aux = new Usuarios();
			usuarios_aux.setCodigo_empresa(codigo_empresa);
			usuarios_aux.setCodigo_sucursal(codigo_sucursal);
			usuarios_aux.setCodigo(admision.getCodigo_medico());

			usuarios_aux = getServiceLocator().getUsuariosService().consultar(
					usuarios_aux);

			String cargo = admision.getVia_ingreso().equals(
					IVias_ingreso.URGENCIA) ? "MEDICO DE URGENCIAS"
					: "MEDICO DE HOSPIT";

			anexo3_entidad.setCargo_reporta(cargo);
			anexo3_entidad.setTel_reporta(usuarios_aux != null ? usuarios_aux
					.getTel_oficina() : "");
			anexo3_entidad.setCreacion_date(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			anexo3_entidad.setUltimo_update(new Timestamp(
					new GregorianCalendar().getTimeInMillis()));
			anexo3_entidad.setDelete_date(null);
			anexo3_entidad.setCreacion_user(usuarios.getCodigo().toString());
			anexo3_entidad.setUltimo_user(usuarios.getCodigo().toString());
			// anexo3_entidad.setDelete_user(tbxDelete_user.getValue());
			anexo3_entidad.setAutorizado("N");
			anexo3_entidad.setCodigo_ips("");
			anexo3_entidad.setCons_ips("");
			anexo3_entidad.setLeido("N");
			anexo3_entidad.setLeido_rechazado("N");
			anexo3_entidad
					.setNro_historia(historia_clinica != null ? historia_clinica
							.getCodigo_historia() : 0L);
			anexo3_entidad.setNeed_autorizacion("");
			anexo3_entidad.setCodigo_receta("");

			anexo3_entidad
					.setNombre_reporta(usuarios_aux != null ? (usuarios_aux
							.getNombres()) : "");
			anexo3_entidad.setCodigo_ips(usuarios_aux != null ? (usuarios_aux
					.getApellidos()) : "");
			anexo3_entidad.setNro_identificacion_reporta(admision
					.getCodigo_medico());
			anexo3_entidad.setNro_ingreso(admision.getNro_ingreso());
			anexo3_entidad
					.setDireccion_reporta(usuarios_aux != null ? usuarios_aux
							.getDireccion() : "");
			anexo3_entidad.setCel_reporta(usuarios_aux != null ? usuarios_aux
					.getTel_oficina() : "");

			Map<String, Object> mapa_ordenes = new HashMap<String, Object>();
			mapa_ordenes.put("codigo_empresa", codigo_empresa);
			mapa_ordenes.put("codigo_sucursal", codigo_sucursal);
			mapa_ordenes.put("nro_ingreso", admision.getNro_ingreso());
			mapa_ordenes.put("codigo_paciente",
					admision.getNro_identificacion());

			List<Detalle_orden> lista_detalle = new ArrayList<Detalle_orden>();

			List<Orden_servicio> listado_ordenes = getServiceLocator()
					.getOrden_servicioService().listar(mapa_ordenes);

			if (!listado_ordenes.isEmpty()) {
				for (Orden_servicio orden_servicio : listado_ordenes) {
					List<Detalle_orden> listado_detalles = orden_servicio
							.getLista_detalle();
					for (Detalle_orden detalle_orden : listado_detalles) {
						Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
								.getManuales_tarifarios(admision);
						Map<String, Object> mapa_procedimientos = Utilidades
								.getProcedimiento(
										manuales_tarifarios,
										new Long(detalle_orden
												.getCodigo_procedimiento()),
										getServiceLocator());
						detalle_orden
								.setNombre_procedimiento(mapa_procedimientos
										.containsKey("nombre_procedimiento") ? mapa_procedimientos
										.get("nombre_procedimiento").toString()
										: "");
						lista_detalle.add(detalle_orden);
					}
				}
			}

			Map<String, Object> mapa_datos = new HashMap<String, Object>();
			mapa_datos.put("lista_detalle", lista_detalle);
			mapa_datos.put("anexo3_entidad", anexo3_entidad);
			mapa_datos.put("accion", "registrar");

			getServiceLocator().getAnexo3EntidadService().guardarDatos(
					mapa_datos);
			// log.info("Se registro el anexo 3 desde el modulo generar");

		} else {
			anexo3_entidad = anexo3_entidad_aux;
		}

		return anexo3_entidad;

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

	public void imprimir(String codigo) throws Exception {
		if (codigo.equals("")) {
			Messagebox.show("El anexo 3 no se ha guardado aún",
					"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("codigo_orden", codigo);
		paramRequest.put("name_report", "Anexo3_entidad");

		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		// window.setMaximized(true);

		// Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
	}

	public void onSeleccionarAdmision(Listitem listitem,
			Enfermera_signos enfermera_signos) throws Exception {
		try {
			// log.info("Item de la admision: " + listitem);
			Admision admision = (Admision) listitem.getValue();
			admision_seleccionada = admision;
			accionForm(true, "registrar");
			mostrarDatos();
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil
					.mensajeError(e, "Admision no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil.mensajeError(e, "Admision no valida Exception", this);
		}
	}

	public void cargarDatosIniciales(Admision admision_seleccionada)
			throws Exception {
		// Map parametros = Executions.getCurrent().getArg();
		// admision_seleccionada = (Admision) parametros.get("admision");
		Long codigo_historia = -1L;
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

		if (impresion_diagnostica != null) {
			tbxCie_p.setValue(impresion_diagnostica.getCie_relacionado1());
			tbxCie_1.setValue(impresion_diagnostica.getCie_relacionado2());
			tbxCie_2.setValue(impresion_diagnostica.getCie_relacionado3());
		}

		inicializarCampos(admision_seleccionada);

	}

	public void inicializarCampos(Admision admision_seleccionada) {
		// Map parametros = Executions.getCurrent().getArg();
		// admision_seleccionada = (Admision) parametros
		// .get("admision_seleccionada");
		Long codigo_historia = -1L;
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
			tbxNomPaciente.setValue(p.getNombre1() + " " + p.getNombre2() + " "
					+ p.getApellido1() + " " + p.getApellido2());
			tbxDirPac.setValue(p.getDireccion() + "");
			tbxFecNac.setValue(new SimpleDateFormat("yyyy-MM-dd").format(p
					.getFecha_nacimiento()));
			tbxTelPac.setValue(p.getTel_oficina());
			Utilidades.seleccionarRadio(rdbTipoIdPac,
					p.getTipo_identificacion());
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

	public void onSeleccionarAdmision(Listitem listitem) throws Exception {
		try {
			// log.info("Item de la admision: " + listitem);
			Admision admision = (Admision) listitem.getValue();
			admision_seleccionada = admision;
			accionForm(true, "registrar");
			mostrarDatos();
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil
					.mensajeError(e, "Admision no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null)
				listitem.setDisabled(false);
			MensajesUtil.mensajeError(e, "Admision no valida Exception", this);
		}
	}

	public void mostrarDatos() throws Exception {
		if (admision_seleccionada != null) {

		} else {
			throw new Exception("No hay una admision seleccionada");
		}

	}

}
