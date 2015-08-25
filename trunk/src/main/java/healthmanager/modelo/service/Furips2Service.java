/*
 * Furips2ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Furips2;
import healthmanager.modelo.dao.Furips2Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("furips2Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Furips2Service implements Serializable{

	@Autowired
	private Furips2Dao furips2Dao;
	@Autowired
	private ConsecutivoService consecutivoService;
	private String limit;

	public Furips2 crear(Furips2 furips2) {
		try {
			String codigo_furips = consecutivoService.crearConsecutivo(
					furips2.getCodigo_empresa(), furips2.getCodigo_sucursal(),
					"FURIPS2");
			codigo_furips = consecutivoService.getZeroFill(codigo_furips, 10);
			furips2.setCodigo_currips(codigo_furips);
			if (consultar(furips2) != null) {
				throw new ValidacionRunTimeException(
						"Furips2 ya se encuentra en la base de datos");
			}
			furips2Dao.crear(furips2);
			consecutivoService.actualizarConsecutivo(
					furips2.getCodigo_empresa(), furips2.getCodigo_sucursal(),
					"FURIPS2", furips2.getCodigo_currips());
			return furips2;
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Furips2 furips2) {
		try {
			return furips2Dao.actualizar(furips2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Furips2 consultar(Furips2 furips2) {
		try {
			return furips2Dao.consultar(furips2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Furips2 furips2) {
		try {
			return furips2Dao.eliminar(furips2);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Furips2> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return furips2Dao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return furips2Dao.existe(parameters);
	}

	public Furips2 consultarPorParametros(Furips2 furips2) {
		return furips2Dao.consultarPorParametros(furips2);
	}

}