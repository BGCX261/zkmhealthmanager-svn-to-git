package com.framework.macros;

import healthmanager.exception.ValidacionRunTimeException;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;

public class TablaPaginadaMacro extends Listbox{

	private int tamanio_pagina = -1;
	Map<String, Object> params = null;
	private Object servicio;
//	private String metodo_listar;
	private String metodo_contador;
	private Method method;
	private Method methodLimit;
	
	private IEventoTablaPaginada ieventoTablaPaginada;
	private Method method_conteo;
//	private Long cantidad_registros_consultados;
	
	
	private int posicion_tabla = 1;
	
	
	private final String METODO_LIMIT = "setLimit";
	
	@Override
	public void setPageSize(int tamanio_pagina) throws WrongValueException {
		this.tamanio_pagina = tamanio_pagina;
	}
     
	
	/**
	 * Este metodo me permite llevar los parametros 
	 * de la consulta para cada vez que se valla a consultar
	 * para trabajar con este metodo se debe tener un metodo el cual permita saber la cantidad con base a
	 * los parametros.
	 * @param params
	 * @param servicio
	 * @param metodo_listar
	 * @param metodo_contador
	 * */
	public void parametrizarListado(Map<String, Object> params,
			Object servicio, String metodo_listar, String metodo_contador, int tamanio_pagina) throws Exception{
		try {
			if(servicio != null){
				// metodo para consultar
				method =  servicio.getClass().getMethod(metodo_listar, Map.class); 
				if(!method.isAccessible()){ 
					method.setAccessible(true); 	
				}
				
				// metodo para verificar la cantidad
				method_conteo = servicio.getClass().getMethod(metodo_contador, Map.class);
				if(!method_conteo.isAccessible()){ 
					method_conteo.setAccessible(true); 	
				}
				
				methodLimit = servicio.getClass().getMethod(METODO_LIMIT, String.class);
				if(!methodLimit.isAccessible()){ 
					methodLimit.setAccessible(true); 	
				}
				
				// tomamos la referencia 
				this.servicio = servicio;
				this.params = params;
//				this.metodo_listar = metodo_listar;
				this.metodo_contador = metodo_contador;
				this.tamanio_pagina = tamanio_pagina;
			}else{
			     throw new ValidacionRunTimeException("Servicio no puede ser nulo.");			
			} 
		} catch (NoSuchMethodException e) {
			 throw new ValidacionRunTimeException("El metodo " + metodo_listar + " no es valido!");
		} catch (IllegalArgumentException e) {
			throw new ValidacionRunTimeException("Parametro no valido para el metodo " + metodo_listar);
		} 
	}
	
	
	/**
	 * Este metodo sirve para que el componente 
	 * realice la consulta de los datos encontrados 
	 * */
	public void listar(){
		try {
			getChildren().clear();// limpiamos los detalles actuales
			if(getIeventoTablaPaginada() != null){
				Object tamanio = method_conteo.invoke(metodo_contador, params);
				if(tamanio instanceof Long){
//					this.cantidad_registros_consultados = (Long)tamanio; // En esta parte tenemos la cantidad de registros
				}else{
					throw new ValidacionRunTimeException("Para realizar esta accion el metodo inicial debe devolver un LONG");
				}
				// Cargamos el limite
				methodLimit.invoke(servicio, getLimite());
				
				// cargamos la informacion
				Object object = method.invoke(servicio, params); 
				if(object instanceof List){
					List<Object> list = (List<Object>) object;
					for (Object objectTemp : list) {
						Listitem listitem = new Listitem();
						listitem.setValue(objectTemp); 
						getIeventoTablaPaginada().onConsultar(objectTemp, listitem); 
						appendChild(listitem); 
					}invalidate();
				}else{
					throw new ValidacionRunTimeException("El metodo no retorna una objeto de tipo java.util.List");	
				}
			}else{
				throw new ValidacionRunTimeException("Para utilizar este metodo debe inicializar el IEventoTablaPaginada");
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Este metodo me genera el estado del limite actual
	 * */
	private String getLimite() {
		return "LIMIT "  +  tamanio_pagina  +  " OFFSET " + (tamanio_pagina * posicion_tabla);
	}

	public interface IEventoTablaPaginada{
		void onConsultar(Object object, Listitem listitem);
	}


	public IEventoTablaPaginada getIeventoTablaPaginada() {
		return ieventoTablaPaginada;
	}


	public void setIeventoTablaPaginada(IEventoTablaPaginada ieventoTablaPaginada) {
		this.ieventoTablaPaginada = ieventoTablaPaginada;
	}
}
