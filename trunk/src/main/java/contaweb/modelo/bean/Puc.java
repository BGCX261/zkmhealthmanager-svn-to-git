/*
 * Puc.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("puc")
public class Puc{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_cuenta;
	private  String nombre;
	private  String cuenta_padre;
	private  String tipo_cuenta;
	private  String grupo_cuenta;
	private  String oculto;
	private  String inactivo;
	private  String maneja_terceros;
	private  String codigo_magnetico;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	
	//private Tipo_cuenta objTipo_cuenta;
	//private Grupo_cuenta objGrupo_cuenta;

	/*** Constructor Por Defecto ***/
	public Puc(){}


	/*** Sobre carga de Constructor ***/
	public Puc(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_cuenta,
		String nombre,
		String cuenta_padre,
		String tipo_cuenta,
		String grupo_cuenta,
		String oculto,
		String inactivo,
		String maneja_terceros,
		String codigo_magnetico,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_cuenta = codigo_cuenta;
		this.nombre = nombre;
		this.cuenta_padre = cuenta_padre;
		this.tipo_cuenta = tipo_cuenta;
		this.grupo_cuenta = grupo_cuenta;
		this.oculto = oculto;
		this.inactivo = inactivo;
		this.maneja_terceros = maneja_terceros;
		this.codigo_magnetico = codigo_magnetico;
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
	public void setCodigo_cuenta(String codigo_cuenta){
		this.codigo_cuenta=codigo_cuenta;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setCuenta_padre(String cuenta_padre){
		this.cuenta_padre=cuenta_padre;
	}
	public void setTipo_cuenta(String tipo_cuenta){
		this.tipo_cuenta=tipo_cuenta;
	}
	public void setGrupo_cuenta(String grupo_cuenta){
		this.grupo_cuenta=grupo_cuenta;
	}
	public void setOculto(String oculto){
		this.oculto=oculto;
	}
	public void setInactivo(String inactivo){
		this.inactivo=inactivo;
	}
	public void setManeja_terceros(String maneja_terceros){
		this.maneja_terceros=maneja_terceros;
	}
	public void setCodigo_magnetico(String codigo_magnetico){
		this.codigo_magnetico=codigo_magnetico;
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
	public String getCodigo_cuenta(){
		return codigo_cuenta;
	}
	public String getNombre(){
		return nombre;
	}
	public String getCuenta_padre(){
		return cuenta_padre;
	}
	public String getTipo_cuenta(){
		return tipo_cuenta;
	}
	public String getGrupo_cuenta(){
		return grupo_cuenta;
	}
	public String getOculto(){
		return oculto;
	}
	public String getInactivo(){
		return inactivo;
	}
	public String getManeja_terceros(){
		return maneja_terceros;
	}
	public String getCodigo_magnetico(){
		return codigo_magnetico;
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

//
//	public void setObjTipo_cuenta(Tipo_cuenta objTipo_cuenta) {
//		this.objTipo_cuenta = objTipo_cuenta;
//	}
//
//
//	public Tipo_cuenta getObjTipo_cuenta() {
//		return objTipo_cuenta;
//	}
//
//
//	public void setObjGrupo_cuenta(Grupo_cuenta objGrupo_cuenta) {
//		this.objGrupo_cuenta = objGrupo_cuenta;
//	}
//
//
//	public Grupo_cuenta getObjGrupo_cuenta() {
//		return objGrupo_cuenta;
//	}
}
