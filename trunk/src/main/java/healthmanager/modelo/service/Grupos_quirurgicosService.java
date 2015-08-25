/*
 * Grupos_quirurgicosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Grupos_iss01;
import healthmanager.modelo.bean.Grupos_quirurgicos;
import healthmanager.modelo.dao.Grupos_iss01Dao;
import healthmanager.modelo.dao.Grupos_quirurgicosDao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConstantes;

@Service("grupos_quirurgicosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Grupos_quirurgicosService implements Serializable{

	private String limit;

	@Autowired
	private Grupos_quirurgicosDao grupos_quirurgicosDao;
	@Autowired
	private Grupos_iss01Dao grupos_iss01Dao;

	public void crear(Grupos_quirurgicos grupos_quirurgicos) {
		try {
			if (consultar(grupos_quirurgicos) != null) {
				throw new HealthmanagerException(
						"Grupos_quirurgicos ya se encuentra en la base de datos");
			}
			grupos_quirurgicosDao.crear(grupos_quirurgicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Grupos_quirurgicos grupos_quirurgicos) {
		try {
			return grupos_quirurgicosDao.actualizar(grupos_quirurgicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Grupos_quirurgicos consultar(Grupos_quirurgicos grupos_quirurgicos) {
		try {
			return grupos_quirurgicosDao.consultar(grupos_quirurgicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Grupos_quirurgicos grupos_quirurgicos) {
		try {
			return grupos_quirurgicosDao.eliminar(grupos_quirurgicos);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Grupos_quirurgicos> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return grupos_quirurgicosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.grupos_quirurgicosDao.existe(parameters);
	}

	// Metodo para obtener los datos del grupo quirurgico esto para cuando se
	// llame del servicio //
	public Map getNomGrupoCirugia(String manual, String grupo,
			String tipo_grupo_soat, String tipo_grupo_iss, Map param) {

		Map<String, Object> mapBean = new HashMap<String, Object>();
		String nom_pcd = "";
		if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
			Grupos_quirurgicos grupos = new Grupos_quirurgicos();
			grupos.setCodigo_grupo(grupo);
			grupos.setTipo_grupo(tipo_grupo_soat);
			grupos = grupos_quirurgicosDao.consultar(grupos);
			nom_pcd = (grupos != null ? grupos.getCodigo_pcd() + "-"
					+ grupos.getNombre_pcd() : "");
		} else if (manual.equals(IConstantes.TIPO_MANUAL_ISS01)
				|| manual.equals(IConstantes.TIPO_MANUAL_ISS04)) {
			Grupos_iss01 grupos = new Grupos_iss01();
			grupos.setCodigo(tipo_grupo_iss);
			grupos = grupos_iss01Dao.consultar(grupos);
			nom_pcd = (grupos != null ? grupos.getCodigo() + "-"
					+ grupos.getDescripcion() : "");
		}

		mapBean.put("nro_factura", (String) param.get("nro_factura"));
		mapBean.put("nro_identificacion",
				(String) param.get("nro_identificacion"));
		mapBean.put("nombre1", (String) param.get("nombre1"));
		mapBean.put("nombre2", (String) param.get("nombre2"));
		mapBean.put("apellido1", (String) param.get("apellido1"));
		mapBean.put("apellido2", (String) param.get("apellido2"));
		mapBean.put("codigo_procedimiento", "");
		mapBean.put("procedimiento", nom_pcd);
		mapBean.put("unidades", new Integer(0));
		mapBean.put("valor_neto", new BigDecimal(0));

		return mapBean;

	}

}
