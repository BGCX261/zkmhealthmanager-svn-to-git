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

public class InterceptorImcEdad0a5 extends InterceptorSelector implements
		IInterceptorGrafica {

	private Timestamp fecha_nacimiento;
	private double imc;
	private ESexo sexo;

	public Timestamp getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(Timestamp fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public double getImc() {
		return imc;
	}

	public void setImc(double imc) {
		this.imc = imc;
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
			String tipo_tabla = ETipoTabla.getTipo(ETipoTabla.BMI);
			
			Double meses = Funcion_getEdad.getMesesLong(fecha_nacimiento); 
			
			Tabla_anios_meses tabla_anios_meses = new Tabla_anios_meses();
			tabla_anios_meses.setMes(meses.intValue());  
			tabla_anios_meses.setSexo(sexoL); 
			tabla_anios_meses.setEdad_inicial(0);
			tabla_anios_meses.setEdad_final(5);
			tabla_anios_meses.setTipo(tipo_tabla);
			RespuestaInt respuestaInt = new RespuestaInt();
			insertPuntosRespuesta(respuestaInt);
			tabla_anios_meses = getServiceLocator().getTabla_anios_mesesService().consultar(tabla_anios_meses);
			
			if(tabla_anios_meses != null){
				double imc_mediana = tabla_anios_meses.getMedia();
				respuestaInt.setValor(Utilidades.calcularMedianaCurvas(imc, imc_mediana, tabla_anios_meses.getMenos_1_sd(), tabla_anios_meses.getMas_1_sd()));
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
		respuestaInt.setY(imc);
		
		//log.info("Meses: " + meses);
		//log.info("IMC: " + imc); 
		
	}

	@Override
	public void mostrarGrafica(List<RespuestaInt> valores, ZKWindow zkWindow,
			ESexo sexo) {
		//log.info("Valores ha graficar: " + valores.size()); 
		String mapper_grafica = sexo == ESexo.FEMENINO ? "/images/graficas/f/bmi_ninias_0_5.mapper" : "/images/graficas/m/bmi_edad_0_a_5.mapper"; 
		final GraficaMacro graficaMacro = new GraficaMacro();
//		graficaMacro.setShowBackLine(true);
		if(sexo == ESexo.FEMENINO){
			graficaMacro.setWidthI(845);
			graficaMacro.setHeigthI(515);
			graficaMacro.setX(763d);
			graficaMacro.setY(20d);
			graficaMacro.setFocoX(63d);
			graficaMacro.setFocoY(467d);
			graficaMacro.setGroups(6);
		}else{
			graficaMacro.setWidthI(873);
			graficaMacro.setHeigthI(531);
			graficaMacro.setX(800d);
			graficaMacro.setY(10d);
			graficaMacro.setFocoX(61d);
			graficaMacro.setFocoY(483d);
			graficaMacro.setGroups(14);
		}
		graficaMacro.setMapper(mapper_grafica);
		graficaMacro.setLengthSectX(60);
		graficaMacro.setLengthSectY(68);
		
		graficaMacro.getPainterMoldG().apllyGraficaSectores();
		graficaMacro.getPainterMoldG().showPoints(validarLista(valores, graficaMacro.getPainterMoldG()));
		mostrarVentanaParaUsuario(graficaMacro, zkWindow, "Grafica de IMC para edad de 0 a 5 Anios");
	}

	private List<RespuestaInt> validarLista(List<RespuestaInt> valores, PainterMoldG painterMoldG) { 
		List<RespuestaInt> respuestaIntsSend = new ArrayList<RespuestaInt>();
		for (RespuestaInt respuestaInt : valores) {
			 RespuestaInt respuestaIntSend = new RespuestaInt();
			 respuestaIntSend.setValor(respuestaInt.getValor());
			 respuestaIntSend.setY(((respuestaInt.getY() - 9) * 5) - 1);
			 respuestaIntSend.setX(((respuestaInt.getX())));
			 respuestaIntsSend.add(respuestaIntSend);
			 //log.info("y ultimo: " + respuestaIntSend.getY()); 
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
		String tipo_tabla = ETipoTabla.getTipo(ETipoTabla.BMI);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sexo", sexoL);
		map.put("tipo", tipo_tabla);
		map.put("edad_inicial", 0);
		map.put("edad_final", 5);
		List<Tabla_anios_meses> tabla_anios_meses = getServiceLocator().getTabla_anios_mesesService().listar(map);
		//log.info("Listado: " + tabla_anios_meses.size()); 
		/* Mostrar tabla */
		tablaMacro.setSexo(sexo);
		tablaMacro.setTipo_tabla(Tipo_tabla.ANIOS_MESE);
		tablaMacro.setTitleValorReturn("IMC");  
		tablaMacro.mostrarTabla(tabla_anios_meses);
		
		mostrarVentanaParaUsuario(tablaMacro, zkWindow, "Tabla de IMC para edad de 0 a 5 Anios");
	}
}
