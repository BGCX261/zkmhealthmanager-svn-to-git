/*
 * ConsecutivoServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */

package healthmanager.modelo.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Consecutivo;
import healthmanager.modelo.dao.ConsecutivoDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("consecutivoService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ConsecutivoService implements Serializable{

	private String limit;

	@Autowired
	private ConsecutivoDao consecutivoDao;

	public void guardarDatos(List<Consecutivo> lista_consecutivos) {
		try {
			for (Consecutivo consecutivo : lista_consecutivos) {
				if (consecutivoDao.consultar(consecutivo) == null) {
					consecutivoDao.crear(consecutivo);
				} else {
					consecutivoDao.actualizar(consecutivo);
				}
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void crear(Consecutivo consecutivo) {
		try {
			if (consultar(consecutivo) != null) {
				throw new HealthmanagerException(
						"Consecutivo ya se encuentra en la base de datos");
			}
			consecutivoDao.crear(consecutivo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Consecutivo consecutivo) {
		try {
			return consecutivoDao.actualizar(consecutivo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Consecutivo consultar(Consecutivo consecutivo) {
		try {
			return consecutivoDao.consultar(consecutivo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Consecutivo consecutivo) {
		try {
			return consecutivoDao.eliminar(consecutivo);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Consecutivo> listar(Map<String, Object> parameter) {
		try {
			parameter.put("limit", limit);
			return consecutivoDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String crearConsecutivo(String codigo_empresa,
			String codigo_sucursal, String tabla) {
		try {
			Consecutivo consecutivo = new Consecutivo();
			consecutivo.setCodigo_empresa(codigo_empresa);
			consecutivo.setCodigo_sucursal(codigo_sucursal);
			consecutivo.setTipo(tabla);

			Consecutivo consecutivo_temp = consecutivoDao
					.consultar(consecutivo);
			if (consecutivo_temp == null) {
				consecutivo.setConsecutivo("1");
				consecutivo.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				consecutivo.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				consecutivoDao.crear(consecutivo);
				return consecutivo.getConsecutivo();
			} else {
				return consecutivo_temp.getConsecutivo();
			}
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public void actualizarConsecutivo(String codigo_empresa,
			String codigo_sucursal, String tabla, String codigo) {
		
		try {
			Consecutivo consecutivo = new Consecutivo();
			consecutivo.setCodigo_empresa(codigo_empresa);
			consecutivo.setCodigo_sucursal(codigo_sucursal);
			consecutivo.setTipo(tabla);
			consecutivo = consecutivoDao.consultar(consecutivo);
			if (consecutivo != null) {
				consecutivo.setConsecutivo((Integer.parseInt(codigo) + 1) + "");
				consecutivoDao.actualizar(consecutivo);
			}

		} catch (Exception e) {
			
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public String getZeroFill(String valor, int zeroFill) {
		
		try {
			String st_zeroFill = "000000000000000000000000000000";
			if (valor.length() > zeroFill) {
				return valor;
			} else {
				return st_zeroFill.substring(0, zeroFill - valor.length())
						+ valor;
			}
		} catch (Exception e) {
			
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
