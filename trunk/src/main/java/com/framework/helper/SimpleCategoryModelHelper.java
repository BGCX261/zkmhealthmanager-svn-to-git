package com.framework.helper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.SimpleCategoryModel;

public class SimpleCategoryModelHelper extends SimpleCategoryModel {
	
	
	private Map<Object, Object> mapPoblacion = new HashMap<Object, Object>();
	
	
	/**
	 * Este metodo me permite cargar los objetos que aplican ha este modelo
	 * @author Luis Miguel Hernandez
	 * */
	public void setValue(Comparable<?> arg0, Comparable<?> arg1, List<?> objects) {
		super.setValue(arg0, arg1, objects.size());
		if(mapPoblacion == null)
			mapPoblacion = new HashMap<Object, Object>();
		mapPoblacion.put(arg0.toString() + arg1.toString(), objects);
	}
	
	public <T>  List<T>  getObjects(String categoria){
		return (List<T>) mapPoblacion.get(categoria); 
	} 
	
}
