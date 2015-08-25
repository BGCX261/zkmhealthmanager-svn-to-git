/*
 * Articulo.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 * Ing. Luis Miguel Hernández Pérez
 */
package contaweb.modelo.bean;

import java.io.Serializable;
import java.sql.*;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.ibatis.type.Alias;

@Alias("articulo")
public class Articulo implements Serializable {

	/************** ATRIBUTOS **************/

	private String codigo_empresa;
	private String codigo_sucursal;
	private String codigo_articulo;
	private String codigo_barra;
	private String nombre1;
	private String nombre2;
	private String nombre3;
	private String referencia;
	private String grupo1;
	private String grupo2;
	private String unidad_medida;
	private String presentacion;
	private String concentracion;
	private String unidad_concentracion;
	private String sin_existencia;
	private String producto;
	private String no_inv;
	private String gasto;
	private String venta;
	private String maneja_costo;
	private String pos;
	private String activo_fijo;
	private double costo;
	private String grava_iva;
	private double iva;
	private double utilidad;
	private double precio1;
	private double precio2;
	private double precio3;
	private double precio4;
	private Integer maximo_permitido;
	private Integer minimo_permitido;
	private Integer punto_reorden;
	private String fabricante;
	private String proveedor;
	private String observaciones;
	private Timestamp creacion_date;
	private Timestamp ultimo_update;
	private Timestamp delete_date;
	private String creacion_user;
	private String ultimo_user;
	private String delete_user;
	private String cum;
	private String registro_sanitario;
	private String vencimiento;
	private Timestamp estado_registro;
	private String modalidad;
	private String cantidad;
	private String titular;
	private String codigo_contabilidad;
	private String codigo_unidad_funcional;
	private Integer unm;
	private String und_unm;
	private boolean facturable;
	private boolean servicio_gravado;
	private String pyp;
	private String alto_costo;
	private String via;
	private double valor_adicional;
	private String comercial;
	private String vademecum_institucional;

	// adicionales
	private Timestamp fecha_compra_activo;
	private double valor_activo;
	private Integer vida_util;
	private String color_activo;
	private String marca_activo;
	private String modelo_activo;
	private String serial_activo;
	private String estado_activo;
	private String empleado;
	private boolean depreciable;
	private String cargo_empleado;
	private String puesto_empleado;
	private String diferido;
	private Boolean servicio_intangible;
	private String referencia_articulo;
	private Integer cantidad_maxima;

	/*** Constructor Por Defecto ***/
	public Articulo() {
	}

	/*** Sobre carga de Constructor ***/
	public Articulo(String codigo_empresa, String codigo_sucursal,
			String codigo_articulo, String codigo_barra, String nombre1,
			String nombre2, String nombre3, String referencia, String grupo1,
			String grupo2, String unidad_medida, String presentacion,
			String concentracion, String unidad_concentracion,
			String sin_existencia, String producto, String no_inv,
			String gasto, String venta, String maneja_costo, String pos,
			String activo_fijo, double costo, String grava_iva, double iva,
			double utilidad, double precio1, double precio2, double precio3,
			double precio4, Integer maximo_permitido, Integer minimo_permitido,
			Integer punto_reorden, String fabricante, String proveedor,
			String observaciones, Timestamp creacion_date,
			Timestamp ultimo_update, Timestamp delete_date,
			String creacion_user, String ultimo_user, String delete_user,
			String cum, String registro_sanitario, String vencimiento,
			Timestamp estado_registro, String modalidad, String cantidad,
			String titular, String codigo_contabilidad,
			String codigo_unidad_funcional, Integer unm, String und_unm,
			boolean facturable, boolean servicio_gravado, String pyp,
			String alto_costo, String via, double valor_adicional,
			String vademecum_institucional) {
		this.codigo_empresa = codigo_empresa;
		this.codigo_sucursal = codigo_sucursal;
		this.codigo_articulo = codigo_articulo;
		this.codigo_barra = codigo_barra;
		this.nombre1 = nombre1;
		this.nombre2 = nombre2;
		this.nombre3 = nombre3;
		this.referencia = referencia;
		this.grupo1 = grupo1;
		this.grupo2 = grupo2;
		this.unidad_medida = unidad_medida;
		this.presentacion = presentacion;
		this.concentracion = concentracion;
		this.unidad_concentracion = unidad_concentracion;
		this.sin_existencia = sin_existencia;
		this.producto = producto;
		this.no_inv = no_inv;
		this.gasto = gasto;
		this.venta = venta;
		this.maneja_costo = maneja_costo;
		this.pos = pos;
		this.activo_fijo = activo_fijo;
		this.costo = costo;
		this.grava_iva = grava_iva;
		this.iva = iva;
		this.utilidad = utilidad;
		this.precio1 = precio1;
		this.precio2 = precio2;
		this.precio3 = precio3;
		this.precio4 = precio4;
		this.maximo_permitido = maximo_permitido;
		this.minimo_permitido = minimo_permitido;
		this.punto_reorden = punto_reorden;
		this.fabricante = fabricante;
		this.proveedor = proveedor;
		this.observaciones = observaciones;
		this.creacion_date = creacion_date;
		this.ultimo_update = ultimo_update;
		this.delete_date = delete_date;
		this.creacion_user = creacion_user;
		this.ultimo_user = ultimo_user;
		this.delete_user = delete_user;
		this.cum = cum;
		this.registro_sanitario = registro_sanitario;
		this.vencimiento = vencimiento;
		this.estado_registro = estado_registro;
		this.modalidad = modalidad;
		this.cantidad = cantidad;
		this.titular = titular;
		this.codigo_contabilidad = codigo_contabilidad;
		this.codigo_unidad_funcional = codigo_unidad_funcional;
		this.unm = unm;
		this.und_unm = und_unm;
		this.facturable = facturable;
		this.servicio_gravado = servicio_gravado;
		this.pyp = pyp;
		this.alto_costo = alto_costo;
		this.via = via;
		this.valor_adicional = valor_adicional;
		this.vademecum_institucional = vademecum_institucional;
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

	public void setCodigo_articulo(String codigo_articulo) {
		this.codigo_articulo = codigo_articulo;
	}

	public void setCodigo_barra(String codigo_barra) {
		this.codigo_barra = codigo_barra;
	}

	public void setNombre1(String nombre1) {
		this.nombre1 = nombre1;
	}

	public void setNombre2(String nombre2) {
		this.nombre2 = nombre2;
	}

	public void setNombre3(String nombre3) {
		this.nombre3 = nombre3;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public void setGrupo1(String grupo1) {
		this.grupo1 = grupo1;
	}

	public void setGrupo2(String grupo2) {
		this.grupo2 = grupo2;
	}

	public void setUnidad_medida(String unidad_medida) {
		this.unidad_medida = unidad_medida;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public void setConcentracion(String concentracion) {
		this.concentracion = concentracion;
	}

	public void setUnidad_concentracion(String unidad_concentracion) {
		this.unidad_concentracion = unidad_concentracion;
	}

	public void setSin_existencia(String sin_existencia) {
		this.sin_existencia = sin_existencia;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public void setNo_inv(String no_inv) {
		this.no_inv = no_inv;
	}

	public void setGasto(String gasto) {
		this.gasto = gasto;
	}

	public void setVenta(String venta) {
		this.venta = venta;
	}

	public void setManeja_costo(String maneja_costo) {
		this.maneja_costo = maneja_costo;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public void setActivo_fijo(String activo_fijo) {
		this.activo_fijo = activo_fijo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public void setGrava_iva(String grava_iva) {
		this.grava_iva = grava_iva;
	}

	public void setIva(double iva) {
		this.iva = iva;
	}

	public void setUtilidad(double utilidad) {
		this.utilidad = utilidad;
	}

	public void setPrecio1(double precio1) {
		this.precio1 = precio1;
	}

	public void setPrecio2(double precio2) {
		this.precio2 = precio2;
	}

	public void setPrecio3(double precio3) {
		this.precio3 = precio3;
	}

	public void setPrecio4(double precio4) {
		this.precio4 = precio4;
	}

	public void setMaximo_permitido(Integer maximo_permitido) {
		this.maximo_permitido = maximo_permitido;
	}

	public void setMinimo_permitido(Integer minimo_permitido) {
		this.minimo_permitido = minimo_permitido;
	}

	public void setPunto_reorden(Integer punto_reorden) {
		this.punto_reorden = punto_reorden;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
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

	public void setCum(String cum) {
		this.cum = cum;
	}

	public void setRegistro_sanitario(String registro_sanitario) {
		this.registro_sanitario = registro_sanitario;
	}

	public void setVencimiento(String vencimiento) {
		this.vencimiento = vencimiento;
	}

	public void setEstado_registro(Timestamp estado_registro) {
		this.estado_registro = estado_registro;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public void setCodigo_contabilidad(String codigo_contabilidad) {
		this.codigo_contabilidad = codigo_contabilidad;
	}

	public void setCodigo_unidad_funcional(String codigo_unidad_funcional) {
		this.codigo_unidad_funcional = codigo_unidad_funcional;
	}

	public void setUnm(Integer unm) {
		this.unm = unm;
	}

	public void setUnd_unm(String und_unm) {
		this.und_unm = und_unm;
	}

	public void setFacturable(boolean facturable) {
		this.facturable = facturable;
	}

	public void setServicio_gravado(boolean servicio_gravado) {
		this.servicio_gravado = servicio_gravado;
	}

	public void setPyp(String pyp) {
		this.pyp = pyp;
	}

	public void setAlto_costo(String alto_costo) {
		this.alto_costo = alto_costo;
	}

	public void setVia(String via) {
		this.via = via;
	}

	public void setValor_adicional(double valor_adicional) {
		this.valor_adicional = valor_adicional;
	}

	public void setComercial(String comercial) {
		this.comercial = comercial;
	}

	public void setVademecum_institucional(String vademecum_institucional) {
		this.vademecum_institucional = vademecum_institucional;
	}

	public void setFecha_compra_activo(Timestamp fecha_compra_activo) {
		this.fecha_compra_activo = fecha_compra_activo;
	}

	public void setValor_activo(double valor_activo) {
		this.valor_activo = valor_activo;
	}

	public void setVida_util(Integer vida_util) {
		this.vida_util = vida_util;
	}

	public void setColor_activo(String color_activo) {
		this.color_activo = color_activo;
	}

	public void setMarca_activo(String marca_activo) {
		this.marca_activo = marca_activo;
	}

	public void setModelo_activo(String modelo_activo) {
		this.modelo_activo = modelo_activo;
	}

	public void setSerial_activo(String serial_activo) {
		this.serial_activo = serial_activo;
	}

	public void setEstado_activo(String estado_activo) {
		this.estado_activo = estado_activo;
	}

	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}

	public void setDepreciable(boolean depreciable) {
		this.depreciable = depreciable;
	}

	public void setCargo_empleado(String cargo_empleado) {
		this.cargo_empleado = cargo_empleado;
	}

	public void setPuesto_empleado(String puesto_empleado) {
		this.puesto_empleado = puesto_empleado;
	}

	public void setDiferido(String diferido) {
		this.diferido = diferido;
	}

	public void setServicio_intangible(Boolean servicio_intangible) {
		this.servicio_intangible = servicio_intangible;
	}

	public void setReferencia_articulo(String referencia_articulo) {
		this.referencia_articulo = referencia_articulo;
	}

	/************** METODOS GET **************/

	public String getCodigo_empresa() {
		return codigo_empresa;
	}

	public String getCodigo_sucursal() {
		return codigo_sucursal;
	}

	public String getCodigo_articulo() {
		return codigo_articulo;
	}

	public String getCodigo_barra() {
		return codigo_barra;
	}

	public String getNombre1() {
		return nombre1;
	}

	public String getNombre2() {
		return nombre2;
	}

	public String getNombre3() {
		return nombre3;
	}

	public String getReferencia() {
		return referencia;
	}

	public String getGrupo1() {
		return grupo1;
	}

	public String getGrupo2() {
		return grupo2;
	}

	public String getUnidad_medida() {
		return unidad_medida;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public String getConcentracion() {
		return concentracion;
	}

	public String getUnidad_concentracion() {
		return unidad_concentracion;
	}

	public String getSin_existencia() {
		return sin_existencia;
	}

	public String getProducto() {
		return producto;
	}

	public String getNo_inv() {
		return no_inv;
	}

	public String getGasto() {
		return gasto;
	}

	public String getVenta() {
		return venta;
	}

	public String getManeja_costo() {
		return maneja_costo;
	}

	public String getPos() {
		return pos;
	}

	public String getActivo_fijo() {
		return activo_fijo;
	}

	public double getCosto() {
		return costo;
	}

	public String getGrava_iva() {
		return grava_iva;
	}

	public double getIva() {
		return iva;
	}

	public double getUtilidad() {
		return utilidad;
	}

	public double getPrecio1() {
		return precio1;
	}

	public double getPrecio2() {
		return precio2;
	}

	public double getPrecio3() {
		return precio3;
	}

	public double getPrecio4() {
		return precio4;
	}

	public Integer getMaximo_permitido() {
		return maximo_permitido;
	}

	public Integer getMinimo_permitido() {
		return minimo_permitido;
	}

	public Integer getPunto_reorden() {
		return punto_reorden;
	}

	public String getFabricante() {
		return fabricante;
	}

	public String getProveedor() {
		return proveedor;
	}

	public String getObservaciones() {
		return observaciones;
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

	public String getCum() {
		return cum;
	}

	public String getRegistro_sanitario() {
		return registro_sanitario;
	}

	public String getVencimiento() {
		return vencimiento;
	}

	public Timestamp getEstado_registro() {
		return estado_registro;
	}

	public String getModalidad() {
		return modalidad;
	}

	public String getCantidad() {
		return cantidad;
	}

	public String getTitular() {
		return titular;
	}

	public String getCodigo_contabilidad() {
		return codigo_contabilidad;
	}

	public String getCodigo_unidad_funcional() {
		return codigo_unidad_funcional;
	}

	public Integer getUnm() {
		return unm;
	}

	public String getUnd_unm() {
		return und_unm;
	}

	public boolean getFacturable() {
		return facturable;
	}

	public boolean getServicio_gravado() {
		return servicio_gravado;
	}

	public String getPyp() {
		return pyp;
	}

	public String getAlto_costo() {
		return alto_costo;
	}

	public String getVia() {
		return via;
	}

	public double getValor_adicional() {
		return valor_adicional;
	}

	public String getComercial() {
		return comercial;
	}

	public String getVademecum_institucional() {
		return vademecum_institucional;
	}

	public Timestamp getFecha_compra_activo() {
		return fecha_compra_activo;
	}

	public double getValor_activo() {
		return valor_activo;
	}

	public Integer getVida_util() {
		return vida_util;
	}

	public String getColor_activo() {
		return color_activo;
	}

	public String getMarca_activo() {
		return marca_activo;
	}

	public String getModelo_activo() {
		return modelo_activo;
	}

	public String getSerial_activo() {
		return serial_activo;
	}

	public String getEstado_activo() {
		return estado_activo;
	}

	public String getEmpleado() {
		return empleado;
	}

	public boolean getDepreciable() {
		return depreciable;
	}

	public String getCargo_empleado() {
		return cargo_empleado;
	}

	public String getPuesto_empleado() {
		return puesto_empleado;
	}

	public String getDiferido() {
		return diferido;
	}

	public Boolean getServicio_intangible() {
		return servicio_intangible;
	}

	public String getReferencia_articulo() {
		return referencia_articulo;
	}

	public Integer getCantidad_maxima() {
		return cantidad_maxima;
	}

	public void setCantidad_maxima(Integer cantidad_maxima) {
		this.cantidad_maxima = cantidad_maxima;
	}

}
