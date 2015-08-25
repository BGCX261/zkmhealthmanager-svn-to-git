package healthmanager.controller;

import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.bean.Via_ingreso_rol;
import healthmanager.modelo.service.CitasService;
import healthmanager.modelo.service.ConsultorioService;
import healthmanager.modelo.service.Detalles_horariosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Horarios_medicoService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Roles_usuarios_capsService;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfoot;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.HoraTextBox;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.BandboxRegistrosIMG.ISeleccionarItem;
import com.framework.util.MensajesUtil;
import healthmanager.modelo.service.GeneralExtraService;

public class Operaciones_horario_rango_fechaAction extends ZKWindow {

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_prestador_ini;
	@View
	private Toolbarbutton btnLimpiarPrestadorIni;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_centro_ini;
	@View
	private Toolbarbutton btnLimpiarCentroIni;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_prestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_centro;
	@View
	private Toolbarbutton btnLimpiarCentro;
	@View
	private Listbox lbxServicios;
	@View
	private Listbox listboxDetalles;
	@View
	private Datebox dtbxFecha_inicio;
	@View
	private HoraTextBox dtbHora_inicio;
	@View
	private Datebox dtbxFecha_fin;
	@View
	private HoraTextBox dtbHora_fin;
	@View
	private Row rowCentroReemplazo;
	@View
	private Row rowPrestadorReemplazo;
	@View
	private Toolbarbutton btnEjecutar;

	private Map<String, String> vias;
	private Map<String, String> roles;
	private Map<String, String> estados_cita;

	public static final Integer TRASLADAR_CITAS = 1;
	public static final Integer ELIMINAR_HORARIO = 2;

	private Integer opc;

	@Override
	public void init() {
		parametrizarBandbox();
		inicializar();
	}

	@Override
	public void afterCargarDatosSession(HttpServletRequest request) {
		if (request.getParameter("opc") != null) {
			opc = Integer.valueOf(request.getParameter("opc").toString());
		}
	}

	@Override
	public void params(Map<String, Object> map) {

	}

	private void inicializar() {
		List<Elemento> listado_vias = getServiceLocator().getServicio(
				ElementoService.class).listar("via_ingreso");
		vias = new HashMap<String, String>();
		for (Elemento elemento : listado_vias) {
			vias.put(elemento.getCodigo(), elemento.getDescripcion());
		}

		List<Elemento> listado_roles = getServiceLocator().getServicio(
				ElementoService.class).listar("rol");
		roles = new HashMap<String, String>();
		for (Elemento elemento : listado_roles) {
			roles.put(elemento.getCodigo(), elemento.getDescripcion());
		}

		List<Elemento> listado_estados_cita = getServiceLocator().getServicio(
				ElementoService.class).listar("estado_cita");
		estados_cita = new HashMap<String, String>();
		for (Elemento elemento : listado_estados_cita) {
			estados_cita.put(elemento.getCodigo(), elemento.getDescripcion());
		}

		SimpleDateFormat ff = new SimpleDateFormat("hh:mm a");
		try {
			dtbHora_inicio.setValue(ff.parse("06:00 AM"));
			dtbHora_fin.setValue(ff.parse("04:00 PM"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		inicializarCentro(tbxCodigo_centro_ini);

		if (opc == TRASLADAR_CITAS) {
			inicializarCentro(tbxCodigo_centro);
			btnEjecutar.setLabel("Trasladar citas");
		} else if (opc == ELIMINAR_HORARIO) {
			rowCentroReemplazo.setVisible(false);
			rowPrestadorReemplazo.setVisible(false);
			btnEjecutar.setLabel("Eliminar horarios");
			btnEjecutar.setImage("/images/borrar.gif");
		}
	}

	private void cargarServicios(Prestadores prestador, Centro_atencion centro,
			Listbox lbxServicios) {
		Map params = new HashMap<String, Object>();
		params.put("codigo_empresa", codigo_empresa);
		params.put("codigo_sucursal", codigo_sucursal);
		if (centro != null) {
			params.put("codigo_centro", centro.getCodigo_centro());
		}
		params.put("codigo_usuario", prestador.getNro_identificacion());
		List<Roles_usuarios_caps> lista_roles = getServiceLocator()
				.getServicio(Roles_usuarios_capsService.class).listar(params);
		lbxServicios.getChildren().clear();
		if (opc == TRASLADAR_CITAS) {
			lbxServicios.appendItem("-- SELECCIONE --", "");
		} else {
			lbxServicios.appendItem("TODOS", "");
		}

		for (Roles_usuarios_caps rols : lista_roles) {
			lbxServicios.appendItem(roles.get(rols.getRol()), rols.getRol());
		}
		if (lbxServicios.getItemCount() == 2) {
			lbxServicios.setSelectedIndex(1);
		} else {
			lbxServicios.setSelectedIndex(0);
		}
	}

	private void parametrizarBandbox() {
		parametrizarBandboxPrestador();
		parametrizarBandboxPrestadorIni();
		parametrizarBandboxCentro(tbxCodigo_centro, btnLimpiarCentro,
				tbxCodigo_prestador);
		parametrizarBandboxCentro(tbxCodigo_centro_ini, btnLimpiarCentroIni,
				tbxCodigo_prestador_ini);
	}

	private void parametrizarBandboxCentro(
			final BandboxRegistrosMacro tbxCodigo_centro,
			Toolbarbutton btnLimpiarCentro,
			final BandboxRegistrosMacro tbxCodigo_prestador) {
		tbxCodigo_centro.inicializar(null, btnLimpiarCentro);
		tbxCodigo_centro
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Centro_atencion>() {

					@Override
					public void renderListitem(Listitem listitem,
							Centro_atencion registro) {
						listitem.appendChild(new Listcell(registro
								.getCodigo_centro()));
						listitem.appendChild(new Listcell(registro
								.getNombre_centro()));
					}

					@Override
					public List<Centro_atencion> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						return getCentro_atencions(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Centro_atencion registro) {
						bandbox.setValue(registro.getCodigo_centro() + "-"
								+ registro.getNombre_centro());
						((BandboxRegistrosMacro) bandbox).setObject(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						((BandboxRegistrosMacro) bandbox).setObject(null);
						tbxCodigo_prestador.setObject(null);
					}
				});
	}

	private void parametrizarBandboxPrestador() {
		tbxCodigo_prestador.inicializar(new Textbox(), btnLimpiarPrestador);
		tbxCodigo_prestador
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {

					@Override
					public void renderListitem(Listitem listitem,
							Prestadores registro) {

						Especialidad especialidad = new Especialidad();
						especialidad.setCodigo(registro
								.getCodigo_especialidad());
						especialidad = getServiceLocator()
								.getEspecialidadService().consultar(
										especialidad);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombres()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellidos()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_prestador().equals("01") ? "MÉDICO"
								: "ENFERMERA"));
						listitem.appendChild(listcell);

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

						Centro_atencion centro = (Centro_atencion) tbxCodigo_centro
								.getRegistroSeleccionado();
						parametros.put(
								"codigo_centro",
								((centro == null || centro.getCodigo_centro()
										.isEmpty()) ? centro_atencion_session
										.getCodigo_centro() : centro
										.getCodigo_centro()));
						if (lbxServicios.getSelectedItem().getValue() != null
								&& lbxServicios.getSelectedIndex() > 0) {
							parametros.put("rol", lbxServicios
									.getSelectedItem().getValue().toString());
						}
						parametros.put("limite_paginado", "limit 25 offset 0");
						return getServiceLocator().getPrestadoresService()
								.listarPorCentro(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Prestadores registro) {
						bandbox.setValue(registro.getNro_identificacion()
								+ " - " + registro.getNombres() + " "
								+ registro.getApellidos());
						textboxInformacion.setValue(registro.getNombres() + " "
								+ registro.getApellidos());
						Especialidad especialidad = new Especialidad();
						especialidad.setCodigo(registro
								.getCodigo_especialidad());
						especialidad = getServiceLocator()
								.getEspecialidadService().consultar(
										especialidad);

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}

				});

		tbxCodigo_prestador.setSeleccionarItem(new ISeleccionarItem() {

			@Override
			public void accion(Object registro) {
				btnLimpiarPrestador.setVisible(false);
			}
		});
	}

	private void parametrizarBandboxPrestadorIni() {
		tbxCodigo_prestador_ini.inicializar(new Textbox(),
				btnLimpiarPrestadorIni);
		tbxCodigo_prestador_ini
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {

					@Override
					public void renderListitem(Listitem listitem,
							Prestadores registro) {

						Especialidad especialidad = new Especialidad();
						especialidad.setCodigo(registro
								.getCodigo_especialidad());
						especialidad = getServiceLocator()
								.getEspecialidadService().consultar(
										especialidad);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombres()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellidos()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_prestador().equals("01") ? "MÉDICO"
								: "ENFERMERA"));
						listitem.appendChild(listcell);

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
						Centro_atencion centro = (Centro_atencion) tbxCodigo_centro_ini
								.getObject();
						parametros.put(
								"codigo_centro",
								((centro == null || centro.getCodigo_centro()
										.isEmpty()) ? centro_atencion_session
										.getCodigo_centro() : centro
										.getCodigo_centro()));
						parametros.put("limite_paginado", "limit 25 offset 0");
						return getServiceLocator().getPrestadoresService()
								.listarPorCentro(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Prestadores registro) {
						bandbox.setValue(registro.getNro_identificacion()
								+ " - " + registro.getNombres() + " "
								+ registro.getApellidos());
						textboxInformacion.setValue(registro.getNombres() + " "
								+ registro.getApellidos());
						Especialidad especialidad = new Especialidad();
						especialidad.setCodigo(registro
								.getCodigo_especialidad());
						especialidad = getServiceLocator()
								.getEspecialidadService().consultar(
										especialidad);
						cargarServicios(registro,
								(Centro_atencion) tbxCodigo_centro_ini
										.getRegistroSeleccionado(),
								lbxServicios);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}

				});

		tbxCodigo_prestador_ini.setSeleccionarItem(new ISeleccionarItem() {

			@Override
			public void accion(Object registro) {
				btnLimpiarPrestadorIni.setVisible(false);
			}
		});
	}

	public List<Centro_atencion> getCentro_atencions(
			Map<String, Object> parametros) {

		return getServiceLocator().getCentro_atencionService().listar(
				parametros);
	}

	public void onSeleccionarRolMedico() {

	}

	private void inicializarCentro(BandboxRegistrosMacro tbxCodigo_centro) {
		List<Centro_atencion> centro_atencions = getCentro_atencions(new HashMap<String, Object>());
		if (centro_atencions.size() == 1) {
			Centro_atencion centro_atencion = centro_atencions.get(0);
			tbxCodigo_centro.seleccionarRegistro(
					centro_atencion,
					centro_atencion.getCodigo_centro() + "-"
							+ centro_atencion.getNombre_centro(), "");
		} else {
			Centro_atencion centro_atencion = centro_atencion_session;
			tbxCodigo_centro.seleccionarRegistro(
					centro_atencion,
					centro_atencion.getCodigo_centro() + "-"
							+ centro_atencion.getNombre_centro(), "");
		}
	}

	public void seleccionarServicio() {

	}

	public void previsualizarDatos() {
		Centro_atencion centro = tbxCodigo_centro_ini.getRegistroSeleccionado();
		Prestadores prestador = tbxCodigo_prestador_ini
				.getRegistroSeleccionado();
		String servicio = lbxServicios.getSelectedItem().getValue();
		Timestamp ini = obtenerFechasComponentes(dtbxFecha_inicio,
				dtbHora_inicio);
		Timestamp fin = obtenerFechasComponentes(dtbxFecha_fin, dtbHora_fin);

		if (opc == TRASLADAR_CITAS) {
			if (centro == null) {
				MensajesUtil.notificarAlerta(
						"No se ha seleccionado el centro de salud",
						tbxCodigo_centro_ini);
			} else if (prestador == null) {
				MensajesUtil.notificarAlerta(
						"No se ha seleccionado el prestador",
						tbxCodigo_prestador_ini);
			} else if (!fin.after(ini)) {
				MensajesUtil
						.notificarAlerta(
								"La fecha inicial no puede ser superior a la fecha inicial",
								dtbxFecha_fin);
			} else {
				cargarCitas(centro, prestador, servicio, ini, fin);
			}
		} else if (opc == ELIMINAR_HORARIO) {
			if (centro == null) {
				MensajesUtil.notificarAlerta(
						"No se ha seleccionado el centro de salud",
						tbxCodigo_centro_ini);
			} else if (prestador == null) {
				MensajesUtil.notificarAlerta(
						"No se ha seleccionado el prestador",
						tbxCodigo_prestador_ini);
			} else if (!fin.after(ini)) {
				MensajesUtil
						.notificarAlerta(
								"La fecha inicial no puede ser superior a la fecha inicial",
								dtbxFecha_fin);
			} else {
				mostrarHorario(centro, prestador, servicio, ini, fin);
			}
		}
	}

	private void mostrarHorario(Centro_atencion centro, Prestadores prestador,
			String servicio, Timestamp ini, Timestamp fin) {

		Map param = new HashMap<String, Object>();
		param.put("codigo_empresa", codigo_empresa);
		param.put("codigo_sucursal", codigo_sucursal);
		param.put("fecha_ini", ini);
		param.put("fecha_fin", fin);
		param.put("codigo_medico", prestador.getNro_identificacion());
		param.put("codigo_centro", centro.getCodigo_centro());
		if (!servicio.isEmpty()) {
			param.put("codigo_rol", servicio);
		}
		// log.info(">>>>>>>>"+param);
		List<Detalles_horarios> listado_detalles = getServiceLocator()
				.getServicio(Detalles_horariosService.class).listar(param);
		log.info("Consultando listado de detalles ===> "
				+ listado_detalles.size());
		listboxDetalles.setVisible(!listado_detalles.isEmpty());
		btnEjecutar.setDisabled(listado_detalles.isEmpty());
		if (!listado_detalles.isEmpty()) {
			for (Component componente : listboxDetalles.getChildren()) {
				if (componente instanceof Listhead) {
					Listhead lh = (Listhead) componente;
					lh.getChildren().clear();
				} else if (componente instanceof Listfoot) {
					Listfoot lf = (Listfoot) componente;
					lf.getChildren().clear();
				}
			}
			listboxDetalles.getChildren().clear();
			Listhead listhead = new Listhead();
			listhead.appendChild(new Listheader("Fecha Horario", "", "240px"));
			listhead.appendChild(new Listheader("Consultorio"));
			listhead.appendChild(new Listheader("Servicio"));
			listhead.appendChild(new Listheader("Estado", "", "100px"));
			listboxDetalles.appendChild(listhead);
			for (Detalles_horarios detalle : listado_detalles) {

				String fecha = new java.text.SimpleDateFormat(
						"EEEEE dd MMMMM yyyy hh:mm a", IConstantes.locale)
						.format(detalle.getFecha_inicial());
				Consultorio consultorio = new Consultorio();
				consultorio.setCodigo_centro(detalle.getCodigo_centro());
				consultorio.setCodigo_consultorio(detalle
						.getCodigo_consultorio());
				consultorio.setCodigo_sucursal(detalle.getCodigo_sucursal());
				consultorio.setCodigo_empresa(detalle.getCodigo_empresa());
				consultorio = getServiceLocator().getServicio(
						ConsultorioService.class).consultar(consultorio);

				Citas cita = new Citas();
				cita.setCodigo_empresa(detalle.getCodigo_empresa());
				cita.setCodigo_sucursal(detalle.getCodigo_sucursal());
				cita.setCodigo_detalle_horario(detalle.getConsecutivo());
				cita = getServiceLocator().getServicio(CitasService.class)
						.consultar(cita);
				String estado = "Libre";
				String color = "black";

				if (cita != null) {
					estado = estados_cita.get(cita.getEstado());
					if (cita.getEstado().equals("1")) {
						color = "blue";
					} else if (cita.getEstado().equals("2")) {
						color = "green";
					} else if (cita.getEstado().equals("5")) {
						color = "red";
					} else if (cita.getEstado().equals("6")) {
						color = "violet";
					}
				}

				Listitem listitem = new Listitem();

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(fecha));
				listcell.setStyle("color:" + color + "; font-weight:bold");
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(consultorio.getNombre()));
				listcell.setStyle("color:" + color + "; font-weight:bold");
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(roles.get(detalle
						.getCodigo_rol())));
				listcell.setStyle("color:" + color + "; font-weight:bold");
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(estado));
				listcell.setStyle("color:" + color + "; font-weight:bold");
				listitem.appendChild(listcell);

				listboxDetalles.appendChild(listitem);
			}
			Listfoot listfoot = new Listfoot();
			Listfooter listfooter = new Listfooter("Detalles horario: "
					+ listado_detalles.size());
			listfoot.appendChild(listfooter);
			listboxDetalles.appendChild(listfoot);
		}
	}

	public void ejecutarProceso() {
		Centro_atencion centro = tbxCodigo_centro_ini.getRegistroSeleccionado();
		Centro_atencion centro_traslado = tbxCodigo_centro
				.getRegistroSeleccionado();
		Prestadores prestador = tbxCodigo_prestador_ini
				.getRegistroSeleccionado();
		Prestadores prestador_traslado = tbxCodigo_prestador
				.getRegistroSeleccionado();
		Timestamp ini = obtenerFechasComponentes(dtbxFecha_inicio,
				dtbHora_inicio);
		Timestamp fin = obtenerFechasComponentes(dtbxFecha_fin, dtbHora_fin);
		String servicio = lbxServicios.getSelectedItem().getValue();

		if (opc == TRASLADAR_CITAS) {
			if (centro == null) {
				MensajesUtil.notificarAlerta(
						"No se ha seleccionado el centro de salud",
						tbxCodigo_centro_ini);
			} else if (prestador == null) {
				MensajesUtil.notificarAlerta(
						"No se ha seleccionado el prestador",
						tbxCodigo_prestador_ini);
			} else if (!fin.after(ini)) {
				MensajesUtil
						.notificarAlerta(
								"La fecha inicial no puede ser superior a la fecha inicial",
								dtbxFecha_fin);
			} else {
				tasladarCitasRangoFecha(prestador, prestador_traslado,
						centro.getCodigo_centro(),
						centro_traslado.getCodigo_centro(), ini, fin);
			}
		} else if (opc == ELIMINAR_HORARIO) {
			if (centro == null) {
				MensajesUtil.notificarAlerta(
						"No se ha seleccionado el centro de salud",
						tbxCodigo_centro_ini);
			} else if (prestador == null) {
				MensajesUtil.notificarAlerta(
						"No se ha seleccionado el prestador",
						tbxCodigo_prestador_ini);
			} else if (!fin.after(ini)) {
				MensajesUtil
						.notificarAlerta(
								"La fecha inicial no puede ser superior a la fecha inicial",
								dtbxFecha_fin);
			} else {
				eliminarHorario(prestador, centro.getCodigo_centro(), servicio,
						ini, fin);
			}
		}

	}

	private void cargarCitas(Centro_atencion centro, Prestadores prestador,
			String servicio, Timestamp ini, Timestamp fin) {

		Map param = new HashMap<String, Object>();
		param.put("codigo_empresa", codigo_empresa);
		param.put("codigo_sucursal", codigo_sucursal);
		param.put("fecha_inicial", ini);
		param.put("fecha_final", fin);
		param.put("rango", "rango");
		param.put("codigo_prestador", prestador.getNro_identificacion());
		param.put("codigo_centro", centro.getCodigo_centro());
		param.put("vias_ingreso", getViasDesdeRol(servicio));
		param.put("estado", '1');
		// log.info(">>>>>"+param);
		List<Citas> citas = getServiceLocator().getServicio(CitasService.class)
				.listar(param);
		listboxDetalles.setVisible(!citas.isEmpty());
		btnEjecutar.setDisabled(citas.isEmpty());
		if (!citas.isEmpty()) {
			for (Component componente : listboxDetalles.getChildren()) {
				if (componente instanceof Listhead) {
					Listhead lh = (Listhead) componente;
					lh.getChildren().clear();
				} else if (componente instanceof Listfoot) {
					Listfoot lf = (Listfoot) componente;
					lf.getChildren().clear();
				}
			}
			listboxDetalles.getChildren().clear();
			Listhead listhead = new Listhead();
			listhead.appendChild(new Listheader("Fecha Cita", "", "140px"));
			listhead.appendChild(new Listheader("Id. Paciente", "", "100px"));
			listhead.appendChild(new Listheader("Paciente"));
			listhead.appendChild(new Listheader("Programa"));
			listboxDetalles.appendChild(listhead);
			for (Citas cita : citas) {

				String fecha = new java.text.SimpleDateFormat(
						"dd/MM/yyyy hh:mm a").format(cita.getFecha_cita());

				Paciente p = new Paciente();
				p.setNro_identificacion(cita.getNro_identificacion());
				p.setCodigo_empresa(codigo_empresa);
				p.setCodigo_sucursal(codigo_sucursal);
				p = getServiceLocator().getServicio(PacienteService.class)
						.consultar(p);

				Listitem listitem = new Listitem();

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(fecha));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(p.getDocumento()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(p.getNombreCompleto()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(
						vias.get(cita.getTipo_consulta())));
				listitem.appendChild(listcell);
				listboxDetalles.appendChild(listitem);
			}
			Listfoot listfoot = new Listfoot();
			Listfooter listfooter = new Listfooter("Citas pendientes: "
					+ citas.size());
			listfoot.appendChild(listfooter);
			listboxDetalles.appendChild(listfoot);
		}
	}

	private void eliminarHorario(Prestadores registroSeleccionado,
			String codigo_centro, String servicio, Timestamp fecha_ini,
			Timestamp fecha_fin) {
		final Map param = new HashMap<String, Object>();
		param.put("codigo_empresa", codigo_empresa);
		param.put("codigo_sucursal", codigo_sucursal);
		param.put("fecha_ini", fecha_ini);
		param.put("fecha_fin", fecha_fin);
		param.put("codigo_medico", registroSeleccionado.getNro_identificacion());
		param.put("codigo_centro", codigo_centro);

		if (!servicio.isEmpty()) {
			param.put("codigo_rol", servicio);
		}
		final List<Detalles_horarios> detalles = getServiceLocator()
				.getServicio(Detalles_horariosService.class).listar(param);
		if (detalles.size() > 0) {
			Messagebox.show("Esta seguro que desea eliminar " + detalles.size()
					+ " detalles horario? ", "Eliminar Registros",
					Messagebox.YES + Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event event) throws Exception {
							if ("onYes".equals(event.getName())) {
								try {
									getServiceLocator().getServicio(
											Detalles_horariosService.class)
											.eliminarValidandoCitas(param);
									// for (Detalles_horarios detalles_horarios
									// : detalles) {
									// getServiceLocator().getServicio(Detalles_horariosService.class).eliminar(detalles_horarios);
									// }
									previsualizarDatos();
									MensajesUtil
											.mensajeInformacion(
													"Eliminacion completada",
													"Eliminacion realizada satisfactoriamente!!!");
								} catch (Exception ex) {
									MensajesUtil.mensajeAlerta("Alerta",
											ex.getMessage());
								}
							}
						}
					});
		}
	}

	public void tasladarCitasRangoFecha(Prestadores prestador_origen,
			Prestadores prestador_destino, String centro,
			String centro_traslado, Timestamp fecha_ini, Timestamp fecha_fin) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			List<String> estados = new ArrayList<String>();
			estados.add("1");
			parametros.put("inner_citas_estados", estados);
			parametros.put("codigo_medico",
					prestador_origen.getNro_identificacion());
			parametros.put("fecha_ini", fecha_ini);
			parametros.put("fecha_fin", fecha_fin);
			List<Detalles_horarios> listado_detalles_aux = getServiceLocator()
					.getServicio(Detalles_horariosService.class).listar(
							parametros);
			parametros = new HashMap<String, Object>();
			parametros.put("prestadores", prestador_origen);
			parametros.put("codigo_centro", centro);
			parametros.put("prestadores_traslado", prestador_destino);
			parametros.put("codigo_centro_trasalado", centro_traslado);
			parametros.put("listado_detalles_aux", listado_detalles_aux);
			parametros.put("creacion_user", usuarios.getCodigo());

			if (listado_detalles_aux.size() > 0) {
				getServiceLocator().getServicio(Horarios_medicoService.class)
						.guardarTrasladoCitas(parametros);
				previsualizarDatos();
				MensajesUtil.mensajeInformacion("Informacion",
						"Traslado hecho satisfactoriamente");
			} else {
				MensajesUtil
						.mensajeAlerta("Alerta",
								"No hay secuencias de horario en este rango de fechas!");
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private Timestamp obtenerFechasComponentes(Datebox datebox,
			HoraTextBox horabox) {
		return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ")
				.format(datebox.getValue())
				+ new SimpleDateFormat("HH:mm:00").format(horabox.getValue()));
	}

	private List<String> getViasDesdeRol(String rol) {
		List<Via_ingreso_rol> via_ingreso_roles;
		List<String> vias = null;
		Map param = new HashMap<String, Object>();
		param.put("codigo_empresa", codigo_empresa);
		param.put("codigo_rol", rol);
		via_ingreso_roles = getServiceLocator().getServicio(
				GeneralExtraService.class).listar(Via_ingreso_rol.class, param);
		if (via_ingreso_roles != null && via_ingreso_roles.size() > 0) {
			vias = new ArrayList<String>();
			for (Via_ingreso_rol via_ingreso_rol : via_ingreso_roles) {
				vias.add(via_ingreso_rol.getCodigo_via_ingreso());
			}
		}
		return vias;
	}
}
