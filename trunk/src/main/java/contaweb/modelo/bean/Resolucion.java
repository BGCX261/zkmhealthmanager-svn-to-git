/*
 * Resolucion.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("resolucionCont")
public class Resolucion implements Serializable{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String resolucion;
	private  byte[] logo;
	private  String extension;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String cuenta_cierre;
	private  String cuenta_cobrar;
	private  String cuenta_pagar;
	private  String cuenta_caja;
	private  String cuenta_ingreso;
	private  String cuenta_copago;
	private  String anio;
	private  String mes;
	private  String cuenta_inventario;
	private  String cuenta_costo;
	private  String leyenda_factura;
	private  boolean contabiliza_aut;
	private  String cuenta_devolucion;
	private  String cuenta_glosa;
	
	private String cuenta_cobrar_radicada;
	
	private  String codigo_contabilidad_medicamentos;
	private  String codigo_contabilidad_materiales;
	private  String codigo_contabilidad_traslados;
	private  String codigo_contabilidad_estancias;
	private  String codigo_contabilidad_honorarios;
	
	private String codigo_tercero_icbf;
	private String codigo_tercero_sena;
	
	private String cuenta_margen_gasto_capita;
	private String cuenta_margen_ingreso_capita;
	
	private  boolean contabilizar_servicios_capita;
	
	private String cuenta_particular;

	/*** Constructor Por Defecto ***/
	public Resolucion(){
		
		codigo_contabilidad_medicamentos = "";
		codigo_contabilidad_materiales = "";
		codigo_contabilidad_traslados = "";
		codigo_contabilidad_estancias = "";
		codigo_contabilidad_honorarios = "";
		
		codigo_tercero_icbf = "";
		codigo_tercero_sena = "";
		
		cuenta_margen_gasto_capita = "";
		cuenta_margen_ingreso_capita = "";
		
		cuenta_particular = "";
	}


	/*** Sobre carga de Constructor ***/
	public Resolucion(
		String codigo_empresa,
		String resolucion,
		byte[] logo,
		String extension,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String cuenta_cierre,
		String cuenta_cobrar,
		String cuenta_pagar,
		String cuenta_caja,
		String cuenta_ingreso,
		String cuenta_copago,
		String anio,
		String mes,
		String cuenta_inventario,
		String cuenta_costo,
		String leyenda_factura,
		boolean contabiliza_aut,
		String cuenta_devolucion,
		String cuenta_glosa){
		this.codigo_empresa = codigo_empresa;
		this.resolucion = resolucion;
		this.logo = logo;
		this.extension = extension;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.cuenta_cierre = cuenta_cierre;
		this.cuenta_cobrar = cuenta_cobrar;
		this.cuenta_pagar = cuenta_pagar;
		this.cuenta_caja = cuenta_caja;
		this.cuenta_ingreso = cuenta_ingreso;
		this.cuenta_copago = cuenta_copago;
		this.anio = anio;
		this.mes = mes;
		this.cuenta_inventario = cuenta_inventario;
		this.cuenta_costo = cuenta_costo;
		this.leyenda_factura = leyenda_factura;
		this.contabiliza_aut = contabiliza_aut;
		this.cuenta_devolucion = cuenta_devolucion;
		this.cuenta_glosa = cuenta_glosa;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setCodigo_empresa(String codigo_empresa){
		this.codigo_empresa=codigo_empresa;
	}
	public void setResolucion(String resolucion){
		this.resolucion=resolucion;
	}
	public void setLogo(byte[] logo){
		this.logo=logo;
	}
	public void setExtension(String extension){
		this.extension=extension;
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
	public void setCuenta_cierre(String cuenta_cierre){
		this.cuenta_cierre=cuenta_cierre;
	}
	public void setCuenta_cobrar(String cuenta_cobrar){
		this.cuenta_cobrar=cuenta_cobrar;
	}
	public void setCuenta_pagar(String cuenta_pagar){
		this.cuenta_pagar=cuenta_pagar;
	}
	public void setCuenta_caja(String cuenta_caja){
		this.cuenta_caja=cuenta_caja;
	}
	public void setCuenta_ingreso(String cuenta_ingreso){
		this.cuenta_ingreso=cuenta_ingreso;
	}
	public void setCuenta_copago(String cuenta_copago){
		this.cuenta_copago=cuenta_copago;
	}
	public void setAnio(String anio){
		this.anio=anio;
	}
	public void setMes(String mes){
		this.mes=mes;
	}
	public void setCuenta_inventario(String cuenta_inventario){
		this.cuenta_inventario=cuenta_inventario;
	}
	public void setCuenta_costo(String cuenta_costo){
		this.cuenta_costo=cuenta_costo;
	}
	public void setLeyenda_factura(String leyenda_factura){
		this.leyenda_factura=leyenda_factura;
	}
	public void setContabiliza_aut(boolean contabiliza_aut){
		this.contabiliza_aut=contabiliza_aut;
	}
	public void setCuenta_devolucion(String cuenta_devolucion){
		this.cuenta_devolucion=cuenta_devolucion;
	}
	public void setCuenta_glosa(String cuenta_glosa){
		this.cuenta_glosa=cuenta_glosa;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getResolucion(){
		return resolucion;
	}
	public byte[] getLogo(){
		return logo;
	}
	public String getExtension(){
		return extension;
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
	public String getCuenta_cierre(){
		return cuenta_cierre;
	}
	public String getCuenta_cobrar(){
		return cuenta_cobrar;
	}
	public String getCuenta_pagar(){
		return cuenta_pagar;
	}
	public String getCuenta_caja(){
		return cuenta_caja;
	}
	public String getCuenta_ingreso(){
		return cuenta_ingreso;
	}
	public String getCuenta_copago(){
		return cuenta_copago;
	}
	public String getAnio(){
		return anio;
	}
	public String getMes(){
		return mes;
	}
	public String getCuenta_inventario(){
		return cuenta_inventario;
	}
	public String getCuenta_costo(){
		return cuenta_costo;
	}
	public String getLeyenda_factura(){
		return leyenda_factura;
	}
	public boolean getContabiliza_aut(){
		return contabiliza_aut;
	}
	public String getCuenta_devolucion(){
		return cuenta_devolucion;
	}
	public String getCuenta_glosa(){
		return cuenta_glosa;
	}


	public String getCuenta_cobrar_radicada() {
		return cuenta_cobrar_radicada;
	}


	public void setCuenta_cobrar_radicada(String cuenta_cobrar_radicada) {
		this.cuenta_cobrar_radicada = cuenta_cobrar_radicada;
	}
	
	public String getCodigo_contabilidad_medicamentos() {
		return codigo_contabilidad_medicamentos;
	}

	public void setCodigo_contabilidad_medicamentos(
			String codigo_contabilidad_medicamentos) {
		this.codigo_contabilidad_medicamentos = codigo_contabilidad_medicamentos;
	}

	public String getCodigo_contabilidad_materiales() {
		return codigo_contabilidad_materiales;
	}

	public void setCodigo_contabilidad_materiales(
			String codigo_contabilidad_materiales) {
		this.codigo_contabilidad_materiales = codigo_contabilidad_materiales;
	}

	public String getCodigo_contabilidad_traslados() {
		return codigo_contabilidad_traslados;
	}

	public void setCodigo_contabilidad_traslados(
			String codigo_contabilidad_traslados) {
		this.codigo_contabilidad_traslados = codigo_contabilidad_traslados;
	}

	public String getCodigo_contabilidad_estancias() {
		return codigo_contabilidad_estancias;
	}

	public void setCodigo_contabilidad_estancias(
			String codigo_contabilidad_estancias) {
		this.codigo_contabilidad_estancias = codigo_contabilidad_estancias;
	}

	public String getCodigo_contabilidad_honorarios() {
		return codigo_contabilidad_honorarios;
	}

	public void setCodigo_contabilidad_honorarios(
			String codigo_contabilidad_honorarios) {
		this.codigo_contabilidad_honorarios = codigo_contabilidad_honorarios;
	}
	
	public String getCodigo_tercero_icbf() {
		return codigo_tercero_icbf;
	}

	public void setCodigo_tercero_icbf(String codigo_tercero_icbf) {
		this.codigo_tercero_icbf = codigo_tercero_icbf;
	}

	public String getCodigo_tercero_sena() {
		return codigo_tercero_sena;
	}

	public void setCodigo_tercero_sena(String codigo_tercero_sena) {
		this.codigo_tercero_sena = codigo_tercero_sena;
	}

	public String getCuenta_margen_gasto_capita() {
		return cuenta_margen_gasto_capita;
	}

	public void setCuenta_margen_gasto_capita(String cuenta_margen_gasto_capita) {
		this.cuenta_margen_gasto_capita = cuenta_margen_gasto_capita;
	}

	public String getCuenta_margen_ingreso_capita() {
		return cuenta_margen_ingreso_capita;
	}

	public void setCuenta_margen_ingreso_capita(
			String cuenta_margen_ingreso_capita) {
		this.cuenta_margen_ingreso_capita = cuenta_margen_ingreso_capita;
	}


	public boolean getContabilizar_servicios_capita() {
		return contabilizar_servicios_capita;
	}


	public void setContabilizar_servicios_capita(
			boolean contabilizar_servicios_capita) {
		this.contabilizar_servicios_capita = contabilizar_servicios_capita;
	}


	public String getCuenta_particular() {
		return cuenta_particular;
	}


	public void setCuenta_particular(String cuenta_particular) {
		this.cuenta_particular = cuenta_particular;
	}
}
