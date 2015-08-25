/*
 * copago_estratoAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Manuales_tarifarios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.res.Res;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;

public class Contratos_seleccionadosAction extends ZKWindow {

//	private static Logger log = Logger
//			.getLogger(Contratos_seleccionadosAction.class);

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;

	@View
	private Groupbox groupboxConsulta;
	@View
	private Listbox listboxResultado;

	@View
	private Listbox lbxTipo_facturacion;

	private EventListener<? extends Event> eventListener;

	@Override
	public void init() throws Exception {
		listarCombos();
		addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event arg0) throws Exception {
				Contratos_seleccionadosAction.this.setVisible(false);
			}

		});
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxTipo_facturacion, false,
				getServiceLocator());

	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("contratos.nombre");
		listitem.setLabel("Nombre del contrato");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("contratos.nro_contrato");
		listitem.setLabel("Nro contrato");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("contratos.codigo_administradora");
		listitem.setLabel("Codigo Administradora");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("ad.nombre");
		listitem.setLabel("Nombre Administradora");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("contratos.id_plan");
		listitem.setLabel("ID Contrato");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("tipo_facturacion", lbxTipo_facturacion
					.getSelectedItem().getValue());
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Contratos> lista_datos = getServiceLocator()
					.getContratosService().listar(parameters);

			//log.info("Total contratos encontrados" + lista_datos.size());

			listboxResultado.getItems().clear();

			for (Contratos contratos : lista_datos) {
				listboxResultado.appendChild(crearFilas(contratos, this));
			}

			listboxResultado.setMold("paging");
			listboxResultado.setPageSize(20);

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente)
			throws Exception {
		Listitem fila = new Listitem();

		final Contratos contratos = (Contratos) objeto;
		
		fila.setValue(contratos);

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(contratos.getCodigo_empresa());
		administradora.setCodigo_sucursal(contratos.getCodigo_sucursal());
		administradora.setCodigo(contratos.getCodigo_administradora());
		administradora = getServiceLocator().getAdministradoraService()
				.consultar(administradora);

		boolean brinda_servicios_pyp = ServiciosDisponiblesUtils
				.isContratoPyp(contratos);

		StringBuilder manuales = new StringBuilder("");

		int c = 0;
		for (Manuales_tarifarios manuales_tarifarios : contratos
				.getManuales_tarifarios()) {
			manuales.append(""
					+ manuales_tarifarios.getMaestro_manual().getManual()
					+ ""
					+ (manuales_tarifarios.getMaestro_manual().getTipo_manual()
							.equals(IConstantes.TIPO_MANUAL_SOAT) ? manuales_tarifarios
							.getAnio() : ""));
			manuales.append(" - Tarifa especial: "
					+ (manuales_tarifarios.getTarifa_especial().equals("S") ? "SI"
							: "NO"));
			manuales.append(((++c) < contratos.getManuales_tarifarios().size()) ? ", "
					: "");
		}

		fila.setTooltiptext(manuales.toString());

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Listcell());
		fila.appendChild(new Listcell(contratos.getId_plan()));
		fila.appendChild(new Listcell(contratos.getCodigo_administradora()));
		fila.appendChild(new Listcell((administradora != null ? administradora
				.getNombre() : "")));
		fila.appendChild(new Listcell(contratos.getNro_contrato()));
		fila.appendChild(new Listcell(contratos.getNombre()));
		fila.appendChild(new Listcell(Res.recortarCadena(manuales.toString(),
				10)));
		fila.appendChild(new Listcell(brinda_servicios_pyp ? "SI" : "NO"));
		fila.appendChild(new Listcell(contratos.getCerrado() ? "SI" : "NO"));

		return fila;
	}

	public void limpiarDatos() {
		listboxResultado.getItems().clear();
	}

	public List<Contratos> obtenerListadoContratos() {
		List<Contratos> listado_contratos = new ArrayList<Contratos>();
		Set<Listitem> lista_items = listboxResultado.getSelectedItems();
		for (Listitem listitem : lista_items) {
			listado_contratos.add((Contratos) listitem.getValue());
		}
		return listado_contratos;
	}

	public void onClickAplicarCambios() throws Exception {
		if (eventListener != null)
			eventListener.onEvent(null);
	}

	public EventListener<? extends Event> getEventListener() {
		return eventListener;
	}

	public void setEventListener(EventListener<? extends Event> eventListener) {
		this.eventListener = eventListener;
	}

}