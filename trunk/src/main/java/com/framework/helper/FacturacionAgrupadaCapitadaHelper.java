package com.framework.helper;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.UsuariosService;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.util.Num_letter;
import com.framework.util.Util;
import com.framework.util.Utilidades;

import contaweb.modelo.bean.Detalle_factura;
import contaweb.modelo.bean.Facturacion;
import contaweb.modelo.service.Detalle_facturaService;
import contaweb.modelo.service.FacturacionService;

public class FacturacionAgrupadaCapitadaHelper {

	public static Logger log = Logger
			.getLogger(FacturacionAgrupadaCapitadaHelper.class);

	/**
	 * Este metodo me valida la salida del reporte de factura
	 * 
	 * @author Luis Miguel
	 * @author Ferney Jimenez
	 * */
	public static List<Map<String, Object>> validarFactura3(List<Map> listado,
			String codigo_empresa, String codigo_sucursal) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String tipo_fact = "GEN_AGR";
		int i = 0;
		for (Map<String, Object> mapTemp : listado) {
			String dct = (String) mapTemp.get("codigo_documento");
			Timestamp fecha = (Timestamp) mapTemp.get("fecha");
			Timestamp fecha_inicio = (Timestamp) mapTemp.get("fecha_inicio");
			Timestamp fecha_final = (Timestamp) mapTemp.get("fecha_final");
			Timestamp fecha_vencimiento = (Timestamp) mapTemp
					.get("fecha_vencimiento");
			Timestamp fecha_interna = (Timestamp) mapTemp.get("fecha_interna");
			String codigo_administradora = (String) mapTemp
					.get("codigo_administradora");
			String id_plan = (String) mapTemp.get("id_plan");
			int plazo = (Integer) mapTemp.get("plazo");
			String codigo_tercero = (String) mapTemp.get("codigo_tercero");
			String creacion_user = (String) mapTemp.get("creacion_user");
			String nro_poliza = (String) mapTemp.get("nro_poliza");
			double valor_total = ((BigDecimal) mapTemp.get("valor_total"))
					.doubleValue();
			double dto_factura = ((BigDecimal) mapTemp.get("dto_factura"))
					.doubleValue();
			double dto_copago = ((BigDecimal) mapTemp.get("dto_copago"))
					.doubleValue();
			double valor_copago = ((BigDecimal) mapTemp.get("valor_copago"))
					.doubleValue();
			String codigo_articulo = (String) mapTemp.get("codigo_articulo");
			String detalle = (String) mapTemp.get("detalle");
			String descripcion = (String) mapTemp.get("descripcion");
			int cantidad = ((Double) mapTemp.get("cantidad")).intValue();
			// double valor_unitario = ((BigDecimal)
			// mapTemp.get("valor_unitario")).doubleValue();
			// double total = ((BigDecimal) mapTemp.get("total")).doubleValue();
			double retencion = ((BigDecimal) mapTemp.get("retencion"))
					.doubleValue();
			String tipo = (String) mapTemp.get("tipo");
			String concepto = (String) mapTemp.get("concepto");

			tipo_fact = tipo;

			Usuarios usuarios = new Usuarios();
			usuarios.setCodigo_empresa(codigo_empresa);
			usuarios.setCodigo_sucursal(codigo_sucursal);
			usuarios.setCodigo(creacion_user);
			usuarios = getServiceLocator().getServicio(UsuariosService.class)
					.consultar(usuarios);

			Administradora admin = new Administradora();
			admin.setCodigo_empresa(codigo_empresa);
			admin.setCodigo_sucursal(codigo_sucursal);
			admin.setCodigo(codigo_administradora);
			admin = getServiceLocator()
					.getServicio(AdministradoraService.class).consultar(admin);

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(codigo_empresa);
			contratos.setCodigo_sucursal(codigo_sucursal);
			contratos.setCodigo_administradora(codigo_administradora);
			contratos.setId_plan(id_plan);
			contratos = getServiceLocator().getServicio(ContratosService.class)
					.consultar(contratos);

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(codigo_empresa);
			paciente.setCodigo_sucursal(codigo_sucursal);
			paciente.setNro_identificacion(codigo_tercero);
			paciente = getServiceLocator().getServicio(PacienteService.class)
					.consultar(paciente);

			if (tipo.equals("GEN_AGR")) {
				Map<String, Object> params_fact = new HashMap<String, Object>();
				params_fact.put("codigo_empresa", codigo_empresa);
				params_fact.put("codigo_sucursal", codigo_sucursal);
				params_fact.put("codigo_comprobante", "15");
				params_fact.put("codigo_documento", codigo_articulo);
				params_fact.put("facturable", true);

				Facturacion fact = new Facturacion();
				fact.setCodigo_empresa(codigo_empresa);
				fact.setCodigo_sucursal(codigo_sucursal);
				fact.setCodigo_comprobante("15");
				// fact.setCodigo_documento(codigo_articulo);
				fact = getServiceLocator()
						.getServicio(FacturacionService.class).consultar(fact);
				double valor_neto = (Utilidades
						.obtenerValorPrimitivo(getServiceLocator().getServicio(
								Detalle_facturaService.class)
								.totalFacturaClinica(params_fact)) - fact
						.getValor_copago());
				double valor_cop = fact.getValor_copago();

				Map<String, Object> parameter = new HashMap<String, Object>();
				parameter.put("codigo_empresa", codigo_empresa);
				parameter.put("codigo_sucursal", codigo_sucursal);
				parameter.put("codigo_comprobante", "15");
				parameter.put("codigo_documento", codigo_articulo);
				List lista_detalle = getServiceLocator().getServicio(
						Detalle_facturaService.class).listar(parameter);

				for (Object object : lista_detalle) {
					Detalle_factura df = (Detalle_factura) object;
					boolean unds = true;
					if (df.getTipo().equals("MEDICAMENTO")
							|| df.getTipo().equals("MATERIALES/INSUMOS")) {
						if (df.getCantidad() <= 0) {
							unds = false;
						}
					}

					if (df.getFacturable() && unds) {

						double valor_item = df.getValor_unitario();
						double dto_cop = 0;
						if (valor_cop > 0) {
							dto_cop = (((valor_item * 100) / (valor_neto + valor_cop)) / 100)
									* valor_cop;
							valor_item = valor_item - dto_cop;
						}

						// //System.Out.Println("dct: "+dct+" codigo art: "+codigo_articulo+" valor_item: "+valor_item+
						// " valor_total: "+df.getValor_unitario()+" valor_cop: "+valor_cop+" valor_fact: "+valor_neto);

						Map<String, Object> map = new HashMap<String, Object>();
						map.put("codigo_documento", dct);
						map.put("fecha", fecha);
						map.put("fecha_inicio", fact.getFecha_inicio());
						map.put("fecha_final", fecha_final);
						map.put("fecha_vencimiento", fecha_vencimiento);
						map.put("fecha_interna", fecha_interna);
						map.put("nit_admin", (admin != null ? admin.getNit()
								: ""));
						map.put("nom_admin", (admin != null ? admin.getNombre()
								: ""));
						map.put("dir_admin",
								(admin != null ? admin.getDireccion() : ""));

						Municipios municipios = new Municipios();
						municipios.setCoddep(admin.getCodigo_dpto());
						municipios.setCodigo(admin.getCodigo_municipio());
						municipios = getServiceLocator().getMunicipiosService()
								.consultar(municipios);

						map.put("ciud_admin",
								(municipios != null ? municipios.getNombre()
										: ""));
						map.put("tel_admin",
								(admin != null ? admin.getTelefono() : ""));
						map.put("plazo", new Integer(plazo));
						map.put("nro_identificacion", codigo_tercero);
						map.put("paciente",
								(paciente != null ? paciente.getNombre1() + " "
										+ paciente.getNombre2() + " "
										+ paciente.getApellido1() + " "
										+ paciente.getApellido2() : ""));
						map.put("sexo", (paciente != null ? (paciente.getSexo()
								.equals("M") ? "Masculino" : "Femenino") : ""));

						String edad = "";
						if (paciente != null)
							edad = Util.getEdadPrsentacion(paciente
									.getFecha_nacimiento());

						map.put("edad", edad);
						map.put("use",
								(usuarios != null) ? usuarios.getNombres()
										+ " " + usuarios.getApellidos() : "");

						Elemento elemento = new Elemento();
						elemento.setTipo("tipo_usuario");
						elemento.setCodigo(paciente != null ? paciente
								.getTipo_usuario() : "");
						elemento = getServiceLocator().getElementoService()
								.consultar(elemento);

						map.put("regimen",
								(elemento != null ? elemento.getDescripcion()
										: ""));
						map.put("nro_contrato",
								(contratos != null ? contratos
										.getNro_contrato() : ""));
						map.put("nro_poliza", nro_poliza);
						map.put("valor_total", new java.math.BigDecimal(
								valor_total));
						map.put("dto_factura", new java.math.BigDecimal(
								dto_factura));
						// map.put("dto_copago", new
						// java.math.BigDecimal(dto_copago));
						map.put("valor_copago", new java.math.BigDecimal(
								valor_copago));
						map.put("retencion",
								new java.math.BigDecimal(retencion));
						map.put("codigo_articulo", df.getCodigo_articulo()
								+ " " + df.getDetalle());
						map.put("detalle", detalle);
						map.put("descripcion", descripcion);
						map.put("cantidad", new Integer((int) df.getCantidad()));
						map.put("valor_unitario", new java.math.BigDecimal(
								valor_item));
						map.put("total", new java.math.BigDecimal(
								(valor_item * df.getCantidad())));
						map.put("factura_interna", codigo_articulo);
						map.put("dto_cop", new java.math.BigDecimal(dto_cop));
						map.put("tipo", tipo);
						map.put("concepto", concepto != null ? concepto : "");
						list.add(map);
						// result +=
						// Integer.parseInt(Main.formatDecimal((valor_item*df.getCantidad()),
						// "#0"));
					}
				}
			} else if (i == 0) {
				// log.info("facturacion capitada");
				// log.info("i es cero");
				Facturacion fact = new Facturacion();
				fact.setCodigo_empresa(codigo_empresa);
				fact.setCodigo_sucursal(codigo_sucursal);
				fact.setCodigo_comprobante("15");
				// fact.setCodigo_documento(codigo_articulo);
				fact = getServiceLocator()
						.getServicio(FacturacionService.class).consultar(fact);
				dto_copago = fact.getValor_copago();

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("codigo_documento", dct);
				// map.put("codigo_documento", codigo_documento);
				map.put("fecha", fecha);
				map.put("fecha_inicio", fecha_inicio);
				map.put("fecha_final", fecha_final);
				map.put("fecha_vencimiento", fecha_vencimiento);
				// map.put("fecha_interna", fecha_interna);
				map.put("fecha_interna", fecha);
				map.put("nit_admin", (admin != null ? admin.getNit() : ""));
				map.put("nom_admin", (admin != null ? admin.getNombre() : ""));
				map.put("dir_admin",
						(admin != null ? admin.getDireccion() : ""));

				Municipios municipios = new Municipios();
				municipios.setCoddep(admin.getCodigo_dpto());
				municipios.setCodigo(admin.getCodigo_municipio());
				municipios = getServiceLocator().getMunicipiosService()
						.consultar(municipios);

				map.put("ciud_admin",
						(municipios != null ? municipios.getNombre() : ""));
				map.put("tel_admin", (admin != null ? admin.getTelefono() : ""));
				map.put("plazo", new Integer(plazo));
				map.put("nro_identificacion", codigo_tercero);
				map.put("paciente",
						(paciente != null ? paciente.getNombre1() + " "
								+ paciente.getNombre2() + " "
								+ paciente.getApellido1() + " "
								+ paciente.getApellido2() : ""));

				map.put("sexo",
						(paciente != null ? (paciente.getSexo().equals("M") ? "Masculino"
								: "Femenino")
								: ""));

				String edad = "";
				if (paciente != null)
					edad = Util.getEdadPrsentacion(paciente
							.getFecha_nacimiento());

				map.put("edad", edad);

				map.put("use", (usuarios != null) ? usuarios.getNombres() + " "
						+ usuarios.getApellidos() : "");

				Elemento elemento = new Elemento();
				elemento.setTipo("tipo_usuario");
				elemento.setCodigo(paciente != null ? paciente
						.getTipo_usuario() : "");
				elemento = getServiceLocator().getElementoService().consultar(
						elemento);

				map.put("regimen",
						(elemento != null ? elemento.getDescripcion() : ""));

				map.put("nro_contrato",
						(contratos != null ? contratos.getNro_contrato() : ""));
				map.put("nro_poliza", nro_poliza);
				map.put("valor_total", new java.math.BigDecimal(valor_total));
				map.put("dto_factura", new java.math.BigDecimal(dto_factura));
				// map.put("dto_copago", new java.math.BigDecimal(dto_copago));
				map.put("valor_copago", new java.math.BigDecimal(valor_copago));
				map.put("retencion", new java.math.BigDecimal(retencion));
				map.put("codigo_articulo", "FACTURA CAP - "
						+ (contratos != null ? contratos.getNombre() : ""));
				// map.put("detalle", detalle);
				map.put("detalle", descripcion);
				map.put("descripcion", descripcion);
				map.put("cantidad", new Integer(cantidad));
				// map.put("valor_unitario", new
				// java.math.BigDecimal(valor_unitario));
				// map.put("total", new java.math.BigDecimal(total));
				map.put("valor_unitario", new java.math.BigDecimal(valor_total));
				map.put("total", new java.math.BigDecimal(valor_total));
				map.put("factura_interna", codigo_articulo);
				map.put("dto_cop", new java.math.BigDecimal(dto_copago));
				map.put("tipo", tipo);
				map.put("total_letras", Num_letter
						.convertirLetras((new java.math.BigDecimal(valor_total
								- retencion).intValue())));
				map.put("concepto", concepto != null ? concepto : "");
				list.add(map);
				break;
			}
			i++;
		}
		if (tipo_fact.equals("GEN_AGR")) {
			agrupar(list);
			ordenar(list);
		}
		return list;
	}

	private static void agrupar(List<Map<String, Object>> list) {
		Map<String, Object> general = new HashMap<String, Object>();

		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);

			String codigo_articulo = (String) map.get("codigo_articulo") + "_";
			String factura_interna = (String) map.get("factura_interna");
			// String detalle = (String) map.get("detalle");
			int cantidad = ((Integer) map.get("cantidad")).intValue();
			double total = ((java.math.BigDecimal) map.get("total"))
					.doubleValue();

			if (general.get(codigo_articulo + factura_interna) == null) {
				general.put(codigo_articulo + factura_interna, map);
			} else {
				Map<String, Object> aux = (Map<String, Object>) general
						.get(codigo_articulo + factura_interna);
				int can_aux = ((Integer) aux.get("cantidad")).intValue();
				int vu_aux = (int) ((java.math.BigDecimal) aux
						.get("valor_unitario")).doubleValue();
				int total_aux = (int) ((java.math.BigDecimal) aux.get("total"))
						.doubleValue();

				can_aux += cantidad;
				total_aux += total;
				if (can_aux > 0) {
					vu_aux = (total_aux / can_aux);
					total_aux = (vu_aux * can_aux);
				}

				aux.put("cantidad", new Integer(can_aux));
				aux.put("valor_unitario", new java.math.BigDecimal(vu_aux));
				aux.put("total", new java.math.BigDecimal(total_aux));
				general.put(codigo_articulo + factura_interna, aux);
			}
		}
		list.clear();
		Iterator it = general.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry e = (Map.Entry) it.next();
			Map map = (Map) e.getValue();
			list.add(map);
		}
	}

	private static void ordenar(List<Map<String, Object>> list) {
		Collections.sort(list, new Comparator() {

			public int compare(Object o1, Object o2) {
				Map<String, Object> aux1 = (Map<String, Object>) o1;
				Map<String, Object> aux2 = (Map<String, Object>) o2;

				String factura_interna1 = (String) aux1.get("factura_interna");
				String factura_interna2 = (String) aux2.get("factura_interna");

				return factura_interna1.compareToIgnoreCase(factura_interna2);
			}
		});
	}

	public static ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}
}
