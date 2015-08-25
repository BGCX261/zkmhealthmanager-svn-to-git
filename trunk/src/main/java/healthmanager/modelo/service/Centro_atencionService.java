/*
 * Centro_atencionServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_barrio;
import healthmanager.modelo.bean.Centro_usuario;
import healthmanager.modelo.dao.Centro_atencionDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("centro_atencionService")
@Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Centro_atencionService implements Serializable {

	@Autowired
	private Centro_atencionDao centro_atencionDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private GeneralExtraService generalExtraService;

	public Centro_atencion guardar(Map<String, Object> datos) {
		try {
			Centro_atencion centro_atencion = (Centro_atencion) datos
					.get("centro_atencion");
			List<Map<String, Object>> lista_detalle_barrios = (List<Map<String, Object>>) datos
					.get("lista_detalle_barrios");

			String accion = (String) datos.get("accion");
			if (accion.equalsIgnoreCase("registrar")) {
				String codigo_centro = consecutivoService.crearConsecutivo(
						centro_atencion.getCodigo_empresa(),
						centro_atencion.getCodigo_sucursal(),
						IConstantes.CONS_CENTRO_ATENCION);
				centro_atencion.setCodigo_centro(consecutivoService
						.getZeroFill(codigo_centro, 2));
				if (consultar(centro_atencion) != null) {
					throw new HealthmanagerException(
							"Centro de atencion"
									+ codigo_centro
									+ " ya se encuentra en la base de datos actualize el consecutivo del centro de atencion");
				}
				centro_atencionDao.crear(centro_atencion);
				consecutivoService.actualizarConsecutivo(
						centro_atencion.getCodigo_empresa(),
						centro_atencion.getCodigo_sucursal(),
						IConstantes.CONS_CENTRO_ATENCION,
						centro_atencion.getCodigo_centro());
			} else {
				centro_atencionDao.actualizar(centro_atencion);
				Centro_barrio auxCentro_barrio = new Centro_barrio();
				auxCentro_barrio.setCodigo_empresa(centro_atencion
						.getCodigo_empresa());
				auxCentro_barrio.setCodigo_sucursal(centro_atencion
						.getCodigo_sucursal());
				auxCentro_barrio.setCodigo_centro(centro_atencion
						.getCodigo_centro());
				generalExtraService.eliminar(auxCentro_barrio);

				Centro_usuario auxCentro_usuario = new Centro_usuario();
				auxCentro_usuario.setCodigo_empresa(centro_atencion
						.getCodigo_empresa());
				auxCentro_usuario.setCodigo_sucursal(centro_atencion
						.getCodigo_sucursal());
				auxCentro_usuario.setCodigo_centro(centro_atencion
						.getCodigo_centro());
				generalExtraService.eliminar(auxCentro_usuario);
			}
			int i = 1;
			for (Map<String, Object> map : lista_detalle_barrios) {
				Centro_barrio centro_barrio = (Centro_barrio) map
						.get("centro_barrio");
				if (centro_barrio == null) {
					throw new Exception("Error en la fila BARRIOS" + i
							+ " registro presenta problemas (Nulo)");
				}
				centro_barrio.setCodigo_empresa(centro_atencion
						.getCodigo_empresa());
				centro_barrio.setCodigo_sucursal(centro_atencion
						.getCodigo_sucursal());
				centro_barrio.setCodigo_centro(centro_atencion
						.getCodigo_centro());

				Centro_barrio auxCentro_barrio = new Centro_barrio();
				auxCentro_barrio.setCodigo_barrio(centro_barrio
						.getCodigo_barrio());
				if (generalExtraService.consultar(auxCentro_barrio) != null) {
					throw new Exception(
							"Error en la fila BARRIOS"
									+ i
									+ " Barrio con codigo "
									+ centro_barrio.getCodigo_barrio()
									+ " ya se eencuentra asignado a un centro de atencion");
				}
				generalExtraService.crear(centro_barrio);
				i++;
			}

			return centro_atencion;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Centro_atencion centro_atencion) {
		try {
			if (consultar(centro_atencion) != null) {
				throw new HealthmanagerException(
						"Centro_atencion ya se encuentra en la base de datos");
			}
			centro_atencionDao.crear(centro_atencion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Centro_atencion centro_atencion) {
		try {
			return centro_atencionDao.actualizar(centro_atencion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Centro_atencion consultar(Centro_atencion centro_atencion) {
		try {
			return centro_atencionDao.consultar(centro_atencion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Centro_atencion centro_atencion) {
		try {
			return centro_atencionDao.eliminar(centro_atencion);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Centro_atencion> listar(Map<String, Object> parameter) {
		try {
			return centro_atencionDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Integer totalResultados(Map<String, Object> parametros) {
		try {
			return centro_atencionDao.totalResultados(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.centro_atencionDao.existe(parameters);
	}

}
