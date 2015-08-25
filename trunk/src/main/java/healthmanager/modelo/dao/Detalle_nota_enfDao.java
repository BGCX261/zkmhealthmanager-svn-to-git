/*
 * Detalle_nota_enfDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_nota_enf;

public interface Detalle_nota_enfDao {

	void crear(Detalle_nota_enf detalle_nota_enf); 

	int actualizar(Detalle_nota_enf detalle_nota_enf); 

	Detalle_nota_enf consultar(Detalle_nota_enf detalle_nota_enf); 

	int eliminar(Detalle_nota_enf detalle_nota_enf); 

	List<Detalle_nota_enf> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}