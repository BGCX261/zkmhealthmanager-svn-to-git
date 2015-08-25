/*
 * AdminServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Totales_registros;
import healthmanager.modelo.dao.Totales_registrosDao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("totales_registrosService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Totales_registrosService implements Serializable{

	private static Logger log = Logger.getLogger(Totales_registrosService.class);

	
	@Autowired
	private Totales_registrosDao totales_registrosDao;
	
	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;

	public void crear(Totales_registros totales_registros) {
		try {
			totales_registrosDao.crear(totales_registros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Totales_registros totales_registros) {
		try {
			return totales_registrosDao.actualizar(totales_registros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Totales_registros consultar(Totales_registros totales_registros) {
		try {
			return totales_registrosDao.consultar(totales_registros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Totales_registros consultar_informacion(
			Totales_registros totales_registros) {
		try {
			return totales_registrosDao
					.consultar_informacion(totales_registros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Totales_registros totales_registros) {
		try {
			return totales_registrosDao.eliminar(totales_registros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Totales_registros> listar(Map<String, Object> parameter) {
		try {
			return totales_registrosDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<String> listar_tablas(Map<String, Object> parametros) {
		try {
			return totales_registrosDao.listar_tablas(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<String> listar_equemas(Map<String, Object> parametros) {
		try {
			return totales_registrosDao.listar_esquemas(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}
	
	public Long getTotalRegistros(String esquema, String objeto) throws SQLException {
		String sql = "SELECT count(1) as total from "+esquema+"."+objeto;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		long total = 0L;
		try {
			pstm = getConexion().prepareStatement(sql);
			rs = pstm.executeQuery();
			if (rs.next()) {
				total = rs.getLong("total");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (pstm != null) {
				pstm.close();
			}
			// sqlSession.close();
		}
		return total;
	}
	
	public Connection getConexion() {
		return sqlSession.getConnection();
	}

}