/*
 * Servicios_contratadosServiceImpl.java
 * 
 * Generado Automaticamente por la herramienta Zkcrud4WEB.
 * Desarrollada por Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Servicios_contratados;
import healthmanager.modelo.dao.Servicios_contratadosDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("servicios_contratadosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Servicios_contratadosService implements Serializable{

	@Autowired
	private Servicios_contratadosDao servicios_contratadosDao;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			String accion = (String) mapa_datos.get("accion");
			Servicios_contratados servicios_contratados = (Servicios_contratados) mapa_datos
					.get("servicios_contratados");
			if (accion.equalsIgnoreCase("registrar")) {
				servicios_contratadosDao.crear(servicios_contratados);
			} else {
				servicios_contratadosDao.actualizar(servicios_contratados);
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Servicios_contratados servicios_contratados) {
		try {
			if (consultar(servicios_contratados) != null) {
				throw new HealthmanagerException(
						"Servicios_contratados ya se encuentra en la base de datos");
			}
			servicios_contratadosDao.crear(servicios_contratados);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Servicios_contratados servicios_contratados) {
		try {
			return servicios_contratadosDao.actualizar(servicios_contratados);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Servicios_contratados consultar(
			Servicios_contratados servicios_contratados) {
		try {
			return servicios_contratadosDao.consultar(servicios_contratados);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Servicios_contratados servicios_contratados) {
		try {
			return servicios_contratadosDao.eliminar(servicios_contratados);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Servicios_contratados> listar(Map<String, Object> parameters) {
		try {
			return servicios_contratadosDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Servicios_contratados> listarServiciosPaciente(
			Map<String, Object> parametros) {
		try {
			return servicios_contratadosDao.listarServiciosPaciente(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	/**
	 * Este metodo me permite saber cuales son los servicios que tiene
	 * disponible ese contrato Ejemplo: Consultas, procedimientos, etc.
	 * */
	public List<Servicios_contratados> listarServiciosPermitidos(
			Map<String, Object> parametros) {
		try {
			return servicios_contratadosDao
					.listarServiciosPermitidos(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Servicios_contratados> listarServiciosParticular(
			Map<String, Object> parametros) {
		try {
			return servicios_contratadosDao
					.listarServiciosParticular(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean getTieneContratoServicio(Map<String, Object> parametros) {
		try {
			return servicios_contratadosDao
					.getTieneContratoServicio(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean getTieneContratoServicioParticular(
			Map<String, Object> parametros) {
		try {
			return servicios_contratadosDao
					.getTieneContratoServicioParticular(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> getServiciosContratadosRepetidos(
			Map<String, Object> parametros) {
		try {
			return servicios_contratadosDao
					.getServiciosContratadosRepetidos(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<String> listar_codigos(Map<String, Object> parametros) {
		try {
			return servicios_contratadosDao.listar_codigos(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public boolean existe(Map<String, Object> parameters) {
		return servicios_contratadosDao.existe(parameters);
	}

	public List<Map<String, Object>> getContratosValidos(
			Map<String, Object> params) {
		return servicios_contratadosDao.getContratosValidos(params);
	}
	
	public List<String> listarServiciosPermitidosPorConfiguracion(Map<String, Object> params){
		return servicios_contratadosDao.listarServiciosPermitidosPorConfiguracion(params);
	}
}