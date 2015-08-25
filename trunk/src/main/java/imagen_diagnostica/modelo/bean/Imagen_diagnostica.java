/*
 * Imagen_diagnostica.java
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

@Alias("imagen_diagnostica")
public class Imagen_diagnostica implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String nro_identificacion;
	private  Timestamp fecha_realizacion;
	private  String lugar;
	private  Integer id_cliente;
	private  Integer id;
	private  String direccion_archivo;
	private  Timestamp creacion_date;
	
	private String descripcion;

	/*** Constructor Por Defecto ***/
	public Imagen_diagnostica(){}


	/*** Sobre carga de Constructor ***/
	public Imagen_diagnostica(
		String codigo_empresa,
		String codigo_sucursal,
		String nro_identificacion,
		Timestamp fecha_realizacion,
		String lugar,
		Integer id_cliente,
		Integer id,
		String direccion_archivo,
		Timestamp creacion_date){
		this.codigo_empresa = codigo_empresa;
		this.setCodigo_sucursal(codigo_sucursal);
		this.nro_identificacion = nro_identificacion;
		this.fecha_realizacion = fecha_realizacion;
		this.lugar = lugar;
		this.id_cliente = id_cliente;
		this.id = id;
		this.direccion_archivo = direccion_archivo;
		this.creacion_date = creacion_date;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setCodigo_empresa(String codigo_empresa){
		this.codigo_empresa=codigo_empresa;
	}
	
	public void setNro_identificacion(String nro_identificacion){
		this.nro_identificacion=nro_identificacion;
	}
	public void setFecha_realizacion(Timestamp fecha_realizacion){
		this.fecha_realizacion=fecha_realizacion;
	}
	public void setLugar(String lugar){
		this.lugar=lugar;
	}
	public void setId_cliente(Integer id_cliente){
		this.id_cliente=id_cliente;
	}
	public void setId(Integer id){
		this.id=id;
	}
	public void setDireccion_archivo(String direccion_archivo){
		this.direccion_archivo=direccion_archivo;
	}
	public void setCreacion_date(Timestamp creacion_date){
		this.creacion_date=creacion_date;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	
	public String getNro_identificacion(){
		return nro_identificacion;
	}
	public Timestamp getFecha_realizacion(){
		return fecha_realizacion;
	}
	public String getLugar(){
		return lugar;
	}
	public Integer getId_cliente(){
		return id_cliente;
	}
	public Integer getId(){
		return id;
	}
	public String getDireccion_archivo(){
		return direccion_archivo;
	}
	public Timestamp getCreacion_date(){
		return creacion_date;
	}


	public String getCodigo_sucursal() {
		return codigo_sucursal;
	}


	public void setCodigo_sucursal(String codigo_sucursal) {
		this.codigo_sucursal = codigo_sucursal;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}