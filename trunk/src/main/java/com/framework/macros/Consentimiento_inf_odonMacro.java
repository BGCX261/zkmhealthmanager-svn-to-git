/* 
 * Generado Automaticamente.
 * Tecnologo: Manuel Aguilar Herrera
 */
package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Consentimiento_inf_odon;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.Consentimiento_inf_odonService;

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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;

public class Consentimiento_inf_odonMacro extends Groupbox implements
		AfterCompose {

	private Listbox lbxParameter;
	private Borderlayout groupboxEditar;
	private Checkbox chbPro_prev_rea_aler_placa;
	private Checkbox chbPro_prev_lac_tej_blan;
	private Checkbox chbPro_prev_rea_aler_fluor;
	private Checkbox chbOpe_dent_tej_blando;
	private Checkbox chbOpe_dent_lux_atm;
	private Checkbox chbOpe_dent_exp_pul;
	private Checkbox chbOpe_dent_pul_rev;
	private Checkbox chbOpe_dent_ulc_trau;
	private Checkbox chbPer_tej_blando;
	private Checkbox chbEndodoncia_frac_inst;
	private Checkbox chbEndodoncia_periodontitis;
	private Checkbox chbEndodoncia_perforaciones;
	private Checkbox chbEndodoncia_sobre;
	private Checkbox chbEndodoncia_tej_blando;
	private Checkbox chbEndodoncia_inj_hipoclorito;
	private Checkbox chbEndodoncia_pigmentacion;
	private Checkbox chbEndodoncia_enfisema;
	private Checkbox chbCirugia_oral_fractura;
	private Checkbox chbCirugia_oral_hematoma;
	private Checkbox chbCirugia_oral_hemorragia;
	private Checkbox chbCirugia_oral_alveolitis;
	private Checkbox chbCirugia_oral_tej_blando;
	private Checkbox chbCirugia_oral_luxacion;
	private Checkbox chbCirugia_oral_alergia;
	private Checkbox chbProc_anes_parestecia;
	private Checkbox chbProc_anes_trismos;
	private Checkbox chbProc_anes_lac;
	private Checkbox chbProc_anes_hem_anafi;
	private Checkbox chbProc_anes_chock;
	private Checkbox chbProc_anes_reac_aler;
	private final String[] idsExcluyentes = { "toolbarbuttonImprimirRI" };

	private ZKWindow zkWindow;
	private Admision admision;
	private String via_ingreso;

	private String codigo_medico;

	private Consentimiento_inf_odon consentimineto_inf_odon;

	private Toolbarbutton toolbarbuttonImprimirRI;

	public static Logger log = Logger
			.getLogger(Consentimiento_inf_odonMacro.class);

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

	private void cargarComponentes() {

		chbPro_prev_rea_aler_placa  = (Checkbox) this.getFellow("chbPro_prev_rea_aler_placa");
		chbPro_prev_lac_tej_blan = (Checkbox) this.getFellow("chbPro_prev_lac_tej_blan");
		chbPro_prev_rea_aler_fluor = (Checkbox) this.getFellow("chbPro_prev_rea_aler_fluor");
		
		chbOpe_dent_tej_blando = (Checkbox) this
				.getFellow("chbOpe_dent_tej_blando");
		chbOpe_dent_lux_atm = (Checkbox) this.getFellow("chbOpe_dent_lux_atm");
		chbOpe_dent_exp_pul = (Checkbox) this.getFellow("chbOpe_dent_exp_pul");
		chbOpe_dent_pul_rev = (Checkbox) this.getFellow("chbOpe_dent_pul_rev");
		chbOpe_dent_ulc_trau = (Checkbox) this
				.getFellow("chbOpe_dent_ulc_trau");
		chbPer_tej_blando = (Checkbox) this.getFellow("chbPer_tej_blando");
		chbEndodoncia_frac_inst = (Checkbox) this
				.getFellow("chbEndodoncia_frac_inst");
		chbEndodoncia_periodontitis = (Checkbox) this
				.getFellow("chbEndodoncia_periodontitis");
		chbEndodoncia_perforaciones = (Checkbox) this
				.getFellow("chbEndodoncia_perforaciones");
		chbEndodoncia_sobre = (Checkbox) this.getFellow("chbEndodoncia_sobre");
		chbEndodoncia_tej_blando = (Checkbox) this
				.getFellow("chbEndodoncia_tej_blando");
		chbEndodoncia_inj_hipoclorito = (Checkbox) this
				.getFellow("chbEndodoncia_inj_hipoclorito");
		chbEndodoncia_pigmentacion = (Checkbox) this
				.getFellow("chbEndodoncia_pigmentacion");
		chbEndodoncia_enfisema = (Checkbox) this
				.getFellow("chbEndodoncia_enfisema");
		chbCirugia_oral_fractura = (Checkbox) this
				.getFellow("chbCirugia_oral_fractura");
		chbCirugia_oral_hematoma = (Checkbox) this
				.getFellow("chbCirugia_oral_hematoma");
		chbCirugia_oral_hemorragia = (Checkbox) this
				.getFellow("chbCirugia_oral_hemorragia");
		chbCirugia_oral_alveolitis = (Checkbox) this
				.getFellow("chbCirugia_oral_alveolitis");
		chbCirugia_oral_tej_blando = (Checkbox) this
				.getFellow("chbCirugia_oral_tej_blando");
		chbCirugia_oral_luxacion = (Checkbox) this
				.getFellow("chbCirugia_oral_luxacion");
		chbCirugia_oral_alergia = (Checkbox) this
				.getFellow("chbCirugia_oral_alergia");
		chbProc_anes_parestecia = (Checkbox) this
				.getFellow("chbProc_anes_parestecia");
		chbProc_anes_trismos = (Checkbox) this
				.getFellow("chbProc_anes_trismos");
		chbProc_anes_lac = (Checkbox) this.getFellow("chbProc_anes_lac");
		chbProc_anes_hem_anafi = (Checkbox) this
				.getFellow("chbProc_anes_hem_anafi");
		chbProc_anes_chock = (Checkbox) this.getFellow("chbProc_anes_chock");
		chbProc_anes_reac_aler = (Checkbox) this
				.getFellow("chbProc_anes_reac_aler");
		toolbarbuttonImprimirRI = (Toolbarbutton) this
				.getFellow("toolbarbuttonImprimirRI");
	}

	public void iniciarMacroConsentimientoInfOdon(ZKWindow zkWindow,
			Paciente paciente) {
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

	public Consentimiento_inf_odon obtenerConsentiminetoInfOdon()
			throws Exception {
		if (consentimineto_inf_odon == null)
			consentimineto_inf_odon = new Consentimiento_inf_odon();

		Consentimiento_inf_odon consentimineto_inf_odon = new Consentimiento_inf_odon();
		consentimineto_inf_odon.setCodigo_empresa(zkWindow.getEmpresa()
				.getCodigo_empresa());
		consentimineto_inf_odon.setCodigo_sucursal(zkWindow.getSucursal()
				.getCodigo_sucursal());
		consentimineto_inf_odon.setCodigo_historia(null);
		consentimineto_inf_odon.setFecha_inicio(null);
		consentimineto_inf_odon.setIdentificacion("");
		consentimineto_inf_odon.setNro_ingreso(admision.getNro_ingreso());
		//log.info("===> admision " + admision.getNro_ingreso());
		consentimineto_inf_odon.setPro_prev_rea_aler_placa(chbPro_prev_rea_aler_placa
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setPro_prev_lac_tej_blan(chbPro_prev_lac_tej_blan
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setPro_prev_rea_aler_fluor(chbPro_prev_rea_aler_fluor
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setOpe_dent_tej_blando(chbOpe_dent_tej_blando
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setOpe_dent_lux_atm(chbOpe_dent_lux_atm
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setOpe_dent_exp_pul(chbOpe_dent_exp_pul
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setOpe_dent_pul_rev(chbOpe_dent_pul_rev
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setOpe_dent_ulc_trau(chbOpe_dent_ulc_trau
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setPer_tej_blando(chbPer_tej_blando.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setEndodoncia_frac_inst(chbEndodoncia_frac_inst
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setEndodoncia_periodontitis(chbEndodoncia_periodontitis
						.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setEndodoncia_perforaciones(chbEndodoncia_perforaciones
						.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setEndodoncia_sobre(chbEndodoncia_sobre
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setEndodoncia_tej_blando(chbEndodoncia_tej_blando.isChecked() ? "S"
						: "N");
		consentimineto_inf_odon
				.setEndodoncia_inj_hipoclorito(chbEndodoncia_inj_hipoclorito
						.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setEndodoncia_pigmentacion(chbEndodoncia_pigmentacion
						.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setEndodoncia_enfisema(chbEndodoncia_enfisema
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setCirugia_oral_fractura(chbCirugia_oral_fractura.isChecked() ? "S"
						: "N");
		consentimineto_inf_odon
				.setCirugia_oral_hematoma(chbCirugia_oral_hematoma.isChecked() ? "S"
						: "N");
		consentimineto_inf_odon
				.setCirugia_oral_hemorragia(chbCirugia_oral_hemorragia
						.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setCirugia_oral_alveolitis(chbCirugia_oral_alveolitis
						.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setCirugia_oral_tej_blando(chbCirugia_oral_tej_blando
						.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setCirugia_oral_luxacion(chbCirugia_oral_luxacion.isChecked() ? "S"
						: "N");
		consentimineto_inf_odon.setCirugia_oral_alergia(chbCirugia_oral_alergia
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setProc_anes_parestecia(chbProc_anes_parestecia
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setProc_anes_trismos(chbProc_anes_trismos
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon
				.setProc_anes_lac(chbProc_anes_lac.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setProc_anes_hem_anafi(chbProc_anes_hem_anafi
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setProc_anes_chock(chbProc_anes_chock
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setProc_anes_reac_aler(chbProc_anes_reac_aler
				.isChecked() ? "S" : "N");
		consentimineto_inf_odon.setCodigo_medico(codigo_medico);
		return consentimineto_inf_odon;

	}

	public void mostrarConsentimiento_inf(
			Consentimiento_inf_odon consentimineto_inf_odon, boolean readonly)
			throws Exception {
		this.consentimineto_inf_odon = consentimineto_inf_odon;
		if (consentimineto_inf_odon != null) {
			
			chbOpe_dent_tej_blando.setChecked(consentimineto_inf_odon
					.getOpe_dent_tej_blando().equals("S") ? true : false);
			chbOpe_dent_lux_atm.setChecked(consentimineto_inf_odon
					.getOpe_dent_lux_atm().equals("S") ? true : false);
			chbOpe_dent_exp_pul.setChecked(consentimineto_inf_odon
					.getOpe_dent_exp_pul().equals("S") ? true : false);
			chbOpe_dent_pul_rev.setChecked(consentimineto_inf_odon
					.getOpe_dent_pul_rev().equals("S") ? true : false);
			chbOpe_dent_ulc_trau.setChecked(consentimineto_inf_odon
					.getOpe_dent_ulc_trau().equals("S") ? true : false);
			chbPer_tej_blando.setChecked(consentimineto_inf_odon
					.getPer_tej_blando().equals("S") ? true : false);
			chbEndodoncia_frac_inst.setChecked(consentimineto_inf_odon
					.getEndodoncia_frac_inst().equals("S") ? true : false);
			chbEndodoncia_periodontitis.setChecked(consentimineto_inf_odon
					.getEndodoncia_periodontitis().equals("S") ? true : false);
			chbEndodoncia_perforaciones.setChecked(consentimineto_inf_odon
					.getEndodoncia_perforaciones().equals("S") ? true : false);
			chbEndodoncia_sobre.setChecked(consentimineto_inf_odon
					.getEndodoncia_sobre().equals("S") ? true : false);
			chbEndodoncia_tej_blando.setChecked(consentimineto_inf_odon
					.getEndodoncia_tej_blando().equals("S") ? true : false);
			chbEndodoncia_inj_hipoclorito
					.setChecked(consentimineto_inf_odon
							.getEndodoncia_inj_hipoclorito().equals("S") ? true
							: false);
			chbEndodoncia_pigmentacion.setChecked(consentimineto_inf_odon
					.getEndodoncia_pigmentacion().equals("S") ? true : false);
			chbEndodoncia_enfisema.setChecked(consentimineto_inf_odon
					.getEndodoncia_enfisema().equals("S") ? true : false);
			chbCirugia_oral_fractura.setChecked(consentimineto_inf_odon
					.getCirugia_oral_fractura().equals("S") ? true : false);
			chbCirugia_oral_hematoma.setChecked(consentimineto_inf_odon
					.getCirugia_oral_hematoma().equals("S") ? true : false);
			chbCirugia_oral_hemorragia.setChecked(consentimineto_inf_odon
					.getCirugia_oral_hemorragia().equals("S") ? true : false);
			chbCirugia_oral_alveolitis.setChecked(consentimineto_inf_odon
					.getCirugia_oral_alveolitis().equals("S") ? true : false);
			chbCirugia_oral_tej_blando.setChecked(consentimineto_inf_odon
					.getCirugia_oral_tej_blando().equals("S") ? true : false);
			chbCirugia_oral_luxacion.setChecked(consentimineto_inf_odon
					.getCirugia_oral_luxacion().equals("S") ? true : false);
			chbCirugia_oral_alergia.setChecked(consentimineto_inf_odon
					.getCirugia_oral_alergia().equals("S") ? true : false);
			chbProc_anes_parestecia.setChecked(consentimineto_inf_odon
					.getProc_anes_parestecia().equals("S") ? true : false);
			chbProc_anes_trismos.setChecked(consentimineto_inf_odon
					.getProc_anes_trismos().equals("S") ? true : false);
			chbProc_anes_lac.setChecked(consentimineto_inf_odon
					.getProc_anes_lac().equals("S") ? true : false);
			chbProc_anes_hem_anafi.setChecked(consentimineto_inf_odon
					.getProc_anes_hem_anafi().equals("S") ? true : false);
			chbProc_anes_chock.setChecked(consentimineto_inf_odon
					.getProc_anes_chock().equals("S") ? true : false);
			chbProc_anes_reac_aler.setChecked(consentimineto_inf_odon
					.getProc_anes_reac_aler().equals("S") ? true : false);
			if(chbPro_prev_rea_aler_placa.getValue() != null){
			chbPro_prev_rea_aler_placa.setChecked(consentimineto_inf_odon.getPro_prev_rea_aler_placa().equals("S") ? true : false);
			}
			if(chbPro_prev_lac_tej_blan.getValue() != null){
			chbPro_prev_lac_tej_blan.setChecked(consentimineto_inf_odon.getPro_prev_lac_tej_blan().equals("S") ? true : false);
			}
			if(chbPro_prev_rea_aler_fluor.getValue() != null){
			chbPro_prev_rea_aler_fluor.setChecked(consentimineto_inf_odon.getPro_prev_rea_aler_fluor().equals("S") ? true : false);
			}
		}
		toolbarbuttonImprimirRI.setVisible(true);

	}

	public void imprimirReporte() {
		Consentimiento_inf_odon consentimineto_inf_odon = new Consentimiento_inf_odon();
		consentimineto_inf_odon.setCodigo_empresa(admision.getCodigo_empresa());
		consentimineto_inf_odon.setCodigo_sucursal(admision
				.getCodigo_sucursal());
		consentimineto_inf_odon.setNro_ingreso(admision.getNro_ingreso());
		consentimineto_inf_odon.setIdentificacion(admision
				.getNro_identificacion());

		consentimineto_inf_odon = this.zkWindow.getServiceLocator()
				.getServicio(Consentimiento_inf_odonService.class)
				.consultarPorParametros(consentimineto_inf_odon);
		//log.info("===> consentimineto_inf_odon===> " + consentimineto_inf_odon);

		if (consentimineto_inf_odon != null) {
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("name_report", "Consentimiento_inf_odon");
			parametros.put("formato", "pdf");
			parametros.put("codigo_historia",
					consentimineto_inf_odon.getCodigo_historia());
			parametros.put("codigo_empresa",
					consentimineto_inf_odon.getCodigo_empresa());
			parametros.put("codigo_sucursal",
					consentimineto_inf_odon.getCodigo_sucursal());
			//log.info("===> odont " + parametros);
			//log.info("==> consetimiento "
					//+ consentimineto_inf_odon.getCodigo_historia());
			//log.info("===> admi " + admision);
			//log.info("parameter>>>>>>>>>>>" + parametros);
			Component componente = Executions.createComponents(
					"/pages/printInformes.zul", zkWindow, parametros);
			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		} else {
			MensajesUtil
					.mensajeAlerta(
							"No se ha guardado ningún consentimeinto informado",
							"Esta intentando imprimir un consentimeinto informado que no se ha guardado aun");
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