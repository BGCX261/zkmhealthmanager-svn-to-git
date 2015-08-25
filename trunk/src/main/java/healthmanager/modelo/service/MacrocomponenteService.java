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

@Service("macrocomponenteService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class MacrocomponenteService implements Serializable{

	@Autowired
	@Qualifier("sqlSession")
	private SqlSessionTemplate sqlSession;

	public List<Object> listar(Paquetes paquetes, String statement,
			Map<String, Object> parametros) {
		try {
			if (paquetes == Paquetes.HEALTHMANAGER) {
				return sqlSession.selectList("healthmanager.modelo.dao."
						+ statement, parametros);
			} else {
				return sqlSession.selectList(
						"contaweb.modelo.dao." + statement, parametros);
			}

		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		} finally {
			// sqlSession.close();
			// sqlSession.close();
		}
	}

	public enum Paquetes {
		HEALTHMANAGER, CONTAWEB;
	}

}
