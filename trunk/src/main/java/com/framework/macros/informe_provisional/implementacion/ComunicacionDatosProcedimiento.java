package com.framework.macros.informe_provisional.implementacion;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Map;

import org.zkoss.zul.Columns;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

public class ComunicacionDatosProcedimiento extends ComunicadorAbstract {

	@Override
	protected String getIdXml() {
		return "getDatosProcedimiento";
	}

	@Override
	protected void montarCabecera(Columns columns) {
		columns.appendChild(getColumn("Documento", "100px"));
		columns.appendChild(getColumn("Tipo ident", "100px"));
		columns.appendChild(getColumn("Primer Apellido", "170px"));
		columns.appendChild(getColumn("Segundo Apellido", "170px"));
		columns.appendChild(getColumn("Primer Nombre", "170px"));
		columns.appendChild(getColumn("Segundo Nombre", "170px"));
		
		// contenido
		columns.appendChild(getColumn("Fecha nacimiento", "130px"));
		columns.appendChild(getColumn("Sexo", "70px"));
		columns.appendChild(getColumn("codigo centro", "100px"));
		columns.appendChild(getColumn("Nómbre centro", "170px"));
		columns.appendChild(getColumn("Tipo factura", "100px"));
		columns.appendChild(getColumn("Via de ingreso", "100px"));
		columns.appendChild(getColumn("Id plan", "100px"));
		columns.appendChild(getColumn("Fecha procedimiento", "100px"));
		columns.appendChild(getColumn("Nro autorizacion", "100px"));
//		columns.appendChild(getColumn("codigo prestador", "100px"));
		columns.appendChild(getColumn("codigo procedimiento", "100px"));
		columns.appendChild(getColumn("Descripcion procedimiento", "170px"));
		columns.appendChild(getColumn("Finalidad de procedimiento", "100px"));
		columns.appendChild(getColumn("Ambito procedimiento", "100px"));
//		columns.appendChild(getColumn("Tipo diagnostico", "100px"));
		columns.appendChild(getColumn("codigo dx principal", "100px"));
		columns.appendChild(getColumn("codigo dx rel", "100px"));
		columns.appendChild(getColumn("Valor procedimiento", "100px"));
		columns.appendChild(getColumn("Valor neto", "100px"));
		columns.appendChild(getColumn("Unidades", "100px"));
		columns.appendChild(getColumn("Facturacion", "100px"));
		columns.appendChild(getColumn("codigo administradora", "100px"));
		columns.appendChild(getColumn("Nómbre administradora", "170px"));
		columns.appendChild(getColumn("Tipo ident médico", "100px"));
		columns.appendChild(getColumn("Nro ident médico", "100px"));
		columns.appendChild(getColumn("Apellidos médico", "100px"));
		columns.appendChild(getColumn("Nombres médico", "100px"));
		columns.appendChild(getColumn("Usuario creador de admision", "170px"));
		columns.appendChild(getColumn("Usuario modificador de admision", "170px"));
		columns.appendChild(getColumn("Dosis", "100px"));
	}

	@Override
	protected void crearFilas(Object dato, Row row) {
		Map<String, Object> map_resultado = (Map<String, Object>) dato;
		String codigo_centro = (String) map_resultado.get("codigo_centro");
		String nombre_centro = (String) map_resultado.get("nombre_centro");
		String tipo_factura = (String) map_resultado.get("tipo_factura");
		String nombre_via_ingreso = (String) map_resultado
				.get("nombre_via_ingreso");
//		String via_ingreso = (String) map_resultado.get("via_ingreso");
//		String nro_identificacion = (String) map_resultado
//				.get("nro_identificacion");
		String id_plan = (String) map_resultado.get("id_plan");
		Date fecha_procedimiento = (Date) map_resultado.get("fecha_procedimiento");
		String numero_autorizacion = (String) map_resultado
				.get("numero_autorizacion");
//		String codigo_prestador = (String) map_resultado
//				.get("codigo_prestador");
//		String codigo_consulta = (String) map_resultado.get("codigo_consulta");
//		String finalidad_consulta = (String) map_resultado
//				.get("finalidad_consulta");
//		String causa_externa = (String) map_resultado.get("causa_externa");
//		String tipo_diagnostico = (String) map_resultado
//				.get("tipo_diagnostico");
		String codigo_diagnostico_principal = (String) map_resultado
				.get("codigo_diagnostico_principal");
		String codigo_diagnostico_relacionado = (String) map_resultado
				.get("codigo_diagnostico_relacionado");
		BigDecimal valor_procedimiento = (BigDecimal) map_resultado.get("valor_procedimiento");
		BigDecimal valor_neto = (BigDecimal) map_resultado.get("valor_neto");
		String facturacion = (String) map_resultado.get("facturacion");
		String codigo_administradora = (String) map_resultado
				.get("codigo_administradora");
		String nombre_administradora = (String) map_resultado.get("nombre_administradora");
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
		String codigo_medico = (String) map_resultado.get("codigo_medico");
		String apellido_medico = (String) map_resultado.get("apellido_medico");
		String nombre_medico = (String) map_resultado.get("nombre_medico");
		String codigo_usr_creacion = (String) map_resultado
				.get("codigo_usr_creacion");
		String apellidos_creacion = (String) map_resultado
				.get("apellidos_creacion");
		String nombre_creacion = (String) map_resultado.get("nombre_creacion");
		String codigo_modificacion = (String) map_resultado
				.get("codigo_modificacion");
		String apellido_mod = (String) map_resultado.get("apellido_mod");
		String nombre_mod = (String) map_resultado.get("nombre_mod");
		String nombre_procedimiento = (String) map_resultado.get("nombre_procedimiento");
		String codigo_cups = (String) map_resultado.get("codigo_cups"); 
		
		String descripcion_finalidad_procedimiento = (String) map_resultado.get("descripcion_finalidad_procedimiento");
		String descripcion_ambito_procedimiento = (String) map_resultado.get("descripcion_ambito_procedimiento");
		Integer unidades = (Integer) map_resultado.get("unidades");
		String descripcion_vacunacion = (String) map_resultado.get("descripcion_vacunacion");

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
		row.appendChild(new Label(dateFormat.format(fecha_procedimiento)));
		row.appendChild(new Label(numero_autorizacion));
//		row.appendChild(new Label(codigo_prestador));
		row.appendChild(new Label(codigo_cups));
		row.appendChild(new Label(nombre_procedimiento));
		row.appendChild(new Label(descripcion_finalidad_procedimiento));
		row.appendChild(new Label(descripcion_ambito_procedimiento));
		row.appendChild(new Label(codigo_diagnostico_principal));
		row.appendChild(new Label(codigo_diagnostico_relacionado));
		row.appendChild(new Label(decimalFormat.format(valor_procedimiento)));
		row.appendChild(new Label(decimalFormat.format(valor_neto)));
		row.appendChild(new Label(unidades + ""));
		row.appendChild(new Label(facturacion));
		row.appendChild(new Label(codigo_administradora));
		row.appendChild(new Label(nombre_administradora));
		
		row.appendChild(new Label("CC")); 
		row.appendChild(new Label(codigo_medico));
		row.appendChild(new Label(apellido_medico));
		row.appendChild(new Label(nombre_medico));
		
		row.appendChild(new Label("(" + codigo_usr_creacion + ") "
				+ nombre_creacion + " " + apellidos_creacion));
		row.appendChild(new Label("(" + codigo_modificacion + ") "
				+ apellido_mod + " " + nombre_mod));
		
		row.appendChild(new Label(descripcion_vacunacion));
	}

}
