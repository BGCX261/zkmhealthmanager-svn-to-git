/*
 * Firma_certificadoDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo Ferney Jimenez Lopez 
 */ 

package healthmanager.modelo.dao;

import java.util.List;
import java.util.Map;
import healthmanager.modelo.bean.Firma_certificado;

public interface Firma_certificadoDao {

	void crear(Firma_certificado firma_certificado); 

	int actualizar(Firma_certificado firma_certificado); 

	Firma_certificado consultar(Firma_certificado firma_certificado); 

	int eliminar(Firma_certificado firma_certificado); 

	List<Firma_certificado> listar(Map<String, Object> parameters); 

	void setLimit(String limit); 

}