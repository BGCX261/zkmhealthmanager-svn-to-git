/*
 * EcografiaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Ecografia;
import healthmanager.modelo.bean.Historia_clinica;
import healthmanager.modelo.dao.AdmisionDao;
import healthmanager.modelo.dao.EcografiaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("ecografiaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class EcografiaService implements Serializable{

	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired
	private EcografiaDao ecografiaDao;
	private String limit;

	@Autowired
	private AdmisionDao admisionDao;

	@Autowired
	private GeneralExtraService generalExtraService;

	public void crear(Ecografia ecografia, Admision admision) {
		Historia_clinica historia_clinica = new Historia_clinica();
		historia_clinica.setCodigo_empresa(ecografia.getCodigo_empresa());
		historia_clinica.setCodigo_sucursal(ecografia.getCodigo_sucursal());
		historia_clinica.setFecha_historia(ecografia.getFecha_inicial());
		historia_clinica.setVia_ingreso(admision.getVia_ingreso());
		historia_clinica.setNro_ingreso(admision.getNro_ingreso());
		historia_clinica
				.setNro_identificacion(admision.getNro_identificacion());
		historia_clinica.setPrestador(ecografia.getCodigo_ginecolo());
		try {
			if (consultar(ecografia) != null) {
				throw new ValidacionRunTimeException(
						"Ecografia ya se encuentra en la base de datos");
			}
			generalExtraService.crear(historia_clinica);
			ecografia.setCodigo_historia(historia_clinica.getCodigo_historia());
			ecografiaDao.crear(ecografia);

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Ecografia ecografia) {
		try {
			return ecografiaDao.actualizar(ecografia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Ecografia consultar(Ecografia ecografia) {
		try {
			return ecografiaDao.consultar(ecografia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Ecografia ecografia) {
		try {
			return ecografiaDao.eliminar(ecografia);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Ecografia> listar(Map<String, Object> parameters) {
		try {
			parameters.put("limit", limit);
			return ecografiaDao.listar(parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return ecografiaDao.existe(parameters);
	}

	public void guardar(Map<String, Object> params) {
		Ecografia ecografia = (Ecografia) params.get("ecografia");
		String accion = (String) params.get("accion");
		Admision admision = (Admision) params.get("admision");
		if (accion.equals("registrar")) {
			crear(ecografia, admision);
			admision.setCodigo_medico(ecografia.getCodigo_ginecolo());
			admision.setAtendida(true);
			admisionDao.actualizar(admision);
		} else {
			actualizar(ecografia);
		}
	}

	public Ecografia consultarPorFiltros(Ecografia ecografia) {
		return ecografiaDao.consultarPorFiltros(ecografia);
	}

}