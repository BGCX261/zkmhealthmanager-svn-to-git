/*
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Hisc_aiepi_mn_2_meses_5_anios;

public interface Hisc_aiepi_mn_2_meses_5_aniosDao {

	void crear(Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios); 

	int actualizar(Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios); 

	Hisc_aiepi_mn_2_meses_5_anios consultar(Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios); 

	int eliminar(Hisc_aiepi_mn_2_meses_5_anios hisc_aiepi_mn_2_meses_5_anios); 

	List<Hisc_aiepi_mn_2_meses_5_anios> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}