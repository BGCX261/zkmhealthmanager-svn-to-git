package com.framework.macros.graficas.interceptor;

import healthmanager.controller.ZKWindow;

import java.util.List;

import com.framework.macros.graficas.respuesta.RespuestaInt;

public interface IInterceptorGrafica {
   /**
    * Este metodo se encargar de devolver el calculo
    * @author Luis Miguel Hernandez
    * */	
   RespuestaInt getResultado();
   
   /**
    * Este metodo se encargar de generar la Grafica
    * @author Luis Miguel Hernandez
    * @param valores - <b>En Esta parte puede enviar, los valores correcpondientes para graficar<br/>
    *                     Teniendo en cuenta el orden de esa manera los graficara.</b>
    * */
   void mostrarGrafica(List<RespuestaInt> valores, ZKWindow zkWindow, ESexo sexo);
   
   
   void mostrarTabla(ESexo sexo, ZKWindow zkWindow);
   
   
   boolean validar();
   
   /**
    * Para Identificar el Sexo
    * @author Luis Miguel Hernandez Perez
    * */
   public enum ESexo {
	   MASCULINO,FEMENINO;
	     
      public static String getSexo(ESexo sexo){
	      if(sexo == MASCULINO)
	    	  return "M";
	      else return "F"; 
      }
   };
   
   
   /**
    *  Este son los tipos de tablas 
    *   IMC - IMC
	*	LGH - Length
	*	HED - HEAD
	*	BMI - BMI
	*	HGH - HEIGHT 
    * */
   public enum ETipoTabla{
	   IMC, LENGTH, HEAD, BMI, HEIGHT, WEIGHT;
	   
	   public static String getTipo(ETipoTabla tipoTabla){
		   if(tipoTabla == BMI){
			   return tipoTabla.toString();
		   }else if(tipoTabla == LENGTH){
			   return "LGH";
		   }else if(tipoTabla == HEAD){
			   return "HED";
		   }else if(tipoTabla == BMI){
			   return "BMI";
		   }else if(tipoTabla == HEIGHT){
			   return "HGH";
		   }else if(tipoTabla == WEIGHT){
			   return "WGH";
		   }
		   return "_$noLook";
	   }
   }
   
}
