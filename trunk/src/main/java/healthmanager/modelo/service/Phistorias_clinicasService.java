/*
 * Phistorias_clinicasServiceImpl.java
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

import com.framework.constantes.IConstantes;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Phistorias_clinicas;
import healthmanager.modelo.service.ConsecutivoService;
import healthmanager.modelo.dao.Phistorias_clinicasDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("phistorias_clinicasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Phistorias_clinicasService implements Serializable{

	private String limit;

	@Autowired
	private Phistorias_clinicasDao phistorias_clinicasDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public void crear(Phistorias_clinicas phistorias_clinicas) {
		try {

			String consecutivo = consecutivoService.crearConsecutivo(
					phistorias_clinicas.getCodigo_empresa(),
					phistorias_clinicas.getCodigo_sucursal(),
					IConstantes.CONS_PHISTORIAS_CLINICAS);
			String codigo = consecutivoService.getZeroFill(consecutivo, 5);
			phistorias_clinicas.setCodigo(codigo);

			if (consultar(phistorias_clinicas) != null) {
				throw new HealthmanagerException(
						"Phistorias_clinicas ya se encuentra en la base de datos");
			}
			phistorias_clinicasDao.crear(phistorias_clinicas);

			consecutivoService.actualizarConsecutivo(
					phistorias_clinicas.getCodigo_empresa(),
					phistorias_clinicas.getCodigo_sucursal(),
					IConstantes.CONS_PHISTORIAS_CLINICAS, codigo);

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Phistorias_clinicas phistorias_clinicas) {
		try {
			return phistorias_clinicasDao.actualizar(phistorias_clinicas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Phistorias_clinicas consultar(Phistorias_clinicas phistorias_clinicas) {
		try {
			return phistorias_clinicasDao.consultar(phistorias_clinicas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Phistorias_clinicas phistorias_clinicas) {
		try {
			return phistorias_clinicasDao.eliminar(phistorias_clinicas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Phistorias_clinicas> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return phistorias_clinicasDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.phistorias_clinicasDao.existe(parameters);
	}

}
