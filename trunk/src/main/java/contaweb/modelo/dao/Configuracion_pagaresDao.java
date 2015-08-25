/*
 * Configuracion_pagaresDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Configuracion_pagares;

public interface Configuracion_pagaresDao {

	void crear(Configuracion_pagares configuracion_pagares); 

	int actualizar(Configuracion_pagares configuracion_pagares); 

	Configuracion_pagares consultar(Configuracion_pagares configuracion_pagares); 

	int eliminar(Configuracion_pagares configuracion_pagares); 

	List<Configuracion_pagares> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}