package com.framework.macros.informe_provisional.implementacion;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.zkoss.zul.Columns;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

public class ComunicadorEstancias extends ComunicadorAbstract {

	protected SimpleDateFormat dateFormat_completa = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	protected String getIdXml() {
		return "getDatosEstancias";
	}

	@Override
	protected void montarCabecera(Columns columns) {
		columns.appendChild(getColumn("Documento", "120px"));
		columns.appendChild(getColumn("Tipo ident", "120px"));
		columns.appendChild(getColumn("Primer Apellido", "170px"));
		columns.appendChild(getColumn("Segundo Apellido", "170px"));
		columns.appendChild(getColumn("Primer nombre", "170px"));
		columns.appendChild(getColumn("Segundo nombre", "170px"));
		columns.appendChild(getColumn("Fecha nacimiento", "170px"));
		columns.appendChild(getColumn("Sexo", "170px"));

		columns.appendChild(getColumn("Id plan", "100px"));
		columns.appendChild(getColumn("Fecha estancia", "100px"));
		columns.appendChild(getColumn("Número autorizacion", "170px"));
		// columns.appendChild(getColumn("codigo_prestador", "170px"));
		columns.appendChild(getColumn("Estancia", "120px"));
		columns.appendChild(getColumn("Nombre Estancia", "170px"));
		columns.appendChild(getColumn("Valor total", "170px"));
		columns.appendChild(getColumn("Facturacion", "170px"));
		columns.appendChild(getColumn("Tipo facturacion", "170px"));
		columns.appendChild(getColumn("codigo Centro", "170px"));
		columns.appendChild(getColumn("Nómbre centro", "170px"));
		columns.appendChild(getColumn("codigo dministradora", "170px"));
		columns.appendChild(getColumn("Nómbre", "170px"));
		columns.appendChild(getColumn("Tipo ident médico", "170px"));
		columns.appendChild(getColumn("Nro ident médico", "120px"));
		columns.appendChild(getColumn("Apellidos médico", "170px"));
		columns.appendChild(getColumn("Nombres médico", "170px"));
		columns.appendChild(getColumn("Fecha ingreso", "120px"));
		columns.appendChild(getColumn("Fecha egreso", "120px"));
		columns.appendChild(getColumn("Dias", "100px"));
		columns.appendChild(getColumn("Diagnostico", "100px"));
		columns.appendChild(getColumn("Estado salida", "170px"));
		columns.appendChild(getColumn("Causa externa", "170px"));
		columns.appendChild(getColumn("Destino salida", "170px"));
		columns.appendChild(getColumn("Via ingreso", "170px"));
	}

	@Override
	protected void crearFilas(Object dato, Row row) {
		Map<String, Object> map_resultado = (Map<String, Object>) dato;
		String documento_paciente = (String) map_resultado
				.get("documento_paciente");
		String tipo_identificacion = (String) map_resultado
				.get("tipo_identificacion");
		String apellido1 = (String) map_resultado.get("apellido1");
		String apellido2 = (String) map_resultado.get("apellido2");
		String nombre1 = (String) map_resultado.get("nombre1");
		String nombre2 = (String) map_resultado.get("nombre2");
		Date fecha_nacimiento = (Date) map_resultado.get("fecha_nacimiento");
		String sexo = (String) map_resultado.get("sexo");
		String id_plan = (String) map_resultado.get("id_plan");
		Timestamp fecha_estancia = (Timestamp) map_resultado
				.get("fecha_estancia");
		String numero_autorizacion = (String) map_resultado
				.get("numero_autorizacion");
		String estancia = (String) map_resultado.get("estancia");
		String nombre_pcd = (String) map_resultado.get("nombre_pcd");
		BigDecimal valor_total = (BigDecimal) map_resultado.get("valor_total");
		String facturacion = (String) map_resultado.get("facturacion");
		String tipo_facturacion = (String) map_resultado
				.get("tipo_facturacion");
		String codigo_centro = (String) map_resultado.get("codigo_centro");
		String nombre_centro = (String) map_resultado.get("nombre_centro");
		String codigo_administradora = (String) map_resultado
				.get("codigo_administradora");
		String nombre_administradora = (String) map_resultado.get("nombre");
		String tipo_identificacion_medico = (String) map_resultado
				.get("tipo_identificacion_medico");
		String nro_identificacion_medico = (String) map_resultado
				.get("nro_identificacion_medico");
		String apellidos_medico = (String) map_resultado
				.get("apellidos_medico");
		String nombres_medico = (String) map_resultado.get("nombres_medico");
		Timestamp fecha_ingreso = (Timestamp) map_resultado
				.get("fecha_ingreso");
		Timestamp fecha_egreso = (Timestamp) map_resultado.get("fecha_egreso");
		Integer dias = (Integer) map_resultado.get("dias");
		String diagnostico = (String) map_resultado.get("diagnostico");
		String descipcion_estado_salida = (String) map_resultado
				.get("descipcion_estado_salida");
		String descripcion_causa_externa = (String) map_resultado
				.get("descripcion_causa_externa");
		String descripcion_destino_salida = (String) map_resultado
				.get("descripcion_destino_salida");
		String descripcion_via_ingreso = (String) map_resultado
				.get("descripcion_via_ingreso");

		row.appendChild(new Label(documento_paciente));
		row.appendChild(new Label(tipo_identificacion));
		row.appendChild(new Label(apellido1));
		row.appendChild(new Label(apellido2));
		row.appendChild(new Label(nombre1));
		row.appendChild(new Label(nombre2));
		row.appendChild(new Label(dateFormat.format(fecha_nacimiento))); 
		row.appendChild(new Label(sexo));
		row.appendChild(new Label(id_plan));
		row.appendChild(new Label(dateFormat_completa.format(fecha_estancia)));
		row.appendChild(new Label(numero_autorizacion));
		row.appendChild(new Label(estancia));
		row.appendChild(new Label(nombre_pcd));
		row.appendChild(new Label(decimalFormat.format(valor_total))); 
		row.appendChild(new Label(facturacion));
		row.appendChild(new Label(getNombreTipo(tipo_facturacion))); 
		row.appendChild(new Label(codigo_centro));
		row.appendChild(new Label(nombre_centro));
		row.appendChild(new Label(codigo_administradora));
		row.appendChild(new Label(nombre_administradora));
		row.appendChild(new Label(tipo_identificacion_medico));
		row.appendChild(new Label(nro_identificacion_medico));
		row.appendChild(new Label(apellidos_medico));
		row.appendChild(new Label(nombres_medico));
		row.appendChild(new Label(dateFormat_completa.format(fecha_ingreso)));
		row.appendChild(new Label(dateFormat_completa.format(fecha_egreso)));
		row.appendChild(new Label(dias + ""));
		row.appendChild(new Label(diagnostico));
		row.appendChild(new Label(descipcion_estado_salida));
		row.appendChild(new Label(descripcion_causa_externa));
		row.appendChild(new Label(descripcion_destino_salida));
		row.appendChild(new Label(descripcion_via_ingreso));
	}

}
