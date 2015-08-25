/*
 * Detalle_factura.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 * Ing. Luis Miguel Hernández Pérez
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import org.apache.ibatis.type.Alias;

@Alias("detalle_factura")
public class Detalle_factura implements Serializable {

	/************** ATRIBUTOS **************/
	private Long id_detalle;
	private Long id_factura;
	private  String codigo_empresa;
	private  String codigo_sucursal;
	
	private  String codigo_bodega;
	private  String codigo_articulo;
	private  String codigo_lote;
	private  double costo;
	private  double utilidad;
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
	private  String plan;
	private  String c_costos;
	private  String factura_concepto;
	private  String tipo;
	private  String detalle;
	private  double valor_real;
	private  boolean facturable;
        private  String estado_glosa;
	private  String respueta_glosa;
	private  String observacion_glosa;
	private  String observacion_respuesta;
	private  String observacion_definitiva_glosa;
	private  String respuesta_definitiva;
	private  String glosado;
	private  Timestamp fecha_respuesta_glosa;
	private  Timestamp fecha_respuesta_definitiva;
	private  Timestamp fecha_glosa;
	private  String estado_final_glosa;
	private String codigo_anexo4;
	
	private String consecutivo_med_ser;
	
	//variable para controlar si este detalle va a ser guardado o no en los paquetes de servicios.
	private boolean excluir_guardado = false;

	/*** Constructor Por Defecto ***/
	public Detalle_factura(){
		setCodigo_anexo4("");
	}

	public Detalle_factura(boolean inicializar){
		if(inicializar){
			setCodigo_anexo4("");
			setEstado_glosa("");
			setEstado_final_glosa(""); 
			setRespuesta_definitiva("");
			setRespueta_glosa(""); 
			setCodigo_bodega("");
			setCodigo_lote("");  
		}
	}
	

	/*** Sobre carga de Constructor ***/
	public Detalle_factura(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_comprobante,
		String codigo_documento,
		String consecutivo,
		String codigo_bodega,
		String codigo_articulo,
		String codigo_lote,
		double costo,
		double utilidad,
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
		String plan,
		String c_costos,
		String factura_concepto,
		String tipo,
		String detalle,
		double valor_real,
		boolean facturable,
                String estado_glosa,
		String respueta_glosa,
		String observacion_glosa,
		String observacion_respuesta,
		String observacion_definitiva_glosa,
		String respuesta_definitiva,
		String glosado,
		Timestamp fecha_respuesta_glosa,
		Timestamp fecha_respuesta_definitiva,
		Timestamp fecha_glosa,
		String estado_final_glosa){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_bodega = codigo_bodega;
		this.codigo_articulo = codigo_articulo;
		this.codigo_lote = codigo_lote;
		this.costo = costo;
		this.utilidad = utilidad;
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
		this.plan = plan;
		this.c_costos = c_costos;
		this.factura_concepto = factura_concepto;
		this.tipo = tipo;
		this.detalle = detalle;
		this.valor_real = valor_real;
		this.facturable = facturable;
                this.estado_glosa = estado_glosa;
		this.respueta_glosa = respueta_glosa;
		this.observacion_glosa = observacion_glosa;
		this.observacion_respuesta = observacion_respuesta;
		this.observacion_definitiva_glosa = observacion_definitiva_glosa;
		this.respuesta_definitiva = respuesta_definitiva;
		this.glosado = glosado;
		this.fecha_respuesta_glosa = fecha_respuesta_glosa;
		this.fecha_respuesta_definitiva = fecha_respuesta_definitiva;
		this.fecha_glosa = fecha_glosa;
		this.estado_final_glosa = estado_final_glosa;
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

	public void setCodigo_bodega(String codigo_bodega){
		this.codigo_bodega=codigo_bodega;
	}
	public void setCodigo_articulo(String codigo_articulo){
		this.codigo_articulo=codigo_articulo;
	}
	public void setCodigo_lote(String codigo_lote){
		this.codigo_lote=codigo_lote;
	}
	public void setCosto(double costo){
		this.costo=costo;
	}
	public void setUtilidad(double utilidad){
		this.utilidad=utilidad;
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
	public void setPlan(String plan){
		this.plan=plan;
	}
	public void setC_costos(String c_costos){
		this.c_costos=c_costos;
	}
	public void setFactura_concepto(String factura_concepto){
		this.factura_concepto=factura_concepto;
	}
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	public void setDetalle(String detalle){
		this.detalle=detalle;
	}
	public void setValor_real(double valor_real){
		this.valor_real=valor_real;
	}
	public void setFacturable(boolean facturable){
		this.facturable=facturable;
	}
        public void setEstado_glosa(String estado_glosa){
		this.estado_glosa=estado_glosa;
	}
	public void setRespueta_glosa(String respueta_glosa){
		this.respueta_glosa=respueta_glosa;
	}
	public void setObservacion_glosa(String observacion_glosa){
		this.observacion_glosa=observacion_glosa;
	}
	public void setObservacion_respuesta(String observacion_respuesta){
		this.observacion_respuesta=observacion_respuesta;
	}
	public void setObservacion_definitiva_glosa(String observacion_definitiva_glosa){
		this.observacion_definitiva_glosa=observacion_definitiva_glosa;
	}
	public void setRespuesta_definitiva(String respuesta_definitiva){
		this.respuesta_definitiva=respuesta_definitiva;
	}
	public void setGlosado(String glosado){
		this.glosado=glosado;
	}
	public void setFecha_respuesta_glosa(Timestamp fecha_respuesta_glosa){
		this.fecha_respuesta_glosa=fecha_respuesta_glosa;
	}
	public void setFecha_respuesta_definitiva(Timestamp fecha_respuesta_definitiva){
		this.fecha_respuesta_definitiva=fecha_respuesta_definitiva;
	}
	public void setFecha_glosa(Timestamp fecha_glosa){
		this.fecha_glosa=fecha_glosa;
	}
	public void setEstado_final_glosa(String estado_final_glosa){
		this.estado_final_glosa=estado_final_glosa;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
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
	public double getCosto(){
		return costo;
	}
	public double getUtilidad(){
		return utilidad;
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
	public String getPlan(){
		return plan;
	}
	public String getC_costos(){
		return c_costos;
	}
	public String getFactura_concepto(){
		return factura_concepto;
	}
	public String getTipo(){
		return tipo;
	}
	public String getDetalle(){
		return detalle;
	}
	public double getValor_real(){
		return valor_real;
	}
	public boolean getFacturable(){
		return facturable;
	}
        public String getEstado_glosa(){
		return estado_glosa;
	}
	public String getRespueta_glosa(){
		return respueta_glosa;
	}
	public String getObservacion_glosa(){
		return observacion_glosa;
	}
	public String getObservacion_respuesta(){
		return observacion_respuesta;
	}
	public String getObservacion_definitiva_glosa(){
		return observacion_definitiva_glosa;
	}
	public String getRespuesta_definitiva(){
		return respuesta_definitiva;
	}
	public String getGlosado(){
		return glosado;
	}
	public Timestamp getFecha_respuesta_glosa(){
		return fecha_respuesta_glosa;
	}
	public Timestamp getFecha_respuesta_definitiva(){
		return fecha_respuesta_definitiva;
	}
	public Timestamp getFecha_glosa(){
		return fecha_glosa;
	}
	public String getEstado_final_glosa(){
		return estado_final_glosa;
	}


	public String getCodigo_anexo4() {
		return codigo_anexo4;
	}


	public void setCodigo_anexo4(String codigo_anexo4) {
		this.codigo_anexo4 = codigo_anexo4;
	}

	public String getConsecutivo_med_ser() {
		return consecutivo_med_ser;
	}

	public void setConsecutivo_med_ser(String consecutivo_med_ser) {
		this.consecutivo_med_ser = consecutivo_med_ser;
	}

	public Long getId_detalle() {
		return id_detalle;
	}

	public void setId_detalle(Long id_detalle) {
		this.id_detalle = id_detalle;
	}

	public Long getId_factura() {
		return id_factura;
	}

	public void setId_factura(Long id_factura) {
		this.id_factura = id_factura;
	}

	public boolean getExcluir_guardado() {
		return excluir_guardado;
	}

	public void setExcluir_guardado(Boolean excluir_guardado) {
		this.excluir_guardado = excluir_guardado;
	}
}