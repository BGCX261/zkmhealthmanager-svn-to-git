/*
 * Bodega.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Luis Miguel Hernandez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("bodega")
public class Bodega{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo;
	private  String nombre;
	private  String medidas;
	private  String ubicacion;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  boolean remision;
	private  boolean flete;
	
	private  Boolean principal;
	private  String codigo_centro;
	
	private  String cuenta_inventario;
	private  String cuenta_ingreso;
	private  String cuenta_costo;

	/*** Constructor Por Defecto ***/
	public Bodega(){
		codigo_centro = "";
		principal = true;
		cuenta_inventario = "";
		cuenta_ingreso = "";
		cuenta_costo = "";
	}


	/*** Sobre carga de Constructor ***/
	public Bodega(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo,
		String nombre,
		String medidas,
		String ubicacion,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		boolean remision,
		boolean flete){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo = codigo;
		this.nombre = nombre;
		this.medidas = medidas;
		this.ubicacion = ubicacion;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.remision = remision;
		this.flete = flete;
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
	public void setCodigo(String codigo){
		this.codigo=codigo;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setMedidas(String medidas){
		this.medidas=medidas;
	}
	public void setUbicacion(String ubicacion){
		this.ubicacion=ubicacion;
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
	public void setRemision(boolean remision){
		this.remision=remision;
	}
	public void setFlete(boolean flete){
		this.flete=flete;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getCodigo(){
		return codigo;
	}
	public String getNombre(){
		return nombre;
	}
	public String getMedidas(){
		return medidas;
	}
	public String getUbicacion(){
		return ubicacion;
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
	public boolean getRemision(){
		return remision;
	}
	public boolean getFlete(){
		return flete;
	}


	public String getCodigo_centro() {
		return codigo_centro;
	}


	public void setCodigo_centro(String codigo_centro) {
		this.codigo_centro = codigo_centro;
	}


	public Boolean getPrincipal() {
		return principal;
	}


	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}


	public String getCuenta_inventario() {
		return cuenta_inventario;
	}


	public void setCuenta_inventario(String cuenta_inventario) {
		this.cuenta_inventario = cuenta_inventario;
	}


	public String getCuenta_ingreso() {
		return cuenta_ingreso;
	}


	public void setCuenta_ingreso(String cuenta_ingreso) {
		this.cuenta_ingreso = cuenta_ingreso;
	}


	public String getCuenta_costo() {
		return cuenta_costo;
	}


	public void setCuenta_costo(String cuenta_costo) {
		this.cuenta_costo = cuenta_costo;
	}
}
