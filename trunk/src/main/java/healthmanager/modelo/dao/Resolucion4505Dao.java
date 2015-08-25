/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package healthmanager.modelo.dao;

import healthmanager.modelo.service.Resolucion4505Service;
import java.util.Map;

/**
 *
 * @author JGONZA7
 */
public interface Resolucion4505Dao {
    void listar(Map<String, Object> map, Resolucion4505Service.OnResultSet onResultSet);
    String consultar(String sql);
    boolean consultarBoolean(String sql);
    
}
