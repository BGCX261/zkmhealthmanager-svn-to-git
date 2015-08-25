/*
 * Inventario.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("inventario")
public class Inventario{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_comprobante;
	private  String codigo_documento;
	private  String codigo_tercero;
	private  String documento_externo;
	private  String codigo_empleado;
	private  Timestamp fecha;
	private  String motivo_inventario;
	private  String descripcion;
	private  String observacion;
	private  String tipo;
	private  String estado;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String prefijo;
	private  String anio;
	private  String mes;
	private  String clasificacion;
	private  String verificado;
	private  String codigo_solicitud;
	private  String tipo_rips;
	private  String codigo_receta;

	/*** Constructor Por Defecto ***/
	public Inventario(){}


	/*** Sobre carga de Constructor ***/
	public Inventario(
		String codigo_empresa,
		String codigo_sucursal,
		String codigo_comprobante,
		String codigo_documento,
		String codigo_tercero,
		String documento_externo,
		String codigo_empleado,
		Timestamp fecha,
		String motivo_inventario,
		String descripcion,
		String observacion,
		String tipo,
		String estado,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String prefijo,
		String anio,
		String mes,
		String clasificacion,
		String verificado,
		String codigo_solicitud,
		String tipo_rips,
		String codigo_receta){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
		this.codigo_tercero = codigo_tercero;
		this.documento_externo = documento_externo;
		this.codigo_empleado = codigo_empleado;
		this.fecha = fecha;
		this.motivo_inventario = motivo_inventario;
		this.descripcion = descripcion;
		this.observacion = observacion;
		this.tipo = tipo;
		this.estado = estado;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.prefijo = prefijo;
		this.anio = anio;
		this.mes = mes;
		this.clasificacion = clasificacion;
		this.verificado = verificado;
		this.codigo_solicitud = codigo_solicitud;
		this.tipo_rips = tipo_rips;
		this.codigo_receta = codigo_receta;
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
	public void setCodigo_tercero(String codigo_tercero){
		this.codigo_tercero=codigo_tercero;
	}
	public void setDocumento_externo(String documento_externo){
		this.documento_externo=documento_externo;
	}
	public void setCodigo_empleado(String codigo_empleado){
		this.codigo_empleado=codigo_empleado;
	}
	public void setFecha(Timestamp fecha){
		this.fecha=fecha;
	}
	public void setMotivo_inventario(String motivo_inventario){
		this.motivo_inventario=motivo_inventario;
	}
	public void setDescripcion(String descripcion){
		this.descripcion=descripcion;
	}
	public void setObservacion(String observacion){
		this.observacion=observacion;
	}
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	public void setEstado(String estado){
		this.estado=estado;
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
	public void setPrefijo(String prefijo){
		this.prefijo=prefijo;
	}
	public void setAnio(String anio){
		this.anio=anio;
	}
	public void setMes(String mes){
		this.mes=mes;
	}
	public void setClasificacion(String clasificacion){
		this.clasificacion=clasificacion;
	}
	public void setVerificado(String verificado){
		this.verificado=verificado;
	}
	public void setCodigo_solicitud(String codigo_solicitud){
		this.codigo_solicitud=codigo_solicitud;
	}
	public void setTipo_rips(String tipo_rips){
		this.tipo_rips=tipo_rips;
	}
	public void setCodigo_receta(String codigo_receta){
		this.codigo_receta=codigo_receta;
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
	public String getCodigo_tercero(){
		return codigo_tercero;
	}
	public String getDocumento_externo(){
		return documento_externo;
	}
	public String getCodigo_empleado(){
		return codigo_empleado;
	}
	public Timestamp getFecha(){
		return fecha;
	}
	public String getMotivo_inventario(){
		return motivo_inventario;
	}
	public String getDescripcion(){
		return descripcion;
	}
	public String getObservacion(){
		return observacion;
	}
	public String getTipo(){
		return tipo;
	}
	public String getEstado(){
		return estado;
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
	public String getPrefijo(){
		return prefijo;
	}
	public String getAnio(){
		return anio;
	}
	public String getMes(){
		return mes;
	}
	public String getClasificacion(){
		return clasificacion;
	}
	public String getVerificado(){
		return verificado;
	}
	public String getCodigo_solicitud(){
		return codigo_solicitud;
	}
	public String getTipo_rips(){
		return tipo_rips;
	}
	public String getCodigo_receta(){
		return codigo_receta;
	}
}
