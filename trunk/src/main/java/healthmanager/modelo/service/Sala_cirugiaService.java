/*
 * Sala_cirugiaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo Luis Miguel Hernandez 
 */ 

package healthmanager.modelo.service;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Sala_cirugia;
import healthmanager.modelo.dao.Sala_cirugiaDao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("sala_cirugiaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Sala_cirugiaService implements Serializable{

	private String limit;

	@Autowired private Sala_cirugiaDao sala_cirugiaDao;

	public void crear(Sala_cirugia sala_cirugia){ 
		try{
			if(consultar(sala_cirugia)!=null){
				throw new HealthmanagerException("Sala_cirugia ya se encuentra en la base de datos");
			}
			sala_cirugiaDao.crear(sala_cirugia);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Sala_cirugia sala_cirugia){ 
		try{
			return sala_cirugiaDao.actualizar(sala_cirugia);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Sala_cirugia consultar(Sala_cirugia sala_cirugia){ 
		try{
			return sala_cirugiaDao.consultar(sala_cirugia);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Sala_cirugia sala_cirugia){ 
		try{
			return sala_cirugiaDao.eliminar(sala_cirugia);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Sala_cirugia> listar(Map<String, Object> parameter){ 
		try{
			parameter.put("limit", limit);
			return sala_cirugiaDao.listar(parameter);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

	public boolean existe(Map<String, Object> parameters){ 
		return this.sala_cirugiaDao.existe(parameters);
	}

}
