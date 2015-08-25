package com.framework.modelo.dao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.Map;

public interface GeneralDao {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface XmlDao {
		String queryId() default "";
	}

	void crear(String queryId, Object dato);

	int actualizar(String queryId, Object dato);

	int eliminar(String queryId, Object dato);

	<T> T consultar(String queryId, T dato);

	<T> List<T> listar(String queryId, Map<String, Object> parametros);

	Long totalizar(String queryId, Map<String, Object> parametros);

	Boolean existe(String queryId, Map<String, Object> parametros);

}
