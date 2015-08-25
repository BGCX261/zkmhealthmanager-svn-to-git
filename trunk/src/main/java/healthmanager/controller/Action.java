/**
 * 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Sucursal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.locator.ServiceLocatorWeb;

/**
 * @author ferney
 * 
 */
public abstract class Action extends Window {

	private static final long serialVersionUID = 4585348366392632830L;

	private static Logger log = Logger.getLogger(Action.class);

	abstract void adicionarPcd(Map<String, Object> pcd) throws Exception;

	public void listarDepartamentos(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}
		List<Departamentos> departamentos = this.getServiceLocator()
				.getDepartamentosService()
				.listar(new HashMap<String, Object>());

		for (Departamentos dpto : departamentos) {
			listitem = new Listitem();
			listitem.setValue(dpto.getCodigo());
			listitem.setLabel(dpto.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		listboxMun.getChildren().clear();
		Listitem listitem;
		String coddep = listboxDpto.getSelectedItem().getValue().toString();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("coddep", coddep);
		List<Municipios> municipios = this.getServiceLocator()
				.getMunicipiosService().listar(parameters);

		for (Municipios mun : municipios) {
			listitem = new Listitem();
			listitem.setValue(mun.getCodigo());
			listitem.setLabel(mun.getNombre());
			listboxMun.appendChild(listitem);
		}
		if (listboxMun.getItemCount() > 0) {
			listboxMun.setSelectedIndex(0);
		}
	}

	public void buscarAdministradora(String value, Listbox lbx)
			throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getAdministradoraService().setLimit(
					"limit 25 offset 0");

			List<Administradora> list = getServiceLocator()
					.getAdministradoraService().listar(parameters);

			lbx.getItems().clear();

			for (Administradora dato : list) {
				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getCodigo() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNit() + ""));
				listitem.appendChild(listcell);

				lbx.appendChild(listitem);
			}

			lbx.setMold("paging");
			lbx.setPageSize(8);
			lbx.applyProperties();

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void selectedAdministradora(Listitem listitem, Bandbox bandbox,
			Textbox textbox) {

		if (listitem.getValue() == null) {
			bandbox.setValue("");
			textbox.setValue("");
		} else {
			Administradora dato = (Administradora) listitem.getValue();
			bandbox.setValue(dato.getCodigo() + " " + dato.getNombre());
			textbox.setValue(dato.getCodigo());
		}
		bandbox.close();
	}

	public void listarContratos(Listbox listbox, String codigo_admin,
			boolean select, boolean solo_abiertos) {

		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		Empresa empresa = ServiceLocatorWeb.getEmpresa(request);
		Sucursal sucursal = ServiceLocatorWeb.getSucursal(request);

		listbox.getChildren().clear();
		Listitem listitem;

		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- Seleccione --");
			listbox.appendChild(listitem);
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", empresa.getCodigo_empresa());
		parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		parameters.put("codigo_administradora", codigo_admin);
		if (solo_abiertos) {
			parameters.put("cerrado", false);
		}
		List<Contratos> lista_contratos = this.getServiceLocator().getContratosService()
				.listar(parameters);

		for (Contratos contratos : lista_contratos) {
			listitem = new Listitem();
			listitem.setValue(contratos.getId_plan());
			listitem.setLabel(contratos.getId_plan() + " - " + contratos.getNombre());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public void listarElementoListbox(Listbox listbox, boolean select,
			String msgDefault) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- " + msgDefault + " --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}
	
	public void pruebas(){
		
	}
	

}
