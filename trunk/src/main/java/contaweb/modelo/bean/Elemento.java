/*
 * Elemento.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("elementoCont")
public class Elemento{

	/************** ATRIBUTOS **************/

	private  String codigo;
	private  String tipo;
	private  String descripcion;

	/*** Constructor Por Defecto ***/
	public Elemento(){}


	/*** Sobre carga de Constructor ***/
	public Elemento(
		String codigo,
		String tipo,
		String descripcion){
		this.codigo = codigo;
		this.tipo = tipo;
		this.descripcion = descripcion;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setCodigo(String codigo){
		this.codigo=codigo;
	}
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	public void setDescripcion(String descripcion){
		this.descripcion=descripcion;
	}
	/************** METODOS GET **************/

	public String getCodigo(){
		return codigo;
	}
	public String getTipo(){
		return tipo;
	}
	public String getDescripcion(){
		return descripcion;
	}
}
