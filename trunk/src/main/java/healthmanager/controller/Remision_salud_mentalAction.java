/*
 * remision_salud_mentalAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Adicional_paciente;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Barrio;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Centro_barrio;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Localidad;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Remision_salud_mental;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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

import healthmanager.modelo.service.GeneralExtraService;

public class Remision_salud_mentalAction extends Borderlayout implements
        AfterCompose {

    private static Logger log = Logger
            .getLogger(Remision_salud_mentalAction.class);
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
    private Textbox tbxIdentificacion;
    private Datebox dtbxFecha_inicial;
    private Listbox lbxAtendida;

    private Textbox tbxEstado;
    private Textbox tbxCodigo_administradora;
    private Bandbox tbxNro_ingreso;
    // private Textbox tbxTipo_doc;
    private Textbox tbxNomPaciente;
    private Textbox tbxAdministradora;
    private Textbox tbxContrato;
    // private Textbox tbxCodigo_contrato;

    private Textbox tbxId_plan;
    private Textbox tbxEdad;
    private Textbox tbxSexo;
    private Textbox tbxDireccion;
    private Textbox tbxTelefono;
    private Textbox tbxNacimiento;
    private Textbox tbxBarrio;
    private Textbox tbxLocalidad;

    private Textbox tbxCentro_atencion;
    private Textbox tbxRemitido;
    private Textbox tbxCual_remitido;
    private Textbox tbxMotivo;
    private Listbox lbxFinalidad_cons;
    private Listbox lbxCausas_externas;
    private Textbox tbxOtros_consulta;
    private Listbox lbxTipo_disnostico;

    private Bandbox tbxTipo_principal;
    private Textbox tbxNomDx;
    // private Textbox tbxPlan;

    private Bandbox tbxTipo_relacionado_1;
    private Bandbox tbxTipo_relacionado_2;
    private Bandbox tbxTipo_relacionado_3;
    private Textbox tbxNomDxRelacionado_1;
    private Textbox tbxNomDxRelacionado_2;
    private Textbox tbxNomDxRelacionado_3;

    @Override
    public void afterCompose() {
        loadComponents();
        cargarDatosSesion();
        listarCombos();
    }

    public void loadComponents() {
        form = (Borderlayout) this.getFellow("formRemision_salud_mental");

        lbxParameter = (Listbox) form.getFellow("lbxParameter");
        tbxValue = (Textbox) form.getFellow("tbxValue");
        tbxAccion = (Textbox) form.getFellow("tbxAccion");
        groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
        groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
        rowsResultado = (Rows) form.getFellow("rowsResultado");
        gridResultado = (Grid) form.getFellow("gridResultado");

        tbxCodigo_historia = (Textbox) form.getFellow("tbxCodigo_historia");
        tbxIdentificacion = (Textbox) form.getFellow("tbxIdentificacion");
        dtbxFecha_inicial = (Datebox) form.getFellow("dtbxFecha_inicial");
        lbxAtendida = (Listbox) form.getFellow("lbxAtendida");

        tbxCodigo_administradora = (Textbox) form
                .getFellow("tbxCodigo_administradora");
        tbxEstado = (Textbox) form.getFellow("tbxEstado");
        tbxNro_ingreso = (Bandbox) form.getFellow("tbxNro_ingreso");
        // tbxTipo_doc = (Textbox)form.getFellow("tbxTipo_doc");
        tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
        tbxAdministradora = (Textbox) form.getFellow("tbxAdministradora");
        tbxContrato = (Textbox) form.getFellow("tbxContrato");
        // tbxCodigo_contrato = (Textbox)form.getFellow("tbxCodigo_contrato");

        tbxId_plan = (Textbox) form.getFellow("tbxId_plan");
        tbxSexo = (Textbox) form.getFellow("tbxSexo");
        tbxEdad = (Textbox) form.getFellow("tbxEdad");
        tbxDireccion = (Textbox) form.getFellow("tbxDireccion");
        tbxTelefono = (Textbox) form.getFellow("tbxTelefono");
        tbxNacimiento = (Textbox) form.getFellow("tbxNacimiento");
        tbxBarrio = (Textbox) form.getFellow("tbxBarrio");
        tbxLocalidad = (Textbox) form.getFellow("tbxLocalidad");

        tbxCentro_atencion = (Textbox) form.getFellow("tbxCentro_atencion");
        tbxRemitido = (Textbox) form.getFellow("tbxRemitido");
        tbxCual_remitido = (Textbox) form.getFellow("tbxCual_remitido");
        tbxMotivo = (Textbox) form.getFellow("tbxMotivo");

        lbxFinalidad_cons = (Listbox) form.getFellow("lbxFinalidad_cons");
        lbxCausas_externas = (Listbox) form.getFellow("lbxCausas_externas");
        tbxOtros_consulta = (Textbox) form.getFellow("tbxOtros_consulta");
        lbxTipo_disnostico = (Listbox) form.getFellow("lbxTipo_disnostico");
        tbxTipo_principal = (Bandbox) form.getFellow("tbxTipo_principal");
        tbxNomDx = (Textbox) form.getFellow("tbxNomDx");
        tbxTipo_relacionado_1 = (Bandbox) form
                .getFellow("tbxTipo_relacionado_1");
        tbxTipo_relacionado_2 = (Bandbox) form
                .getFellow("tbxTipo_relacionado_2");
        tbxTipo_relacionado_3 = (Bandbox) form
                .getFellow("tbxTipo_relacionado_3");
        tbxNomDxRelacionado_1 = (Textbox) form
                .getFellow("tbxNomDxRelacionado_1");
        tbxNomDxRelacionado_2 = (Textbox) form
                .getFellow("tbxNomDxRelacionado_2");
        tbxNomDxRelacionado_3 = (Textbox) form
                .getFellow("tbxNomDxRelacionado_3");

    }

    public void initRemision_salud_mental() throws Exception {
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
        listarAtendida();

        List<Elemento> elementos = this.getServiceLocator()
                .getElementoService().listar("ante_familiares");
        /* tipos de diasnosticos */
        elementos = this.getServiceLocator().getElementoService()
                .listar("tipo_diagnostico");
        listarElementoListboxFromType(lbxTipo_disnostico, true, elementos,
                false);

        /* listar finalidad */
        elementos = this.getServiceLocator().getElementoService()
                .listar("finalidad_cons");
        listarElementoListboxFromType(lbxFinalidad_cons, false, elementos,
                false);

        /* causas externas */
        elementos = this.getServiceLocator().getElementoService()
                .listar("causa_externa");
        listarElementoListboxFromType(lbxCausas_externas, true, elementos,
                false);
    }

    public void listarAtendida() {
        lbxAtendida.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue(false);
        listitem.setLabel("Pendiente");
        lbxAtendida.appendChild(listitem);

        listitem = new Listitem();
        listitem.setValue(true);
        listitem.setLabel("Atendida");
        lbxAtendida.appendChild(listitem);

        if (lbxAtendida.getItemCount() > 0) {
            lbxAtendida.setSelectedIndex(0);
        }
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
//		listitem.setValue("codigo_historia");
//		listitem.setLabel("Codigo_historia");
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

        tbxNro_ingreso
                .setStyle("text-transform:uppercase;background-color:white");
        lbxTipo_disnostico
                .setStyle("text-transform:uppercase;background-color:white");
        tbxCual_remitido
                .setStyle("text-transform:uppercase;background-color:white");
        tbxMotivo.setStyle("text-transform:uppercase;background-color:white");

        lbxFinalidad_cons
                .setStyle("text-transform:uppercase;background-color:white");
        lbxCausas_externas
                .setStyle("text-transform:uppercase;background-color:white");
        lbxTipo_disnostico
                .setStyle("text-transform:uppercase;background-color:white");
        tbxTipo_principal
                .setStyle("text-transform:uppercase;background-color:white");

        String mensaje = "Los campos marcados con (*) son obligatorios";

        boolean valida = true;

        if (tbxNro_ingreso.getValue().equals("")) {
            throw new Exception("Seleccione un ingreso de paciente");
        }

        if (tbxNro_ingreso.getText().trim().equals("")) {
            tbxNro_ingreso
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (tbxCual_remitido.getText().trim().equals("")) {
            tbxCual_remitido
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (tbxMotivo.getText().trim().equals("")) {
            tbxMotivo
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (lbxCausas_externas.getSelectedIndex() == 0) {
            lbxCausas_externas
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (lbxFinalidad_cons.getSelectedIndex() == 0) {
            lbxFinalidad_cons
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (lbxTipo_disnostico.getSelectedIndex() == 0) {
            lbxTipo_disnostico
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (tbxTipo_principal.getText().trim().equals("")) {

            tbxTipo_principal
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            mensaje = "Debe digitar la impresion diagnostica";
            valida = false;

        } else if (vaidarIgualdad(tbxTipo_principal.getText(),
                tbxTipo_relacionado_1.getText(),
                tbxTipo_relacionado_2.getText(),
                tbxTipo_relacionado_3.getText())) {
            mensaje = "no se puede repetir la impresion diagnostica";
            valida = false;
        }

        if (!valida) {
            Messagebox.show(mensaje,
                    usuarios.getNombres() + " recuerde que...", Messagebox.OK,
                    Messagebox.EXCLAMATION);
        }

        //log.info(mensaje);
        return valida;
    }

    private boolean vaidarIgualdad(String... in) {
        Map map = new HashMap();
        for (String inO : in) {
            if (!inO.trim().isEmpty()) {
                if (map.containsKey(inO)) {
                    return true;
                }
                map.put(inO, inO);
            }
        }
        return false;
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

            getServiceLocator().getRemision_salud_mentalService().setLimit(
                    "limit 25 offset 0");

            List<Remision_salud_mental> lista_datos = getServiceLocator()
                    .getRemision_salud_mentalService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Remision_salud_mental remision_salud_mental : lista_datos) {
                rowsResultado.appendChild(crearFilas(remision_salud_mental,
                        this));
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

        final Remision_salud_mental remision_salud_mental = (Remision_salud_mental) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(remision_salud_mental.getCodigo_historia()
                + ""));
        fila.appendChild(new Label(remision_salud_mental.getIdentificacion()
                + ""));
        fila.appendChild(new Label(remision_salud_mental.getFecha_inicial()
                + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {

                mostrarDatos(remision_salud_mental);
            }
        });
        hbox.appendChild(img);

        img = new Image();
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
                                    eliminarDatos(remision_salud_mental);
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

                if (tbxNro_ingreso.getValue().equals("")) {
                    throw new Exception("Seleccione un ingreso de paciente");
                }
                Admision admision = new Admision();
                admision.setCodigo_empresa(empresa.getCodigo_empresa());
                admision.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                admision.setNro_identificacion(tbxIdentificacion.getValue());
                admision.setNro_ingreso(tbxNro_ingreso.getValue());
                admision = getServiceLocator().getAdmisionService().consultar(
                        admision);
                if (admision == null) {
                    throw new Exception("No hay admision que actualizar");
                }
                admision.setAtendida((Boolean) lbxAtendida.getSelectedItem()
                        .getValue());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("admision", admision);
//				map.put("parametros_empresa", parametros_empresa);
                getServiceLocator().getAdmisionService().actualizar(map);

                Map datos = new HashMap();

                Paciente paciente = new Paciente();
                paciente.setCodigo_empresa(admision.getCodigo_empresa());
                paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
                paciente.setNro_identificacion(admision.getNro_identificacion());
                paciente = getServiceLocator().getPacienteService().consultar(
                        paciente);

                Adicional_paciente adicional_paciente = new Adicional_paciente();
                adicional_paciente.setCodigo_empresa(paciente.getCodigo_empresa());
                adicional_paciente.setCodigo_sucursal(paciente.getCodigo_sucursal());
                adicional_paciente.setNro_identificacion(paciente
                        .getNro_identificacion());
                adicional_paciente = getServiceLocator().getServicio(
                        GeneralExtraService.class).consultar(adicional_paciente);

                Centro_barrio centro_barrio = new Centro_barrio();
                centro_barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente.getCodigo_barrio() : "");
                centro_barrio = getServiceLocator().getServicio(GeneralExtraService.class).consultar(centro_barrio);
                //log.info("centro_barrio: " + centro_barrio);

                Barrio barrio = new Barrio();
                barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente.getCodigo_barrio() : "");
                barrio = getServiceLocator().getBarrioService().consultar(barrio);
                //log.info("barrio: " + barrio);

                Centro_atencion centro = new Centro_atencion();
                centro.setCodigo_empresa(paciente.getCodigo_empresa());
                centro.setCodigo_sucursal(paciente.getCodigo_sucursal());
                centro.setCodigo_centro(centro_barrio.getCodigo_centro());
                centro = getServiceLocator().getCentro_atencionService().consultar(centro);
                //log.info("centro: " + centro);

                Remision_salud_mental remision_salud_mental = new Remision_salud_mental();
                remision_salud_mental.setCodigo_empresa(empresa
                        .getCodigo_empresa());
                remision_salud_mental.setCodigo_sucursal(sucursal
                        .getCodigo_sucursal());
                remision_salud_mental.setCodigo_historia(tbxCodigo_historia
                        .getValue());
                remision_salud_mental.setIdentificacion(tbxIdentificacion
                        .getValue());
                remision_salud_mental.setFecha_inicial(new Timestamp(
                        dtbxFecha_inicial.getValue().getTime()));
                remision_salud_mental.setNro_ingreso(tbxNro_ingreso.getValue());
                remision_salud_mental.setCentro_atencion(centro.getCodigo_centro());
                remision_salud_mental.setRemitido(tbxRemitido.getValue());
                remision_salud_mental.setCual_remitido(tbxCual_remitido
                        .getValue());
                remision_salud_mental.setMotivo(tbxMotivo.getValue());
                remision_salud_mental.setFinalidad_cons(lbxFinalidad_cons
                        .getSelectedItem().getValue().toString());
                remision_salud_mental.setCausas_externas(lbxCausas_externas
                        .getSelectedItem().getValue().toString());
                remision_salud_mental
                        .setOtros_consulta_externa(tbxOtros_consulta.getValue());
                remision_salud_mental.setTipo_disnostico(lbxTipo_disnostico
                        .getSelectedItem().getValue().toString());
                remision_salud_mental.setTipo_principal(tbxTipo_principal
                        .getValue());
                remision_salud_mental
                        .setTipo_relacionado_1(tbxTipo_relacionado_1.getValue());
                remision_salud_mental
                        .setTipo_relacionado_2(tbxTipo_relacionado_2.getValue());
                remision_salud_mental
                        .setTipo_relacionado_3(tbxTipo_relacionado_3.getValue());

                datos.put("codigo_historia", remision_salud_mental);
                datos.put("accion", tbxAccion.getText());

                remision_salud_mental = getServiceLocator()
                        .getRemision_salud_mentalService().guardar(datos);
                tbxCodigo_historia.setValue(remision_salud_mental
                        .getCodigo_historia());

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
        Remision_salud_mental remision_salud_mental = (Remision_salud_mental) obj;
        try {
            tbxCodigo_historia.setValue(remision_salud_mental
                    .getCodigo_historia());
            tbxIdentificacion.setValue(remision_salud_mental
                    .getIdentificacion());
            dtbxFecha_inicial
                    .setValue(remision_salud_mental.getFecha_inicial());
            Admision dato = new Admision();
            dato.setCodigo_empresa(remision_salud_mental.getCodigo_empresa());
            dato.setCodigo_sucursal(remision_salud_mental.getCodigo_sucursal());
            dato.setNro_identificacion(remision_salud_mental
                    .getIdentificacion());
            dato.setNro_ingreso(remision_salud_mental.getNro_ingreso());
            dato = getServiceLocator().getAdmisionService().consultar(dato);
            if (dato == null) {
                throw new Exception("admision no asignada");
            }

            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(dato.getCodigo_empresa());
            paciente.setCodigo_sucursal(dato.getCodigo_sucursal());
            paciente.setNro_identificacion(dato.getNro_identificacion());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);

            Administradora admin = new Administradora();
            admin.setCodigo(dato.getCodigo_administradora());
            admin = getServiceLocator().getAdministradoraService().consultar(
                    admin);

            Contratos contratos = new Contratos();
            contratos.setCodigo_empresa(dato.getCodigo_empresa());
            contratos.setCodigo_sucursal(dato.getCodigo_sucursal());
            contratos.setCodigo_administradora(dato.getCodigo_administradora());
            contratos.setId_plan(dato.getId_plan());
            contratos = getServiceLocator().getContratosService().consultar(contratos);

            tbxEstado.setValue(dato.getEstado());
            tbxCodigo_administradora.setValue(dato.getCodigo_administradora());
            tbxId_plan.setValue(dato.getId_plan());
            tbxNro_ingreso.setValue(dato.getNro_ingreso());

            tbxNomPaciente.setValue((paciente != null ? paciente.getApellido1()
                    + " " + paciente.getApellido2() + " "
                    + paciente.getNombre1() : ""));
            tbxSexo.setValue((paciente != null ? paciente.getSexo() : ""));
            tbxNacimiento.setValue((paciente != null ? new SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()) : ""));
            tbxEdad.setValue((paciente != null ? paciente.getEdad() : ""));
            tbxDireccion.setValue((paciente != null ? paciente.getDireccion()
                    : ""));
            tbxTelefono
                    .setValue((paciente != null ? paciente.getTel_res() : ""));
            // tbxTipo_doc.setValue((tipo_doc!=null?tipo_doc.getDescripcion():""));
            tbxAdministradora.setValue(dato.getCodigo_administradora() + " - "
                    + (admin != null ? admin.getNombre() : ""));
            tbxContrato.setValue((contratos != null ? contratos.getNombre() : ""));
            // tbxCodigo_contrato.setValue(contratos!=null?contratos.getId_plan():"");

            Adicional_paciente adicional_paciente = new Adicional_paciente();
            adicional_paciente.setCodigo_empresa(paciente.getCodigo_empresa());
            adicional_paciente.setCodigo_sucursal(paciente.getCodigo_sucursal());
            adicional_paciente.setNro_identificacion(paciente
                    .getNro_identificacion());
            adicional_paciente = getServiceLocator().getServicio(
                    GeneralExtraService.class).consultar(adicional_paciente);

            Centro_barrio centro_barrio = new Centro_barrio();
            centro_barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente.getCodigo_barrio() : "");
            centro_barrio = getServiceLocator().getServicio(GeneralExtraService.class).consultar(centro_barrio);
            //log.info("centro_barrio: " + centro_barrio);

            Barrio barrio = new Barrio();
            barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente.getCodigo_barrio() : "");
            barrio = getServiceLocator().getBarrioService().consultar(barrio);
            //log.info("barrio: " + barrio);

            Centro_atencion centro = new Centro_atencion();
            centro.setCodigo_empresa(paciente.getCodigo_empresa());
            centro.setCodigo_sucursal(paciente.getCodigo_sucursal());
            centro.setCodigo_centro(centro_barrio.getCodigo_centro());
            centro = getServiceLocator().getCentro_atencionService().consultar(centro);
            //log.info("centro: " + centro);

            Localidad localidad = new Localidad();
            localidad.setCodigo_localidad(barrio.getCodigo_localidad());
            localidad = getServiceLocator().getServicio(GeneralExtraService.class).consultar(localidad);
            //log.info("localidad: " + localidad);

            tbxBarrio.setValue((barrio != null ? barrio.getBarrio() : ""));
            tbxLocalidad.setValue((localidad != null ? localidad.getLocalidad() : ""));
            tbxCentro_atencion.setValue((centro != null ? centro.getNombre_centro() : ""));

            Elemento regimen = new Elemento();
            regimen.setCodigo(paciente.getTipo_usuario());
            regimen.setTipo("tipo_usuario");
            regimen = getServiceLocator().getElementoService().consultar(
                    regimen);

            tbxRemitido.setValue((regimen != null ? regimen.getDescripcion()
                    : ""));
            tbxCual_remitido.setValue(remision_salud_mental.getCual_remitido());
            tbxMotivo.setValue(remision_salud_mental.getMotivo());
            for (int i = 0; i < lbxFinalidad_cons.getItemCount(); i++) {
                Listitem listitem = lbxFinalidad_cons.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(remision_salud_mental.getFinalidad_cons())) {
                    listitem.setSelected(true);
                    i = lbxFinalidad_cons.getItemCount();
                }
            }
            for (int i = 0; i < lbxCausas_externas.getItemCount(); i++) {
                Listitem listitem = lbxCausas_externas.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(remision_salud_mental.getCausas_externas())) {
                    listitem.setSelected(true);
                    i = lbxCausas_externas.getItemCount();
                }
            }
            tbxOtros_consulta.setValue(remision_salud_mental
                    .getOtros_consulta_externa());

            for (int i = 0; i < lbxTipo_disnostico.getItemCount(); i++) {
                Listitem listitem = lbxTipo_disnostico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(remision_salud_mental.getTipo_disnostico())) {
                    listitem.setSelected(true);
                    i = lbxTipo_disnostico.getItemCount();
                }
            }
            Cie cie = new Cie();
            cie.setCodigo(remision_salud_mental.getTipo_principal());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);
            tbxTipo_principal.setValue(remision_salud_mental
                    .getTipo_principal());
            tbxNomDx.setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 1 */
            cie = new Cie();
            cie.setCodigo(remision_salud_mental.getTipo_relacionado_1());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_1.setValue(remision_salud_mental
                    .getTipo_relacionado_1());
            tbxNomDxRelacionado_1
                    .setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 2 */
            cie = new Cie();
            cie.setCodigo(remision_salud_mental.getTipo_relacionado_2());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_2.setValue(remision_salud_mental
                    .getTipo_relacionado_2());
            tbxNomDxRelacionado_2
                    .setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 3 */
            cie = new Cie();
            cie.setCodigo(remision_salud_mental.getTipo_relacionado_3());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_3.setValue(remision_salud_mental
                    .getTipo_relacionado_3());

            tbxNomDxRelacionado_3
                    .setValue((cie != null ? cie.getNombre() : ""));

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
        Remision_salud_mental remision_salud_mental = (Remision_salud_mental) obj;
        try {
            int result = getServiceLocator().getRemision_salud_mentalService()
                    .eliminar(remision_salud_mental);
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

    public void buscarAdmision(String value, String valor_estado, Listbox lbx)
            throws Exception {
        try {
            Map<String, Object> parameters = new HashMap();
            parameters.put("codigo_empresa", empresa.getCodigo_empresa());
            parameters.put("codigo_sucursal", sucursal.getCodigo_sucursal());
            parameters.put("paramTodo", "");
            parameters.put("value", "%" + value.toUpperCase().trim() + "%");
            if (!valor_estado.equals("")) {
                parameters.put("estado", valor_estado);
            }

            parameters.put("limite_paginado",
                    "limit 25 offset 0");

            List<Admision> list = getServiceLocator().getAdmisionService()
                    .listarResultados(parameters);

            lbx.getItems().clear();

            for (Admision dato : list) {

                Paciente paciente = dato.getPaciente();

                String apellidos = (paciente != null ? paciente.getApellido1()
                        + " " + paciente.getApellido2() : "");
                String nombres = (paciente != null ? paciente.getNombre1()
                        + " " + paciente.getNombre2() : "");

                String estado = (dato.getEstado().equals("1") ? "Activo"
                        : "Terminada");

                Listitem listitem = new Listitem();
                listitem.setValue(dato);

                Listcell listcell = new Listcell();
                listcell.appendChild(new Label(dato.getNro_ingreso() + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(dato.getNro_identificacion()
                        + ""));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(apellidos));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(nombres));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm").format(dato.getFecha_ingreso())));
                listitem.appendChild(listcell);

                listcell = new Listcell();
                listcell.appendChild(new Label(estado));
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

    public void selectedAdmision(Listitem listitem) {
        if (listitem.getValue() == null) {
            /*
             * tbxCodigo_prestador.setValue("000000000");
             * tbxNomPrestador.setValue("MEDICO POR DEFECTO");
             */

            tbxEstado.setValue("");
            tbxCodigo_administradora.setValue("");
            tbxId_plan.setValue("");
            tbxNro_ingreso.setValue("");

            tbxIdentificacion.setValue("");
            tbxNomPaciente.setValue("");
            tbxSexo.setValue("");
            tbxNacimiento.setValue("");
            tbxEdad.setValue("");
            tbxDireccion.setValue("");
            tbxTelefono.setValue("");
            // tbxTipo_doc.setValue("");
            tbxAdministradora.setValue("");
            // tbxCodigo_contrato.setValue("");
            tbxContrato.setValue("");
            tbxRemitido.setValue("");
            tbxBarrio.setValue("");
            tbxLocalidad.setValue("");
            tbxCentro_atencion.setValue("");

            lbxAtendida.setSelectedIndex(0);

        } else {

            Admision dato = (Admision) listitem.getValue();
            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(dato.getCodigo_empresa());
            paciente.setCodigo_sucursal(dato.getCodigo_sucursal());
            paciente.setNro_identificacion(dato.getNro_identificacion());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);

            Elemento tipo_doc = new Elemento();
            tipo_doc.setTipo("tipo_id");
            tipo_doc.setCodigo((paciente != null ? paciente
                    .getTipo_identificacion() : ""));
            tipo_doc = getServiceLocator().getElementoService().consultar(
                    tipo_doc);

            Administradora admin = new Administradora();
            admin.setCodigo_empresa(dato.getCodigo_empresa());
            admin.setCodigo_sucursal(dato.getCodigo_sucursal());
            admin.setCodigo(dato.getCodigo_administradora());
            admin = getServiceLocator().getAdministradoraService().consultar(
                    admin);

            Contratos contratos = new Contratos();
            contratos.setCodigo_empresa(dato.getCodigo_empresa());
            contratos.setCodigo_sucursal(dato.getCodigo_sucursal());
            contratos.setCodigo_administradora(dato.getCodigo_administradora());
            contratos.setId_plan(dato.getId_plan());
            contratos = getServiceLocator().getContratosService().consultar(contratos);

            tbxEstado.setValue(dato.getEstado());
            tbxCodigo_administradora.setValue(dato.getCodigo_administradora());
            tbxId_plan.setValue(dato.getId_plan());
            tbxNro_ingreso.setValue(dato.getNro_ingreso());

            tbxIdentificacion.setValue(dato.getNro_identificacion());
            tbxNomPaciente.setValue((paciente != null ? paciente.getApellido1()
                    + " " + paciente.getApellido2() + " "
                    + paciente.getNombre1() : ""));
            tbxSexo.setValue((paciente != null ? paciente.getSexo() : ""));
            tbxNacimiento.setValue((paciente != null ? new SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()) : ""));
            tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
                    paciente.getUnidad_medidad(), false));
            tbxDireccion.setValue((paciente != null ? paciente.getDireccion()
                    : ""));
            tbxTelefono
                    .setValue((paciente != null ? paciente.getTel_res() : ""));
            // tbxTipo_doc.setValue((tipo_doc!=null?tipo_doc.getDescripcion():""));
            tbxAdministradora.setValue(dato.getCodigo_administradora() + " - "
                    + (admin != null ? admin.getNombre() : ""));
            // tbxCodigo_contrato.setValue(contratos!=null?contratos.getId_plan():"");
            tbxContrato.setValue((contratos != null ? contratos.getNombre() : ""));

            Elemento regimen = new Elemento();
            regimen.setCodigo(paciente.getTipo_usuario());
            regimen.setTipo("tipo_usuario");
            regimen = getServiceLocator().getElementoService().consultar(
                    regimen);

            tbxRemitido.setValue((regimen != null ? regimen.getDescripcion()
                    : ""));

            Adicional_paciente adicional_paciente = new Adicional_paciente();
            adicional_paciente.setCodigo_empresa(paciente.getCodigo_empresa());
            adicional_paciente.setCodigo_sucursal(paciente.getCodigo_sucursal());
            adicional_paciente.setNro_identificacion(paciente
                    .getNro_identificacion());
            adicional_paciente = getServiceLocator().getServicio(
                    GeneralExtraService.class).consultar(adicional_paciente);

            Centro_barrio centro_barrio = new Centro_barrio();
            centro_barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente.getCodigo_barrio() : "");
            centro_barrio = getServiceLocator().getServicio(GeneralExtraService.class).consultar(centro_barrio);
            //log.info("centro_barrio: " + centro_barrio);

            Barrio barrio = new Barrio();
            barrio.setCodigo_barrio(adicional_paciente != null ? adicional_paciente.getCodigo_barrio() : "");
            barrio = getServiceLocator().getBarrioService().consultar(barrio);
            //log.info("barrio: " + barrio);

            Centro_atencion centro = new Centro_atencion();
            centro.setCodigo_empresa(paciente.getCodigo_empresa());
            centro.setCodigo_sucursal(paciente.getCodigo_sucursal());
            centro.setCodigo_centro(centro_barrio.getCodigo_centro());
            centro = getServiceLocator().getCentro_atencionService().consultar(centro);
            //log.info("centro: " + centro);

            Localidad localidad = new Localidad();
            localidad.setCodigo_localidad(barrio.getCodigo_localidad());
            localidad = getServiceLocator().getServicio(GeneralExtraService.class).consultar(localidad);
            //log.info("localidad: " + localidad);

            tbxBarrio.setValue((barrio != null ? barrio.getBarrio() : ""));
            tbxLocalidad.setValue((localidad != null ? localidad.getLocalidad() : ""));
            tbxCentro_atencion.setValue((centro != null ? centro.getNombre_centro() : ""));

            if (dato.getAtendida()) {
                lbxAtendida.setSelectedIndex(1);
            } else {
                lbxAtendida.setSelectedIndex(0);
            }

        }

        tbxNro_ingreso.close();
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

    public void selectedDx(Listitem listitem, Bandbox bandbox, Textbox textbox) {
        if (listitem.getValue() == null) {
            bandbox.setValue("");
            textbox.setValue("");
        } else {
            Cie dato = (Cie) listitem.getValue();
            bandbox.setValue(dato.getCodigo());
            textbox.setValue(dato.getNombre());
        }
        bandbox.close();
    }

    public void listarElementoListboxFromType(Listbox listbox, boolean select,
            List<Elemento> elementos, boolean selectEnd) {
        listbox.getChildren().clear();
        Listitem listitem;
        if (select) {
            listitem = new Listitem();
            listitem.setValue("");
            listitem.setLabel("-- seleccione --");
            listbox.appendChild(listitem);
        }

        for (Elemento elemento : elementos) {
            listitem = new Listitem();
            listitem.setValue(elemento.getCodigo());
            listitem.setLabel(elemento.getDescripcion());
            listbox.appendChild(listitem);
        }
        if (listbox.getItemCount() > 0) {
            if (!selectEnd) {
                listbox.setSelectedIndex(0);
            } else {
                listbox.setSelectedIndex(listbox.getChildren().size() - 1);
            }
        }
    }

    public void seleccion(Listbox listbox, int entero,
            Component[] abstractComponents) {
        try {
            System.out.println("" + listbox.getSelectedItem().getValue());

            String num = entero + "";
            for (Component abstractComponent : abstractComponents) {

                if (listbox.getSelectedItem().getValue().equals(num)) {
                    abstractComponent.setVisible(true);
                    // textbox.setVisible(true);
                } else {
                    // label.setVisible(false);
                    if (abstractComponent instanceof Textbox) {
                        ((Textbox) abstractComponent).setValue("");

                    }
                    abstractComponent.setVisible(false);
                }
            }
        } catch (Exception e) {
            //  block
            System.out.println("Excepcion loaded");
            e.printStackTrace();
        }
    }
}
