/*
 * his_cancer_infantilAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.His_cancer_infantil;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.Bandbox;
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
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.locator.ServiceLocatorWeb;
import com.framework.util.Util;

public class His_cancer_infantilAction extends Borderlayout implements
		AfterCompose {

	private static Logger log = Logger
			.getLogger(His_cancer_infantilAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	// private Permisos_sc permisos_sc;

	private Empresa empresa;
	private Sucursal sucursal;
	private Usuarios usuarios;

	private Borderlayout form;

	// Componentes //
	private Listbox lbxParameter;
	private Textbox tbxValue;
	private Textbox tbxAccion;
	private Groupbox groupboxEditar;
	private Groupbox groupboxConsulta;
	private Rows rowsResultado;
	private Grid gridResultado;

	private Textbox tbxCodigo_historia;
	private Datebox dtbxFecha_inicial;
	private Bandbox tbxCodigo_eps;
	private Textbox tbxNombre_eps;
	private Listbox lbxCodigo_dpto;
	private Listbox lbxCodigo_municipio;
	private Textbox tbxAcompanante;
	private Listbox lbxParentesco;

	private Bandbox tbxIdentificacion;
	private Textbox tbxNomPaciente;
	private Textbox tbxTipoIdentificacion;
	private Datebox dbxNacimiento;
	private Textbox tbxEdad;
	private Textbox tbxSexo;

	private Textbox tbxNombre_padre;
	private Bandbox tbxOcupacion_padre;
	private Textbox tbxNombre_madre;
	private Bandbox tbxOcupacion_madre;

	private Textbox tbxMotivo;
	private Textbox tbxEmbarazo;
	private Textbox tbxDuracion_meses;
	private Textbox tbxDuracion_dias;
	private Textbox tbxParto;
	private Textbox tbxDuracion_nacer;
	private Textbox tbxMedida;
	private Textbox tbxProblema;
	private Textbox tbxDesc_problema;
	private Textbox tbxEnfermedades;
	private Listbox lbxFiebre;
	private Listbox lbxDolor_cabeza;
	private Textbox tbxDuracion_dolor;
	private Listbox lbxDolor_cabeza_noche;
	private Listbox lbxVomito;
	private Listbox lbxDolor_huesos;
	private Listbox lbxActividades;
	private Listbox lbxAumento;
	private Listbox lbxCambios;
	private Textbox tbxCambios_cuales;
	private Textbox tbxCambios_cuando;
	private Textbox tbxObservaciones;
	private Listbox lbxPalidez;
	private Checkbox chbOjo_blanco;
	private Checkbox chbEstrabismo;
	private Checkbox chbOjo_dif;
	private Checkbox chbSangre_ojo;
	private Checkbox chbSalido;
	private Checkbox chbDolor;
	private Checkbox chbConsistencia;
	private Checkbox chbEvolucion;
	private Checkbox chbSintomas_neurologicos;
	private Listbox lbxDebilidad;
	private Checkbox chbAsimetria;
	private Checkbox chbEstado_conciencia;
	private Checkbox chbPerdida_equilibrio;
	private Checkbox chbCojea;
	private Checkbox chbAlteracion_vision;
	private Checkbox chbMasa_abdominal;
	private Checkbox chbHepatografia;
	private Checkbox chbVolumen;
	private Radiogroup rdbClasificacion;
	private Textbox tbxTemperatura;
	private Textbox tbxFc;
	private Textbox tbxFr;
	private Textbox tbxTalla;
	private Textbox tbxPeso;
	private Textbox tbxPc;
	private Textbox tbxImc;
	private Textbox tbxAlarma;
	private Textbox tbxConsulta_control;
	private Textbox tbxMedidas_preventiva;
	private Textbox tbxRecomendaciones;
	private Textbox tbxDiagnostico;
	private Textbox tbxCodigo_diagnostico;
	private Textbox tbxTratamiento;
	private Textbox tbxRecomendacion_alimentacion;
	private Textbox tbxEvolucion_servicio;
	private Textbox tbxDireccion;
	private Textbox tbxTelefono;
	private Radiogroup rdbSeleccion;
	private Checkbox chbPetequias;
	private Checkbox chbEquimosis;
	private Checkbox chbSangrado;

//	private String codigo_historia;

	@Override
	public void afterCompose() {
		loadComponents();
		cargarDatosSesion();
		listarCombos();
	}

	public void loadComponents() {
		form = (Borderlayout) this.getFellow("formHis_cancer_infantil");

		lbxParameter = (Listbox) form.getFellow("lbxParameter");
		tbxValue = (Textbox) form.getFellow("tbxValue");
		tbxAccion = (Textbox) form.getFellow("tbxAccion");
		groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
		groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
		rowsResultado = (Rows) form.getFellow("rowsResultado");
		gridResultado = (Grid) form.getFellow("gridResultado");

		tbxCodigo_historia = (Textbox) form.getFellow("tbxCodigo_historia");
		dtbxFecha_inicial = (Datebox) form.getFellow("dtbxFecha_inicial");
		tbxCodigo_eps = (Bandbox) form.getFellow("tbxCodigo_eps");
		tbxNombre_eps = (Textbox) form.getFellow("tbxNombre_eps");
		lbxCodigo_dpto = (Listbox) form.getFellow("lbxCodigo_dpto");
		lbxCodigo_municipio = (Listbox) form.getFellow("lbxCodigo_municipio");
		tbxAcompanante = (Textbox) form.getFellow("tbxAcompanante");
		lbxParentesco = (Listbox) form.getFellow("lbxParentesco");

		tbxIdentificacion = (Bandbox) form.getFellow("tbxIdentificacion");
		tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
		tbxTipoIdentificacion = (Textbox) form
				.getFellow("tbxTipoIdentificacion");
		tbxEdad = (Textbox) form.getFellow("tbxEdad");
		tbxSexo = (Textbox) form.getFellow("tbxSexo");
		dbxNacimiento = (Datebox) form.getFellow("dbxNacimiento");

		tbxOcupacion_padre = (Bandbox) form.getFellow("tbxOcupacion_padre");
		tbxNombre_padre = (Textbox) form.getFellow("tbxNombre_padre");
		tbxOcupacion_madre = (Bandbox) form.getFellow("tbxOcupacion_madre");
		tbxNombre_madre = (Textbox) form.getFellow("tbxNombre_madre");
		tbxMotivo = (Textbox) form.getFellow("tbxMotivo");
		tbxEmbarazo = (Textbox) form.getFellow("tbxEmbarazo");
		tbxDuracion_meses = (Textbox) form.getFellow("tbxDuracion_meses");
		tbxDuracion_dias = (Textbox) form.getFellow("tbxDuracion_dias");
		tbxParto = (Textbox) form.getFellow("tbxParto");
		tbxDuracion_nacer = (Textbox) form.getFellow("tbxDuracion_nacer");
		tbxMedida = (Textbox) form.getFellow("tbxMedida");
		tbxProblema = (Textbox) form.getFellow("tbxProblema");
		tbxDesc_problema = (Textbox) form.getFellow("tbxDesc_problema");
		tbxEnfermedades = (Textbox) form.getFellow("tbxEnfermedades");
		lbxFiebre = (Listbox) form.getFellow("lbxFiebre");
		lbxDolor_cabeza = (Listbox) form.getFellow("lbxDolor_cabeza");
		tbxDuracion_dolor = (Textbox) form.getFellow("tbxDuracion_dolor");
		lbxDolor_cabeza_noche = (Listbox) form
				.getFellow("lbxDolor_cabeza_noche");
		lbxVomito = (Listbox) form.getFellow("lbxVomito");
		lbxDolor_huesos = (Listbox) form.getFellow("lbxDolor_huesos");
		lbxActividades = (Listbox) form.getFellow("lbxActividades");
		lbxAumento = (Listbox) form.getFellow("lbxAumento");
		lbxCambios = (Listbox) form.getFellow("lbxCambios");
		tbxCambios_cuales = (Textbox) form.getFellow("tbxCambios_cuales");
		tbxCambios_cuando = (Textbox) form.getFellow("tbxCambios_cuando");
		tbxObservaciones = (Textbox) form.getFellow("tbxObservaciones");
		lbxPalidez = (Listbox) form.getFellow("lbxPalidez");
		chbOjo_blanco = (Checkbox) form.getFellow("chbOjo_blanco");
		chbEstrabismo = (Checkbox) form.getFellow("chbEstrabismo");
		chbOjo_dif = (Checkbox) form.getFellow("chbOjo_dif");
		chbSangre_ojo = (Checkbox) form.getFellow("chbSangre_ojo");
		chbSalido = (Checkbox) form.getFellow("chbSalido");
		chbDolor = (Checkbox) form.getFellow("chbDolor");
		chbConsistencia = (Checkbox) form.getFellow("chbConsistencia");
		chbEvolucion = (Checkbox) form.getFellow("chbEvolucion");
		chbSintomas_neurologicos = (Checkbox) form
				.getFellow("chbSintomas_neurologicos");
		lbxDebilidad = (Listbox) form.getFellow("lbxDebilidad");
		chbAsimetria = (Checkbox) form.getFellow("chbAsimetria");
		chbEstado_conciencia = (Checkbox) form
				.getFellow("chbEstado_conciencia");
		chbPerdida_equilibrio = (Checkbox) form
				.getFellow("chbPerdida_equilibrio");
		chbCojea = (Checkbox) form.getFellow("chbCojea");
		chbAlteracion_vision = (Checkbox) form
				.getFellow("chbAlteracion_vision");
		chbMasa_abdominal = (Checkbox) form.getFellow("chbMasa_abdominal");
		chbHepatografia = (Checkbox) form.getFellow("chbHepatografia");
		chbVolumen = (Checkbox) form.getFellow("chbVolumen");
		rdbClasificacion = (Radiogroup) form.getFellow("rdbClasificacion");
		tbxTemperatura = (Textbox) form.getFellow("tbxTemperatura");
		tbxFc = (Textbox) form.getFellow("tbxFc");
		tbxFr = (Textbox) form.getFellow("tbxFr");
		tbxTalla = (Textbox) form.getFellow("tbxTalla");
		tbxPeso = (Textbox) form.getFellow("tbxPeso");
		tbxPc = (Textbox) form.getFellow("tbxPc");
		tbxImc = (Textbox) form.getFellow("tbxImc");
		tbxAlarma = (Textbox) form.getFellow("tbxAlarma");
		tbxConsulta_control = (Textbox) form.getFellow("tbxConsulta_control");
		tbxMedidas_preventiva = (Textbox) form
				.getFellow("tbxMedidas_preventiva");
		tbxRecomendaciones = (Textbox) form.getFellow("tbxRecomendaciones");
		tbxDiagnostico = (Textbox) form.getFellow("tbxDiagnostico");
		tbxCodigo_diagnostico = (Textbox) form
				.getFellow("tbxCodigo_diagnostico");
		tbxTratamiento = (Textbox) form.getFellow("tbxTratamiento");
		tbxRecomendacion_alimentacion = (Textbox) form
				.getFellow("tbxRecomendacion_alimentacion");
		tbxEvolucion_servicio = (Textbox) form
				.getFellow("tbxEvolucion_servicio");
		tbxDireccion = (Textbox) form.getFellow("tbxDireccion");
		tbxTelefono = (Textbox) form.getFellow("tbxTelefono");
		rdbSeleccion = (Radiogroup) form.getFellow("rdbSeleccion");
		chbPetequias = (Checkbox) form.getFellow("chbPetequias");
		chbEquimosis = (Checkbox) form.getFellow("chbEquimosis");
		chbSangrado = (Checkbox) form.getFellow("chbSangrado");

	}

	public void initHis_cancer_infantil() throws Exception {
//		HttpServletRequest request = (HttpServletRequest) Executions
//				.getCurrent().getNativeRequest();
		try {
			accionForm(true, "registrar");

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}

	}

	public void cargarDatosSesion() {
		HttpServletRequest request = (HttpServletRequest) Executions
				.getCurrent().getNativeRequest();

		empresa = ServiceLocatorWeb.getEmpresa(request);
		sucursal = ServiceLocatorWeb.getSucursal(request);
		usuarios = ServiceLocatorWeb.getUsuarios(request);
	}

	public void listarCombos() {
		listarParameter();
		listarDepartamentos(lbxCodigo_dpto, true);
		listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
		listarElementoListbox(lbxParentesco, false);
		listarElementoListbox(lbxFiebre, false);
		listarElementoListbox(lbxDolor_cabeza, false);
		listarElementoListbox(lbxDolor_cabeza_noche, false);
		listarElementoListbox(lbxVomito, false);
		listarElementoListbox(lbxDolor_huesos, false);
		listarElementoListbox(lbxActividades, false);
		listarElementoListbox(lbxAumento, false);
		listarElementoListbox(lbxCambios, false);
		listarElementoListbox(lbxPalidez, false);
		listarElementoListbox(lbxDebilidad, false);
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo de la Historia");
//		lbxParameter.appendChild(listitem);
//
//		listitem = new Listitem();
		listitem.setValue("identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

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

	// Metodo para buscar //
	public void buscarDatos() throws Exception {
		try {
			String codigo_empresa = empresa.getCodigo_empresa();
			String codigo_sucursal = sucursal.getCodigo_sucursal();
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			getServiceLocator().getHis_cancer_infantilService().setLimit(
					"limit 25 offset 0");

			List<His_cancer_infantil> lista_datos = getServiceLocator()
					.getHis_cancer_infantilService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (His_cancer_infantil his_cancer_infantil : lista_datos) {
				rowsResultado
						.appendChild(crearFilas(his_cancer_infantil, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final His_cancer_infantil his_cancer_infantil = (His_cancer_infantil) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(his_cancer_infantil.getCodigo_historia()
				+ ""));
		fila.appendChild(new Label(his_cancer_infantil.getIdentificacion() + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {

				mostrarDatos(his_cancer_infantil);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		// img.setVisible((permisos_sc.getPermiso_eliminar()?true:false));
		img.setSrc("/images/eliminar.gif");
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
									eliminarDatos(his_cancer_infantil);
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

				Map datos = new HashMap();

				His_cancer_infantil his_cancer_infantil = new His_cancer_infantil();
				his_cancer_infantil.setCodigo_empresa(empresa
						.getCodigo_empresa());
				his_cancer_infantil.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				his_cancer_infantil.setCodigo_historia(tbxCodigo_historia
						.getValue());
				his_cancer_infantil.setFecha_inicial(new Timestamp(
						dtbxFecha_inicial.getValue().getTime()));
				his_cancer_infantil.setCodigo_eps(tbxCodigo_eps.getValue());
				his_cancer_infantil.setCodigo_dpto(lbxCodigo_dpto
						.getSelectedItem().getValue().toString());
				his_cancer_infantil.setCodigo_municipio(lbxCodigo_municipio
						.getSelectedItem().getValue().toString());
				his_cancer_infantil.setAcompanante(tbxAcompanante.getValue());
				his_cancer_infantil.setParentesco(lbxParentesco
						.getSelectedItem().getValue().toString());
				his_cancer_infantil.setOcupacion_padre(tbxOcupacion_padre
						.getValue());
				his_cancer_infantil.setOcupacion_madre(tbxOcupacion_madre
						.getValue());
				his_cancer_infantil.setMotivo(tbxMotivo.getValue());
				his_cancer_infantil.setEmbarazo(tbxEmbarazo.getValue());
				his_cancer_infantil.setDuracion_meses(tbxDuracion_meses
						.getValue());
				his_cancer_infantil.setDuracion_dias(tbxDuracion_dias
						.getValue());
				his_cancer_infantil.setParto(tbxParto.getValue());
				his_cancer_infantil.setDuracion_nacer(tbxDuracion_nacer
						.getValue());
				his_cancer_infantil.setMedida(tbxMedida.getValue());
				his_cancer_infantil.setProblema("0");
				his_cancer_infantil.setDesc_problema(tbxDesc_problema
						.getValue());
				his_cancer_infantil.setEnfermedades(tbxEnfermedades.getValue());
				his_cancer_infantil.setFiebre(lbxFiebre.getSelectedItem()
						.getValue().toString());
				his_cancer_infantil.setDolor_cabeza(lbxDolor_cabeza
						.getSelectedItem().getValue().toString());
				his_cancer_infantil.setDuracion_dolor(tbxDuracion_dolor
						.getValue());
				his_cancer_infantil.setDolor_cabeza_noche(lbxDolor_cabeza_noche
						.getSelectedItem().getValue().toString());
				his_cancer_infantil.setVomito(lbxVomito.getSelectedItem()
						.getValue().toString());
				his_cancer_infantil.setDolor_huesos(lbxDolor_huesos
						.getSelectedItem().getValue().toString());
				his_cancer_infantil.setActividades(lbxActividades
						.getSelectedItem().getValue().toString());
				his_cancer_infantil.setAumento(lbxAumento.getSelectedItem()
						.getValue().toString());
				his_cancer_infantil.setCambios(lbxCambios.getSelectedItem()
						.getValue().toString());
				his_cancer_infantil.setCambios_cuales(tbxCambios_cuales
						.getValue());
				his_cancer_infantil.setCambios_cuando(tbxCambios_cuando
						.getValue());
				his_cancer_infantil.setObservaciones(tbxObservaciones
						.getValue());
				his_cancer_infantil.setPalidez(lbxPalidez.getSelectedItem()
						.getValue().toString());
				his_cancer_infantil.setOjo_blanco(chbOjo_blanco.isChecked());
				his_cancer_infantil.setEstrabismo(chbEstrabismo.isChecked());
				his_cancer_infantil.setOjo_dif(chbOjo_dif.isChecked());
				his_cancer_infantil.setSangre_ojo(chbSangre_ojo.isChecked());
				his_cancer_infantil.setSalido(chbSalido.isChecked());
				his_cancer_infantil.setDolor(chbDolor.isChecked());
				his_cancer_infantil
						.setConsistencia(chbConsistencia.isChecked());
				his_cancer_infantil.setEvolucion(chbEvolucion.isChecked());
				his_cancer_infantil
						.setSintomas_neurologicos(chbSintomas_neurologicos
								.isChecked());
				his_cancer_infantil.setDebilidad(lbxDebilidad.getSelectedItem()
						.getValue().toString());
				his_cancer_infantil.setAsimetria(chbAsimetria.isChecked());
				his_cancer_infantil.setEstado_conciencia(chbEstado_conciencia
						.isChecked());
				his_cancer_infantil.setPerdida_equilibrio(chbPerdida_equilibrio
						.isChecked());
				his_cancer_infantil.setCojea(chbCojea.isChecked());
				his_cancer_infantil.setAlteracion_vision(chbAlteracion_vision
						.isChecked());
				his_cancer_infantil.setMasa_abdominal(chbMasa_abdominal
						.isChecked());
				his_cancer_infantil
						.setHepatografia(chbHepatografia.isChecked());
				his_cancer_infantil.setVolumen(chbVolumen.isChecked());
				his_cancer_infantil.setClasificacion(rdbClasificacion
						.getSelectedItem().getValue().toString());
				his_cancer_infantil.setTemperatura(tbxTemperatura.getValue());
				his_cancer_infantil.setFc(tbxFc.getValue());
				his_cancer_infantil.setFr(tbxFr.getValue());
				his_cancer_infantil.setTalla(tbxTalla.getValue());
				his_cancer_infantil.setPeso(tbxPeso.getValue());
				his_cancer_infantil.setPc(tbxPc.getValue());
				his_cancer_infantil.setImc(tbxImc.getValue());
				his_cancer_infantil.setAlarma(tbxAlarma.getValue());
				his_cancer_infantil.setConsulta_control(tbxConsulta_control
						.getValue());
				his_cancer_infantil.setMedidas_preventiva(tbxMedidas_preventiva
						.getValue());
				his_cancer_infantil.setRecomendaciones(tbxRecomendaciones
						.getValue());
				his_cancer_infantil.setDiagnostico(tbxDiagnostico.getValue());
				his_cancer_infantil.setCodigo_diagnostico(tbxCodigo_diagnostico
						.getValue());
				his_cancer_infantil.setTratamiento(tbxTratamiento.getValue());
				his_cancer_infantil
						.setRecomendacion_alimentacion(tbxRecomendacion_alimentacion
								.getValue());
				his_cancer_infantil.setEvolucion_servicio(tbxEvolucion_servicio
						.getValue());
				his_cancer_infantil.setIdentificacion(tbxIdentificacion
						.getValue());
				his_cancer_infantil.setDireccion(tbxDireccion.getValue());
				his_cancer_infantil.setTelefono(tbxTelefono.getValue());
				his_cancer_infantil.setSeleccion(rdbSeleccion.getSelectedItem()
						.getValue().toString());
				his_cancer_infantil.setPetequias(chbPetequias.isChecked());
				his_cancer_infantil.setEquimosis(chbEquimosis.isChecked());
				his_cancer_infantil.setSangrado(chbSangrado.isChecked());

				datos.put("codigo_historia", his_cancer_infantil);
				datos.put("accion", tbxAccion.getText());

				his_cancer_infantil = getServiceLocator()
						.getHis_cancer_infantilService().guardar(datos);

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					accionForm(true, "modificar");
				}
				/*
				 * else{ int result =
				 * getServiceLocator().getHis_cancer_infantilService
				 * ().actualizar(his_cancer_infantil); if(result==0){ throw new
				 * Exception(
				 * "Lo sentimos pero los datos a modificar no se encuentran en base de datos"
				 * ); }
				 * 
				 * }
				 */

//				codigo_historia = his_cancer_infantil.getCodigo_historia();

				Messagebox.show("Los datos se guardaron satisfactoriamente",
						"Informacion ..", Messagebox.OK,
						Messagebox.INFORMATION);

			}

		} catch (Exception e) {
			
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		His_cancer_infantil his_cancer_infantil = (His_cancer_infantil) obj;
		try {
			tbxCodigo_historia.setValue(his_cancer_infantil
					.getCodigo_historia());
			dtbxFecha_inicial.setValue(his_cancer_infantil.getFecha_inicial());

			Administradora administradora = new Administradora();
			administradora.setCodigo(his_cancer_infantil.getCodigo_eps());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getCodigo_dpto())) {
					listitem.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

			tbxAcompanante.setValue(his_cancer_infantil.getAcompanante());

			for (int i = 0; i < lbxParentesco.getItemCount(); i++) {
				Listitem listitem = lbxParentesco.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getParentesco())) {
					listitem.setSelected(true);
					i = lbxParentesco.getItemCount();
				}
			}

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(his_cancer_infantil.getCodigo_empresa());
			paciente.setCodigo_sucursal(his_cancer_infantil
					.getCodigo_sucursal());
			paciente.setNro_identificacion(his_cancer_infantil
					.getIdentificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);

			Elemento elemento = new Elemento();
			elemento.setCodigo(paciente.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxIdentificacion.setValue(his_cancer_infantil.getIdentificacion());
			tbxNomPaciente.setValue((paciente != null ? paciente.getNombre1()
					+ " " + paciente.getApellido1() : ""));
			tbxTipoIdentificacion.setValue((paciente != null ? paciente
					.getTipo_identificacion() : ""));
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
					paciente.getUnidad_medidad(), false));
			tbxSexo.setValue((elemento != null ? elemento.getDescripcion() : ""));
			dbxNacimiento.setValue(paciente.getFecha_nacimiento());

			Ocupaciones ocupacion_padre = new Ocupaciones();
			ocupacion_padre.setId(his_cancer_infantil.getOcupacion_padre());
			ocupacion_padre = getServiceLocator().getOcupacionesService()
					.consultar(ocupacion_padre);
			tbxNombre_padre.setValue(ocupacion_padre != null ? ocupacion_padre
					.getNombre() : "");
			tbxOcupacion_padre
					.setValue(ocupacion_padre != null ? ocupacion_padre.getId()
							: "");

			Ocupaciones ocupacion_madre = new Ocupaciones();
			ocupacion_madre.setId(his_cancer_infantil.getOcupacion_madre());
			ocupacion_madre = getServiceLocator().getOcupacionesService()
					.consultar(ocupacion_madre);
			tbxNombre_madre.setValue(ocupacion_madre != null ? ocupacion_madre
					.getNombre() : "");
			tbxOcupacion_madre
					.setValue(ocupacion_madre != null ? ocupacion_madre.getId()
							: "");

			tbxMotivo.setValue(his_cancer_infantil.getMotivo());
			tbxEmbarazo.setValue(his_cancer_infantil.getEmbarazo());
			tbxDuracion_meses.setValue(his_cancer_infantil.getDuracion_meses());
			tbxDuracion_dias.setValue(his_cancer_infantil.getDuracion_dias());
			tbxParto.setValue(his_cancer_infantil.getParto());
			tbxDuracion_nacer.setValue(his_cancer_infantil.getDuracion_nacer());
			tbxMedida.setValue(his_cancer_infantil.getMedida());
			tbxProblema.setValue(his_cancer_infantil.getProblema());
			tbxDesc_problema.setValue(his_cancer_infantil.getDesc_problema());
			tbxEnfermedades.setValue(his_cancer_infantil.getEnfermedades());
			for (int i = 0; i < lbxFiebre.getItemCount(); i++) {
				Listitem listitem = lbxFiebre.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getFiebre())) {
					listitem.setSelected(true);
					i = lbxFiebre.getItemCount();
				}
			}
			for (int i = 0; i < lbxDolor_cabeza.getItemCount(); i++) {
				Listitem listitem = lbxDolor_cabeza.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getDolor_cabeza())) {
					listitem.setSelected(true);
					i = lbxDolor_cabeza.getItemCount();
				}
			}
			tbxDuracion_dolor.setValue(his_cancer_infantil.getDuracion_dolor());
			for (int i = 0; i < lbxDolor_cabeza_noche.getItemCount(); i++) {
				Listitem listitem = lbxDolor_cabeza_noche.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getDolor_cabeza_noche())) {
					listitem.setSelected(true);
					i = lbxDolor_cabeza_noche.getItemCount();
				}
			}
			for (int i = 0; i < lbxVomito.getItemCount(); i++) {
				Listitem listitem = lbxVomito.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getVomito())) {
					listitem.setSelected(true);
					i = lbxVomito.getItemCount();
				}
			}
			for (int i = 0; i < lbxDolor_huesos.getItemCount(); i++) {
				Listitem listitem = lbxDolor_huesos.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getDolor_huesos())) {
					listitem.setSelected(true);
					i = lbxDolor_huesos.getItemCount();
				}
			}
			for (int i = 0; i < lbxActividades.getItemCount(); i++) {
				Listitem listitem = lbxActividades.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getActividades())) {
					listitem.setSelected(true);
					i = lbxActividades.getItemCount();
				}
			}
			for (int i = 0; i < lbxAumento.getItemCount(); i++) {
				Listitem listitem = lbxAumento.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getAumento())) {
					listitem.setSelected(true);
					i = lbxAumento.getItemCount();
				}
			}
			for (int i = 0; i < lbxCambios.getItemCount(); i++) {
				Listitem listitem = lbxCambios.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getCambios())) {
					listitem.setSelected(true);
					i = lbxCambios.getItemCount();
				}
			}
			tbxCambios_cuales.setValue(his_cancer_infantil.getCambios_cuales());
			tbxCambios_cuando.setValue(his_cancer_infantil.getCambios_cuando());
			tbxObservaciones.setValue(his_cancer_infantil.getObservaciones());
			for (int i = 0; i < lbxPalidez.getItemCount(); i++) {
				Listitem listitem = lbxPalidez.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getPalidez())) {
					listitem.setSelected(true);
					i = lbxPalidez.getItemCount();
				}
			}
			chbOjo_blanco.setChecked(his_cancer_infantil.getOjo_blanco());
			chbEstrabismo.setChecked(his_cancer_infantil.getEstrabismo());
			chbOjo_dif.setChecked(his_cancer_infantil.getOjo_dif());
			chbSangre_ojo.setChecked(his_cancer_infantil.getSangre_ojo());
			chbSalido.setChecked(his_cancer_infantil.getSalido());
			chbDolor.setChecked(his_cancer_infantil.getDolor());
			chbConsistencia.setChecked(his_cancer_infantil.getConsistencia());
			chbEvolucion.setChecked(his_cancer_infantil.getEvolucion());
			chbSintomas_neurologicos.setChecked(his_cancer_infantil
					.getSintomas_neurologicos());
			for (int i = 0; i < lbxDebilidad.getItemCount(); i++) {
				Listitem listitem = lbxDebilidad.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(his_cancer_infantil.getDebilidad())) {
					listitem.setSelected(true);
					i = lbxDebilidad.getItemCount();
				}
			}
			chbAsimetria.setChecked(his_cancer_infantil.getAsimetria());
			chbEstado_conciencia.setChecked(his_cancer_infantil
					.getEstado_conciencia());
			chbPerdida_equilibrio.setChecked(his_cancer_infantil
					.getPerdida_equilibrio());
			chbCojea.setChecked(his_cancer_infantil.getCojea());
			chbAlteracion_vision.setChecked(his_cancer_infantil
					.getAlteracion_vision());
			chbMasa_abdominal.setChecked(his_cancer_infantil
					.getMasa_abdominal());
			chbHepatografia.setChecked(his_cancer_infantil.getHepatografia());
			chbVolumen.setChecked(his_cancer_infantil.getVolumen());
			Radio radio = (Radio) rdbClasificacion.getFellow("Clasificacion"
					+ his_cancer_infantil.getClasificacion());
			radio.setChecked(true);
			tbxTemperatura.setValue(his_cancer_infantil.getTemperatura());
			tbxFc.setValue(his_cancer_infantil.getFc());
			tbxFr.setValue(his_cancer_infantil.getFr());
			tbxTalla.setValue(his_cancer_infantil.getTalla());
			tbxPeso.setValue(his_cancer_infantil.getPeso());
			tbxPc.setValue(his_cancer_infantil.getPc());
			tbxImc.setValue(his_cancer_infantil.getImc());
			tbxAlarma.setValue(his_cancer_infantil.getAlarma());
			tbxConsulta_control.setValue(his_cancer_infantil
					.getConsulta_control());
			tbxMedidas_preventiva.setValue(his_cancer_infantil
					.getMedidas_preventiva());
			tbxRecomendaciones.setValue(his_cancer_infantil
					.getRecomendaciones());
			tbxDiagnostico.setValue(his_cancer_infantil.getDiagnostico());
			tbxCodigo_diagnostico.setValue(his_cancer_infantil
					.getCodigo_diagnostico());
			tbxTratamiento.setValue(his_cancer_infantil.getTratamiento());
			tbxRecomendacion_alimentacion.setValue(his_cancer_infantil
					.getRecomendacion_alimentacion());
			tbxEvolucion_servicio.setValue(his_cancer_infantil
					.getEvolucion_servicio());

			tbxDireccion.setValue(his_cancer_infantil.getDireccion());
			tbxTelefono.setValue(his_cancer_infantil.getTelefono());
			Radio radio1 = (Radio) rdbSeleccion.getFellow("Seleccion"
					+ his_cancer_infantil.getSeleccion());
			radio1.setChecked(true);
			chbPetequias.setChecked(his_cancer_infantil.getPetequias());
			chbEquimosis.setChecked(his_cancer_infantil.getEquimosis());
			chbSangrado.setChecked(his_cancer_infantil.getSangrado());

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
		His_cancer_infantil his_cancer_infantil = (His_cancer_infantil) obj;
		try {
			int result = getServiceLocator().getHis_cancer_infantilService()
					.eliminar(his_cancer_infantil);
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

	public ServiceLocatorWeb getServiceLocator() {
		return ServiceLocatorWeb.getInstance();
	}

	public void buscarOcupaciones(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getOcupacionesService().setLimit(
					"limit 25 offset 0");

			List<Ocupaciones> list = getServiceLocator()
					.getOcupacionesService().listar(parameters);

			lbx.getItems().clear();

			for (Ocupaciones dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getId() + ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre()));
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

	public void selectedOcupaciones(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_padre.setValue("");
			tbxOcupacion_padre.setValue("");

		} else {
			Ocupaciones dato = (Ocupaciones) listitem.getValue();
			tbxNombre_padre.setValue(dato.getNombre());
			tbxOcupacion_padre.setValue(dato.getId());

		}
		tbxOcupacion_padre.close();
	}

	public void selectedOcupacion_madre(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_madre.setValue("");
			tbxOcupacion_madre.setValue("");

		} else {
			Ocupaciones dato = (Ocupaciones) listitem.getValue();
			tbxNombre_madre.setValue(dato.getNombre());
			tbxOcupacion_madre.setValue(dato.getId());

		}
		tbxOcupacion_madre.close();
	}

	public void buscarEps(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			getServiceLocator().getOcupacionesService().setLimit(
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
				listcell.appendChild(new Label(dato.getNombre()));
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

	public void selectedEps(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxNombre_eps.setValue("");
			tbxCodigo_eps.setValue("");

		} else {
			Administradora dato = (Administradora) listitem.getValue();
			tbxNombre_eps.setValue(dato.getNombre());
			tbxCodigo_eps.setValue(dato.getCodigo());

		}
		tbxCodigo_eps.close();
	}

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
				.getDepartamentosService().listar(new HashMap());

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
		Map<String, Object> parameters = new HashMap();
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

	public void buscarPaciente(String value, Listbox lbx) throws Exception {
		try {
			Map<String, Object> parameters = new HashMap();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", "%" + value.toUpperCase().trim() + "%");

			parameters.put("limite_paginado", 
					"limit 25 offset 0");

			List<Paciente> list = getServiceLocator().getPacienteService()
					.listar(parameters);

			lbx.getItems().clear();

			for (Paciente dato : list) {

				Listitem listitem = new Listitem();
				listitem.setValue(dato);

				Listcell listcell = new Listcell();
				listcell.appendChild(new Label(dato.getTipo_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNro_identificacion()
						+ ""));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getNombre1() + " "
						+ dato.getNombre2()));
				listitem.appendChild(listcell);

				listcell = new Listcell();
				listcell.appendChild(new Label(dato.getApellido1() + " "
						+ dato.getApellido2()));
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

	public void selectedPaciente(Listitem listitem) {
		if (listitem.getValue() == null) {
			tbxIdentificacion.setValue("");
			tbxNomPaciente.setValue("");
			tbxTipoIdentificacion.setValue("");
			tbxEdad.setValue("");
			tbxSexo.setValue("");
			dbxNacimiento.setValue(null);
			tbxCodigo_eps.setValue("");
			tbxNombre_eps.setValue("");
			tbxDireccion.setValue("");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem1.getValue().toString().equals("")) {
					listitem1.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
				if (listitem1.getValue().toString().equals("")) {
					listitem1.setSelected(true);
					i = lbxCodigo_municipio.getItemCount();
				}
			}

		} else {
			Paciente dato = (Paciente) listitem.getValue();

			Elemento elemento = new Elemento();
			elemento.setCodigo(dato.getSexo());
			elemento.setTipo("sexo");
			elemento = getServiceLocator().getElementoService().consultar(
					elemento);

			tbxIdentificacion.setValue(dato.getNro_identificacion());
			tbxNomPaciente.setValue(dato.getNombre1() + " " + dato.getNombre2()
					+ " " + dato.getApellido1() + " " + dato.getApellido2());
			tbxTipoIdentificacion.setValue(dato.getTipo_identificacion());
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(dato.getFecha_nacimiento()), dato
					.getUnidad_medidad(), false));
			tbxSexo.setValue(elemento != null ? elemento.getDescripcion() : "");
			dbxNacimiento.setValue(dato.getFecha_nacimiento());
			tbxDireccion.setValue(dato.getDireccion());

			Administradora administradora = new Administradora();
			administradora.setCodigo(dato.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			tbxCodigo_eps.setValue(administradora != null ? administradora
					.getCodigo() : "");
			tbxNombre_eps.setValue(administradora != null ? administradora
					.getNombre() : "");

			for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_dpto.getItemAtIndex(i);
				if (listitem1.getValue().toString()
						.equals(dato.getCodigo_dpto())) {
					listitem1.setSelected(true);
					i = lbxCodigo_dpto.getItemCount();
				}
			}

			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
				Listitem listitem1 = lbxCodigo_municipio.getItemAtIndex(i);
				if (listitem1.getValue().toString()
						.equals(dato.getCodigo_municipio())) {
					listitem1.setSelected(true);
					i = lbxCodigo_municipio.getItemCount();
				}
			}
		}

		tbxIdentificacion.close();
	}

}
