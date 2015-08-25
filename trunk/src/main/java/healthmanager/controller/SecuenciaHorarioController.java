package healthmanager.controller;

import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Festivos;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.service.ConsultorioService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.GeneralExtraService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.helper.DateFormatterHelper;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.HoraTextBox;
import com.framework.macros.HoraTextBox.OnCambioHora;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.L2HContraintDate;
import com.framework.res.ZKSimpleCalendarEvent;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.framework.util.secuencia_hora.ArquitectoVisualizarHoraUtils;
import com.framework.util.secuencia_hora.ArquitectoVisualizarHoraUtils.EventoSecuenciaHoras;
import com.framework.util.secuencia_hora.Hora;

public class SecuenciaHorarioController extends ZKWindow {

	@DayId(id = Calendar.SUNDAY)
	@View
	private Checkbox chkDomingo;
	@DayId(id = Calendar.MONDAY)
	@View
	private Checkbox chkLunes;
	@DayId(id = Calendar.TUESDAY)
	@View
	private Checkbox chkMartes;
	@DayId(id = Calendar.THURSDAY)
	@View
	private Checkbox chkMiercoles;
	@DayId(id = Calendar.WEDNESDAY)
	@View
	private Checkbox chkJueves;
	@DayId(id = Calendar.FRIDAY)
	@View
	private Checkbox chkViernes;
	@DayId(id = Calendar.SATURDAY)
	@View
	private Checkbox chkSabado;

	@View
	private Checkbox chkFestivos;

	@View
	private HoraTextBox dtbinit;
	@View
	private Row rowEnd;
	@View
	private HoraTextBox dtbend;
	@View
	private Spinner ibxSecuencia;
	@View
	private Combobox cbbcolor;
	@View
	private Row rowDescripcion;
	@View
	private Textbox txbDescripcion;
	@View
	private Checkbox ckbcitas;
	@View
	private Toolbarbutton okBtn;
	@View
	private Toolbarbutton cancelBtn;
	@View
	private Datebox dateboxInit;
	@View
	private Datebox dateboxEnd;
	@View
	private Iframe ifmVisualizador;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_centro;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_consultorio;
	@View
	private Toolbarbutton btnLimpiarCentro;
	@View
	private Toolbarbutton btnLimpiarConsultorio;
	@View
	private Listbox lbxRoles;
	@View
	private Row rowRolEspecifico;
	@View
	private Listbox lbxRolesExcepciones;

	private ConsultorioService consultorioService;

	private Integer horas_medico;

	private final int MAX_VALOR_SECUENCIA = 60;

	private Integer tiempo_cita;

	private String codigo_medico;

	private Boolean recargar_horario = false;

	private Integer agregados = 0;

	private String accion_formulario;

	private Date fecha_it;

	/**
	 * Esta anotacion es para identificar los checkbox, con respecto a cada dia
	 * de la semana
	 * 
	 * @author Luis Miguel
	 * */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface DayId {
		int id();
	}

	private HorariosAction parent;

	private String rol_seleccionado;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void init() {
		this.parent = (HorariosAction) getParent();
		initTime();
		cargarEventos();
		parametrizarBandbox();
		inicializarCentro();
		recargar_horario = false;
		this.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				if (recargar_horario) {
					parent.recargarHorarioPrestador();
				}
			}
		});
		dtbend.setOnCambioHora(new OnCambioHora() {
			@Override
			public void onCambio(HoraTextBox horaTextBox) {
				onChangeHoraFinal();
				
			}
		});
	}
	
	private void inicializarCentro() {
		List<Centro_atencion> centro_atencions = getCentro_atencions(new HashMap<String, Object>());
		if (centro_atencions.size() == 1) {
			Centro_atencion centro_atencion = centro_atencions.get(0);
			tbxCodigo_centro.seleccionarRegistro(
					centro_atencion,
					centro_atencion.getCodigo_centro() + "-"
							+ centro_atencion.getNombre_centro(), "");
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		horas_medico = (Integer) map.get("horas_medico");
		accion_formulario = (String) map.get("accion_formulario");
		codigo_medico = (String) map.get("codigo_medico");
		rol_seleccionado = (String) map.get("rol_seleccionado");
		tiempo_cita = (Integer) map.get("tiempo_cita");
		fecha_it = (Date) map.get("date");
		if (tiempo_cita == null) {
			tiempo_cita = 15;
		}
		//log.info("accion_formulario ===> " + accion_formulario);
	}

	private void parametrizarBandbox() {
		parametrizarBandboxCentro();
		parametrizarBandboxConsultorios();
		listarRoles();
		listarRolesExcepciones();
		onSeleccionarRolMedico();
		for (int i = 0; i < lbxRoles.getItemCount(); i++) {
			Listitem listitem = lbxRoles.getItemAtIndex(i);
			if (((Roles_usuarios) listitem.getValue()).getRol().equals(
					rol_seleccionado)) {
				listitem.setSelected(true);
				break;
			}
		}
	}

	private void listarRolesExcepciones() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("tipo", "via_ingreso");
		parametros.put("codigos", new String[] {
				IVias_ingreso.HC_DETECCION_ALT_EMBARAZO,
				IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS,
				IVias_ingreso.HC_MENOR_2_MESES_2_ANOS,
				IVias_ingreso.HC_MENOR_2_MESES, IVias_ingreso.ADULTO_MAYOR,
				IVias_ingreso.SALUD_ORAL, IVias_ingreso.ALTERACION_JOVEN,
				IVias_ingreso.HIPERTENSO_DIABETICO,
				IVias_ingreso.PLANIFICACION_FAMILIAR });

		lbxRolesExcepciones.getChildren().clear();
		List<Elemento> listado_roles = getServiceLocator().getServicio(
				ElementoService.class).listar(parametros);
		boolean seleccionar = false;
		for (Elemento elementos_vias_ingresos : listado_roles) {
			Listitem listitem = new Listitem();
			listitem.setValue(elementos_vias_ingresos);
			listitem.setLabel(elementos_vias_ingresos.getDescripcion()
					.toUpperCase());
			lbxRolesExcepciones.appendChild(listitem);
		}

		if (!seleccionar && lbxRolesExcepciones.getItemCount() > 0) {
			lbxRolesExcepciones.setSelectedIndex(0);
		}
	}

	private void listarRoles() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("codigo_usuario", codigo_medico);

		lbxRoles.getChildren().clear();
		List<Roles_usuarios> listado_roles = getServiceLocator()
				.getRoles_usuariosService().listar(parametros);
		boolean seleccionar = false;
		for (Roles_usuarios roles_usuarios : listado_roles) {
			if (Utilidades.isMedico(roles_usuarios.getRol())
					|| Utilidades.isEnfermeraJefe(roles_usuarios.getRol())) {
				Listitem listitem = new Listitem();
				listitem.setValue(roles_usuarios);
				listitem.setLabel(roles_usuarios.getElementoRol()
						.getDescripcion()
						+ " ("
						+ roles_usuarios.getHoras_medico() + " horas)");
				lbxRoles.appendChild(listitem);
			}
		}

		if (!seleccionar && lbxRoles.getItemCount() > 0) {
			lbxRoles.setSelectedIndex(0);
		}
	}

	private void parametrizarBandboxConsultorios() {
		tbxCodigo_consultorio.inicializar(null, btnLimpiarConsultorio);
		tbxCodigo_consultorio
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Consultorio>() {
					@Override
					public void renderListitem(Listitem listitem,
							Consultorio registro) {
						listitem.appendChild(new Listcell(registro
								.getCodigo_centro()));
						listitem.appendChild(new Listcell(registro.getNombre()));
					}

					@Override
					public List<Consultorio> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("codigo_centro",
								((Centro_atencion) tbxCodigo_centro
										.getRegistroSeleccionado())
										.getCodigo_centro());
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						parametros.put("limite_paginado", "limit 25 offset 0");
						return consultorioService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Consultorio registro) {
						bandbox.setValue(registro.getCodigo_centro() + "-"
								+ registro.getNombre());
						// textboxInformacion.setValue(registro.getNombre());
						((BandboxRegistrosMacro) bandbox).setObject(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						((BandboxRegistrosMacro) bandbox).setObject(null);
					}
				});
	}

	private void parametrizarBandboxCentro() {
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
						// textboxInformacion.setValue(registro.getNombre_centro());
						((BandboxRegistrosMacro) bandbox).setObject(registro);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						((BandboxRegistrosMacro) bandbox).setObject(null);
						tbxCodigo_consultorio.limpiarSeleccion(true);
					}
				});
	}

	public List<Centro_atencion> getCentro_atencions(
			Map<String, Object> parametros) {
		Roles_usuarios roles_usuarios = (lbxRoles.getSelectedItem() != null ? (Roles_usuarios) lbxRoles
				.getSelectedItem().getValue() : null);
		parametros.put("codigo_empresa", empresa.getCodigo_empresa());
		parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parametros.put("rol_usuario",
				roles_usuarios != null ? roles_usuarios.getRol() : "");
		parametros.put("usuario",
				roles_usuarios != null ? roles_usuarios.getCodigo_usuario()
						: "");
		return getServiceLocator().getCentro_atencionService().listar(
				parametros);
	}

	public void onSeleccionarRolMedico() {
		if (lbxRoles.getSelectedItem() != null) {
			Roles_usuarios roles_usuarios = (Roles_usuarios) lbxRoles
					.getSelectedItem().getValue();
			if (Utilidades.isMedicoPyP(roles_usuarios.getRol())
					|| Utilidades.isAuxiliarOEnfermeraPyP(roles_usuarios
							.getRol())) {
				if (Utilidades.isAuxiliarOEnfermeraPyP(roles_usuarios.getRol())) {
					rowRolEspecifico.setVisible(true);
				} else {
					rowRolEspecifico.setVisible(false);
				}
			} else {
				rowRolEspecifico.setVisible(false);
			}
		} else {
			rowRolEspecifico.setVisible(false);
		}
	}

	private void cargarEventos() {
		ibxSecuencia.addEventListener(Events.ON_BLUR,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event evt) throws Exception {
						validarSecuencia();
					}
				});
		aplicarIndicador(chkDomingo, chkJueves, chkLunes, chkMartes,
				chkMiercoles, chkSabado, chkViernes);
	}

	public void validarSecuencia() throws Exception {
		Integer value = ibxSecuencia.getValue();
		if (value != null) {
			if (value.intValue() > MAX_VALOR_SECUENCIA) {
				MensajesUtil.notificar(
						"La secuencia no se puede pasar de 60 Minutos",
						Clients.NOTIFICATION_TYPE_WARNING, ibxSecuencia);
			} else {
				visualizarHorario(value.intValue());
			}
		}
	}

	/**
	 * En este metodo podemos visualizar, el horario en el dia. ejemplo
	 * <table border="1">
	 * <tr>
	 * <th style="background-color:#f0f0f0">.: Secuencia de Horario :.</th>
	 * </tr>
	 * <tr>
	 * <th>07:00 AM - 07:30 AM</th>
	 * </tr>
	 * <tr>
	 * <th>08:00 AM - 08:30 AM</th>
	 * </tr>
	 * <tr>
	 * <th>09:00 AM - 09:30 AM</th>
	 * </tr>
	 * <tr>
	 * <th>...</th>
	 * </tr>
	 * </table>
	 * 
	 * @author Luis Miguel
	 * @param i
	 * */
	protected void visualizarHorario(int secuencia) throws Exception {
		AMedia amedia = new AMedia("vizualizador.html", "html", "text/html",
				ArquitectoVisualizarHoraUtils.visualizar(
						new Hora(dtbinit.getValue()), secuencia, new Hora(
								dtbend.getValue())));
		ifmVisualizador.setContent(amedia);
	}

	public void aplicarIndicadorr(Checkbox checkbox) {
		aplicarIndicador(checkbox);
	}

	private void aplicarIndicador(Checkbox... checkbox) {
		for (Checkbox checkboxTemp : checkbox) {
			if (checkboxTemp.isChecked()) {
				checkboxTemp.setStyle("color:blue;");
			} else {
				checkboxTemp.setStyle("color:black;");
			}
		}
	}

	private void initTime() {

		ibxSecuencia.setValue(tiempo_cita);
		dateboxInit.setValue(fecha_it);
		// dtbinit.setValue(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_it);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR, calendar.getActualMaximum(Calendar.HOUR));
		dateboxEnd.setValue(calendar.getTime());
		// dtbend.setValue(calendar.getTime());

		dtbinit.setOnCambioHora(new OnCambioHora() {
			@Override
			public void onCambio(HoraTextBox horaTextBox) {
				onChangeHoraInicial();
				
			}
		});
	}

	public void onChangeHoraInicial() {
		Date hora_inicial = dtbinit.getValue();
		Calendar calendar_hora = Calendar.getInstance();
		calendar_hora.setTime(hora_inicial);
		if (horas_medico == null)
			horas_medico = 8;
		calendar_hora.set(Calendar.HOUR, calendar_hora.get(Calendar.HOUR)
				+ horas_medico);
		dtbend.setValue(calendar_hora.getTime());
		try {
			validarSecuencia();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onChangeHoraFinal() {
		try {
			validarSecuencia();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Este metodo es para generar la secuencia del horario tomado desde el dia
	 * inicial hasta el dia final, en es secuencia toma dia 1, con una
	 * subcesuencia que son las horas de ese dia, generadas por la utilidad
	 * <b>ArquitectoVisualizarHoraUtils</b> la cual contiene una metodo
	 * visualizar en el cual por medio de una interface
	 * <b>EventoSecuenciaHoras</b> debuelve la secuencia.
	 * 
	 * @author Luis Miguel Hernández Pérez
	 * */
	public void generarSecuenciaHorario() throws Exception {
		try {
			if (validar()) {
				Date dateInit = innerTime(dateboxInit.getValue(),
						dtbinit.getValue());
				Date dateEnd = innerTime(dateboxEnd.getValue(),

				dtbend.getValue());

				/* consultamos los festivos entre el rango de fecha */
				List<Festivos> festivos = null;
				if (!chkFestivos.isChecked()) {
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("fecha_inicio", dateboxInit.getValue());
					parameters.put("fecha_final", dateboxEnd.getValue());
					festivos = getServiceLocator().getServicio(
							GeneralExtraService.class).listar(Festivos.class,parameters);
				}
				agregados = 0;
				do {
					if (isActiveDate(dateInit, festivos)) {
						final Date date = dateInit;
						/* agregamos las secuencias entre rangos de horas */
						ArquitectoVisualizarHoraUtils.visualizar(new Hora(
								dtbinit.getValue()), ibxSecuencia.getValue()
								.intValue(), new Hora(dtbend.getValue()),
								new EventoSecuenciaHoras() {
									public void rangoHora(Date horaInicial,
											Date horaFinal) throws Exception {
										/*
										 * en esta parte agregamos la secuencia
										 */
										ZKSimpleCalendarEvent sce = new ZKSimpleCalendarEvent();
										sce.setBeginDate(innerTime(date,
												horaInicial));
										sce.setEndDate(innerTime(date,
												horaFinal));
										sce.setCodigo_centro_salud(((Centro_atencion) tbxCodigo_centro
												.getRegistroSeleccionado())
												.getCodigo_centro());
										sce.setCodigo_consulorio(((Consultorio) tbxCodigo_consultorio
												.getRegistroSeleccionado())
												.getCodigo_consultorio());
										sce.setConsultorio((Consultorio) tbxCodigo_consultorio
												.getRegistroSeleccionado());

										sce.setContentColor(cbbcolor
												.getSelectedItem().getValue()
												.toString());
										Roles_usuarios roles_usuarios = lbxRoles
												.getSelectedItem().getValue();
										sce.setCodigo_rol(roles_usuarios
												.getRol());
										Elemento elementoViaIngreso = lbxRolesExcepciones
												.getSelectedItem().getValue();

										if (Utilidades
												.isAuxiliarOEnfermeraPyP(roles_usuarios
														.getRol())) {
											sce.setCodigo_programa_excepcion(elementoViaIngreso
													.getCodigo());
											//log.info(":::_< Programa agregado");
										} else {
											sce.setCodigo_programa_excepcion("");
										}
										if (ckbcitas.isChecked()) {
											sce.setHeaderColor("#3BC1ED");
											sce.setDescripcion("Consultorio");
										} else {
											sce.setHeaderColor(cbbcolor
													.getSelectedItem()
													.getValue().toString());
											sce.setDescripcion(txbDescripcion
													.getValue().toUpperCase());
										}
										DateFormatterHelper
												.aplicarFormatoValidoPara(sce);
										sce.setCita(ckbcitas.isChecked());
										sce.setEvento_calendar(CalendarsEvent.ON_EVENT_CREATE);
										parent.addEvent(sce);
										if (!accion_formulario
												.equalsIgnoreCase("registrar")) {
											recargar_horario = true;
										}
										agregados++;
									}
								});
					}
					dateInit = incremetDay(dateInit);
				} while (L2HContraintDate.nextDate(dateInit, dateEnd));

				if (agregados.intValue() > 0) {
					MensajesUtil
							.mensajeInformacion(
									"Detalles de horarios agregados",
									"Se agregaron "
											+ agregados
											+ " detalles de horarios para el horario en cuestion",
									new EventListener<Event>() {

										@Override
										public void onEvent(Event arg0)
												throws Exception {
											if (accion_formulario
													.equalsIgnoreCase("registrar")) {
												parent.renderizarHorario();
											}
										}
									});
				} else {
					MensajesUtil
							.mensajeAlerta("No hay detalles que agregar",
									"No se genero ningun detalle de horario para el horario en cuestion");
				}

			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private boolean validar() {
		boolean valido = true;

		if (ibxSecuencia.getValue() == null) {
			valido = false;
			MensajesUtil.notificar("Campo no puede ser vacio",
					Clients.NOTIFICATION_TYPE_WARNING, ibxSecuencia);
		}

		if (tbxCodigo_centro.getValue().trim().isEmpty() && valido) {
			valido = false;
			MensajesUtil.notificar("Campo no puede ser vacio",
					Clients.NOTIFICATION_TYPE_WARNING, tbxCodigo_centro);
		}

		if (tbxCodigo_consultorio.getValue().trim().isEmpty() && valido) {
			valido = false;
			MensajesUtil.notificar("Campo no puede ser vacio",
					Clients.NOTIFICATION_TYPE_WARNING, tbxCodigo_consultorio);
		}

		if (valido)
			try {
				Date dateInit = innerTime(dateboxInit.getValue(),
						dtbinit.getValue());
				Date dateEnd = innerTime(dateboxEnd.getValue(),
						dtbend.getValue());

				Date dateHoraEnd = dtbend.getValue();

				L2HContraintDate.validarFecha(dateInit, dateEnd,
						"Rango de Fecha no Valido");
				L2HContraintDate.validarFecha(dateInit,
						innerTime(dateInit, dateHoraEnd),
						"Rango de Fecha no Valido");
			} catch (Exception e) {
				valido = false;
				Messagebox.show(e.getMessage(), "Fechas", Messagebox.OK,
						Messagebox.EXCLAMATION);
			}

		return valido;
	}

	private Date innerTime(Date init, Date end) {
		return Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd ")
				.format(init) + new SimpleDateFormat("HH:mm:ss").format(end));
	}

	private Date incremetDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.get(Calendar.DAY_OF_MONTH) + 1);
		return calendar.getTime();
	}

	private boolean isActiveDate(Date dateInit, List<Festivos> festivos) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateInit);

		// Verificamos si ahi festivos
		if (festivos != null && !festivos.isEmpty()) {
			for (Festivos festivosTemp : festivos) {
				String dia_festivo = dateFormat.format(festivosTemp.getFecha());
				String dia_horario = dateFormat.format(dateInit);
				if (dia_festivo.equals(dia_horario)) {
					return false;// Retornar falso cuando es festivo
				}
			}
		}

		// Verificamos los dias
		switch (calendar.get(Calendar.DAY_OF_WEEK)) {
		case Calendar.SUNDAY:
			return chkDomingo.isChecked();
		case Calendar.MONDAY:
			return chkLunes.isChecked();
		case Calendar.TUESDAY:
			return chkMartes.isChecked();
		case Calendar.WEDNESDAY:
			return chkMiercoles.isChecked();
		case Calendar.THURSDAY:
			return chkJueves.isChecked();
		case Calendar.FRIDAY:
			return chkViernes.isChecked();
		case Calendar.SATURDAY:
			return chkSabado.isChecked();
		default:
			return false;
		}
	}

}
