package healthmanager.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Hospitalizacion;
import healthmanager.modelo.bean.Recien_nacido;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Prestadores;
import com.framework.util.Util;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
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
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Recien_nacidoAction extends ZKWindow {

	private static Logger log = Logger.getLogger(Recien_nacidoAction.class);

	private static final long serialVersionUID = 2183354871530698149L;

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

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxNro_identificacion;
	@View
	private Textbox tbxNomPaciente;
	@View
	private Toolbarbutton btnLimpiarPaciente;

	@View
	private Textbox tbxNacimiento;
	@View
	private Textbox tbxEdad;
	@View
	private Textbox tbxSexo;
	@View
	private Textbox tbxEstrato;
	@View
	private Textbox tbxTipo_afiliado;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_prestador;
	@View
	private Textbox tbxNomPrestador;
	@View
	private Toolbarbutton btnLimpiarPrestador;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_diagnostico_principal;
	@View
	private Textbox tbxNomDxp;
	@View
	private Toolbarbutton btnLimpiarDxp;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCausa_muerte;
	@View
	private Textbox tbxNomDx5;
	@View
	private Toolbarbutton btnLimpiarDx5;

	@View
	private Textbox tbxNro_factura;
	@View
	private Listbox lbxNro_ingreso;
	@View
	private Datebox dtbxFecha_nacimiento;
	@View
	private Textbox tbxNumero_autorizacion;
	@View
	private Intbox ibxEdad_gestacional;
	@View
	private Radiogroup rdbControl_prenatal;
	@View
	private Listbox lbxSexo;
	@View
	private Intbox ibxPeso;
	@View
	private Datebox dtbxFecha_muerte;

	@View
	private Textbox tbxAseguradora;

	private String FACTURACION_RIPS;

	private Map<String, Object> datos_seleccion = new HashMap<String, Object>();
	private final String[] idsExcluyentes = new String[] {
			"tbxNro_identificacion", "btnLimpiarPaciente", "btCancel",
			"btGuardar", "tbxAccion", "btNew" };

	@Override
	public void init() throws Exception {
		
		parametrizarBandbox();
		listarCombos();
		deshabilitarCampos(true);

		boolean ocultarConsulta = false;
		String nro_ingreso = "";
		String nro_identificacion = "";

		Map parametros = Executions.getCurrent().getArg();
		if (parametros != null) {
			if (parametros.isEmpty()) {
				parametros = null;
			}
		}
		if (parametros != null) {
			ocultarConsulta = (Boolean) parametros.get("ocultarConsulta");
			nro_ingreso = (String) parametros.get("nro_ingreso");
			nro_identificacion = (String) parametros.get("nro_identificacion");

			cargarDatosModuloFactura(nro_ingreso, nro_identificacion,
					ocultarConsulta);
		}
	}

	@Override
	public void params(Map<String, Object> map) {
		FACTURACION_RIPS = (String) map.get("FACTURACION_RIPS");
	}

	private void cargarDatosModuloFactura(String nro_ingreso,
			String nro_identificacion, boolean ocultarConsulta)
			throws Exception {

		accionForm(true, "Registrar");
		tbxNro_identificacion.setDisabled(true);
		btnLimpiarPaciente.setVisible(false);
		lbxNro_ingreso.setDisabled(true);
		ocultarBotoConsultar(ocultarConsulta);

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(empresa.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setNro_identificacion(nro_identificacion);
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		if (paciente != null) {
			tbxNro_identificacion.setValue(paciente.getNro_identificacion());
			tbxNomPaciente.setValue(paciente.getNombreCompleto());
			datos_seleccion.put("sexo", paciente.getSexo());
			datos_seleccion.put("tipo_identificacion",
					paciente.getTipo_identificacion());
			datos_seleccion.put("fecha_nac", new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()));

			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()), "1",
					false));
			tbxSexo.setValue(Utilidades.getNombreElemento(paciente.getSexo(),
					"sexo", Recien_nacidoAction.this));
			tbxEstrato.setValue(paciente.getEstrato());
			tbxTipo_afiliado.setValue(Utilidades.getNombreElemento(
					paciente.getTipo_afiliado(), "tipo_afiliado",
					Recien_nacidoAction.this));
			tbxNacimiento.setValue(new java.text.SimpleDateFormat("dd/MM/yyyy")
					.format(paciente.getFecha_nacimiento()));

			Admision aux2 = new Admision();
			aux2.setCodigo_empresa(empresa.getCodigo_empresa());
			aux2.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			aux2.setNro_identificacion(nro_identificacion);
			aux2.setNro_ingreso(nro_ingreso);
			aux2 = getServiceLocator().getAdmisionService().consultar(aux2);

			if (aux2 != null) {
				// //log.info("aux2: "+aux2);

				Administradora admin = new Administradora();
				admin.setCodigo_empresa(aux2.getCodigo_empresa());
				admin.setCodigo_sucursal(aux2.getCodigo_sucursal());
				admin.setCodigo(aux2.getCodigo_administradora());
				admin = getServiceLocator().getAdministradoraService()
						.consultar(admin);

				lbxNro_ingreso.getItems().clear();
				Listitem listitem = new Listitem();
				listitem.setSelected(true);
				listitem.setLabel(aux2.getNro_ingreso() + " -- "
						+ (admin != null ? admin.getNombre() : ""));
				listitem.setValue(aux2);
				cargarAdmisiones(aux2);
				lbxNro_ingreso.appendChild(listitem);
				btnLimpiarPaciente.setVisible(false);

			}
		}
	}

	private void ocultarBotoConsultar(boolean sw) {
		
		if (!sw) {
			((Button) groupboxEditar.getFellow("btCancel")).setVisible(true);
			((Button) groupboxEditar.getFellow("btNew")).setVisible(true);
		} else {
			((Button) groupboxEditar.getFellow("btCancel")).setVisible(false);
			((Button) groupboxEditar.getFellow("btNew")).setVisible(false);
		}
	}

	public void listarCombos() {
		listarParameter();
		Utilidades.listarElementoListbox(lbxNro_ingreso, false,
				getServiceLocator());
		Utilidades.listarElementoListbox(lbxSexo, false, getServiceLocator());
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("t2.nro_identificacion");
		listitem.setLabel("Identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("t2.nombre1||' '||t2.nombre2");
		listitem.setLabel("Nombres");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("t2.apellido1||' '||t2.apellido2");
		listitem.setLabel("Apellidos");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_factura");
		listitem.setLabel("Nro registro");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("recien_nacido.fecha_nacimiento");
		listitem.setLabel("Fecha(aaaa-mm-dd)");
		lbxParameter.appendChild(listitem);

		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}

	private void parametrizarBandbox() {
		parametrizarBandboxPaciente();
		parametrizarBandboxPrestador();
		parametrizarBandboxDiagnostico();
	}

	private void parametrizarBandboxPaciente() {
		tbxNro_identificacion.inicializar(tbxNomPaciente, btnLimpiarPaciente);
		tbxNro_identificacion
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

						return Recien_nacidoAction.this.getServiceLocator()
								.getPacienteService().listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Paciente registro) {
						Recien_nacido recien_nacido = new Recien_nacido();
						recien_nacido.setCodigo_empresa(registro
								.getCodigo_empresa());
						recien_nacido.setCodigo_sucursal(registro
								.getCodigo_sucursal());
						recien_nacido.setNro_identificacion(registro
								.getNro_identificacion());

						List<Admision> listaAdmisiones = listarAdmisiones(
								recien_nacido, false);
						// TODO: Edicion Jhonny Gomez
						List<Admision> listaAdmisionesReal = new ArrayList<Admision>();

						for (Admision admision : listaAdmisiones) {
							Hospitalizacion h = new Hospitalizacion();
							h.setCodigo_empresa(admision.getCodigo_empresa());
							h.setCodigo_sucursal(admision.getCodigo_sucursal());
							h.setNro_identificacion(admision
									.getNro_identificacion());
							h.setNro_ingreso(admision.getNro_ingreso());
							h = getServiceLocator().getHospitalizacionService()
									.consultar(h);

							if (admision.getHospitalizacion().equals("S")
									&& h == null) {
								listaAdmisionesReal.add(admision);
							}
						}

						// En caso de no encontrar admisiones validas
						if (listaAdmisionesReal.isEmpty()) {
							Messagebox
									.show("No se ha registrado el ingreso del paciente para su atencion en urgencias",
											"Paciente no admisionado",
											Messagebox.OK,
											Messagebox.EXCLAMATION);
							limpiarDatos();
							deshabilitarCampos(true);
							return false;
						} else {
							listarIngresos(lbxNro_ingreso, listaAdmisiones,
									false);
							Admision aux2 = listaAdmisionesReal.get(0);
							recien_nacido.setNro_ingreso(aux2.getNro_ingreso());

							final Recien_nacido rn = getServiceLocator()
									.getRecien_nacidoService().consultar(
											recien_nacido);
							if (rn != null) {
								// Si encuentra una admision valida y una recien
								// nacido con los datos seleccionados
								return msgExistencia(rn);
							} else {
								// Si encuentra una admision valida y sin recien
								// nacido con los datos seleccionados
								bandbox.setValue(registro
										.getNro_identificacion());
								textboxInformacion.setValue(registro
										.getNombreCompleto());

								tbxNacimiento
										.setValue(new java.text.SimpleDateFormat(
												"dd/MM/yyyy").format(registro
												.getFecha_nacimiento()));
								tbxSexo.setValue(Utilidades.getNombreElemento(
										registro.getSexo(), "sexo",
										Recien_nacidoAction.this));
								tbxEstrato.setValue(registro.getEstrato());
								tbxEdad.setValue(Util.getEdad(
										new java.text.SimpleDateFormat(
												"dd/MM/yyyy").format(registro
												.getFecha_nacimiento()), "1",
										true));
								tbxTipo_afiliado.setValue(Utilidades
										.getNombreElemento(
												registro.getTipo_afiliado(),
												"tipo_afiliado",
												Recien_nacidoAction.this));

								datos_seleccion.put("sexo", registro.getSexo());
								datos_seleccion.put("tipo_identificacion",
										registro.getTipo_identificacion());
								datos_seleccion.put("fecha_nac",
										new java.text.SimpleDateFormat(
												"dd/MM/yyyy").format(registro
												.getFecha_nacimiento()));

								deshabilitarCampos(false);

								listarIngresos(lbxNro_ingreso, listaAdmisiones,
										false);

								for (int j = 0; j < lbxNro_ingreso
										.getItemCount(); j++) {
									Listitem listitem = lbxNro_ingreso
											.getItemAtIndex(j);
									if (listitem.getValue() == aux2) {
										listitem.setSelected(true);
									}
								}

								datos_seleccion.put("list_item_admision",
										lbxNro_ingreso.getSelectedItem());

								cargarAdmisiones(aux2);
								return true;
							}
						}
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
						
						limpiarDatos();
						datos_seleccion.remove("sexo");
						datos_seleccion.remove("fecha_nac");
						datos_seleccion.remove("tipo_identificacion");
						listarIngresos(lbxNro_ingreso,
								new LinkedList<Admision>(), true);
						deshabilitarCampos(true);
					}
				});
	}

	private void parametrizarBandboxPrestador() {
		tbxCodigo_prestador.inicializar(tbxNomPrestador, btnLimpiarPrestador);
		tbxCodigo_prestador
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Map<String, Object>>() {

					@Override
					public void renderListitem(Listitem listitem,
							Map<String, Object> registro) {
						Listcell listcell = new Listcell();
						listcell.appendChild(new Label((String) registro
								.get(Utilidades.CODIGO_USUARIO)));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label((String) registro
								.get(Utilidades.NOMBRES)));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label((String) registro
								.get(Utilidades.APELLIDOS)));
						listitem.appendChild(listcell);

						listcell = new Listcell();
						listcell.appendChild(new Label(Utilidades
								.getTipoMedico((String) registro
										.get(Utilidades.TIPO_MEDICO))));
						listitem.appendChild(listcell);
					}

					@Override
					public List<Map<String, Object>> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", "");
						parametros.put("value", "%"
								+ valorBusqueda.toUpperCase().trim() + "%");
						parametros.put("codigo_empresa",
								sucursal.getCodigo_empresa());
						parametros.put("codigo_sucursal",
								sucursal.getCodigo_sucursal());
						parametros.put("rol", "05");

						getServiceLocator().getUsuariosService().setLimit(
								"limit 25 offset 0");

						return getServiceLocator().getUsuariosService()
								.getUsuarioByRol(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion,
							Map<String, Object> registro) {
						bandbox.setValue((String) registro
								.get(Utilidades.CODIGO_USUARIO));
						textboxInformacion.setValue((String) registro
								.get(Utilidades.NOMBRES)
								+ " "
								+ (String) registro.get(Utilidades.APELLIDOS));
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {
					}

				});
	}

	private void parametrizarBandboxDiagnostico() {
		tbxCodigo_diagnostico_principal.inicializar(tbxNomDxp, btnLimpiarDxp);
		tbxCausa_muerte.inicializar(tbxNomDx5, btnLimpiarDx5);

		BandboxRegistrosIMG<Cie> bandboxRegistrosIMG = new BandboxRegistrosIMG<Cie>() {

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

				return getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class, parametros);

			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Cie registro) {
				try {
					Map map = new HashMap();
					map.put("nombre_entidad", registro.getNombre());
					map.put("limite_inferior", registro.getLimite_inferior());
					map.put("limite_superior", registro.getLimite_superior());
					map.put("sexo_entidad", registro.getSexo());

					map.put("fecha_nac", datos_seleccion.get("fecha_nac"));
					map.put("sexo_pct", datos_seleccion.get("sexo"));
					Map result = Utilidades.validarInformacionLimiteSexo(
							"Diagnostico", registro.getCodigo(),
							registro.getLimite_inferior(),
							registro.getLimite_superior(), registro.getSexo(),
							(String) datos_seleccion.get("fecha_nac"),
							(String) datos_seleccion.get("sexo"));
					if (!((Boolean) result.get("success"))) {
						throw new Exception((String) result.get("msg"));
					}
					bandbox.setValue(registro.getCodigo());
					textboxInformacion.setValue(registro.getNombre());

					if (bandbox.equals(tbxCodigo_diagnostico_principal)) {
						datos_seleccion.put("cie_diagnostico_principal",
								registro);
					} else if (bandbox.equals(tbxCausa_muerte)) {
						datos_seleccion.put("cie_causa_muerte", registro);
					}

				} catch (Exception e) {
					MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
				}

				return true;

			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {
				if (bandbox.equals(tbxCodigo_diagnostico_principal)) {
					//log.info("limpiar tbxCodigo_diagnostico_principal");
					datos_seleccion.remove("cie_diagnostico_principal");
				} else if (bandbox.equals(tbxCausa_muerte)) {
					//log.info("limpiar tbxCausa_muerte");
					datos_seleccion.remove("cie_causa_muerte");
				}
			}

		};

		tbxCodigo_diagnostico_principal
				.setBandboxRegistrosIMG(bandboxRegistrosIMG);
		tbxCausa_muerte.setBandboxRegistrosIMG(bandboxRegistrosIMG);
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

	// Limpiamos los campos del formulario //
	public void limpiarDatos() {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		tbxNro_identificacion.setValue("");
		tbxNro_identificacion.setDisabled(false);
		deshabilitarCampos(true);

		dtbxFecha_muerte.setValue(null);
		rdbControl_prenatal.getItemAtIndex(0).setSelected(true);

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxNro_identificacion
				.setStyle("text-transform:uppercase;background-color:white");
		lbxNro_ingreso.setStyle("background-color:white");
		tbxCodigo_prestador
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_diagnostico_principal
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCausa_muerte
				.setStyle("text-transform:uppercase;background-color:white");
		dtbxFecha_muerte.setStyle("background-color:white");
		dtbxFecha_nacimiento.setStyle("background-color:white");

		Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
				.getValue());

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (tbxNro_identificacion.getText().trim().equals("")) {
			tbxNro_identificacion
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxCodigo_prestador.getText().trim().equals("")) {
			tbxCodigo_prestador
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (admision == null) {
			lbxNro_ingreso.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (tbxCodigo_diagnostico_principal.getText().trim().equals("")) {
			tbxCodigo_diagnostico_principal
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		if (valida) {
			if (dtbxFecha_muerte.getValue() != null
					&& tbxCausa_muerte.getText().trim().equals("")) {
				tbxCausa_muerte
						.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				mensaje = "Debe colocar fecha de  muerte de recien nacido.....,\n sino se presenta muerte del recien nacido dar doble click sobre el campo fecha de muerte y causa de muerte para borrar";
				valida = false;
			}
		}

		if (valida) {
			if (dtbxFecha_muerte.getValue() == null
					&& !tbxCausa_muerte.getText().trim().equals("")) {
				dtbxFecha_muerte.setStyle("background-color:#F6BBBE");
				mensaje = "Debe colocar fecha de  muerte de recien nacido.....,\n sino se presenta muerte del recien nacido dar doble click sobre el campo fecha de muerte y causa de muerte para borrar";
				valida = false;
			}
		}

		if (valida) {
			if (dtbxFecha_muerte.getValue() != null) {
				if (dtbxFecha_nacimiento.getValue().compareTo(
						dtbxFecha_muerte.getValue()) > 0) {
					dtbxFecha_nacimiento.setStyle("background-color:#F6BBBE");
					mensaje = "La fecha de nacimiento no puede ser mayor a la fecha de muerte";
					valida = false;
				}
			}

		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
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

			getServiceLocator().getRecien_nacidoService().setLimit(
					"limit 25 offset 0");

			List<Recien_nacido> lista_datos = getServiceLocator()
					.getRecien_nacidoService().listar(parameters);
			rowsResultado.getChildren().clear();

			for (Recien_nacido recien_nacido : lista_datos) {
				rowsResultado.appendChild(crearFilas(recien_nacido, this));
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

		final Recien_nacido recien_nacido = (Recien_nacido) objeto;

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(recien_nacido.getCodigo_empresa());
		paciente.setCodigo_sucursal(recien_nacido.getCodigo_sucursal());
		paciente.setNro_identificacion(recien_nacido.getNro_identificacion());
		paciente = getServiceLocator().getPacienteService().consultar(paciente);
		String nombres_paciente = (paciente != null ? paciente.getNombre1()
				+ " " + paciente.getNombre2() : "");
		String apellidos_paciente = (paciente != null ? paciente.getApellido1()
				+ " " + paciente.getApellido2() : "");

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(recien_nacido.getNro_factura() + ""));
		fila.appendChild(new Label(recien_nacido.getTipo_identificacion() + ""));
		fila.appendChild(new Label(recien_nacido.getNro_identificacion() + ""));
		fila.appendChild(new Label(recien_nacido.getNro_ingreso() + ""));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(recien_nacido.getFecha_nacimiento()) + ""));
		fila.appendChild(new Label(apellidos_paciente + ""));
		fila.appendChild(new Label(nombres_paciente + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(recien_nacido);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		String estado_admision = Utilidades.getEstado_admision(
				recien_nacido.getNro_ingreso(),
				recien_nacido.getNro_identificacion(), this);
		if (estado_admision.equals("2")) {
			img.setVisible(false);
		}
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
									eliminarDatos(recien_nacido);
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
			FormularioUtil.setUpperCase(groupboxEditar);
			if (validarForm()) {

				Admision admision = ((Admision) lbxNro_ingreso
						.getSelectedItem().getValue());

				Recien_nacido recien_nacido = new Recien_nacido();
				recien_nacido.setCodigo_empresa(empresa.getCodigo_empresa());
				recien_nacido.setCodigo_sucursal(sucursal.getCodigo_sucursal());
				recien_nacido.setNro_factura(tbxNro_factura.getValue());
				recien_nacido = getServiceLocator().getRecien_nacidoService()
						.consultar(recien_nacido);
				if (recien_nacido == null) {
					recien_nacido = new Recien_nacido();
					recien_nacido
							.setCodigo_empresa(empresa.getCodigo_empresa());
					recien_nacido.setCodigo_sucursal(sucursal
							.getCodigo_sucursal());
					recien_nacido.setNro_factura(tbxNro_factura.getValue());
				}
				recien_nacido.setTipo_identificacion((String) datos_seleccion
						.get("tipo_identificacion"));
				recien_nacido.setNro_identificacion(tbxNro_identificacion
						.getValue());
				recien_nacido.setCodigo_administradora((String) datos_seleccion
						.get("codigo_administradora"));
				recien_nacido.setId_plan((String) datos_seleccion
						.get("id_plan"));
				recien_nacido.setNro_ingreso(admision.getNro_ingreso());
				recien_nacido.setCodigo_prestador(tbxCodigo_prestador
						.getValue());
				recien_nacido.setFecha_nacimiento(new Timestamp(
						dtbxFecha_nacimiento.getValue().getTime()));
				recien_nacido.setNumero_autorizacion(tbxNumero_autorizacion
						.getValue());
				recien_nacido.setEdad_gestacional((ibxEdad_gestacional
						.getValue() != null ? ibxEdad_gestacional.getValue()
						: 0)
						+ "");
				recien_nacido.setControl_prenatal(rdbControl_prenatal
						.getSelectedItem().getValue().toString());
				recien_nacido.setSexo(lbxSexo.getSelectedItem().getValue()
						.toString());
				recien_nacido.setPeso((ibxPeso.getValue() != null ? ibxPeso
						.getValue() : 0) + "");
				if (dtbxFecha_muerte.getValue() != null) {
					recien_nacido.setFecha_muerte(new Timestamp(
							dtbxFecha_muerte.getValue().getTime()));
				}

				recien_nacido
						.setCodigo_diagnostico_principal(tbxCodigo_diagnostico_principal
								.getValue());
				recien_nacido.setCausa_muerte(tbxCausa_muerte.getValue());

				recien_nacido.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				recien_nacido.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				recien_nacido.setCreacion_user(usuarios.getCodigo().toString());
				recien_nacido.setUltimo_user(usuarios.getCodigo().toString());

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getRecien_nacidoService().crear(
							recien_nacido);
					if (FACTURACION_RIPS != null) {
						this.detach();
					} else {
						accionForm(true, "registrar");
					}

				} else {
					int result = getServiceLocator().getRecien_nacidoService()
							.actualizar(recien_nacido);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				Messagebox
						.show("Los datos se guardaron satisfactoriamente",
								"Informacion ..", Messagebox.OK,
								Messagebox.INFORMATION);

				if (this.getParent() instanceof Facturacion_ripsAction) {
					this.detach();
				}

			}
		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Recien_nacido recien_nacido = (Recien_nacido) obj;
		try {
			deshabilitarCampos(false);
			tbxNro_factura.setValue(recien_nacido.getNro_factura());
			listarIngresos(lbxNro_ingreso,
					listarAdmisiones(recien_nacido, true), false);
			Utilidades.seleccionarListitem(lbxNro_ingreso,
					recien_nacido.getNro_ingreso());

			Paciente paciente = new Paciente();
			paciente.setCodigo_empresa(recien_nacido.getCodigo_empresa());
			paciente.setCodigo_sucursal(recien_nacido.getCodigo_sucursal());
			paciente.setNro_identificacion(recien_nacido
					.getNro_identificacion());
			paciente = getServiceLocator().getPacienteService().consultar(
					paciente);
			tbxNro_identificacion.seleccionarRegistro(paciente, recien_nacido
					.getNro_identificacion(),
					(paciente != null ? paciente.getNombreCompleto() : ""));
			datos_seleccion
					.put("tipo_identificacion",
							(paciente != null ? paciente
									.getTipo_identificacion() : ""));
			datos_seleccion.put("sexo", (paciente != null ? paciente.getSexo()
					: ""));
			datos_seleccion.put(
					"fecha_nac",
					(paciente != null ? new SimpleDateFormat("dd/MM/yyyy")
							.format(paciente.getFecha_nacimiento()) : ""));
			tbxNacimiento.setValue(new java.text.SimpleDateFormat("dd/MM/yyyy")
					.format(paciente.getFecha_nacimiento()));
			tbxSexo.setValue(Utilidades.getNombreElemento(paciente.getSexo(),
					"sexo", Recien_nacidoAction.this));
			tbxEstrato.setValue(paciente.getEstrato());
			tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
					"dd/MM/yyyy").format(paciente.getFecha_nacimiento()), "1",
					true));
			tbxTipo_afiliado.setValue(Utilidades.getNombreElemento(
					paciente.getTipo_afiliado(), "tipo_afiliado",
					Recien_nacidoAction.this));

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(recien_nacido.getCodigo_empresa());
			prestadores.setCodigo_sucursal(recien_nacido.getCodigo_sucursal());
			prestadores.setNro_identificacion(recien_nacido
					.getCodigo_prestador());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);

			tbxCodigo_prestador.seleccionarRegistro(prestadores, recien_nacido
					.getCodigo_prestador(),
					(prestadores != null ? prestadores.getNombres() + " "
							+ prestadores.getApellidos() : ""));

			datos_seleccion.put("codigo_administradora",
					recien_nacido.getCodigo_administradora());
			datos_seleccion.put("id_plan", recien_nacido.getId_plan());

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(recien_nacido.getCodigo_empresa());
			administradora.setCodigo_sucursal(recien_nacido
					.getCodigo_sucursal());
			administradora.setCodigo(recien_nacido.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(recien_nacido.getCodigo_empresa());
			contratos.setCodigo_sucursal(recien_nacido.getCodigo_sucursal());
			contratos.setCodigo_administradora(recien_nacido
					.getCodigo_administradora());
			contratos.setId_plan(recien_nacido.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(
					contratos);
			tbxAseguradora.setValue((administradora != null ? administradora
					.getNombre() : "")
					+ " - "
					+ (contratos != null ? contratos.getNombre() : ""));

			dtbxFecha_nacimiento.setValue(recien_nacido.getFecha_nacimiento());
			tbxNumero_autorizacion.setValue(recien_nacido
					.getNumero_autorizacion());
			ibxEdad_gestacional.setText(recien_nacido.getEdad_gestacional());
			Radio radio = (Radio) rdbControl_prenatal
					.getFellow("radioControl_prenatal"
							+ recien_nacido.getControl_prenatal());
			radio.setChecked(true);
			Utilidades.seleccionarListitem(lbxSexo, recien_nacido.getSexo());
			if (recien_nacido.getFecha_muerte() != null) {
				dtbxFecha_muerte.setValue(recien_nacido.getFecha_muerte());
			}
			ibxPeso.setText(recien_nacido.getPeso());

			Cie cie = new Cie();
			cie.setCodigo(recien_nacido.getCodigo_diagnostico_principal());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
			tbxCodigo_diagnostico_principal.seleccionarRegistro(cie,
					recien_nacido.getCodigo_diagnostico_principal(),
					(cie != null ? cie.getNombre() : ""));
			datos_seleccion.put("cie_diagnostico_principal", cie);

			cie = new Cie();
			cie.setCodigo(recien_nacido.getCausa_muerte());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
			tbxCausa_muerte.seleccionarRegistro(cie,
					recien_nacido.getCausa_muerte(),
					(cie != null ? cie.getNombre() : ""));
			datos_seleccion.put("cie_causa_muerte", cie);

			validarRegistroEditar(recien_nacido);

			// Mostramos la vista //
			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Recien_nacido recien_nacido = (Recien_nacido) obj;
		try {
			int result = getServiceLocator().getRecien_nacidoService()
					.eliminar(recien_nacido);
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

	private void validarRegistroEditar(Recien_nacido recien_nacido)
			throws Exception {
		String estado_admision = Utilidades.getEstado_admision(
				recien_nacido.getNro_ingreso(),
				recien_nacido.getNro_identificacion(), this);
		if (this.getParent() instanceof Facturacion_ripsAction) {
			estado_admision = "1";
		}
		if (estado_admision.equals("2")) {
			bloqueoBotonGuardar(true);
		} else {
			bloqueoBotonGuardar(false);
		}
	}

	private void bloqueoBotonGuardar(boolean sw) {
		
		if (!sw) {
			((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(false);
			((Button) groupboxEditar.getFellow("btGuardar"))
					.setImage("/images/Save16.gif");
		} else {
			((Button) groupboxEditar.getFellow("btGuardar")).setDisabled(true);
			((Button) groupboxEditar.getFellow("btGuardar"))
					.setImage("/images/Save16_disabled.gif");
		}
	}

	public void deshabilitarCampos(boolean sw) {
		FormularioUtil.deshabilitarComponentes(groupboxEditar, sw,
				idsExcluyentes);
		bloqueoBotonGuardar(sw);
		if (sw) {
			listarIngresos(lbxNro_ingreso, new LinkedList<Admision>(), true);
		}
	}

	public void listarIngresos(Listbox listbox, List<Admision> listaAdmisiones,
			boolean select) {
		listbox.getItems().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue(null);
			listitem.setLabel("-- --");
			listbox.appendChild(listitem);
		}

		for (Admision a : listaAdmisiones) {
			Administradora admin = new Administradora();
			admin.setCodigo_empresa(a.getCodigo_empresa());
			admin.setCodigo_sucursal(a.getCodigo_sucursal());
			admin.setCodigo(a.getCodigo_administradora());
			admin = getServiceLocator().getAdministradoraService().consultar(
					admin);

			listitem = new Listitem();
			listitem.setValue(a);
			listitem.setLabel(a.getNro_ingreso() + " -- "
					+ (admin != null ? admin.getNombre() : ""));
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	public List<Admision> listarAdmisiones(Recien_nacido recien_nacido,
			boolean sw) {
		Map paramAdmision = new HashMap();
		paramAdmision.put("codigo_empresa", recien_nacido.getCodigo_empresa());
		paramAdmision
				.put("codigo_sucursal", recien_nacido.getCodigo_sucursal());
		paramAdmision.put("nro_identificacion",
				recien_nacido.getNro_identificacion());
		paramAdmision.put("order", "fecha_ingreso desc");
		if (sw) {
			paramAdmision.put("nro_ingreso", recien_nacido.getNro_ingreso());
		} else {
			paramAdmision.put("estado", "1");
		}

		List<Admision> listaAdmisiones = getServiceLocator()
				.getAdmisionService().listarTabla(paramAdmision);

		return listaAdmisiones;
	}

	public void cargarAdmisiones(Admision aux2) {
		if (aux2 != null) {
			deshabilitarCampos(false);

			datos_seleccion.put("codigo_administradora",
					aux2.getCodigo_administradora());
			datos_seleccion.put("id_plan", aux2.getId_plan());

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(aux2.getCodigo_empresa());
			administradora.setCodigo_sucursal(aux2.getCodigo_sucursal());
			administradora.setCodigo(aux2.getCodigo_administradora());
			administradora = getServiceLocator().getAdministradoraService()
					.consultar(administradora);

			Contratos contratos = new Contratos();
			contratos.setCodigo_empresa(aux2.getCodigo_empresa());
			contratos.setCodigo_sucursal(aux2.getCodigo_sucursal());
			contratos.setCodigo_administradora(aux2.getCodigo_administradora());
			contratos.setId_plan(aux2.getId_plan());
			contratos = getServiceLocator().getContratosService().consultar(
					contratos);
			tbxAseguradora.setValue((administradora != null ? administradora
					.getNombre() : "")
					+ " - "
					+ (contratos != null ? contratos.getNombre() : ""));

			Cie cie = new Cie();
			cie.setCodigo(aux2.getDiagnostico_ingreso());
			cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
			tbxCodigo_diagnostico_principal.seleccionarRegistro(cie,
					aux2.getDiagnostico_ingreso(),
					(cie != null ? cie.getNombre() : ""));

			datos_seleccion.put("cie_diagnostico_principal", cie);

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(empresa.getCodigo_empresa());
			prestadores.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			prestadores.setNro_identificacion(aux2.getCodigo_medico());
			prestadores = getServiceLocator().getPrestadoresService()
					.consultar(prestadores);
			tbxCodigo_prestador.seleccionarRegistro(prestadores,
					aux2.getCodigo_medico(),
					(prestadores != null ? prestadores.getNombres() + " "
							+ prestadores.getApellidos() : ""));
		}
	}

	public void nuevoRegistro() throws Exception {
		Messagebox
				.show("perderá esta informacion que esta digitando, seguro que va a crear un nuevo registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									accionForm(true, "registrar");
								}
							}
						});
	}

	// TODO: Ediccion Jhonny Gomez
	public void selectedAdmision(Admision admision) {
		if (admision == null) {
			datos_seleccion.remove("list_item_admision");
			limpiarDatos();
			deshabilitarCampos(true);
		} else {
			Recien_nacido recien_nacido = new Recien_nacido();

			recien_nacido.setCodigo_empresa(admision.getCodigo_empresa());
			recien_nacido.setCodigo_sucursal(admision.getCodigo_sucursal());
			recien_nacido.setNro_identificacion(admision
					.getNro_identificacion());
			recien_nacido.setNro_ingreso(admision.getNro_ingreso());
			final Recien_nacido rn = getServiceLocator()
					.getRecien_nacidoService().consultar(recien_nacido);

			if (admision.getRecien_nacido().equals("S") && rn != null) {
				msgExistencia(rn);
			} else if (admision.getRecien_nacido().equals("N")) {
				Messagebox
						.show("No se ha registrado el ingreso del paciente a recien nacido",
								"Paciente no admisionado", Messagebox.OK,
								Messagebox.EXCLAMATION);
				lbxNro_ingreso.setSelectedItem((Listitem) datos_seleccion
						.get("list_item_admision"));
				return;
			} else {
				datos_seleccion.put("list_item_admision",
						lbxNro_ingreso.getSelectedItem());
				cargarAdmisiones(admision);
			}
		}
	}

	public void selectedAdmision(Listitem listitem) throws Exception {
		Admision admision = ((Admision) listitem.getValue());
		selectedAdmision(admision);
	}

	public boolean msgExistencia(final Recien_nacido rn) {
		Messagebox
				.show("Ya existe un registro de este recien nacido, ¿Desea modificar los datos?",
						"Recien nacido ya registrado", Messagebox.YES
								+ Messagebox.NO, Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									mostrarDatos(rn);
								}
							}
						});
		return true;
	}

}
