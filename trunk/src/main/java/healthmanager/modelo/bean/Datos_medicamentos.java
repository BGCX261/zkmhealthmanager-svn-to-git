/*
 * Datos_medicamentos.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.modelo.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

import contaweb.modelo.bean.Articulo;		

@Alias("datos_medicamentos")
public class Datos_medicamentos implements Serializable {

	/************** ATRIBUTOS **************/

	private String codigo_empresa;
	private String codigo_sucursal;
	private String nro_factura;
	private String consecutivo;
	private String codigo_bodega;
	private String codigo_medicamento;
	private String codigo_lote;
	private Integer unidades;
	private double valor_unitario;
	private double valor_total;
	private String cancelo_copago;
	private Timestamp creacion_date;
	private Timestamp ultimo_update;
	private Timestamp delete_date;
	private String creacion_user;
	private String ultimo_user;
	private String delete_user;
	private double valor_real;
	private double descuento;
	private double incremento;
	private Integer cantidad_entregada;
	private String referencia_pyp;
	private String tipo_medicamento;
	private String nombre_generico;
	private String forma_farmaceutica;
	private String concentracion_medicamento;
	private String unidad_medida;

	private Articulo articulo;
	private Facturacion_medicamento facturacion_medicamento;

	/*** Constructor Por Defecto ***/
	public Datos_medicamentos() {
	}

	/*** Sobre carga de Constructor ***/
	public Datos_medicamentos(String codigo_empresa, String codigo_sucursal,
			String nro_factura, String consecutivo, String codigo_bodega,
			String codigo_medicamento, String codigo_lote, Integer unidades,
			double valor_unitario, double valor_total, String cancelo_copago,
			Timestamp creacion_date, Timestamp ultimo_update,
			Timestamp delete_date, String creacion_user, String ultimo_user,
			String delete_user, double valor_real, double descuento,
			double incremento, Integer cantidad_entregada,
			String referencia_pyp, String tipo_medicamento,
			String nombre_generico, String forma_farmaceutica,
			String concentracion_medicamento, String unidad_medida) {
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.nro_factura = nro_factura;
		this.consecutivo = consecutivo;
		this.codigo_bodega = codigo_bodega;
		this.codigo_medicamento = codigo_medicamento;
		this.codigo_lote = codigo_lote;
		this.unidades = unidades;
		this.valor_unitario = valor_unitario;
		this.valor_total = valor_total;
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
		this.cantidad_entregada = cantidad_entregada;
		this.referencia_pyp = referencia_pyp;
		this.tipo_medicamento = tipo_medicamento;
		this.nombre_generico = nombre_generico;
		this.forma_farmaceutica = forma_farmaceutica;
		this.concentracion_medicamento = concentracion_medicamento;
		this.unidad_medida = unidad_medida;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	/************** METODOS SET ****************/

	public void setCodigo_empresa(String codigo_empresa) {
		this.codigo_empresa = codigo_empresa;
	}

	public void setCodigo_sucursal(String codigo_sucursal) {
		this.codigo_sucursal = codigo_sucursal;
	}

	public void setNro_factura(String nro_factura) {
		this.nro_factura = nro_factura;
	}

	public void setConsecutivo(String consecutivo) {
		this.consecutivo = consecutivo;
	}

	public void setCodigo_bodega(String codigo_bodega) {
		this.codigo_bodega = codigo_bodega;
	}

	public void setCodigo_medicamento(String codigo_medicamento) {
		this.codigo_medicamento = codigo_medicamento;
	}

	public void setCodigo_lote(String codigo_lote) {
		this.codigo_lote = codigo_lote;
	}

	public void setUnidades(Integer unidades) {
		this.unidades = unidades;
	}

	public void setValor_unitario(double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}

	public void setValor_total(double valor_total) {
		this.valor_total = valor_total;
	}

	public void setCancelo_copago(String cancelo_copago) {
		this.cancelo_copago = cancelo_copago;
	}

	public void setCreacion_date(Timestamp creacion_date) {
		this.creacion_date = creacion_date;
	}

	public void setUltimo_update(Timestamp ultimo_update) {
		this.ultimo_update = ultimo_update;
	}

	public void setDelete_date(Timestamp delete_date) {
		this.delete_date = delete_date;
	}

	public void setCreacion_user(String creacion_user) {
		this.creacion_user = creacion_user;
	}

	public void setUltimo_user(String ultimo_user) {
		this.ultimo_user = ultimo_user;
	}

	public void setDelete_user(String delete_user) {
		this.delete_user = delete_user;
	}

	public void setValor_real(double valor_real) {
		this.valor_real = valor_real;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}

	public void setIncremento(double incremento) {
		this.incremento = incremento;
	}

	public void setCantidad_entregada(Integer cantidad_entregada) {
		this.cantidad_entregada = cantidad_entregada;
	}

	public void setReferencia_pyp(String referencia_pyp) {
		this.referencia_pyp = referencia_pyp;
	}

	public void setTipo_medicamento(String tipo_medicamento) {
		this.tipo_medicamento = tipo_medicamento;
	}

	public void setNombre_generico(String nombre_generico) {
		this.nombre_generico = nombre_generico;
	}

	public void setForma_farmaceutica(String forma_farmaceutica) {
		this.forma_farmaceutica = forma_farmaceutica;
	}

	public void setConcentracion_medicamento(String concentracion_medicamento) {
		this.concentracion_medicamento = concentracion_medicamento;
	}

	public void setUnidad_medida(String unidad_medida) {
		this.unidad_medida = unidad_medida;
	}

	/************** METODOS GET **************/

	public String getCodigo_empresa() {
		return codigo_empresa;
	}

	public String getCodigo_sucursal() {
		return codigo_sucursal;
	}

	public String getNro_factura() {
		return nro_factura;
	}

	public String getConsecutivo() {
		return consecutivo;
	}

	public String getCodigo_bodega() {
		return codigo_bodega;
	}

	public String getCodigo_medicamento() {
		return codigo_medicamento;
	}

	public String getCodigo_lote() {
		return codigo_lote;
	}

	public Integer getUnidades() {
		return unidades;
	}

	public double getValor_unitario() {
		return valor_unitario;
	}

	public double getValor_total() {
		return valor_total;
	}

	public String getCancelo_copago() {
		return cancelo_copago;
	}

	public Timestamp getCreacion_date() {
		return creacion_date;
	}

	public Timestamp getUltimo_update() {
		return ultimo_update;
	}

	public Timestamp getDelete_date() {
		return delete_date;
	}

	public String getCreacion_user() {
		return creacion_user;
	}

	public String getUltimo_user() {
		return ultimo_user;
	}

	public String getDelete_user() {
		return delete_user;
	}

	public double getValor_real() {
		return valor_real;
	}

	public double getDescuento() {
		return descuento;
	}

	public double getIncremento() {
		return incremento;
	}

	public Integer getCantidad_entregada() {
		return cantidad_entregada;
	}

	public String getReferencia_pyp() {
		return referencia_pyp;
	}

	public String getTipo_medicamento() {
		return tipo_medicamento;
	}

	public String getNombre_generico() {
		return nombre_generico;
	}

	public String getForma_farmaceutica() {
		return forma_farmaceutica;
	}

	public String getConcentracion_medicamento() {
		return concentracion_medicamento;
	}

	public String getUnidad_medida() {
		return unidad_medida;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Facturacion_medicamento getFacturacion_medicamento() {
		return facturacion_medicamento;
	}

	public void setFacturacion_medicamento(
			Facturacion_medicamento facturacion_medicamento) {
		this.facturacion_medicamento = facturacion_medicamento;
	}
}