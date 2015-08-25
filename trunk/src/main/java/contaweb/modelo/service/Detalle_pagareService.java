/*
 * Detalle_pagareServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package contaweb.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import contaweb.modelo.bean.Detalle_pagare;
import contaweb.modelo.dao.Detalle_pagareDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("detalle_pagareService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Detalle_pagareService implements Serializable{

	@Autowired
	private Detalle_pagareDao detalle_pagareDao;
	private String limit;

	public void crear(Detalle_pagare detalle_pagare) {
		try {
			if (consultar(detalle_pagare) != null) {
				throw new HealthmanagerException(
						"Detalle_pagare ya se encuentra en la base de datos");
			}
			detalle_pagareDao.crear(detalle_pagare);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Detalle_pagare detalle_pagare) {
		try {
			return detalle_pagareDao.actualizar(detalle_pagare);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Detalle_pagare consultar(Detalle_pagare detalle_pagare) {
		try {
			return detalle_pagareDao.consultar(detalle_pagare);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Detalle_pagare detalle_pagare) {
		try {
			return detalle_pagareDao.eliminar(detalle_pagare);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Detalle_pagare> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return detalle_pagareDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return detalle_pagareDao.existe(parameters);
	}

}