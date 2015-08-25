/*
 * Laboratorios_resultadosServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package imagen_diagnostica.modelo.service;

import healthmanager.exception.HealthmanagerException;
import imagen_diagnostica.modelo.bean.Laboratorios_resultados;
import imagen_diagnostica.modelo.dao.Laboratorios_resultadosDao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("laboratorios_resultadosService")
public class Laboratorios_resultadosService {

	@Autowired private Laboratorios_resultadosDao laboratorios_resultadosDao;
	private String limit;

	public void crear(Laboratorios_resultados laboratorios_resultados){ 
		try{
			if(consultar(laboratorios_resultados)!=null){
				throw new HealthmanagerException("Laboratorios_resultados ya se encuentra en la base de datos");
			}
			laboratorios_resultadosDao.crear(laboratorios_resultados);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Laboratorios_resultados laboratorios_resultados){ 
		try{
			return laboratorios_resultadosDao.actualizar(laboratorios_resultados);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Laboratorios_resultados consultar(Laboratorios_resultados laboratorios_resultados){ 
		try{
			return laboratorios_resultadosDao.consultar(laboratorios_resultados);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Laboratorios_resultados laboratorios_resultados){ 
		try{
			return laboratorios_resultadosDao.eliminar(laboratorios_resultados);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Laboratorios_resultados> listar(Map<String,Object> parameters){ 
		try{
			parameters.put("limit",limit);
			return laboratorios_resultadosDao.listar(parameters);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

	public boolean existe(Map<String,Object> parameters){ 
		return laboratorios_resultadosDao.existe(parameters);
	}

}