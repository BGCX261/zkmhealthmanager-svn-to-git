/*
 * Tabla_anios_meses.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */ 
package tablas_crecimiento_desarrollo.modelo.bean;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("tabla_anios_meses")
public class Tabla_anios_meses implements Serializable {

	/************** ATRIBUTOS **************/

	private  String sexo;
	private  String tipo;
	private  Integer edad_inicial;
	private  String tipo_edad_inicial;
	private  Integer edad_final;
	private  String tipo_edad_final;
	private  String anio_mes;
	private  Integer mes;
	private  double l;
	private  double m;
	private  double s;
	private  double sd;
	private  double menos_3_sd;
	private  double menos_2_sd;
	private  double menos_1_sd;
	private  double media;
	private  double mas_1_sd;
	private  double mas_2_sd;
	private  double mas_3_sd;

	/*** Constructor Por Defecto ***/
	public Tabla_anios_meses(){}


	/*** Sobre carga de Constructor ***/
	public Tabla_anios_meses(
		String sexo,
		String tipo,
		Integer edad_inicial,
		String tipo_edad_inicial,
		Integer edad_final,
		String tipo_edad_final,
		String anio_mes,
		Integer mes,
		double l,
		double m,
		double s,
		double sd,
		double menos_3_sd,
		double menos_2_sd,
		double menos_1_sd,
		double media,
		double mas_1_sd,
		double mas_2_sd,
		double mas_3_sd){
		this.sexo = sexo;
		this.tipo = tipo;
		this.edad_inicial = edad_inicial;
		this.tipo_edad_inicial = tipo_edad_inicial;
		this.edad_final = edad_final;
		this.tipo_edad_final = tipo_edad_final;
		this.anio_mes = anio_mes;
		this.mes = mes;
		this.l = l;
		this.m = m;
		this.s = s;
		this.sd = sd;
		this.menos_3_sd = menos_3_sd;
		this.menos_2_sd = menos_2_sd;
		this.menos_1_sd = menos_1_sd;
		this.media = media;
		this.mas_1_sd = mas_1_sd;
		this.mas_2_sd = mas_2_sd;
		this.mas_3_sd = mas_3_sd;
	}
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	/************** METODOS SET ****************/

	public void setSexo(String sexo){
		this.sexo=sexo;
	}
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	public void setEdad_inicial(Integer edad_inicial){
		this.edad_inicial=edad_inicial;
	}
	public void setTipo_edad_inicial(String tipo_edad_inicial){
		this.tipo_edad_inicial=tipo_edad_inicial;
	}
	public void setEdad_final(Integer edad_final){
		this.edad_final=edad_final;
	}
	public void setTipo_edad_final(String tipo_edad_final){
		this.tipo_edad_final=tipo_edad_final;
	}
	public void setAnio_mes(String anio_mes){
		this.anio_mes=anio_mes;
	}
	public void setMes(Integer mes){
		this.mes=mes;
	}
	public void setL(double l){
		this.l=l;
	}
	public void setM(double m){
		this.m=m;
	}
	public void setS(double s){
		this.s=s;
	}
	public void setSd(double sd){
		this.sd=sd;
	}
	public void setMenos_3_sd(double menos_3_sd){
		this.menos_3_sd=menos_3_sd;
	}
	public void setMenos_2_sd(double menos_2_sd){
		this.menos_2_sd=menos_2_sd;
	}
	public void setMenos_1_sd(double menos_1_sd){
		this.menos_1_sd=menos_1_sd;
	}
	public void setMedia(double media){
		this.media=media;
	}
	public void setMas_1_sd(double mas_1_sd){
		this.mas_1_sd=mas_1_sd;
	}
	public void setMas_2_sd(double mas_2_sd){
		this.mas_2_sd=mas_2_sd;
	}
	public void setMas_3_sd(double mas_3_sd){
		this.mas_3_sd=mas_3_sd;
	}
	/************** METODOS GET **************/

	public String getSexo(){
		return sexo;
	}
	public String getTipo(){
		return tipo;
	}
	public Integer getEdad_inicial(){
		return edad_inicial;
	}
	public String getTipo_edad_inicial(){
		return tipo_edad_inicial;
	}
	public Integer getEdad_final(){
		return edad_final;
	}
	public String getTipo_edad_final(){
		return tipo_edad_final;
	}
	public String getAnio_mes(){
		return anio_mes;
	}
	public Integer getMes(){
		return mes;
	}
	public double getL(){
		return l;
	}
	public double getM(){
		return m;
	}
	public double getS(){
		return s;
	}
	public double getSd(){
		return sd;
	}
	public double getMenos_3_sd(){
		return menos_3_sd;
	}
	public double getMenos_2_sd(){
		return menos_2_sd;
	}
	public double getMenos_1_sd(){
		return menos_1_sd;
	}
	public double getMedia(){
		return media;
	}
	public double getMas_1_sd(){
		return mas_1_sd;
	}
	public double getMas_2_sd(){
		return mas_2_sd;
	}
	public double getMas_3_sd(){
		return mas_3_sd;
	}
}