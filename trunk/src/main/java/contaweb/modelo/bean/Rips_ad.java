/*
 * Rips_ad.java
 * 
 * Generado Automaticamente .
 * Ing. Luis Miguel Hernández Pérez
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

import com.framework.util.ConvertidorRipAObjeto.Convertir;

@Alias("rips_ad")
public class Rips_ad implements Serializable {

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_comprobante;
	private  String codigo_documento;
	private  Integer id;
	@Convertir(pos = 2)private  String codigo_prestador;
	@Convertir(pos = 3)private  String codigo_concepto;
	@Convertir(pos = 4)private  Integer cantidad;
	@Convertir(pos = 5)private  double valor_unitario;
	@Convertir(pos = 6)private  double valor_total;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  String creacion_user;
	private  String ultimo_user;
	@Convertir(pos = 1)private  String nro_factura;

	/*** Constructor Por Defecto ***/
	public Rips_ad(){}


	/*** Sobre carga de Constructor ***/
	public Rips_ad(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_comprobante,
		String codigo_documento,
		Integer id,
		String codigo_prestador,
		String codigo_concepto,
		Integer cantidad,
		double valor_unitario,
		double valor_total,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		String creacion_user,
		String ultimo_user,
		String nro_factura){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
		this.id = id;
		this.codigo_prestador = codigo_prestador;
		this.codigo_concepto = codigo_concepto;
		this.cantidad = cantidad;
		this.valor_unitario = valor_unitario;
		this.valor_total = valor_total;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.nro_factura = nro_factura;
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
	public void setId(Integer id){
		this.id=id;
	}
	public void setCodigo_prestador(String codigo_prestador){
		this.codigo_prestador=codigo_prestador;
	}
	public void setCodigo_concepto(String codigo_concepto){
		this.codigo_concepto=codigo_concepto;
	}
	public void setCantidad(Integer cantidad){
		this.cantidad=cantidad;
	}
	public void setValor_unitario(double valor_unitario){
		this.valor_unitario=valor_unitario;
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
	public void setCreacion_user(String creacion_user){
		this.creacion_user=creacion_user;
	}
	public void setUltimo_user(String ultimo_user){
		this.ultimo_user=ultimo_user;
	}
	public void setNro_factura(String nro_factura){
		this.nro_factura=nro_factura;
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
	public Integer getId(){
		return id;
	}
	public String getCodigo_prestador(){
		return codigo_prestador;
	}
	public String getCodigo_concepto(){
		return codigo_concepto;
	}
	public Integer getCantidad(){
		return cantidad;
	}
	public double getValor_unitario(){
		return valor_unitario;
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
	public String getCreacion_user(){
		return creacion_user;
	}
	public String getUltimo_user(){
		return ultimo_user;
	}
	public String getNro_factura(){
		return nro_factura;
	}
}