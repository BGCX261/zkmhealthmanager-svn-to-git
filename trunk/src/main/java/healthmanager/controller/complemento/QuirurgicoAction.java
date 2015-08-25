package healthmanager.controller.complemento;

import healthmanager.controller.ZKWindow;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.service.PrestadoresService;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.ITiposServicio;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro.CargaRapidaInformacionUltimoRegistro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class QuirurgicoAction extends ZKWindow {

	private GeneralExtraService generalExtraService;

	// Parametros
	// private String via_ingreso;
	// private Prestadores prestadores;
	// private Date fecha;
	private Paciente paciente;
	private Prestadores prestadores;
	// private Administradora administradora;
	private Datos_procedimiento datos_procedimiento_quirurgico;
	private Admision admision;

	private Object procedimiento;// procedimiento SOAT o ISS

	private CargaRapidaInformacionUltimoRegistro cargaRapidaInformacionUltimoRegistro;

	private String tipo_init;

	@View
	private Radiogroup rdbTipo_intervencion;
	@View
	private Groupbox groupboxForm;
	@View
	private Radio radioTipo_intervencion1;
	@View
	private Datebox dtbxFecha_procedimiento;
	@View
	private Listbox lbxAmbito_procedimiento;
	@View
	private Radio radioTipo_intervencion2;
	@View
	private Radio radioTipo_intervencion3;
	@View
	private Listbox lbxForma_realizacion;
	@View
	private Listbox lbxFinalidad_procedimiento;
	@View
	private Radio radioTipo_intervencion5;
	@View
	private Radio radioTipo_intervencion4;
	@View
	private Listbox lbxPersonal_atiende;
	@View
	private Textbox tbxNumero_autorizacion;
	@View
	private Checkbox chbIncruento;
	@View
	private Checkbox chbCobra_cirujano;
	@View
	private Doublebox dbxValor_cirujano;
	@View
	private Doublebox dbxAuxValor_cirujano;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_prestador;
	@View
	private Textbox tbxNomMedico;
	@View
	private Toolbarbutton imgQuitar_med;
	@View
	private Checkbox chbCobra_anestesiologo;
	@View
	private Doublebox dbxValor_anestesiologo;
	@View
	private Doublebox dbxAuxValor_anestesiologo;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_anestesiologo;
	@View
	private Textbox tbxNomAnestesiologo;
	@View
	private Toolbarbutton imgQuitar_ana;
	@View
	private Checkbox chbCobra_ayudante;
	@View
	private Doublebox dbxValor_ayudante;
	@View
	private Doublebox dbxAuxValor_ayudante;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_ayudante;
	@View
	private Textbox tbxNomAyudante;
	@View
	private Toolbarbutton imgQuitar_ayu;
	@View
	private Checkbox chbCobra_sala;
	@View
	private Doublebox dbxValor_sala;
	@View
	private Doublebox dbxAuxValor_sala;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_diagnostico_principal;
	@View
	private Textbox tbxNomDxpp;
	@View
	private Toolbarbutton btnLimpiarDxpp;
	@View
	private Checkbox chbCobra_materiales;
	@View
	private Doublebox dbxValor_materiales;
	@View
	private Doublebox dbxAuxValor_materiales;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_diagnostico_relacionado;
	@View
	private Textbox tbxNomDx1;
	@View
	private Toolbarbutton imgQuitar_dx1;
	@View
	private Checkbox chbCobra_perfusionista;
	@View
	private Doublebox dbxValor_perfusionista;
	@View
	private Doublebox dbxAuxValor_perfusionista;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxComplicacion;
	@View
	private Textbox tbxNomDx2;
	@View
	private Toolbarbutton imgQuitar_dx2;
	@View
	private Doublebox dbxValor_neto2;
	@View
	private Doublebox dbxAuxValor_neto2;
	@View
	private Listbox lbxTipo_sala;
	@View
	private Checkbox chbCirugia_conjunto;

	private Doublebox double_box_valor_total;

	@Override
	public void init() throws Exception {
		tipo_init = "1";
		listarCombos();
		activarSeccionPcd(false);
		activarSeccionPcd(true);
		parametrizaBandbox();
		cargarDatos();
	}

	private void parametrizaBandbox() {
		parametrizarBandboxPrestador(tbxCodigo_prestador, tbxNomMedico,
				imgQuitar_med, "codigo_prestador");
		parametrizarBandboxPrestador(tbxCodigo_anestesiologo,
				tbxNomAnestesiologo, imgQuitar_ana, "codigo_anestesiologo");
		parametrizarBandboxPrestador(tbxCodigo_ayudante, tbxNomAyudante,
				imgQuitar_ayu, "codigo_ayudante");
		tbxCodigo_prestador.setReadonly(false);
		if (prestadores != null) {
			tbxCodigo_prestador.seleccionarRegistro(prestadores,
					prestadores.getNro_identificacion(),
					prestadores.getNombres());
		}

		// Diagnosticos
		inicializarBandboxCie(tbxCodigo_diagnostico_principal, tbxNomDxpp,
				btnLimpiarDxpp, true, tbxCodigo_diagnostico_relacionado,
				"codigo_diagnostico_principal");
		inicializarBandboxCie(tbxCodigo_diagnostico_relacionado, tbxNomDx1,
				imgQuitar_dx1, false, tbxComplicacion,
				"codigo_diagnostico_relacionado");
		inicializarBandboxCie(tbxComplicacion, tbxNomDx2, imgQuitar_dx2, false,
				lbxTipo_sala, "complicacion");
	}

	@Override
	public void params(Map<String, Object> map) {
		// via_ingreso = (String) map.get(ITiposServicio.PARAM_VIA_INGRESO);
		prestadores = (Prestadores) map.get(ITiposServicio.PARAM_PRESTADOR);
		// administradora = (Administradora) map
		// .get(ITiposServicio.PARAM_ADMINISTRADORA);
		// fecha = (Date) map.get(ITiposServicio.PARAM_FECHA);
		datos_procedimiento_quirurgico = (Datos_procedimiento) map
				.get(ITiposServicio.PARAM_RIPS);
		paciente = (Paciente) map.get(ITiposServicio.PARAM_PACIENTES);
		cargaRapidaInformacionUltimoRegistro = (CargaRapidaInformacionUltimoRegistro) map
				.get(ITiposServicio.PARAM_DIAGNOSTICO);
		procedimiento = map.get(ITiposServicio.PARAM_PROCEDIMIENTO);
		admision = (Admision) map.get(ITiposServicio.PARAM_ADMISION);
		double_box_valor_total = (Doublebox) map
				.get(ITiposServicio.PARAM_VALOR_TOTAL);
	}

	public void listarCombos() {
		Utilidades.listarElementoListbox(lbxAmbito_procedimiento, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxFinalidad_procedimiento, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxPersonal_atiende, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxForma_realizacion, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_sala, false,
				getServiceLocator());
	}

	@Override
	public void _despuesIniciar() {
		Res.cargarAutomatica(dtbxFecha_procedimiento,
				datos_procedimiento_quirurgico, "fecha_procedimiento");
		Res.cargarAutomatica(tbxNumero_autorizacion,
				datos_procedimiento_quirurgico, "numero_autorizacion");
		Res.cargarAutomatica(lbxAmbito_procedimiento,
				datos_procedimiento_quirurgico, "ambito_procedimiento");
		Res.cargarAutomatica(lbxFinalidad_procedimiento,
				datos_procedimiento_quirurgico, "finalidad_procedimiento");
		Res.cargarAutomatica(lbxPersonal_atiende,
				datos_procedimiento_quirurgico, "personal_atiende");
		Res.cargarAutomatica(lbxForma_realizacion,
				datos_procedimiento_quirurgico, "forma_realizacion");
		Res.cargarAutomatica(chbCirugia_conjunto,
				datos_procedimiento_quirurgico, "cirugia_conjunto");
		Res.cargarAutomatica(chbIncruento, datos_procedimiento_quirurgico,
				"incruento");
		Res.cargarAutomatica(lbxTipo_sala, datos_procedimiento_quirurgico,
				"tipo_sala");

		Res.cargarAutomatica(chbCobra_cirujano, datos_procedimiento_quirurgico,
				"cobra_cirujano");
		Res.cargarAutomatica(chbCobra_anestesiologo,
				datos_procedimiento_quirurgico, "cobra_anestesiologo");
		Res.cargarAutomatica(chbCobra_ayudante, datos_procedimiento_quirurgico,
				"cobra_ayudante");
		Res.cargarAutomatica(chbCobra_sala, datos_procedimiento_quirurgico,
				"cobra_sala");
		Res.cargarAutomatica(chbCobra_materiales,
				datos_procedimiento_quirurgico, "cobra_materiales");
		Res.cargarAutomatica(chbCobra_perfusionista,
				datos_procedimiento_quirurgico, "cobra_perfusionista");

		Res.cargarAutomatica(dbxValor_anestesiologo,
				datos_procedimiento_quirurgico, "valor_anestesiologo");
		Res.cargarAutomatica(dbxValor_cirujano, datos_procedimiento_quirurgico,
				"valor_cirujano");
		Res.cargarAutomatica(dbxValor_ayudante, datos_procedimiento_quirurgico,
				"valor_ayudante");
		Res.cargarAutomatica(dbxValor_materiales,
				datos_procedimiento_quirurgico, "valor_materiales");
		Res.cargarAutomatica(dbxValor_perfusionista,
				datos_procedimiento_quirurgico, "valor_perfusionista");
		Res.cargarAutomatica(dbxValor_sala, datos_procedimiento_quirurgico,
				"valor_sala");
		calcularSubtotal();
	}

	private void parametrizarBandboxPrestador(
			BandboxRegistrosMacro bandboxRegistrosMacro, Textbox textbox,
			Toolbarbutton toolbarbutton, final String campo) {
		bandboxRegistrosMacro.inicializar(textbox, toolbarbutton);
		bandboxRegistrosMacro
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Map<String, Object>>() {

					@Override
					public void renderListitem(Listitem listitem,
							Map<String, Object> dato) {

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label((String) dato
								.get(Utilidades.CODIGO_USUARIO)));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label((String) dato
								.get(Utilidades.NOMBRES)));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label((String) dato
								.get(Utilidades.APELLIDOS)));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(Utilidades
								.getTipoMedico((String) dato
										.get(Utilidades.TIPO_MEDICO))));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Map<String, Object>> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						Map<String, Object> parameters = new HashMap<String, Object>();
						parameters.put("paramTodo", "");
						parameters.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						parameters.put("codigo_empresa",
								sucursal.getCodigo_empresa());
						parameters.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parameters.put("rol_medico", "_todos");

						getServiceLocator().getUsuariosService().setLimit(
								"limit 25 offset 0");

						return getServiceLocator().getUsuariosService()
								.getUsuarioByRol(parameters);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion,
							Map<String, Object> registro) {

						String nro_identificacion = (String) registro
								.get(Utilidades.CODIGO_USUARIO);
						String nombre = (registro.get("nombres") + " " + registro
								.get("apellidos"));

						Res.setValor(datos_procedimiento_quirurgico, campo,
								nro_identificacion);

						bandbox.setValue(nro_identificacion);
						textboxInformacion.setValue(nombre);
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						Res.setValor(datos_procedimiento_quirurgico, campo, "");
					}

				});
		String nro_identificacion_medico = Res.getValor(
				datos_procedimiento_quirurgico, campo);
		if (nro_identificacion_medico != null
				&& !nro_identificacion_medico.trim().isEmpty()) {
			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(getSucursal().getCodigo_empresa());
			prestadores.setCodigo_sucursal(getSucursal().getCodigo_sucursal());
			prestadores.setNro_identificacion(nro_identificacion_medico);
			prestadores = getServiceLocator().getServicio(
					PrestadoresService.class).consultar(prestadores);
			if (prestadores != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put(Utilidades.CODIGO_USUARIO,
						prestadores.getNro_identificacion());
				map.put("nombres", prestadores.getNombres());
				map.put("apellidos", prestadores.getApellidos());
				bandboxRegistrosMacro.seleccionarRegistro(
						map,
						prestadores.getNro_identificacion(),
						prestadores.getNombres() + " "
								+ prestadores.getApellidos());
			}
		}
	}

	public void activarSeccionPcd(boolean sw) {
		chbCobra_cirujano.setDisabled(!sw);
		chbCobra_ayudante.setDisabled(!sw);
		chbCobra_anestesiologo.setDisabled(!sw);
		chbCobra_sala.setDisabled(!sw);
		chbCobra_materiales.setDisabled(!sw);
		chbCobra_perfusionista.setDisabled(true);

		/*
		 * tbxCodigo_prestador.setDisabled(!sw);
		 * tbxCodigo_ayudante.setDisabled(!sw);
		 * tbxCodigo_anestesiologo.setDisabled(!sw);
		 */

		tbxCodigo_diagnostico_principal.setDisabled(!sw);
		tbxCodigo_diagnostico_relacionado.setDisabled(!sw);
		tbxComplicacion.setDisabled(!sw);

		imgQuitar_med.setVisible(sw);
		imgQuitar_ana.setVisible(sw);
		imgQuitar_ayu.setVisible(sw);

		imgQuitar_dx1.setVisible(sw);
		imgQuitar_dx2.setVisible(sw);

		if (!sw) {
			chbCobra_cirujano.setChecked(false);
			chbCobra_ayudante.setChecked(false);
			chbCobra_anestesiologo.setChecked(false);
			chbCobra_sala.setChecked(false);
			chbCobra_materiales.setChecked(false);
			chbCobra_perfusionista.setChecked(false);

			tbxCodigo_prestador.setDisabled(true);
			tbxCodigo_ayudante.setDisabled(true);
			tbxCodigo_anestesiologo.setDisabled(true);

			imgQuitar_dx1.setVisible(false);
			imgQuitar_dx2.setVisible(false);

			tbxCodigo_prestador.setValue("");
			tbxNomMedico.setValue("");
			tbxCodigo_ayudante.setValue("");
			tbxNomAyudante.setValue("");
			tbxCodigo_anestesiologo.setValue("");
			tbxNomAnestesiologo.setValue("");

			String dx = "";
			String nombre_dx = "";
			if (admision != null) {
				dx = admision.getDiagnostico_ingreso();
				Cie cie = new Cie();
				cie.setCodigo(admision.getDiagnostico_ingreso());
				cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
				nombre_dx = (cie != null ? cie.getNombre() : "");
			}

			tbxCodigo_diagnostico_principal.setValue(dx);
			tbxNomDxpp.setValue(nombre_dx);

			tbxCodigo_diagnostico_relacionado.setValue("");
			tbxNomDx1.setValue("");

			tbxComplicacion.setValue("");
			tbxNomDx2.setValue("");

			dbxValor_cirujano.setValue(0.0);
			dbxValor_anestesiologo.setValue(0.0);
			dbxValor_ayudante.setValue(0.0);
			dbxValor_sala.setValue(0.0);
			dbxValor_materiales.setValue(0.0);
			dbxValor_perfusionista.setValue(0.0);

			dbxAuxValor_cirujano.setValue(0.0);
			dbxAuxValor_anestesiologo.setValue(0.0);
			dbxAuxValor_ayudante.setValue(0.0);
			dbxAuxValor_sala.setValue(0.0);
			dbxAuxValor_materiales.setValue(0.0);
			dbxAuxValor_perfusionista.setValue(0.0);

			dbxValor_neto2.setValue(0.0);
			dbxAuxValor_neto2.setValue(0.0);

		}
	}

	private void inicializarBandboxCie(
			BandboxRegistrosMacro bandboxRegistrosMacro, Textbox textbox,
			Toolbarbutton toolbarbutton, final boolean principal,
			final HtmlBasedComponent basedComponent, final String campo) {
		bandboxRegistrosMacro.inicializar(textbox, toolbarbutton);
		BandboxRegistrosIMG<Cie> bandboxRegistrosIMG = new BandboxRegistrosIMG<Cie>() {
			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Cie registro) {
				try {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("nombre_entidad", registro.getNombre());
					map.put("limite_inferior", registro.getLimite_inferior());
					map.put("limite_superior", registro.getLimite_superior());
					map.put("sexo_entidad", registro.getSexo());

					String fecha_nacimiento = new SimpleDateFormat("dd/MM/yyyy")
							.format(paciente.getFecha_nacimiento());

					map.put("fecha_nac", fecha_nacimiento);
					map.put("sexo_pct", paciente.getSexo());
					Map<String, Object> result = Utilidades
							.validarInformacionLimiteSexo("Diagnostico",
									registro.getCodigo(),
									registro.getLimite_inferior(),
									registro.getLimite_superior(),
									registro.getSexo(), fecha_nacimiento,
									paciente.getSexo());
					if (!((Boolean) result.get("success"))) {
						throw new ValidacionRunTimeException(
								(String) result.get("msg"));
					}
					bandbox.setValue(registro.getCodigo());
					textboxInformacion.setValue(registro.getNombre());
					if (principal) {
						habilitarDxRelacionados(true);
					}
					// Actualuzamos datos
					Res.setValor(datos_procedimiento_quirurgico, campo,
							bandbox.getValue());
					basedComponent.setFocus(true);

					// colocamos en cola el ultimo diagnostico seleccionado
					if (bandbox.equals(tbxCodigo_diagnostico_principal)) {
						cargaRapidaInformacionUltimoRegistro
								.setDiagnostico_principal(bandbox.getValue());
					} else if (bandbox
							.equals(tbxCodigo_diagnostico_relacionado)) {
						cargaRapidaInformacionUltimoRegistro
								.setDiagnostico_relacionado(bandbox.getValue());
					}
				} catch (Exception e) {
					MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
				}
				return true;
			}

			@Override
			public void renderListitem(Listitem listitem, Cie registro) {
				listitem.setValue(registro);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(registro.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getSexo()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getLimite_inferior()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(registro.getLimite_superior()));
				listitem.appendChild(listcell);
			}

			@Override
			public List<Cie> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {
				parametros.put("paramTodo", "");
				parametros.put("value", "%"
						+ valorBusqueda.toUpperCase().trim() + "%");
				parametros.put("limite_paginado", "limit 25 offset 0");
				return generalExtraService.listar(Cie.class,parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				if (principal) {
					habilitarDxRelacionados(false);
				}
				Res.setValor(datos_procedimiento_quirurgico, campo, "");
			}
		};
		/* inyectamos el mismo evento */
		bandboxRegistrosMacro.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		if (principal) {
			bandboxRegistrosMacro.setReadonly(false);
		}
		// inicializamos componente
		Object object_valor = Res.getValor(datos_procedimiento_quirurgico,
				campo);
		if (object_valor != null && !object_valor.toString().isEmpty()) {
			Cie cie = new Cie();
			cie.setCodigo(object_valor.toString());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(
					cie);
			bandboxRegistrosMacro.seleccionarRegistro(cie, cie.getCodigo(),
					cie.getNombre());
		}
	}

	private void habilitarDxRelacionados(boolean habilitar) {
		tbxCodigo_diagnostico_relacionado.setReadonly(!habilitar);
		tbxComplicacion.setReadonly(!habilitar);
		tbxCodigo_diagnostico_relacionado.setButtonVisible(habilitar);
		tbxComplicacion.setButtonVisible(habilitar);
	}

	private void cargarDatos() {
		// habilitarDxRelacionados(false);
		//
		// if (administradora != null) {
		// tbxAseguradora.setValue(administradora.getCodigo() + " "
		// + administradora.getNombre());
		// }
		// // inicializamos prestador si existe
		// // if (prestadores != null) {
		// // tbxCodigo_prestador.seleccionarRegistro(prestadores,
		// // prestadores.getNro_identificacion(),
		// // prestadores.getNombres());
		// // }
		dtbxFecha_procedimiento.setValue(cargaRapidaInformacionUltimoRegistro
				.getFecha());
		// tbxCodigo_prestador.setReadonly(false);
		// dtbxFecha_consulta.setFocus(true);
		//
		// Utilidades.aplicarOnOk(dtbxFecha_consulta, tbxNumero_autorizacion);
		// Utilidades.aplicarOnOk(tbxNumero_autorizacion,
		// lbxFinalidad_consulta);
		// Utilidades.aplicarOnOk(lbxFinalidad_consulta, lbxCausa_externa);
		// Utilidades.aplicarOnOk(lbxCausa_externa,
		// tbxCodigo_diagnostico_principal);
		// Utilidades.aplicarOnOk(dbxValor_cuota, lbxTipo_diagnostico);
	}

	// METODOS DE COMPLEMENTO
	public void calcularValorServicio(String tipo, boolean checked,
			Doublebox doubleboxValor, Doublebox auxValor) {
		try {
			Resolucion resolucion = new Resolucion();
			resolucion.setCodigo_empresa(empresa.getCodigo_empresa());
			resolucion = getServiceLocator().getResolucionService().consultar(
					resolucion);
			String cobra_materiales = (resolucion != null ? resolucion
					.getCobrar_materiales() : "N");

			String manual = IConstantes.TIPO_MANUAL_SOAT;
			String grupo = (String) Res.getValor(procedimiento, "grupo");
			String tipo_grupo = Utilidades.getTipo_grupo_cirugia(tipo, manual);

			if (grupo == null) {
				grupo = "";
			}

			Double uvr = (Double) Res.getValor(procedimiento, "uvr");
			if (uvr == null) {
				uvr = 0d;
			}

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("nro_identificacion",
					paciente.getNro_identificacion());
			parameters.put("nro_ingreso", admision.getNro_ingreso());
			parameters.put("anio", anio.toString());
			parameters.put("manual", manual);
			parameters.put("grupo", grupo);
			parameters.put("tipo_grupo", tipo_grupo);
			parameters.put("cobra", checked ? "S" : "N");
			parameters.put("uvr", uvr != null ? uvr : 0);
			parameters.put("tipo_sala", lbxTipo_sala.getSelectedItem()
					.getValue());
			parameters.put("admision", admision);
			// //log.info("parameters cirug: "+parameters);
			double result[] = getServiceLocator()
					.getDatos_procedimientoService().consultarValorCirugia(
							parameters);

			doubleboxValor.setValue(result[0]);
			auxValor.setValue(result[1]);

			if (tipo.equals("1")) {
				tbxCodigo_prestador.setDisabled(checked ? false : true);
				imgQuitar_med.setVisible(!checked ? false : true);
				if (!checked) {
					tbxCodigo_prestador.limpiarSeleccion(true);
				}
			} else if (tipo.equals("2")) {
				tbxCodigo_anestesiologo.setDisabled(checked ? false : true);
				imgQuitar_ana.setVisible(!checked ? false : true);
				if (!checked) {
					tbxCodigo_anestesiologo.limpiarSeleccion(true);
				}
			} else if (tipo.equals("3")) {
				tbxCodigo_ayudante.setDisabled(checked ? false : true);
				imgQuitar_ayu.setVisible(!checked ? false : true);
				if (!checked) {
					tbxCodigo_ayudante.limpiarSeleccion(true);
				}
			}

			if (manual.equals("SOAT")
					&& tipo.equals("5")
					&& (grupo.equals("20") || grupo.equals("21")
							|| grupo.equals("22") || grupo.equals("22"))
					&& cobra_materiales.equals("S")) {
				dbxValor_materiales.setReadonly(false);
				dbxValor_materiales.focus();
			} else if (manual.equals("ISS01") && tipo.equals("5")
					&& (uvr > 170) && cobra_materiales.equals("S")) {
				dbxValor_materiales.setReadonly(false);
				dbxValor_materiales.focus();
			} else {
				dbxValor_materiales.setReadonly(true);
			}

			calcularSubtotal();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void calcularSubtotal() {
		double total = 0;
		double total_real = 0;

		if (dbxValor_materiales.getValue() == null) {
			MensajesUtil.mensajeAlerta("Alerta !!!",
					"El valor del material es invalido");
			return;
		}

		total = dbxValor_cirujano.getValue()
				+ dbxValor_anestesiologo.getValue()
				+ dbxValor_ayudante.getValue() + dbxValor_sala.getValue()
				+ dbxValor_materiales.getValue()
				+ dbxValor_perfusionista.getValue();
		total_real = dbxAuxValor_cirujano.getValue()
				+ dbxAuxValor_anestesiologo.getValue()
				+ dbxAuxValor_ayudante.getValue() + dbxAuxValor_sala.getValue()
				+ dbxAuxValor_materiales.getValue()
				+ dbxAuxValor_perfusionista.getValue();

		// Actualizamos valores a procedimiento
		datos_procedimiento_quirurgico
				.setValor_anestesiologo(dbxValor_anestesiologo.getValue());
		datos_procedimiento_quirurgico.setValor_cirujano(dbxValor_cirujano
				.getValue());
		datos_procedimiento_quirurgico.setValor_ayudante(dbxValor_ayudante
				.getValue());
		datos_procedimiento_quirurgico.setValor_materiales(dbxValor_materiales
				.getValue());
		datos_procedimiento_quirurgico
				.setValor_perfusionista(dbxValor_perfusionista.getValue());
		datos_procedimiento_quirurgico.setValor_sala(dbxValor_sala.getValue());
		datos_procedimiento_quirurgico.setValor_neto(total);
		datos_procedimiento_quirurgico.setValor_real(total_real);

		// total_real =
		// parseFloat(document.form1.aux_valor_cirujano.value)+parseFloat(document.form1.aux_valor_anestesiologo.value)+parseFloat(document.form1.aux_valor_ayudante.value)+parseFloat(document.form1.aux_valor_sala.value)+aux_valor_materiales+parseFloat(document.form1.aux_valor_perfusionista.value);
		dbxValor_neto2.setValue(total);
		dbxAuxValor_neto2.setValue(total_real);
		if (double_box_valor_total != null) {
			double_box_valor_total.setValue(total);
		}
	}

	public void selectedIncruento(boolean checked) throws Exception {
		chbCirugia_conjunto.setChecked(!checked);
		tipo_init = "1";
		for (int i = 2; i <= 5; i++) {
			if (i != 4) {
				((Radio) getFellow("radioTipo_intervencion" + i))
						.setDisabled(checked);
			}
		}
		if (checked) {
			((Radio) getFellow("radioTipo_intervencion1")).setChecked(true);
		}
	}

	public void selectedCirugia_conjunto(final boolean checked)
			throws Exception {
		Messagebox
				.show("¿Desea realmente cambiar de tipo de intervension ?\nEsta operacion borrará lo realizado ",
						"Confirmacion", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									tipo_init = "3";
									chbCirugia_conjunto.setChecked(!checked);
									for (int i = 2; i <= 5; i++) {
										if (i != 3) {
											((Radio) getFellow("radioTipo_intervencion"
													+ i)).setDisabled(checked);
										}
									}
									if (checked) {
										((Radio) getFellow("radioTipo_intervencion3"))
												.setChecked(true);
									}
									activarSeccionPcd(false);
								} else {
									chbCirugia_conjunto.setChecked(!checked);
									return;
								}
							}
						});

	}

	public void selectedTipo_intervension(String tipo_intervension)
			throws Exception {
		((Radio) getFellow("radioTipo_intervencion" + tipo_init))
				.setChecked(true);
		tipo_init = tipo_intervension;
		if (tipo_intervension.equals("4")) {
			lbxForma_realizacion.setSelectedIndex(0);
		} else if (tipo_intervension.equals("5")) {
			lbxForma_realizacion.setSelectedIndex(3);
		} else {
			lbxForma_realizacion.setSelectedIndex(Integer
					.parseInt(tipo_intervension) - 1);
		}
	}

}
