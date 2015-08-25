/*
 * Rips_au.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.framework.util.ConvertidorRipAObjeto.Convertir;

public class Rips_au implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	@Convertir(pos = 1) private  String nro_factura;
	@Convertir(pos = 2) private  String codigo_factura;
	@Convertir(pos = 3) private  String codigo_prestador;
	@Convertir(pos = 4) private  String tipo_id;
	@Convertir(pos = 5) private  String nro_id;
	@Convertir(pos = 6) private  Timestamp fecha_ingreso;
	@Convertir(pos = 7) private  String hora_ingreso;
	@Convertir(pos = 8) private  String nro_autorizacion;
	@Convertir(pos = 9) private  String causa_externa;
	@Convertir(pos = 10) private  String diagnostico_salida;
	@Convertir(pos = 11) private  String diagnostico_relacionado_1_salida;
	@Convertir(pos = 12) private  String diagnostico_relacionado_2_salida;
	@Convertir(pos = 13) private  String diagnostico_relacionado_3_salida;
	@Convertir(pos = 14) private  String destino_usuario_salidad;
	@Convertir(pos = 15) private  String estado_salida;
	@Convertir(pos = 16) private  String causa_basica_muerte;
	@Convertir(pos = 17) private  String fecha_salida;
	@Convertir(pos = 18) private  String hora_salida;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  String creacion_user;
	private  String ultimo_user;
	private  Integer id;
	private  String codigo_comprobante;
	private  String codigo_documento;

	/*** Constructor Por Defecto ***/
	public Rips_au(){}


	/*** Sobre carga de Constructor ***/
	public Rips_au(
		String codigo_empresa,
		String codigo_sucursal,
		String nro_factura,
		String codigo_factura,
		String codigo_prestador,
		String tipo_id,
		String nro_id,
		Timestamp fecha_ingreso,
		String hora_ingreso,
		String nro_autorizacion,
		String causa_externa,
		String diagnostico_salida,
		String diagnostico_relacionado_1_salida,
		String diagnostico_relacionado_2_salida,
		String diagnostico_relacionado_3_salida,
		String destino_usuario_salidad,
		String estado_salida,
		String causa_basica_muerte,
		String fecha_salida,
		String hora_salida,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		String creacion_user,
		String ultimo_user,
		Integer id,
		String codigo_comprobante,
		String codigo_documento){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.nro_factura = nro_factura;
		this.codigo_factura = codigo_factura;
		this.codigo_prestador = codigo_prestador;
		this.tipo_id = tipo_id;
		this.nro_id = nro_id;
		this.fecha_ingreso = fecha_ingreso;
		this.hora_ingreso = hora_ingreso;
		this.nro_autorizacion = nro_autorizacion;
		this.causa_externa = causa_externa;
		this.diagnostico_salida = diagnostico_salida;
		this.diagnostico_relacionado_1_salida = diagnostico_relacionado_1_salida;
		this.diagnostico_relacionado_2_salida = diagnostico_relacionado_2_salida;
		this.diagnostico_relacionado_3_salida = diagnostico_relacionado_3_salida;
		this.destino_usuario_salidad = destino_usuario_salidad;
		this.estado_salida = estado_salida;
		this.causa_basica_muerte = causa_basica_muerte;
		this.fecha_salida = fecha_salida;
		this.hora_salida = hora_salida;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.id = id;
		this.codigo_comprobante = codigo_comprobante;
		this.codigo_documento = codigo_documento;
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
	public void setNro_factura(String nro_factura){
		this.nro_factura=nro_factura;
	}
	public void setCodigo_factura(String codigo_factura){
		this.codigo_factura=codigo_factura;
	}
	public void setCodigo_prestador(String codigo_prestador){
		this.codigo_prestador=codigo_prestador;
	}
	public void setTipo_id(String tipo_id){
		this.tipo_id=tipo_id;
	}
	public void setNro_id(String nro_id){
		this.nro_id=nro_id;
	}
	public void setFecha_ingreso(Timestamp fecha_ingreso){
		this.fecha_ingreso=fecha_ingreso;
	}
	public void setHora_ingreso(String hora_ingreso){
		this.hora_ingreso=hora_ingreso;
	}
	public void setNro_autorizacion(String nro_autorizacion){
		this.nro_autorizacion=nro_autorizacion;
	}
	public void setCausa_externa(String causa_externa){
		this.causa_externa=causa_externa;
	}
	public void setDiagnostico_salida(String diagnostico_salida){
		this.diagnostico_salida=diagnostico_salida;
	}
	public void setDiagnostico_relacionado_1_salida(String diagnostico_relacionado_1_salida){
		this.diagnostico_relacionado_1_salida=diagnostico_relacionado_1_salida;
	}
	public void setDiagnostico_relacionado_2_salida(String diagnostico_relacionado_2_salida){
		this.diagnostico_relacionado_2_salida=diagnostico_relacionado_2_salida;
	}
	public void setDiagnostico_relacionado_3_salida(String diagnostico_relacionado_3_salida){
		this.diagnostico_relacionado_3_salida=diagnostico_relacionado_3_salida;
	}
	public void setDestino_usuario_salidad(String destino_usuario_salidad){
		this.destino_usuario_salidad=destino_usuario_salidad;
	}
	public void setEstado_salida(String estado_salida){
		this.estado_salida=estado_salida;
	}
	public void setCausa_basica_muerte(String causa_basica_muerte){
		this.causa_basica_muerte=causa_basica_muerte;
	}
	public void setFecha_salida(String fecha_salida){
		this.fecha_salida=fecha_salida;
	}
	public void setHora_salida(String hora_salida){
		this.hora_salida=hora_salida;
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
	public void setId(Integer id){
		this.id=id;
	}
	public void setCodigo_comprobante(String codigo_comprobante){
		this.codigo_comprobante=codigo_comprobante;
	}
	public void setCodigo_documento(String codigo_documento){
		this.codigo_documento=codigo_documento;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getNro_factura(){
		return nro_factura;
	}
	public String getCodigo_factura(){
		return codigo_factura;
	}
	public String getCodigo_prestador(){
		return codigo_prestador;
	}
	public String getTipo_id(){
		return tipo_id;
	}
	public String getNro_id(){
		return nro_id;
	}
	public Timestamp getFecha_ingreso(){
		return fecha_ingreso;
	}
	public String getHora_ingreso(){
		return hora_ingreso;
	}
	public String getNro_autorizacion(){
		return nro_autorizacion;
	}
	public String getCausa_externa(){
		return causa_externa;
	}
	public String getDiagnostico_salida(){
		return diagnostico_salida;
	}
	public String getDiagnostico_relacionado_1_salida(){
		return diagnostico_relacionado_1_salida;
	}
	public String getDiagnostico_relacionado_2_salida(){
		return diagnostico_relacionado_2_salida;
	}
	public String getDiagnostico_relacionado_3_salida(){
		return diagnostico_relacionado_3_salida;
	}
	public String getDestino_usuario_salidad(){
		return destino_usuario_salidad;
	}
	public String getEstado_salida(){
		return estado_salida;
	}
	public String getCausa_basica_muerte(){
		return causa_basica_muerte;
	}
	public String getFecha_salida(){
		return fecha_salida;
	}
	public String getHora_salida(){
		return hora_salida;
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
	public Integer getId(){
		return id;
	}
	public String getCodigo_comprobante(){
		return codigo_comprobante;
	}
	public String getCodigo_documento(){
		return codigo_documento;
	}
}