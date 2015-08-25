/*
 * EmpresaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Empresa;
import contaweb.modelo.dao.EmpresaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("empresaContawebService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class EmpresaService implements Serializable{

	private String limit;

	@Autowired
	private EmpresaDao empresaDao;

	public void crear(Empresa empresa) {
		try {
			if (consultar(empresa) != null) {
				throw new HealthmanagerException(
						"Empresa ya se encuentra en la base de datos");
			}
			empresaDao.crear(empresa);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Empresa empresa) {
		try {
			return empresaDao.actualizar(empresa);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Empresa consultar(Empresa empresa) {
		try {
			return empresaDao.consultar(empresa);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Empresa empresa) {
		try {
			return empresaDao.eliminar(empresa);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Empresa> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return empresaDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.empresaDao.existe(parameters);
	}

}
