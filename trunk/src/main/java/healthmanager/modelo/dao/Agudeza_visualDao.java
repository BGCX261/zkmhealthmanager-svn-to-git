/*
 * Agudeza_visualDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Agudeza_visual;

public interface Agudeza_visualDao {

	void crear(Agudeza_visual agudeza_visual);

	int actualizar(Agudeza_visual agudeza_visual);

	Agudeza_visual consultar(Agudeza_visual agudeza_visual);

	Agudeza_visual consultarAnio(Agudeza_visual agudeza_visual);

	int eliminar(Agudeza_visual agudeza_visual);

	List<Agudeza_visual> listar(Map<String, Object> parameters);

	void setLimit(String limit);

}