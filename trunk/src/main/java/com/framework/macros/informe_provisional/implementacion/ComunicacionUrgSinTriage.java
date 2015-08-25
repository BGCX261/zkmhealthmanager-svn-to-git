package com.framework.macros.informe_provisional.implementacion;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.zkoss.zul.Columns;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

public class ComunicacionUrgSinTriage extends ComunicadorAbstract {

	protected SimpleDateFormat dateFormat_completa = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	protected void montarCabecera(Columns columns) {
		columns.appendChild(getColumn("Tipo Ient", "120px"));
		columns.appendChild(getColumn("Documento", "120px"));
		columns.appendChild(getColumn("Primer apellido", "170px"));
		columns.appendChild(getColumn("Segundo apellido", "170px"));
		columns.appendChild(getColumn("Primer nombre", "170px"));
		columns.appendChild(getColumn("Segundo nombre", "170px"));
		columns.appendChild(getColumn("Fecha nacimiento", "120px"));
		columns.appendChild(getColumn("Sexo", "100px"));
		columns.appendChild(getColumn("Tipo Factura", "100px"));

		columns.appendChild(getColumn("Nro ingreso", "170px"));
		columns.appendChild(getColumn("Id plan", "120px"));
		columns.appendChild(getColumn("Fecha ingreso", "170px"));
		columns.appendChild(getColumn("codigo Médico triage", "120px"));
		columns.appendChild(getColumn("Apellido médico triage", "170px"));
		columns.appendChild(getColumn("Nómbre médico triage", "170px"));
		columns.appendChild(getColumn("Fecha atencion", "120px"));
		columns.appendChild(getColumn("codigo médico", "120px"));
		columns.appendChild(getColumn("Apellido médico", "170px"));
		columns.appendChild(getColumn("Nombre médico", "170px"));
		columns.appendChild(getColumn("Cie principal", "120px"));
		columns.appendChild(getColumn("Cie relacionado 1", "120px"));
		columns.appendChild(getColumn("Cie relacionado 2", "120px"));
		columns.appendChild(getColumn("Cie relacionado 3", "120px"));
	}

	@Override
	protected String getIdXml() {
		return "getUrgSinTriage";
	}

	@Override
	protected void crearFilas(Object dato, Row row) {
		Map<String, Object> map_resultado = (Map<String, Object>) dato;

		String tipo_identificacion = (String) map_resultado
				.get("tipo_identificacion");
		String documento = (String) map_resultado.get("documento");
		String apellido1 = (String) map_resultado.get("apellido1");
		String apellido2 = (String) map_resultado.get("apellido2");
		String nombre1 = (String) map_resultado.get("nombre1");
		String nombre2 = (String) map_resultado.get("nombre2");
		Date fecha_nacimiento = (Date) map_resultado.get("fecha_nacimiento");
		String sexo = (String) map_resultado.get("sexo");
		String nro_ingreso = (String) map_resultado.get("nro_ingreso");
		String id_plan = (String) map_resultado.get("id_plan");
		Timestamp fecha_ingreso = (Timestamp) map_resultado
				.get("fecha_ingreso");
//		String nivel_triage = (String) map_resultado.get("nivel_triage");
//		Timestamp fecha_triage = (Timestamp) map_resultado.get("fecha_triage");
		String codigo_medico_triage = (String) map_resultado
				.get("codigo_medico_triage");
		String apellido_medico_triage = (String) map_resultado
				.get("apellido_medico_triage");
		String nombre_medico_triage = (String) map_resultado
				.get("nombre_medico_triage");
		Timestamp fecha_atencion = (Timestamp) map_resultado
				.get("fecha_atencion");
		String codigo_medico = (String) map_resultado.get("codigo_medico");
		String apellido_medico = (String) map_resultado.get("apellido_medico");
		String nombre_medico = (String) map_resultado.get("nombre_medico");
		String cie_principal = (String) map_resultado.get("cie_principal");
		String cie_relacionado1 = (String) map_resultado
				.get("cie_relacionado1");
		String cie_relacionado2 = (String) map_resultado
				.get("cie_relacionado2");
		String cie_relacionado3 = (String) map_resultado
				.get("cie_relacionado3");
		String tipo_factura = (String) map_resultado.get("tipo_factura");

		row.appendChild(new Label(tipo_identificacion));
		row.appendChild(new Label(documento));
		row.appendChild(new Label(apellido1));
		row.appendChild(new Label(apellido2));
		row.appendChild(new Label(nombre1));
		row.appendChild(new Label(nombre2));
		row.appendChild(new Label(dateFormat.format(fecha_nacimiento)));
		row.appendChild(new Label(sexo));
		row.appendChild(new Label(getNombreTipo(tipo_factura)));
		row.appendChild(new Label(nro_ingreso));
		row.appendChild(new Label(id_plan));
		row.appendChild(new Label(dateFormat_completa.format(fecha_ingreso)));
//		row.appendChild(new Label(nivel_triage));
//		row.appendChild(new Label(dateFormat_completa.format(fecha_triage)));
		row.appendChild(new Label(codigo_medico_triage));
		row.appendChild(new Label(apellido_medico_triage));
		row.appendChild(new Label(nombre_medico_triage));
		row.appendChild(new Label(fecha_atencion != null ? dateFormat_completa
				.format(fecha_atencion) : ""));
		row.appendChild(new Label(codigo_medico));
		row.appendChild(new Label(apellido_medico));
		row.appendChild(new Label(nombre_medico));
		row.appendChild(new Label(cie_principal));
		row.appendChild(new Label(cie_relacionado1));
		row.appendChild(new Label(cie_relacionado2));
		row.appendChild(new Label(cie_relacionado3));
	}

}
