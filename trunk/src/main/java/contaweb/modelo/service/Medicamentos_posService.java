/*
 * Medicamentos_posServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos softcomputo 
 */ 

package contaweb.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import contaweb.modelo.bean.Medicamentos_pos;
import contaweb.modelo.dao.Medicamentos_posDao;

@Service("medicamentos_posService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Medicamentos_posService implements Serializable{

	private String limit;

	@Autowired private Medicamentos_posDao medicamentos_posDao;

	public void crear(Medicamentos_pos medicamentos_pos){ 
		try{
			if(consultar(medicamentos_pos)!=null){
				throw new HealthmanagerException("Medicamentos_pos ya se encuentra en la base de datos");
			}
			medicamentos_posDao.crear(medicamentos_pos);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Medicamentos_pos medicamentos_pos){ 
		try{
			return medicamentos_posDao.actualizar(medicamentos_pos);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Medicamentos_pos consultar(Medicamentos_pos medicamentos_pos){ 
		try{
			return medicamentos_posDao.consultar(medicamentos_pos);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Medicamentos_pos medicamentos_pos){ 
		try{
			return medicamentos_posDao.eliminar(medicamentos_pos);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Medicamentos_pos> listar(Map parameter){ 
		try{
			parameter.put("limit", limit);
			return medicamentos_posDao.listar(parameter);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

}
