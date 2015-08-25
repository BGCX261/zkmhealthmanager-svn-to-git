/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthmanager.modelo.dao.impl;

import com.softcomputo.commons.exception.DataAccessRuntimeException;
import com.softcomputo.dao.impl.AbstractJdbcDao;
import healthmanager.modelo.dao.AdminExtraDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author JGONZA7
 */
public class AdminExtraDaoImpl extends AbstractJdbcDao implements AdminExtraDao {

    @Override
    public List<Map<String, Object>> getInformacionConexiones(boolean incluir_postgres) {
        List<Map<String, Object>> listado_datos = new LinkedList<Map<String, Object>>();
        String sql = "SELECT count(*) as total," + "datname," + "state "
                + "from pg_stat_activity group by datname,state "
                + (!incluir_postgres ? " having datname != 'postgres'" : "")
                + " order by datname; ";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = this.getConnection();
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("total", rs.getLong("total"));
                map.put("datname", rs.getString("datname"));
                map.put("state", rs.getString("state"));
                listado_datos.add(map);
            }
        } catch (Exception e) {
            throw new DataAccessRuntimeException(e.getMessage(), e);
        } finally {
            this.closeResources(pstm, rs, connection);
        }
        return listado_datos;
    }

    @Override
    public List<Map<String, Object>> getInformacionQuerys(boolean incluir_postgres) {
        List<Map<String, Object>> listado_datos = new ArrayList<Map<String, Object>>();
        String sql = "SELECT pid, state, query " + "from pg_stat_activity "
                + (!incluir_postgres ? " where datname != 'postgres'" : "")
                + " order by pid,state; ";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = this.getConnection();
            pstm = connection.prepareStatement(sql);
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
            throw new DataAccessRuntimeException(e.getMessage(), e);
        } finally {
            this.closeResources(pstm, rs, connection);
        }
        return listado_datos;
    }

    @Override
    public List<Map<String, Object>> getInformacionRegistros() {
        List<Map<String, Object>> listado_datos = new ArrayList<Map<String, Object>>();
        String sql = "SELECT schemaname,relname " + "from pg_stat_user_tables "
                + " order by schemaname,relname;";
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            connection = this.getConnection();
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            while (rs.next()) {
                String schemaname = rs.getString("schemaname");
                String relname = rs.getString("relname");
                String sql2 = "select count(1) as total from " + schemaname
                        + "." + relname;
                PreparedStatement pstm2 = connection.prepareStatement(sql2);
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
            throw new DataAccessRuntimeException(e.getMessage(), e);
        } finally {
            this.closeResources(pstm, rs, connection);
        }
        return listado_datos;
    }

}
