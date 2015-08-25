/*
 * Detalle_pagare.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("detalle_pagare")
public class Detalle_pagare implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_comprobante;
	private  String codigo_documento;
	private  String consecutivo;
	private  String codigo_servicio;
	private  String concepto;
	private  double cantidad;
	private  double valor_unitario;
	private  double valor_total;
	private  double copago;

	/*** Constructor Por Defecto ***/
	public Detalle_pagare(){}


	/*** Sobre carga de Constructor ***/
	public Detalle_pagare(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_comprobante,
		String codigo_documento,
		String consecutivo,
		String codigo_servicio,
		String concepto,
		double cantidad,
		double valor_unitario,
		double valor_total,
		double copago){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
		this.consecutivo = consecutivo;
		this.codigo_servicio = codigo_servicio;
		this.concepto = concepto;
		this.cantidad = cantidad;
		this.valor_unitario = valor_unitario;
		this.valor_total = valor_total;
		this.copago = copago;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setCodigo_empresa(String codigo_empresa){
		this.codigo_empresa=codigo_empresa;
	}
	public void setCodigo_sucursal(String codigo_sucursal){
		this.codigo_sucursal=codigo_sucursal;
	}
	public void setCodigo_comprobante(String codigo_comprobante){
		this.codigo_comprobante=codigo_comprobante;
	}
	public void setCodigo_documento(String codigo_documento){
		this.codigo_documento=codigo_documento;
	}
	public void setConsecutivo(String consecutivo){
		this.consecutivo=consecutivo;
	}
	public void setCodigo_servicio(String codigo_servicio){
		this.codigo_servicio=codigo_servicio;
	}
	public void setConcepto(String concepto){
		this.concepto=concepto;
	}
	public void setCantidad(double cantidad){
		this.cantidad=cantidad;
	}
	public void setValor_unitario(double valor_unitario){
		this.valor_unitario=valor_unitario;
	}
	public void setValor_total(double valor_total){
		this.valor_total=valor_total;
	}
	public void setCopago(double copago){
		this.copago=copago;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getCodigo_comprobante(){
		return codigo_comprobante;
	}
	public String getCodigo_documento(){
		return codigo_documento;
	}
	public String getConsecutivo(){
		return consecutivo;
	}
	public String getCodigo_servicio(){
		return codigo_servicio;
	}
	public String getConcepto(){
		return concepto;
	}
	public double getCantidad(){
		return cantidad;
	}
	public double getValor_unitario(){
		return valor_unitario;
	}
	public double getValor_total(){
		return valor_total;
	}
	public double getCopago(){
		return copago;
	}
}