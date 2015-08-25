package com.framework.modelo.service;

import java.util.List;
import java.util.Map;

public interface GeneralService {

	<T> void crear(T dato);

	void crear(String queryId, Object dato);

	<T> int actualizar(T dato);

	int actualizar(String queryId, Object dato);

	<T> int eliminar(T dato);

	int eliminar(String queryId, Object dato);

	<T> T consultar(T dato);

	Object consultar(String queryId, Object dato);

	<T> List<T> listar(Class<?> clase, Map<String, Object> parametros);

	<T> List<T> listar(String queryId, Map<String, Object> parametros);

	Long totalizar(Class<?> clase, Map<String, Object> parametros);
	
	Long totalizar(String queryId, Map<String, Object> parametros);
	
	Boolean existe(Class<?> clase, Map<String, Object> parametros);

	Boolean existe(String queryId, Map<String, Object> parametros);

}
