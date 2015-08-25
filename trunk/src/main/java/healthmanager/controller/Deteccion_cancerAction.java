/*
 * deteccion_cancerAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Ferney Jimenez Lopez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Administradora;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Contratos;
import healthmanager.modelo.bean.Departamentos;
import healthmanager.modelo.bean.Deteccion_cancer;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Municipios;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Plantilla_pyp;
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
import healthmanager.modelo.service.GeneralExtraService;

public class Deteccion_cancerAction extends Borderlayout implements
        AfterCompose {

    private static Logger log = Logger.getLogger(Deteccion_cancerAction.class);
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
    private Textbox tbxCodigo_eps;
    private Textbox tbxNombre_eps;
    private Listbox lbxCodigo_dpto;
    private Listbox lbxCodigo_municipio;
    private Textbox tbxContrato;
    private Textbox tbxCodigo_contrato;

    private Bandbox tbxIdentificacion;
    private Textbox tbxNomPaciente;
    private Textbox tbxTipoIdentificacion;
    private Datebox dbxNacimiento;
    private Textbox tbxEdad;
    private Textbox tbxSexo;
    private Textbox tbxDireccion;

    private Textbox tbxPlanificacion;
    private Textbox tbxTiempo_meses;
    private Textbox tbxTiempo_ano;
    private Radiogroup rdbEmbarazada;
    private Textbox tbxG;
    private Textbox tbxP;
    private Textbox tbxA;
    private Datebox dtbxFum;
    private Radiogroup rdbCitologias_previas;
    private Datebox dtbxFuc;
    private Radiogroup rdbResultado_fuc;
    private Checkbox chbCauterizacion;
    private Datebox dtbxFecha_cauterizacion;
    private Checkbox chbConizacion;
    private Datebox dtbxFecha_conizacion;
    private Checkbox chbHisterectomia;
    private Datebox dtbxFecha_histerectomia;
    private Checkbox chbRadioterapia;
    private Datebox dtbxFecha_radioterapia;
    private Textbox tbxAspecto_cuello;
    private Textbox tbxTomo_citologia;
    private Textbox tbxCargo;
    private Datebox dtbxFecha_citologia;
    private Textbox tbxLaboratorio;
    private Datebox dtbxFecha_laboratorio;
    private Listbox lbxNo_placa;
    private Checkbox chbInsuficiente;
    private Checkbox chbMal_fijado;
    private Checkbox chbHemorragico;
    private Checkbox chbExudado;
    private Checkbox chbGrueso;
    private Checkbox chbAusencia;
    private Listbox lbxCodigo_consulta_pyp;
    private Listbox lbxFinalidad_cons;
    private Listbox lbxCausas_externas;
    private Listbox lbxTipo_disnostico;
    // private Textbox tbxTratamiento;

    private Bandbox tbxTipo_principal;
    private Textbox tbxNomDx;
    // private Textbox tbxPlan;

    private Bandbox tbxTipo_relacionado_1;
    private Bandbox tbxTipo_relacionado_2;
    private Bandbox tbxTipo_relacionado_3;

    private Textbox tbxNomDxRelacionado_1;
    private Textbox tbxNomDxRelacionado_2;
    private Textbox tbxNomDxRelacionado_3;
    private Checkbox chbCitologia_negativa;
    private Checkbox chbCambios_asociados;
    private Checkbox chbNic;
    private Checkbox chbNic2;
    private Checkbox chbNic3;
    private Checkbox chbCa_in;
    private Checkbox chbCa_escamolecular;
    private Checkbox chbAgus_atipicas;
    private Checkbox chbAscus_atipicas;
    private Checkbox chbNeoplasias;
    private Checkbox chbOtros;
    private Listbox lbxCitologia_resultado;
    private Textbox tbxResultado;
    private Textbox tbxLapso;
    private Checkbox chbReporte;
    private Checkbox chbPlaca_inadecuada;
    private Listbox lbxEntrega;
    private Textbox tbxObservacion;

    @Override
    public void afterCompose() {
        loadComponents();
        cargarDatosSesion();
        listarCombos();
    }

    public void loadComponents() {
        form = (Borderlayout) this.getFellow("formDeteccion_cancer");

        lbxParameter = (Listbox) form.getFellow("lbxParameter");
        tbxValue = (Textbox) form.getFellow("tbxValue");
        tbxAccion = (Textbox) form.getFellow("tbxAccion");
        groupboxEditar = (Groupbox) form.getFellow("groupboxEditar");
        groupboxConsulta = (Groupbox) form.getFellow("groupboxConsulta");
        rowsResultado = (Rows) form.getFellow("rowsResultado");
        gridResultado = (Grid) form.getFellow("gridResultado");

        tbxCodigo_historia = (Textbox) form.getFellow("tbxCodigo_historia");
        dtbxFecha_inicial = (Datebox) form.getFellow("dtbxFecha_inicial");
        tbxCodigo_eps = (Textbox) form.getFellow("tbxCodigo_eps");
        tbxNombre_eps = (Textbox) form.getFellow("tbxNombre_eps");
        lbxCodigo_dpto = (Listbox) form.getFellow("lbxCodigo_dpto");
        lbxCodigo_municipio = (Listbox) form.getFellow("lbxCodigo_municipio");
        tbxContrato = (Textbox) form.getFellow("tbxContrato");
        tbxCodigo_contrato = (Textbox) form.getFellow("tbxCodigo_contrato");

        tbxIdentificacion = (Bandbox) form.getFellow("tbxIdentificacion");
        tbxNomPaciente = (Textbox) form.getFellow("tbxNomPaciente");
        tbxTipoIdentificacion = (Textbox) form
                .getFellow("tbxTipoIdentificacion");
        tbxEdad = (Textbox) form.getFellow("tbxEdad");
        tbxSexo = (Textbox) form.getFellow("tbxSexo");
        dbxNacimiento = (Datebox) form.getFellow("dbxNacimiento");
        tbxDireccion = (Textbox) form.getFellow("tbxDireccion");

        tbxPlanificacion = (Textbox) form.getFellow("tbxPlanificacion");
        tbxTiempo_meses = (Textbox) form.getFellow("tbxTiempo_meses");
        tbxTiempo_ano = (Textbox) form.getFellow("tbxTiempo_ano");
        rdbEmbarazada = (Radiogroup) form.getFellow("rdbEmbarazada");
        tbxG = (Textbox) form.getFellow("tbxG");
        tbxP = (Textbox) form.getFellow("tbxP");
        tbxA = (Textbox) form.getFellow("tbxA");
        dtbxFum = (Datebox) form.getFellow("dtbxFum");
        rdbCitologias_previas = (Radiogroup) form
                .getFellow("rdbCitologias_previas");
        dtbxFuc = (Datebox) form.getFellow("dtbxFuc");
        rdbResultado_fuc = (Radiogroup) form.getFellow("rdbResultado_fuc");
        chbCauterizacion = (Checkbox) form.getFellow("chbCauterizacion");
        dtbxFecha_cauterizacion = (Datebox) form
                .getFellow("dtbxFecha_cauterizacion");
        chbConizacion = (Checkbox) form.getFellow("chbConizacion");
        dtbxFecha_conizacion = (Datebox) form.getFellow("dtbxFecha_conizacion");
        chbHisterectomia = (Checkbox) form.getFellow("chbHisterectomia");
        dtbxFecha_histerectomia = (Datebox) form
                .getFellow("dtbxFecha_histerectomia");
        chbRadioterapia = (Checkbox) form.getFellow("chbRadioterapia");
        dtbxFecha_radioterapia = (Datebox) form
                .getFellow("dtbxFecha_radioterapia");
        tbxAspecto_cuello = (Textbox) form.getFellow("tbxAspecto_cuello");
        tbxTomo_citologia = (Textbox) form.getFellow("tbxTomo_citologia");
        tbxCargo = (Textbox) form.getFellow("tbxCargo");
        dtbxFecha_citologia = (Datebox) form.getFellow("dtbxFecha_citologia");
        tbxLaboratorio = (Textbox) form.getFellow("tbxLaboratorio");
        dtbxFecha_laboratorio = (Datebox) form
                .getFellow("dtbxFecha_laboratorio");
        lbxNo_placa = (Listbox) form.getFellow("lbxNo_placa");
        chbInsuficiente = (Checkbox) form.getFellow("chbInsuficiente");
        chbMal_fijado = (Checkbox) form.getFellow("chbMal_fijado");
        chbHemorragico = (Checkbox) form.getFellow("chbHemorragico");
        chbExudado = (Checkbox) form.getFellow("chbExudado");
        chbGrueso = (Checkbox) form.getFellow("chbGrueso");
        chbAusencia = (Checkbox) form.getFellow("chbAusencia");
        lbxCodigo_consulta_pyp = (Listbox) form
                .getFellow("lbxCodigo_consulta_pyp");
        lbxFinalidad_cons = (Listbox) form.getFellow("lbxFinalidad_cons");
        lbxCausas_externas = (Listbox) form.getFellow("lbxCausas_externas");
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

        chbCitologia_negativa = (Checkbox) form
                .getFellow("chbCitologia_negativa");
        chbCambios_asociados = (Checkbox) form
                .getFellow("chbCambios_asociados");
        chbNic = (Checkbox) form.getFellow("chbNic");
        chbNic2 = (Checkbox) form.getFellow("chbNic2");
        chbNic3 = (Checkbox) form.getFellow("chbNic3");
        chbCa_in = (Checkbox) form.getFellow("chbCa_in");
        chbCa_escamolecular = (Checkbox) form.getFellow("chbCa_escamolecular");
        chbAgus_atipicas = (Checkbox) form.getFellow("chbAgus_atipicas");
        chbAscus_atipicas = (Checkbox) form.getFellow("chbAscus_atipicas");
        chbNeoplasias = (Checkbox) form.getFellow("chbNeoplasias");
        chbOtros = (Checkbox) form.getFellow("chbOtros");
        lbxCitologia_resultado = (Listbox) form
                .getFellow("lbxCitologia_resultado");
        tbxResultado = (Textbox) form.getFellow("tbxResultado");
        tbxLapso = (Textbox) form.getFellow("tbxLapso");
        chbReporte = (Checkbox) form.getFellow("chbReporte");
        chbPlaca_inadecuada = (Checkbox) form.getFellow("chbPlaca_inadecuada");
        lbxEntrega = (Listbox) form.getFellow("lbxEntrega");
        tbxObservacion = (Textbox) form.getFellow("tbxObservacion");

    }

    public void initDeteccion_cancer() throws Exception {
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
        listarElementoListbox(lbxNo_placa, false);

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

        listarElementoListbox(lbxCitologia_resultado, false);
        listarElementoListbox(lbxEntrega, false);
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

        tbxIdentificacion
                .setStyle("text-transform:uppercase;background-color:white");
        lbxTipo_disnostico
                .setStyle("text-transform:uppercase;background-color:white");
        lbxCodigo_consulta_pyp
                .setStyle("text-transform:uppercase;background-color:white");

        String mensaje = "Los campos marcados con (*) son obligatorios";

        boolean valida = true;

        if (tbxIdentificacion.getText().trim().equals("")) {
            tbxIdentificacion
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (lbxTipo_disnostico.getSelectedIndex() == 0) {
            lbxTipo_disnostico
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (!lbxFinalidad_cons.getSelectedItem().getValue().toString()
                .equalsIgnoreCase("10")
                && lbxCodigo_consulta_pyp.getSelectedIndex() == 0) {
            lbxCodigo_consulta_pyp
                    .setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (tbxTipo_principal.getText().trim().equals("")) {
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

            getServiceLocator().getDeteccion_cancerService().setLimit(
                    "limit 25 offset 0");

            List<Deteccion_cancer> lista_datos = getServiceLocator()
                    .getDeteccion_cancerService().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Deteccion_cancer deteccion_cancer : lista_datos) {
                rowsResultado.appendChild(crearFilas(deteccion_cancer, this));
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

        final Deteccion_cancer deteccion_cancer = (Deteccion_cancer) objeto;

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(deteccion_cancer.getCodigo_historia() + ""));
        fila.appendChild(new Label(deteccion_cancer.getIdentificacion() + ""));
        fila.appendChild(new Label(deteccion_cancer.getFecha_inicial() + ""));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event arg0) throws Exception {

                mostrarDatos(deteccion_cancer);
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
                                    eliminarDatos(deteccion_cancer);
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

                Deteccion_cancer deteccion_cancer = new Deteccion_cancer();
                deteccion_cancer.setCodigo_empresa(empresa.getCodigo_empresa());
                deteccion_cancer.setCodigo_sucursal(sucursal
                        .getCodigo_sucursal());
                deteccion_cancer.setCodigo_historia(tbxCodigo_historia
                        .getValue());
                deteccion_cancer
                        .setIdentificacion(tbxIdentificacion.getValue());
                deteccion_cancer.setFecha_inicial(new Timestamp(
                        dtbxFecha_inicial.getValue().getTime()));
                deteccion_cancer.setPlanificacion(tbxPlanificacion.getValue());
                deteccion_cancer.setTiempo_meses(tbxTiempo_meses.getValue());
                deteccion_cancer.setTiempo_ano(tbxTiempo_ano.getValue());
                deteccion_cancer.setEmbarazada(rdbEmbarazada.getSelectedItem()
                        .getValue().toString());
                deteccion_cancer.setG(tbxG.getValue());
                deteccion_cancer.setP(tbxP.getValue());
                deteccion_cancer.setA(tbxA.getValue());
                deteccion_cancer.setFum(new Timestamp(dtbxFum.getValue()
                        .getTime()));
                deteccion_cancer.setCitologias_previas(rdbCitologias_previas
                        .getSelectedItem().getValue().toString());
                deteccion_cancer.setFuc(new Timestamp(dtbxFuc.getValue()
                        .getTime()));
                deteccion_cancer.setResultado_fuc(rdbResultado_fuc
                        .getSelectedItem().getValue().toString());
                deteccion_cancer.setCauterizacion(chbCauterizacion.isChecked());
                deteccion_cancer.setFecha_cauterizacion(new Timestamp(
                        dtbxFecha_cauterizacion.getValue().getTime()));
                deteccion_cancer.setConizacion(chbConizacion.isChecked());
                deteccion_cancer.setFecha_conizacion(new Timestamp(
                        dtbxFecha_conizacion.getValue().getTime()));
                deteccion_cancer.setHisterectomia(chbHisterectomia.isChecked());
                deteccion_cancer.setFecha_histerectomia(new Timestamp(
                        dtbxFecha_histerectomia.getValue().getTime()));
                deteccion_cancer.setRadioterapia(chbRadioterapia.isChecked());
                deteccion_cancer.setFecha_radioterapia(new Timestamp(
                        dtbxFecha_radioterapia.getValue().getTime()));
                deteccion_cancer
                        .setAspecto_cuello(tbxAspecto_cuello.getValue());
                deteccion_cancer
                        .setTomo_citologia(tbxTomo_citologia.getValue());
                deteccion_cancer.setCargo(tbxCargo.getValue());
                deteccion_cancer.setFecha_citologia(new Timestamp(
                        dtbxFecha_citologia.getValue().getTime()));
                deteccion_cancer.setLaboratorio(tbxLaboratorio.getValue());
                deteccion_cancer.setFecha_laboratorio(new Timestamp(
                        dtbxFecha_laboratorio.getValue().getTime()));
                deteccion_cancer.setNo_placa(lbxNo_placa.getSelectedItem()
                        .getValue().toString());
                deteccion_cancer.setInsuficiente(chbInsuficiente.isChecked());
                deteccion_cancer.setMal_fijado(chbMal_fijado.isChecked());
                deteccion_cancer.setHemorragico(chbHemorragico.isChecked());
                deteccion_cancer.setExudado(chbExudado.isChecked());
                deteccion_cancer.setGrueso(chbGrueso.isChecked());
                deteccion_cancer.setAusencia(chbAusencia.isChecked());
                deteccion_cancer.setCodigo_consulta_pyp(lbxCodigo_consulta_pyp
                        .getSelectedItem().getValue().toString());
                deteccion_cancer.setFinalidad_cons(lbxFinalidad_cons
                        .getSelectedItem().getValue().toString());
                deteccion_cancer.setCausas_externas(lbxCausas_externas
                        .getSelectedItem().getValue().toString());
                deteccion_cancer.setTipo_disnostico(lbxTipo_disnostico
                        .getSelectedItem().getValue().toString());
                deteccion_cancer
                        .setTipo_principal(tbxTipo_principal.getValue());
                deteccion_cancer.setTipo_relacionado_1(tbxTipo_relacionado_1
                        .getValue());
                deteccion_cancer.setTipo_relacionado_2(tbxTipo_relacionado_2
                        .getValue());
                deteccion_cancer.setTipo_relacionado_3(tbxTipo_relacionado_3
                        .getValue());
                deteccion_cancer.setCitologia_negativa(chbCitologia_negativa
                        .isChecked());
                deteccion_cancer.setCambios_asociados(chbCambios_asociados
                        .isChecked());
                deteccion_cancer.setNic(chbNic.isChecked());
                deteccion_cancer.setNic2(chbNic2.isChecked());
                deteccion_cancer.setNic3(chbNic3.isChecked());
                deteccion_cancer.setCa_in(chbCa_in.isChecked());
                deteccion_cancer.setCa_escamolecular(chbCa_escamolecular
                        .isChecked());
                deteccion_cancer.setAgus_atipicas(chbAgus_atipicas.isChecked());
                deteccion_cancer.setAscus_atipicas(chbAscus_atipicas
                        .isChecked());
                deteccion_cancer.setNeoplasias(chbNeoplasias.isChecked());
                deteccion_cancer.setOtros(chbOtros.isChecked());
                deteccion_cancer.setCitologia_resultado(lbxCitologia_resultado
                        .getSelectedItem().getValue().toString());
                deteccion_cancer.setResultado(tbxResultado.getValue());
                deteccion_cancer.setLapso(tbxLapso.getValue());
                deteccion_cancer.setReporte(chbReporte.isChecked());
                deteccion_cancer.setPlaca_inadecuada(chbPlaca_inadecuada
                        .isChecked());
                deteccion_cancer.setEntrega(lbxEntrega.getSelectedItem()
                        .getValue().toString());
                deteccion_cancer.setObservacion(tbxObservacion.getValue());

                datos.put("codigo_historia", deteccion_cancer);
                datos.put("accion", tbxAccion.getText());

                deteccion_cancer = getServiceLocator()
                        .getDeteccion_cancerService().guardar(datos);

                if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
                    accionForm(true, "modificar");
                }

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
        Deteccion_cancer deteccion_cancer = (Deteccion_cancer) obj;
        try {
            tbxCodigo_historia.setValue(deteccion_cancer.getCodigo_historia());
            dtbxFecha_inicial.setValue(deteccion_cancer.getFecha_inicial());

            Paciente paciente = new Paciente();
            paciente.setCodigo_empresa(deteccion_cancer.getCodigo_empresa());
            paciente.setCodigo_sucursal(deteccion_cancer.getCodigo_sucursal());
            paciente.setNro_identificacion(deteccion_cancer.getIdentificacion());
            paciente = getServiceLocator().getPacienteService().consultar(
                    paciente);

            Elemento elemento = new Elemento();
            elemento.setCodigo(paciente.getSexo());
            elemento.setTipo("sexo");
            elemento = getServiceLocator().getElementoService().consultar(
                    elemento);

            tbxIdentificacion.setValue(deteccion_cancer.getIdentificacion());
            tbxNomPaciente.setValue((paciente != null ? paciente.getNombre1()
                    + " " + paciente.getApellido1() : ""));
            tbxTipoIdentificacion.setValue((paciente != null ? paciente
                    .getTipo_identificacion() : ""));
            tbxEdad.setValue(Util.getEdad(new java.text.SimpleDateFormat(
                    "dd/MM/yyyy").format(paciente.getFecha_nacimiento()),
                    paciente.getUnidad_medidad(), false));
            tbxSexo.setValue((elemento != null ? elemento.getDescripcion() : ""));
            dbxNacimiento.setValue(paciente.getFecha_nacimiento());
            tbxDireccion.setValue(paciente.getDireccion());

            Administradora administradora = new Administradora();
            administradora.setCodigo(paciente.getCodigo_administradora());
            administradora = getServiceLocator().getAdministradoraService()
                    .consultar(administradora);

            tbxCodigo_eps.setValue(administradora != null ? administradora
                    .getCodigo() : "");
            tbxNombre_eps.setValue(administradora != null ? administradora
                    .getNombre() : "");

            Contratos contratos = new Contratos();
            contratos.setCodigo_empresa(deteccion_cancer.getCodigo_empresa());
            contratos.setCodigo_sucursal(deteccion_cancer.getCodigo_sucursal());
            contratos.setCodigo_administradora(paciente.getCodigo_administradora());
//			contratos.setId_plan(paciente.getId_plan());
            contratos = getServiceLocator().getContratosService().consultar(contratos);

            tbxCodigo_contrato.setValue(contratos != null ? contratos.getId_plan()
                    : "");
            tbxContrato.setValue(contratos != null ? contratos.getNombre() : "");

            for (int i = 0; i < lbxCodigo_dpto.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_dpto.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(paciente.getCodigo_dpto())) {
                    listitem.setSelected(true);
                    i = lbxCodigo_dpto.getItemCount();
                }
            }

            listarMunicipios(lbxCodigo_municipio, lbxCodigo_dpto);

            tbxPlanificacion.setValue(deteccion_cancer.getPlanificacion());
            tbxTiempo_meses.setValue(deteccion_cancer.getTiempo_meses());
            tbxTiempo_ano.setValue(deteccion_cancer.getTiempo_ano());
            Radio radio = (Radio) rdbEmbarazada.getFellow("Embarazada"
                    + deteccion_cancer.getEmbarazada());
            radio.setChecked(true);
            tbxG.setValue(deteccion_cancer.getG());
            tbxP.setValue(deteccion_cancer.getP());
            tbxA.setValue(deteccion_cancer.getA());
            dtbxFum.setValue(deteccion_cancer.getFum());
            Radio radio1 = (Radio) rdbCitologias_previas
                    .getFellow("Citologias_previas"
                            + deteccion_cancer.getCitologias_previas());
            radio1.setChecked(true);
            dtbxFuc.setValue(deteccion_cancer.getFuc());
            Radio radio2 = (Radio) rdbResultado_fuc.getFellow("Resultado_fuc"
                    + deteccion_cancer.getResultado_fuc());
            radio2.setChecked(true);
            chbCauterizacion.setChecked(deteccion_cancer.getCauterizacion());
            dtbxFecha_cauterizacion.setValue(deteccion_cancer
                    .getFecha_cauterizacion());
            chbConizacion.setChecked(deteccion_cancer.getConizacion());
            dtbxFecha_conizacion.setValue(deteccion_cancer
                    .getFecha_conizacion());
            chbHisterectomia.setChecked(deteccion_cancer.getHisterectomia());
            dtbxFecha_histerectomia.setValue(deteccion_cancer
                    .getFecha_histerectomia());
            chbRadioterapia.setChecked(deteccion_cancer.getRadioterapia());
            dtbxFecha_radioterapia.setValue(deteccion_cancer
                    .getFecha_radioterapia());
            tbxAspecto_cuello.setValue(deteccion_cancer.getAspecto_cuello());
            tbxTomo_citologia.setValue(deteccion_cancer.getTomo_citologia());
            tbxCargo.setValue(deteccion_cancer.getCargo());
            dtbxFecha_citologia.setValue(deteccion_cancer.getFecha_citologia());
            tbxLaboratorio.setValue(deteccion_cancer.getLaboratorio());
            dtbxFecha_laboratorio.setValue(deteccion_cancer
                    .getFecha_laboratorio());
            for (int i = 0; i < lbxNo_placa.getItemCount(); i++) {
                Listitem listitem = lbxNo_placa.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(deteccion_cancer.getNo_placa())) {
                    listitem.setSelected(true);
                    i = lbxNo_placa.getItemCount();
                }
            }
            chbInsuficiente.setChecked(deteccion_cancer.getInsuficiente());
            chbMal_fijado.setChecked(deteccion_cancer.getMal_fijado());
            chbHemorragico.setChecked(deteccion_cancer.getHemorragico());
            chbExudado.setChecked(deteccion_cancer.getExudado());
            chbGrueso.setChecked(deteccion_cancer.getGrueso());
            chbAusencia.setChecked(deteccion_cancer.getAusencia());

            for (int i = 0; i < lbxFinalidad_cons.getItemCount(); i++) {
                Listitem listitem = lbxFinalidad_cons.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(deteccion_cancer.getFinalidad_cons())) {
                    listitem.setSelected(true);
                    i = lbxFinalidad_cons.getItemCount();
                }
            }
            seleccionarProgramaPyp();

            for (int i = 0; i < lbxCausas_externas.getItemCount(); i++) {
                Listitem listitem = lbxCausas_externas.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(deteccion_cancer.getCausas_externas())) {
                    listitem.setSelected(true);
                    i = lbxCausas_externas.getItemCount();
                }
            }
            for (int i = 0; i < lbxCodigo_consulta_pyp.getItemCount(); i++) {
                Listitem listitem = lbxCodigo_consulta_pyp.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(deteccion_cancer.getCodigo_consulta_pyp())) {
                    listitem.setSelected(true);
                    i = lbxCodigo_consulta_pyp.getItemCount();
                }
            }
            for (int i = 0; i < lbxTipo_disnostico.getItemCount(); i++) {
                Listitem listitem = lbxTipo_disnostico.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(deteccion_cancer.getTipo_disnostico())) {
                    listitem.setSelected(true);
                    i = lbxTipo_disnostico.getItemCount();
                }
            }

            Cie cie = new Cie();
            cie.setCodigo(deteccion_cancer.getTipo_principal());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);
            tbxTipo_principal.setValue(deteccion_cancer.getTipo_principal());
            tbxNomDx.setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 1 */
            cie = new Cie();
            cie.setCodigo(deteccion_cancer.getTipo_relacionado_1());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_1.setValue(deteccion_cancer
                    .getTipo_relacionado_1());
            tbxNomDxRelacionado_1
                    .setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 2 */
            cie = new Cie();
            cie.setCodigo(deteccion_cancer.getTipo_relacionado_2());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_2.setValue(deteccion_cancer
                    .getTipo_relacionado_2());
            tbxNomDxRelacionado_2
                    .setValue((cie != null ? cie.getNombre() : ""));

            /* relacionado 3 */
            cie = new Cie();
            cie.setCodigo(deteccion_cancer.getTipo_relacionado_3());
            //log.info("antes: " + cie);
            cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);
            //log.info("despues: " + cie);

            tbxTipo_relacionado_3.setValue(deteccion_cancer
                    .getTipo_relacionado_3());
            tbxNomDxRelacionado_3
                    .setValue((cie != null ? cie.getNombre() : ""));

            chbCitologia_negativa.setChecked(deteccion_cancer
                    .getCitologia_negativa());
            chbCambios_asociados.setChecked(deteccion_cancer
                    .getCambios_asociados());
            chbNic.setChecked(deteccion_cancer.getNic());
            chbNic2.setChecked(deteccion_cancer.getNic2());
            chbNic3.setChecked(deteccion_cancer.getNic3());
            chbCa_in.setChecked(deteccion_cancer.getCa_in());
            chbCa_escamolecular.setChecked(deteccion_cancer
                    .getCa_escamolecular());
            chbAgus_atipicas.setChecked(deteccion_cancer.getAgus_atipicas());
            chbAscus_atipicas.setChecked(deteccion_cancer.getAscus_atipicas());
            chbNeoplasias.setChecked(deteccion_cancer.getNeoplasias());
            chbOtros.setChecked(deteccion_cancer.getOtros());
            for (int i = 0; i < lbxCitologia_resultado.getItemCount(); i++) {
                Listitem listitem = lbxCitologia_resultado.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(deteccion_cancer.getCitologia_resultado())) {
                    listitem.setSelected(true);
                    i = lbxCitologia_resultado.getItemCount();
                }
            }
            tbxResultado.setValue(deteccion_cancer.getResultado());
            tbxLapso.setValue(deteccion_cancer.getLapso());
            chbReporte.setChecked(deteccion_cancer.getReporte());
            chbPlaca_inadecuada.setChecked(deteccion_cancer
                    .getPlaca_inadecuada());
            for (int i = 0; i < lbxEntrega.getItemCount(); i++) {
                Listitem listitem = lbxEntrega.getItemAtIndex(i);
                if (listitem.getValue().toString()
                        .equals(deteccion_cancer.getEntrega())) {
                    listitem.setSelected(true);
                    i = lbxEntrega.getItemCount();
                }
            }
            tbxObservacion.setValue(deteccion_cancer.getObservacion());

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
        Deteccion_cancer deteccion_cancer = (Deteccion_cancer) obj;
        try {
            int result = getServiceLocator().getDeteccion_cancerService()
                    .eliminar(deteccion_cancer);
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
            tbxCodigo_contrato.setValue("");
            tbxContrato.setValue("");

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

            Contratos contratos = new Contratos();
            contratos.setCodigo_empresa(dato.getCodigo_empresa());
            contratos.setCodigo_sucursal(dato.getCodigo_sucursal());
            contratos.setCodigo_administradora(dato.getCodigo_administradora());
//			contratos.setId_plan(dato.getId_plan());
            contratos = getServiceLocator().getContratosService().consultar(contratos);

            tbxCodigo_contrato.setValue(contratos != null ? contratos.getId_plan()
                    : "");
            tbxContrato.setValue(contratos != null ? contratos.getNombre() : "");

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

    public void seleccionarProgramaPyp() {
        String codigo_finalidad = ""
                + lbxFinalidad_cons.getSelectedItem().getValue();
        if (!codigo_finalidad.trim().isEmpty()) {
            Map<String, Object> parameters = new HashMap();
            parameters.put("area_intervencion", codigo_finalidad);
            List<Plantilla_pyp> plantillaPyps = getServiceLocator()
                    .getPlantillaPypService().listar(parameters);
            lbxCodigo_consulta_pyp.getChildren().clear();
            Listitem listitem;
            listitem = new Listitem();
            listitem.setValue("");
            listitem.setLabel("-- seleccione --");
            lbxCodigo_consulta_pyp.appendChild(listitem);

            for (Plantilla_pyp plantillaPyp : plantillaPyps) {
                listitem = new Listitem();
                listitem.setValue(plantillaPyp.getCodigo_pdc());
                listitem.setLabel(plantillaPyp.getCodigo_pdc() + "-"
                        + plantillaPyp.getNombre_pcd());
                lbxCodigo_consulta_pyp.appendChild(listitem);
            }
            if (lbxCodigo_consulta_pyp.getItemCount() > 0) {
                lbxCodigo_consulta_pyp.setSelectedIndex(0);
            }
        } else {
            initPypList();
        }

    }

    private void initPypList() {
        lbxCodigo_consulta_pyp.getChildren().clear();
        Listitem listitem;
        listitem = new Listitem();
        listitem.setValue("");
        listitem.setLabel("-- seleccione --");
        lbxCodigo_consulta_pyp.appendChild(listitem);

        if (lbxCodigo_consulta_pyp.getItemCount() > 0) {
            lbxCodigo_consulta_pyp.setSelectedIndex(0);
        }
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
}
