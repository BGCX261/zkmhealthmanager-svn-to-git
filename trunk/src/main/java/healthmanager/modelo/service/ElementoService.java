/*
 * ElementoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.dao.ElementoDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("elementoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ElementoService implements Serializable{

	@Autowired
	private ElementoDao elementoDao;

	public void crear(Elemento elemento) {
		try {
			if (consultar(elemento) != null) {
				throw new ValidacionRunTimeException(
						"Elemento ya se encuentra en la base de datos");
			}
			elementoDao.crear(elemento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Elemento elemento) {
		try {
			return elementoDao.actualizar(elemento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Elemento consultar(Elemento elemento) {
		try {
			return elementoDao.consultar(elemento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Elemento elemento) {
		try {
			return elementoDao.eliminar(elemento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Elemento> listar(Map<String, Object> parameter) {
		try {
			return elementoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Elemento> listar(String tipo) {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("tipo", tipo);
			parametros.put("orden_codigo", "orden_codigo");
			return elementoDao.listar(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Map<String, String> listarMapElementos(String tipo) {
		try {
			Map<String, String> mapa_datos = new HashMap<String, String>();
			List<Elemento> listado = listar(tipo);
			for (Elemento elemento : listado) {
				mapa_datos.put(elemento.getCodigo(), elemento.getDescripcion());
			}
			return mapa_datos;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		try {
			return elementoDao.existe(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
