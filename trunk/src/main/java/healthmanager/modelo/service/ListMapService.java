package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.modelo.dao.ListMapDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import com.framework.res.MapToList;

@Service("listMapService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class ListMapService implements Serializable{

	@Autowired
	private ListMapDao listMapDao;

	public List getLisMaps(MapToList mapToList, Map params) {
		return listMapDao.getLisMaps(mapToList, params);
	}

}
