/*
 * Configuracion_pagares.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("configuracion_pagares")
public class Configuracion_pagares implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String anio;
	private  double valor_minimo_pagare;
	private  double valor_minimo_cuotas;
	private  Integer numero_maximo_cuotas;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;

	/*** Constructor Por Defecto ***/
	public Configuracion_pagares(){}


	/*** Sobre carga de Constructor ***/
	public Configuracion_pagares(
		String codigo_empresa,
		String codigo_sucursal,
		String anio,
		double valor_minimo_pagare,
		double valor_minimo_cuotas,
		Integer numero_maximo_cuotas,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.anio = anio;
		this.valor_minimo_pagare = valor_minimo_pagare;
		this.valor_minimo_cuotas = valor_minimo_cuotas;
		this.numero_maximo_cuotas = numero_maximo_cuotas;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
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
	public void setAnio(String anio){
		this.anio=anio;
	}
	public void setValor_minimo_pagare(double valor_minimo_pagare){
		this.valor_minimo_pagare=valor_minimo_pagare;
	}
	public void setValor_minimo_cuotas(double valor_minimo_cuotas){
		this.valor_minimo_cuotas=valor_minimo_cuotas;
	}
	public void setNumero_maximo_cuotas(Integer numero_maximo_cuotas){
		this.numero_maximo_cuotas=numero_maximo_cuotas;
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
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getAnio(){
		return anio;
	}
	public double getValor_minimo_pagare(){
		return valor_minimo_pagare;
	}
	public double getValor_minimo_cuotas(){
		return valor_minimo_cuotas;
	}
	public Integer getNumero_maximo_cuotas(){
		return numero_maximo_cuotas;
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
}