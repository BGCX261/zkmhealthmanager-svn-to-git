/*
 * Cie_epidemiologiaDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Cie_epidemiologia;

public interface Cie_epidemiologiaDao {

	void crear(Cie_epidemiologia cie_epidemiologia); 

	int actualizar(Cie_epidemiologia cie_epidemiologia); 

	Cie_epidemiologia consultar(Cie_epidemiologia cie_epidemiologia); 

	int eliminar(Cie_epidemiologia cie_epidemiologia); 

	List<Cie_epidemiologia> listar(Map<String,Object> parameters); 

	List<Cie_epidemiologia> listar_cie(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}