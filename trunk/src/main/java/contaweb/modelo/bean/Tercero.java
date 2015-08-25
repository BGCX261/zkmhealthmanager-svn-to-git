/*
 * Tercero.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */ 
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("tercero")
public class Tercero implements Serializable{

	/************** ATRIBUTOS **************/

	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String nro_identificacion;
	private  String tipo_identificacion;
	private  String nombre1;
	private  String nombre2;
	private  String apellido1;
	private  String apellido2;
	private  String direccion;
	private  String tel_oficina;
	private  String tel_res;
	private  String fax;
	private  String contacto;
	private  String email;
	private  String codigo_dpto;
	private  String codigo_municipio;
	private  String zona_venta;
	private  String codigo_vendedor;
	private  String tipo_contribuyente;
	private  String observacion;
	private  Timestamp creacion_date;
	private  Timestamp ultimo_update;
	private  Timestamp delete_date;
	private  String creacion_user;
	private  String ultimo_user;
	private  String delete_user;
	private  String empresa;
	private  String direccion_empresa;
	private  String telefono_empresa;
	private  String cargo;
	private  String tiempo_servicio;
	private  double ingresos;
	private  String propietario;
	private  String direccion_propietario;
	private  double valor_arrendamiento;
	private  String tiempo_habita;
	private  String banco;
	private  double tarifa_ica;
	private  String tipo;
	private  String cuenta_cobrar;
	private  String cuenta_pagar;
	private  String dv;
	private  String tipo_aseguradora;
	private  String cuenta_retencion;
	private boolean aplica_reteica;
	
	private  String cuenta_cobrar2;
	private  String cuenta_cobrar3;
	
	private  boolean autoretencion;
	private  String cuenta_autorete1;
	private  String cuenta_autorete2;
	
	private  String cuenta_reteica;

	/*** Constructor Por Defecto ***/
	public Tercero(){
		this.codigo_empresa        = "";
		this.codigo_sucursal       = "";
		this.nro_identificacion    = "";
		this.tipo_identificacion    = ""; 
		this.nombre1				= ""; 
		this.nombre2				= ""; 
		this.apellido1 				= ""; 
		this.apellido2 				= ""; 
		this.direccion 				= ""; 
		this.tel_oficina 			= ""; 
		this.tel_res 				= ""; 
		this.fax 					= ""; 
		this.contacto 				= ""; 
		this.email 					= ""; 
		this.codigo_dpto 			= ""; 
		this.codigo_municipio 		= ""; 
		this.zona_venta 			= ""; 
		this.codigo_vendedor 		= ""; 
		this.tipo_contribuyente 	= ""; 
		this.observacion 			= ""; 
		this.creacion_user 			= ""; 
		this.ultimo_user 			= ""; 
		this.delete_user 			= ""; 
		this.empresa 				= ""; 
		this.direccion_empresa 		= ""; 
		this.telefono_empresa 		= ""; 
		this.cargo 					= ""; 
		this.tiempo_servicio 		= ""; 
		this.ingresos 				= 0.0; 
		this.propietario 			= ""; 
		this.direccion_propietario  = ""; 
		this.valor_arrendamiento 	= 0.0; 
		this.tiempo_habita 			= ""; 
		this.banco 					= ""; 
		this.tarifa_ica 			= 0.0; 
		this.tipo 					= ""; 
		this.cuenta_cobrar 			= ""; 
		this.cuenta_pagar 			= ""; 
		this.dv 					= "";
		this.tipo_aseguradora       = "01";
        this.cuenta_retencion       = "";
        this.cuenta_cobrar2 = "";
        this.cuenta_cobrar3 = "";
        
        cuenta_autorete1 = "";
		cuenta_autorete2 = "";
		
		cuenta_reteica = "";
	}


	/*** Sobre carga de Constructor ***/
	public Tercero(
		String codigo_empresa,
		String codigo_sucursal,
		String nro_identificacion,
		String tipo_identificacion,
		String nombre1,
		String nombre2,
		String apellido1,
		String apellido2,
		String direccion,
		String tel_oficina,
		String tel_res,
		String fax,
		String contacto,
		String email,
		String codigo_dpto,
		String codigo_municipio,
		String zona_venta,
		String codigo_vendedor,
		String tipo_contribuyente,
		String observacion,
		Timestamp creacion_date,
		Timestamp ultimo_update,
		Timestamp delete_date,
		String creacion_user,
		String ultimo_user,
		String delete_user,
		String empresa,
		String direccion_empresa,
		String telefono_empresa,
		String cargo,
		String tiempo_servicio,
		double ingresos,
		String propietario,
		String direccion_propietario,
		double valor_arrendamiento,
		String tiempo_habita,
		String banco,
		double tarifa_ica,
		String tipo,
		String cuenta_cobrar,
		String cuenta_pagar,
		String dv,
		String tipo_aseguradora,
		String cuenta_retencion,
		String cuenta_cobrar2){
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.nro_identificacion = nro_identificacion;
		this.tipo_identificacion = tipo_identificacion;
		this.nombre1 = nombre1;
		this.nombre2 = nombre2;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.direccion = direccion;
		this.tel_oficina = tel_oficina;
		this.tel_res = tel_res;
		this.fax = fax;
		this.contacto = contacto;
		this.email = email;
		this.codigo_dpto = codigo_dpto;
		this.codigo_municipio = codigo_municipio;
		this.zona_venta = zona_venta;
		this.codigo_vendedor = codigo_vendedor;
		this.tipo_contribuyente = tipo_contribuyente;
		this.observacion = observacion;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.empresa = empresa;
		this.direccion_empresa = direccion_empresa;
		this.telefono_empresa = telefono_empresa;
		this.cargo = cargo;
		this.tiempo_servicio = tiempo_servicio;
		this.ingresos = ingresos;
		this.propietario = propietario;
		this.direccion_propietario = direccion_propietario;
		this.valor_arrendamiento = valor_arrendamiento;
		this.tiempo_habita = tiempo_habita;
		this.banco = banco;
		this.tarifa_ica = tarifa_ica;
		this.tipo = tipo;
		this.cuenta_cobrar = cuenta_cobrar;
		this.cuenta_pagar = cuenta_pagar;
		this.dv = dv;
		this.tipo_aseguradora = tipo_aseguradora;
		this.cuenta_retencion = cuenta_retencion;
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
	public void setNro_identificacion(String nro_identificacion){
		this.nro_identificacion=nro_identificacion;
	}
	public void setTipo_identificacion(String tipo_identificacion){
		this.tipo_identificacion=tipo_identificacion;
	}
	public void setNombre1(String nombre1){
		this.nombre1=nombre1;
	}
	public void setNombre2(String nombre2){
		this.nombre2=nombre2;
	}
	public void setApellido1(String apellido1){
		this.apellido1=apellido1;
	}
	public void setApellido2(String apellido2){
		this.apellido2=apellido2;
	}
	public void setDireccion(String direccion){
		this.direccion=direccion;
	}
	public void setTel_oficina(String tel_oficina){
		this.tel_oficina=tel_oficina;
	}
	public void setTel_res(String tel_res){
		this.tel_res=tel_res;
	}
	public void setFax(String fax){
		this.fax=fax;
	}
	public void setContacto(String contacto){
		this.contacto=contacto;
	}
	public void setEmail(String email){
		this.email=email;
	}
	public void setCodigo_dpto(String codigo_dpto){
		this.codigo_dpto=codigo_dpto;
	}
	public void setCodigo_municipio(String codigo_municipio){
		this.codigo_municipio=codigo_municipio;
	}
	public void setZona_venta(String zona_venta){
		this.zona_venta=zona_venta;
	}
	public void setCodigo_vendedor(String codigo_vendedor){
		this.codigo_vendedor=codigo_vendedor;
	}
	public void setTipo_contribuyente(String tipo_contribuyente){
		this.tipo_contribuyente=tipo_contribuyente;
	}
	public void setObservacion(String observacion){
		this.observacion=observacion;
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
	public void setEmpresa(String empresa){
		this.empresa=empresa;
	}
	public void setDireccion_empresa(String direccion_empresa){
		this.direccion_empresa=direccion_empresa;
	}
	public void setTelefono_empresa(String telefono_empresa){
		this.telefono_empresa=telefono_empresa;
	}
	public void setCargo(String cargo){
		this.cargo=cargo;
	}
	public void setTiempo_servicio(String tiempo_servicio){
		this.tiempo_servicio=tiempo_servicio;
	}
	public void setIngresos(double ingresos){
		this.ingresos=ingresos;
	}
	public void setPropietario(String propietario){
		this.propietario=propietario;
	}
	public void setDireccion_propietario(String direccion_propietario){
		this.direccion_propietario=direccion_propietario;
	}
	public void setValor_arrendamiento(double valor_arrendamiento){
		this.valor_arrendamiento=valor_arrendamiento;
	}
	public void setTiempo_habita(String tiempo_habita){
		this.tiempo_habita=tiempo_habita;
	}
	public void setBanco(String banco){
		this.banco=banco;
	}
	public void setTarifa_ica(double tarifa_ica){
		this.tarifa_ica=tarifa_ica;
	}
	public void setTipo(String tipo){
		this.tipo=tipo;
	}
	public void setCuenta_cobrar(String cuenta_cobrar){
		this.cuenta_cobrar=cuenta_cobrar;
	}
	public void setCuenta_pagar(String cuenta_pagar){
		this.cuenta_pagar=cuenta_pagar;
	}
	public void setDv(String dv){
		this.dv=dv;
	}
	public void setTipo_aseguradora(String tipo_aseguradora){
		this.tipo_aseguradora=tipo_aseguradora;
	}
	public void setCuenta_retencion(String cuenta_retencion){
		this.cuenta_retencion=cuenta_retencion;
	}
	/************** METODOS GET **************/

	public String getCodigo_empresa(){
		return codigo_empresa;
	}
	public String getCodigo_sucursal(){
		return codigo_sucursal;
	}
	public String getNro_identificacion(){
		return nro_identificacion;
	}
	public String getTipo_identificacion(){
		return tipo_identificacion;
	}
	public String getNombre1(){
		return nombre1;
	}
	public String getNombre2(){
		return nombre2;
	}
	public String getApellido1(){
		return apellido1;
	}
	public String getApellido2(){
		return apellido2;
	}
	public String getDireccion(){
		return direccion;
	}
	public String getTel_oficina(){
		return tel_oficina;
	}
	public String getTel_res(){
		return tel_res;
	}
	public String getFax(){
		return fax;
	}
	public String getContacto(){
		return contacto;
	}
	public String getEmail(){
		return email;
	}
	public String getCodigo_dpto(){
		return codigo_dpto;
	}
	public String getCodigo_municipio(){
		return codigo_municipio;
	}
	public String getZona_venta(){
		return zona_venta;
	}
	public String getCodigo_vendedor(){
		return codigo_vendedor;
	}
	public String getTipo_contribuyente(){
		return tipo_contribuyente;
	}
	public String getObservacion(){
		return observacion;
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
	public String getEmpresa(){
		return empresa;
	}
	public String getDireccion_empresa(){
		return direccion_empresa;
	}
	public String getTelefono_empresa(){
		return telefono_empresa;
	}
	public String getCargo(){
		return cargo;
	}
	public String getTiempo_servicio(){
		return tiempo_servicio;
	}
	public double getIngresos(){
		return ingresos;
	}
	public String getPropietario(){
		return propietario;
	}
	public String getDireccion_propietario(){
		return direccion_propietario;
	}
	public double getValor_arrendamiento(){
		return valor_arrendamiento;
	}
	public String getTiempo_habita(){
		return tiempo_habita;
	}
	public String getBanco(){
		return banco;
	}
	public double getTarifa_ica(){
		return tarifa_ica;
	}
	public String getTipo(){
		return tipo;
	}
	public String getCuenta_cobrar(){
		return cuenta_cobrar;
	}
	public String getCuenta_pagar(){
		return cuenta_pagar;
	}
	public String getDv(){
		return dv;
	}
	public String getTipo_aseguradora(){
		return tipo_aseguradora;
	}
	public String getCuenta_retencion(){
		return cuenta_retencion;
	}


	public void setAplica_reteica(boolean aplica_reteica) {
		this.aplica_reteica = aplica_reteica;
	}


	public boolean getAplica_reteica() {
		return aplica_reteica;
	}


	public String getCuenta_cobrar2() {
		return cuenta_cobrar2;
	}


	public void setCuenta_cobrar2(String cuenta_cobrar2) {
		this.cuenta_cobrar2 = cuenta_cobrar2;
	}


	public String getCuenta_cobrar3() {
		return cuenta_cobrar3;
	}


	public void setCuenta_cobrar3(String cuenta_cobrar3) {
		this.cuenta_cobrar3 = cuenta_cobrar3;
	}


	public boolean getAutoretencion() {
		return autoretencion;
	}


	public void setAutoretencion(boolean autoretencion) {
		this.autoretencion = autoretencion;
	}


	public String getCuenta_autorete1() {
		return cuenta_autorete1;
	}


	public void setCuenta_autorete1(String cuenta_autorete1) {
		this.cuenta_autorete1 = cuenta_autorete1;
	}


	public String getCuenta_autorete2() {
		return cuenta_autorete2;
	}


	public void setCuenta_autorete2(String cuenta_autorete2) {
		this.cuenta_autorete2 = cuenta_autorete2;
	}


	public String getCuenta_reteica() {
		return cuenta_reteica;
	}


	public void setCuenta_reteica(String cuenta_reteica) {
		this.cuenta_reteica = cuenta_reteica;
	}
}
