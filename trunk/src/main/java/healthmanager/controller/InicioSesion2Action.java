/**
 * 
 */
package healthmanager.controller;

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

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.modelo.service.GeneralService;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.Validador;
import com.framework.validator.URLValidator;

/**
 * @author ferney
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InicioSesion2Action extends Window implements AfterCompose {

	private static Logger log = Logger.getLogger(InicioSesion2Action.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@WireVariable
	private GeneralService generalService;

	private Textbox tbxLogin;
	private Textbox tbxPassword;

	private HttpServletRequest request;
	private HttpSession session;

	public void afterCompose() {
		// Library.setProperty("org.zkoss.theme.preferred", "sapphire");
		Selectors.wireVariables(this, this,
				Selectors.newVariableResolvers(getClass(), null));
		loadComponents();
		request = (HttpServletRequest) Executions.getCurrent()
				.getNativeRequest();
		session = request.getSession();

		if (session.getAttribute("usuarios") != null) {
			Executions.sendRedirect("/pages/indexPage.zul");
		} else if (session.getAttribute("paciente") != null) {
			Executions.sendRedirect("/pages/mainAfiliado.zul");
		} else {
			session.removeAttribute("usuarios");
			session.removeAttribute("rol_sistema");
			session.removeAttribute("empresa");
			session.removeAttribute("sucursal");
			session.removeAttribute("registros_entradas");
		}
	}

	public void loadComponents() {
		tbxLogin = (Textbox) this.getFellow("tbxLogin");
		tbxPassword = (Textbox) this.getFellow("tbxPassword");
	}

	// Metodo para iniciar seSIÓN //
	public void iniciarSesion() throws Exception {
		try {
			String habilitar_re = Labels.getLabel("app.habilitar_re");
			FormularioUtil.validarCamposObligatorios(tbxLogin, tbxPassword);
			FormularioUtil.limpiarComponentesRestricciones(tbxLogin);
			FormularioUtil.limpiarComponentesRestricciones(tbxPassword);
			Usuarios usuarios = new Usuarios();
			usuarios.setLogin(tbxLogin.getText().trim().toUpperCase());
			usuarios.setPassword(tbxPassword.getText().trim());

			usuarios = generalService.consultar(usuarios);

			if (usuarios == null) {
				Paciente paciente = new Paciente();
				paciente.setLogin(tbxLogin.getText().trim().toUpperCase());
				paciente.setPassword(tbxPassword.getText().trim());
				paciente = (Paciente) generalService
						.consultar(
								"healthmanager.modelo.dao.PacienteDao.consultarPorLoginPassword",
								paciente);
				if (paciente == null) {
					throw new ValidacionRunTimeException(
							"Error al obtener usuario. Login y password no coinciden !!!");
				} else { // cargamos la sesion del paciente
					Empresa empresa = new Empresa();
					empresa.setCodigo_empresa(paciente.getCodigo_empresa());
					empresa = generalService.consultar(empresa);

					Validador.validar(empresa);

					Sucursal sucursal = new Sucursal();
					sucursal.setCodigo_empresa(paciente.getCodigo_empresa());
					sucursal.setCodigo_sucursal(paciente.getCodigo_sucursal());
					sucursal = generalService.consultar(sucursal);

					Parametros_empresa parametros_empresa = new Parametros_empresa();
					parametros_empresa.setCodigo_empresa(empresa
							.getCodigo_empresa());
					parametros_empresa = generalService.consultar(

					parametros_empresa);

					Resolucion resolucion = new Resolucion();
					resolucion.setCodigo_empresa(empresa.getCodigo_empresa());
					resolucion = generalService.consultar(resolucion);

					loadPeriodo(request, resolucion);

					session.setAttribute("usuarios", paciente);
					session.setAttribute("empresa", empresa);
					session.setAttribute("sucursal", sucursal);
					session.setAttribute("parametros_empresa",
							parametros_empresa);
					session.setAttribute("resolucion", resolucion);
					session.setAttribute(Attributes.PREFERRED_LOCALE,
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
						registros_entradas.setFecha_ingreso(new Timestamp(
								new Date().getTime()));
						generalService
								.crear("healthmanager.modelo.dao.Registros_entradasDao.crear",
										registros_entradas);
						session.setAttribute("registros_entradas",
								registros_entradas);
					}

					Executions.getCurrent().sendRedirect(
							"/pages/mainAfiliado.zul");
				}
			} else {
				// Listamos los roles que tiene asignado el usuario //
				Empresa empresa = new Empresa();
				empresa.setCodigo_empresa(usuarios.getCodigo_empresa());
				empresa = generalService.consultar(

				empresa);

				Validador.validar(empresa);

				Sucursal sucursal = new Sucursal();
				sucursal.setCodigo_empresa(usuarios.getCodigo_empresa());
				sucursal.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				sucursal = generalService.consultar(

				sucursal);

				Parametros_empresa parametros_empresa = new Parametros_empresa();
				parametros_empresa.setCodigo_empresa(empresa
						.getCodigo_empresa());
				parametros_empresa = generalService.consultar(

				parametros_empresa);

				Resolucion resolucion = new Resolucion();
				resolucion.setCodigo_empresa(empresa.getCodigo_empresa());
				resolucion = generalService.consultar(

				resolucion);

				Map<String, Object> parameters = new HashMap();
				parameters.put("codigo_empresa", usuarios.getCodigo_empresa());
				parameters
						.put("codigo_sucursal", usuarios.getCodigo_sucursal());
				parameters.put("codigo_usuario", usuarios.getCodigo());
				List<Roles_usuarios> lista_roles_usuarios = generalService
						.listar(Roles_usuarios.class, parameters);

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

				// log.info("Ingresó el usuario: " + usuarios.getNombres() +
				// " "
				// + usuarios.getApellidos() + "...");

				loadPeriodo(request, resolucion);

				// Consultamos sentro de salud
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", empresa.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				parametros.put("codigo_usuario", usuarios.getCodigo());
				parametros.put("rol", roles.getRol());

				List<Roles_usuarios_caps> listado_caps_asignados = generalService
						.listar(Roles_usuarios_caps.class, parametros);

				if (!listado_caps_asignados.isEmpty()) {
					Roles_usuarios_caps roles_usuarios_caps = listado_caps_asignados
							.get(0);
					Centro_atencion centro_atencion = new Centro_atencion();
					centro_atencion.setCodigo_empresa(roles_usuarios_caps
							.getCodigo_empresa());
					centro_atencion.setCodigo_sucursal(roles_usuarios_caps
							.getCodigo_sucursal());
					centro_atencion.setCodigo_centro(roles_usuarios_caps
							.getCodigo_centro());
					centro_atencion = generalService.consultar(

					centro_atencion);
					session.setAttribute("centro_atencion", centro_atencion);
				}

				Map<String, Object> mapParametrosCentro = new HashMap<String, Object>();
				mapParametrosCentro.put("rol", roles.getRol());
				mapParametrosCentro.put("codigo_empresa",
						usuarios.getCodigo_empresa());
				mapParametrosCentro.put("codigo_sucursal",
						usuarios.getCodigo_sucursal());
				mapParametrosCentro.put("codigo_usuario", usuarios.getCodigo());
				boolean existe = generalService.existe(Centro_atencion.class,
						parameters);
				if (!existe) {
					Elemento elemento = new Elemento();
					elemento.setTipo("rol");
					elemento.setCodigo(roles.getRol());
					elemento = generalService.consultar(

					elemento);

					throw new ValidacionRunTimeException(
							"Este usuario no tiene un centro de atencion asignado para el rol "
									+ (elemento != null ? elemento
											.getDescripcion()
											: "ROL NO ENCONTRADO"));
				}

				session.setAttribute("rol_usuario", roles.getRol());
				session.setAttribute("usuarios", usuarios);
				session.setAttribute("empresa", empresa);
				session.setAttribute("sucursal", sucursal);
				session.setAttribute("parametros_empresa", parametros_empresa);
				session.setAttribute("saludo", "_saludar");
				session.setAttribute("resolucion", resolucion);

				if (habilitar_re != null && habilitar_re.equalsIgnoreCase("S")) {
					Registros_entradas registros_entradas = new Registros_entradas();
					registros_entradas.setCodigo_empresa(empresa
							.getCodigo_empresa());
					registros_entradas.setCodigo_usuario(usuarios.getCodigo());
					registros_entradas.setEstado("1");
					registros_entradas.setCodigo_sucursal(usuarios
							.getCodigo_sucursal());
					registros_entradas.setTipo("U");
					registros_entradas.setFecha_ingreso(new Timestamp(
							new Date().getTime()));
					generalService.crear(registros_entradas);
					session.setAttribute("registros_entradas",
							registros_entradas);
				}

				session.setAttribute(Attributes.PREFERRED_LOCALE,
						IConstantes.locale);

				Executions
						.sendRedirect("/pages/indexPage.zul?pageInicioSesion=");
			}
		} catch (ValidacionRunTimeException e) {

			tbxPassword.setText("");
			tbxPassword.focus();
			Notificaciones.mostrarNotificacionAlerta("Advertencia",
					e.getMessage(), IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		} catch (BloqueoException e) {
			Validador.lock(e.getEmpresa());
			session.setAttribute("empresa", e.getEmpresa());
			Executions.sendRedirect(URLValidator.PAGINA_FALTA_PAGO);
		} catch (WrongValueException e) {
			tbxLogin.focus();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			tbxPassword.setText("");
			tbxPassword.focus();
			Messagebox.show(e.getMessage(), "ERROR !!", Messagebox.OK,
					Messagebox.ERROR);
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
		// zlog.info("@loadPeriodo Anio: " + year + " mes: " + month);

		session.setAttribute("anio", year);
		session.setAttribute("mes", month);
		/* fin envio de periodo */
	}

	public void autoInicioSesion() throws Exception {
		tbxLogin.setValue("ADMIN");
		tbxPassword.setValue("ADMIN");
		iniciarSesion();
	}

}
