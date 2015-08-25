/*
 * Clientes_maquinas.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package imagen_diagnostica.modelo.bean;

import java.io.Serializable;
import java.sql.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("clientes_maquinas")
public class Clientes_maquinas implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  Integer id;
	private  String direccion;
	private  String nombre_cliente;
	private  Timestamp creacion_date;
	private  String creacion_user;

	/*** Constructor Por Defecto ***/
	public Clientes_maquinas(){}


	/*** Sobre carga de Constructor ***/
	public Clientes_maquinas(
		String codigo_empresa,
		String codigo_sucursal,
		Integer id,
		String direccion,
		String nombre_cliente,
		Timestamp creacion_date,
		String creacion_user){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.id = id;
		this.direccion = direccion;
		this.nombre_cliente = nombre_cliente;
		this.creacion_date = creacion_date;
		this.creacion_user = creacion_user;
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
	public void setId(Integer id){
		this.id=id;
	}
	public void setDireccion(String direccion){
		this.direccion=direccion;
	}
	public void setNombre_cliente(String nombre_cliente){
		this.nombre_cliente=nombre_cliente;
	}
	public void setCreacion_date(Timestamp creacion_date){
		this.creacion_date=creacion_date;
	}
	public void setCreacion_user(String creacion_user){
		this.creacion_user=creacion_user;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public Integer getId(){
		return id;
	}
	public String getDireccion(){
		return direccion;
	}
	public String getNombre_cliente(){
		return nombre_cliente;
	}
	public Timestamp getCreacion_date(){
		return creacion_date;
	}
	public String getCreacion_user(){
		return creacion_user;
	}
}