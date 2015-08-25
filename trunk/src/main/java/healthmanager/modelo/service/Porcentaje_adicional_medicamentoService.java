/*
 * Porcentaje_adicional_medicamentoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Row;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Porcentaje_adicional_medicamento;
import healthmanager.modelo.dao.Porcentaje_adicional_medicamentoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("porcentaje_adicional_medicamentoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Porcentaje_adicional_medicamentoService implements Serializable{

	private String limit;

	@Autowired
	private Porcentaje_adicional_medicamentoDao porcentaje_adicional_medicamentoDao;

	public void crear(
			Porcentaje_adicional_medicamento porcentaje_adicional_medicamento) {
		try {
			if (consultar(porcentaje_adicional_medicamento) != null) {
				throw new HealthmanagerException(
						"Porcentaje_adicional_medicamento ya se encuentra en la base de datos");
			}
			porcentaje_adicional_medicamentoDao
					.crear(porcentaje_adicional_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(
			Porcentaje_adicional_medicamento porcentaje_adicional_medicamento) {
		try {
			return porcentaje_adicional_medicamentoDao
					.actualizar(porcentaje_adicional_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Porcentaje_adicional_medicamento consultar(
			Porcentaje_adicional_medicamento porcentaje_adicional_medicamento) {
		try {
			return porcentaje_adicional_medicamentoDao
					.consultar(porcentaje_adicional_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(
			Porcentaje_adicional_medicamento porcentaje_adicional_medicamento) {
		try {
			return porcentaje_adicional_medicamentoDao
					.eliminar(porcentaje_adicional_medicamento);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Porcentaje_adicional_medicamento> listar(
			Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return porcentaje_adicional_medicamentoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.porcentaje_adicional_medicamentoDao.existe(parameters);
	}

	public void guardar(Map<String, Object> map) {
		List<Component> components = (List<Component>) map.get("list");
		List<Porcentaje_adicional_medicamento> adicional_medicamentos = (List<Porcentaje_adicional_medicamento>) map
				.get("listDel");

		/* eliminamos */
		for (Porcentaje_adicional_medicamento porcentaje_adicional_medicamento : adicional_medicamentos) {
			porcentaje_adicional_medicamentoDao
					.eliminar(porcentaje_adicional_medicamento);
		}

		/* fin eliminar */
		for (Component component : components) {
			if (component instanceof Row) {
				Row row = (Row) component;
				Porcentaje_adicional_medicamento adicional_medicamento = row
						.getValue();
				if (porcentaje_adicional_medicamentoDao
						.consultar(adicional_medicamento) != null) {
					porcentaje_adicional_medicamentoDao
							.actualizar(adicional_medicamento);
				} else {
					porcentaje_adicional_medicamentoDao
							.crear(adicional_medicamento);
				}
			}
		}
	}

}
