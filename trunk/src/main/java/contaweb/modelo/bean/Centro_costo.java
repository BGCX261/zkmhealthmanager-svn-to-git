/*
 * Centro_costo.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("centro_costo")
public class Centro_costo{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo;
	private  String nombre;
	private  String oculto;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String codigo_padre;
	
	private String codigo_centro;
	
	private Centro_costo centro_costoPadre;

	/*** Constructor Por Defecto ***/
	public Centro_costo(){}


	/*** Sobre carga de Constructor ***/
	public Centro_costo(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo,
		String nombre,
		String oculto,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String codigo_padre){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo = codigo;
		this.nombre = nombre;
		this.oculto = oculto;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.codigo_padre = codigo_padre;
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
	public void setOculto(String oculto){
		this.oculto=oculto;
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
	public void setCodigo_padre(String codigo_padre){
		this.codigo_padre=codigo_padre;
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
	public String getOculto(){
		return oculto;
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
	public String getCodigo_padre(){
		return codigo_padre;
	}


	public void setCentro_costoPadre(Centro_costo centro_costoPadre) {
		this.centro_costoPadre = centro_costoPadre;
	}


	public Centro_costo getCentro_costoPadre() {
		return centro_costoPadre;
	}


	public String getCodigo_centro() {
		return codigo_centro;
	}


	public void setCodigo_centro(String codigo_centro) {
		this.codigo_centro = codigo_centro;
	}


}
