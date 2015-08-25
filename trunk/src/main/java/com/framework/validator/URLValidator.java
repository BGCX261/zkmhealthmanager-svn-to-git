package com.framework.validator;

import healthmanager.modelo.bean.Empresa;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.http.DHtmlLayoutServlet;

import com.framework.util.Validador;
import com.softcomputo.composer.constantes.IParametrosSesion;

public class URLValidator extends DHtmlLayoutServlet {

    private static final long serialVersionUID = 2913072449450522286L;

	// private static Logger log = Logger.getLogger(URLValidator.class);

    /* constantes */
    public static final String USUARIO_AUTENTICADO = "USUARIO_AUTENTICADO";
    public static final String PROCESO_AUTENTICACION = "PROCESO_AUTENTICACION";

    public static final String URL_PAGINA = "URL_PAGINA";
    public static final String PAGINA_INICIO = "/pages/inicioSesion.zul";
    public static final String PAGINA_MAIN = "/main.zul";
    public static final String PAGINA_AUTENTICACION = "/autenticando.zul";
    public static final String PAGINA_NOTFOUND = "/pages/page_nofound.zul";

    public static final String TITULO_ERROR_PARAMETROS = "ERROR AL ACCEDER A LA APLICACION.";
    public static final String MSG_ERROR_PARAMETROS = "No se admiten parámetros en la URL. Esta intentando acceder a la aplicacion de manera incorrecta";

    public static final String TITULO_USUARIO_NO_REGISTRADO = "USUARIO NO REGISTRADO";
    public static final String MSG_USUARIO_NO_REGISTRADO = "No se encontraron usuarios con este username y este password";

    public static final String PAGINA_FALTA_PAGO = "/pages/falta_pago.zul";

    public static final String PAGINA_ADMIN_INDEX = "/admin/index.zul";

    @Override
    protected boolean process(Session sess, HttpServletRequest request,
            HttpServletResponse response, String path, boolean bRichlet)
            throws ServletException, IOException {

        boolean processState = false;
        boolean lock = false;

        if (path.endsWith("/timeout.zul")) {
            processState = super.process(sess, request, response, path,
                    bRichlet);
        } else {

            if (sess == null) {
                processState = super.process(sess, request, response,
                        PAGINA_INICIO, bRichlet);
            } else {
                Empresa empresa = (Empresa) sess.getAttribute(IParametrosSesion.PARAM_EMPRESA);
                if (empresa != null && Validador.isLock(empresa)) {
                    processState = super.process(sess, request, response,
                            PAGINA_FALTA_PAGO, bRichlet);
                    lock = true;
                } else {
                    if (path.endsWith(PAGINA_INICIO)) {
                        processState = super.process(sess, request, response,
                                PAGINA_INICIO, bRichlet);
                    } else if (path.endsWith(PAGINA_FALTA_PAGO)) {
                        processState = super.process(sess, request, response,
                                PAGINA_FALTA_PAGO, bRichlet);
                    } else if (path.endsWith(PAGINA_ADMIN_INDEX)) {
                        processState = super.process(sess, request, response,
                                PAGINA_ADMIN_INDEX, bRichlet);
                    } else if (sess.getAttribute(IParametrosSesion.PARAM_EMPRESA) != null
                            && sess.getAttribute(IParametrosSesion.PARAM_SUCURSAL) != null
                            && sess.getAttribute(IParametrosSesion.PARAM_USUARIO) != null) {
                        processState = super.process(sess, request, response,
                                path, bRichlet);
                    }
                }
            }

            if (!processState) {
                if (lock) {
                    return super.process(sess, request, response,
                            PAGINA_FALTA_PAGO, bRichlet);
                } else {
                    return super.process(sess, request, response,
                            PAGINA_NOTFOUND, bRichlet);
                }
            }
        }

        return processState;

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

}
