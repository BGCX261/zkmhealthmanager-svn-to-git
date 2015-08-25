/*
 * Detalle_pagareDao.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 

package contaweb.modelo.dao;

import java.util.List;
import java.util.Map;
import contaweb.modelo.bean.Detalle_pagare;

public interface Detalle_pagareDao {

	void crear(Detalle_pagare detalle_pagare); 

	int actualizar(Detalle_pagare detalle_pagare); 

	Detalle_pagare consultar(Detalle_pagare detalle_pagare); 

	int eliminar(Detalle_pagare detalle_pagare); 

	List<Detalle_pagare> listar(Map<String,Object> parameters); 

	void setLimit(String limit); 

	boolean existe(Map<String,Object> param); 

}