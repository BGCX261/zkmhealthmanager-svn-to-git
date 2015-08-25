/*
 * Detalle_contabilizacion.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("detalle_contabilizacion")
public class Detalle_contabilizacion{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo;
	private  String codigo_cuenta;
	private  String menajo;
	private  String tipo_contribuyente;
	private  String doc;
	private  String forma_pago;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String tipo_aseguradora;

	/*** Constructor Por Defecto ***/
	public Detalle_contabilizacion(){}


	/*** Sobre carga de Constructor ***/
	public Detalle_contabilizacion(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo,
		String codigo_cuenta,
		String menajo,
		String tipo_contribuyente,
		String doc,
		String forma_pago,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String tipo_aseguradora){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo = codigo;
		this.codigo_cuenta = codigo_cuenta;
		this.menajo = menajo;
		this.tipo_contribuyente = tipo_contribuyente;
		this.doc = doc;
		this.forma_pago = forma_pago;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.tipo_aseguradora = tipo_aseguradora;
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
	public void setCodigo(String codigo){
		this.codigo=codigo;
	}
	public void setCodigo_cuenta(String codigo_cuenta){
		this.codigo_cuenta=codigo_cuenta;
	}
	public void setMenajo(String menajo){
		this.menajo=menajo;
	}
	public void setTipo_contribuyente(String tipo_contribuyente){
		this.tipo_contribuyente=tipo_contribuyente;
	}
	public void setDoc(String doc){
		this.doc=doc;
	}
	public void setForma_pago(String forma_pago){
		this.forma_pago=forma_pago;
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
	public void setTipo_aseguradora(String tipo_aseguradora){
		this.tipo_aseguradora=tipo_aseguradora;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getCodigo(){
		return codigo;
	}
	public String getCodigo_cuenta(){
		return codigo_cuenta;
	}
	public String getMenajo(){
		return menajo;
	}
	public String getTipo_contribuyente(){
		return tipo_contribuyente;
	}
	public String getDoc(){
		return doc;
	}
	public String getForma_pago(){
		return forma_pago;
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
	public String getTipo_aseguradora(){
		return tipo_aseguradora;
	}
}
