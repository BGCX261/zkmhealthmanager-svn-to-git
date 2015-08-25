/*
 * EmpresaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import contaweb.modelo.bean.Empresa;

@Repository("empresaContawebDao")
public interface EmpresaDao {

	void crear(Empresa empresa); 

	int actualizar(Empresa empresa); 

	Empresa consultar(Empresa empresa); 

	int eliminar(Empresa empresa); 

	List<Empresa> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}