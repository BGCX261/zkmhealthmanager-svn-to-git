/*
 * ProgramasServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Programas;
import healthmanager.modelo.bean.Programas_actividades_pyp;
import healthmanager.modelo.dao.ProgramasDao;
import healthmanager.modelo.dao.Programas_actividades_pypDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("programasService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ProgramasService implements Serializable{

	@Autowired
	private ProgramasDao programasDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private Programas_actividades_pypDao programas_actividades_pypDao;
	private String limit;

	public void guardarDatos(Map<String, Object> mapa_datos) {
		try {
			String accion = (String) mapa_datos.get("accion");
			Programas programas = (Programas) mapa_datos.get("programas");
			Map<String, String> mapa_seleccionados = (Map<String, String>) mapa_datos
					.get("mapa_seleccionados");

			if (accion.equalsIgnoreCase("registrar")) {
				String tipoConsecutivo = IConstantes.CONS_PROGRAMAS;
				String consecutivo = consecutivoService.crearConsecutivo(
						programas.getCodigo_empresa(),
						programas.getCodigo_sucursal(), tipoConsecutivo);
				String codigo_programa = consecutivoService.getZeroFill(
						consecutivo, 10);

				programas.setCodigo_programa(codigo_programa);

				if (consultar(programas) != null) {
					throw new HealthmanagerException(
							"El programa ya se encuentra en la base de datos");
				}

				programasDao.crear(programas);

			} else {
				programasDao.actualizar(programas);
			}

			Programas_actividades_pyp programas_actividades_pyp = new Programas_actividades_pyp();
			programas_actividades_pyp.setCodigo_empresa(programas
					.getCodigo_empresa());
			programas_actividades_pyp.setCodigo_sucursal(programas
					.getCodigo_sucursal());
			programas_actividades_pyp.setCodigo_programa(programas
					.getCodigo_programa());

			programas_actividades_pypDao.eliminar(programas_actividades_pyp);

			for (String key_cups : mapa_seleccionados.keySet()) {
				programas_actividades_pyp.setCodigo_cups(key_cups);
				programas_actividades_pypDao.crear(programas_actividades_pyp);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Programas programas) {
		try {
			if (consultar(programas) != null) {
				throw new HealthmanagerException(
						"Programas ya se encuentra en la base de datos");
			}
			programasDao.crear(programas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Programas programas) {
		try {
			return programasDao.actualizar(programas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Programas consultar(Programas programas) {
		try {
			return programasDao.consultar(programas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Programas programas) {
		try {
			return programasDao.eliminar(programas);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Programas> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return programasDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return programasDao.existe(parameters);
	}

}