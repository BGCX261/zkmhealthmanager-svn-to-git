/*
 * Nota_contable.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import org.apache.ibatis.type.Alias;

@Alias("nota_contable")
public class Nota_contable implements Serializable{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_comprobante;
	private  String codigo_documento;
	private  String prefijo;
	private  String anio;
	private  String mes;
	private  Timestamp fecha;
	private  String elaboro;
	private  String codigo_tercero;
	private  String clasificacion;
	private  String concepto;
	private  String verificado;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String estado;
	private  String forma_pago;
	private  String documento_externo;
	private  String banco;
	private  String medio_pago;
	
	private String codigo_rp;
	private String codigo_obligacion;
	
	private String fuente_factura_glosa;
	private String documento_factura_glosa;
	
	private List<Detalle_nota> lista_detalle;

	/*** Constructor Por Defecto ***/
	public Nota_contable(){
		estado = "CANC";
        forma_pago = "";
        documento_externo = "";
        banco = "";
        medio_pago = "";
        codigo_rp = "";
		codigo_obligacion = "";
	}


	/*** Sobre carga de Constructor ***/
	public Nota_contable(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_comprobante,
		String codigo_documento,
		String prefijo,
		String anio,
		String mes,
		Timestamp fecha,
		String elaboro,
		String codigo_tercero,
		String clasificacion,
		String concepto,
		String verificado,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String estado,
		String forma_pago,
		String documento_externo,
		String banco,
		String medio_pago){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
		this.prefijo = prefijo;
		this.anio = anio;
		this.mes = mes;
		this.fecha = fecha;
		this.elaboro = elaboro;
		this.codigo_tercero = codigo_tercero;
		this.clasificacion = clasificacion;
		this.concepto = concepto;
		this.verificado = verificado;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.estado = estado;
		this.forma_pago = forma_pago;
		this.documento_externo = documento_externo;
		this.banco = banco;
		this.medio_pago = medio_pago;
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
	public void setPrefijo(String prefijo){
		this.prefijo=prefijo;
	}
	public void setAnio(String anio){
		this.anio=anio;
	}
	public void setMes(String mes){
		this.mes=mes;
	}
	public void setFecha(Timestamp fecha){
		this.fecha=fecha;
	}
	public void setElaboro(String elaboro){
		this.elaboro=elaboro;
	}
	public void setCodigo_tercero(String codigo_tercero){
		this.codigo_tercero=codigo_tercero;
	}
	public void setClasificacion(String clasificacion){
		this.clasificacion=clasificacion;
	}
	public void setConcepto(String concepto){
		this.concepto=concepto;
	}
	public void setVerificado(String verificado){
		this.verificado=verificado;
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
	public void setEstado(String estado){
		this.estado=estado;
	}
	public void setForma_pago(String forma_pago){
		this.forma_pago=forma_pago;
	}
	public void setDocumento_externo(String documento_externo){
		this.documento_externo=documento_externo;
	}
	public void setBanco(String banco){
		this.banco=banco;
	}
	public void setMedio_pago(String medio_pago){
		this.medio_pago=medio_pago;
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
	public String getPrefijo(){
		return prefijo;
	}
	public String getAnio(){
		return anio;
	}
	public String getMes(){
		return mes;
	}
	public Timestamp getFecha(){
		return fecha;
	}
	public String getElaboro(){
		return elaboro;
	}
	public String getCodigo_tercero(){
		return codigo_tercero;
	}
	public String getClasificacion(){
		return clasificacion;
	}
	public String getConcepto(){
		return concepto;
	}
	public String getVerificado(){
		return verificado;
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
	public String getEstado(){
		return estado;
	}
	public String getForma_pago(){
		return forma_pago;
	}
	public String getDocumento_externo(){
		return documento_externo;
	}
	public String getBanco(){
		return banco;
	}
	public String getMedio_pago(){
		return medio_pago;
	}


	public String getCodigo_rp() {
		return codigo_rp;
	}


	public void setCodigo_rp(String codigo_rp) {
		this.codigo_rp = codigo_rp;
	}


	public String getCodigo_obligacion() {
		return codigo_obligacion;
	}


	public void setCodigo_obligacion(String codigo_obligacion) {
		this.codigo_obligacion = codigo_obligacion;
	}


	public String getFuente_factura_glosa() {
		return fuente_factura_glosa;
	}


	public void setFuente_factura_glosa(String fuente_factura_glosa) {
		this.fuente_factura_glosa = fuente_factura_glosa;
	}


	public String getDocumento_factura_glosa() {
		return documento_factura_glosa;
	}


	public void setDocumento_factura_glosa(String documento_factura_glosa) {
		this.documento_factura_glosa = documento_factura_glosa;
	}


	public void setLista_detalle(List<Detalle_nota> lista_detalle) {
		this.lista_detalle = lista_detalle;
	}


	public List<Detalle_nota> getLista_detalle() {
		return lista_detalle;
	}
}
