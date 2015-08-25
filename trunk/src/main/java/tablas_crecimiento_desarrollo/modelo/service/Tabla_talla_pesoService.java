/*
 * Tabla_talla_pesoServiceImpl.java
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

import tablas_crecimiento_desarrollo.modelo.bean.Tabla_talla_peso;
import tablas_crecimiento_desarrollo.modelo.dao.Tabla_talla_pesoDao;

@Service("tabla_talla_pesoService")
public class Tabla_talla_pesoService {
	@Autowired
	private Tabla_talla_pesoDao tabla_talla_pesoDao;

	public void crear(Tabla_talla_peso tabla_talla_peso){ 
		try{
			if(consultar(tabla_talla_peso)!=null){
				throw new HealthmanagerException("Tabla_talla_peso ya se encuentra en la base de datos");
			}
			tabla_talla_pesoDao.crear(tabla_talla_peso);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Tabla_talla_peso tabla_talla_peso){ 
		try{
			return tabla_talla_pesoDao.actualizar(tabla_talla_peso);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Tabla_talla_peso consultar(Tabla_talla_peso tabla_talla_peso){ 
		try{
			return tabla_talla_pesoDao.consultar(tabla_talla_peso);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Tabla_talla_peso tabla_talla_peso){ 
		try{
			return tabla_talla_pesoDao.eliminar(tabla_talla_peso);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Tabla_talla_peso> listar(Map<String,Object> parameter){ 
		try{
			return tabla_talla_pesoDao.listar(parameter);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setTabla_talla_pesoDao (Tabla_talla_pesoDao tabla_talla_pesoDao){
		this.tabla_talla_pesoDao = tabla_talla_pesoDao;
	}

	public Tabla_talla_pesoDao getTabla_talla_pesoDao(){
		return this.tabla_talla_pesoDao;
	}

	public void setLimit(String limit){
		this.tabla_talla_pesoDao.setLimit(limit);
	}

	public boolean existe(Map<String,Object> parameters){ 
		return this.tabla_talla_pesoDao.existe(parameters);
	}

}