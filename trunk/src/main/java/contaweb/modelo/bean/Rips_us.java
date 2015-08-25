/*
 * Rips_us.java
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

public class Rips_us implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  Integer id;
	@Convertir(pos = 3) private  String codigo_administradora;
	@Convertir(pos = 4) private  String tipo_usuario;
	@Convertir(pos = 5) private  String primer_apellido;
	@Convertir(pos = 6) private  String segundo_apellido;
	@Convertir(pos = 7) private  String primer_nombre;
	@Convertir(pos = 8) private  String segundo_nombre;
	@Convertir(pos = 9) private  String edad;
	@Convertir(pos = 10) private  String unidad_medida_edad;
	@Convertir(pos = 11) private  String sexo;
	@Convertir(pos = 12) private  String codigo_dpto;
	@Convertir(pos = 13) private  String codigo_municipio;
	@Convertir(pos = 14) private  String zona_residencia;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  String creacion_user;
	private  String ultimo_user;
	@Convertir(pos = 1) private  String tipo_id_usuario;
	@Convertir(pos = 2) private  String identificacion;

	/*** Constructor Por Defecto ***/
	public Rips_us(){}


	/*** Sobre carga de Constructor ***/
	public Rips_us(
		String codigo_empresa,
		String codigo_sucursal,
		Integer id,
		String codigo_administradora,
		String tipo_usuario,
		String primer_apellido,
		String segundo_apellido,
		String primer_nombre,
		String segundo_nombre,
		String edad,
		String unidad_medida_edad,
		String sexo,
		String codigo_dpto,
		String codigo_municipio,
		String zona_residencia,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		String creacion_user,
		String ultimo_user,
		String tipo_id_usuario,
		String identificacion){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.id = id;
		this.codigo_administradora = codigo_administradora;
		this.tipo_usuario = tipo_usuario;
		this.primer_apellido = primer_apellido;
		this.segundo_apellido = segundo_apellido;
		this.primer_nombre = primer_nombre;
		this.segundo_nombre = segundo_nombre;
		this.edad = edad;
		this.unidad_medida_edad = unidad_medida_edad;
		this.sexo = sexo;
		this.codigo_dpto = codigo_dpto;
		this.codigo_municipio = codigo_municipio;
		this.zona_residencia = zona_residencia;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.tipo_id_usuario = tipo_id_usuario;
		this.identificacion = identificacion;
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
	public void setCodigo_administradora(String codigo_administradora){
		this.codigo_administradora=codigo_administradora;
	}
	public void setTipo_usuario(String tipo_usuario){
		this.tipo_usuario=tipo_usuario;
	}
	public void setPrimer_apellido(String primer_apellido){
		this.primer_apellido=primer_apellido;
	}
	public void setSegundo_apellido(String segundo_apellido){
		this.segundo_apellido=segundo_apellido;
	}
	public void setPrimer_nombre(String primer_nombre){
		this.primer_nombre=primer_nombre;
	}
	public void setSegundo_nombre(String segundo_nombre){
		this.segundo_nombre=segundo_nombre;
	}
	public void setEdad(String edad){
		this.edad=edad;
	}
	public void setUnidad_medida_edad(String unidad_medida_edad){
		this.unidad_medida_edad=unidad_medida_edad;
	}
	public void setSexo(String sexo){
		this.sexo=sexo;
	}
	public void setCodigo_dpto(String codigo_dpto){
		this.codigo_dpto=codigo_dpto;
	}
	public void setCodigo_municipio(String codigo_municipio){
		this.codigo_municipio=codigo_municipio;
	}
	public void setZona_residencia(String zona_residencia){
		this.zona_residencia=zona_residencia;
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
	public void setTipo_id_usuario(String tipo_id_usuario){
		this.tipo_id_usuario=tipo_id_usuario;
	}
	public void setIdentificacion(String identificacion){
		this.identificacion=identificacion;
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
	public String getCodigo_administradora(){
		return codigo_administradora;
	}
	public String getTipo_usuario(){
		return tipo_usuario;
	}
	public String getPrimer_apellido(){
		return primer_apellido;
	}
	public String getSegundo_apellido(){
		return segundo_apellido;
	}
	public String getPrimer_nombre(){
		return primer_nombre;
	}
	public String getSegundo_nombre(){
		return segundo_nombre;
	}
	public String getEdad(){
		return edad;
	}
	public String getUnidad_medida_edad(){
		return unidad_medida_edad;
	}
	public String getSexo(){
		return sexo;
	}
	public String getCodigo_dpto(){
		return codigo_dpto;
	}
	public String getCodigo_municipio(){
		return codigo_municipio;
	}
	public String getZona_residencia(){
		return zona_residencia;
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
	public String getTipo_id_usuario(){
		return tipo_id_usuario;
	}
	public String getIdentificacion(){
		return identificacion;
	}
}