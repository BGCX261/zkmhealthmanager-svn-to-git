/*
 * PaisServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos softcomputo
 */ 

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Pais;
import healthmanager.modelo.dao.PaisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("paisService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class PaisService implements Serializable{

	@Autowired private PaisDao paisDao;
	private String limit;

	public void crear(Pais pais){ 
		try{
			Pais pais_temp = consultar(pais);
			if (pais_temp != null) {
				throw new ValidacionRunTimeException("El pais con el código "
						+ pais.getCodigo_pais() + " " + pais_temp.getNombre()
						+ " ya se encuentra en la base de datos");
			}
			paisDao.crear(pais);
		}catch(ValidacionRunTimeException e){
			throw new ValidacionRunTimeException(e.getMessage(),e);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Pais pais){ 
		try{
			return paisDao.actualizar(pais);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Pais consultar(Pais pais){ 
		try{
			return paisDao.consultar(pais);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}
	
	public Pais consultarID(Pais pais){
		try{
			return paisDao.consultarID(pais);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Pais pais){ 
		try{
			return paisDao.eliminar(pais);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Pais> listar(Map<String,Object> parameters){ 
		try{
			parameters.put("limit",limit);
			return paisDao.listar(parameters);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

	public boolean exist(Map<String,Object> parameters){ 
		return paisDao.exist(parameters);
	}

}