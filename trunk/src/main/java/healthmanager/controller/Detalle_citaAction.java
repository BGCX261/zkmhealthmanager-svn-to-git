package healthmanager.controller;

import healthmanager.modelo.bean.Citas;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;

import java.util.Date;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;

public class Detalle_citaAction extends ZKWindow {

	// private static Logger log = Logger.getLogger(Detalle_citaAction.class);

	@View
	private Textbox tbxCodigo_cita;
	@View
	private Textbox tbxPrestador;
	@View
	private Textbox tbxPaciente;
	@View
	private Datebox dtbFecha_cita;
	@View
	private Textbox tbxHora;

	@Override
	public void init() throws Exception {

	}

	public void cargarCita(String codigo_cita) {
		try {
			Citas citas = new Citas();
			citas.setCodigo_empresa(empresa.getCodigo_empresa());
			citas.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			citas.setCodigo_cita(codigo_cita);
			citas = getServiceLocator().getCitasService().consultar(citas);
			if (citas == null) {
				MensajesUtil.mensajeAlerta("Alerta !!",
						"Registro de cita no existe");
				this.detach();
				return;
			}

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(citas.getCodigo_empresa());
			prestadores.setCodigo_sucursal(citas.getCodigo_sucursal());
			prestadores.setNro_identificacion(citas.getCodigo_prestador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(citas.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			tbxCodigo_cita.setValue(citas.getCodigo_cita());
			tbxPrestador.setValue((prestadores != null ? prestadores
					.getNombres() + " " + prestadores.getApellidos() : ""));
			tbxPaciente.setValue((paciente != null ? paciente
					.getNombreCompleto() : ""));
			dtbFecha_cita.setValue(new Date(citas.getFecha_cita().getTime()));
			tbxHora.setValue(citas.getHora());

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void cancelarCita() {
		try {
			Messagebox.show("Usuario " + usuarios.getNombres()
					+ " está seguro que desea cancelar la cita?",
					"Informacion", Messagebox.OK + Messagebox.CANCEL,
					Messagebox.INFORMATION, new EventListener() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							guardarEstadoCita("cancelar");
						}
					});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarCita() {
		try {
			Messagebox.show("Usuario " + usuarios.getNombres()
					+ " está seguro que desea eliminar la cita?",
					"Informacion", Messagebox.OK + Messagebox.CANCEL,
					Messagebox.INFORMATION, new EventListener() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							guardarEstadoCita("eliminar");
						}
					});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void cumplirCitaManual() {
		try {
			Messagebox
					.show("Usuario "
							+ usuarios.getNombres()
							+ "¿Está seguro que desea marcar estar cita como cumplida ?",
							"Informacion", Messagebox.OK + Messagebox.CANCEL,
							Messagebox.INFORMATION, new EventListener() {
								@Override
								public void onEvent(Event arg0)
										throws Exception {
									guardarEstadoCita("cumplir");
								}
							});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	private void guardarEstadoCita(String opcion) throws Exception {
		Citas citas = new Citas();
		citas.setCodigo_empresa(empresa.getCodigo_empresa());
		citas.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		citas.setCodigo_cita(tbxCodigo_cita.getValue());
		citas = getServiceLocator().getCitasService().consultar(citas);
		// log.info("citas: "+citas);
		if (citas == null) {
			MensajesUtil
					.mensajeAlerta("Alerta!!", "Registro de cita no existe");
		} else {
			if (opcion.equals("cancelar")) {
				citas.setEstado("5");
				citas.setCodigo_detalle_horario(-1);
				getServiceLocator().getCitasService().actualizar(citas);
			} else if (opcion.equals("eliminar")) {
				getServiceLocator().getCitasService().eliminar(citas);
			} else if (opcion.equals("cumplir")) {
				citas.setEstado("3");
				getServiceLocator().getCitasService().actualizar(citas);
			}

			MensajesUtil.mensajeInformacion("Exito",
					"Registro de Cita se actualizo exitosamente");
			if (this.getParent() instanceof Control_citas_pacientesAction) {
				Control_citas_pacientesAction action = (Control_citas_pacientesAction) this
						.getParent();
				action.listarCitas();
				this.detach();
			}
		}
	}

}
