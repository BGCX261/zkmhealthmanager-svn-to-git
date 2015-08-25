/*
 * Detalle_nota.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("detalle_nota")
public class Detalle_nota{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_comprobante;
	private  String codigo_documento;
	private  String consecutivo;
	private  String codigo_cuenta;
	private  String concepto;
	private  String tercero;
	private  String cheque;
	private  double debito;
	private  double credito;
	private  String c_costo;
	private  String abona;
	private  Timestamp vence;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String banco;
	private  double iva;
	private  String cheque_consignado;
	private  String tipo;
	private  String concepto_abona;

	/*** Constructor Por Defecto ***/
	public Detalle_nota(){
		banco = "";
        iva = 0;
        cheque_consignado = "N";
        tipo = "";
        concepto_abona = "";
	}


	/*** Sobre carga de Constructor ***/
	public Detalle_nota(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_comprobante,
		String codigo_documento,
		String consecutivo,
		String codigo_cuenta,
		String concepto,
		String tercero,
		String cheque,
		double debito,
		double credito,
		String c_costo,
		String abona,
		Timestamp vence,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String banco,
		double iva,
		String cheque_consignado,
		String tipo,
		String concepto_abona){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
		this.consecutivo = consecutivo;
		this.codigo_cuenta = codigo_cuenta;
		this.concepto = concepto;
		this.tercero = tercero;
		this.cheque = cheque;
		this.debito = debito;
		this.credito = credito;
		this.c_costo = c_costo;
		this.abona = abona;
		this.vence = vence;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.banco = banco;
		this.iva = iva;
		this.cheque_consignado = cheque_consignado;
		this.tipo = tipo;
		this.concepto_abona = concepto_abona;
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
	public void setCodigo_cuenta(String codigo_cuenta){
		this.codigo_cuenta=codigo_cuenta;
	}
	public void setConcepto(String concepto){
		this.concepto=concepto;
	}
	public void setTercero(String tercero){
		this.tercero=tercero;
	}
	public void setCheque(String cheque){
		this.cheque=cheque;
	}
	public void setDebito(double debito){
		this.debito=debito;
	}
	public void setCredito(double credito){
		this.credito=credito;
	}
	public void setC_costo(String c_costo){
		this.c_costo=c_costo;
	}
	public void setAbona(String abona){
		this.abona=abona;
	}
	public void setVence(Timestamp vence){
		this.vence=vence;
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
	public void setBanco(String banco){
		this.banco=banco;
	}
	public void setIva(double iva){
		this.iva=iva;
	}
	public void setCheque_consignado(String cheque_consignado){
		this.cheque_consignado=cheque_consignado;
	}
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	public void setConcepto_abona(String concepto_abona){
		this.concepto_abona=concepto_abona;
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
	public String getCodigo_cuenta(){
		return codigo_cuenta;
	}
	public String getConcepto(){
		return concepto;
	}
	public String getTercero(){
		return tercero;
	}
	public String getCheque(){
		return cheque;
	}
	public double getDebito(){
		return debito;
	}
	public double getCredito(){
		return credito;
	}
	public String getC_costo(){
		return c_costo;
	}
	public String getAbona(){
		return abona;
	}
	public Timestamp getVence(){
		return vence;
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
	public String getBanco(){
		return banco;
	}
	public double getIva(){
		return iva;
	}
	public String getCheque_consignado(){
		return cheque_consignado;
	}
	public String getTipo(){
		return tipo;
	}
	public String getConcepto_abona(){
		return concepto_abona;
	}
}
