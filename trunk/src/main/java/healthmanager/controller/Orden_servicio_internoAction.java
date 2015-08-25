/**
 *
 */
package healthmanager.controller;

import healthmanager.controller.ZKWindow.OpcionesFormulario;
import healthmanager.controller.ZKWindow.View;
import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Detalle_orden;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Orden_servicio;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Procedimientos;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.ISeleccionarComponente;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.res.CargardorDeDatos;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;
import com.softcomputo.composer.constantes.IParametrosSesion;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * @author ferney
 *
 */
public class Orden_servicio_internoAction extends Listbox implements
        AfterCompose, ISeleccionarComponente {

    private static Logger log = Logger
            .getLogger(Orden_servicio_internoAction.class);
    private static final long serialVersionUID = -9145887024839938515L;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd/MM/yyyy");

	// private Permisos_sc permisos_sc;
    private Empresa empresa;
    private Sucursal sucursal;
    private Usuarios usuarios;

    // Componentes //
    private Listbox lbxFormatoOrden;

    private Textbox tbxAccion;
    private Textbox tbxCodigo_orden;
    private Textbox tbxCodigo_paciente;
    private Textbox tbxSexo_pct;
    private Textbox tbxFecha_nac;
    private Textbox tbxCodigo_administradora;
    private Textbox tbxId_plan;
    private Textbox tbxNro_ingreso;
    private Textbox tbxTipo_hc;

    private Datebox dtbxFecha_orden;
    private Bandbox tbxCodigo_ordenador;
    private Textbox tbxNomOrdenador;
    private Bandbox tbxCodigo_dx;
    private Textbox tbxNomOrdenDx;
    private Textbox tbxSexo_dx;
    private Textbox tbxLimite_inferior_dx;
    private Textbox tbxLimite_superior_dx;
    private Bandbox tbxId_prestador;
    private Textbox tbxNomPrestadorOrden;

    private Listbox listboxOrdenes_servicio;
    @View
    Grid gridtabla;
    @View
    Div divcontenedorPametrizado;
    @View
    Toolbar toolbarGuardar;

    private List<Map> lista_datos;

    private String rol_usuario;
    private Admision admision;
    private String tipo_hc;

    private boolean trabajar_sin_admision_atendida;

    private ZKWindow zkWindow;

    private Map<String, Object> parametros;

    private String opcion_formulario;

    private Orden_servicio orden_servicio;

    private Doublebox tbxUnidades;

    @View
    private Toolbarbutton btAdicionar2Orden;
    @View
    private Toolbarbutton btImprimir2Orden;

    @View
    private Hlayout hlayoutConsentimiento;

    @View
    private Listfooter listfooterObservaciones;

    @View
    private Radiogroup rgpConsentimiento_informado;

    @View
    private Toolbarbutton toolbarbuttonConsentimiento;

    @View
    private Datebox dtbxFecha_consentimiento;

    @View
    private Textbox tbxNombres_procedimientos;

    @View
    private Listheader colEstado_realizado;
    @View
    private Listheader lhrValor;
    @View
    private Listheader lhrCantidad;
    @View
    private Textbox tbxObservaciones_ordenes;

    private List<String> seleccionados = new ArrayList<String>();

    private String ocultar_consentimiento;

    private Double valor_inicial = 0d;

    @Override
    public void afterCompose() {

        try {
            parametros = (Map<String, Object>) Executions.getCurrent().getArg();
            CargardorDeDatos.initComponents(this);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void inicializar(ZKWindow zkWindow) throws Exception {
        setZkWindow(zkWindow);
        loadComponents();
        cargarDatosSesion();
        initOrden_servicio();
        validarParaImpresion();
    }

    public void loadComponents() {
        listboxOrdenes_servicio = (Listbox) this
                .getFellow("listboxOrdenes_servicio");

        lbxFormatoOrden = (Listbox) this.getFellow("lbxFormatoOrden");
        tbxCodigo_orden = (Textbox) this.getFellow("tbxCodigo_orden");
        tbxAccion = (Textbox) this.getFellow("tbxAccionOrden");
        tbxCodigo_paciente = (Textbox) this.getFellow("tbxCodigo_paciente");
        tbxSexo_pct = (Textbox) this.getFellow("tbxSexo_pctOrden");
        tbxFecha_nac = (Textbox) this.getFellow("tbxFecha_nacOrden");
        tbxCodigo_administradora = (Textbox) this
                .getFellow("tbxCodigo_administradoraOrden");
        tbxId_plan = (Textbox) this.getFellow("tbxId_planOrden");
        tbxNro_ingreso = (Textbox) this.getFellow("tbxNro_ingresoOrden");
        tbxTipo_hc = (Textbox) this.getFellow("tbxTipo_hcOrden");

        dtbxFecha_orden = (Datebox) this.getFellow("dtbxFecha_orden");
        tbxCodigo_ordenador = (Bandbox) this.getFellow("tbxCodigo_ordenador");
        tbxNomOrdenador = (Textbox) this.getFellow("tbxNomOrdenador");
        tbxCodigo_dx = (Bandbox) this.getFellow("tbxCodigo_dx");
        tbxNomOrdenDx = (Textbox) this.getFellow("tbxNomOrdenDx");
        tbxSexo_dx = (Textbox) this.getFellow("tbxSexo_dx");
        tbxLimite_inferior_dx = (Textbox) this
                .getFellow("tbxLimite_inferior_dx");
        tbxLimite_superior_dx = (Textbox) this
                .getFellow("tbxLimite_superior_dx");
        tbxId_prestador = (Bandbox) this.getFellow("tbxId_prestador");
        tbxNomPrestadorOrden = (Textbox) this.getFellow("tbxNomPrestadorOrden");
        tbxObservaciones_ordenes = (Textbox) this
                .getFellow("tbxObservaciones_ordenes");
        listfooterObservaciones = (Listfooter) this
                .getFellow("listfooterObservaciones");

    }

    public void initOrden_servicio() throws Exception {
        HttpServletRequest request = (HttpServletRequest) Executions
                .getCurrent().getNativeRequest();
        try {
            opcion_formulario = (String) parametros.get("opcion_formulario");
            String id_m = request.getParameter("id_menu");
            if (id_m != null) {
                // crearPermisos(new Integer(id_m));
            }

            admision = (Admision) parametros.get("admision");
            tipo_hc = (String) parametros.get("tipo_hc");
            ocultar_consentimiento = (String) parametros
                    .get("ocultar_consentimiento");

            if (parametros.get("ocultarRecomendacion") != null) {
                listfooterObservaciones.setVisible(false);
            } else {
                listfooterObservaciones.setVisible(true);
            }

			// DEBE SER EL NRO DE INGRESO DE LA ADMISION QUE ACTUALMENTE SE
            // QUIERE REGISTRAR
            String nro_ingreso = (String) parametros.get("nro_ingreso");

            String codigo_orden = (String) parametros.get("codigo_orden");
            String nro_identificacion = admision.getNro_identificacion();

            String estado = admision.getEstado();
            String codigo_administradora = admision.getCodigo_administradora();
            String id_plan = admision.getId_plan();

            if (parametros.get("ocultarInformacion") != null) {
                gridtabla.setVisible(false);
                toolbarGuardar.setVisible(false);
                divcontenedorPametrizado.setVisible(true);
                trabajar_sin_admision_atendida = true;
            }

            Paciente paciente = admision.getPaciente();

            String sexo_pct = paciente.getSexo();
            String fecha_nac = new SimpleDateFormat("dd-MM-yyyy")
                    .format(paciente.getFecha_nacimiento());

            tbxCodigo_paciente.setValue(nro_identificacion);
            tbxSexo_pct.setValue(sexo_pct);
            tbxFecha_nac.setValue(fecha_nac);
            tbxNro_ingreso.setValue(nro_ingreso);
            tbxCodigo_administradora.setValue(codigo_administradora);
            tbxId_plan.setValue(id_plan);
            tbxTipo_hc.setValue(tipo_hc);

            if (nro_identificacion.equals("") && nro_ingreso.equals("")) {
                accionForm(true, "registrar");
                deshabilitarCampos(true);
            } else {
                Orden_servicio orden_servicio = new Orden_servicio();
                Map<String, Object> mapa_parametros = new HashMap<String, Object>();
                mapa_parametros.put("codigo_orden", codigo_orden);
                mapa_parametros.put("codigo_empresa",
                        admision.getCodigo_empresa());
                mapa_parametros.put("codigo_sucursal",
                        admision.getCodigo_sucursal());
                mapa_parametros.put("codigo_paciente",
                        admision.getNro_identificacion());
                mapa_parametros.put("nro_ingreso", nro_ingreso);
                mapa_parametros.put("tipo_hc", tipo_hc);

                List<Orden_servicio> listado_ordenes = zkWindow
                        .getServiceLocator().getOrden_servicioService()
                        .listar(mapa_parametros);

                if (!listado_ordenes.isEmpty()) {
					// log.info("Ordenes encontradas ===> "
                    // + listado_ordenes.size());
                    orden_servicio = listado_ordenes.get(0);
                    mostrarDatos(orden_servicio);
                } else {
                    rgpConsentimiento_informado.setSelectedIndex(1);
                    accionForm(true, "registrar");
                }

                if (estado.equals("2")) {
                    deshabilitarCampos(true);
                } else {
                    deshabilitarCampos(false);
                }
            }

            if (parametros.get("valorInicial") != null) {
                valor_inicial = (Double) parametros.get("valorInicial");
            } else {
                valor_inicial = 0d;
            }

            if (parametros.get("mostrarEstado") != null) {
                colEstado_realizado.setVisible(true);
            }

            if (parametros.get("ocultarEstado") != null) {
                colEstado_realizado.setVisible(false);
            }

            if (parametros.get("ocultarCantidad") != null) {
                lhrCantidad.setVisible(false);
            }

            if (parametros.get("mostrarCantidad") != null) {
                lhrCantidad.setVisible(true);
            }

            if (parametros.get("ocultarValor") != null) {
                lhrValor.setVisible(false);
            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", zkWindow);
        }

    }

    /*
     * public void crearPermisos(Integer id_menu)throws Exception{
     * if(usuarios.getNivel().equals("01")){ permisos_sc = new Permisos_sc();
     * permisos_sc.setId_menu(id_menu);
     * permisos_sc.setId_usuario(usuarios.getId());
     * permisos_sc.setPermiso_crear(true);
     * permisos_sc.setPermiso_modificar(true);
     * permisos_sc.setPermiso_consultar(true);
     * permisos_sc.setPermiso_eliminar(true); }else{ permisos_sc = new
     * Permisos_sc(); permisos_sc.setId_menu(id_menu);
     * permisos_sc.setId_usuario(usuarios.getId()); permisos_sc =
     * getServiceLocator().getPermisos_scService().consultar(permisos_sc); }
     * if(!permisos_sc.getPermiso_consultar()){ accionForm(true,"registrar");
     * ((Button)form.getFellow("btCancelar")).setDisabled(true);
     * ((Button)form.getFellow
     * ("btCancelar")).setImage("/images/cancel_disabled.jpg"); }
     * if(!permisos_sc.getPermiso_crear()){
     * ((Button)form.getFellow("btNuevo")).setDisabled(true);
     * ((Button)form.getFellow
     * ("btNuevo")).setImage("/images/New16_disabled.gif");
     * ((Button)form.getFellow("btNew")).setDisabled(true);
     * ((Button)form.getFellow("btNew")).setImage("/images/New16_disabled.gif");
     * } }
     */
    public void cargarDatosSesion() {
        lista_datos = new LinkedList<Map>();
        HttpServletRequest request = (HttpServletRequest) Executions
                .getCurrent().getNativeRequest();

        empresa = ServiceLocatorWeb.getEmpresa(request);
        sucursal = ServiceLocatorWeb.getSucursal(request);
        usuarios = ServiceLocatorWeb.getUsuarios(request);

        rol_usuario = (String) request.getSession().getAttribute(IParametrosSesion.PARAM_ROL_USUARIO);
    }

    public void cargarPrestador() throws Exception {
        if (rol_usuario.equals("05")) {
            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(empresa.getCodigo_empresa());
            prestadores.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            prestadores.setNro_identificacion(usuarios.getCodigo());
            prestadores = zkWindow.getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);
            if (prestadores == null) {
                throw new Exception(
                        "Usuario no esta creado en el modulo de prestadore, actualize informacion de usuario");
            }
            tbxCodigo_ordenador.setValue(prestadores.getNro_identificacion());
            tbxNomOrdenador.setValue(prestadores.getNombres() + " "
                    + prestadores.getApellidos());

        } else {
            if (admision != null) {
                Prestadores prestadores = new Prestadores();
                prestadores.setCodigo_empresa(admision.getCodigo_empresa());
                prestadores.setCodigo_sucursal(admision.getCodigo_sucursal());
                prestadores.setNro_identificacion(admision.getCodigo_medico());
                prestadores = zkWindow.getServiceLocator()
                        .getPrestadoresService().consultar(prestadores);

                tbxCodigo_ordenador.setValue((prestadores != null ? prestadores
                        .getNro_identificacion() : "000000000"));
                tbxNomOrdenador.setValue((prestadores != null ? prestadores
                        .getNombres() + " " + prestadores.getApellidos()
                        : "MEDICO POR DEFECTO"));

            } else {
                tbxCodigo_ordenador.setValue("000000000");
                tbxNomOrdenador.setValue("MEDICO POR DEFECTO");
            }

        }
    }

    public void cargarDx() throws Exception {
        Cie cie = new Cie();
        cie.setCodigo((admision != null ? admision.getDiagnostico_ingreso()
                : "Z000"));
        cie = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

        tbxCodigo_dx.setValue((cie != null ? cie.getCodigo() : "Z000"));
        tbxNomOrdenDx.setValue((cie != null ? cie.getNombre()
                : "EXAMEN MEDICO GENERAL"));

    }

    // Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        if (accion.equalsIgnoreCase("registrar")) {
            limpiarDatos();
            dtbxFecha_consentimiento.setValue(new Date());
            rgpConsentimiento_informado.setSelectedIndex(0);
        }

        tbxAccion.setValue(accion);
    }

    // Convertimos todos las valores de textbox a mayusculas
    public void setUpperCase() {
        Collection<Component> collection = this.getFellows();
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
        Collection<Component> collection = this.getFellows();
        for (Component abstractComponent : collection) {
            if (abstractComponent instanceof Textbox) {
                if (!((Textbox) abstractComponent).getId().equals("tbxValue")
                        && !((Textbox) abstractComponent).getId().equals(
                                "tbxCodigo_paciente")
                        && !((Textbox) abstractComponent).getId().equals(
                                "tbxSexo_pctOrden")
                        && !((Textbox) abstractComponent).getId().equals(
                                "tbxFecha_nacOrden")
                        && !((Textbox) abstractComponent).getId().equals(
                                "tbxNro_ingresoOrden")
                        && !((Textbox) abstractComponent).getId().equals(
                                "tbxCodigo_administradoraOrden")
                        && !((Textbox) abstractComponent).getId().equals(
                                "tbxId_planOrden")
                        && !((Textbox) abstractComponent).getId().equals(
                                "tbxTipo_hcOrden")) {
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
        /*
         * tbxCodigo_ordenador.setValue("000000000");
         * tbxNomOrdenador.setValue("MEDICO POR DEFECTO");
         * 
         * tbxCodigo_dx.setValue("Z000");
         * tbxNomDx.setValue("EXAMEN MEDICO GENERAL");
         */
        cargarPrestador();
        cargarDx();
        lista_datos.clear();
        crearFilas();
		// adicionarBlanco();
		/*
         * if(permisos_sc.getPermiso_crear()){
         * ((Button)form.getFellow("btGuardar")).setDisabled(false);
         * ((Button)form.getFellow("btGuardar")).setImage("/images/Save16.gif");
         * }else{ ((Button)form.getFellow("btGuardar")).setDisabled(true);
         * ((Button
         * )form.getFellow("btGuardar")).setImage("/images/Save16_disabled.gif"
         * ); }
         */
    }

    // Limpiamos los campos del formulario //
    public void deshabilitarCampos(boolean sw) throws Exception {
        Collection<Component> collection = this.getFellows();
        for (Component abstractComponent : collection) {
            if (abstractComponent instanceof Textbox) {
                if (!((Textbox) abstractComponent).getId().equals(
                        "tbxValueOrden")) {
                    ((Textbox) abstractComponent).setDisabled(sw);
                }
            }
            if (abstractComponent instanceof Listbox) {
                if (!((Listbox) abstractComponent).getId().equals(
                        "lbxFormatoOrden")) {
                    if (((Listbox) abstractComponent).getItemCount() > 0) {
                        ((Listbox) abstractComponent).setDisabled(sw);
                    }
                }
            }
            if (abstractComponent instanceof Doublebox) {
                ((Doublebox) abstractComponent).setDisabled(sw);
            }
            if (abstractComponent instanceof Intbox) {
                ((Intbox) abstractComponent).setDisabled(sw);
            }
            if (abstractComponent instanceof Datebox) {
                ((Datebox) abstractComponent).setDisabled(sw);
            }
            if (abstractComponent instanceof Checkbox) {
                ((Checkbox) abstractComponent).setDisabled(sw);
            }
            if (abstractComponent instanceof Radio) {
                ((Radio) abstractComponent).setDisabled(sw);
            }
        }

        for (int i = 0; i < lista_datos.size(); i++) {
            Listitem fila = (Listitem) listboxOrdenes_servicio
                    .getFellow("fila_" + i);

            Textbox tbxCodigo_procedimiento = (Textbox) fila
                    .getFellow("codigo_procedimiento_" + i);
            Textbox tbxNombre_procedimiento = (Textbox) fila
                    .getFellow("nombre_procedimiento_" + i);
            Doublebox tbxUnidades = (Doublebox) fila.getFellow("unidades_" + i);
            Doublebox tbxValor_procedimiento = (Doublebox) fila
                    .getFellow("valor_procedimiento_" + i);
            /*
             * Doublebox tbxDescuento = (Doublebox)
             * fila.getFellow("descuento_"+i); Doublebox tbxIncremento =
             * (Doublebox) fila.getFellow("incremento_"+i); Doublebox
             * tbxValor_real = (Doublebox) fila.getFellow("valor_real_"+i);
             * Textbox tbxSexo_pcd = (Textbox) fila.getFellow("sexo_pcd_"+i);
             * Textbox tbxLimite_inferior_pcd = (Textbox)
             * fila.getFellow("limite_inferior_pcd_"+i); Textbox
             * tbxLimite_superior_pcd = (Textbox)
             * fila.getFellow("limite_superior_pcd_"+i);
             */
            Image img = (Image) fila.getFellow("img_" + i);

            if (!sw) {
                tbxCodigo_procedimiento.setReadonly(false);
                tbxNombre_procedimiento.setReadonly(false);
                tbxUnidades.setReadonly(false);
                tbxValor_procedimiento.setReadonly(false);
                img.setVisible(true);
                tbxNombres_procedimientos.setReadonly(false);
                FormularioUtil.deshabilitarComponentes(
                        rgpConsentimiento_informado, false, new String[]{});
            } else {
                tbxCodigo_procedimiento.setReadonly(true);
                tbxNombre_procedimiento.setReadonly(true);
                tbxUnidades.setReadonly(true);
                tbxValor_procedimiento.setReadonly(true);
                img.setVisible(false);
                tbxNombres_procedimientos.setReadonly(true);
                FormularioUtil.deshabilitarComponentes(
                        rgpConsentimiento_informado, true, new String[]{});
            }

            if (opcion_formulario != null
                    && opcion_formulario.equals(OpcionesFormulario.MOSTRAR
                            .toString())) {
                // log.info("opcion_formulario ===> " + opcion_formulario);
                tbxCodigo_procedimiento.setReadonly(true);
                tbxNombre_procedimiento.setReadonly(true);
                tbxUnidades.setReadonly(true);
                tbxValor_procedimiento.setReadonly(true);
                img.setVisible(false);
                tbxNombres_procedimientos.setReadonly(true);
                FormularioUtil.deshabilitarComponentes(
                        rgpConsentimiento_informado, true, new String[]{});
            }

        }

        if (!sw) {
            ((Button) this.getFellow("btGuardarOrden")).setDisabled(false);
            ((Button) this.getFellow("btGuardarOrden"))
                    .setImage("/images/Save16.gif");

            ((Button) this.getFellow("btEliminarOrden")).setDisabled(false);
            ((Button) this.getFellow("btEliminarOrden"))
                    .setImage("/images/eliminar.gif");

            ((Button) this.getFellow("btAdicionarOrden")).setDisabled(false);
        } else {
            ((Button) this.getFellow("btGuardarOrden")).setDisabled(true);
            ((Button) this.getFellow("btGuardarOrden"))
                    .setImage("/images/Save16_disabled.gif");

            ((Button) this.getFellow("btEliminarOrden")).setDisabled(true);
            ((Button) this.getFellow("btEliminarOrden"))
                    .setImage("/images/eliminar_gris.gif");

            ((Button) this.getFellow("btAdicionarOrden")).setDisabled(true);
        }

        if (rol_usuario.equals("05")) {
            tbxCodigo_ordenador.setDisabled(true);
        } else {
            tbxCodigo_ordenador.setDisabled(sw);
        }
    }

    public void buscarOrdenador(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("codigo_empresa", empresa.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");

            parametros.put("limite_paginado", "limit 25 offset 0");

            List<Prestadores> list = zkWindow.getServiceLocator()
                    .getPrestadoresService().listar(parameters);

            lbx.getItems().clear();

            for (Prestadores dato : list) {

                Especialidad especialidad = new Especialidad();
                especialidad.setCodigo(dato.getCodigo_especialidad());
                especialidad = zkWindow.getServiceLocator()
                        .getEspecialidadService().consultar(especialidad);

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
                listcell.appendChild(new Label(dato.getNombres()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getApellidos()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(
                        (especialidad != null ? especialidad.getNombre() : "")));
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

    public void selectedOrdenador(Listitem listitem) {
        if (listitem.getValue() == null) {
            tbxCodigo_ordenador.setValue("");
            tbxNomOrdenador.setValue("");
        } else {
            Prestadores dato = (Prestadores) listitem.getValue();
            tbxCodigo_ordenador.setValue(dato.getNro_identificacion());
            tbxNomOrdenador.setValue(dato.getNombres() + " "
                    + dato.getApellidos());
        }
        tbxCodigo_ordenador.close();
    }

    public void buscarPrestador(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");

            zkWindow.getServiceLocator().getAdministradoraService()
                    .setLimit("limit 25 offset 0");

            List<Administradora> list = zkWindow.getServiceLocator()
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

    public void selectedPrestador(Listitem listitem) {
        if (listitem.getValue() == null) {
            tbxId_prestador.setValue("");
            tbxNomPrestadorOrden.setValue("");
        } else {
            Administradora dato = (Administradora) listitem.getValue();
            tbxId_prestador.setValue(dato.getCodigo());
            tbxNomPrestadorOrden.setValue(dato.getNombre());
        }
        tbxId_prestador.close();
    }

    public void buscarDx(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");

            parameters.put("limite_paginado", "limit 25 offset 0");

            List<Cie> list = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                    .listar(Cie.class, parameters);

            lbx.getItems().clear();

            for (Cie dato : list) {

                Listitem listitem = new Listitem();
                listitem.setValue(dato);

                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(dato.getCodigo() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getNombre() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getSexo()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getLimite_inferior()));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getLimite_superior()));
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

    public void selectedDx(Listitem listitem) {
        if (listitem.getValue() == null) {
            tbxCodigo_dx.setValue("");
            tbxNomOrdenDx.setValue("");
            tbxSexo_dx.setValue("A");
            tbxLimite_inferior_dx.setValue("0");
            tbxLimite_superior_dx.setValue("599");
        } else {
            Cie dato = (Cie) listitem.getValue();
            tbxCodigo_dx.setValue(dato.getCodigo());
            tbxNomOrdenDx.setValue(dato.getNombre());
            tbxSexo_dx.setValue(dato.getSexo());
            tbxLimite_inferior_dx.setValue(dato.getLimite_inferior());
            tbxLimite_superior_dx.setValue(dato.getLimite_superior());
        }
        tbxCodigo_dx.close();
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        tbxCodigo_ordenador.setStyle("background-color:white");
        tbxNomOrdenador.setStyle("background-color:white");

        tbxId_prestador.setStyle("background-color:white");
        tbxNomPrestadorOrden.setStyle("background-color:white");

        tbxCodigo_dx.setStyle("background-color:white");
        tbxNomOrdenDx.setStyle("background-color:white");

        boolean valida = true;

        String mensaje = "Los campos marcados con (*) son obligatorios";

        if (tbxCodigo_ordenador.getText().trim().equals("")) {
            tbxCodigo_ordenador.setStyle("background-color:#F6BBBE");
            tbxNomOrdenador.setStyle("background-color:#F6BBBE");
            valida = false;
        }

        if (tbxId_prestador.getText().trim().equals("")) {
            tbxId_prestador.setStyle("background-color:#F6BBBE");
            tbxNomPrestadorOrden.setStyle("background-color:#F6BBBE");
            valida = false;
        }

        if (tbxCodigo_dx.getText().trim().equals("")) {
            tbxCodigo_dx.setStyle("background-color:#F6BBBE");
            tbxNomOrdenDx.setStyle("background-color:#F6BBBE");
            valida = false;
        }

        if (valida && lista_datos.size() <= 0) {
            mensaje = "Debe crear al menos un registro de ordenes";
            valida = false;
        }

        if (valida) {
            Object result[] = validarDx("");
            valida = (Boolean) result[0];
            mensaje = (String) result[1];
        }

        if (valida) {
            actualizarPagina();
            for (int i = 0; i < lista_datos.size(); i++) {
                Map bean = lista_datos.get(i);
                String codigo_procedimiento = (String) bean
                        .get("codigo_procedimiento");
                String nombre_procedimiento = (String) bean
                        .get("nombre_procedimiento");
                // log.info("Nomgre procedimeinto: " + nombre_procedimiento);

                if (((Double) bean.get("unidades")) <= 0) {
                    mensaje = "El valor de las unidades en el procedimiento "
                            + nombre_procedimiento
                            + " no puede ser menor ni igual a cero";
                    valida = false;
                    i = lista_datos.size();
                }

                if (valida) {
                    Object result[] = validarPcd(bean, "El procedimiento");
                    valida = (Boolean) result[0];
                    mensaje = (String) result[1];
                }

                if (valida) {
                    Object result[] = validarDx("");
                    valida = (Boolean) result[0];
                    mensaje = (String) result[1];
                }

                if (valida) {
                    for (int j = i + 1; j < lista_datos.size(); j++) {
                        Map beanAux = lista_datos.get(j);
                        if (codigo_procedimiento.equals((String) beanAux
                                .get("codigo_procedimiento"))) {
                            valida = false;
                            mensaje = "El procedimiento "
                                    + codigo_procedimiento
                                    + " se encuentra repetido";
                            i = lista_datos.size();
                            j = lista_datos.size();
                        }
                    }
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

    public boolean validarFormExterno() throws Exception {
        tbxUnidades = null;
        tbxCodigo_ordenador.setStyle("background-color:white");
        tbxNomOrdenador.setStyle("background-color:white");

        tbxId_prestador.setStyle("background-color:white");
        tbxNomPrestadorOrden.setStyle("background-color:white");

        tbxCodigo_dx.setStyle("background-color:white");
        tbxNomOrdenDx.setStyle("background-color:white");

        boolean valida = true;

        String mensaje = "Los campos marcados con (*) son obligatorios";

        if (trabajar_sin_admision_atendida) {
            cargarPrestador();
            cargarDx();
            tbxId_prestador.setValue(admision.getCodigo_administradora());
        }

        if (tbxCodigo_ordenador.getText().trim().equals("")) {
            tbxCodigo_ordenador.setStyle("background-color:#F6BBBE");
            tbxNomOrdenador.setStyle("background-color:#F6BBBE");
            valida = false;
        }

        if (tbxId_prestador.getText().trim().equals("")) {
            tbxId_prestador.setStyle("background-color:#F6BBBE");
            tbxNomPrestadorOrden.setStyle("background-color:#F6BBBE");
            valida = false;
        }

        if (tbxCodigo_dx.getText().trim().equals("")) {
            tbxCodigo_dx.setStyle("background-color:#F6BBBE");
            tbxNomOrdenDx.setStyle("background-color:#F6BBBE");
            valida = false;
        }

		// if (valida && lista_datos.size() <= 0) {
        // mensaje = "Debe crear al menos un registro de ordenes";
        // valida = false;
        // }
        if (valida) {
            Object result[] = validarDx("");
            valida = (Boolean) result[0];
            mensaje = (String) result[1];
        }

        if (valida && !lista_datos.isEmpty()) {
            actualizarPagina();

            for (int i = 0; i < lista_datos.size(); i++) {
                Map bean = lista_datos.get(i);
                String codigo_procedimiento = (String) bean
                        .get("codigo_procedimiento");
                String nombre_procedimiento = (String) bean
                        .get("nombre_procedimiento");

                double unidades = bean.get("unidades") != null ? ((Double) bean
                        .get("unidades")) : 0;

                if (unidades <= 0) {
                    mensaje = "El valor de las unidades en el procedimiento "
                            + nombre_procedimiento
                            + " no puede ser menor ni igual a cero";
                    valida = false;
                    tbxUnidades = (Doublebox) this.getFellow("unidades_" + i);
                    i = lista_datos.size();
                }

                if (valida) {
                    Object result[] = validarPcd(bean, "El procedimiento");
                    valida = (Boolean) result[0];
                    mensaje = (String) result[1];
                }

                if (valida) {
                    Object result[] = validarDx("");
                    valida = (Boolean) result[0];
                    mensaje = (String) result[1];
                }

                if (valida) {
                    for (int j = i + 1; j < lista_datos.size(); j++) {
                        Map beanAux = lista_datos.get(j);
                        if (codigo_procedimiento.equals((String) beanAux
                                .get("codigo_procedimiento"))) {
                            valida = false;
                            mensaje = "El procedimiento "
                                    + codigo_procedimiento
                                    + " se encuentra repetido";
                            i = lista_datos.size();
                            j = lista_datos.size();
                        }
                    }
                }
            }
        }

        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres()
                    + " recuerde que...", mensaje, new EventListener<Event>() {

                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            if (tbxUnidades != null) {
                                tbxUnidades.setFocus(true);
                            }
                        }

                    });
        }

        return valida;
    }

    public Object[] validarPcd(Map bean, String msg) {
        String codigo_procedimiento = (String) bean.get("codigo_cups");
        String limite_inferior = (String) bean.get("limite_inferior_pcd");
        String limite_superior = (String) bean.get("limite_superior_pcd");
        String sexo_pcd = (String) bean.get("sexo_pcd");

        Map<String, Object> mapa_validar = Utilidades
                .validarInformacionLimiteSexo("Procedimiento",
                        codigo_procedimiento, limite_inferior, limite_superior,
                        sexo_pcd, simpleDateFormat.format(admision
                                .getPaciente().getFecha_nacimiento()), admision
                        .getPaciente().getSexo());

        return new Object[]{(Boolean) mapa_validar.get("success"),
            (String) mapa_validar.get("result")};

    }

    public Object[] validarDx(String msg) {
        // String nombre_dx = tbxNomOrdenDx.getValue();
        String limite_inferior = tbxLimite_inferior_dx.getValue();
        String limite_superior = tbxLimite_superior_dx.getValue();
        String sexo_dx = tbxSexo_dx.getValue();

        Map<String, Object> mapa_validar = Utilidades
                .validarInformacionLimiteSexo("Diagnostico", tbxCodigo_dx
                        .getValue(), limite_inferior, limite_superior, sexo_dx,
                        simpleDateFormat.format(admision.getPaciente()
                                .getFecha_nacimiento()), admision.getPaciente()
                        .getSexo());

        return new Object[]{(Boolean) mapa_validar.get("success"),
            (String) mapa_validar.get("result")};

    }

    public Map<String, Object> obtenerDatos() {
        Map datos = new HashMap();
        if (orden_servicio == null) {
            orden_servicio = new Orden_servicio();
        }

        orden_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
        orden_servicio.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        orden_servicio
                .setCodigo_orden(orden_servicio.getCodigo_orden() != null ? orden_servicio
                        .getCodigo_orden() : tbxCodigo_orden.getValue());
        orden_servicio.setFecha_orden(new Timestamp(dtbxFecha_orden.getValue()
                .getTime()));
        orden_servicio.setNro_ingreso(tbxNro_ingreso.getValue());
        orden_servicio.setCodigo_paciente(admision.getNro_identificacion());
        orden_servicio.setCodigo_administradora(admision
                .getCodigo_administradora());
        orden_servicio.setId_plan(admision.getId_plan());
        orden_servicio.setCodigo_ordenador(tbxCodigo_ordenador.getValue());
        orden_servicio.setCodigo_dx(tbxCodigo_dx.getValue());
        orden_servicio.setId_prestador(usuarios.getCodigo());
        orden_servicio.setEstado("01");
        orden_servicio.setCreacion_date(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        orden_servicio.setUltimo_update(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        orden_servicio.setCreacion_user(usuarios.getCodigo());
        orden_servicio.setUltimo_user(usuarios.getCodigo());
        orden_servicio.setActualizado(true);
        orden_servicio.setTipo_hc(tbxTipo_hc.getValue());
        orden_servicio.setCumplida("N");
        orden_servicio.setConsentimiento(rgpConsentimiento_informado
                .getSelectedItem().getValue().toString());
        orden_servicio.setObservacion(tbxObservaciones_ordenes.getValue());
        if (dtbxFecha_consentimiento.getValue() != null) {
            orden_servicio.setFecha_consentimiento(new Timestamp(
                    dtbxFecha_consentimiento.getValue().getTime()));
        }
        orden_servicio.setNombres_procedimientos(tbxNombres_procedimientos
                .getValue());

		// por defecto el tipo de la orden tiene que ser de tipo ambulatoria que
        // es 1
        orden_servicio.setTipo_orden(IConstantes.TIPO_ORDEN_AMBULATORIA);
        actualizarPagina();
        List<Detalle_orden> lista_detalle = new LinkedList<Detalle_orden>();
        for (int i = 0; i < lista_datos.size(); i++) {
            Map bean = lista_datos.get(i);

            String consecutivo = i + "";
            String codigo_procedimiento = (String) bean
                    .get("codigo_procedimiento");
            double valor_procedimiento = (Double) bean
                    .get("valor_procedimiento");
            double unidades = (Double) bean.get("unidades");
            double descuento = (Double) bean.get("descuento");
            double incremento = (Double) bean.get("incremento");
            double valor_real = (Double) bean.get("valor_real");
            String codigo_cups = (String) bean.get("codigo_cups");
            String tipo_procedimiento = (String) bean.get("tipo_procedimiento");
            String nombre_procedimiento = (String) bean
                    .get("nombre_procedimiento");

            Detalle_orden detalle = new Detalle_orden();
            detalle.setCodigo_empresa(orden_servicio.getCodigo_empresa());
            detalle.setCodigo_sucursal(orden_servicio.getCodigo_sucursal());
            detalle.setCodigo_orden(orden_servicio.getId());
            detalle.setConsecutivo(consecutivo);
            detalle.setCodigo_procedimiento(codigo_procedimiento);
            detalle.setValor_procedimiento(valor_procedimiento);
            detalle.setUnidades((int) unidades);
            detalle.setDescuento(descuento);
            detalle.setIncremento(incremento);
            detalle.setValor_real(valor_real);
            detalle.setCodigo_cups(codigo_cups);
            detalle.setRealizado("N");
            detalle.setUnidades_realizadas(0);
            detalle.setTipo_procedimiento(tipo_procedimiento);
            detalle.setNombre_procedimiento(nombre_procedimiento);
            lista_detalle.add(detalle);
        }

        datos.put("orden_servicio", orden_servicio);
        datos.put("lista_detalle", lista_detalle);
        datos.put("accion", tbxAccion.getText());
        return datos;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            // setUpperCase();
            if (validarForm()) {
                // Cargamos los componentes //

                Map datos = new HashMap();

                Orden_servicio orden_servicio = new Orden_servicio();
                orden_servicio.setCodigo_empresa(empresa.getCodigo_empresa());
                orden_servicio
                        .setCodigo_sucursal(sucursal.getCodigo_sucursal());
                orden_servicio.setCodigo_orden(tbxCodigo_orden.getValue());
                orden_servicio.setFecha_orden(new Timestamp(dtbxFecha_orden
                        .getValue().getTime()));
                orden_servicio.setNro_ingreso(tbxNro_ingreso.getValue());
                orden_servicio
                        .setCodigo_paciente(tbxCodigo_paciente.getValue());
                orden_servicio
                        .setCodigo_administradora(tbxCodigo_administradora
                                .getValue());
                orden_servicio.setId_plan(tbxId_plan.getValue());
                orden_servicio.setCodigo_ordenador(tbxCodigo_ordenador
                        .getValue());
                orden_servicio.setCodigo_dx(tbxCodigo_dx.getValue());
                orden_servicio.setId_prestador(tbxId_prestador.getValue());
                orden_servicio.setEstado("01");
                orden_servicio.setCreacion_date(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                orden_servicio.setUltimo_update(new Timestamp(
                        new GregorianCalendar().getTimeInMillis()));
                orden_servicio.setCreacion_user(usuarios.getCodigo());
                orden_servicio.setUltimo_user(usuarios.getCodigo());
                orden_servicio.setActualizado(true);
                orden_servicio.setTipo_hc(tbxTipo_hc.getValue());
                orden_servicio.setObservacion(tbxObservaciones_ordenes
                        .getValue());

                List<Detalle_orden> lista_detalle = new LinkedList<Detalle_orden>();
                for (int i = 0; i < lista_datos.size(); i++) {
                    Map bean = lista_datos.get(i);

                    String consecutivo = i + "";
                    String codigo_procedimiento = (String) bean
                            .get("codigo_procedimiento");
                    double valor_procedimiento = (Double) bean
                            .get("valor_procedimiento");
                    double unidades = (Double) bean.get("unidades");
                    double descuento = (Double) bean.get("descuento");
                    double incremento = (Double) bean.get("incremento");
                    double valor_real = (Double) bean.get("valor_real");
					// String nombre_procedimiento =
                    // (String)bean.get("nombre_procedimiento");

                    Detalle_orden detalle = new Detalle_orden();
                    detalle.setCodigo_empresa(orden_servicio
                            .getCodigo_empresa());
                    detalle.setCodigo_sucursal(orden_servicio
                            .getCodigo_sucursal());
                    detalle.setCodigo_orden(orden_servicio.getId());
                    detalle.setConsecutivo(consecutivo);
                    detalle.setCodigo_procedimiento(codigo_procedimiento);
                    detalle.setValor_procedimiento(valor_procedimiento);
                    detalle.setUnidades((int) unidades);
                    detalle.setDescuento(descuento);
                    detalle.setIncremento(incremento);
                    detalle.setValor_real(valor_real);
                    lista_detalle.add(detalle);
                }

                datos.put("orden_servicio", orden_servicio);
                datos.put("lista_detalle", lista_detalle);
                datos.put("accion", tbxAccion.getText());

                this.orden_servicio = zkWindow.getServiceLocator()
                        .getOrden_servicioService().guardar(datos);
                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    tbxAccion.setText("modificar");
                }
                mostrarDatos(orden_servicio);

                Messagebox
                        .show("Los datos se guardaron satisfactoriamente. Desea imprimir el documento? ",
                                "impresion", Messagebox.YES + Messagebox.NO,
                                Messagebox.QUESTION,
                                new org.zkoss.zk.ui.event.EventListener() {
                                    public void onEvent(Event event)
                                    throws Exception {
                                        if ("onYes".equals(event.getName())) {
                                            imprimir();
                                        }
                                    }
                                });
            }

        } catch (Exception e) {

            log.error(e.getMessage(), e);
            Messagebox.show(e.getMessage(), "Mensaje de Error !!",
                    Messagebox.OK, Messagebox.ERROR);
        }
    }

    public void mostrarDatos(Object obj, Boolean mostrar_observaciones)
            throws Exception {
        listfooterObservaciones.setVisible(mostrar_observaciones);
        mostrarDatos(obj);
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        orden_servicio = (Orden_servicio) obj;
        try {
            tbxCodigo_orden.setValue(orden_servicio.getCodigo_orden());
            tbxCodigo_paciente.setValue(orden_servicio.getCodigo_paciente());
            tbxCodigo_administradora.setValue(orden_servicio
                    .getCodigo_administradora());
            tbxId_plan.setValue(orden_servicio.getId_plan());
            tbxNro_ingreso.setValue(orden_servicio.getNro_ingreso());

            dtbxFecha_orden.setValue(orden_servicio.getFecha_orden());

            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(orden_servicio.getCodigo_empresa());
            prestadores.setCodigo_sucursal(orden_servicio.getCodigo_sucursal());
            prestadores.setNro_identificacion(orden_servicio
                    .getCodigo_ordenador());
            prestadores = zkWindow.getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);
            tbxCodigo_ordenador.setValue(orden_servicio.getCodigo_ordenador());
            tbxNomOrdenador.setValue((prestadores != null ? prestadores
                    .getNombres() + " " + prestadores.getApellidos() : ""));

            Cie cie = new Cie();
            cie.setCodigo(orden_servicio.getCodigo_dx());
            cie = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            tbxCodigo_dx.setValue(orden_servicio.getCodigo_dx());
            tbxNomOrdenDx.setValue((cie != null ? cie.getNombre() : ""));
            tbxSexo_dx.setValue((cie != null ? cie.getSexo() : "A"));
            tbxLimite_inferior_dx.setValue((cie != null ? cie
                    .getLimite_inferior() : "0"));
            tbxLimite_superior_dx.setValue((cie != null ? cie
                    .getLimite_superior() : "599"));

            Administradora admin = new Administradora();
            admin.setCodigo_empresa(orden_servicio.getCodigo_empresa());
            admin.setCodigo_sucursal(orden_servicio.getCodigo_sucursal());
            admin.setCodigo(orden_servicio.getId_prestador());
            admin = zkWindow.getServiceLocator().getAdministradoraService()
                    .consultar(admin);
            tbxId_prestador.setValue(orden_servicio.getId_prestador());
            tbxNomPrestadorOrden.setValue((admin != null ? admin.getNombre()
                    : ""));

            tbxObservaciones_ordenes.setValue(orden_servicio.getObservacion());

            lista_datos.clear();
            listboxOrdenes_servicio.getItems().clear();
            List<Detalle_orden> lista_detalle = orden_servicio
                    .getLista_detalle();

            if (lista_detalle == null) {
                lista_detalle = new ArrayList<Detalle_orden>();
            }

            for (Detalle_orden detalle : lista_detalle) {
                Map bean = llenarBeanDetalle(detalle);
                lista_datos.add(bean);
            }

            crearFilas();

            if (!lista_detalle.isEmpty()) {
                hlayoutConsentimiento.setVisible(true);
                if (orden_servicio.getConsentimiento() != null
                        && orden_servicio.getConsentimiento().equals("S")) {
                    rgpConsentimiento_informado.setSelectedIndex(0);
                } else {
                    rgpConsentimiento_informado.setSelectedIndex(1);
                }
                dtbxFecha_consentimiento.setValue(orden_servicio
                        .getFecha_consentimiento());
                tbxNombres_procedimientos.setValue(orden_servicio
                        .getNombres_procedimientos());
            }

            if (ocultar_consentimiento != null) {
                hlayoutConsentimiento.setVisible(false);
                // log.info(">>>>>>>1>FOOTER INVISIBLE");
            }

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "Este dato no se puede editar",
                    zkWindow);
        }
    }

    public void eliminarDatos(String codigo_orden) throws Exception {
        final String cod_ord = codigo_orden;
        if (codigo_orden.equals("")) {
            Messagebox.show("La orden de servicio no se ha guardado aún",
                    "Informacion ..", Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }
        try {
            Messagebox.show("Esta seguro que desea eliminar este registro? ",
                    "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                    Messagebox.QUESTION,
                    new org.zkoss.zk.ui.event.EventListener() {
                        public void onEvent(Event event) throws Exception {
                            if ("onYes".equals(event.getName())) {
                                Orden_servicio orden_servicio = new Orden_servicio();
                                orden_servicio.setCodigo_empresa(empresa
                                        .getCodigo_empresa());
                                orden_servicio.setCodigo_sucursal(sucursal
                                        .getCodigo_sucursal());
                                orden_servicio.setCodigo_orden(cod_ord);
                                int result = zkWindow.getServiceLocator()
                                .getOrden_servicioService()
                                .eliminar(orden_servicio);
                                if (result == 0) {
                                    throw new Exception(
                                            "Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
                                }

                                accionForm(true, "registrar");

                                Messagebox
                                .show("Informacion se eliminó satisfactoriamente !!",
                                        "Information", Messagebox.OK,
                                        Messagebox.INFORMATION);
                            }
                        }
                    });

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

    public void imprimir() throws Exception {
        if (orden_servicio == null) {
            Messagebox.show("La orden no se ha guardado aún",
                    "Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }

        Map paramRequest = new HashMap();
        paramRequest.put("name_report", "Orden_servicio");
        paramRequest.put("id", orden_servicio.getId());
        paramRequest.put("formato", lbxFormatoOrden.getSelectedItem()
                .getValue().toString());

        Component componente = Executions.createComponents(
                "/pages/printInformes.zul", zkWindow, paramRequest);
        final Window window = (Window) componente;
        window.setMode("modal");
        window.setMaximizable(true);
        window.setMaximized(true);

        // Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
    }

    private Map llenarBeanDetalle(Detalle_orden detalle) throws Exception {
        String codigo_procedimiento = detalle.getCodigo_procedimiento();
        String nombre_procedimiento = "";
        String sexo_pcd = "A";
        String limite_inferior_pcd = "0";
        String limite_superior_pcd = "0";
        double valor_procedimiento = detalle.getValor_procedimiento();
        int unidades = detalle.getUnidades();
        double descuento = detalle.getDescuento();
        double incremento = detalle.getIncremento();
        double valor_real = detalle.getValor_real();
        String codigo_cups = detalle.getCodigo_cups();

        Procedimientos proc = new Procedimientos();
        proc.setId_procedimiento(new Long(detalle.getCodigo_procedimiento()));
        // proc.setCodigo_cups(detalle.getCodigo_procedimiento());
        proc = zkWindow.getServiceLocator().getProcedimientosService()
                .consultar(proc);
        nombre_procedimiento = (proc != null ? proc.getDescripcion() : "");
        sexo_pcd = (proc != null ? proc.getSexo() : "");
        limite_inferior_pcd = (proc != null ? proc.getLimite_inferior() : "");
        limite_superior_pcd = (proc != null ? proc.getLimite_superior() : "");
        codigo_cups = proc != null ? proc.getCodigo_cups() : detalle
                .getCodigo_procedimiento();

		// log.info("nombre_procedimiento ===> " + nombre_procedimiento);
        // log.info("codigo_cups          ===> " + codigo_cups);
        Map bean = new HashMap();
        bean.put("id_procedimiento", proc.getId_procedimiento());
        bean.put("codigo_procedimiento", codigo_procedimiento);
        bean.put("nombre_procedimiento", nombre_procedimiento);
        bean.put("unidades", (double) unidades);
        bean.put(
                "unidades_realizadas",
                detalle.getUnidades_realizadas() != null ? (double) detalle
                .getUnidades_realizadas() : 0.0);
        bean.put("valor_procedimiento", valor_procedimiento);
        bean.put("descuento", descuento);
        bean.put("incremento", incremento);
        bean.put("valor_real", valor_real);
        bean.put("sexo_pcd", sexo_pcd);
        bean.put("limite_inferior_pcd", limite_inferior_pcd);
        bean.put("limite_superior_pcd", limite_superior_pcd);
        bean.put("codigo_cups", codigo_cups);
        bean.put("tipo_procedimiento", detalle.getTipo_procedimiento());
        bean.put("cantidad_maxima", proc != null ? proc.getCantidad_maxima()
                : null);

        detalle.setNombre_procedimiento(nombre_procedimiento);

        return bean;
    }

    public void openPcd() throws Exception {

        Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                .getManuales_tarifarios(admision);

        String pages = "";
        // getServiceLocator().getContratosService().consultar(contratos);

        String anio = (manuales_tarifarios != null ? (manuales_tarifarios
                .getAnio() != null ? manuales_tarifarios.getAnio() : "") : "");

        pages = "/pages/openProcedimientos.zul";

        Map parametros = new HashMap();
        parametros.put("codigo_empresa", empresa.getCodigo_empresa());
        parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
        parametros.put("codigo_administradora",
                tbxCodigo_administradora.getValue());
        parametros.put("id_plan", tbxId_plan.getValue());
        parametros.put("restringido", "");
        parametros.put("nro_ingreso", tbxNro_ingreso.getValue());
        parametros.put("nro_identificacion", tbxCodigo_paciente.getValue());
        parametros.put("admision", admision);

        parametros.put("anio", anio);
        parametros.put("seleccionados", seleccionados);
        parametros.put("via_ingreso", admision.getVia_ingreso());
        parametros.put("manual_tarifario", manuales_tarifarios);

		// log.info("Paginas: " + pages);
        Component componente = Executions.createComponents(pages, null,
                parametros);
        final Window ventana = (Window) componente;
        if (ventana instanceof OpenProcedimientosAction) {
            ((OpenProcedimientosAction) ventana)
                    .setSeleccionar_componente(this);
        }

        ventana.setWidth("990px");
        ventana.setHeight("400px");
        ventana.setClosable(true);
        ventana.setMaximizable(true);
        ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS");
        ventana.setMode("modal");
    }

    public void adicionarOrden(Detalle_orden detalle,
            String nombre_procedimiento) throws Exception {
        try {
            Map bean = llenarBeanDetalle(detalle);
            bean.put("nombre_procedimiento", nombre_procedimiento);
            lista_datos.add(bean);
            crearFilas();
            // repintarGridCuentas();
        } catch (Exception e) {
            MensajesUtil.mensajeError(e,
                    "Error al seleccionar el procedimiento", zkWindow);
        }
    }

    public void crearFilas() throws Exception {
        listboxOrdenes_servicio.getItems().clear();
        seleccionados.clear();
        for (int j = 0; j < lista_datos.size(); j++) {
            Map bean = lista_datos.get(j);
            crearFilaOrden(bean, j);
        }
        listboxOrdenes_servicio.setVisible(true);
        listboxOrdenes_servicio.setMold("paging");
        listboxOrdenes_servicio.setPageSize(20);
        listboxOrdenes_servicio.applyProperties();
        listboxOrdenes_servicio.invalidate();
    }

    public void crearFilaOrden(Map bean, int j) throws Exception {
        final int index = j;

        final String codigo_procedimiento = (String) bean
                .get("codigo_procedimiento");
        final Long id_procedimiento = (Long) bean.get("id_procedimiento");

        seleccionados.add(id_procedimiento + "");

        String nombre_procedimiento = (String) bean.get("nombre_procedimiento");
        double valor_procedimiento = (Double) bean.get("valor_procedimiento");

        double unidades = valor_inicial != 0d ? valor_inicial : (bean
                .get("unidades") != null ? (Double) bean.get("unidades") : 0d);

        double descuento = (Double) bean.get("descuento");
        double incremento = (Double) bean.get("incremento");
        double valor_real = (Double) bean.get("valor_real");
        String sexo_pcd = (String) bean.get("sexo_pcd");
        String limite_inferior_pcd = (String) bean.get("limite_inferior_pcd");
        String limite_superior_pcd = (String) bean.get("limite_superior_pcd");
        String codigo_cups = (String) bean.get("codigo_cups");

        double unidades_realizadas = (Double) bean.get("unidades_realizadas");
        Integer cantidad_maxima = (Integer) bean.get("cantidad_maxima");

        final Listitem fila = new Listitem();
        fila.setStyle("text-align: center;nowrap:nowrap");

        // Columna codigo //
        Listcell cell = new Listcell();

        final Textbox tbxCodigo_procedimiento = new Textbox(
                codigo_procedimiento);
        // tbxCodigo_procedimiento.setInplace(true);
        tbxCodigo_procedimiento.setId("codigo_procedimiento_" + j);
        tbxCodigo_procedimiento.setHflex("1");
        tbxCodigo_procedimiento.setReadonly(true);
        tbxCodigo_procedimiento.setInplace(true);
        tbxCodigo_procedimiento.setVisible(false);
        tbxCodigo_procedimiento.setAttribute("map_", bean);
        cell.appendChild(tbxCodigo_procedimiento);

        final Textbox tbxCodigo_cups = new Textbox(codigo_cups);
        // tbxCodigo_procedimiento.setInplace(true);
        tbxCodigo_cups.setId("codigo_cups_" + j);
        tbxCodigo_cups.setHflex("1");
        tbxCodigo_cups.setReadonly(true);
        tbxCodigo_cups.setInplace(true);
        cell.appendChild(tbxCodigo_cups);

        fila.appendChild(cell);

        // Columna nombre //
        cell = new Listcell();

        final Textbox tbxNombre_procedimiento = new Textbox(
                nombre_procedimiento);
        // tbxNombre_procedimiento.setInplace(true);
        tbxNombre_procedimiento.setId("nombre_procedimiento_" + j);
        tbxNombre_procedimiento.setHflex("1");
        tbxNombre_procedimiento.setReadonly(true);
        tbxNombre_procedimiento.setInplace(true);
        cell.appendChild(tbxNombre_procedimiento);
        fila.appendChild(cell);

        // Columna unidades //
        cell = new Listcell();

        final Doublebox tbxUnidades = new Doublebox(unidades);
        if (unidades == 0.0) {
            tbxUnidades.setText("");
        }
        // tbxUnidades.setInplace(true);
        tbxUnidades.setId("unidades_" + j);
        tbxUnidades.setFormat("#0.##");
        tbxUnidades.setHflex("1");
        tbxUnidades.setReadonly(false);

        if (cantidad_maxima != null && cantidad_maxima > 0) {
            // para validar las cantidades
            tbxUnidades.setMaxlength(cantidad_maxima.toString().length());
            FormularioUtil.validarCantidadMaxima(cantidad_maxima, tbxUnidades,
                    nombre_procedimiento, "PROCEDIMIENTO");
        } else {
            if (admision != null
                    && (admision.getVia_ingreso().equals(
                            IVias_ingreso.ODONTOLOGIA)
                    || admision.getVia_ingreso().equals(
                            IVias_ingreso.ODONTOLOGIA2) || admision
                    .getVia_ingreso().equals(
                            IVias_ingreso.URGENCIA_ODONTOLOGICO))) {
                tbxUnidades.setMaxlength(2);
            } else {
                tbxUnidades.setMaxlength(1);
            }
        }

        cell.appendChild(tbxUnidades);
        fila.appendChild(cell);

        // Columna valor pcd //
        cell = new Listcell();

        final Doublebox tbxValor_procedimiento = new Doublebox(
                valor_procedimiento);
        // tbxValor_procedimiento.setInplace(true);
        tbxValor_procedimiento.setId("valor_procedimiento_" + j);
        tbxValor_procedimiento.setFormat("#,##0.00");
        tbxValor_procedimiento.setHflex("1");
        tbxValor_procedimiento.setReadonly(true);
        cell.appendChild(tbxValor_procedimiento);

        final Doublebox tbxDescuento = new Doublebox(descuento);
        tbxDescuento.setId("descuento_" + j);
        tbxDescuento.setVisible(false);
        cell.appendChild(tbxDescuento);

        final Doublebox tbxIncremento = new Doublebox(incremento);
        tbxIncremento.setId("incremento_" + j);
        tbxIncremento.setVisible(false);
        cell.appendChild(tbxIncremento);

        final Doublebox tbxValor_real = new Doublebox(valor_real);
        tbxValor_real.setId("valor_real_" + j);
        tbxValor_real.setVisible(false);
        cell.appendChild(tbxValor_real);

        final Textbox tbxSexo_pcd = new Textbox(sexo_pcd);
        tbxSexo_pcd.setId("sexo_pcd_" + j);
        tbxSexo_pcd.setVisible(false);
        cell.appendChild(tbxSexo_pcd);

        final Textbox tbxLimite_inferior_pcd = new Textbox(limite_inferior_pcd);
        tbxLimite_inferior_pcd.setId("limite_inferior_pcd_" + j);
        tbxLimite_inferior_pcd.setVisible(false);
        cell.appendChild(tbxLimite_inferior_pcd);

        final Textbox tbxLimite_superior_pcd = new Textbox(limite_superior_pcd);
        tbxLimite_superior_pcd.setId("limite_superior_pcd_" + j);
        tbxLimite_superior_pcd.setVisible(false);
        cell.appendChild(tbxLimite_superior_pcd);

        fila.appendChild(cell);

        // columna estado del medicamento
        cell = new Listcell();
        Textbox textbox_estado = new Textbox();
        textbox_estado.setReadonly(true);
        textbox_estado.setHflex("1");
        if (unidades_realizadas < unidades) {
            if (unidades_realizadas == 0.0) {
                textbox_estado
                        .setStyle("text-align:center;background-color:red"
                                + ";font-weight:bold;border-style:solid;color:white;font-size:14px");
                textbox_estado.setValue("0");
                cell.setTooltiptext("Procedimiento no realizado");
            } else {
                textbox_estado
                        .setStyle("text-align:center;background-color:orange"
                                + ";font-weight:bold;border-style:solid;color:white;font-size:14px");
                textbox_estado.setValue("" + (int) unidades_realizadas);
                cell.setTooltiptext("Procedimiento realizado parcialmente");
            }
        } else {
            textbox_estado
                    .setStyle("text-align:center;background-color:green"
                            + ";font-weight:bold;border-style:solid;color:white;font-size:14px");
            textbox_estado.setValue("" + (int) unidades_realizadas);
            cell.setTooltiptext("Procedimiento entregado");
        }

        cell.appendChild(textbox_estado);

        fila.appendChild(cell);

        // Columna borrar //
        cell = new Listcell();

        Image img = new Image("/images/borrar.gif");
        img.setId("img_" + j);
        img.setTooltiptext("Quitar registro");
        img.setStyle("cursor:pointer");
        cell.appendChild(img);
        fila.appendChild(cell);

        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {

                Messagebox.show(
                        "Esta seguro que desea eliminar este registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    seleccionados.remove(id_procedimiento + "");
                                    actualizarPagina();
                                    lista_datos.remove(index);
                                    crearFilas();
                                    if (lista_datos.isEmpty()) {
                                        hlayoutConsentimiento.setVisible(false);
                                    }
                                }
                            }
                        });
            }
        });
        fila.setId("fila_" + j);
        listboxOrdenes_servicio.appendChild(fila);
    }

    /**
     * Este metodo me permite limpiar los detalles
	 *
     */
    public void limpiar() {
        listboxOrdenes_servicio.getItems().clear();
        lista_datos.clear();
        seleccionados.clear();
        listboxOrdenes_servicio.invalidate();
    }

    public void actualizarPagina() {
        for (int i = 0; i < lista_datos.size(); i++) {
            Map bean = lista_datos.get(i);

            Listitem fila = (Listitem) listboxOrdenes_servicio
                    .getFellow("fila_" + i);
            Doublebox tbxUnidades = (Doublebox) fila.getFellow("unidades_" + i);
            bean.put("unidades", tbxUnidades.getValue());
            lista_datos.set(i, bean);
        }
    }

    public ZKWindow getZkWindow() {
        return zkWindow;
    }

    public void setZkWindow(ZKWindow zkWindow) {
        this.zkWindow = zkWindow;
    }

    @Override
    public void onSeleccionar(Map<String, Object> pcd) throws Exception {
        // log.info("@adicionarPcd Ejecutando evento");
        actualizarPagina();
		// Detalle_orden detalle_orden = new Detalle_orden();
        // detalle_orden.setCodigo_empresa((String) pcd.get("codigo_empresa"));
        // detalle_orden.setCodigo_sucursal((String)
        // pcd.get("codigo_sucursal"));
        // detalle_orden.setCodigo_procedimiento(pcd
        // .get("id_procedimiento")+"");
        // detalle_orden.setValor_procedimiento((Double) pcd
        // .get("valor_procedimiento"));
        // detalle_orden.setDescuento((Double) pcd.get("descuento"));
        // detalle_orden.setIncremento((Double) pcd.get("incremento"));
        // detalle_orden.setValor_real((Double) pcd.get("valor_real"));
        // detalle_orden.setUnidades(0);
        // detalle_orden.setCodigo_cups((String) pcd.get("codigo_cups"));
        // detalle_orden.setRealizado("N");
        // detalle_orden.setTipo_procedimiento((String) pcd
        // .get("tipo_procedimiento"));

		// Integer frecuencia = (Integer) pcd.get("frecuencia");
		// String nombre_pcd = (String) pcd.get("nombre_procedimiento");
        // detalle_orden.setNombre_procedimiento(nombre_pcd);
		// log.info("Frecuencia: " + frecuencia);
		// Esta validacion es para que me valide la meta dependiendo del
        // programa
        // Admision admision = Res.clonar(this.admision);
        // if (admision.getVia_ingreso().equals(IVias_ingreso.ODONTOLOGIA2)
        // || admision.getVia_ingreso().equals(IVias_ingreso.ODONTOLOGIA)) {
        // admision.setVia_ingreso(IVias_ingreso.SALUD_ORAL);
        // }
        // if (ManejadorPoblacion.validarFrecuenciaOrdenamiento(detalle_orden
        // .getCodigo_cups(), frecuencia != null ? frecuencia : 0,
        // nombre_pcd, admision, getServiceLocator())) {
        pcd.put("codigo_procedimiento", pcd.get("id_procedimiento") + "");
        pcd.put("unidades_realizadas", 0d);
        lista_datos.add(pcd);
        crearFilas();
        // }

        if (pcd.get("id_procedimiento").equals(
                new Long(IConstantes.PROCEDIMIENTO_VIH_906249))
                && ocultar_consentimiento == null) {
            // log.info(">>>>>>>>VIH>");
            hlayoutConsentimiento.setVisible(true);
            tbxNombres_procedimientos.setValue((String) pcd
                    .get("nombre_procedimiento"));
        }
    }

    public void adicionarDetalle(Detalle_orden detalle_orden,
            Manuales_tarifarios manuales_tarifarios) throws Exception {
        Map<String, Object> pcd = Utilidades.getProcedimiento(
                manuales_tarifarios,
                new Long(detalle_orden.getCodigo_procedimiento()),
                zkWindow.getServiceLocator());
        Map bean = llenarBeanDetalle(detalle_orden);
        bean.put("nombre_procedimiento",
                (String) pcd.get("nombre_procedimiento"));
        lista_datos.add(bean);
        seleccionados.add(detalle_orden.getCodigo_procedimiento());
    }

    public boolean validarParaImpresion() {
        if (!lista_datos.isEmpty()) {
            btImprimir2Orden.setDisabled(false);
            btImprimir2Orden.setVisible(true);
            toolbarbuttonConsentimiento.setVisible(true);
            return true;
        } else {
            btImprimir2Orden.setDisabled(true);
            return false;
        }
    }

    public void imprimirConsentimientoInformado() {
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("name_report", "Consentimiento_informado");
        parametros.put("formato", "pdf");
        parametros.put("admision", admision);
        parametros.put("fecha_consentimiento", dtbxFecha_consentimiento
                .getValue() != null ? new Timestamp(dtbxFecha_consentimiento
                        .getValue().getTime()) : null);
        parametros.put("aceptar", rgpConsentimiento_informado.getSelectedItem()
                .getValue().toString());
        parametros.put("codigo_medico", admision.getCodigo_medico());
        parametros.put("nombre_procedimiento",
                tbxNombres_procedimientos.getValue());
        Component componente = Executions.createComponents(
                "/pages/printInformes.zul", zkWindow, parametros);
        final Window window = (Window) componente;
        window.setMode("modal");
        window.setMaximizable(true);
        window.setMaximized(true);
    }

    public Toolbarbutton obtenerBotonAgregar() {
        return btAdicionar2Orden;
    }

    public Toolbarbutton obtenerBotonImprimir() {
        return btImprimir2Orden;
    }

    public int getTotalDetalles() {
        return lista_datos.size();
    }

    public Toolbarbutton getToolbarbuttonConsentimiento() {
        return toolbarbuttonConsentimiento;
    }

    public void setToolbarbuttonConsentimiento(
            Toolbarbutton toolbarbuttonConsentimiento) {
        this.toolbarbuttonConsentimiento = toolbarbuttonConsentimiento;
    }

}
