package healthmanager.controller;


import healthmanager.modelo.bean.Citas_web;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.Citas_webService;

import java.util.Date;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;

public class Detalle_cita_webAction   extends ZKWindow{
	
//	private static Logger log = Logger.getLogger(Detalle_cita_webAction.class);
	
	@View private Textbox tbxCodigo_cita;
	@View private Textbox tbxPrestador;
	@View private Textbox tbxPaciente;
	@View private Datebox dtbFecha_cita;
	@View private Textbox tbxHora;
	

	@Override
	public void init() throws Exception {
		
	}
	
	public void cargarCita(String codigo_cita){
		try {
			Citas_web citas = new Citas_web();
			citas.setCodigo_empresa(empresa.getCodigo_empresa());
			citas.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			citas.setCodigo_cita(codigo_cita);
			citas = getServiceLocator().getServicio(Citas_webService.class).consultar(citas);
			if(citas==null){
				MensajesUtil.mensajeAlerta("Alerta !!", "Registro de cita no existe");
				this.detach();
				return;
			}
			
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(citas.getCodigo_empresa());
			prestadores.setCodigo_sucursal(citas.getCodigo_sucursal());
//			prestadores.setNro_identificacion(citas.getCodigo_prestador());
			prestadores = getServiceLocator().getPrestadoresService().consultar(prestadores);
			
			Paciente paciente = citas.getPaciente();
			
			tbxCodigo_cita.setValue(citas.getCodigo_cita());
			tbxPrestador.setValue((prestadores!=null?prestadores.getNombres()+" "+prestadores.getApellidos():""));
			tbxPaciente.setValue((paciente!=null?paciente.getNombreCompleto():""));
			dtbFecha_cita.setValue(new Date(citas.getFecha_cita().getTime()));
			tbxHora.setValue(citas.getHora());
			
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	public void cancelarCita(boolean accion){
		try {
			final boolean auxAccion = accion;
			Messagebox.show("Usuario " + paciente_session.getNombreCompleto() 
					+ " está seguro que desea "+(accion?"Cancelar":"Eliminar")+" la cita", "Informacion",
					Messagebox.OK + Messagebox.CANCEL,
					Messagebox.INFORMATION, new EventListener() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							guardarEstadoCita(auxAccion);
						}
			});
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
	
	private void guardarEstadoCita(boolean auxAccion)throws Exception{
		Citas_web citas = new Citas_web();
		citas.setCodigo_empresa(empresa.getCodigo_empresa());
		citas.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		citas.setCodigo_cita(tbxCodigo_cita.getValue());
		citas = getServiceLocator().getServicio(Citas_webService.class).consultar(citas);
		//log.info("citas: "+citas);
		if(citas==null){
			MensajesUtil.mensajeAlerta("Alerta !!", "Registro de cita no existe");
		}else{
			if(auxAccion){
				citas.setEstado("5");
				citas.setCodigo_detalle_horario(-1);
				getServiceLocator().getServicio(Citas_webService.class).actualizar(citas);
			}else{
				getServiceLocator().getServicio(Citas_webService.class).eliminar(citas);
			}
			
			MensajesUtil.mensajeInformacion("Exito", "Registro de Cita "+
					(auxAccion?"canceló":"eliminó")+" exitosamente");
			if(this.getParent() instanceof Control_citas_pacientesAction){
				Control_citas_pacientes_WebAction action = (Control_citas_pacientes_WebAction) this
						.getParent();
				action.listarCitas();
				this.detach();
			}
		}
	}

}
