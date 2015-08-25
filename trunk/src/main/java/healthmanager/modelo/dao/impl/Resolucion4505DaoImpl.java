/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package healthmanager.modelo.dao.impl;

import com.framework.state.impl.LineaBean;
import com.softcomputo.commons.exception.DataAccessRuntimeException;
import com.softcomputo.dao.impl.AbstractJdbcDao;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.dao.Resolucion4505Dao;
import healthmanager.modelo.service.Resolucion4505Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author JGONZA7
 */
public class Resolucion4505DaoImpl extends AbstractJdbcDao implements Resolucion4505Dao {

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private String getWhereSQL(Map<String, Object> map) {
        StringBuilder sqlWhere = new StringBuilder();
        sqlWhere.append("WHERE (EXISTS (SELECT 1 FROM public.admision AS ads WHERE ads.codigo_empresa = pac.codigo_empresa ");
        sqlWhere.append("AND ads.codigo_sucursal = pac.codigo_sucursal AND ads.nro_identificacion = pac.nro_identificacion ");
        sqlWhere.append("AND CAST(ads.fecha_ingreso AS DATE) BETWEEN '"
                + dateFormat.format(map.get("fecha_inicio")) + "' AND '"
                + dateFormat.format(map.get("fecha_final")) + "') OR ");
        sqlWhere.append("EXISTS(SELECT 1 FROM public.resultado_laboratorios AS rls WHERE rls.codigo_empresa = pac.codigo_empresa ");
        sqlWhere.append("AND rls.codigo_sucursal = pac.codigo_sucursal AND rls.nro_identificacion = pac.nro_identificacion ");
        sqlWhere.append("AND CAST(rls.fecha_resultado AS DATE) BETWEEN '"
                + dateFormat.format(map.get("fecha_inicio")) + "' AND '"
                + dateFormat.format(map.get("fecha_final")) + "')) ");
        sqlWhere.append("AND codigo_administradora = '"
                + map.get("codigo_administradora") + "'");
        sqlWhere.append("AND codigo_empresa = '" + map.get("codigo_empresa")
                + "'");
        sqlWhere.append("AND codigo_sucursal = '" + map.get("codigo_sucursal")
                + "'");
        return sqlWhere.toString();
    }

    private String getSQLCount(String where) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT count(*) FROM public.paciente AS pac ");
        builder.append(where);
        builder.append(";");
        return builder.toString();
    }

    private String getSQL(String where) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("pac.nro_identificacion AS id_paciente, ");
        sql.append("pac.tipo_identificacion AS variable_3, ");
        sql.append("pac.documento AS variable_4,  ");
        sql.append("pac.apellido1 AS variable_5, ");
        sql.append("CASE ");
        sql.append("WHEN ( ");
        sql.append("(pac.apellido2)::text = ''::text ");
        sql.append(") THEN 'NONE'::character varying ");
        sql.append("ELSE pac.apellido2 ");
        sql.append("END AS variable_6, ");
        sql.append("pac.nombre1 AS variable_7, ");
        sql.append("CASE ");
        sql.append("WHEN ( ");
        sql.append("(pac.nombre2)::text = ''::text ");
        sql.append(") THEN 'NONE'::character varying ");
        sql.append("ELSE pac.nombre2 ");
        sql.append("END AS variable_8, ");
        sql.append("to_char(pac.fecha_nacimiento, 'yyyy-MM-dd'::text) AS variable_9, ");
        sql.append("pac.sexo AS variable_10, ");
        sql.append("CASE ");
        sql.append("WHEN ( ");
        sql.append("(pac.pertenencia_etnica)::text = ''::text ");
        sql.append(") THEN '6'::character varying ");
        sql.append("ELSE pac.pertenencia_etnica ");
        sql.append("END AS variable_11, ");
        sql.append("CASE ");
        sql.append("WHEN ( ");
        sql.append("(pac.codigo_ocupacion)::text <> ''::text ");
        sql.append(") THEN pac.codigo_ocupacion ");
        sql.append("ELSE '9998'::character varying ");
        sql.append("END AS variable_12, ");
        sql.append("CASE ");
        sql.append("WHEN ( ");
        sql.append("(pac.codigo_educativo)::text <> ''::text ");
        sql.append(") THEN pac.codigo_educativo ");
        sql.append("ELSE '13'::character varying ");
        sql.append("END AS variable_13, ");
        sql.append("pac.codigo_empresa, ");
        sql.append("pac.codigo_sucursal, ");
        sql.append("pac.codigo_administradora ");
        sql.append("FROM public.paciente AS pac ");
        sql.append(where);
        sql.append(";");
        return sql.toString();
    }

    @Override
    public void listar(Map<String, Object> map, Resolucion4505Service.OnResultSet onResultSet) {
        PreparedStatement pstm = null;
        PreparedStatement pstm2 = null;
        ResultSet rs = null;
        Connection connection = null;
        try {
            String sql_where = getWhereSQL(map);
            long total = 0;
            connection = getConnection();
            pstm = connection.prepareStatement(getSQLCount(sql_where));
            // consultamos la cantidad
            rs = pstm.executeQuery();
            if (rs.next()) {
                total = rs.getLong(1);
            }
            onResultSet.onCalcularTotalVerificar(total);
            // montamos el total
            pstm2 = connection.prepareStatement(getSQL(sql_where));
            rs = pstm2.executeQuery();
            long contador = 0;
            List<LineaBean> lineaBeans = new ArrayList<LineaBean>();
            while (rs.next()) {
                lineaBeans.add(onResultSet.onNuevo(rs, ++contador, total));
            }
            onResultSet.onComplementar(lineaBeans);
            onResultSet.onFinalizar();
        } catch (Exception e) {
            throw new DataAccessRuntimeException(e.getMessage());
        } finally {
            this.releaseResources(connection, pstm, pstm2, rs);
        }
    }

    @Override
    public String consultar(String sql) {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        try {
            connection = getConnection();
            pstm = connection.prepareStatement(sql);
            // consultamos la cantidad
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            throw new HealthmanagerException(e.getMessage());
        } finally {
            this.closeResources(pstm, rs, connection);
        }
        throw new HealthmanagerException("sin resultado");
    }

    @Override
    public boolean consultarBoolean(String sql) {
        Connection connection = null;
        ResultSet rs = null;
        PreparedStatement pstm = null;
        try {
            // consultamos la cantidad
            connection = getConnection();
            pstm = connection.prepareStatement(sql);
            // consultamos la cantidad
            rs = pstm.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new HealthmanagerException(e.getMessage());
        } finally {
            this.closeResources(pstm, rs, connection);
        }
        throw new HealthmanagerException("sin resultado");
    }

}
