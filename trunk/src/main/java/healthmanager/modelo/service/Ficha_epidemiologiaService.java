/*
 * Ficha_epidemiologiaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.dao.Ficha_epidemiologiaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("ficha_epidemiologiaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Ficha_epidemiologiaService implements Serializable{

	@Autowired private Ficha_epidemiologiaDao ficha_epidemiologiaDao;

	public void crear(Ficha_epidemiologia ficha_epidemiologia){ 
		try{
			if(consultar(ficha_epidemiologia)!=null){
				throw new HealthmanagerException("Ficha_epidemiologia ya se encuentra en la base de datos");
			}
			ficha_epidemiologiaDao.crear(ficha_epidemiologia);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Ficha_epidemiologia ficha_epidemiologia){ 
		try{
			return ficha_epidemiologiaDao.actualizar(ficha_epidemiologia);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Ficha_epidemiologia consultar(Ficha_epidemiologia ficha_epidemiologia){ 
		try{
			return ficha_epidemiologiaDao.consultar(ficha_epidemiologia);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Ficha_epidemiologia ficha_epidemiologia){ 
		try{
			return ficha_epidemiologiaDao.eliminar(ficha_epidemiologia);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Ficha_epidemiologia> listar(Map<String,Object> parameters){ 
		try{
			parameters.put("orden","orden");
			return ficha_epidemiologiaDao.listar(parameters);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}


	public boolean existe(Map<String,Object> parameters){ 
		return ficha_epidemiologiaDao.existe(parameters);
	}

}