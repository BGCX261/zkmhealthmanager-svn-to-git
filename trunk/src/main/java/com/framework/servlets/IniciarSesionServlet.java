package com.framework.servlets;

import healthmanager.exception.BloqueoException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Registros_entradas;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.Registros_entradasService;
import healthmanager.modelo.service.Roles_usuarios_capsService;
import healthmanager.modelo.service.UsuariosService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.WrongValueException;

import com.framework.constantes.IConstantes;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.util.Validador;
import com.framework.validator.URLValidator;
import healthmanager.modelo.service.GeneralExtraService;

/**
 * Servlet implementation class IniciarSesionServlet
 */
@Deprecated
public class IniciarSesionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

//	private static Logger log = Logger.getLogger(IniciarSesionServlet.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IniciarSesionServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            ServletContext servletContext = request.getSession()
                    .getServletContext();
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            if (login != null && password != null) {
                try {
                    String habilitar_re = Labels.getLabel("app.habilitar_re");
                    Usuarios usuarios = new Usuarios();
                    usuarios.setLogin(login);
                    usuarios.setPassword(password);
                    
                    UsuariosService usuariosService = getServiceLocator(
                            servletContext).getUsuariosService();
                    usuarios = usuariosService.consultar(usuarios);
                    
                    if (usuarios == null) {
                        Paciente paciente = new Paciente();
                        paciente.setLogin(login.toUpperCase());
                        paciente.setPassword(password.trim());
                        paciente = getServiceLocator(servletContext)
                                .getPacienteService()
                                .consultarPorLoginPassword(paciente);
                        if (paciente == null) {
                            throw new ValidacionRunTimeException(
                                    "Error al obtener usuario. Login y password no coinciden !!!");
                        } else { // cargamos la sesion del paciente
                            Empresa empresa = new Empresa();
                            empresa.setCodigo_empresa(paciente
                                    .getCodigo_empresa());
                            empresa = this.getServiceLocator(servletContext)
                                    .getServicio(GeneralExtraService.class).consultar(empresa);
                            
                            Validador.validar(empresa);
                            
                            Sucursal sucursal = new Sucursal();
                            sucursal.setCodigo_empresa(paciente
                                    .getCodigo_empresa());
                            sucursal.setCodigo_sucursal(paciente
                                    .getCodigo_sucursal());
                            sucursal = this.getServiceLocator(servletContext)
                                    .getServicio(GeneralExtraService.class).consultar(sucursal);
                            
                            Parametros_empresa parametros_empresa = new Parametros_empresa();
                            parametros_empresa.setCodigo_empresa(empresa
                                    .getCodigo_empresa());
                            parametros_empresa = getServiceLocator(
                                    servletContext)
                                    .getServicio(GeneralExtraService.class).consultar(
                                            parametros_empresa);
                            
                            Resolucion resolucion = new Resolucion();
                            resolucion.setCodigo_empresa(empresa
                                    .getCodigo_empresa());
                            resolucion = getServiceLocator(servletContext)
                                    .getResolucionService().consultar(
                                            resolucion);
                            
                            loadPeriodo(request, resolucion);
                            
                            request.getSession().setAttribute("usuarios",
                                    paciente);
                            request.getSession().setAttribute("empresa",
                                    empresa);
                            request.getSession().setAttribute("sucursal",
                                    sucursal);
                            request.getSession().setAttribute(
                                    "parametros_empresa", parametros_empresa);
                            request.getSession().setAttribute("resolucion",
                                    resolucion);
                            request.getSession().setAttribute(
                                    Attributes.PREFERRED_LOCALE,
                                    IConstantes.locale);
                            
                            if (habilitar_re != null
                                    && habilitar_re.equalsIgnoreCase("S")) {
                                Registros_entradas registros_entradas = new Registros_entradas();
                                registros_entradas.setCodigo_empresa(empresa
                                        .getCodigo_empresa());
                                registros_entradas.setCodigo_usuario(paciente
                                        .getNro_identificacion());
                                registros_entradas.setCodigo_sucursal(paciente
                                        .getCodigo_sucursal());
                                registros_entradas.setTipo("P");
                                registros_entradas.setEstado("1");
                                registros_entradas
                                        .setFecha_ingreso(new Timestamp(
                                                        new Date().getTime()));
                                getServiceLocator(servletContext).getServicio(
                                        Registros_entradasService.class).crear(
                                                registros_entradas);
                                request.getSession().setAttribute(
                                        "registros_entradas",
                                        registros_entradas);
                            }
                            
                            response.sendRedirect(request.getContextPath() + "/pages/mainAfiliado.zul");
                        }
                    } else {
                        // Listamos los roles que tiene asignado el usuario //
                        Empresa empresa = new Empresa();
                        empresa.setCodigo_empresa(usuarios.getCodigo_empresa());
                        empresa = this.getServiceLocator(servletContext)
                                .getServicio(GeneralExtraService.class).consultar(empresa);
                        
                        Validador.validar(empresa);
                        
                        Sucursal sucursal = new Sucursal();
                        sucursal.setCodigo_empresa(usuarios.getCodigo_empresa());
                        sucursal.setCodigo_sucursal(usuarios
                                .getCodigo_sucursal());
                        sucursal = this.getServiceLocator(servletContext)
                                .getServicio(GeneralExtraService.class).consultar(sucursal);
                        
                        Parametros_empresa parametros_empresa = new Parametros_empresa();
                        parametros_empresa.setCodigo_empresa(empresa
                                .getCodigo_empresa());
                        parametros_empresa = getServiceLocator(servletContext)
                                .getServicio(GeneralExtraService.class).consultar(
                                        parametros_empresa);
                        
                        Resolucion resolucion = new Resolucion();
                        resolucion.setCodigo_empresa(empresa
                                .getCodigo_empresa());
                        resolucion = getServiceLocator(servletContext)
                                .getResolucionService().consultar(resolucion);
                        
                        Map<String, Object> parameters = new HashMap();
                        parameters.put("codigo_empresa",
                                usuarios.getCodigo_empresa());
                        parameters.put("codigo_sucursal",
                                usuarios.getCodigo_sucursal());
                        parameters.put("codigo_usuario", usuarios.getCodigo());
                        List<Roles_usuarios> lista_roles_usuarios = getServiceLocator(
                                servletContext).getRoles_usuariosService()
                                .listar(parameters);
                        
                        if (lista_roles_usuarios.size() == 0) {
                            throw new ValidacionRunTimeException(
                                    "Lo sentimos pero el usuario: "
                                    + usuarios.getNombres()
                                    + " "
                                    + usuarios.getApellidos()
                                    + " no tiene roles "
                                    + "asignados consulte con el administrador del sistema para que asigne sus roles...");
                        }
                        
                        Roles_usuarios roles = lista_roles_usuarios.get(0);

						// log.info("Ingresó el usuario: " +
                        // usuarios.getNombres() + " "
                        // + usuarios.getApellidos() + "...");
                        loadPeriodo(request, resolucion);

                        // Consultamos sentro de salud
                        Map<String, Object> parametros = new HashMap<String, Object>();
                        parametros.put("codigo_empresa",
                                empresa.getCodigo_empresa());
                        parametros.put("codigo_sucursal",
                                sucursal.getCodigo_sucursal());
                        parametros.put("codigo_usuario", usuarios.getCodigo());
                        parametros.put("rol", roles.getRol());
                        
                        List<Roles_usuarios_caps> listado_caps_asignados = getServiceLocator(
                                servletContext).getServicio(
                                        Roles_usuarios_capsService.class).listar(
                                        parametros);
                        
                        if (!listado_caps_asignados.isEmpty()) {
                            Roles_usuarios_caps roles_usuarios_caps = listado_caps_asignados
                                    .get(0);
                            Centro_atencion centro_atencion = new Centro_atencion();
                            centro_atencion
                                    .setCodigo_empresa(roles_usuarios_caps
                                            .getCodigo_empresa());
                            centro_atencion
                                    .setCodigo_sucursal(roles_usuarios_caps
                                            .getCodigo_sucursal());
                            centro_atencion
                                    .setCodigo_centro(roles_usuarios_caps
                                            .getCodigo_centro());
                            centro_atencion = getServiceLocator(servletContext)
                                    .getCentro_atencionService().consultar(
                                            centro_atencion);
                            request.getSession().setAttribute(
                                    "centro_atencion", centro_atencion);
                        }
                        
                        Map<String, Object> mapParametrosCentro = new HashMap<String, Object>();
                        mapParametrosCentro.put("rol", roles.getRol());
                        mapParametrosCentro.put("codigo_empresa",
                                usuarios.getCodigo_empresa());
                        mapParametrosCentro.put("codigo_sucursal",
                                usuarios.getCodigo_sucursal());
                        mapParametrosCentro.put("codigo_usuario",
                                usuarios.getCodigo());
                        boolean existe = getServiceLocator(servletContext)
                                .getServicio(Roles_usuarios_capsService.class)
                                .existe(parameters);
                        if (!existe) {
                            Elemento elemento = new Elemento();
                            elemento.setTipo("rol");
                            elemento.setCodigo(roles.getRol());
                            elemento = getServiceLocator(servletContext)
                                    .getServicio(ElementoService.class)
                                    .consultar(elemento);
                            
                            throw new ValidacionRunTimeException(
                                    "Este usuario no tiene un centro de atencion asignado para el rol "
                                    + (elemento != null ? elemento
                                    .getDescripcion()
                                    : "ROL NO ENCONTRADO"));
                        }
                        
                        request.getSession().setAttribute("rol_usuario",
                                roles.getRol());
                        request.getSession().setAttribute("usuarios", usuarios);
                        request.getSession().setAttribute("empresa", empresa);
                        request.getSession().setAttribute("sucursal", sucursal);
                        request.getSession().setAttribute("parametros_empresa",
                                parametros_empresa);
                        request.getSession().setAttribute("saludo", "_saludar");
                        request.getSession().setAttribute("resolucion",
                                resolucion);
                        
                        if (habilitar_re != null
                                && habilitar_re.equalsIgnoreCase("S")) {
                            Registros_entradas registros_entradas = new Registros_entradas();
                            registros_entradas.setCodigo_empresa(empresa
                                    .getCodigo_empresa());
                            registros_entradas.setCodigo_usuario(usuarios
                                    .getCodigo());
                            registros_entradas.setEstado("1");
                            registros_entradas.setCodigo_sucursal(usuarios
                                    .getCodigo_sucursal());
                            registros_entradas.setTipo("U");
                            registros_entradas.setFecha_ingreso(new Timestamp(
                                    new Date().getTime()));
                            getServiceLocator(servletContext).getServicio(
                                    Registros_entradasService.class).crear(
                                            registros_entradas);
                            request.getSession().setAttribute(
                                    "registros_entradas", registros_entradas);
                        }
                        
                        request.getSession().setAttribute(
                                Attributes.PREFERRED_LOCALE, IConstantes.locale);
                        
                        response.sendRedirect(request.getContextPath() + "/pages/indexPage.zul?pageInicioSesion=");
                        
                    }
                } catch (ValidacionRunTimeException e) {
                    
                } catch (BloqueoException e) {
                    Validador.lock(e.getEmpresa());
                    request.getSession()
                            .setAttribute("empresa", e.getEmpresa());
                    response.sendRedirect(request.getContextPath() + URLValidator.PAGINA_FALTA_PAGO);
                } catch (WrongValueException e) {
                    
                } catch (Exception e) {
                    
                }
                
            }
        } finally {
            
        }
    }
    
    private void loadPeriodo(HttpServletRequest request, Resolucion resolucion) {
        /* cargamos periodo */
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        
        if (resolucion != null) {
            if (!resolucion.getAnio().equalsIgnoreCase("PR")
                    && resolucion.getAnio().matches("[0-9]*$")) {
                year = Integer.parseInt(resolucion.getAnio());
            }
            
            if (!resolucion.getMes().equalsIgnoreCase("PR")
                    && resolucion.getMes().matches("[0-9]*$")) {
                month = Integer.parseInt(resolucion.getMes()) - 1;
            }
        }
        // log.info("@loadPeriodo Anio: " + year + " mes: " + month);

        request.getSession().setAttribute("anio", year);
        request.getSession().setAttribute("mes", month);
        /* fin envio de periodo */
    }
    
    public ServiceLocatorWeb getServiceLocator(ServletContext servletContext) {
        return ServiceLocatorWeb.getInstance();
    }
    
}
