package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Esquema_vacunacion;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.bean.Vacunas;
import healthmanager.modelo.bean.Vacunas_pacientes;
import healthmanager.modelo.service.UsuariosService;
import healthmanager.modelo.service.VacunasService;
import healthmanager.modelo.service.Vacunas_pacientesService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.EstadoVacunaMacro;
import com.framework.macros.EstadoVacunaMacro.OnAccionEstadoVacunacion;
import com.framework.notificaciones.Notificaciones;
import com.framework.res.L2HContraintDate;
import com.framework.res.LabelAlign;
import com.framework.res.LabelAlign.AlignText;
import com.framework.util.ManejadorPoblacion;
import com.framework.util.Utilidades;

public class HistorialVacunasAction extends ZKWindow {

	public static final String PARAM_PACIENTE = "pac";

	@View
	private Listbox lbxVacunasPendientes;
	@View
	private Rows rowHistorialVacunas;

	@View
	private Toolbarbutton tbnAgregarVacunas;

	private IVacunacionWindowReceptor receptor;

	private UsuariosService usuariosService;
	private Vacunas_pacientesService vacunas_pacientesService;

	private Vacunas_pacientesService pacientesService;

	private Paciente paciente;

	private List<Vacunas_pacientes> listVacunasRealizadas;

	@Override
	public void params(Map<String, Object> map) {
		paciente = (Paciente) map.get(PARAM_PACIENTE);
		if (getParent() instanceof IVacunacionWindowReceptor) {
			receptor = (IVacunacionWindowReceptor) getParent();
		}

		if (paciente == null) {
			Admision admision = (Admision) map
					.get(IVias_ingreso.ADMISION_PACIENTE);

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(admision.getCodigo_empresa());
			paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
			paciente.setNro_identificacion(admision.getNro_identificacion());
			this.paciente = getServiceLocator().getPacienteService().consultar(
					paciente);
		}

	}

	@Override
	public void init() throws Exception {
		if (paciente == null) {
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					"Paciente no existe", IConstantes.TIEMPO_NOTIFICACIONES_MEDIO);
			detach();
		}
		cargarHistorialVacunas();
		cargarVacunasPendientes();
	}

	private void cargarVacunasPendientes() {
		lbxVacunasPendientes.getItems().clear();
		List<Esquema_vacunacion> vacunasPermitidas = ManejadorPoblacion
				.getVacunasDisponibles(paciente, getServiceLocator());
		if (!vacunasPermitidas.isEmpty()) {
			for (Esquema_vacunacion vacunas : vacunasPermitidas) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_empresa", paciente.getCodigo_empresa());
				map.put("codigo_sucursal", paciente.getCodigo_sucursal());
				map.put("nro_identificacion", paciente.getNro_identificacion());
				map.put("codigo_cups_vacuna", vacunas.getCodigo_vacuna());
				map.put("id_esquema_vacunacion", vacunas.getConsecutivo());
				if (!vacunas_pacientesService.existe(map)) {
					lbxVacunasPendientes.appendChild(crearRowVacuna(vacunas));
				}
			}
			validarAccionAgregarVacunas();
		}
		//log.info("Vacunas permitidas: " + vacunasPermitidas);
		lbxVacunasPendientes.invalidate();
	}

	public void agregarVacunasSeleccionadas() {
		Set<Listitem> listitemsVacunas = lbxVacunasPendientes
				.getSelectedItems();
		if (listitemsVacunas.isEmpty()) {
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					"Para esta opcion debe seleccionar un vacuna", IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		} else {
			List<Esquema_vacunacion> esquemavacunacion_List = new ArrayList<Esquema_vacunacion>();
			for (Listitem listitem : listitemsVacunas) {
				Esquema_vacunacion esquema_vacunacion = (Esquema_vacunacion) listitem
						.getValue();

				String nombre = "( DOSIS: " + esquema_vacunacion.getDosis()
						+ " - "
						+ esquema_vacunacion.getDescripcion().toUpperCase()
						+ ") "
						+ esquema_vacunacion.getVacunas().getDescripcion();
				esquema_vacunacion.getVacunas().setDescripcion(nombre);
				esquemavacunacion_List.add(esquema_vacunacion);
			}
			receptor.vacunasSeleccionadas(esquemavacunacion_List);
			detach();
		}
	}

	private void validarAccionAgregarVacunas() {
		tbnAgregarVacunas
				.setDisabled(lbxVacunasPendientes.getItems().isEmpty());
		tbnAgregarVacunas.setVisible(receptor != null);
	}

	private Listitem crearRowVacuna(final Esquema_vacunacion vacunas) {
		final String descripcion = "(" + vacunas.getDescripcion().toUpperCase()
				+ ") " + vacunas.getVacunas().getDescripcion();

		final Listitem listitem = new Listitem();
		listitem.setValue(vacunas);
		listitem.appendChild(new Listcell(vacunas.getCodigo_vacuna()));
		listitem.appendChild(new Listcell(descripcion));
		listitem.appendChild(new Listcell(vacunas.getVia_administracion()
				.equals("01") ? "INTRAMUSCULAR" : "ORAL"));
		listitem.appendChild(new Listcell(vacunas.getDosis() + ""));

		EstadoVacunaMacro estadoVacunaMacro = new EstadoVacunaMacro(this,
				"/images/cancelar_mini.png");
		estadoVacunaMacro.setTooltiptext("Cancelar");
		estadoVacunaMacro
				.setOnAccionEstadoVacunacion(new OnAccionEstadoVacunacion() {
					@Override
					public boolean onGuardar(String estado, String observacion) {
						int anio = L2HContraintDate.getAnio(paciente
								.getFecha_nacimiento());
						int mes = L2HContraintDate.getMes(paciente
								.getFecha_nacimiento());

						Vacunas vacuna = new Vacunas();
						vacuna.setCodigo_empresa(vacunas.getCodigo_empresa());
						vacuna.setCodigo_sucursal(vacunas.getCodigo_sucursal());
						vacuna.setCodigo_procedimiento(vacunas.getCodigo_vacuna());
						vacuna = getServiceLocator().getServicio(
								VacunasService.class).consultar(vacuna);

						/* registramos vacunas */
						Vacunas_pacientes vacunas_pacientes = new Vacunas_pacientes();
						vacunas_pacientes.setCodigo_empresa(paciente
								.getCodigo_empresa());
						vacunas_pacientes.setCodigo_sucursal(paciente
								.getCodigo_sucursal());
						vacunas_pacientes.setCodigo_procedimiento_vacuna(vacunas
								.getCodigo_vacuna());
						vacunas_pacientes.setNro_identificacion(paciente
								.getNro_identificacion());
						vacunas_pacientes.setId_esquema_vacunacion(vacunas
								.getConsecutivo());
						vacunas_pacientes.setDosis(vacunas.getDosis());
						vacunas_pacientes.setCreacion_date(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						vacunas_pacientes.setUltimo_update(new Timestamp(
								new GregorianCalendar().getTimeInMillis()));
						vacunas_pacientes.setCreacion_user(usuarios.getCodigo()
								.toString());
						vacunas_pacientes.setUltimo_user(usuarios.getCodigo()
								.toString());
						vacunas_pacientes.setCodigo_jeringa("");
						vacunas_pacientes.setValor_jeringa(0);
						vacunas_pacientes.setAnio(anio);
						vacunas_pacientes.setMeses(mes + 1);
						vacunas_pacientes.setVia_administracion(vacunas
								.getVia_administracion());
						vacunas_pacientes.setDescripcion_edad(descripcion);
						vacunas_pacientes.setFecha_vacunacion(new Timestamp(
								Calendar.getInstance().getTimeInMillis()));
						vacunas_pacientes.setRespuesta_4505(vacunas
								.getRespuesta_4505() + "");
						vacunas_pacientes.setEstado(estado);
						vacunas_pacientes.setObservacion_estado(observacion);
						pacientesService.crear(vacunas_pacientes);
						lbxVacunasPendientes.removeChild(listitem);
						return true;
					}
				});
		// estadoVacunaMacro.setLabel("Cancelar");
		Listcell listcell = new Listcell();
		listcell.appendChild(estadoVacunaMacro);
		listitem.appendChild(listcell);

		return listitem;
	}

	private void cargarHistorialVacunas() {
		rowHistorialVacunas.getChildren().clear();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("codigo_empresa", paciente.getCodigo_empresa());
		map.put("codigo_sucursal", paciente.getCodigo_sucursal());
		map.put("nro_identificacion", paciente.getNro_identificacion());
		listVacunasRealizadas = vacunas_pacientesService.listar(map);
		for (Vacunas_pacientes vacunas : listVacunasRealizadas) {
			rowHistorialVacunas.appendChild(crearRowVacunaPacientes(vacunas));
		}
		rowHistorialVacunas.invalidate();
	}

	private Row crearRowVacunaPacientes(Vacunas_pacientes vacunasPacientes) {
		Row row = new Row();
		row.setValue(vacunasPacientes);

		row.appendChild(new Label(vacunasPacientes.getDescripcion_edad()));
		row.appendChild(new LabelAlign("" + vacunasPacientes.getDosis(),
				AlignText.RIGHT));
		row.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd")
				.format(vacunasPacientes.getFecha_vacunacion())));
		row.appendChild(new Label(vacunasPacientes.getVia_administracion()
				.equals("01") ? "INTRAMUSCULAR" : "ORAL"));

		Usuarios usuarios = new Usuarios();
		usuarios.setCodigo_empresa(codigo_empresa);
		usuarios.setCodigo_sucursal(codigo_sucursal);
		usuarios.setCodigo(vacunasPacientes.getCreacion_user());
		usuarios = usuariosService.consultar(usuarios);

		// if(vacunasPacientes.getEstado().equals(IConstantes.ESTADO_VACUNA_NUEVA)){
		String codigo = "" + usuarios != null ? usuarios.getCodigo() : "";
		String descipcion = "" + usuarios != null ? usuarios.getNombres() + " "
				+ usuarios.getApellidos() : "";
		// }
		row.appendChild(new Label(codigo));
		row.appendChild(new Label(descipcion));

		String descripcion_tipo = Utilidades.getDescripcionElemento(
				vacunasPacientes.getEstado(), "cancelacion_vacuna",
				getServiceLocator());
		row.appendChild(new Label(descripcion_tipo.toUpperCase()));
		return row;
	}

	/**
	 * Esta interface permite una comunicacion con el padre de la vista de
	 * historial de vacunas
	 * 
	 * @author Luis Miguel
	 * */
	public interface IVacunacionWindowReceptor {
		public void vacunasSeleccionadas(
				List<Esquema_vacunacion> esquema_vacunacions);
	}

}
