/*
 * Cups.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package healthmanager.modelo.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("cups")
public class Cups implements Serializable{

	/************** ATRIBUTOS **************/

	private  String codigo;
	private  String nombre;
	private  String sexo;
	private  String limite_inferior;
	private  String limite_superior;
	private  String archivo;
	private  String qx;

	/*** Constructor Por Defecto ***/
	public Cups(){}


	/*** Sobre carga de Constructor ***/
	public Cups(
		String codigo,
		String nombre,
		String sexo,
		String limite_inferior,
		String limite_superior,
		String archivo,
		String qx){
		this.codigo = codigo;
		this.nombre = nombre;
		this.sexo = sexo;
		this.limite_inferior = limite_inferior;
		this.limite_superior = limite_superior;
		this.archivo = archivo;
		this.qx = qx;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setCodigo(String codigo){
		this.codigo=codigo;
	}
	public void setNombre(String nombre){
		this.nombre=nombre;
	}
	public void setSexo(String sexo){
		this.sexo=sexo;
	}
	public void setLimite_inferior(String limite_inferior){
		this.limite_inferior=limite_inferior;
	}
	public void setLimite_superior(String limite_superior){
		this.limite_superior=limite_superior;
	}
	public void setArchivo(String archivo){
		this.archivo=archivo;
	}
	public void setQx(String qx){
		this.qx=qx;
	}
	/************** METODOS GET **************/

	public String getCodigo(){
		return codigo;
	}
	public String getNombre(){
		return nombre;
	}
	public String getSexo(){
		return sexo;
	}
	public String getLimite_inferior(){
		return limite_inferior;
	}
	public String getLimite_superior(){
		return limite_superior;
	}
	public String getArchivo(){
		return archivo;
	}
	public String getQx(){
		return qx;
	}
}
