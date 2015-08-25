/*
 * PabellonServiceImpl.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez 
 */ 

package healthmanager.modelo.service;

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

@Service("variables2193Service")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Variables2193Service implements Serializable{

	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;

	public List<Map<String,Object>> getVariable(String variable, Map parameter){ 
		try{
			return sqlSession.selectList("healthmanager.modelo.dao.variables2193Model.variable_"+variable, parameter);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}finally{
//			sqlSession.close();
		}
	}
//	public List<Map<String,Object>> getVariable(String variable, Map parameter){ 
//		try{
//			Method method = variablesDao.getClass().getMethod("variable_" + variable, Map.class);
//			method.setAccessible(true);
//			return (List<Map<String, Object>>) method.invoke(variablesDao, parameter);
//		}catch(Exception e){
//			throw new HealthmanagerException(e.getMessage(),e);
//		}
//	}
}