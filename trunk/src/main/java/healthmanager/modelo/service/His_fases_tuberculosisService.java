/*
 * His_fases_tuberculosisServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.His_fases_tuberculosis;
import healthmanager.modelo.dao.His_fases_tuberculosisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("his_fases_tuberculosisService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class His_fases_tuberculosisService implements Serializable{

	@Autowired
	private His_fases_tuberculosisDao his_fases_tuberculosisDao;
	private String limit;

	public void crear(His_fases_tuberculosis his_fases_tuberculosis) {

		if (consultar(his_fases_tuberculosis) == null) {
			his_fases_tuberculosisDao.crear(his_fases_tuberculosis);
		} else {
			actualizar(his_fases_tuberculosis);
		}

		// try{
		// if(consultar(his_fases_tuberculosis)!=null){
		// throw new
		// HealthmanagerException("His_fases_tuberculosis ya se encuentra en la base de datos");
		// }
		// his_fases_tuberculosisDao.crear(his_fases_tuberculosis);
		// }catch(Exception e){
		// throw new HealthmanagerException(e.getMessage(),e);
		// }
	}

	public int actualizar(His_fases_tuberculosis his_fases_tuberculosis) {
		try {
			return his_fases_tuberculosisDao.actualizar(his_fases_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public His_fases_tuberculosis consultar(
			His_fases_tuberculosis his_fases_tuberculosis) {
		try {
			return his_fases_tuberculosisDao.consultar(his_fases_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(His_fases_tuberculosis his_fases_tuberculosis) {
		try {
			return his_fases_tuberculosisDao.eliminar(his_fases_tuberculosis);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<His_fases_tuberculosis> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return his_fases_tuberculosisDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return his_fases_tuberculosisDao.existe(parameters);
	}

}