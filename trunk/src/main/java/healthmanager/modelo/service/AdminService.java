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
import healthmanager.modelo.bean.Admin;
import healthmanager.modelo.dao.AdminDao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("adminService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class AdminService implements Serializable{

	private static Logger log = Logger.getLogger(AdminService.class);

	@Autowired
	private AdminDao adminDao;

	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;

	public void crear(Admin admin) {
		try {
			if (consultar(admin) != null) {
				throw new HealthmanagerException(
						"Admin ya se encuentra en la base de datos");
			}
			adminDao.crear(admin);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int actualizar(Admin admin) {
		try {
			return adminDao.actualizar(admin);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public Admin consultar(Admin admin) {
		try {
			return adminDao.consultar(admin);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public int eliminar(Admin admin) {
		try {
			return adminDao.eliminar(admin);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Admin> listar(Map<String, Object> parameter) {
		try {
			return adminDao.listar(parameter);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

	public List<Map<String, Object>> getInformacionConexiones(
			boolean incluir_postgres) throws SQLException {
		List<Map<String, Object>> listado_datos = new ArrayList<Map<String, Object>>();
		String sql = "SELECT count(*) as total," + "datname," + "state "
				+ "from pg_stat_activity group by datname,state "
				+ (!incluir_postgres ? " having datname != 'postgres'" : "")
				+ " order by datname; ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = getConexion().prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("total", rs.getLong("total"));
				map.put("datname", rs.getString("datname"));
				map.put("state", rs.getString("state"));
				listado_datos.add(map);
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
		return listado_datos;
	}

	public List<Map<String, Object>> getInformacionQuerys(
			boolean incluir_postgres) throws SQLException {
		List<Map<String, Object>> listado_datos = new ArrayList<Map<String, Object>>();
		String sql = "SELECT pid, state, query " + "from pg_stat_activity "
				+ (!incluir_postgres ? " where datname != 'postgres'" : "")
				+ " order by pid,state; ";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = getConexion().prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				String query = rs.getString("query");
				if (!query.toLowerCase().trim().contains("select 1")
						&& !query.toLowerCase().trim()
								.contains("select current_timestamp")) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("pid", rs.getString("pid"));
					map.put("state", rs.getString("state"));
					map.put("query", rs.getString("query"));
					listado_datos.add(map);
				}
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
		return listado_datos;
	}

	public List<Map<String, Object>> getInformacionRegistros()
			throws SQLException {
		List<Map<String, Object>> listado_datos = new ArrayList<Map<String, Object>>();
		String sql = "SELECT schemaname,relname " + "from pg_stat_user_tables "
				+ " order by schemaname,relname;";
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			pstm = getConexion().prepareStatement(sql);
			rs = pstm.executeQuery();
			while (rs.next()) {
				String schemaname = rs.getString("schemaname");
				String relname = rs.getString("relname");
				String sql2 = "select count(1) as total from " + schemaname
						+ "." + relname;
				PreparedStatement pstm2 = getConexion().prepareStatement(sql2);
				ResultSet rs2 = pstm2.executeQuery();
				if (rs2.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("schemaname", schemaname);
					map.put("relname", relname);
					map.put("total", rs2.getLong("total"));
					listado_datos.add(map);
				}
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
		return listado_datos;
	}

	public Connection getConexion() {
		return sqlSession.getConnection();
	}

	public void setLimit(String limit) {
		this.adminDao.setLimit(limit);
	}

	public boolean existe(Map<String, Object> parameters) {
		return this.adminDao.existe(parameters);
	}

}