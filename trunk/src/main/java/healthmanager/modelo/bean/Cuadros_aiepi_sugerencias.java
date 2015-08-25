/*
 * Cuadros_aiepi_sugerencias.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package healthmanager.modelo.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("cuadros_aiepi_sugerencias")
public class Cuadros_aiepi_sugerencias implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  Integer id_sugerencia;
	private  String via_ingreso;
	private  String cuadro;
	private  String estado;
	private  String sugerencia;
	private  String enlace;

	/*** Constructor Por Defecto ***/
	public Cuadros_aiepi_sugerencias(){}


	/*** Sobre carga de Constructor ***/
	public Cuadros_aiepi_sugerencias(
		String codigo_empresa,
		String codigo_sucursal,
		Integer id_sugerencia,
		String via_ingreso,
		String cuadro,
		String estado,
		String sugerencia,
		String enlace){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.id_sugerencia = id_sugerencia;
		this.via_ingreso = via_ingreso;
		this.cuadro = cuadro;
		this.estado = estado;
		this.sugerencia = sugerencia;
		this.enlace = enlace;
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
	public void setId_sugerencia(Integer id_sugerencia){
		this.id_sugerencia=id_sugerencia;
	}
	public void setVia_ingreso(String via_ingreso){
		this.via_ingreso=via_ingreso;
	}
	public void setCuadro(String cuadro){
		this.cuadro=cuadro;
	}
	public void setEstado(String estado){
		this.estado=estado;
	}
	public void setSugerencia(String sugerencia){
		this.sugerencia=sugerencia;
	}
	public void setEnlace(String enlace){
		this.enlace=enlace;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public Integer getId_sugerencia(){
		return id_sugerencia;
	}
	public String getVia_ingreso(){
		return via_ingreso;
	}
	public String getCuadro(){
		return cuadro;
	}
	public String getEstado(){
		return estado;
	}
	public String getSugerencia(){
		return sugerencia;
	}
	public String getEnlace(){
		return enlace;
	}
}