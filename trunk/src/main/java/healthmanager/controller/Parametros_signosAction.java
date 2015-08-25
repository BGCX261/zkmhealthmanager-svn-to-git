/*
 * parametros_signosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Parametros_signos;
import healthmanager.modelo.service.Parametros_signosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Parametros_signosAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(Parametros_signosAction.class);

	private Parametros_signosService parametros_signosService;

	@View
	private Rows rowsParametros;

	@Override
	public void init() {
		listarCombos();
		mostrarDatos();
	}

	public void listarCombos() {
		listarParameter();
	}

	private void inicializarParametros() {
		//log.info("ejecutando metodo @inicializarParametros()");
		rowsParametros.getChildren().clear();
		rowsParametros.appendChild(crearFilaParametro(1, "RECIEN NACIDO", "M"));
		rowsParametros.appendChild(crearFilaParametro(2, "RECIEN NACIDO", "F"));
		rowsParametros
				.appendChild(crearFilaParametro(3, "LACTANTE MENOR", "M"));
		rowsParametros
				.appendChild(crearFilaParametro(4, "LACTANTE MENOR", "F"));
		rowsParametros
				.appendChild(crearFilaParametro(5, "LACTANTE MAYOR", "M"));
		rowsParametros
				.appendChild(crearFilaParametro(6, "LACTANTE MAYOR", "F"));
		rowsParametros.appendChild(crearFilaParametro(7, "PREESCOLAR", "M"));
		rowsParametros.appendChild(crearFilaParametro(8, "PREESCOLAR", "F"));
		rowsParametros.appendChild(crearFilaParametro(9, "ESCOLAR", "M"));
		rowsParametros.appendChild(crearFilaParametro(10, "ESCOLAR", "F"));
		rowsParametros.appendChild(crearFilaParametro(11, "ADOLESCENTE", "M"));
		rowsParametros.appendChild(crearFilaParametro(12, "ADOLESCENTE", "F"));
		rowsParametros.appendChild(crearFilaParametro(13, "ADULTO", "M"));
		rowsParametros.appendChild(crearFilaParametro(14, "ADULTO", "F"));
		rowsParametros.appendChild(crearFilaParametro(15, "ADULTO MAYOR", "M"));
		rowsParametros.appendChild(crearFilaParametro(16, "ADULTO MAYOR", "F"));
	}

	private Row crearFilaParametro(int codigo, String nombre, String sexo) {
		Row fila = new Row();
		fila.setId("row_fila_parametros_" + codigo);
		fila.setValue(codigo);
		fila.appendChild(new Label(codigo + ""));
		Label label_nombre = new Label(nombre);
		label_nombre.setId("label_nombre_" + codigo);
		fila.appendChild(label_nombre);
		Label label_sexo = new Label(sexo);
		label_sexo.setId("label_sexo_" + codigo);
		fila.appendChild(label_sexo);
		Listbox listbox_medida = new Listbox();
		listbox_medida.setId("listbox_medida_" + codigo);
		listbox_medida.setMold("select");
		listbox_medida.setHflex("1");
		agregarItemsMedida(listbox_medida);
		fila.appendChild(listbox_medida);
		Intbox intbox_edad_inf = new Intbox();
		intbox_edad_inf.setId("intbox_edad_inf_" + codigo);
		fila.appendChild(intbox_edad_inf);
		Intbox intbox_edad_sup = new Intbox();
		intbox_edad_sup.setId("intbox_edad_sup_" + codigo);
		fila.appendChild(intbox_edad_sup);
		Doublebox doublebox_frecuencia_cardiaca_inf = new Doublebox();
		doublebox_frecuencia_cardiaca_inf
				.setId("doublebox_frecuencia_cardiaca_inf_" + codigo);
		fila.appendChild(doublebox_frecuencia_cardiaca_inf);
		Doublebox doublebox_frecuencia_cardiaca_sup = new Doublebox();
		doublebox_frecuencia_cardiaca_sup
				.setId("doublebox_frecuencia_cardiaca_sup_" + codigo);
		fila.appendChild(doublebox_frecuencia_cardiaca_sup);
		Doublebox doublebox_frecuencia_respiratoria_inf = new Doublebox();
		doublebox_frecuencia_respiratoria_inf
				.setId("doublebox_frecuencia_respiratoria_inf_" + codigo);
		fila.appendChild(doublebox_frecuencia_respiratoria_inf);
		Doublebox doublebox_frecuencia_respiratoria_sup = new Doublebox();
		doublebox_frecuencia_respiratoria_sup
				.setId("doublebox_frecuencia_respiratoria_sup_" + codigo);
		fila.appendChild(doublebox_frecuencia_respiratoria_sup);
		Doublebox doublebox_temperatura_inf = new Doublebox();
		doublebox_temperatura_inf.setId("doublebox_temperatura_inf_" + codigo);
		fila.appendChild(doublebox_temperatura_inf);
		Doublebox doublebox_temperatura_sup = new Doublebox();
		doublebox_temperatura_sup.setId("doublebox_temperatura_sup_" + codigo);
		fila.appendChild(doublebox_temperatura_sup);
		Doublebox doublebox_ta_sistolica_inf = new Doublebox();
		doublebox_ta_sistolica_inf
				.setId("doublebox_ta_sistolica_inf_" + codigo);
		fila.appendChild(doublebox_ta_sistolica_inf);
		Doublebox doublebox_ta_sistolica_sup = new Doublebox();
		doublebox_ta_sistolica_sup
				.setId("doublebox_ta_sistolica_sup_" + codigo);
		fila.appendChild(doublebox_ta_sistolica_sup);
		Doublebox doublebox_ta_diastolica_inf = new Doublebox();
		doublebox_ta_diastolica_inf.setId("doublebox_ta_diastolica_inf_"
				+ codigo);
		fila.appendChild(doublebox_ta_diastolica_inf);
		Doublebox doublebox_ta_diastolica_sup = new Doublebox();
		doublebox_ta_diastolica_sup.setId("doublebox_ta_diastolica_sup_"
				+ codigo);
		fila.appendChild(doublebox_ta_diastolica_sup);
		Doublebox doublebox_creatinina_serica_inf = new Doublebox();
		doublebox_creatinina_serica_inf
				.setId("doublebox_creatinina_serica_inf_" + codigo);
		fila.appendChild(doublebox_creatinina_serica_inf);
		Doublebox doublebox_creatinina_serica_sup = new Doublebox();
		doublebox_creatinina_serica_sup
				.setId("doublebox_creatinina_serica_sup_" + codigo);
		fila.appendChild(doublebox_creatinina_serica_sup);

		return fila;
	}

	public boolean validarParametros() {
		boolean valida = true;
		String mensaje = "";
		List<Component> listado_filas = rowsParametros.getChildren();
		for (Component componente : listado_filas) {
			Row fila = (Row) componente;
			int codigo = (Integer) fila.getValue();
			Integer edad_inf = ((Intbox) this.getFellow("intbox_edad_inf_"
					+ codigo)).getValue() != null ? ((Intbox) this
					.getFellow("intbox_edad_inf_" + codigo)).getValue() : 0;
			Integer edad_sup = ((Intbox) this.getFellow("intbox_edad_sup_"
					+ codigo)).getValue() != null ? ((Intbox) this
					.getFellow("intbox_edad_sup_" + codigo)).getValue() : 0;
			if (edad_sup < edad_inf) {
				valida = false;
				mensaje = "La edad inferior no puede ser mayor que la edad superior en la fila "
						+ codigo;
				break;
			}

			Double frecuencia_cardiaca_inf = getValorCampo("doublebox_frecuencia_cardiaca_inf_"
					+ codigo);
			Double frecuencia_cardiaca_sup = getValorCampo("doublebox_frecuencia_cardiaca_sup_"
					+ codigo);

			if (frecuencia_cardiaca_sup < frecuencia_cardiaca_inf) {
				valida = false;
				mensaje = "La frecuencia cardiaca inferior no puede ser mayor que la frecuencia cardiaca superior en la fila "
						+ codigo;
				break;
			}

			Double frecuencia_respiratoria_inf = getValorCampo("doublebox_frecuencia_respiratoria_inf_"
					+ codigo);
			Double frecuencia_respiratoria_sup = getValorCampo("doublebox_frecuencia_respiratoria_sup_"
					+ codigo);

			if (frecuencia_respiratoria_sup < frecuencia_respiratoria_inf) {
				valida = false;
				mensaje = "La frecuencia respiratoria inferior no puede ser mayor que la frecuencia respiratoria superior en la fila "
						+ codigo;
				break;
			}

			Double temperatura_inf = getValorCampo("doublebox_temperatura_inf_"
					+ codigo);
			Double temperatura_sup = getValorCampo("doublebox_temperatura_sup_"
					+ codigo);

			if (temperatura_sup < temperatura_inf) {
				valida = false;
				mensaje = "La temperatura inferior no puede ser mayor que la temperatura superior en la fila "
						+ codigo;
				break;
			}

			Double ta_sistolica_inf = getValorCampo("doublebox_ta_sistolica_inf_"
					+ codigo);
			Double ta_sistolica_sup = getValorCampo("doublebox_ta_sistolica_sup_"
					+ codigo);

			if (ta_sistolica_sup < ta_sistolica_inf) {
				valida = false;
				mensaje = "La tension arterial sistolica inferior no puede ser mayor que la tension arterial sistolica superior en la fila "
						+ codigo;
				break;
			}

			Double ta_diastolica_inf = getValorCampo("doublebox_ta_diastolica_inf_"
					+ codigo);
			Double ta_diastolica_sup = getValorCampo("doublebox_ta_diastolica_sup_"
					+ codigo);

			if (ta_diastolica_sup < ta_diastolica_inf) {
				valida = false;
				mensaje = "La tension arterial diastolica inferior no puede ser mayor que la tension arterial diastolica superior en la fila "
						+ codigo;
				break;
			}

			Double creatinina_serica_inf = getValorCampo("doublebox_creatinina_serica_inf_"
					+ codigo);
			Double creatinina_serica_sup = getValorCampo("doublebox_creatinina_serica_sup_"
					+ codigo);

			if (creatinina_serica_sup < creatinina_serica_inf) {
				valida = false;
				mensaje = "La creatinina serica inferior no puede ser mayor que la creatinina serica superior en la fila "
						+ codigo;
				break;
			}

		}

		if (!valida) {
			MensajesUtil.mensajeAlerta("Errores validar los rangos", mensaje);
		}

		return valida;
	}

	private Double getValorCampo(String id) {
		return ((Doublebox) this.getFellow(id)).getValue() != null ? ((Doublebox) this
				.getFellow(id)).getValue() : 0.0;
	}

	private void setValorCampo(String id, Double valor) {
		((Doublebox) this.getFellow(id)).setValue(valor);
	}

	private void agregarItemsMedida(Listbox listbox_medida) {
		listbox_medida.getChildren().clear();
		listbox_medida.appendChild(new Listitem("Dias", "3"));
		listbox_medida.appendChild(new Listitem("Meses", "2"));
		listbox_medida.appendChild(new Listitem("años", "1"));
		listbox_medida.setSelectedIndex(2);
	}

	public void listarParameter() {

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {

	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			if (validarParametros()) {
				List<Parametros_signos> listado_parametros = new ArrayList<Parametros_signos>();

				List<Component> listado_filas = rowsParametros.getChildren();
				for (Component componente : listado_filas) {
					Row fila = (Row) componente;
					int codigo = (Integer) fila.getValue();

					Parametros_signos parametros_signos = new Parametros_signos();

					String nombre = ((Label) this.getFellow("label_nombre_"
							+ codigo)).getValue();
					String sexo = ((Label) this.getFellow("label_sexo_"
							+ codigo)).getValue();

					Listbox listbox_medida = (Listbox) this
							.getFellow("listbox_medida_" + codigo);

					Integer edad_inf = ((Intbox) this
							.getFellow("intbox_edad_inf_" + codigo)).getValue() != null ? ((Intbox) this
							.getFellow("intbox_edad_inf_" + codigo)).getValue()
							: 0;
					Integer edad_sup = ((Intbox) this
							.getFellow("intbox_edad_sup_" + codigo)).getValue() != null ? ((Intbox) this
							.getFellow("intbox_edad_sup_" + codigo)).getValue()
							: 0;

					Double frecuencia_cardiaca_inf = getValorCampo("doublebox_frecuencia_cardiaca_inf_"
							+ codigo);
					Double frecuencia_cardiaca_sup = getValorCampo("doublebox_frecuencia_cardiaca_sup_"
							+ codigo);

					Double frecuencia_respiratoria_inf = getValorCampo("doublebox_frecuencia_respiratoria_inf_"
							+ codigo);
					Double frecuencia_respiratoria_sup = getValorCampo("doublebox_frecuencia_respiratoria_sup_"
							+ codigo);

					Double temperatura_inf = getValorCampo("doublebox_temperatura_inf_"
							+ codigo);
					Double temperatura_sup = getValorCampo("doublebox_temperatura_sup_"
							+ codigo);

					Double ta_sistolica_inf = getValorCampo("doublebox_ta_sistolica_inf_"
							+ codigo);
					Double ta_sistolica_sup = getValorCampo("doublebox_ta_sistolica_sup_"
							+ codigo);

					Double ta_diastolica_inf = getValorCampo("doublebox_ta_diastolica_inf_"
							+ codigo);
					Double ta_diastolica_sup = getValorCampo("doublebox_ta_diastolica_sup_"
							+ codigo);

					Double creatinina_serica_inf = getValorCampo("doublebox_creatinina_serica_inf_"
							+ codigo);
					Double creatinina_serica_sup = getValorCampo("doublebox_creatinina_serica_sup_"
							+ codigo);

					parametros_signos.setCodigo(codigo);
					parametros_signos
							.setCreatinina_cerica_inf(creatinina_serica_inf);
					parametros_signos
							.setCreatinina_cerica_sup(creatinina_serica_sup);
					parametros_signos.setCodigo_empresa(codigo_empresa);
					parametros_signos.setEdad_inferior(edad_inf);
					parametros_signos.setEdad_superior(edad_sup);
					parametros_signos
							.setFrecuencia_cardiaca_inf(frecuencia_cardiaca_inf);
					parametros_signos
							.setFrecuencia_cardiaca_sup(frecuencia_cardiaca_sup);
					parametros_signos
							.setFrecuencia_respiratoria_inf(frecuencia_respiratoria_inf);
					parametros_signos
							.setFrecuencia_respiratoria_sup(frecuencia_respiratoria_sup);
					parametros_signos.setMedida_edad(listbox_medida
							.getSelectedItem().getValue().toString());
					parametros_signos.setNombre(nombre);
					parametros_signos.setSexo(sexo);
					parametros_signos.setTa_diastolica_inf(ta_diastolica_inf);
					parametros_signos.setTa_diastolica_sup(ta_diastolica_sup);
					parametros_signos.setTa_sistolica_inf(ta_sistolica_inf);
					parametros_signos.setTa_sistolica_sup(ta_sistolica_sup);
					parametros_signos.setTemperatura_inf(temperatura_inf);
					parametros_signos.setTemperatura_sup(temperatura_sup);

					listado_parametros.add(parametros_signos);

				}

				parametros_signosService.guardarDatos(listado_parametros);

				MensajesUtil.mensajeInformacion("Informacion ...",
						"Los datos se guardaron satisfactoriamente");
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos() {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			List<Parametros_signos> listado_parametros = parametros_signosService
					.listar(parametros);
			inicializarParametros();
			for (Parametros_signos parametros_signos : listado_parametros) {
				int codigo = parametros_signos.getCodigo();
				if (this.hasFellow("row_fila_parametros_" + codigo)) {
					Listbox listbox_medida = (Listbox) this
							.getFellow("listbox_medida_" + codigo);
					Utilidades.seleccionarListitem(listbox_medida,
							parametros_signos.getMedida_edad());
					Intbox intbox_edad_inf = (Intbox) this
							.getFellow("intbox_edad_inf_" + codigo);
					intbox_edad_inf.setValue(parametros_signos
							.getEdad_inferior());
					Intbox intbox_edad_sup = (Intbox) this
							.getFellow("intbox_edad_sup_" + codigo);
					intbox_edad_sup.setValue(parametros_signos
							.getEdad_superior());

					setValorCampo(
							"doublebox_frecuencia_cardiaca_inf_" + codigo,
							parametros_signos.getFrecuencia_cardiaca_inf());

					setValorCampo(
							"doublebox_frecuencia_cardiaca_sup_" + codigo,
							parametros_signos.getFrecuencia_cardiaca_sup());

					setValorCampo("doublebox_frecuencia_respiratoria_inf_"
							+ codigo,
							parametros_signos.getFrecuencia_respiratoria_inf());

					setValorCampo("doublebox_frecuencia_respiratoria_sup_"
							+ codigo,
							parametros_signos.getFrecuencia_respiratoria_sup());

					setValorCampo("doublebox_temperatura_inf_" + codigo,
							parametros_signos.getTemperatura_inf());

					setValorCampo("doublebox_temperatura_sup_" + codigo,
							parametros_signos.getTemperatura_sup());

					setValorCampo("doublebox_ta_sistolica_inf_" + codigo,
							parametros_signos.getTa_sistolica_inf());

					setValorCampo("doublebox_ta_sistolica_sup_" + codigo,
							parametros_signos.getTa_sistolica_sup());

					setValorCampo("doublebox_ta_diastolica_inf_" + codigo,
							parametros_signos.getTa_diastolica_inf());

					setValorCampo("doublebox_ta_diastolica_sup_" + codigo,
							parametros_signos.getTa_diastolica_sup());

					setValorCampo("doublebox_creatinina_serica_inf_" + codigo,
							parametros_signos.getCreatinina_cerica_inf());

					setValorCampo("doublebox_creatinina_serica_sup_" + codigo,
							parametros_signos.getCreatinina_cerica_sup());
				}
			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

}