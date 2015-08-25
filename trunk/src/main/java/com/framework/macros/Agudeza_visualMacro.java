/*
 * Agudeza_visualMacro.java
 * 
 * Tecnologo: Manuel Aguilar 
 */
package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Agudeza_visual;
import healthmanager.modelo.bean.Paciente;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;

import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

public class Agudeza_visualMacro extends Groupbox implements AfterCompose {

	// Componentes //

	private Listbox lbxParameter;
	private Borderlayout groupboxEditar;
	private Doublebox dbxCon_lentes_od;
	private Doublebox dbxCon_lentes_od2;
	private Doublebox dbxCon_lentes_oi;
	private Doublebox dbxCon_lentes_oi2;
	private Doublebox dbxCon_lentes_ao;
	private Doublebox dbxCon_lentes_ao2;
	private Textbox tbxObservacion;
	private Radiogroup rdbConducta;

	private final String[] idsExcluyentes = {};

	private ZKWindow zkWindow;
	private Admision admision;
	private String via_ingreso;

	private Agudeza_visual agudeza_visual;
	public static Logger log = Logger.getLogger(Agudeza_visual.class);

	public void inicializarMacro(ZKWindow zkWindow, Admision admision,
			String via_ingreso) {
		setZkWindow(zkWindow);
		setAdmision(admision);
		setVia_ingreso(via_ingreso);
	}

	@Override
	public void afterCompose() {
		cargarComponentes();

	}

	public static boolean aplicaAgudeza(Admision admision) {
		boolean sw = false;
		Integer edad = Integer.valueOf(Util.getEdad(
				new java.text.SimpleDateFormat("dd/MM/yyyy").format(admision
						.getPaciente().getFecha_nacimiento()), "1", false));
		if (edad == 4 || edad == 11 || edad == 16 || edad == 45 || edad == 55
				|| edad == 55 || edad == 60 || edad == 65 || edad == 70
				|| edad == 75 || edad == 80 || edad == 85 || edad == 90
				|| edad == 95) {
			sw = true;
		}

		return sw;
	}

	private void cargarComponentes() {
		dbxCon_lentes_ao = (Doublebox) this.getFellow("dbxCon_lentes_ao");
		dbxCon_lentes_ao2 = (Doublebox) this.getFellow("dbxCon_lentes_ao2");
		dbxCon_lentes_od = (Doublebox) this.getFellow("dbxCon_lentes_od");
		dbxCon_lentes_od2 = (Doublebox) this.getFellow("dbxCon_lentes_od2");
		dbxCon_lentes_oi = (Doublebox) this.getFellow("dbxCon_lentes_oi");
		dbxCon_lentes_oi2 = (Doublebox) this.getFellow("dbxCon_lentes_oi2");
		rdbConducta = (Radiogroup) this.getFellow("rdbConducta");
		tbxObservacion = (Textbox) this.getFellow("tbxObservacion");
	}

	public boolean validarCamposObligatorios() {
		boolean valida = false;
		FormularioUtil.validarCamposObligatorios(dbxCon_lentes_ao,
				dbxCon_lentes_ao2, dbxCon_lentes_od, dbxCon_lentes_od2,
				dbxCon_lentes_oi, dbxCon_lentes_oi2, tbxObservacion);
		if (!dbxCon_lentes_ao.getText().isEmpty()
				&& !dbxCon_lentes_ao2.getText().isEmpty()
				&& !dbxCon_lentes_od.getText().isEmpty()
				&& !dbxCon_lentes_od2.getText().isEmpty()
				&& !dbxCon_lentes_oi.getText().isEmpty()
				&& !dbxCon_lentes_oi2.getText().isEmpty()
				&& !tbxObservacion.getText().isEmpty()) {
			valida = true;
		}

		return valida;

	}

	public void iniciarMacroAgudezaVisual(ZKWindow zkWindow, Paciente paciente) {
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

	public Agudeza_visual obtenerAgudezaVisual() throws Exception {
		if (agudeza_visual == null)
			agudeza_visual = new Agudeza_visual();
		agudeza_visual.setCodigo_empresa(zkWindow.getEmpresa()
				.getCodigo_empresa());
		agudeza_visual.setCodigo_sucursal(zkWindow.getSucursal()
				.getCodigo_sucursal());
		agudeza_visual.setCodigo_historia(null);
		agudeza_visual.setIdentificacion("");
		agudeza_visual.setCon_lentes_ao(ConvertidorDatosUtil
				.convertirDato(dbxCon_lentes_ao.getValue()));
		agudeza_visual.setCon_lentes_ao2(ConvertidorDatosUtil
				.convertirDato(dbxCon_lentes_ao2.getValue()));
		agudeza_visual.setCon_lentes_od(ConvertidorDatosUtil
				.convertirDato(dbxCon_lentes_od.getValue()));
		agudeza_visual.setCon_lentes_od2(ConvertidorDatosUtil
				.convertirDato(dbxCon_lentes_od2.getValue()));
		agudeza_visual.setCon_lentes_oi(ConvertidorDatosUtil
				.convertirDato(dbxCon_lentes_oi.getValue()));
		agudeza_visual.setCon_lentes_oi2(ConvertidorDatosUtil
				.convertirDato(dbxCon_lentes_oi2.getValue()));
		agudeza_visual.setConducta(rdbConducta.getSelectedItem().getValue()
				.equals("1") ? "S" : "N");
		agudeza_visual.setObservacion(tbxObservacion.getValue());

		return agudeza_visual;

	}

	public void mostrarAgudezaVisual(Agudeza_visual agudeza_visual,
			boolean readonly) throws Exception {
		this.agudeza_visual = agudeza_visual;
		if (this.agudeza_visual != null) {
			dbxCon_lentes_od.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_od()));
			dbxCon_lentes_od2.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_od2()));
			dbxCon_lentes_oi.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_oi()));
			dbxCon_lentes_oi2.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_oi2()));
			dbxCon_lentes_ao.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_ao()));
			dbxCon_lentes_ao2.setValue(ConvertidorDatosUtil
					.convertirDato(agudeza_visual.getCon_lentes_ao2()));
			tbxObservacion.setValue(agudeza_visual.getObservacion());
			Utilidades.seleccionarRadio(rdbConducta, agudeza_visual
					.getConducta().toString());
		} else {
			//log.info(">>>>>>>>>>>>>>" + agudeza_visual);
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