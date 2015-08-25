/*
 * ficha_epidemiologia_n41Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Ficha_epidemiologia_historial;
import healthmanager.modelo.bean.Ficha_epidemiologia_n41;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Ficha_epidemiologia_n41Action extends
		FichaEpidemiologiaModel<Ficha_epidemiologia_n41> {

//	private static Logger log = Logger
//			.getLogger(Ficha_epidemiologia_n41Action.class);

	// Componentes //
	@View
	private Textbox tbxNombres_y_apellidos;
	@View
	private Textbox tbxTipo_identidad_p;
	@View
	private Textbox tbxIdentificacion_p;
	@View private Textbox tbxAseguradora;
	@View private Textbox tbxNombre_aseguradora;
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;
	@View
	private Datebox dtbxFecha_inicial;
	@View
	private Textbox tbxCodigo_ficha;
	@View
	private Radiogroup rdbSexual;
	@View
	private Radiogroup rdbPerinatal;
	@View
	private Radiogroup rdbParental;
	@View
	private Radiogroup rdbTipo_prueba;
	@View
	private Datebox dtbxFecha_resultado;
	@View
	private Textbox tbxValor_carga_viral;
	@View
	private Radiogroup rdbEstado_clinico;
	@View
	private Intbox ibxNum_hijos_men18_hombre;
	@View
	private Intbox ibxNum_hijos_men18_mujer;
	@View
	private Radiogroup rdbEmbarazo;
	@View
	private Intbox ibxNum_semana_diagnostico;
	@View
	private Checkbox chbCandidiasis_esofagica;
	@View
	private Checkbox chbCandidiasis_vias_aere;
	@View
	private Checkbox chbTuberculosis_pulm;
	@View
	private Checkbox chbCancer_cerv;
	@View
	private Checkbox chbTuberculosis_extrapul;
	@View
	private Checkbox chbCoccidiosis;
	@View
	private Checkbox chbCitomegalovirosis;
	@View
	private Checkbox chbRetinitis_citomegalovirosis;
	@View
	private Checkbox chbEncefalopatia_voi;
	@View
	private Checkbox chbOtra_micro_vacteria;
	@View
	private Textbox tbxOtra_micro_vacteria2;
	@View
	private Checkbox chbHistoplasmosis_extrap;
	@View
	private Checkbox chbIsosporidiasis_cron;
	@View
	private Checkbox chbHerpes_zoster;
	@View
	private Checkbox chbHistoplasmosis_diseminada;
	@View
	private Checkbox chbLimfoma_burki;
	@View
	private Checkbox chbNeumonia_pneumosis;
	@View
	private Checkbox chbNeumonia_recurrente;
	@View
	private Checkbox chbLinfoma_inmunoblastico;
	@View
	private Checkbox chbCriptosporidiasis_cro;
	@View
	private Checkbox chbCriptosporidiasis_extrap;
	@View
	private Checkbox chbSarcoma_caposi;
	@View
	private Checkbox chbSindrome_emaciacion;
	@View
	private Checkbox chbLeucoencefalopatia_multifocal;
	@View
	private Checkbox chbSepticemia_recurrente;
	@View
	private Checkbox chbToxoplasmosis_cerebral;
	@View
	private Checkbox chbHepatitis_b;
	@View
	private Checkbox chbHepatitis_c;
	@View
	private Checkbox chbTuberculosis_meningea;
	@View
	private Checkbox chbMeningitis;
	private final String[] idsExcluyentes = {};
	private Admision admision;

	private Ficha_epidemiologia_historial ficha_seleccionada;
	
	public void ocultarCampo(Radiogroup rgbx, Intbox tx) {
		if (rgbx.getSelectedItem().getValue().equals("S")) {
			tx.setReadonly(false);
		}else {
			tx.setReadonly(true);
			tx.setText("");
		}

	}

	@Override
	public void init() throws Exception {
		listarCombos();
		obtenerAdmision(admision);

		//log.info("ficha-------> "+ficha_seleccionada);
		
		if(ficha_seleccionada != null){
			//log.info("consultar ficha-------> "+ficha_seleccionada);
			Ficha_epidemiologia_n41 ficha = new Ficha_epidemiologia_n41();
			ficha.setCodigo_empresa(ficha_seleccionada.getCodigo_empresa());
			ficha.setCodigo_sucursal(ficha_seleccionada.getCodigo_sucursal());
			ficha.setCodigo_ficha(ficha_seleccionada.getConsecutivo());
			ficha = (Ficha_epidemiologia_n41) getServiceLocator().getFicha_epidemiologia_nnService().consultar(ficha);
			
			//log.info("consultar ficha 41-------> "+ficha);
			
			mostrarDatos(ficha);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		if (map.containsKey(IVias_ingreso.ADMISION_PACIENTE)) {
			admision = (Admision) map.get(IVias_ingreso.ADMISION_PACIENTE);
			ficha_seleccionada = (Ficha_epidemiologia_historial) map.get("ficha_seleccionada");
			//log.info("ficha_seleccionada"+ficha_seleccionada);
			
		}
	}

	public void listarCombos() {
		listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_ficha");
		listitem.setLabel("Codigo_ficha");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	public void marcarcheck(Checkbox chek, Textbox texb) {
		if (chek.isChecked()) {
			texb.setReadonly(false);
		}
		if (!chek.isChecked()) {
			texb.setReadonly(true);
			texb.setText(" ");
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarFichaEpidemiologia() {

		boolean valida = true;

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getFicha_epidemiologia_nnService().setLimit(
					"limit 25 offset 0");

			List<Ficha_epidemiologia_n41> lista_datos = getServiceLocator()
					.getFicha_epidemiologia_nnService().listar(Ficha_epidemiologia_n41.class, parameters);
			rowsResultado.getChildren().clear();

			for (Ficha_epidemiologia_n41 ficha_epidemiologia_n41 : lista_datos) {
				rowsResultado.appendChild(crearFilas(ficha_epidemiologia_n41,
						this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Ficha_epidemiologia_n41 ficha_epidemiologia_n41 = (Ficha_epidemiologia_n41) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(ficha_epidemiologia_n41);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(ficha_epidemiologia_n41);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public Ficha_epidemiologia_n41 obtenerFichaEpidemiologia() {
		// Cargamos los componentes //

		Ficha_epidemiologia_n41 ficha_epidemiologia_n41 = new Ficha_epidemiologia_n41();
		ficha_epidemiologia_n41.setFecha_inicial(new Timestamp(
				dtbxFecha_inicial.getValue().getTime()));
		ficha_epidemiologia_n41.setCodigo_empresa(empresa.getCodigo_empresa());
		ficha_epidemiologia_n41.setCodigo_sucursal(sucursal
				.getCodigo_sucursal());
		ficha_epidemiologia_n41.setCodigo_ficha(tbxCodigo_ficha.getValue());
		ficha_epidemiologia_n41.setIdentificacion(tbxIdentificacion_p.getValue());
		ficha_epidemiologia_n41.setCodigo_diagnostico("A014");
		ficha_epidemiologia_n41.setPrimer_nombre_paciente("");
		ficha_epidemiologia_n41.setSegundo_nombre_paciente("");
		ficha_epidemiologia_n41.setPrimer_apellido_paciente("");
		ficha_epidemiologia_n41.setSegundo_apellido_paciente("");
		ficha_epidemiologia_n41.setTipo_identidad("");
		ficha_epidemiologia_n41.setNumero_identidad("");
		ficha_epidemiologia_n41.setSexual(rdbSexual.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n41.setPerinatal(rdbPerinatal.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n41.setParental(rdbParental.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n41.setTipo_prueba(rdbTipo_prueba.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n41.setFecha_resultado(new Timestamp(
				dtbxFecha_resultado.getValue().getTime()));
		ficha_epidemiologia_n41.setValor_carga_viral(tbxValor_carga_viral
				.getValue());
		ficha_epidemiologia_n41.setEstado_clinico(rdbEstado_clinico
				.getSelectedItem().getValue().toString());
		ficha_epidemiologia_n41
				.setNum_hijos_men18_hombre((ibxNum_hijos_men18_hombre
						.getValue() != null ? ibxNum_hijos_men18_hombre
						.getValue().toString() : "0"));
		ficha_epidemiologia_n41
				.setNum_hijos_men18_mujer((ibxNum_hijos_men18_mujer.getValue() != null ? ibxNum_hijos_men18_mujer
						.getValue().toString() : "0"));
		ficha_epidemiologia_n41.setEmbarazo(rdbEmbarazo.getSelectedItem()
				.getValue().toString());
		ficha_epidemiologia_n41
				.setNum_semana_diagnostico((ibxNum_semana_diagnostico
						.getValue() != null ? ibxNum_semana_diagnostico
						.getValue().toString() : "0"));
		ficha_epidemiologia_n41
				.setCandidiasis_esofagica(chbCandidiasis_esofagica.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n41
				.setCandidiasis_vias_aere(chbCandidiasis_vias_aere.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n41.setTuberculosis_pulm(chbTuberculosis_pulm
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41.setCancer_cerv(chbCancer_cerv.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n41
				.setTuberculosis_extrapul(chbTuberculosis_extrapul.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n41.setCoccidiosis(chbCoccidiosis.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n41.setCitomegalovirosis(chbCitomegalovirosis
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41
				.setRetinitis_citomegalovirosis(chbRetinitis_citomegalovirosis
						.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41.setEncefalopatia_voi(chbEncefalopatia_voi
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41.setOtra_micro_vacteria(chbOtra_micro_vacteria
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41.setOtra_micro_vacteria2(tbxOtra_micro_vacteria2
				.getValue());
		ficha_epidemiologia_n41
				.setHistoplasmosis_extrap(chbHistoplasmosis_extrap.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n41.setIsosporidiasis_cron(chbIsosporidiasis_cron
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41
				.setHerpes_zoster(chbHerpes_zoster.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41
				.setHistoplasmosis_diseminada(chbHistoplasmosis_diseminada
						.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41
				.setLimfoma_burki(chbLimfoma_burki.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41.setNeumonia_pneumosis(chbNeumonia_pneumosis
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41.setNeumonia_recurrente(chbNeumonia_recurrente
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41
				.setLinfoma_inmunoblastico(chbLinfoma_inmunoblastico
						.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41
				.setCriptosporidiasis_cro(chbCriptosporidiasis_cro.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n41
				.setCriptosporidiasis_extrap(chbCriptosporidiasis_extrap
						.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41
				.setSarcoma_caposi(chbSarcoma_caposi.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41.setSindrome_emaciacion(chbSindrome_emaciacion
				.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41
				.setLeucoencefalopatia_multifocal(chbLeucoencefalopatia_multifocal
						.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41
				.setSepticemia_recurrente(chbSepticemia_recurrente.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n41
				.setToxoplasmosis_cerebral(chbToxoplasmosis_cerebral
						.isChecked() ? "S" : "N");
		ficha_epidemiologia_n41.setHepatitis_b(chbHepatitis_b.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n41.setHepatitis_c(chbHepatitis_c.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n41
				.setTuberculosis_meningea(chbTuberculosis_meningea.isChecked() ? "S"
						: "N");
		ficha_epidemiologia_n41.setMeningitis(chbMeningitis.isChecked() ? "S"
				: "N");
		ficha_epidemiologia_n41.setCreacion_date(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n41.setUltimo_update(new Timestamp(
				new GregorianCalendar().getTimeInMillis()));
		ficha_epidemiologia_n41.setCreacion_user(usuarios.getCodigo()
				.toString());
		ficha_epidemiologia_n41.setDelete_date(null);
		ficha_epidemiologia_n41.setUltimo_user(usuarios.getCodigo().toString());
		ficha_epidemiologia_n41.setDelete_user(null);

		return ficha_epidemiologia_n41;

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Ficha_epidemiologia_n41 obj) throws Exception {
		Ficha_epidemiologia_n41 ficha_epidemiologia_n41 = (Ficha_epidemiologia_n41) obj;
		try {
			dtbxFecha_inicial.setValue(ficha_epidemiologia_n41
					.getFecha_inicial());
			tbxCodigo_ficha.setValue(ficha_epidemiologia_n41.getCodigo_ficha());

			FormularioUtil.deshabilitarComponentes(groupboxEditar, true,new String[] { });
			
			Utilidades.seleccionarRadio(rdbSexual,
					ficha_epidemiologia_n41.getSexual());
			Utilidades.seleccionarRadio(rdbPerinatal,
					ficha_epidemiologia_n41.getPerinatal());
			Utilidades.seleccionarRadio(rdbParental,
					ficha_epidemiologia_n41.getParental());
			Utilidades.seleccionarRadio(rdbTipo_prueba,
					ficha_epidemiologia_n41.getTipo_prueba());
			dtbxFecha_resultado.setValue(ficha_epidemiologia_n41
					.getFecha_resultado());
			tbxValor_carga_viral.setValue(ficha_epidemiologia_n41
					.getValor_carga_viral());
			Utilidades.seleccionarRadio(rdbEstado_clinico,
					ficha_epidemiologia_n41.getEstado_clinico());
			ibxNum_hijos_men18_hombre.setValue(Integer
					.valueOf(ficha_epidemiologia_n41
							.getNum_hijos_men18_hombre()));
			ibxNum_hijos_men18_mujer
					.setValue(Integer.valueOf(ficha_epidemiologia_n41
							.getNum_hijos_men18_mujer()));
			Utilidades.seleccionarRadio(rdbEmbarazo,
					ficha_epidemiologia_n41.getEmbarazo());
			ibxNum_semana_diagnostico.setValue(Integer
					.valueOf(ficha_epidemiologia_n41
							.getNum_semana_diagnostico()));
			chbCandidiasis_esofagica.setChecked(ficha_epidemiologia_n41
					.getCandidiasis_esofagica().equals("S") ? true : false);
			chbCandidiasis_vias_aere.setChecked(ficha_epidemiologia_n41
					.getCandidiasis_vias_aere().equals("S") ? true : false);
			chbTuberculosis_pulm.setChecked(ficha_epidemiologia_n41
					.getTuberculosis_pulm().equals("S") ? true : false);
			chbCancer_cerv.setChecked(ficha_epidemiologia_n41.getCancer_cerv()
					.equals("S") ? true : false);
			chbTuberculosis_extrapul.setChecked(ficha_epidemiologia_n41
					.getTuberculosis_extrapul().equals("S") ? true : false);
			chbCoccidiosis.setChecked(ficha_epidemiologia_n41.getCoccidiosis()
					.equals("S") ? true : false);
			chbCitomegalovirosis.setChecked(ficha_epidemiologia_n41
					.getCitomegalovirosis().equals("S") ? true : false);
			chbRetinitis_citomegalovirosis.setChecked(ficha_epidemiologia_n41
					.getRetinitis_citomegalovirosis().equals("S") ? true
					: false);
			chbEncefalopatia_voi.setChecked(ficha_epidemiologia_n41
					.getEncefalopatia_voi().equals("S") ? true : false);
			chbOtra_micro_vacteria.setChecked(ficha_epidemiologia_n41
					.getOtra_micro_vacteria().equals("S") ? true : false);
			tbxOtra_micro_vacteria2.setValue(ficha_epidemiologia_n41
					.getOtra_micro_vacteria2());
			chbHistoplasmosis_extrap.setChecked(ficha_epidemiologia_n41
					.getHistoplasmosis_extrap().equals("S") ? true : false);
			chbIsosporidiasis_cron.setChecked(ficha_epidemiologia_n41
					.getIsosporidiasis_cron().equals("S") ? true : false);
			chbHerpes_zoster.setChecked(ficha_epidemiologia_n41
					.getHerpes_zoster().equals("S") ? true : false);
			chbHistoplasmosis_diseminada.setChecked(ficha_epidemiologia_n41
					.getHistoplasmosis_diseminada().equals("S") ? true : false);
			chbLimfoma_burki.setChecked(ficha_epidemiologia_n41
					.getLimfoma_burki().equals("S") ? true : false);
			chbNeumonia_pneumosis.setChecked(ficha_epidemiologia_n41
					.getNeumonia_pneumosis().equals("S") ? true : false);
			chbNeumonia_recurrente.setChecked(ficha_epidemiologia_n41
					.getNeumonia_recurrente().equals("S") ? true : false);
			chbLinfoma_inmunoblastico.setChecked(ficha_epidemiologia_n41
					.getLinfoma_inmunoblastico().equals("S") ? true : false);
			chbCriptosporidiasis_cro.setChecked(ficha_epidemiologia_n41
					.getCriptosporidiasis_cro().equals("S") ? true : false);
			chbCriptosporidiasis_extrap.setChecked(ficha_epidemiologia_n41
					.getCriptosporidiasis_extrap().equals("S") ? true : false);
			chbSarcoma_caposi.setChecked(ficha_epidemiologia_n41
					.getSarcoma_caposi().equals("S") ? true : false);
			chbSindrome_emaciacion.setChecked(ficha_epidemiologia_n41
					.getSindrome_emaciacion().equals("S") ? true : false);
			chbLeucoencefalopatia_multifocal.setChecked(ficha_epidemiologia_n41
					.getLeucoencefalopatia_multifocal().equals("S") ? true
					: false);
			chbSepticemia_recurrente.setChecked(ficha_epidemiologia_n41
					.getSepticemia_recurrente().equals("S") ? true : false);
			chbToxoplasmosis_cerebral.setChecked(ficha_epidemiologia_n41
					.getToxoplasmosis_cerebral().equals("S") ? true : false);
			chbHepatitis_b.setChecked(ficha_epidemiologia_n41.getHepatitis_b()
					.equals("S") ? true : false);
			chbHepatitis_c.setChecked(ficha_epidemiologia_n41.getHepatitis_c()
					.equals("S") ? true : false);
			chbTuberculosis_meningea.setChecked(ficha_epidemiologia_n41
					.getTuberculosis_meningea().equals("S") ? true : false);
			chbMeningitis.setChecked(ficha_epidemiologia_n41.getMeningitis()
					.equals("S") ? true : false);

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Ficha_epidemiologia_n41 ficha_epidemiologia_n41 = (Ficha_epidemiologia_n41) obj;
		try {
			int result = getServiceLocator()
					.getFicha_epidemiologia_nnService().eliminar(
							ficha_epidemiologia_n41);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	public void obtenerAdmision(Admision admision) {
		Paciente paciente = admision.getPaciente();
		tbxIdentificacion_p.setValue(admision.getNro_identificacion());
		tbxNombres_y_apellidos.setValue(paciente.getNombreCompleto());
		tbxTipo_identidad_p.setValue(paciente.getTipo_identificacion());
		
		tbxAseguradora.setValue(admision.getCodigo_administradora());
		//log.info("PACIENTE"+paciente);
		//log.info("ADMINISTRADORA"+admision.getCodigo_empresa()+" "+admision.getCodigo_sucursal()+" "+paciente.getCodigo_administradora());
		
		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(admision.getCodigo_empresa());
		administradora.setCodigo_sucursal(admision.getCodigo_sucursal());
		administradora.setCodigo(admision.getCodigo_administradora());
		administradora = getServiceLocator().getAdministradoraService().consultar(administradora);
		//log.info("administradora"+administradora);
		
		tbxNombre_aseguradora.setValue(administradora.getNombre());
	}

	
	@Override
	public Ficha_epidemiologia_n41 consultarDatos(Map<String, Object> map,
			Ficha_epidemiologia ficha_epidemiologia) throws Exception {
//				Ficha_epidemiologia ficha = (Ficha_epidemiologia)ficha_epidemiologia;
				
				//log.info("-----------------");
				
				//log.info("map"+map);
				//log.info("ficha"+ficha);
				
				Impresion_diagnostica impresion_diagnostica = (Impresion_diagnostica) map.get("impresion_diagnostica");
				Cie_epidemiologia cie_epidemiologia = (Cie_epidemiologia) map.get("cie_epidemiologia");
				Admision admision = (Admision) map.get("admision");
				
				Map<String,Object> parameters = new HashMap<String,Object>();
				parameters.put("codigo_empresa", admision.getCodigo_empresa());
				parameters.put("codigo_sucursal", admision.getCodigo_sucursal());
				parameters.put("identificacion", admision.getNro_identificacion());
				
				if(impresion_diagnostica != null){
				parameters.put("codigo_historia", impresion_diagnostica.getCodigo_historia());
				}else{
					return null;
				}
				
				if(cie_epidemiologia != null){
					parameters.put("codigo_diagnostico", cie_epidemiologia.getCodigo_cie());			
				}else{
					return null;
				}
				
				getServiceLocator().getFicha_epidemiologia_nnService().setLimit("limit 25 offset 0");
				
				List<Ficha_epidemiologia_n41> lista_datos = getServiceLocator().getFicha_epidemiologia_nnService().listar(
						Ficha_epidemiologia_n41.class, parameters);
				
				//log.info("lista_datos"+lista_datos);
				
				if (!lista_datos.isEmpty()){
					Ficha_epidemiologia_n41 ficha_n41 = lista_datos.get(lista_datos.size() -1);
					
					return ficha_n41;
				}else{

					return null;
				}
	}

}