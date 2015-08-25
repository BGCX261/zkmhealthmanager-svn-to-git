package healthmanager.controller.complemento;

import healthmanager.controller.ZKWindow;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.service.Esquema_vacunacionService;
import healthmanager.modelo.service.VacunasService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.ITiposServicio;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro.CargaRapidaInformacionUltimoRegistro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro.ETIPO_SERVICIO;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class ProcedimientosAction extends ZKWindow {

	private String via_ingreso;
	// private Prestadores prestadores;
	// private Administradora administradora;
//	private Date fecha;
	private Object datos_procedimiento;
	private Configuracion_admision_ingreso configuracion_admision_ingreso;
	private Paciente paciente;

	private GeneralExtraService generalExtraService;

	private CargaRapidaInformacionUltimoRegistro cargaRapidaInformacionUltimoRegistro;

	public static final String PYP_DETECCION_TEMPRANA_ALTERACIONES_ADULTO = "431220_02";
	public static final String PYP_ATENCION_PREVENTIVA_SALUD_BUCAL = "431220_01";
	public static final String PYP_DETECCION_TEMPRANA_CRECIMIENTO_MENOR_DIEZ_ANIOS = "431220_03";
	public static final String PYP_DETECCION_TEMPRANA_ALTERACIONES_DESARROLLO_JOVEN = "431220_04";
	public static final String PYP_DETECCION_ALTERACIONES_AGUDEZA_VISUAL = "431220_05";
	public static final String PYP_PLANIFICACION_FAMILIAR = "431220_06";
	public static final String PYP_DETECCION_TEMPRANA_ALTERACIONES_EMBARAZO = "431220_07";
	public static final String PYP_PROGRAMA_AMPLIADO_INMUNIZACION = "431220_08";
	public static final String PYP_DETECCION_TEMPRANA_CANCER_CUELLO_UTERINO = "431220_09";
	public static final String PYP_ATENCION_RECIEN_NACIDO = "431220_11";
	public static final String PYP_DETECCION_TEMPRANA_CANCER_SENO = "431220_12";
	public static final String PYP_ATENCION_PARTO = "431220_10";

	@View
	private Datebox dtbxFecha;
	@View
	private Textbox tbxAutorizacion;
	@View
	private Listbox lbxAmbitoRealizacion;
	@View
	private Listbox lbxFinalidadProcedimiento;
	@View
	private Listbox lbxPersonalAtiende;
	@View
	private Listbox lbxFormaRealizacion;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_diagnostico_principal;
	@View
	private Textbox tbxNomDxpp;
	@View
	private Toolbarbutton btnLimpiarDxpp;
	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxDiagRelacionado;
	@View
	private Textbox tbxInfoDiagRelacionado;
	@View
	private Toolbarbutton btnLimpiarDiagRelacionado;
	@View(isMacro = true)
	private BandboxRegistrosMacro bandboxComplicacion;
	@View
	private Textbox tbxInfoComplicacion;
	@View
	private Toolbarbutton btnLimpiarComplicacion;
	@View
	private Listbox lbxProgramaPYP;

	@View
	private Row rowProgramaPyp;
	@View
	private Row rowDocificacionVacuna;
	@View
	private Listbox lbxDocificacion;

	private ServiciosFacturacionMacro COMPONENTE_MACRO;
	private Map<String, Object> map_detalle_factura;

	@Override
	public void init() throws Exception {
		listarCombos();
		parametrizaBandbox();
		cargarDatos();

		if (configuracion_admision_ingreso == null) {
			throw new ValidacionRunTimeException(
					"La via de ingreso "
							+ via_ingreso
							+ " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
		}

		if (!configuracion_admision_ingreso.getEs_pyp()) {
			rowProgramaPyp.setVisible(false);
		}

	}

	private void cargarDatos() {
		validarEsquemaVacunacion();
		dtbxFecha.setValue(cargaRapidaInformacionUltimoRegistro.getFecha());
		Utilidades.aplicarOnOk(dtbxFecha, tbxAutorizacion);
		Utilidades.aplicarOnOk(tbxAutorizacion, lbxAmbitoRealizacion);
		Utilidades.aplicarOnOk(lbxAmbitoRealizacion, lbxFinalidadProcedimiento);
		Utilidades.aplicarOnOk(lbxFinalidadProcedimiento, lbxPersonalAtiende);
		Utilidades.aplicarOnOk(lbxPersonalAtiende, lbxFormaRealizacion);
		Utilidades.aplicarOnOk(lbxFormaRealizacion,
				tbxCodigo_diagnostico_principal);
	}

	private boolean seleccionarVacuna() {
		// selecciona mos la vacuna correspondiente
		if (rowDocificacionVacuna.isVisible()) {
			Datos_procedimiento datos_procedimiento = (Datos_procedimiento) this.datos_procedimiento;
			Esquema_vacunacion esquema_vacunacion = new Esquema_vacunacion();
			esquema_vacunacion.setCodigo_empresa(datos_procedimiento
					.getCodigo_empresa());
			esquema_vacunacion.setCodigo_sucursal(datos_procedimiento
					.getCodigo_sucursal());
			esquema_vacunacion.setCodigo_vacuna(datos_procedimiento
					.getCodigo_vacuna());
			esquema_vacunacion.setConsecutivo(datos_procedimiento
					.getId_esquema_vacunacion());
			esquema_vacunacion = getServiceLocator().getServicio(
					Esquema_vacunacionService.class).consultar(
					esquema_vacunacion);
			if (esquema_vacunacion != null) {
				List<Listitem> listitems = lbxDocificacion.getItems();
				for (Listitem listitem : listitems) {
					Esquema_vacunacion esquema_vacunacion_temp = listitem
							.getValue();
					if (esquema_vacunacion_temp.getConsecutivo().intValue() == esquema_vacunacion
							.getConsecutivo().intValue()) {
						listitem.setSelected(true);
						inyectarEsquemaVacunacion();
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void _despuesIniciar() {
		//log.info("datos_procedimiento ===> " + datos_procedimiento);
		Res.cargarAutomatica(dtbxFecha, datos_procedimiento,
				"fecha_procedimiento");
		
		Res.cargarAutomatica(tbxAutorizacion, datos_procedimiento,
				"numero_autorizacion");

//		Utilidades.seleccionarListitem(lbxAmbitoRealizacion,
//				((Datos_procedimiento) datos_procedimiento)
//						.getAmbito_procedimiento());
		Res.cargarAutomatica(lbxAmbitoRealizacion, datos_procedimiento,
				"ambito_procedimiento");

//		Utilidades.seleccionarListitem(lbxFinalidadProcedimiento,
//				((Datos_procedimiento) datos_procedimiento)
//						.getFinalidad_procedimiento());
		Res.cargarAutomatica(lbxFinalidadProcedimiento, datos_procedimiento,
				"finalidad_procedimiento");

//		Utilidades.seleccionarListitem(lbxPersonalAtiende,
//				((Datos_procedimiento) datos_procedimiento)
//						.getPersonal_atiende());
		Res.cargarAutomatica(lbxPersonalAtiende, datos_procedimiento,
				"personal_atiende");

//		Utilidades.seleccionarListitem(lbxFormaRealizacion,
//				((Datos_procedimiento) datos_procedimiento)
//						.getForma_realizacion());
		Res.cargarAutomatica(lbxFormaRealizacion, datos_procedimiento,
				"forma_realizacion");

		filtarAreaFuncional();
	}

	private void filtarAreaFuncional() {
		List<Listitem> listitems = lbxProgramaPYP.getItems();
		Listitem listitem_temp = null;
		for (Listitem listitem : listitems) {
			// Verificamos que la via ingreso sea Pyp para seleccionar un
			// programa
			String codigo_programa = listitem.getValue();
			if (via_ingreso.equals(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)
					&& codigo_programa
							.equals(PYP_DETECCION_TEMPRANA_ALTERACIONES_EMBARAZO)) {
				listitem_temp = listitem;
			} else if ((via_ingreso
					.equals(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)
					|| via_ingreso.equals(IVias_ingreso.HC_MENOR_2_MESES) || via_ingreso
						.equals(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS))
					&& codigo_programa
							.equals(PYP_DETECCION_TEMPRANA_CRECIMIENTO_MENOR_DIEZ_ANIOS)) {
				listitem_temp = listitem;
			} else if ((via_ingreso.equals(IVias_ingreso.SALUD_ORAL))
					&& codigo_programa
							.equals(PYP_ATENCION_PREVENTIVA_SALUD_BUCAL)) {
				listitem_temp = listitem;
			} else if (!listitem.getValue().equals("N/A")
					&& !listitem.getValue().toString().trim().isEmpty()) {
				listitem.setVisible(false);
			} else if (listitem.getValue().equals("N/A")) {
				listitem.setSelected(true);
			}
		}
		if (listitem_temp != null) {
			listitem_temp.setSelected(true);
		}
	}

	private void listarCombos() {
		Utilidades.listarElementoListbox(lbxAmbitoRealizacion, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFinalidadProcedimiento, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxPersonalAtiende, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFormaRealizacion, true,
				getServiceLocator());
		Utilidades.listarProgramasPYP(lbxProgramaPYP, true, this);

		// AGREGAR NO APLICA
		lbxProgramaPYP.appendChild(new Listitem("NO APLICA", "N/A"));

		// cargar eventos
		lbxDocificacion.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						inyectarEsquemaVacunacion();
					}
				});
	}

	@Override
	public void params(Map<String, Object> map) {
		via_ingreso = (String) map.get(ITiposServicio.PARAM_VIA_INGRESO);
		// prestadores = (Prestadores) map.get(ITiposServicio.PARAM_PRESTADOR);
		// administradora = (Administradora)
		// map.get(ITiposServicio.PARAM_ADMINISTRADORA);
//		fecha = (Date) map.get(ITiposServicio.PARAM_FECHA);
		datos_procedimiento = map.get(ITiposServicio.PARAM_RIPS);
		configuracion_admision_ingreso = (Configuracion_admision_ingreso) map
				.get(ITiposServicio.PARAM_CONFIG_ADMISION);
		paciente = (Paciente) map.get(ITiposServicio.PARAM_PACIENTES);
		cargaRapidaInformacionUltimoRegistro = (CargaRapidaInformacionUltimoRegistro) map.get(ITiposServicio.PARAM_DIAGNOSTICO);
		COMPONENTE_MACRO = (ServiciosFacturacionMacro) map
				.get("COMPONENTE_MACRO");
		map_detalle_factura = (Map<String, Object>) map
				.get(ITiposServicio.PARAM_MAP_DETALLE);
	}

	private void validarEsquemaVacunacion() {
		if (datos_procedimiento != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("codigo_empresa", codigo_empresa);
			map.put("codigo_sucursal", codigo_sucursal);
			map.put("codigo_vacuna",
					((Datos_procedimiento) datos_procedimiento)
							.getCodigo_procedimiento());
			List<Esquema_vacunacion> esquema_vacunacions = getServiceLocator()
					.getServicio(Esquema_vacunacionService.class).listar(map);
			if (esquema_vacunacions.isEmpty()) {
				rowDocificacionVacuna.setVisible(false);
			} else {
				rowDocificacionVacuna.setVisible(true);
				map_detalle_factura.put(
						ITiposServicio.APLICA_ESQUEMA_VACUNACION,
						ITiposServicio.APLICA_ESQUEMA_VACUNACION);
				for (Esquema_vacunacion esquema_vacunacion : esquema_vacunacions) {
					lbxDocificacion
							.appendChild(new Listitem(""
									+ esquema_vacunacion.getDescripcion()
											.toUpperCase(), esquema_vacunacion));
				}

				/**
				 * Seleccionamos vacuna correspondiente si no ahi vacunas,
				 * seleccionamos la promera por defecto
				 * */
				if (!seleccionarVacuna() && lbxDocificacion.getItemCount() > 0) {
					lbxDocificacion.setSelectedIndex(0);
					inyectarEsquemaVacunacion();
				}
			}
		} else {
			rowDocificacionVacuna.setVisible(false);
		}
	}

	/**
	 * Este metodo me permite inyectar los valores correspondientes a la
	 * seleccion del esquema de vacunacion
	 * 
	 * @author Luis Miguel
	 * */
	private void inyectarEsquemaVacunacion() {
		Esquema_vacunacion esquema_vacunacion = lbxDocificacion
				.getSelectedItem().getValue();
		map_detalle_factura.put(ITiposServicio.PARAM_ESQUEMA_VACUNACION,
				esquema_vacunacion);

		if (esquema_vacunacion != null) {
			Datos_procedimiento datos_procedimiento = (Datos_procedimiento) this.datos_procedimiento;
			datos_procedimiento.setCodigo_vacuna(esquema_vacunacion
					.getCodigo_vacuna());
			datos_procedimiento.setId_esquema_vacunacion(esquema_vacunacion
					.getConsecutivo());

			Vacunas vacunas = new Vacunas();
			vacunas.setCodigo_empresa(esquema_vacunacion.getCodigo_empresa());
			vacunas.setCodigo_sucursal(esquema_vacunacion.getCodigo_sucursal());
			vacunas.setCodigo_procedimiento(esquema_vacunacion
					.getCodigo_vacuna());
			vacunas = getServiceLocator().getServicio(VacunasService.class)
					.consultar(vacunas);
			if (vacunas != null) {
				Cie cie = new Cie();
				cie.setCodigo(vacunas.getCodigo_cie());
				cie = getServiceLocator().getServicio(GeneralExtraService.class)
						.consultar(cie);
				if (cie != null) {
					tbxCodigo_diagnostico_principal.seleccionarRegistro(cie,
							cie.getCodigo(), cie.getNombre());
					datos_procedimiento.setCodigo_diagnostico_principal(cie
							.getCodigo());
				}
			}
		}
	}

	private void parametrizaBandbox() {
		inicializarBandboxCie(tbxCodigo_diagnostico_principal, tbxNomDxpp,
				btnLimpiarDxpp, bandboxDiagRelacionado,
				"codigo_diagnostico_principal");
		inicializarBandboxCie(bandboxDiagRelacionado, tbxInfoDiagRelacionado,
				btnLimpiarDiagRelacionado, bandboxComplicacion,
				"codigo_diagnostico_relacionado");
		inicializarBandboxCie(bandboxComplicacion, tbxInfoComplicacion,
				btnLimpiarComplicacion, lbxProgramaPYP, "complicacion");
	}

	private void inicializarBandboxCie(
			BandboxRegistrosMacro bandboxRegistrosMacro, Textbox textbox,
			Toolbarbutton toolbarbutton,
			final HtmlBasedComponent basedComponent, final String campo) {
		bandboxRegistrosMacro.inicializar(textbox, toolbarbutton);
		BandboxRegistrosIMG<Cie> bandboxRegistrosIMG = new BandboxRegistrosIMG<Cie>() {
			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Cie registro) {
				try {
					Map<String,Object> map = new HashMap();
					map.put("nombre_entidad", registro.getNombre());
					map.put("limite_inferior", registro.getLimite_inferior());
					map.put("limite_superior", registro.getLimite_superior());
					map.put("sexo_entidad", registro.getSexo());

					String fecha_nacimiento = new SimpleDateFormat("dd/MM/yyyy")
							.format(paciente.getFecha_nacimiento());

					map.put("fecha_nac", fecha_nacimiento);
					map.put("sexo_pct", paciente.getSexo());
					Map<String,Object> result = Utilidades.validarInformacionLimiteSexo(
							"Diagnostico", registro.getCodigo(),
							registro.getLimite_inferior(),
							registro.getLimite_superior(), registro.getSexo(),
							fecha_nacimiento, paciente.getSexo());
					if (!((Boolean) result.get("success"))) {
						throw new ValidacionRunTimeException(
								(String) result.get("msg"));
					}
					bandbox.setValue(registro.getCodigo());
					textboxInformacion.setValue(registro.getNombre());
					// Actualuzamos datos
					Res.setValor(datos_procedimiento, campo, bandbox.getValue());
					basedComponent.setFocus(true);

					// colocamos en cola el ultimo diagnostico seleccionado
					if (bandbox.equals(tbxCodigo_diagnostico_principal)) {
						cargaRapidaInformacionUltimoRegistro.setDiagnostico_principal(bandbox.getValue());
					} else if (bandbox.equals(bandboxDiagRelacionado)) {
						cargaRapidaInformacionUltimoRegistro.setDiagnostico_relacionado(bandbox.getValue());
					}

				} catch (Exception e) {
					MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
				}
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem, Cie registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getSexo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getLimite_inferior()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getLimite_superior()));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Cie> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				parametros.put("limite_paginado", "limit 25 offset 0");
				return generalExtraService.listar(Cie.class,parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				Res.setValor(datos_procedimiento, campo, "");
			}
		};
		/* inyectamos el mismo evento */
		bandboxRegistrosMacro.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		bandboxRegistrosMacro.setReadonly(false);
		// inicializamos componente
		Object object_valor = Res.getValor(datos_procedimiento, campo);
		if (object_valor != null && !object_valor.toString().isEmpty()) {
			Cie cie = new Cie();
			cie.setCodigo(object_valor.toString());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(
					cie);
			bandboxRegistrosMacro.seleccionarRegistro(cie, cie.getCodigo(),
					cie.getNombre());
		}
	}
	
	@Override
	public void detach() {
		if(datos_procedimiento instanceof Datos_procedimiento){
			Datos_procedimiento procedimiento = (Datos_procedimiento) datos_procedimiento;
			procedimiento.setNumero_autorizacion(tbxAutorizacion.getValue()); 
			procedimiento.setFecha_procedimiento(dtbxFecha.getValue() != null ? new Timestamp(
					dtbxFecha.getValue().getTime()) : null); 
		}
		super.detach();
	}

	public void onClickAgregarOtroServicio() {
		if (COMPONENTE_MACRO != null) {
			//log.info("ejecutando metodo @onClickAgregarOtroServicio()");
			COMPONENTE_MACRO.agregarServicio(ETIPO_SERVICIO.SERVICIO, true);
			this.onClose();
		}
	}

}
