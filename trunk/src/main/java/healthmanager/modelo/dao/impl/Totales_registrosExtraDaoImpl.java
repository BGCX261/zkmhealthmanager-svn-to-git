/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package healthmanager.modelo.dao.impl;

import com.softcomputo.commons.exception.DataAccessRuntimeException;
import com.softcomputo.dao.impl.AbstractJdbcDao;
import healthmanager.modelo.dao.Totales_registrosExtraDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author JGONZA7
 */
public class Totales_registrosExtraDaoImpl extends AbstractJdbcDao
        implements Totales_registrosExtraDao {

    @Override
    public Long getTotalRegistros(String esquema, String objeto) {
        String sql = "SELECT count(1) as total from " + esquema + "." + objeto;
        Connection connection = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        long total = 0L;
        try {
            connection = getConnection();
            pstm = connection.prepareStatement(sql);
            rs = pstm.executeQuery();
            if (rs.next()) {
                total = rs.getLong("total");
            }
        } catch (Exception e) {
            throw new DataAccessRuntimeException(e.getMessage(), e);
        } finally {
            this.closeResources(pstm, rs, connection);
        }
        return total;
    }
    
}
