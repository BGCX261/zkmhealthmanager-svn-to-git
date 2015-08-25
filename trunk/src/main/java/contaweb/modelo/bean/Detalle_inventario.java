/*
 * Detalle_inventario.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("detalle_inventario")
public class Detalle_inventario{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_comprobante;
	private  String codigo_documento;
	private  String consecutivo;
	private  String codigo_bodega;
	private  String codigo_articulo;
	private  String codigo_lote;
	private  double cantidad;
	private  double valor_unitario;
	private  double valor_total;
	private  double descuento;
	private  double iva;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  double depreciacion;
	private  String c_costos;
	private  String cancelo_copago;
	private  boolean es_depreciacion;

	/*** Constructor Por Defecto ***/
	public Detalle_inventario(){}


	/*** Sobre carga de Constructor ***/
	public Detalle_inventario(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_comprobante,
		String codigo_documento,
		String consecutivo,
		String codigo_bodega,
		String codigo_articulo,
		String codigo_lote,
		double cantidad,
		double valor_unitario,
		double valor_total,
		double descuento,
		double iva,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		double depreciacion,
		String c_costos,
		String cancelo_copago,
		boolean es_depreciacion){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
		this.consecutivo = consecutivo;
		this.codigo_bodega = codigo_bodega;
		this.codigo_articulo = codigo_articulo;
		this.codigo_lote = codigo_lote;
		this.cantidad = cantidad;
		this.valor_unitario = valor_unitario;
		this.valor_total = valor_total;
		this.descuento = descuento;
		this.iva = iva;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.depreciacion = depreciacion;
		this.c_costos = c_costos;
		this.cancelo_copago = cancelo_copago;
		this.es_depreciacion = es_depreciacion;
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
	public void setCodigo_comprobante(String codigo_comprobante){
		this.codigo_comprobante=codigo_comprobante;
	}
	public void setCodigo_documento(String codigo_documento){
		this.codigo_documento=codigo_documento;
	}
	public void setConsecutivo(String consecutivo){
		this.consecutivo=consecutivo;
	}
	public void setCodigo_bodega(String codigo_bodega){
		this.codigo_bodega=codigo_bodega;
	}
	public void setCodigo_articulo(String codigo_articulo){
		this.codigo_articulo=codigo_articulo;
	}
	public void setCodigo_lote(String codigo_lote){
		this.codigo_lote=codigo_lote;
	}
	public void setCantidad(double cantidad){
		this.cantidad=cantidad;
	}
	public void setValor_unitario(double valor_unitario){
		this.valor_unitario=valor_unitario;
	}
	public void setValor_total(double valor_total){
		this.valor_total=valor_total;
	}
	public void setDescuento(double descuento){
		this.descuento=descuento;
	}
	public void setIva(double iva){
		this.iva=iva;
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
	public void setDepreciacion(double depreciacion){
		this.depreciacion=depreciacion;
	}
	public void setC_costos(String c_costos){
		this.c_costos=c_costos;
	}
	public void setCancelo_copago(String cancelo_copago){
		this.cancelo_copago=cancelo_copago;
	}
	public void setEs_depreciacion(boolean es_depreciacion){
		this.es_depreciacion=es_depreciacion;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getCodigo_comprobante(){
		return codigo_comprobante;
	}
	public String getCodigo_documento(){
		return codigo_documento;
	}
	public String getConsecutivo(){
		return consecutivo;
	}
	public String getCodigo_bodega(){
		return codigo_bodega;
	}
	public String getCodigo_articulo(){
		return codigo_articulo;
	}
	public String getCodigo_lote(){
		return codigo_lote;
	}
	public double getCantidad(){
		return cantidad;
	}
	public double getValor_unitario(){
		return valor_unitario;
	}
	public double getValor_total(){
		return valor_total;
	}
	public double getDescuento(){
		return descuento;
	}
	public double getIva(){
		return iva;
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
	public double getDepreciacion(){
		return depreciacion;
	}
	public String getC_costos(){
		return c_costos;
	}
	public String getCancelo_copago(){
		return cancelo_copago;
	}
	public boolean getEs_depreciacion(){
		return es_depreciacion;
	}
}
