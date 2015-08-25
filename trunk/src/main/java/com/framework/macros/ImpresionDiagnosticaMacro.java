package com.framework.macros;

import healthmanager.controller.FichaEpidemiologiaModel;
import healthmanager.controller.ZKWindow;
import healthmanager.exception.ImpresionDiagnosticaException;
import healthmanager.modelo.bean.Admision;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Cie_epidemiologia;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Ficha_epidemiologia;
import healthmanager.modelo.bean.Impresion_diagnostica;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Plantilla_pyp;
import healthmanager.modelo.service.Cie_epidemiologiaService;
import healthmanager.modelo.service.PacienteService;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

import com.framework.constantes.IVias_ingreso;
import com.framework.interfaces.IDatosFichaEpidemiologia;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.MapeadorDeComponentesUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import com.framework.util.UtilidadesComponentes;
import healthmanager.modelo.service.GeneralExtraService;

public class ImpresionDiagnosticaMacro extends Groupbox implements AfterCompose {

    private Impresion_diagnostica impresion_diagnostica;

    // private ElementoService elementoService;
    private ZKWindow zkWindow;
    private Admision admision;
    private String via_ingreso;
    private Map<String, Object> parametros;

    private BandboxRegistrosMacro bandboxPrincipal_cie;
    private BandboxRegistrosMacro bandboxRelacionado1_cie;
    private BandboxRegistrosMacro bandboxRelacionado2_cie;
    private BandboxRegistrosMacro bandboxRelacionado3_cie;
    private BandboxRegistrosMacro bandboxRelacionado4_cie;

    private Textbox tbxPrincipal_cie;
    private Textbox tbxRelacionado1_cie;
    private Textbox tbxRelacionado2_cie;
    private Textbox tbxRelacionado3_cie;
    private Textbox tbxRelacionado4_cie;

    private Toolbarbutton btnLimpiar_principal;
    private Toolbarbutton btnLimpiar_relacionado1;
    private Toolbarbutton btnLimpiar_relacionado2;
    private Toolbarbutton btnLimpiar_relacionado3;
    private Toolbarbutton btnLimpiar_relacionado4;

    private Listbox lbxTipo_principal;
    private Listbox lbxTipo_relacionado1;
    private Listbox lbxTipo_relacionado2;
    private Listbox lbxTipo_relacionado3;
    private Listbox lbxTipo_relacionado4;

    private Listbox lbxFinalidad_consulta;
    private Listbox lbxCodigo_consulta_pyp;
    private Listbox lbxCausas_externas;
    private Row rowCausas_externas;

    /* Guardar lbx */
    private Listbox lbxTratamiento_lepra;
    @SuppressWarnings("unused")
    private Row rowLepra;

    private boolean ficha_principal = true, ficha_relacionado1 = true,
            ficha_relacionado2 = true, ficha_relacionado3 = true,
            ficha_relacionado4 = true;

    private Toolbarbutton btnFicha_principal;
    private Toolbarbutton btnFicha_relacionado1;
    private Toolbarbutton btnFicha_relacionado2;
    private Toolbarbutton btnFicha_relacionado3;
    private Toolbarbutton btnFicha_relacionado4;

    private Map<String, FichaEpidemiologiaModel> mapa_ventanas = new HashMap<String, FichaEpidemiologiaModel>();

    private Textbox tbxNcie;
    private Row rowRelacionado4;

    private String codigo_ficha = "";
    private Boolean mostrar_alerta;

    private boolean mostrar = false;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
            "dd/MM/yyyy");

    @Override
    public void afterCompose() {
        cargarComponentes();
        if (tbxNcie.getValue().isEmpty()) {
            tbxNcie.setValue("3");
        } else if (tbxNcie.getValue().equals("4")) {
            rowRelacionado4.setVisible(true);
        }
        mapa_ventanas.clear();
        parametros = new HashMap<String, Object>();
    }

    private void cargarComponentes() {
        bandboxPrincipal_cie = (BandboxRegistrosMacro) this.getFellow(
                "bandboxPrincipal_cie").getFirstChild();
        bandboxRelacionado1_cie = (BandboxRegistrosMacro) this.getFellow(
                "bandboxRelacionado1_cie").getFirstChild();
        bandboxRelacionado2_cie = (BandboxRegistrosMacro) this.getFellow(
                "bandboxRelacionado2_cie").getFirstChild();
        bandboxRelacionado3_cie = (BandboxRegistrosMacro) this.getFellow(
                "bandboxRelacionado3_cie").getFirstChild();
        bandboxRelacionado4_cie = (BandboxRegistrosMacro) this.getFellow(
                "bandboxRelacionado4_cie").getFirstChild();

        tbxPrincipal_cie = (Textbox) this.getFellow("tbxPrincipal_cie");
        tbxRelacionado1_cie = (Textbox) this.getFellow("tbxRelacionado1_cie");
        tbxRelacionado2_cie = (Textbox) this.getFellow("tbxRelacionado2_cie");
        tbxRelacionado3_cie = (Textbox) this.getFellow("tbxRelacionado3_cie");
        tbxRelacionado4_cie = (Textbox) this.getFellow("tbxRelacionado4_cie");

        btnLimpiar_principal = (Toolbarbutton) this
                .getFellow("btnLimpiar_principal");
        btnLimpiar_relacionado1 = (Toolbarbutton) this
                .getFellow("btnLimpiar_relacionado1");
        btnLimpiar_relacionado2 = (Toolbarbutton) this
                .getFellow("btnLimpiar_relacionado2");
        btnLimpiar_relacionado3 = (Toolbarbutton) this
                .getFellow("btnLimpiar_relacionado3");
        btnLimpiar_relacionado4 = (Toolbarbutton) this
                .getFellow("btnLimpiar_relacionado4");

        lbxTipo_principal = (Listbox) this.getFellow("lbxTipo_principal");
        lbxTipo_relacionado1 = (Listbox) this.getFellow("lbxTipo_relacionado1");
        lbxTipo_relacionado2 = (Listbox) this.getFellow("lbxTipo_relacionado2");
        lbxTipo_relacionado3 = (Listbox) this.getFellow("lbxTipo_relacionado3");
        lbxTipo_relacionado4 = (Listbox) this.getFellow("lbxTipo_relacionado4");

        lbxFinalidad_consulta = (Listbox) this
                .getFellow("lbxFinalidad_consulta");
        lbxCodigo_consulta_pyp = (Listbox) this
                .getFellow("lbxCodigo_consulta_pyp");
        lbxCausas_externas = (Listbox) this.getFellow("lbxCausas_externas");
        lbxTratamiento_lepra = (Listbox) this.getFellow("lbxTratamiento_lepra");
        rowCausas_externas = (Row) this.getFellow("rowCausas_externas");
        rowLepra = (Row) this.getFellow("rowLepra");

        btnFicha_principal = (Toolbarbutton) this
                .getFellow("btnFicha_principal");
        btnFicha_relacionado1 = (Toolbarbutton) this
                .getFellow("btnFicha_relacionado1");
        btnFicha_relacionado2 = (Toolbarbutton) this
                .getFellow("btnFicha_relacionado2");
        btnFicha_relacionado3 = (Toolbarbutton) this
                .getFellow("btnFicha_relacionado3");
        btnFicha_relacionado4 = (Toolbarbutton) this
                .getFellow("btnFicha_relacionado4");
        tbxNcie = (Textbox) this.getFellow("tbxNcie");
        rowRelacionado4 = (Row) this.getFellow("rowRelacionado4");

    }

    public void inicializarMacro(ZKWindow zkWindow, Admision admision,
            String via_ingreso) {
        setZkWindow(zkWindow);
        setAdmision(admision);
        setVia_ingreso(via_ingreso);
        parametrizarBandboxDiagnostico();
        listarCombos();
        validarAdmision();
    }

    private void validarAdmision() {
        if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
                || via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)) {
            String columnas = "codigo#80px|Nombre|Sexo#60px";
            bandboxPrincipal_cie.agregarColumnas(columnas);
            bandboxRelacionado1_cie.agregarColumnas(columnas);
            bandboxRelacionado2_cie.agregarColumnas(columnas);
            bandboxRelacionado3_cie.agregarColumnas(columnas);
            bandboxRelacionado4_cie.agregarColumnas(columnas);
        }
    }

    private void parametrizarBandboxDiagnostico() {
        bandboxPrincipal_cie
                .inicializar(tbxPrincipal_cie, btnLimpiar_principal);
        bandboxRelacionado1_cie.inicializar(tbxRelacionado1_cie,
                btnLimpiar_relacionado1);
        bandboxRelacionado2_cie.inicializar(tbxRelacionado2_cie,
                btnLimpiar_relacionado2);
        bandboxRelacionado3_cie.inicializar(tbxRelacionado3_cie,
                btnLimpiar_relacionado3);
        bandboxRelacionado4_cie.inicializar(tbxRelacionado4_cie,
                btnLimpiar_relacionado4);
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

                if (!via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
                        && !via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)) {
                    listcell = new Listcell();
                    listcell.appendChild(new Label(registro
                            .getLimite_inferior()));
                    listitem.appendChild(listcell);

                    listcell = new Listcell();
                    listcell.appendChild(new Label(registro
                            .getLimite_superior()));
                    listitem.appendChild(listcell);
                }
            }

            @Override
            public List<Cie> listarRegistros(String valorBusqueda,
                    Map<String, Object> parametros) {
                parametros.put("paramTodo", "");
                aplicarFiltroDiagnostico(parametros);
                parametros.put("sexo", getSexo());
                parametros.put("value", "%"
                        + valorBusqueda.toUpperCase().trim() + "%");

                parametros.put("limite_paginado", "limit 25 offset 0");
                /*zkWindow.getServiceLocator().getCieService()
                 .setLimit("limit 25 offset 0");*/

                return zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                        .listar(Cie.class, parametros);

            }

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Cie registro) {

                limpiarSeleccion(bandbox);

                boolean retorno = true;

                try {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("nombre_entidad", registro.getNombre());
                    map.put("limite_inferior", registro.getLimite_inferior());
                    map.put("limite_superior", registro.getLimite_superior());
                    map.put("sexo_entidad", registro.getSexo());

                    map.put("fecha_nac", admision.getPaciente()
                            .getFecha_nacimiento());
                    map.put("sexo_pct", admision.getPaciente().getSexo());
                    Map<String, Object> result = Utilidades
                            .validarInformacionLimiteSexo("Diagnostico",
                                    registro.getCodigo(), registro
                                    .getLimite_inferior(), registro
                                    .getLimite_superior(), registro
                                    .getSexo(), simpleDateFormat
                                    .format(admision.getPaciente()
                                            .getFecha_nacimiento()),
                                    admision.getPaciente().getSexo());
                    if (!((Boolean) result.get("success"))) {
                        throw new Exception((String) result.get("msg"));
                    }

                    if (bandbox.equals(bandboxPrincipal_cie)) {
                        if (ficha_principal) {
                            retorno = verificarFichaEpidemiologia(registro,
                                    btnFicha_principal, true);
                        }
                    } else if (bandbox.equals(bandboxRelacionado1_cie)) {
                        if (ficha_relacionado1) {
                            retorno = verificarFichaEpidemiologia(registro,
                                    btnFicha_relacionado1, true);
                        }
                    } else if (bandbox.equals(bandboxRelacionado2_cie)) {
                        if (ficha_relacionado2) {
                            retorno = verificarFichaEpidemiologia(registro,
                                    btnFicha_relacionado2, true);
                        }
                    } else if (bandbox.equals(bandboxRelacionado3_cie)) {
                        if (ficha_relacionado3) {
                            retorno = verificarFichaEpidemiologia(registro,
                                    btnFicha_relacionado3, true);
                        }
                    } else if (bandbox.equals(bandboxRelacionado4_cie)) {
                        if (ficha_relacionado4) {
                            retorno = verificarFichaEpidemiologia(registro,
                                    btnFicha_relacionado4, true);
                        }
                    }

                    if (retorno) {
                        bandbox.setValue(registro.getCodigo());
                        textboxInformacion.setValue(registro.getNombre());
                    }

                    // log.info("codigo_ficha2" + codigo_ficha);
                    if (!((via_ingreso.equals(IVias_ingreso.PSICOLOGIA))
                            || (via_ingreso.equals(IVias_ingreso.PSIQUIATRIA)) || (via_ingreso
                            .equals(IVias_ingreso.VISITA_DOMICILIARIA)))) {

                        if (codigo_ficha != "0000") {
                            if (codigo_ficha.equals("33")
                                    || codigo_ficha.equals("42")
                                    || codigo_ficha.equals("43")
                                    || codigo_ficha.equals("9999")) {

                                MensajesUtil
                                        .mensajeAlerta("Alerta !!",
                                                "Este diagnóstico amerita remision al programa de Salud Mental");
                                codigo_ficha = "0000";
                            }
                        } else {

                            Map<String, Object> parameters = new HashMap<String, Object>();
                            parameters.put("tipo", "alertas_salud_mental");
                            parameters.put("descripcion", bandbox.getValue());

                            // log.info("parameters>>>>" + parameters);
                            // //log.info("elementoService"+elementoService);
                            mostrar_alerta = zkWindow.getServiceLocator()
                                    .getElementoService()
                                    .existe(parameters);

                            // log.info("mostrar_alerta>>>>" + mostrar_alerta);
                            if (mostrar_alerta) {
                                MensajesUtil
                                        .mensajeAlerta("Alerta !!",
                                                "Este diagnóstico amerita remision al programa de Salud Mental");
                                codigo_ficha = "0000";
                            }
                        }

                    }

                    /*
                     * if((codigo_ficha != "0000")){
                     * if(codigo_ficha.equals("12")){ rowLepra.setVisible(true);
                     * }else{ rowLepra.setVisible(false); } }
                     */
                } catch (Exception e) {
                    MensajesUtil.mensajeAlerta("Alerta", e.getMessage());
                    retorno = false;
                }

                return retorno;
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
                if (bandbox.equals(bandboxPrincipal_cie)) {
                    lbxTipo_principal.setSelectedIndex(1);
                    btnFicha_principal.setVisible(false);
                    btnFicha_principal.removeAttribute("CODIGO_CIE");
                } else if (bandbox.equals(bandboxRelacionado1_cie)) {
                    lbxTipo_relacionado1.setSelectedIndex(1);
                    btnFicha_relacionado1.setVisible(false);
                    btnFicha_relacionado1.removeAttribute("CODIGO_CIE");
                } else if (bandbox.equals(bandboxRelacionado2_cie)) {
                    lbxTipo_relacionado2.setSelectedIndex(1);
                    btnFicha_relacionado2.setVisible(false);
                    btnFicha_relacionado2.removeAttribute("CODIGO_CIE");
                } else if (bandbox.equals(bandboxRelacionado3_cie)) {
                    lbxTipo_relacionado3.setSelectedIndex(1);
                    btnFicha_relacionado3.setVisible(false);
                    btnFicha_relacionado3.removeAttribute("CODIGO_CIE");

                } else if (bandbox.equals(bandboxRelacionado4_cie)) {
                    lbxTipo_relacionado4.setSelectedIndex(1);
                    btnFicha_relacionado4.setVisible(false);
                    btnFicha_relacionado4.removeAttribute("CODIGO_CIE");
                }

                if (mapa_ventanas.containsKey(bandbox.getValue())) {
                    mapa_ventanas.remove(bandbox.getValue()).detach();
                }
            }

        };

        bandboxPrincipal_cie.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxRelacionado1_cie.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxRelacionado2_cie.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxRelacionado3_cie.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        bandboxRelacionado4_cie.setBandboxRegistrosIMG(bandboxRegistrosIMG);
    }

    protected String getSexo() {
        Paciente paciente = new Paciente();
        paciente.setCodigo_empresa(admision.getCodigo_empresa());
        paciente.setCodigo_sucursal(admision.getCodigo_sucursal());
        paciente.setNro_identificacion(admision.getNro_identificacion());
        paciente = zkWindow.getServiceLocator()
                .getServicio(PacienteService.class).consultar(paciente);
        if (paciente != null) {
            return paciente.getSexo().equals("M") ? "H" : "M";
        }
        return "A";
    }

    protected void aplicarFiltroDiagnostico(Map<String, Object> parametros2) {
        if (via_ingreso.equals(IVias_ingreso.ODONTOLOGIA2)
                || via_ingreso.equals(IVias_ingreso.ODONTOLOGIA)) {
            parametros2.put("clasificacion", via_ingreso);
        }
    }

    public boolean verificarFichaEpidemiologia(Cie registro,
            final Toolbarbutton btnFicha, final boolean domodal)
            throws Exception {
        Cie_epidemiologia cie_epidemiologia = new Cie_epidemiologia();
        cie_epidemiologia.setCodigo_empresa(zkWindow.getEmpresa()
                .getCodigo_empresa());
        cie_epidemiologia.setCodigo_sucursal(zkWindow.getSucursal()
                .getCodigo_sucursal());
        cie_epidemiologia.setCodigo_cie(registro.getCodigo());

        cie_epidemiologia = zkWindow.getServiceLocator()
                .getServicio(Cie_epidemiologiaService.class)
                .consultar(cie_epidemiologia);

        // log.info("cie_epidemiologia" + cie_epidemiologia);
        // log.info("cie" + registro);
        if (cie_epidemiologia != null) {
            Ficha_epidemiologia ficha_epidemiologia = cie_epidemiologia
                    .getFicha_epidemiologia();

            codigo_ficha = cie_epidemiologia.getCodigo_ficha();

            // log.info("codigo_ficha ---- " + codigo_ficha);
            if (mapa_ventanas.containsKey(registro.getCodigo())) {
                // log.info("" + codigo_ficha);
                final FichaEpidemiologiaModel window = mapa_ventanas
                        .get(registro.getCodigo());
                String mensaje = "Ficha de epidemiologia "
                        + window.getTitle()
                        + " se encuentra abierta porque ya este diagnóstico ha sido seleccionado";
                MensajesUtil.mensajeAlerta(
                        "Ficha de epidemiologia " + window.getTitle()
                        + " abierta", mensaje,
                        new EventListener<Event>() {

                            @Override
                            public void onEvent(Event arg0) throws Exception {
                                window.setVisible(true);
                                if (domodal) {
                                    window.doModal();
                                }
                            }

                        });
                return false;
            } else {
                parametros.put(IVias_ingreso.ADMISION_PACIENTE, admision);
                final FichaEpidemiologiaModel ventana_ficha = (FichaEpidemiologiaModel) Executions
                        .createComponents(ficha_epidemiologia.getUrl_pagina(),
                                null, parametros);

                if (!zkWindow.hasFellow(ventana_ficha.getId())) {
                    // --------- Jose ------------
                    ventana_ficha.setParent(zkWindow);
                    Map<String, Object> dato = new HashMap<String, Object>();
                    dato.put("cie_epidemiologia", cie_epidemiologia);
                    dato.put("impresion_diagnostica", impresion_diagnostica);
                    dato.put("admision", admision);

                    IDatosFichaEpidemiologia ficha = ventana_ficha
                            .consultarDatos(dato, ficha_epidemiologia);

                    if (ficha != null) {

                        ventana_ficha.mostrarDatos(ficha);
                    }

                    // ----------------------------
                    mapa_ventanas.put(registro.getCodigo(), ventana_ficha);

                    btnFicha.setAttribute("CODIGO_CIE", registro.getCodigo());
                    ventana_ficha.setTitle(ficha_epidemiologia.getTitulo());
                    ventana_ficha.addEventListener(Events.ON_MINIMIZE,
                            new EventListener<Event>() {

                                @Override
                                public void onEvent(Event arg0)
                                throws Exception {
                                    if (ventana_ficha.isMinimized()) {
                                        MensajesUtil.notificarInformacion(
                                                "Ficha: "
                                                + ventana_ficha
                                                .getTitle(),
                                                btnFicha);
                                        btnFicha.setVisible(true);
                                        btnFicha.setTooltiptext("Haga click aqui para ver la ficha de epidemiologia: "
                                                + ventana_ficha.getTitle());
                                        Clients.scrollIntoView(btnFicha);
                                    }
                                }

                            });
                    ventana_ficha.setMinimizable(true);
                    ventana_ficha.setPage(zkWindow.getPage());
                    if (mostrar) {
                        if (impresion_diagnostica != null) {

                        }
                    }
                    if (domodal) {
                        ventana_ficha.doModal();
                    }
                } else {
                    if (!admision.getAtendida()) {
                        FichaEpidemiologiaModel ventana_ficha_aux = (FichaEpidemiologiaModel) zkWindow
                                .getFellow(ventana_ficha.getId());

                        if (domodal) {
                            ventana_ficha_aux.setVisible(true);
                            ventana_ficha_aux.doModal();
                        }
                    }

                    ventana_ficha.detach();
                }

                return true;
            }
        } else {
            codigo_ficha = "0000";
            return true;
        }
    }

    public void abrirVentanaFicha(Toolbarbutton toolbarbutton) {
        if (toolbarbutton.hasAttribute("CODIGO_CIE")) {
            FichaEpidemiologiaModel ventana_ficha = mapa_ventanas
                    .get(toolbarbutton.getAttribute("CODIGO_CIE").toString());
            ventana_ficha.setVisible(true);
            ventana_ficha.doModal();
        }
    }

    private void listarCombos() {
        List<Elemento> elementos = zkWindow.getServiceLocator()
                .getElementoService().listar("tipo_diagnostico");
        UtilidadesComponentes.listarElementosListbox(true, elementos, false,
                lbxTipo_principal, lbxTipo_relacionado1, lbxTipo_relacionado2,
                lbxTipo_relacionado3, lbxTipo_relacionado4);

        elementos = zkWindow.getServiceLocator().getElementoService()
                .listar("tratamiento_lepra");
        UtilidadesComponentes.listarElementosListbox(true, elementos, false,
                lbxTratamiento_lepra);

        /* listar finalidad de consulta */
        elementos = zkWindow.getServiceLocator().getElementoService()
                .listar("finalidad_cons");
        // Utilidades.listarElementoListboxFromType(lbxFinalidad_consulta, true,
        // elementos, false);

        lbxFinalidad_consulta.getChildren().clear();
        lbxFinalidad_consulta.appendItem("-- seleccione --", "");

        for (Elemento elemento : elementos) {
            Listitem listitem = new Listitem(elemento.getDescripcion(),
                    elemento.getCodigo());
            listitem.setVisible(false);
            lbxFinalidad_consulta.appendChild(listitem);
        }

        validarFinalidadConsulta(lbxFinalidad_consulta);

        if (via_ingreso.equalsIgnoreCase(IVias_ingreso.CONSULTA_EXTERNA)
                || via_ingreso.equalsIgnoreCase(IVias_ingreso.HC_AIEPI_2_MESES)
                || via_ingreso
                .equalsIgnoreCase(IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS)) {
            Utilidades.seleccionarListitem(lbxFinalidad_consulta, "01");
            onSeleccionarFinalidadConsulta();
        }

        if (lbxFinalidad_consulta.getSelectedItem() == null
                && lbxFinalidad_consulta.getItemCount() > 0) {
            lbxFinalidad_consulta.setSelectedIndex(0);
        }

        /* causas externas */
        elementos = zkWindow.getServiceLocator().getElementoService()
                .listar("causa_externa");
        UtilidadesComponentes.listarElementosListbox(false, elementos, false,
                lbxCausas_externas);
        Utilidades.seleccionarListitem(lbxCausas_externas, "13");
    }

    private void validarFinalidadConsulta(Listbox listbox_finalidad) {
        for (int i = 0; i < listbox_finalidad.getItemCount(); i++) {
            Listitem listitem = listbox_finalidad.getItemAtIndex(i);
            String codigo = listitem.getValue().toString();
            if (via_ingreso.equalsIgnoreCase(IVias_ingreso.HC_MENOR_2_MESES)
                    || via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HC_MENOR_2_MESES_2_ANOS)
                    || via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HC_MENOR_2_ANOS_5_ANOS)
                    || via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HISC_DETECCION_ALT_MENOR_5A_10A)) {
                if (codigo.equals("04")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else if (via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HIPERTENSO_DIABETICO)) {
                if (codigo.equals("01")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.ADULTO_MAYOR)) {
                if (codigo.equals("07")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.ODONTOLOGIA2)
                    || via_ingreso.equalsIgnoreCase(IVias_ingreso.ODONTOLOGIA)) {
                if (codigo.equals("01")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else if (via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.CONSULTA_EXTERNA)
                    || via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HC_AIEPI_2_MESES)
                    || via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HC_AIEPI_2_MESES_5_ANOS)
                    || via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.CONSULTA_ESPECIALIZADA)) {
                listitem.setVisible(true);
            } else if (via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HISTORIA_CLINICA_RECIEN_NACIDO)) {
                if (codigo.equals("02")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else if (via_ingreso.equalsIgnoreCase(IVias_ingreso.URGENCIA)) {
                if (codigo.equals("01")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }

            } else if (via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HOSPITALIZACIONES)) {
                if (codigo.equals("01")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else if (via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.ALTERACION_JOVEN)) {
                if (codigo.equals("05")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else if (via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HC_DETECCION_ALT_EMBARAZO)) {
                if (codigo.equals("06")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else if (via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.HIPERTENSO_DIABETICO)) {
                if (codigo.equals("01")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else if (via_ingreso
                    .equalsIgnoreCase(IVias_ingreso.PLANIFICACION_FAMILIAR)) {
                if (codigo.equals("03")) {
                    listitem.setVisible(true);
                    listitem.setSelected(true);
                    onSeleccionarFinalidadConsulta();
                }
            } else {
                listitem.setVisible(true);
            }

        }
    }

    public void onSeleccionarFinalidadConsulta() {

        lbxCodigo_consulta_pyp.getChildren().clear();
        String codigo_finalidad = ""
                + lbxFinalidad_consulta.getSelectedItem().getValue();

        if (!codigo_finalidad.trim().isEmpty()
                && !codigo_finalidad.equalsIgnoreCase("01")) {
            Map<String, Object> parameters = new HashMap();
            parameters.put("area_intervencion", codigo_finalidad);
            List<Plantilla_pyp> plantillaPyps = zkWindow.getServiceLocator()
                    .getPlantillaPypService().listar(parameters);
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
            rowCausas_externas.setVisible(false);
        } else {
            if (codigo_finalidad.equalsIgnoreCase("01")) {
                rowCausas_externas.setVisible(true);
                Utilidades.seleccionarListitem(lbxCausas_externas, "13");
            }
            lbxCodigo_consulta_pyp.appendItem("-- NO APLICA --", "");
            lbxCodigo_consulta_pyp.setDisabled(true);
        }
        if (lbxCodigo_consulta_pyp.getItemCount() > 0) {
            lbxCodigo_consulta_pyp.setSelectedIndex(0);
        }
    }

    public Impresion_diagnostica obtenerImpresionDiagnostica()
            throws ImpresionDiagnosticaException {
        if (impresion_diagnostica == null) {
            impresion_diagnostica = new Impresion_diagnostica();
        }
        if (validarImpresionDiagnostica()) {
            impresion_diagnostica
                    .setListado_fichas(obtenerFichasEpidemiologia());
            impresion_diagnostica.setCodigo_empresa(zkWindow.codigo_empresa);
            impresion_diagnostica.setCodigo_sucursal(zkWindow.codigo_sucursal);
            impresion_diagnostica.setCausas_externas(lbxCausas_externas
                    .getSelectedItem().getValue().toString());
            impresion_diagnostica.setCie_principal(bandboxPrincipal_cie
                    .getValue());
            impresion_diagnostica.setCie_relacionado1(bandboxRelacionado1_cie
                    .getValue());
            impresion_diagnostica.setCie_relacionado2(bandboxRelacionado2_cie
                    .getValue());
            impresion_diagnostica.setCie_relacionado3(bandboxRelacionado3_cie
                    .getValue());
            impresion_diagnostica.setCie_relacionado4(bandboxRelacionado4_cie
                    .getValue());
            impresion_diagnostica.setCodigo_consulta_pyp(lbxCodigo_consulta_pyp
                    .getSelectedItem().getValue().toString());

            impresion_diagnostica.setFinalidad_consulta(lbxFinalidad_consulta
                    .getSelectedItem().getValue().toString());
            impresion_diagnostica.setTipo_principal(lbxTipo_principal
                    .getSelectedItem().getValue().toString());
            impresion_diagnostica.setTipo_relacionado1(lbxTipo_relacionado1
                    .getSelectedItem().getValue().toString());
            impresion_diagnostica.setTipo_relacionado2(lbxTipo_relacionado2
                    .getSelectedItem().getValue().toString());
            impresion_diagnostica.setTipo_relacionado3(lbxTipo_relacionado3
                    .getSelectedItem().getValue().toString());
            impresion_diagnostica.setTipo_relacionado4(lbxTipo_relacionado4
                    .getSelectedItem().getValue().toString());
            impresion_diagnostica.setNro_identificacion(admision
                    .getNro_identificacion());
            impresion_diagnostica.setCreacion_date(new Timestamp(Calendar
                    .getInstance().getTimeInMillis()));
            return impresion_diagnostica;
        } else {
            throw new ImpresionDiagnosticaException(
                    "Diligenciar los campos de Impresion Diagnostica");
        }
    }

    public void mostrarImpresionDiagnostica(
            Impresion_diagnostica impresion_diagnostica, boolean readonly)
            throws Exception {
        LimpiarDiagnosticos();
        for (String key_ventana : mapa_ventanas.keySet()) {
            mapa_ventanas.get(key_ventana).detach();
        }
        mapa_ventanas.clear();
        // parametros = new HashMap<String, Object>();

        mostrar = true;
        this.impresion_diagnostica = impresion_diagnostica;
        if (this.impresion_diagnostica == null) {
            this.impresion_diagnostica = new Impresion_diagnostica();
            this.impresion_diagnostica
                    .setCodigo_empresa(zkWindow.codigo_empresa);
            this.impresion_diagnostica
                    .setCodigo_sucursal(zkWindow.codigo_sucursal);
            impresion_diagnostica = this.impresion_diagnostica;
        }

        Cie cie_aux = new Cie();
        cie_aux.setCodigo(impresion_diagnostica.getCie_principal());
        cie_aux = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                .consultar(cie_aux);
        if (cie_aux != null) {
            bandboxPrincipal_cie.seleccionarRegistro(cie_aux,
                    cie_aux.getCodigo(), cie_aux.getNombre());
            bandboxPrincipal_cie.getButtonLimpiar().setVisible(false);
        }
        Utilidades.seleccionarListitem(lbxTipo_principal,
                impresion_diagnostica.getTipo_principal());

        verificarFichaEpidemiologia(cie_aux, btnFicha_principal, true);

        if (impresion_diagnostica.getCie_relacionado1() != null
                && !impresion_diagnostica.getCie_relacionado1().isEmpty()) {
            cie_aux = new Cie();
            cie_aux.setCodigo(impresion_diagnostica.getCie_relacionado1());
            cie_aux = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                    .consultar(cie_aux);
            if (cie_aux != null) {
                bandboxRelacionado1_cie.seleccionarRegistro(cie_aux,
                        cie_aux.getCodigo(), cie_aux.getNombre());
                bandboxRelacionado1_cie.getButtonLimpiar().setVisible(false);
            }
            Utilidades.seleccionarListitem(lbxTipo_relacionado1,
                    impresion_diagnostica.getTipo_relacionado1());

            verificarFichaEpidemiologia(cie_aux, btnFicha_relacionado1, true);
        }

        if (impresion_diagnostica.getCie_relacionado2() != null
                && !impresion_diagnostica.getCie_relacionado2().isEmpty()) {
            cie_aux = new Cie();
            cie_aux.setCodigo(impresion_diagnostica.getCie_relacionado2());
            cie_aux = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                    .consultar(cie_aux);
            if (cie_aux != null) {
                bandboxRelacionado2_cie.seleccionarRegistro(cie_aux,
                        cie_aux.getCodigo(), cie_aux.getNombre());
                bandboxRelacionado2_cie.getButtonLimpiar().setVisible(false);
            }
            Utilidades.seleccionarListitem(lbxTipo_relacionado2,
                    impresion_diagnostica.getTipo_relacionado2());

            verificarFichaEpidemiologia(cie_aux, btnFicha_relacionado2, true);

        }

        if (impresion_diagnostica.getCie_relacionado3() != null
                && !impresion_diagnostica.getCie_relacionado3().isEmpty()) {
            cie_aux = new Cie();
            cie_aux.setCodigo(impresion_diagnostica.getCie_relacionado3());
            cie_aux = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                    .consultar(cie_aux);
            if (cie_aux != null) {
                bandboxRelacionado3_cie.seleccionarRegistro(cie_aux,
                        cie_aux.getCodigo(), cie_aux.getNombre());
                bandboxRelacionado3_cie.getButtonLimpiar().setVisible(false);
            }
            Utilidades.seleccionarListitem(lbxTipo_relacionado3,
                    impresion_diagnostica.getTipo_relacionado3());

            verificarFichaEpidemiologia(cie_aux, btnFicha_relacionado3, true);
        }

        Utilidades.seleccionarListitem(lbxFinalidad_consulta,
                impresion_diagnostica.getFinalidad_consulta());
        onSeleccionarFinalidadConsulta();
        Utilidades.seleccionarListitem(lbxCodigo_consulta_pyp,
                impresion_diagnostica.getCodigo_consulta_pyp());
        Utilidades.seleccionarListitem(lbxCausas_externas,
                impresion_diagnostica.getCausas_externas());

    }

    public void mostrarImpresionDiagnostica_reportes(
            Impresion_diagnostica impresion_diagnostica, boolean readonly)
            throws Exception {
        LimpiarDiagnosticos();
        for (String key_ventana : mapa_ventanas.keySet()) {
            mapa_ventanas.get(key_ventana).detach();
        }
        mapa_ventanas.clear();
        // parametros = new HashMap<String, Object>();

        mostrar = true;
        this.impresion_diagnostica = impresion_diagnostica;
        if (this.impresion_diagnostica == null) {
            this.impresion_diagnostica = new Impresion_diagnostica();
            this.impresion_diagnostica
                    .setCodigo_empresa(zkWindow.codigo_empresa);
            this.impresion_diagnostica
                    .setCodigo_sucursal(zkWindow.codigo_sucursal);
            impresion_diagnostica = this.impresion_diagnostica;
        }

        Cie cie_aux = new Cie();
        cie_aux.setCodigo(impresion_diagnostica.getCie_principal());
        cie_aux = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                .consultar(cie_aux);
        if (cie_aux != null) {
            bandboxPrincipal_cie.seleccionarRegistro(cie_aux,
                    cie_aux.getCodigo(), cie_aux.getNombre());
            bandboxPrincipal_cie.getButtonLimpiar().setVisible(false);
        }
        Utilidades.seleccionarListitem(lbxTipo_principal,
                impresion_diagnostica.getTipo_principal());

        // verificarFichaEpidemiologia(cie_aux, btnFicha_principal, true);
        if (impresion_diagnostica.getCie_relacionado1() != null
                && !impresion_diagnostica.getCie_relacionado1().isEmpty()) {
            cie_aux = new Cie();
            cie_aux.setCodigo(impresion_diagnostica.getCie_relacionado1());
            cie_aux = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                    .consultar(cie_aux);
            if (cie_aux != null) {
                bandboxRelacionado1_cie.seleccionarRegistro(cie_aux,
                        cie_aux.getCodigo(), cie_aux.getNombre());
                bandboxRelacionado1_cie.getButtonLimpiar().setVisible(false);
            }
            Utilidades.seleccionarListitem(lbxTipo_relacionado1,
                    impresion_diagnostica.getTipo_relacionado1());

            // verificarFichaEpidemiologia(cie_aux, btnFicha_relacionado1,
            // true);
        }

        if (impresion_diagnostica.getCie_relacionado2() != null
                && !impresion_diagnostica.getCie_relacionado2().isEmpty()) {
            cie_aux = new Cie();
            cie_aux.setCodigo(impresion_diagnostica.getCie_relacionado2());
            cie_aux = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                    .consultar(cie_aux);
            if (cie_aux != null) {
                bandboxRelacionado2_cie.seleccionarRegistro(cie_aux,
                        cie_aux.getCodigo(), cie_aux.getNombre());
                bandboxRelacionado2_cie.getButtonLimpiar().setVisible(false);
            }
            Utilidades.seleccionarListitem(lbxTipo_relacionado2,
                    impresion_diagnostica.getTipo_relacionado2());

            // verificarFichaEpidemiologia(cie_aux, btnFicha_relacionado2,
            // true);
        }

        if (impresion_diagnostica.getCie_relacionado3() != null
                && !impresion_diagnostica.getCie_relacionado3().isEmpty()) {
            cie_aux = new Cie();
            cie_aux.setCodigo(impresion_diagnostica.getCie_relacionado3());
            cie_aux = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                    .consultar(cie_aux);
            if (cie_aux != null) {
                bandboxRelacionado3_cie.seleccionarRegistro(cie_aux,
                        cie_aux.getCodigo(), cie_aux.getNombre());
                bandboxRelacionado3_cie.getButtonLimpiar().setVisible(false);
            }
            Utilidades.seleccionarListitem(lbxTipo_relacionado3,
                    impresion_diagnostica.getTipo_relacionado3());

            // verificarFichaEpidemiologia(cie_aux, btnFicha_relacionado3,
            // true);
        }

        Utilidades.seleccionarListitem(lbxFinalidad_consulta,
                impresion_diagnostica.getFinalidad_consulta());
        onSeleccionarFinalidadConsulta();
        Utilidades.seleccionarListitem(lbxCodigo_consulta_pyp,
                impresion_diagnostica.getCodigo_consulta_pyp());
        Utilidades.seleccionarListitem(lbxCausas_externas,
                impresion_diagnostica.getCausas_externas());

    }

    public void mostrarImpresionDiagnostica2(
            Impresion_diagnostica impresion_diagnostica, boolean readonly)
            throws Exception {
        LimpiarDiagnosticos();
        for (String key_ventana : mapa_ventanas.keySet()) {
            mapa_ventanas.get(key_ventana).detach();
        }
        mapa_ventanas.clear();
        // parametros = new HashMap<String, Object>();

        mostrar = true;
        this.impresion_diagnostica = impresion_diagnostica;
        if (this.impresion_diagnostica == null) {
            this.impresion_diagnostica = new Impresion_diagnostica();
            this.impresion_diagnostica
                    .setCodigo_empresa(zkWindow.codigo_empresa);
            this.impresion_diagnostica
                    .setCodigo_sucursal(zkWindow.codigo_sucursal);
            impresion_diagnostica = this.impresion_diagnostica;
        }

        Cie cie_aux = new Cie();
        cie_aux.setCodigo(impresion_diagnostica.getCie_principal());
        cie_aux = zkWindow.getServiceLocator().getServicio(GeneralExtraService.class)
                .consultar(cie_aux);
        if (cie_aux != null) {
            bandboxPrincipal_cie.seleccionarRegistro(cie_aux,
                    cie_aux.getCodigo(), cie_aux.getNombre());
            bandboxPrincipal_cie.getButtonLimpiar().setVisible(false);
        }
        Utilidades.seleccionarListitem(lbxTipo_principal,
                impresion_diagnostica.getTipo_principal());

        verificarFichaEpidemiologia(cie_aux, btnFicha_principal, true);

        Utilidades.seleccionarListitem(lbxFinalidad_consulta,
                impresion_diagnostica.getFinalidad_consulta());
        onSeleccionarFinalidadConsulta();
        Utilidades.seleccionarListitem(lbxCodigo_consulta_pyp,
                impresion_diagnostica.getCodigo_consulta_pyp());
        Utilidades.seleccionarListitem(lbxCausas_externas,
                impresion_diagnostica.getCausas_externas());

    }

    public List<IDatosFichaEpidemiologia> obtenerFichasEpidemiologia()
            throws ImpresionDiagnosticaException {
        List<IDatosFichaEpidemiologia> listado = new ArrayList<IDatosFichaEpidemiologia>();
        for (String key_mapa : mapa_ventanas.keySet()) {
            final FichaEpidemiologiaModel ficha_epidemiologia = mapa_ventanas
                    .get(key_mapa);
            if (ficha_epidemiologia.validarFichaEpidemiologia()) {
                IDatosFichaEpidemiologia datos_ficha = (IDatosFichaEpidemiologia) ficha_epidemiologia
                        .obtenerFichaEpidemiologia();
                datos_ficha.setCodigo_diagnostico(key_mapa);
                datos_ficha.setVia_ingreso(via_ingreso);
                listado.add(datos_ficha);
            } else {
                MensajesUtil.mensajeAlerta(
                        "Diligenciar " + ficha_epidemiologia.getTitle(),
                        "El diligenciamiento de la ficha "
                        + ficha_epidemiologia.getTitle()
                        + " no es el correcto...",
                        new EventListener<Event>() {

                            @Override
                            public void onEvent(Event arg0) throws Exception {
                                ficha_epidemiologia.setVisible(true);
                                ficha_epidemiologia.doModal();
                            }
                        });

                throw new ImpresionDiagnosticaException(
                        "El diligenciamiento de la ficha no es el correcto");
            }
        }
        return listado;
    }

    public boolean validarImpresionDiagnostica() {
        boolean valida = true;

        if (bandboxPrincipal_cie.getRegistroSeleccionado() == null) {
            MensajesUtil.mensajeAlerta("diagnóstico obligatorio !!!",
                    "El diagnóstico principal es obligatorio",
                    new EventListener<Event>() {

                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            Clients.scrollIntoView(bandboxPrincipal_cie);
                            bandboxPrincipal_cie.setFocus(true);
                            MensajesUtil.notificarAlerta(
                                    "El diagnóstico principal es obligatorio",
                                    bandboxPrincipal_cie);
                        }
                    });
            return false;
        } else {
            if (lbxTipo_principal.getSelectedIndex() == 0) {
                MensajesUtil.mensajeAlerta("Tipo diagnóstico obligatorio !!!",
                        "El Tipo de diagnóstico principal es obligatorio",
                        new EventListener<Event>() {

                            @Override
                            public void onEvent(Event arg0) throws Exception {
                                lbxTipo_principal.setFocus(true);
                            }
                        });
                return false;
            } else {
                if (mapa_ventanas.containsKey(bandboxPrincipal_cie.getValue())) {
                    final FichaEpidemiologiaModel ficha_epidemiologia = (FichaEpidemiologiaModel) mapa_ventanas
                            .get(bandboxPrincipal_cie.getValue());
                    if (!ficha_epidemiologia.validarFichaEpidemiologia()) {
                        MensajesUtil
                                .mensajeAlerta(
                                        "Diligenciar ficha de epidemiologia !!!",
                                        "La ficha de epidemiologia "
                                        + ficha_epidemiologia
                                        .getTitle()
                                        + " no ha sido dilenciada correctamente",
                                        new EventListener<Event>() {

                                            @Override
                                            public void onEvent(Event arg0)
                                            throws Exception {
                                                ficha_epidemiologia
                                                .setVisible(true);
                                                ficha_epidemiologia.doModal();
                                            }
                                        });
                        return false;
                    }
                }
            }
        }

        if (bandboxRelacionado1_cie.getRegistroSeleccionado() != null
                && lbxTipo_relacionado1.getSelectedIndex() == 0) {
            MensajesUtil.mensajeAlerta("Tipo diagnóstico obligatorio !!!",
                    "El Tipo de diagnóstico relacionado 1  es obligatorio",
                    new EventListener<Event>() {

                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            bandboxRelacionado1_cie.setFocus(true);
                        }
                    });
            return false;
        } else {
            if (mapa_ventanas.containsKey(bandboxRelacionado1_cie.getValue())) {
                final FichaEpidemiologiaModel ficha_epidemiologia = (FichaEpidemiologiaModel) mapa_ventanas
                        .get(bandboxRelacionado1_cie.getValue());
                if (!ficha_epidemiologia.validarFichaEpidemiologia()) {
                    MensajesUtil.mensajeAlerta(
                            "Diligenciar ficha de epidemiologia !!!",
                            "La ficha de epidemiologia "
                            + ficha_epidemiologia.getTitle()
                            + " no ha sido dilenciada correctamente",
                            new EventListener<Event>() {

                                @Override
                                public void onEvent(Event arg0)
                                throws Exception {
                                    ficha_epidemiologia.setVisible(true);
                                    ficha_epidemiologia.doModal();
                                }
                            });
                    return false;
                }
            }
        }

        if (bandboxRelacionado2_cie.getRegistroSeleccionado() != null
                && lbxTipo_relacionado2.getSelectedIndex() == 0) {
            MensajesUtil.mensajeAlerta("Tipo diagnóstico obligatorio !!!",
                    "El Tipo de diagnóstico relacionado 2  es obligatorio",
                    new EventListener<Event>() {

                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            lbxTipo_relacionado2.setFocus(true);
                        }
                    });
            return false;
        } else {
            if (mapa_ventanas.containsKey(bandboxRelacionado2_cie.getValue())) {
                final FichaEpidemiologiaModel ficha_epidemiologia = (FichaEpidemiologiaModel) mapa_ventanas
                        .get(bandboxRelacionado2_cie.getValue());
                if (!ficha_epidemiologia.validarFichaEpidemiologia()) {
                    MensajesUtil.mensajeAlerta(
                            "Diligenciar ficha de epidemiologia !!!",
                            "La ficha de epidemiologia "
                            + ficha_epidemiologia.getTitle()
                            + " no ha sido dilenciada correctamente",
                            new EventListener<Event>() {

                                @Override
                                public void onEvent(Event arg0)
                                throws Exception {
                                    ficha_epidemiologia.setVisible(true);
                                    ficha_epidemiologia.doModal();
                                }
                            });
                    return false;
                }
            }
        }

        if (bandboxRelacionado3_cie.getRegistroSeleccionado() != null
                && lbxTipo_relacionado3.getSelectedIndex() == 0) {
            MensajesUtil.mensajeAlerta("Tipo diagnóstico obligatorio !!!",
                    "El Tipo de diagnóstico relacionado 3  es obligatorio",
                    new EventListener<Event>() {

                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            lbxTipo_relacionado3.setFocus(true);
                        }
                    });
            return false;
        } else {
            if (mapa_ventanas.containsKey(bandboxRelacionado3_cie.getValue())) {
                final FichaEpidemiologiaModel ficha_epidemiologia = (FichaEpidemiologiaModel) mapa_ventanas
                        .get(bandboxRelacionado3_cie.getValue());
                if (!ficha_epidemiologia.validarFichaEpidemiologia()) {
                    MensajesUtil.mensajeAlerta(
                            "Diligenciar ficha de epidemiologia !!!",
                            "La ficha de epidemiologia "
                            + ficha_epidemiologia.getTitle()
                            + " no ha sido dilenciada correctamente",
                            new EventListener<Event>() {

                                @Override
                                public void onEvent(Event arg0)
                                throws Exception {
                                    ficha_epidemiologia.setVisible(true);
                                    ficha_epidemiologia.doModal();
                                }
                            });
                    return false;
                }
            }
        }

        if (bandboxRelacionado4_cie.getRegistroSeleccionado() != null
                && lbxTipo_relacionado4.getSelectedIndex() == 0) {
            MensajesUtil.mensajeAlerta("Tipo diagnóstico obligatorio !!!",
                    "El Tipo de diagnóstico relacionado 4  es obligatorio",
                    new EventListener<Event>() {

                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            lbxTipo_relacionado4.setFocus(true);
                        }
                    });
            return false;
        } else {
            if (mapa_ventanas.containsKey(bandboxRelacionado4_cie.getValue())) {
                final FichaEpidemiologiaModel ficha_epidemiologia = (FichaEpidemiologiaModel) mapa_ventanas
                        .get(bandboxRelacionado4_cie.getValue());
                if (!ficha_epidemiologia.validarFichaEpidemiologia()) {
                    MensajesUtil.mensajeAlerta(
                            "Diligenciar ficha de epidemiologia !!!",
                            "La ficha de epidemiologia "
                            + ficha_epidemiologia.getTitle()
                            + " no ha sido dilenciada correctamente",
                            new EventListener<Event>() {

                                @Override
                                public void onEvent(Event arg0)
                                throws Exception {
                                    ficha_epidemiologia.setVisible(true);
                                    ficha_epidemiologia.doModal();
                                }
                            });
                    return false;
                }
            }
        }

        if (lbxFinalidad_consulta.getSelectedIndex() == 0) {
            MensajesUtil.mensajeAlerta("Campo obligatorio !!!",
                    "Debe seleccionar la finalidad de consulta",
                    new EventListener<Event>() {

                        @Override
                        public void onEvent(Event arg0) throws Exception {
                            lbxFinalidad_consulta.setFocus(true);
                        }
                    });
            return false;
        }

        return valida;
    }

    public void setZkWindow(ZKWindow zkWindow) {
        this.zkWindow = zkWindow;
    }

    public ZKWindow getZkWindow() {
        return zkWindow;
    }

    public Admision getAdmision() {
        return admision;
    }

    public void setAdmision(Admision admision) {
        this.admision = admision;
        onCargarConfiguracion();
    }

    private void onCargarConfiguracion() {

    }

    public void setParametros(Map<String, Object> parametros) {
        this.parametros = parametros;
    }

    public Map<String, Object> getParametros() {
        return parametros;
    }

    public String getVia_ingreso() {
        return via_ingreso;
    }

    public void setVia_ingreso(String via_ingreso) {
        this.via_ingreso = via_ingreso;
    }

    public void LimpiarDiagnosticos() {
        bandboxPrincipal_cie.setValue("");
        tbxPrincipal_cie.setValue("");
        lbxTipo_principal.setSelectedIndex(0);
        bandboxRelacionado1_cie.setValue("");
        tbxRelacionado1_cie.setValue("");
        lbxTipo_relacionado1.setSelectedIndex(0);
        bandboxRelacionado2_cie.setValue("");
        tbxRelacionado2_cie.setValue("");
        lbxTipo_relacionado2.setSelectedIndex(0);
        bandboxRelacionado3_cie.setValue("");
        tbxRelacionado3_cie.setValue("");
        lbxTipo_relacionado3.setSelectedIndex(0);
        bandboxRelacionado4_cie.setValue("");
        tbxRelacionado4_cie.setValue("");
        lbxTipo_relacionado4.setSelectedIndex(0);
    }

    public Listbox getLbxCausas_externas() {
        return lbxCausas_externas;
    }

    public void setLbxCausas_externas(Listbox lbxCausas_externas) {
        this.lbxCausas_externas = lbxCausas_externas;
    }

    public void complementarInformacion(Map<String, Object> map) {
        map.put("impresion_diagnostica_cie_principal",
                bandboxPrincipal_cie.getValue());
        map.put("impresion_diagnostica_tipo_principal",
                MapeadorDeComponentesUtil.getValorListbox(lbxTipo_principal));
        map.put("impresion_diagnostica_cie_relacionado1",
                bandboxRelacionado1_cie.getValue());
        map.put("impresion_diagnostica_tipo_relacionado1",
                MapeadorDeComponentesUtil.getValorListbox(lbxTipo_relacionado1));
        map.put("impresion_diagnostica_cie_relacionado2",
                bandboxRelacionado2_cie.getValue());
        map.put("impresion_diagnostica_tipo_relacionado2",
                MapeadorDeComponentesUtil.getValorListbox(lbxTipo_relacionado2));
        map.put("impresion_diagnostica_cie_relacionado3",
                bandboxRelacionado3_cie.getValue());
        map.put("impresion_diagnostica_tipo_relacionado3",
                MapeadorDeComponentesUtil.getValorListbox(lbxTipo_relacionado3));
        map.put("impresion_diagnostica_cie_relacionado4",
                bandboxRelacionado4_cie.getValue());
        map.put("impresion_diagnostica_tipo_relacionado4",
                MapeadorDeComponentesUtil.getValorListbox(lbxTipo_relacionado4));
        map.put("finalidad_consulta", MapeadorDeComponentesUtil
                .getValorListbox(lbxFinalidad_consulta));
        map.put("principal", tbxPrincipal_cie.getValue());
        map.put("relacionado1", tbxRelacionado1_cie.getValue());
        map.put("relacionado2", tbxRelacionado2_cie.getValue());
        map.put("relacionado3", tbxRelacionado3_cie.getValue());
        map.put("causas_externa",
                MapeadorDeComponentesUtil.getValorListbox(lbxCausas_externas));
    }
}
