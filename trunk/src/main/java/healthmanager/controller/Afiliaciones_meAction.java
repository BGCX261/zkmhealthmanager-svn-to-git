/*
 * afiliaciones_meAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.controller.Beneficiarios_meAction.ACCION_BENEFICIARIOS;
import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Afiliaciones_me;
import healthmanager.modelo.bean.Aportantes_ma;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Historial_observaciones;
import healthmanager.modelo.bean.Localidad;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Ocupaciones;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Pacientes_contratos;
import healthmanager.modelo.bean.Solicitud_e1;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.AdministradoraService;
import healthmanager.modelo.service.Afiliaciones_meService;
import healthmanager.modelo.service.BarrioService;
import healthmanager.modelo.service.Historial_observacionesService;
import healthmanager.modelo.service.OcupacionesService;
import healthmanager.modelo.service.PacienteService;
import healthmanager.modelo.service.Pacientes_contratosService;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.zkoss.image.AImage;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Auxheader;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
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
import org.zkoss.zul.Popup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IAfiliaciones;
import com.framework.constantes.IConstantes;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.HistorialObservacionMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesComponentes;

import contaweb.modelo.bean.Tercero;
import healthmanager.modelo.service.GeneralExtraService;

public class Afiliaciones_meAction extends ZKWindow {

    public static final String IMAGE_OK = "/images/activo.gif";
    public static final String IMAGE_CANCEL = "/images/estado_error.gif";

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
    private BandboxRegistrosMacro tbxCodigo_aportante;
    // private Textbox tbxNro_identificacion_afiliado;
    @SuppressWarnings("unused")
    private Listbox lbxTipo_afiliado;
    @SuppressWarnings("unused")
    private Listbox lbxParenteco_cotizante;
    @View
    private Listbox lbxDepartamento_afiliacion;
    @View
    private Listbox lbxMunicipio_afiliacion;
    // private Listbox lbxZona_afiliacion;
    @View
    private Datebox dtbxFecha_afiliacion;
    @View
    private Textbox tbxNomAportante;
    @View
    private Datebox dtbxFecha_vencimiento;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_aportante2;
    @View
    private Textbox tbxNomAportante2;

    @View
    private Toolbarbutton tbxAportanteBorrar;
    @View
    private Toolbarbutton tbxAportanteBorrar2;

    /* datos del paciente */
    @View
    private Listbox lbxTipo_identificacion;
    @View
    private Textbox tbxNro_identificacion;
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
    private Textbox tbxProfesion;
    @View
    private Textbox tbxTel_oficina;
    @View
    private Textbox tbxDireccion;
    @View
    private Listbox lbxEstado_civil;
    @View
    private Listbox lbxEstrato;
    @View
    private Listbox lbxTipo_usuario;
    @View
    private Listbox lbxPertenencia_etnica;

    @View
    private Listbox listboxContratos;

    @View
    private Textbox tbxInfoOcupacion;
    @View
    private Toolbarbutton btnLimpiarOcupacion;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_ocupacion;
    @View
    private Listbox lbxCodigo_educativo;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCod_bar;

    @View
    private Textbox tbxInfoBar;
    @View
    private Toolbarbutton btnLimpiarBar;

    @View
    private Image imgState;

    @View
    private Textbox tbxObservaciones;

    @View
    private Textbox tbxLogin;
    @View
    private Textbox tbxPassword;

    /* nuevos campos */
    // @View private Listbox lbxEscolaridad;
    @View
    private Checkbox chbDiscapacidad;
    @View
    private Textbox tbxCedula_tutor;
    @View
    private Textbox tbxCodigo_empleado1;
    @View
    private Textbox tbxCodigo_empleado2;
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxIdentificacion_ibc;
    @View
    private Textbox tbxDescripcion_discapacidad;
    @View
    private Label lbDescripcion_discapacidad;

    @View
    private Listbox lbxTipo_empleado;
    @View
    private Listbox lbxNivel_sisben;
    @View
    private Listbox lbxEstadoAfiliacion;
    @View
    private Textbox tbxNombre_universidad;
    @View
    private Label lbNombre_universidad;
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxUniversidad;

    @View
    private Toolbarbutton btnLimpiarAseguradora;

    @View
    private Textbox tbxNomIBC;

    @View
    private Grid grid_beneficiarios;
    @View
    private Rows rows_beneficiarios;

    @View
    private Listbox lbxParameterAfiliaciones;

    /* columns */
    @View
    private Column column_CotizanteName;
    @View
    private Column colum_idaportante;
    @View
    private Listbox lbxRh;

    @View
    private Auxheader auzheader;
    @View
    private Checkbox chbConvension;
    @View
    private Image imageUsuario;
    @View
    private Datebox dtbxFecha_vinculacion;

    @View(isMacro = true)
    private HistorialObservacionMacro historialObservacionMacro;

    @View
    private Tab tabDatosCotizante;
    @View
    private Doublebox dbxCuota_moderadora;
    @View
    private Doublebox dbxPorcentaje_copago;

    private List<Afiliaciones_me> list_afiliacionesMesBeneficiarios;
    private Solicitud_e1 solicitud_e1;
    private Afiliaciones_me afiliaciones_meGlobal;
    private byte[] bytefoto = null;
    private Rows rowsCotizante;

    @SuppressWarnings("unchecked")
    private void verificamosSolicitud() {
        try {
            Map<String, Object> map = (Map<String, Object>) Executions
                    .getCurrent().getArg();
            if (map != null) {
                solicitud_e1 = (Solicitud_e1) map.get("SE1");
                if (solicitud_e1 != null) {
                    mostrarDatos(solicitud_e1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void loadEvents() {
        chbDiscapacidad.addEventListener("onCheck", new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                verificarSeleccionDiscapacidad();
            }
        });

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
                paciente = getServiceLocator().getPacienteService().consultar(
                        paciente);
                // log.info("::-" + paciente);
                boolean existPacinet = paciente != null;

                Usuarios usuarios = new Usuarios();
                usuarios.setLogin(login);
                boolean existUsuario = getServiceLocator().getUsuariosService()
                        .consultar(usuarios) != null;

				// log.info("::-" + login);
                // log.info("Exist Paciente: " + existPacinet + " - "
                // + existUsuario);
                if (existPacinet) {
					// log.info("Exist Paciente Nro ID: "
                    // + paciente.getNro_identificacion() + " - "
                    // + tbxNro_identificacion.getValue());
                    if (paciente.getNro_identificacion().equals(
                            tbxNro_identificacion.getValue())) {
                        existPacinet = false;
                    }
                }

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

    private void verificarSeleccionDiscapacidad() {
        setVisibleDiscapacidad(chbDiscapacidad.isChecked());
    }

    public void borrarImagen() throws Exception {
        ServletContext servletContext = (ServletContext) Executions
                .getCurrent().getDesktop().getWebApp().getServletContext();
        AImage aImage = new AImage(new File(
                servletContext.getRealPath("/images/perfil.gif")));
        imageUsuario.setContent(aImage);
        bytefoto = null;
    }

    public void guardarImagen(Media media) throws Exception {
        try {
            if (media instanceof org.zkoss.image.Image) {
                AImage aImage = new AImage("foto_usr", media.getByteData());
                imageUsuario.setContent(aImage);
                bytefoto = media.getByteData();
            } else {
                Messagebox.show("No es una foto: " + media, "Error",
                        Messagebox.OK, Messagebox.EXCLAMATION);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Error cargando foto!!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    private void listarParamAfiliados() {
        lbxParameterAfiliaciones.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("");
        listitem.setLabel("Todos");
        lbxParameterAfiliaciones.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("C");
        listitem.setLabel("Cotizantes");
        lbxParameterAfiliaciones.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("B");
        listitem.setLabel("Beneficiarios");
        lbxParameterAfiliaciones.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("T");
        listitem.setLabel("Adicional");
        lbxParameterAfiliaciones.appendChild(listitem);

        if (lbxParameterAfiliaciones.getItemCount() > 0) {
            lbxParameterAfiliaciones.setSelectedIndex(0);
        }

        /* cargamos evento */
        lbxParameterAfiliaciones.addEventListener("onSelect",
                new EventListener<Event>() {
                    @Override
                    public void onEvent(Event arg0) throws Exception {
                        log.info("Prueba");
                        buscarDatos();
                    }
                });
    }

    public void mostrarBeneficiarios(
            Afiliaciones_me afiliacionesMeBeneficiario,
            boolean actializar_lista_padre) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accion", ACCION_BENEFICIARIOS.ADD_BENEFICIARIO); // accion
        // agregar
        // beneficiarios
        map.put("AB", afiliacionesMeBeneficiario); // accion modificar
        // beneficiarios
        if (afiliacionesMeBeneficiario != null) {
            Map<String, Object> map_historial = new HashMap<String, Object>();
            map_historial.put("codigo_empresa",
                    afiliacionesMeBeneficiario.getCodigo_empresa());
            map_historial.put("codigo_sucursal",
                    afiliacionesMeBeneficiario.getCodigo_sucursal());
            map_historial
                    .put("id_afiliado", afiliacionesMeBeneficiario.getId());
            List<Historial_observaciones> historial_observaciones = getServiceLocator()
                    .getServicio(Historial_observacionesService.class).listar(
                            map_historial);

            map.put("hist_ob", historial_observaciones);
        }

        if (actializar_lista_padre) {
            map.put("UPdate", true); // accion modificar beneficiarios
        }
        Component componente = Executions.createComponents(
                "/pages/beneficiarios.zul", this, map);
        final Window ventana = (Window) componente;
        ventana.setWidth("100%");
        ventana.setHeight("100%");
        ventana.setVflex("1");
        ventana.doModal();
    }

    public void mostrarBeneficiarios(Afiliaciones_me afiliacionesMeBeneficiario)
            throws Exception {
        mostrarBeneficiarios(afiliacionesMeBeneficiario, false);
    }

    public void mostrarBeneficiariosEnList(
            Afiliaciones_me afiliacionesMeBeneficiario) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("accion", ACCION_BENEFICIARIOS.LIST_BENEFICIARIOS); // accion
        // agregar
        // beneficiarios
        map.put("AB", afiliacionesMeBeneficiario); // accion modificar
        // beneficiarios
        Component componente = Executions.createComponents(
                "/pages/beneficiarios.zul", this, map);
        final Window ventana = (Window) componente;
        ventana.setWidth("100%");
        ventana.setHeight("100%");
        ventana.setVflex("1");
        ventana.doModal();
    }

    public void listarCombos() {
        listarParameter();
        UtilidadesComponentes.listarElementosListbox(false, false,
                getServiceLocator(), lbxZona, lbxUnidad_medidad);
        UtilidadesComponentes.listarElementosListbox(true, false,
                getServiceLocator(), lbxTipo_usuario, lbxEstrato,
                lbxEstado_civil, lbxSexo, lbxTipo_empleado, lbxRh);

        listarDepartamentos(lbxCodigo_dpto, true);
        listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
        listarDepartamentos(lbxDepartamento_afiliacion, true);
        listarMunicipios(lbxMunicipio_afiliacion, lbxDepartamento_afiliacion);
        listarElementoListboxTipoId(lbxTipo_identificacion, true);
        listarParamAfiliados();
        listarPertenenciaEtnica();
        listarNivelEducativo();
        UtilidadesComponentes.listarElementosListbox(true, false,
                getServiceLocator(), lbxNivel_sisben, lbxEstadoAfiliacion);
    }

    private void listarNivelEducativo() {
        Utilidades.listarNivel_educativo(lbxCodigo_educativo, true,
                getServiceLocator());
    }

    private void listarPertenenciaEtnica() {
        Utilidades.listarPertenencia_etnica(lbxPertenencia_etnica, true,
                getServiceLocator());
    }

    public void listarElementoListboxTipoId(Listbox listbox, boolean select) {
        listbox.getChildren().clear();
        Listitem listitem;
        if (select) {
            listitem = new Listitem();
            listitem.setValue("");
            listitem.setLabel("-- seleccione --");
            listbox.appendChild(listitem);
        }

        String tipo = listbox.getName();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("tipo", tipo);
        map.put("permitidos2", "_");

        List<Elemento> elementos = this.getServiceLocator()
                .getElementoService().listar(map);

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

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("pac.documento || ' ' || pac.nombre1 || ' ' || pac.nombre2 || ' ' || pac.apellido1 || ' ' || pac.apellido2");
        listitem.setLabel("Datos del afiliado");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("cotizante.documento || ' ' || cotizante.nombre1 || ' ' || cotizante.nombre2 || ' ' || cotizante.apellido1 || ' ' || cotizante.apellido2");
        listitem.setLabel("Datos del cotizante");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
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

        if (municipios.size() > 0) {
            for (Municipios mun : municipios) {
                listitem = new Listitem();
                listitem.setValue(mun.getCodigo());
                listitem.setLabel(mun.getNombre());
                listboxMun.appendChild(listitem);
            }
        } else {
            listitem = new Listitem();
            listitem.setValue("");
            listitem.setLabel(" -seleccione- ");
            listboxMun.appendChild(listitem);
        }

        if (listboxMun.getItemCount() > 0) {
            listboxMun.setSelectedIndex(0);
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
        FormularioUtil.limpiarComponentes(groupboxEditar);
        listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);
        setVisibleDiscapacidad(false);
        lbxTipo_identificacion.setDisabled(false);
        tbxNro_identificacion.setReadonly(false);
        rows_beneficiarios.getChildren().clear();
        if (list_afiliacionesMesBeneficiarios != null) {
            list_afiliacionesMesBeneficiarios.clear();
        }
        borrarImagen();
        listarContratos(listboxContratos, tbxUniversidad.getValue(), true, true);
        dtbxFecha_nacimiento.setValue(null);
        dtbxFecha_afiliacion.setValue(null);
        dtbxFecha_vencimiento.setValue(null);
        historialObservacionMacro.limpiar();
        tabDatosCotizante.setSelected(true);
        afiliaciones_meGlobal = null;
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
        List<Contratos> lista_contratos = getServiceLocator()
                .getContratosService().listar(parameters);

        for (Contratos contratos : lista_contratos) {
            listitem = new Listitem();
            listitem.setValue(contratos);
            listitem.appendChild(new Listcell());
            listitem.appendChild(new Listcell(contratos.getId_plan() + " - "
                    + contratos.getNombre()));
            listbox.appendChild(listitem);
        }
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {
        boolean valida = true;
        String msj = "Los campos marcados con (*) son obligatorios";
        try {
            FormularioUtil.validarCamposObligatorios(tbxUniversidad,
                    dtbxFecha_nacimiento, lbxTipo_usuario,
                    lbxTipo_identificacion, dtbxFecha_afiliacion,
                    tbxCodigo_aportante, lbxCodigo_dpto,
                    lbxDepartamento_afiliacion, tbxApellido1, tbxNombre1,
                    tbxNro_identificacion, tbxDescripcion_discapacidad, lbxRh,
                    lbxPertenencia_etnica, lbxEstadoAfiliacion, tbxCod_bar,
                    dtbxFecha_vinculacion, dbxCuota_moderadora,
                    dbxPorcentaje_copago);

            Date fecha_actual = Calendar.getInstance().getTime();

            if (valida
                    && dtbxFecha_nacimiento.getValue().compareTo(fecha_actual) > 0) {
                valida = false;
                msj = "La fecha de nacimiento no puede ser mayor que la fecha actual";
            }

            if (valida
                    && dtbxFecha_afiliacion.getValue().compareTo(fecha_actual) > 0) {
                valida = false;
                msj = "La fecha de afiliacion no puede ser mayor que la fecha actual";
            }

            if (valida && tbxCod_bar.getValue().trim().isEmpty()) {
                Clients.scrollIntoView(tbxCod_bar);
                valida = false;
                msj = "El barrio es obligatorio, para la informacion del paciente";
            }

            /**
             * Este metodo me permite, verificar si se repite un beneficiario
             * conyugue
			 *
             */
            if (valida && !list_afiliacionesMesBeneficiarios.isEmpty()) {
                boolean existe_conyugue_activo = false;
                for (Afiliaciones_me afiliaciones_me : list_afiliacionesMesBeneficiarios) {
                    if (afiliaciones_me.getParenteco_cotizante().equals(
                            IConstantes.PARENTESCO_CONYUGE)
                            && afiliaciones_me
                            .getPaciente()
                            .getEstado_afiliacion()
                            .equals(IConstantes.ESTADO_AFILIACION_ACTIVO)) {
                        if (existe_conyugue_activo) {
                            valida = false;
                            msj = "Este cotizante ya tiene mas de un beneficiario tipo conyugue activo";
                            break;
                        }
                        existe_conyugue_activo = true;
                    }
                }
            }

            if (!valida) {
                Messagebox.show(msj,
                        usuarios.getNombres() + " recuerde que...",
                        Messagebox.OK, Messagebox.EXCLAMATION);
            }

        } catch (WrongValueException e) {
            valida = false;
        }
        return valida;
    }

    public void agregarBeneficiarios(Afiliaciones_me afiliacionesMe)
            throws Exception {
        verificamosBeneficiarios(afiliacionesMe);

        list_afiliacionesMesBeneficiarios.add(afiliacionesMe);
        buscarBeneficiarios();
    }

    private void verificamosBeneficiarios(Afiliaciones_me afiliacionesMe)
            throws Exception {
        if (afiliacionesMe.getNro_identificacion_afiliado().equalsIgnoreCase(
                tbxNro_identificacion.getValue())) {
            throw new Exception(
                    "El cotizante no puede ser beneficiario del mismo");
        }
        for (Afiliaciones_me afiliacionesMeTemp : list_afiliacionesMesBeneficiarios) {
            if (afiliacionesMeTemp.getNro_identificacion_afiliado().equals(
                    afiliacionesMe.getNro_identificacion_afiliado())) {
                throw new Exception(
                        "Ya hay un beneficiario con el mismo nro de identificacion\n "
                        + " Id: "
                        + afiliacionesMeTemp
                        .getNro_identificacion_afiliado()
                        + "\n nombre: "
                        + afiliacionesMeTemp.getPaciente().getNombre1());
            }
        }
    }

    private void verificamosIgualdad() throws Exception {
        if (list_afiliacionesMesBeneficiarios != null) {
            for (Afiliaciones_me afiliacionesMeTemp : list_afiliacionesMesBeneficiarios) {
                if (afiliacionesMeTemp.getNro_identificacion_afiliado()
                        .equalsIgnoreCase(tbxNro_identificacion.getValue())) {
                    throw new ValidacionRunTimeException(
                            "El cotizante no puede ser beneficiario del mismo, verifique los la identificacion de los beneficiarios");
                }
            }
        }
    }

    public void buscarBeneficiarios() throws Exception {
        try {
            if (list_afiliacionesMesBeneficiarios != null) {
                rows_beneficiarios.getChildren().clear();

                for (Afiliaciones_me afiliaciones_me : list_afiliacionesMesBeneficiarios) {
                    rows_beneficiarios.appendChild(crearFilasBeneficiarios(
                            afiliaciones_me, this));
                }

                grid_beneficiarios.setVisible(true);
                grid_beneficiarios.setMold("paging");
                grid_beneficiarios.setPageSize(20);

                grid_beneficiarios.applyProperties();
                grid_beneficiarios.invalidate();
                grid_beneficiarios.setVisible(true);
            }
        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Mensaje de Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    private String getDescripcionElement(String codigo, String tipo) {
        Elemento elemento = new Elemento();
        elemento.setTipo(tipo);
        elemento.setCodigo(codigo);
        elemento = getServiceLocator().getElementoService().consultar(elemento);
        return elemento != null ? elemento.getDescripcion() : "";
    }

    public Row crearFilasBeneficiarios(Object objeto, Component componente)
            throws Exception {
        Row fila = new Row();

        final Afiliaciones_me afiliaciones_me = (Afiliaciones_me) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(afiliaciones_me.getPaciente()
                .getTipo_identificacion() + ""));
        fila.appendChild(new Label(afiliaciones_me.getPaciente().getDocumento()
                + ""));
        fila.appendChild(new Label(afiliaciones_me.getPaciente().getNombre1()
                + " " + afiliaciones_me.getPaciente().getNombre2()));
        fila.appendChild(new Label(afiliaciones_me.getPaciente().getApellido1()
                + " " + afiliaciones_me.getPaciente().getApellido2()));
        fila.appendChild(new Label(afiliaciones_me.getPaciente().getSexo()
                .equalsIgnoreCase("M") ? "MASCULINO" : "FEMENINO"));
        fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                .format(afiliaciones_me.getFecha_afiliacion()) + ""));
        fila.appendChild(new Label(getDescripcionElement(
                afiliaciones_me.getParenteco_cotizante(), "parentesco")));
        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarBeneficiarios(afiliaciones_me);
            }
        });
        hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    // Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String parameter = lbxParameter.getSelectedItem().getValue()
                    .toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");
            parameters.put("codigo_empresa", usuarios.getCodigo_empresa());
            parameters.put("codigo_sucursal", usuarios.getCodigo_sucursal());

            if (!lbxParameterAfiliaciones.getSelectedItem().getValue()
                    .toString().isEmpty()) {
                parameters.put("tipo_afiliado", lbxParameterAfiliaciones
                        .getSelectedItem().getValue().toString());
            }

            getServiceLocator().getAfiliacionesMeService().setLimit(
                    "limit 25 offset 0");

            List<Afiliaciones_me> lista_datos = getServiceLocator()
                    .getAfiliacionesMeService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Afiliaciones_me afiliaciones_me : lista_datos) {
                rowsResultado.appendChild(crearFilas(afiliaciones_me, this));
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

    private Cell getCellCotizante(final Afiliaciones_me afiliaciones_me) {
        final Cell cellCotizante = new Cell();
        if (!afiliaciones_me.getTipo_afiliado().equals(
                IAfiliaciones.TIPO_AFILIADO_COTIZANTE)) {
            cellCotizante.setStyle("color:#f0f0f0");
            cellCotizante.appendChild(new Image("/images/consentimiento.png"));
            cellCotizante.addEventListener(Events.ON_MOUSE_OVER,
                    new EventListener<Event>() {
                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            // Cargamos datos cotizante
                            Afiliaciones_me afiliacionesMeCotizante = (Afiliaciones_me) cellCotizante
                            .getAttribute("COTIZANTE");
                            if (afiliacionesMeCotizante == null) {
                                afiliacionesMeCotizante = new Afiliaciones_me();
                                afiliacionesMeCotizante
                                .setCodigo_empresa(afiliaciones_me
                                        .getCodigo_empresa());
                                afiliacionesMeCotizante
                                .setCodigo_sucursal(afiliaciones_me
                                        .getCodigo_sucursal());
                                afiliacionesMeCotizante
                                .setNro_identificacion_afiliado(afiliaciones_me
                                        .getNro_identificacion_cotizante());
                                afiliacionesMeCotizante = getServiceLocator()
                                .getAfiliacionesMeService().consultar(
                                        afiliacionesMeCotizante);
                                cellCotizante.setAttribute("COTIZANTE",
                                        afiliacionesMeCotizante);
                            }
                            if (afiliacionesMeCotizante != null) {
                                mostrarAfiliadoPopUp(afiliacionesMeCotizante,
                                        cellCotizante);
                            } else {
                                MensajesUtil
                                .mensajeAlerta("Advertencia",
                                        "El cotizante no existe verifique la informacion");
                            }
                        }
                    });
        }
        return cellCotizante;
    }

    public Row crearFilas(Object objeto, Component componente) throws Exception {
        Row fila = new Row();

        final Afiliaciones_me afiliaciones_me = (Afiliaciones_me) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        String nombre_afiliado = "* NO ENCONTRADO";
        String numero_identificacion = "* NO ENCONTRADO";
        Paciente paciente = null;
        if (afiliaciones_me != null) {
            paciente = afiliaciones_me.getPaciente();
            if (paciente != null) {
                nombre_afiliado = paciente.getApellido1() + " "
                        + paciente.getApellido2() + " " + paciente.getNombre1()
                        + " " + paciente.getNombre2();
                numero_identificacion = paciente.getDocumento();
            }
        }

        fila.setStyle("text-align: justify;nowrap:nowrap");
        /* Seccion cotizante */

        fila.appendChild(getCellCotizante(afiliaciones_me));

        fila.appendChild(new Label(numero_identificacion + ""));
        fila.appendChild(new Label(nombre_afiliado + ""));
        fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                .format(afiliaciones_me.getFecha_afiliacion()) + ""));
        fila.appendChild(new Label(
                afiliaciones_me.getAportantes_ma() != null ? afiliaciones_me
                .getAportantes_ma().getInformacion()
                : "* Aportante no encontrado * "));
        fila.appendChild(new Label(
                afiliaciones_me.getElemento_estado() != null ? afiliaciones_me
                .getElemento_estado().getDescripcion()
                : "* Estado no encontrado *"));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                if (afiliaciones_me.getTipo_afiliado().equals(
                        IAfiliaciones.TIPO_AFILIADO_COTIZANTE)) {
                    mostrarDatos(afiliaciones_me);
                } else {
                    mostrarBeneficiarios(afiliaciones_me, true);
                }
            }
        });
        hbox.appendChild(img);

        if (afiliaciones_me.getTipo_afiliado().equals("C")) {
            img = new Image();
            img.setSrc("/images/add_perfil.png");
            img.setTooltiptext("Ver Beneficiarios");
            img.setStyle("cursor: pointer");
            img.addEventListener("onClick", new EventListener<Event>() {
                @Override
                public void onEvent(Event arg0) throws Exception {
                    mostrarBeneficiariosEnList(afiliaciones_me);
                }
            });
            hbox.appendChild(space);
            hbox.appendChild(img);
        }

        final boolean activo = paciente.getEstado_afiliacion().equals(
                IConstantes.ESTADO_AFILIACION_ACTIVO);
        img = new Image();
        img.setSrc("/images/" + (activo ? "lock.gif" : "open.png"));
        img.setTooltiptext((!activo ? "Activar" : "Suspender ") + " afiliado");
        img.setStyle("cursor: pointer");
        final Paciente pacienteTemp = paciente;
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(final Event arg0) throws Exception {
                Messagebox.show("Esta seguro que deseas "
                        + (activo ? "in" : "") + "activar este paciente?",
                        "Cerrar contrato", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener<Event>() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    try {
                                        pacienteTemp
                                        .setEstado_afiliacion(activo ? IConstantes.ESTADO_AFILIACION_SUSPENDIDO
                                                : IConstantes.ESTADO_AFILIACION_ACTIVO);
                                        pacienteTemp.setUltimo_user(usuarios
                                                .getCodigo());
                                        pacienteTemp
                                        .setUltimo_update(new Timestamp(
                                                        System.currentTimeMillis()));
                                        getServiceLocator().getServicio(
                                                PacienteService.class)
                                        .actualizar(pacienteTemp, true);
                                        buscarDatos();
                                    } catch (Exception e) {
                                        MensajesUtil.mensajeError(e, null,
                                                Afiliaciones_meAction.this);
                                    }

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

    protected void mostrarAfiliadoPopUp(Afiliaciones_me afiliaciones_me,
            Cell cellFila) {
        if (rowsCotizante == null) {
            rowsCotizante = new Rows();

            Popup popupCotizante = new Popup();
            popupCotizante.setWidth("500px");

            Groupbox groupbox = new Groupbox();
            groupbox.setMold("3d");
            groupbox.setClosable(false);
            Caption caption = new Caption("COTIZANTE");
            groupbox.appendChild(caption);
            popupCotizante.appendChild(groupbox);

            Toolbarbutton toolbarbutton = new Toolbarbutton("Cerrar",
                    "/images/cancelar_mini.png");
            toolbarbutton.addEventListener(Events.ON_CLICK,
                    new EventListener<Event>() {
                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            Popup popUpInfo = (Popup) rowsCotizante
                            .getAttribute("popup");
                            popUpInfo.close();
                        }
                    });
            caption.appendChild(toolbarbutton);

            Hbox hbox = new Hbox();
            groupbox.appendChild(hbox);
            Hbox hboxImg = new Hbox();
            hbox.appendChild(hboxImg);
            rowsCotizante.setAttribute("img", hboxImg);

            Vbox vbox = new Vbox();
            vbox.setAlign("center");
            vbox.setHflex("1");
            hbox.appendChild(vbox);
            Grid grid = new Grid();
            Columns columns = new Columns();
            Column column = new Column();
            column.setWidth("120px");
            columns.appendChild(column);
            columns.appendChild(new Column());
            grid.appendChild(columns);
            vbox.appendChild(grid);

            rowsCotizante.setAttribute("popup", popupCotizante);
            grid.appendChild(rowsCotizante);
            appendChild(popupCotizante);
        } else {
            rowsCotizante.getChildren().clear();
        }

        Hbox hboxImg = (Hbox) rowsCotizante.getAttribute("img");
        hboxImg.getChildren().clear();
        if (afiliaciones_me.getFoto_afiliados() != null) {
            try {
                hboxImg.setAlign("center");
                Image image = new Image();
                image.setHeight("150px");
                AImage aImage = new AImage("foto_usr",
                        afiliaciones_me.getFoto_afiliados());
                image.setContent(aImage);
                hboxImg.appendChild(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }

        Paciente paciente = afiliaciones_me.getPaciente();
        Row row = new Row();
        row.appendChild(new Label("Tipo Identificacion: "));
        Cell cell = new Cell();
        cell.appendChild(new Label("" + paciente.getTipo_identificacion()));
        row.appendChild(cell);
        rowsCotizante.appendChild(row);

        row = new Row();
        row.appendChild(new Label("Nro Identificacion: "));
        cell = new Cell();
        cell.appendChild(new Label("" + paciente.getNro_identificacion()));
        row.appendChild(cell);
        rowsCotizante.appendChild(row);

        row = new Row();
        row.appendChild(new Label("Nombres: "));
        cell = new Cell();
        cell.appendChild(new Label("" + paciente.getNombre1() + " "
                + paciente.getNombre2()));
        row.appendChild(cell);
        rowsCotizante.appendChild(row);

        row = new Row();
        row.appendChild(new Label("Apellidos: "));
        cell = new Cell();
        cell.appendChild(new Label("" + paciente.getApellido1() + " "
                + paciente.getApellido2()));
        row.appendChild(cell);
        rowsCotizante.appendChild(row);

		// row = new Row();
        // cell = new Cell();
        // cell.setColspan(2);
        //
        // // cell.setAlign("right");
        // // cell.appendChild(toolbarbutton);
        // row.appendChild(cell);
        // rowsCotizante.appendChild(row);
        Popup popUpInfo = (Popup) rowsCotizante.getAttribute("popup");
        if (popUpInfo.isVisible()) {
            popUpInfo.close();
        } else {
            popUpInfo.open(cellFila, "after_end");
        }
    }

    private Afiliaciones_me getBeanAfiliaciones_me() {
        Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
        afiliaciones_me.setCodigo_empresa(empresa.getCodigo_empresa());
        afiliaciones_me.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        afiliaciones_me.setNro_identificacion_cotizante("");
        afiliaciones_me
                .setNro_identificacion_afiliado(afiliaciones_meGlobal != null ? afiliaciones_meGlobal
                        .getNro_identificacion_afiliado()
                        : tbxNro_identificacion.getValue());
        afiliaciones_me.setTipo_afiliado("C");
        afiliaciones_me.setParenteco_cotizante("");
        afiliaciones_me.setDepartamento_afiliacion(lbxCodigo_dpto
                .getSelectedItem().getValue().toString());
        afiliaciones_me.setMunicipio_afiliacion(lbxCodigo_municipio
                .getSelectedItem().getValue().toString());
        afiliaciones_me.setZona_afiliacion(lbxZona.getSelectedItem().getValue()
                .toString());
        afiliaciones_me.setFecha_afiliacion(new Timestamp(dtbxFecha_afiliacion
                .getValue().getTime()));

        afiliaciones_me
                .setNro_identificacion_aportante(getCodigoAportante(tbxCodigo_aportante));
        afiliaciones_me
                .setNro_identificacion_aportante2(getCodigoAportante(tbxCodigo_aportante2));
        // afiliaciones_me.setEscolaridad(lbxEscolaridad.getSelectedItem().getValue().toString());
        afiliaciones_me
                .setDiscapacidad(chbDiscapacidad.isChecked() ? "S" : "N");
        afiliaciones_me.setCedula_tutor(tbxCedula_tutor.getValue());
        afiliaciones_me.setCodigo_empleado1(tbxCodigo_empleado1.getValue());
        afiliaciones_me.setCodigo_empleado2(tbxCodigo_empleado2.getValue());
        afiliaciones_me.setIdentificacion_ibc(tbxIdentificacion_ibc.getValue());
        afiliaciones_me.setDescripcion_discapacidad(tbxDescripcion_discapacidad
                .getValue());
        afiliaciones_me.setTipo_empleado(lbxTipo_empleado.getSelectedItem()
                .getValue().toString());
        afiliaciones_me.setNombre_universidad("");
        if (afiliaciones_meGlobal == null) {
            afiliaciones_me.setCreacion_date(new Timestamp(
                    new GregorianCalendar().getTimeInMillis()));
            afiliaciones_me.setCreacion_user(usuarios.getCodigo().toString());
        } else {
            afiliaciones_me.setCreacion_date(afiliaciones_meGlobal
                    .getCreacion_date());
            afiliaciones_me.setCreacion_user(afiliaciones_meGlobal
                    .getCreacion_user());
        }

        afiliaciones_me.setUltimo_update(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));

        afiliaciones_me.setUltimo_user(usuarios.getCodigo().toString());
        afiliaciones_me.setConvension(chbConvension.isChecked() ? "ND" : "RC");
        afiliaciones_me.setRh(lbxRh.getSelectedItem().getValue().toString());

        afiliaciones_me
                .setId(afiliaciones_meGlobal != null ? afiliaciones_meGlobal
                        .getId() : null);
        afiliaciones_me.setFoto_afiliados(bytefoto);
        afiliaciones_me.setObservaciones(tbxObservaciones.getValue());
        afiliaciones_me
                .setFecha_vinculacion(dtbxFecha_vinculacion.getValue() != null ? new Timestamp(
                                dtbxFecha_vinculacion.getValue().getTime()) : null);
        afiliaciones_me
                .setFecha_vencimiento(dtbxFecha_vencimiento.getValue() != null ? new Timestamp(
                                dtbxFecha_vencimiento.getValue().getTime()) : null);
        afiliaciones_me.setCuota_moderadora(dbxCuota_moderadora.getValue());
        afiliaciones_me.setPorcentaje_copago(dbxPorcentaje_copago.getValue());

        return afiliaciones_me;
    }

    private String getCodigoAportante(
            BandboxRegistrosMacro bandboxRegistrosMacro) {
        String codigo = "";
        Aportantes_ma aportantes_ma = bandboxRegistrosMacro
                .getRegistroSeleccionado();
        if (aportantes_ma != null) {
            codigo = aportantes_ma.getCodigo();
        }
        // log.info("código aportante: " + codigo);
        return codigo;
    }

    private Paciente getBeanPaciente() {
        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(empresa.getCodigo_empresa());
        paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        paciente.setTipo_identificacion(lbxTipo_identificacion
                .getSelectedItem().getValue().toString());
        paciente.setNro_identificacion(afiliaciones_meGlobal != null ? afiliaciones_meGlobal
                .getNro_identificacion_afiliado() : tbxNro_identificacion
                .getValue());
        paciente.setCodigo_administradora(tbxUniversidad.getValue());
        paciente.setTipo_usuario(lbxTipo_usuario.getSelectedItem().getValue()
                .toString());
        paciente.setApellido1(tbxApellido1.getValue());
        paciente.setApellido2(tbxApellido2.getValue());
        paciente.setNombre1(tbxNombre1.getValue());
        paciente.setNombre2(tbxNombre2.getValue());
        paciente.setFecha_nacimiento(new Timestamp(dtbxFecha_nacimiento
                .getValue().getTime()));
        paciente.setEdad("");
        paciente.setUnidad_medidad(lbxUnidad_medidad.getSelectedItem()
                .getValue().toString());
        paciente.setSexo(lbxSexo.getSelectedItem().getValue().toString());
        paciente.setCodigo_dpto(lbxCodigo_dpto.getSelectedItem().getValue()
                .toString());
        paciente.setCodigo_municipio(lbxCodigo_municipio.getSelectedItem()
                .getValue().toString());
        paciente.setZona(lbxZona.getSelectedItem().getValue().toString());
        paciente.setLugar_exp("");
        paciente.setProfesion(tbxProfesion.getValue());
        paciente.setTel_oficina("");
        paciente.setTel_res(tbxTel_oficina.getValue());
        paciente.setDireccion(tbxDireccion.getValue());
        paciente.setEstado_civil(lbxEstado_civil.getSelectedItem().getValue()
                .toString());
        paciente.setPaciente_particula("N");
        paciente.setEstrato(lbxEstrato.getSelectedItem().getValue().toString());
        paciente.setTipo_afiliado("1");
        paciente.setCreacion_date(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        paciente.setUltimo_update(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        paciente.setCreacion_user(usuarios.getCodigo().toString());
        paciente.setUltimo_user(usuarios.getCodigo().toString());
        paciente.setEstado_afiliacion("01");
        paciente.setIngresos(0);
        paciente.setGrupo_cuota("");
        paciente.setLogin(tbxLogin.getValue());
        paciente.setPassword(tbxPassword.getValue());
        paciente.setPertenencia_etnica(lbxPertenencia_etnica.getSelectedItem()
                .getValue().toString());
        paciente.setNivel_sisben(lbxNivel_sisben.getSelectedItem().getValue()
                .toString());

        paciente.setCodigo_ocupacion(tbxCodigo_ocupacion.getValue());
        paciente.setCodigo_educativo(lbxCodigo_educativo.getSelectedItem()
                .getValue().toString());
        paciente.setEstado_afiliacion(lbxEstadoAfiliacion.getSelectedItem()
                .getValue().toString());
        paciente.setDocumento(tbxNro_identificacion.getValue());
        return paciente;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            setUpperCase();
            if (validarForm()) {
                // Cargamos los componentes //
                verificamosIgualdad();

                Paciente paciente = getBeanPaciente();
                Afiliaciones_me afiliaciones_me = getBeanAfiliaciones_me();
                List<Contratos> listado_contratos = new ArrayList<Contratos>();

                Adicional_paciente adicional_paciente = new Adicional_paciente();
                adicional_paciente.setCodigo_empresa(paciente
                        .getCodigo_empresa());
                adicional_paciente.setCodigo_sucursal(paciente
                        .getCodigo_sucursal());
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

                for (Listitem listitem : listboxContratos.getSelectedItems()) {
                    Contratos contratos = (Contratos) listitem.getValue();
                    listado_contratos.add(contratos);
                }

                Map<String, Object> mapa_datos = new HashMap<String, Object>();
                mapa_datos.put(Afiliaciones_meService.LISTADO_CONTRATO,
                        listado_contratos);
                mapa_datos.put(Afiliaciones_meService.AFILIACIONES,
                        afiliaciones_me);
                mapa_datos.put(Afiliaciones_meService.PACIENTES, paciente);
                mapa_datos.put(Afiliaciones_meService.ADICIONAL_PACIENTES,
                        adicional_paciente);
                mapa_datos.put(Afiliaciones_meService.HISTORIAL_OBSERVACIONES,
                        historialObservacionMacro.getHistorial_observaciones());
                mapa_datos.put(Afiliaciones_meService.BENEFICIARIOS,
                        list_afiliacionesMesBeneficiarios);

                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    getServiceLocator().getAfiliacionesMeService().crear(
                            mapa_datos);
                    accionForm(true, "registrar");
                } else {
                    int result = getServiceLocator().getAfiliacionesMeService()
                            .actualizar(mapa_datos);
                    if (result == 0) {
                        throw new Exception(
                                "Lo sentimos pero los datos a modificar no se encuentran en base de datos");
                    }
                }

                Messagebox.show("Los datos se guardaron satisfactoriamente",
                        "Informacion ..", Messagebox.OK,
                        Messagebox.INFORMATION, new EventListener<Event>() {

                            @Override
                            public void onEvent(Event arg0) throws Exception {
                                if (solicitud_e1 != null) {
                                    onClose();
                                }
                            }
                        });
            }
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, null, this);
        }

    }

	// private String getPlan() throws Exception {
    //
    // Map<String, Object> map = new HashMap<String, Object>();
    // map.put("codigo_empresa", usuarios.getCodigo_empresa());
    // map.put("codigo_sucursal", usuarios.getCodigo_sucursal());
    // map.put("codigo_administradora", usuarios.getCodigo_empresa());
    // List<Contratos> contratos = getServiceLocator().getContratosService()
    // .listar(map);
    // if (contratos.isEmpty()) {
    // throw new Exception(
    // "La para esta accion debe tener un plan registrado");
    // } else {
    // return contratos.get(0).getId_plan();
    // }
    // }
    public void buscarUniversidad(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", empresa.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");
            parameters.put("tipo", TerceroAction.TYPE);

            getServiceLocator().getTerceroService().setLimit(
                    "limit 25 offset 0");

            List<Tercero> list = getServiceLocator().getTerceroService()
                    .listar(parameters);

            lbx.getItems().clear();

            for (Tercero dato : list) {

                Listitem listitem = new Listitem();
                listitem.setValue(dato);

                String dv = dato.getDv() + "";

                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(dato.getNro_identificacion()
                        + "" + (dv.isEmpty() ? "" : " - " + dv)));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getNombre1()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getDireccion()));
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

    public void selectedUniversidad(Listitem listitem) {
        if (listitem.getValue() == null) {
            tbxUniversidad.setValue("");
            tbxNombre_universidad.setValue("");
        } else {
            Tercero dato = (Tercero) listitem.getValue();
            tbxUniversidad.setValue(dato.getNro_identificacion());
            tbxNombre_universidad.setValue(dato.getNombre1());
        }
        tbxUniversidad.close();
    }

    public void buscarAfiliadoIBC(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", empresa.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");
            parameters.put("tipo_afiliado", "C");
            parameters.put("diferent_me", tbxNro_identificacion.getValue());

            getServiceLocator().getAfiliacionesMeService().setLimit(
                    "limit 25 offset 0");

            List<Afiliaciones_me> list = getServiceLocator()
                    .getAfiliacionesMeService().listar(parameters);

            lbx.getItems().clear();

            for (Afiliaciones_me dato : list) {

                Listitem listitem = new Listitem();
                listitem.setValue(dato);

                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(dato.getPaciente()
                        .getTipo_identificacion() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato
                        .getNro_identificacion_afiliado()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getPaciente()
                        .getNombreCompleto()));
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

    public void selectedAfiliadoIBC(Listitem listitem) {
        if (listitem.getValue() == null) {
            tbxIdentificacion_ibc.setValue("");
            tbxNomIBC.setValue("");
        } else {
            Afiliaciones_me dato = (Afiliaciones_me) listitem.getValue();
            tbxIdentificacion_ibc.setValue(dato
                    .getNro_identificacion_afiliado());
            tbxNomIBC.setValue(dato.getPaciente().getNombreCompleto());
        }
        tbxIdentificacion_ibc.close();
    }

    private void mostrarDatos(Solicitud_e1 solicitudE1) throws Exception {

        try {
            for (int i = 0; i < lbxTipo_identificacion.getItemCount(); i++) {
                Listitem listitem = lbxTipo_identificacion.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(solicitudE1.getTipo_identificacion())) {
                    listitem.setSelected(true);
                    break;
                }
            }

            for (int i = 0; i < lbxSexo.getItemCount(); i++) {
                Listitem listitem = lbxSexo.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(solicitudE1.getSexo())) {
                    listitem.setSelected(true);
                    break;
                }
            }

            for (int i = 0; i < lbxZona.getItemCount(); i++) {
                Listitem listitem = lbxZona.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(solicitudE1.getZona())) {
                    listitem.setSelected(true);
                    break;
                }
            }

            for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(solicitudE1.getCodigo_dpto())) {
                    listitem.setSelected(true);
                    break;
                }
            }

            listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

            for (int i = 0; i < lbxCodigo_municipio.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_municipio.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(solicitudE1.getCodigo_municipio())) {
                    listitem.setSelected(true);
                    break;
                }
            }

            for (int i = 0; i < lbxDepartamento_afiliacion.getItemCount(); i++) {
                Listitem listitem = lbxDepartamento_afiliacion
                        .getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(solicitudE1.getCodigo_dpto())) {
                    listitem.setSelected(true);
                    break;
                }
            }

            listarMunicipios(lbxMunicipio_afiliacion,
                    lbxDepartamento_afiliacion);

            for (int i = 0; i < lbxMunicipio_afiliacion.getItemCount(); i++) {
                Listitem listitem = lbxMunicipio_afiliacion.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(solicitudE1.getCodigo_municipio())) {
                    listitem.setSelected(true);
                    break;
                }
            }

            tbxApellido1.setValue(solicitudE1.getApellido1());
            tbxApellido2.setValue(solicitudE1.getApellido2());
            tbxNombre1.setValue(solicitudE1.getNombre1());
            tbxNombre2.setValue(solicitudE1.getNombre2());
            tbxNro_identificacion.setValue(solicitudE1.getNro_identificacion());
            dtbxFecha_nacimiento.setValue(solicitudE1.getFecha_nacimiento());
            dtbxFecha_afiliacion.setValue(solicitudE1.getFecha_afiliacion());
            groupboxConsulta.setVisible(false);
            groupboxEditar.setVisible(true);
        } catch (WrongValueException e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            Messagebox.show("Este dato no se puede editar", "Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        limpiarDatos();
        afiliaciones_meGlobal = (Afiliaciones_me) obj;
        list_afiliacionesMesBeneficiarios = new ArrayList<Afiliaciones_me>();
        try {
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(sucursal.getCodigo_empresa());
            paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            paciente.setNro_identificacion(afiliaciones_meGlobal
                    .getNro_identificacion_afiliado());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);

            afiliaciones_meGlobal.setPaciente(paciente);

            for (int i = 0; i < lbxTipo_identificacion.getItemCount(); i++) {
                Listitem listitem = lbxTipo_identificacion.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(paciente.getTipo_identificacion())) {
                    listitem.setSelected(true);
                    i = lbxTipo_identificacion.getItemCount();
                }
            }
            for (int i = 0; i < lbxTipo_usuario.getItemCount(); i++) {
                Listitem listitem = lbxTipo_usuario.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(paciente.getTipo_usuario())) {
                    listitem.setSelected(true);
                    i = lbxTipo_usuario.getItemCount();
                }
            }

            Map<String, Object> parametros = new HashMap<String, Object>();
            parametros.put("codigo_empresa", codigo_empresa);
            parametros.put("codigo_sucursal", codigo_sucursal);
            parametros.put("nro_identificacion",
                    paciente.getNro_identificacion());
            parametros.put("codigo_administradora",
                    paciente.getCodigo_administradora());

            List<Pacientes_contratos> listado_contratos = getServiceLocator()
                    .getServicio(Pacientes_contratosService.class).listar(
                            parametros);

            Ocupaciones ocupaciones = new Ocupaciones();
            ocupaciones.setId(paciente.getCodigo_educativo());
            ocupaciones = getServiceLocator().getServicio(
                    OcupacionesService.class).consultar(ocupaciones);
            if (ocupaciones != null) {
                tbxCodigo_ocupacion.seleccionarRegistro(ocupaciones,
                        ocupaciones.getId(), ocupaciones.getNombre());
            }
            for (int i = 0; i < lbxCodigo_educativo.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_educativo.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(paciente.getCodigo_educativo())) {
                    listitem.setSelected(true);
                    break;
                }
            }

            for (int i = 0; i < lbxPertenencia_etnica.getItemCount(); i++) {
                Listitem listitem = lbxPertenencia_etnica.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(paciente.getPertenencia_etnica())) {
                    listitem.setSelected(true);
                    break;
                }
            }

            chbConvension.setChecked(afiliaciones_meGlobal.getConvension()
                    .equals("ND"));
            for (int i = 0; i < lbxRh.getItemCount(); i++) {
                Listitem listitem = lbxRh.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(afiliaciones_meGlobal.getRh())) {
                    listitem.setSelected(true);
                    i = lbxRh.getItemCount();
                }
            }
            // Mostramos el socumento en el caso del paciente
            tbxNro_identificacion.setValue(paciente.getDocumento());

            tbxApellido1.setValue(paciente.getApellido1());
            tbxApellido2.setValue(paciente.getApellido2());
            tbxNombre1.setValue(paciente.getNombre1());
            tbxNombre2.setValue(paciente.getNombre2());
            dtbxFecha_nacimiento.setValue(paciente.getFecha_nacimiento());
            tbxTel_oficina.setValue(paciente.getTel_res());
            tbxProfesion.setValue(paciente.getProfesion());
            dtbxFecha_afiliacion.setValue(afiliaciones_meGlobal
                    .getFecha_afiliacion());
            dtbxFecha_vencimiento.setValue(afiliaciones_meGlobal
                    .getFecha_vencimiento());

            Utilidades.setValueFrom(lbxUnidad_medidad,
                    paciente.getUnidad_medidad());
            Utilidades.setValueFrom(lbxSexo, paciente.getSexo());
            Utilidades.setValueFrom(lbxCodigo_dpto, paciente.getCodigo_dpto());
            listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

            Utilidades.setValueFrom(lbxDepartamento_afiliacion,
                    afiliaciones_meGlobal.getDepartamento_afiliacion());
            listarMunicipios(lbxMunicipio_afiliacion,
                    lbxDepartamento_afiliacion);

            Utilidades.setValueFrom(lbxZona, paciente.getZona());
            tbxProfesion.setValue(paciente.getProfesion());
            tbxDireccion.setValue(paciente.getDireccion());

            Utilidades
                    .setValueFrom(lbxEstado_civil, paciente.getEstado_civil());
            Utilidades.setValueFrom(lbxEstrato, paciente.getEstrato());

            tbxPassword.setValue(paciente.getPassword());
            tbxLogin.setValue(paciente.getLogin() != null ? paciente.getLogin()
                    : "");
            tbxDescripcion_discapacidad.setValue(afiliaciones_meGlobal
                    .getObservaciones());

            cargarAportante(
                    afiliaciones_meGlobal.getNro_identificacion_aportante(),
                    tbxCodigo_aportante);
            cargarAportante(
                    afiliaciones_meGlobal.getNro_identificacion_aportante2(),
                    tbxCodigo_aportante2);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("codigo_empresa", afiliaciones_meGlobal.getCodigo_empresa());
            map.put("codigo_sucursal",
                    afiliaciones_meGlobal.getCodigo_sucursal());
            map.put("nro_identificacion_cotizante",
                    afiliaciones_meGlobal.getNro_identificacion_afiliado());
            list_afiliacionesMesBeneficiarios = getServiceLocator()
                    .getServicio(Afiliaciones_meService.class).listar(map);
            buscarBeneficiarios();
            buscarAfiliadorIBC(afiliaciones_meGlobal.getIdentificacion_ibc());
            // buscarTerceroUniversidad(afiliaciones_meGlobal.getNombre_universidad());
            Administradora administradora = new Administradora();
            administradora.setCodigo_empresa(afiliaciones_meGlobal
                    .getCodigo_empresa());
            administradora.setCodigo_sucursal(afiliaciones_meGlobal
                    .getCodigo_sucursal());
            administradora.setCodigo(paciente.getCodigo_administradora());
            administradora = getServiceLocator().getServicio(
                    AdministradoraService.class).consultar(administradora);
            if (administradora != null) {
                tbxUniversidad.seleccionarRegistro(administradora,
                        administradora.getCodigo(), administradora.getNombre());
                listarContratos(listboxContratos, tbxUniversidad.getValue(),
                        true, true);
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
            }

            Adicional_paciente adicional_paciente = new Adicional_paciente();
            adicional_paciente.setCodigo_empresa(paciente.getCodigo_empresa());
            adicional_paciente
                    .setCodigo_sucursal(paciente.getCodigo_sucursal());
            adicional_paciente.setNro_identificacion(paciente
                    .getNro_identificacion());
            adicional_paciente = getServiceLocator().getServicio(
                    GeneralExtraService.class).consultar(
                            adicional_paciente);
            if (adicional_paciente != null) {
                Barrio barrio = new Barrio();
                barrio.setCodigo_barrio(adicional_paciente.getCodigo_barrio());
                barrio = getServiceLocator().getServicio(BarrioService.class)
                        .consultar(barrio);
                if (barrio != null) {
                    tbxCod_bar.seleccionarRegistro(barrio,
                            barrio.getCodigo_barrio(), barrio.getBarrio());
                }
            }

            for (int i = 0; i < lbxTipo_empleado.getItemCount(); i++) {
                Listitem listitem = lbxTipo_empleado.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(afiliaciones_meGlobal.getTipo_empleado())) {
                    listitem.setSelected(true);
                    i = lbxTipo_empleado.getItemCount();
                }
            }

            chbDiscapacidad.setChecked(afiliaciones_meGlobal.getDiscapacidad()
                    .equalsIgnoreCase("s"));
            tbxCedula_tutor.setValue(afiliaciones_meGlobal.getCedula_tutor());
            tbxCodigo_empleado1.setValue(afiliaciones_meGlobal
                    .getCodigo_empleado1());
            tbxCodigo_empleado2.setValue(afiliaciones_meGlobal
                    .getCodigo_empleado2());

            dbxCuota_moderadora.setValue(afiliaciones_meGlobal
                    .getCuota_moderadora());
            dbxPorcentaje_copago.setValue(afiliaciones_meGlobal
                    .getPorcentaje_copago());

            Afiliaciones_me afiliaciones_me = new Afiliaciones_me();
            afiliaciones_me.setCodigo_empresa(afiliaciones_meGlobal
                    .getCodigo_empresa());
            afiliaciones_me.setCodigo_sucursal(afiliaciones_meGlobal
                    .getCodigo_sucursal());
            afiliaciones_me
                    .setNro_identificacion_afiliado(afiliaciones_meGlobal
                            .getIdentificacion_ibc());
            afiliaciones_me = getServiceLocator().getServicio(
                    Afiliaciones_meService.class).consultar(afiliaciones_me);
            if (afiliaciones_me != null) {
                Paciente paciente2 = afiliaciones_me.getPaciente();
                tbxIdentificacion_ibc.seleccionarRegistro(afiliaciones_me,
                        afiliaciones_me.getNro_identificacion_afiliado(),
                        paciente2.getNombreCompleto());
            }

            tbxDescripcion_discapacidad.setValue(afiliaciones_meGlobal
                    .getDescripcion_discapacidad());

            verificarSeleccionDiscapacidad();
            Utilidades.setValueFrom(lbxEstadoAfiliacion,
                    paciente.getEstado_afiliacion());

            /* test de cargar foto */
            bytefoto = afiliaciones_meGlobal.getFoto_afiliados();
            if (bytefoto != null) {
                AImage aImage = new AImage("foto_usr", bytefoto);
                imageUsuario.setContent(aImage);
            }
            imgState.setSrc(tbxLogin.getValue().isEmpty() ? null : IMAGE_OK);
            dtbxFecha_vinculacion.setValue(afiliaciones_meGlobal
                    .getFecha_vinculacion());

            // Cargamos observaciones
            map = new HashMap<String, Object>();
            map.put("codigo_empresa", afiliaciones_meGlobal.getCodigo_empresa());
            map.put("codigo_sucursal",
                    afiliaciones_meGlobal.getCodigo_sucursal());
            map.put("id_afiliado", afiliaciones_meGlobal.getId());
            List<Historial_observaciones> historial_observaciones = getServiceLocator()
                    .getServicio(Historial_observacionesService.class).listar(
                            map);
            historialObservacionMacro
                    .setHistorialObservaciones(historial_observaciones);

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            Messagebox.show("Este dato no se puede editar", "Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    private void buscarAfiliadorIBC(String nro_id) {
        Afiliaciones_me afiliacionesMe = new Afiliaciones_me();
        afiliacionesMe.setCodigo_empresa(sucursal.getCodigo_empresa());
        afiliacionesMe.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        afiliacionesMe.setNro_identificacion_afiliado(nro_id + "");
        afiliacionesMe = getServiceLocator().getAfiliacionesMeService()
                .consultar(afiliacionesMe);
        tbxIdentificacion_ibc.setValue(nro_id);
        tbxNomIBC.setValue(afiliacionesMe != null ? afiliacionesMe
                .getPaciente().getNombreCompleto() : "");
    }

	// private void buscarTerceroUniversidad(String nro_id) {
    // Tercero tercero = new Tercero();
    // tercero.setCodigo_empresa(sucursal.getCodigo_empresa());
    // tercero.setCodigo_sucursal(sucursal.getCodigo_sucursal());
    // tercero.setNro_identificacion(nro_id);
    // tercero = getServiceLocator().getTerceroService().consultar(tercero);
    //
    // if(tercero != null){
    // tbxUniversidad.seleccionarRegistro(tercero,
    // tercero.getNro_identificacion(), tercero.getNombre1());
    // }
    // // tbxUniversidad.setValue(nro_id);
    // // tbxNombre_universidad.setValue(tercero != null ? tercero.getNombre1()
    // // : "");
    // }
    private void cargarAportante(String nro_id,
            BandboxRegistrosMacro tbxCodigo_aportante) {
        Aportantes_ma aportantesMa = new Aportantes_ma();
        aportantesMa.setCodigo_empresa(sucursal.getCodigo_empresa());
        aportantesMa.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        aportantesMa.setCodigo(nro_id);
        aportantesMa = getServiceLocator().getAportantesMaService().consultar(
                aportantesMa);
        if (aportantesMa != null) {
            tbxCodigo_aportante.seleccionarRegistro(aportantesMa,
                    aportantesMa.getNro_identificacon(),
                    aportantesMa.getNombre());
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Afiliaciones_me afiliaciones_me = (Afiliaciones_me) obj;
        try {
            int result = getServiceLocator().getAfiliacionesMeService()
                    .eliminar(afiliaciones_me);
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

    private void setVisibleDiscapacidad(boolean visible) {
        lbDescripcion_discapacidad.setVisible(visible);
        tbxDescripcion_discapacidad.setVisible(visible);
    }

    public ServiceLocatorWeb getServiceLocator() {
        return ServiceLocatorWeb.getInstance();
    }

    @Override
    public void init() throws Exception {
        list_afiliacionesMesBeneficiarios = new ArrayList<Afiliaciones_me>();
        parametrizarBandbox();
        loadEvents();
        listarCombos();
        verificamosSolicitud();
    }

    private void parametrizarBandbox() {
        inicializarBandboxUniversidad();
        inicializarBandboxAfiliadoIBC();
        inicializarBandboxAportante();
        parametrizarBandboxOcupacion();
        parametrizarBandboxBarrio();
    }

    private void inicializarBandboxAportante() {
        tbxCodigo_aportante.inicializar(tbxNomAportante, tbxAportanteBorrar);
        tbxCodigo_aportante2.inicializar(tbxNomAportante2, tbxAportanteBorrar2);

        BandboxRegistrosIMG<Aportantes_ma> bandboxRegistrosIMG = new BandboxRegistrosIMG<Aportantes_ma>() {

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Aportantes_ma registro) {
                bandbox.setValue(registro.getNro_identificacon());
                textboxInformacion.setValue(registro.getNombre());
                return true;
            }

            @Override
            public void renderListitem(Listitem listitem, Aportantes_ma registro) {
                Listcell listcell = new Listcell();
                String addDV = registro.getDv() != null ? (!registro.getDv()
                        .isEmpty() ? "-" + registro.getDv() : "") : "";

                listcell.appendChild(new Label(registro
                        .getTipo_identificacion() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNro_identificacon()
                        + addDV));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(registro.getNombre() + ""));
                listitem.appendChild(listcell);
            }

            @Override
            public List<Aportantes_ma> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros) {
                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("codigo_empresa", empresa.getCodigo_empresa());
                parameters
                        .put("codigo_sucursal", sucursal.getCodigo_sucursal());
                parameters.put("paramTodo", "");
                parameters.put("value", valorBusqueda.toUpperCase().trim());

                parametros.put("limite_paginado", "limit 25 offset 0");

                return getServiceLocator().getAportantesMaService().listar(
                        parameters);
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {

            }
        };
        tbxCodigo_aportante.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        tbxCodigo_aportante2.setBandboxRegistrosIMG(bandboxRegistrosIMG);
    }

    private void inicializarBandboxAfiliadoIBC() {
        tbxIdentificacion_ibc
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Afiliaciones_me>() {
                    @Override
                    public void renderListitem(Listitem listitem,
                            Afiliaciones_me registro) {
                        Listcell listcell = new Listcell();
                        listcell.appendChild(new Label(registro.getPaciente()
                                        .getTipo_identificacion() + ""));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(registro
                                        .getNro_identificacion_afiliado()));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(registro.getPaciente()
                                        .getNombreCompleto()));
                        listitem.appendChild(listcell);
                    }

                    @Override
                    public List<Afiliaciones_me> listarRegistros(
                            String valorBusqueda, Map<String, Object> parametros) {
                                Map<String, Object> parameters = new HashMap<String, Object>();
                                parameters.put("codigo_empresa",
                                        empresa.getCodigo_empresa());
                                parameters.put("codigo_sucursal",
                                        sucursal.getCodigo_sucursal());
                                parameters.put("paramTodo", "");
                                parameters.put("value", "%"
                                        + valorBusqueda.toUpperCase().trim() + "%");
                                parameters.put("tipo_afiliado", "C");
                                parameters.put("diferent_me",
                                        tbxNro_identificacion.getValue());

                                getServiceLocator().getAfiliacionesMeService()
                                .setLimit("limit 25 offset 0");

                                return getServiceLocator().getAfiliacionesMeService()
                                .listar(parameters);
                            }

                            @Override
                            public boolean seleccionarRegistro(Bandbox bandbox,
                                    Textbox textboxInformacion, Afiliaciones_me registro) {
                                bandbox.setValue(""
                                        + registro.getNro_identificacion_afiliado());
                                textboxInformacion.setValue(""
                                        + registro.getPaciente().getNombreCompleto());
                                return true;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {

                            }

                });
    }

    private void parametrizarBandboxBarrio() {
        tbxCod_bar.inicializar(tbxInfoBar, btnLimpiarBar);
        tbxCod_bar.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Barrio>() {

            @Override
            public void renderListitem(Listitem listitem, Barrio registro) {

                Localidad localidad = new Localidad();
                localidad.setCodigo_localidad(registro != null ? registro
                        .getCodigo_localidad() : "");
                localidad = getServiceLocator().getServicio(GeneralExtraService.class)
                        .consultar(localidad);

                Municipios municipios = new Municipios();
                municipios.setCodigo((localidad != null ? localidad.getCodigo()
                        : ""));
                municipios.setCoddep((localidad != null ? localidad.getCoddep()
                        : ""));
                municipios = getServiceLocator().getMunicipiosService()
                        .consultar(municipios);

                Departamentos departamentos = new Departamentos();
                departamentos.setCodigo((localidad != null ? localidad
                        .getCoddep() : ""));
                departamentos = getServiceLocator().getDepartamentosService()
                        .consultar(departamentos);

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
                return getServiceLocator().getBarrioService()
                        .listar(parametros);
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
        tbxCodigo_ocupacion.inicializar(
                (Textbox) getFellow("tbxInfoOcupacion"),
                (Toolbarbutton) getFellow("btnLimpiarOcupacion"));
        tbxCodigo_ocupacion
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Ocupaciones>() {

                    @Override
                    public void renderListitem(Listitem listitem,
                            Ocupaciones registro) {
                        listitem.appendChild(new Listcell(registro.getId()));
                        listitem.appendChild(new Listcell(registro.getNombre()));
						// tbxInfoOcupacion.setHflex("0");
                        // tbxInfoOcupacion.setWidth("270px");
                    }

                    @Override
                    public List<Ocupaciones> listarRegistros(
                            String valorBusqueda, Map<String, Object> parametros) {
                                parametros.put("paramTodo", valorBusqueda.toLowerCase());
                                return getServiceLocator().getOcupacionesService()
                                .listar(parametros);
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
						// tbxInfoOcupacion.setHflex("1");
                                // tbxInfoOcupacion.setWidth(null);
                            }
                });

    }

    private void inicializarBandboxUniversidad() {
        tbxUniversidad
                .inicializar(tbxNombre_universidad, btnLimpiarAseguradora);
        tbxUniversidad
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

                    @Override
                    public void renderListitem(Listitem listitem,
                            Administradora registro) {
                        listitem.appendChild(new Listcell(registro.getNit()));
                        listitem.appendChild(new Listcell(registro.getCodigo()));
                        listitem.appendChild(new Listcell(registro.getNombre()));
                        listitem.appendChild(new Listcell(registro
                                        .getDireccion()));
                    }

                    @Override
                    public List<Administradora> listarRegistros(
                            String valorBusqueda, Map<String, Object> parametros) {
                                parametros.put("codigo_empresa",
                                        empresa.getCodigo_empresa());
                                parametros.put("codigo_sucursal",
                                        sucursal.getCodigo_sucursal());
                                parametros.put("paramTodo", "paramTodo");
                                parametros.put("value", valorBusqueda);
                                return getServiceLocator().getAdministradoraService()
                                .listar(parametros);
                            }

                            @Override
                            public boolean seleccionarRegistro(Bandbox bandbox,
                                    Textbox textboxInformacion, Administradora registro) {
                                bandbox.setValue(registro.getCodigo());
                                bandbox.setAttribute("admin", registro);
                                textboxInformacion.setValue(registro.getNit() + " "
                                        + registro.getNombre());
                                listarContratos(listboxContratos,
                                        tbxUniversidad.getValue(), true, true);
                                return true;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {
                                listarContratos(listboxContratos,
                                        tbxUniversidad.getValue(), true, true);
                            }
                });
    }
}
