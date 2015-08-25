package healthmanager.controller;

import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Consultorio;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.service.ElementoService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.helper.DateFormatterHelper;
import com.framework.interfaces.EventosHorarios;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.HoraTextBox;
import com.framework.macros.HoraTextBox.OnCambioHora;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.L2HContraintDate;
import com.framework.res.ZKSimpleCalendarEvent;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class CrearEventoCita extends ZKWindow {

	/* compoenentes */
	@View
	private HoraTextBox dtbinit;
	@View
	private Listbox ltbhorainit;
	@View
	private Checkbox chkAllDay;
	@View
	private HoraTextBox dtbend;
	@View
	private Listbox ltbhoraend;
	@View
	private Combobox cbbcolor;
	@View
	private Textbox txbDescripcion;
	@View
	private Checkbox ckbcitas;
	@View
	private Button okBtn;
	@View
	private Button cancelBtn;
	@View
	private Button eliminarBtn;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_centro;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_consultorio;
	@View
	private Toolbarbutton btnLimpiarCentro;
	@View
	private Toolbarbutton btnLimpiarConsultorio;

	@View
	private Listbox lbxRolesExcepciones;
	@View
	private Row rowRolEspecifico;

	@View
	private Listbox lbxRoles;

	public static SimpleDateFormat formatEvent = new SimpleDateFormat(
			"yyyy-MM-dd");

	private EventosHorarios parent;

	private Date date_fecha;
//	private String hora_current;
	private Integer tiempo_cita;
	private String evento;
	private ZKSimpleCalendarEvent datos_evento;

	@View
	private Datebox dtbxFecha_dia;
	private String codigo_medico;
	private String rol_seleccionado;

	@Override
	public void params(Map<String, Object> map) {
		date_fecha = (Date) map.get("date_fecha");
//		hora_current = (String) map.get("hora_current");
		tiempo_cita = (Integer) map.get("tiempo_cita");
		evento = (String) map.get("evento");
		datos_evento = (ZKSimpleCalendarEvent) map.get("datos_evento");
		codigo_medico = (String) map.get("codigo_medico");
		rol_seleccionado = (String) map.get("rol_seleccionado");
	}

	private void initTimeDropdown() {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		calendar.set(11, 0);
		calendar.set(12, 0);
		calendar.set(13, 0);

		ltbhorainit.getChildren().clear();
		ltbhoraend.getChildren().clear();

		for (int i = 0; i < 288; i++) {
			String HHMM = sdf.format(calendar.getTime());
			Listitem listitem = new Listitem();
			listitem.setValue(HHMM);
			listitem.setLabel(HHMM);
			ltbhorainit.appendChild(listitem);
			listitem = new Listitem();
			listitem.setValue(HHMM);
			listitem.setLabel(HHMM);
			ltbhoraend.appendChild(listitem);
			calendar.add(12, 5);
		}

		if (ltbhorainit.getItemCount() > 0) {
			ltbhorainit.setSelectedIndex(0);
		}

		if (ltbhoraend.getItemCount() > 0) {
			ltbhoraend.setSelectedIndex(0);
		}
	}

	@Override
	public void init() {
		this.parent = (EventosHorarios) getParent();
		initTimeDropdown();
		initTime();
		dtbxFecha_dia.setValue(date_fecha);
		parametrizarBandbox();
		listarRoles();
		listarRolesExcepciones();
		if (evento.equals(CalendarsEvent.ON_EVENT_EDIT)) {
			mostrarDatosEvento(datos_evento);
		} else {
			eliminarBtn.setDisabled(true);
			for (int i = 0; i < lbxRoles.getItemCount(); i++) {
				Listitem listitem = lbxRoles.getItemAtIndex(i);
				if (((Roles_usuarios) listitem.getValue()).getRol().equals(
						rol_seleccionado)) {
					listitem.setSelected(true);
					break;
				}
			}
		}
		inicializarCentro();
	}

	private void inicializarCentro() {
		List<Centro_atencion> centro_atencions = getCentro_atencions(new HashMap<String, Object>());
		if (centro_atencions.size() == 1) {
			Centro_atencion centro_atencion = centro_atencions.get(0);
			tbxCodigo_centro.seleccionarRegistro(
					centro_atencion,
					centro_atencion.getCodigo_centro() + "-"
							+ centro_atencion.getNombre_centro(), "");
			tbxCodigo_centro.setObject(centro_atencion);
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

	private void initTime() {
		if (evento.equals(CalendarsEvent.ON_EVENT_CREATE)) {
			//log.info("date_fecha ===>" + date_fecha);
			//log.info("hora_current ===>" + hora_current);
			if (tiempo_cita == null || tiempo_cita.intValue() == 0)
				tiempo_cita = 15;
			dtbinit.setValue(date_fecha);
			// if (hora_current != null)
			// dtbinit.setText(hora_current);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(dtbinit.getValue());
			calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)
					+ tiempo_cita);
			dtbend.setValue(calendar.getTime());
			setTitle(new SimpleDateFormat("EEEEEEEEEEE dd MMMMMMMMMMM yyyy")
					.format(date_fecha));
			dtbinit.setOnCambioHora(new OnCambioHora() {
				@Override
				public void onCambio(HoraTextBox horaTextBox) {
					onChangeFechaInicial();
				}
			});
		}
	}

	public void onChangeFechaInicial() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dtbinit.getValue());
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)
				+ tiempo_cita);
		dtbend.setValue(calendar.getTime());
	}

	public void mostrarDatosEvento(ZKSimpleCalendarEvent datos_evento) {
		dtbxFecha_dia.setValue(datos_evento.getBeginDate());
		dtbinit.setValue(datos_evento.getBeginDate());
		dtbend.setValue(datos_evento.getEndDate());
		ckbcitas.setChecked(datos_evento.isCita());

		Centro_atencion centro_atencion = new Centro_atencion();
		centro_atencion.setCodigo_centro(datos_evento.getCodigo_centro_salud());
		centro_atencion.setCodigo_empresa(codigo_empresa);
		centro_atencion.setCodigo_sucursal(codigo_sucursal);
		centro_atencion = getServiceLocator().getCentro_atencionService()
				.consultar(centro_atencion);

		tbxCodigo_centro.seleccionarRegistro(
				centro_atencion,
				centro_atencion.getCodigo_centro() + "-"
						+ centro_atencion.getNombre_centro(), "");

		Consultorio consultorio = new Consultorio();
		consultorio.setCodigo_empresa(codigo_empresa);
		consultorio.setCodigo_sucursal(codigo_sucursal);
		consultorio.setCodigo_centro(datos_evento.getCodigo_centro_salud());
		consultorio.setCodigo_consultorio(datos_evento.getCodigo_consulorio());

		consultorio = getServiceLocator().getConsultorioService().consultar(
				consultorio);

		tbxCodigo_consultorio.seleccionarRegistro(
				consultorio,
				consultorio.getCodigo_consultorio() + "-"
						+ consultorio.getNombre(), "");

		FormularioUtil.deshabilitarComponentes(this, true, "okBtn",
				"cancelBtn", "eliminarBtn");

		// Listar Rols
		for (int i = 0; i < lbxRoles.getItemCount(); i++) {
			Listitem listitem = lbxRoles.getItemAtIndex(i);
			if (((Roles_usuarios) listitem.getValue()).getRol().equals(
					datos_evento.getCodigo_rol())) {
				listitem.setSelected(true);
				break;
			}
		}

		if (datos_evento.getCodigo_programa_excepcion() != null) {
			for (int i = 0; i < lbxRolesExcepciones.getItemCount(); i++) {
				Listitem listitem = lbxRolesExcepciones.getItemAtIndex(i);
				if (((Elemento) listitem.getValue()).getCodigo().equals(
						datos_evento.getCodigo_programa_excepcion())) {
					listitem.setSelected(true);
					break;

				}
			}
		}

		okBtn.setDisabled(true);
		cancelBtn.setDisabled(false);
		eliminarBtn.setDisabled(false);
		onSeleccionarRolMedico();
	}

	private void parametrizarBandbox() {
		parametrizarBandboxCentro();
		parametrizarBandboxConsultorios();
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

						return getServiceLocator().getConsultorioService()
								.listar(parametros);
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
		Roles_usuarios roles_usuarios = lbxRoles.getSelectedItem().getValue();
		if (Utilidades.isAuxiliarOEnfermeraPyP(roles_usuarios.getRol())) {
			rowRolEspecifico.setVisible(true);
		} else {
			rowRolEspecifico.setVisible(false);
		}
	}

	public void addEvent() {
		try {
			if (!tbxCodigo_centro.getValue().isEmpty()
					&& !tbxCodigo_consultorio.getValue().isEmpty()) {

				ZKSimpleCalendarEvent sce = new ZKSimpleCalendarEvent();
				sce.setBeginDate(crearFechaHora(dtbxFecha_dia.getValue(),
						dtbinit.getValue()));
				sce.setEndDate(crearFechaHora(dtbxFecha_dia.getValue(),
						dtbend.getValue()));
				L2HContraintDate.validateIncludeToDay(sce.getBeginDate(),
						sce.getEndDate(), "Rango de Fecha no Valido");

				sce.setContentColor(cbbcolor.getSelectedItem().getValue()
						.toString());
				if (ckbcitas.isChecked()) {
					sce.setHeaderColor("#3BC1ED");
					sce.setDescripcion("Consultorio");
				} else {
					sce.setHeaderColor(cbbcolor.getSelectedItem().getValue()
							.toString());
					sce.setDescripcion(txbDescripcion.getValue().toUpperCase());
				}
				Roles_usuarios roles_usuarios = lbxRoles.getSelectedItem()
						.getValue();
				DateFormatterHelper.aplicarFormatoValidoPara(sce);
				sce.setAllDay(chkAllDay.isChecked());
				sce.setCita(ckbcitas.isChecked());
				sce.setEvento_calendar(evento);
				sce.setCodigo_rol(roles_usuarios.getRol());
				Centro_atencion centro_atencion = (Centro_atencion) tbxCodigo_centro
						.getRegistroSeleccionado();
				Consultorio consultorio = (Consultorio) tbxCodigo_consultorio
						.getRegistroSeleccionado();
				sce.setCodigo_centro_salud(centro_atencion.getCodigo_centro());
				sce.setCodigo_consulorio(consultorio.getCodigo_consultorio());
				Elemento elementoViaIngreso = lbxRolesExcepciones
						.getSelectedItem().getValue();

				if (Utilidades.isAuxiliarOEnfermeraPyP(roles_usuarios.getRol())) {
					sce.setCodigo_programa_excepcion(elementoViaIngreso
							.getCodigo());
					//log.info(":::_< Programa agregado");
				}
				parent.addEvent(sce);
				dtbinit.setValue(dtbend.getValue());
				onChangeFechaInicial();
			} else {
				MensajesUtil.mensajeAlerta("Campos obligatorios",
						"Debe diligenciar el centro de salud y el consultorio");
			}

		} catch (Exception e) {
			// e.printStackTrace();
			Messagebox.show(e.getMessage(), "Fechas", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	public void eliminarEvento() {
		try {
			parent.eliminarEvent(datos_evento);
			onClose();
		} catch (Exception e) {
			// e.printStackTrace();
			Messagebox.show(e.getMessage(), "Error al eliminar el evento",
					Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public Date crearFechaHora(Date fecha_dia, Date fecha_aux) {
		Calendar calendar_aux = Calendar.getInstance();
		calendar_aux.setTime(fecha_aux);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_dia);
		calendar.set(Calendar.HOUR_OF_DAY,
				calendar_aux.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, calendar_aux.get(Calendar.MINUTE));

		return calendar.getTime();
	}

}
