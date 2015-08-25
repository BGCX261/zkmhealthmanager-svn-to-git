/*
 * remision_internaAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Remision_interna;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;

public class Remision_internaMacro extends Groupbox implements AfterCompose {

	// private static Logger log =
	// Logger.getLogger(Remision_internaAction.class);

	// private Remision_internaService remision_internaService;

	// Componentes //

	private Listbox lbxParameter;
	private Borderlayout groupboxEditar;

	private Checkbox chbCrecimiento_desarrollo;
	private Checkbox chbDet_alteracion_joven;
	private Checkbox chbDet_alteracion_adulto_mayor;
	private Checkbox chbControl_prenatal;
	private Checkbox chbUrgencia;
	private Checkbox chbDet_alteracion_agudeza_visual;
	private Checkbox chbProg_hipertencion_arterial;
	private Checkbox chbProg_planificacion_fami;
	private Checkbox chbPsicologia;
	private Checkbox chbNutricion;
	private Checkbox chbExamen_fisico;
	private Checkbox chbPrev_salud_bucal;
	private Checkbox chbVacunacion;
	private Checkbox chbCitologia_servicio;
	private Checkbox chbAtencion_recien_nacido;
	private Checkbox chbProg_diabetes;
	private Checkbox chbProg_tbc;
	private Checkbox chbProg_lepra;
	private Checkbox chbConsulta_externa;
	private Checkbox chbPsiquiatria;
	private Checkbox chbPsicofilaxis;
	private Checkbox chbGinecobstetrico;
	private Checkbox chbRadiografias;
	private Checkbox chbEndodoncia;
	private Textbox tbxObservacion;
	private final String[] idsExcluyentes = { "toolbarbuttonImprimirRI" };

	private ZKWindow zkWindow;
	private Admision admision;
	private String via_ingreso;

	private String codigo_medico;

	private Remision_interna remision_interna;

	private Toolbarbutton toolbarbuttonImprimirRI;

	public static Logger log = Logger.getLogger(Remision_internaMacro.class);

	public void inicializarMacro(ZKWindow zkWindow, Admision admision,
			String via_ingreso) {
		setZkWindow(zkWindow);
		setAdmision(admision);
		setVia_ingreso(via_ingreso);
		this.codigo_medico = admision.getCodigo_medico();
	}

	@Override
	public void afterCompose() {
		cargarComponentes();

	}

	public void onSelccionarConsultaExtera(boolean seleccionar) {
		chbConsulta_externa.setChecked(seleccionar);
	}

	private void cargarComponentes() {
		chbCrecimiento_desarrollo = (Checkbox) this
				.getFellow("chbCrecimiento_desarrollo");
		chbDet_alteracion_joven = (Checkbox) this
				.getFellow("chbDet_alteracion_joven");
		chbDet_alteracion_adulto_mayor = (Checkbox) this
				.getFellow("chbDet_alteracion_adulto_mayor");
		chbControl_prenatal = (Checkbox) this.getFellow("chbControl_prenatal");
		chbUrgencia = (Checkbox) this.getFellow("chbUrgencia");
		chbDet_alteracion_agudeza_visual = (Checkbox) this
				.getFellow("chbDet_alteracion_agudeza_visual");
		chbProg_hipertencion_arterial = (Checkbox) this
				.getFellow("chbProg_hipertencion_arterial");
		chbProg_planificacion_fami = (Checkbox) this
				.getFellow("chbProg_planificacion_fami");
		chbPsicologia = (Checkbox) this.getFellow("chbPsicologia");
		chbNutricion = (Checkbox) this.getFellow("chbNutricion");
		chbExamen_fisico = (Checkbox) this.getFellow("chbExamen_fisico");
		chbPrev_salud_bucal = (Checkbox) this.getFellow("chbPrev_salud_bucal");
		chbVacunacion = (Checkbox) this.getFellow("chbVacunacion");
		chbCitologia_servicio = (Checkbox) this
				.getFellow("chbCitologia_servicio");
		chbAtencion_recien_nacido = (Checkbox) this
				.getFellow("chbAtencion_recien_nacido");
		chbProg_diabetes = (Checkbox) this.getFellow("chbProg_diabetes");
		chbProg_tbc = (Checkbox) this.getFellow("chbProg_tbc");
		chbProg_lepra = (Checkbox) this.getFellow("chbProg_lepra");
		chbConsulta_externa = (Checkbox) this.getFellow("chbConsulta_externa");
		chbPsiquiatria = (Checkbox) this.getFellow("chbPsiquiatria");
		chbPsicofilaxis = (Checkbox) this.getFellow("chbPsicofilaxis");
		chbGinecobstetrico = (Checkbox) this.getFellow("chbGinecobstetrico");
		chbRadiografias = (Checkbox) this.getFellow("chbRadiografias");
		chbEndodoncia = (Checkbox) this.getFellow("chbEndodoncia");
		tbxObservacion = (Textbox) this.getFellow("tbxObservacion");
		toolbarbuttonImprimirRI = (Toolbarbutton) this
				.getFellow("toolbarbuttonImprimirRI");
	}

	public void iniciarMacroRemisionInterna(ZKWindow zkWindow, Paciente paciente) {
		this.zkWindow = zkWindow;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", zkWindow.codigo_empresa);
		parameters.put("codigo_sucursal", zkWindow.codigo_sucursal);

	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("codigo_paciente");
		listitem.setLabel("Codigo_paciente");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	public Remision_interna obtenerRemisionInterna() throws Exception {
		if (remision_interna == null)
			remision_interna = new Remision_interna();

		remision_interna.setCodigo_empresa(zkWindow.getEmpresa()
				.getCodigo_empresa());
		remision_interna.setCodigo_sucursal(zkWindow.getSucursal()
				.getCodigo_sucursal());
		remision_interna.setCodigo_historia(null);
		remision_interna.setCodigo_paciente("");
		remision_interna.setFecha_inicio(null);
		remision_interna.setIdentificacion("");
		remision_interna.setCrecimiento_desarrollo(chbCrecimiento_desarrollo
				.isChecked() ? "S" : "N");
		remision_interna.setDet_alteracion_joven(chbDet_alteracion_joven
				.isChecked() ? "S" : "N");
		remision_interna
				.setDet_alteracion_adulto_mayor(chbDet_alteracion_adulto_mayor
						.isChecked() ? "S" : "N");
		remision_interna
				.setControl_prenatal(chbControl_prenatal.isChecked() ? "S"
						: "N");
		remision_interna.setUrgencia(chbUrgencia.isChecked() ? "S" : "N");
		remision_interna
				.setDet_alteracion_agudeza_visual(chbDet_alteracion_agudeza_visual
						.isChecked() ? "S" : "N");
		remision_interna
				.setProg_hipertencion_arterial(chbProg_hipertencion_arterial
						.isChecked() ? "S" : "N");
		remision_interna.setProg_planificacion_fami(chbProg_planificacion_fami
				.isChecked() ? "S" : "N");
		remision_interna.setPsicologia(chbPsicologia.isChecked() ? "S" : "N");
		remision_interna.setNutricion(chbNutricion.isChecked() ? "S" : "N");
		remision_interna.setExamen_fisico(chbExamen_fisico.isChecked() ? "S"
				: "N");
		remision_interna
				.setPrev_salud_bucal(chbPrev_salud_bucal.isChecked() ? "S"
						: "N");
		remision_interna.setVacunacion(chbVacunacion.isChecked() ? "S" : "N");
		remision_interna.setCitologia_servicio(chbCitologia_servicio
				.isChecked() ? "S" : "N");
		remision_interna.setAtencion_recien_nacido(chbAtencion_recien_nacido
				.isChecked() ? "S" : "N");
		remision_interna.setProg_diabetes(chbProg_diabetes.isChecked() ? "S"
				: "N");
		remision_interna.setProg_tbc(chbProg_tbc.isChecked() ? "S" : "N");
		remision_interna.setProg_lepra(chbProg_lepra.isChecked() ? "S" : "N");
		remision_interna
				.setConsulta_externa(chbConsulta_externa.isChecked() ? "S"
						: "N");
		remision_interna.setPsiquiatria(chbPsiquiatria.isChecked() ? "S" : "N");
		remision_interna.setPsicofilaxis(chbPsicofilaxis.isChecked() ? "S"
				: "N");
		remision_interna
				.setGinecobstetrico(chbGinecobstetrico.isChecked() ? "S" : "N");
		remision_interna
				.setRadiografia(chbRadiografias.isChecked() ? "S" : "N");
		remision_interna.setEndodoncia(chbEndodoncia.isChecked() ? "S" : "N");
		remision_interna.setObservacion(tbxObservacion.getValue());
		remision_interna.setCodigo_medico(codigo_medico);
		return remision_interna;

	}

	public void mostrarRemisionInterna(Remision_interna remision_interna,
			boolean readonly) throws Exception {
		//log.info(">>>>>>>>>>>>>>" + remision_interna);
		this.remision_interna = remision_interna;
		if (this.remision_interna != null) {
			chbCrecimiento_desarrollo.setChecked(remision_interna
					.getCrecimiento_desarrollo().equals("S") ? true : false);
			chbDet_alteracion_joven.setChecked(remision_interna
					.getDet_alteracion_joven().equals("S") ? true : false);
			chbDet_alteracion_adulto_mayor.setChecked(remision_interna
					.getDet_alteracion_adulto_mayor().equals("S") ? true
					: false);
			chbControl_prenatal.setChecked(remision_interna
					.getControl_prenatal().equals("S") ? true : false);
			chbUrgencia
					.setChecked(remision_interna.getUrgencia().equals("S") ? true
							: false);
			chbDet_alteracion_agudeza_visual.setChecked(remision_interna
					.getDet_alteracion_agudeza_visual().equals("S") ? true
					: false);
			chbProg_hipertencion_arterial
					.setChecked(remision_interna
							.getProg_hipertencion_arterial().equals("S") ? true
							: false);
			chbProg_planificacion_fami.setChecked(remision_interna
					.getProg_planificacion_fami().equals("S") ? true : false);
			chbPsicologia.setChecked(remision_interna.getPsicologia().equals(
					"S") ? true : false);
			chbNutricion
					.setChecked(remision_interna.getNutricion().equals("S") ? true
							: false);
			chbExamen_fisico.setChecked(remision_interna.getExamen_fisico()
					.equals("S") ? true : false);
			chbPrev_salud_bucal.setChecked(remision_interna
					.getPrev_salud_bucal().equals("S") ? true : false);
			chbVacunacion.setChecked(remision_interna.getVacunacion().equals(
					"S") ? true : false);
			chbCitologia_servicio.setChecked(remision_interna
					.getCitologia_servicio().equals("S") ? true : false);
			chbAtencion_recien_nacido.setChecked(remision_interna
					.getAtencion_recien_nacido().equals("S") ? true : false);
			chbProg_diabetes.setChecked(remision_interna.getProg_diabetes()
					.equals("S") ? true : false);
			chbProg_tbc
					.setChecked(remision_interna.getProg_tbc().equals("S") ? true
							: false);
			chbProg_lepra.setChecked(remision_interna.getProg_lepra().equals(
					"S") ? true : false);
			chbConsulta_externa.setChecked(remision_interna
					.getConsulta_externa().equals("S") ? true : false);
			chbGinecobstetrico.setChecked(remision_interna.getGinecobstetrico()
					.equals("S") ? true : false);
			chbPsiquiatria.setChecked(remision_interna.getPsicologia().equals(
					"S") ? true : false);
			chbPsicofilaxis.setChecked(remision_interna.getPsicofilaxis()
					.equals("S") ? true : false);
			chbRadiografias.setChecked(remision_interna.getRadiografia()
					.equals("S") ? true : false);
			chbEndodoncia.setChecked(remision_interna.getEndodoncia().equals(
					"S") ? true : false);

			tbxObservacion.setValue(remision_interna.getObservacion());
			codigo_medico = remision_interna.getCodigo_medico();

		}

		toolbarbuttonImprimirRI.setVisible(true);
	}

	public void deshabilitarCheck(Admision admision, String via_ingreso) {

		setAdmision(admision);
		String sexo = admision.getPaciente().getSexo();
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		Integer edadmes = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "2", false));

		Boolean agudeza = !(edad == 4 || edad == 11 || edad == 16 || edad == 45
				|| edad == 55 || edad == 60 || edad == 65 || edad == 70
				|| edad == 75 || edad == 80);

		Boolean adulto = !(edad == 45 || edad == 50 || edad == 55 || edad == 60 || edad == 65);

		Boolean joven = !(edad >= 10 && edad <= 29);

		Boolean crecimiento = (edad >= 10);

		Boolean nacido = !(edadmes < 2);

		Boolean embarazo = !(sexo.equalsIgnoreCase("F") && edad >= 15);

		if ((via_ingreso.equals(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO))
				&& sexo.equalsIgnoreCase("F") && (edad >= 10)) {
			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(false);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(edad < 50);
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(edad < 15);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(false);
			chbGinecobstetrico.setDisabled(false);
		}

		if ((via_ingreso.equals(IVias_ingreso.ALTERACION_JOVEN))) {
			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(false);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(edad < 50);
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio
					.setDisabled(!(sexo.equalsIgnoreCase("F") && (edad < 15)));
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(false);
			chbGinecobstetrico.setDisabled(false);
		}

		if ((via_ingreso.equals(IVias_ingreso.ADULTO_MAYOR))) {
			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(sexo.equalsIgnoreCase("F")
					&& !(edad >= 45 && edad == 65));
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(edad < 50);
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(edad < 15);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(sexo.equalsIgnoreCase("F")
					&& !(edad >= 45 && edad == 65));
		}

		if ((via_ingreso.equals(IVias_ingreso.HIPERTENSO_DIABETICO))) {
			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(sexo.equalsIgnoreCase("F"));
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(edad < 50);
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(edad < 15);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(true);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.PLANIFICACION_FAMILIAR))) {
			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(edad < 50);
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(edad < 15);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(true);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.ODONTOLOGIA))) {
			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(false);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(true);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(edad < 50);
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(edad < 15);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(true);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO))) {

			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(true);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(true);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(true);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(true);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}
		if ((via_ingreso.equals(IVias_ingreso.URGENCIA))) {
			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(embarazo);
			chbUrgencia.setDisabled(true);
			chbDet_alteracion_agudeza_visual.setDisabled(true);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(false);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(edad >= 50);
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(sexo.equalsIgnoreCase("F"));
			chbAtencion_recien_nacido.setDisabled(!(edad == 0));
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.HC_MENOR_2_MESES))) {

			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(true);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(true);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(true);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(true);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}
		if ((via_ingreso.equals(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS))) {

			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(true);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(true);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(!(edad == 1));
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS))) {

			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(!(edad >= 4));
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(!(edad >= 2));
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.CONSULTA_EXTERNA))) {
			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(embarazo);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(false);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(edad < 50);
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio
					.setDisabled(!(sexo.equalsIgnoreCase("F") && (edad >= 15)));
			chbAtencion_recien_nacido.setDisabled(nacido);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.HC_AIEPI_2_MESES))) {

			chbCrecimiento_desarrollo.setDisabled(nacido);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(true);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(true);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(true);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(true);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS))) {

			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(!(edad >= 4));
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(!(edad >= 2));
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A))) {

			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(true);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(!(edad >= 2));
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}

		if ((via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2) || (via_ingreso
				.equals(IVias_ingreso.ODONTOLOGIA)))
				&& (edad > 45)
				&& sexo.equalsIgnoreCase("M")) {
			chbRadiografias.setDisabled(false);
			chbEndodoncia.setDisabled(false);
			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(false);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(true);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}
		if ((via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2) || (via_ingreso
				.equals(IVias_ingreso.ODONTOLOGIA)))
				&& (edad >= 1 && edad <= 10)) {
			chbRadiografias.setDisabled(false);
			chbEndodoncia.setDisabled(false);
			chbCrecimiento_desarrollo.setDisabled(false);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(true);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}
		if ((via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2) || (via_ingreso
				.equals(IVias_ingreso.ODONTOLOGIA)))
				&& (edad > 10 && edad < 45) && sexo.equalsIgnoreCase("F")) {
			chbRadiografias.setDisabled(false);
			chbEndodoncia.setDisabled(false);
			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(false);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(false);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(false);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(false);
			chbPrev_salud_bucal.setDisabled(true);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(false);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(false);
			chbGinecobstetrico.setDisabled(false);
		}
		if ((via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2) || (via_ingreso
				.equals(IVias_ingreso.ODONTOLOGIA)))
				&& (edad > 10 && edad < 45) && sexo.equalsIgnoreCase("M")) {
			chbRadiografias.setDisabled(false);
			chbEndodoncia.setDisabled(false);
			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(false);
			chbDet_alteracion_adulto_mayor.setDisabled(true);
			chbControl_prenatal.setDisabled(true);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(true);
			chbPrev_salud_bucal.setDisabled(true);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(false);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}
		if ((via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2) || (via_ingreso
				.equals(IVias_ingreso.ODONTOLOGIA)))
				&& (edad > 45)
				&& sexo.equalsIgnoreCase("F")) {
			chbRadiografias.setDisabled(false);
			chbEndodoncia.setDisabled(false);
			chbCrecimiento_desarrollo.setDisabled(true);
			chbDet_alteracion_joven.setDisabled(true);
			chbDet_alteracion_adulto_mayor.setDisabled(false);
			chbControl_prenatal.setDisabled(false);
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(false);
			chbPrev_salud_bucal.setDisabled(true);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(true);
			chbAtencion_recien_nacido.setDisabled(true);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(false);
			chbGinecobstetrico.setDisabled(false);
		}

		if ((via_ingreso.equals(IVias_ingreso.PSICOLOGIA))) {
			//log.info(">>>validar chek");
			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(!sexo.equalsIgnoreCase("F"));
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico
					.setDisabled(!(sexo.equalsIgnoreCase("F") && (edad < 50)));
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio
					.setDisabled(!(sexo.equalsIgnoreCase("F") && (edad < 15)));
			chbAtencion_recien_nacido.setDisabled(nacido);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(false);
			chbGinecobstetrico.setDisabled(!sexo.equalsIgnoreCase("F"));
		}

		if ((via_ingreso.equals(IVias_ingreso.PSIQUIATRIA))) {
			//log.info(">>>validar chek" + IVias_ingreso.PSIQUIATRIA);
			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(!sexo.equalsIgnoreCase("F"));
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico
					.setDisabled(!(sexo.equalsIgnoreCase("F") && (edad < 50)));
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio
					.setDisabled(!(sexo.equalsIgnoreCase("F") && (edad < 15)));
			chbAtencion_recien_nacido.setDisabled(nacido);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(false);
			chbGinecobstetrico.setDisabled(!sexo.equalsIgnoreCase("F"));
		}

		if ((via_ingreso.equals(IVias_ingreso.VISITA_DOMICILIARIA))) {
			//log.info(">>>validar chek");
			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(!sexo.equalsIgnoreCase("F"));
			chbUrgencia.setDisabled(false);
			chbDet_alteracion_agudeza_visual.setDisabled(agudeza);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(true);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico
					.setDisabled(!(sexo.equalsIgnoreCase("F") && (edad < 50)));
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio
					.setDisabled(!(sexo.equalsIgnoreCase("F") && (edad < 15)));
			chbAtencion_recien_nacido.setDisabled(nacido);
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(false);
			chbGinecobstetrico.setDisabled(!sexo.equalsIgnoreCase("F"));
		}
		if ((via_ingreso.equals(IVias_ingreso.HOSPITALIZACIONES))) {
			chbCrecimiento_desarrollo.setDisabled(crecimiento);
			chbDet_alteracion_joven.setDisabled(joven);
			chbDet_alteracion_adulto_mayor.setDisabled(adulto);
			chbControl_prenatal.setDisabled(embarazo);
			chbUrgencia.setDisabled(true);
			chbDet_alteracion_agudeza_visual.setDisabled(true);
			chbProg_hipertencion_arterial.setDisabled(false);
			chbProg_planificacion_fami.setDisabled(false);
			chbPsicologia.setDisabled(false);
			chbNutricion.setDisabled(false);
			chbExamen_fisico.setDisabled(edad >= 50);
			chbPrev_salud_bucal.setDisabled(false);
			chbVacunacion.setDisabled(false);
			chbCitologia_servicio.setDisabled(sexo.equalsIgnoreCase("F"));
			chbAtencion_recien_nacido.setDisabled(!(edad == 0));
			chbProg_diabetes.setDisabled(false);
			chbProg_tbc.setDisabled(false);
			chbProg_lepra.setDisabled(false);
			chbConsulta_externa.setDisabled(false);
			chbPsiquiatria.setDisabled(false);
			chbPsicofilaxis.setDisabled(true);
			chbGinecobstetrico.setDisabled(true);
		}
	}

	public void imprimirReporte() {
		if (remision_interna != null) {
			//log.info("remision_interna  ====> " + remision_interna);
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("name_report", "RemisionInterna");
			parametros.put("formato", "pdf");
			parametros.put("admision", admision);
			parametros.put("codigo_historia",
					remision_interna.getCodigo_historia());
			parametros.put("codigo_medico", admision.getCodigo_medico());
			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", zkWindow, parametros);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			MensajesUtil
					.mensajeAlerta(
							"No se ha guardado ninguna remision interna",
							"Esta intentando imprimir una remision interna que no se ha guardado aun");
		}
	}

	public ZKWindow getZkWindow() {
		return zkWindow;
	}

	public void setZkWindow(ZKWindow zkWindow) {
		this.zkWindow = zkWindow;
	}

	public Admision getAdmision() {
		return admision;
	}

	public void setAdmision(Admision admision) {
		this.admision = admision;
	}

	public String getVia_ingreso() {
		return via_ingreso;
	}

	public void setVia_ingreso(String via_ingreso) {
		this.via_ingreso = via_ingreso;
	}

}