package com.framework.macros.graficas.interceptor.impl;

import healthmanager.controller.ZKWindow;
import healthmanager.exception.HealthmanagerException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;

import tablas_crecimiento_desarrollo.modelo.bean.Tabla_talla_peso;

import com.framework.macros.graficas.GraficaMacro;
import com.framework.macros.graficas.TablaMacro;
import com.framework.macros.graficas.TablaMacro.Tipo_tabla;
import com.framework.macros.graficas.interceptor.IInterceptorGrafica;
import com.framework.macros.graficas.interceptor.InterceptorSelector;
import com.framework.macros.graficas.moldG.PainterMoldG;
import com.framework.macros.graficas.respuesta.RespuestaInt;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class InterceptorPesoTalla2a5Anios extends InterceptorSelector implements
		IInterceptorGrafica {

	private double peso;
	private ESexo sexo;
	private double talla;
	
	

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public ESexo getSexo() {
		return sexo;
	}

	public void setSexo(ESexo sexo) {
		this.sexo = sexo;
	}

	public double getTalla() {
		return talla;
	}

	public void setTalla(double talla) {
		this.talla = talla;
	}

	
	@Override
	public RespuestaInt getResultado() {
		if(validar()){
			String sexoL = ESexo.getSexo(sexo);
			String tipo_tabla = ETipoTabla.getTipo(ETipoTabla.WEIGHT);
			
			Tabla_talla_peso tabla_talla_peso = new Tabla_talla_peso();
			tabla_talla_peso.setTalla_peso(validateAproximacion0Apunto5(talla)); 
			tabla_talla_peso.setSexo(sexoL); 
			tabla_talla_peso.setEdad_inicial(2);
			tabla_talla_peso.setEdad_final(5);
			tabla_talla_peso.setTipo(tipo_tabla);
			RespuestaInt respuestaInt = new RespuestaInt();
			insertPuntosRespuesta(respuestaInt);
			tabla_talla_peso = getServiceLocator().getTalla_pesoService().consultar(tabla_talla_peso);
			if(tabla_talla_peso != null){
				double peso_mediana = tabla_talla_peso.getMedia();
				respuestaInt.setValor(Utilidades.calcularMedianaCurvas(peso, peso_mediana, tabla_talla_peso.getMenos_1_sd(), tabla_talla_peso.getMas_1_sd()));
				return respuestaInt;
			}
			throw new HealthmanagerException(" Esta no se encuentra en el rango establecido: " + talla); 
		}else{
			MensajesUtil.mensajeAlerta("Advertencia", "No pueden haber valores nulos.."); 
			return null;
		}
	}
	
	private void insertPuntosRespuesta(RespuestaInt respuestaInt){
		respuestaInt.setX(talla);
		respuestaInt.setY(peso);
	}

	@Override
	public void mostrarGrafica(List<RespuestaInt> valores, ZKWindow zkWindow,
			ESexo sexo) {
		//log.info("Valores ha graficar: " + valores.size()); 
		String mapper_grafica = sexo == ESexo.FEMENINO ? "/images/graficas/f/peso_estatura_2_5.mapper" : "/images/graficas/m/peso_estatura_2_5.mapper"; 
		final GraficaMacro graficaMacro = new GraficaMacro();
//		graficaMacro.setShowBackLine(true);
		if(sexo == ESexo.FEMENINO){
			graficaMacro.setWidthI(869);
			graficaMacro.setHeigthI(533);
			graficaMacro.setX(798d);
			graficaMacro.setY(11d);
			graficaMacro.setFocoX(58d);
			graficaMacro.setFocoY(485d);
			graficaMacro.setGroups(1);
			graficaMacro.setMapper(mapper_grafica);
			graficaMacro.setLengthSectX(55);
			graficaMacro.setLengthSectY(55);
		}else{
			graficaMacro.setWidthI(871);
			graficaMacro.setHeigthI(537);
			graficaMacro.setX(797d);
			graficaMacro.setY(15d);
			graficaMacro.setFocoX(56d);
			graficaMacro.setFocoY(488d);
			graficaMacro.setGroups(1);
			graficaMacro.setMapper(mapper_grafica);
			graficaMacro.setLengthSectX(55);
			graficaMacro.setLengthSectY(52);
		}
		
		graficaMacro.getPainterMoldG().apllyGraficaSectores();
		graficaMacro.getPainterMoldG().showPoints(validarLista(valores, graficaMacro.getPainterMoldG()));
		mostrarVentanaParaUsuario(graficaMacro, zkWindow, "Grafica de Peso para estatura de 2 a 5 Anios");
	}
	
	private List<RespuestaInt> validarLista(List<RespuestaInt> valores, PainterMoldG painterMoldG) { 
		List<RespuestaInt> respuestaIntsSend = new ArrayList<RespuestaInt>();
		for (RespuestaInt respuestaInt : valores) {
			 RespuestaInt respuestaIntSend = new RespuestaInt();
			 respuestaIntSend.setX(respuestaInt.getX());
			 respuestaIntSend.setY(respuestaInt.getY());
			 respuestaIntSend.setValor(respuestaInt.getValor());
			 if(sexo == ESexo.FEMENINO){
				 respuestaIntSend.setY(((respuestaInt.getY() - 4) * 2) - 1);
			 }else{
				 respuestaIntSend.setY(((respuestaInt.getY() - 5) * 2));
			 }
			 respuestaIntSend.setX(((respuestaInt.getX() - 65)));
			 //log.info("Eje de las X: " + respuestaIntSend.getX());
			 //log.info("Eje de las Y: " + respuestaIntSend.getY()); 
			 respuestaIntsSend.add(respuestaIntSend);
		}
		return respuestaIntsSend;
	}

	@Override
	public boolean validar() {
		boolean validado = true;
		if(sexo == null)
			validado = false;
		
		return validado;
	}

	@Override
	public void mostrarTabla(ESexo sexo, ZKWindow zkWindow) {
		TablaMacro tablaMacro = (TablaMacro) Executions.createComponents("/WEB-INF/macros/graficas/TablaGraficas.zul", zkWindow, new HashMap<String, Object>()); 
		String sexoL = ESexo.getSexo(sexo);
		String tipo_tabla = ETipoTabla.getTipo(ETipoTabla.WEIGHT);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sexo", sexoL);
		map.put("tipo", tipo_tabla);
		map.put("edad_inicial", 2);
		map.put("edad_final", 5);
		List<Tabla_talla_peso> tabla_talla_pesos = getServiceLocator().getTalla_pesoService().listar(map);
		//log.info("Listado: " + tabla_talla_pesos.size()); 
		/* Mostrar tabla */
		tablaMacro.setSexo(sexo);
		tablaMacro.setTipo_tabla(Tipo_tabla.NUMERIC_LENGTH);
		tablaMacro.setTitleValorReturn("ESTATURA");
		tablaMacro.mostrarTabla(tabla_talla_pesos);
		
		mostrarVentanaParaUsuario(tablaMacro, zkWindow, "Tabla de Peso para estatura de 2 a 5 Anios");
	}

}
