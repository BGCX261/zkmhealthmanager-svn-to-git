/*
 * Kardex.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("kardex")
public class Kardex{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_fuente;
	private  String codigo_documento;
	private  String codigo_bodega;
	private  String codigo_articulo;
	private  Timestamp fecha;
	private  String detalle;
	private  double unidad_entrada;
	private  double valor_entrada;
	private  double total_entrada;
	private  double unidad_salida;
	private  double valor_salida;
	private  double total_salida;
	private  double unidad_total;
	private  double valor_unitario_total;
	private  double valor_total;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;

	/*** Constructor Por Defecto ***/
	public Kardex(){}


	/*** Sobre carga de Constructor ***/
	public Kardex(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_fuente,
		String codigo_documento,
		String codigo_bodega,
		String codigo_articulo,
		Timestamp fecha,
		String detalle,
		double unidad_entrada,
		double valor_entrada,
		double total_entrada,
		double unidad_salida,
		double valor_salida,
		double total_salida,
		double unidad_total,
		double valor_unitario_total,
		double valor_total,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_fuente = codigo_fuente;
		this.codigo_documento = codigo_documento;
		this.codigo_bodega = codigo_bodega;
		this.codigo_articulo = codigo_articulo;
		this.fecha = fecha;
		this.detalle = detalle;
		this.unidad_entrada = unidad_entrada;
		this.valor_entrada = valor_entrada;
		this.total_entrada = total_entrada;
		this.unidad_salida = unidad_salida;
		this.valor_salida = valor_salida;
		this.total_salida = total_salida;
		this.unidad_total = unidad_total;
		this.valor_unitario_total = valor_unitario_total;
		this.valor_total = valor_total;
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
	public void setCodigo_fuente(String codigo_fuente){
		this.codigo_fuente=codigo_fuente;
	}
	public void setCodigo_documento(String codigo_documento){
		this.codigo_documento=codigo_documento;
	}
	public void setCodigo_bodega(String codigo_bodega){
		this.codigo_bodega=codigo_bodega;
	}
	public void setCodigo_articulo(String codigo_articulo){
		this.codigo_articulo=codigo_articulo;
	}
	public void setFecha(Timestamp fecha){
		this.fecha=fecha;
	}
	public void setDetalle(String detalle){
		this.detalle=detalle;
	}
	public void setUnidad_entrada(double unidad_entrada){
		this.unidad_entrada=unidad_entrada;
	}
	public void setValor_entrada(double valor_entrada){
		this.valor_entrada=valor_entrada;
	}
	public void setTotal_entrada(double total_entrada){
		this.total_entrada=total_entrada;
	}
	public void setUnidad_salida(double unidad_salida){
		this.unidad_salida=unidad_salida;
	}
	public void setValor_salida(double valor_salida){
		this.valor_salida=valor_salida;
	}
	public void setTotal_salida(double total_salida){
		this.total_salida=total_salida;
	}
	public void setUnidad_total(double unidad_total){
		this.unidad_total=unidad_total;
	}
	public void setValor_unitario_total(double valor_unitario_total){
		this.valor_unitario_total=valor_unitario_total;
	}
	public void setValor_total(double valor_total){
		this.valor_total=valor_total;
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
	public String getCodigo_fuente(){
		return codigo_fuente;
	}
	public String getCodigo_documento(){
		return codigo_documento;
	}
	public String getCodigo_bodega(){
		return codigo_bodega;
	}
	public String getCodigo_articulo(){
		return codigo_articulo;
	}
	public Timestamp getFecha(){
		return fecha;
	}
	public String getDetalle(){
		return detalle;
	}
	public double getUnidad_entrada(){
		return unidad_entrada;
	}
	public double getValor_entrada(){
		return valor_entrada;
	}
	public double getTotal_entrada(){
		return total_entrada;
	}
	public double getUnidad_salida(){
		return unidad_salida;
	}
	public double getValor_salida(){
		return valor_salida;
	}
	public double getTotal_salida(){
		return total_salida;
	}
	public double getUnidad_total(){
		return unidad_total;
	}
	public double getValor_unitario_total(){
		return valor_unitario_total;
	}
	public double getValor_total(){
		return valor_total;
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
