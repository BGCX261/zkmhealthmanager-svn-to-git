/*
 * admisionAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.ElementoService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

import com.framework.constantes.IVias_ingreso;
import com.framework.util.MensajesUtil;

public class Auditoria_admision_atendidasAction extends ZKWindow {

	private AdmisionService admisionService;

	// Componentes //

	@View
	private Groupbox groupboxConsulta;
	@View
	private Listbox listboxResultado;
	@View
	private Label lbTotal_admisiones;

	private Map<String, String> vias = new HashMap<String, String>();
	private Map<String, String> estado_admision = new HashMap<String, String>();

	@Override
	public void init() {
		for (Elemento elemento : getServiceLocator().getServicio(
				ElementoService.class).listar("via_ingreso")) {
			vias.put(elemento.getCodigo(), elemento.getDescripcion());
		}
		for (Elemento elemento : getServiceLocator().getServicio(
				ElementoService.class).listar("admision_estado")) {
			estado_admision
					.put(elemento.getCodigo(), elemento.getDescripcion());
		}

	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
		} else {
			// buscarDatos();
			// limpiarDatos();
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		if (!valida) {
			MensajesUtil.mensajeAlerta(usuarios.getNombres()
					+ " recuerde que...",
					"Los campos marcados con (*) son obligatorios");
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {

			String value = "";

			lbTotal_admisiones.setValue("");

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("codigo_centro",
					centro_atencion_session.getCodigo_centro());
			parameters.put("paramTodo", "paramTodo");
			parameters.put("value", value);
			parameters.put("atendiendo", true);
			parameters.put("atendida", false);

			List<String> vias_ingreso = new ArrayList<String>();
			vias_ingreso.add(IVias_ingreso.HOSPITALIZACIONES);
			vias_ingreso.add(IVias_ingreso.URGENCIA);
			vias_ingreso.add(IVias_ingreso.SALUD_ORAL);

			parameters.put("vias_ingreso", vias_ingreso);

			List<Admision> lista_datos = admisionService
					.listarResultados(parameters);
			listboxResultado.getItems().clear();

			for (Admision admision : lista_datos) {
				listboxResultado.appendChild(crearFilas(admision, this));
			}

			lbTotal_admisiones.setValue(lista_datos.size() + "");

			listboxResultado.setVisible(true);
			// listboxResultado.setMold("paging");
			// listboxResultado.setPageSize(20);

			listboxResultado.applyProperties();
			listboxResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Listitem crearFilas(Object objeto, Component componente)
			throws Exception {
		Listitem fila = new Listitem();

		final Admision admision = (Admision) objeto;

		Hbox hbox = new Hbox();

		Paciente paciente = admision.getPaciente();
		String nombre_paciente = "";
		if (paciente != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(paciente.getNombre1());
			sb.append(!paciente.getNombre2().isEmpty() ? " "
					+ paciente.getNombre2() : "");
			sb.append(!paciente.getApellido1().isEmpty() ? " "
					+ paciente.getApellido1() : "");
			sb.append(!paciente.getApellido2().isEmpty() ? " "
					+ paciente.getApellido2() : "");
			nombre_paciente = sb.toString().toUpperCase();
		}

		fila.appendChild(new Listcell());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		fila.appendChild(new Listcell(dateFormat.format(admision
				.getFecha_ingreso()) + ""));
		fila.appendChild(new Listcell(admision.getNro_ingreso() + ""));
		fila.appendChild(new Listcell(admision.getNro_identificacion() + ""));
		fila.appendChild(new Listcell(nombre_paciente));
		fila.appendChild(new Listcell(vias.get(admision.getVia_ingreso()) + ""));
		// fila.appendChild(new
		// Listcell(estado_admision.get(admision.getEstado()) + ""));
		// fila.appendChild(new Listcell(admision.getAtendida() ? "SI" : "NO"));

		Listcell listcell = new Listcell();
		listcell.appendChild(hbox);

		fila.appendChild(listcell);

		fila.setValue(admision);

		return fila;
	}

	public void eliminarDatos(Object obj) throws Exception {
		Admision admision = (Admision) obj;
		try {
			admision.setDelete_user(getUsuarios().getCodigo()); 
			int result = admisionService.eliminar(admision);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}

	public void corregirMarcas() {
		try {
			Set<Listitem> listado_seleccionadas = listboxResultado
					.getSelectedItems();
			if (!listado_seleccionadas.isEmpty()) {
				for (Listitem listitem : listado_seleccionadas) {
					Admision admision = (Admision) listitem.getValue();
					admision = getServiceLocator().getAdmisionService()
							.consultar(admision);
					admision.setAtendiendo(false);
					admisionService.actualizar_estado(admision);
				}
				MensajesUtil.mensajeInformacion("Informacion",
						"Marcas corregidas satisfactoriamente!!!");
				buscarDatos();
			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void iniciar() {
		try {
			buscarDatos();
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}
}