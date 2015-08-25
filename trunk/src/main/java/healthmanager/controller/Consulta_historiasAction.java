/*
 * admisionAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.His_triage;
import healthmanager.modelo.bean.Hisc_urgencia;
import healthmanager.modelo.bean.Nivel_educativo;
import healthmanager.modelo.bean.Odontologia;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pertenencia_etnica;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Ficha_inicio_lepraService;
import healthmanager.modelo.service.His_triageService;
import healthmanager.modelo.service.Hisc_urgenciaService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.Seguimiento_control_pqtService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.UiException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.INotas;
import com.framework.constantes.IRoles;
import com.framework.constantes.IRutas_historia;
import com.framework.constantes.IVias_ingreso;
import com.framework.constantes.IVias_ingreso.Opciones_via_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ContenedorPaginasMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ModuloConsultaIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

public class Consulta_historiasAction extends ZKWindow implements
		ModuloConsultaIMG {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6385712606375798844L;

	private static Logger log = Logger
			.getLogger(Consulta_historiasAction.class);

	private Admision admision_seleccionada;

	// Componentes //
	@View
	private Textbox tbxValue;
	@View
	private Listbox listboxResultado;
	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Datebox dtbxFecha_final;
	// @View
	// private Checkbox chkFiltro_atendidas;

	@View
	private ContenedorPaginasMacro tabboxContendor;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_medico;
	@View
	private Textbox tbxNomPrestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;

	@View
	private Listbox lbxVias_ingreso;

	@View
	private Groupbox groupboxConsultar;

	@View
	private Borderlayout borderlayoutEditar;
	@View
	private Toolbarbutton toolbarbuttonInformacion_paciente;
	@View
	private Textbox tbxIngreso;
	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxTipo_identificacion;
	@View
	private Textbox tbxFecha_ingreso;
	@View
	private Textbox tbxAseguradora;
	@View
	private Textbox tbxContrato;
	@View
	private Textbox tbxNivel_educativo;
	@View
	private Textbox tbxPertencia_etnica;
	@View
	private Textbox tbxEdad;
	@View
	private Textbox tbxSexo;
	@View
	private Toolbarbutton btnFiltro_ingreso;
	@View
	private Hbox hbxPrestador;
	@View
	private Listbox lbxParametros;
	@View
	private Listbox lbxCentros_atencion;
	@View
	private Bandbox bandboxBuscar_centros;
	@View
	private Toolbarbutton btnFiltro_centros;
	@View
	private Popup popupViasIngreso;
	@View
	private Popup popupCentros_atencion;
	@View
	private Checkbox chkFiltro_triage;

	private Textbox tbxTipo_urgencia = new Textbox();

	private Ficha_inicio_lepraService ficha_inicio_lepraService;
	private Seguimiento_control_pqtService seguimiento_control_pqtService;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	@Override
	public void init() throws Exception {
		listarCombos();
		parametrizarBandboxPrestador();
		parametrizarResultadoPaginado();

		if (!(rol_usuario.equals(IRoles.ADMINISTRADOR)
				|| rol_usuario.equals(IRoles.CALIDAD) || rol_usuario
					.equals(IRoles.AUDITOR))) {
			Prestadores pr = new Prestadores();
			pr.setNro_identificacion(usuarios.getCodigo());
			pr.setCodigo_empresa(usuarios.getCodigo_empresa());
			pr.setCodigo_sucursal(usuarios.getCodigo_sucursal());

			pr = getServiceLocator().getServicio(PrestadoresService.class)
					.consultar(pr);

			if (pr != null) {
				String nombre = (String) pr.getNombres() + " "
						+ (String) pr.getApellidos();
				tbxCodigo_medico.seleccionarRegistro(pr,
						pr.getNro_identificacion(), nombre);
			}

		} else {
			tbxCodigo_medico.setDisabled(false);
			tbxNomPrestador.setDisabled(false);
		}
		MensajesUtil.notificarInformacion("Fecha inicial de busqueda",
				dtbxFecha_inicial);
	}

	public void listarCombos() {
		cargarVias(lbxVias_ingreso);
		lbxParametros.getItems().clear();
		lbxParametros.appendItem("Nro identificacion", "nro_identificacion");
		lbxParametros.appendItem("Primer nombre", "nombre1");
		lbxParametros.appendItem("Segundo nombre", "nombre2");
		lbxParametros.appendItem("Primer apellido", "apellido1");
		lbxParametros.appendItem("Segundo apellido", "apellido2");
		lbxParametros.appendItem("Cualquier parametro", "paramTodo");
		lbxParametros.setSelectedIndex(0);
		listarCentros();
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
				listbox.appendChild(listitem);
			}
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsultar.setVisible(!sw);
		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		}
	}

	public void onCheckTriage() {
		if (chkFiltro_triage.isChecked()) {
			btnFiltro_ingreso.setDisabled(true);
		} else {
			btnFiltro_ingreso.setDisabled(false);
		}
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parametro = lbxParametros.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

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
			parameters.put("order_defecto", "_order_defecto");
			if (chkFiltro_triage.isChecked()) {
				parameters.put("realizo_triage", true);
			} else {
				parameters.put("atendida", true);
				if (!lbxVias_ingreso.getSelectedItems().isEmpty()) {
					List<String> listado_vias = new ArrayList<String>();
					for (Listitem listitem : lbxVias_ingreso.getSelectedItems()) {
						listado_vias.add((String) listitem.getValue());
					}
					btnFiltro_ingreso.setImage("/images/filtro1.png");
					parameters.put("vias_ingreso", listado_vias);
				} else {
					btnFiltro_ingreso.setImage("/images/filtro.png");
				}

			}
			// parameters.put("estado", "1");

			if (dtbxFecha_inicial.getValue() != null
					&& dtbxFecha_final.getValue() != null) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String fecha1 = dateFormat.format(dtbxFecha_inicial.getValue());
				String fecha2 = dateFormat.format(dtbxFecha_final.getValue());

				if (dateFormat.parse(fecha1).getTime() > dateFormat.parse(
						fecha2).getTime()) {
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

			Prestadores prestadores = tbxCodigo_medico
					.getRegistroSeleccionado();

			if (prestadores != null) {
				parameters.put("codigo_medico_mod",
						prestadores.getNro_identificacion());
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

			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente)
			throws Exception {
		final Listitem fila = new Listitem();

		final Admision admision = (Admision) objeto;
		Prestadores p = admision.getPrestadores();

		String prest = "";
		if (p != null) {
			prest = p.getNombres() + " " + p.getApellidos();
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

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		fila.appendChild(new Listcell(dateFormat.format(admision
				.getFecha_ingreso()) + ""));
		fila.appendChild(new Listcell(admision.getNro_ingreso() + ""));
		fila.appendChild(new Listcell(paciente != null ? paciente
				.getDocumento() : admision.getNro_identificacion()));
		fila.appendChild(new Listcell(nombre_paciente));

		Elemento elemento = admision.getElemento_via_ingreso();

		fila.appendChild(new Listcell(prest));
		fila.appendChild(new Listcell(elemento != null ? elemento
				.getDescripcion() : admision.getVia_ingreso()));
		fila.appendChild(new Listcell((admision.getPrimera_vez()
				.equalsIgnoreCase("S") ? "Si" : "No")));

		Hbox hbox = new Hbox();
		Hisc_urgencia hisc_urgencia = null;
		His_triage his_triage = null;
		if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)) {
			hisc_urgencia = new Hisc_urgencia();
			hisc_urgencia.setCodigo_empresa(codigo_empresa);
			hisc_urgencia.setCodigo_sucursal(codigo_sucursal);
			hisc_urgencia.setNro_identificacion(admision
					.getNro_identificacion());
			hisc_urgencia.setNro_ingreso(admision.getNro_ingreso());
			hisc_urgencia = getServiceLocator().getServicio(
					Hisc_urgenciaService.class).consultar(hisc_urgencia);

			his_triage = new His_triage();
			his_triage.setCodigo_empresa(codigo_empresa);
			his_triage.setCodigo_sucursal(codigo_sucursal);
			his_triage.setIdentificacion(admision.getNro_identificacion());
			his_triage.setNro_ingreso(admision.getNro_ingreso());
			his_triage = getServiceLocator().getServicio(
					His_triageService.class).consultar(his_triage);
		}

		Listcell listcell = new Listcell();
		Toolbarbutton toolbarbutton;
		if (his_triage != null) {
			final String codigo_triage = his_triage.getCodigo_historia();
			toolbarbutton = new Toolbarbutton("Triage", "/images/print_ico.gif");
			toolbarbutton.setStyle("font-size:8px");
			toolbarbutton.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {

						@Override
						public void onEvent(Event event) throws Exception {
							imprimirTriage(codigo_triage);
						}
					});
			hbox.appendChild(toolbarbutton);
		}

		toolbarbutton = new Toolbarbutton("Mostrar", "/images/mostrar_info.png");
		toolbarbutton.setTooltiptext("Mostrar historia clinica");
		toolbarbutton.setStyle("font-size:8px");
		toolbarbutton.setAttribute("TIPO_HIS_URG",
				hisc_urgencia != null ? hisc_urgencia.getTipo_urgencia() : "");
		toolbarbutton.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {

					@Override
					public void onEvent(Event event) throws Exception {

						if (event.getTarget() != null) {
							tbxTipo_urgencia.setValue(event.getTarget()
									.getAttribute("TIPO_HIS_URG").toString());
							onSeleccionarAdmision(fila);
						}
					}
				});
		if (admision.getVia_ingreso().equals(IVias_ingreso.URGENCIA)
				&& hisc_urgencia == null) {
			toolbarbutton.setDisabled(true);
		}
		hbox.appendChild(toolbarbutton);
		listcell.appendChild(hbox);
		fila.appendChild(listcell);

		fila.setValue(admision);

		return fila;
	}

	private void parametrizarBandboxPrestador() {
		tbxCodigo_medico.inicializar(tbxNomPrestador, btnLimpiarPrestador);
		tbxCodigo_medico
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {

					@Override
					public void renderListitem(Listitem listitem,
							Prestadores registro) {

						// Extraemos valores
						String nro_identificacion = (String) registro
								.getNro_identificacion();
						String nombres = (String) registro.getNombres();
						String apellidos = (String) registro.getApellidos();

						// Mostramos valores en vista
						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(nro_identificacion));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(nombres));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(apellidos));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Prestadores> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("codigo_empresa",
								sucursal.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());

						parametros.put("limite_paginado", "limit 25 offset 0");

						return getServiceLocator().getServicio(
								PrestadoresService.class).listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Prestadores registro) {

						String nro_identificacion = (String) registro
								.getNro_identificacion();
						String nombre = (String) registro.getNombres() + " "
								+ (String) registro.getApellidos();

						bandbox.setValue(nro_identificacion);
						textboxInformacion.setValue(nombre);
						actualizarDatosHistoria();
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						actualizarDatosHistoria();
					}

				});
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Admision> listarResultados(
					Map<String, Object> parametros) {
				List<Admision> listado = getServiceLocator()
						.getAdmisionService().listarResultados(parametros);
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				return 100;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilas(dato, listboxResultado);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				listboxResultado, 8);
	}

	private void actualizarDatosHistoria() {
		try {
			buscarDatos();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void accionForm2(final String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			if (admision_seleccionada.getVia_ingreso().equals(
					IVias_ingreso.VISITA_DOMICILIARIA)) {
				// buscarDatos_VisitaDomiciliaria();
				// divVisitaDomiciliaria.setVisible(true);
				// divAsistencial.setVisible(false);
				// divTuberculosis_lepra.setVisible(false);
				// log.info("visita");
			} else if ((admision_seleccionada.getVia_ingreso()
					.equals(IVias_ingreso.CONTROL_TUBERCULOSIS))
					|| (admision_seleccionada.getVia_ingreso()
							.equals(IVias_ingreso.CONTROL_LEPRA))) {
				// buscarDatos_Tuberculosis_lepra();
				// divTuberculosis_lepra.setVisible(true);
				// divVisitaDomiciliaria.setVisible(false);
				// divAsistencial.setVisible(false);
				// //log.info("tuberculosis");
			} else {
				// buscarDatos();
				// divAsistencial.setVisible(true);
				// divVisitaDomiciliaria.setVisible(false);
				// divTuberculosis_lepra.setVisible(false);
				// //log.info("asistencial");
			}

			groupboxConsultar.setVisible(true);
			borderlayoutEditar.setVisible(false);
		} else {
			groupboxConsultar.setVisible(false);
			borderlayoutEditar.setVisible(true);
		}
	}

	public void accionForm(String accion) throws Exception {
		if (!accion.equalsIgnoreCase("registrar")) {
			if (admision_seleccionada.getVia_ingreso().equals(
					IVias_ingreso.VISITA_DOMICILIARIA)) {
				// buscarDatos_VisitaDomiciliaria();
				// divVisitaDomiciliaria.setVisible(true);
				// divAsistencial.setVisible(false);
				// divTuberculosis_lepra.setVisible(false);
				// //log.info("visita");
			} else if ((admision_seleccionada.getVia_ingreso()
					.equals(IVias_ingreso.CONTROL_TUBERCULOSIS))
					|| (admision_seleccionada.getVia_ingreso()
							.equals(IVias_ingreso.CONTROL_LEPRA))) {
				// buscarDatos_Tuberculosis_lepra();
				// divTuberculosis_lepra.setVisible(true);
				// divVisitaDomiciliaria.setVisible(false);
				// divAsistencial.setVisible(false);
				// //log.info("tuberculosis");
			} else {
				buscarDatos();
				// divAsistencial.setVisible(true);
				// divVisitaDomiciliaria.setVisible(false);
				// divTuberculosis_lepra.setVisible(false);
				// log.info("asistencial");
			}

			groupboxConsultar.setVisible(true);
			borderlayoutEditar.setVisible(false);
		} else {
			groupboxConsultar.setVisible(false);
			borderlayoutEditar.setVisible(true);
		}
	}

	public void onSeleccionarAdmision(Listitem listitem) throws Exception {
		try {
			// log.info("Item de la admision: " + listitem);
			Admision admision = (Admision) listitem.getValue();
			admision = getServiceLocator().getAdmisionService().consultar(
					admision);
			admision_seleccionada = admision;
			accionForm("registrar");
			mostrarDatos();
			inicializarContenedorPaginas();
			// cargarHistorialHistorias();
			borderlayoutEditar.invalidate();
		} catch (UiException e) { // para que no salga el problema de unica ID
			if (listitem != null) {
				listitem.setDisabled(false);
			}
			MensajesUtil
					.mensajeError(e, "Admision no valida UiException", this);
		} catch (Exception e) {
			if (listitem != null) {
				listitem.setDisabled(false);
			}
			MensajesUtil.mensajeError(e, "Admision no valida Exception", this);
		}
	}

	public void mostrarDatos() throws Exception {
		if (admision_seleccionada != null) {
			toolbarbuttonInformacion_paciente.setLabel(admision_seleccionada
					.getPaciente().getNombreCompleto());
			tbxIngreso.setValue(admision_seleccionada.getNro_ingreso());
			tbxNro_identificacion.setValue(admision_seleccionada
					.getNro_identificacion());

			// Agregado por jhonny
			Map<String, Integer> mapa_edades = Util
					.getEdadYYYYMMDD(admision_seleccionada.getPaciente()
							.getFecha_nacimiento());

			Integer anios = mapa_edades.get("anios");
			Integer meses = mapa_edades.get("meses");
			Integer dias = mapa_edades.get("dias");

			if (anios.intValue() == 0 && meses.intValue() == 0) {
				tbxEdad.setValue(dias + (dias == 1 ? " día" : " días"));
			} else if (anios.intValue() == 0) {
				tbxEdad.setValue(meses + (meses == 1 ? " mes (" : " meses (")
						+ (dias + (dias == 1 ? " día" : " días")) + ")");
			} else {
				int current_meses = meses.intValue() - (anios.intValue() * 12);
				tbxEdad.setValue(anios
						+ (anios == 1 ? " año" : " años")
						+ (current_meses != 0 ? (" y " + current_meses + (current_meses == 1 ? " mes "
								: " meses"))
								: ""));
			}

			if (admision_seleccionada.getPaciente().getSexo() != null) {
				if (admision_seleccionada.getPaciente().getSexo().equals("M")) {
					tbxSexo.setValue("MASCULINO");
				} else if (admision_seleccionada.getPaciente().getSexo()
						.equals("F")) {
					tbxSexo.setValue("FEMENINO");
				}
			}

			// Fin Jhonny
			tbxTipo_identificacion.setValue(Utilidades.getNombreElemento(
					admision_seleccionada.getPaciente()
							.getTipo_identificacion(), "tipo_id", this));
			tbxFecha_ingreso.setValue(new SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss").format(admision_seleccionada
					.getFecha_ingreso()));
			tbxAseguradora
					.setValue(admision_seleccionada.getAdministradora() != null ? admision_seleccionada
							.getAdministradora().getCodigo()
							+ " "
							+ admision_seleccionada.getAdministradora()
									.getNombre() : admision_seleccionada
							.getCodigo_administradora());
			tbxContrato
					.setValue(admision_seleccionada.getContratos() != null ? admision_seleccionada
							.getContratos().getNombre() : admision_seleccionada
							.getId_plan());

			Nivel_educativo educacion = new Nivel_educativo();
			educacion.setId(admision_seleccionada.getPaciente()
					.getCodigo_educativo());
			educacion = getServiceLocator().getNivel_educativoService()
					.consultar(educacion);

			Pertenencia_etnica etnica = new Pertenencia_etnica();
			etnica.setId(admision_seleccionada.getPaciente()
					.getPertenencia_etnica());
			etnica = getServiceLocator().getPertenencia_etnicaService()
					.consultar(etnica);

			tbxNivel_educativo.setValue(educacion != null ? educacion
					.getNombre() : "");
			tbxPertencia_etnica.setValue("("
					+ admision_seleccionada.getPaciente()
							.getPertenencia_etnica() + ") "
					+ (etnica != null ? etnica.getNombre() : ""));
		} else {
			throw new Exception("No hay una admision seleccionada");
		}
	}

	protected void onSeleccionarAdmisionLaboratorio(Listitem listitem) {
		Admision admision = (Admision) listitem.getValue();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IVias_ingreso.ADMISION_PACIENTE, admision);
		TomaMuestraAction tomaMuestraAction = (TomaMuestraAction) Executions
				.createComponents("/pages/toma_muestra.zul", this, map);
		tomaMuestraAction.setWidth("100%");
		tomaMuestraAction.setHeight("100%");
		tomaMuestraAction.doModal();
	}

	public void inicializarContenedorPaginas() throws Exception {
		tabboxContendor.cerrarTabs();
		String via_ingreso = admision_seleccionada.getVia_ingreso();
		// log.info("ejecutando metodo @inicializarContenedorPaginas() ===> VIA_INGRESO="
		// + via_ingreso);

		// CAMBIAR AQUI
		if (admision_seleccionada != null) {
			boolean mostrar_prescripcion = true, mostrar_remision = true, mostrar_autorizaciones = true, mostrar_notas_odontologia = false;
			boolean mostrar_notasaclaratorias = false, mostrar_evolucion = false, mostrar_tuberculosis = false;
			boolean mostrar_epicrisis = false, mostrar_contrarreferencia = false;
			boolean mostrar_lepra = false, mostrar_ordenes_medicas = false, mostrar_notas_enfermeria = false, mostrar_registro_med = false;
			/*
			 * mostrar_seguimiento_lepra = false; boolean
			 * mostrar_discapacidades_lepra = false,mostrar_conviviente_lepra =
			 * false;
			 */

			Odontologia odontologiaTemp = null;

			tabboxContendor.cerrarTabs();

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.VIA_INGRESO, via_ingreso);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			parametros.put(IVias_ingreso.PADRE, this);
			parametros.put(IVias_ingreso.TIPO_URGENCIA,
					tbxTipo_urgencia.getValue());
			tabboxContendor.getTabs().setStyle(
					"background-image:url(../images/bar01.gif)");

			// consultamos configuracion
			Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
			configuracion_admision_ingreso
					.setCodigo_empresa(admision_seleccionada
							.getCodigo_empresa());
			configuracion_admision_ingreso
					.setCodigo_sucursal(admision_seleccionada
							.getCodigo_sucursal());
			configuracion_admision_ingreso.setVia_ingreso(admision_seleccionada
					.getVia_ingreso());
			configuracion_admision_ingreso = getServiceLocator().getServicio(
					Configuracion_admision_ingresoService.class).consultar(
					configuracion_admision_ingreso);

			if (via_ingreso.equals(IVias_ingreso.FORMULARIO_TRIAGE)) {
				// log.info("ha entrado a la validacionn de triage ");
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_FORMULARIO_TRIAGE);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_FORMULARIO_TRIAGE);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.URGENCIA)) {
				log.info("Tipo de urgencia ===> " + tbxTipo_urgencia.getValue());
				if (tbxTipo_urgencia.getValue().equals(
						IVias_ingreso.TIPO_URGENCIA_OBSTETRICA)) {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_HC_OBSTETRICA);
					tabboxContendor
							.setLabelTabInicio(IRutas_historia.LABEL_HC_OBSTETRICA);
				} else if (tbxTipo_urgencia.getValue().equals(
						IVias_ingreso.TIPO_URGENCIA_GENERAL)
						|| tbxTipo_urgencia.getValue().isEmpty()) {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_HC_URGENCIA);
					tabboxContendor
							.setLabelTabInicio(IRutas_historia.LABEL_HC_URGENCIA);
				}
				mostrar_ordenes_medicas = true;
				mostrar_notas_enfermeria = true;
				mostrar_registro_med = true;
				mostrar_epicrisis = true;
				mostrar_notasaclaratorias = false;
				mostrar_contrarreferencia = true;
				mostrar_autorizaciones = false;
				mostrar_evolucion = true;
				mostrar_prescripcion = false;
				mostrar_remision = false;
			} else if (via_ingreso
					.equals(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_RECIEN_NACIDO);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_RECIEN_NACIDO);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_notasaclaratorias = false;
				mostrar_epicrisis = true;
				mostrar_ordenes_medicas = true;
				mostrar_notas_enfermeria = true;
			} else if (via_ingreso.equals(IVias_ingreso.HIPERTENSO_DIABETICO)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_HIPETENSO_DIABETICO);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_HIPERTENSO_DIABETICO);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.HC_MENOR_2_MESES)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_MENOR_2_MESES);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_MENOR_2_MESES);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;

			} else if (via_ingreso
					.equals(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_MENOR_2_MESES_2_ANIOS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_MENOR_2_MESES_2_ANIOS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_MENOR_2_ANIOS_5_ANIOS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_MENOR_2_ANIOS_5_ANIOS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso
					.equals(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_MENOR_5_ANIOS_10_ANIOS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_MENOR_5_ANIOS_10_ANIOS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso
					.equals(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ALTERACION_EMBARAZO);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ALTERACION_EMBARAZO);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.ADULTO_MAYOR)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ADULTO_MAYOR);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ADULTO_MAYOR);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.ALTERACION_JOVEN)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ALTERACION_JOVEN);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ALTERACION_JOVEN);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.SERVICIOS_AMIGABLES)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_SERVICIOS_AMIGABLES);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_SERVICIOS_AMIGABLES);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.PLANIFICACION_FAMILIAR)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_PLANIFICACION_FAMILIAR);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_PLANIFICACION_FAMILIAR);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.PSICOLOGIA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_PSICOLOGIA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_PSICOLOGIA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.PSIQUIATRIA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_PSIQUIATRIA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_PSIQUIATRIA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ODONTOLOGIA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ODONTOLOGIA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
					|| via_ingreso.equals(IVias_ingreso.SALUD_ORAL)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ODONTOLOGIA2);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ODONTOLOGIA2);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_notas_odontologia = true;

				Map<String, Object> parametros_odontologia = new HashMap<String, Object>();
				parametros_odontologia.put("codigo_empresa", codigo_empresa);
				parametros_odontologia.put("codigo_sucursal", codigo_sucursal);
				parametros_odontologia.put("identificacion",
						admision_seleccionada.getNro_identificacion());

				List<Odontologia> listado_odontologia = getServiceLocator()
						.getOdontologiaService().listar(parametros_odontologia);
				admision_seleccionada.setPrimera_vez("S");
				if (!listado_odontologia.isEmpty()) {
					admision_seleccionada.setPrimera_vez("N");
					odontologiaTemp = listado_odontologia.get(0);
					parametros.put("odontologiaTemp", odontologiaTemp);
				}

			} else if (via_ingreso.equals(IVias_ingreso.URGENCIA_ODONTOLOGICO)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_URGENCIA_ODONTOLOGICO);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_URGENCIA_ODONTOLOGICO);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.VISITA_DOMICILIARIA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_VISITA_DOMICILIARIA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_VISITA_DOMICILIARIA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.HC_AIEPI_2_MESES)) {
				Citas citas = new Citas();
				citas.setCodigo_empresa(admision_seleccionada
						.getCodigo_empresa());
				citas.setCodigo_sucursal(admision_seleccionada
						.getCodigo_sucursal());
				citas.setCodigo_cita(admision_seleccionada.getCodigo_cita());
				citas.setNro_identificacion(admision_seleccionada
						.getNro_identificacion());
				citas = getServiceLocator().getCitasService().consultar(citas);
				parametros.put(IVias_ingreso.CITA_PACIENTE,
						citas != null ? citas : new Citas());
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso
					.equals(IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS)) {
				Citas citas = new Citas();
				citas.setCodigo_empresa(admision_seleccionada
						.getCodigo_empresa());
				citas.setCodigo_sucursal(admision_seleccionada
						.getCodigo_sucursal());
				citas.setCodigo_cita(admision_seleccionada.getCodigo_cita());
				citas.setNro_identificacion(admision_seleccionada
						.getNro_identificacion());
				citas = getServiceLocator().getCitasService().consultar(citas);
				parametros.put(IVias_ingreso.CITA_PACIENTE,
						citas != null ? citas : new Citas());
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES_5_ANOS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES_5_ANOS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_HC_HOSPITALIZACIONES);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_HC_HOSPITALIZACIONES);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_evolucion = true;
				mostrar_epicrisis = true;
				mostrar_contrarreferencia = true;
				mostrar_ordenes_medicas = true;
				mostrar_registro_med = true;
				mostrar_notas_enfermeria = true;
			} else if (via_ingreso.equals(IVias_ingreso.CITOLOGIA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_CITOLOGIA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_CITOLOGIA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.CONTROL_TUBERCULOSIS)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_HC_TUBERCULOSIS);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_HC_TUBERCULOSIS);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
			} else if (via_ingreso.equals(IVias_ingreso.CONTROL_LEPRA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_INICIO_TRATAMIENTO_LEPRA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_INICIO_TRATAMIENTO_LEPRA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_lepra = true;
			} else if (via_ingreso.equals(IVias_ingreso.ECOGRAFIA)
					&& admision_seleccionada.getTipo_adicional_via_ingreso()
							.equals(IVias_ingreso.ECOGRAFIA_GINECOSTETRICA)) {
				tabboxContendor
						.setUrlPaginaInicio(IRutas_historia.PAGINA_ECOGRAFIA_GINECOSTETRICA);
				tabboxContendor
						.setLabelTabInicio(IRutas_historia.LABEL_ECOGRAFIA_GINECOSTETRICA);
				mostrar_prescripcion = false;
				mostrar_remision = false;
				mostrar_autorizaciones = false;
				mostrar_evolucion = false;
				mostrar_contrarreferencia = false;
				mostrar_notasaclaratorias = false;
				mostrar_epicrisis = false;
			} else if (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA)
					|| via_ingreso.equals(IVias_ingreso.CONSULTA_ESPECIALIZADA)
					|| (configuracion_admision_ingreso != null && configuracion_admision_ingreso
							.isAplica_cualquier_servicio())) {
				Citas citas = new Citas();
				citas.setCodigo_empresa(admision_seleccionada
						.getCodigo_empresa());
				citas.setCodigo_sucursal(admision_seleccionada
						.getCodigo_sucursal());
				citas.setCodigo_cita(admision_seleccionada.getCodigo_cita());
				citas.setNro_identificacion(admision_seleccionada
						.getNro_identificacion());

				citas = getServiceLocator().getCitasService().consultar(citas);

				parametros.put(IVias_ingreso.CITA_PACIENTE, citas);

				if (parametros_empresa.getTipo_historia_clinica().equals("01")) {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_1);
				} else if (parametros_empresa.getTipo_historia_clinica()
						.equals("02")) {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_2);
				} else {
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_CONSULTA_EXTERNA_NRO_3);
					if (admision_seleccionada.isAplica_tuberculosis()) {
						mostrar_tuberculosis = true;
					} else {
						mostrar_tuberculosis = false;
					}
					if (admision_seleccionada.isAplica_lepra()) {
						mostrar_lepra = true;
					} else {
						mostrar_lepra = false;
					}
					mostrar_prescripcion = false;
					mostrar_remision = false;
					mostrar_autorizaciones = false;
				}

				boolean cambiar_nombre_historia = true;
				if (via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA)) {
					tabboxContendor
							.setLabelTabInicio(IRutas_historia.LABEL_CONSULTA_EXTERNA);
				} else if (via_ingreso
						.equals(IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
					tabboxContendor
							.setLabelTabInicio(IRutas_historia.LABEL_CONSULTA_ESPECIALIZADA);
				} else {
					if (configuracion_admision_ingreso != null) {
						Elemento elemento = new Elemento();
						elemento.setTipo("via_ingreso");
						elemento.setCodigo(configuracion_admision_ingreso
								.getVia_ingreso());
						elemento = getServiceLocator().getServicio(
								ElementoService.class).consultar(elemento);
						if (elemento != null) {
							tabboxContendor.setLabelTabInicio(elemento
									.getDescripcion());
							cambiar_nombre_historia = false;
						} else {
							tabboxContendor.setLabelTabInicio("HISTORIA");
						}
					}
				}

				int edad = Util.getEdadYYYYMMDD(
						admision_seleccionada.getPaciente()
								.getFecha_nacimiento(),
						admision_seleccionada.getCreacion_date()).get("anios");

				if (edad == 0) {
					int edad_meses = Util.getEdadYYYYMMDD(
							admision_seleccionada.getPaciente()
									.getFecha_nacimiento(),
							admision_seleccionada.getCreacion_date()).get(
							"meses");

					if (edad_meses < 2) {
						if (cambiar_nombre_historia) {
							tabboxContendor
									.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES);
						}
						tabboxContendor
								.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES);
					} else {
						if (cambiar_nombre_historia) {
							tabboxContendor
									.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES_5_ANOS);
						}
						tabboxContendor
								.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES_5_ANOS);
					}

				} else if (edad < 5) {
					if (cambiar_nombre_historia) {
						tabboxContendor
								.setLabelTabInicio(IRutas_historia.LABEL_AIEPI_MENOR_2_MESES_5_ANOS);
					}
					tabboxContendor
							.setUrlPaginaInicio(IRutas_historia.PAGINA_AIEPI_MENOR_2_MESES_5_ANOS);
				}

			}

			tabboxContendor.inicializarInicio(false, parametros);

			if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
					|| via_ingreso.equals(IVias_ingreso.SALUD_ORAL)) {
				if (admision_seleccionada.getAtendida()) {
					if (odontologiaTemp != null) {
						cargarEvolucionOdonotologia(odontologiaTemp,
								OpcionesFormulario.REGISTRAR, false);
						// cargarRegClinicoHigiene(odontologiaTemp, false,
						// OpcionesFormulario.REGISTRAR);
					} else {
						throw new Exception(
								"NO hay una historia clinica relacionada a este paciente admitido");
					}
				}
			}

			/*
			 * Permitimos cargar la preescripcion medica para todas las
			 * historias
			 */
			if (mostrar_ordenes_medicas) {
				cargarOrdenesMedicas();
			}

			if (mostrar_notas_enfermeria) {
				cargarNotasEnfermeria();
			}

			if (mostrar_registro_med) {
				cargarRegistroMedicamentos();
			}

			if (mostrar_tuberculosis) {
				cargarTarjetaTuberculosis();
			}

			if (mostrar_prescripcion) {
				cargarPreescripcionMedica();
			}

			if (mostrar_autorizaciones) {
				cargarAutorizaciones();
			}

			if (mostrar_remision) {
				cargarRemisiones();
			}

			if (mostrar_notasaclaratorias) {
				cargarNotasAclaratorias();
			}

			if (mostrar_notas_odontologia) {
				cargarNota_odontologia();
			}

			if (mostrar_evolucion) {
				cargarEvoluciones();
			}

			if (mostrar_epicrisis) {
				cargarEpicrisis();
			}

			if (mostrar_contrarreferencia) {
				cargarContrarreferencia();
			}

			if (mostrar_lepra) {
				cargarFichaLepra_inicio();
				cargarFichaLepra_control();
				cargarFichaLepra_discapacidades();
				cargarFichaLepra_convivientes();
			}
		} else {
			throw new Exception("No hay una admision seleccionada");
		}
	}

	/**
	 * Este metodo permite cargar la tarjeta de tuberculosis
	 *
	 */
	private void cargarTarjetaTuberculosis() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision_seleccionada);
		parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
				Opciones_via_ingreso.REGISTRAR);
		tabboxContendor.abrirPaginaTabDemanda(false,
				IRutas_historia.PAGINA_HC_TUBERCULOSIS,
				IRutas_historia.LABEL_HC_TUBERCULOSIS, parametros);
	}

	/**
	 * Este metodo permite cargar la ficha de inicio de tratamiento lepra
	 *
	 */
	private void cargarFichaLepra_inicio() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision_seleccionada);
		parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
				Opciones_via_ingreso.REGISTRAR);
		tabboxContendor.abrirPaginaTabDemanda(false,
				IRutas_historia.PAGINA_INICIO_TRATAMIENTO_LEPRA,
				IRutas_historia.LABEL_INICIO_TRATAMIENTO_LEPRA, parametros);
	}

	/**
	 * Este metodo permite cargar la ficha de seguimiento y control de
	 * tratamiento lepra
	 *
	 */
	private void cargarFichaLepra_control() {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", tbxNro_identificacion.getValue());

		ficha_inicio_lepraService.setLimit("limit 25 offset 0");

		// log.info("parameters>>>>" + parameters);
		Boolean ficha_inicio = ficha_inicio_lepraService
				.existe_paciente_lepra(parameters);
		// log.info("ficha_inicio>>>>" + ficha_inicio);

		if (ficha_inicio) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_SEGUIMIENTO_TRATAMIENTO_LEPRA,
					IRutas_historia.LABEL_SEGUIMIENTO_TRATAMIENTO_LEPRA,
					parametros);
		}
	}

	/**
	 * Este metodo permite cargar la ficha de valorizacion de discapacidades
	 *
	 */
	private void cargarFichaLepra_discapacidades() {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", tbxNro_identificacion.getValue());
		parameters.put("fecha_actual", new Date());

		// log.info("parameters" + parameters);
		seguimiento_control_pqtService.setLimit("limit 25 offset 0");

		// log.info("parameters>>>>" + parameters);
		Boolean fecha_tratamiento = seguimiento_control_pqtService
				.existe_fecha_fin_tratamiento(parameters);
		// log.info("fecha_tratamiento>>>>" + fecha_tratamiento);

		if (fecha_tratamiento) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_VALORACION_DISCAPACIDADES_LEPRA,
					IRutas_historia.LABEL_VALORACION_DISCAPACIDADES_LEPRA,
					parametros);
		}
	}

	/**
	 * Este metodo permite cargar la ficha de control de convivientes
	 *
	 */
	private void cargarFichaLepra_convivientes() {

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("nro_identificacion", tbxNro_identificacion.getValue());
		parameters.put("fecha_actual", new Date());

		// log.info("parameters" + parameters);
		seguimiento_control_pqtService.setLimit("limit 25 offset 0");

		// log.info("parameters>>>>" + parameters);
		Boolean fecha_tratamiento = seguimiento_control_pqtService
				.existe_fecha_fin_tratamiento(parameters);
		// log.info("fecha_tratamiento>>>>" + fecha_tratamiento);

		if (fecha_tratamiento) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_CONTROL_CONVIVIENTES_LEPRA,
					IRutas_historia.LABEL_CONTROL_CONVIVIENTES_LEPRA,
					parametros);
		}
	}

	/**
	 * Este metodo permite cargar las notas aclaratorias
	 *
	 */
	public void cargarEpicrisis() {
		if (admision_seleccionada != null
				&& admision_seleccionada.getAtendida()) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_HC_EPICRISIS,
					IRutas_historia.LABEL_HC_EPICRISIS, parametros);
		}

	}

	/**
	 * Este metodo permite cargar las notas aclaratorias
	 *
	 */
	private void cargarContrarreferencia() {
		if (admision_seleccionada != null
				&& !admision_seleccionada.getAtendida()) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put("codigo_administradora",
					admision_seleccionada.getCodigo_administradora());
			parametros.put("id_plan", admision_seleccionada.getId_plan());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			parametros.put(IVias_ingreso.OPCION_VIA_INGRESO,
					Opciones_via_ingreso.REGISTRAR);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_CONTRAREFERENCIA,
					IRutas_historia.LABEL_CONTRAREFERENCIA, parametros);
		}

	}

	/**
	 * Este metodo se encarga de llamar las recetas medicas
	 *
	 */
	private void cargarPreescripcionMedica() {
		Map<String, Object> parametros = new HashMap<String, Object>();
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
		tabboxContendor.abrirPaginaTabDemanda(false, "/pages/receta_rips.zul",
				"PRESCRIPCION MEDICA", parametros);
	}

	/**
	 * Este metodo permite cargar las Autorizaciones
	 *
	 */
	private void cargarAutorizaciones() {
		if (parametros_empresa != null) {
			if (parametros_empresa.getTrabaja_autorizacion()) {
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("nro_identificacion",
						admision_seleccionada.getNro_identificacion());
				parametros.put("nro_ingreso",
						admision_seleccionada.getNro_ingreso());
				parametros.put("estado", admision_seleccionada.getEstado());
				parametros.put("tipo_hc", "");
				parametros.put(IVias_ingreso.ADMISION_PACIENTE,
						admision_seleccionada);

				tabboxContendor.abrirPaginaTabDemanda(false,
						IRutas_historia.PAGINA_AUTORIZACIONES,
						"AUTORIZACIONES", parametros);
			}
		}
	}

	public void cargarAgudeza() {
		if (parametros_empresa != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("nro_identificacion",
					admision_seleccionada.getNro_identificacion());
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("estado", admision_seleccionada.getEstado());
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);

			// tabboxContendor.abrirPaginaTabDemanda(false,
			// IRutas_historia.PAGINA_AGUDEZA_VISUAL,"AGUDEZA VISUAL",
			// parametros);
		}
	}

	public void cargarOrdenesMedicas() {
		if (parametros_empresa != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put(IVias_ingreso.ADMISION_PACIENTE,
					admision_seleccionada);
			tabboxContendor.abrirPaginaTabDemanda(false,
					IRutas_historia.PAGINA_ORDENES_MEDICAS,
					IRutas_historia.LABEL_ORDENES_MEDICAS, parametros);
		}
	}

	/**
	 * Este metodo permite cargar las notas de enfermeria
	 *
	 */
	private void cargarNotasEnfermeria() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("admision_seleccionada", admision_seleccionada);
		parametros.put("rol_medico", "S");
		tabboxContendor.abrirPaginaTabDemanda(false,
				"/pages/notas_enfermeria.zul", "NOTAS DE ENFERMERIA",
				parametros);
	}

	/**
	 * Este metodo permite cargar las notas de enfermeria
	 *
	 */
	private void cargarRegistroMedicamentos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("admision_seleccionada", admision_seleccionada);
		parametros.put("rol_medico", "S");
		tabboxContendor.abrirPaginaTabDemanda(false,
				"/pages/registro_medicamentos.zul", "REGISTRO DE MEDICAMENTOS",
				parametros);
	}

	/**
	 * Este metodo permite cargar las notas aclaratorias
	 *
	 */
	private void cargarNotasAclaratorias() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("tipo", INotas.NOTAS_ACLARATORIAS);
		tabboxContendor
				.abrirPaginaTabDemanda(false, "/pages/nota_aclaratoria.zul",
						"NOTAS ACLARATORIAS", parametros);
	}

	/**
	 * Metodo para cargar remisiones Remisiones
	 *
	 *
	 */
	private void cargarRemisiones() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("tipo_hc", "");
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision_seleccionada);

		tabboxContendor.abrirPaginaTabDemanda(false,
				IRutas_historia.PAGINA_REMISIONES, "REMISIONES", parametros);
	}

	/**
	 * Para odontologia
	 *
	 */
	public void cargarNota_odontologia() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("id_plan", admision_seleccionada.getId_plan());
		parametros.put("tipo_hc", "");
		parametros.put("tipo", INotas.NOTAS_ODONTOLOGIA);

		tabboxContendor.abrirPaginaTabDemanda(false,
				"/pages/nota_aclaratoria.zul", "NOTAS ODONTOLOGIA", parametros);
	}

	public void cargarEvolucionOdonotologia(Odontologia odontologia,
			OpcionesFormulario opcion_formulario, boolean primera_vez) {
		/* verificamos si existe una historia odonotologica */
		if (odontologia != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("odontologiaTemp", odontologia);
			parametros.put("admision", admision_seleccionada);
			parametros.put("nro_ingreso",
					admision_seleccionada.getNro_ingreso());
			parametros.put("primera_vez", primera_vez);
			parametros.put("opcion_formulario", opcion_formulario);
			tabboxContendor.abrirPaginaTabDemanda(false,
					"/pages/evolucion_odontologia.zul",
					"EVOLUCION ODONTOLOGIA", parametros);
		}
	}

	// public void cargarRegClinicoHigiene(Odontologia odontologia,
	// boolean desde_historia, OpcionesFormulario opcion_formulario) {
	// /* verificamos si existe una historia odonotologica */
	// if (odontologia != null) {
	// Map<String, Object> parametros = new HashMap<String, Object>();
	// parametros.put("odontologia", odontologia);
	// parametros.put("nro_ingreso",
	// admision_seleccionada.getNro_ingreso());
	// parametros.put(IVias_ingreso.ADMISION_PACIENTE,
	// admision_seleccionada);
	// parametros.put("desde_historia", desde_historia);
	// parametros.put("opcion_formulario", opcion_formulario);
	// tabboxContendor.abrirPaginaTabDemanda(false,
	// "/pages/reg_clinico_higiene.zul", "REG. CLINICO HIGIENE",
	// parametros);
	// }
	// }
	/**
	 * Metodo para cargar evoluciones
	 *
	 *
	 */
	private void cargarEvoluciones() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("nro_identificacion",
				admision_seleccionada.getNro_identificacion());
		parametros.put("nro_ingreso", admision_seleccionada.getNro_ingreso());
		parametros.put("estado", admision_seleccionada.getEstado());
		parametros.put("codigo_administradora",
				admision_seleccionada.getCodigo_administradora());
		parametros.put("tipo_hc", admision_seleccionada.getVia_ingreso() + "-"
				+ tbxTipo_urgencia.getValue());
		parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision_seleccionada);
		parametros.put("ocultar_prestador", "ocultar_prestador");

		tabboxContendor.abrirPaginaTabDemanda(false,
				IRutas_historia.PAGINA_EVOLUCIONES, "EVOLUCIONES CLINICAS",
				parametros);
	}

	@Override
	public void cargarEvolucionOdonotologia(Odontologia odontologia,
			OpcionesFormulario opcion_formulario,
			List<Map<String, Object>> lista_codigos_fac, boolean primera_vez) {

	}

	@Override
	public OpcionesFormulario opcion_formulario(List<String> lista_codigos_fac,
			boolean primera_vez) {

		return null;
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

	public void imprimirTriage(String codigo_historia) {
		if (codigo_historia != null && !codigo_historia.isEmpty()) {
			Map<String, Object> paramRequest = new HashMap<String, Object>();
			paramRequest.put("name_report", "ReporteTriage");
			paramRequest.put("codigo_historia", codigo_historia);

			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", this, paramRequest);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			MensajesUtil.mensajeAlerta(
					"No hay un triage seleccionado para imprimir !!",
					"debe seleccionar un triage !!");
			return;
		}

	}

}
