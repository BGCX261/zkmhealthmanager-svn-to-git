/*
 * Laboratorios_resultados.java
 * 
 * Generado Automaticamente .
 * Ing. 	Luis Miguel Hernández Pérez
 */ 
package imagen_diagnostica.modelo.bean;

import java.io.Serializable;
import java.sql.*;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("laboratorios_resultados")
public class Laboratorios_resultados implements Serializable {

	/************** ATRIBUTOS **************/

	private  Integer id_resultado;
	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String nro_identificacion;
	private  Timestamp fecha_realizacion;
	private  String lugar;
	private  Integer id_cliente;
	private  String direccion_archivo;
	private  Timestamp creacion_date;
	private  String codigo_orden;

	/*** Constructor Por Defecto ***/
	public Laboratorios_resultados(){}


	/*** Sobre carga de Constructor ***/
	public Laboratorios_resultados(
		Integer id_resultado,
		String codigo_empresa,
		String codigo_sucursal,
		String nro_identificacion,
		Timestamp fecha_realizacion,
		String lugar,
		Integer id_cliente,
		String direccion_archivo,
		Timestamp creacion_date,
		String codigo_orden){
		this.id_resultado = id_resultado;
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.nro_identificacion = nro_identificacion;
		this.fecha_realizacion = fecha_realizacion;
		this.lugar = lugar;
		this.id_cliente = id_cliente;
		this.direccion_archivo = direccion_archivo;
		this.creacion_date = creacion_date;
		this.codigo_orden = codigo_orden;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setId_resultado(Integer id_resultado){
		this.id_resultado=id_resultado;
	}
	public void setCodigo_empresa(String codigo_empresa){
		this.codigo_empresa=codigo_empresa;
	}
	public void setCodigo_sucursal(String codigo_sucursal){
		this.codigo_sucursal=codigo_sucursal;
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
	public void setDireccion_archivo(String direccion_archivo){
		this.direccion_archivo=direccion_archivo;
	}
	public void setCreacion_date(Timestamp creacion_date){
		this.creacion_date=creacion_date;
	}
	public void setCodigo_orden(String codigo_orden){
		this.codigo_orden=codigo_orden;
	}
	/************** METODOS GET **************/

	public Integer getId_resultado(){
		return id_resultado;
	}
	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
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
	public String getDireccion_archivo(){
		return direccion_archivo;
	}
	public Timestamp getCreacion_date(){
		return creacion_date;
	}
	public String getCodigo_orden(){
		return codigo_orden;
	}
}