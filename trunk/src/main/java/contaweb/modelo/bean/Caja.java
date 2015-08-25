/*
 * Caja.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 * Ing. Luis Miguel Hernández Pérez
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import org.apache.ibatis.type.Alias;

@Alias("caja")
public class Caja implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_recibo;
	private  String codigo_comprobante;
	private  String codigo_documento;
	private  String codigo_tercero;
	private  String tipo_recibo;
	private  Timestamp fecha;
	private  double valor_recibo;
	private  double efectivo;
	private  double valor_tarjeta;
	private  String numero_tarjeta;
	private  String banco_tarjeta;
	private  String tipo_tarjeta;
	private  String tipo_cuenta;
	private  String cuenta_tarjeta;
	private  String amparador;
	private  double valor_cheque;
	private  String numero_cheque;
	private  String cuenta_cheque;
	private  String banco_cheque;
	private  String sucursal_cheque;
	private  Timestamp fecha_cheque;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String vendedor;
	private  String referencia;
	private  String codigo_credito;
	private  String concepto;
	private  String fuente;
	private  String prefijo;
	private  String anio;
	private  String mes;
	private  String nro_ingreso;
	private  String codigo_administradora;
	private  String id_plan;
	private  String tipo_tercero;
	private  double descuento;
	private  boolean es_remision;
	private  String medio_pago;
	private  String codigo_cita;
	private  String copago_autorizaciones;
	private  String copago_medicamentos;
	private  String codigo_anexo4;
	private  String codigo_receta;
    private  String codigo_anexo9;
        private  String codigo_orden;
	private  String convension;
	private List<Detalle_caja> lista_detalle;

	/*** Constructor Por Defecto ***/
	public Caja(){
		medio_pago = "";
        codigo_cita = "";
        copago_autorizaciones = "N";
        copago_medicamentos = "N";
        codigo_anexo4 = "";
        codigo_receta = "";
        codigo_anexo9 = "";
        codigo_orden = "";
	}
	
	public Caja(boolean valor_inicialozado){
		if(valor_inicialozado){
			medio_pago = "";
	        codigo_cita = "";
	        copago_autorizaciones = "N";
	        copago_medicamentos = "N";
	        codigo_anexo4 = "";
	        codigo_receta = "";
	        codigo_anexo9 = "";
	        codigo_orden = "";
		}
	}


	/*** Sobre carga de Constructor ***/
	public Caja(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_recibo,
		String codigo_comprobante,
		String codigo_documento,
		String codigo_tercero,
		String tipo_recibo,
		Timestamp fecha,
		double valor_recibo,
		double efectivo,
		double valor_tarjeta,
		String numero_tarjeta,
		String banco_tarjeta,
		String tipo_tarjeta,
		String tipo_cuenta,
		String cuenta_tarjeta,
		String amparador,
		double valor_cheque,
		String numero_cheque,
		String cuenta_cheque,
		String banco_cheque,
		String sucursal_cheque,
		Timestamp fecha_cheque,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String vendedor,
		String referencia,
		String codigo_credito,
		String concepto,
		String fuente,
		String prefijo,
		String anio,
		String mes,
		String nro_ingreso,
		String codigo_administradora,
		String id_plan,
		String tipo_tercero,
		double descuento,
		boolean es_remision,
		String medio_pago,
		String codigo_cita,
		String copago_autorizaciones,
		String copago_medicamentos,
		String codigo_anexo4,
		String codigo_receta,
		String codigo_anexo9,
		String codigo_orden){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_recibo = codigo_recibo;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
		this.codigo_tercero = codigo_tercero;
		this.tipo_recibo = tipo_recibo;
		this.fecha = fecha;
		this.valor_recibo = valor_recibo;
		this.efectivo = efectivo;
		this.valor_tarjeta = valor_tarjeta;
		this.numero_tarjeta = numero_tarjeta;
		this.banco_tarjeta = banco_tarjeta;
		this.tipo_tarjeta = tipo_tarjeta;
		this.tipo_cuenta = tipo_cuenta;
		this.cuenta_tarjeta = cuenta_tarjeta;
		this.amparador = amparador;
		this.valor_cheque = valor_cheque;
		this.numero_cheque = numero_cheque;
		this.cuenta_cheque = cuenta_cheque;
		this.banco_cheque = banco_cheque;
		this.sucursal_cheque = sucursal_cheque;
		this.fecha_cheque = fecha_cheque;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.vendedor = vendedor;
		this.referencia = referencia;
		this.codigo_credito = codigo_credito;
		this.concepto = concepto;
		this.fuente = fuente;
		this.prefijo = prefijo;
		this.anio = anio;
		this.mes = mes;
		this.nro_ingreso = nro_ingreso;
		this.codigo_administradora = codigo_administradora;
		this.id_plan = id_plan;
		this.tipo_tercero = tipo_tercero;
		this.descuento = descuento;
		this.es_remision = es_remision;
		this.medio_pago = medio_pago;
		this.codigo_cita = codigo_cita;
		this.copago_autorizaciones = copago_autorizaciones;
		this.copago_medicamentos = copago_medicamentos;
		this.codigo_anexo4 = codigo_anexo4;
		this.codigo_receta = codigo_receta;
		this.codigo_anexo9 = codigo_anexo9;
		this.codigo_orden = codigo_orden;
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
	public void setCodigo_comprobante(String codigo_comprobante){
		this.codigo_comprobante=codigo_comprobante;
	}
	public void setCodigo_documento(String codigo_documento){
		this.codigo_documento=codigo_documento;
	}
	public void setCodigo_tercero(String codigo_tercero){
		this.codigo_tercero=codigo_tercero;
	}
	public void setTipo_recibo(String tipo_recibo){
		this.tipo_recibo=tipo_recibo;
	}
	public void setFecha(Timestamp fecha){
		this.fecha=fecha;
	}
	public void setValor_recibo(double valor_recibo){
		this.valor_recibo=valor_recibo;
	}
	public void setEfectivo(double efectivo){
		this.efectivo=efectivo;
	}
	public void setValor_tarjeta(double valor_tarjeta){
		this.valor_tarjeta=valor_tarjeta;
	}
	public void setNumero_tarjeta(String numero_tarjeta){
		this.numero_tarjeta=numero_tarjeta;
	}
	public void setBanco_tarjeta(String banco_tarjeta){
		this.banco_tarjeta=banco_tarjeta;
	}
	public void setTipo_tarjeta(String tipo_tarjeta){
		this.tipo_tarjeta=tipo_tarjeta;
	}
	public void setTipo_cuenta(String tipo_cuenta){
		this.tipo_cuenta=tipo_cuenta;
	}
	public void setCuenta_tarjeta(String cuenta_tarjeta){
		this.cuenta_tarjeta=cuenta_tarjeta;
	}
	public void setAmparador(String amparador){
		this.amparador=amparador;
	}
	public void setValor_cheque(double valor_cheque){
		this.valor_cheque=valor_cheque;
	}
	public void setNumero_cheque(String numero_cheque){
		this.numero_cheque=numero_cheque;
	}
	public void setCuenta_cheque(String cuenta_cheque){
		this.cuenta_cheque=cuenta_cheque;
	}
	public void setBanco_cheque(String banco_cheque){
		this.banco_cheque=banco_cheque;
	}
	public void setSucursal_cheque(String sucursal_cheque){
		this.sucursal_cheque=sucursal_cheque;
	}
	public void setFecha_cheque(Timestamp fecha_cheque){
		this.fecha_cheque=fecha_cheque;
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
	public void setVendedor(String vendedor){
		this.vendedor=vendedor;
	}
	public void setReferencia(String referencia){
		this.referencia=referencia;
	}
	public void setCodigo_credito(String codigo_credito){
		this.codigo_credito=codigo_credito;
	}
	public void setConcepto(String concepto){
		this.concepto=concepto;
	}
	public void setFuente(String fuente){
		this.fuente=fuente;
	}
	public void setPrefijo(String prefijo){
		this.prefijo=prefijo;
	}
	public void setAnio(String anio){
		this.anio=anio;
	}
	public void setMes(String mes){
		this.mes=mes;
	}
	public void setNro_ingreso(String nro_ingreso){
		this.nro_ingreso=nro_ingreso;
	}
	public void setCodigo_administradora(String codigo_administradora){
		this.codigo_administradora=codigo_administradora;
	}
	public void setId_plan(String id_plan){
		this.id_plan=id_plan;
	}
	public void setTipo_tercero(String tipo_tercero){
		this.tipo_tercero=tipo_tercero;
	}
	public void setDescuento(double descuento){
		this.descuento=descuento;
	}
	public void setEs_remision(boolean es_remision){
		this.es_remision=es_remision;
	}
	public void setMedio_pago(String medio_pago){
		this.medio_pago=medio_pago;
	}
	public void setCodigo_cita(String codigo_cita){
		this.codigo_cita=codigo_cita;
	}
	public void setCopago_autorizaciones(String copago_autorizaciones){
		this.copago_autorizaciones=copago_autorizaciones;
	}
	public void setCopago_medicamentos(String copago_medicamentos){
		this.copago_medicamentos=copago_medicamentos;
	}
	public void setCodigo_anexo4(String codigo_anexo4){
		this.codigo_anexo4=codigo_anexo4;
	}
	public void setCodigo_receta(String codigo_receta){
		this.codigo_receta=codigo_receta;
	}
	public void setCodigo_anexo9(String codigo_anexo9){
		this.codigo_anexo9=codigo_anexo9;
	}
	public void setCodigo_orden(String codigo_orden){
		this.codigo_orden=codigo_orden;
	}
public void setConvension(String convension){
		this.convension=convension;
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
	public String getCodigo_comprobante(){
		return codigo_comprobante;
	}
	public String getCodigo_documento(){
		return codigo_documento;
	}
	public String getCodigo_tercero(){
		return codigo_tercero;
	}
	public String getTipo_recibo(){
		return tipo_recibo;
	}
	public Timestamp getFecha(){
		return fecha;
	}
	public double getValor_recibo(){
		return valor_recibo;
	}
	public double getEfectivo(){
		return efectivo;
	}
	public double getValor_tarjeta(){
		return valor_tarjeta;
	}
	public String getNumero_tarjeta(){
		return numero_tarjeta;
	}
	public String getBanco_tarjeta(){
		return banco_tarjeta;
	}
	public String getTipo_tarjeta(){
		return tipo_tarjeta;
	}
	public String getTipo_cuenta(){
		return tipo_cuenta;
	}
	public String getCuenta_tarjeta(){
		return cuenta_tarjeta;
	}
	public String getAmparador(){
		return amparador;
	}
	public double getValor_cheque(){
		return valor_cheque;
	}
	public String getNumero_cheque(){
		return numero_cheque;
	}
	public String getCuenta_cheque(){
		return cuenta_cheque;
	}
	public String getBanco_cheque(){
		return banco_cheque;
	}
	public String getSucursal_cheque(){
		return sucursal_cheque;
	}
	public Timestamp getFecha_cheque(){
		return fecha_cheque;
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
	public String getVendedor(){
		return vendedor;
	}
	public String getReferencia(){
		return referencia;
	}
	public String getCodigo_credito(){
		return codigo_credito;
	}
	public String getConcepto(){
		return concepto;
	}
	public String getFuente(){
		return fuente;
	}
	public String getPrefijo(){
		return prefijo;
	}
	public String getAnio(){
		return anio;
	}
	public String getMes(){
		return mes;
	}
	public String getNro_ingreso(){
		return nro_ingreso;
	}
	public String getCodigo_administradora(){
		return codigo_administradora;
	}
	public String getId_plan(){
		return id_plan;
	}
	public String getTipo_tercero(){
		return tipo_tercero;
	}
	public double getDescuento(){
		return descuento;
	}
	public boolean getEs_remision(){
		return es_remision;
	}
	public String getMedio_pago(){
		return medio_pago;
	}
	public String getCodigo_cita(){
		return codigo_cita;
	}
	public String getCopago_autorizaciones(){
		return copago_autorizaciones;
	}
	public String getCopago_medicamentos(){
		return copago_medicamentos;
	}
	public String getCodigo_anexo4(){
		return codigo_anexo4;
	}

        public String getCodigo_receta(){
		return codigo_receta;
	} 

        public String getCodigo_anexo9(){
		return codigo_anexo9;
	}

        public String getCodigo_orden(){
		return codigo_orden;
	}

	public void setLista_detalle(List<Detalle_caja> lista_detalle) {
		this.lista_detalle = lista_detalle;
	}


	public List<Detalle_caja> getLista_detalle() {
		return lista_detalle;
	}
public String getConvension(){
		return convension;
	}
}
