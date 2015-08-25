/*
 * Lote_articulo.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("lote_articulo")
public class Lote_articulo{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_lote;
	private  String codigo_bodega;
	private  String codigo_articulo;
	private  double cantidad;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;

	/*** Constructor Por Defecto ***/
	public Lote_articulo(){}


	/*** Sobre carga de Constructor ***/
	public Lote_articulo(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_lote,
		String codigo_bodega,
		String codigo_articulo,
		double cantidad,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_lote = codigo_lote;
		this.codigo_bodega = codigo_bodega;
		this.codigo_articulo = codigo_articulo;
		this.cantidad = cantidad;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
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
	public void setCodigo_lote(String codigo_lote){
		this.codigo_lote=codigo_lote;
	}
	public void setCodigo_bodega(String codigo_bodega){
		this.codigo_bodega=codigo_bodega;
	}
	public void setCodigo_articulo(String codigo_articulo){
		this.codigo_articulo=codigo_articulo;
	}
	public void setCantidad(double cantidad){
		this.cantidad=cantidad;
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
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getCodigo_lote(){
		return codigo_lote;
	}
	public String getCodigo_bodega(){
		return codigo_bodega;
	}
	public String getCodigo_articulo(){
		return codigo_articulo;
	}
	public double getCantidad(){
		return cantidad;
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
}
