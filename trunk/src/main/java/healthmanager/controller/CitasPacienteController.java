package healthmanager.controller;

import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Control_cita;
import healthmanager.modelo.bean.Detalles_horarios;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.calendar.impl.SimpleCalendarModel;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;

import com.framework.interfaces.ISeleccionarMedico;
import com.framework.res.L2HContraintDate;
import com.framework.res.MapToList;
import com.framework.res.Res;
import com.framework.res.ZKSimpleCalendarEvent;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class CitasPacienteController extends ZKWindow implements ISeleccionarMedico{
	
	public static String TYPE = "type"; // 01 - en session ->  02 - beneficiarios
	
	@View private Bandbox tbxBeneficiario;
	@View private Textbox tbxValueBeneficiario;
	@View private Listbox lbxBeneficiario;
	@View private Textbox tbxNombreBeneficiario;
	@View private Bandbox tbxMedico;
	@View private Textbox tbxValueMedico;
	@View private Listbox lbxMedico;
	@View private Textbox tbxNombreMedico;
	@View private Listbox lbxTipo_consulta;
	@View private Listbox lbxPrograma;
	@View private Listbox lbxTActividad;
	@View private Listbox lbxanio;
	@View private Listbox lbxmes;
	@View private Listbox lbxday;
	@View private Calendars calendars;
	
	@View private Datebox dtbxFecha_solicitada;
	@View private Row rowBeneficiario;
	
	private boolean fromBeneficiario = false;
	
	private SimpleCalendarModel simpleCalendarModel;

	@Override
	public void init() {
	   initCalendarModel();
       cargarTypeOfCita();
       loadComponents();  
	} 

	private void initCalendarModel() {
		simpleCalendarModel = new SimpleCalendarModel();
		calendars.setModel(simpleCalendarModel);
		
		/* evento de modelo para partar un cita */
		calendars.addEventListener("onEventEdit", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				final CalendarsEvent evento = (CalendarsEvent)arg0;
				//System.Out.Println("" + evento.getCalendarEvent());
				Messagebox.show("Usuario " + paciente_session.getNombreCompleto() 
						+ " estas seguro que deseas apartar esta cita, para el dia " + new SimpleDateFormat("yyyy/MM/dd").format(evento.getCalendarEvent().getBeginDate()), "Informacion",
						Messagebox.OK + Messagebox.CANCEL,
						Messagebox.INFORMATION, new EventListener() {
							@Override
							public void onEvent(Event arg0) throws Exception {
								   /* apartamos la cita */
								if("onOK".equals(arg0.getName())){
									try {
										Citas citas = new Citas();
										citas.setFecha_solicitada(new Timestamp(dtbxFecha_solicitada.getValue().getTime()));
										apartarCitaMedica(citas, fromBeneficiario ? tbxBeneficiario.getValue() : paciente_session.getNro_identificacion(),
												   tbxMedico.getValue(),
												   new Timestamp(evento.getCalendarEvent().getBeginDate().getTime()),
												   "1",
												   paciente_session.getCodigo_administradora(),
												   "",
												   "",
												   ((ZKSimpleCalendarEvent)evento.getCalendarEvent()).getConsecutivo());
										   simpleCalendarModel.remove(evento.getCalendarEvent());
										   Messagebox.show("Cita apartada exitosamente", "Informacion",
												Messagebox.OK, Messagebox.INFORMATION );
									} catch (Exception e) { 
										throw new Exception(e);
									}
								}
							}
					});
			}
		});
	}
	
	public String apartarCitaMedica(Citas citas, String nro_id_paciente,
			String codigo_prestador, Timestamp fecha_cita,
			String tipo_consulta, String codigo_administradora,
			String area_intervencion, String codigo_pcd, Integer codigo_detalle)
			throws Exception {
		String codigo_cita = getServiceLocator().getConsecutivoService()
				.crearConsecutivo(sucursal.getCodigo_empresa(),
						sucursal.getCodigo_sucursal(), "CITAS");
		codigo_cita = getServiceLocator().getConsecutivoService().getZeroFill(
				codigo_cita, 10);

		boolean entro = false;

		citas.setCodigo_empresa(sucursal.getCodigo_empresa());
		citas.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		citas.setCodigo_cita(codigo_cita);
		citas.setNro_identificacion(nro_id_paciente);
		citas.setCodigo_prestador(codigo_prestador);
		citas.setFecha_cita(fecha_cita);
		citas.setHora(formatHora.format(fecha_cita));
		citas.setValor_cita(0);
		citas.setCopago_cita(0);
		citas.setEstado("1");
		citas.setTipo_consulta(tipo_consulta);
		citas.setCreacion_date(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		citas.setUltimo_update(new Timestamp(Calendar.getInstance()
				.getTimeInMillis()));
		citas.setCreacion_user(getCreacionUser());
		citas.setUltimo_user(getCreacionUser());
		// citas.setCodigo_plantilla(codigo_plantilla);
		citas.setArea_intervencion(area_intervencion);
		citas.setCodigo_plantilla(codigo_pcd);
		citas.setCodigo_detalle_horario(codigo_detalle);

		Date today = Calendar.getInstance().getTime();

		/* calculamos la resolucion 1552 */
		citas.setDiferencia1(new Long(L2HContraintDate.getDiferentDay(
				citas.getFecha_solicitada(), today)).intValue());
		citas.setDiferencia2(new Long(L2HContraintDate.getDiferentDay(
				fecha_cita, today)).intValue());/* fin de calculo de diferencias */
		/*  */

		Control_cita control = new Control_cita();
		control.setCodigo_empresa(codigo_empresa);
		control.setCodigo_sucursal(codigo_sucursal);
		control.setCodigo_administradora(codigo_administradora);
		control.setServicio(tipo_consulta);
		control = (Control_cita) getServiceLocator().getControl_citaService()
				.consultar(control);
		if (control != null) {
			String tipo_control = "Por día";
			if (control.getTipo_control().equals("1")) {
				tipo_control = "Por día";
			} else if (control.getTipo_control().equals("2")) {
				tipo_control = "Por mes";
			} else if (control.getTipo_control().equals("3")) {
				tipo_control = "Por año";
			}

			Map param = new HashMap();
			param.put("codigo_empresa", codigo_empresa);
			param.put("codigo_sucursal", codigo_sucursal);
			param.put("codigo", codigo_administradora);
			param.put("tipo_control", tipo_control);
			param.put("alcanze_control", control.getAlcanze_control());
			param.put("nro_identificacion", nro_id_paciente);

			if (tipo_control.equals("1")) {
				param.put("fecha_cita_s", new SimpleDateFormat("yyyy-MM-dd")
						.format(citas.getFecha_cita()));
			} else if (tipo_control.equals("2")) {
				param.put("fecha_cita_m", new SimpleDateFormat("yyyy")
						.format(citas.getFecha_cita()));
				param.put("fecha_cita_s", new SimpleDateFormat("MM")
						.format(citas.getFecha_cita()));
			} else if (tipo_control.equals("3")) {
				param.put("fecha_cita_s", new SimpleDateFormat("yyyy")
						.format(citas.getFecha_cita()));
			}

			int total = getServiceLocator().getCitasService()
					.obtenerCitasAseguradora(param);
			if (total >= control.getTotal_citas()) {
				Elemento elemento = new Elemento();
				elemento.setCodigo("" + tipo_consulta);
				elemento.setTipo("tipo_consulta");
				elemento = getServiceLocator().getElementoService().consultar(
						elemento);

				Paciente paciente = new Paciente();
				paciente.setCodigo_empresa(codigo_empresa);
				paciente.setCodigo_sucursal(codigo_sucursal);
				paciente.setNro_identificacion(nro_id_paciente);
				paciente = getServiceLocator().getPacienteService().consultar(
						paciente);
				if (paciente == null)
					throw new Exception("El Paciente con el nro id "
							+ nro_id_paciente + " no existe.");

				throw new Exception(
						"Esta aseguradora ya ha sobrepasado el límite de citas "
								+ tipo_control
								+ " para el servicio de "
								+ (elemento != null ? elemento.getDescripcion()
										: "No se ha encontrado el tipo de consulta")
								+ " "
								+ (control.getAlcanze_control().equals("1")
										&& paciente != null ? "para el paciente: "
										+ paciente.getNombre1()
										+ " "
										+ paciente.getApellido1()
										+ " "
										+ paciente.getApellido2()
										: "") + " que es: "
								+ control.getTotal_citas() + "");
			}
		}

		if (!entro && area_intervencion.equals("")
				&& citas.getTipo_consulta().equals("4")) {
			throw new Exception(
					"Para consultas pyp debe seleccione el programa");
		}

		if (!entro && codigo_pcd.equals("")
				&& citas.getTipo_consulta().equals("4")) {
			throw new Exception(
					"Para consultas pyp debe seleccionar la actividad");
		}

		if (!entro && !codigo_pcd.equals("")
				&& !citas.getTipo_consulta().equals("4")) {
			throw new Exception(
					"Para consultas diferentes a pyp no debe seleccionar la actividad");
		}

		getServiceLocator().getCitasService().crear(citas);
		getServiceLocator().getConsecutivoService().actualizarConsecutivo(
				codigo_empresa, codigo_sucursal, "CITAS", codigo_cita);
		return codigo_cita;
	}

	private void loadYearsAndMonths() {
		
		String[] anios = Res.getAnnos(7);
		for (String anio : anios) {
			Listitem listitem = new Listitem();
			listitem.setValue(anio);
			listitem.setLabel("" + anio);
			lbxanio.appendChild(listitem);
		}
		lbxanio.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				 Date date = calendars.getCurrentDate();
				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(date);
				 calendar.set(Calendar.YEAR, Integer.parseInt(lbxanio.getSelectedItem().getValue().toString()));
			     calendars.setCurrentDate(calendar.getTime());
			}
		});
		
		lbxmes.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				 Date date = calendars.getCurrentDate();
				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(date);
				 calendar.set(Calendar.MONTH, lbxmes.getSelectedIndex());
			     calendars.setCurrentDate(calendar.getTime());
			     loadDay();
			}
		});
		lbxday.addEventListener("onSelect", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				if(lbxday.getSelectedIndex() == 0){
					calendars.setDays(7); 
					calendars.setMold("month"); 
				}else{
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(calendars.getCurrentDate());
					calendar.set(Calendar.DAY_OF_MONTH, lbxday.getSelectedIndex());
					calendars.setCurrentDate(calendar.getTime());
					calendars.setDays(1); 
					calendars.setTimeslots(2);
					calendars.setMold("default"); 
				}
			}
		});
		
		lbxmes.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
		loadDay(); 
	}
	
	private void loadDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(calendars.getCurrentDate());
		Listitem listitem = new Listitem();
		listitem.setValue("");
		listitem.setLabel("Todos");
		lbxday.appendChild(listitem);
		for (int i = 1; i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH); i++) { 
			listitem = new Listitem();
			listitem.setValue(i);
			listitem.setLabel("" + i);
			lbxday.appendChild(listitem);
		}
		if (lbxday.getItemCount() > 0) {
			lbxday.setSelectedIndex(0);
		}
	}

	@Override
	public void eventoSeleccionarMedico(boolean selectd, Map<String,Object> dato) {
		try {
			simpleCalendarModel.clear();
			if(selectd){
				cargarDiasDisponibles((String)dato.get(Utilidades.CODIGO_USUARIO));  
			}else{
				limpiarDatos();
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "",this);
		}
	}
	
	public void limpiarDatos() throws Exception {
		tbxMedico.setDisabled(false);
	}
 
	private void cargarDiasDisponibles(String codigo_medico) throws Exception{
		Map<String, Object> parameters = new HashMap();
		parameters.put("codigo_empresa", sucursal.getCodigo_empresa());
		parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parameters.put("codigo_medico", "" + codigo_medico);
		parameters.put("limit", "limit 25 offset 0"); 
		
		List<Detalles_horarios> detallesHorarios = getServiceLocator().getListMapService().getLisMaps(MapToList.HORARIOS_DISPONIBLES, parameters);
        for (Detalles_horarios detallesHorariosTemp : detallesHorarios) {
        	simpleCalendarModel.add(Res.converTo(detallesHorariosTemp)); 
		}
	}

	private void loadComponents() {
		 loadYearsAndMonths(); 
	}

	private void cargarTypeOfCita() {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		 String type = request.getParameter(TYPE);
		 if((type+"").equals("02")){
			 fromBeneficiario = true;
		 }else{
			 rowBeneficiario.setVisible(false);
		 }
	}

}
