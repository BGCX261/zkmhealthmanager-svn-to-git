/*
 * Cuadros_aiepi_tutoriales.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */ 
package healthmanager.modelo.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("cuadros_aiepi_tutoriales")
public class Cuadros_aiepi_tutoriales implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String cuadro;
	private  String via_ingreso;
	private  String no_columna;
	private  String titulo;
	private  String info;
	private  String color;

	/*** Constructor Por Defecto ***/
	public Cuadros_aiepi_tutoriales(){}


	/*** Sobre carga de Constructor ***/
	public Cuadros_aiepi_tutoriales(
		String codigo_empresa,
		String codigo_sucursal,
		String cuadro,
		String via_ingreso,
		String no_columna,
		String titulo,
		String info,
		String color){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.cuadro = cuadro;
		this.via_ingreso = via_ingreso;
		this.no_columna = no_columna;
		this.titulo = titulo;
		this.info = info;
		this.color = color;
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
	public void setCuadro(String cuadro){
		this.cuadro=cuadro;
	}
	public void setVia_ingreso(String via_ingreso){
		this.via_ingreso=via_ingreso;
	}
	public void setNo_columna(String no_columna){
		this.no_columna=no_columna;
	}
	public void setTitulo(String titulo){
		this.titulo=titulo;
	}
	public void setInfo(String info){
		this.info=info;
	}
	public void setColor(String color){
		this.color=color;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getCuadro(){
		return cuadro;
	}
	public String getVia_ingreso(){
		return via_ingreso;
	}
	public String getNo_columna(){
		return no_columna;
	}
	public String getTitulo(){
		return titulo;
	}
	public String getInfo(){
		return info;
	}
	public String getColor(){
		return color;
	}
}