/*
 * Anexo1ServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Anexo1;
import healthmanager.modelo.dao.Anexo1Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("anexo1Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Anexo1Service implements Serializable{

	@Autowired
	private Anexo1Dao anexo1Dao;
	@Autowired
	private ConsecutivoService consecutivoService;

	private String limit;

	public Anexo1 crear(Anexo1 anexo1) {
		try {
			if (consultar(anexo1) != null) {
				throw new HealthmanagerException(
						"El anexo ya se encuentra en la base de datos");
			}

			String tipoConsecutivo = IConstantes.CONS_ANEXO_1;
			String consecutivo = consecutivoService.crearConsecutivo(
					anexo1.getCodigo_empresa(), anexo1.getCodigo_sucursal(),
					tipoConsecutivo);
			String codigo = consecutivoService.getZeroFill(consecutivo, 10);
			anexo1.setCodigo(codigo);

			anexo1Dao.crear(anexo1);
			consecutivoService.actualizarConsecutivo(
					anexo1.getCodigo_empresa(), anexo1.getCodigo_sucursal(),
					tipoConsecutivo, codigo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
		return anexo1;
	}

	public int actualizar(Anexo1 anexo1) {
		try {
			return anexo1Dao.actualizar(anexo1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Anexo1 consultar(Anexo1 anexo1) {
		try {
			return anexo1Dao.consultar(anexo1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Anexo1 anexo1) {
		try {
			return anexo1Dao.eliminar(anexo1);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Anexo1> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return anexo1Dao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return anexo1Dao.existe(parameters);
	}

}