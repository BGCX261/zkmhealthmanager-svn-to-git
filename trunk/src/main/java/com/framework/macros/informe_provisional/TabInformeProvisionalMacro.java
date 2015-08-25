package com.framework.macros.informe_provisional;

import healthmanager.modelo.service.ReportService;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.informe_provisional.implementacion.ComunicacionDatosMedicamentos;
import com.framework.macros.informe_provisional.implementacion.ComunicacionDatosProcedimiento;
import com.framework.macros.informe_provisional.implementacion.ComunicacionUrgSinTriage;
import com.framework.macros.informe_provisional.implementacion.ComunicadorDatosConsulta;
import com.framework.macros.informe_provisional.implementacion.ComunicadorEstancias;
import com.framework.macros.informe_provisional.implementacion.ComunicadorTriage;

public class TabInformeProvisionalMacro extends Tab {

	public SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	public enum ITipoServicio {
		DATOS_CONSULTA, DATOS_PROCEDIMIENTO, MEDICAMENTOS_INSUMOS, ESTANCIAS, TRIAGE, SIN_TRIAGE;

		public String getTitulo() {
			return this.toString().replace("_", " ");
		}

		public IComunidadorAccion getComunicador() {
			if (this == DATOS_CONSULTA) {
				return new ComunicadorDatosConsulta();
			} else if (this == DATOS_PROCEDIMIENTO) {
				return new ComunicacionDatosProcedimiento();
			} else if (this == MEDICAMENTOS_INSUMOS) {
				return new ComunicacionDatosMedicamentos();
			} else if (this == ESTANCIAS) {
				return new ComunicadorEstancias();
			} else if (this == TRIAGE) {
				return new ComunicadorTriage();
			} else if (this == SIN_TRIAGE) {
				return new ComunicacionUrgSinTriage();
			}
			return null;
		}

		public String getIdCantidadXml() {
			if (this == DATOS_CONSULTA) {
				return "getCantidadDatosConsulta";
			} else if (this == DATOS_PROCEDIMIENTO) {
				return "getCantidadDatosProcedimiento";
			} else if (this == MEDICAMENTOS_INSUMOS) {
				return "getCantidadDatosMedicamento";
			} else if (this == ESTANCIAS) {
				return "getCantidadEstancias";
			} else if (this == TRIAGE) {
				return "getCantidadTriage";
			} else if (this == SIN_TRIAGE) {
				return "getCantidadUrgSinTriage";
			}
			return null;
		}
	}

	private Tabpanel tabpanel;
	private ITipoServicio tipoServicio;
	// private IComunidadorAccion comunidadorAccion;
	private Map<String, Object> map;
	private Tree tree;
	private Div dvContenedor;

	public interface IComunidadorAccion {
		public void mostarInformacion(Map<String, Object> fecha_seleccionada,
				Map<String, Object> param, Div contenedor, String titulo);
	}

	public TabInformeProvisionalMacro(boolean inicializada, ITipoServicio tipo,
			Map<String, Object> map_param, Tabpanels tabpanelcontenedora,
			Div dvContenedor) {
		setLabel(tipo.getTitulo());
		tabpanel = new Tabpanel();
		this.dvContenedor = dvContenedor;

		tabpanelcontenedora.appendChild(tabpanel);
		this.tipoServicio = tipo;
		this.map = map_param;
		// comunidadorAccion = tipoServicio.getComunicador();
		if (inicializada) {
			consultar();
		} else {
			TabInformeProvisionalMacro.this.addEventListener(Events.ON_CLICK,
					new EventListener<Event>() {
						@Override
						public void onEvent(Event arg0) throws Exception {
							consultar();
						}
					});
		}
	}

	private void consultar() {
		getArbol().addEventListener(Events.ON_SELECT,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event evt) throws Exception {
						Tree tree = (Tree) evt.getTarget();
						Treeitem treeitem = tree.getSelectedItem();
						if (treeitem != null) {
							treeitem.setSelected(false);
							tipoServicio.getComunicador()
									.mostarInformacion(
											(Map<String, Object>) treeitem
													.getValue(), map,
											dvContenedor,
											tipoServicio.getTitulo());
						}
					}
				});
	}

	/**
	 * Este metodo me permite deselecionar
	 * */
	public void deseleccionar() {
		Treeitem treeitem = getArbol().getSelectedItem();
		if (treeitem != null) {
			treeitem.setSelected(false);
		}
	}

	private Tree getArbol() {
		// creacion de arbol
		if (tree == null) {
			tree = new Tree();
			tree.setWidth("97%");
			// tree.setHeight("100%");

			Div div = new Div();
			div.setHeight("100%");
			div.setStyle("overflow:auto;overflow-x:hidden;");
			div.appendChild(tree);
			tabpanel.appendChild(div);

			// contenedor de hijos del arbol
			Treechildren treechildren = new Treechildren();
			tree.appendChild(treechildren);

			List<Map<String, Object>> listado_servicios = getServiceLocator()
					.getServicio(ReportService.class).getReport(map,
							"informeprovisionarModel",
							tipoServicio.getIdCantidadXml());
			Long total = 0L;
			for (Map<String, Object> map : listado_servicios) {
				Date fecha = (Date) map.get("fecha");
				Long cantidad = (Long) map.get("cantidad");
				treechildren.appendChild(new Treeitem(dateFormat.format(fecha)
						+ " - (" + cantidad + ")", map));
				total += cantidad;
			}
			setLabel(tipoServicio.getTitulo() + " - Total: " + total);
		}
		return tree;
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

	public boolean contieneInformacion() {
		return (tree != null && !tree.getItems().isEmpty());
	}
}
