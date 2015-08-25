/*
 * Pagare.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("pagare")
public class Pagare implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_comprobante;
	private  String codigo_documento;
	private  Timestamp fecha;
	private  String concepto;
	private  String anio;
	private  String mes;
	private  String nro_ingreso;
	private  String codigo_administradora;
	private  String codigo_tercero;
	private  String id_plan;
	private  String codigo_cita;
	private  String codigo_anexo4;
	private  String codigo_receta;
	private  String codigo_anexo9;
	private  String codigo_orden;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  double valor_pagare;
	private  Integer nro_cuota;
	private  Timestamp primer_vencimiento;
	private String tipo_recibo;
	
	private String copago_autorizaciones;
	private String copago_medicamentos;
	
	private double interes;
	
	private List<Detalle_pagare> lista_detalle;

	/*** Constructor Por Defecto ***/
	public Pagare(){
        codigo_cita = "";
        setCopago_autorizaciones("N");
        setCopago_medicamentos("N");
        codigo_anexo4 = "";
        codigo_receta = "";
        codigo_anexo9 = "";
        codigo_orden = "";
	}
	
	public Pagare(boolean valor_inicialozado){
		if(valor_inicialozado){
			codigo_cita = "";
	        setCopago_autorizaciones("N");
	        setCopago_medicamentos("N");
	        codigo_anexo4 = "";
	        codigo_receta = "";
	        codigo_anexo9 = "";
	        codigo_orden = "";
		}
		
	}


	/*** Sobre carga de Constructor ***/
	public Pagare(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_comprobante,
		String codigo_documento,
		Timestamp fecha,
		String concepto,
		String anio,
		String mes,
		String nro_ingreso,
		String codigo_administradora,
		String codigo_tercero,
		String id_plan,
		String codigo_cita,
		String codigo_anexo4,
		String codigo_receta,
		String codigo_anexo9,
		String codigo_orden,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		double valor_pagare,
		Integer nro_cuota,
		Timestamp primer_vencimiento){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
		this.fecha = fecha;
		this.concepto = concepto;
		this.anio = anio;
		this.mes = mes;
		this.nro_ingreso = nro_ingreso;
		this.codigo_administradora = codigo_administradora;
		this.codigo_tercero = codigo_tercero;
		this.id_plan = id_plan;
		this.codigo_cita = codigo_cita;
		this.codigo_anexo4 = codigo_anexo4;
		this.codigo_receta = codigo_receta;
		this.codigo_anexo9 = codigo_anexo9;
		this.codigo_orden = codigo_orden;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.valor_pagare = valor_pagare;
		this.nro_cuota = nro_cuota;
		this.primer_vencimiento = primer_vencimiento;
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
	public void setFecha(Timestamp fecha){
		this.fecha=fecha;
	}
	public void setConcepto(String concepto){
		this.concepto=concepto;
	}
	public void setAnio(String anio){
		this.anio=anio;
	}
	public void setMes(String mes){
		this.mes=mes;
	}
	public void setNro_ingreso(String nro_ingreso){
		this.nro_ingreso=nro_ingreso;
	}
	public void setCodigo_administradora(String codigo_administradora){
		this.codigo_administradora=codigo_administradora;
	}
	public void setCodigo_tercero(String codigo_tercero){
		this.codigo_tercero=codigo_tercero;
	}
	public void setId_plan(String id_plan){
		this.id_plan=id_plan;
	}
	public void setCodigo_cita(String codigo_cita){
		this.codigo_cita=codigo_cita;
	}
	public void setCodigo_anexo4(String codigo_anexo4){
		this.codigo_anexo4=codigo_anexo4;
	}
	public void setCodigo_receta(String codigo_receta){
		this.codigo_receta=codigo_receta;
	}
	public void setCodigo_anexo9(String codigo_anexo9){
		this.codigo_anexo9=codigo_anexo9;
	}
	public void setCodigo_orden(String codigo_orden){
		this.codigo_orden=codigo_orden;
	}
	public void setCreacion_date(Timestamp creacion_date){
		this.creacion_date=creacion_date;
	}
	public void setUltimo_update(Timestamp ultimo_update){
		this.ultimo_update=ultimo_update;
	}
	public void setDelete_date(Timestamp delete_date){
		this.delete_date=delete_date;
	}
	public void setCreacion_user(String creacion_user){
		this.creacion_user=creacion_user;
	}
	public void setUltimo_user(String ultimo_user){
		this.ultimo_user=ultimo_user;
	}
	public void setDelete_user(String delete_user){
		this.delete_user=delete_user;
	}
	public void setValor_pagare(double valor_pagare){
		this.valor_pagare=valor_pagare;
	}
	public void setNro_cuota(Integer nro_cuota){
		this.nro_cuota=nro_cuota;
	}
	public void setPrimer_vencimiento(Timestamp primer_vencimiento){
		this.primer_vencimiento=primer_vencimiento;
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
	public Timestamp getFecha(){
		return fecha;
	}
	public String getConcepto(){
		return concepto;
	}
	public String getAnio(){
		return anio;
	}
	public String getMes(){
		return mes;
	}
	public String getNro_ingreso(){
		return nro_ingreso;
	}
	public String getCodigo_administradora(){
		return codigo_administradora;
	}
	public String getCodigo_tercero(){
		return codigo_tercero;
	}
	public String getId_plan(){
		return id_plan;
	}
	public String getCodigo_cita(){
		return codigo_cita;
	}
	public String getCodigo_anexo4(){
		return codigo_anexo4;
	}
	public String getCodigo_receta(){
		return codigo_receta;
	}
	public String getCodigo_anexo9(){
		return codigo_anexo9;
	}
	public String getCodigo_orden(){
		return codigo_orden;
	}
	public Timestamp getCreacion_date(){
		return creacion_date;
	}
	public Timestamp getUltimo_update(){
		return ultimo_update;
	}
	public Timestamp getDelete_date(){
		return delete_date;
	}
	public String getCreacion_user(){
		return creacion_user;
	}
	public String getUltimo_user(){
		return ultimo_user;
	}
	public String getDelete_user(){
		return delete_user;
	}
	public double getValor_pagare(){
		return valor_pagare;
	}
	public Integer getNro_cuota(){
		return nro_cuota;
	}
	public Timestamp getPrimer_vencimiento(){
		return primer_vencimiento;
	}


	public String getTipo_recibo() {
		return tipo_recibo;
	}


	public void setTipo_recibo(String tipo_recibo) {
		this.tipo_recibo = tipo_recibo;
	}


	public String getCopago_autorizaciones() {
		return copago_autorizaciones;
	}


	public void setCopago_autorizaciones(String copago_autorizaciones) {
		this.copago_autorizaciones = copago_autorizaciones;
	}


	public String getCopago_medicamentos() {
		return copago_medicamentos;
	}


	public void setCopago_medicamentos(String copago_medicamentos) {
		this.copago_medicamentos = copago_medicamentos;
	}


	public double getInteres() {
		return interes;
	}

	public void setInteres(double interes) {
		this.interes = interes;
	}

	public List<Detalle_pagare> getLista_detalle() {
		return lista_detalle;
	}


	public void setLista_detalle(List<Detalle_pagare> lista_detalle) {
		this.lista_detalle = lista_detalle;
	}
}