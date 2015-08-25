package healthmanager.controller.complemento;

import healthmanager.controller.ZKWindow;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IConstantes;
import com.framework.constantes.ITiposServicio;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IAccionEvento;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro.CargaRapidaInformacionUltimoRegistro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro.ETIPO_SERVICIO;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class ConsultasAction extends ZKWindow {

	// Servicios
	private GeneralExtraService generalExtraService;

	// Parametros
	private String via_ingreso;
	// private Prestadores prestadores;
//	private Date fecha;
	private Paciente paciente;

	private CargaRapidaInformacionUltimoRegistro cargaRapidaInformacionUltimoRegistro;

	// variables

	// componentes
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_prestador;
	@View
	private Textbox tbxNomMedico;
	@View
	private Toolbarbutton btnLimpiarPrestador;
	@View
	private Listbox lbxNro_ingreso;
	@View
	private Textbox tbxAseguradora;
	@View
	private Datebox dtbxFecha_consulta;
	@View
	private Textbox tbxNumero_autorizacion;
	@View
	private Listbox lbxFinalidad_consulta;
	@View
	private Listbox lbxCausa_externa;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_diagnostico_principal;
	@View
	private Textbox tbxNomDxpp;
	@View
	private Toolbarbutton btnLimpiarDxpp;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_diagnostico_rel1;
	@View
	private Textbox tbxNomDxr1;
	@View
	private Toolbarbutton btnLimpiarDxr1;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_diagnostico_rel2;
	@View
	private Textbox tbxNomDxr2;

	@View
	private Toolbarbutton btnLimpiarDxr2;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_diagnostico_rel3;
	@View
	private Textbox tbxNomDxr3;
	@View
	private Toolbarbutton btnLimpiarDxr3;
	@View
	private Doublebox dbxValor_cuota;
	@View
	private Listbox lbxTipo_diagnostico;

	private Administradora administradora;

	private Object datos_consulta;

	private ServiciosFacturacionMacro COMPONENTE_MACRO;

	@Override
	public void init() throws Exception {
		listarCombos();
		parametrizaBandbox();
		cargarDatos();
	}

	private void cargarDatos() {
		habilitarDxRelacionados(false);

		if (administradora != null) {
			tbxAseguradora.setValue(administradora.getCodigo() + " "
					+ administradora.getNombre());
		}
		// inicializamos prestador si existe
		// if (prestadores != null) {
		// tbxCodigo_prestador.seleccionarRegistro(prestadores,
		// prestadores.getNro_identificacion(),
		// prestadores.getNombres());
		// }
		dtbxFecha_consulta.setValue(cargaRapidaInformacionUltimoRegistro.getFecha()); 
		tbxCodigo_prestador.setReadonly(false);
		dtbxFecha_consulta.setFocus(true);

		Utilidades.aplicarOnOk(dtbxFecha_consulta, tbxNumero_autorizacion);
		Utilidades.aplicarOnOk(tbxNumero_autorizacion, lbxFinalidad_consulta);
		Utilidades.aplicarOnOk(lbxFinalidad_consulta, lbxCausa_externa);
		Utilidades.aplicarOnOk(lbxCausa_externa,
				tbxCodigo_diagnostico_principal);
		Utilidades.aplicarOnOk(dbxValor_cuota, lbxTipo_diagnostico);
	}

	@Override
	public void params(Map<String, Object> map) {
		via_ingreso = (String) map.get(ITiposServicio.PARAM_VIA_INGRESO);
		// prestadores = (Prestadores) map.get(ITiposServicio.PARAM_PRESTADOR);
		administradora = (Administradora) map
				.get(ITiposServicio.PARAM_ADMINISTRADORA);
//		fecha = (Date) map.get(ITiposServicio.PARAM_FECHA);
		datos_consulta = map.get(ITiposServicio.PARAM_RIPS);
		paciente = (Paciente) map.get(ITiposServicio.PARAM_PACIENTES);
		cargaRapidaInformacionUltimoRegistro = (CargaRapidaInformacionUltimoRegistro) map.get(ITiposServicio.PARAM_DIAGNOSTICO);
		COMPONENTE_MACRO = (ServiciosFacturacionMacro) map
				.get("COMPONENTE_MACRO");
	}

	@Override
	public void _despuesIniciar() {
		Res.cargarAutomatica(dtbxFecha_consulta, datos_consulta,
				"fecha_consulta", new IAccionEvento() {
					@Override
					public void accion() {
						if(dtbxFecha_consulta.getValue() != null){
							cargaRapidaInformacionUltimoRegistro
									.setFecha(new Timestamp(dtbxFecha_consulta
											.getValue().getTime())); 
						}else{
							cargaRapidaInformacionUltimoRegistro.setFecha(null);
						}
					}
				});
		Res.cargarAutomatica(tbxNumero_autorizacion, datos_consulta,
				"numero_autorizacion", new IAccionEvento() {
					@Override
					public void accion() {
						cargaRapidaInformacionUltimoRegistro
								.setNro_autorizacion(tbxNumero_autorizacion
										.getValue()); 
					}
				});
		Res.cargarAutomatica(lbxFinalidad_consulta, datos_consulta,
				"finalidad_consulta");
		Res.cargarAutomatica(lbxCausa_externa, datos_consulta, "causa_externa");
		Res.cargarAutomatica(lbxTipo_diagnostico, datos_consulta,
				"tipo_diagnostico");
	}

	private void parametrizaBandbox() {
		inicializarBandboxCie(tbxCodigo_diagnostico_principal, tbxNomDxpp,
				btnLimpiarDxpp, true, tbxCodigo_diagnostico_rel1,
				"codigo_diagnostico_principal");
		inicializarBandboxCie(tbxCodigo_diagnostico_rel1, tbxNomDxr1,
				btnLimpiarDxr1, false, tbxCodigo_diagnostico_rel2,
				"codigo_diagnostico1");
		inicializarBandboxCie(tbxCodigo_diagnostico_rel2, tbxNomDxr2,
				btnLimpiarDxr2, false, tbxCodigo_diagnostico_rel3,
				"codigo_diagnostico2");
		inicializarBandboxCie(tbxCodigo_diagnostico_rel3, tbxNomDxr3,
				btnLimpiarDxr3, false, dbxValor_cuota, "codigo_diagnostico3");
		// parametrizarBandboxPrestador();
	}

	public void listarCombos() {
		Utilidades.listarElementoListbox(lbxCausa_externa, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_diagnostico, false,
				getServiceLocator());
		listarFinalidadConsulta();
		Utilidades.setValueFrom(lbxCausa_externa,
				IConstantes.CAUSA_EXTERNA_ENFERMEDAD_GENERAL);
		inicializarNroIngreso();
	}

	private void listarFinalidadConsulta() {
		List<Elemento> elementos = getServiceLocator().getElementoService()
				.listar("finalidad_cons");
		lbxFinalidad_consulta.getChildren().clear();
		lbxFinalidad_consulta.appendItem("-- seleccione --", "");
		for (Elemento elemento : elementos) {
			Listitem listitem = new Listitem(elemento.getDescripcion(),
					elemento.getCodigo());
			listitem.setVisible(false);
			lbxFinalidad_consulta.appendChild(listitem);
		}
		validarFinalidadConsulta(lbxFinalidad_consulta);
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
					Map<String,Object> map = new HashMap();
					map.put("nombre_entidad", registro.getNombre());
					map.put("limite_inferior", registro.getLimite_inferior());
					map.put("limite_superior", registro.getLimite_superior());
					map.put("sexo_entidad", registro.getSexo());

					String fecha_nacimiento = new SimpleDateFormat("dd/MM/yyyy")
							.format(paciente.getFecha_nacimiento());

					map.put("fecha_nac", fecha_nacimiento);
					map.put("sexo_pct", paciente.getSexo());
					Map<String,Object> result = Utilidades.validarInformacionLimiteSexo(
							"Diagnostico", registro.getCodigo(),
							registro.getLimite_inferior(),
							registro.getLimite_superior(), registro.getSexo(),
							fecha_nacimiento, paciente.getSexo());
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
					Res.setValor(datos_consulta, campo, bandbox.getValue());
					basedComponent.setFocus(true);

					// colocamos en cola el ultimo diagnostico seleccionado
					if (bandbox.equals(tbxCodigo_diagnostico_principal)) {
						cargaRapidaInformacionUltimoRegistro.setDiagnostico_principal(bandbox.getValue());
					} else if (bandbox.equals(tbxCodigo_diagnostico_rel1)) {
						cargaRapidaInformacionUltimoRegistro.setDiagnostico_relacionado(bandbox.getValue());
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
				return generalExtraService.listar(Cie.class, parametros);
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				if (principal) {
					habilitarDxRelacionados(false);
				}
				Res.setValor(datos_consulta, campo, "");
			}
		};
		/* inyectamos el mismo evento */
		bandboxRegistrosMacro.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		if (principal) {
			bandboxRegistrosMacro.setReadonly(false);
		}
		// inicializamos componente
		Object object_valor = Res.getValor(datos_consulta, campo);
		if (object_valor != null && !object_valor.toString().isEmpty()) {
			Cie cie = new Cie();
			cie.setCodigo(object_valor.toString());
			cie = generalExtraService.consultar(
					cie);
			bandboxRegistrosMacro.seleccionarRegistro(cie, cie.getCodigo(),
					cie.getNombre());
			if (principal) {
				habilitarDxRelacionados(true);
			}
		}
	}

	private void habilitarDxRelacionados(boolean habilitar) {
		tbxCodigo_diagnostico_rel1.setReadonly(!habilitar);
		tbxCodigo_diagnostico_rel3.setReadonly(!habilitar);
		tbxCodigo_diagnostico_rel2.setReadonly(!habilitar);
		tbxCodigo_diagnostico_rel1.setButtonVisible(habilitar);
		tbxCodigo_diagnostico_rel2.setButtonVisible(habilitar);
		tbxCodigo_diagnostico_rel3.setButtonVisible(habilitar);
	}

	/* metodos de complemento */
	private void validarFinalidadConsulta(Listbox listbox_finalidad) {
		for (int i = 0; i < listbox_finalidad.getItemCount(); i++) {
			Listitem listitem = listbox_finalidad.getItemAtIndex(i);
			String codigo = listitem.getValue().toString();
			if (via_ingreso.equalsIgnoreCase(IVias_ingreso.HC_MENOR_2_MESES)
					|| via_ingreso
							.equalsIgnoreCase(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)
					|| via_ingreso
							.equalsIgnoreCase(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)
					|| via_ingreso
							.equalsIgnoreCase(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)) {
				if (codigo.equals("04")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			} else if (via_ingreso
					.equalsIgnoreCase(IVias_ingreso.HIPERTENSO_DIABETICO)) {
				if (codigo.equals("01")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			} else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.ADULTO_MAYOR)) {
				if (codigo.equals("07")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			} else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.ODONTOLOGIA2)
					|| via_ingreso.equalsIgnoreCase(IVias_ingreso.ODONTOLOGIA)) {
				if (codigo.equals("01")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}

			} else if (via_ingreso
					.equalsIgnoreCase(IVias_ingreso.CONSULTA_EXTERNA)
					|| via_ingreso
							.equalsIgnoreCase(IVias_ingreso.HC_AIEPI_2_MESES)
					|| via_ingreso
							.equalsIgnoreCase(IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS)
					|| via_ingreso
							.equalsIgnoreCase(IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
				if (codigo.equals("01")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
				}
			} else if (via_ingreso
					.equalsIgnoreCase(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO)) {
				if (codigo.equals("02")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			} else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.URGENCIA)) {
				if (codigo.equals("01")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}

			} else if (via_ingreso
					.equalsIgnoreCase(IVias_ingreso.HOSPITALIZACIONES)) {
				if (codigo.equals("01")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			}

			else if (via_ingreso
					.equalsIgnoreCase(IVias_ingreso.ALTERACION_JOVEN)) {
				if (codigo.equals("05")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			} else if (via_ingreso
					.equalsIgnoreCase(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)) {
				if (codigo.equals("06")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			} else if (via_ingreso
					.equalsIgnoreCase(IVias_ingreso.HIPERTENSO_DIABETICO)) {
				if (codigo.equals("01")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			} else if (via_ingreso
					.equalsIgnoreCase(IVias_ingreso.PLANIFICACION_FAMILIAR)) {
				if (codigo.equals("03")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			} else {
				if (codigo.equals("01")) {
					listitem.setVisible(true);
					listitem.setSelected(true);
					// onSeleccionarFinalidadConsulta();
				}
			}
		}
	}

	// private void parametrizarBandboxPrestador() {
	// tbxCodigo_prestador.inicializar(tbxNomMedico, btnLimpiarPrestador);
	// tbxCodigo_prestador
	// .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Map<String, Object>>() {
	//
	// @Override
	// public void renderListitem(Listitem listitem,
	// Map<String, Object> registro) {
	//
	// // Extraemos valores
	// String nro_identificacion = (String) registro
	// .get("nro_identificacion");
	// String nombres = (String) registro.get("nombres");
	// String apellidos = (String) registro.get("apellidos");
	// Integer citas_del_dia = (Integer) registro
	// .get("citas_del_dia");
	// Integer citas_asignadas = (Integer) registro
	// .get("citas_asignadas");
	// Integer citas_pendientes = (Integer) registro
	// .get("citas_pendientes");
	// String tipo_prestador = (String) registro
	// .get("tipo_prestador");
	//
	// // Mostramos valores en vista
	//
	// Listcell listcell = new Listcell();
	// listcell.appendChild(new Label(nro_identificacion));
	// listitem.appendChild(listcell);
	//
	// listcell = new Listcell();
	// listcell.appendChild(new Label(nombres + " "
	// + apellidos));
	// listitem.appendChild(listcell);
	//
	// String nombre_medico = tipo_prestador.equals("01") ? "médico"
	// : "ENFERMERA";
	// if (tipo_prestador.equals("03")) {
	// nombre_medico = "VACUNADOR";
	// }
	// if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)
	// || via_ingreso
	// .equals(IVias_ingreso.ODONTOLOGIA2)
	// || via_ingreso
	// .equals(IVias_ingreso.URGENCIA_ODONTOLOGICO)
	// || via_ingreso.equals(IVias_ingreso.SALUD_ORAL)) {
	// nombre_medico = "ODONTÓLOGO";
	// }
	//
	// listcell = new Listcell();
	// listcell.appendChild(new Label(nombre_medico));
	// listitem.appendChild(listcell);
	//
	// listcell = new Listcell();
	// listcell.appendChild(new Label(
	// citas_del_dia != null ? citas_del_dia + ""
	// : "0"));
	// listitem.appendChild(listcell);
	//
	// listcell = new Listcell();
	// listcell.appendChild(new Label(
	// citas_asignadas != null ? citas_asignadas + ""
	// : "0"));
	// listitem.appendChild(listcell);
	//
	// listcell = new Listcell();
	// listcell.appendChild(new Label(
	// citas_pendientes != null ? citas_pendientes
	// + "" : "0"));
	// listitem.appendChild(listcell);
	// }
	//
	// @Override
	// public List<Map<String, Object>> listarRegistros(
	// String valorBusqueda, Map<String, Object> parametros) {
	// if (!via_ingreso.isEmpty()) {
	// parametros.put("paramTodo", "");
	// parametros.put("value", "%"
	// + valorBusqueda.toUpperCase().trim() + "%");
	// parametros.put("codigo_empresa",
	// sucursal.getCodigo_empresa());
	// parametros.put("codigo_sucursal",
	// sucursal.getCodigo_sucursal());
	//
	// parametros.put("rol", getRolViaIngreso());
	// // Esta validacion se va a utilizar cuando sea
	// // vacunacion
	// if (!via_ingreso
	// .equals(IVias_ingreso.VIA_VACUNACION)) {
	// Centro_atencion centro_atencion = (Centro_atencion)

	// parametros
	// .put("codigo_centro_dh",
	// centro_atencion != null ? centro_atencion
	// .getCodigo_centro()
	// : IConstantes.CENTRO_ATENCION_CUALQUIERA);
	// String tipo_prestador = habilitarFiltroEnfermera();
	// if (!tipo_prestador.equals("00")) {
	// parametros.put("tipo_prestador",
	// tipo_prestador);
	// }
	// }
	// getServiceLocator().getPrestadoresService()
	// .setLimit("limit 25 offset 0");
	//
	// // //log.info(">>>>>>>>>>>>>" + parametros);
	// return getServiceLocator().getPrestadoresService()
	// .listarPrestadoresPorRolCentro(parametros);
	// } else {
	// return new ArrayList<Map<String, Object>>();
	// }
	// }
	//
	// @Override
	// public boolean seleccionarRegistro(Bandbox bandbox,
	// Textbox textboxInformacion,
	// Map<String, Object> registro) {
	//
	// String nro_identificacion = (String) registro
	// .get("nro_identificacion");
	// String nombre = (registro.get("nombres") + " " + registro
	// .get("apellidos"));
	//
	// bandbox.setValue(nro_identificacion);
	// textboxInformacion.setValue(nombre);
	// dtbxFecha_consulta.setFocus(true);
	// return true;
	// }
	//
	// @Override
	// public void limpiarSeleccion(Bandbox bandbox) {
	// }
	//
	// });
	// }

	// private List<Via_ingreso_rol> getRolViaIngreso() {
	// Map<String, Object> param = new HashMap<String, Object>();
	// param.put("codigo_via_ingreso", via_ingreso);
	// return getServiceLocator().getServicio(Via_ingreso_rolService.class)
	// .listar(param);
	// }
	//
	// public String habilitarFiltroEnfermera() {
	// return Utilidades.habilitarFiltroEnfermera(via_ingreso, "",
	// getParametros_empresa());
	// }

	private void inicializarNroIngreso() {
		lbxNro_ingreso.getChildren().clear();
		lbxNro_ingreso.appendChild(new Listitem("  --  ", ""));
		lbxNro_ingreso.setSelectedIndex(0);
	}
	
	@Override
	public void detach() {
		if(datos_consulta instanceof Datos_consulta){
			Datos_consulta consulta = (Datos_consulta) datos_consulta;
			consulta.setNumero_autorizacion(tbxNumero_autorizacion.getValue());
			consulta.setFecha_consulta(dtbxFecha_consulta.getValue() != null ? new Timestamp(
					dtbxFecha_consulta.getValue().getTime()) : null); 
			cargaRapidaInformacionUltimoRegistro.setNro_autorizacion(consulta.getNumero_autorizacion());
			cargaRapidaInformacionUltimoRegistro.setFecha(consulta.getFecha_consulta());  
		}
		super.detach();
	}

	public void onClickAgregarOtroServicio() {
		if (COMPONENTE_MACRO != null) {
			//log.info("ejecutando metodo @onClickAgregarOtroServicio()");
			COMPONENTE_MACRO.agregarServicio(ETIPO_SERVICIO.SERVICIO, true);
			this.onClose();
		}
	}

}
