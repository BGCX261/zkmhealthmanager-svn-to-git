package healthmanager.controller;

import healthmanager.modelo.bean.Frecuencia_ordenamiento;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

import com.framework.interfaces.OnProcedimientoEvent;
import com.framework.util.MensajesUtil;

public class FrecuenciaOrdenamientoAction extends ZKWindow {

	@View
	private Textbox tbxCodigo;
	@View
	private Textbox tbxCodigo_cups;
	@View
	private Textbox tbxNombre;
	@View
	private Datebox dtbxFechaRealizacion;

	/* evento */
	private OnProcedimientoEvent onProcedimientoEvent;
	private Frecuencia_ordenamiento frec_orden;
	private int fr;

	@Override
	public void init() {
		loadDatosInView();
	}

	private void loadDatosInView() {
		
		Map map = Executions.getCurrent().getArg();
		String codigo = (String) map.get("codigo");
		String codigo_sups = (String) map.get("codigo_cups");
		String nombreve = (String) map.get("nombreve");
		frec_orden = (Frecuencia_ordenamiento) map.get("frec_orden"); 
		fr = (Integer) map.get("fr");  

		tbxCodigo.setValue(codigo);
		tbxCodigo_cups.setValue(codigo_sups);
		tbxNombre.setValue(nombreve);
	}

	public void acept() {
		try {
			if (validateFecha()) {
				frec_orden.setFecha_realizacion(new Timestamp(dtbxFechaRealizacion.getValue().getTime()));
				if(frec_orden.getCantidad() == null)
				   frec_orden.setCantidad(1);
				getServiceLocator().getFrecuencia_ordenamientoService().actualizar(frec_orden);
				
				Timestamp today = new Timestamp(Calendar.getInstance().getTimeInMillis());
	    		Calendar calendar = Calendar.getInstance();
	    		calendar.setTime(frec_orden.getFecha_realizacion());
	    		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + fr);
	    		Timestamp fecha_para_volver_hacer = new Timestamp(calendar.getTimeInMillis());
	    		
	    		/**/
	    		   //System.Out.Println("*****************************");
				   //System.Out.Println("Frecuencia de orden: " + fr);
				   //System.Out.Println("Fecha realizacion: " + frec_orden.getFecha_realizacion());
				   //System.Out.Println("Fecha volver hacer: " + fecha_para_volver_hacer);
				   //System.Out.Println("Fecha de hor: " + today);
				   //System.Out.Println("*****************************");
				/**/
	    		
	    		if(today.compareTo(fecha_para_volver_hacer) > 0){
	    			if (onProcedimientoEvent != null) {
						onProcedimientoEvent.onAcept();
					}
	    		}else{
	    			Messagebox
					.show("para poder enviar este procedimiento tiene que pasar de esta fecha ? para la realizacion de este procedimiento nuevamente".replace(
							"?",
							new SimpleDateFormat(
									"yyyy/MM/dd hh:mm a")
									.format(fecha_para_volver_hacer)));
	    		}
				onClose();
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "",this);
		}
	}

	private boolean validateFecha() throws Exception{
		dtbxFechaRealizacion
				.setStyle("text-transform:uppercase;background-color:white");

		String mensaje = "Los campos marcados con (*) son obligatorios";

		boolean valida = true;

		if (dtbxFechaRealizacion.getText().trim().equals("")) {
			dtbxFechaRealizacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			Messagebox.show("" + mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public void noSeLoHaRealizado() {
		if (onProcedimientoEvent != null) {
			if(frec_orden != null){
				// eliminar ****
				getServiceLocator().getFrecuencia_ordenamientoService().eliminar(frec_orden);
			}
			onProcedimientoEvent.onCancel();
		}
		onClose();
	}

	public OnProcedimientoEvent getOnProcedimientoEvent() {
		return onProcedimientoEvent;
	}

	public void setOnProcedimientoEvent(
			OnProcedimientoEvent onProcedimientoEvent) {
		this.onProcedimientoEvent = onProcedimientoEvent;
	}
}
