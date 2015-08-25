/*
 * Detalle_caja.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("detalle_caja")
public class Detalle_caja{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_recibo;
	private  String consecutivo;
	private  String nro_cuota;
	private  String codigo_bodega;
	private  String codigo_articulo;
	private  String codigo_lote;
	private  String detalle;
	private  double cantidad;
	private  double valor_unitario;
	private  double valor_total;
	private  double descuento;
	private  double iva;
	private  String tipo;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String fuente;
	private  double copago;
	private  double interes;
	private  String concepto_abona;
	private  double valor_adicional;
	
	/* campos temporales */
	private double porcentaje_adicionar;
	
	

	public double getPorcentaje_adicionar() {
		return porcentaje_adicionar;
	}


	public void setPorcentaje_adicionar(double porcentajeAdicionar) {
		porcentaje_adicionar = porcentajeAdicionar;
	}


	/*** Constructor Por Defecto ***/
	public Detalle_caja(){
		concepto_abona = "";
	}


	/*** Sobre carga de Constructor ***/
	public Detalle_caja(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_recibo,
		String consecutivo,
		String nro_cuota,
		String codigo_bodega,
		String codigo_articulo,
		String codigo_lote,
		String detalle,
		double cantidad,
		double valor_unitario,
		double valor_total,
		double descuento,
		double iva,
		String tipo,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String fuente,
		double copago,
		double interes,
		String concepto_abona,
		double valor_adicional){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_recibo = codigo_recibo;
		this.consecutivo = consecutivo;
		this.nro_cuota = nro_cuota;
		this.codigo_bodega = codigo_bodega;
		this.codigo_articulo = codigo_articulo;
		this.codigo_lote = codigo_lote;
		this.detalle = detalle;
		this.cantidad = cantidad;
		this.valor_unitario = valor_unitario;
		this.valor_total = valor_total;
		this.descuento = descuento;
		this.iva = iva;
		this.tipo = tipo;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.fuente = fuente;
		this.copago = copago;
		this.interes = interes;
		this.concepto_abona = concepto_abona;
		this.valor_adicional = valor_adicional;
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
	public void setCodigo_recibo(String codigo_recibo){
		this.codigo_recibo=codigo_recibo;
	}
	public void setConsecutivo(String consecutivo){
		this.consecutivo=consecutivo;
	}
	public void setNro_cuota(String nro_cuota){
		this.nro_cuota=nro_cuota;
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
	public void setDetalle(String detalle){
		this.detalle=detalle;
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
	public void setTipo(String tipo){
		this.tipo=tipo;
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
	public void setFuente(String fuente){
		this.fuente=fuente;
	}
	public void setCopago(double copago){
		this.copago=copago;
	}
	public void setInteres(double interes){
		this.interes=interes;
	}
	public void setConcepto_abona(String concepto_abona){
		this.concepto_abona=concepto_abona;
	}
	public void setValor_adicional(double valor_adicional){
		this.valor_adicional=valor_adicional;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getCodigo_recibo(){
		return codigo_recibo;
	}
	public String getConsecutivo(){
		return consecutivo;
	}
	public String getNro_cuota(){
		return nro_cuota;
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
	public String getDetalle(){
		return detalle;
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
	public String getTipo(){
		return tipo;
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
	public String getFuente(){
		return fuente;
	}
	public double getCopago(){
		return copago;
	}
	public double getInteres(){
		return interes;
	}
	public String getConcepto_abona(){
		return concepto_abona;
	}
	public double getValor_adicional(){
		return valor_adicional;
	}
}
