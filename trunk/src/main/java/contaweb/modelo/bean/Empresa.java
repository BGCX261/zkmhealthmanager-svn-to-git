/*
 * Empresa.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import org.apache.ibatis.type.Alias;

@Alias("empresaCont")
public class Empresa implements Serializable{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String tipo_identificacion;
	private  String nro_identificacion;
	private  String nombre_empresa;
	private  String identificador;
	private  String direccion;
	private  String telefonos;
	private  String fax;
	private  String codigo_dpto;
	private  String codigo_municipio;
	private  String trabaja_inventario;
	private  String saldos_negativos;
	private  String maneja_contabilidad;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String email;
	private  String gerente;
	private  String tipo_contribuyente;
	private  String dv;
	private  boolean trabaja_hm;
	private  String modelo_factura_venta;
	private  double porcentaje_comision;
	private  boolean trabaja_recargas;
	private  boolean iva_nodescontable;
	private boolean trabaja_sw;
	private boolean afiliados_caja;
	private double interes_pagares;

	/*** Constructor Por Defecto ***/
	public Empresa(){}


	/*** Sobre carga de Constructor ***/
	public Empresa(
		String codigo_empresa,
		String tipo_identificacion,
		String nro_identificacion,
		String nombre_empresa,
		String identificador,
		String direccion,
		String telefonos,
		String fax,
		String codigo_dpto,
		String codigo_municipio,
		String trabaja_inventario,
		String saldos_negativos,
		String maneja_contabilidad,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String email,
		String gerente,
		String tipo_contribuyente,
		String dv,
		boolean trabaja_hm,
		String modelo_factura_venta,
		double porcentaje_comision,
		boolean trabaja_recargas,
		boolean iva_nodescontable){
		this.codigo_empresa = codigo_empresa;
		this.tipo_identificacion = tipo_identificacion;
		this.nro_identificacion = nro_identificacion;
		this.nombre_empresa = nombre_empresa;
		this.identificador = identificador;
		this.direccion = direccion;
		this.telefonos = telefonos;
		this.fax = fax;
		this.codigo_dpto = codigo_dpto;
		this.codigo_municipio = codigo_municipio;
		this.trabaja_inventario = trabaja_inventario;
		this.saldos_negativos = saldos_negativos;
		this.maneja_contabilidad = maneja_contabilidad;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.email = email;
		this.gerente = gerente;
		this.tipo_contribuyente = tipo_contribuyente;
		this.dv = dv;
		this.trabaja_hm = trabaja_hm;
		this.modelo_factura_venta = modelo_factura_venta;
		this.porcentaje_comision = porcentaje_comision;
		this.trabaja_recargas = trabaja_recargas;
		this.iva_nodescontable = iva_nodescontable;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setCodigo_empresa(String codigo_empresa){
		this.codigo_empresa=codigo_empresa;
	}
	public void setTipo_identificacion(String tipo_identificacion){
		this.tipo_identificacion=tipo_identificacion;
	}
	public void setNro_identificacion(String nro_identificacion){
		this.nro_identificacion=nro_identificacion;
	}
	public void setNombre_empresa(String nombre_empresa){
		this.nombre_empresa=nombre_empresa;
	}
	public void setIdentificador(String identificador){
		this.identificador=identificador;
	}
	public void setDireccion(String direccion){
		this.direccion=direccion;
	}
	public void setTelefonos(String telefonos){
		this.telefonos=telefonos;
	}
	public void setFax(String fax){
		this.fax=fax;
	}
	public void setCodigo_dpto(String codigo_dpto){
		this.codigo_dpto=codigo_dpto;
	}
	public void setCodigo_municipio(String codigo_municipio){
		this.codigo_municipio=codigo_municipio;
	}
	public void setTrabaja_inventario(String trabaja_inventario){
		this.trabaja_inventario=trabaja_inventario;
	}
	public void setSaldos_negativos(String saldos_negativos){
		this.saldos_negativos=saldos_negativos;
	}
	public void setManeja_contabilidad(String maneja_contabilidad){
		this.maneja_contabilidad=maneja_contabilidad;
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
	public void setEmail(String email){
		this.email=email;
	}
	public void setGerente(String gerente){
		this.gerente=gerente;
	}
	public void setTipo_contribuyente(String tipo_contribuyente){
		this.tipo_contribuyente=tipo_contribuyente;
	}
	public void setDv(String dv){
		this.dv=dv;
	}
	public void setTrabaja_hm(boolean trabaja_hm){
		this.trabaja_hm=trabaja_hm;
	}
	public void setModelo_factura_venta(String modelo_factura_venta){
		this.modelo_factura_venta=modelo_factura_venta;
	}
	public void setPorcentaje_comision(double porcentaje_comision){
		this.porcentaje_comision=porcentaje_comision;
	}
	public void setTrabaja_recargas(boolean trabaja_recargas){
		this.trabaja_recargas=trabaja_recargas;
	}
	public void setIva_nodescontable(boolean iva_nodescontable){
		this.iva_nodescontable=iva_nodescontable;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getTipo_identificacion(){
		return tipo_identificacion;
	}
	public String getNro_identificacion(){
		return nro_identificacion;
	}
	public String getNombre_empresa(){
		return nombre_empresa;
	}
	public String getIdentificador(){
		return identificador;
	}
	public String getDireccion(){
		return direccion;
	}
	public String getTelefonos(){
		return telefonos;
	}
	public String getFax(){
		return fax;
	}
	public String getCodigo_dpto(){
		return codigo_dpto;
	}
	public String getCodigo_municipio(){
		return codigo_municipio;
	}
	public String getTrabaja_inventario(){
		return trabaja_inventario;
	}
	public String getSaldos_negativos(){
		return saldos_negativos;
	}
	public String getManeja_contabilidad(){
		return maneja_contabilidad;
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
	public String getEmail(){
		return email;
	}
	public String getGerente(){
		return gerente;
	}
	public String getTipo_contribuyente(){
		return tipo_contribuyente;
	}
	public String getDv(){
		return dv;
	}
	public boolean getTrabaja_hm(){
		return trabaja_hm;
	}
	public String getModelo_factura_venta(){
		return modelo_factura_venta;
	}
	public double getPorcentaje_comision(){
		return porcentaje_comision;
	}
	public boolean getTrabaja_recargas(){
		return trabaja_recargas;
	}
	public boolean getIva_nodescontable(){
		return iva_nodescontable;
	}


	public boolean getTrabaja_sw() {
		return trabaja_sw;
	}


	public void setTrabaja_sw(boolean trabaja_sw) {
		this.trabaja_sw = trabaja_sw;
	}


	public boolean getAfiliados_caja() {
		return afiliados_caja;
	}


	public void setAfiliados_caja(boolean afiliados_caja) {
		this.afiliados_caja = afiliados_caja;
	}


	public double getInteres_pagares() {
		return interes_pagares;
	}


	public void setInteres_pagares(double interes_pagares) {
		this.interes_pagares = interes_pagares;
	}
}
