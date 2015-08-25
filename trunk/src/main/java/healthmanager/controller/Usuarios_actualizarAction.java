/*
 * usuariosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologos: 
	Ferney Jimenez Lopez
	Luis Hernadez Perez
	Dario Perez Campillo
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Datos_consulta;
import healthmanager.modelo.bean.Horarios_medico;
import healthmanager.modelo.bean.Prestadores;
import healthmanager.modelo.bean.Roles_usuarios;
import healthmanager.modelo.bean.Usuarios;
import healthmanager.modelo.service.Datos_consultaService;
import healthmanager.modelo.service.PrestadoresService;
import healthmanager.modelo.service.Roles_usuariosService;
import healthmanager.modelo.service.UsuariosService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Usuarios_actualizarAction extends ZKWindow {

	private UsuariosService usuariosService;

	private Roles_usuariosService roles_usuariosService;

	private PrestadoresService prestadoresService;

	private List<Prestadores> listado_prestadores = new ArrayList<Prestadores>();

	private List<Prestadores> listado_prestadores_eliminar = new ArrayList<Prestadores>();

	@View
	private Textbox tbxUsuariosCrear;

	@View
	private Textbox tbxUsuariosEliminar;

	@Override
	public void init() {
		
	}

	public void buscarDatos() {
		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros.put("codigo_empresa", codigo_empresa);
		parametros.put("codigo_sucursal", codigo_sucursal);

		List<Usuarios> listado_usuarios = usuariosService.listar(parametros);

		StringBuilder stringBuilderCrear = new StringBuilder();
		StringBuilder stringBuilderEliminar = new StringBuilder();

		for (Usuarios usuarios : listado_usuarios) {
			parametros.put("codigo_usuario", usuarios.getCodigo());

			List<Roles_usuarios> listado_roles = roles_usuariosService
					.listar(parametros);

			boolean rol_prestador = false;
			String tipo_prestador = "01";

			for (Roles_usuarios roles_usuarios : listado_roles) {
				if (Utilidades.isEnfermeraJefe(roles_usuarios.getRol())) {
					rol_prestador = true;
					tipo_prestador = "02";
				} else if (Utilidades.isMedico(roles_usuarios.getRol())) {
					rol_prestador = true;
					tipo_prestador = "01";
				}
			}

			Prestadores prestadores = new Prestadores();
			prestadores.setCodigo_empresa(usuarios.getCodigo_empresa());
			prestadores.setCodigo_sucursal(usuarios.getCodigo_sucursal());
			prestadores.setNro_identificacion(usuarios.getCodigo());

			if (rol_prestador) {
				String especialidad = tipo_prestador.equals("01") ? "21" : "60";
				if (usuarios.getCodigo_especialidad() != null) {
					if (!usuarios.getCodigo_especialidad().isEmpty()) {
						especialidad = usuarios.getCodigo_especialidad();
					}
				}
				stringBuilderCrear.append(usuarios.getCodigo()).append(" ")
						.append(usuarios.getNombres()).append(" ")
						.append(usuarios.getApellidos()).append("\n");
				prestadores.setTipo_identificacion("CC");
				prestadores.setApellidos(usuarios.getApellidos());
				prestadores.setNombres(usuarios.getNombres());
				prestadores.setDireccion(usuarios.getDireccion());
				prestadores.setTel_oficina(usuarios.getTel_oficina());
				prestadores.setTel_res(usuarios.getTel_res());
				prestadores.setCelular(usuarios.getCelular());
				prestadores.setRegistro_medico(usuarios.getRegistro_medico());
				prestadores.setTipo(usuarios.getTipo_med());
				prestadores.setCodigo_especialidad(especialidad);
				prestadores.setCreacion_date(usuarios.getCreacion_date());
				prestadores.setUltimo_update(usuarios.getUltimo_update());
				prestadores.setCreacion_user(usuarios.getCreacion_user());
				prestadores.setUltimo_user(usuarios.getUltimo_user());
				prestadores.setLogin(usuarios.getLogin());
				prestadores.setPassword(usuarios.getPassword());
				prestadores.setTipo_medico(usuarios.getTipo_medico());
				prestadores.setTipo_prestador(tipo_prestador);

				listado_prestadores.add(prestadores);
			} else {
				stringBuilderEliminar.append(usuarios.getCodigo()).append(" ")
						.append(usuarios.getNombres()).append(" ")
						.append(usuarios.getApellidos()).append("\n");

				listado_prestadores_eliminar.add(prestadores);
			}

		}

		tbxUsuariosCrear.setValue(stringBuilderCrear.toString());
		tbxUsuariosEliminar.setValue(stringBuilderEliminar.toString());

	}

	public void actualizarPrestadores() {
		for (Prestadores prestadores : listado_prestadores) {
			Prestadores prestadores_aux = prestadoresService
					.consultar(prestadores);
			if (prestadores_aux == null) {
				prestadoresService.crear(prestadores);
			} else {
				prestadores_aux.setTipo_prestador(prestadores
						.getTipo_prestador());
			}
		}

		for (Prestadores prestadores : listado_prestadores_eliminar) {
			if (prestadoresService.consultar(prestadores) != null) {
				Horarios_medico horarios_medico = new Horarios_medico();
				horarios_medico.setCodigo_empresa(codigo_empresa);
				horarios_medico.setCodigo_sucursal(codigo_sucursal);
				horarios_medico.setCodigo_medico(prestadores
						.getNro_identificacion());

				getServiceLocator().getHorariosMedicoService().eliminar(
						horarios_medico);

				Map<String, Object> parametros = new HashMap<String, Object>();
				parametros.put("codigo_empresa", codigo_empresa);
				parametros.put("codigo_sucursal", codigo_sucursal);
				parametros.put("codigo_prestador",
						prestadores.getNro_identificacion());

				List<Datos_consulta> listado_consultas = getServiceLocator()
						.getServicio(Datos_consultaService.class).listarTabla(
								parametros);

				for (Datos_consulta datos_consulta : listado_consultas) {
					getServiceLocator()
							.getServicio(Datos_consultaService.class).eliminarRegistro(
									datos_consulta);
				}

				prestadoresService.eliminar(prestadores);
			}
		}

		MensajesUtil.mensajeInformacion("Informacion",
				"Datos actualizados satisfactoriamente");
	}

}