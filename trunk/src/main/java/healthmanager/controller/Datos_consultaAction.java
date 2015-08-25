/*
 * datos_consultaAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Configuracion_admision_ingreso;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Via_ingreso_consultas;
import healthmanager.modelo.service.AdmisionService;
import healthmanager.modelo.service.Configuracion_admision_ingresoService;
import healthmanager.modelo.service.Plantilla_pypService;
import healthmanager.modelo.service.VacunasService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
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
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.impl.XulElement;

import com.framework.constantes.IConstantes;
import com.framework.constantes.IRoles;
import com.framework.constantes.IVias_ingreso;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.ResultadoPaginadoMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.ResultadoPaginadoMacroIMG;
import com.framework.res.Res;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Datos_consultaAction extends ZKWindow {

    private static Logger log = Logger.getLogger(Datos_consultaAction.class);
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
    private Groupbox groupboxForm;
    @View
    private Rows rowsResultado;
    @View
    private Grid gridResultado;

    @View
    private Bandbox tbxNro_identificacion;
    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_prestador;
    @View
    private Listbox lbxNro_ingreso;
    @View
    private Textbox tbxCodigo_consulta;
    @View
    private Datebox dtbxFecha_consulta;
    @View
    private Textbox tbxNumero_autorizacion;
    @View
    private Doublebox dbxValor_consulta;
    @View
    private Doublebox dbxValor_cuota;
    @View
    private Doublebox dbxValor_neto;
    @View
    private Listbox lbxFinalidad_consulta;
    @View
    private Listbox lbxCausa_externa;
    @View
    private Listbox lbxTipo_diagnostico;
    @View
    private Bandbox tbxCodigo_diagnostico_principal;
    @View
    private Bandbox tbxCodigo_diagnostico1;
    @View
    private Bandbox tbxCodigo_diagnostico2;
    @View
    private Bandbox tbxCodigo_diagnostico3;

    @View
    private Textbox tbxNro_factura;
    @View
    private Textbox tbxConsecutivo;
    @View
    private Textbox tbxTipo_identificacion;
    @View
    private Textbox tbxCodigo_administradora;
    @View
    private Textbox tbxId_plan;

    @View
    private Textbox tbxSexo_pct;
    @View
    private Textbox tbxFecha_nac;

    @View
    private Textbox tbxNomPcd;
    @View
    private Textbox tbxSexo_pcd;
    @View
    private Textbox tbxLimite_inferior_pcd;
    @View
    private Textbox tbxLimite_superior_pcd;
    @View
    private Textbox tbxEs_pyp;
    @View
    private Doublebox dbxValor_real;
    @View
    private Doublebox dbxDescuento;
    @View
    private Doublebox dbxIncremento;

    @View
    private Textbox tbxSexo_dxpp;
    @View
    private Textbox tbxLimite_inferior_dxpp;
    @View
    private Textbox tbxLimite_superior_dxpp;

    @View
    private Textbox tbxSexo_dx1;
    @View
    private Textbox tbxLimite_inferior_dx1;
    @View
    private Textbox tbxLimite_superior_dx1;

    @View
    private Textbox tbxSexo_dx2;
    @View
    private Textbox tbxLimite_inferior_dx2;
    @View
    private Textbox tbxLimite_superior_dx2;

    @View
    private Textbox tbxSexo_dx3;
    @View
    private Textbox tbxLimite_inferior_dx3;
    @View
    private Textbox tbxLimite_superior_dx3;

    // @View private Textbox tbxManual;
    @View
    private Textbox tbxAnio;
    @View
    private Textbox tbxPyp_plan;

    @View
    private Textbox tbxNomPaciente;
    @View
    private Textbox tbxNomMedico;
    @View
    private Textbox tbxAseguradora;

    @View
    private Textbox tbxNomDxpp;
    @View
    private Textbox tbxNomDx1;
    @View
    private Textbox tbxNomDx2;
    @View
    private Textbox tbxNomDx3;

    @View
    private Toolbarbutton imgQuitar_pct;
    @View
    private Toolbarbutton btnLimpiarPrestador;

    private List<Datos_consulta> lista_datos;
    @View
    private Textbox tbxPor_lote;

    @View
    private Spinner spinnerFilas;

    @View
    private Bandbox bandboxBuscar_centros;

    @View
    private Listbox lbxCentros_atencion;

    @View
    private Toolbarbutton btnFiltro_centros;

    // Jose Ojeda
    @View
    private Listbox listboxProcedimientos;
    private List<String> consultaSeleccionados = new ArrayList<String>();
    // private Listitem listitem_current;

    private List<Map> lista_consultas = new ArrayList<Map>();

    private Manuales_tarifarios manuales_tarifarios;

    private final String[] idsExcluyentes = new String[]{"tbxValue",
        "tbxNro_identificacion", "lbxParameter", "tbxValuePaciente",
        "tbxValuePrestador", "tbxValueDx", "tbxValueDx1", "tbxValueDx2",
        "tbxValueDx3", "tbxPor_lote", "dtbxFecha_consulta"};

    private ResultadoPaginadoMacro resultadoPaginadoMacro = new ResultadoPaginadoMacro();

    @Override
    public void init() throws Exception {
        lista_datos = new ArrayList<Datos_consulta>();
        listarCombos();
        deshabilitarCampos(true);
        deshabilitarComponentes();
        HttpServletRequest request = (HttpServletRequest) Executions
                .getCurrent().getNativeRequest();
        String modulo_mostrar = request.getParameter("modulo_mostrar");
        String por_lotes = request.getParameter("por_lotes");
        boolean ocultarConsulta = false;
        String nro_ingreso = "";
        String nro_identificacion = "";
        Long codigo_registro = -1L;
        Map parametros = Executions.getCurrent().getArg();
        if (parametros != null) {
            if (parametros.isEmpty()) {
                parametros = null;
            }
        }
        if (parametros != null) {
            por_lotes = (String) parametros.get("por_lotes");
            modulo_mostrar = (String) parametros.get("modulo_mostrar");
            ocultarConsulta = (Boolean) parametros.get("ocultarConsulta");
            nro_ingreso = (String) parametros.get("nro_ingreso");
            nro_identificacion = (String) parametros.get("nro_identificacion");
            codigo_registro = (Long) parametros.get("codigo_registro");
        }

        tbxPor_lote.setValue((por_lotes != null ? por_lotes : "N"));

        if (tbxPor_lote.getValue().equals("S")) {
            modulo_mostrar = "registrar";
        }

		// activarLote();
        if (parametros != null) {
            cargarDatosModuloFactura(nro_ingreso, nro_identificacion,
                    codigo_registro, ocultarConsulta);
        } else {
            if (modulo_mostrar == null) {
                accionForm(true, "registrar");
            } else {
                if (modulo_mostrar.equalsIgnoreCase("consultar")) {
                    accionForm(false, "registrar");
                    // listboxProcedimientos.setVisible(false);
                } else {
                    accionForm(true, "registrar");
                }
            }
        }
        parametrizarBandbox();
		// log.info("@Motrar dtbxFecha_consulta ===> "
        // + dtbxFecha_consulta.getValue());
    }

    public void deshabilitarComponentes() {
        if (rol_usuario.equals(IRoles.FACTURADOR_CAPS)) {
			// log.info("rol facturador ===> " + rol_usuario);
            // dbxValor_consulta.setReadonly(true);
            // dbxValor_neto.setReadonly(true);
            tbxNumero_autorizacion.setReadonly(true);
        } else {
			// dbxValor_neto.setReadonly(false);
            // dbxValor_consulta.setReadonly(false);
            tbxNumero_autorizacion.setReadonly(false);
        }
    }

    private void parametrizarBandbox() {
        parametrizarBandboxPrestador();
        parametrizarResultadoPaginado();
    }

    private void parametrizarResultadoPaginado() {
        ResultadoPaginadoMacroIMG resultadoPaginadoMacroIMG = new ResultadoPaginadoMacroIMG() {

            @Override
            public List<Datos_consulta> listarResultados(
                    Map<String, Object> parametros) {
                List<Datos_consulta> listado = getServiceLocator()
                        .getDatos_consultaService()
                        .listarResultados(parametros);
                // log.info("listado ====> " + listado.size());
                return listado;
            }

            @Override
            public Integer totalResultados(Map<String, Object> parametros) {
                Integer total = getServiceLocator().getDatos_consultaService()
                        .totalResultados(parametros);
                // log.info("total ====> " + total);
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

    private void parametrizarBandboxPrestador() {
        tbxCodigo_prestador.inicializar(tbxNomMedico, btnLimpiarPrestador);
        tbxCodigo_prestador
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {

                    @Override
                    public void renderListitem(Listitem listitem,
                            Prestadores registro) {

                        Especialidad especialidad = new Especialidad();
                        especialidad.setCodigo(registro
                                .getCodigo_especialidad());
                        especialidad = getServiceLocator()
                        .getEspecialidadService().consultar(
                                especialidad);

                        Listcell listcell = new Listcell();
                        listcell.appendChild(new Label(registro
                                        .getNro_identificacion()));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(registro.getNombres()));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(registro.getApellidos()));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(
                                        especialidad != null ? especialidad.getNombre()
                                        : ""));
                        listitem.appendChild(listcell);
                    }

                    @Override
                    public List<Prestadores> listarRegistros(
                            String valorBusqueda, Map<String, Object> parametros) {
                                parametros.put("paramTodo", "");
                                parametros.put("value", "%"
                                        + valorBusqueda.toUpperCase().trim() + "%");
                                parametros.put("codigo_empresa",
                                        sucursal.getCodigo_empresa());
                                parametros.put("codigo_sucursal",
                                        sucursal.getCodigo_sucursal());

                                parametros.put("limite_paginado", "limit 25 offset 0");

                                return getServiceLocator().getPrestadoresService()
                                .listar(parametros);
                            }

                            @Override
                            public boolean seleccionarRegistro(Bandbox bandbox,
                                    Textbox textboxInformacion, Prestadores registro) {
                                bandbox.setValue(registro.getNro_identificacion());
                                textboxInformacion.setValue(registro.getNombres() + " "
                                        + registro.getApellidos());
						// Especialidad especialidad = new Especialidad();
                                // especialidad.setCodigo(registro
                                // .getCodigo_especialidad());
                                // especialidad = getServiceLocator()
                                // .getEspecialidadService().consultar(
                                // especialidad);
                                return true;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {

                            }

                });
    }

    private void cargarDatosModuloFactura(String nro_ingreso,
            String nro_identificacion, Long codigo_registro,
            boolean ocultarConsulta) throws Exception {

        tbxNro_identificacion.setDisabled(true);
        imgQuitar_pct.setVisible(false);
        lbxNro_ingreso.setDisabled(true);

        ocultarBotoConsultar(ocultarConsulta);

        Datos_consulta datos_consulta = new Datos_consulta();
        datos_consulta.setCodigo_empresa(empresa.getCodigo_empresa());
        datos_consulta.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        datos_consulta.setCodigo_registro(codigo_registro);
        datos_consulta = getServiceLocator().getDatos_consultaService()
                .consultar(datos_consulta);
        if (datos_consulta != null) {
            listboxProcedimientos.setVisible(false);
            mostrarDatos(datos_consulta);
        } else {
            listboxProcedimientos.setVisible(true);
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(empresa.getCodigo_empresa());
            paciente.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            paciente.setNro_identificacion(nro_identificacion);
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);
            if (paciente != null) {
                tbxNro_identificacion
                        .setValue(paciente.getNro_identificacion());
                tbxNomPaciente.setValue(paciente.getNombreCompleto());
                tbxSexo_pct.setValue(paciente.getSexo());
                tbxTipo_identificacion.setValue(paciente
                        .getTipo_identificacion());
                tbxFecha_nac.setValue(new java.text.SimpleDateFormat(
                        "dd/MM/yyyy").format(paciente.getFecha_nacimiento()));

                Admision aux2 = new Admision();
                aux2.setCodigo_empresa(empresa.getCodigo_empresa());
                aux2.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                aux2.setNro_identificacion(nro_identificacion);
                aux2.setNro_ingreso(nro_ingreso);
                aux2 = getServiceLocator().getServicio(AdmisionService.class)
                        .consultar(aux2);

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
                    imgQuitar_pct.setVisible(false);

                }
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
        Utilidades.listarElementoListbox(lbxFinalidad_consulta, false,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxCausa_externa, false,
                getServiceLocator());
        Utilidades.listarElementoListbox(lbxTipo_diagnostico, false,
                getServiceLocator());
        listarCentros();
    }

    public void listarCentros() {
        lbxCentros_atencion.getItems().clear();
        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros.put("codigo_empresa", codigo_empresa);
        parametros.put("codigo_sucursal", codigo_sucursal);
        List<Centro_atencion> listado_centros = getServiceLocator()
                .getCentro_atencionService().listar(parametros);
        for (Centro_atencion centro_atencion : listado_centros) {
            Listitem listitem = new Listitem();
            listitem.setValue(centro_atencion);
            listitem.appendChild(new Listcell());
            listitem.appendChild(new Listcell(centro_atencion
                    .getCodigo_centro()
                    + " - "
                    + centro_atencion.getNombre_centro()));
            if (centro_atencion_session != null) {
                if (centro_atencion.getCodigo_centro().equals(
                        centro_atencion_session.getCodigo_centro())) {
                    listitem.setSelected(true);
                }
            }
            lbxCentros_atencion.appendChild(listitem);
        }
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("nro_identificacion");
        listitem.setLabel("Identificacion");
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
        listitem.setValue("nro_factura");
        listitem.setLabel("Nro registro");
        lbxParameter.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue("fecha_consulta");
        listitem.setLabel("Fecha(aaaa-mm-dd)");
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
            listboxProcedimientos.setVisible(false);
        } else {
            // buscarDatos();
            listboxProcedimientos.setVisible(true);
            limpiarDatos();
            listboxProcedimientos.getItems().clear();
            lista_consultas.clear();

        }
        tbxAccion.setValue(accion);
    }

	// Deshabilitar campos/Habilitar campos
    public void deshabilitarCampos(boolean sw) throws Exception {
        FormularioUtil
                .deshabilitarComponentes(groupboxForm, sw, idsExcluyentes);
        bloqueoBotonGuardar(sw);

        if (sw) {
            listarIngresos(lbxNro_ingreso, new LinkedList<Admision>(), true);
        }
        // Grilla //
        lista_datos.clear();
        // crearFilas();
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxForm, idsExcluyentes);
        imgQuitar_pct.setVisible(true);
        tbxNro_identificacion.setDisabled(false);
        tbxNro_identificacion.setValue("");
        deshabilitarCampos(true);
        dbxValor_consulta.setText("");
        dbxValor_neto.setText("");
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        boolean valida = true;

        String mensaje = "Los campos marcadosson obligatorios";

        if (tbxNro_identificacion.getText().trim().equals("")) {
            mensaje = "El campo Identificacion es obligatorio";
            MensajesUtil.notificarAlerta(mensaje, tbxNro_identificacion);
            valida = false;
        }

        if (tbxCodigo_prestador.getText().trim().equals("")) {
            mensaje = "El campo Prestador es obligatorio";
            MensajesUtil.notificarAlerta(mensaje, tbxCodigo_prestador);
            valida = false;
        }

        if (tbxCodigo_diagnostico_principal.getText().trim().equals("")) {
            mensaje = "El campo Diag Ppal es obligatorio";
            MensajesUtil.notificarAlerta(mensaje,
                    tbxCodigo_diagnostico_principal);
            valida = false;
        }

        Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
                .getValue());

        if (admision == null) {
            mensaje = "El campo Nro ingreso es obligatorio";
            MensajesUtil.notificarAlerta(mensaje, lbxNro_ingreso);
            valida = false;
        }

        if (valida) {
            if (lbxFinalidad_consulta.getSelectedItem().getValue().equals("10")
                    && tbxPyp_plan.getValue().equals("S")) {
                mensaje = "Para contratos pyp debe seleccionar finalidad consulta";
                MensajesUtil.notificarAlerta(mensaje, lbxFinalidad_consulta);
                valida = false;
            }
        }

        if (valida) {

            Configuracion_admision_ingreso configuracion_admision_ingreso = new Configuracion_admision_ingreso();
            configuracion_admision_ingreso.setCodigo_empresa(codigo_empresa);
            configuracion_admision_ingreso.setCodigo_sucursal(codigo_sucursal);
            configuracion_admision_ingreso.setVia_ingreso(admision
                    .getVia_ingreso());

            configuracion_admision_ingreso = getServiceLocator().getServicio(
                    Configuracion_admision_ingresoService.class).consultar(
                            configuracion_admision_ingreso);

            if (configuracion_admision_ingreso == null) {
                throw new ValidacionRunTimeException(
                        "La via de ingreso "
                        + admision.getVia_ingreso()
                        + " no tiene una configuracion creada. Por favor crear la configuracion para que esta via de ingreso pueda operar de forma correcta");
            }

            if (tbxPyp_plan.getValue().equals("S")
                    && configuracion_admision_ingreso.getEs_pyp()
                    && !admision.getVia_ingreso().equals(
                            IVias_ingreso.HIPERTENSO_DIABETICO)) {
                if (admision.getVia_ingreso().equals(
                        IVias_ingreso.VIA_VACUNACION)) {
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("codigo_empresa", admision.getCodigo_empresa());
                    param.put("codigo_sucursal", admision.getCodigo_sucursal());
                    param.put("codigo_cups", tbxCodigo_consulta.getValue());
                    boolean vacuna_registrada = getServiceLocator()
                            .getServicio(VacunasService.class).existe(param);
                    if (!vacuna_registrada) {
                        mensaje = "El procedimiento y finalidad de consulta no se encuntra en el programa pyp ";
                        valida = false;
                    }
                } else {
                    // Modificado por las actividades tiene mas de 1 código
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("codigo_pdc_like", tbxCodigo_consulta.getValue());
                    param.put("area_intervencion", lbxFinalidad_consulta
                            .getSelectedItem().getValue().toString());
                    boolean existe = getServiceLocator().getServicio(
                            Plantilla_pypService.class).existe(param);
                    if (!existe) {
                        mensaje = "El procedimiento y finalidad de consulta no se encuntra en el programa pyp";
                        valida = false;
                    }
                }
            }

            if (!valida) {
                Messagebox.show(mensaje, usuarios.getNombres()
                        + " recuerde que...", Messagebox.OK,
                        Messagebox.EXCLAMATION);
            }

        }

        return valida;
    }

    // Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String parameter = lbxParameter.getSelectedItem().getValue()
                    .toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", codigo_empresa);
            parameters.put("codigo_sucursal", codigo_sucursal);
            if (!value.isEmpty()) {
                parameters.put("parameter", parameter);
                parameters.put("value", "%" + value + "%");
            }

            List<String> listado_centros = new ArrayList<String>();
            if (!lbxCentros_atencion.getSelectedItems().isEmpty()) {
                for (Listitem listitem : lbxCentros_atencion.getSelectedItems()) {
                    Centro_atencion centro_atencion = (Centro_atencion) listitem
                            .getValue();
                    listado_centros.add(centro_atencion.getCodigo_centro());
                }
                btnFiltro_centros.setImage("/images/filtro1.png");
                parameters.put("listado_centros", listado_centros);
            } else {
                btnFiltro_centros.setImage("/images/filtro.png");
            }

            resultadoPaginadoMacro.buscarDatos(parameters);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    /**
     * @param objeto
     * @param componente
     * @return
     * @throws Exception
     */
    public Row crearFilas(Object objeto, Component componente) throws Exception {
        Row fila = new Row();

        final Datos_consulta datos_consulta = (Datos_consulta) objeto;

        Paciente paciente = datos_consulta.getPaciente();
        String nombres_paciente = (paciente != null ? paciente.getNombre1()
                + " " + paciente.getNombre2() : "");
        String apellidos_paciente = (paciente != null ? paciente.getApellido1()
                + " " + paciente.getApellido2() : "");

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(datos_consulta.getCodigo_registro() + ""));
        fila.appendChild(new Label(datos_consulta.getTipo_identificacion() + ""));
        fila.appendChild(new Label(datos_consulta.getNro_identificacion() + ""));
        fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                .format(datos_consulta.getFecha_consulta()) + ""));
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
                mostrarDatos(datos_consulta);
            }
        });
        hbox.appendChild(img);

        fila.appendChild(hbox);

        return fila;
    }

    // Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxForm);
            if (validarForm()) {
				// Cargamos los componentes //

                // //log.info("listboxProcedimientos"+listboxProcedimientos.getSelectedItem().getValue().toString());
                if (listboxProcedimientos.getSelectedItem() != null) {
                    Map<String, Object> mapa_datos_aux = new HashMap<String, Object>();
                    // mapa_datos_aux.put("codigo_consulta", codigo_consulta);
                    mapa_datos_aux.put("finalidad_consulta",
                            lbxFinalidad_consulta.getSelectedItem().getValue()
                            .toString());
                    mapa_datos_aux.put("causa_externa", lbxCausa_externa
                            .getSelectedItem().getValue().toString());
                    mapa_datos_aux.put("tipo_diagnostico", lbxTipo_diagnostico
                            .getSelectedItem().getValue().toString());
                    mapa_datos_aux.put("diagnostico_principal",
                            tbxCodigo_diagnostico_principal.getValue());
                    mapa_datos_aux.put("diagnostico1",
                            tbxCodigo_diagnostico1.getValue());
                    mapa_datos_aux.put("diagnostico2",
                            tbxCodigo_diagnostico2.getValue());
                    mapa_datos_aux.put("diagnostico3",
                            tbxCodigo_diagnostico3.getValue());

                    mapa_datos_aux.put("valor_consulta",
                            dbxValor_consulta.getValue());
                    mapa_datos_aux
                            .put("valor_cuota", dbxValor_cuota.getValue());
                    mapa_datos_aux.put("valor_neto", dbxValor_neto.getValue());
                    listboxProcedimientos.getSelectedItem().setAttribute(
                            "detalle", mapa_datos_aux);
                }

                List<Datos_consulta> lista_consulta = new ArrayList<Datos_consulta>();

				// //log.info("listboxProcedimientos2"+listboxProcedimientos.getItems());
                for (Listitem listitem : listboxProcedimientos.getItems()) {

                    String codigo_consulta = listitem.getValue().toString();

                    Map<String, Object> datos_guardar = (Map<String, Object>) listitem
                            .getAttribute("detalle");

                    Double descuento = (Double) listitem
                            .getAttribute("descuento");
                    Double incremento = (Double) listitem
                            .getAttribute("incremento");
                    Double valor_real = (Double) listitem
                            .getAttribute("valor_real");

					// Datos_consulta datos_consulta = getDatos_consultas();
                    Admision admision = ((Admision) lbxNro_ingreso
                            .getSelectedItem().getValue());

					// log.info("tbxNro_factura.getValue()"
                    // + tbxNro_factura.getValue());
                    Datos_consulta datos_consulta = new Datos_consulta();
                    datos_consulta.setCodigo_empresa(empresa
                            .getCodigo_empresa());
                    datos_consulta.setCodigo_sucursal(sucursal
                            .getCodigo_sucursal());
                    datos_consulta.setCodigo_registro(Utilidades
                            .getValorLong(tbxNro_factura.getValue().trim()));
                    datos_consulta = getServiceLocator()
                            .getDatos_consultaService().consultar(
                                    datos_consulta);
                    // log.info("datos_consulta" + datos_consulta);

                    if (datos_consulta == null) {
                        datos_consulta = new Datos_consulta();
                        datos_consulta.setCodigo_empresa(empresa
                                .getCodigo_empresa());
                        datos_consulta.setCodigo_sucursal(sucursal
                                .getCodigo_sucursal());
                        datos_consulta
                                .setCodigo_registro(Utilidades
                                        .getValorLong(tbxNro_factura.getValue()
                                                .trim()));
                    }
                    datos_consulta
                            .setTipo_identificacion(tbxTipo_identificacion
                                    .getValue());
                    datos_consulta.setNro_identificacion(tbxNro_identificacion
                            .getValue());
                    datos_consulta
                            .setCodigo_administradora(tbxCodigo_administradora
                                    .getValue());
                    datos_consulta.setId_plan(tbxId_plan.getValue());
                    datos_consulta.setCodigo_prestador(tbxCodigo_prestador
                            .getValue());
                    datos_consulta.setNro_ingreso(admision.getNro_ingreso());
                    datos_consulta.setCodigo_consulta(codigo_consulta);
                    datos_consulta.setFecha_consulta(new Timestamp(
                            dtbxFecha_consulta.getValue().getTime()));
                    datos_consulta
                            .setNumero_autorizacion(tbxNumero_autorizacion
                                    .getValue());
                    datos_consulta.setValor_consulta((Double) datos_guardar
                            .get("valor_consulta"));
                    datos_consulta.setValor_cuota((Double) datos_guardar
                            .get("valor_cuota"));
                    datos_consulta.setValor_neto((Double) datos_guardar
                            .get("valor_neto"));

                    datos_consulta.setFinalidad_consulta((String) datos_guardar
                            .get("finalidad_consulta"));
                    datos_consulta.setCausa_externa((String) datos_guardar
                            .get("causa_externa"));
                    datos_consulta.setTipo_diagnostico((String) datos_guardar
                            .get("tipo_diagnostico"));
                    datos_consulta
                            .setCodigo_diagnostico_principal((String) datos_guardar
                                    .get("diagnostico_principal"));
                    datos_consulta
                            .setCodigo_diagnostico1((String) datos_guardar
                                    .get("diagnostico1"));
                    datos_consulta
                            .setCodigo_diagnostico2((String) datos_guardar
                                    .get("diagnostico2"));
                    datos_consulta
                            .setCodigo_diagnostico3((String) datos_guardar
                                    .get("diagnostico3"));

                    datos_consulta.setCancelo_copago("N");

                    datos_consulta.setCreacion_date(new Timestamp(
                            new GregorianCalendar().getTimeInMillis()));
                    datos_consulta.setUltimo_update(new Timestamp(
                            new GregorianCalendar().getTimeInMillis()));
                    datos_consulta.setCreacion_user(usuarios.getCodigo()
                            .toString());
                    datos_consulta.setUltimo_user(usuarios.getCodigo()
                            .toString());
                    datos_consulta.setValor_real(valor_real);
                    datos_consulta.setDescuento(descuento);
                    datos_consulta.setIncremento(incremento);
                    lista_consulta.add(datos_consulta);
                    // log.info("=====> datos_consulta " + lista_consulta);

                }

                Map<String, Object> datos = new HashMap<String, Object>();
                datos.put("lista_consulta", lista_consulta);
                datos.put("accion", tbxAccion.getValue());

				// log.info("datos" + datos);
                getServiceLocator().getDatos_consultaService().guardar(datos);

                tbxAccion.setValue("modificar");
                // tbxNro_factura.setValue(datos_consulta.getNro_factura());

                Messagebox
                        .show("Los datos se guardaron satisfactoriamente",
                                "Informacion ..", Messagebox.OK,
                                Messagebox.INFORMATION);

            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    /*
     * public void guardarDatosLote() throws Exception { if (lista_datos.size()
     * <= 0) { Messagebox
     * .show("Debe adicionar al menos un registro de consulta a la grilla",
     * usuarios.getNombres() + " recuerde que...", Messagebox.OK,
     * Messagebox.EXCLAMATION); return; }
     * 
     * getServiceLocator().getDatos_consultaService().crearLote(lista_datos);
     * accionForm(true, "registrar");
     * 
     * Messagebox.show("Los datos se guardaron satisfactoriamente",
     * "Informacion ..", Messagebox.OK, Messagebox.INFORMATION); }
     */
    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Datos_consulta datos_consulta = (Datos_consulta) obj;
        try {
            datos_consulta = getServiceLocator().getDatos_consultaService()
                    .consultar(datos_consulta);
            // log.info("---------" + datos_consulta);
            deshabilitarCampos(false);

            tbxNro_factura.setValue(datos_consulta.getCodigo_registro() + "");
            // log.info("---------" + datos_consulta.getNro_factura());

            tbxTipo_identificacion.setValue(datos_consulta
                    .getTipo_identificacion());
            tbxNro_identificacion.setValue(datos_consulta
                    .getNro_identificacion());

            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(datos_consulta.getCodigo_empresa());
            paciente.setCodigo_sucursal(datos_consulta.getCodigo_sucursal());
            paciente.setNro_identificacion(datos_consulta
                    .getNro_identificacion());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);
            tbxNomPaciente.setValue((paciente != null ? paciente
                    .getNombreCompleto() : ""));
            tbxSexo_pct.setValue((paciente != null ? paciente.getSexo() : ""));
            tbxFecha_nac.setValue((paciente != null ? new SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()) : ""));

            tbxCodigo_administradora.setValue(datos_consulta
                    .getCodigo_administradora());
            tbxId_plan.setValue(datos_consulta.getId_plan());

            Administradora administradora = new Administradora();
            administradora
                    .setCodigo_empresa(datos_consulta.getCodigo_empresa());
            administradora.setCodigo_sucursal(datos_consulta
                    .getCodigo_sucursal());
            administradora.setCodigo(datos_consulta.getCodigo_administradora());
            administradora = getServiceLocator().getAdministradoraService()
                    .consultar(administradora);

            Admision admision = new Admision();
            admision.setCodigo_empresa(datos_consulta.getCodigo_empresa());
            admision.setCodigo_sucursal(datos_consulta.getCodigo_sucursal());
            admision.setCodigo_cita(datos_consulta.getCodigo_cita());
            admision.setNro_identificacion(datos_consulta
                    .getNro_identificacion());
            admision.setNro_ingreso(datos_consulta.getNro_ingreso());
            admision = getServiceLocator().getServicio(AdmisionService.class)
                    .consultar(admision);
            // log.info("admision ---> " + admision);

            Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                    .getManuales_tarifarios(admision);
            String[] unidad_funcional = ServiciosDisponiblesUtils
                    .getServicios(admision);

            Contratos contratos = new Contratos();
            contratos.setCodigo_empresa(datos_consulta.getCodigo_empresa());
            contratos.setCodigo_sucursal(datos_consulta.getCodigo_sucursal());
            contratos.setCodigo_administradora(datos_consulta
                    .getCodigo_administradora());
            contratos.setId_plan(manuales_tarifarios.getId_contrato());
            contratos = getServiceLocator().getContratosService().consultar(
                    contratos);

            tbxAseguradora.setValue((administradora != null ? administradora
                    .getNombre() : "")
                    + " - "
                    + (contratos != null ? contratos.getNombre() : ""));

            this.manuales_tarifarios = manuales_tarifarios;
			// tbxManual.setValue((contratos != null ?
            // manuales_tarifarios.getManual_tarifario()
            // : ""));
            tbxPyp_plan
                    .setValue((unidad_funcional != null ? (ServiciosDisponiblesUtils
                            .isServicioPyp(unidad_funcional) ? "S" : "N") : "N"));
            tbxAnio.setValue((contratos != null ? manuales_tarifarios.getAnio()
                    : ""));

            tbxCodigo_prestador.setValue(datos_consulta.getCodigo_prestador());
            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(datos_consulta.getCodigo_empresa());
            prestadores.setCodigo_sucursal(datos_consulta.getCodigo_sucursal());
            prestadores.setNro_identificacion(datos_consulta
                    .getCodigo_prestador());
            prestadores = getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);
            tbxNomMedico.setValue((prestadores != null ? prestadores
                    .getNombres() + " " + prestadores.getApellidos() : ""));

            listarIngresos(lbxNro_ingreso,
                    listarAdmisiones(datos_consulta, true), false);
            for (int i = 0; i < lbxNro_ingreso.getItemCount(); i++) {
                Listitem listitem = lbxNro_ingreso.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(datos_consulta.getNro_ingreso())) {
                    listitem.setSelected(true);
                    i = lbxNro_ingreso.getItemCount();
                }
            }
            tbxCodigo_consulta.setValue(datos_consulta.getCodigo_consulta());

            Map<String, Object> bean = Utilidades.getProcedimiento(
                    manuales_tarifarios,
                    new Long(datos_consulta.getCodigo_consulta()),
                    getServiceLocator());

            tbxNomPcd.setValue((String) bean.get("nombre_procedimiento"));
            tbxSexo_pcd.setValue((String) bean.get("sexo_pcd"));
            tbxLimite_inferior_pcd.setValue((String) bean
                    .get("limite_inferior_pcd"));
            tbxLimite_superior_pcd.setValue((String) bean
                    .get("limite_superior_pcd"));
            tbxEs_pyp.setValue((String) bean.get("es_pyp"));
            dbxValor_real.setValue(datos_consulta.getValor_real());
            dbxDescuento.setValue(datos_consulta.getDescuento());
            dbxIncremento.setValue(datos_consulta.getIncremento());

            dtbxFecha_consulta.setValue(datos_consulta.getFecha_consulta());
            tbxNumero_autorizacion.setValue(datos_consulta
                    .getNumero_autorizacion());
            dbxValor_consulta.setValue(datos_consulta.getValor_consulta());
            dbxValor_cuota.setValue(datos_consulta.getValor_cuota());
            dbxValor_neto.setValue(datos_consulta.getValor_neto());
            for (int i = 0; i < lbxFinalidad_consulta.getItemCount(); i++) {
                Listitem listitem = lbxFinalidad_consulta.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(datos_consulta.getFinalidad_consulta())) {
                    listitem.setSelected(true);
                    i = lbxFinalidad_consulta.getItemCount();
                }
            }
            for (int i = 0; i < lbxCausa_externa.getItemCount(); i++) {
                Listitem listitem = lbxCausa_externa.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(datos_consulta.getCausa_externa())) {
                    listitem.setSelected(true);
                    i = lbxCausa_externa.getItemCount();
                }
            }
            for (int i = 0; i < lbxTipo_diagnostico.getItemCount(); i++) {
                Listitem listitem = lbxTipo_diagnostico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(datos_consulta.getTipo_diagnostico())) {
                    listitem.setSelected(true);
                    i = lbxTipo_diagnostico.getItemCount();
                }
            }
            tbxCodigo_diagnostico_principal.setValue(datos_consulta
                    .getCodigo_diagnostico_principal());
            Cie cie = new Cie();
            cie.setCodigo(datos_consulta.getCodigo_diagnostico_principal());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            tbxNomDxpp.setValue((cie != null ? cie.getNombre() : ""));
            tbxSexo_dxpp.setValue((cie != null ? cie.getSexo() : ""));
            tbxLimite_inferior_dxpp.setValue((cie != null ? cie
                    .getLimite_inferior() : ""));
            tbxLimite_superior_dxpp.setValue((cie != null ? cie
                    .getLimite_superior() : ""));

            tbxCodigo_diagnostico1.setValue(datos_consulta
                    .getCodigo_diagnostico1());
            cie = new Cie();
            cie.setCodigo(datos_consulta.getCodigo_diagnostico1());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            tbxNomDx1.setValue((cie != null ? cie.getNombre() : ""));
            tbxSexo_dx1.setValue((cie != null ? cie.getSexo() : ""));
            tbxLimite_inferior_dx1.setValue((cie != null ? cie
                    .getLimite_inferior() : ""));
            tbxLimite_superior_dx1.setValue((cie != null ? cie
                    .getLimite_superior() : ""));

            tbxCodigo_diagnostico2.setValue(datos_consulta
                    .getCodigo_diagnostico2());
            cie = new Cie();
            cie.setCodigo(datos_consulta.getCodigo_diagnostico2());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            tbxNomDx2.setValue((cie != null ? cie.getNombre() : ""));
            tbxSexo_dx2.setValue((cie != null ? cie.getSexo() : ""));
            tbxLimite_inferior_dx2.setValue((cie != null ? cie
                    .getLimite_inferior() : ""));
            tbxLimite_superior_dx2.setValue((cie != null ? cie
                    .getLimite_superior() : ""));

            tbxCodigo_diagnostico3.setValue(datos_consulta
                    .getCodigo_diagnostico3());
            cie = new Cie();
            cie.setCodigo(datos_consulta.getCodigo_diagnostico3());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            tbxNomDx3.setValue((cie != null ? cie.getNombre() : ""));
            tbxSexo_dx3.setValue((cie != null ? cie.getSexo() : ""));
            tbxLimite_inferior_dx3.setValue((cie != null ? cie
                    .getLimite_inferior() : ""));
            tbxLimite_superior_dx3.setValue((cie != null ? cie
                    .getLimite_superior() : ""));

            imgQuitar_pct.setVisible(false);
            tbxNro_identificacion.setDisabled(true);

            if (parametros_empresa
                    .getPermitir_cambiar_diagnosticos_datos_consulta().equals(
                            "N")) {
                validarRegistroEditar(datos_consulta);
            } else {
                tbxCodigo_diagnostico_principal.setDisabled(false);
                tbxCodigo_diagnostico1.setDisabled(false);
                tbxCodigo_diagnostico2.setDisabled(false);
                tbxCodigo_diagnostico3.setDisabled(false);
                tbxCodigo_diagnostico_principal.setReadonly(true);
                tbxCodigo_diagnostico1.setReadonly(true);
                tbxCodigo_diagnostico2.setReadonly(true);
                tbxCodigo_diagnostico3.setReadonly(true);
            }

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
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

    public void eliminarDatos(Object obj) throws Exception {
        Datos_consulta datos_consulta = (Datos_consulta) obj;
        try {
            int result = getServiceLocator().getDatos_consultaService()
                    .eliminarActualizarFactura(datos_consulta);
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

    public void buscarPaciente(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("codigo_empresa", empresa.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");

            parameters.put("limite_paginado", "limit 25 offset 0");

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

    public List<Admision> listarAdmisiones(Datos_consulta datos_consulta,
            boolean sw) throws Exception {
        Map paramAdmision = new HashMap();
        paramAdmision.put("codigo_empresa", datos_consulta.getCodigo_empresa());
        paramAdmision.put("codigo_sucursal",
                datos_consulta.getCodigo_sucursal());
        paramAdmision.put("nro_identificacion",
                datos_consulta.getNro_identificacion());
        paramAdmision.put("order", "fecha_ingreso desc");
        if (sw) {
            paramAdmision.put("nro_ingreso", datos_consulta.getNro_ingreso());
        } else {
            paramAdmision.put("estado", "1");
        }

        List<Admision> listaAdmisiones = getServiceLocator().getServicio(
                AdmisionService.class).listarTabla(paramAdmision);

        return listaAdmisiones;
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

    public void cargarAdmisiones(Admision aux2) throws Exception {

        if (aux2 != null) {
            deshabilitarCampos(false);

            Administradora admin = new Administradora();
            admin.setCodigo_empresa(aux2.getCodigo_empresa());
            admin.setCodigo_sucursal(aux2.getCodigo_sucursal());
            admin.setCodigo(aux2.getCodigo_administradora());
            admin = getServiceLocator().getAdministradoraService().consultar(
                    admin);

            Prestadores prestadores = new Prestadores();
            prestadores.setCodigo_empresa(empresa.getCodigo_empresa());
            prestadores.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            prestadores.setNro_identificacion(aux2.getCodigo_medico());
            prestadores = getServiceLocator().getPrestadoresService()
                    .consultar(prestadores);

            Cie cie = new Cie();
            cie.setCodigo(aux2.getDiagnostico_ingreso());
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

            Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                    .getManuales_tarifarios(aux2);
            String[] unidad_funcional = ServiciosDisponiblesUtils
                    .getServicios(aux2);

            if (manuales_tarifarios != null) {
                Contratos plan = new Contratos();
                plan.setCodigo_empresa(empresa.getCodigo_empresa());
                plan.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                plan.setCodigo_administradora(aux2.getCodigo_administradora());
                plan.setId_plan(manuales_tarifarios.getId_contrato());
                plan = getServiceLocator().getContratosService()
                        .consultar(plan);

                String administradora = "";
                administradora = (admin != null ? admin.getNombre() : "");
                administradora += " - ";
                administradora += (plan != null ? plan.getNombre() : "");
				// String manual = (plan != null ?
                // manuales_tarifarios.getManual_tarifario() : "");
                String pyp = (unidad_funcional != null ? (ServiciosDisponiblesUtils
                        .isServicioPyp(unidad_funcional) ? "S" : "N") : "N");
                String anio = (plan != null ? manuales_tarifarios.getAnio()
                        : "");

                tbxCodigo_prestador.setValue(aux2.getCodigo_medico());
                tbxNomMedico.setValue((prestadores != null ? prestadores
                        .getNombres() + " " + prestadores.getApellidos() : ""));

                tbxCodigo_diagnostico_principal.setValue("");
                tbxNomDxpp.setValue("");
                tbxSexo_dxpp.setValue("");
                tbxLimite_inferior_dxpp.setValue("");
                tbxLimite_superior_dxpp.setValue("");

                tbxAseguradora.setValue(administradora);
                tbxCodigo_administradora.setValue(aux2
                        .getCodigo_administradora());
                tbxId_plan.setValue(aux2.getId_plan());
                tbxNumero_autorizacion
                        .setValue(aux2.getNro_autorizacion() != null ? aux2
                                .getNro_autorizacion() : "");

                this.manuales_tarifarios = manuales_tarifarios;
                tbxAnio.setValue(anio);
                tbxPyp_plan.setValue(pyp);
            }
            dtbxFecha_consulta.setValue(aux2.getFecha_ingreso());
			// log.info("@Motrar dtbxFecha_consulta ===> "
            // + dtbxFecha_consulta.getValue());
        }
    }

    public void borrarAdmision() throws Exception {
        limpiarDatos();
        tbxNro_identificacion.setValue("");
        tbxNomPaciente.setValue("");
        tbxSexo_pct.setValue("");
        tbxFecha_nac.setValue("");
        tbxTipo_identificacion.setValue("");
        listarIngresos(lbxNro_ingreso, new LinkedList<Admision>(), true);
        deshabilitarCampos(true);
    }

    public void selectedPaciente(Listitem listitem) throws Exception {
        if (listitem.getValue() == null) {
            borrarAdmision();
        } else {
            Paciente dato = (Paciente) listitem.getValue();

            Datos_consulta datos_consulta = new Datos_consulta();
            datos_consulta.setCodigo_empresa(dato.getCodigo_empresa());
            datos_consulta.setCodigo_sucursal(dato.getCodigo_sucursal());
            datos_consulta.setNro_identificacion(dato.getNro_identificacion());
            List<Admision> listaAdmisiones = listarAdmisiones(datos_consulta,
                    false);
            if (listaAdmisiones.isEmpty()) {
                Messagebox.show("No se ha registrado el Ingreso del Paciente",
                        "Paciente no admisionado", Messagebox.OK,
                        Messagebox.EXCLAMATION);
                limpiarDatos();
                deshabilitarCampos(true);
                return;
            }
            tbxNro_identificacion.close();
            tbxNro_identificacion.setValue(dato.getNro_identificacion());
            tbxNomPaciente.setValue(dato.getNombreCompleto());
            tbxSexo_pct.setValue(dato.getSexo());
            tbxTipo_identificacion.setValue(dato.getTipo_identificacion());
            tbxFecha_nac.setValue(new java.text.SimpleDateFormat("dd/MM/yyyy")
                    .format(dato.getFecha_nacimiento()));
            deshabilitarCampos(false);

            imgQuitar_pct.setVisible(true);

            listarIngresos(lbxNro_ingreso, listaAdmisiones, false);
            Admision aux2 = (!listaAdmisiones.isEmpty() ? listaAdmisiones
                    .get(0) : null);
            cargarAdmisiones(aux2);

        }
    }

    public void selectedAdmision(Listitem listitem) throws Exception {
        Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
                .getValue());

        if (admision == null) {
            borrarAdmision();
        } else {
            cargarAdmisiones(admision);
        }
    }

    public boolean validarDiagnostico() {

        boolean valida = true;

        String mensaje = "El campo Diag Ppal es obligatorio para agregar registros";

        if (tbxCodigo_diagnostico_principal.getText().trim().equals("")) {
            MensajesUtil.notificarAlerta(mensaje,
                    tbxCodigo_diagnostico_principal);
            valida = false;
        }

        return valida;

    }

    public void openPcd() throws Exception {

        if (validarDiagnostico()) {

            Listitem listitem = lbxNro_ingreso.getSelectedItem();
            if (listitem != null && listitem.getValue() != null) {

                Admision admision = listitem.getValue();
                Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                        .getManuales_tarifarios(admision);

                if (manuales_tarifarios == null) {
                    MensajesUtil
                            .mensajeAlerta("Advertencia",
                                    "Este paciente no tiene permiso para brindarle este servicio");
                } else {
                    String pages = "";
                    String anio = tbxAnio.getValue();

                    pages = "/pages/openProcedimientos.zul";

                    Map parametros = new HashMap();
                    parametros.put("codigo_empresa",
                            empresa.getCodigo_empresa());
                    parametros.put("codigo_sucursal",
                            sucursal.getCodigo_sucursal());
                    parametros.put("codigo_administradora",
                            tbxCodigo_administradora.getValue());
                    parametros.put("id_plan", tbxId_plan.getValue());
                    parametros.put("restringido", "");
                    parametros
                            .put("nro_ingreso",
                                    (admision != null ? admision
                                    .getNro_ingreso() : ""));
                    parametros.put("nro_identificacion",
                            tbxNro_identificacion.getValue());
                    parametros.put("anio", anio);
                    parametros.put("seleccionados", consultaSeleccionados);
                    parametros.put("consulta", "S");
                    parametros
                            .put("via_ingreso",
                                    admision != null ? admision
                                    .getVia_ingreso() : null);

                    parametros.put("ocultar_filtro_procedimiento",
                            "ocultar_filtro_procedimiento");

                    if (tbxPyp_plan.getValue().equals("S")) {
                        parametros.put("pyp", tbxPyp_plan.getValue());
                    }

                    Component componente = Executions.createComponents(pages,
                            this, parametros);
                    final Window ventana = (Window) componente;
                    ventana.setWidth("990px");
                    ventana.setHeight("400px");
                    ventana.setClosable(true);
                    ventana.setMaximizable(true);
                    ventana.setTitle("CONSULTAR PROCEDIMIENTOS HABILITADOS ");
                    ventana.setMode("modal");
                }
            }
        }
    }

    public void seleccionarProcedimientoAutomatico() {
        Listitem listitem = lbxNro_ingreso.getSelectedItem();
        try {
            if (listitem != null && listitem.getValue() != null) {

                Admision admision = listitem.getValue();
                Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                        .getManuales_tarifarios(admision);
                Via_ingreso_consultas via_ingreso_consultas = ServiciosDisponiblesUtils
                        .getVia_ingreso_consultas(admision);

                if (manuales_tarifarios == null
                        || via_ingreso_consultas == null) {
                    MensajesUtil
                            .mensajeAlerta("Advertencia",
                                    "Este paciente no tiene permiso para brindarle este servicio");
                } else {
                    String manual = manuales_tarifarios != null ? manuales_tarifarios
                            .getMaestro_manual().getTipo_manual() : "";
                    // String anio = tbxAnio.getValue();

                    String codigo_procedimiento = admision.getPrimera_vez()
                            .equals("S") ? via_ingreso_consultas
                            .getPro_consulta_primera() : via_ingreso_consultas
                            .getPro_consulta_control();
                    if (manual.equals(IConstantes.TIPO_MANUAL_SOAT)) {
                        codigo_procedimiento = admision.getPrimera_vez()
                                .equals("S") ? via_ingreso_consultas
                                .getPro_consulta_primera()
                                : via_ingreso_consultas
                                .getPro_consulta_control();
                    }
                    Utilidades.getProcedimiento(manuales_tarifarios, new Long(
                            codigo_procedimiento), getServiceLocator());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void adicionarPcd(Map<String, Object> pcd) throws Exception {

        final String codigo_consulta = pcd.get("codigo_procedimiento")
                .toString();
        consultaSeleccionados.add(codigo_consulta);

        lista_consultas.add(pcd);
        mostrarInformacion();

    }

    public void mostrarInformacion() {

        listboxProcedimientos.getItems().clear();

        for (final Map mapa_datos : lista_consultas) {

            final Listitem listitem = new Listitem();
            listitem.setValue(mapa_datos.get("codigo_procedimiento"));

            listitem.appendChild(new Listcell());

            Listcell listcell = new Listcell();
            Textbox textbox = new Textbox(mapa_datos
                    .get("codigo_procedimiento").toString());
            textbox.setInplace(true);
            textbox.setWidth("95%");
            textbox.setReadonly(true);
            listcell.appendChild(textbox);
            listitem.appendChild(listcell);

            listcell = new Listcell();
            textbox = new Textbox(mapa_datos.get("nombre_procedimiento")
                    .toString());
            textbox.setInplace(true);
            textbox.setWidth("95%");
            textbox.setReadonly(true);
            listcell.appendChild(textbox);
            listitem.appendChild(listcell);

            listcell = new Listcell();
            Datebox datebox = new Datebox(dtbxFecha_consulta.getValue());
            datebox.setInplace(true);
            datebox.setWidth("95%");
            datebox.setReadonly(true);
            datebox.setButtonVisible(false);
            listcell.appendChild(datebox);
            listitem.appendChild(listcell);

            listcell = new Listcell();
            textbox = new Textbox(tbxCodigo_diagnostico_principal.getValue());
            textbox.setInplace(true);
            textbox.setWidth("95%");
            textbox.setReadonly(true);
            listcell.appendChild(textbox);
            listitem.appendChild(listcell);

            listitem.setAttribute("valor_total", mapa_datos.get("valor_total"));
            listitem.setAttribute("descuento", mapa_datos.get("descuento"));
            listitem.setAttribute("incremento", mapa_datos.get("incremento"));
            listitem.setAttribute("valor_real", mapa_datos.get("valor_real"));
            listitem.setAttribute("valor_real",
                    mapa_datos.get("valor_procedimiento"));

            mapa_datos.put("codigo_consulta",
                    mapa_datos.get("codigo_procedimiento"));
            mapa_datos.put("finalidad_consulta", lbxFinalidad_consulta
                    .getSelectedItem().getValue().toString());
            mapa_datos.put("causa_externa", lbxCausa_externa.getSelectedItem()
                    .getValue().toString());
            mapa_datos.put("tipo_diagnostico", lbxTipo_diagnostico
                    .getSelectedItem().getValue().toString());
            mapa_datos.put("diagnostico_principal",
                    tbxCodigo_diagnostico_principal.getValue());
            mapa_datos.put("diagnostico1", tbxCodigo_diagnostico1.getValue());
            mapa_datos.put("diagnostico2", tbxCodigo_diagnostico2.getValue());
            mapa_datos.put("diagnostico3", tbxCodigo_diagnostico3.getValue());

            mapa_datos.put("valor_consulta",
                    mapa_datos.get("valor_procedimiento"));
            mapa_datos.put("valor_cuota", dbxValor_cuota.getValue());
            mapa_datos.put("valor_neto", mapa_datos.get("valor_procedimiento"));

            listitem.setAttribute("detalle", mapa_datos);

            final Image image = new Image("/images/borrar.gif");
            image.setStyle("cursor:pointer");
            image.addEventListener(Events.ON_CLICK, new EventListener<Event>() {

                @Override
                public void onEvent(Event event) throws Exception {
                    consultaSeleccionados.remove(mapa_datos.get(
                            "codigo_procedimiento").toString());
                    listitem.detach();
                }

            });

            listcell = new Listcell();
            listcell.appendChild(image);
            listitem.appendChild(listcell);

            listboxProcedimientos.appendChild(listitem);
        }
    }

    public void onSeleccionarListboxProcedimientos() {
		// Listitem listitem = listboxProcedimientos.getSelectedItem();
        // String codigo_consulta = listitem.getValue().toString();
        //
        // try {
        // if (listitem_current != null) {
        //
        //
        // Map<String, Object> mapa_datos_aux = new HashMap<String, Object>();
        // mapa_datos_aux.put("codigo_consulta", codigo_consulta);
        // mapa_datos_aux.put("finalidad_consulta",
        // lbxFinalidad_consulta.getSelectedItem()
        // .getValue().toString());
        // mapa_datos_aux.put("causa_externa", lbxCausa_externa
        // .getSelectedItem().getValue().toString());
        // mapa_datos_aux.put("tipo_diagnostico",
        // lbxTipo_diagnostico.getSelectedItem()
        // .getValue().toString());
        // mapa_datos_aux.put("diagnostico_principal",
        // tbxCodigo_diagnostico_principal.getValue());
        // mapa_datos_aux.put("diagnostico1",
        // tbxCodigo_diagnostico1.getValue());
        // mapa_datos_aux.put("diagnostico2",
        // tbxCodigo_diagnostico2.getValue());
        // mapa_datos_aux.put("diagnostico3",
        // tbxCodigo_diagnostico3.getValue());
        //
        // mapa_datos_aux.put("valor_consulta",dbxValor_consulta.getValue());
        // mapa_datos_aux.put("valor_cuota", dbxValor_cuota.getValue());
        // mapa_datos_aux.put("valor_neto", dbxValor_neto.getValue());
        // listitem_current
        // .setAttribute("detalle", mapa_datos_aux);
        // }
        //
        // listboxProcedimientos.invalidate();
        //
        // if (listitem.hasAttribute("detalle"))
        //
        // editarDatos((Map<String, Object>) listitem
        // .getAttribute("detalle"));
        // else
        // editarDatos(new HashMap<String, Object>());
        // } catch (Exception e) {
        //
        // MensajesUtil.mensajeError(e, "", null);
        //
        // }
        //
        // listitem_current = listitem;
        //
        //

        int indice = listboxProcedimientos.getSelectedIndex();
        try {
            editarDatos(lista_consultas.get(indice));
        } catch (Exception e) {
            // block
            e.printStackTrace();
        }

    }

    public void buscarDx(String value, Listbox lbx) throws Exception {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");

            parameters.put("limite_paginado", "limit 25 offset 0");

            List<Cie> list = getServiceLocator().getServicio(GeneralExtraService.class).listar(Cie.class,
                    parameters);

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

    public void selectedDx(Listitem listitem, Bandbox tbxCodigoDX,
            Textbox tbxNombreDX, Textbox tbxSexoDX, Textbox tbxLimite_infDX,
            Textbox tbxLimite_supDX) throws Exception {
        if (listitem.getValue() == null) {
            tbxCodigoDX.setValue("");
            tbxNombreDX.setValue("");
            tbxSexoDX.setValue("");
            tbxLimite_infDX.setValue("");
            tbxLimite_supDX.setValue("");
        } else {
            Cie dato = (Cie) listitem.getValue();

            Map map = new HashMap();
            map.put("nombre_entidad", dato.getNombre());
            map.put("limite_inferior", dato.getLimite_inferior());
            map.put("limite_superior", dato.getLimite_superior());
            map.put("sexo_entidad", dato.getSexo());

            map.put("fecha_nac", tbxFecha_nac.getValue());
            map.put("sexo_pct", tbxSexo_pct.getValue());
            Map result = Utilidades.validarInformacionLimiteSexo("Diagnostico",
                    dato.getCodigo(), dato.getLimite_inferior(),
                    dato.getLimite_superior(), dato.getSexo(),
                    tbxFecha_nac.getValue(), tbxSexo_pct.getValue());
            if (!((Boolean) result.get("success"))) {
                throw new Exception((String) result.get("msg"));
            }

            tbxCodigoDX.setValue(dato.getCodigo());
            tbxNombreDX.setValue(dato.getNombre());

            tbxSexoDX.setValue(dato.getSexo());
            tbxLimite_infDX.setValue(dato.getLimite_inferior());
            tbxLimite_supDX.setValue(dato.getLimite_superior());
        }
        tbxCodigoDX.close();
    }

    private String getEstado_admision(Datos_consulta datos_consulta)
            throws Exception {
        String estado = "1";

        Admision admision = new Admision();
        admision.setCodigo_empresa(datos_consulta.getCodigo_empresa());
        admision.setCodigo_sucursal(datos_consulta.getCodigo_sucursal());
        admision.setNro_ingreso(datos_consulta.getNro_ingreso());
        admision.setNro_identificacion(datos_consulta.getNro_identificacion());
        admision = getServiceLocator().getServicio(AdmisionService.class)
                .consultar(admision);
        estado = (admision != null ? admision.getEstado() : "1");

        return estado;
    }

    private void validarRegistroEditar(Datos_consulta datos_consulta)
            throws Exception {
        String estado_admision = getEstado_admision(datos_consulta);
        if (this.getParent() instanceof Facturacion_ripsAction) {
            estado_admision = "1";
        }
        if (estado_admision.equals("2")) {
            bloqueoBotonGuardar(true);
        } else if (!datos_consulta.getTipo_hc().equals("")) {
            bloqueoBotonGuardar(true);
        } else if (datos_consulta.getCita_pyp()) {
            bloqueoBotonGuardar(true);
        } else {
            bloqueoBotonGuardar(false);
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
                                    // do the thing
                                    accionForm(true, "registrar");
                                }
                            }
                        });
    }

    public void guardarDatos_consulta() throws Exception {

        // log.info("tbxAccion.getValue()" + tbxAccion.getValue());
        if (!tbxAccion.getValue().equalsIgnoreCase("registrar")) {
            guardarDatos_editar();
        } else {
            guardarDatos();
        }

    }

    public void guardarDatos_editar() throws Exception {

        Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
                .getValue());

        Datos_consulta datos_consulta = new Datos_consulta();
        datos_consulta.setCodigo_empresa(empresa.getCodigo_empresa());
        datos_consulta.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        datos_consulta.setCodigo_registro(Utilidades
                .getValorLong(tbxNro_factura.getValue().trim()));
        datos_consulta
                .setTipo_identificacion(tbxTipo_identificacion.getValue());
        datos_consulta.setNro_identificacion(tbxNro_identificacion.getValue());
        datos_consulta.setCodigo_administradora(tbxCodigo_administradora
                .getValue());
        datos_consulta.setId_plan(tbxId_plan.getValue());
        datos_consulta.setCodigo_prestador(tbxCodigo_prestador.getValue());
        datos_consulta.setNro_ingreso(admision.getNro_ingreso());
        datos_consulta.setCodigo_consulta(tbxCodigo_consulta.getValue());
        datos_consulta.setFecha_consulta(new Timestamp(dtbxFecha_consulta
                .getValue().getTime()));
        datos_consulta
                .setNumero_autorizacion(tbxNumero_autorizacion.getValue());
        datos_consulta
                .setValor_consulta(dbxValor_consulta.getValue() != null ? dbxValor_consulta
                        .getValue() : 0.0);
        datos_consulta
                .setValor_cuota(dbxValor_cuota.getValue() != null ? dbxValor_cuota
                        .getValue() : 0.0);
        datos_consulta
                .setValor_neto(dbxValor_neto.getValue() != null ? dbxValor_neto
                        .getValue() : 0.0);

        datos_consulta.setFinalidad_consulta(lbxFinalidad_consulta
                .getSelectedItem().getValue().toString());
        datos_consulta.setCausa_externa(lbxCausa_externa.getSelectedItem()
                .getValue().toString());
        datos_consulta.setTipo_diagnostico(lbxTipo_diagnostico
                .getSelectedItem().getValue().toString());
        datos_consulta
                .setCodigo_diagnostico_principal(tbxCodigo_diagnostico_principal
                        .getValue());
        datos_consulta
                .setCodigo_diagnostico1(tbxCodigo_diagnostico1.getValue());
        datos_consulta
                .setCodigo_diagnostico2(tbxCodigo_diagnostico2.getValue());
        datos_consulta
                .setCodigo_diagnostico3(tbxCodigo_diagnostico3.getValue());

        datos_consulta.setCancelo_copago("N");

        datos_consulta.setCreacion_date(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        datos_consulta.setUltimo_update(new Timestamp(new GregorianCalendar()
                .getTimeInMillis()));
        datos_consulta.setCreacion_user(usuarios.getCodigo().toString());
        datos_consulta.setUltimo_user(usuarios.getCodigo().toString());
        datos_consulta
                .setValor_real(dbxValor_real.getValue() != null ? dbxValor_real
                        .getValue() : 0.0);
        datos_consulta
                .setDescuento(dbxDescuento.getValue() != null ? dbxDescuento
                        .getValue() : 0.0);
        datos_consulta
                .setIncremento(dbxIncremento.getValue() != null ? dbxIncremento
                        .getValue() : 0.0);
        List<Datos_consulta> lista_consulta = new ArrayList<Datos_consulta>();
        lista_consulta.add(datos_consulta);

        Map<String, Object> datos = new HashMap<String, Object>();
        datos.put("lista_consulta", lista_consulta);
        datos.put("accion", tbxAccion.getValue());

		// log.info("datos" + datos);
        getServiceLocator().getDatos_consultaService().guardar(datos);

        tbxAccion.setValue("modificar");
        // tbxNro_factura.setValue(datos_consulta.getNro_factura());

        Messagebox.show("Los datos se modificaron satisfactoriamente",
                "Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
    }

    public void editarDatos(Map<String, Object> mapa_datos) throws Exception {

        tbxCodigo_consulta.setValue((String) mapa_datos.get("codigo_consulta"));

        Map<String, Object> bean = Utilidades.getProcedimiento(
                manuales_tarifarios, new Long(tbxCodigo_consulta.getValue()),
                getServiceLocator());

        tbxNomPcd.setValue((String) bean.get("nombre_procedimiento"));
        tbxSexo_pcd.setValue((String) bean.get("sexo_pcd"));
        tbxLimite_inferior_pcd.setValue((String) bean
                .get("limite_inferior_pcd"));
        tbxLimite_superior_pcd.setValue((String) bean
                .get("limite_superior_pcd"));
        tbxEs_pyp.setValue((String) bean.get("es_pyp"));

        dbxValor_consulta.setValue((Double) mapa_datos.get("valor_consulta"));
        dbxValor_cuota.setValue((Double) mapa_datos.get("valor_cuota"));
        dbxValor_neto.setValue((Double) mapa_datos.get("valor_neto"));

        for (int i = 0; i < lbxFinalidad_consulta.getItemCount(); i++) {
            Listitem listitem2 = lbxFinalidad_consulta.getItemAtIndex(i);
            if (listitem2.getValue().toString()
                    .equals(mapa_datos.get("finalidad_consulta"))) {
                listitem2.setSelected(true);
                i = lbxFinalidad_consulta.getItemCount();
            }
        }

        for (int i = 0; i < lbxCausa_externa.getItemCount(); i++) {
            Listitem listitem2 = lbxCausa_externa.getItemAtIndex(i);
            if (listitem2.getValue().toString()
                    .equals(mapa_datos.get("causa_externa"))) {
                listitem2.setSelected(true);
                i = lbxCausa_externa.getItemCount();
            }
        }

        for (int i = 0; i < lbxTipo_diagnostico.getItemCount(); i++) {
            Listitem listitem2 = lbxTipo_diagnostico.getItemAtIndex(i);
            if (listitem2.getValue().toString()
                    .equals(mapa_datos.get("tipo_diagnostico"))) {
                listitem2.setSelected(true);
                i = lbxTipo_diagnostico.getItemCount();
            }
        }

        Cie cie = new Cie();
        cie.setCodigo((String) mapa_datos.get("diagnostico_principal"));
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

        if (cie != null) {
            tbxCodigo_diagnostico_principal.setValue((String) mapa_datos
                    .get("diagnostico_principal"));
            tbxNomDxpp.setValue(cie.getNombre());
        } else {
            tbxCodigo_diagnostico_principal.setValue("");
            tbxNomDxpp.setValue("");
        }

        cie = new Cie();
        cie.setCodigo((String) mapa_datos.get("diagnostico1"));
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

        if (cie != null) {
            tbxCodigo_diagnostico1.setValue((String) mapa_datos
                    .get("diagnostico1"));
            tbxNomDx1.setValue(cie.getNombre());
        } else {
            tbxCodigo_diagnostico1.setValue("");
            tbxNomDx1.setValue("");
        }

        cie = new Cie();
        cie.setCodigo((String) mapa_datos.get("diagnostico2"));
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

        if (cie != null) {
            tbxCodigo_diagnostico2.setValue((String) mapa_datos
                    .get("diagnostico2"));
            tbxNomDx2.setValue(cie.getNombre());
        } else {
            tbxCodigo_diagnostico2.setValue("");
            tbxNomDx2.setValue("");
        }

        cie = new Cie();
        cie.setCodigo((String) mapa_datos.get("diagnostico3"));
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

        if (cie != null) {
            tbxCodigo_diagnostico3.setValue((String) mapa_datos
                    .get("diagnostico3"));
            tbxNomDx3.setValue(cie.getNombre());
        } else {
            tbxCodigo_diagnostico3.setValue("");
            tbxNomDx3.setValue("");
        }

        Res.cargarAutomatica(dbxValor_cuota, mapa_datos, "valor_cuota", null);
        Res.cargarAutomatica(lbxFinalidad_consulta, mapa_datos,
                "finalidad_consulta", null);
        Res.cargarAutomatica(lbxCausa_externa, mapa_datos, "causa_externa",
                null);
        Res.cargarAutomatica(lbxTipo_diagnostico, mapa_datos,
                "tipo_diagnostico", null);
        Res.cargarAutomatica(tbxCodigo_diagnostico_principal, mapa_datos,
                "diagnostico_principal", null);
        Res.cargarAutomatica(tbxCodigo_diagnostico1, mapa_datos,
                "diagnostico1", null);
        Res.cargarAutomatica(tbxCodigo_diagnostico2, mapa_datos,
                "diagnostico2", null);
        Res.cargarAutomatica(tbxCodigo_diagnostico3, mapa_datos,
                "diagnostico3", null);

    }

    public void buscarCentro() {
        String valor = bandboxBuscar_centros.getValue().trim().toUpperCase();
        if (!valor.isEmpty()) {
            List<Listitem> listado = lbxCentros_atencion.getItems();
            for (Listitem listitem : listado) {
                Centro_atencion centro_atencion = (Centro_atencion) listitem
                        .getValue();
                if (centro_atencion.getNombre_centro().toUpperCase()
                        .contains(valor)
                        || valor.equals(centro_atencion.getCodigo_centro())) {
                    Clients.scrollIntoView(listitem);
                    MensajesUtil.notificarInformacion("Resultado encontrado",
                            listitem);
                    break;
                }
            }
        }
    }

}
