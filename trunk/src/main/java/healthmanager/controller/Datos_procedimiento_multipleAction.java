/**
 *
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Datos_procedimiento;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Manuales_tarifarios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Porcentajes_cirugias;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.service.AdmisionService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
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
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;
import com.framework.util.ServiciosDisponiblesUtils;
import com.framework.util.Util;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * @author Usuario
 *
 */
public class Datos_procedimiento_multipleAction extends ZKWindow {

    private static Logger log = Logger
            .getLogger(Datos_procedimiento_multipleAction.class);
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
    @View
    private Bandbox tbxCodigo_prestador;
    @View
    private Listbox lbxNro_ingreso;
    @View
    private Datebox dtbxFecha_procedimiento;
    @View
    private Textbox tbxCodigo_procedimiento;
    @View
    private Textbox tbxNumero_autorizacion;
	// @View private Doublebox dbxValor_procedimiento;
    // @View private Intbox ibxUnidades;
    // @View private Doublebox dbxCopago;
    @View
    private Doublebox dbxValor_neto;
    @View
    private Listbox lbxAmbito_procedimiento;
    @View
    private Listbox lbxFinalidad_procedimiento;
    @View
    private Listbox lbxPersonal_atiende;
    @View
    private Listbox lbxForma_realizacion;
    @View
    private Bandbox tbxCodigo_diagnostico_principal;
    @View
    private Bandbox tbxCodigo_diagnostico_relacionado;
    @View
    private Bandbox tbxComplicacion;
    @View
    private Radiogroup rdbTipo_intervencion;
    @View
    private Checkbox chbCobra_cirujano;
    @View
    private Checkbox chbCobra_anestesiologo;
    @View
    private Checkbox chbCobra_ayudante;
    @View
    private Checkbox chbCobra_sala;
    @View
    private Checkbox chbCobra_materiales;
    @View
    private Checkbox chbCobra_perfusionista;
    @View
    private Doublebox dbxValor_cirujano;
    @View
    private Doublebox dbxValor_anestesiologo;
    @View
    private Doublebox dbxValor_ayudante;
    @View
    private Doublebox dbxValor_sala;
    @View
    private Doublebox dbxValor_materiales;
    @View
    private Doublebox dbxValor_perfusionista;
    @View
    private Bandbox tbxCodigo_anestesiologo;
    @View
    private Bandbox tbxCodigo_ayudante;
    @View
    private Checkbox chbCirugia_conjunto;
    @View
    private Checkbox chbIncruento;
    @View
    private Listbox lbxTipo_sala;

    @View
    private Textbox tbxNro_factura;
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
    private Textbox tbxNacimiento;
    @View
    private Textbox tbxEdad;
    @View
    private Textbox tbxSexo;
    @View
    private Textbox tbxEstrato;

    @View
    private Textbox tbxNomPcd;
    @View
    private Textbox tbxSexo_pcd;
    @View
    private Textbox tbxLimite_inferior_pcd;
    @View
    private Textbox tbxLimite_superior_pcd;
    @View
    private Doublebox dbxUvr;
    @View
    private Textbox tbxGrupo;

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
    private Textbox tbxManual;
    @View
    private Textbox tbxAnio;

    @View
    private Textbox tbxNomPaciente;
    @View
    private Textbox tbxNomMedico;
    @View
    private Textbox tbxNomAnestesiologo;
    @View
    private Textbox tbxNomAyudante;
    @View
    private Textbox tbxAseguradora;

    @View
    private Textbox tbxNomDxpp;
    @View
    private Textbox tbxNomDx1;
    @View
    private Textbox tbxNomDx2;

    @View
    private Toolbarbutton imgQuitar_pct;
    @View
    private Toolbarbutton imgQuitar_med;
    @View
    private Toolbarbutton imgQuitar_ana;
    @View
    private Toolbarbutton imgQuitar_ayu;
    @View
    private Toolbarbutton imgConsultar_pcd;
    @View
    private Toolbarbutton imgQuitar_pcd;
    @View
    private Toolbarbutton imgQuitar_dxpp;
    @View
    private Toolbarbutton imgQuitar_dx1;
    @View
    private Toolbarbutton imgQuitar_dx2;

    private List<Datos_procedimiento> lista_datos;
    private List<Datos_procedimiento> lista_aux;

    @View
    private Grid gridProcedimientos;
    @View
    private Rows rowsProcedimientos;

    @View
    private Doublebox dbxValor_neto2;
    @View
    private Doublebox dbxAuxValor_neto2;

    @View
    private Doublebox dbxAuxValor_cirujano;
    @View
    private Doublebox dbxAuxValor_anestesiologo;
    @View
    private Doublebox dbxAuxValor_ayudante;
    @View
    private Doublebox dbxAuxValor_sala;
    @View
    private Doublebox dbxAuxValor_materiales;
    @View
    private Doublebox dbxAuxValor_perfusionista;

    @View
    private Textbox tbxTipo_int;

    @View
    private Listbox lbxFormato;

    @Override
    public void init() throws Exception {

        lista_datos = new LinkedList<Datos_procedimiento>();
        lista_aux = new LinkedList<Datos_procedimiento>();

        listarCombos();

        deshabilitarCampos(true);

        boolean ocultarConsulta = false;
        String nro_ingreso = "";
        String nro_identificacion = "";
        Long codigo_registro = null;
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
            codigo_registro = (Long) parametros.get("codigo_registro");
            cargarDatosModuloFactura(nro_ingreso, nro_identificacion,
                    codigo_registro, ocultarConsulta);
        } else {
            accionForm(true, "registrar");
        }

    }

    private void cargarDatosModuloFactura(String nro_ingreso,
            String nro_identificacion, Long codigo_registro,
            boolean ocultarConsulta) throws Exception {

        tbxNro_identificacion.setDisabled(true);
        imgQuitar_pct.setVisible(false);
        lbxNro_ingreso.setDisabled(true);
        ocultarBotoConsultar(ocultarConsulta);

        Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
        datos_procedimiento.setCodigo_empresa(empresa.getCodigo_empresa());
        datos_procedimiento.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        datos_procedimiento.setCodigo_registro(codigo_registro);
        datos_procedimiento = getServiceLocator()
                .getDatos_procedimientoService().consultar(datos_procedimiento);
        if (datos_procedimiento != null) {
            mostrarDatos(datos_procedimiento);
        } else {
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

                tbxNacimiento.setValue(new java.text.SimpleDateFormat(
                        "dd/MM/yyyy").format(paciente.getFecha_nacimiento()));
                tbxSexo.setValue(Utilidades.getNombreElemento(
                        paciente.getSexo(), "sexo", this));
                tbxEstrato.setValue(paciente.getEstrato());
                tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
                        "dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
                        "1", true));

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
        listarElementoListbox(lbxAmbito_procedimiento, false);
        listarElementoListbox(lbxFinalidad_procedimiento, false);
        listarElementoListbox(lbxPersonal_atiende, false);
        listarElementoListbox(lbxForma_realizacion, false);
        listarElementoListbox(lbxTipo_sala, false);
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
        listitem.setValue("fecha_procedimiento");
        listitem.setLabel("Fecha(aaaa-mm-dd)");
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

    // Deshabilitar campos/Habilitar campos
    public void deshabilitarCampos(boolean sw) throws Exception {
        Collection<Component> collection = groupboxForm.getFellows();
        for (Component abstractComponent : collection) {
            if (abstractComponent instanceof Textbox) {
                if (!((Textbox) abstractComponent).getId().startsWith(
                        "tbxValue")
                        && !((Textbox) abstractComponent).getId().equals(
                                "tbxNro_identificacion")) {
                    ((Textbox) abstractComponent).setDisabled(sw);
                }
            }
            if (abstractComponent instanceof Listbox) {
                if (!((Listbox) abstractComponent).getId().equals(
                        "lbxParameter")
                        && !((Listbox) abstractComponent).getId().equals(
                                "lbxFormato")) {
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

        lbxForma_realizacion.setDisabled(true);
        rdbTipo_intervencion.getItemAtIndex(4).setDisabled(true);
        // imgQuitar_pct.setVisible(!sw);
        imgQuitar_med.setVisible(!sw);
        imgConsultar_pcd.setVisible(!sw);
        imgQuitar_pcd.setVisible(!sw);
        imgQuitar_dxpp.setVisible(!sw);
        imgQuitar_dx1.setVisible(!sw);
        imgQuitar_dx2.setVisible(!sw);

        imgQuitar_ana.setVisible(!sw);
        imgQuitar_ayu.setVisible(!sw);

        bloqueoBotonGuardar(sw);

        if (sw) {
            listarIngresos(lbxNro_ingreso, new LinkedList<Admision>(), true);
        }

        // Grilla //
        borrarTodo();

    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        Collection<Component> collection = groupboxEditar.getFellows();
        for (Component abstractComponent : collection) {
            if (abstractComponent instanceof Textbox) {
                if (!((Textbox) abstractComponent).getId().equals("tbxValue")
                        && !((Textbox) abstractComponent).getId().equals(
                                "tbxPor_lote")) {
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
                ((Radio) ((Radiogroup) abstractComponent).getItemAtIndex(0))
                        .setChecked(true);
            }
        }
        rdbTipo_intervencion.getItemAtIndex(0).setChecked(true);
        chbCirugia_conjunto.setChecked(true);
        imgQuitar_pct.setVisible(true);
        tbxNro_identificacion.setDisabled(false);
        deshabilitarCampos(true);
        borrarTodo();
    }

    // Metodo para validar campos del formulario //
    public boolean validarForm() throws Exception {

        tbxNro_identificacion
                .setStyle("text-transform:uppercase;background-color:white");
        tbxCodigo_prestador
                .setStyle("text-transform:uppercase;background-color:white");
        tbxCodigo_anestesiologo
                .setStyle("text-transform:uppercase;background-color:white");
        tbxCodigo_ayudante
                .setStyle("text-transform:uppercase;background-color:white");
        tbxCodigo_procedimiento
                .setStyle("text-transform:uppercase;background-color:white");
        lbxNro_ingreso.setStyle("background-color:white");
        dbxValor_neto.setStyle("background-color:white");
        tbxCodigo_diagnostico_principal
                .setStyle("text-transform:uppercase;background-color:white");

        Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
                .getValue());

        boolean valida = true;

        String tipo_intervencion = rdbTipo_intervencion.getSelectedItem()
                .getValue();
        if (tipo_intervencion == null) {
            tipo_intervencion = "";
        }

        String mensaje = "Los campos marcados con (*) son obligatorios";

        if (tbxNro_identificacion.getText().trim().equals("")) {
            tbxNro_identificacion
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (tbxCodigo_prestador.getText().trim().equals("")
                && chbCobra_cirujano.isChecked()) {
            tbxCodigo_prestador
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (tbxCodigo_anestesiologo.getText().trim().equals("")
                && chbCobra_anestesiologo.isChecked()) {
            tbxCodigo_anestesiologo
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (tbxCodigo_ayudante.getText().trim().equals("")
                && chbCobra_ayudante.isChecked()) {
            tbxCodigo_ayudante
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (tbxCodigo_procedimiento.getText().trim().equals("")) {
            tbxCodigo_procedimiento
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (admision == null) {
            lbxNro_ingreso.setStyle("background-color:#F6BBBE");
            valida = false;
        }
        if (dbxValor_neto2.getText().trim().equals("")) {
            dbxValor_neto2.setStyle("background-color:#F6BBBE");
            valida = false;
        } else if (dbxValor_neto2.getValue() <= 0) {
            dbxValor_neto2.setStyle("background-color:#F6BBBE");
            valida = false;
            mensaje = "El valor es igual a cero";
        }
        if (tbxCodigo_diagnostico_principal.getText().trim().equals("")) {
            tbxCodigo_diagnostico_principal
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (valida && tipo_intervencion.equals("")) {
            valida = false;
            mensaje = "Debe seleccionar tipo de intervencion";
        }

        if (valida && lista_datos.size() > 0 && tipo_intervencion.equals("1")) {
            valida = false;
            mensaje = "Solo puede haber un procedimiento para Una sola Intervencion";
        }

        if (valida && lista_datos.size() > 0 && tipo_intervencion.equals("2")) {
            valida = false;
            mensaje = "Solo puede haber un procedimiento para Bilateral";
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
            parameters.put("codigo_empresa", empresa.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");
            parameters.put("es_quirurgico", "S");

            parameters.put("limite_paginado", "limit 25 offset 0");

            List<Map> lista_datos = getServiceLocator()
                    .getDatos_procedimientoService().listar_quirurgicos(
                            parameters);
            rowsResultado.getChildren().clear();

            for (Map bean : lista_datos) {
                rowsResultado.appendChild(crearFilas(bean, this));
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

        final Map map = (Map) objeto;
        final Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
        datos_procedimiento.setCodigo_empresa(empresa.getCodigo_empresa());
        datos_procedimiento.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        datos_procedimiento.setCodigo_registro((Long) map.get("codigo_registro"));
        datos_procedimiento.setFecha_procedimiento((Timestamp) map
                .get("fecha_procedimiento"));
        datos_procedimiento.setNro_identificacion((String) map
                .get("nro_identificacion"));
        datos_procedimiento.setNro_ingreso((String) map.get("nro_ingreso"));
        datos_procedimiento.setConsecutivo_registro("1");

        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
        paciente.setCodigo_sucursal(datos_procedimiento.getCodigo_sucursal());
        paciente.setNro_identificacion(datos_procedimiento
                .getNro_identificacion());
        paciente = getServiceLocator().getPacienteService().consultar(paciente);
        String nombres_paciente = (paciente != null ? paciente.getNombre1()
                + " " + paciente.getNombre2() : "");
        String apellidos_paciente = (paciente != null ? paciente.getApellido1()
                + " " + paciente.getApellido2() : "");
        String tipo_identificacion = (paciente != null ? paciente
                .getTipo_identificacion() : "");

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(datos_procedimiento.getCodigo_registro() + ""));
        fila.appendChild(new Label(tipo_identificacion + ""));
        fila.appendChild(new Label(datos_procedimiento.getNro_identificacion()
                + ""));
        fila.appendChild(new Label(new SimpleDateFormat("dd/MM/yyyy")
                .format(datos_procedimiento.getFecha_procedimiento())));
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
                mostrarDatos(datos_procedimiento);
            }
        });
        hbox.appendChild(img);

        img = new Image();
        img.setSrc("/images/borrar.gif");
        img.setTooltiptext("Eliminar");
        img.setStyle("cursor: pointer");
        String estado_admision = getEstado_admision(datos_procedimiento);
        if (estado_admision.equals("2")) {
            img.setVisible(false);
        } else {
            img.setVisible(true);
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
                                    eliminarDatos(datos_procedimiento);
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

    public void guardarDatos() throws Exception {
        if (lista_datos.size() <= 0) {
            Messagebox
                    .show("Debe adicionar al menos un registro de procedimiento a la grilla",
                            usuarios.getNombres() + " recuerde que...",
                            Messagebox.OK, Messagebox.EXCLAMATION);
            return;
        }

        for (Datos_procedimiento datos_procedimiento : lista_datos) {
            datos_procedimiento.setFecha_procedimiento(new Timestamp(
                    dtbxFecha_procedimiento.getValue().getTime()));
            datos_procedimiento.setNumero_autorizacion(tbxNumero_autorizacion
                    .getValue());
            datos_procedimiento.setAmbito_procedimiento(lbxAmbito_procedimiento
                    .getSelectedItem().getValue().toString());
            datos_procedimiento
                    .setFinalidad_procedimiento(lbxFinalidad_procedimiento
                            .getSelectedItem().getValue().toString());
            datos_procedimiento.setPersonal_atiende(lbxPersonal_atiende
                    .getSelectedItem().getValue().toString());
        }

        getServiceLocator().getDatos_procedimientoService().crearCirugia(
                lista_datos, empresa.getCodigo_empresa(),
                sucursal.getCodigo_sucursal(), Utilidades.getValorLong(tbxNro_factura.getValue()));

        accionForm(true, "registrar");

        Messagebox.show("Los datos se guardaron satisfactoriamente",
                "Informacion ..", Messagebox.OK, Messagebox.INFORMATION);

        if (this.getParent() instanceof Facturacion_ripsAction) {
            Facturacion_ripsAction action = (Facturacion_ripsAction) this
                    .getParent();
            action.consultarServiciosFacturas(true);
            this.detach();
        }
    }

    private Datos_procedimiento getDatos_procedimiento() {
        Admision admision = ((Admision) lbxNro_ingreso.getSelectedItem()
                .getValue());

        Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
        datos_procedimiento.setCodigo_empresa(empresa.getCodigo_empresa());
        datos_procedimiento.setCodigo_sucursal(sucursal.getCodigo_sucursal());
        datos_procedimiento.setCodigo_registro(Utilidades.getValorLong(tbxNro_factura.getValue()));
        datos_procedimiento.setConsecutivo_registro("1");
        datos_procedimiento = getServiceLocator()
                .getDatos_procedimientoService().consultar(datos_procedimiento);
        if (datos_procedimiento == null) {
            datos_procedimiento = new Datos_procedimiento();
            datos_procedimiento.setCodigo_empresa(empresa.getCodigo_empresa());
            datos_procedimiento.setCodigo_sucursal(sucursal
                    .getCodigo_sucursal());
            datos_procedimiento.setCodigo_registro(Utilidades.getValorLong(tbxNro_factura.getValue()));
            datos_procedimiento.setConsecutivo_registro("1");
        }

        datos_procedimiento.setTipo_identificacion(tbxTipo_identificacion
                .getValue());
        datos_procedimiento.setNro_identificacion(tbxNro_identificacion
                .getValue());
        datos_procedimiento.setCodigo_administradora(tbxCodigo_administradora
                .getValue());
        datos_procedimiento.setId_plan(tbxId_plan.getValue());
        datos_procedimiento.setCodigo_prestador(tbxCodigo_prestador.getValue());
        datos_procedimiento.setNro_ingreso(admision.getNro_ingreso());
        datos_procedimiento.setFecha_procedimiento(new Timestamp(
                dtbxFecha_procedimiento.getValue().getTime()));
        datos_procedimiento.setCodigo_procedimiento(tbxCodigo_procedimiento
                .getValue());
        String codigo_cups = ((Map<String, Object>) tbxCodigo_procedimiento
                .getAttribute("mapProcedimeinto")).get("codigo_cups") + "";
        datos_procedimiento.setCodigo_cups(codigo_cups);
        datos_procedimiento.setNumero_autorizacion(tbxNumero_autorizacion
                .getValue());
        datos_procedimiento
                .setValor_procedimiento((dbxValor_neto.getValue() != null ? dbxValor_neto
                        .getValue() : 0.00));
        datos_procedimiento.setUnidades(1);
        datos_procedimiento.setCopago(0.00);
        datos_procedimiento
                .setValor_neto((dbxValor_neto2.getValue() != null ? dbxValor_neto2
                        .getValue() : 0.00));
        datos_procedimiento.setAmbito_procedimiento(lbxAmbito_procedimiento
                .getSelectedItem().getValue().toString());
        datos_procedimiento
                .setFinalidad_procedimiento(lbxFinalidad_procedimiento
                        .getSelectedItem().getValue().toString());
        datos_procedimiento.setPersonal_atiende(lbxPersonal_atiende
                .getSelectedItem().getValue().toString());
        datos_procedimiento.setForma_realizacion(lbxForma_realizacion
                .getSelectedItem().getValue().toString());
        datos_procedimiento
                .setCodigo_diagnostico_principal(tbxCodigo_diagnostico_principal
                        .getValue());
        datos_procedimiento
                .setCodigo_diagnostico_relacionado(tbxCodigo_diagnostico_relacionado
                        .getValue());
        datos_procedimiento.setComplicacion(tbxComplicacion.getValue());
        datos_procedimiento.setCancelo_copago("N");
        datos_procedimiento.setCreacion_date(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        datos_procedimiento.setUltimo_update(new Timestamp(
                new GregorianCalendar().getTimeInMillis()));
        datos_procedimiento.setCreacion_user(usuarios.getCodigo().toString());
        datos_procedimiento.setUltimo_user(usuarios.getCodigo().toString());
        datos_procedimiento.setTipo_intervencion(rdbTipo_intervencion
                .getSelectedItem().getValue().toString());
        datos_procedimiento.setEs_quirurgico("S");
        datos_procedimiento
                .setCobra_cirujano(chbCobra_cirujano.isChecked() ? "S" : "N");
        datos_procedimiento.setCobra_anestesiologo(chbCobra_anestesiologo
                .isChecked() ? "S" : "N");
        datos_procedimiento
                .setCobra_ayudante(chbCobra_ayudante.isChecked() ? "S" : "N");
        datos_procedimiento
                .setCobra_sala(chbCobra_sala.isChecked() ? "S" : "N");
        datos_procedimiento
                .setCobra_materiales(chbCobra_materiales.isChecked() ? "S"
                        : "N");
        datos_procedimiento.setCobra_perfusionista(chbCobra_perfusionista
                .isChecked() ? "S" : "N");
        datos_procedimiento.setGrupo(tbxGrupo.getValue());
        datos_procedimiento
                .setValor_cirujano((dbxValor_cirujano.getValue() != null ? dbxValor_cirujano
                        .getValue() : 0.00));
        datos_procedimiento
                .setValor_anestesiologo((dbxValor_anestesiologo.getValue() != null ? dbxValor_anestesiologo
                        .getValue() : 0.00));
        datos_procedimiento
                .setValor_ayudante((dbxValor_ayudante.getValue() != null ? dbxValor_ayudante
                        .getValue() : 0.00));
        datos_procedimiento
                .setValor_sala((dbxValor_sala.getValue() != null ? dbxValor_sala
                        .getValue() : 0.00));
        datos_procedimiento
                .setValor_materiales((dbxValor_materiales.getValue() != null ? dbxValor_materiales
                        .getValue() : 0.00));
        datos_procedimiento
                .setValor_perfusionista((dbxValor_perfusionista.getValue() != null ? dbxValor_perfusionista
                        .getValue() : 0.00));
        datos_procedimiento.setCodigo_anestesiologo(tbxCodigo_anestesiologo
                .getValue());
        datos_procedimiento.setCodigo_ayudante(tbxCodigo_ayudante.getValue());
        datos_procedimiento
                .setCirugia_conjunto(chbCirugia_conjunto.isChecked() ? "S"
                        : "N");
        datos_procedimiento.setIncruento(chbIncruento.isChecked() ? "S" : "N");
        datos_procedimiento.setTipo_sala(lbxTipo_sala.getSelectedItem()
                .getValue().toString());
        datos_procedimiento.setCodigo_programa("");
        datos_procedimiento.setValor_real(dbxAuxValor_neto2.getValue());
        datos_procedimiento.setDescuento(0.0);
        datos_procedimiento.setIncremento(0.0);

        return datos_procedimiento;
    }

    // Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Datos_procedimiento datos_procedimiento = (Datos_procedimiento) obj;
        try {
            deshabilitarCampos(false);
            activarSeccionPcd(false);
            datos_procedimiento.setConsecutivo_registro("1");
            datos_procedimiento = getServiceLocator()
                    .getDatos_procedimientoService().consultar(
                            datos_procedimiento);
            if (datos_procedimiento == null) {
                throw new Exception(
                        "Error no existe registro de procedimiento multiple que editar");
            }

            tbxNro_factura.setValue(datos_procedimiento.getCodigo_registro() + "");
            tbxTipo_identificacion.setValue(datos_procedimiento
                    .getTipo_identificacion());

            tbxNro_identificacion.setValue(datos_procedimiento
                    .getNro_identificacion());
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
            paciente.setCodigo_sucursal(datos_procedimiento
                    .getCodigo_sucursal());
            paciente.setNro_identificacion(datos_procedimiento
                    .getNro_identificacion());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);
            tbxNomPaciente.setValue((paciente != null ? paciente
                    .getNombreCompleto() : ""));
            tbxSexo_pct.setValue((paciente != null ? paciente.getSexo() : ""));
            tbxFecha_nac.setValue((paciente != null ? new SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()) : ""));
            tbxNacimiento
                    .setValue((paciente != null ? new java.text.SimpleDateFormat(
                                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento())
                            : ""));
            tbxSexo.setValue((paciente != null ? Utilidades.getNombreElemento(
                    paciente.getSexo(), "sexo", this) : ""));
            tbxEstrato
                    .setValue((paciente != null ? paciente.getEstrato() : ""));
            tbxEdad.setValue((paciente != null ? Util.getEdad(
                    new java.text.SimpleDateFormat("dd/MM/yyyy")
                    .format(paciente.getFecha_nacimiento()), "1", true)
                    : ""));

            tbxCodigo_administradora.setValue(datos_procedimiento
                    .getCodigo_administradora());
            tbxId_plan.setValue(datos_procedimiento.getId_plan());

            Administradora administradora = new Administradora();
            administradora.setCodigo_empresa(datos_procedimiento
                    .getCodigo_empresa());
            administradora.setCodigo_sucursal(datos_procedimiento
                    .getCodigo_sucursal());
            administradora.setCodigo(datos_procedimiento
                    .getCodigo_administradora());
            administradora = getServiceLocator().getAdministradoraService()
                    .consultar(administradora);

            Admision admision = new Admision();
            admision.setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
            admision.setCodigo_sucursal(datos_procedimiento
                    .getCodigo_sucursal());
            admision.setNro_identificacion(datos_procedimiento
                    .getNro_identificacion());
            admision.setNro_ingreso(datos_procedimiento.getNro_ingreso());
            admision = getServiceLocator().getServicio(AdmisionService.class)
                    .consultar(admision);

            Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                    .getManuales_tarifarios(admision);

            Contratos contratos = new Contratos();
            contratos
                    .setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
            contratos.setCodigo_sucursal(datos_procedimiento
                    .getCodigo_sucursal());
            contratos.setCodigo_administradora(datos_procedimiento
                    .getCodigo_administradora());
            contratos.setId_plan(datos_procedimiento.getId_plan());
            contratos = getServiceLocator().getContratosService().consultar(
                    contratos);
            tbxAseguradora.setValue((administradora != null ? administradora
                    .getNombre() : "")
                    + " - "
                    + (contratos != null ? contratos.getNombre() : ""));

            tbxManual.setValue((contratos != null ? manuales_tarifarios
                    .getMaestro_manual().getTipo_manual() : ""));
            tbxAnio.setValue((contratos != null ? manuales_tarifarios.getAnio()
                    : ""));

            listarIngresos(lbxNro_ingreso,
                    listarAdmisiones(datos_procedimiento, true), false);
            for (int i = 0; i < lbxNro_ingreso.getItemCount(); i++) {
                Listitem listitem = lbxNro_ingreso.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(datos_procedimiento.getNro_ingreso())) {
                    listitem.setSelected(true);
                    i = lbxNro_ingreso.getItemCount();
                }
            }
            dtbxFecha_procedimiento.setValue(datos_procedimiento
                    .getFecha_procedimiento());
            // tbxCodigo_procedimiento.setValue(datos_procedimiento.getCodigo_procedimiento());
            tbxNumero_autorizacion.setValue(datos_procedimiento
                    .getNumero_autorizacion());
            dbxValor_neto.setValue(0.0);
            for (int i = 0; i < lbxAmbito_procedimiento.getItemCount(); i++) {
                Listitem listitem = lbxAmbito_procedimiento.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(datos_procedimiento.getAmbito_procedimiento())) {
                    listitem.setSelected(true);
                    i = lbxAmbito_procedimiento.getItemCount();
                }
            }
            for (int i = 0; i < lbxFinalidad_procedimiento.getItemCount(); i++) {
                Listitem listitem = lbxFinalidad_procedimiento
                        .getItemAtIndex(i);
                if (listitem
                        .getValue()
                        .toString()
                        .equals(datos_procedimiento
                                .getFinalidad_procedimiento())) {
                    listitem.setSelected(true);
                    i = lbxFinalidad_procedimiento.getItemCount();
                }
            }
            for (int i = 0; i < lbxPersonal_atiende.getItemCount(); i++) {
                Listitem listitem = lbxPersonal_atiende.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(datos_procedimiento.getPersonal_atiende())) {
                    listitem.setSelected(true);
                    i = lbxPersonal_atiende.getItemCount();
                }
            }
            for (int i = 0; i < lbxForma_realizacion.getItemCount(); i++) {
                Listitem listitem = lbxForma_realizacion.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(datos_procedimiento.getForma_realizacion())) {
                    listitem.setSelected(true);
                    i = lbxForma_realizacion.getItemCount();
                }
            }
            tbxCodigo_diagnostico_principal.setValue(datos_procedimiento
                    .getCodigo_diagnostico_principal());
            tbxCodigo_diagnostico_relacionado.setValue(datos_procedimiento
                    .getCodigo_diagnostico_relacionado());
            tbxComplicacion.setValue(datos_procedimiento.getComplicacion());
            Radio radio = (Radio) rdbTipo_intervencion
                    .getFellow("radioTipo_intervencion"
                            + datos_procedimiento.getTipo_intervencion());
            radio.setChecked(true);

            chbCirugia_conjunto.setChecked(datos_procedimiento
                    .getCirugia_conjunto().equals("S") ? true : false);
            chbIncruento.setChecked(datos_procedimiento.getIncruento().equals(
                    "S") ? true : false);
            for (int i = 0; i < lbxTipo_sala.getItemCount(); i++) {
                Listitem listitem = lbxTipo_sala.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(datos_procedimiento.getTipo_sala())) {
                    listitem.setSelected(true);
                    i = lbxTipo_sala.getItemCount();
                }
            }

            imgQuitar_pct.setVisible(false);
            tbxNro_identificacion.setDisabled(true);

            Map<String, Object> parameters = new HashMap();
            parameters.put("codigo_empresa",
                    datos_procedimiento.getCodigo_empresa());
            parameters.put("codigo_sucursal",
                    datos_procedimiento.getCodigo_sucursal());
            parameters.put("codigo_registro", datos_procedimiento.getCodigo_registro());
            parameters.put("es_quirurgico", "S");
            parameters.put("order_cirugia", "");

            validarRegistroEditar(datos_procedimiento);

            borrarTodo();
            rowsProcedimientos.getChildren().clear();
            List<Datos_procedimiento> lista_datos_pro = getServiceLocator()
                    .getDatos_procedimientoService().listarTabla(parameters);
            lista_aux = new LinkedList();
            Utilidades.ordenarProcedimientosQuirurgicos(lista_datos_pro,
                    manuales_tarifarios, getServiceLocator());
            for (int i = 0; i < lista_datos_pro.size(); i++) {
                Datos_procedimiento dp = lista_datos_pro.get(i);
                if (contratos != null) {
                    double valor_cirujano = dp.getValor_cirujano();
                    double valor_anestesiologo = dp.getValor_anestesiologo();
                    double valor_ayudante = dp.getValor_ayudante();
                    double valor_sala = dp.getValor_sala();
                    double valor_materiales = dp.getValor_materiales();
                    double valor_perfusionista = dp.getValor_perfusionista();

                    if (i != 0) {
                        String aux_manual = (tbxManual.getValue()
                                .equals("SOAT") ? "SOAT" : "ISS");
                        Porcentajes_cirugias porcentajes_cirugias = new Porcentajes_cirugias();
                        porcentajes_cirugias.setManual_tarifario(aux_manual);
                        porcentajes_cirugias
                                .setTipo_intervencion(rdbTipo_intervencion
                                        .getSelectedItem().getValue() + "");
                        // //log.info("porcentajes_cirugias antes: "+porcentajes_cirugias);
                        porcentajes_cirugias = getServiceLocator()
                                .getPorcentajes_cirugiasService().consultar(
                                        porcentajes_cirugias);
                        // //log.info("porcentajes_cirugias despues: "+porcentajes_cirugias);
                        if (porcentajes_cirugias == null) {
                            throw new Exception(
                                    "No se ha definido porcentaje de cirugías");
                        }

                        valor_cirujano = (100 * dp.getValor_cirujano() / (porcentajes_cirugias
                                .getPorcentaje_cirujano() * 100));
                        valor_anestesiologo = (100 * dp
                                .getValor_anestesiologo() / (porcentajes_cirugias
                                .getPorcentaje_anestesiologo() * 100));
                        valor_ayudante = (100 * dp.getValor_ayudante() / (porcentajes_cirugias
                                .getPorcentaje_ayudante() * 100));
                        valor_sala = (100 * dp.getValor_sala() / (porcentajes_cirugias
                                .getPorcentaje_sala() * 100));
                        valor_materiales = (100 * dp.getValor_materiales() / (porcentajes_cirugias
                                .getPorcentaje_materiales() * 100));

                    }

                    Datos_procedimiento datos = new Datos_procedimiento(dp);
                    datos.setCodigo_procedimiento(dp.getCodigo_procedimiento());
                    datos_procedimiento
                            .setCobra_cirujano(valor_cirujano > 0 ? "S" : "N");
                    datos_procedimiento
                            .setCobra_anestesiologo(valor_anestesiologo > 0 ? "S"
                                    : "N");
                    datos_procedimiento
                            .setCobra_ayudante(valor_ayudante > 0 ? "S" : "N");
                    datos_procedimiento.setCobra_sala(valor_sala > 0 ? "S"
                            : "N");
                    datos_procedimiento
                            .setCobra_materiales(valor_materiales > 0 ? "S"
                                    : "N");
                    datos_procedimiento
                            .setCobra_perfusionista(valor_perfusionista > 0 ? "S"
                                    : "N");
                    datos.setGrupo(dp.getGrupo());
                    datos.setValor_cirujano(valor_cirujano);
                    datos.setValor_anestesiologo(valor_anestesiologo);
                    datos.setValor_ayudante(valor_ayudante);
                    datos.setValor_sala(valor_sala);
                    datos.setValor_materiales(valor_materiales);
                    datos.setValor_perfusionista(valor_perfusionista);
                    datos.setCodigo_prestador(dp.getCodigo_prestador());
                    datos.setCodigo_anestesiologo(dp.getCobra_anestesiologo());
                    datos.setCodigo_ayudante(dp.getCobra_ayudante());
                    datos.setValor_real(dp.getValor_real());
                    datos.setCodigo_cups(dp.getCodigo_cups());

                    lista_aux.add(datos);
                }

            }
            for (Datos_procedimiento datos_procedimiento2 : lista_datos_pro) {
                agregarProcedimiento(datos_procedimiento2, false);
            }

            // Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {

            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Datos_procedimiento datos_procedimiento = (Datos_procedimiento) obj;
        datos_procedimiento.setConsecutivo_registro(null);
        try {
            int result = getServiceLocator().getDatos_procedimientoService()
                    .eliminarActualizarFactura(datos_procedimiento);
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
            MensajesUtil.mensajeError(r, "", this);
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

    public List<Admision> listarAdmisiones(
            Datos_procedimiento datos_procedimiento, boolean sw)
            throws Exception {
        Map paramAdmision = new HashMap();
        paramAdmision.put("codigo_empresa",
                datos_procedimiento.getCodigo_empresa());
        paramAdmision.put("codigo_sucursal",
                datos_procedimiento.getCodigo_sucursal());
        paramAdmision.put("nro_identificacion",
                datos_procedimiento.getNro_identificacion());
        paramAdmision.put("order", "fecha_ingreso desc");
        if (sw) {
            paramAdmision.put("nro_ingreso",
                    datos_procedimiento.getNro_ingreso());
        } else {
            paramAdmision.put("estado", "1");
        }
        // //log.info("paramAdmision: "+paramAdmision);
        List<Admision> listaAdmisiones = getServiceLocator().getServicio(
                AdmisionService.class).listarTabla(paramAdmision);
        // //log.info("listaAdmisiones: "+listaAdmisiones);

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

            Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                    .getManuales_tarifarios(aux2);

            Contratos plan = new Contratos();
            plan.setCodigo_empresa(empresa.getCodigo_empresa());
            plan.setCodigo_sucursal(sucursal.getCodigo_sucursal());
            plan.setCodigo_administradora(aux2.getCodigo_administradora());
            plan.setId_plan(aux2.getId_plan());
            plan = getServiceLocator().getContratosService().consultar(plan);

            String administradora = "";
            administradora = (admin != null ? admin.getNombre() : "");
            administradora += " - ";
            administradora += (plan != null ? plan.getNombre() : "");
            String manual = (plan != null ? manuales_tarifarios
                    .getMaestro_manual().getTipo_manual() : "");
            String anio = (plan != null ? manuales_tarifarios.getAnio() : "");

            tbxAseguradora.setValue(administradora);
            tbxCodigo_administradora.setValue(aux2.getCodigo_administradora());
            tbxId_plan.setValue(aux2.getId_plan());

            tbxManual.setValue(manual);
            tbxAnio.setValue(anio);
            dtbxFecha_procedimiento.setValue(aux2.getFecha_ingreso());
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
            activarSeccionPcd(false);
        } else {
            Paciente dato = (Paciente) listitem.getValue();

            Datos_procedimiento datos_procedimiento = new Datos_procedimiento();
            datos_procedimiento.setCodigo_empresa(dato.getCodigo_empresa());
            datos_procedimiento.setCodigo_sucursal(dato.getCodigo_sucursal());
            datos_procedimiento.setNro_identificacion(dato
                    .getNro_identificacion());

            List<Admision> listaAdmisiones = listarAdmisiones(
                    datos_procedimiento, false);
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

            tbxNacimiento.setValue(new java.text.SimpleDateFormat("dd/MM/yyyy")
                    .format(dato.getFecha_nacimiento()));
            tbxSexo.setValue(Utilidades.getNombreElemento(dato.getSexo(),
                    "sexo", this));
            tbxEstrato.setValue(dato.getEstrato());
            tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
                    "dd/MM/yyyy").format(dato.getFecha_nacimiento()), "1", true));

            deshabilitarCampos(false);

            listarIngresos(lbxNro_ingreso, listaAdmisiones, false);
            Admision aux2 = (!listaAdmisiones.isEmpty() ? listaAdmisiones
                    .get(0) : null);
            cargarAdmisiones(aux2);
            activarSeccionPcd(false);
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
        activarSeccionPcd(false);
    }

    public void openPcd() throws Exception {
        String pages = "";
        String manual = tbxManual.getValue();
        String anio = tbxAnio.getValue();

        pages = "/pages/openProcedimientos.zul";

        Admision admision = lbxNro_ingreso.getSelectedItem().getValue();

        Map parametros = new HashMap();
        parametros.put("codigo_empresa", empresa.getCodigo_empresa());
        parametros.put("codigo_sucursal", sucursal.getCodigo_sucursal());
        parametros.put("codigo_administradora",
                tbxCodigo_administradora.getValue());
        parametros.put("id_plan", tbxId_plan.getValue());
        parametros.put("restringido", "");
        parametros.put("nro_ingreso",
                (admision != null ? admision.getNro_ingreso() : ""));
        parametros.put("nro_identificacion", tbxNro_identificacion.getValue());
        parametros.put("anio", anio);
        parametros.put("via_ingreso",
                admision != null ? admision.getVia_ingreso() : null);
		// parametros.put("consulta", "N");
        // parametros.put("quirurgico", "S");

        parametros.put("ocultar_filtro_procedimiento",
                "ocultar_filtro_procedimiento");

        Component componente = Executions.createComponents(pages, this,
                parametros);
        final Window ventana = (Window) componente;
        ventana.setWidth("990px");
        ventana.setHeight("400px");
        ventana.setClosable(true);
        ventana.setMaximizable(true);
        ventana.setTitle("CONSULTAR PROCEDIMIENTOS " + manual);
        ventana.setMode("modal");
    }

    public void adicionarPcd(Map pcd) throws Exception {

        Map map = new HashMap();
        map.put("nombre_entidad", (String) pcd.get("nombre_procedimiento"));
        map.put("limite_inferior", (String) pcd.get("limite_inferior_pcd"));
        map.put("limite_superior", (String) pcd.get("limite_superior_pcd"));
        map.put("sexo_entidad", (String) pcd.get("sexo_pcd"));

        map.put("fecha_nac", tbxFecha_nac.getValue());
        map.put("sexo_pct", tbxSexo_pct.getValue());
        Map result = Utilidades.validarInformacionLimiteSexo("Procedimiento",
                (String) pcd.get("nombre_procedimiento"),
                (String) pcd.get("limite_inferior_pcd"),
                (String) pcd.get("limite_superior_pcd"),
                (String) pcd.get("sexo_pcd"), tbxFecha_nac.getValue(),
                tbxSexo_pct.getValue());
        if (!((Boolean) result.get("success"))) {
            throw new Exception((String) result.get("msg"));
        }

        tbxCodigo_procedimiento.setValue((String) pcd
                .get("codigo_procedimiento"));
        tbxCodigo_procedimiento.setAttribute("mapProcedimeinto", pcd);
        tbxNomPcd.setValue((String) pcd.get("nombre_procedimiento"));
        tbxSexo_pcd.setValue((String) pcd.get("sexo_pcd"));
        tbxLimite_inferior_pcd
                .setValue((String) pcd.get("limite_inferior_pcd"));
        tbxLimite_superior_pcd
                .setValue((String) pcd.get("limite_superior_pcd"));
        tbxGrupo.setValue((String) pcd.get("grupo"));
        dbxUvr.setValue((Double) pcd.get("uvr"));
        activarSeccionPcd(false);
        activarSeccionPcd(true);

    }

    public void quitarPcd() throws Exception {
        tbxCodigo_procedimiento.setValue("");
        tbxNomPcd.setValue("");
        tbxSexo_pcd.setValue("");
        tbxLimite_inferior_pcd.setValue("");
        tbxLimite_superior_pcd.setValue("");
        tbxGrupo.setValue("");
        dbxUvr.setValue(0.0);

        activarSeccionPcd(false);
    }

    public void activarSeccionPcd(boolean sw) {
        chbCobra_cirujano.setDisabled(!sw);
        chbCobra_ayudante.setDisabled(!sw);
        chbCobra_anestesiologo.setDisabled(!sw);
        chbCobra_sala.setDisabled(!sw);
        chbCobra_materiales.setDisabled(!sw);
        chbCobra_perfusionista.setDisabled(true);

        /*
         * tbxCodigo_prestador.setDisabled(!sw);
         * tbxCodigo_ayudante.setDisabled(!sw);
         * tbxCodigo_anestesiologo.setDisabled(!sw);
         */
        tbxCodigo_diagnostico_principal.setDisabled(!sw);
        tbxCodigo_diagnostico_relacionado.setDisabled(!sw);
        tbxComplicacion.setDisabled(!sw);

        imgQuitar_med.setVisible(sw);
        imgQuitar_ana.setVisible(sw);
        imgQuitar_ayu.setVisible(sw);

        imgQuitar_dxpp.setVisible(sw);
        imgQuitar_dx1.setVisible(sw);
        imgQuitar_dx2.setVisible(sw);

        if (!sw) {
            chbCobra_cirujano.setChecked(false);
            chbCobra_ayudante.setChecked(false);
            chbCobra_anestesiologo.setChecked(false);
            chbCobra_sala.setChecked(false);
            chbCobra_materiales.setChecked(false);
            chbCobra_perfusionista.setChecked(false);

            tbxCodigo_prestador.setDisabled(true);
            tbxCodigo_ayudante.setDisabled(true);
            tbxCodigo_anestesiologo.setDisabled(true);

            imgQuitar_dxpp.setVisible(false);
            imgQuitar_dx1.setVisible(false);
            imgQuitar_dx2.setVisible(false);

            tbxCodigo_prestador.setValue("");
            tbxNomMedico.setValue("");
            tbxCodigo_ayudante.setValue("");
            tbxNomAyudante.setValue("");
            tbxCodigo_anestesiologo.setValue("");
            tbxNomAnestesiologo.setValue("");

            Admision admision = lbxNro_ingreso.getSelectedItem().getValue();
            String dx = "";
            String nombre_dx = "";
            if (admision != null) {
                dx = admision.getDiagnostico_ingreso();
                Cie cie = new Cie();
                cie.setCodigo(admision.getDiagnostico_ingreso());
                cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
                nombre_dx = (cie != null ? cie.getNombre() : "");
            }

            tbxCodigo_diagnostico_principal.setValue(dx);
            tbxNomDxpp.setValue(nombre_dx);
            tbxSexo_dxpp.setValue("");
            tbxLimite_inferior_dxpp.setValue("");
            tbxLimite_superior_dxpp.setValue("");

            tbxCodigo_diagnostico_relacionado.setValue("");
            tbxNomDx1.setValue("");
            tbxSexo_dx1.setValue("");
            tbxLimite_inferior_dx1.setValue("");
            tbxLimite_superior_dx1.setValue("");

            tbxComplicacion.setValue("");
            tbxNomDx2.setValue("");
            tbxSexo_dx2.setValue("");
            tbxLimite_inferior_dx2.setValue("");
            tbxLimite_superior_dx2.setValue("");

            dbxValor_cirujano.setValue(0.0);
            dbxValor_anestesiologo.setValue(0.0);
            dbxValor_ayudante.setValue(0.0);
            dbxValor_sala.setValue(0.0);
            dbxValor_materiales.setValue(0.0);
            dbxValor_perfusionista.setValue(0.0);

            dbxAuxValor_cirujano.setValue(0.0);
            dbxAuxValor_anestesiologo.setValue(0.0);
            dbxAuxValor_ayudante.setValue(0.0);
            dbxAuxValor_sala.setValue(0.0);
            dbxAuxValor_materiales.setValue(0.0);
            dbxAuxValor_perfusionista.setValue(0.0);

            dbxValor_neto2.setValue(0.0);
            dbxAuxValor_neto2.setValue(0.0);

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

    private String getEstado_admision(Datos_procedimiento datos_procedimiento)
            throws Exception {
        String estado = "1";

        Admision admision = new Admision();
        admision.setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
        admision.setCodigo_sucursal(datos_procedimiento.getCodigo_sucursal());
        admision.setNro_ingreso(datos_procedimiento.getNro_ingreso());
        admision.setNro_identificacion(datos_procedimiento
                .getNro_identificacion());
        admision = getServiceLocator().getServicio(AdmisionService.class)
                .consultar(admision);
        estado = (admision != null ? admision.getEstado() : "1");

        return estado;
    }

    private void validarRegistroEditar(Datos_procedimiento datos_procedimiento)
            throws Exception {
        String estado_admision = getEstado_admision(datos_procedimiento);
        if (this.getParent() instanceof Facturacion_ripsAction) {
            estado_admision = "1";
        }
        if (estado_admision.equals("2")) {
            bloqueoBotonGuardar(true);
        } else if (!datos_procedimiento.getCodigo_orden().equals("")) {
            bloqueoBotonGuardar(true);
        } else {
            bloqueoBotonGuardar(false);
        }
    }

    // Metodo para agregar procedimientos//
    public void agregarProcedimiento() throws Exception {
        if (validarForm()) {
            Datos_procedimiento datos_procedimiento = getDatos_procedimiento();
            agregarProcedimiento(datos_procedimiento, true);
        }
    }

    // Metodo para agregar procedimientos//
    public void agregarProcedimiento(Datos_procedimiento datos_procedimiento,
            boolean generarAux) throws Exception {
        double aux_valor_cirujano = dbxAuxValor_cirujano.getValue();
        double aux_valor_anestesiologo = dbxAuxValor_anestesiologo.getValue();
        double aux_valor_ayudante = dbxAuxValor_ayudante.getValue();
        double aux_valor_sala = dbxAuxValor_sala.getValue();
        double aux_valor_materiales = dbxAuxValor_materiales.getValue();

        double aux_valor_perfusionista = dbxAuxValor_perfusionista.getValue();
        // double aux_valor_neto2 = dbxAuxValor_neto2.getValue();

        Admision admision = new Admision();
        admision.setCodigo_empresa(datos_procedimiento.getCodigo_empresa());
        admision.setCodigo_sucursal(datos_procedimiento.getCodigo_sucursal());
        admision.setNro_identificacion(datos_procedimiento
                .getNro_identificacion());
        admision.setNro_ingreso(datos_procedimiento.getNro_ingreso());
        admision = getServiceLocator().getServicio(AdmisionService.class)
                .consultar(admision);

        Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                .getManuales_tarifarios(admision);

        Resolucion resolucion = new Resolucion();
        resolucion.setCodigo_empresa(empresa.getCodigo_empresa());
        resolucion = getServiceLocator().getResolucionService().consultar(
                resolucion);
        String cobra_cirugia = (resolucion != null ? resolucion
                .getCobrar_cirugia() : "N");
        String cobrar_cirugia_soat = (resolucion != null ? resolucion
                .getCobrar_cirugia_soat() : "N");

        if (generarAux) {
            lista_aux.add(datos_procedimiento);

            if (rdbTipo_intervencion.getSelectedItem().getValue().equals("2")) {
                Datos_procedimiento datos_procedimiento2 = new Datos_procedimiento(
                        datos_procedimiento);
                lista_aux.add(datos_procedimiento2);
            }
        }

        Utilidades.ordenarProcedimientosQuirurgicos(lista_aux,
                manuales_tarifarios, getServiceLocator());
        lista_datos.clear();

        for (int i = 0; i < lista_aux.size(); i++) {
            Datos_procedimiento data = lista_aux.get(i);

            Datos_procedimiento aux = new Datos_procedimiento(data);

            if (i == 0) {
                lista_datos.add(aux);
            } else {
                // Consultamos el porcentaje de cirugías //
                String aux_manual = (tbxManual.getValue().equals("SOAT") ? "SOAT"
                        : "ISS");
                Porcentajes_cirugias porcentajes_cirugias = new Porcentajes_cirugias();
                porcentajes_cirugias.setManual_tarifario(aux_manual);
                porcentajes_cirugias.setTipo_intervencion(rdbTipo_intervencion
                        .getSelectedItem().getValue() + "");
                // //log.info("porcentajes_cirugias antes: "+porcentajes_cirugias);
                porcentajes_cirugias = getServiceLocator()
                        .getPorcentajes_cirugiasService().consultar(
                                porcentajes_cirugias);
                // //log.info("porcentajes_cirugias despues: "+porcentajes_cirugias);
                if (porcentajes_cirugias == null) {
                    throw new Exception(
                            "No se ha definido porcentaje de cirugías");
                }

                aux.setValor_cirujano(aux.getValor_cirujano()
                        * porcentajes_cirugias.getPorcentaje_cirujano());
                aux.setValor_anestesiologo(aux.getValor_anestesiologo()
                        * porcentajes_cirugias.getPorcentaje_anestesiologo());
                aux.setValor_ayudante(aux.getValor_ayudante()
                        * porcentajes_cirugias.getPorcentaje_ayudante());
                aux.setValor_sala(aux.getValor_sala()
                        * porcentajes_cirugias.getPorcentaje_sala());
                aux.setValor_materiales(aux.getValor_materiales()
                        * porcentajes_cirugias.getPorcentaje_materiales());
                aux.setValor_perfusionista(aux.getValor_perfusionista()
                        * porcentajes_cirugias.getPorcentaje_perfusionista());

                validarCantidad_cirugias(aux, cobrar_cirugia_soat,
                        cobra_cirugia, i);

                Datos_procedimiento aux2 = new Datos_procedimiento();
                aux2.setValor_cirujano(aux_valor_cirujano
                        * porcentajes_cirugias.getPorcentaje_cirujano());
                aux2.setValor_anestesiologo(aux_valor_anestesiologo
                        * porcentajes_cirugias.getPorcentaje_anestesiologo());
                aux2.setValor_ayudante(aux_valor_ayudante
                        * porcentajes_cirugias.getPorcentaje_ayudante());
                aux2.setValor_sala(aux_valor_sala
                        * porcentajes_cirugias.getPorcentaje_sala());
                aux2.setValor_materiales(aux_valor_materiales
                        * porcentajes_cirugias.getPorcentaje_materiales());
                aux2.setValor_perfusionista(aux_valor_perfusionista
                        * porcentajes_cirugias.getPorcentaje_perfusionista());

                validarCantidad_cirugias(aux2, cobrar_cirugia_soat,
                        cobra_cirugia, i);

                aux.setValor_neto(aux2.getValor_cirujano()
                        + aux2.getValor_anestesiologo()
                        + aux2.getValor_ayudante() + aux2.getValor_sala()
                        + aux2.getValor_materiales() + aux_valor_perfusionista);

                lista_datos.add(aux);

            }

        }

        crearFilas();
        quitarPcd();
    }

	// Este metodo es para validar cuano las cirugías se pasen de mas de dos
    // registros y sean intervencion multiple igual vía//
    public void validarCantidad_cirugias(Datos_procedimiento aux,
            String cobrar_cirugia_soat, String cobra_cirugia, int i) {

        if (rdbTipo_intervencion.getSelectedItem().getValue().equals("3")) {
            if (tbxManual.getValue().equals("SOAT")) {
                if (cobrar_cirugia_soat.equals("N")) {
                    aux.setValor_sala(0.0);
                    aux.setValor_materiales(0.0);
                }
            } else {
                if (i > 1) {
                    if (cobra_cirugia.equals("N")) {
                        aux.setValor_cirujano(0.0);
                        aux.setValor_anestesiologo(0.0);
                        aux.setValor_ayudante(0.0);
                        aux.setValor_sala(0.0);
                        aux.setValor_materiales(0.0);
                    }
                }
            }
        }
    }

    public void crearFilas() throws Exception {
        rowsProcedimientos.getChildren().clear();
        for (int j = 0; j < lista_datos.size(); j++) {
            Datos_procedimiento bean = lista_datos.get(j);
            crearFilaDatos_procedimiento(bean, j);
        }
        calcularTotal();
    }

    public void crearFilaDatos_procedimiento(Datos_procedimiento bean, int j)
            throws Exception {

        final int index = j;

        Admision admision = lbxNro_ingreso.getSelectedItem().getValue();
        Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                .getManuales_tarifarios(admision);

        String codigo_procedimiento = bean.getCodigo_procedimiento();
        Map<String, Object> beanPcd = Utilidades.getProcedimiento(
                manuales_tarifarios, new Long(codigo_procedimiento),
                getServiceLocator());
        String nombre_procedimiento = (String) beanPcd
                .get("nombre_procedimiento");
        String grupo = bean.getGrupo();

        final Row fila = new Row();
        fila.setStyle("text-align: center;nowrap:nowrap");

        // Columna borrar //
        Cell cell = new Cell();
        cell.setAlign("left");
        cell.setValign("top");
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
                                    lista_datos.remove(index);
                                    lista_aux.remove(index);
                                    Admision admision = lbxNro_ingreso
                                    .getSelectedItem().getValue();
                                    Manuales_tarifarios manuales_tarifarios = ServiciosDisponiblesUtils
                                    .getManuales_tarifarios(admision);
                                    Utilidades
                                    .ordenarProcedimientosQuirurgicos(
                                            lista_datos,
                                            manuales_tarifarios,
                                            getServiceLocator());
                                    Utilidades
                                    .ordenarProcedimientosQuirurgicos(
                                            lista_aux,
                                            manuales_tarifarios,
                                            getServiceLocator());
                                    crearFilas();
                                }
                            }
                        });
            }
        });

        // Columna codigo //
        cell = new Cell();
        cell.setAlign("left");
        final Textbox tbxCodigo_procedimiento = new Textbox(
                codigo_procedimiento);
        tbxCodigo_procedimiento.setInplace(true);
        tbxCodigo_procedimiento.setId("codigo_procedimiento_" + j);
        tbxCodigo_procedimiento.setHflex("1");
        tbxCodigo_procedimiento.setReadonly(true);
        cell.appendChild(tbxCodigo_procedimiento);
        fila.appendChild(cell);

        // Columna nombre //
        cell = new Cell();
        cell.setAlign("left");
        final Textbox tbxNombre_procedimiento = new Textbox(
                nombre_procedimiento);
        tbxNombre_procedimiento.setInplace(true);
        tbxNombre_procedimiento.setId("nombre_procedimiento_" + j);
        tbxNombre_procedimiento.setHflex("1");
        tbxNombre_procedimiento.setReadonly(true);
        cell.appendChild(tbxNombre_procedimiento);
        fila.appendChild(cell);

        // Columna grupo //
        cell = new Cell();
        cell.setAlign("left");
        final Textbox tbxGrupo = new Textbox(grupo);
        tbxGrupo.setInplace(true);
        tbxGrupo.setId("grupo_" + j);
        tbxGrupo.setHflex("1");
        tbxGrupo.setReadonly(true);
        cell.appendChild(tbxGrupo);
        fila.appendChild(cell);

        // Columna valor cirujano //
        cell = new Cell();
        cell.setAlign("left");
        final Doublebox tbxCirujano = new Doublebox(bean.getValor_cirujano());
        tbxCirujano.setFormat("#,##0.00");
        tbxCirujano.setInplace(true);
        tbxCirujano.setId("cirujano_" + j);
        tbxCirujano.setHflex("1");
        tbxCirujano.setReadonly(true);
        cell.appendChild(tbxCirujano);
        fila.appendChild(cell);

        // Columna valor anestesiologo //
        cell = new Cell();
        cell.setAlign("left");
        final Doublebox tbxAnestesiologo = new Doublebox(
                bean.getValor_anestesiologo());
        tbxAnestesiologo.setFormat("#,##0.00");
        tbxAnestesiologo.setInplace(true);
        tbxAnestesiologo.setId("anestesiologo_" + j);
        tbxAnestesiologo.setHflex("1");
        tbxAnestesiologo.setReadonly(true);
        cell.appendChild(tbxAnestesiologo);
        fila.appendChild(cell);

        // Columna valor ayudante //
        cell = new Cell();
        cell.setAlign("left");
        final Doublebox tbxAyudante = new Doublebox(bean.getValor_ayudante());
        tbxAyudante.setFormat("#,##0.00");
        tbxAyudante.setInplace(true);
        tbxAyudante.setId("ayudante_" + j);
        tbxAyudante.setHflex("1");
        tbxAyudante.setReadonly(true);
        cell.appendChild(tbxAyudante);
        fila.appendChild(cell);

        // Columna valor sala //
        cell = new Cell();
        cell.setAlign("left");
        final Doublebox tbxSala = new Doublebox(bean.getValor_sala());
        tbxSala.setFormat("#,##0.00");
        tbxSala.setInplace(true);
        tbxSala.setId("sala_" + j);
        tbxSala.setHflex("1");
        tbxSala.setReadonly(true);
        cell.appendChild(tbxSala);
        fila.appendChild(cell);

        // Columna valor materiales //
        cell = new Cell();
        cell.setAlign("left");
        final Doublebox tbxMateriales = new Doublebox(
                bean.getValor_materiales());
        tbxMateriales.setFormat("#,##0.00");
        tbxMateriales.setInplace(true);
        tbxMateriales.setId("materiales_" + j);
        tbxMateriales.setHflex("1");
        tbxMateriales.setReadonly(true);
        cell.appendChild(tbxMateriales);
        fila.appendChild(cell);

        // Columna valor perfusionista //
        cell = new Cell();
        cell.setAlign("left");
        final Doublebox tbxPerfusionista = new Doublebox(
                bean.getValor_perfusionista());
        tbxPerfusionista.setFormat("#,##0.00");
        tbxPerfusionista.setInplace(true);
        tbxPerfusionista.setId("perfusionista_" + j);
        tbxPerfusionista.setHflex("1");
        tbxPerfusionista.setReadonly(true);
        cell.appendChild(tbxPerfusionista);
        fila.appendChild(cell);

        // Columna valor total //
        cell = new Cell();
        cell.setAlign("left");
        final Doublebox tbxTotal = new Doublebox(
                (bean.getValor_cirujano() + bean.getValor_anestesiologo()
                + bean.getValor_ayudante() + bean.getValor_sala()
                + bean.getValor_materiales() + bean
                .getValor_perfusionista()));
        tbxTotal.setFormat("#,##0.00");
        tbxTotal.setInplace(true);
        tbxTotal.setId("total_" + j);
        tbxTotal.setHflex("1");
        tbxTotal.setReadonly(true);
        cell.appendChild(tbxTotal);
        fila.appendChild(cell);

        fila.setId("fila_" + j);

        rowsProcedimientos.appendChild(fila);

        gridProcedimientos.setVisible(true);
        gridProcedimientos.setMold("paging");
        gridProcedimientos.setPageSize(20);
        gridProcedimientos.applyProperties();
        gridProcedimientos.invalidate();
    }

    public void calcularTotal() {
        dbxValor_neto.setValue(0.0);
        double sum = 0;
        for (Datos_procedimiento aux : lista_datos) {
            sum += (aux.getValor_cirujano() + aux.getValor_anestesiologo()
                    + aux.getValor_ayudante() + aux.getValor_sala()
                    + aux.getValor_materiales() + aux.getValor_perfusionista());
        }
        dbxValor_neto.setValue(sum);
    }

	// Metodo para calcular valores al seleccionar un check
    // (Cirujano,Anestesiologo,Ayudante,Sala o Materiales)//
    public void calcularValorServicio(String tipo, boolean checked,
            Doublebox doubleboxValor, Doublebox auxValor) {
        try {
            Resolucion resolucion = new Resolucion();
            resolucion.setCodigo_empresa(empresa.getCodigo_empresa());
            resolucion = getServiceLocator().getResolucionService().consultar(
                    resolucion);
            String cobra_materiales = (resolucion != null ? resolucion
                    .getCobrar_materiales() : "N");

            String manual = tbxManual.getValue();
            String grupo = tbxGrupo.getValue();
            String tipo_grupo = Utilidades.getTipo_grupo_cirugia(tipo,
                    tbxManual.getValue());

            Admision admision = lbxNro_ingreso.getSelectedItem().getValue();

            Map<String, Object> parameters = new HashMap();
            parameters.put("codigo_empresa", empresa.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parameters.put("nro_identificacion",
                    tbxNro_identificacion.getValue());
            parameters.put("nro_ingreso", admision.getNro_ingreso());
            parameters.put("anio", tbxAnio.getValue());
            parameters.put("manual", tbxManual.getValue());
            parameters.put("grupo", tbxGrupo.getValue());
            parameters.put("tipo_grupo", tipo_grupo);
            parameters.put("cobra", checked ? "S" : "N");
            parameters.put("uvr", dbxUvr.getValue());
            parameters.put("tipo_sala", lbxTipo_sala.getSelectedItem()
                    .getValue());
            // //log.info("parameters cirug: "+parameters);
            double result[] = getServiceLocator()
                    .getDatos_procedimientoService().consultarValorCirugia(
                            parameters);

            doubleboxValor.setValue(result[0]);
            auxValor.setValue(result[1]);

            if (tipo.equals("1")) {
                tbxCodigo_prestador.setDisabled(checked ? false : true);
                imgQuitar_med.setVisible(!checked ? false : true);
                if (!checked) {
                    tbxCodigo_prestador.setValue("");
                    tbxNomMedico.setValue("");
                }
            } else if (tipo.equals("2")) {
                tbxCodigo_anestesiologo.setDisabled(checked ? false : true);
                imgQuitar_ana.setVisible(!checked ? false : true);
                if (!checked) {
                    tbxCodigo_anestesiologo.setValue("");
                    tbxNomAnestesiologo.setValue("");
                }
            } else if (tipo.equals("3")) {
                tbxCodigo_ayudante.setDisabled(checked ? false : true);
                imgQuitar_ayu.setVisible(!checked ? false : true);
                if (!checked) {
                    tbxCodigo_ayudante.setValue("");
                    tbxNomAyudante.setValue("");
                }
            }

            if (manual.equals("SOAT")
                    && tipo.equals("5")
                    && (grupo.equals("20") || grupo.equals("21")
                    || grupo.equals("22") || grupo.equals("22"))
                    && cobra_materiales.equals("S")) {
                dbxValor_materiales.setReadonly(false);
                dbxValor_materiales.focus();
            } else if (manual.equals("ISS01") && tipo.equals("5")
                    && (dbxUvr.getValue().doubleValue() > 170)
                    && cobra_materiales.equals("S")) {
                dbxValor_materiales.setReadonly(false);
                dbxValor_materiales.focus();
            } else {
                dbxValor_materiales.setReadonly(true);
            }

            calcularSubtotal();

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void selectedTipo_intervension(String tipo_intervension)
            throws Exception {
        if (!lista_datos.isEmpty()) {
            Messagebox
                    .show("¿Desea realmente cambiar de tipo de intervension ?\nEsta operacion borrará los procedimientos agregados. ",
                            "Confirmacion", Messagebox.YES + Messagebox.NO,
                            Messagebox.QUESTION,
                            new org.zkoss.zk.ui.event.EventListener() {
                                public void onEvent(Event event)
                                throws Exception {
                                    if ("onYes".equals(event.getName())) {
                                        // do the thing
                                        borrarTodo();
                                    } else {
                                        ((Radio) groupboxEditar
                                        .getFellow("radioTipo_intervencion"
                                                + tbxTipo_int
                                                .getValue()))
                                        .setChecked(true);
                                    }
                                }
                            });
        }
        tbxTipo_int.setValue(tipo_intervension);
        /*
         * //log.info("tipo_intervension: "+(Integer.parseInt(tipo_intervension)-
         * 1) );
         * //log.info("Forma_realizacion size: "+lbxForma_realizacion.getItemCount
         * ());
         */
        if (tipo_intervension.equals("4")) {
            lbxForma_realizacion.setSelectedIndex(0);
        } else if (tipo_intervension.equals("5")) {
            lbxForma_realizacion.setSelectedIndex(3);
        } else {
            lbxForma_realizacion.setSelectedIndex(Integer
                    .parseInt(tipo_intervension) - 1);
        }
    }

    public void selectedIncruento(boolean checked) throws Exception {
        final boolean check = checked;
        if (!lista_datos.isEmpty()) {
            Messagebox
                    .show("¿Desea realmente cambiar de tipo de intervension ?\nEsta operacion borrará los procedimientos agregados. ",
                            "Confirmacion", Messagebox.YES + Messagebox.NO,
                            Messagebox.QUESTION,
                            new org.zkoss.zk.ui.event.EventListener() {
                                public void onEvent(Event event)
                                throws Exception {
                                    if ("onYes".equals(event.getName())) {
                                        // do the thing
                                        tbxTipo_int.setValue("1");
                                        borrarTodo();
                                    } else {
                                        chbIncruento.setChecked(!check);
                                        return;
                                    }
                                }
                            });
        }
        tbxTipo_int.setValue("1");
        quitarPcd();
        chbCirugia_conjunto.setChecked(!checked);
        for (int i = 2; i <= 5; i++) {
            if (i != 4) {
                ((Radio) groupboxEditar.getFellow("radioTipo_intervencion" + i))
                        .setDisabled(checked);
            }
        }
        if (checked) {
            ((Radio) groupboxEditar.getFellow("radioTipo_intervencion1"))
                    .setChecked(true);
        }

    }

    public void selectedCirugia_conjunto(boolean checked) throws Exception {
        final boolean check = checked;
        if (!lista_datos.isEmpty()) {
            Messagebox
                    .show("¿Desea realmente cambiar de tipo de intervension ?\nEsta operacion borrará los procedimientos agregados. ",
                            "Confirmacion", Messagebox.YES + Messagebox.NO,
                            Messagebox.QUESTION,
                            new org.zkoss.zk.ui.event.EventListener() {
                                public void onEvent(Event event)
                                throws Exception {
                                    if ("onYes".equals(event.getName())) {
                                        // do the thing
                                        tbxTipo_int.setValue("3");
                                        borrarTodo();
                                    } else {
                                        chbCirugia_conjunto.setChecked(!check);
                                        return;
                                    }
                                }
                            });
        }
        tbxTipo_int.setValue("3");
        quitarPcd();
        chbCirugia_conjunto.setChecked(!checked);
        for (int i = 2; i <= 5; i++) {
            if (i != 3) {
                ((Radio) groupboxEditar.getFellow("radioTipo_intervencion" + i))
                        .setDisabled(checked);
            }
        }
        if (checked) {
            ((Radio) groupboxEditar.getFellow("radioTipo_intervencion3"))
                    .setChecked(true);
        }

    }

    public void selectedTipo_sala(String value, int index) throws Exception {
        final int indexx = index;
        if (!lista_datos.isEmpty()) {
            Messagebox
                    .show("¿Desea realmente cambiar de tipo de intervension ?\nEsta operacion borrará los procedimientos agregados. ",
                            "Confirmacion", Messagebox.YES + Messagebox.NO,
                            Messagebox.QUESTION,
                            new org.zkoss.zk.ui.event.EventListener() {
                                public void onEvent(Event event)
                                throws Exception {
                                    if ("onYes".equals(event.getName())) {
                                        // do the thing
                                        borrarTodo();
                                    } else {
                                        if (indexx == 0) {
                                            lbxTipo_sala.setSelectedIndex(1);
                                        } else {
                                            lbxTipo_sala.setSelectedIndex(0);
                                        }
                                        return;
                                    }
                                }
                            });
        }

    }

    public void borrarTodo() throws Exception {
        lista_aux.clear();
        lista_datos.clear();
        crearFilas();
    }

    public void calcularSubtotal() {
        double total = 0;
        double total_real = 0;

        if (dbxValor_materiales.getValue() == null) {
            MensajesUtil.mensajeAlerta("Alerta !!!",
                    "El valor del material es invalido");
            return;
        }

        total = dbxValor_cirujano.getValue()
                + dbxValor_anestesiologo.getValue()
                + dbxValor_ayudante.getValue() + dbxValor_sala.getValue()
                + dbxValor_materiales.getValue()
                + dbxValor_perfusionista.getValue();
        total_real = dbxAuxValor_cirujano.getValue()
                + dbxAuxValor_anestesiologo.getValue()
                + dbxAuxValor_ayudante.getValue() + dbxAuxValor_sala.getValue()
                + dbxAuxValor_materiales.getValue()
                + dbxAuxValor_perfusionista.getValue();
		// total_real =
        // parseFloat(document.form1.aux_valor_cirujano.value)+parseFloat(document.form1.aux_valor_anestesiologo.value)+parseFloat(document.form1.aux_valor_ayudante.value)+parseFloat(document.form1.aux_valor_sala.value)+aux_valor_materiales+parseFloat(document.form1.aux_valor_perfusionista.value);
        dbxValor_neto2.setValue(total);
        dbxAuxValor_neto2.setValue(total_real);
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

    public void imprimir(String nro_factura) throws Exception {
        if (nro_factura.equals("")) {
            Messagebox.show("No se ha guardado el registro", "Informacion ..",
                    Messagebox.OK, Messagebox.INFORMATION);
            return;
        }

        Map paramRequest = new HashMap();
        paramRequest.put("name_report", "Procedimiento_multiple");
        paramRequest.put("nro_factura", nro_factura);
        paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
                .toString());

        Component componente = Executions.createComponents(
                "/pages/printInformes.zul", this, paramRequest);
        final Window window = (Window) componente;
        window.setMode("modal");
        window.setMaximizable(true);
        window.setMaximized(true);
    }

}
