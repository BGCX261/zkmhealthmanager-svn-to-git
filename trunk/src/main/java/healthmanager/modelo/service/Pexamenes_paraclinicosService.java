/*
 * Pexamenes_paraclinicosServiceImpl.java
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
import healthmanager.modelo.bean.Pexamenes_paraclinicos;
import healthmanager.modelo.dao.Pexamenes_paraclinicosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("pexamenes_paraclinicosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Pexamenes_paraclinicosService implements Serializable{

	@Autowired
	private Pexamenes_paraclinicosDao pexamenes_paraclinicosDao;
	@Autowired
	private ConsecutivoService consecutivoService;

	public void crear(Pexamenes_paraclinicos pexamenes_paraclinicos) {
		try {
			String consecutivo = consecutivoService.crearConsecutivo(
					pexamenes_paraclinicos.getCodigo_empresa(),
					pexamenes_paraclinicos.getCodigo_sucursal(),
					IConstantes.CONS_PEXAMENES_PARACLINICOS);
			String codigo = consecutivoService.getZeroFill(consecutivo, 5);
			pexamenes_paraclinicos.setCodigo(codigo);
			if (consultar(pexamenes_paraclinicos) != null) {
				throw new HealthmanagerException(
						"Pexamenes_paraclinicos ya se encuentra en la base de datos");
			}
			pexamenes_paraclinicosDao.crear(pexamenes_paraclinicos);
			consecutivoService.actualizarConsecutivo(
					pexamenes_paraclinicos.getCodigo_empresa(),
					pexamenes_paraclinicos.getCodigo_sucursal(),
					IConstantes.CONS_PEXAMENES_PARACLINICOS, codigo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Pexamenes_paraclinicos pexamenes_paraclinicos) {
		try {
			return pexamenes_paraclinicosDao.actualizar(pexamenes_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Pexamenes_paraclinicos consultar(
			Pexamenes_paraclinicos pexamenes_paraclinicos) {
		try {
			return pexamenes_paraclinicosDao.consultar(pexamenes_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Pexamenes_paraclinicos pexamenes_paraclinicos) {
		try {
			return pexamenes_paraclinicosDao.eliminar(pexamenes_paraclinicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Pexamenes_paraclinicos> listar(Map<String, Object> parameter) {
		try {
			return pexamenes_paraclinicosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.pexamenes_paraclinicosDao.existe(parameters);
	}

}
