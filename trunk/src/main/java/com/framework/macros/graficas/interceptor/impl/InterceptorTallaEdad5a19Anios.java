package com.framework.macros.graficas.interceptor.impl;

import healthmanager.controller.ZKWindow;
import healthmanager.exception.HealthmanagerException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;

import tablas_crecimiento_desarrollo.modelo.bean.Tabla_anios_meses;

import com.framework.macros.graficas.GraficaMacro;
import com.framework.macros.graficas.TablaMacro;
import com.framework.macros.graficas.TablaMacro.Tipo_tabla;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica;
import com.framework.macros.graficas.interceptor.InterceptorSelector;
import com.framework.macros.graficas.moldG.PainterMoldG;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.res.Funcion_getEdad;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class InterceptorTallaEdad5a19Anios extends InterceptorSelector
		implements IInterceptorGrafica {

	private Timestamp fecha_nacimiento;
	private double talla;
	private ESexo sexo;

	public Timestamp getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Timestamp fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public double getTalla() {
		return talla;
	}

	public void setTalla(double talla) {
		this.talla = talla;
	}

	public ESexo getSexo() {
		return sexo;
	}

	public void setSexo(ESexo sexo) {
		this.sexo = sexo;
	}

	@Override
	public RespuestaInt getResultado() {
		if(validar()){
			String sexoL = ESexo.getSexo(sexo);
			String tipo_tabla = ETipoTabla.getTipo(ETipoTabla.HEIGHT);
			
			Double meses = Funcion_getEdad.getMesesLong(fecha_nacimiento); 
			
			Tabla_anios_meses tabla_anios_meses = new Tabla_anios_meses();
			tabla_anios_meses.setMes(meses.intValue());  
			tabla_anios_meses.setSexo(sexoL); 
			tabla_anios_meses.setEdad_inicial(5);
			tabla_anios_meses.setEdad_final(19);
			tabla_anios_meses.setTipo(tipo_tabla);
			RespuestaInt respuestaInt = new RespuestaInt();
			insertPuntosRespuesta(respuestaInt);
			tabla_anios_meses = getServiceLocator().getTabla_anios_mesesService().consultar(tabla_anios_meses);
			
			if(tabla_anios_meses != null){
				double talla_mediana = tabla_anios_meses.getMedia();
				respuestaInt.setValor(Utilidades.calcularMedianaCurvas(talla, talla_mediana, tabla_anios_meses.getMenos_1_sd(), tabla_anios_meses.getMas_1_sd()));
				return respuestaInt;
			}
			throw new HealthmanagerException(" Esta no se encuentra en el rango establecido para la edad: " + meses.intValue());
        }else{
       	 return null;
        }
	}
	
	private void insertPuntosRespuesta(RespuestaInt respuestaInt){
		double meses = Funcion_getEdad.getMesesLong(fecha_nacimiento); 
		respuestaInt.setX(meses);
		respuestaInt.setY(talla);
		
		//log.info("Meses: " + meses);
		//log.info("Talla: " + talla); 
		
	}

	@Override
	public void mostrarGrafica(List<RespuestaInt> valores, ZKWindow zkWindow,
			ESexo sexo) {
		//log.info("Valores ha graficar: " + valores.size()); 
		String mapper_grafica = sexo == ESexo.FEMENINO ? "/images/graficas/f/talla_edad_5_19.mapper" : "/images/graficas/m/talla_edad_5_19.mapper"; 
		final GraficaMacro graficaMacro = new GraficaMacro();
//		graficaMacro.setShowBackLine(true);
		if(sexo == ESexo.FEMENINO){
			graficaMacro.setWidthI(889);
			graficaMacro.setHeigthI(555);
			graficaMacro.setX(808d);
			graficaMacro.setY(14d);
			graficaMacro.setFocoX(63d);
			graficaMacro.setFocoY(509d);
			graficaMacro.setGroups(2);
			graficaMacro.setMapper(mapper_grafica);
			graficaMacro.setLengthSectX(56);
			graficaMacro.setLengthSectY(19);
		}else{
			graficaMacro.setWidthI(897);
			graficaMacro.setHeigthI(563);
			graficaMacro.setX(809d);
			graficaMacro.setY(17d);
			graficaMacro.setFocoX(64d);
			graficaMacro.setFocoY(512d);
			graficaMacro.setGroups(2);
			graficaMacro.setMapper(mapper_grafica);
			graficaMacro.setLengthSectX(56);
			graficaMacro.setLengthSectY(22);
		}
		//log.info("x: " + graficaMacro.getX()); 
		//log.info("y: " + graficaMacro.getY());
		graficaMacro.getPainterMoldG().apllyGraficaSectores();
		graficaMacro.getPainterMoldG().showPoints(validarLista(valores, graficaMacro.getPainterMoldG()));
		mostrarVentanaParaUsuario(graficaMacro, zkWindow, "Grafica de Talla para edad de 5 a 19 Anios");
	}

	private List<RespuestaInt> validarLista(List<RespuestaInt> valores, PainterMoldG painterMoldG) { 
		List<RespuestaInt> respuestaIntsSend = new ArrayList<RespuestaInt>();
		for (RespuestaInt respuestaInt : valores) {
//			 double yTemp = respuestaInt.getY(); // por que el valor es cambiantes
			 RespuestaInt respuestaIntSend = new RespuestaInt();
			 respuestaIntSend.setX(respuestaInt.getX());
			 respuestaIntSend.setY(respuestaInt.getY());
			 respuestaIntSend.setValor(respuestaInt.getValor());
			 respuestaIntSend.setY(((respuestaInt.getY() - 90)));
			 respuestaIntSend.setX(((respuestaInt.getX() - 5) / 4));
			 respuestaIntsSend.add(respuestaIntSend);
		}
		return respuestaIntsSend;
	}
	
	
	@Override
	public boolean validar() {
		boolean validado = true;
		if(sexo == null)
			validado = false;
		
		if(fecha_nacimiento == null)
			validado = false;
		
		if(!validado){
			MensajesUtil.mensajeAlerta("Advertencia", "No pueden haber valores nulos.."); 
		}
		return validado;
	}

	@Override
	public void mostrarTabla(ESexo sexo, ZKWindow zkWindow) {
		TablaMacro tablaMacro = (TablaMacro) Executions.createComponents("/WEB-INF/macros/graficas/TablaGraficas.zul", zkWindow, new HashMap<String, Object>()); 
		String sexoL = ESexo.getSexo(sexo);
		String tipo_tabla = ETipoTabla.getTipo(ETipoTabla.HEIGHT);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sexo", sexoL);
		map.put("tipo", tipo_tabla);
		map.put("edad_inicial", 5);
		map.put("edad_final", 19);
		List<Tabla_anios_meses> tabla_talla_pesos = getServiceLocator().getTabla_anios_mesesService().listar(map);
		//log.info("Listado: " + tabla_talla_pesos.size()); 
		/* Mostrar tabla */
		tablaMacro.setSexo(sexo);
		tablaMacro.setTipo_tabla(Tipo_tabla.ANIOS_MESE);
		tablaMacro.setTitleValorReturn("ESTATURA");
		tablaMacro.mostrarTabla(tabla_talla_pesos);
		
		mostrarVentanaParaUsuario(tablaMacro, zkWindow, "Tabla de Talla para edad de 5 a 19 Anios");
	}

}
