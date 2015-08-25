/*
 * Aportantes_maServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Aportantes_ma;
import healthmanager.modelo.bean.Aportes_cotizaciones;
import healthmanager.modelo.dao.Aportantes_maDao;
import healthmanager.modelo.dao.Aportes_cotizacionesDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.constantes.IConsecutivos;

@Service("aportantes_maService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Aportantes_maService implements Serializable{

	public static final String PARAM_ANIO_COPIA = "PAC";
	public static final String PARAM_ANIO = "PA";
	public static final String PARAM_MES_COPIA = "PMC";
	public static final String PARAM_MES = "PM";
	public static final String PARAM_APORTANTE = "PATE";
	public static final String PARAM_EMPRESA = "PE";
	public static final String PARAM_SUCURSAL = "PS";
	
	
	private String limit;

	@Autowired
	private Aportantes_maDao aportantes_maDao;
	@Autowired
	private ConsecutivoService consecutivoService;
	@Autowired private Aportes_cotizacionesDao aportes_cotizacionesDao;

	public void crear(Aportantes_ma aportantes_ma) {
		try {
			String codigo_aportante = consecutivoService.crearConsecutivo(
					aportantes_ma.getCodigo_empresa(),
					aportantes_ma.getCodigo_sucursal(),
					IConsecutivos.CONS_APORTANTE);
			aportantes_ma.setCodigo(codigo_aportante);
			if (consultar(aportantes_ma) != null) {
				throw new HealthmanagerException(
						"Aportantes_ma ya se encuentra en la base de datos");
			}
			aportantes_maDao.crear(aportantes_ma);
			consecutivoService.actualizarConsecutivo(
					aportantes_ma.getCodigo_empresa(),
					aportantes_ma.getCodigo_sucursal(),
					IConsecutivos.CONS_APORTANTE, codigo_aportante);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Aportantes_ma aportantes_ma) {
		try {
			return aportantes_maDao.actualizar(aportantes_ma);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Aportantes_ma consultar(Aportantes_ma aportantes_ma) {
		try {
			return aportantes_maDao.consultar(aportantes_ma);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Aportantes_ma aportantes_ma) {
		try {
			return aportantes_maDao.eliminar(aportantes_ma);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Aportantes_ma> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return aportantes_maDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public List<Map<String, Object>> listarMap(Map param) {
		param.put("limit", limit);
		return aportantes_maDao.listarMap(param);
	}

	public int guardarAportesOtroPeriodo(Map<String, Object> param) { 
		try {
			String anio_copia = (String) param.get(PARAM_ANIO_COPIA);
			String mes_copia = (String) param.get(PARAM_MES_COPIA);
			
			String anio = (String) param.get(PARAM_ANIO);
			String mes = (String) param.get(PARAM_MES);
			
			String codigo_aportante = (String) param.get(PARAM_APORTANTE);
			String codigo_empresa = (String) param.get(PARAM_EMPRESA);
			String codigo_sucursal = (String) param.get(PARAM_SUCURSAL);
			
			
			if (anio_copia.equalsIgnoreCase(anio)
					&& mes_copia.equalsIgnoreCase(mes)) {
				throw new ValidacionRunTimeException("No puede hacer una copia del mismo periodo para los aportes"); 
			}else{
				 Map<String, Object> parametro_consulta = new HashMap<String, Object>();
				 parametro_consulta.put("codigo_empresa", codigo_empresa);
				 parametro_consulta.put("codigo_sucursal", codigo_sucursal);
				 parametro_consulta.put("codigo_aportadores", codigo_aportante);
				 
				 // consultamos los aportes 
				 parametro_consulta.put("anio", anio_copia);
				 parametro_consulta.put("mes", mes_copia);
				 List<Aportes_cotizaciones> listado_aportes_cotizaciones = aportes_cotizacionesDao
						.listar(parametro_consulta);
				 
				 if(listado_aportes_cotizaciones.isEmpty()){
					 throw new ValidacionRunTimeException("El aportante no tiene aportes disponibles para ese periodo"); 
				 }
				 
				 parametro_consulta.put("anio", anio);
				 parametro_consulta.put("mes", mes);
				 
				 // eliminamos los aportes del mes a realizar la copia
				 aportes_cotizacionesDao.eliminarParametrizado(parametro_consulta);
				 
				 for (Aportes_cotizaciones aportes_cotizaciones : listado_aportes_cotizaciones) {
				    aportes_cotizaciones.setAnio(anio);
				    aportes_cotizaciones.setMes(mes);
				    aportes_cotizacionesDao.crear(aportes_cotizaciones); 
				 }
				 
				 return listado_aportes_cotizaciones.size();
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
