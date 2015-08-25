package healthmanager.modelo.dao;

import healthmanager.modelo.bean.Recibo_caja;

import java.util.List;
import java.util.Map;

public interface Recibo_cajaDao {

	void crear(Recibo_caja recibo_caja);

	int actualizar(Recibo_caja recibo_caja);

	Recibo_caja consultar(Recibo_caja recibo_caja);
	
	Recibo_caja consultar_recibo(Recibo_caja recibo_caja);

	int eliminar(Recibo_caja recibo_caja);

	List<Recibo_caja> listar(Map<String, Object> parameters);

	void setLimit(String limit);

	Integer totalResultados(Map<String, Object> parameters);
	
	boolean existe(Map<String, Object> parameters);

}
