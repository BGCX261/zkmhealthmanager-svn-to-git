/*
 * Tabla_anios_mesesServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package tablas_crecimiento_desarrollo.modelo.service;

import healthmanager.exception.HealthmanagerException;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tablas_crecimiento_desarrollo.modelo.bean.Tabla_anios_meses;
import tablas_crecimiento_desarrollo.modelo.dao.Tabla_anios_mesesDao;

@Service("tabla_anios_mesesService")
public class Tabla_anios_mesesService{

	@Autowired
	private Tabla_anios_mesesDao tabla_anios_mesesDao;

	public void crear(Tabla_anios_meses tabla_anios_meses){ 
		try{
			if(consultar(tabla_anios_meses)!=null){
				throw new HealthmanagerException("Tabla_anios_meses ya se encuentra en la base de datos");
			}
			tabla_anios_mesesDao.crear(tabla_anios_meses);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Tabla_anios_meses tabla_anios_meses){ 
		try{
			return tabla_anios_mesesDao.actualizar(tabla_anios_meses);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Tabla_anios_meses consultar(Tabla_anios_meses tabla_anios_meses){ 
		try{
			return tabla_anios_mesesDao.consultar(tabla_anios_meses);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Tabla_anios_meses tabla_anios_meses){ 
		try{
			return tabla_anios_mesesDao.eliminar(tabla_anios_meses);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Tabla_anios_meses> listar(Map<String,Object> parameter){ 
		try{
			return tabla_anios_mesesDao.listar(parameter);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setTabla_anios_mesesDao (Tabla_anios_mesesDao tabla_anios_mesesDao){
		this.tabla_anios_mesesDao = tabla_anios_mesesDao;
	}

	public Tabla_anios_mesesDao getTabla_anios_mesesDao(){
		return this.tabla_anios_mesesDao;
	}

	public void setLimit(String limit){
		this.tabla_anios_mesesDao.setLimit(limit);
	}

	public boolean existe(Map<String,Object> parameters){ 
		return this.tabla_anios_mesesDao.existe(parameters);
	}

}