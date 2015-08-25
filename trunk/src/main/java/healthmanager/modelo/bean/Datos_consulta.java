/*
 * Datos_consulta.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package healthmanager.modelo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("datos_consulta")
public class Datos_consulta implements Serializable{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  Long codigo_registro;
	private  String tipo_identificacion;
	private  String nro_identificacion;
	private  String codigo_administradora;
	private  String id_plan;
	private  String codigo_prestador;
	private  String nro_ingreso;
	private  String codigo_consulta;
	private  Timestamp fecha_consulta;
	private  String numero_autorizacion;
	private  double valor_consulta;
	private  double valor_cuota;
	private  double valor_neto;
	private  String finalidad_consulta;
	private  String causa_externa;
	private  String tipo_diagnostico;
	private  String codigo_diagnostico_principal;
	private  String codigo_diagnostico1;
	private  String codigo_diagnostico2;
	private  String codigo_diagnostico3;
	private  String cancelo_copago;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  double valor_real;
	private  double descuento;
	private  double incremento;
	private  String codigo_orden;
	private  String tipo_hc;
	private  String codigo_programa;
	private  boolean cita_pyp;
	private  String codigo_cita;
	
	private Paciente paciente;
	
	private Procedimientos procedimientos;

	/*** Constructor Por Defecto ***/
	public Datos_consulta(){
		codigo_orden = "";
        tipo_hc = "";
		codigo_programa = "";
		codigo_cita = "";
	}
	
	
	public Datos_consulta(boolean inicializar){
		if(inicializar){
			codigo_orden = "";
	        tipo_hc = "";
			codigo_programa = "";
			codigo_cita = "";
		}
	}


	/*** Sobre carga de Constructor ***/
	public Datos_consulta(
		String codigo_empresa,
		String codigo_sucursal,
		Long codigo_registro,
		String tipo_identificacion,
		String nro_identificacion,
		String codigo_administradora,
		String id_plan,
		String codigo_prestador,
		String nro_ingreso,
		String codigo_consulta,
		Timestamp fecha_consulta,
		String numero_autorizacion,
		double valor_consulta,
		double valor_cuota,
		double valor_neto,
		String finalidad_consulta,
		String causa_externa,
		String tipo_diagnostico,
		String codigo_diagnostico_principal,
		String codigo_diagnostico1,
		String codigo_diagnostico2,
		String codigo_diagnostico3,
		String cancelo_copago,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		double valor_real,
		double descuento,
		double incremento,
		String codigo_orden,
		String tipo_hc){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_registro =codigo_registro;
		this.tipo_identificacion = tipo_identificacion;
		this.nro_identificacion = nro_identificacion;
		this.codigo_administradora = codigo_administradora;
		this.id_plan = id_plan;
		this.codigo_prestador = codigo_prestador;
		this.nro_ingreso = nro_ingreso;
		this.codigo_consulta = codigo_consulta;
		this.fecha_consulta = fecha_consulta;
		this.numero_autorizacion = numero_autorizacion;
		this.valor_consulta = valor_consulta;
		this.valor_cuota = valor_cuota;
		this.valor_neto = valor_neto;
		this.finalidad_consulta = finalidad_consulta;
		this.causa_externa = causa_externa;
		this.tipo_diagnostico = tipo_diagnostico;
		this.codigo_diagnostico_principal = codigo_diagnostico_principal;
		this.codigo_diagnostico1 = codigo_diagnostico1;
		this.codigo_diagnostico2 = codigo_diagnostico2;
		this.codigo_diagnostico3 = codigo_diagnostico3;
		this.cancelo_copago = cancelo_copago;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.valor_real = valor_real;
		this.descuento = descuento;
		this.incremento = incremento;
		this.codigo_orden = codigo_orden;
		this.tipo_hc = tipo_hc;
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
	
	public void setTipo_identificacion(String tipo_identificacion){
		this.tipo_identificacion=tipo_identificacion;
	}
	public void setNro_identificacion(String nro_identificacion){
		this.nro_identificacion=nro_identificacion;
	}
	public void setCodigo_administradora(String codigo_administradora){
		this.codigo_administradora=codigo_administradora;
	}
	public void setId_plan(String id_plan){
		this.id_plan=id_plan;
	}
	public void setCodigo_prestador(String codigo_prestador){
		this.codigo_prestador=codigo_prestador;
	}
	public void setNro_ingreso(String nro_ingreso){
		this.nro_ingreso=nro_ingreso;
	}
	public void setCodigo_consulta(String codigo_consulta){
		this.codigo_consulta=codigo_consulta;
	}
	public void setFecha_consulta(Timestamp fecha_consulta){
		this.fecha_consulta=fecha_consulta;
	}
	public void setNumero_autorizacion(String numero_autorizacion){
		this.numero_autorizacion=numero_autorizacion;
	}
	public void setValor_consulta(double valor_consulta){
		this.valor_consulta=valor_consulta;
	}
	public void setValor_cuota(double valor_cuota){
		this.valor_cuota=valor_cuota;
	}
	public void setValor_neto(double valor_neto){
		this.valor_neto=valor_neto;
	}
	public void setFinalidad_consulta(String finalidad_consulta){
		this.finalidad_consulta=finalidad_consulta;
	}
	public void setCausa_externa(String causa_externa){
		this.causa_externa=causa_externa;
	}
	public void setTipo_diagnostico(String tipo_diagnostico){
		this.tipo_diagnostico=tipo_diagnostico;
	}
	public void setCodigo_diagnostico_principal(String codigo_diagnostico_principal){
		this.codigo_diagnostico_principal=codigo_diagnostico_principal;
	}
	public void setCodigo_diagnostico1(String codigo_diagnostico1){
		this.codigo_diagnostico1=codigo_diagnostico1;
	}
	public void setCodigo_diagnostico2(String codigo_diagnostico2){
		this.codigo_diagnostico2=codigo_diagnostico2;
	}
	public void setCodigo_diagnostico3(String codigo_diagnostico3){
		this.codigo_diagnostico3=codigo_diagnostico3;
	}

	public void setCancelo_copago(String cancelo_copago){
		this.cancelo_copago=cancelo_copago;
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
	public void setValor_real(double valor_real){
		this.valor_real=valor_real;
	}
	public void setDescuento(double descuento){
		this.descuento=descuento;
	}
	public void setIncremento(double incremento){
		this.incremento=incremento;
	}
	public void setCodigo_orden(String codigo_orden){
		this.codigo_orden=codigo_orden;
	}
	public void setTipo_hc(String tipo_hc){
		this.tipo_hc=tipo_hc;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	
	public String getTipo_identificacion(){
		return tipo_identificacion;
	}
	public String getNro_identificacion(){
		return nro_identificacion;
	}
	public String getCodigo_administradora(){
		return codigo_administradora;
	}
	public String getId_plan(){
		return id_plan;
	}
	public String getCodigo_prestador(){
		return codigo_prestador;
	}
	public String getNro_ingreso(){
		return nro_ingreso;
	}
	public String getCodigo_consulta(){
		return codigo_consulta;
	}
	public Timestamp getFecha_consulta(){
		return fecha_consulta;
	}
	public String getNumero_autorizacion(){
		return numero_autorizacion;
	}
	public double getValor_consulta(){
		return valor_consulta;
	}
	public double getValor_cuota(){
		return valor_cuota;
	}
	public double getValor_neto(){
		return valor_neto;
	}
	public String getFinalidad_consulta(){
		return finalidad_consulta;
	}
	public String getCausa_externa(){
		return causa_externa;
	}
	public String getTipo_diagnostico(){
		return tipo_diagnostico;
	}
	public String getCodigo_diagnostico_principal(){
		return codigo_diagnostico_principal;
	}
	public String getCodigo_diagnostico1(){
		return codigo_diagnostico1;
	}
	public String getCodigo_diagnostico2(){
		return codigo_diagnostico2;
	}
	public String getCodigo_diagnostico3(){
		return codigo_diagnostico3;
	}
	
	public String getCancelo_copago(){
		return cancelo_copago;
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
	public double getValor_real(){
		return valor_real;
	}
	public double getDescuento(){
		return descuento;
	}
	public double getIncremento(){
		return incremento;
	}
	public String getCodigo_orden(){
		return codigo_orden;
	}
	public String getTipo_hc(){
		return tipo_hc;
	}


	public String getCodigo_programa() {
		return codigo_programa;
	}


	public void setCodigo_programa(String codigo_programa) {
		this.codigo_programa = codigo_programa;
	}


	public boolean getCita_pyp() {
		return cita_pyp;
	}


	public void setCita_pyp(boolean cita_pyp) {
		this.cita_pyp = cita_pyp;
	}


	public String getCodigo_cita() {
		return codigo_cita;
	}


	public void setCodigo_cita(String codigo_cita) {
		this.codigo_cita = codigo_cita;
	}


	public Paciente getPaciente() {
		return paciente;
	}


	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}


	public Procedimientos getProcedimientos() {
		return procedimientos;
	}


	public void setProcedimientos(Procedimientos procedimientos) {
		this.procedimientos = procedimientos;
	}


	public Long getCodigo_registro() {
		return codigo_registro;
	}


	public void setCodigo_registro(Long codigo_registro) {
		this.codigo_registro = codigo_registro;
	}
}
