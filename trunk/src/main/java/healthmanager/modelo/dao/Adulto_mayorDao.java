package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;

import healthmanager.modelo.bean.Adulto_mayor;

public interface Adulto_mayorDao {

	void crear(Adulto_mayor adulto_mayor); 

	int actualizar(Adulto_mayor adulto_mayor); 

	Adulto_mayor consultar(Adulto_mayor adulto_mayor); 

	Adulto_mayor consultar_historia(Adulto_mayor adulto_mayor); 

	int eliminar(Adulto_mayor adulto_mayor); 

	List<Adulto_mayor> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String, Object> param); 
	
	Integer total_registros(Map<String, Object> parameters);

}