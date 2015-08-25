/*
 * C_costo_via_ingreso.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("c_costo_via_ingreso")
public class C_costo_via_ingreso implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_cc;
	private  String via_ingreso;
	private  String codigo_centro;

	/*** Constructor Por Defecto ***/
	public C_costo_via_ingreso(){}


	/*** Sobre carga de Constructor ***/
	public C_costo_via_ingreso(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_cc,
		String via_ingreso,
		String codigo_centro){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_cc = codigo_cc;
		this.via_ingreso = via_ingreso;
		this.codigo_centro = codigo_centro;
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
	public void setCodigo_cc(String codigo_cc){
		this.codigo_cc=codigo_cc;
	}
	public void setVia_ingreso(String via_ingreso){
		this.via_ingreso=via_ingreso;
	}
	public void setCodigo_centro(String codigo_centro){
		this.codigo_centro=codigo_centro;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getCodigo_cc(){
		return codigo_cc;
	}
	public String getVia_ingreso(){
		return via_ingreso;
	}
	public String getCodigo_centro(){
		return codigo_centro;
	}
}