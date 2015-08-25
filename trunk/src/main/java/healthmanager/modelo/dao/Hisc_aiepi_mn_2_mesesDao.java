/*
 * Hisc_aiepi_mn_2_mesesDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Hisc_aiepi_mn_2_meses;

public interface Hisc_aiepi_mn_2_mesesDao {

	void crear(Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses); 

	int actualizar(Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses); 

	Hisc_aiepi_mn_2_meses consultar(Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses); 

	int eliminar(Hisc_aiepi_mn_2_meses hisc_aiepi_mn_2_meses); 

	List<Hisc_aiepi_mn_2_meses> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

	Integer total_registros(Map<String, Object> parameters);
}