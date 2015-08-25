/*
 * Cartera.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("cartera")
public class Cartera{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_comprobante;
	private  String codigo_documento;
	private  String nro_cuota;
	private  Timestamp vencimiento;
	private  double abono;
	private  double saldos;
	private  double total;
	private  String tipo;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  double dto_cuota;
	private  double interes;
	private  String manejo;
	private  String codigo_tercero;
	private  String cuenta;
	private  String concepto;
	private  String anio;
	private  String grado;
	private  String documento_soporte;
	private String detalle;

	/*** Constructor Por Defecto ***/
	public Cartera(){
		anio = "";
		grado = "";
		documento_soporte = "";
		setDetalle("");
	}


	/*** Sobre carga de Constructor ***/
	public Cartera(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_comprobante,
		String codigo_documento,
		String nro_cuota,
		Timestamp vencimiento,
		double abono,
		double saldos,
		double total,
		String tipo,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		double dto_cuota,
		double interes,
		String manejo,
		String codigo_tercero,
		String cuenta,
		String concepto,
		String anio,
		String grado){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
		this.nro_cuota = nro_cuota;
		this.vencimiento = vencimiento;
		this.abono = abono;
		this.saldos = saldos;
		this.total = total;
		this.tipo = tipo;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.dto_cuota = dto_cuota;
		this.interes = interes;
		this.manejo = manejo;
		this.codigo_tercero = codigo_tercero;
		this.cuenta = cuenta;
		this.concepto = concepto;
		this.anio = anio;
		this.grado = grado;
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
	public void setNro_cuota(String nro_cuota){
		this.nro_cuota=nro_cuota;
	}
	public void setVencimiento(Timestamp vencimiento){
		this.vencimiento=vencimiento;
	}
	public void setAbono(double abono){
		this.abono=abono;
	}
	public void setSaldos(double saldos){
		this.saldos=saldos;
	}
	public void setTotal(double total){
		this.total=total;
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
	public void setDto_cuota(double dto_cuota){
		this.dto_cuota=dto_cuota;
	}
	public void setInteres(double interes){
		this.interes=interes;
	}
	public void setManejo(String manejo){
		this.manejo=manejo;
	}
	public void setCodigo_tercero(String codigo_tercero){
		this.codigo_tercero=codigo_tercero;
	}
	public void setCuenta(String cuenta){
		this.cuenta=cuenta;
	}
	public void setConcepto(String concepto){
		this.concepto=concepto;
	}
	public void setAnio(String anio){
		this.anio=anio;
	}
	public void setGrado(String grado){
		this.grado=grado;
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
	public String getNro_cuota(){
		return nro_cuota;
	}
	public Timestamp getVencimiento(){
		return vencimiento;
	}
	public double getAbono(){
		return abono;
	}
	public double getSaldos(){
		return saldos;
	}
	public double getTotal(){
		return total;
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
	public double getDto_cuota(){
		return dto_cuota;
	}
	public double getInteres(){
		return interes;
	}
	public String getManejo(){
		return manejo;
	}
	public String getCodigo_tercero(){
		return codigo_tercero;
	}
	public String getCuenta(){
		return cuenta;
	}
	public String getConcepto(){
		return concepto;
	}
	public String getAnio(){
		return anio;
	}
	public String getGrado(){
		return grado;
	}


	public String getDocumento_soporte() {
		return documento_soporte;
	}


	public void setDocumento_soporte(String documento_soporte) {
		this.documento_soporte = documento_soporte;
	}


	public String getDetalle() {
		return detalle;
	}


	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
}
