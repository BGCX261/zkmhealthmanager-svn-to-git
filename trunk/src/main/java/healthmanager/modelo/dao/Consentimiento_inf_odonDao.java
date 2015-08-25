/*
 * Consentimineto_inf_odonDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Consentimiento_inf_odon;

public interface Consentimiento_inf_odonDao {

	void crear(Consentimiento_inf_odon consentimineto_inf_odon);

	int actualizar(Consentimiento_inf_odon consentimineto_inf_odon);

	Consentimiento_inf_odon consultar(
			Consentimiento_inf_odon consentimineto_inf_odon);

	int eliminar(Consentimiento_inf_odon consentimineto_inf_odon);

	List<Consentimiento_inf_odon> listar(Map<String, Object> parameters);

	void setLimit(String limit);

	boolean existe(Map<String, Object> param);

	Consentimiento_inf_odon consultarPorParametros(Consentimiento_inf_odon consentimineto_inf_odon); 
}