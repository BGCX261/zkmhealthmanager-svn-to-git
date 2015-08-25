/*
 * Consecutivo.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("consecutivoCont")
public class Consecutivo{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String tipo;
	private  String consecutivo;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;

	/*** Constructor Por Defecto ***/
	public Consecutivo(){}


	/*** Sobre carga de Constructor ***/
	public Consecutivo(
		String codigo_empresa,
		String codigo_sucursal,
		String tipo,
		String consecutivo,
		Timestamp creacion_date,
		Timestamp ultimo_update){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.tipo = tipo;
		this.consecutivo = consecutivo;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
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
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	public void setConsecutivo(String consecutivo){
		this.consecutivo=consecutivo;
	}
	public void setCreacion_date(Timestamp creacion_date){
		this.creacion_date=creacion_date;
	}
	public void setUltimo_update(Timestamp ultimo_update){
		this.ultimo_update=ultimo_update;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getTipo(){
		return tipo;
	}
	public String getConsecutivo(){
		return consecutivo;
	}
	public Timestamp getCreacion_date(){
		return creacion_date;
	}
	public Timestamp getUltimo_update(){
		return ultimo_update;
	}
}
