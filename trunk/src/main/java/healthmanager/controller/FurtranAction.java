/*
 * furtranAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo: 	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Furtran;
import healthmanager.modelo.bean.Ips;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.FurtranService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.IpsService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.AbstractComponent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
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
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class FurtranAction extends ZKWindow {

	private FurtranService furtranService;

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
	private Textbox tbxConsecutivo;
	@View
	private Datebox dtbxFecha_entrega;
	@View
	private Textbox tbxRadicado_anterior;
	@View
	private Checkbox chbRespuesta_glosa;
	@View
	private Textbox tbxRadicado_nuevo;
	@View
	private Textbox tbxNombre_empresa;
	@View
	private Textbox tbxCodigo_habilitacion;
	@View
	private Textbox tbxNombre_persona1;
	@View
	private Textbox tbxNombre_persona2;
	@View
	private Textbox tbxApellido_persona1;
	@View
	private Textbox tbxApellido_persona2;
	@View
	private Listbox lbxTipo_identificacion;
	@View
	private Textbox tbxNro_identificacion;
	@View
	private Radiogroup rdbServicio_empresa;
	@View
	private Radiogroup rdbServicio_natural;
	@View
	private Textbox tbxOtro_servicio;
	@View
	private Textbox tbxPlaca;
	@View
	private Textbox tbxDireccion;
	@View
	private Intbox ibxTelefono;
	@View
	private Textbox tbxDepartamento;
	@View
	private Textbox tbxMunicipio;
	@View
	private Listbox lbxTipo_identificacion_permitido;
	@View
	private Listbox lbxTipo_doc_victima1;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_victima1;
	@View
	private Textbox tbxNombre1_victima1;
	@View
	private Textbox tbxNombre2_victima1;
	@View
	private Textbox tbxApellido1_victima1;
	@View
	private Textbox tbxApellido2_victima1;
	@View
	private Listbox lbxTipo_doc_victima2;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_victima2;
	@View
	private Textbox tbxNombre1_victima2;
	@View
	private Textbox tbxNombre2_victima2;
	@View
	private Textbox tbxApellido1_victima2;
	@View
	private Textbox tbxApellido2_victima2;
	@View
	private Listbox lbxTipo_doc_victima3;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_victima3;
	@View
	private Textbox tbxNombre1_victima3;
	@View
	private Textbox tbxNombre2_victima3;
	@View
	private Textbox tbxApellido1_victima3;
	@View
	private Textbox tbxApellido2_victima3;
	@View
	private Listbox lbxTipo_doc_victima4;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_victima4;
	@View
	private Textbox tbxNombre1_victima4;
	@View
	private Textbox tbxNombre2_victima4;
	@View
	private Textbox tbxApellido1_victima4;
	@View
	private Textbox tbxApellido2_victima4;
	@View
	private Listbox lbxTipo_doc_victima5;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_victima5;
	@View
	private Textbox tbxNombre1_victima5;
	@View
	private Textbox tbxNombre2_victima5;
	@View
	private Textbox tbxApellido1_victima5;
	@View
	private Textbox tbxApellido2_victima5;
	@View
	private Textbox tbxDireccion_victima;
	@View
	private Radiogroup rdbZona_victima;
	@View
	private Listbox lbxDepartamento_victima;
	@View
	private Listbox lbxMunicipio_victima;
	@View
	private Datebox dtbxFecha_traslado;
	@View
	private Timebox tbHora_traslado;

	@View
	private Textbox tbxNit;
	@View
	private Textbox tbxDireccion_traslado;
	@View
	private Intbox ibxTelefono_traslado;
	@View
	private Textbox tbxDepartamento_traslado;
	@View
	private Textbox tbxMunicipio_traslado;
	@View
	private Textbox tbxDepartamento_traslado_nombre;
	@View
	private Textbox tbxMunicipio_traslado_nombre;

	@View
	private Textbox tbxDepartamento_nombre;
	@View
	private Textbox tbxMunicipio_nombre;

	// Bandbox eps
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCod_habilitacion_ips;
	@View
	private Textbox tbxNombre_ips;
	@View
	private Toolbarbutton btnLimpiarFurtran;

	private final String[] idsExcluyentes = {};

	@View
	private Toolbarbutton btImprimir;

	@View
	private Row rowOtro;
	@View
	private Label lbOtroServicio;

//	private Furtran furtran;
//	private Admision admision_seleccionada;

	@View
	private Toolbarbutton btnLimpiarPaciente;
	@View
	private Toolbarbutton btnLimpiarPaciente2;
	@View
	private Toolbarbutton btnLimpiarPaciente3;
	@View
	private Toolbarbutton btnLimpiarPaciente4;
	@View
	private Toolbarbutton btnLimpiarPaciente5;
	private String identificacion_paciente;

	public static final String PARAM_PACIENTE = "PARAM_paci";

	private String FACTURACION_RIPS;

	@Override
	public void params(Map<String, Object> map) {
		identificacion_paciente = (String) map.get("nro_identificacion");
		FACTURACION_RIPS = (String) map.get("FACTURACION_RIPS");
		//log.info("Paciente: " + identificacion_paciente);

	}

	@Override
	public void init() {
		listarCombos();
		consultarEmpresa();
		parametrizarBandbox();

		cargarDatosModuloFactura(identificacion_paciente);
	}

	private void cargarDatosModuloFactura(String nro_identificacion) {

		tbxNro_victima1.setReadonly(true);
		btnLimpiarPaciente.setVisible(false);

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(empresa.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(nro_identificacion);
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null) {
			tbxNro_victima1.setValue(paciente.getNro_identificacion());
			tbxNombre1_victima1.setValue(paciente.getNombre1());
			tbxNombre2_victima1.setValue(paciente.getNombre2());
			tbxApellido1_victima1.setValue(paciente.getApellido1());
			tbxApellido2_victima1.setValue(paciente.getApellido2());

			for (int i = 0; i < lbxTipo_doc_victima1.getItemCount(); i++) {
				Listitem listitem = lbxTipo_doc_victima1.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(paciente.getTipo_identificacion())) {
					listitem.setSelected(true);
					i = lbxTipo_doc_victima1.getItemCount();
				}
			}

			tbxNro_victima1.setReadonly(true);
			tbxNombre1_victima1.setReadonly(true);
			tbxNombre2_victima1.setReadonly(true);
			tbxApellido1_victima1.setReadonly(true);
			tbxApellido2_victima1.setReadonly(true);
			lbxTipo_doc_victima1.setDisabled(true);

			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);
		} else {
			groupboxConsulta.setVisible(true);
			groupboxEditar.setVisible(false);
		}
	}

	public void parametrizarBandbox() {
		parametrizarBandboxPaciente();
		parametrizarBandboxPaciente2();
		parametrizarBandboxPaciente3();
		parametrizarBandboxPaciente4();
		parametrizarBandboxPaciente5();
	}

	public void listarCombos() {
		listarParameter();

		Utilidades.listarElementoListbox(lbxTipo_identificacion, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_identificacion_permitido,
				true, getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_doc_victima1, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_doc_victima2, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_doc_victima3, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_doc_victima4, true,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxTipo_doc_victima5, true,
				getServiceLocator());
		Utilidades.listarDepartamentos(lbxDepartamento_victima, true,
				getServiceLocator());
		Utilidades.listarMunicipios(lbxMunicipio_victima,
				lbxDepartamento_victima, getServiceLocator());
		parametrizarBandboxEps();
	}

	private void parametrizarBandboxPaciente() {
		tbxNro_victima1.inicializar(tbxNombre1_victima1, btnLimpiarPaciente);
		tbxNro_victima1
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado", 
								"limit 25 offset 0");

						return FurtranAction.this.getServiceLocator()
								.getPacienteService().listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro.getNombre1());
						tbxNombre2_victima1.setValue(registro.getNombre2());
						tbxApellido1_victima1.setValue(registro.getApellido1());
						tbxApellido2_victima1.setValue(registro.getApellido2());
						//log.info("==> tipo id "
								//+ registro.getTipo_identificacion());
						if (registro.getTipo_identificacion().equals("NI")) {
							lbxTipo_doc_victima1.setSelectedIndex(5);
						} else if (registro.getTipo_identificacion().equals(
								"CC")) {
							lbxTipo_doc_victima1.setSelectedIndex(2);
						} else if (registro.getTipo_identificacion().equals(
								"PA")) {
							lbxTipo_doc_victima1.setSelectedIndex(6);
						} else if (registro.getTipo_identificacion().equals(
								"RC")) {
							lbxTipo_doc_victima1.setSelectedIndex(7);
						} else if (registro.getTipo_identificacion().equals(
								"TI")) {
							lbxTipo_doc_victima1.setSelectedIndex(8);
						} else if (registro.getTipo_identificacion().equals(
								"AS")) {
							lbxTipo_doc_victima1.setSelectedIndex(1);
						} else if (registro.getTipo_identificacion().equals(
								"MS")) {
							lbxTipo_doc_victima1.setSelectedIndex(4);
						} else if (registro.getTipo_identificacion().equals(
								"UN")) {
							lbxTipo_doc_victima1.setSelectedIndex(9);
						}

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbxNro_victima1.setValue("");
						tbxNombre1_victima1.setValue("");
						tbxNombre2_victima1.setValue("");
						tbxApellido1_victima1.setValue("");
						tbxApellido2_victima1.setValue("");
						lbxTipo_doc_victima1.setSelectedIndex(0);
					}
				});
	}

	private void parametrizarBandboxPaciente2() {
		tbxNro_victima2.inicializar(tbxNombre1_victima2, btnLimpiarPaciente2);
		tbxNro_victima2
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado", 
								"limit 25 offset 0");

						return FurtranAction.this.getServiceLocator()
								.getPacienteService().listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro.getNombre1());
						tbxNombre2_victima2.setValue(registro.getNombre2());
						tbxApellido1_victima2.setValue(registro.getApellido1());
						tbxApellido2_victima2.setValue(registro.getApellido2());
						//log.info("==> tipo id "
								//+ registro.getTipo_identificacion());
						if (registro.getTipo_identificacion().equals("NI")) {
							lbxTipo_doc_victima2.setSelectedIndex(5);
						} else if (registro.getTipo_identificacion().equals(
								"CC")) {
							lbxTipo_doc_victima2.setSelectedIndex(2);
						} else if (registro.getTipo_identificacion().equals(
								"PA")) {
							lbxTipo_doc_victima2.setSelectedIndex(6);
						} else if (registro.getTipo_identificacion().equals(
								"RC")) {
							lbxTipo_doc_victima2.setSelectedIndex(7);
						} else if (registro.getTipo_identificacion().equals(
								"TI")) {
							lbxTipo_doc_victima2.setSelectedIndex(8);
						} else if (registro.getTipo_identificacion().equals(
								"AS")) {
							lbxTipo_doc_victima2.setSelectedIndex(1);
						} else if (registro.getTipo_identificacion().equals(
								"MS")) {
							lbxTipo_doc_victima2.setSelectedIndex(4);
						} else if (registro.getTipo_identificacion().equals(
								"UN")) {
							lbxTipo_doc_victima2.setSelectedIndex(9);
						}

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbxNro_victima2.setValue("");
						tbxNombre1_victima2.setValue("");
						tbxNombre2_victima2.setValue("");
						tbxApellido1_victima2.setValue("");
						tbxApellido2_victima2.setValue("");
						lbxTipo_doc_victima2.setSelectedIndex(0);
					}
				});
	}

	private void parametrizarBandboxPaciente3() {
		tbxNro_victima3.inicializar(tbxNombre1_victima3, btnLimpiarPaciente3);
		tbxNro_victima3
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado", 
								"limit 25 offset 0");

						return FurtranAction.this.getServiceLocator()
								.getPacienteService().listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro.getNombre1());
						tbxNombre2_victima3.setValue(registro.getNombre2());
						tbxApellido1_victima3.setValue(registro.getApellido1());
						tbxApellido2_victima3.setValue(registro.getApellido2());
						//log.info("==> tipo id "
								//+ registro.getTipo_identificacion());
						if (registro.getTipo_identificacion().equals("NI")) {
							lbxTipo_doc_victima3.setSelectedIndex(5);
						} else if (registro.getTipo_identificacion().equals(
								"CC")) {
							lbxTipo_doc_victima3.setSelectedIndex(2);
						} else if (registro.getTipo_identificacion().equals(
								"PA")) {
							lbxTipo_doc_victima3.setSelectedIndex(6);
						} else if (registro.getTipo_identificacion().equals(
								"RC")) {
							lbxTipo_doc_victima3.setSelectedIndex(7);
						} else if (registro.getTipo_identificacion().equals(
								"TI")) {
							lbxTipo_doc_victima3.setSelectedIndex(8);
						} else if (registro.getTipo_identificacion().equals(
								"AS")) {
							lbxTipo_doc_victima3.setSelectedIndex(1);
						} else if (registro.getTipo_identificacion().equals(
								"MS")) {
							lbxTipo_doc_victima3.setSelectedIndex(4);
						} else if (registro.getTipo_identificacion().equals(
								"UN")) {
							lbxTipo_doc_victima3.setSelectedIndex(9);
						}

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbxNro_victima3.setValue("");
						tbxNombre1_victima3.setValue("");
						tbxNombre2_victima3.setValue("");
						tbxApellido1_victima3.setValue("");
						tbxApellido2_victima3.setValue("");
						lbxTipo_doc_victima3.setSelectedIndex(0);
					}
				});
	}

	private void parametrizarBandboxPaciente4() {
		tbxNro_victima4.inicializar(tbxNombre1_victima4, btnLimpiarPaciente4);
		tbxNro_victima4
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado", 
								"limit 25 offset 0");

						return FurtranAction.this.getServiceLocator()
								.getPacienteService().listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro.getNombre1());
						tbxNombre2_victima4.setValue(registro.getNombre2());
						tbxApellido1_victima4.setValue(registro.getApellido1());
						tbxApellido2_victima4.setValue(registro.getApellido2());
						//log.info("==> tipo id "
								//+ registro.getTipo_identificacion());
						if (registro.getTipo_identificacion().equals("NI")) {
							lbxTipo_doc_victima4.setSelectedIndex(5);
						} else if (registro.getTipo_identificacion().equals(
								"CC")) {
							lbxTipo_doc_victima4.setSelectedIndex(2);
						} else if (registro.getTipo_identificacion().equals(
								"PA")) {
							lbxTipo_doc_victima4.setSelectedIndex(6);
						} else if (registro.getTipo_identificacion().equals(
								"RC")) {
							lbxTipo_doc_victima4.setSelectedIndex(7);
						} else if (registro.getTipo_identificacion().equals(
								"TI")) {
							lbxTipo_doc_victima4.setSelectedIndex(8);
						} else if (registro.getTipo_identificacion().equals(
								"AS")) {
							lbxTipo_doc_victima4.setSelectedIndex(1);
						} else if (registro.getTipo_identificacion().equals(
								"MS")) {
							lbxTipo_doc_victima4.setSelectedIndex(4);
						} else if (registro.getTipo_identificacion().equals(
								"UN")) {
							lbxTipo_doc_victima4.setSelectedIndex(9);
						}

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbxNro_victima4.setValue("");
						tbxNombre1_victima4.setValue("");
						tbxNombre2_victima4.setValue("");
						tbxApellido1_victima4.setValue("");
						tbxApellido2_victima4.setValue("");
						lbxTipo_doc_victima4.setSelectedIndex(0);
					}
				});
	}

	private void parametrizarBandboxPaciente5() {
		tbxNro_victima5.inicializar(tbxNombre1_victima3, btnLimpiarPaciente5);
		tbxNro_victima5
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

					@Override
					public void renderListitem(Listitem listitem,
							Paciente registro) {
						listitem.setValue(registro);

						Listcell listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getTipo_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro
								.getNro_identificacion() + ""));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getNombre1()
								+ " " + registro.getNombre2()));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(registro.getApellido1()
								+ " " + registro.getApellido2()));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Paciente> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {

						parametros.put("codigo_empresa",
								empresa.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");

						parametros.put("limite_paginado", 
								"limit 25 offset 0");

						return FurtranAction.this.getServiceLocator()
								.getPacienteService().listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {

						bandbox.setValue(registro.getNro_identificacion());
						textboxInformacion.setValue(registro.getNombre1());
						tbxNombre2_victima5.setValue(registro.getNombre2());
						tbxApellido1_victima5.setValue(registro.getApellido1());
						tbxApellido2_victima5.setValue(registro.getApellido2());
						//log.info("==> tipo id "
								//+ registro.getTipo_identificacion());
						if (registro.getTipo_identificacion().equals("NI")) {
							lbxTipo_doc_victima5.setSelectedIndex(5);
						} else if (registro.getTipo_identificacion().equals(
								"CC")) {
							lbxTipo_doc_victima5.setSelectedIndex(2);
						} else if (registro.getTipo_identificacion().equals(
								"PA")) {
							lbxTipo_doc_victima5.setSelectedIndex(6);
						} else if (registro.getTipo_identificacion().equals(
								"RC")) {
							lbxTipo_doc_victima5.setSelectedIndex(7);
						} else if (registro.getTipo_identificacion().equals(
								"TI")) {
							lbxTipo_doc_victima5.setSelectedIndex(8);
						} else if (registro.getTipo_identificacion().equals(
								"AS")) {
							lbxTipo_doc_victima5.setSelectedIndex(1);
						} else if (registro.getTipo_identificacion().equals(
								"MS")) {
							lbxTipo_doc_victima5.setSelectedIndex(4);
						} else if (registro.getTipo_identificacion().equals(
								"UN")) {
							lbxTipo_doc_victima5.setSelectedIndex(9);
						}

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbxNro_victima5.setValue("");
						tbxNombre1_victima5.setValue("");
						tbxNombre2_victima5.setValue("");
						tbxApellido1_victima5.setValue("");
						tbxApellido2_victima5.setValue("");
						lbxTipo_doc_victima5.setSelectedIndex(0);
					}
				});
	}

	private void parametrizarBandboxEps() {
		tbxCod_habilitacion_ips.inicializar(tbxNombre_ips, btnLimpiarFurtran);
		tbxCod_habilitacion_ips
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Ips>() {

					@Override
					public void renderListitem(Listitem listitem, Ips registro) {
						listitem.appendChild(new Listcell(registro
								.getCodigo_ips()));
						listitem.appendChild(new Listcell(registro.getNombre()));
					}

					@Override
					public List<Ips> listarRegistros(String valorBusqueda,
							Map<String, Object> parametros) {
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						return getServiceLocator()
								.getServicio(IpsService.class).listar(
										parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Ips registro) {
						bandbox.setValue(registro.getCodigo_ips());
						textboxInformacion.setValue(registro.getNombre());

						Departamentos departamentos = new Departamentos();
						departamentos.setCodigo(registro.getCodigo_dpto());
						departamentos = getServiceLocator()
								.getDepartamentosService().consultar(
										departamentos);

						Municipios municipios = new Municipios();
						municipios.setCoddep(departamentos.getCodigo());
						municipios.setCodigo(registro.getCodigo_mun());
						municipios = getServiceLocator().getMunicipiosService()
								.consultar(municipios);
						tbxNit.setValue(registro.getNro_identificacion());
						tbxDepartamento_traslado_nombre.setValue(departamentos
								.getNombre());
						tbxDepartamento_traslado.setValue(departamentos
								.getCodigo());
						tbxMunicipio_traslado_nombre.setValue(municipios
								.getNombre());
						tbxMunicipio_traslado.setValue(municipios.getCodigo());
						tbxDireccion_traslado.setValue(registro.getDireccion());
						ibxTelefono_traslado.setValue((registro.getTelefono() != null && !registro
								.getTelefono().isEmpty()) ? Integer
								.parseInt(registro.getTelefono()) : null);
						//log.info("====> nombre " + tbxNombre_ips.getValue());

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						tbxNit.setValue("");
						tbxDepartamento_traslado_nombre.setValue("");
						tbxDepartamento_traslado.setValue("");
						tbxMunicipio_traslado_nombre.setValue("");
						tbxMunicipio_traslado.setValue("");
						tbxDireccion_traslado.setValue("");
						ibxTelefono_traslado.setValue(0);
					}
				});

	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Documento");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre_empresa||' '||nombre_persona1||' '||nombre_persona2||' '||apellido_persona1||' '||apellido_persona2");
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
			btImprimir.setVisible(true);
		} else {
			limpiarDatos();
			consultarEmpresa();
			btImprimir.setVisible(false);
		}
		tbxAccion.setValue(accion);
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		boolean valida = true;

		FormularioUtil.validarCamposObligatorios(tbxRadicado_nuevo,
				lbxTipo_identificacion, tbxNro_identificacion, tbxPlaca,
				ibxTelefono, tbxDepartamento, tbxDireccion,
				tbxDireccion_victima, lbxDepartamento_victima);

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

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");

			furtranService.setLimit("limit 25 offset 0");

			List<Furtran> lista_datos = furtranService.listar(parameters);
			rowsResultado.getChildren().clear();

			for (Furtran furtran : lista_datos) {
				rowsResultado.appendChild(crearFilas(furtran, this));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Furtran furtran = (Furtran) objeto;

		Elemento elemento = new Elemento();
		elemento.setCodigo(furtran.getTipo_doc_victima1());
		elemento.setTipo("tipo_id");
		elemento = getServiceLocator().getElementoService().consultar(elemento);

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(elemento.getDescripcion() + ""));
		fila.appendChild(new Label(furtran.getNro_victima1() + ""));
		fila.appendChild(new Label(furtran.getNombre1_victima1() + " "
				+ furtran.getNombre2_victima1() + " "
				+ furtran.getApellido1_victima1() + " "
				+ furtran.getApellido2_victima1()));

		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy hh:mm a")
				.format(furtran.getFecha_creacion())));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(furtran);
			}
		});

		hbox.appendChild(space);
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/print_ico.gif");
		img.setTooltiptext("Imprimir");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				imprimir(furtran);
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
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {
				// Cargamos los componentes //

				Furtran furtran = new Furtran();
				furtran.setCodigo_empresa(empresa.getCodigo_empresa());
				furtran.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				furtran.setConsecutivo(tbxConsecutivo.getValue());
				furtran.setFecha_creacion(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				furtran.setFecha_entrega(new Timestamp(dtbxFecha_entrega
						.getValue().getTime()));
				furtran.setRadicado_anterior(tbxRadicado_anterior.getValue());
				furtran.setRespuesta_glosa(chbRespuesta_glosa.isChecked());
				furtran.setRadicado_nuevo(tbxRadicado_nuevo.getValue());
				furtran.setNombre_empresa(tbxNombre_empresa.getValue());
				furtran.setCodigo_habilitacion(tbxCodigo_habilitacion
						.getValue());
				furtran.setNombre_persona1(tbxNombre_persona1.getValue());
				furtran.setNombre_persona2(tbxNombre_persona2.getValue());
				furtran.setApellido_persona1(tbxApellido_persona1.getValue());
				furtran.setApellido_persona2(tbxApellido_persona2.getValue());
				furtran.setTipo_identificacion(lbxTipo_identificacion
						.getSelectedItem().getValue().toString());
				furtran.setNro_identificacion(tbxNro_identificacion.getValue());
				furtran.setServicio_empresa(rdbServicio_empresa
						.getSelectedItem().getValue().toString());
				furtran.setServicio_natural(rdbServicio_natural
						.getSelectedItem().getValue().toString());
				furtran.setOtro_servicio(tbxOtro_servicio.getValue());
				furtran.setPlaca(tbxPlaca.getValue());
				furtran.setDireccion(tbxDireccion.getValue());
				furtran.setTelefono((ibxTelefono.getValue() != null ? ibxTelefono
						.getValue() + ""
						: ""));
				furtran.setDepartamento(tbxDepartamento.getValue());
				furtran.setMunicipio(tbxMunicipio.getValue());
				furtran.setTipo_identificacion_permitido(lbxTipo_identificacion_permitido
						.getSelectedItem().getValue().toString());
				furtran.setTipo_doc_victima1(lbxTipo_doc_victima1
						.getSelectedItem().getValue().toString());
				furtran.setNro_victima1(tbxNro_victima1.getValue());
				furtran.setNombre1_victima1(tbxNombre1_victima1.getValue());
				furtran.setNombre2_victima1(tbxNombre2_victima1.getValue());
				furtran.setApellido1_victima1(tbxApellido1_victima1.getValue());
				furtran.setApellido2_victima1(tbxApellido2_victima1.getValue());
				furtran.setTipo_doc_victima2(lbxTipo_doc_victima2
						.getSelectedItem().getValue().toString());
				furtran.setNro_victima2(tbxNro_victima2.getValue());
				furtran.setNombre1_victima2(tbxNombre1_victima2.getValue());
				furtran.setNombre2_victima2(tbxNombre2_victima2.getValue());
				furtran.setApellido1_victima2(tbxApellido1_victima2.getValue());
				furtran.setApellido2_victima2(tbxApellido2_victima2.getValue());
				furtran.setTipo_doc_victima3(lbxTipo_doc_victima3
						.getSelectedItem().getValue().toString());
				furtran.setNro_victima3(tbxNro_victima3.getValue());
				furtran.setNombre1_victima3(tbxNombre1_victima3.getValue());
				furtran.setNombre2_victima3(tbxNombre2_victima3.getValue());
				furtran.setApellido1_victima3(tbxApellido1_victima3.getValue());
				furtran.setApellido2_victima3(tbxApellido2_victima3.getValue());
				furtran.setTipo_doc_victima4(lbxTipo_doc_victima4
						.getSelectedItem().getValue().toString());
				furtran.setNro_victima4(tbxNro_victima4.getValue());
				furtran.setNombre1_victima4(tbxNombre1_victima4.getValue());
				furtran.setNombre2_victima4(tbxNombre2_victima4.getValue());
				furtran.setApellido1_victima4(tbxApellido1_victima4.getValue());
				furtran.setApellido2_victima4(tbxApellido2_victima4.getValue());
				furtran.setTipo_doc_victima5(lbxTipo_doc_victima5
						.getSelectedItem().getValue().toString());
				furtran.setNro_victima5(tbxNro_victima5.getValue());
				furtran.setNombre1_victima5(tbxNombre1_victima5.getValue());
				furtran.setNombre2_victima5(tbxNombre2_victima5.getValue());
				furtran.setApellido1_victima5(tbxApellido1_victima5.getValue());
				furtran.setApellido2_victima5(tbxApellido2_victima5.getValue());
				furtran.setDireccion_victima(tbxDireccion_victima.getValue());
				furtran.setZona_victima(rdbZona_victima.getSelectedItem()
						.getValue().toString());
				furtran.setDepartamento_victima(lbxDepartamento_victima
						.getSelectedItem().getValue().toString());
				furtran.setMunicipio_victima(lbxMunicipio_victima
						.getSelectedItem().getValue().toString());
				furtran.setFecha_traslado(new Timestamp(dtbxFecha_traslado
						.getValue().getTime()));
				furtran.setHora_traslado(new Timestamp(tbHora_traslado
						.getValue().getTime()));
				furtran.setNombre_ips(tbxNombre_ips.getValue());
				furtran.setNit(tbxNit.getValue());
				furtran.setCod_habilitacion_ips(tbxCod_habilitacion_ips
						.getValue());
				furtran.setDireccion_traslado(tbxDireccion_traslado.getValue());
				furtran.setTelefono_traslado((ibxTelefono_traslado.getValue() != null ? ibxTelefono_traslado
						.getValue() + ""
						: ""));
				furtran.setDepartamento_traslado(tbxDepartamento_traslado
						.getValue());
				furtran.setMunicipio_traslado(tbxMunicipio_traslado.getValue());
				furtran.setCodigo_representante("");
				furtran.setCreacion_date(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				furtran.setUltimo_update(new Timestamp(new GregorianCalendar()
						.getTimeInMillis()));
				furtran.setCreacion_user(usuarios.getCodigo().toString());
				furtran.setDelete_date(null);
				furtran.setUltimo_user(usuarios.getCodigo().toString());
				furtran.setDelete_user(null);

				Map<String, Object> datos = new HashMap<String, Object>();
				datos.put("furtran", furtran);
				datos.put("accion", tbxAccion.getText());

				final Furtran auxFurtran = furtranService.guardar(datos);

				if (FACTURACION_RIPS != null) {
					this.detach();
				} else {
					mostrarDatos(auxFurtran);

					Messagebox
							.show("Los datos se guardaron satisfactoriamente. Desea imprimir el documento? ",
									"impresion",
									Messagebox.YES + Messagebox.NO,
									Messagebox.QUESTION,
									new org.zkoss.zk.ui.event.EventListener<Event>() {
										public void onEvent(Event event)
												throws Exception {
											if ("onYes".equals(event.getName())) {
												// do the thing
												imprimir();
											}
										}
									});
				}

			}

		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			}
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Furtran furtran = (Furtran) obj;
		try {

			btImprimir.setVisible(true);
			tbxConsecutivo.setValue(furtran.getConsecutivo());
			dtbxFecha_entrega.setValue(furtran.getFecha_entrega());
			tbxRadicado_anterior.setValue(furtran.getRadicado_anterior());
			chbRespuesta_glosa.setChecked(furtran.getRespuesta_glosa());
			tbxRadicado_nuevo.setValue(furtran.getRadicado_nuevo());
			tbxNombre_empresa.setValue(furtran.getNombre_empresa());
			tbxCodigo_habilitacion.setValue(furtran.getCodigo_habilitacion());
			tbxNombre_persona1.setValue(furtran.getNombre_persona1());
			tbxNombre_persona2.setValue(furtran.getNombre_persona2());
			tbxApellido_persona1.setValue(furtran.getApellido_persona1());
			tbxApellido_persona2.setValue(furtran.getApellido_persona2());
			for (int i = 0; i < lbxTipo_identificacion.getItemCount(); i++) {
				Listitem listitem = lbxTipo_identificacion.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(furtran.getTipo_identificacion())) {
					listitem.setSelected(true);
					i = lbxTipo_identificacion.getItemCount();
				}
			}
			tbxNro_identificacion.setValue(furtran.getNro_identificacion());
			Utilidades.seleccionarRadio(rdbServicio_empresa,
					furtran.getServicio_empresa());
			Utilidades.seleccionarRadio(rdbServicio_natural,
					furtran.getServicio_natural());

			if (furtran.getServicio_natural().equals("O")) {
				rowOtro.setVisible(true);
				lbOtroServicio.setVisible(true);
				tbxOtro_servicio.setVisible(true);
				tbxOtro_servicio.setValue(furtran.getOtro_servicio());
			} else {
				rowOtro.setVisible(false);
				lbOtroServicio.setVisible(false);
				tbxOtro_servicio.setVisible(false);

			}
			tbxPlaca.setValue(furtran.getPlaca());
			tbxDireccion.setValue(furtran.getDireccion());
			ibxTelefono.setValue((furtran.getTelefono() != null && !furtran
					.getTelefono().isEmpty()) ? Integer.parseInt(furtran
					.getTelefono()) : null);

			tbxDepartamento.setValue(furtran.getDepartamento());
			tbxMunicipio.setValue(furtran.getMunicipio());

			for (int i = 0; i < lbxTipo_identificacion_permitido.getItemCount(); i++) {
				Listitem listitem = lbxTipo_identificacion_permitido
						.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(furtran.getTipo_identificacion_permitido())) {
					listitem.setSelected(true);
					i = lbxTipo_identificacion_permitido.getItemCount();
				}
			}
			for (int i = 0; i < lbxTipo_doc_victima1.getItemCount(); i++) {
				Listitem listitem = lbxTipo_doc_victima1.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(furtran.getTipo_doc_victima1())) {
					listitem.setSelected(true);
					i = lbxTipo_doc_victima1.getItemCount();
				}
			}
			tbxNro_victima1.setValue(furtran.getNro_victima1());
			tbxNombre1_victima1.setValue(furtran.getNombre1_victima1());
			tbxNombre2_victima1.setValue(furtran.getNombre2_victima1());
			tbxApellido1_victima1.setValue(furtran.getApellido1_victima1());
			tbxApellido2_victima1.setValue(furtran.getApellido2_victima1());
			for (int i = 0; i < lbxTipo_doc_victima2.getItemCount(); i++) {
				Listitem listitem = lbxTipo_doc_victima2.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(furtran.getTipo_doc_victima2())) {
					listitem.setSelected(true);
					i = lbxTipo_doc_victima2.getItemCount();
				}
			}
			tbxNro_victima2.setValue(furtran.getNro_victima2());
			tbxNombre1_victima2.setValue(furtran.getNombre1_victima2());
			tbxNombre2_victima2.setValue(furtran.getNombre2_victima2());
			tbxApellido1_victima2.setValue(furtran.getApellido1_victima2());
			tbxApellido2_victima2.setValue(furtran.getApellido2_victima2());
			for (int i = 0; i < lbxTipo_doc_victima3.getItemCount(); i++) {
				Listitem listitem = lbxTipo_doc_victima3.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(furtran.getTipo_doc_victima3())) {
					listitem.setSelected(true);
					i = lbxTipo_doc_victima3.getItemCount();
				}
			}
			tbxNro_victima3.setValue(furtran.getNro_victima3());
			tbxNombre1_victima3.setValue(furtran.getNombre1_victima3());
			tbxNombre2_victima3.setValue(furtran.getNombre2_victima3());
			tbxApellido1_victima3.setValue(furtran.getApellido1_victima3());
			tbxApellido2_victima3.setValue(furtran.getApellido2_victima3());
			for (int i = 0; i < lbxTipo_doc_victima4.getItemCount(); i++) {
				Listitem listitem = lbxTipo_doc_victima4.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(furtran.getTipo_doc_victima4())) {
					listitem.setSelected(true);
					i = lbxTipo_doc_victima4.getItemCount();
				}
			}
			tbxNro_victima4.setValue(furtran.getNro_victima4());
			tbxNombre1_victima4.setValue(furtran.getNombre1_victima4());
			tbxNombre2_victima4.setValue(furtran.getNombre2_victima4());
			tbxApellido1_victima4.setValue(furtran.getApellido1_victima4());
			tbxApellido2_victima4.setValue(furtran.getApellido2_victima4());
			for (int i = 0; i < lbxTipo_doc_victima5.getItemCount(); i++) {
				Listitem listitem = lbxTipo_doc_victima5.getItemAtIndex(i);
				if (listitem.getValue().toString()
						.equals(furtran.getTipo_doc_victima5())) {
					listitem.setSelected(true);
					i = lbxTipo_doc_victima5.getItemCount();
				}
			}
			tbxNro_victima5.setValue(furtran.getNro_victima5());
			tbxNombre1_victima5.setValue(furtran.getNombre1_victima5());
			tbxNombre2_victima5.setValue(furtran.getNombre2_victima5());
			tbxApellido1_victima5.setValue(furtran.getApellido1_victima5());
			tbxApellido2_victima5.setValue(furtran.getApellido2_victima5());
			tbxDireccion_victima.setValue(furtran.getDireccion_victima());
			Utilidades.seleccionarRadio(rdbZona_victima,
					furtran.getZona_victima());

			Utilidades.seleccionarListitem(lbxDepartamento_victima,
					furtran.getDepartamento_victima());
			Utilidades.listarMunicipios(lbxMunicipio_victima,
					lbxDepartamento_victima, getServiceLocator());
			Utilidades.seleccionarListitem(lbxMunicipio_victima,
					furtran.getMunicipio_victima());

			dtbxFecha_traslado.setValue(furtran.getFecha_traslado());
			tbHora_traslado.setValue(furtran.getHora_traslado());
			tbxNombre_ips.setValue(furtran.getNombre_ips());
			tbxNit.setValue(furtran.getNit());
			tbxCod_habilitacion_ips.setValue(furtran.getCod_habilitacion_ips());
			tbxDireccion_traslado.setValue(furtran.getDireccion_traslado());
			tbxDepartamento_traslado.setValue(furtran
					.getDepartamento_traslado());
			tbxMunicipio_traslado.setValue(furtran.getMunicipio_traslado());

			Departamentos dpto = new Departamentos();
			dpto.setCodigo(furtran.getDepartamento_traslado());
			dpto = getServiceLocator().getDepartamentosService()
					.consultar(dpto);

			tbxDepartamento_nombre.setValue(dpto.getNombre());

			Municipios municipio = new Municipios();
			municipio.setCodigo(furtran.getMunicipio_traslado());
			municipio.setCoddep(furtran.getDepartamento_traslado());
			municipio = getServiceLocator().getMunicipiosService().consultar(
					municipio);

			tbxMunicipio_nombre.setValue(municipio.getNombre());

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Furtran furtran = (Furtran) obj;
		try {
			int result = furtranService.eliminar(furtran);
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

	public void imprimir() throws Exception {

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Furtran");
		paramRequest.put("empresa", empresa.getCodigo_empresa());
		paramRequest.put("sucursal", sucursal.getCodigo_sucursal());
		paramRequest.put("consecutivo", tbxConsecutivo.getValue());

		//log.info("consecutivo" + tbxConsecutivo.getValue());
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void imprimir(Furtran furtran) throws Exception {

		Map<String, Object> paramRequest = new HashMap<String, Object>();
		paramRequest.put("name_report", "Furtran");
		paramRequest.put("empresa", empresa.getCodigo_empresa());
		paramRequest.put("sucursal", sucursal.getCodigo_sucursal());
		paramRequest.put("consecutivo", furtran.getConsecutivo());

		//log.info("consecutivo" + tbxConsecutivo.getValue());
		Component componente = Executions.createComponents(
				"/pages/printInformes.zul", this, paramRequest);
		final Window window = (Window) componente;
		window.setMode("modal");
		window.setMaximizable(true);
		window.setMaximized(true);
	}

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		Utilidades.listarMunicipios(listboxMun, listboxDpto,
				getServiceLocator());
	}

	public void mostrar_conRadio(ZKWindow form, Radiogroup radiogroup,
			AbstractComponent[] abstractComponents) {
		try {
			String valor = "O";
			FormularioUtil.mostrarComponentes_conRadiogroup(form, radiogroup,
					valor, abstractComponents);

		} catch (Exception e) {
			//  block
			//System.Out.Println("Excepcion loaded");
			e.printStackTrace();
		}
	}

	public void consultarEmpresa() {
		Empresa empresa = new Empresa();
		empresa.setCodigo_empresa(codigo_empresa);
		empresa = getServiceLocator().getServicio(GeneralExtraService.class).consultar(empresa);

		tbxNombre_empresa.setValue(empresa.getNombre_empresa());
		lbxTipo_identificacion.setSelectedIndex(5);
		tbxNro_identificacion.setValue(empresa.getNro_identificacion());
		tbxDireccion.setValue(empresa.getDireccion());
		tbxCodigo_habilitacion.setValue(empresa.getCodigo_empresa());
		ibxTelefono.setValue((empresa.getTelefonos() != null && !empresa
				.getTelefonos().isEmpty()) ? Integer.parseInt(empresa
				.getTelefonos()) : null);
		tbxMunicipio.setValue(empresa.getCodigo_municipio());
		tbxDepartamento.setValue(empresa.getCodigo_dpto());
		Departamentos dpto = new Departamentos();
		dpto.setCodigo(empresa.getCodigo_dpto());
		dpto = getServiceLocator().getDepartamentosService().consultar(dpto);

		tbxDepartamento_nombre.setValue(dpto.getNombre());

		Municipios municipio = new Municipios();
		municipio.setCodigo(empresa.getCodigo_municipio());
		municipio.setCoddep(empresa.getCodigo_dpto());
		municipio = getServiceLocator().getMunicipiosService().consultar(
				municipio);

		tbxMunicipio_nombre.setValue(municipio.getNombre());
	}

}