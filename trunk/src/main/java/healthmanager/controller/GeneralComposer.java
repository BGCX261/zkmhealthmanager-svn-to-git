package healthmanager.controller;

import healthmanager.exception.BloqueoException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zul.Window;

import com.framework.interfaces.IHistoria_generica;
import com.framework.interfaces.WindowStandar;
import com.framework.locator.ServiceLocatorWeb;
import com.framework.util.MensajesUtil;
import com.framework.util.Validador;
import com.framework.validator.URLValidator;
import com.softcomputo.composer.constantes.IParametrosSesion;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class GeneralComposer extends Window implements AfterCompose,
        WindowStandar, Serializable {

    private static final long serialVersionUID = -8014105834933331170L;

    public static Logger log = Logger.getLogger(GeneralComposer.class);

    /* end medico */
    public Empresa empresa;
    protected Sucursal sucursal;
    public Usuarios usuarios;
    public Paciente paciente_session;
    public String rol_usuario;
    public Centro_atencion centro_atencion_session;

    public static final SimpleDateFormat formatFecha = new SimpleDateFormat(
            "yyyy-MM-dd");
    public static final SimpleDateFormat formatHora = new SimpleDateFormat(
            "hh:mm a");

    /* campos inicializados para todas las vistas */
    public String codigo_empresa;
    public String codigo_sucursal;

    public Parametros_empresa parametros_empresa;
    public Resolucion resolucion;

    public Integer anio;
    public Integer mes;

    public void cargarDatosSesion() {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
                .getNativeRequest();
        empresa = ServiceLocatorWeb.getEmpresa(request);
        sucursal = ServiceLocatorWeb.getSucursal(request);
        usuarios = ServiceLocatorWeb.getUsuarios(request);
        rol_usuario = (String) request.getSession().getAttribute(IParametrosSesion.PARAM_ROL_USUARIO);
        centro_atencion_session = (Centro_atencion) request.getSession()
                .getAttribute(IParametrosSesion.PARAM_CENTRO_ATENCION);
        HttpSession session = request.getSession();
        Object userEnSession = session.getAttribute(IParametrosSesion.PARAM_USUARIO);
        if (userEnSession instanceof Paciente) {
            paciente_session = (Paciente) userEnSession;
        }

        codigo_empresa = empresa != null ? empresa.getCodigo_empresa() : "null";
        codigo_sucursal = sucursal != null ? sucursal.getCodigo_sucursal()
                : "null";

        /* cargamos los parametros de la empresa */
        parametros_empresa = (Parametros_empresa) session
                .getAttribute(IParametrosSesion.PARAM_PARAMETROS_EMPRESA);
        resolucion = (Resolucion) session.getAttribute(IParametrosSesion.PARAM_RESOLUCION);
        try {
            Validador.validar(empresa);
        } catch (BloqueoException e) {
            Validador.lock(empresa);
            Executions.getCurrent()
                    .sendRedirect(URLValidator.PAGINA_FALTA_PAGO);
        }

        /* aplicamos como atributo */
        afterCargarDatosSession(request);
        anio = (Integer) session.getAttribute(IParametrosSesion.PARAM_ANIO);
        mes = (Integer) session.getAttribute(IParametrosSesion.PARAM_MES);
    }

    public void afterCargarDatosSession(HttpServletRequest request) {
    }

    @SuppressWarnings("unchecked")
    @Override
    public void afterCompose() {
        try {
            Selectors.wireVariables(this, this,
                    Selectors.newVariableResolvers(getClass(), null));
            HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
                    .getNativeRequest();
            HttpSession session = request.getSession();
            beforeInit();
            initComponents(this);
            cargarDatosSesion();
            params((Map<String, Object>) Executions.getCurrent().getArg());
            init();
            _despuesIniciar();
            if (this instanceof IHistoria_generica) {
                this.addEventListener(Events.ON_CREATE,
                        new EventListener<Event>() {

                            @Override
                            public void onEvent(Event arg0) throws Exception {
                                ((IHistoria_generica) GeneralComposer.this)
                                .initHistoria();
                            }
                        });

            }
            validarTiempoSesion();
        } catch (Exception e) {
            MensajesUtil.mensajeError(e, "", this);
        }
    }

    /**
     * Este metodo se ejecuta despues que termina el metodo init
	 *
     */
    public void _despuesIniciar() {
    }

    public void _recargar() {
        Executions.getCurrent().sendRedirect("indexPage.zul", "_top");
    }

    public void params(Map<String, Object> map) {
    }

    public void onError(Throwable throwable) {
    }

    public void beforeInit() {
    }

    public abstract void init() throws Exception;

    public String getCreacionUser() {
        if (usuarios == null) {
            return paciente_session.getNro_identificacion();
        } else {
            return usuarios.getCodigo();
        }
    }

    public void actualizarParametroEnSesion(String nombre, Object parametro) {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
                .getNativeRequest();
        request.getSession().setAttribute(nombre, parametro);
    }

    public Object getParametroSesion(String nombre) {
        HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
                .getNativeRequest();
        return request.getSession().getAttribute(nombre);
    }

    public String getUserInSessionError() {
        if (usuarios == null) {
            if (paciente_session == null) {
                return paciente_session.getNro_identificacion() + " "
                        + paciente_session.getNombreCompleto()
                        + " - (ROL : PACIENTE)";
            } else {
                return "No se pudo cargar el nombre del usuario";
            }
        } else {
            HttpServletRequest request = (HttpServletRequest) Executions.getCurrent()
                    .getNativeRequest();
            String rol = (String) request.getSession().getAttribute(
                    "rol_usuario");
            if (rol == null) {
                rol = "";
            }

            String nombre_rol = "";
            if (rol.equals("01")) {
                nombre_rol = "Administrador";
            } else if (rol.equals("02")) {
                nombre_rol = "Facturador";
            } else if (rol.equals("03")) {
                nombre_rol = "Auditor";
            } else if (rol.equals("04")) {
                nombre_rol = "Farmacia";
            } else if (rol.equals("05")) {
                nombre_rol = "Medico";
            } else if (rol.equals("06")) {
                nombre_rol = "Recepcion";
            } else if (rol.equals("07")) {
                nombre_rol = "AUXILIAR DE AUDITORIA";
            } else {
                nombre_rol = "No tiene un rol correcto";
            }
            return usuarios.getCodigo() + " " + usuarios.getNombres() + " "
                    + usuarios.getApellidos() + " - (ROL : " + nombre_rol + ")";
        }
    }

    public void adicionarPcd(Map<String, Object> pcd) throws Exception {
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface View {

        String field() default "$";

        Class<?> classField() default View.class;

        boolean isStringCheck() default false;

        boolean isMacro() default false;
    }

    private void initComponents(Component component) throws Exception {
        Field[] fields = component.getClass().getDeclaredFields();
        StringBuffer stringBuffer = new StringBuffer();
        for (Field field : fields) {
            field.setAccessible(true);
            View view = field.getAnnotation(View.class);
            if (view != null) {
                Object object = null;
                if (!component.hasFellow(field.getName())) {
                    stringBuffer.append("'").append(field.getName())
                            .append("', ");
                } else {
                    object = view.isMacro() ? component.getFellow(
                            field.getName()).getFirstChild() : component
                            .getFellow("" + field.getName());
                    field.set(component, object);
                    _componenteInicializado((Component) object);
                }
                continue;
            }
        }

        if (!stringBuffer.toString().isEmpty()) {
            throw new Exception("NO se encontraron los fellows siguientes: \n"
                    + stringBuffer.toString(), new Exception(
                            "NO se encontraron los fellows siguientes:  \n"
                            + stringBuffer.toString()));
        }

    }

    /**
     * Este metodo me permite, obtener el componente cuando de se incialice
	 *
     */
    public void _componenteInicializado(Component component) {

    }

    public enum OpcionesFormulario {

        REGISTRAR, MODIFICAR, MOSTRAR, CONSULTAR
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public Parametros_empresa getParametros_empresa() {
        return parametros_empresa;
    }

    public void setParametros_empresa(Parametros_empresa parametros_empresa) {
        this.parametros_empresa = parametros_empresa;
    }

    public String getRol_usuario() {
        return rol_usuario;
    }

    public void setRol_usuario(String rol_usuario) {
        this.rol_usuario = rol_usuario;
    }

    public HttpServletRequest getHttpServletRequest() {
        return (HttpServletRequest) Executions.getCurrent().getNativeRequest();
    }

    public HttpServletResponse getHttpServletResponse() {
        return (HttpServletResponse) Executions.getCurrent()
                .getNativeResponse();
    }

    /**
     * Este metodo me permite eliminar componentes centralizado
     *
     * @author Luis Miguel
	 *
     */
    public void _removerComponente(String id) {
        Component component = getFellowIfAny(id);
        if (component != null) {
            component.detach();
        }
    }

    public int cantidad(Enumeration<?> enumeracion) {
        int cont = 0;
        while (enumeracion.hasMoreElements()) {
            enumeracion.nextElement();
            cont++;
        }

        return cont;
    }

    public void mostrarLog() {
        log.info("Evento onTimer()");
    }

    public void validarTiempoSesion() throws Exception {

    }

}
