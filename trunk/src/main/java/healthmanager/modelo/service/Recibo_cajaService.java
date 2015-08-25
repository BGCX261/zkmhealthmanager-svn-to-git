/*
 * BarrioServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Detalle_recibo_caja;
import healthmanager.modelo.bean.Recibo_caja;
import healthmanager.modelo.dao.Recibo_cajaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("recibo_cajaService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Recibo_cajaService implements Serializable {

	@Autowired
	private Recibo_cajaDao recibo_cajaDao;
	@Autowired
	private GeneralExtraService generalExtraService;

	@SuppressWarnings("unchecked")
	public void guardarDatos(Map<String, Object> datos) {
		try {
			String accion = (String) datos.get("accion");
			Recibo_caja recibo_caja = (Recibo_caja) datos.get("recibo_caja");
			List<Detalle_recibo_caja> listado_detalles = (List<Detalle_recibo_caja>) datos
					.get("listado_detalles");
			if (accion.equalsIgnoreCase("registrar")) {
				recibo_cajaDao.crear(recibo_caja);
			} else {
				recibo_cajaDao.actualizar(recibo_caja);
				Detalle_recibo_caja detalle_recibo_caja = new Detalle_recibo_caja();
				detalle_recibo_caja.setId_recibo(recibo_caja.getId());
				generalExtraService.eliminar(detalle_recibo_caja,
						"eliminar_recibo");
			}
			for (Detalle_recibo_caja detalle_recibo_caja : listado_detalles) {
				detalle_recibo_caja.setId_recibo(recibo_caja.getId());
				generalExtraService.crear(detalle_recibo_caja);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Recibo_caja recibo_caja) {
		try {
			if (consultar(recibo_caja) != null) {
				throw new HealthmanagerException(
						"recibo_caja ya se encuentra en la base de datos");
			}
			recibo_cajaDao.crear(recibo_caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Recibo_caja recibo_caja) {
		try {
			return recibo_cajaDao.actualizar(recibo_caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Recibo_caja consultar(Recibo_caja recibo_caja) {
		try {
			return recibo_cajaDao.consultar(recibo_caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public Recibo_caja consultar_recibo(Recibo_caja recibo_caja) {
		try {
			return recibo_cajaDao.consultar_recibo(recibo_caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Recibo_caja recibo_caja) {
		try {
			return recibo_cajaDao.eliminar(recibo_caja);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Recibo_caja> listar(Map<String, Object> parameter) {
		try {
			return recibo_cajaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.recibo_cajaDao.existe(parameters);
	}

	public Integer totalResultados(Map<String, Object> parameters) {
		try {
			return recibo_cajaDao.totalResultados(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
