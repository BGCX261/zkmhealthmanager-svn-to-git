/*
 * FacturacionDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */

package contaweb.modelo.dao;

import healthmanager.modelo.bean.Admision;

import java.util.List;
import java.util.Map;

import contaweb.modelo.bean.Facturacion;

public interface FacturacionDao {

	void crear(Facturacion facturacion);

	int actualizar(Facturacion facturacion);

	Facturacion consultar(Facturacion facturacion);
	
	Facturacion consultar_informacion(Facturacion facturacion);

	int eliminar(Facturacion facturacion);

	void setLimit(String limit);

	Map<String, Object> getValorTotalYRecuperado(Map<String, Object> parametros);

	List<Map<String,Object>> listar_facturas(Map<String, Object> parametros);

	int actualizar_centro(Admision admision);
	
	List<Facturacion> listarRegistros(Map<String, Object> parametros);
	
	List<Facturacion> listarFacturacion_rips(Map<String, Object> parametros);

	Integer totalResultados(Map<String, Object> parameters);
	
	boolean existe(Facturacion facturacion);
	
	boolean existePorParametro(Map<String, Object> param);
	
	int anularFacturaAgrupadaCapitada(Map<String, Object> parametros);
	
	Double totalizarSumasDescuentos(Map<String, Object> parametros);
	
	int actualizarCodigoFacturaCapitada(Map<String, Object> parametros);
	
	
	long getTotalServicios(Map<String, Object> map);
	
	List<Map<String, Object>> listarServicios(Map<String, Object> map);
	
	Integer getCantidadMaximaServicio(Map<String, Object> map);
}