/*
 * Orden_servicio2ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_orden2;
import healthmanager.modelo.bean.Orden_servicio2;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Detalle_orden2Dao;
import healthmanager.modelo.dao.Orden_servicio2Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("orden_servicio2Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Orden_servicio2Service implements Serializable{

	private String limit;

	@Autowired
	private Orden_servicio2Dao orden_servicio2Dao;
	@Autowired
	private Detalle_orden2Dao detalle_orden2Dao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public Orden_servicio2 guardar(Map<String, Object> datos) {
		try {
			Orden_servicio2 orden_servicio2 = (Orden_servicio2) datos
					.get("orden_servicio2");
			List<Detalle_orden2> lista_detalle = (List<Detalle_orden2>) datos
					.get("lista_detalle");
			String accion = (String) datos.get("accion");

			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_orden = consecutivoService
						.crearConsecutivo(
								orden_servicio2.getCodigo_empresa(),
								orden_servicio2.getCodigo_sucursal(),
								"ORDEN_SERVICIO2");
				orden_servicio2.setCodigo_orden(codigo_orden);
				if (consultar(orden_servicio2) != null) {
					throw new HealthmanagerException(
							"Orden de servicio nro "
									+ codigo_orden
									+ " ya se encuentra en la base de datos actualize el consecutivo de la orden de servicio");
				}
				orden_servicio2Dao.crear(orden_servicio2);
				consecutivoService.actualizarConsecutivo(
						orden_servicio2.getCodigo_empresa(),
						orden_servicio2.getCodigo_sucursal(),
						"ORDEN_SERVICIO2", orden_servicio2.getCodigo_orden());
			} else {
				orden_servicio2Dao.actualizar(orden_servicio2);
			}

			// Registramos los procedimientos //
			Detalle_orden2 detalle_orden2Aux = new Detalle_orden2();
			detalle_orden2Aux.setCodigo_empresa(orden_servicio2
					.getCodigo_empresa());
			detalle_orden2Aux.setCodigo_sucursal(orden_servicio2
					.getCodigo_sucursal());
			detalle_orden2Aux
					.setCodigo_orden(orden_servicio2.getCodigo_orden());
			detalle_orden2Dao.eliminar(detalle_orden2Aux);

			int i = 0;
			for (Detalle_orden2 detalle_orden2 : lista_detalle) {
				detalle_orden2.setConsecutivo((i + 1) + "");
				detalle_orden2.setCodigo_orden(orden_servicio2
						.getCodigo_orden());
				detalle_orden2.setCodigo_empresa(orden_servicio2
						.getCodigo_empresa());
				detalle_orden2.setCodigo_sucursal(orden_servicio2
						.getCodigo_sucursal());
				if (detalle_orden2Dao.consultar(detalle_orden2) != null) {
					throw new HealthmanagerException(
							"Procedimiento  de la fila " + (i + 1)
									+ " se encuentra repetido");
				}
				detalle_orden2Dao.crear(detalle_orden2);
				i++;
			}

			// orden_servicio2 = consultar(orden_servicio2);
			orden_servicio2.setLista_detalle(lista_detalle);

			return orden_servicio2;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Orden_servicio2 orden_servicio2) {
		try {
			if (consultar(orden_servicio2) != null) {
				throw new HealthmanagerException(
						"Orden_servicio2 ya se encuentra en la base de datos");
			}
			orden_servicio2Dao.crear(orden_servicio2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Orden_servicio2 orden_servicio2) {
		try {
			return orden_servicio2Dao.actualizar(orden_servicio2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Orden_servicio2 consultar(Orden_servicio2 orden_servicio2) {
		try {
			return orden_servicio2Dao.consultar(orden_servicio2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Orden_servicio2 orden_servicio2) {
		try {
			return orden_servicio2Dao.eliminar(orden_servicio2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Orden_servicio2> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return orden_servicio2Dao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.orden_servicio2Dao.existe(parameters);
	}

}
