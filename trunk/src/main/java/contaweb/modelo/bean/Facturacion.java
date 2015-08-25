/*
 * Facturacion.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 * Ing. Luis Miguel Hernández Pérez
 */ 
package contaweb.modelo.bean;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Elemento;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("facturacion")
public class Facturacion implements Serializable {

	/************** ATRIBUTOS **************/
	
	private Long id_factura;
	private String nro_atencion;
	
	private  String codigo_empresa;
	private  String codigo_sucursal;
	private  String codigo_comprobante;
	private  String codigo_documento_res;
	private  String codigo_tercero;
	private  String documento_externo;
	private  String codigo_empleado;
	private  Timestamp fecha;
	private  Timestamp fecha_vencimiento;
	private  Integer total_cuotas;
	private  Integer plazo;
	private  String forma_pago;
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
	private  String codigo_credito;
	private  String remision;
	private  String prefijo;
	private  String anio;
	private  String mes;
	private  String clasificacion;
	private  String verificado;
	private  double retencion;
	private  String nro_ingreso;
	private  Timestamp fecha_inicio;
	private  Timestamp fecha_final;
	private  String codigo_administradora;
	private  String id_plan;
	private  String nro_contrato;
	private  String nro_poliza;
	private  double valor_total;
	private  double valor_cuota;
	private  double valor_copago;
	private  String nocopago;
	private  double dto_factura;
	private  double dto_copago;
	private  double cop_canc;
	private  String factura;
	private  String post;
	private  String tipo_identificacion;
	private  String asesor;
	private  String gestor;
	private  Integer dia_pago;
	private  String codigo_fiador1;
	private  String codigo_fiador2;
	private  String nro_cuenta;
	private  boolean es_credito;
	private  String radicado;
	private  Date fecha_erradicacion;
	private  String auditado;
	private  double valor_negocio;
	private  double porc_interes;
	private  double c_inicial;
	private  double abono_inicial;
	private  double descuento_pp;
	private  String medio_pago;
	private  double reteica;
	private  double reteiva;
	private  Date fecha_glosa;
	private  String motivo_glosa;
	private  double valor_glosa_inicial;
	private  double valor_glosa_aceptada;
	private  double valor_glosa_noaceptada;
	private  String observaciones;
	private  String estado_glosa;
	private  double valor_glosa_levantada;
	private  double valor_glosa_ratificada;
	private  double valor_pagar_factura;
	private  String cuenta_retencion;
	private  String anio_retencion;
	private  String codigo_rp;
	private  Integer nro_dias_reportados;
	private  String tarifa_usuario_dia;
	private  String concepto;
	
	private  String fuenta_radicado;
	private  String documento_radicado;
	private  String tipo_glosa;
	private  String estado_respuesta_glosa;
	private  String fact_glosada;
	private  Timestamp fecha_devolucion_factura;
	private  Timestamp fecha_respuesta_glosa;
	private  Timestamp fecha_respuesta_definitiva;
	private  String respueta_glosa;
	private  String observacion_glosa;
	private  String observacion_respuesta;
	private  String observacion_definitiva_glosa;
	private  String respuesta_definitiva;
	private  String concepto_glosa;
	
    private String codigo_obligacion;
    
    private String codigo_centro;
    
    private String descipcion_mes_facturado;
    private Integer poblacion_segun_base_datos;
    private String descripcion_nro_contrato;
    private String descripcion_tipo_servicio;
    private Timestamp fecha_contabilizacion;
    private Boolean aplica_descuento;
    
    private Long codigo_paquete;
    private String motivo_anulacion;
    
    private Double total_factura_clinica;
    private boolean tiene_urgencia;
    private boolean tiene_observacion;
    private boolean tiene_hospitalizacion;
    private boolean tiene_recien_nacido;
    
    //optimizacion de consults
    private Admision admision;
    private Elemento evia;
    private Administradora administradora;

	/*** Constructor Por Defecto ***/
	public Facturacion(){
		this.medio_pago = "";
        this.radicado = "N";
        this.motivo_glosa = "";
        this.observaciones = "";
        this.estado_glosa = "";
        this.cuenta_retencion = "";
        this.anio_retencion = "";
        this.codigo_rp = "";
        this.codigo_obligacion = "";
	}
	
	public Facturacion(boolean inicializar){
		if(inicializar){
			this.medio_pago = "";
	        this.radicado = "N";
	        this.motivo_glosa = "";
	        this.observaciones = "";
	        this.estado_glosa = "";
	        this.cuenta_retencion = "";
	        this.anio_retencion = "";
	        this.codigo_rp = "";
	        this.codigo_obligacion = "";
		}
	}

	/*** Sobre carga de Constructor ***/
	
	
			
	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
	public Facturacion(String codigoEmpresa, String codigoSucursal,
			String codigoComprobante, String codigoDocumento,
			String codigoTercero, String documentoExterno,
			String codigoEmpleado, Timestamp fecha, Timestamp fechaVencimiento,
			Integer totalCuotas, Integer plazo, String formaPago,
			String descripcion, String observacion, String tipo, String estado,
			Timestamp creacionDate, Timestamp ultimoUpdate,
			Timestamp deleteDate, String creacionUser, String ultimoUser,
			String deleteUser, String codigoCredito, String remision,
			String prefijo, String anio, String mes, String clasificacion,
			String verificado, double retencion, String nroIngreso,
			Timestamp fechaInicio, Timestamp fechaFinal,
			String codigoAdministradora, String idPlan, String nroContrato,
			String nroPoliza, double valorTotal, double valorCuota,
			double valorCopago, String nocopago, double dtoFactura,
			double dtoCopago, double copCanc, String factura, String post,
			String tipoIdentificacion, String asesor, String gestor,
			Integer diaPago, String codigoFiador1, String codigoFiador2,
			String nroCuenta, boolean esCredito, String radicado,
			Date fechaErradicacion, String auditado, double valorNegocio,
			double porcInteres, double cInicial, double abonoInicial,
			double descuentoPp, String medioPago, double reteica,
			double reteiva, Date fechaGlosa, String motivoGlosa,
			double valorGlosaInicial, double valorGlosaAceptada,
			double valorGlosaNoaceptada, String observaciones,
			String estadoGlosa, double valorGlosaLevantada,
			double valorGlosaRatificada, double valorPagarFactura,
			String cuentaRetencion, String anioRetencion, String codigoRp,
		String tipo_glosa) {
		super();
		codigo_empresa = codigoEmpresa;
		codigo_sucursal = codigoSucursal;
		codigo_comprobante = codigoComprobante;
		setCodigo_documento_res(codigoDocumento);
		codigo_tercero = codigoTercero;
		documento_externo = documentoExterno;
		codigo_empleado = codigoEmpleado;
		this.fecha = fecha;
		fecha_vencimiento = fechaVencimiento;
		total_cuotas = totalCuotas;
		this.plazo = plazo;
		forma_pago = formaPago;
		this.descripcion = descripcion;
		this.observacion = observacion;
		this.tipo = tipo;
		this.estado = estado;
		creacion_date = creacionDate;
		ultimo_update = ultimoUpdate;
		delete_date = deleteDate;
		creacion_user = creacionUser;
		ultimo_user = ultimoUser;
		delete_user = deleteUser;
		codigo_credito = codigoCredito;
		this.remision = remision;
		this.prefijo = prefijo;
		this.anio = anio;
		this.mes = mes;
		this.clasificacion = clasificacion;
		this.verificado = verificado;
		this.retencion = retencion;
		nro_ingreso = nroIngreso;
		fecha_inicio = fechaInicio;
		fecha_final = fechaFinal;
		codigo_administradora = codigoAdministradora;
		id_plan = idPlan;
		nro_contrato = nroContrato;
		nro_poliza = nroPoliza;
		valor_total = valorTotal;
		valor_cuota = valorCuota;
		valor_copago = valorCopago;
		this.nocopago = nocopago;
		dto_factura = dtoFactura;
		dto_copago = dtoCopago;
		cop_canc = copCanc;
		this.factura = factura;
		this.post = post;
		tipo_identificacion = tipoIdentificacion;
		this.asesor = asesor;
		this.gestor = gestor;
		dia_pago = diaPago;
		codigo_fiador1 = codigoFiador1;
		codigo_fiador2 = codigoFiador2;
		nro_cuenta = nroCuenta;
		es_credito = esCredito;
		this.radicado = radicado;
		fecha_erradicacion = fechaErradicacion;
		this.auditado = auditado;
		valor_negocio = valorNegocio;
		porc_interes = porcInteres;
		c_inicial = cInicial;
		abono_inicial = abonoInicial;
		descuento_pp = descuentoPp;
		medio_pago = medioPago;
		this.reteica = reteica;
		this.reteiva = reteiva;
		fecha_glosa = fechaGlosa;
		motivo_glosa = motivoGlosa;
		valor_glosa_inicial = valorGlosaInicial;
		valor_glosa_aceptada = valorGlosaAceptada;
		valor_glosa_noaceptada = valorGlosaNoaceptada;
		this.observaciones = observaciones;
		estado_glosa = estadoGlosa;
		valor_glosa_levantada = valorGlosaLevantada;
		valor_glosa_ratificada = valorGlosaRatificada;
		valor_pagar_factura = valorPagarFactura;
		cuenta_retencion = cuentaRetencion;
		anio_retencion = anioRetencion;
		codigo_rp = codigoRp;
		this.tipo_glosa = tipo_glosa;
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
	public void setFecha_vencimiento(Timestamp fecha_vencimiento){
		this.fecha_vencimiento=fecha_vencimiento;
	}
	public void setTotal_cuotas(Integer total_cuotas){
		this.total_cuotas=total_cuotas;
	}
	public void setPlazo(Integer plazo){
		this.plazo=plazo;
	}
	public void setForma_pago(String forma_pago){
		this.forma_pago=forma_pago;
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
	public void setCodigo_credito(String codigo_credito){
		this.codigo_credito=codigo_credito;
	}
	public void setRemision(String remision){
		this.remision=remision;
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
	public void setRetencion(double retencion){
		this.retencion=retencion;
	}
	public void setNro_ingreso(String nro_ingreso){
		this.nro_ingreso=nro_ingreso;
	}
	public void setFecha_inicio(Timestamp fecha_inicio){
		this.fecha_inicio=fecha_inicio;
	}
	public void setFecha_final(Timestamp fecha_final){
		this.fecha_final=fecha_final;
	}
	public void setCodigo_administradora(String codigo_administradora){
		this.codigo_administradora=codigo_administradora;
	}
	public void setId_plan(String id_plan){
		this.id_plan=id_plan;
	}
	public void setNro_contrato(String nro_contrato){
		this.nro_contrato=nro_contrato;
	}
	public void setNro_poliza(String nro_poliza){
		this.nro_poliza=nro_poliza;
	}
	public void setValor_total(double valor_total){
		this.valor_total=valor_total;
	}
	public void setValor_cuota(double valor_cuota){
		this.valor_cuota=valor_cuota;
	}
	public void setValor_copago(double valor_copago){
		this.valor_copago=valor_copago;
	}
	public void setNocopago(String nocopago){
		this.nocopago=nocopago;
	}
	public void setDto_factura(double dto_factura){
		this.dto_factura=dto_factura;
	}
	public void setDto_copago(double dto_copago){
		this.dto_copago=dto_copago;
	}
	public void setCop_canc(double cop_canc){
		this.cop_canc=cop_canc;
	}
	public void setFactura(String factura){
		this.factura=factura;
	}
	public void setPost(String post){
		this.post=post;
	}
	public void setTipo_identificacion(String tipo_identificacion){
		this.tipo_identificacion=tipo_identificacion;
	}
	public void setAsesor(String asesor){
		this.asesor=asesor;
	}
	public void setGestor(String gestor){
		this.gestor=gestor;
	}
	public void setDia_pago(Integer dia_pago){
		this.dia_pago=dia_pago;
	}
	public void setCodigo_fiador1(String codigo_fiador1){
		this.codigo_fiador1=codigo_fiador1;
	}
	public void setCodigo_fiador2(String codigo_fiador2){
		this.codigo_fiador2=codigo_fiador2;
	}
	public void setNro_cuenta(String nro_cuenta){
		this.nro_cuenta=nro_cuenta;
	}
	public void setEs_credito(boolean es_credito){
		this.es_credito=es_credito;
	}
	public void setRadicado(String radicado){
		this.radicado=radicado;
	}
	public void setFecha_erradicacion(Date fecha_erradicacion){
		this.fecha_erradicacion=fecha_erradicacion;
	}
	public void setAuditado(String auditado){
		this.auditado=auditado;
	}
	public void setValor_negocio(double valor_negocio){
		this.valor_negocio=valor_negocio;
	}
	public void setPorc_interes(double porc_interes){
		this.porc_interes=porc_interes;
	}
	public void setC_inicial(double c_inicial){
		this.c_inicial=c_inicial;
	}
	public void setAbono_inicial(double abono_inicial){
		this.abono_inicial=abono_inicial;
	}
	public void setDescuento_pp(double descuento_pp){
		this.descuento_pp=descuento_pp;
	}
	public void setMedio_pago(String medio_pago){
		this.medio_pago=medio_pago;
	}
	public void setReteica(double reteica){
		this.reteica=reteica;
	}
	public void setReteiva(double reteiva){
		this.reteiva=reteiva;
	}
	public void setFecha_glosa(Date fecha_glosa){
		this.fecha_glosa=fecha_glosa;
	}
	public void setMotivo_glosa(String motivo_glosa){
		this.motivo_glosa=motivo_glosa;
	}
	public void setValor_glosa_inicial(double valor_glosa_inicial){
		this.valor_glosa_inicial=valor_glosa_inicial;
	}
	public void setValor_glosa_aceptada(double valor_glosa_aceptada){
		this.valor_glosa_aceptada=valor_glosa_aceptada;
	}
	public void setValor_glosa_noaceptada(double valor_glosa_noaceptada){
		this.valor_glosa_noaceptada=valor_glosa_noaceptada;
	}
	public void setObservaciones(String observaciones){
		this.observaciones=observaciones;
	}
	public void setEstado_glosa(String estado_glosa){
		this.estado_glosa=estado_glosa;
	}
	public void setValor_glosa_levantada(double valor_glosa_levantada){
		this.valor_glosa_levantada=valor_glosa_levantada;
	}
	public void setValor_glosa_ratificada(double valor_glosa_ratificada){
		this.valor_glosa_ratificada=valor_glosa_ratificada;
	}
	public void setValor_pagar_factura(double valor_pagar_factura){
		this.valor_pagar_factura=valor_pagar_factura;
	}
	public void setCuenta_retencion(String cuenta_retencion){
		this.cuenta_retencion=cuenta_retencion;
	}
	public void setAnio_retencion(String anio_retencion){
		this.anio_retencion=anio_retencion;
	}
	public void setCodigo_rp(String codigo_rp){
		this.codigo_rp=codigo_rp;
	}
	public void setNro_dias_reportados(Integer nro_dias_reportados){
		this.nro_dias_reportados=nro_dias_reportados;
	}
	public void setTarifa_usuario_dia(String tarifa_usuario_dia){
		this.tarifa_usuario_dia=tarifa_usuario_dia;
	}
	public void setConcepto(String concepto){
		this.concepto=concepto;
	}
        public void setTipo_glosa(String tipo_glosa){
		this.tipo_glosa=tipo_glosa;
	}
	public void setEstado_respuesta_glosa(String estado_respuesta_glosa){
		this.estado_respuesta_glosa=estado_respuesta_glosa;
	}
	public void setFact_glosada(String fact_glosada){
		this.fact_glosada=fact_glosada;
	}
	public void setFecha_devolucion_factura(Timestamp fecha_devolucion_factura){
		this.fecha_devolucion_factura=fecha_devolucion_factura;
	}
	public void setFecha_respuesta_glosa(Timestamp fecha_respuesta_glosa){
		this.fecha_respuesta_glosa=fecha_respuesta_glosa;
	}
	public void setFecha_respuesta_definitiva(Timestamp fecha_respuesta_definitiva){
		this.fecha_respuesta_definitiva=fecha_respuesta_definitiva;
	}
	public void setRespueta_glosa(String respueta_glosa){
		this.respueta_glosa=respueta_glosa;
	}
	public void setObservacion_glosa(String observacion_glosa){
		this.observacion_glosa=observacion_glosa;
	}
	public void setObservacion_respuesta(String observacion_respuesta){
		this.observacion_respuesta=observacion_respuesta;
	}
	public void setObservacion_definitiva_glosa(String observacion_definitiva_glosa){
		this.observacion_definitiva_glosa=observacion_definitiva_glosa;
	}
	public void setRespuesta_definitiva(String respuesta_definitiva){
		this.respuesta_definitiva=respuesta_definitiva;
	}
	public void setConcepto_glosa(String concepto_glosa){
		this.concepto_glosa=concepto_glosa;
	}
	
	public void setDescipcion_mes_facturado(String descipcion_mes_facturado) {
		this.descipcion_mes_facturado = descipcion_mes_facturado;
	}

	public void setPoblacion_segun_base_datos(Integer poblacion_segun_base_datos) {
		this.poblacion_segun_base_datos = poblacion_segun_base_datos;
	}

	public void setDescripcion_nro_contrato(String descripcion_nro_contrato) {
		this.descripcion_nro_contrato = descripcion_nro_contrato;
	}

	public void setDescripcion_tipo_servicio(String descripcion_tipo_servicio) {
		this.descripcion_tipo_servicio = descripcion_tipo_servicio;
	}
	
	public void setFecha_contabilizacion(Timestamp fecha_contabilizacion) {
		this.fecha_contabilizacion = fecha_contabilizacion;
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
	public Timestamp getFecha_vencimiento(){
		return fecha_vencimiento;
	}
	public Integer getTotal_cuotas(){
		return total_cuotas;
	}
	public Integer getPlazo(){
		return plazo;
	}
	public String getForma_pago(){
		return forma_pago;
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
	public String getCodigo_credito(){
		return codigo_credito;
	}
	public String getRemision(){
		return remision;
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
	public double getRetencion(){
		return retencion;
	}
	public String getNro_ingreso(){
		return nro_ingreso;
	}
	public Timestamp getFecha_inicio(){
		return fecha_inicio;
	}
	public Timestamp getFecha_final(){
		return fecha_final;
	}
	public String getCodigo_administradora(){
		return codigo_administradora;
	}
	public String getId_plan(){
		return id_plan;
	}
	public String getNro_contrato(){
		return nro_contrato;
	}
	public String getNro_poliza(){
		return nro_poliza;
	}
	public double getValor_total(){
		return valor_total;
	}
	public double getValor_cuota(){
		return valor_cuota;
	}
	public double getValor_copago(){
		return valor_copago;
	}
	public String getNocopago(){
		return nocopago;
	}
	public double getDto_factura(){
		return dto_factura;
	}
	public double getDto_copago(){
		return dto_copago;
	}
	public double getCop_canc(){
		return cop_canc;
	}
	public String getFactura(){
		return factura;
	}
	public String getPost(){
		return post;
	}
	public String getTipo_identificacion(){
		return tipo_identificacion;
	}
	public String getAsesor(){
		return asesor;
	}
	public String getGestor(){
		return gestor;
	}
	public Integer getDia_pago(){
		return dia_pago;
	}
	public String getCodigo_fiador1(){
		return codigo_fiador1;
	}
	public String getCodigo_fiador2(){
		return codigo_fiador2;
	}
	public String getNro_cuenta(){
		return nro_cuenta;
	}
	public boolean getEs_credito(){
		return es_credito;
	}
	public String getRadicado(){
		return radicado;
	}
	public Date getFecha_erradicacion(){
		return fecha_erradicacion;
	}
	public String getAuditado(){
		return auditado;
	}
	public double getValor_negocio(){
		return valor_negocio;
	}
	public double getPorc_interes(){
		return porc_interes;
	}
	public double getC_inicial(){
		return c_inicial;
	}
	public double getAbono_inicial(){
		return abono_inicial;
	}
	public double getDescuento_pp(){
		return descuento_pp;
	}
	public String getMedio_pago(){
		return medio_pago;
	}
	public double getReteica(){
		return reteica;
	}
	public double getReteiva(){
		return reteiva;
	}
	public Date getFecha_glosa(){
		return fecha_glosa;
	}
	public String getMotivo_glosa(){
		return motivo_glosa;
	}
	public double getValor_glosa_inicial(){
		return valor_glosa_inicial;
	}
	public double getValor_glosa_aceptada(){
		return valor_glosa_aceptada;
	}
	public double getValor_glosa_noaceptada(){
		return valor_glosa_noaceptada;
	}
	public String getObservaciones(){
		return observaciones;
	}
	public String getEstado_glosa(){
		return estado_glosa;
	}
	public double getValor_glosa_levantada(){
		return valor_glosa_levantada;
	}
	public double getValor_glosa_ratificada(){
		return valor_glosa_ratificada;
	}
	public double getValor_pagar_factura(){
		return valor_pagar_factura;
	}
	public String getCuenta_retencion(){
		return cuenta_retencion;
	}
	public String getAnio_retencion(){
		return anio_retencion;
	}
	public String getCodigo_rp(){
		return codigo_rp;
	}
	public Integer getNro_dias_reportados(){
		return nro_dias_reportados;
	}
	public String getTarifa_usuario_dia(){
		return tarifa_usuario_dia;
	}
	public String getConcepto(){
		return concepto;
	}
	
	public String getFuenta_radicado() {
		return fuenta_radicado;
	}

	public void setFuenta_radicado(String fuenta_radicado) {
		this.fuenta_radicado = fuenta_radicado;
	}

	public String getDocumento_radicado() {
		return documento_radicado;
	}

	public void setDocumento_radicado(String documento_radicado) {
		this.documento_radicado = documento_radicado;
	}

        public String getTipo_glosa(){
		return tipo_glosa;
	}
	public String getEstado_respuesta_glosa(){
		return estado_respuesta_glosa;
	}
	public String getFact_glosada(){
		return fact_glosada;
	}
	public Timestamp getFecha_devolucion_factura(){
		return fecha_devolucion_factura;
	}
	public Timestamp getFecha_respuesta_glosa(){
		return fecha_respuesta_glosa;
	}
	public Timestamp getFecha_respuesta_definitiva(){
		return fecha_respuesta_definitiva;
	}
	public String getRespueta_glosa(){
		return respueta_glosa;
	}
	public String getObservacion_glosa(){
		return observacion_glosa;
	}
	public String getObservacion_respuesta(){
		return observacion_respuesta;
	}
	public String getObservacion_definitiva_glosa(){
		return observacion_definitiva_glosa;
	}
	public String getRespuesta_definitiva(){
		return respuesta_definitiva;
	}
	public String getConcepto_glosa(){
		return concepto_glosa;
	}

	public String getCodigo_obligacion() {
		return codigo_obligacion;
	}

	public void setCodigo_obligacion(String codigo_obligacion) {
		this.codigo_obligacion = codigo_obligacion;
	}

	public String getCodigo_centro() {
		return codigo_centro;
	}

	public void setCodigo_centro(String codigo_centro) {
		this.codigo_centro = codigo_centro;
	}

	public Admision getAdmision() {
		return admision;
	}

	public void setAdmision(Admision admision) {
		this.admision = admision;
	}

	public Elemento getEvia() {
		return evia;
	}

	public void setEvia(Elemento evia) {
		this.evia = evia;
	}

	public String getDescipcion_mes_facturado() {
		return descipcion_mes_facturado;
	}

	public Integer getPoblacion_segun_base_datos() {
		return poblacion_segun_base_datos;
	}

	public String getDescripcion_nro_contrato() {
		return descripcion_nro_contrato;
	}

	public String getDescripcion_tipo_servicio() {
		return descripcion_tipo_servicio;
	}

	public Timestamp getFecha_contabilizacion() {
		return fecha_contabilizacion;
	}

	public Double getTotal_factura_clinica() {
		return total_factura_clinica;
	}

	public void setTotal_factura_clinica(Double total_factura_clinica) {
		this.total_factura_clinica = total_factura_clinica;
	}

	public Administradora getAdministradora() {
		return administradora;
	}

	public void setAdministradora(Administradora administradora) {
		this.administradora = administradora;
	}

	public boolean getTiene_urgencia() {
		return tiene_urgencia;
	}

	public void setTiene_urgencia(boolean tiene_urgencia) {
		this.tiene_urgencia = tiene_urgencia;
	}

	public boolean getTiene_hospitalizacion() {
		return tiene_hospitalizacion;
	}

	public void setTiene_hospitalizacion(boolean tiene_hospitalizacion) {
		this.tiene_hospitalizacion = tiene_hospitalizacion;
	}

	public boolean getTiene_recien_nacido() {
		return tiene_recien_nacido;
	}

	public void setTiene_recien_nacido(boolean tiene_recien_nacido) {
		this.tiene_recien_nacido = tiene_recien_nacido;
	}

	public boolean getTiene_observacion() {
		return tiene_observacion;
	}

	public void setTiene_observacion(boolean tiene_observacion) {
		this.tiene_observacion = tiene_observacion;
	}

	public Boolean getAplica_descuento() {
		return aplica_descuento;
	}

	public void setAplica_descuento(Boolean aplica_descuento) {
		this.aplica_descuento = aplica_descuento;
	}

	public Long getId_factura() {
		return id_factura;
	}

	public void setId_factura(Long id_factura) {
		this.id_factura = id_factura;
	}

	public String getNro_atencion() {
		return nro_atencion;
	}

	public void setNro_atencion(String nro_atencion) {
		this.nro_atencion = nro_atencion;
	}

	public String getCodigo_documento_res() {
		return codigo_documento_res;
	}

	public void setCodigo_documento_res(String codigo_documento_res) {
		this.codigo_documento_res = codigo_documento_res;
	}

	public Long getCodigo_paquete() {
		return codigo_paquete;
	}

	public void setCodigo_paquete(Long codigo_paquete) {
		this.codigo_paquete = codigo_paquete;
	}

	public String getMotivo_anulacion() {
		return motivo_anulacion;
	}

	public void setMotivo_anulacion(String motivo_anulacion) {
		this.motivo_anulacion = motivo_anulacion;
	}

}