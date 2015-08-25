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
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Via_ingreso_contratadas;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.Impresion_diagnosticaService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.FacturacionService;

public class Auditoria_admisionAction extends ZKWindow {

	private static Logger log = Logger
			.getLogger(Auditoria_admisionAction.class);

	private AdmisionService admisionService;
	private Impresion_diagnosticaService impresion_diagnosticaService;
	private ContratosService contratosService;

	// Componentes //
	@View
	private Textbox tbxValue;
	@View
	private Groupbox groupboxConsulta;
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
	private Listbox lbxContratos;
	@View
	private Listbox lbxVias_ingreso;
	@View
	private Toolbarbutton btnFiltro_ingreso;
	@View
	private Bandbox bandboxBuscar_centros;
	@View
	private Listbox lbxCentros_atencion;
	@View
	private Toolbarbutton btnFiltro_centros;
	@View
	private Popup popupViasIngreso;
	@View
	private Popup popupCentros_atencion;
	@View
	private Listbox lbxParametros;

	@View
	private Checkbox chkFiltro_incluir_urgencia;
	@View
	private Checkbox chkFiltro_incluir_hosp;

	private FacturacionService facturacionService;

	@Override
	public void init() {
		listarCombos();
		parametrizarBandboxAdministradora();
	}

	@Override
	public void _despuesIniciar() {
		cargarVias(lbxVias_ingreso);
		listarCentros();
	}

	public void listarCombos() {
		lbxParametros.getItems().clear();
		lbxParametros.appendItem("Nro identificacion", "nro_identificacion");
		lbxParametros.appendItem("Primer nombre", "nombre1");
		lbxParametros.appendItem("Segundo nombre", "nombre2");
		lbxParametros.appendItem("Primer apellido", "apellido1");
		lbxParametros.appendItem("Segundo apellido", "apellido2");
		lbxParametros.appendItem("Cualquier parametro", "paramTodo");
		lbxParametros.setSelectedIndex(0);
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);

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
			String parametro = lbxParametros.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			lbTotal_admisiones.setValue("");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);

			if (!value.isEmpty()) {
				if (parametro.equals("paramTodo")) {
					parameters.put("paramTodo", "paramTodo");
					parameters.put("value", value);
				} else {
					parameters.put(parametro, value);
				}
			}

			parameters.put("atendida", chkFiltro_atendidas.isChecked());
			parameters.put("estado", "1");// Estado 1 significa que esta activa

			if (lbxVias_ingreso.getSelectedItems().size() > 0
					|| chkFiltro_incluir_hosp.isChecked()
					|| chkFiltro_incluir_urgencia.isChecked()) {
				List<String> listado_vias = new ArrayList<String>();
				for (Listitem listitem : lbxVias_ingreso.getSelectedItems()) {
					listado_vias.add((String) listitem.getValue());
				}

				// incluimos las areas
				if (chkFiltro_incluir_hosp.isChecked()) {
					listado_vias.add(IVias_ingreso.HOSPITALIZACIONES);
				}

				if (chkFiltro_incluir_urgencia.isChecked()) {
					listado_vias.add(IVias_ingreso.URGENCIA);
				}

				btnFiltro_ingreso.setImage("/images/filtro1.png");
				parameters.put("vias_ingreso", listado_vias);
			} else {
				btnFiltro_ingreso.setImage("/images/filtro.png");
				List<String> vias_ingreso = new ArrayList<String>();
				vias_ingreso.add(IVias_ingreso.HOSPITALIZACIONES);
				vias_ingreso.add(IVias_ingreso.URGENCIA);
				parameters.put("vias_ingreso_excluyentes", vias_ingreso);
			}

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
				if (lbxContratos.getSelectedIndex() != 0) {
					Contratos contratos = (Contratos) lbxContratos
							.getSelectedItem().getValue();
					parameters.put("id_plan", contratos.getId_plan());
				}
			}

			List<String> listado_centros = new ArrayList<String>();
			if (!lbxCentros_atencion.getSelectedItems().isEmpty()) {
				for (Listitem listitem : lbxCentros_atencion.getSelectedItems()) {
					Centro_atencion centro_atencion = (Centro_atencion) listitem
							.getValue();
					listado_centros.add(centro_atencion.getCodigo_centro());
				}
				btnFiltro_centros.setImage("/images/filtro1.png");
				parameters.put("listado_centros", listado_centros);
			} else {
				btnFiltro_centros.setImage("/images/filtro.png");
			}
			popupViasIngreso.close();
			popupCentros_atencion.close();

			// for (String key_map : parameters.keySet()) {
			// log.info(key_map + " ===> " + parameters.get(key_map));
			// }

			List<Admision> lista_datos = admisionService
					.listarResultados(parameters);
			listboxResultado.getItems().clear();

			for (Admision admision : lista_datos) {
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

	private void cargarVias(Listbox listbox) {
		Listitem listitem;
		String tipo = listbox.getName();

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", tipo);
		parametros.put("orden_descripcion", "orden_descripcion");

		List<Elemento> elementos = getServiceLocator().getElementoService()
				.listar(parametros);

		for (Elemento elemento : elementos) {
			if (!elemento.getCodigo().equals(IVias_ingreso.URGENCIA)
					&& !elemento.getCodigo().equals(
							IVias_ingreso.HOSPITALIZACIONES)) {
				listitem = new Listitem();
				listitem.setValue(elemento.getCodigo());
				listitem.setLabel(elemento.getDescripcion());
				listbox.appendChild(listitem);
			}

		}
	}

	public void buscarCentro() {
		String valor = bandboxBuscar_centros.getValue().trim().toUpperCase();
		if (!valor.isEmpty()) {
			List<Listitem> listado = lbxCentros_atencion.getItems();
			for (Listitem listitem : listado) {
				Centro_atencion centro_atencion = (Centro_atencion) listitem
						.getValue();
				if (centro_atencion.getNombre_centro().toUpperCase()
						.contains(valor)
						|| valor.equals(centro_atencion.getCodigo_centro())) {
					Clients.scrollIntoView(listitem);
					MensajesUtil.notificarInformacion("Resultado encontrado",
							listitem);
					break;
				}
			}
		}
	}

	public void listarCentros() {
		lbxCentros_atencion.getItems().clear();
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		List<Centro_atencion> listado_centros = getServiceLocator()
				.getCentro_atencionService().listar(parametros);
		for (Centro_atencion centro_atencion : listado_centros) {
			Listitem listitem = new Listitem();
			listitem.setValue(centro_atencion);
			listitem.appendChild(new Listcell());
			listitem.appendChild(new Listcell(centro_atencion
					.getCodigo_centro()
					+ " - "
					+ centro_atencion.getNombre_centro()));
			if (centro_atencion_session != null) {
				if (centro_atencion.getCodigo_centro().equals(
						centro_atencion_session.getCodigo_centro())) {
					listitem.setSelected(true);
				}
			}
			lbxCentros_atencion.appendChild(listitem);
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
		fila.appendChild(new Listcell(elemento != null ? elemento
				.getDescripcion() : "(NO ENCONTRADA)"));
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
				admision = admisionService.consultar(admision);
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

	public void generarFacturacionSeleccionadas(boolean tiene_servicios) {
		try {
			Set<Listitem> listado_seleccionadas = listboxResultado
					.getSelectedItems();
			if (!listado_seleccionadas.isEmpty()) {
				List<Admision> listado_admisiones = new ArrayList<Admision>();
				for (Listitem listitem : listado_seleccionadas) {
					Admision admision = (Admision) listitem.getValue();
					admision = admisionService.consultar(admision);
					listado_admisiones.add(admision);
				}

				Map<String, Object> mapa_datos = new HashMap<String, Object>();
				mapa_datos.put("listado_admisiones", listado_admisiones);

				List<Map<String, Object>> listador_errores = new ArrayList<Map<String, Object>>();
				for (Admision admision : listado_admisiones) {
					try {
						Map<String, Object> map_fact = new HashMap<String, Object>();
						map_fact.put("codigo_empresa",
								admision.getCodigo_empresa());
						map_fact.put("codigo_sucursal",
								admision.getCodigo_sucursal());
						map_fact.put("codigo_tercero",
								admision.getNro_identificacion());
						map_fact.put("nro_ingreso", admision.getNro_ingreso());
						if (getServiceLocator().getServicio(
								FacturacionService.class).existePorParametro(
								map_fact)) {
							admision.setEstado("2");
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("admision", admision);
							// map.put("parametros_empresa",
							// parametros_empresa);
							admisionService.actualizar(map);
						} else if (validarSinConsulta(admision)) {
							if (admisionService
									.consultarContieneServicios(admision)) {
								ServiciosDisponiblesUtils
										.facturacionAutomatica(admision, true);
							}
						} else if (chkFiltro_incluir_urgencia.isChecked()
								&& admision.getVia_ingreso().equals(
										IVias_ingreso.URGENCIA)) {
							if (admisionService
									.consultarContieneServicios(admision)) {
								crearFacturaUrgencia(admision);
							}
						} else if (chkFiltro_incluir_hosp.isChecked()
								&& admision.getVia_ingreso().equals(
										IVias_ingreso.HOSPITALIZACIONES)) {
							if (admisionService
									.consultarContieneServicios(admision)) {
								crearFacturaHospitalizacion(admision);
							}
						} else if (!tiene_servicios
								|| admisionService
										.consultarContieneServicios(admision)) {
							admisionService.crearConsulta(admision,
									getImpresionDiagnostica(admision));
						}
					} catch (Exception e) {
						e.printStackTrace();
						Map<String, Object> map_contenedor_errores = new HashMap<String, Object>();
						map_contenedor_errores.put(IConstantes.PARAM_EXCEPCION,
								e);
						map_contenedor_errores.put(IConstantes.PARAM_ADMISION,
								admision);
						listador_errores.add(map_contenedor_errores);
					}
				}

				// List<Map<String, Object>> exceptions = admisionService
				// .guardarFacturacionAdimisiones(mapa_datos);
				if (listador_errores.isEmpty()) {
					MensajesUtil.mensajeInformacion("Informacion",
							"Facturas generadas satisfactoriamente !!!");
				} else {
					mostrarExcepciones(listador_errores);
				}
				// recargamos las consultas
				buscarDatos();
			} else {
				MensajesUtil.mensajeAlerta("Advertencia",
						"Por lo menos debe seleccionar una admisón");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void crearFacturaHospitalizacion(Admision admision) {
		crearFactura(admision);
	}

	private void crearFacturaUrgencia(Admision admision) {
		crearFactura(admision);
	}

	private void crearFactura(Admision admision) {
		Facturacion facturacion = getFacturacion(admision);
		Via_ingreso_contratadas via_ingreso_contratadas = ServiciosDisponiblesUtils
				.getVia_ingreso_contratadas(admision);
		Map<String, Object> serviciosCargados = getServiceLocator()
				.getFacturacionService().consultarAdmisionFactura(admision,
						facturacion.getFecha(),
						new ArrayList<Detalle_factura>(),
						via_ingreso_contratadas, "registrar");

		List<Detalle_factura> lista_datos = (List<Detalle_factura>) serviciosCargados
				.get("lista_detalle");

		Map<String, Object> datos_facturacion = new HashMap<String, Object>();
		datos_facturacion.put("facturacion", facturacion);
		datos_facturacion.put("lista_detalle", lista_datos);
		datos_facturacion.put("admision_aux", admision);
		datos_facturacion.put("accion", "registrar");
		datos_facturacion.put("contratos",
				ServiciosDisponiblesUtils.getContratosAdmision(admision));
		facturacionService.guardarDatos(datos_facturacion);
	}

	private Facturacion getFacturacion(Admision admision) {
		Calendar vence = Calendar.getInstance();

		Date fecha = vence.getTime();

		vence.set(Calendar.DATE, vence.get(Calendar.DATE) + 30);

		Facturacion facturacion = new Facturacion();
		facturacion.setCodigo_empresa(admision.getCodigo_empresa());
		facturacion.setCodigo_sucursal(admision.getCodigo_sucursal());
		facturacion.setCodigo_comprobante("");

		facturacion.setTipo_identificacion(admision.getPaciente()
				.getTipo_identificacion());
		facturacion.setCodigo_tercero(admision.getNro_identificacion());
		facturacion.setDocumento_externo("");
		facturacion.setCodigo_empleado("");
		facturacion.setFecha(new Timestamp(fecha.getTime()));
		facturacion
				.setFecha_vencimiento(new Timestamp(vence.getTimeInMillis()));
		facturacion.setNro_ingreso(admision.getNro_ingreso());
		facturacion.setFecha_inicio(admision.getFecha_ingreso());
		facturacion.setFecha_final(getFechaFinal(admision));
		facturacion.setCodigo_administradora(admision
				.getCodigo_administradora());
		facturacion.setId_plan(admision.getId_plan());
		facturacion.setNro_contrato("");
		facturacion.setNro_poliza("");
		facturacion.setTotal_cuotas(1);
		facturacion.setPlazo(30);
		facturacion.setForma_pago("02");
		facturacion.setDescripcion("FACTURACION DE PROCEDIMIENTOS Y CONSULTAS");
		facturacion.setObservacion("");
		facturacion.setValor_total(0d);
		facturacion.setValor_cuota(0d);
		facturacion.setValor_copago(0d);
		facturacion.setNocopago("S");
		facturacion.setDto_factura(0d);
		facturacion.setDto_copago(0.0);
		facturacion.setCop_canc(0d);
		facturacion.setAplica_descuento(false);
		// facturacion.setTipo("IND");
		facturacion.setEstado("PEND");
		// facturacion.setFactura(factura);
		facturacion.setPost("N");
		facturacion.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		facturacion.setCreacion_user(usuarios.getCodigo().toString());
		facturacion.setUltimo_user(usuarios.getCodigo().toString());
		facturacion.setCreacion_date(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		facturacion.setCodigo_credito("");
		facturacion.setRemision("");
		facturacion.setPrefijo("");
		facturacion.setAnio(new java.text.SimpleDateFormat("yyyy")
				.format(fecha));
		facturacion.setMes(new java.text.SimpleDateFormat("MM").format(fecha));
		facturacion.setClasificacion("");
		facturacion.setVerificado("N");
		facturacion.setRetencion(0.0);
		facturacion.setFactura("");
		facturacion.setRadicado("N");
		facturacion.setAuditado("N");
		facturacion.setValor_pagar_factura(facturacion.getValor_total());

		return facturacion;
	}

	/**
	 * Este metodo me devuelve las fecha final para el cierre de esas facturas
	 * 
	 * @author Luis Miguel
	 * */
	private Timestamp getFechaFinal(Admision admision) {
		Timestamp fecha_final = admisionService
				.getFechaUltimoServicio(admision);
		if (fecha_final == null) {
			Calendar calendar = Calendar.getInstance();
			if (admision.getFecha_atencion() != null) {
				calendar.setTime(admision.getFecha_atencion());
			} else {
				calendar.setTime(admision.getFecha_ingreso());
			}
			calendar.set(Calendar.HOUR_OF_DAY,
					calendar.get(Calendar.HOUR_OF_DAY) + 1);
			return new Timestamp(calendar.getTimeInMillis());
		}
		return fecha_final;
	}

	private boolean validarSinConsulta(Admision admision) {
		return admision.getVia_ingreso().equals(IVias_ingreso.CITOLOGIA)
				|| admision.getVia_ingreso().equals(
						IVias_ingreso.VIA_VACUNACION);
	}

	private Impresion_diagnostica getImpresionDiagnostica(Admision admision) {
		Long id_historia = admisionService.getIdHistoria(admision);
		if (id_historia != null) {
			Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
			impresion_diagnostica.setCodigo_empresa(admision
					.getCodigo_empresa());
			impresion_diagnostica.setCodigo_sucursal(admision
					.getCodigo_sucursal());
			impresion_diagnostica.setCodigo_historia(id_historia);
			impresion_diagnostica = impresion_diagnosticaService
					.consultar(impresion_diagnostica);
			if (impresion_diagnostica != null) {
				return impresion_diagnostica;
			} else {
				return getNuevaImpresion();
			}
		} else {
			return getNuevaImpresion();
		}
	}

	private Impresion_diagnostica getNuevaImpresion() {
		Impresion_diagnostica impresion_diagnostica = new Impresion_diagnostica();
		impresion_diagnostica.setFinalidad_consulta("10");
		impresion_diagnostica.setCie_principal("Z000");
		impresion_diagnostica.setTipo_principal("1");
		return impresion_diagnostica;
	}

	// TODO: terminar vizualizador de errores al generar el proceso
	private void mostrarExcepciones(List<Map<String, Object>> exceptions) {
		MensajesUtil
				.mensajeAlerta(
						"Advertencia",
						"se presentaron enrrores "
								+ exceptions.size()
								+ " al procesal algunos servicios\n El modulo para visualizar los errores esra en proceso");
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
						lbxContratos.setDisabled(false);
						Utilidades.listarcontratosPorAdministradora(
								lbxContratos, true, registro.getCodigo(),
								Auditoria_admisionAction.this, contratosService);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						lbxContratos.setDisabled(true);
						lbxContratos.setSelectedIndex(0);
					}
				});
	}

}