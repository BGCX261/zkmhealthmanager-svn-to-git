/*
 * Imagen_diagnosticaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package imagen_diagnostica.modelo.service;

import healthmanager.exception.HealthmanagerException;
import imagen_diagnostica.modelo.bean.Imagen_diagnostica;
import imagen_diagnostica.modelo.dao.Imagen_diagnosticaDao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("imagen_diagnosticaService")
public class Imagen_diagnosticaService {

	@Autowired
	private Imagen_diagnosticaDao imagen_diagnosticaDao;
	private String limit; 

	public void crear(Imagen_diagnostica imagen_diagnostica){ 
		try{
			if(consultar(imagen_diagnostica)!=null){
				throw new HealthmanagerException("Imagen_diagnostica ya se encuentra en la base de datos");
			}
			imagen_diagnosticaDao.crear(imagen_diagnostica);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Imagen_diagnostica imagen_diagnostica){ 
		try{
			return imagen_diagnosticaDao.actualizar(imagen_diagnostica);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Imagen_diagnostica consultar(Imagen_diagnostica imagen_diagnostica){ 
		try{
			return imagen_diagnosticaDao.consultar(imagen_diagnostica);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Imagen_diagnostica imagen_diagnostica){ 
		try{
			return imagen_diagnosticaDao.eliminar(imagen_diagnostica);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Imagen_diagnostica> listar(Map<String,Object> parameter){ 
		try{
			parameter.put("limit", limit); 
			return imagen_diagnosticaDao.listar(parameter);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setImagen_diagnosticaDao (Imagen_diagnosticaDao imagen_diagnosticaDao){
		this.imagen_diagnosticaDao = imagen_diagnosticaDao;
	}

	public Imagen_diagnosticaDao getImagen_diagnosticaDao(){
		return this.imagen_diagnosticaDao;
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

	public boolean existe(Map<String,Object> parameters){ 
		return this.imagen_diagnosticaDao.existe(parameters);
	}
	
	public Integer consultarTotalImaenDiagnostica(
			Map<String, Object> total_imagenes_diagnosticasMap) {
		return this.imagen_diagnosticaDao.consultarTotalImaenDiagnostica(total_imagenes_diagnosticasMap);
	}

}