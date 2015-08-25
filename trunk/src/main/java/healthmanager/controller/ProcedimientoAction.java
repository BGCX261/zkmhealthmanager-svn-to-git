/*
 * procedimientoAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Procedimientos;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.constantes.IConstantes;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class ProcedimientoAction extends ZKWindow {

	private static Logger log = Logger.getLogger(ProcedimientoAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;

	@View
	private Textbox tbxCodigo_soat;
	@View
	private Textbox tbxCodigo_cups;
	@View
	private Textbox tbxDescripcion;
	@View
	private Doublebox dbxPorcentaje;
	@View
	private Listbox lbxClasificacion;
	@View
	private Checkbox chbConsulta;
	@View
	private Checkbox chbQuirurgico;
	@View
	private Listbox lbxTipo_quirurgico;
	@View
	private Checkbox chbUrgencias;
	@View
	private Checkbox chbHospitalizacion;
	@View
	private Checkbox chbRecien_nacido;
	@View
	private Textbox tbxArea_intervencion;
	@View
	private Doublebox dbxMeta;
	@View
	private Listbox lbxGrupo;
	@View
	private Listbox lbxCodigo_contabilidad;
	@View
	private Listbox lbxSexo;
	@View
	private Intbox ibxLimite_inferior;
	@View
	private Intbox ibxLimite_superior;
	@View
	private Checkbox chbPyp;
	@View
	private Checkbox chbCobra_copago;
	@View
	private Listbox lbxTipo_procedimiento;
	@View
	private Intbox ibxFrecuencia_orden;
	@View
	private Checkbox chbConsulta_especializada;
	@View
	Checkbox chbConsulta_especializada_med_esp;
	@View
	private Checkbox chbAut_medi_general;
	@View
	private Checkbox chbAut_medi_especialista;
	@View
	private Row rowAutorizacionMedico;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxTipoProcedimiento;
	@View
	private Listbox lbxNivel;
	@View
	private Textbox tbxCodigo_interno;

	@View
	private Listbox lbxNiveles;

	@Override
	public void init() {
		listarCombos();
		loadevents();
		parametrizarbandbox();
	}

	private void parametrizarbandbox() {

	}

	private void loadevents() {
		chbQuirurgico.addEventListener("onCheck", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				lbxTipo_quirurgico.setDisabled(!chbQuirurgico.isChecked());
			}
		});
		// Habilitamos cuando trabaje como caja prevision
		rowAutorizacionMedico.setVisible(sucursal.getTipo().equals(
				IConstantes.TIPOS_SUCURSAL_CAJA_PREV));
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxClasificacion, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxGrupo, true, getServiceLocator());
		Utilidades.listarNivelEmpresa(lbxNivel, false);
		Utilidades.listarNivelEmpresa(lbxNiveles, false);
		if (lbxNivel.getItemCount() > 0) {
			lbxNivel.setSelectedIndex(0);
		}
		// listarElementoListbox(lbxSexo,true);
		// listarUnidadFuncional(LBX, true);
		Utilidades.listarContabiliazaciones(lbxCodigo_contabilidad, true, this);
		Utilidades.listarElementoListbox(lbxTipo_procedimiento, true,
				getServiceLocator());
		Utilidades.listarQuirurgicos(lbxTipo_quirurgico, true);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("codigo_soat");
		listitem.setLabel("Codigo soat");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_cups");
		listitem.setLabel("Codigo cups");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("descripcion");
		listitem.setLabel("Nombre");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	// Accion del formulario si es nuevo o consultar //
	public void accionForm(boolean sw, String accion) throws Exception {
		groupboxConsulta.setVisible(!sw);
		groupboxEditar.setVisible(sw);

		if (!accion.equalsIgnoreCase("registrar")) {
			buscarDatos();
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
		tbxCodigo_interno.setReadonly(false);
		tbxTipoProcedimiento.limpiarSeleccion(false);
		Utilidades.quitarSeleccion(lbxNiveles);
		chbPyp.setChecked(true);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxCodigo_soat
				.setStyle("text-transform:uppercase;background-color:white");
		tbxDescripcion
				.setStyle("text-transform:uppercase;background-color:white");
		dbxPorcentaje.setStyle("background-color:white");
		lbxTipo_procedimiento.setStyle("background-color:white");
		lbxTipo_quirurgico.setStyle("background-color:white");
		ibxFrecuencia_orden
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_interno
				.setStyle("text-transform:uppercase;background-color:white");

		String msj = "Los campos marcados con (*) son obligatorios";
		boolean valida = true;

		if (tbxCodigo_soat.getText().trim().equals("")) {
			tbxCodigo_soat
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (tbxCodigo_soat.getText().trim().equals("")) {
			tbxCodigo_soat
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (tbxCodigo_interno.getText().trim().equals("")) {
			tbxCodigo_interno
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (tbxDescripcion.getText().trim().equals("")) {
			tbxDescripcion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (ibxFrecuencia_orden.getText().isEmpty()) {
			ibxFrecuencia_orden
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (chbQuirurgico.isChecked() && lbxGrupo.getSelectedIndex() == 0) {
			lbxGrupo.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			msj = "Para los procedimientos quirurgicos debe seleccionar un grupo";
		}

		if (chbQuirurgico.isChecked()
				&& lbxTipo_quirurgico.getSelectedIndex() == 0) {
			lbxTipo_quirurgico
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
			msj = "Para los procedimientos quirurgicos debe seleccionar un tipo de quirurgicos";
		}

		if (dbxPorcentaje.getText().trim().equals("")) {
			dbxPorcentaje.setStyle("background-color:#F6BBBE");
			valida = false;
		} else if (dbxPorcentaje.getValue().doubleValue() == 0d) {
			valida = false;
			dbxPorcentaje.setStyle("background-color:#F6BBBE");
			dbxPorcentaje.focus();
			msj = "El porcentaje no puede ser cero para los procedimiento tipo consulta";
		}

		if (!valida) {
			Messagebox.show(msj, usuarios.getNombres() + " recuerde que...",
					Messagebox.OK, Messagebox.EXCLAMATION);
		}

		return valida;
	}

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("es_grupo", "N");

			getServiceLocator().getProcedimientosService().setLimit(
					"limit 25 offset 0");

			List<Procedimientos> lista_datos = getServiceLocator()
					.getProcedimientosService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Procedimientos procedimiento : lista_datos) {
				rowsResultado.appendChild(crearFilas(procedimiento, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Procedimientos procedimiento = (Procedimientos) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(procedimiento.getId_procedimiento() + ""));
		fila.appendChild(new Label(procedimiento.getCodigo_cups() + ""));
		fila.appendChild(new Label(procedimiento.getDescripcion() + ""));
		// fila.appendChild(new Label(procedimiento.getPorcentaje() + ""));
		fila.appendChild(new Label(procedimiento.getSexo() + ""));
		fila.appendChild(new Label(procedimiento.getLimite_inferior() + ""));
		fila.appendChild(new Label(procedimiento.getLimite_superior() + ""));
		fila.appendChild(new Label(
				procedimiento.getPyp().equalsIgnoreCase("S") ? "SI" : "NO"));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(procedimiento);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(procedimiento);
									buscarDatos();
								}
							}
						});
			}
		});
		hbox.appendChild(space);
		hbox.appendChild(img);

		fila.appendChild(hbox);

		return fila;
	}

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //
				//System.Out.Println(" init ");
				Procedimientos procedimiento = new Procedimientos();
				procedimiento.setId_procedimiento(new Long(tbxCodigo_interno
						.getValue()));
				procedimiento.setCodigo_cups(tbxCodigo_cups.getValue());
				procedimiento.setDescripcion(tbxDescripcion.getValue());
//				procedimiento
//						.setPorcentaje((dbxPorcentaje.getValue() != null ? dbxPorcentaje
//								.getValue() : 0.00));
				procedimiento.setClasificacion(lbxClasificacion
						.getSelectedItem().getValue().toString());
//				procedimiento.setCodigo_unidad_funcional(unidad_funcional);
//				procedimiento
//						.setCodigo_tipo_procedimiento(tbxTipoProcedimiento
//								.getRegistroSeleccionado() != null ? ((Unidad_funcional) tbxTipoProcedimiento
//								.getRegistroSeleccionado()).getCodigo() : "");
				procedimiento.setConsulta(chbConsulta.isChecked() ? "S" : "N");
				procedimiento.setQuirurgico(chbQuirurgico.isChecked() ? "S"
						: "N");
				procedimiento.setTipo_quirurgico(lbxTipo_quirurgico
						.getSelectedItem().getValue().toString());
				procedimiento
						.setUrgencias(chbUrgencias.isChecked() ? "S" : "N");
				procedimiento
						.setHospitalizacion(chbHospitalizacion.isChecked() ? "S"
								: "N");
				procedimiento
						.setRecien_nacido(chbRecien_nacido.isChecked() ? "S"
								: "N");
				procedimiento.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				procedimiento.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				procedimiento.setCreacion_user(usuarios.getCodigo().toString());
				procedimiento.setUltimo_user(usuarios.getCodigo().toString());
//				procedimiento.setArea_intervencion(tbxArea_intervencion
//						.getValue());
//				procedimiento.setMeta(chbConsulta.isChecked() ? 0d : null);
//				procedimiento.setGrupo(lbxGrupo.getSelectedItem().getValue()
//						.toString());
				procedimiento.setCodigo_contabilidad(lbxCodigo_contabilidad
						.getSelectedItem().getValue().toString());
				procedimiento.setSexo(lbxSexo.getSelectedItem().getValue()
						.toString());
				procedimiento
						.setLimite_inferior((ibxLimite_inferior.getText() != null ? ibxLimite_inferior
								.getText() : "0"));
				procedimiento
						.setLimite_superior((ibxLimite_superior.getText() != null ? ibxLimite_superior
								.getText() : "0"));
				procedimiento.setPyp(chbPyp.isChecked() ? "S" : "N");
				procedimiento.setCobra_copago(chbCobra_copago.isChecked() ? "S"
						: "N");
				procedimiento.setTipo_procedimiento("");
				procedimiento.setFrecuencia_orden((ibxFrecuencia_orden
						.getValue() != null ? ibxFrecuencia_orden.getValue()
						: 0));
//				procedimiento
//						.setConsulta_especializada(chbConsulta_especializada
//								.isChecked() ? "S" : "N");
//				procedimiento
//						.setConsulta_especializada_med_esp(chbConsulta_especializada_med_esp
//								.isChecked() ? "S" : "N");
				procedimiento.setAut_medi_general(chbAut_medi_general
						.isChecked() ? "S" : "N");
				procedimiento.setAut_medi_especialista(chbAut_medi_especialista
						.isChecked() ? "S" : "N");
				procedimiento.setNivel(lbxNivel.getSelectedItem().getValue()
						.toString());
				procedimiento.setEs_grupo("N");
				procedimiento.setEditable(true);
				procedimiento.setExcepcion_nivel(Utilidades
						.getNivelesExcepcion(lbxNiveles));

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getProcedimientosService().crear(
							procedimiento);
					accionForm(true, "registrar");
				} else {
					int result = getServiceLocator().getProcedimientosService()
							.actualizar(procedimiento);
					if (result == 0) {
						throw new ValidacionRunTimeException(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				Messagebox
						.show("Los datos se guardaron satisfactoriamente",
								"Informacion ..", Messagebox.OK,
								Messagebox.INFORMATION);

			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Procedimientos procedimiento = (Procedimientos) obj;
		try {
			tbxCodigo_interno.setReadonly(true);
			tbxCodigo_interno.setValue(procedimiento.getId_procedimiento()+"");
			tbxCodigo_cups.setValue(procedimiento.getCodigo_cups());
			tbxDescripcion.setValue(procedimiento.getDescripcion());
			for (int i = 0; i < lbxClasificacion.getItemCount(); i++) {
				Listitem listitem = lbxClasificacion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(procedimiento.getClasificacion())) {
					listitem.setSelected(true);
					break;
				}
			}
			chbConsulta.setChecked(procedimiento.getConsulta()
					.equalsIgnoreCase("S"));
			chbQuirurgico.setChecked(procedimiento.getQuirurgico()
					.equalsIgnoreCase("S"));
			for (int i = 0; i < lbxTipo_quirurgico.getItemCount(); i++) {
				Listitem listitem = lbxTipo_quirurgico.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(procedimiento.getTipo_quirurgico())) {
					listitem.setSelected(true);
					break;
				}
			}
		
			chbUrgencias.setChecked(procedimiento.getUrgencias()
					.equalsIgnoreCase("S"));
			chbHospitalizacion.setChecked(procedimiento.getHospitalizacion()
					.equalsIgnoreCase("S"));
			chbRecien_nacido.setChecked(procedimiento.getRecien_nacido()
					.equalsIgnoreCase("S"));
		
			
			
			for (int i = 0; i < lbxCodigo_contabilidad.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_contabilidad.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(procedimiento.getCodigo_contabilidad())) {
					listitem.setSelected(true);
					break;
				}
			}
			for (int i = 0; i < lbxSexo.getItemCount(); i++) {
				Listitem listitem = lbxSexo.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(procedimiento.getSexo())) {
					listitem.setSelected(true);
					break;
				}
			}

			
			ibxLimite_inferior.setText(procedimiento.getLimite_inferior());
			ibxLimite_superior.setText(procedimiento.getLimite_superior());
			chbPyp.setChecked(procedimiento.getPyp().equalsIgnoreCase("S"));
			chbCobra_copago.setChecked(procedimiento.getCobra_copago()
					.equalsIgnoreCase("S"));
			for (int i = 0; i < lbxTipo_procedimiento.getItemCount(); i++) {
				Listitem listitem = lbxTipo_procedimiento.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(procedimiento.getTipo_procedimiento())) {
					listitem.setSelected(true);
					break;
				}
			}
			ibxFrecuencia_orden.setValue(procedimiento.getFrecuencia_orden());
			
			chbAut_medi_general.setChecked(procedimiento.getAut_medi_general()
					.equals("S"));
			chbAut_medi_especialista.setChecked(procedimiento
					.getAut_medi_especialista().equals("S"));
			Utilidades.setNivelesExcepcion(procedimiento.getExcepcion_nivel(),
					lbxNiveles);
			Utilidades.setValueFrom(lbxNivel, procedimiento.getNivel());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Procedimientos procedimiento = (Procedimientos) obj;
		try {
			int result = getServiceLocator().getProcedimientosService()
					.eliminar(procedimiento);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			
			log.error(e.getMessage(), e);
			Messagebox
					.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
							"Error !!", Messagebox.OK, Messagebox.ERROR);
		} catch (RuntimeException r) {
			log.error(r.getMessage(), r);
			Messagebox.show(r.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

}
