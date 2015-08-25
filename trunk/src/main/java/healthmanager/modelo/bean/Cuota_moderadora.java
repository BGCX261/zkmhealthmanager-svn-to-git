/*
 * Cuota_moderadora.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.modelo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("cuota_moderadora")
public class Cuota_moderadora implements Serializable{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String grupo;
	private  String tipo_nivel;
	private  double nivel1;
	private  double nivel2;
	private  double porcentaje_copago;
	private  double limite_evento;
	private  double maximo_anio;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  String creacion_user;
	private  String ultimo_user;
	private  double valor_cuota;

	/*** Constructor Por Defecto ***/
	public Cuota_moderadora(){}


	/*** Sobre carga de Constructor ***/
	public Cuota_moderadora(
		String codigo_empresa,
		String grupo,
		String tipo_nivel,
		double nivel1,
		double nivel2,
		double porcentaje_copago,
		double limite_evento,
		double maximo_anio,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		String creacion_user,
		String ultimo_user,
		double valor_cuota){
		this.codigo_empresa = codigo_empresa;
		this.grupo = grupo;
		this.tipo_nivel = tipo_nivel;
		this.nivel1 = nivel1;
		this.nivel2 = nivel2;
		this.porcentaje_copago = porcentaje_copago;
		this.limite_evento = limite_evento;
		this.maximo_anio = maximo_anio;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.valor_cuota = valor_cuota;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setCodigo_empresa(String codigo_empresa){
		this.codigo_empresa=codigo_empresa;
	}
	public void setGrupo(String grupo){
		this.grupo=grupo;
	}
	public void setTipo_nivel(String tipo_nivel){
		this.tipo_nivel=tipo_nivel;
	}
	public void setNivel1(double nivel1){
		this.nivel1=nivel1;
	}
	public void setNivel2(double nivel2){
		this.nivel2=nivel2;
	}
	public void setPorcentaje_copago(double porcentaje_copago){
		this.porcentaje_copago=porcentaje_copago;
	}
	public void setLimite_evento(double limite_evento){
		this.limite_evento=limite_evento;
	}
	public void setMaximo_anio(double maximo_anio){
		this.maximo_anio=maximo_anio;
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
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getGrupo(){
		return grupo;
	}
	public String getTipo_nivel(){
		return tipo_nivel;
	}
	public double getNivel1(){
		return nivel1;
	}
	public double getNivel2(){
		return nivel2;
	}
	public double getPorcentaje_copago(){
		return porcentaje_copago;
	}
	public double getLimite_evento(){
		return limite_evento;
	}
	public double getMaximo_anio(){
		return maximo_anio;
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


	public void setValor_cuota(double valor_cuota) {
		this.valor_cuota = valor_cuota;
	}


	public double getValor_cuota() {
		return valor_cuota;
	}
}
