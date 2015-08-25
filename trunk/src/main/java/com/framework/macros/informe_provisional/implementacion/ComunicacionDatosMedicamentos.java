package com.framework.macros.informe_provisional.implementacion;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

import org.zkoss.zul.Columns;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

public class ComunicacionDatosMedicamentos extends ComunicadorAbstract {

	@Override
	protected String getIdXml() {
		return "getDatosMedicamentosInsumos";
	}

	@Override
	protected void montarCabecera(Columns columns) {
		columns.appendChild(getColumn("Documento", "120px"));
		columns.appendChild(getColumn("Tipo ident", "100px"));
		columns.appendChild(getColumn("Primer Apellido", "170px"));
		columns.appendChild(getColumn("Segundo Apellido", "170px"));
		columns.appendChild(getColumn("Primer nombre", "170px"));
		columns.appendChild(getColumn("Segundo nombre", "170px"));
		
		columns.appendChild(getColumn("Fecha nacimiento", "100px"));
		columns.appendChild(getColumn("Sexo", "100px"));

		columns.appendChild(getColumn("codigo centro", "120px"));
		columns.appendChild(getColumn("Nómbre centro", "170px"));
		columns.appendChild(getColumn("Tipo factura", "120px"));
		columns.appendChild(getColumn("Via_ingreso", "170px"));
		// columns.appendChild(getColumn("via_ingreso", "100px"));
//		columns.appendChild(getColumn("nro_identificacion", "100px"));
		columns.appendChild(getColumn("Id plan", "120px"));
		columns.appendChild(getColumn("Fecha medicamento", "120px"));
		columns.appendChild(getColumn("Número autorizacion", "120px"));
		columns.appendChild(getColumn("codigo medicamento", "120px"));
		columns.appendChild(getColumn("Nombre medicamento", "170px"));
//		columns.appendChild(getColumn("Tipo medicamento", "100px"));
		columns.appendChild(getColumn("Tipo", "120px"));
		columns.appendChild(getColumn("Unidades", "100px"));
		columns.appendChild(getColumn("Valor unitario", "100px"));
		columns.appendChild(getColumn("Valor total", "100px"));
		columns.appendChild(getColumn("Facturacion", "100px"));
		columns.appendChild(getColumn("codigo administradora", "100px"));
		columns.appendChild(getColumn("Nombre administradora", "170px"));
		columns.appendChild(getColumn("codigo medico", "100px"));
		columns.appendChild(getColumn("Apellido médico", "170px"));
		columns.appendChild(getColumn("Nombre médico", "170px"));
	}

	@Override
	protected void crearFilas(Object dato, Row row) {
		Map<String, Object> map_resultado = (Map<String, Object>) dato;
		String documento = (String) map_resultado.get("documento");
		String tipo_identificacion = (String) map_resultado
				.get("tipo_identificacion");
		String apellido1 = (String) map_resultado.get("apellido1");
		String apellido2 = (String) map_resultado.get("apellido2");
		String nombre1 = (String) map_resultado.get("nombre1");
		String nombre2 = (String) map_resultado.get("nombre2");
		Date fecha_nacimiento = (Date) map_resultado
				.get("fecha_nacimiento");
		String sexo = (String) map_resultado.get("sexo");
		
		String codigo_centro = (String) map_resultado.get("codigo_centro");
		String nombre_centro = (String) map_resultado.get("nombre_centro");
		String tipo_factura = (String) map_resultado.get("tipo_factura");
		String nombre_via_ingreso = (String) map_resultado
				.get("nombre_via_ingreso");
		
		String id_plan = (String) map_resultado.get("id_plan");
		Date fecha_medicamento = (Date) map_resultado.get("fecha_medicamento");
		String numero_autorizacion = (String) map_resultado
				.get("numero_autorizacion");
		
		String codigo_medicamento = (String) map_resultado.get("codigo_medicamento");
		String nombre_medicamento = (String) map_resultado.get("nombre_medicamento");
		
		String descripcion_tipo = (String) map_resultado.get("descripcion_tipo");
		BigDecimal valor_unitario = (BigDecimal) map_resultado.get("valor_unitario");
		BigDecimal valor_total  = (BigDecimal) map_resultado.get("valor_total");
		Integer unidades = (Integer) map_resultado.get("unidades"); 
		
		String codigo_administradora = (String) map_resultado
				.get("codigo_administradora");
		String nombre_administradora = (String) map_resultado.get("nombre");
		String codigo_medico = (String) map_resultado.get("codigo_medico");
		String apellido_medico = (String) map_resultado.get("apellido_medico");
		String nombre_medico = (String) map_resultado.get("nombre_medico");
		String facturacion = (String) map_resultado.get("facturacion");
		
		row.appendChild(new Label(documento));
		row.appendChild(new Label(tipo_identificacion));
		row.appendChild(new Label(apellido1));
		row.appendChild(new Label(apellido2));
		row.appendChild(new Label(nombre1));
		row.appendChild(new Label(nombre2));
		
		row.appendChild(new Label(dateFormat.format(fecha_nacimiento)));
		row.appendChild(new Label(sexo));
		row.appendChild(new Label(codigo_centro));
		row.appendChild(new Label(nombre_centro));
		row.appendChild(new Label(tipo_factura));
		row.appendChild(new Label(nombre_via_ingreso));
		row.appendChild(new Label(id_plan));
		row.appendChild(new Label(dateFormat.format(fecha_medicamento)));
		row.appendChild(new Label(numero_autorizacion));
		row.appendChild(new Label(codigo_medicamento));
		row.appendChild(new Label(nombre_medicamento));
		row.appendChild(new Label(descripcion_tipo));
		row.appendChild(new Label(unidades + ""));
		row.appendChild(new Label(decimalFormat.format(valor_unitario))); 
		row.appendChild(new Label(decimalFormat.format(valor_total))); 
		row.appendChild(new Label(facturacion));
		row.appendChild(new Label(codigo_administradora));
		row.appendChild(new Label(nombre_administradora));
		row.appendChild(new Label(codigo_medico));
		row.appendChild(new Label(apellido_medico));
		row.appendChild(new Label(nombre_medico));
	}

}
