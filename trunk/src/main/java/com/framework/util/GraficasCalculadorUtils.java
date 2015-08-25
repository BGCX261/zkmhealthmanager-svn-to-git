package com.framework.util;

import healthmanager.controller.ZKWindow;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.List;

import com.framework.macros.graficas.interceptor.IInterceptorGrafica.ESexo;
import com.framework.macros.graficas.interceptor.impl.InterceptorImcEdad0a5;
import com.framework.macros.graficas.interceptor.impl.InterceptorImcEdad2a5;
import com.framework.macros.graficas.interceptor.impl.InterceptorImcEdad5a19;
import com.framework.macros.graficas.interceptor.impl.InterceptorPerimetroCefalicoEdad0a5;
import com.framework.macros.graficas.interceptor.impl.InterceptorPesoTalla0a2Anios;
import com.framework.macros.graficas.interceptor.impl.InterceptorPesoTalla2a5Anios;
import com.framework.macros.graficas.interceptor.impl.InterceptorTallaEdad0a2Anios;
import com.framework.macros.graficas.interceptor.impl.InterceptorTallaEdad2a5;
import com.framework.macros.graficas.interceptor.impl.InterceptorTallaEdad5a19Anios;
import com.framework.macros.graficas.interceptor.impl.IntercerptorPesoEdad0a2Anios;
import com.framework.macros.graficas.interceptor.impl.IntercerptorPesoEdad2a5Anios;
import com.framework.macros.graficas.respuesta.RespuestaInt;

/**
 * <br/><b>Esta utilidad permite, realizar los calculos, y graficas de examen fisico de las historias de pyp</b>
 * @author Ing. Luis Miguel Hernandez
 * */
public class GraficasCalculadorUtils {
	
//	private static Logger log = Logger.getLogger(GraficasCalculadorUtils.class);
	
	
	/* Modo Calulador*/
	/**
	 *  <br/><b>Calculador y graficador de Peso para la edad de 0 a 2 años de pyp</b>
	 *  @author Luis Miguel Hernandez P 
     *  @return IInterceptorGrafica - Este es el interceptor entre la historia, el calulo y la Grafica  
	 * */	
   public static RespuestaInt calcularPesoEdad0$2Anios(ESexo sexo, double peso, double talla, Timestamp fecha_nacimiento){
	   IntercerptorPesoEdad0a2Anios intercerptorPesoEdad0a2Anios = new IntercerptorPesoEdad0a2Anios(sexo);
	   intercerptorPesoEdad0a2Anios.setPeso(peso);
	   intercerptorPesoEdad0a2Anios.setTalla(talla);
	   intercerptorPesoEdad0a2Anios.setFecha_nacimineto(fecha_nacimiento);
	   return intercerptorPesoEdad0a2Anios.getResultado(); 
   }
   
   
	/* Modo Calulador*/
	/**
	 *  <br/><b>Calculador y graficador de Peso para la edad de 2 a 5 años de pyp</b>
	 *  @author Jhonny Gómez Meza 
    *  @return IInterceptorGrafica - Este es el interceptor entre la historia, el calulo y la Grafica  
	 * */	
  public static RespuestaInt calcularPesoEdad2$5Anios(ESexo sexo, double peso, double talla, Timestamp fecha_nacimiento){
	   IntercerptorPesoEdad2a5Anios intercerptorPesoEdad2a5Anios = new IntercerptorPesoEdad2a5Anios(sexo);
	   intercerptorPesoEdad2a5Anios.setPeso(peso);
	   intercerptorPesoEdad2a5Anios.setTalla(talla);
	   intercerptorPesoEdad2a5Anios.setFecha_nacimineto(fecha_nacimiento);
	   return intercerptorPesoEdad2a5Anios.getResultado(); 
  }
  
   
	/**
	 *  <br/><b>Calculador y graficador de Peso para la edad de 0 a 2 años de pyp</b>
	 *  @author Luis Miguel Hernandez P 
    *  @return IInterceptorGrafica - Este es el interceptor entre la historia, el calulo y la Grafica  
	 * */	
  public static RespuestaInt calcularPesoTalla0$2Anios(ESexo sexo, double peso, double talla){
	   InterceptorPesoTalla0a2Anios interceptorPesoTalla0a2Anios = new InterceptorPesoTalla0a2Anios();
	   interceptorPesoTalla0a2Anios.setPeso(peso);
	   interceptorPesoTalla0a2Anios.setTalla(talla);
	   interceptorPesoTalla0a2Anios.setSexo(sexo);
	   return interceptorPesoTalla0a2Anios.getResultado(); 
  }
  
  
  public static RespuestaInt calcularPesoTalla2$5Anios(ESexo sexo, double peso, double talla){
	   InterceptorPesoTalla2a5Anios interceptorPesoTalla2a5Anios = new InterceptorPesoTalla2a5Anios();
	   interceptorPesoTalla2a5Anios.setPeso(peso);
	   interceptorPesoTalla2a5Anios.setTalla(talla);
	   interceptorPesoTalla2a5Anios.setSexo(sexo);
	   return interceptorPesoTalla2a5Anios.getResultado(); 
 }
  
  
  /**
	 *  <br/><b>Calculador y graficador de Peso para la edad de 0 a 2 años de pyp</b>
	 *  @author Luis Miguel Hernandez P 
  *  @return IInterceptorGrafica - Este es el interceptor entre la historia, el calulo y la Grafica  
	 * */	
public static RespuestaInt calcularPerimetroCefalicoEdad0$2Anios(ESexo sexo, double perimetro_cefalico, Timestamp fecha_nacimiento){
	   InterceptorPerimetroCefalicoEdad0a5 interceptorPerimetroCefalicoEdad0a5 = new InterceptorPerimetroCefalicoEdad0a5();
	   interceptorPerimetroCefalicoEdad0a5.setPerimetro_cefalico(perimetro_cefalico);
	   interceptorPerimetroCefalicoEdad0a5.setFecha_nacimiento(fecha_nacimiento);
	   interceptorPerimetroCefalicoEdad0a5.setSexo(sexo);
	   return interceptorPerimetroCefalicoEdad0a5.getResultado(); 
}
   
   /**
	 *  <br/><b>Calculador y graficador de Talla para la edad de 0 a 2 años de pyp</b>
	 *  @author Luis Miguel Hernandez P 
    *  @return IInterceptorGrafica - Este es el interceptor entre la historia, el calulo y la Grafica  
	 * */	
  public static RespuestaInt calcularTallaEdad0$2Anios(ESexo sexo,double talla, Timestamp fecha_nacimiento){
	   InterceptorTallaEdad0a2Anios interceptorTallaEdad0a2Anios = new InterceptorTallaEdad0a2Anios();
	   interceptorTallaEdad0a2Anios.setTalla(talla);
	   interceptorTallaEdad0a2Anios.setFecha_nacimiento(fecha_nacimiento);
	   interceptorTallaEdad0a2Anios.setSexo(sexo);
	   return interceptorTallaEdad0a2Anios.getResultado(); 
  }
  
  public static RespuestaInt calcularTallaEdad2$5Anios(ESexo sexo,double talla, Timestamp fecha_nacimiento){
	   InterceptorTallaEdad2a5 interceptorTallaEdad2a5 = new InterceptorTallaEdad2a5();
	   interceptorTallaEdad2a5.setTalla(talla);
	   interceptorTallaEdad2a5.setFecha_nacimiento(fecha_nacimiento);
	   interceptorTallaEdad2a5.setSexo(sexo);
	   return interceptorTallaEdad2a5.getResultado(); 
 }
  
  public static RespuestaInt calcularTallaEdad5$19Anios(ESexo sexo,double talla, Timestamp fecha_nacimiento){
	   InterceptorTallaEdad5a19Anios interceptorTallaEdad5a19Anios = new InterceptorTallaEdad5a19Anios();
	   interceptorTallaEdad5a19Anios.setTalla(talla);
	   interceptorTallaEdad5a19Anios.setFecha_nacimiento(fecha_nacimiento);
	   interceptorTallaEdad5a19Anios.setSexo(sexo);
	   return interceptorTallaEdad5a19Anios.getResultado(); 
}
  
  public static RespuestaInt calcularIMCEdad2$5Anios(ESexo sexo,double imc, Timestamp fecha_nacimiento){
	   InterceptorImcEdad2a5 interceptorImcEdad2a5 = new InterceptorImcEdad2a5();
	   interceptorImcEdad2a5.setImc(imc);
	   interceptorImcEdad2a5.setFecha_nacimiento(fecha_nacimiento);
	   interceptorImcEdad2a5.setSexo(sexo);
	   return interceptorImcEdad2a5.getResultado(); 
}
  
  public static RespuestaInt calcularIMCEdad5$19Anios(ESexo sexo,double imc, Timestamp fecha_nacimiento){
	   InterceptorImcEdad5a19 interceptorImcEdad5a19 = new InterceptorImcEdad5a19();
	   interceptorImcEdad5a19.setImc(imc);
	   interceptorImcEdad5a19.setFecha_nacimiento(fecha_nacimiento);
	   interceptorImcEdad5a19.setSexo(sexo);
	   return interceptorImcEdad5a19.getResultado(); 
}
  
  public static RespuestaInt calcularIMCEdad0$5Anios(ESexo sexo,double imc, Timestamp fecha_nacimiento){
	   InterceptorImcEdad0a5 interceptorImcEdad0a5 = new InterceptorImcEdad0a5();
	   interceptorImcEdad0a5.setImc(imc);
	   interceptorImcEdad0a5.setFecha_nacimiento(fecha_nacimiento);
	   interceptorImcEdad0a5.setSexo(sexo);
	   return interceptorImcEdad0a5.getResultado(); 
}
   
   /*****************************************************************************************/
   
   /* Metodo Grafica */
   public enum TipoGrafica{
		PESO_EDAD_0_A_2_ANIOS, TALLA_EDAD_0_A_2_ANIOS, PESO_TALLA_0_A_2, PERIMETRO_CEFALICO_EDAD_0_5, TALLA_EDAD_DE_0_A_5
		, PESO_TALLA_2_A_5, IMC_EDAD_2_A_5, TALLA_EDAD_5_19, IMC_EDAD_5_A_19, IMC_EDAD_0_A_5;
	    
	   private static Class<?> getClassInterceptor(TipoGrafica tipoGrafica){
		    if(tipoGrafica == null)MensajesUtil.mensajeAlerta("Advertencia", "El tipo Grafica no puede ser nulo..!!"); 
			switch (tipoGrafica) {
			case PESO_EDAD_0_A_2_ANIOS:
				return IntercerptorPesoEdad0a2Anios.class;
			case TALLA_EDAD_0_A_2_ANIOS:
				return InterceptorTallaEdad0a2Anios.class;
			case PESO_TALLA_0_A_2:
				return InterceptorPesoTalla0a2Anios.class;
			case PERIMETRO_CEFALICO_EDAD_0_5:
				return InterceptorPerimetroCefalicoEdad0a5.class;
			case TALLA_EDAD_DE_0_A_5:
				return InterceptorTallaEdad2a5.class;
			case PESO_TALLA_2_A_5:
				return InterceptorPesoTalla2a5Anios.class;
			case IMC_EDAD_2_A_5:
				return InterceptorImcEdad2a5.class;
			case TALLA_EDAD_5_19:
				return InterceptorTallaEdad5a19Anios.class;
			case IMC_EDAD_5_A_19:
				return InterceptorImcEdad5a19.class;
			case IMC_EDAD_0_A_5:
				return InterceptorImcEdad0a5.class;
			default:
				return null;
			}
	   }
   }
   
   
   /**
	 *  <br/><b>Calculador y graficador de Peso para la edad de 0 a 2 años de pyp</b>
	 *  @author Luis Miguel Hernandez P 
    *  @return IInterceptorGrafica - Este es el interceptor entre la historia, el calulo y la Grafica  
	 * */
   public static void mostrarGrafica(TipoGrafica tipoGrafica, List<RespuestaInt> valores, ZKWindow zkWindow, ESexo sexo){
	   try {
		Class<?> classInterceptor = TipoGrafica.getClassInterceptor(tipoGrafica);
		   if(classInterceptor != null){
			   Method method = classInterceptor.getDeclaredMethod("mostrarGrafica", List.class, ZKWindow.class, ESexo.class);
			   method.setAccessible(true);
			   method.invoke(classInterceptor.newInstance(), valores, zkWindow, sexo);  
		   }else{
			   MensajesUtil.mensajeAlerta("Advertencia", "Grafica No Valida..!!"); 
			   //log.info("Grafica no Valida"); 
		   }
	} catch (Exception e) {
		e.printStackTrace();
	}
   }
   
   
   /**
	 *  <br/><b>Muestra las tablas de pyp</b>
	 *  @author Luis Miguel Hernandez P 
   *  @return IInterceptorGrafica - Este es el interceptor entre la historia, el calulo y la Grafica  
	 * */
  public static void mostrarTabla(TipoGrafica tipoTabla, ZKWindow zkWindow, ESexo sexo){
	   try {
		Class<?> classInterceptor = TipoGrafica.getClassInterceptor(tipoTabla);
		   if(classInterceptor != null){
			   Method method = classInterceptor.getDeclaredMethod("mostrarTabla", ESexo.class, ZKWindow.class);
			   method.setAccessible(true);
			   method.invoke(classInterceptor.newInstance(), sexo, zkWindow);  
		   }else{
			   MensajesUtil.mensajeAlerta("Advertencia", "Tabla No Valida..!!"); 
			   //log.info("Grafica no Valida"); 
		   }
	} catch (Exception e) {
		e.printStackTrace();
	}
  }
 }
