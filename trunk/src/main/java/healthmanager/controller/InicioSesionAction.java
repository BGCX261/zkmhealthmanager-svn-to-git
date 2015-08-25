/**
 *
 */
package healthmanager.controller;

import healthmanager.exception.BloqueoException;
import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Centro_atencion;
import healthmanager.modelo.bean.Elemento;
import healthmanager.modelo.bean.Empresa;
import healthmanager.modelo.bean.Parametros_empresa;
import healthmanager.modelo.bean.Registros_entradas;
import healthmanager.modelo.bean.Resolucion;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.bean.Roles_usuarios_caps;
import healthmanager.modelo.bean.Sucursal;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Centro_atencionService;
import healthmanager.modelo.service.ElementoService;
import healthmanager.modelo.service.GeneralExtraService;
import healthmanager.modelo.service.Registros_entradasService;
import healthmanager.modelo.service.ResolucionService;
import healthmanager.modelo.service.Roles_usuariosService;
import healthmanager.modelo.service.Roles_usuarios_capsService;
import healthmanager.modelo.service.UsuariosService;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.util.resource.Labels;
import org.zkoss.web.Attributes;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.constantes.IConstantes;
import com.framework.notificaciones.Notificaciones;
import com.framework.util.FormularioUtil;
import com.framework.util.Validador;
import com.framework.validator.URLValidator;
import com.softcomputo.composer.ControladorComposer;
import com.softcomputo.composer.constantes.IParametrosSesion;

/**
 * @author ferney
 *
 */
public class InicioSesionAction extends ControladorComposer<Window> {

	private static final Logger log = Logger
			.getLogger(InicioSesionAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

	@WireVariable
	private UsuariosService usuariosService;
	@WireVariable
	private ResolucionService resolucionService;
	@WireVariable
	private Registros_entradasService registros_entradasService;
	@WireVariable
	private Roles_usuariosService roles_usuariosService;
	@WireVariable
	private Roles_usuarios_capsService roles_usuarios_capsService;
	@WireVariable
	private Centro_atencionService centro_atencionService;
	@WireVariable
	private ElementoService elementoService;
	@WireVariable
	private GeneralExtraService generalExtraService;

	@Wire
	private Textbox tbxLogin;
	@Wire
	private Textbox tbxPassword;

	@Override
	public void init() throws Exception {
		if (getParametroSesion(IParametrosSesion.PARAM_USUARIO) != null) {
			Executions.sendRedirect("/pages/indexPage.zul");
		} else if (getParametroSesion(IParametrosSesion.PARAM_PACIENTE) != null) {
			Executions.sendRedirect("/pages/mainAfiliado.zul");
		} else {
			eliminarParametroSesion(IParametrosSesion.PARAM_USUARIO);
			eliminarParametroSesion(IParametrosSesion.PARAM_ROL_USUARIO);
			eliminarParametroSesion(IParametrosSesion.PARAM_EMPRESA);
			eliminarParametroSesion(IParametrosSesion.PARAM_SUCURSAL);
			eliminarParametroSesion(IParametrosSesion.PARAM_REG_ENTRADAS);
		}
	}

	// Metodo para iniciar seSIÓN //
	@Listen("onClick = #btnIniciar;onOK=#tbxPassword")
	public void iniciarSesion() throws Exception {
		try {
			String habilitar_re = Labels.getLabel("app.habilitar_re");
			FormularioUtil.validarCamposObligatorios(tbxLogin, tbxPassword);
			FormularioUtil.limpiarComponentesRestricciones(tbxLogin);
			FormularioUtil.limpiarComponentesRestricciones(tbxPassword);
			Usuarios usuarios = new Usuarios();
			usuarios.setLogin(tbxLogin.getText().trim().toUpperCase());
			usuarios.setPassword(tbxPassword.getText().trim());

			usuarios = usuariosService.consultar(usuarios);

			if (usuarios == null) {
				throw new ValidacionRunTimeException(
						"Error al obtener usuario. Login y password no coinciden !!!");
			} else {
				// Listamos los roles que tiene asignado el usuario //
				Empresa empresa = new Empresa();
				empresa.setCodigo_empresa(usuarios.getCodigo_empresa());
				empresa = generalExtraService.consultar(empresa);

				Validador.validar(empresa);

				Sucursal sucursal = new Sucursal();
				sucursal.setCodigo_empresa(usuarios.getCodigo_empresa());
				sucursal.setCodigo_sucursal(usuarios.getCodigo_sucursal());
				sucursal = generalExtraService.consultar(sucursal);

				Parametros_empresa parametros_empresa = new Parametros_empresa();
				parametros_empresa.setCodigo_empresa(empresa
						.getCodigo_empresa());
				parametros_empresa = generalExtraService
						.consultar(parametros_empresa);

				Resolucion resolucion = new Resolucion();
				resolucion.setCodigo_empresa(empresa.getCodigo_empresa());
				resolucion = resolucionService.consultar(resolucion);

				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("codigo_empresa", usuarios.getCodigo_empresa());
				parameters
						.put("codigo_sucursal", usuarios.getCodigo_sucursal());
				parameters.put("codigo_usuario", usuarios.getCodigo());
				List<Roles_usuarios> lista_roles_usuarios = roles_usuariosService
						.listar(parameters);

				if (lista_roles_usuarios.isEmpty()) {
					throw new ValidacionRunTimeException(
							"Lo sentimos pero el usuario: "
									+ usuarios.getNombres()
									+ " "
									+ usuarios.getApellidos()
									+ " no tiene roles "
									+ "asignados consulte con el administrador del sistema para que asigne sus roles...");
				}

				Roles_usuarios roles = lista_roles_usuarios.get(0);

				log.info("Ingresó el usuario: " + usuarios.getNombres() + " "
						+ usuarios.getApellidos() + "...");
				cargarPeriodo(resolucion);

				// Consultamos sentro de salud
				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", empresa.getCodigo_empresa());
				parametros
						.put("codigo_sucursal", sucursal.getCodigo_sucursal());
				parametros.put("codigo_usuario", usuarios.getCodigo());
				parametros.put("rol", roles.getRol());

				List<Roles_usuarios_caps> listado_caps_asignados = roles_usuarios_capsService
						.listar(parametros);

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
					centro_atencion = centro_atencionService
							.consultar(centro_atencion);
					setParametroSesion(IParametrosSesion.PARAM_CENTRO_ATENCION,
							centro_atencion);
				}

				Map<String, Object> mapParametrosCentro = new HashMap<String, Object>();
				mapParametrosCentro.put("rol", roles.getRol());
				mapParametrosCentro.put("codigo_empresa",
						usuarios.getCodigo_empresa());
				mapParametrosCentro.put("codigo_sucursal",
						usuarios.getCodigo_sucursal());
				mapParametrosCentro.put("codigo_usuario", usuarios.getCodigo());
				boolean existe = roles_usuarios_capsService.existe(parameters);
				if (!existe) {
					Elemento elemento = new Elemento();
					elemento.setTipo("rol");
					elemento.setCodigo(roles.getRol());
					elemento = elementoService.consultar(elemento);

					throw new ValidacionRunTimeException(
							"Este usuario no tiene un centro de atencion asignado para el rol "
									+ (elemento != null ? elemento
											.getDescripcion()
											: "ROL NO ENCONTRADO"));
				}
				setParametroSesion(IParametrosSesion.PARAM_ROL_USUARIO,
						roles.getRol());
				setParametroSesion(IParametrosSesion.PARAM_USUARIO, usuarios);
				setParametroSesion(IParametrosSesion.PARAM_EMPRESA, empresa);
				setParametroSesion(IParametrosSesion.PARAM_SUCURSAL, sucursal);
				setParametroSesion(IParametrosSesion.PARAM_PARAMETROS_EMPRESA,
						parametros_empresa);
				setParametroSesion(IParametrosSesion.PARAM_RESOLUCION,
						resolucion);

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
					registros_entradasService.crear(registros_entradas);
					setParametroSesion(IParametrosSesion.PARAM_REG_ENTRADAS,
							registros_entradas);
				}
				setParametroSesion(Attributes.PREFERRED_LOCALE,
						IConstantes.locale);
				Executions
						.sendRedirect("/pages/indexPage.zul?pageInicioSesion=");
			}
		} catch (ValidacionRunTimeException e) {

			tbxPassword.setText("");
			tbxPassword.focus();
			Notificaciones
					.mostrarNotificacionAlerta("Advertencia", e.getMessage(),
							IConstantes.TIEMPO_NOTIFICACIONES_GENERALES);
		} catch (BloqueoException e) {
			Validador.lock(e.getEmpresa());
			setParametroSesion(IParametrosSesion.PARAM_EMPRESA, e.getEmpresa());
			Executions.sendRedirect(URLValidator.PAGINA_FALTA_PAGO);
		} catch (WrongValueException e) {
			tbxLogin.focus();
		}
	}

	private void cargarPeriodo(Resolucion resolucion) {
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

		setParametroSesion(IParametrosSesion.PARAM_ANIO, year);
		setParametroSesion(IParametrosSesion.PARAM_MES, month);
		/* fin envio de periodo */
	}

	public void autoInicioSesion() throws Exception {
		tbxLogin.setValue("ADMIN");
		tbxPassword.setValue("ADMIN");
		iniciarSesion();
	}

}
