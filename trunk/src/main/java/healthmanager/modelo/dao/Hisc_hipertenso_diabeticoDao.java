/*
 * Hisc_hipertenso_diabeticoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */

package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Hisc_hipertenso_diabetico;

import java.util.List;
import java.util.Map;

public interface Hisc_hipertenso_diabeticoDao {

	void crear(Hisc_hipertenso_diabetico hisc_hipertenso_diabetico);

	int actualizar(Hisc_hipertenso_diabetico hisc_hipertenso_diabetico);

	Hisc_hipertenso_diabetico consultar(
			Hisc_hipertenso_diabetico hisc_hipertenso_diabetico);

	int eliminar(Hisc_hipertenso_diabetico hisc_hipertenso_diabetico);

	List<Hisc_hipertenso_diabetico> listar(Map<String, Object> parameters);

	void setLimit(String limit);

	boolean existe(Map<String, Object> param);

	Integer total_registros(Map<String, Object> parameters);

}