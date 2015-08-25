/*
 * pacienteAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 * Ing. Luis Miguel Hernández Pérez
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Localidad;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.BarrioService;
import healthmanager.modelo.service.ContratosService;
import healthmanager.modelo.service.DepartamentosService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.MunicipiosService;
import healthmanager.modelo.service.Nivel_educativoService;
import healthmanager.modelo.service.OcupacionesService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Pacientes_contratosService;
import healthmanager.modelo.service.Pertenencia_etnicaService;
import healthmanager.modelo.service.UsuariosService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IOnRegistroEvent;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.util.ConvertidorDatosUtil;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Util;
import com.framework.util.Utilidades;

import healthmanager.modelo.service.GeneralExtraService;

public class PacienteAction extends GeneralComposer {

	private static Logger log = Logger.getLogger(PacienteAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	public static final String IMAGE_OK = "/images/activo.gif";
	public static final String IMAGE_CANCEL = "/images/estado_error.gif";

	/**
	 * ** servicios
	 */
	@WireVariable
	private AdministradoraService administradoraService;
	@WireVariable
	private PacienteService pacienteService;
	@WireVariable
	private Pacientes_contratosService pacientes_contratosService;
	@WireVariable
	private GeneralExtraService generalExtraService;
	@WireVariable
	private BarrioService barrioService;
	@WireVariable
	private DepartamentosService departamentosService;
	@WireVariable
	private MunicipiosService municipiosService;
	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private Nivel_educativoService nivel_educativoService;
	@WireVariable
	private Pertenencia_etnicaService pertenencia_etnicaService;
	@WireVariable
	private OcupacionesService ocupacionesService;
	@WireVariable
	private ContratosService contratosService;
	@WireVariable
	private UsuariosService usuariosService;

	/**
	 * ** fin de servicios
	 */
	private Admision admision_madre;

	// Componentes //
	private Textbox tbxidentificacion_ms;
	private Rows rowsResultadoMenor;
	private Button buttonCancelar;
	@View
	private Window windowMenorSinIdentificacion;
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

	private Grid gridResultadoMenor;

	@View
	private Listbox lbxTipo_identificacion;
	@View
	private Textbox tbxNro_identificacion;
	@View
	private Textbox tbxDocumento;
	@View
	private Textbox tbxCodigo_administradora;
	@View
	private Bandbox tbxNom_administradora;
	@View
	private Listbox listboxContratos;
	@View
	private Listbox lbxTipo_usuario;
	@View
	private Textbox tbxApellido1;
	@View
	private Textbox tbxApellido2;
	@View
	private Textbox tbxNombre1;
	@View
	private Textbox tbxNombre2;
	@View
	private Datebox dtbxFecha_nacimiento;
	@View
	private Textbox tbxEdad;
	@View
	private Listbox lbxUnidad_medidad;
	@View
	private Listbox lbxSexo;
	@View
	private Listbox lbxCodigo_dpto;
	@View
	private Listbox lbxCodigo_municipio;
	@View
	private Listbox lbxZona;
	@View
	private Textbox tbxLugar_exp;
	@View
	private Textbox tbxProfesion;
	@View
	private Doublebox dbxTel_oficina;
	@View
	private Doublebox dbxTel_res;
	@View
	private Textbox tbxDireccion;
	@View
	private Listbox lbxEstado_civil;
	@View
	private Listbox lbxEstrato;
	@View
	private Listbox lbxTipo_afiliado;
	@View
	private Doublebox dbxIngresos;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_ocupacion;
	@View
	private Textbox tbxInfoOcupacion;
	@View
	private Toolbarbutton btnLimpiarOcupacion;

	@View
	private Listbox lbxCodigo_educativo;
	@View
	private Listbox lbxPertenencia_etnica;

	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCod_bar;
	@View
	private Textbox tbxInfoBar;
	@View
	private Toolbarbutton btnLimpiarBar;

	@View
	private Toolbarbutton btCancelar;
	@View
	private Toolbarbutton btNuevo;

	private String nro_ingreso_rn;

	@View
	private Listbox lbxArea_paciente;
	@View
	private Listbox lbxNivel_sisben;

	@View
	private Datebox dtbxFecha_afiliacion;

	@View
	private Image imgState;
	@View
	private Textbox tbxLogin;
	@View
	private Textbox tbxPassword;
	@View
	private Toolbarbutton btnMostrar_ms;

	private boolean habilitado_contrato = false;

	private final String[] idsExcluyentes = {};

	private IOnRegistroEvent<Paciente> onRegistroEvent;

	private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

	private ResultadoPaginadoMacro resultadoPaginadoMacroMenor = new ResultadoPaginadoMacro();

	@Override
	public void init() throws Exception {
		listarCombos();
		loadEvents();
		if (rol_usuario.equals(IRoles.ADMINISTRADOR)
				|| rol_usuario.equals(IRoles.FACTURADOR_ADMINISTRATIVA)) {
			habilitado_contrato = true;
		}

		tbxidentificacion_ms = (Textbox) windowMenorSinIdentificacion
				.getFellow("tbxidentificacion_ms");
		buttonCancelar = (Button) windowMenorSinIdentificacion
				.getFellow("buttonCancelar");
		rowsResultadoMenor = (Rows) windowMenorSinIdentificacion
				.getFellow("rowsResultadoMenor");
		gridResultadoMenor = (Grid) windowMenorSinIdentificacion
				.getFellow("gridResultadoMenor");
		buttonCancelar.addEventListener(Events.ON_CLICK,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						windowMenorSinIdentificacion.setVisible(false);
					}

				});
	}

	@Override
	public void params(Map<String, Object> map) {
		admision_madre = (Admision) map.get("admision_madre");
	}

	/**
	 * Muestra una ventana emergente para que se registre la informacion del
	 * recien nacido.
	 *
	 * @author Manuel Aguilar
	 */
	public void mostrarVentanaRecienNacido() {
		if (lbxTipo_identificacion.getSelectedItem().getValue().toString()
				.equals("MS")) {
			btnMostrar_ms.setVisible(true);
			btnMostrar_ms.getParent().invalidate();
			windowMenorSinIdentificacion.setVisible(true);
			windowMenorSinIdentificacion.doModal();
			tbxidentificacion_ms.setValue("");
		} else {
			btnMostrar_ms.setVisible(false);
		}
	}

	/**
	 * Muestra una ventana emergente para que se registre la informacion del
	 * recien nacido.
	 *
	 * @author Manuel Aguilar
	 */
	public void mostrarVentanaRecienNacido2() {
		Utilidades.seleccionarListitem(lbxTipo_identificacion, "MS");
		windowMenorSinIdentificacion.setVisible(true);
		windowMenorSinIdentificacion.doModal();
		tbxidentificacion_ms.setValue("");
	}

	public void inicializarRecienNacido() {
		if (admision_madre != null) {

			groupboxConsulta.setVisible(false);
			groupboxEditar.setVisible(true);

			btCancelar.setVisible(false);
			btNuevo.setVisible(false);
			tbxDocumento.setValue(admision_madre.getNro_identificacion() + "-");
			Utilidades.seleccionarListitem(lbxTipo_identificacion, "MS");
			tbxNombre1.setValue(admision_madre.getPaciente().getNombre1());
			tbxNombre2.setValue("");
			tbxApellido1.setValue("HIJO DE ");
			tbxApellido2.setValue("");
			Utilidades.seleccionarListitem(lbxZona, admision_madre
					.getPaciente().getZona());
			Utilidades.seleccionarListitem(lbxTipo_usuario, admision_madre
					.getPaciente().getTipo_usuario());
			Utilidades.seleccionarListitem(lbxTipo_afiliado, admision_madre
					.getPaciente().getTipo_afiliado());
			Utilidades.seleccionarListitem(lbxTipo_usuario, admision_madre
					.getPaciente().getTipo_usuario());
			Utilidades.seleccionarListitem(lbxEstrato, admision_madre
					.getPaciente().getEstrato());
			Utilidades.seleccionarListitem(lbxEstado_civil, "3");
			// dbxIngresos.setValue(admision_madre.getPaciente().getIngresos());
			Administradora administradora = new Administradora();
			administradora
					.setCodigo_empresa(admision_madre.getCodigo_empresa());
			administradora.setCodigo_sucursal(admision_madre
					.getCodigo_sucursal());
			administradora.setCodigo(admision_madre.getCodigo_administradora());
			administradora = administradoraService.consultar(administradora);

			tbxCodigo_administradora
					.setValue(administradora != null ? administradora
							.getCodigo() : "");
			tbxNom_administradora
					.setValue(administradora != null ? administradora
							.getCodigo() + " " + administradora.getNombre()
							: "");

			listarContratos(listboxContratos,
					tbxCodigo_administradora.getValue(), true, true);

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("nro_identificacion",
					admision_madre.getNro_identificacion());
			parametros.put("codigo_administradora",
					admision_madre.getCodigo_administradora());

			List<Pacientes_contratos> listado_contratos = pacientes_contratosService
					.listar(parametros);

			for (Pacientes_contratos pacientes_contratos : listado_contratos) {
				for (Listitem listitem : listboxContratos.getItems()) {
					listitem.setDisabled(true);
					Contratos contratos = (Contratos) listitem.getValue();
					if (pacientes_contratos.getId_codigo().equals(
							contratos.getId_plan())) {
						listitem.setSelected(true);
					}

				}
			}

			Adicional_paciente adicional_paciente = new Adicional_paciente();
			adicional_paciente.setCodigo_empresa(codigo_empresa);
			adicional_paciente.setCodigo_sucursal(codigo_sucursal);
			adicional_paciente.setNro_identificacion(admision_madre
					.getPaciente().getNro_identificacion());
			adicional_paciente = generalExtraService
					.consultar(adicional_paciente);

			Barrio barrio = new Barrio();
			barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente
					.getCodigo_barrio() : "");
			barrio = barrioService.consultar(barrio);
			tbxCod_bar
					.setValue(barrio != null ? barrio.getCodigo_barrio() : "");
			tbxInfoBar.setValue((barrio != null ? barrio.getBarrio() : ""));

			Utilidades.seleccionarListitem(lbxCodigo_dpto, admision_madre
					.getPaciente().getCodigo_dpto());
			listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
			Utilidades.seleccionarListitem(lbxCodigo_municipio, admision_madre
					.getPaciente().getCodigo_municipio());

			obtenerEdad(true);

			tbxDocumento.setFocus(true);
			lbxTipo_identificacion.setDisabled(true);
			lbxTipo_usuario.setDisabled(true);
			lbxTipo_afiliado.setDisabled(true);
			lbxEstrato.setDisabled(true);
			lbxEstado_civil.setDisabled(true);
			tbxNom_administradora.setDisabled(true);
			listboxContratos.setDisabled(true);
			tbxCod_bar.setDisabled(true);
			lbxCodigo_dpto.setDisabled(true);
			lbxCodigo_municipio.setDisabled(true);
			dtbxFecha_nacimiento.setValue(new Date());
			deshabilitarCampos(dtbxFecha_nacimiento);
		}
	}

	private void loadEvents() {
		tbxLogin.addEventListener("onChanging", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				validateLogin(arg0);
			}
		});

		tbxLogin.addEventListener("onBlur", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				validateLogin(arg0);
			}
		});
	}

	public void initPaciente() throws Exception {
		try {
			// accionForm(true, "registrar");
			inicializarRecienNacido();
		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Error !!", Messagebox.OK,
					Messagebox.ERROR);
		}
	}

	public void listarCombos() {
		listarParameter();

		listarContratos(listboxContratos, tbxCodigo_administradora.getValue(),
				true, true);
		listarElementoListbox(lbxTipo_usuario, false);
		listarElementoListbox(lbxUnidad_medidad, false);
		listarElementoListbox(lbxSexo, true);
		Utilidades.listarDepartamentos(lbxCodigo_dpto, true,
				departamentosService);
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
				municipiosService);
		Utilidades.listarElementoListbox(lbxTipo_identificacion, true,
				elementoService);
		listarElementoListbox(lbxZona, false);
		listarElementoListbox(lbxEstado_civil, true);
		listarElementoListbox(lbxTipo_afiliado, false);
		listarElementoListbox(lbxEstrato, false);

		Utilidades.listarPertenencia_etnica(lbxPertenencia_etnica, true,
				pertenencia_etnicaService);
		Utilidades.listarNivel_educativo(lbxCodigo_educativo, true,
				nivel_educativoService);
		Utilidades
				.listarElementoListbox(lbxNivel_sisben, true, elementoService);
		parametrizarBandbox();
		listarElementoListbox(lbxArea_paciente, false);
	}

	private void parametrizarBandbox() {
		parametrizarBandboxOcupacion();
		parametrizarBandboxBarrio();
		parametrizarResultadoPaginado();
		parametrizarResultadoPaginado2();
	}

	private void parametrizarBandboxBarrio() {
		tbxCod_bar.inicializar(tbxInfoBar, btnLimpiarBar);
		tbxCod_bar.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Barrio>() {

			@Override
			public void renderListitem(Listitem listitem, Barrio registro) {

				Localidad localidad = new Localidad();
				localidad.setCodigo_localidad(registro != null ? registro
						.getCodigo_localidad() : "");
				localidad = generalExtraService.consultar(localidad);

				Municipios municipios = new Municipios();
				municipios.setCodigo((localidad != null ? localidad.getCodigo()
						: ""));
				municipios.setCoddep((localidad != null ? localidad.getCoddep()
						: ""));
				municipios = municipiosService.consultar(municipios);

				Departamentos departamentos = new Departamentos();
				departamentos.setCodigo((localidad != null ? localidad
						.getCoddep() : ""));
				departamentos = departamentosService.consultar(departamentos);

				String codigo_barrio = (registro != null ? registro
						.getCodigo_barrio() : "");
				String nombre_barrio = (registro != null ? registro.getBarrio()
						: "");
				String nombre_localidad = (localidad != null ? localidad
						.getLocalidad() : "");
				String nombre_mun = (municipios != null ? municipios
						.getNombre() : "");
				String nombre_dpto = (departamentos != null ? departamentos
						.getNombre() : "");

				listitem.appendChild(new Listcell(codigo_barrio));
				listitem.appendChild(new Listcell(nombre_barrio));
				listitem.appendChild(new Listcell(nombre_localidad));
				listitem.appendChild(new Listcell(nombre_mun + " - "
						+ nombre_dpto));
			}

			@Override
			public List<Barrio> listarRegistros(String valorBusqueda,
					Map<String, Object> parametros) {

				parametros.put(IConstantes.PARAMETRO_TODO,
						valorBusqueda.toLowerCase());
				parametros.put("limite_paginado", "limit 25 offset 0");
				return barrioService.listar(parametros);
			}

			@Override
			public boolean seleccionarRegistro(Bandbox bandbox,
					Textbox textboxInformacion, Barrio registro) {
				bandbox.setValue(registro.getCodigo_barrio());
				textboxInformacion.setValue(registro.getBarrio());
				return true;
			}

			@Override
			public void limpiarSeleccion(Bandbox bandbox) {

			}
		});

	}

	private void parametrizarBandboxOcupacion() {
		tbxCodigo_ocupacion.inicializar(tbxInfoOcupacion, btnLimpiarOcupacion);
		tbxCodigo_ocupacion
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Ocupaciones>() {

					@Override
					public void renderListitem(Listitem listitem,
							Ocupaciones registro) {
						listitem.appendChild(new Listcell(registro.getId()));
						listitem.appendChild(new Listcell(registro.getNombre()));
					}

					@Override
					public List<Ocupaciones> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("paramTodo", valorBusqueda.toLowerCase());
						return ocupacionesService.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Ocupaciones registro) {
						bandbox.setValue(registro.getId());
						textboxInformacion.setValue(registro.getNombre());

						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});

	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();

		Listitem listitem = new Listitem();
		listitem.setValue("documento");
		listitem.setLabel("Nro Identificacion");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nro_identificacion");
		listitem.setLabel("Id");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("nombre1||' '||nombre2");
		listitem.setLabel("Nombres");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("apellido1||' '||apellido2");
		listitem.setLabel("Apellidos");
		lbxParameter.appendChild(listitem);

		listitem = new Listitem();
		listitem.setValue("codigo_administradora");
		listitem.setLabel("Codigo administradora");
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
			listitem.setLabel("--  --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = elementoService.listar(tipo);

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

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);
		listarContratos(listboxContratos, tbxCodigo_administradora.getValue(),
				true, true);
		for (Listitem listitem : listboxContratos.getItems()) {
			listitem.setSelected(false);
		}
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
				municipiosService);
		obtenerEdad(true);

		dtbxFecha_afiliacion.setText("");
		dbxTel_res.setText("");

		btnMostrar_ms.setVisible(false);
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		FormularioUtil.validarCamposObligatorios(tbxDocumento, tbxNombre1,
				tbxApellido1, lbxCodigo_dpto, lbxCodigo_municipio,
				tbxNom_administradora, tbxDireccion, dbxTel_res,
				lbxPertenencia_etnica, lbxCodigo_educativo,
				lbxTipo_identificacion, lbxSexo);

		tbxDocumento
				.setStyle("background-color:white;text-transform:uppercase");
		tbxNombre1.setStyle("background-color:white;text-transform:uppercase");
		tbxApellido1
				.setStyle("background-color:white;text-transform:uppercase");
		lbxCodigo_dpto.setStyle("background-color:white;");
		lbxCodigo_municipio.setStyle("background-color:white;");
		tbxNom_administradora.setStyle("background-color:white");
		tbxNom_administradora.setWidth("450px");
		tbxNom_administradora.applyProperties();
		lbxPertenencia_etnica.setStyle("background-color:white");
		dtbxFecha_nacimiento.setStyle("background-color:white");
		tbxCod_bar.setStyle("background-color:white");
		lbxCodigo_educativo.setStyle("background-color:white");
		lbxTipo_identificacion.setStyle("background-color:white");

		boolean valida = true;

		String mensaje = "Los campos marcados con (*) son obligatorios";

		if (valida) {
			if (validNro_id(tbxDocumento.getValue(), lbxTipo_identificacion
					.getSelectedItem().getValue().toString())) {
				tbxDocumento
						.setStyle("text-transform:uppercase;background-color:#F6BBBE");
				valida = false;
				mensaje = "El nro de identificacion no puede contener ninguno de los stes caracteres: "
						+ "'.' , ',', '/','|','-', '_' , '\' ni espacios en blancos";
			}
		}

		if (valida) {
			if (lbxPertenencia_etnica.getSelectedItems().isEmpty()) {
				lbxPertenencia_etnica.setStyle("background-color:red");
				valida = false;
			}
		}
		if (valida && tbxInfoBar.getValue().toString().isEmpty()) {
			if (tbxInfoBar.getValue().isEmpty()
					|| tbxInfoBar.getValue() != null) {
				Clients.wrongValue(tbxCod_bar, "Seleccione el barrio");
				// tbxCod_bar.setStyle("border-style: none; fondo:red");
				valida = false;
			}
		}

		if (valida) {
			if (lbxCodigo_educativo.getSelectedItems().isEmpty()) {
				lbxCodigo_educativo.setStyle("background-color:red");
				valida = false;
			}
		}
		if (valida) {
			if (lbxTipo_identificacion.getSelectedItems().isEmpty()) {
				lbxTipo_identificacion.setStyle("background-color:red");
				valida = false;
			}
		}
		if (valida) {
			if (dtbxFecha_nacimiento.getText().isEmpty()) {
				dtbxFecha_nacimiento.setStyle("background-color:#F6BBBE");
				valida = false;
				mensaje = "Debe seleccionar La fecha de nacimiento del paciente a registrar";
			} else if (dtbxFecha_nacimiento.getValue().compareTo(new Date()) > 0) {
				dtbxFecha_nacimiento.setStyle("background-color:#F6BBBE");
				valida = false;
				mensaje = "La fecha de nacimiento es mayor que la actual ";
			}
		}

		if (valida) {
			if (admision_madre != null) {
				if (!String.valueOf(tbxDocumento.getValue()).startsWith(
						admision_madre.getNro_identificacion() + "-")) {
					valida = false;
					mensaje = "El nro de identificacion del paciente recien nacido debe empezar por el nro de identificacion de la madre";
				} else if (String.valueOf(tbxDocumento.getValue()).endsWith(
						admision_madre.getNro_identificacion() + "-")) {
					valida = false;
					mensaje = "No ha ingresado el consecutivo del nro de identificacion del paciente recien nacido";
				}
			}
		}

		// if (valida && listboxContratos.getSelectedItems().isEmpty()) {
		// valida = false;
		// mensaje =
		// "No hay contratos seleccionados para guardar la informacion de este paciente";
		// Clients.scrollIntoView(listboxContratos);
		// }
		if (valida) {
			if (lbxTipo_usuario.getSelectedItem().getValue().toString()
					.equals("2")
					&& lbxNivel_sisben.getSelectedIndex() == 0) {
				mensaje = "El campo Nivel del sisben es obligatorio";
				lbxNivel_sisben.setStyle("background-color:#F6BBBE");
				valida = false;
			}
		}

		if (!(imgState.getSrc() + "").equals(IMAGE_OK)
				&& !tbxLogin.getText().trim().equals("")) {
			tbxLogin.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			mensaje = "Login no valido";
			valida = false;
		}

		if ((imgState.getSrc() + "").equals(IMAGE_OK)
				&& tbxPassword.getText().trim().equals("")) {
			tbxPassword
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}

		String tipo_id = lbxTipo_identificacion.getSelectedItem().getValue();
		if (tipo_id.equalsIgnoreCase("MS")
				&& !tbxDocumento.getValue().toString()
						.matches("(\\w{2}_)?[0-9]*-[0-9]{1,2}")) {
			mensaje = "En el número de identificacion debe colocar el número que corresponde a la posicion del hijo, luego del guion.";
			valida = false;
		}

		// Este tiene que estar de ultimo
		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public boolean validNro_id(String nro_id, String tipo_id) {
		for (int i = 0; i < nro_id.length(); i++) {
			char ch = nro_id.charAt(i);
			if (ch == '.' || ch == ',' || ch == '/' || ch == '|'
					|| (ch + "").equals("'")
					|| (ch == '-' && !tipo_id.equals("MS")) || ch == '_'
					|| ch == ' ') {
				return false;
			}
		}
		return false;
	}

	public void obtenerEdad(Boolean auto) {
		try {
			if (dtbxFecha_nacimiento.getValue() != null) {

				if (auto) {
					String unidad = Util.getUnidadMedidaEdad(new Timestamp(
							dtbxFecha_nacimiento.getValue().getTime()),
							new Timestamp(new Date().getTime()));
					Utilidades.seleccionarListitem(lbxUnidad_medidad, unidad);
				}
				tbxEdad.setValue(Util.getEdad(dtbxFecha_nacimiento.getValue(),
						lbxUnidad_medidad.getSelectedItem().getValue()
								.toString(), true));
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void deshabilitarCampos(Datebox fecha_nac) {
		Date fecha = fecha_nac.getValue();
		if (fecha != null) {
			Integer edad = Integer.parseInt(Util.getEdad(fecha, "1", false));

			if (edad <= 14) {
				if (edad < 5) {
					Ocupaciones ocupaciones = new Ocupaciones();
					ocupaciones.setId("9998");
					ocupaciones = ocupacionesService.consultar(ocupaciones);
					if (tbxCodigo_ocupacion.getText().isEmpty()) {
						tbxCodigo_ocupacion
								.setValue((ocupaciones != null ? ocupaciones
										.getId() : ""));
						tbxInfoOcupacion
								.setValue((ocupaciones != null ? ocupaciones
										.getNombre() : ""));
					}
					if (lbxCodigo_educativo.getSelectedIndex() == 0) {
						Utilidades.setValueFrom(lbxCodigo_educativo, "13");
					}
					lbxEstado_civil.setDisabled(true);
					dbxIngresos.setDisabled(true);
					dbxTel_oficina.setDisabled(true);
					tbxProfesion.setDisabled(true);
					Utilidades.seleccionarListitem(lbxEstado_civil, "");
					dbxIngresos.setValue(0.0);
					dbxTel_oficina.setValue(null);
					tbxProfesion.setValue("");
				} else {
					Ocupaciones ocupaciones = new Ocupaciones();
					ocupaciones.setId("9997");
					ocupaciones = ocupacionesService.consultar(ocupaciones);
					if (tbxCodigo_ocupacion.getText().isEmpty()) {
						tbxCodigo_ocupacion
								.setValue((ocupaciones != null ? ocupaciones
										.getId() : ""));
						tbxInfoOcupacion
								.setValue((ocupaciones != null ? ocupaciones
										.getNombre() : ""));
					}

					if (lbxCodigo_educativo.getSelectedIndex() == 0) {
						if (edad == 5) {
							Utilidades.setValueFrom(lbxCodigo_educativo, "1");
						} else if (edad >= 6 && edad < 11) {
							Utilidades.setValueFrom(lbxCodigo_educativo, "2");
						} else {
							Utilidades.setValueFrom(lbxCodigo_educativo, "3");
						}
					}

					lbxEstado_civil.setDisabled(true);
					dbxIngresos.setDisabled(true);
					dbxTel_oficina.setDisabled(true);
					tbxProfesion.setDisabled(true);
					Utilidades.seleccionarListitem(lbxEstado_civil, "");
					dbxIngresos.setValue(0.0);
					dbxTel_oficina.setValue(null);
					tbxProfesion.setValue("");
				}
			} else {
				lbxEstado_civil.setDisabled(false);
				dbxIngresos.setDisabled(false);
				dbxTel_oficina.setDisabled(false);
				tbxProfesion.setDisabled(false);
			}
		} else {
			lbxEstado_civil.setDisabled(true);
			dbxIngresos.setDisabled(true);
			dbxTel_oficina.setDisabled(true);
			tbxProfesion.setDisabled(true);
			Utilidades.seleccionarListitem(lbxEstado_civil, "");
			dbxIngresos.setValue(0.0);
			dbxTel_oficina.setValue(null);
			tbxProfesion.setValue("");
		}
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
			// filtra por la edad del padre >= 10 años
			parameters.put("value", "%" + value + "%");

			resultadoPaginadoMacro.buscarDatos(parameters);

		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	// Metodo para buscar //
	public void buscarDatos2() throws Exception {
		try {
			String codigo_empresa = empresa.getCodigo_empresa();
			String codigo_sucursal = sucursal.getCodigo_sucursal();
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxidentificacion_ms.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", codigo_empresa);
			parameters.put("codigo_sucursal", codigo_sucursal);
			parameters.put("parameter", parameter);
			// filtra por la edad del padre >= 10 años
			parameters.put("filtar_edad_mayor_igual", 10);
			parameters.put("value", "%" + value + "%");
			// log.info(">>>>>>>>" + parameters);
			parameters.put("limite_paginado", "limit 25 offset 0");

			List<Paciente> lista_datos = pacienteService.listar(parameters);
			rowsResultado.getChildren().clear();
			rowsResultadoMenor.getChildren().clear();

			for (Paciente paciente : lista_datos) {
				rowsResultadoMenor.appendChild(crearFilasMenor(paciente, this));
			}

			gridResultadoMenor.setVisible(true);
			gridResultadoMenor.setMold("paging");
			gridResultadoMenor.setPageSize(20);

			gridResultadoMenor.applyProperties();
			gridResultadoMenor.invalidate();
			gridResultadoMenor.setVisible(true);

		} catch (Exception e) {

			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	private void parametrizarResultadoPaginado() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Paciente> listarResultados(
					Map<String, Object> parametros) {
				List<Paciente> listado = pacienteService.listar(parametros);
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = pacienteService.totalResultados(parametros);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilas(dato, gridResultado);
			}

		};
		resultadoPaginadoMacro.incicializar(resultadoPaginadoMacroIMG,
				gridResultado, 7);
	}

	private void parametrizarResultadoPaginado2() {
		ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

			@Override
			public List<Paciente> listarResultados(
					Map<String, Object> parametros) {
				List<Paciente> listado = pacienteService.listar(parametros);
				return listado;
			}

			@Override
			public Integer totalResultados(Map<String, Object> parametros) {
				Integer total = pacienteService.totalResultados(parametros);
				return total;
			}

			@Override
			public XulElement renderizar(Object dato) throws Exception {
				return crearFilasMenor(dato, gridResultadoMenor);
			}

		};
		resultadoPaginadoMacroMenor.incicializar(resultadoPaginadoMacroIMG,
				gridResultadoMenor, 7);
	}

	public void llenarCamposMenor(Paciente paciente) {
		tbxDocumento.setValue(paciente.getNro_identificacion() + "-");
		tbxLugar_exp.setValue(paciente.getLugar_exp());
		Utilidades.seleccionarListitem(lbxUnidad_medidad, "3");
		lbxUnidad_medidad.setDisabled(true);
		tbxNombre1.setValue(paciente.getNombre1());
		tbxApellido1.setValue("HIJO DE");
		Utilidades.seleccionarListitem(lbxCodigo_dpto,
				paciente.getCodigo_dpto());
		Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
				municipiosService);
		Utilidades.seleccionarListitem(lbxCodigo_municipio,
				paciente.getCodigo_municipio());
		tbxDireccion.setValue(paciente.getDireccion());
		dbxTel_res.setValue(ConvertidorDatosUtil.convertirDato(paciente
				.getTel_res()));
		dbxTel_oficina.setValue(ConvertidorDatosUtil.convertirDato(paciente
				.getTel_oficina()));
		Utilidades.seleccionarListitem(lbxCodigo_educativo, "13");
		Utilidades.seleccionarListitem(lbxPertenencia_etnica,
				paciente.getPertenencia_etnica());
		Utilidades.setValueFrom(lbxPertenencia_etnica,
				paciente.getPertenencia_etnica());
		Utilidades.seleccionarListitem(lbxNivel_sisben,
				paciente.getNivel_sisben());
		dtbxFecha_afiliacion.setValue(null);
		Utilidades.seleccionarListitem(lbxEstado_civil, "");
		lbxEstado_civil.setDisabled(true);
		Ocupaciones ocupaciones = new Ocupaciones();
		ocupaciones.setId("9998");
		ocupaciones = ocupacionesService.consultar(ocupaciones);
		if (tbxCodigo_ocupacion.getText().isEmpty()) {
			tbxCodigo_ocupacion.setValue((ocupaciones != null ? ocupaciones
					.getId() : ""));
			tbxInfoOcupacion.setValue((ocupaciones != null ? ocupaciones
					.getNombre() : ""));
		}

		Adicional_paciente adicional_paciente = new Adicional_paciente();
		adicional_paciente.setCodigo_empresa(paciente.getCodigo_empresa());
		adicional_paciente.setCodigo_sucursal(paciente.getCodigo_sucursal());
		adicional_paciente.setNro_identificacion(paciente
				.getNro_identificacion());

		adicional_paciente = generalExtraService.consultar(adicional_paciente);

		if (adicional_paciente == null) {
			adicional_paciente = new Adicional_paciente();
			adicional_paciente.setCodigo_empresa(paciente.getCodigo_empresa());
			adicional_paciente
					.setCodigo_sucursal(paciente.getCodigo_sucursal());
			adicional_paciente.setNro_identificacion(paciente
					.getNro_identificacion());
		}

		Barrio barrio = new Barrio();
		barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente
				.getCodigo_barrio() : "");
		barrio = barrioService.consultar(barrio);
		tbxCod_bar.setValue(barrio != null ? barrio.getCodigo_barrio() : "");
		tbxInfoBar.setValue(barrio != null ? barrio.getBarrio() : "");

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(paciente.getCodigo_empresa());
		administradora.setCodigo_sucursal(paciente.getCodigo_sucursal());
		administradora.setCodigo(paciente.getCodigo_administradora());
		administradora = administradoraService.consultar(administradora);

		tbxCodigo_administradora
				.setValue(administradora != null ? administradora.getCodigo()
						: "");
		tbxNom_administradora.setValue(administradora != null ? administradora
				.getCodigo() + " " + administradora.getNombre() : "");

		listarContratos(listboxContratos, tbxCodigo_administradora.getValue(),
				true, false);

		Utilidades.seleccionarListitem(lbxTipo_usuario,
				paciente.getTipo_usuario());

		listarTipo_afiliado(lbxTipo_afiliado, lbxTipo_usuario);

		Utilidades.setValueFrom(lbxPertenencia_etnica,
				paciente.getPertenencia_etnica());

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);
		parametros.put("nro_identificacion", paciente.getNro_identificacion());
		parametros.put("codigo_administradora",
				paciente.getCodigo_administradora());

		List<Pacientes_contratos> listado_contratos = pacientes_contratosService
				.listar(parametros);

		for (Pacientes_contratos pacientes_contratos : listado_contratos) {
			for (Listitem listitem : listboxContratos.getItems()) {
				Contratos contratos = (Contratos) listitem.getValue();
				if (pacientes_contratos.getId_codigo().equals(
						contratos.getId_plan())) {
					listitem.setSelected(true);
					break;
				}

			}
		}
		tbxidentificacion_ms.setValue("");
	}

	public Row crearFilasMenor(Object objeto, Component componente)
			throws Exception {
		Row fila = new Row();

		final Paciente paciente = (Paciente) objeto;

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(paciente.getCodigo_empresa());
		administradora.setCodigo_sucursal(paciente.getCodigo_sucursal());
		administradora.setCodigo(paciente.getCodigo_administradora());
		administradora = administradoraService.consultar(administradora);

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(paciente.getTipo_identificacion() + ""));
		fila.appendChild(new Label(paciente.getDocumento() + ""));
		fila.appendChild(new Label(paciente.getCodigo_administradora() + " - "
				+ (administradora != null ? administradora.getNombre() : "")));
		fila.appendChild(new Label(paciente.getApellido1() + " "
				+ paciente.getApellido2()));
		fila.appendChild(new Label(paciente.getNombre1() + " "
				+ paciente.getNombre2()));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(paciente.getFecha_nacimiento())));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/atendido.png");
		img.setTooltiptext("Aceptar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				llenarCamposMenor(paciente);
				windowMenorSinIdentificacion.setVisible(false);
			}
		});
		hbox.appendChild(img);

		hbox.appendChild(space);

		fila.appendChild(hbox);

		return fila;
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Paciente paciente = (Paciente) objeto;

		Administradora administradora = new Administradora();
		administradora.setCodigo_empresa(paciente.getCodigo_empresa());
		administradora.setCodigo_sucursal(paciente.getCodigo_sucursal());
		administradora.setCodigo(paciente.getCodigo_administradora());
		administradora = administradoraService.consultar(administradora);

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(paciente.getTipo_identificacion() + ""));
		fila.appendChild(new Label(paciente.getDocumento() + ""));
		fila.appendChild(new Label(paciente.getCodigo_administradora() + " - "
				+ (administradora != null ? administradora.getNombre() : "")));
		fila.appendChild(new Label(paciente.getApellido1() + " "
				+ paciente.getApellido2()));
		fila.appendChild(new Label(paciente.getNombre1() + " "
				+ paciente.getNombre2()));
		fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
				.format(paciente.getFecha_nacimiento())));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {

				mostrarDatos(paciente);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/eliminar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener<Event>() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(paciente);
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
			if (validarForm()) {
				final List<Contratos> listado_contratos = new ArrayList<Contratos>();

				for (Listitem listitem : listboxContratos.getSelectedItems()) {
					Contratos contratos = (Contratos) listitem.getValue();
					listado_contratos.add(contratos);
				}
				if (listado_contratos.isEmpty()) {
					Messagebox
							.show("Este paciente no presenta asignacion de contratos. ¿Esta seguro que desea guardarlo sin contratos?",
									"Paciente sin asignacion de contratos",
									Messagebox.YES + Messagebox.NO,
									Messagebox.QUESTION,
									new org.zkoss.zk.ui.event.EventListener<Event>() {
										public void onEvent(Event event)
												throws Exception {
											if ("onYes".equals(event.getName())) {
												guardarDatosPaciente(listado_contratos);
											}
										}
									});
				} else {
					guardarDatosPaciente(listado_contratos);
				}
			}

		} catch (ValidacionRunTimeException e) {
			MensajesUtil.mensajeAlerta("Advertencia", e.getMessage());
		} catch (Exception e) {
			if (!(e instanceof WrongValueException)) {
				MensajesUtil.mensajeError(e, "", this);
			} else {
				// log.info("Error de componentes invalidos ====> "
				// + ((WrongValueException) e).getComponent().getId());
			}
		}
	}

	private void guardarDatosPaciente(List<Contratos> listado_contratos)
			throws Exception {
		FormularioUtil.setUpperCase(this);

		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(empresa.getCodigo_empresa());
		paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
		paciente.setTipo_identificacion(lbxTipo_identificacion
				.getSelectedItem().getValue().toString());
		paciente.setNro_identificacion(getNroIdentificacionAccion());
		paciente.setDocumento(tbxDocumento.getValue());
		paciente.setCodigo_administradora(tbxCodigo_administradora.getValue());
		paciente.setTipo_usuario(lbxTipo_usuario.getSelectedItem().getValue()
				.toString());
		paciente.setApellido1(tbxApellido1.getValue());
		paciente.setApellido2(tbxApellido2.getValue());
		paciente.setNombre1(tbxNombre1.getValue());
		paciente.setNombre2(tbxNombre2.getValue());
		paciente.setFecha_nacimiento(new Timestamp(dtbxFecha_nacimiento
				.getValue().getTime()));
		paciente.setEdad(tbxEdad.getValue());
		paciente.setUnidad_medidad(lbxUnidad_medidad.getSelectedItem()
				.getValue().toString());
		paciente.setSexo(lbxSexo.getSelectedItem().getValue().toString());
		paciente.setCodigo_dpto(lbxCodigo_dpto.getSelectedItem().getValue()
				.toString());
		paciente.setCodigo_municipio(lbxCodigo_municipio.getSelectedItem()
				.getValue().toString());
		paciente.setZona(lbxZona.getSelectedItem().getValue().toString());
		paciente.setLugar_exp(tbxLugar_exp.getValue());
		paciente.setProfesion(tbxProfesion.getValue());
		// paciente.setTel_oficina(String.valueOf(dbxTel_oficina
		// .getValue()));
		paciente.setTel_oficina(dbxTel_oficina.getText());
		// paciente.setTel_res(String.valueOf(dbxTel_res.getValue()));
		paciente.setTel_res(dbxTel_res.getText());
		paciente.setDireccion(tbxDireccion.getValue());
		paciente.setEstado_civil(lbxEstado_civil.getSelectedItem().getValue()
				.toString());
		paciente.setPaciente_particula("N");
		paciente.setEstrato(lbxEstrato.getSelectedItem().getValue().toString());
		paciente.setTipo_afiliado(lbxTipo_afiliado.getSelectedItem().getValue()
				.toString());
		paciente.setCreacion_date(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		paciente.setUltimo_update(new Timestamp(new GregorianCalendar()
				.getTimeInMillis()));
		paciente.setCreacion_user(usuarios.getCodigo());
		paciente.setUltimo_user(usuarios.getCodigo());
		paciente.setIngresos((dbxIngresos.getValue() != null ? dbxIngresos
				.getValue() : 0.00));
		paciente.setLogin("");
		paciente.setPassword("");
		paciente.setNivel_sisben(lbxNivel_sisben.getSelectedItem().getValue()
				.toString());

		// log.info("Ingresos: " + paciente.getIngresos());
		paciente.setPertenencia_etnica(lbxPertenencia_etnica.getSelectedItem()
				.getValue().toString());
		paciente.setCodigo_educativo(lbxCodigo_educativo.getSelectedItem()
				.getValue().toString());
		paciente.setCodigo_ocupacion(tbxCodigo_ocupacion.getValue());
		paciente.setArea_paciente(lbxArea_paciente.getSelectedItem().getValue()
				.toString());
		paciente.setNivel_sisben(lbxNivel_sisben.getSelectedItem().getValue()
				.toString());

		Admision admision_rn = new Admision();

		Map<String, Object> mapa_datos = new HashMap<String, Object>();
		mapa_datos.put("paciente", paciente);
		mapa_datos.put("accion", tbxAccion.getText());
		if (admision_madre != null) {
			admision_rn.setAplica_triage(false);
			admision_rn.setAtendida(false);
			admision_rn.setCama(admision_madre.getCama());
			admision_rn.setCausa_externa(admision_madre.getCausa_externa());
			admision_rn.setCodigo_administradora(admision_madre
					.getCodigo_administradora());
			admision_rn.setCodigo_cita("");
			admision_rn.setCodigo_empresa(codigo_empresa);
			admision_rn.setCodigo_especialidad(admision_madre
					.getCodigo_especialidad());
			admision_rn.setCodigo_medico(admision_madre.getCodigo_medico());
			admision_rn.setCodigo_sucursal(codigo_sucursal);
			admision_rn.setCondicion_usuaria(admision_madre
					.getCondicion_usuaria());
			admision_rn.setCreacion_date(new Timestamp((new Date()).getTime()));
			admision_rn.setCreacion_user(usuarios.getCodigo());
			admision_rn.setDelete_date(new Timestamp((new Date()).getTime()));
			admision_rn.setDelete_user(usuarios.getCodigo());
			admision_rn.setDiagnostico_ingreso(admision_madre
					.getDiagnostico_ingreso());
			admision_rn.setEstado(admision_madre.getEstado());
			admision_rn.setFecha_ingreso(new Timestamp((new Date()).getTime()));
			admision_rn.setGrado_discapacidad(admision_madre
					.getGrado_discapacidad());
			admision_rn.setHospitalizacion("N");
			admision_rn.setId_plan(admision_madre.getId_plan());
			admision_rn.setIngreso_programa(admision_madre
					.getIngreso_programa());
			admision_rn.setNro_autorizacion(admision_madre
					.getNro_autorizacion());
			admision_rn.setNro_identificacion(paciente.getNro_identificacion());
			admision_rn.setNro_ingreso(nro_ingreso_rn);
			admision_rn.setPrimera_vez(admision_madre.getPrimera_vez());
			admision_rn.setRealizo_triage(false);
			admision_rn.setRecien_nacido("S");
			admision_rn.setTipo_atencion(admision_madre.getTipo_atencion());
			admision_rn.setTipo_diagnostico(admision_madre
					.getTipo_diagnostico());
			admision_rn.setTipo_discapacidad(admision_madre
					.getTipo_discapacidad());
			admision_rn.setUltimo_update(new Timestamp((new Date()).getTime()));
			admision_rn.setUltimo_user(usuarios.getCodigo());
			admision_rn.setUrgencias("N");
			admision_rn.setTipo_consulta("");
			admision_rn.setTipo_adicional_via_ingreso("");
			admision_rn
					.setVia_ingreso(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO);
			admision_rn.setCodigo_centro(admision_madre.getCodigo_centro());
			admision_rn.setAdmision_parto("N");
			admision_rn.setParticular(admision_madre.getParticular());
			admision_rn.setTipo_psicologia("");
			admision_rn.setPaciente(paciente);
			mapa_datos.put("admision_rn", admision_rn);
		}

		mapa_datos.put("listado_contratos", listado_contratos);

		Adicional_paciente adicional_paciente = new Adicional_paciente();
		adicional_paciente.setCodigo_empresa(paciente.getCodigo_empresa());
		adicional_paciente.setCodigo_sucursal(paciente.getCodigo_sucursal());
		adicional_paciente.setNro_identificacion(paciente
				.getNro_identificacion());
		adicional_paciente.setCarnet("");
		if (dtbxFecha_afiliacion.getValue() != null) {
			adicional_paciente.setFecha_afiliacion(new Timestamp(
					dtbxFecha_afiliacion.getValue().getTime()));
		}
		adicional_paciente.setTipo_poblacion("5");
		adicional_paciente.setFicha_sisben("0");
		adicional_paciente.setModalidad_subsidio("ST");
		adicional_paciente.setCodigo_barrio(tbxCod_bar.getValue());

		paciente.setLogin(tbxLogin.getValue());
		paciente.setPassword(tbxPassword.getValue());

		mapa_datos.put("adicional_paciente", adicional_paciente);
		mapa_datos.put("parametros_empresa", parametros_empresa);
		pacienteService.guardarDatos(mapa_datos);

		nro_ingreso_rn = admision_rn.getNro_ingreso();
		paciente = pacienteService.consultar(paciente);
		mostrarDatos(paciente);

		/**
		 * Esto me permite saber cuando otra vista necesite saber cuando, otra
		 * desde otra vista se neceite registrar, un paciente.
		 *
		 */
		if (onRegistroEvent != null) {
			onRegistroEvent.registroGuardado(paciente);
		}

		Messagebox.show("Los datos se guardaron satisfactoriamente",
				"Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Paciente paciente = (Paciente) obj;
		try {
			// Limpiamos componentes
			FormularioUtil.limpiarComponentesRestricciones(groupboxEditar);

			Adicional_paciente adicional_paciente = new Adicional_paciente();
			adicional_paciente.setCodigo_empresa(paciente.getCodigo_empresa());
			adicional_paciente
					.setCodigo_sucursal(paciente.getCodigo_sucursal());
			adicional_paciente.setNro_identificacion(paciente
					.getNro_identificacion());

			adicional_paciente = generalExtraService
					.consultar(adicional_paciente);

			if (adicional_paciente == null) {
				adicional_paciente = new Adicional_paciente();
				adicional_paciente.setCodigo_empresa(paciente
						.getCodigo_empresa());
				adicional_paciente.setCodigo_sucursal(paciente
						.getCodigo_sucursal());
				adicional_paciente.setNro_identificacion(paciente
						.getNro_identificacion());
			}

			Utilidades.seleccionarListitem(lbxTipo_identificacion,
					paciente.getTipo_identificacion());

			tbxNro_identificacion.setValue(paciente.getNro_identificacion());
			tbxDocumento.setValue(paciente.getDocumento());

			Administradora administradora = new Administradora();
			administradora.setCodigo_empresa(paciente.getCodigo_empresa());
			administradora.setCodigo_sucursal(paciente.getCodigo_sucursal());
			administradora.setCodigo(paciente.getCodigo_administradora());
			administradora = administradoraService.consultar(administradora);

			tbxCodigo_administradora
					.setValue(administradora != null ? administradora
							.getCodigo() : "");
			tbxNom_administradora
					.setValue(administradora != null ? administradora
							.getCodigo() + " " + administradora.getNombre()
							: "");

			listarContratos(listboxContratos,
					tbxCodigo_administradora.getValue(), true, false);

			Utilidades.seleccionarListitem(lbxTipo_usuario,
					paciente.getTipo_usuario());

			listarTipo_afiliado(lbxTipo_afiliado, lbxTipo_usuario);

			tbxApellido1.setValue(paciente.getApellido1());
			tbxApellido2.setValue(paciente.getApellido2());
			tbxNombre1.setValue(paciente.getNombre1());
			tbxNombre2.setValue(paciente.getNombre2());
			dtbxFecha_nacimiento.setValue(paciente.getFecha_nacimiento());

			tbxEdad.setValue(paciente.getEdad() != null ? paciente.getEdad()
					: "");

			Utilidades.seleccionarListitem(lbxUnidad_medidad,
					paciente.getUnidad_medidad());

			Utilidades.seleccionarListitem(lbxSexo, paciente.getSexo());
			Utilidades.seleccionarListitem(lbxCodigo_dpto,
					paciente.getCodigo_dpto());
			Utilidades.listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto,
					municipiosService);

			Utilidades.seleccionarListitem(lbxCodigo_municipio,
					paciente.getCodigo_municipio());
			Utilidades.seleccionarListitem(lbxZona, paciente.getZona());

			tbxLugar_exp.setValue(paciente.getLugar_exp());
			tbxProfesion.setValue(paciente.getProfesion());
			if (!paciente.getTel_oficina().isEmpty()
					&& paciente.getTel_oficina().matches("[0-9]*$")) {
				dbxTel_oficina.setText(paciente.getTel_oficina());
			}
			if (paciente.getTel_res() != null
					&& !paciente.getTel_res().isEmpty()
					&& paciente.getTel_res().matches("[0-9]*$")) {
				dbxTel_res.setText(paciente.getTel_res());
			}
			tbxDireccion.setValue(paciente.getDireccion());

			Utilidades.seleccionarListitem(lbxEstado_civil,
					paciente.getEstado_civil());
			Utilidades.seleccionarListitem(lbxEstrato, paciente.getEstrato());
			Utilidades.seleccionarListitem(lbxTipo_afiliado,
					paciente.getTipo_afiliado());

			dbxIngresos.setValue(paciente.getIngresos());

			Utilidades.setValueFrom(lbxPertenencia_etnica,
					paciente.getPertenencia_etnica());
			Utilidades.setValueFrom(lbxCodigo_educativo,
					paciente.getCodigo_educativo());

			Ocupaciones ocupaciones = new Ocupaciones();
			ocupaciones.setId(paciente.getCodigo_ocupacion());
			ocupaciones = ocupacionesService.consultar(ocupaciones);
			tbxCodigo_ocupacion.setValue(paciente.getCodigo_ocupacion());
			tbxInfoOcupacion.setValue((ocupaciones != null ? ocupaciones
					.getNombre() : ""));

			Barrio barrio = new Barrio();
			barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente
					.getCodigo_barrio() : "");
			barrio = barrioService.consultar(barrio);
			tbxCod_bar
					.setValue(barrio != null ? barrio.getCodigo_barrio() : "");
			tbxInfoBar.setValue(barrio != null ? barrio.getBarrio() : "");

			Utilidades.seleccionarListitem(lbxArea_paciente,
					paciente.getArea_paciente());
			Utilidades.seleccionarListitem(lbxNivel_sisben,
					paciente.getNivel_sisben());

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("codigo_empresa", codigo_empresa);
			parametros.put("codigo_sucursal", codigo_sucursal);
			parametros.put("nro_identificacion",
					paciente.getNro_identificacion());
			parametros.put("codigo_administradora",
					paciente.getCodigo_administradora());

			List<Pacientes_contratos> listado_contratos = pacientes_contratosService
					.listar(parametros);

			for (Pacientes_contratos pacientes_contratos : listado_contratos) {
				for (Listitem listitem : listboxContratos.getItems()) {
					Contratos contratos = (Contratos) listitem.getValue();
					if (pacientes_contratos.getId_codigo().equals(
							contratos.getId_plan())) {
						listitem.setSelected(true);
						break;
					}
				}
			}

			tbxPassword.setValue(paciente.getPassword());
			tbxLogin.setValue(paciente.getLogin() != null ? paciente.getLogin()
					: "");

			tbxDocumento.setReadonly(!rol_usuario.equals(IRoles.ADMINISTRADOR));
			obtenerEdad(true);

			if (adicional_paciente.getFecha_afiliacion() != null) {
				dtbxFecha_afiliacion.setValue(adicional_paciente
						.getFecha_afiliacion());
			}

			imgState.setSrc(tbxLogin.getValue().isEmpty() ? null : IMAGE_OK);

			tbxAccion.setText("modificar");
			accionForm(true, tbxAccion.getText());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show("Este dato no se puede editar", "Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Paciente paciente = (Paciente) obj;
		try {
			paciente.setDelete_user(getUsuarios().getCodigo());
			int result = pacienteService.eliminar(paciente);
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

	public void listarMunicipios(Listbox listboxMun, Listbox listboxDpto) {
		Utilidades.listarMunicipios(listboxMun, listboxDpto, municipiosService);
	}

	public void listarTipo_afiliado(Listbox listboxAfiliado,
			Listbox listboxRegimen) {
		Utilidades.listarTipo_afiliado(listboxAfiliado, listboxRegimen,
				elementoService);
	}

	public void buscarAdministradora(String value, Listbox lbx)
			throws Exception {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("codigo_empresa", empresa.getCodigo_empresa());
			parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
			parameters.put("paramTodo", "");
			parameters.put("value", value.toLowerCase().trim());

			administradoraService.setLimit("limit 25 offset 0");
			List<Administradora> list = administradoraService
					.listar(parameters);

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

		listbox.getItems().clear();

		Listitem listitem;

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("codigo_empresa", codigo_empresa);
		parameters.put("codigo_sucursal", codigo_sucursal);
		parameters.put("codigo_administradora", codigo_admin);
		if (solo_abiertos) {
			parameters.put("cerrado", false);
		}
		List<Contratos> lista_contratos = contratosService.listar(parameters);

		for (Contratos contratos : lista_contratos) {
			listitem = new Listitem();
			/*
			 * Este parametro me permite habilitar la carga de conratos
			 * capitados por medio de un facturador
			 */
			if (getParametros_empresa()
					.getHabilitar_asignacion_cont_cap_facturador().equals("N")
					&& !habilitado_contrato
					&& !contratos.getTipo_facturacion().equals("02")) {
				listitem.setDisabled(true);
			}
			listitem.setValue(contratos);
			listitem.appendChild(new Listcell());
			listitem.appendChild(new Listcell(contratos.getId_plan() + " - "
					+ contratos.getNombre()));

			Elemento elemento = new Elemento();
			String tipo_contrato = "tipo_contrato";
			elemento.setCodigo(contratos.getTipo_facturacion());
			elemento.setTipo(tipo_contrato);
			elemento = elementoService.consultar(elemento);
			listitem.appendChild(new Listcell(elemento.getDescripcion() + ""));

			listitem.appendChild(new Listcell(
					contratos.getCerrado() ? "Cerrado" : "Habilitado"));

			// Marcamos el item, para identificar que esta bloqueado
			if (contratos.getCerrado()) {
				listitem.setStyle("background-color:#ccffff");
				listitem.setTooltiptext("Contrato cancelado");
			}

			if (contratos.getTipo_facturacion().equals("04")) {
				listitem.setStyle("background-color:#00cc00");
			}

			if (contratos.getTipo_facturacion().equals("02")) {
				listitem.setStyle("background-color:#F7926A");
			}

			listbox.appendChild(listitem);
		}
	}

	private String getNroIdentificacionAccion() {
		return (tbxAccion.getValue().equalsIgnoreCase("registrar") ? tbxDocumento
				.getValue().toUpperCase() : tbxNro_identificacion.getValue()
				.toUpperCase());
	}

	public void consultarExistenciaPaciente() {
		try {
			String nro_identificacion = getNroIdentificacionAccion();
			if (!nro_identificacion.isEmpty()
					&& !tbxDocumento.getValue().isEmpty()) {
				Paciente paciente = new Paciente();
				paciente.setCodigo_empresa(codigo_empresa);
				paciente.setCodigo_sucursal(codigo_sucursal);
				paciente.setTipo_identificacion(lbxTipo_identificacion
						.getSelectedItem().getValue().toString());
				paciente.setNro_identificacion(getNroIdentificacionAccion());
				paciente.setDocumento(tbxDocumento.getValue());

				paciente = pacienteService.consultarPorDocumento(paciente);

				if (paciente != null) {
					MensajesUtil.mensajeAlerta("Nro de documento ya existe",
							"Ya existe un paciente con este numero de documento. "
									+ paciente.getTipo_identificacion()
									+ paciente.getDocumento() + " - "
									+ paciente.getNombreCompleto(),
							new EventListener<Event>() {

								@Override
								public void onEvent(Event arg0)
										throws Exception {
									tbxDocumento.setFocus(true);
								}
							});
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public void MostrarListboxSisben(Cell cell) {
		if (lbxTipo_usuario.getSelectedItem().getValue().equals("2")) {
			cell.setVisible(true);
		} else {
			cell.setVisible(false);
		}
	}

	private void validateLogin(Event event) {
		// activo.gif - estado_error.gif
		String login = tbxLogin.getValue();
		if (event instanceof InputEvent) {
			login = ((InputEvent) event).getValue();
		}
		if (login != null) {
			if (!login.trim().isEmpty()) {
				login = login.toUpperCase();
				Paciente paciente = new Paciente();
				paciente.setLogin(login);
				paciente.setCodigo_empresa(codigo_empresa);
				paciente.setCodigo_sucursal(codigo_sucursal);
				paciente.setNro_identificacion(paciente.getNro_identificacion());

				paciente = pacienteService.consultarPorLoginPassword(paciente);
				boolean existPacinet = paciente != null;
				// log.info("::-PACIENTE1" + paciente);

				Usuarios usuarios = new Usuarios();
				usuarios.setLogin(login);
				boolean existUsuario = usuariosService.consultar(usuarios) != null;

				// log.info("::-" + login);
				// log.info("Exist Paciente: " + existPacinet + " - "
				// + existUsuario);
				// if (existPacinet) {
				// //log.info("Exist Paciente Nro ID: "
				// + paciente.getNro_identificacion() + " - "
				// + tbxNro_identificacion.getValue());
				// if (paciente.getNro_identificacion().equals(
				// tbxNro_identificacion.getValue())) {
				// existPacinet = false;
				// }
				// }
				if (existPacinet || existUsuario) {
					imgState.setSrc(IMAGE_CANCEL);
					imgState.setTooltiptext("Este login ya existe");
				} else if (!login.trim().isEmpty()) {
					imgState.setSrc(IMAGE_OK);
					imgState.setTooltiptext("Login Correcto");
				} else {
					noImage();
				}
			} else {
				noImage();
			}
		} else {
			noImage();
		}
		imgState.applyProperties();
	}

	private void noImage() {
		imgState.setSrc(null);
		imgState.setTooltiptext("");
	}

	public IOnRegistroEvent<Paciente> getOnRegistroEvent() {
		return onRegistroEvent;
	}

	public void setOnRegistroEvent(IOnRegistroEvent<Paciente> onRegistroEvent) {
		this.onRegistroEvent = onRegistroEvent;
	}
}
