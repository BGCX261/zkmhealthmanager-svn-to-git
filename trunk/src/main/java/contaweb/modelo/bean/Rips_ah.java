/*
 * Rips_ah.java
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

public class Rips_ah implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	@Convertir(pos = 1)  private  String nro_factura;
	private  Integer id;
	@Convertir(pos = 2)  private  String codigo_prestador;
	@Convertir(pos = 3)  private  String tipo_id;
	@Convertir(pos = 4)  private  String nro_id;
	@Convertir(pos = 5)  private  String via_ingreso;
	@Convertir(pos = 6)  private  String fecha_ingreso;
	@Convertir(pos = 7)  private  String hora_ingreso;
	@Convertir(pos = 8)  private  String nro_autorizacion;
	@Convertir(pos = 9)  private  String causa_externa;
	@Convertir(pos = 10)  private  String diagnostico_principal_ingreso;
	@Convertir(pos = 11)  private  String diagnostico_principal_egreso;
	@Convertir(pos = 12)  private  String diagnostico_principal_egreso_1;
	@Convertir(pos = 13)  private  String diagnostico_principal_egreso_2;
	@Convertir(pos = 14)  private  String diagnostico_principal_egreso_3;
	@Convertir(pos = 15)  private  String diagnostico_complicacion;
	@Convertir(pos = 16)  private  String estado_salida;
	@Convertir(pos = 17)  private  String diagnostico_causa_basica;
	@Convertir(pos = 18)  private  Timestamp fecha_egreso;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  String creacion_user;
	private  String ultimo_user;
	@Convertir(pos = 19) private  String hora_egreso;
	private  String codigo_comprobante;
	private  String codigo_documento;

	/*** Constructor Por Defecto ***/
	public Rips_ah(){}


	/*** Sobre carga de Constructor ***/
	public Rips_ah(
		String codigo_empresa,
		String codigo_sucursal,
		String nro_factura,
		Integer id,
		String codigo_prestador,
		String tipo_id,
		String nro_id,
		String via_ingreso,
		String fecha_ingreso,
		String hora_ingreso,
		String nro_autorizacion,
		String causa_externa,
		String diagnostico_principal_ingreso,
		String diagnostico_principal_egreso,
		String diagnostico_principal_egreso_2,
		String diagnostico_principal_egreso_3,
		String diagnostico_complicacion,
		String estado_salida,
		String diagnostico_causa_basica,
		Timestamp fecha_egreso,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		String creacion_user,
		String ultimo_user,
		String hora_egreso,
		String diagnostico_principal_egreso_1,
		String codigo_comprobante,
		String codigo_documento){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.nro_factura = nro_factura;
		this.id = id;
		this.codigo_prestador = codigo_prestador;
		this.tipo_id = tipo_id;
		this.nro_id = nro_id;
		this.via_ingreso = via_ingreso;
		this.fecha_ingreso = fecha_ingreso;
		this.hora_ingreso = hora_ingreso;
		this.nro_autorizacion = nro_autorizacion;
		this.causa_externa = causa_externa;
		this.diagnostico_principal_ingreso = diagnostico_principal_ingreso;
		this.diagnostico_principal_egreso = diagnostico_principal_egreso;
		this.diagnostico_principal_egreso_2 = diagnostico_principal_egreso_2;
		this.diagnostico_principal_egreso_3 = diagnostico_principal_egreso_3;
		this.diagnostico_complicacion = diagnostico_complicacion;
		this.estado_salida = estado_salida;
		this.diagnostico_causa_basica = diagnostico_causa_basica;
		this.fecha_egreso = fecha_egreso;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.hora_egreso = hora_egreso;
		this.diagnostico_principal_egreso_1 = diagnostico_principal_egreso_1;
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
	public void setId(Integer id){
		this.id=id;
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
	public void setVia_ingreso(String via_ingreso){
		this.via_ingreso=via_ingreso;
	}
	public void setFecha_ingreso(String fecha_ingreso){
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
	public void setDiagnostico_principal_ingreso(String diagnostico_principal_ingreso){
		this.diagnostico_principal_ingreso=diagnostico_principal_ingreso;
	}
	public void setDiagnostico_principal_egreso(String diagnostico_principal_egreso){
		this.diagnostico_principal_egreso=diagnostico_principal_egreso;
	}
	public void setDiagnostico_principal_egreso_2(String diagnostico_principal_egreso_2){
		this.diagnostico_principal_egreso_2=diagnostico_principal_egreso_2;
	}
	public void setDiagnostico_principal_egreso_3(String diagnostico_principal_egreso_3){
		this.diagnostico_principal_egreso_3=diagnostico_principal_egreso_3;
	}
	public void setDiagnostico_complicacion(String diagnostico_complicacion){
		this.diagnostico_complicacion=diagnostico_complicacion;
	}
	public void setEstado_salida(String estado_salida){
		this.estado_salida=estado_salida;
	}
	public void setDiagnostico_causa_basica(String diagnostico_causa_basica){
		this.diagnostico_causa_basica=diagnostico_causa_basica;
	}
	public void setFecha_egreso(Timestamp fecha_egreso){
		this.fecha_egreso=fecha_egreso;
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
	public void setHora_egreso(String hora_egreso){
		this.hora_egreso=hora_egreso;
	}
	public void setDiagnostico_principal_egreso_1(String diagnostico_principal_egreso_1){
		this.diagnostico_principal_egreso_1=diagnostico_principal_egreso_1;
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
	public Integer getId(){
		return id;
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
	public String getVia_ingreso(){
		return via_ingreso;
	}
	public String getFecha_ingreso(){
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
	public String getDiagnostico_principal_ingreso(){
		return diagnostico_principal_ingreso;
	}
	public String getDiagnostico_principal_egreso(){
		return diagnostico_principal_egreso;
	}
	public String getDiagnostico_principal_egreso_2(){
		return diagnostico_principal_egreso_2;
	}
	public String getDiagnostico_principal_egreso_3(){
		return diagnostico_principal_egreso_3;
	}
	public String getDiagnostico_complicacion(){
		return diagnostico_complicacion;
	}
	public String getEstado_salida(){
		return estado_salida;
	}
	public String getDiagnostico_causa_basica(){
		return diagnostico_causa_basica;
	}
	public Timestamp getFecha_egreso(){
		return fecha_egreso;
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
	public String getHora_egreso(){
		return hora_egreso;
	}
	public String getDiagnostico_principal_egreso_1(){
		return diagnostico_principal_egreso_1;
	}
	public String getCodigo_comprobante(){
		return codigo_comprobante;
	}
	public String getCodigo_documento(){
		return codigo_documento;
	}
}