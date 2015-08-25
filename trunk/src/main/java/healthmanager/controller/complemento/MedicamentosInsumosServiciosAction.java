package healthmanager.controller.complemento;

import healthmanager.controller.ZKWindow;

import java.util.Date;
import java.util.Map;

import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;

import com.framework.constantes.ITiposServicio;
import com.framework.macros.facturacion.ServiciosFacturacionMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro.CargaRapidaInformacionUltimoRegistro;
import com.framework.res.Res;

public class MedicamentosInsumosServiciosAction extends ZKWindow {
	
	@View private Datebox dtbxFecha;
	@View private Textbox tbxNumero_autorizacion;
	
	private Map<String, Object> mapa_servicios;
	private CargaRapidaInformacionUltimoRegistro cargaRapidaInformacionUltimoRegistro; 

	@Override
	public void init() throws Exception {
	}

	@Override
	public void params(Map<String, Object> map) {
//		fecha = (Date) map.get(ITiposServicio.PARAM_FECHA);
		mapa_servicios = (Map<String, Object>) map.get(ITiposServicio.PARAM_RIPS); 
		cargaRapidaInformacionUltimoRegistro = (CargaRapidaInformacionUltimoRegistro) map
				.get(ITiposServicio.PARAM_DIAGNOSTICO);
	} 
	
	@Override
	public void _despuesIniciar() {
		Res.cargarAutomatica(tbxNumero_autorizacion, mapa_servicios, ServiciosFacturacionMacro.NRO_AUTORIZACION, null); 
		Res.cargarAutomatica(dtbxFecha, mapa_servicios, ServiciosFacturacionMacro.FECHA_REALIZACION, null);
		
		String nro_autorizacion = (String) mapa_servicios.get(ServiciosFacturacionMacro.NRO_AUTORIZACION); 
		Date fecha_registro = (Date) mapa_servicios.get(ServiciosFacturacionMacro.FECHA_REALIZACION); 
		 
		tbxNumero_autorizacion.setValue(nro_autorizacion);
		dtbxFecha.setValue(fecha_registro != null ? fecha_registro
				: cargaRapidaInformacionUltimoRegistro.getFecha()); 
	}
	
	@Override
	public void detach() {
		mapa_servicios.put(ServiciosFacturacionMacro.NRO_AUTORIZACION,
				tbxNumero_autorizacion.getValue());
		mapa_servicios.put(ServiciosFacturacionMacro.FECHA_REALIZACION,
				dtbxFecha.getValue());
		super.detach();
	}

}
