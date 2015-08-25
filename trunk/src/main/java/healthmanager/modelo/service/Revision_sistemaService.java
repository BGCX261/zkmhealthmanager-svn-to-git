/*
 * Revision_sistemaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Revision_sistema;
import healthmanager.modelo.dao.Revision_sistemaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("revision_sistemaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Revision_sistemaService implements Serializable{

	private String limit;

	@Autowired private Revision_sistemaDao revision_sistemaDao;

	public void crear(Revision_sistema revision_sistema){ 
		try{
			if(consultar(revision_sistema)!=null){
				throw new HealthmanagerException("Revision_sistema ya se encuentra en la base de datos");
			}
			revision_sistemaDao.crear(revision_sistema);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Revision_sistema revision_sistema){ 
		try{
			return revision_sistemaDao.actualizar(revision_sistema);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Revision_sistema consultar(Revision_sistema revision_sistema){ 
		try{
			return revision_sistemaDao.consultar(revision_sistema);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Revision_sistema revision_sistema){ 
		try{
			return revision_sistemaDao.eliminar(revision_sistema);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Revision_sistema> listar(Map<String, Object> parameter){ 
		try{
			parameter.put("limit", limit);
			return revision_sistemaDao.listar(parameter);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters){ 
		return this.revision_sistemaDao.existe(parameters);
	}

}
