package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Hisc_historial;
import healthmanager.modelo.dao.Hisc_historialDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("hisc_historialService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Hisc_historialService implements Serializable{

	@Autowired
	private Hisc_historialDao hisc_historialDao;

	public Hisc_historial consultar_historial(Map<String, Object> parametros) {
		try {
			return hisc_historialDao.consultar_historial(parametros);
		} catch (Exception e) {
			throw new HealthmanagerException(e.getMessage(), e);
		}
	}

}
