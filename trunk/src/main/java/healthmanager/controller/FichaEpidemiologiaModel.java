package healthmanager.controller;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.service.Ficha_epidemiologia_nnService;
import healthmanager.modelo.service.Impresion_diagnosticaService;

import java.util.List;
import java.util.Map;

import com.framework.interfaces.IDatosFichaEpidemiologia;

public abstract class FichaEpidemiologiaModel<T extends IDatosFichaEpidemiologia> extends ZKWindow {

	public abstract boolean validarFichaEpidemiologia();

	public abstract T obtenerFichaEpidemiologia();
	

	public abstract T consultarDatos(Map<String, Object> map, Ficha_epidemiologia ficha_epidemiologia)throws Exception;
	
	
	public abstract void mostrarDatos(T dato)throws Exception;
	
	//public Usuarios usuarios;
	
	
	@Override
	public abstract void params(Map<String, Object> map);

	public static void guardarDatosImpresionDiagnostica(Long codigo_historia,
			Map<String, Object> mapa_datos,
			Ficha_epidemiologia_nnService ficha_epidemiologia_nnService,
			Impresion_diagnosticaService impresion_diagnosticaService, String codigo_medico)
			throws Exception {
		Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) mapa_datos
				.get("impresion_diagnostica");
		if (impresion_diagnostica != null) {
			Admision admision = (Admision) mapa_datos.get("admision");
			impresion_diagnostica.setCodigo_historia(codigo_historia);
			impresion_diagnostica.setNro_identificacion(admision.getNro_identificacion());
			impresion_diagnosticaService.guardarDatos(impresion_diagnostica);

			List<IDatosFichaEpidemiologia> listado_fichas = impresion_diagnostica
					.getListado_fichas();
			//log.info("---->"+listado_fichas);
			
			for (IDatosFichaEpidemiologia iDatosFichaEpidemiologia : listado_fichas) {
				iDatosFichaEpidemiologia.setCodigo_historia(codigo_historia);
				mapa_datos.put("datos_ficha_epidemiologia",
						iDatosFichaEpidemiologia);
				mapa_datos.put("identificacion", admision.getNro_identificacion());
				mapa_datos.put("codigo_medico",codigo_medico);
				
				ficha_epidemiologia_nnService.guardar(mapa_datos);
			}
		}
	}

}
