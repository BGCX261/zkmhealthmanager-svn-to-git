/*
 * Certificado_incapacidad_cajaServiceImpl.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Certificado_incapacidad_caja;
import healthmanager.modelo.dao.Certificado_incapacidad_cajaDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

@Service("certificado_incapacidad_cajaService")
@Scope(value="singleton",proxyMode=ScopedProxyMode.TARGET_CLASS)
public class Certificado_incapacidad_cajaService implements Serializable{

	@Autowired private Certificado_incapacidad_cajaDao certificado_incapacidad_cajaDao;
	@Autowired private ConsecutivoService consecutivoService;
	private String limit;

	private final int CANTIDAD_CEROS = 5;
	
	public void crear(Certificado_incapacidad_caja certificado_incapacidad_caja){ 
		try{
			String codigo = consecutivoService.getZeroFill(consecutivoService
					.crearConsecutivo(certificado_incapacidad_caja.getCodigo_empresa(),
							certificado_incapacidad_caja.getCodigo_sucursal(),
							"CERTIFICADOS_INCAPACIDAD"), CANTIDAD_CEROS);
			certificado_incapacidad_caja.setCodigo(codigo);
			consecutivoService.actualizarConsecutivo(
					certificado_incapacidad_caja.getCodigo_empresa(),
					certificado_incapacidad_caja.getCodigo_sucursal(),
					"CERTIFICADOS_INCAPACIDAD", codigo);
			if(consultar(certificado_incapacidad_caja)!=null){
				throw new HealthmanagerException("Certificado ya se encuentra en la base de datos, verifique los consecutivos");
			}
			certificado_incapacidad_cajaDao.crear(certificado_incapacidad_caja);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int actualizar(Certificado_incapacidad_caja certificado_incapacidad_caja){ 
		try{
			return certificado_incapacidad_cajaDao.actualizar(certificado_incapacidad_caja);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public Certificado_incapacidad_caja consultar(Certificado_incapacidad_caja certificado_incapacidad_caja){ 
		try{
			return certificado_incapacidad_cajaDao.consultar(certificado_incapacidad_caja);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public int eliminar(Certificado_incapacidad_caja certificado_incapacidad_caja){ 
		try{
			return certificado_incapacidad_cajaDao.eliminar(certificado_incapacidad_caja);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public List<Certificado_incapacidad_caja> listar(Map<String,Object> parameters){ 
		try{
			parameters.put("limit",limit);
			return certificado_incapacidad_cajaDao.listar(parameters);
		}catch(Exception e){
			throw new HealthmanagerException(e.getMessage(),e);
		}
	}

	public void setLimit(String limit){
		this.limit = limit;
	}

	public boolean exist(Map<String,Object> parameters){ 
		return certificado_incapacidad_cajaDao.exist(parameters);
	}

	public Integer totalResultados(Map<String, Object> parametros) { 
		return certificado_incapacidad_cajaDao.totalResultados(parametros);
	}



}