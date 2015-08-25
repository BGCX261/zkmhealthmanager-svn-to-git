/*
 * agudeza_visualAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;

public class Reporte_ibcAction extends ZKWindow {

	// Componentes //
	@View private Textbox tbxAccion;
	@View private Groupbox groupboxEditar;
	@View private Groupbox groupboxConsulta;

	@View private Datebox dtbxFecha_inicial;

	@View private Bandbox tbxIdentificacion;
	@View private Textbox tbxNomPaciente;
	@View private Textbox tbxTipoIdentificacion;
	
	@View private Datebox dtbxFecha_inicio;
	@View private Datebox dtbxFecha_final;

	@View private Listbox lbxFormato;

	
	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
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

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			// buscarDatos();
		} else {
			// buscarDatos();
			limpiarDatos();
		}
		tbxAccion.setValue(accion);
	}

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent)
							.setText(((Textbox) abstractComponent).getText()
									.trim().toUpperCase());
				}
			}
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals(
						"lbxParameter")) {
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setSelectedIndex(0);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setText("0.00");
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setText("");
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setValue(new Date());
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setChecked(false);
			}
			if (abstractComponent instanceof Radiogroup) {
				((Radio) ((Radiogroup) abstractComponent).getFirstChild())
						.setChecked(true);
			}
		}

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public void buscarPaciente(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getAfiliacionesMeService().setLimit(
					"limit 25 offset 0");

			List<Afiliaciones_me> list = getServiceLocator()
					.getAfiliacionesMeService().listar(parameters);

			lbx.getItems().clear();

			for (Afiliaciones_me dato : list) {

				if (dato.getTipo_afiliado().equals("C")) {

					Paciente paciente = new Paciente();
					paciente.setCodigo_empresa(dato.getCodigo_empresa());
					paciente.setCodigo_sucursal(dato.getCodigo_sucursal());
					paciente.setNro_identificacion(dato
							.getNro_identificacion_afiliado());
					paciente = getServiceLocator().getPacienteService()
							.consultar(paciente);

					Listitem listitem = new Listitem();
					listitem.setValue(dato);

					Listcell listcell = new Listcell();
					listcell.appendChild(new Label(paciente
							.getTipo_identificacion() + ""));
					listitem.appendChild(listcell);

					listcell = new Listcell();
					listcell.appendChild(new Label(paciente
							.getNro_identificacion() + ""));
					listitem.appendChild(listcell);

					listcell = new Listcell();
					listcell.appendChild(new Label(paciente.getNombre1() + " "
							+ paciente.getNombre2()));
					listitem.appendChild(listcell);

					listcell = new Listcell();
					listcell.appendChild(new Label(paciente.getApellido1()
							+ " " + paciente.getApellido2()));
					listitem.appendChild(listcell);

					lbx.appendChild(listitem);
				}
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

	public void selectedPaciente(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxIdentificacion.setValue("");
			tbxNomPaciente.setValue("");
			tbxTipoIdentificacion.setValue("");

		} else {
			Afiliaciones_me dato = (Afiliaciones_me) listitem.getValue();

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(dato.getCodigo_empresa());
			paciente.setCodigo_sucursal(dato.getCodigo_sucursal());
			paciente.setNro_identificacion(dato
					.getNro_identificacion_afiliado());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			tbxIdentificacion.setValue(paciente.getNro_identificacion());
			tbxNomPaciente.setValue(paciente.getNombre1() + " "
					+ paciente.getNombre2() + " " + paciente.getApellido1()
					+ " " + paciente.getApellido2());
			tbxTipoIdentificacion.setValue(paciente.getTipo_identificacion());

		}

		tbxIdentificacion.close();
	}

	public void imprimir() throws Exception {
		if (tbxIdentificacion.getValue().trim().isEmpty()) {
			Messagebox.show("Ingrese un paciente", "Informacion ..",
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}

		Map paramRequest = new HashMap();
		paramRequest.put("name_report", "Reporte_ibc");
		paramRequest.put("codigo_empresa", empresa.getCodigo_empresa());
		paramRequest.put("codigo_sucursal", sucursal.getCodigo_sucursal());
		paramRequest.put("identificacion", tbxIdentificacion.getValue());
		paramRequest.put("fecha_inicial", dtbxFecha_inicial.getValue());
		paramRequest.put("fecha_inicio", dtbxFecha_inicio.getValue());
		paramRequest.put("fecha_final", dtbxFecha_final.getValue());
		paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
				.toString());
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);

		Boolean mostrar_ventana = true;
		String mensaje_vacio = "";

		if (paramRequest.containsKey("retorno_vacio")) {
			mostrar_ventana = !(Boolean) paramRequest.get("retorno_vacio");
			mensaje_vacio = (String) paramRequest.get("mensaje_vacio");
		}

		if (!mostrar_ventana) {
			MensajesUtil.mensajeAlerta("Alerta !!!", mensaje_vacio);
			componente.detach();
		} else {

			final Window window = (Window) componente;
			window.setMode("modal");
			window.setMaximizable(true);
			window.setMaximized(true);
		}

	}

	@Override
	public void init() throws Exception {
		accionForm(true, "registrar");
	}
}
