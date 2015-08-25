/*
 * Configuracion_pagaresServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Configuracion_pagares;
import contaweb.modelo.dao.Configuracion_pagaresDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("configuracion_pagaresService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Configuracion_pagaresService implements Serializable{

	@Autowired
	private Configuracion_pagaresDao configuracion_pagaresDao;
	private String limit;

	public void crear(Configuracion_pagares configuracion_pagares) {
		try {
			if (consultar(configuracion_pagares) != null) {
				throw new HealthmanagerException(
						"Configuracion_pagares ya se encuentra en la base de datos");
			}
			configuracion_pagaresDao.crear(configuracion_pagares);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Configuracion_pagares configuracion_pagares) {
		try {
			return configuracion_pagaresDao.actualizar(configuracion_pagares);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Configuracion_pagares consultar(
			Configuracion_pagares configuracion_pagares) {
		try {
			return configuracion_pagaresDao.consultar(configuracion_pagares);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Configuracion_pagares configuracion_pagares) {
		try {
			return configuracion_pagaresDao.eliminar(configuracion_pagares);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Configuracion_pagares> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return configuracion_pagaresDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return configuracion_pagaresDao.existe(parameters);
	}

}