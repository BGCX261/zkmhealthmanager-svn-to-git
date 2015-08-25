package healthmanager.controller.admin;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Admision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Longbox;

public class Actualizar_facturasAction extends ZKWindow {

	private static final long serialVersionUID = -9145887024839938515L;

	private List<Map<String,Object>> listado_facturas = new ArrayList<Map<String,Object>>();

	@View
	private Longbox lgxTotal;

//	private int current;

	@Override
	public void init() throws Exception {

	}

	public void consultarFacturas() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("centro_vacio", "_centro_vacio");

		listado_facturas = getServiceLocator().getFacturacionService()
				.listar_facturas(parametros);

		lgxTotal.setValue(new Long(listado_facturas.size()));
	}

	public void iniciarActualizacionFacturas() {
		
		if (listado_facturas != null) {
//			current = 0;
			for (Map<String,Object> map : listado_facturas) {
				String codigo_empresa = (String) map.get("codigo_empresa");
				String codigo_sucursal = (String) map.get("codigo_sucursal");
				String nro_ingreso = (String) map.get("nro_ingreso");
				String codigo_tercero = (String) map.get("codigo_tercero");

				Admision admision = new Admision();
				admision.setCodigo_empresa(codigo_empresa);
				admision.setCodigo_sucursal(codigo_sucursal);
				admision.setNro_ingreso(nro_ingreso);
				admision.setNro_identificacion(codigo_tercero);
				admision = getServiceLocator().getAdmisionService().consultar(
						admision);

				if (admision != null) {
					getServiceLocator().getFacturacionService()
							.actualizarCentro(admision);
				}
				
//				current++;
				//log.info("Actualizadas ==> "+current);
			}
		}
	}

}