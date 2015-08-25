package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.service.AdministradoraService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;

public class SeleccionadorAdministradoraAction extends GeneralComposer {
	public static final String _TIPO_ASEGURADORA = "ti_asg";
	public static final String _EVENTO_SELECCIONAR_ASEGURADORA = "Evs_asegura";
	private OnSeleccionadorAseguradorasAccion onSeleccionadorAseguradorasAccion;

	private String tipo_aseg = "";

	@View
	private Listbox lbxSeleccionarAdministradora;
	
	@WireVariable
	private AdministradoraService administradoraService;

	@Override
	public void params(Map<String, Object> map) {
		tipo_aseg = (String) map.get(_TIPO_ASEGURADORA);
		setOnSeleccionadorAseguradorasAccion((OnSeleccionadorAseguradorasAccion) map
				.get(_EVENTO_SELECCIONAR_ASEGURADORA));
		if (tipo_aseg != null) {
			if (ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRABAJO
					.equals(tipo_aseg)) {
				setTitle("ASEGURADORAS ARL(ASEGURADORA DE RIESGOS LABORALES)");
			} else if (ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRANSITO
					.equals(tipo_aseg)
					|| ServiciosDisponiblesUtils.CAUSA_EXTERNA_EVENTO_CATASTROFICO
							.equals(tipo_aseg)) {
				setTitle("ASEGURADORAS SOAT");
			} else {
				setTitle("NO DEFINIDO");
			}
		}
	}

	@Override
	public void init() throws Exception {
		cargarEventos();
		inicializarAdministradoras();
	}

	private void inicializarAdministradoras() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("codigo_servicio", ServiciosDisponiblesUtils.CODSER_URGENCIAS);
		param.put(
				"tipo_aseguradora",
				tipo_aseg
						.equals(ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRABAJO) ? ServiciosDisponiblesUtils.TIPO_ASEGURADORA_ARL
						: ServiciosDisponiblesUtils.TIPO_ASEGURADORA_SOAT);
		// log.info("param ==> "+param);
		List<Administradora> administradoras = administradoraService
				.listarAdministradorasServicio(param);

		if (!administradoras.isEmpty()) {
			for (Administradora administradora : administradoras) {
				lbxSeleccionarAdministradora.appendChild(new Listitem(
						administradora.getCodigo() + " "
								+ administradora.getNombre().toUpperCase(),
						administradora));
			}
			doModal();
		} else {
			onClose();
			if (ServiciosDisponiblesUtils.CAUSA_EXTERNA_ACCIDENTE_TRABAJO
					.equals(tipo_aseg)) {
				MensajesUtil
						.mensajeAlerta(
								"Advertencia",
								"EL SISTEMA NO ENCONTRO NINGUNA ASEGURADORA ARL(ASEGURADORA DE RIESGOS LABORALES)");
			} else {
				MensajesUtil.mensajeAlerta("Advertencia",
						"EL SISTEMA NO ENCONTRO NINGUNA ASEGURADORA SOAT");
			}
		}
	}

	private void cargarEventos() {
		lbxSeleccionarAdministradora.addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						Administradora administradora = lbxSeleccionarAdministradora
								.getSelectedItem().getValue();
						Messagebox
								.show("Esta seguro que desea seleccionar la aseguradora "
										+ administradora.getNombre()
												.toUpperCase() + "?",
										"Seleccion de Aseguradora",
										Messagebox.YES + Messagebox.NO,
										Messagebox.QUESTION,
										new org.zkoss.zk.ui.event.EventListener<Event>() {
											public void onEvent(Event event)
													throws Exception {
												if ("onYes".equals(event
														.getName())) {
													// do the thing
													if (onSeleccionadorAseguradorasAccion != null) {
														onSeleccionadorAseguradorasAccion
																.onSeleccionAseguradora((Administradora) lbxSeleccionarAdministradora
																		.getSelectedItem()
																		.getValue());
													}
													detach();
												}
											}
										});
					}
				});
	}

	@Override
	public void onClose() {
		if (onSeleccionadorAseguradorasAccion != null)
			onSeleccionadorAseguradorasAccion.onCancelar();
		super.onClose();
	}

	public interface OnSeleccionadorAseguradorasAccion {
		void onCancelar();

		void onSeleccionAseguradora(Administradora administradora);
	}

	public OnSeleccionadorAseguradorasAccion getOnSeleccionadorAseguradorasAccion() {
		return onSeleccionadorAseguradorasAccion;
	}

	public void setOnSeleccionadorAseguradorasAccion(
			OnSeleccionadorAseguradorasAccion onSeleccionadorAseguradorasAccion) {
		this.onSeleccionadorAseguradorasAccion = onSeleccionadorAseguradorasAccion;
	}

}
