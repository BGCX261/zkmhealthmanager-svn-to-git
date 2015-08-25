/**
 * 
 */
package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * @author ferney
 *
 */

@Repository("reportContawebDao")
public interface ReportDao {
	
	List<Map> getReport(Map<String, Object> parameters,String namespace);
	
	List<Map> getReport(Map<String, Object> parameters,String namespace,String id_sql);
}
