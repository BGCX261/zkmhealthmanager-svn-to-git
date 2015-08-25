package healthmanager.controller.complemento;

import healthmanager.controller.Facturacion_ripsAction;
import healthmanager.controller.GeneralComposer;
import healthmanager.controller.ZKWindow;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Datos_servicio;
import healthmanager.modelo.bean.Paciente;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.ITiposServicio;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.facturacion.ServiciosFacturacionMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class ComplementacionMultipleAction extends ZKWindow {

	private GeneralExtraService generalExtraService;

	@View
	private Datebox dtbxFecha;
	@View
	private Textbox tbxNumero_autorizacion;
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

	@View
	private Checkbox chkFecha;
	@View
	private Checkbox chkAutorizacion;
	@View
	private Checkbox chkDiagnosticoPrincipal;
	@View
	private Checkbox chkDiagnosticoRelacionado1;

	private Set<Listitem> lista_servicios;
	private Paciente paciente;
	private String nro_autorizacion;
	private GeneralComposer generalComposer;

	@Override
	public void params(Map<String, Object> map) {
		lista_servicios = (Set<Listitem>) map
				.get(ServiciosFacturacionMacro.PARAM_LISTADO_SERVICIOS);
		paciente = (Paciente) map.get(ServiciosFacturacionMacro.PARAM_PACIENTE);
		nro_autorizacion = (String) map
				.get(ServiciosFacturacionMacro.PARAM_AUTORIZACION);
		generalComposer = (GeneralComposer) map
				.get(ServiciosFacturacionMacro.PARAM_WINDOW_PADRE);
	}

	@Override
	public void init() throws Exception {
		parametrizaBandbox();
		tbxNumero_autorizacion.setValue(nro_autorizacion);
	}

	/**
	 * Este metodo me permite complentar de manera rapida todos los detalles
	 * 
	 * @author Luis Miguel
	 * */
	public void onComplementar() {
		try {
			for (Listitem listitem : lista_servicios) {
				Map<String, Object> map_servicios = listitem.getValue();
				Object servicio_rips = map_servicios
						.get(ITiposServicio.PARAM_RIPS);
				if (servicio_rips != null) {
					complementar(servicio_rips, map_servicios);
				}
			}
			setAutorizacionWindow();
			MensajesUtil.mensajeInformacion("Informacion",
					"Complementacion exitosa");
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, null, this);
		}
	}

	private void setAutorizacionWindow() {
		if (generalComposer instanceof Facturacion_ripsAction) {
			((Facturacion_ripsAction) generalComposer)
					.setNroAutorizacion(nro_autorizacion);
		}
	}

	private void complementar(Object servicio_rips,
			Map<String, Object> map_servicios) {
		Toolbarbutton toolbarbutton_complememto = (Toolbarbutton) map_servicios
				.get(ServiciosFacturacionMacro.TOOLBAR_BUTTON_COMPLEMENTO);
		Datebox datebox_realizacion = (Datebox) toolbarbutton_complememto
				.getAttribute(ServiciosFacturacionMacro.DATE_BOX_REALIZACION);
		if (servicio_rips instanceof Datos_consulta) {
			Datos_consulta datos_consulta = (Datos_consulta) servicio_rips;
			if (chkFecha.isChecked()) {
				datos_consulta
						.setFecha_consulta(dtbxFecha.getValue() != null ? new Timestamp(
								dtbxFecha.getValue().getTime()) : null);
				datebox_realizacion
						.setValue(datos_consulta.getFecha_consulta());
			}
			if (chkAutorizacion.isChecked()) {
				datos_consulta.setNumero_autorizacion(tbxNumero_autorizacion
						.getValue());
			}
			if (chkDiagnosticoPrincipal.isChecked()) {
				datos_consulta
						.setCodigo_diagnostico_principal(tbxCodigo_diagnostico_principal
								.getValue());
			}
			if (chkDiagnosticoRelacionado1.isChecked()) {
				datos_consulta
						.setCodigo_diagnostico1(tbxCodigo_diagnostico_rel1
								.getValue());
			}
		} else if (servicio_rips instanceof Datos_procedimiento) {
			Datos_procedimiento datos_procedimiento = (Datos_procedimiento) servicio_rips;
			if (chkFecha.isChecked()) {
				datos_procedimiento
						.setFecha_procedimiento(dtbxFecha.getValue() != null ? new Timestamp(
								dtbxFecha.getValue().getTime()) : null);
				datebox_realizacion.setValue(datos_procedimiento
						.getFecha_procedimiento());
			}
			if (chkAutorizacion.isChecked()) {
				datos_procedimiento
						.setNumero_autorizacion(tbxNumero_autorizacion
								.getValue());
			}
			if (chkDiagnosticoPrincipal.isChecked()) {
				datos_procedimiento
						.setCodigo_diagnostico_principal(tbxCodigo_diagnostico_principal
								.getValue());
			}
			if (chkDiagnosticoRelacionado1.isChecked()) {
				datos_procedimiento
						.setCodigo_diagnostico_relacionado(tbxCodigo_diagnostico_rel1
								.getValue());
			}
		} else if (servicio_rips instanceof Datos_medicamentos
				|| servicio_rips instanceof Datos_servicio) {
			if (chkFecha.isChecked()) {
				map_servicios.put(ServiciosFacturacionMacro.FECHA_REALIZACION,
						dtbxFecha.getValue() != null ? new Timestamp(dtbxFecha
								.getValue().getTime()) : null);
				datebox_realizacion
						.setValue(dtbxFecha.getValue() != null ? new Timestamp(
								dtbxFecha.getValue().getTime()) : null);
			}
			if (chkAutorizacion.isChecked()) {
				map_servicios.put(ServiciosFacturacionMacro.NRO_AUTORIZACION,
						tbxNumero_autorizacion.getValue());
			}
		}
	}

	public void onSeleccionar(boolean estado) {
		chkFecha.setChecked(estado);
		chkAutorizacion.setChecked(estado);
		chkDiagnosticoPrincipal.setChecked(estado);
		chkDiagnosticoRelacionado1.setChecked(estado);
	}

	private void parametrizaBandbox() {
		inicializarBandboxCie(tbxCodigo_diagnostico_principal, tbxNomDxpp,
				btnLimpiarDxpp, true, tbxCodigo_diagnostico_rel1,
				"codigo_diagnostico_principal");
		inicializarBandboxCie(tbxCodigo_diagnostico_rel1, tbxNomDxr1,
				btnLimpiarDxr1, false, null, "codigo_diagnostico1");
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
					Map<String, Object> map = new HashMap();
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
						String msj = (String) result.get("msg");
						throw new ValidacionRunTimeException((msj != null
								&& !msj.trim().isEmpty() ? msj
								: "El dx no es valido para este paciente"));
					}
					bandbox.setValue(registro.getCodigo());
					textboxInformacion.setValue(registro.getNombre());

					if (basedComponent != null) {
						basedComponent.setFocus(true);
					}
				} catch (Exception e) {
					MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
					return false;
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
			}
		};
		/* inyectamos el mismo evento */
		bandboxRegistrosMacro.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		bandboxRegistrosMacro.setReadonly(false);
	}

}
