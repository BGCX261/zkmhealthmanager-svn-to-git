/*
 * orden_servicio2Action.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
 Ferney Jimenez Lopez
 Luis Hernadez Perez
 Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Cie;
import healthmanager.modelo.bean.Cups;
import healthmanager.modelo.bean.Detalle_orden2;
import healthmanager.modelo.bean.Especialidad;
import healthmanager.modelo.bean.Orden_servicio2;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.service.MacrocomponenteService.Paquetes;
import com.framework.util.Util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Borderlayout;
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

import com.framework.interfaces.IImprimirRegistros;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.WindowRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.macros.impl.WindowRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;
import healthmanager.modelo.service.GeneralExtraService;

public class Orden_servicio2Action extends ZKWindow implements WindowRegistrosIMG, IImprimirRegistros {

//	private static Logger log = Logger.getLogger(Orden_servicio2Action.class);
    //Componentes //
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
    private Textbox tbxCodigo_orden;
    @View
    private Datebox dtbxFecha_orden;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_paciente;
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
    private Textbox tbxTipo_identificacion;
    @View
    private Textbox tbxEstrato;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_prestador;
    @View
    private Textbox tbxNomPrestador;
    @View
    private Toolbarbutton btnLimpiarPrestador;

    @View
    private Textbox tbxEstado;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_dx1;
    @View
    private Textbox tbxNomDx1;
    @View
    private Toolbarbutton btnLimpiarDx1;

    @View(isMacro = true)
    private BandboxRegistrosMacro tbxCodigo_dx2;
    @View
    private Textbox tbxNomDx2;
    @View
    private Toolbarbutton btnLimpiarDx2;

    @View
    private Textbox tbxObservaciones;

    @View
    private Grid gridOrden2;
    @View
    private Rows rowsOrden2;

    private List<Detalle_orden2> lista_datos;

    @View
    private Listbox lbxFormato;

    private List<String> lista_seleccionados = new ArrayList<String>();

    private final String[] idsExcluyentes = {"btCancel", "btGuardar",
        "tbxAccion", "btNew"};

    @Override
    public void init() throws Exception {
        listarCombos();
        lista_datos = new ArrayList<Detalle_orden2>();
        parametrizarBandbox();
        limpiarDatos();
    }

    public void listarCombos() {
        listarParameter();
    }

    public void listarParameter() {
        lbxParameter.getChildren().clear();

        Listitem listitem = new Listitem();
        listitem.setValue("os.codigo_paciente");
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
        listitem.setValue("os.codigo_orden");
        listitem.setLabel("Codigo orden");
        lbxParameter.appendChild(listitem);

        if (lbxParameter.getItemCount() > 0) {
            lbxParameter.setSelectedIndex(0);
        }
    }

    //Accion del formulario si es nuevo o consultar //
    public void accionForm(boolean sw, String accion) throws Exception {
        groupboxConsulta.setVisible(!sw);
        groupboxEditar.setVisible(sw);

        if (!accion.equalsIgnoreCase("registrar")) {
            buscarDatos();
        } else {
            //buscarDatos();
            limpiarDatos();
        }
        tbxAccion.setValue(accion);
    }

    // Limpiamos los campos del formulario //
    public void limpiarDatos() throws Exception {
        FormularioUtil.limpiarComponentes(groupboxEditar, idsExcluyentes);

        tbxCodigo_dx1.setValue("Z000");
        tbxNomDx1.setValue(cargarDX("Z000"));

        tbxCodigo_prestador.setValue("000000000");
        tbxNomPrestador.setValue(cargarPrestador("000000000"));

        tbxCodigo_paciente.setDisabled(false);

        lista_datos.clear();
        crearFilas();
    }

    private String cargarDX(String codigo_dx) {
        Cie cie = new Cie();
        cie.setCodigo(codigo_dx);
        cie = getServiceLocator().getServicio(GeneralExtraService.class).consultar(cie);

        return (cie != null ? cie.getNombre() : "");
    }

    private String cargarPrestador(String codigo_prestador) {
        Prestadores prestadores = new Prestadores();
        prestadores.setCodigo_empresa(codigo_empresa);
        prestadores.setCodigo_sucursal(codigo_sucursal);
        prestadores.setNro_identificacion(codigo_prestador);
        prestadores = getServiceLocator().getPrestadoresService().consultar(prestadores);

        return (prestadores != null ? prestadores.getNombres() + " " + prestadores.getApellidos() : "");
    }
	//Metodo para validar campos del formulario //

    private void parametrizarBandbox() {
        parametrizarBandboxPaciente();
        parametrizarBandboxPrestador();
        parametrizarBandboxDx();

    }

    private void parametrizarBandboxPaciente() {
        tbxCodigo_paciente.inicializar(tbxNomPaciente, btnLimpiarPaciente);
        tbxCodigo_paciente.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Paciente>() {

            @Override
            public void renderListitem(Listitem listitem, Paciente registro) {
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

                return Orden_servicio2Action.this
                        .getServiceLocator().getPacienteService()
                        .listar(parametros);
            }

            @Override
            public boolean seleccionarRegistro(Bandbox bandbox,
                    Textbox textboxInformacion, Paciente registro) {

                bandbox.setValue(registro.getNro_identificacion());
                textboxInformacion.setValue(registro
                        .getNombreCompleto());

                cargarDatosPaciente(registro);

                return true;
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
                borrarDatosPaciente();

            }
        });
    }

    private void parametrizarBandboxPrestador() {
        tbxCodigo_prestador.inicializar(tbxNomPrestador, btnLimpiarPrestador);
        tbxCodigo_prestador
                .setBandboxRegistrosIMG(new BandboxRegistrosIMG<Prestadores>() {

                    @Override
                    public void renderListitem(Listitem listitem,
                            Prestadores registro) {

                        Especialidad especialidad = new Especialidad();
                        especialidad.setCodigo(registro.getCodigo_especialidad());
                        especialidad = getServiceLocator().getEspecialidadService().consultar(especialidad);

                        Listcell listcell = new Listcell();
                        listcell.appendChild(new Label(registro.getNro_identificacion()));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(registro.getNombres()));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(registro.getApellidos()));
                        listitem.appendChild(listcell);

                        listcell = new Listcell();
                        listcell.appendChild(new Label(especialidad != null
                                        ? especialidad.getNombre() : ""));
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

                                return getServiceLocator().getPrestadoresService().listar(parametros);
                            }

                            @Override
                            public boolean seleccionarRegistro(Bandbox bandbox,
                                    Textbox textboxInformacion,
                                    Prestadores registro) {
                                bandbox.setValue(registro.getNro_identificacion());
                                textboxInformacion.setValue(registro.getNombres()
                                        + " " + registro.getApellidos());

                                return true;
                            }

                            @Override
                            public void limpiarSeleccion(Bandbox bandbox) {
                            }

                });
    }

    private void parametrizarBandboxDx() {
        tbxCodigo_dx1.inicializar(tbxNomDx1,
                btnLimpiarDx1);

        tbxCodigo_dx2.inicializar(tbxNomDx2,
                btnLimpiarDx2);

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
                bandbox.setValue(registro.getCodigo());
                textboxInformacion.setValue(registro.getNombre());
                return true;
            }

            @Override
            public void limpiarSeleccion(Bandbox bandbox) {
            }

        };

        tbxCodigo_dx1.setBandboxRegistrosIMG(bandboxRegistrosIMG);
        tbxCodigo_dx2.setBandboxRegistrosIMG(bandboxRegistrosIMG);
    }

    public void cargarDatosPaciente(Paciente paciente) {

        tbxNacimiento.setValue((paciente != null ? new java.text.SimpleDateFormat(
                "dd/MM/yyyy").format(paciente.getFecha_nacimiento()) : ""));
        tbxEdad.setValue((paciente != null ? Util.getEdad(new java.text.SimpleDateFormat(
                "dd/MM/yyyy").format(paciente.getFecha_nacimiento()), "1", true) : ""));
        tbxSexo.setValue((paciente != null ? Utilidades.getNombreElemento(paciente.getSexo(), "sexo", this) : ""));
        tbxTipo_identificacion.setValue((paciente != null ? paciente.getTipo_identificacion() : ""));
        tbxEstrato.setValue((paciente != null ? paciente.getEstrato() : ""));

    }

    public void borrarDatosPaciente() {
        tbxNacimiento.setValue("");
        tbxEdad.setValue("");
        tbxSexo.setValue("");
        tbxTipo_identificacion.setValue("");
        tbxEstrato.setValue("");
    }

    //Metodo para buscar //
    public void buscarDatos() throws Exception {
        try {
            String parameter = lbxParameter.getSelectedItem().getValue().toString();
            String value = tbxValue.getValue().toUpperCase().trim();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("parameter", parameter);
            parameters.put("value", "%" + value + "%");

            getServiceLocator().getOrden_servicio2Service().setLimit("limit 25 offset 0");

            List<Orden_servicio2> lista_datos = getServiceLocator().getOrden_servicio2Service().listar(parameters);
            rowsResultado.getChildren().clear();

            for (Orden_servicio2 orden_servicio2 : lista_datos) {
                rowsResultado.appendChild(crearFilas(orden_servicio2, this));
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

        final Orden_servicio2 orden_servicio2 = (Orden_servicio2) objeto;

        Paciente paciente = orden_servicio2.getPaciente();
        Prestadores prestadores = orden_servicio2.getPrestadores();

        Hbox hbox = new Hbox();
        Space space = new Space();

        fila.setStyle("text-align: justify;nowrap:nowrap");

        fila.setStyle("text-align: justify;nowrap:nowrap");
        fila.appendChild(new Label(orden_servicio2.getCodigo_orden()));
        fila.appendChild(new Label(new SimpleDateFormat("yyyy-MM-dd HH:mm").
                format(orden_servicio2.getFecha_orden())));
        fila.appendChild(new Label(orden_servicio2.getCodigo_paciente()));
        fila.appendChild(new Label((paciente != null ? paciente.getNombreCompleto() : "")));
        fila.appendChild(new Label((prestadores != null ? prestadores.getNombres() + " "
                + prestadores.getApellidos() : "")));

        hbox.appendChild(space);

        Image img = new Image();
        img.setSrc("/images/editar.gif");
        img.setTooltiptext("Editar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                mostrarDatos(orden_servicio2);
            }
        });
        hbox.appendChild(img);

        img = new Image();
        img.setSrc("/images/borrar.gif");
        img.setTooltiptext("Eliminar");
        img.setStyle("cursor: pointer");
        img.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
            @Override
            public void onEvent(Event arg0) throws Exception {
                Messagebox.show("Esta seguro que desea eliminar este registro? ",
                        "Eliminar Registro", Messagebox.YES + Messagebox.NO,
                        Messagebox.QUESTION,
                        new org.zkoss.zk.ui.event.EventListener<Event>() {
                            public void onEvent(Event event) throws Exception {
                                if ("onYes".equals(event.getName())) {
                                    // do the thing
                                    eliminarDatos(orden_servicio2);
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

    public boolean validarForm() throws Exception {
        tbxCodigo_paciente.setStyle("text-transform:uppercase;background-color:white");
        tbxCodigo_prestador.setStyle("text-transform:uppercase;background-color:white");
        tbxCodigo_dx1.setStyle("text-transform:uppercase;background-color:white");

        boolean valida = true;

        String mensaje = "Los campos marcados con (*) son obligatorios";

        if (tbxCodigo_paciente.getText().trim().equals("")) {
            tbxCodigo_paciente.setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (tbxCodigo_prestador.getText().trim().equals("")) {
            tbxCodigo_prestador.setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }
        if (tbxCodigo_dx1.getText().trim().equals("")) {
            tbxCodigo_dx1.setStyle("text-transform:uppercase;background-color:#F6BBBE");
            valida = false;
        }

        if (valida && lista_datos.size() <= 0) {
            mensaje = "Debe crear al menos un registro de procedimientos";
            valida = false;
        }

        if (valida) {
            actualizarPagina();
            for (int i = 0; i < lista_datos.size(); i++) {
                Detalle_orden2 detalle_orden2 = lista_datos.get(i);
                if (detalle_orden2.getUnidades() <= 0) {
                    mensaje = "El valor de las cantidades en el servicio "
                            + detalle_orden2.getCodigo_cups()
                            + " no puede ser menor ni igual a cero";
                    valida = false;
                    i = lista_datos.size();
                }
            }
        }

        if (!valida) {
            MensajesUtil.mensajeAlerta(usuarios.getNombres() + " recuerde que...",
                    mensaje);
        }

        return valida;
    }

    //Metodo para guardar la informacion //
    public void guardarDatos() throws Exception {
        try {
            FormularioUtil.setUpperCase(groupboxEditar);
            if (validarForm()) {
                //Cargamos los componentes //
                final Map datos = new HashMap();

                Orden_servicio2 orden_servicio2 = new Orden_servicio2();
                orden_servicio2.setCodigo_empresa(empresa.getCodigo_empresa());
                orden_servicio2.setCodigo_sucursal(sucursal.getCodigo_sucursal());
                orden_servicio2.setCodigo_orden(tbxCodigo_orden.getValue());
                orden_servicio2.setFecha_orden(new Timestamp(dtbxFecha_orden.getValue().getTime()));
                orden_servicio2.setCodigo_paciente(tbxCodigo_paciente.getValue());
                orden_servicio2.setCodigo_prestador(tbxCodigo_prestador.getValue());
                orden_servicio2.setEstado(tbxEstado.getValue());
                orden_servicio2.setCreacion_date(new Timestamp(new GregorianCalendar().getTimeInMillis()));
                orden_servicio2.setUltimo_update(new Timestamp(new GregorianCalendar().getTimeInMillis()));
                orden_servicio2.setCreacion_user(usuarios.getCodigo().toString());
                orden_servicio2.setUltimo_user(usuarios.getCodigo().toString());
                orden_servicio2.setCodigo_dx1(tbxCodigo_dx1.getValue());
                orden_servicio2.setCodigo_dx2(tbxCodigo_dx2.getValue());
                orden_servicio2.setObservaciones(tbxObservaciones.getValue());

                datos.put("orden_servicio2", orden_servicio2);
                datos.put("lista_detalle", lista_datos);
                datos.put("accion", tbxAccion.getText());

                final Orden_servicio2 auxOrden_servicio2 = getServiceLocator().getOrden_servicio2Service().guardar(datos);

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
                                            imprimir(auxOrden_servicio2.getCodigo_orden());
                                        }
                                    }
                                });
                mostrarDatos(orden_servicio2);

            }

        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }

    }

    //Metodo para colocar los datos del objeto que se consulta en la vista //
    public void mostrarDatos(Object obj) throws Exception {
        Orden_servicio2 orden_servicio2 = (Orden_servicio2) obj;
        Paciente paciente = orden_servicio2.getPaciente();
        Prestadores prestadores = orden_servicio2.getPrestadores();
        try {
            tbxCodigo_orden.setValue(orden_servicio2.getCodigo_orden());
            dtbxFecha_orden.setValue(orden_servicio2.getFecha_orden());
            tbxCodigo_paciente.setValue(orden_servicio2.getCodigo_paciente());
            tbxNomPaciente.setValue((paciente != null ? paciente.getNombreCompleto() : ""));
            cargarDatosPaciente(paciente);

            tbxCodigo_prestador.setValue(orden_servicio2.getCodigo_prestador());
            tbxNomPrestador.setValue((prestadores != null ? prestadores.getNombres() + " "
                    + prestadores.getApellidos() : ""));
            tbxEstado.setValue(orden_servicio2.getEstado());
            tbxCodigo_dx1.setValue(orden_servicio2.getCodigo_dx1());
            tbxNomDx1.setValue(cargarDX(orden_servicio2.getCodigo_dx1()));
            tbxCodigo_dx2.setValue(orden_servicio2.getCodigo_dx2());
            tbxNomDx2.setValue(cargarDX(orden_servicio2.getCodigo_dx2()));
            tbxObservaciones.setValue(orden_servicio2.getObservaciones());

            lista_datos = orden_servicio2.getLista_detalle();
            crearFilas();

            tbxCodigo_paciente.setDisabled(true);

            //Mostramos la vista //
            tbxAccion.setText("modificar");
            accionForm(true, tbxAccion.getText());
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void eliminarDatos(Object obj) throws Exception {
        Orden_servicio2 orden_servicio2 = (Orden_servicio2) obj;
        try {
            int result = getServiceLocator().getOrden_servicio2Service().eliminar(orden_servicio2);
            if (result == 0) {
                throw new Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
            }

            Messagebox.show("Informacion se eliminó satisfactoriamente !!",
                    "Information", Messagebox.OK, Messagebox.INFORMATION);
        } catch (HealthmanagerException e) {
            MensajesUtil.mensajeError(e, "Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos", this);
        } catch (RuntimeException r) {
            MensajesUtil.mensajeError(r, "", this);
        }
    }

    public void abrirVentanaCups() {
        Map<String, Object> parametros = new HashMap<String, Object>();
        String columnas = "Codigo#100px|Nombre|Sexo#80px|L. inf.#80px|L. sup#80px";
        WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
                "CONSULTAR CUPS", Paquetes.HEALTHMANAGER,
                "CupsDao.listar", this, columnas,
                parametros);
        windowRegistros.mostrarWindow(lista_seleccionados);
    }

    @Override
    public void onSeleccionarRegistro(Object registro) {
        if (registro instanceof Cups) {
            onSeleccionarRegistroCups(registro);
        }
    }

    private void onSeleccionarRegistroCups(Object registro) {
        Cups cups = (Cups) registro;
        Detalle_orden2 detalle = new Detalle_orden2();
        detalle.setCodigo_empresa(codigo_empresa);
        detalle.setCodigo_sucursal(codigo_sucursal);
        detalle.setCodigo_cups(cups.getCodigo());
        detalle.setCups(cups);

        adicionarDetalle_orden(detalle);
    }

    @Override
    public Object[] celdasListitem(Object registro) {
        if (registro instanceof Cups) {
            return celdasListitemCups(registro);
        } else if (registro instanceof Orden_servicio2) {
            return celdasListitemOrden_servicio(registro);
        }

        return new Object[]{};

    }

    private Object[] celdasListitemCups(Object registro) {
        Cups cups = (Cups) registro;

        return new Object[]{cups.getCodigo(),
            cups.getNombre(),
            cups.getSexo(),
            cups.getLimite_inferior(),
            cups.getLimite_superior()};
    }

    public void adicionarDetalle_orden(Detalle_orden2 detalle) {
        try {
            lista_datos.add(detalle);
            crearFilas();
            // repintarGridCuentas();
        } catch (Exception e) {

            MensajesUtil.mensajeError(e, "", this);
        }
    }

    public void crearFilas() throws Exception {
        rowsOrden2.getChildren().clear();
        for (int j = 0; j < lista_datos.size(); j++) {
            Detalle_orden2 detalle = lista_datos.get(j);
            crearFilaDetalle_orden2(detalle, j);
        }
    }

    public void crearFilaDetalle_orden2(final Detalle_orden2 detalle, int j) throws Exception {
        final int index = j;

        Cups cups = detalle.getCups();

        String codigo_procedimiento = detalle.getCodigo_cups();
        String nombre_procedimiento = (cups != null ? cups.getNombre() : "");
        double unidades = detalle.getUnidades();

        final Row fila = new Row();
        fila.setStyle("text-align: center;nowrap:nowrap");

        // Columna codigo //
        Cell cell = new Cell();
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

        // Columna unidades //
        cell = new Cell();
        cell.setAlign("left");
        final Doublebox tbxUnidades = new Doublebox(unidades);
        tbxUnidades.setInplace(true);
        tbxUnidades.setId("unidades_" + j);
        tbxUnidades.setFormat("#,##0.00");
        tbxUnidades.setHflex("1");
        tbxUnidades.setReadonly(false);
        cell.appendChild(tbxUnidades);
        fila.appendChild(cell);

        // Columna borrar //
        cell = new Cell();
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
                                    actualizarPagina();
                                    lista_datos.remove(index);
                                    crearFilas();
                                }
                            }
                        });
            }
        });

        fila.setId("fila_" + j);

        rowsOrden2.appendChild(fila);

        gridOrden2.setVisible(true);
        gridOrden2.setMold("paging");
        gridOrden2.setPageSize(20);
        gridOrden2.applyProperties();
        gridOrden2.invalidate();
    }

    public void actualizarPagina() throws Exception {
        for (int i = 0; i < lista_datos.size(); i++) {
            Detalle_orden2 detalle_orden2 = lista_datos.get(i);

            Row fila = (Row) rowsOrden2.getFellow("fila_" + i);
            Doublebox tbxUnidades = (Doublebox) fila.getFellow("unidades_" + i);
            detalle_orden2.setUnidades(tbxUnidades.getValue());
            lista_datos.set(i, detalle_orden2);
        }
    }

    public void imprimir(String codigo_orden) {
        if (codigo_orden.equals("")) {
            Messagebox.show("La orden no se ha guardado todavía",
                    "Informacion ..", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }

        Map paramRequest = new HashMap();
        paramRequest.put("name_report", "Orden_servicio2");
        paramRequest.put("codigo_orden", codigo_orden);
        paramRequest.put("formato", lbxFormato.getSelectedItem().getValue()
                .toString());

        Component componente = Executions.createComponents(
                "/pages/printInformes.zul", this, paramRequest);
        final Window window = (Window) componente;
        window.setMode("modal");
        window.setMaximizable(true);
        window.setMaximized(true);

        // Clients.evalJavaScript("window.open('"+request.getContextPath()+"/pages/printInformes.zul"+parametros_pagina+"', '_blank');");
    }

    public void abrirWindowHistorial() {
        if (tbxCodigo_paciente.getValue().equals("")) {
            MensajesUtil.mensajeAlerta("Alerta!!", "Debe seleccionar el paciente");
            return;
        }
        Map map = new HashMap();
		//map.put("codigo_empresa", codigo_empresa);
        //map.put("codigo_sucursal", codigo_sucursal);
        map.put("codigo_paciente", tbxCodigo_paciente.getValue());

        String columnas = "Codigo orden#90px|Fecha#120px|Nro id#90px|Paciente|Prestador#200px";
        WindowRegistrosMacro windowRegistros = new WindowRegistrosMacro(
                "CONSULTAR HISTORIAL DE ORDENES DE SERVICIOS", Paquetes.HEALTHMANAGER,
                "Orden_servicio2Dao.listar", this, columnas,
                map);
        windowRegistros.getToolbar().setVisible(false);
        windowRegistros.setiImprimirRegistros(this);
        windowRegistros.setSeleccionar(false);
        windowRegistros.mostrarWindow(new ArrayList<String>());
        windowRegistros.onConsultarRegistros();

    }

    private Object[] celdasListitemOrden_servicio(Object registro) {
        Orden_servicio2 orden_servicio2 = (Orden_servicio2) registro;
        Paciente paciente = orden_servicio2.getPaciente();
        Prestadores prestadores = orden_servicio2.getPrestadores();

        return new Object[]{orden_servicio2.getCodigo_orden(),
            new SimpleDateFormat("yyyy-MM-dd").
            format(orden_servicio2.getFecha_orden()),
            orden_servicio2.getCodigo_paciente(),
            (paciente != null ? paciente.getNombreCompleto() : ""),
            (prestadores != null ? prestadores.getNombres() + " "
            + prestadores.getApellidos() : "")};
    }

    @Override
    public void imprimirRegistro(Object object) {

        Orden_servicio2 auxOrden_servicio2
                = (Orden_servicio2) object;
        imprimir(auxOrden_servicio2.getCodigo_orden());

    }

}
