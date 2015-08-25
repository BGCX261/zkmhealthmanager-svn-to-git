/**
 * 
 */
package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * @author ferney
 *
 */
@Service("reportContawebService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ReportService implements Serializable{
	
	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;


	public List<Map> getReport(Map<String, Object> parameters, String namespace) {
		try {
			return sqlSession.selectList("contaweb.modelo.dao.report."+namespace+".select", parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}finally{
//			sqlSession2.close();
		}
	}


	public List<Map> getReport(Map<String, Object> parameters, String namespace, String id_sql) {
		try {
			return sqlSession.selectList("contaweb.modelo.dao.report."+namespace+"."+id_sql, parameters);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}finally{
//			sqlSession2.close();
		}
	}

}
