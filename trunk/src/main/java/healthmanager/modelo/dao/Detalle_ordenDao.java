/*
 * Detalle_ordenDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Detalle_orden;

public interface Detalle_ordenDao {
	
	
	// constantes 
	public static final String CODIGO_EMPRESA = "codigo_empresa";
	public static final String CODIGO_SUCURSAL = "codigo_sucursal";
	public static final String CODIGO_ORDEN = "id_orden";
	public static final String CONSECUTIVO = "consecutivo";
	public static final String CODIGO_PROCEDIMIENTO = "codigo_procedimiento";
	public static final String VALOR_PROCEDIMIENTO = "valor_procedimiento";
	public static final String UNIDADES = "unidades";
	public static final String VALOR_REAL = "valor_real";
	public static final String DESCUENTO = "descuento";
	public static final String INCREMENTO = "incremento";
	public static final String CODIGO_CUPS = "codigo_cups";
    public static final String CODIGO_MEDICO = "codigo_medico";
    public static final String NOMBRES_MEDICO = "nombres_medico";
    public static final String NRO_IDENTIFICACION = "nro_identificacion";
    public static final String NOMBRE_PACIENTE = "nombre_paciente";
    public static final String CODIGO_DX = "codigo_dx";
    public static final String CODIGO_ADMINISTRADORA = "codigo_administradora";
    public static final String NOMBRE_ADMINISTRADORA = "nombre_administradora";
    public static final String FECHA_ORDEN = "fecha_orden";
    public static final String REALIZADO = "realizado"; 
	

	void crear(Detalle_orden detalle_orden); 

	int actualizar(Detalle_orden detalle_orden); 

	Detalle_orden consultar(Detalle_orden detalle_orden); 

	int eliminar(Detalle_orden detalle_orden); 

	List<Detalle_orden> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 
	
	List<Map<String, Object>> listarLaboratoriosRegistrado(Map<String, Object> param); 
	
	int actualizarPorProcedimiento(Detalle_orden detalle_orden);
	
	List<Detalle_orden> listarParametrizado(Map<String, Object> parameters);

}