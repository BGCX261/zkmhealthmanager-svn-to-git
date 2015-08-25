/*
 * Impresion_diagnosticaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Impresion_diagnostica;

public interface Impresion_diagnosticaDao {

	void crear(Impresion_diagnostica impresion_diagnostica); 

	int actualizar(Impresion_diagnostica impresion_diagnostica); 

	Impresion_diagnostica consultar(Impresion_diagnostica impresion_diagnostica); 

	int eliminar(Impresion_diagnostica impresion_diagnostica); 

	List<Impresion_diagnostica> listar(Map<String,Object> parameters); 

	List<Impresion_diagnostica> listar_paciente_contuberculosis_lepra(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param);

	Impresion_diagnostica getInformacionDiagnostica(Admision admision);  

}