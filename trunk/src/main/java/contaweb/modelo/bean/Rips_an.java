/*
 * Rips_an.java
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

public class Rips_an implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  Integer id;
	@Convertir(pos = 1) private  String nro_factura;
	@Convertir(pos = 2) private  String codigo_factur;
	@Convertir(pos = 3) private  String codigo_prestador;
	@Convertir(pos = 4) private  String tipo_id_madre;
	@Convertir(pos = 5) private  String nro_id_madre;
	@Convertir(pos = 6) private  String fecha_nacimiento;
	@Convertir(pos = 7) private  String hora_nacimiento;
	@Convertir(pos = 8) private  String edad_gestacional;
	@Convertir(pos = 9) private  String control_prenatal;
	@Convertir(pos = 10) private  String sexo;
	@Convertir(pos = 11) private  String peso;
	@Convertir(pos = 12) private  String diagnostico_recien_nacido;
	@Convertir(pos = 13) private  String causa_basica_muerte;
	@Convertir(pos = 14) private  String fecha_muerte;
	@Convertir(pos = 15) private  String hora_muerte_recien_nacido;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  String creacion_user;
	private  String ultimo_user;
	private  String codigo_comprobante;
	private  String codigo_documento;

	/*** Constructor Por Defecto ***/
	public Rips_an(){}


	/*** Sobre carga de Constructor ***/
	public Rips_an(
		String codigo_empresa,
		String codigo_sucursal,
		Integer id,
		String nro_factura,
		String codigo_factur,
		String codigo_prestador,
		String tipo_id_madre,
		String nro_id_madre,
		String fecha_nacimiento,
		String hora_nacimiento,
		String edad_gestacional,
		String control_prenatal,
		String sexo,
		String peso,
		String diagnostico_recien_nacido,
		String causa_basica_muerte,
		String fecha_muerte,
		String hora_muerte_recien_nacido,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		String creacion_user,
		String ultimo_user,
		String codigo_comprobante,
		String codigo_documento){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.id = id;
		this.nro_factura = nro_factura;
		this.codigo_factur = codigo_factur;
		this.codigo_prestador = codigo_prestador;
		this.tipo_id_madre = tipo_id_madre;
		this.nro_id_madre = nro_id_madre;
		this.fecha_nacimiento = fecha_nacimiento;
		this.hora_nacimiento = hora_nacimiento;
		this.edad_gestacional = edad_gestacional;
		this.control_prenatal = control_prenatal;
		this.sexo = sexo;
		this.peso = peso;
		this.diagnostico_recien_nacido = diagnostico_recien_nacido;
		this.causa_basica_muerte = causa_basica_muerte;
		this.fecha_muerte = fecha_muerte;
		this.hora_muerte_recien_nacido = hora_muerte_recien_nacido;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
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
	public void setId(Integer id){
		this.id=id;
	}
	public void setNro_factura(String nro_factura){
		this.nro_factura=nro_factura;
	}
	public void setCodigo_factur(String codigo_factur){
		this.codigo_factur=codigo_factur;
	}
	public void setCodigo_prestador(String codigo_prestador){
		this.codigo_prestador=codigo_prestador;
	}
	public void setTipo_id_madre(String tipo_id_madre){
		this.tipo_id_madre=tipo_id_madre;
	}
	public void setNro_id_madre(String nro_id_madre){
		this.nro_id_madre=nro_id_madre;
	}
	public void setFecha_nacimiento(String fecha_nacimiento){
		this.fecha_nacimiento=fecha_nacimiento;
	}
	public void setHora_nacimiento(String hora_nacimiento){
		this.hora_nacimiento=hora_nacimiento;
	}
	public void setEdad_gestacional(String edad_gestacional){
		this.edad_gestacional=edad_gestacional;
	}
	public void setControl_prenatal(String control_prenatal){
		this.control_prenatal=control_prenatal;
	}
	public void setSexo(String sexo){
		this.sexo=sexo;
	}
	public void setPeso(String peso){
		this.peso=peso;
	}
	public void setDiagnostico_recien_nacido(String diagnostico_recien_nacido){
		this.diagnostico_recien_nacido=diagnostico_recien_nacido;
	}
	public void setCausa_basica_muerte(String causa_basica_muerte){
		this.causa_basica_muerte=causa_basica_muerte;
	}
	public void setFecha_muerte(String fecha_muerte){
		this.fecha_muerte=fecha_muerte;
	}
	public void setHora_muerte_recien_nacido(String hora_muerte_recien_nacido){
		this.hora_muerte_recien_nacido=hora_muerte_recien_nacido;
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
	public Integer getId(){
		return id;
	}
	public String getNro_factura(){
		return nro_factura;
	}
	public String getCodigo_factur(){
		return codigo_factur;
	}
	public String getCodigo_prestador(){
		return codigo_prestador;
	}
	public String getTipo_id_madre(){
		return tipo_id_madre;
	}
	public String getNro_id_madre(){
		return nro_id_madre;
	}
	public String getFecha_nacimiento(){
		return fecha_nacimiento;
	}
	public String getHora_nacimiento(){
		return hora_nacimiento;
	}
	public String getEdad_gestacional(){
		return edad_gestacional;
	}
	public String getControl_prenatal(){
		return control_prenatal;
	}
	public String getSexo(){
		return sexo;
	}
	public String getPeso(){
		return peso;
	}
	public String getDiagnostico_recien_nacido(){
		return diagnostico_recien_nacido;
	}
	public String getCausa_basica_muerte(){
		return causa_basica_muerte;
	}
	public String getFecha_muerte(){
		return fecha_muerte;
	}
	public String getHora_muerte_recien_nacido(){
		return hora_muerte_recien_nacido;
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
	public String getCodigo_comprobante(){
		return codigo_comprobante;
	}
	public String getCodigo_documento(){
		return codigo_documento;
	}
}