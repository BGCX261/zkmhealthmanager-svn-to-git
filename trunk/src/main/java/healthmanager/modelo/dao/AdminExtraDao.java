/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

/**
 *
 * @author JGONZA7
 */
public interface AdminExtraDao {
    List<Map<String, Object>> getInformacionConexiones(boolean incluir_postgres);
    List<Map<String, Object>> getInformacionQuerys(boolean incluir_postgres);
    List<Map<String, Object>> getInformacionRegistros();  
}
