package com.framework.macros;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_barrio;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Localidad;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.impl.XulElement;

import com.framework.macros.impl.InformacionPacienteIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MapeadorDeComponentesUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class InformacionPacientesMacro extends Grid implements AfterCompose {

	private Timestamp fecha_inicial;
	private Timestamp fecha_cierre;

	private Textbox mcTbxMostrar_centro;
	private Datebox mcDtbxFecha_inicial;
	private Label mcLbFecha_cierre;
	private Datebox mcDtbxFecha_cierre;
	private Longbox mcTbxCodigo_historia;
	private Textbox mcTbxCodigo_historiaFac;
	private Bandbox mcBandboxPaciente;
	private Textbox mcTbxInfoPaciente;
	// private Radiogroup mcRdbSexo;
	private Textbox mcTbxSexo;
	private Textbox mcTbxDireccion;
	private Doublebox mcDbxTelefono;
	private Datebox mcDtbxFecha_ncto;
	private Textbox mcTbxEdad;
	private Intbox mcIbxAnios = new Intbox();
	private Intbox mcIbxMeses = new Intbox();
	private Intbox mcIbxDias = new Intbox();
	private Textbox mcTbxLugar_ncto;
	private Textbox mcTbxRegimen;
	private Listbox mcLbxRaza;
	private Listbox mcLbxOcupacion;
	private Textbox mcTbxEstado_civil;
	private Textbox mcTbxAseguradora;
	private Listbox mcLbxEducativo;
	private Textbox mcTbxBarrio;
	private Textbox mcTbxLocalidad;
	private Textbox mcTbxCentro_atencion;
	private Label mcLbCentro_atencion;

	private String codigo_centro;

	private ZKWindow zkWindow;
	private Admision admision;
	private String codigo_historia_aux;

	private Checkbox chkAplica_tel;

	// private String codigo_barrio;
	// private String codigo_localidad;

	@Override
	public void afterCompose() {
		cargarComponentes();
		if (mcTbxMostrar_centro.getValue() != null
				&& mcTbxMostrar_centro.getValue().equalsIgnoreCase("true")) {
			mcTbxCentro_atencion.setVisible(true);
			mcLbCentro_atencion.setVisible(true);
		} else {
			mcTbxCentro_atencion.setVisible(false);
			mcLbCentro_atencion.setVisible(false);
		}
	}

	private void cargarComponentes() {
		mcDtbxFecha_inicial = (Datebox) this.getFellow("mcDtbxFecha_inicial");
		mcTbxCodigo_historia = (Longbox) this.getFellow("mcTbxCodigo_historia");
		mcTbxCodigo_historiaFac = (Textbox) this
				.getFellow("mcTbxCodigo_historiaFac");
		mcBandboxPaciente = (Bandbox) this.getFellow("mcBandboxPaciente");
		mcTbxInfoPaciente = (Textbox) this.getFellow("mcTbxInfoPaciente");
		// mcRdbSexo = (Radiogroup) this.getFellow("mcRdbSexo");
		mcTbxSexo = (Textbox) this.getFellow("mcTbxSexo");
		mcTbxDireccion = (Textbox) this.getFellow("mcTbxDireccion");
		mcDbxTelefono = (Doublebox) this.getFellow("mcDbxTelefono");
		mcDtbxFecha_ncto = (Datebox) this.getFellow("mcDtbxFecha_ncto");
		mcTbxLugar_ncto = (Textbox) this.getFellow("mcTbxLugar_ncto");
		mcTbxRegimen = (Textbox) this.getFellow("mcTbxRegimen");
		mcLbxRaza = (Listbox) this.getFellow("mcLbxRaza");
		mcLbxOcupacion = (Listbox) this.getFellow("mcLbxOcupacion");
		mcLbxEducativo = (Listbox) this.getFellow("mcLbxEducativo");
		mcTbxEstado_civil = (Textbox) this.getFellow("mcTbxEstado_civil");
		mcTbxAseguradora = (Textbox) this.getFellow("mcTbxAseguradora");
		mcTbxBarrio = (Textbox) this.getFellow("mcTbxBarrio");
		mcTbxLocalidad = (Textbox) this.getFellow("mcTbxLocalidad");
		mcLbCentro_atencion = (Label) this.getFellow("mcLbCentro_atencion");
		mcTbxCentro_atencion = (Textbox) this.getFellow("mcTbxCentro_atencion");
		mcTbxEdad = (Textbox) this.getFellow("mcTbxEdad");
		mcTbxMostrar_centro = (Textbox) this.getFellow("mcTbxMostrar_centro");
		mcLbFecha_cierre = (Label) this.getFellow("mcLbFecha_cierre");
		mcDtbxFecha_cierre = (Datebox) this.getFellow("mcDtbxFecha_cierre");

		chkAplica_tel = (Checkbox) this.getFellow("chkAplica_tel");
	}

	/**
	 * Meetodo para cargar la informacion la admision en vista
	 * 
	 * @param admision
	 *            admision que se desea cargar
	 * @param zkWindow
	 *            ventana donde esta la admision que tiene los servicios para
	 *            hacer las consultas
	 * @param informacionPacienteIMG
	 *            Implementacion del proceso que desea ejecutar cuando se cargue
	 *            la informacion
	 */

	public void cargarInformacion(Admision admision, ZKWindow zkWindow,
			InformacionPacienteIMG informacionPacienteIMG) {
		try {
			this.zkWindow = zkWindow;
			this.admision = admision;
			FormularioUtil.limpiarComponentes(this,
					new String[] { "mcDtbxFecha_inicial" });

			Utilidades.listarPertenencia_etnica(mcLbxRaza, true,
					zkWindow.getServiceLocator());
			mcLbxRaza.setDisabled(true);
			Utilidades.listarOcupaciones(mcLbxOcupacion, true,
					zkWindow.getServiceLocator());
			Utilidades.listarNivel_educativo(mcLbxEducativo, true,
					zkWindow.getServiceLocator());

			mcDtbxFecha_inicial.setText("");
			mcDtbxFecha_cierre.setText("");
			// mcDtbxFecha_inicial.setValue(admision.getFecha_ingreso());
			if (fecha_inicial != null)
				mcDtbxFecha_inicial.setValue(fecha_inicial);
			if (fecha_cierre != null)
				mcDtbxFecha_cierre.setValue(fecha_cierre);

			Paciente registro = admision.getPaciente();
			mcBandboxPaciente.setValue(admision.getNro_identificacion());
			mcTbxCodigo_historiaFac.setValue(admision.getNro_identificacion());
			mcTbxInfoPaciente.setValue(registro.getNombreCompleto());
			mcTbxDireccion.setValue(registro.getDireccion());

			String telefono_res = (registro.getTel_res() != null
					&& !registro.getTel_res().isEmpty() ? registro.getTel_res()
					: "");

			chkAplica_tel.setChecked(!telefono_res.isEmpty());

			mcDbxTelefono.setValue(ConvertidorDatosUtil
					.convertirDato(telefono_res));
			mcDtbxFecha_ncto.setValue(registro.getFecha_nacimiento());
			Departamentos departamentos = new Departamentos(
					registro.getCodigo_dpto(), "");
			departamentos = zkWindow.getServiceLocator()
					.getDepartamentosService().consultar(departamentos);
			Municipios municipios = new Municipios(
					registro.getCodigo_municipio(), registro.getCodigo_dpto(),
					"");
			municipios = zkWindow.getServiceLocator().getMunicipiosService()
					.consultar(municipios);
			mcTbxLugar_ncto.setValue((municipios != null ? municipios
					.getNombre() : "")
					+ " - "
					+ (departamentos != null ? departamentos.getNombre() : ""));

			Utilidades.seleccionarListitem(mcLbxRaza, admision.getPaciente()
					.getPertenencia_etnica());

			mcTbxEstado_civil.setValue(Utilidades.getNombreElemento(
					registro.getEstado_civil(), "estado_civil", zkWindow));

			mcTbxAseguradora
					.setValue(admision.getAdministradora() != null ? (admision
							.getAdministradora().getCodigo() + "-" + admision
							.getAdministradora().getNombre()) : ""
							+ registro.getCodigo_administradora());

			Utilidades.seleccionarListitem(mcLbxOcupacion,
					registro.getCodigo_ocupacion());

			Utilidades.seleccionarListitem(mcLbxEducativo,
					registro.getCodigo_educativo());

			// Utilidades.seleccionarRadio(mcRdbSexo, registro.getSexo());
			mcTbxSexo.setValue(Utilidades.getNombreElemento(registro.getSexo(),
					"sexo", zkWindow));
			mcTbxRegimen.setValue(Utilidades.getNombreElemento(
					registro.getTipo_usuario(), "tipo_usuario", zkWindow));

			Map<String, Integer> mapa_edades = Util.getEdadYYYYMMDD(registro
					.getFecha_nacimiento());

			Integer anios = mapa_edades.get("anios");
			Integer meses = mapa_edades.get("meses");
			Integer dias = mapa_edades.get("dias");

			mcIbxAnios.setValue(anios);
			mcIbxMeses.setValue(meses);
			mcIbxDias.setValue(dias);

			if (anios.intValue() == 0 && meses.intValue() == 0) {
				mcTbxEdad.setValue(dias + (dias == 1 ? " día" : " días"));
			} else if (anios.intValue() == 0) {
				mcTbxEdad.setValue(meses + (meses == 1 ? " mes (" : " meses (")
						+ (dias + (dias == 1 ? " día" : " días")) + ")");
			} else {
				int current_meses = meses.intValue() - (anios.intValue() * 12);
				mcTbxEdad
						.setValue(anios
								+ (anios == 1 ? " año" : " Años")
								+ (current_meses != 0 ? (" y " + current_meses + (current_meses == 1 ? " mes "
										: " meses"))
										: ""));
			}

			Adicional_paciente adicional_paciente = new Adicional_paciente();
			adicional_paciente.setCodigo_empresa(zkWindow.codigo_empresa);
			adicional_paciente.setCodigo_sucursal(zkWindow.codigo_sucursal);
			adicional_paciente.setNro_identificacion(registro
					.getNro_identificacion());
			adicional_paciente = zkWindow.getServiceLocator()
					.getServicio(GeneralExtraService.class)
					.consultar(adicional_paciente);

			Barrio barrio = new Barrio();
			barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente
					.getCodigo_barrio() : "");
			barrio = zkWindow.getServiceLocator().getBarrioService()
					.consultar(barrio);
			mcTbxBarrio.setValue(barrio != null ? ("("
					+ barrio.getCodigo_barrio() + ") " + barrio.getBarrio())
					: "");
			// codigo_barrio = barrio.getCodigo_barrio();

			if (barrio != null) {
				Localidad localidad = new Localidad();
				localidad.setCodigo_localidad(barrio.getCodigo_localidad());
				localidad = zkWindow.getServiceLocator()
						.getServicio(GeneralExtraService.class)
						.consultar(localidad);

				mcTbxLocalidad.setValue(localidad != null ? ("("
						+ localidad.getCodigo_localidad() + ") " + localidad
						.getLocalidad()) : barrio.getCodigo_localidad());
				// codigo_localidad = localidad.getCodigo_localidad();

			}

			Centro_barrio centro_barrio = new Centro_barrio();
			centro_barrio
					.setCodigo_barrio(adicional_paciente != null ? adicional_paciente
							.getCodigo_barrio() : "");
			centro_barrio = zkWindow.getServiceLocator()
					.getServicio(GeneralExtraService.class)
					.consultar(centro_barrio);

			if (centro_barrio != null) {
				codigo_centro = centro_barrio.getCodigo_centro();
				Centro_atencion centro = new Centro_atencion();
				centro.setCodigo_empresa(admision.getCodigo_empresa());
				centro.setCodigo_sucursal(admision.getCodigo_sucursal());
				centro.setCodigo_centro(centro_barrio.getCodigo_centro());
				centro = zkWindow.getServiceLocator()
						.getCentro_atencionService().consultar(centro);

				mcTbxCentro_atencion.setValue(centro != null ? (centro
						.getNombre_centro() + " " + centro.getCodigo_centro())
						: "");
			}

			if (informacionPacienteIMG != null) {
				informacionPacienteIMG.ejecutarProceso();
			}

			if (anios.intValue() >= 100) {
				MensajesUtil.notificarAlerta(
						"Observar la edad de este paciente...", mcTbxEdad);
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", zkWindow);
		}
	}

	public void setFecha_inicio(Date fecha) {
		mcDtbxFecha_inicial.setValue(fecha);
		this.fecha_inicial = new Timestamp(fecha.getTime());
	}

	public void setFecha_cierre(boolean mostrar, Date fecha) {
		mcLbFecha_cierre.setVisible(mostrar);
		mcDtbxFecha_cierre.setVisible(mostrar);
		mcDtbxFecha_cierre.setValue(fecha);
		this.fecha_cierre = new Timestamp(fecha.getTime());

	}

	public Long getCodigo_historia() {
		return mcTbxCodigo_historia.getValue();
	}

	public String getCodigo_historiaFac() {
		return mcTbxCodigo_historiaFac.getValue();
	}

	public Timestamp getFecha_inicial() {
		return fecha_inicial;
	}

	public String getCodigo_centro() {
		return codigo_centro;
	}

	public Integer getEdadAnios() {
		return mcIbxAnios.getValue();
	}

	public String getNro_identificacion() {
		return mcTbxCodigo_historiaFac.getValue();
	}

	public void setCodigo_historia(Long codigo_historia) {
		mcTbxCodigo_historia.setValue(codigo_historia);
	}

	public void setCodigo_historiaFac(String codigo_historiaFac) {
		mcTbxCodigo_historiaFac.setValue(codigo_historiaFac);
	}

	public void validarInformacionPaciente() throws Exception {
		// FormularioUtil.validarCamposObligatorios(mcLbxRaza,
		// mcTbxDireccion,mcDbxTelefono, mcLbxOcupacion, mcLbxEducativo);

		XulElement[] componente1 = { mcTbxDireccion, mcDbxTelefono,
				mcLbxOcupacion, mcLbxEducativo };
		XulElement[] componente2 = { mcTbxDireccion, mcLbxOcupacion,
				mcLbxEducativo };

		if (chkAplica_tel.isChecked()) {
			FormularioUtil.validarCamposObligatorios(componente1);
		} else {
			FormularioUtil.validarCamposObligatorios(componente2);
		}

		Paciente paciente = admision.getPaciente();
		paciente.setNro_identificacion(admision.getNro_identificacion());
		paciente.setCodigo_empresa(zkWindow.codigo_empresa);
		paciente.setCodigo_sucursal(zkWindow.codigo_sucursal);
		paciente = zkWindow.getServiceLocator().getPacienteService()
				.consultar(paciente);

		// paciente.setPertenencia_etnica(mcLbxRaza.getSelectedItem().getValue().toString());
		paciente.setDireccion(mcTbxDireccion.getValue());
		paciente.setTel_res(chkAplica_tel.isChecked() ? mcDbxTelefono.getText()
				: "");
		paciente.setCodigo_ocupacion(mcLbxOcupacion.getSelectedItem()
				.getValue().toString());
		paciente.setCodigo_educativo(mcLbxEducativo.getSelectedItem()
				.getValue().toString());
		zkWindow.getServiceLocator().getPacienteService()
				.actualizar(paciente, false);
		admision.setPaciente(paciente);
	}

	public void onCheckAplicaTel(Doublebox dbxTel) {
		if (!chkAplica_tel.isChecked()) {
			dbxTel.setDisabled(true);
		} else {
			dbxTel.setDisabled(false);
		}
	}

	public void complementarInformacion(Map<String, Object> map) {
		map.put("paciente_sexo", mcTbxSexo.getValue());
		map.put("edad", mcTbxEdad.getValue());
		map.put("estado_civil", mcTbxEstado_civil.getValue());
		map.put("pert_etnica",
				MapeadorDeComponentesUtil.getValorListbox(mcLbxRaza));
		map.put("municipios", mcTbxLugar_ncto.getValue());
		map.put("barrio", mcTbxBarrio.getValue());
		map.put("paciente_direccion", mcTbxDireccion.getValue());
		map.put("localidad", mcTbxLocalidad.getValue());
		map.put("paciente_tel_res",
				mcDbxTelefono.getValue() != null
						&& mcDbxTelefono.getValue().doubleValue() > 0 ? mcDbxTelefono
						.getValue() + ""
						: "");
		map.put("ocupacion",
				MapeadorDeComponentesUtil.getValorListbox(mcLbxOcupacion));
		map.put("nivel_educativo",
				MapeadorDeComponentesUtil.getValorListbox(mcLbxEducativo));
		map.put("regimen", mcTbxRegimen.getValue());
		map.put("administradora", mcTbxAseguradora.getValue());
	}

	public void setCodigo_historia_aux(String codigo_historia_aux) {
		this.codigo_historia_aux = codigo_historia_aux;
	}

	public String getCodigo_historia_aux() {
		return codigo_historia_aux;
	}

}
