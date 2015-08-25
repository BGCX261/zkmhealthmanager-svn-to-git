package com.framework.util;

import healthmanager.exception.BloqueoException;
import healthmanager.modelo.bean.Empresa;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.framework.constantes.IConstantes;

public class Validador {
	
	public static Logger log = Logger.getLogger(Validador.class);
	
	public static void validar(Empresa empresa) {
		if(empresa != null){
			if(isLock(empresa)){
				throw new BloqueoException("lock", empresa);
			}else if (empresa.getDelete_date() != null) {
				Date date = empresa.getDelete_date();
				if (date.compareTo(Calendar.getInstance().getTime()) < 0) {
					throw new BloqueoException("lock", empresa);
				}
			}
		}
	}

	public static void lock(Empresa empresa) {
		String n_f =  "L" + empresa.getCodigo_empresa() + IConstantes.FILE_CONFIG;
		String dir = getDirectorio();
        ArchivoUtil.guardarArchivo(dir + n_f, empresa); 
	}
	
	public static boolean isLock(Empresa empresa){
		if(empresa != null){
			String n_f =  "L" +empresa.getCodigo_empresa() + IConstantes.FILE_CONFIG; 
			String dir = getDirectorio();
			Object object = ArchivoUtil.abrirArchivo(dir + n_f);
			return object != null;
		}else{
			return false;
		}
	}
	
	public static String getDirectorio(){
		try { 
			Properties prop = new Properties();
			prop.load(Validador.class.getResourceAsStream("lock.properties"));
			String directorio = prop.getProperty("dir");
			File file = new File(directorio);
			if(!file.exists()){
				file.mkdirs();
			}
			
//			//log.info("Directorio: " + file.getAbsolutePath());  
			return file.getAbsolutePath(); 
		} catch (Exception e) {
			log.debug("Error en el directorio bloqueo", e);
			return "/tmp/";
		}  
	}
}
