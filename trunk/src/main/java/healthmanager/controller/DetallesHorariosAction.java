/*
 * horariosAction.java
 * 
 * Generado Automaticamente .
 * Tecnologo. Luis Miguel Hernandez 
 */
package healthmanager.controller;

import healthmanager.modelo.bean.Detalles_horarios;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Spinner;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

public class DetallesHorariosAction extends ZKWindow {

//	private static Logger log = Logger.getLogger(DetallesHorariosAction.class);
	private static final long serialVersionUID = -9145887024839938515L;

//	private HorariosAction parent_horarios;

//	private Window form;

	// Componentes //

	private String nombre_dia;
	private String dia_semana;
	private Integer posicion;
	private Integer posicion_relativa;

	@View
	private Textbox tbxDiaSemana;
	@View
	private Textbox tbxNombreDia;

	@View
	private Spinner dspInicioHoras;
	@View
	private Spinner dspInicioMinutos;
	@View
	private Listbox lbxAmPmInicio;

	@View
	private Spinner dspFinalHoras;
	@View
	private Spinner dspFinalMinutos;
	@View
	private Listbox lbxAmPmFinal;

	public void listarCombos() {
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {

	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {
		boolean valida = true;
		String mensaje = "";
		if (valida) {
			if (dspInicioHoras.getValue() == null
					|| dspInicioMinutos.getValue() == null
					|| dspFinalHoras.getValue() == null
					|| dspFinalMinutos.getValue() == null) {
				mensaje = "Hay campos vacios o iguales a cero en las la configuracion de las horas";
				valida = false;
			}
		}

		if (!valida) {
			Messagebox.show(mensaje,
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public void eliminarDatos(Object obj) throws Exception {
		// Horarios_medico horarios = (Horarios_medico)obj;
		// try{
		// int result =
		// getServiceLocator().getHorariosService().eliminar(horarios);
		// if(result==0){
		// throw new
		// Exception("Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
		// }
		//
		// Messagebox.show("Informacion se eliminó satisfactoriamente !!","Information",
		// Messagebox.OK, Messagebox.INFORMATION);
		// }catch (ServinotasException e) {
		// 
		// log.error(e.getMessage(), e);
		// Messagebox.show("Este objeto no se puede eliminar por que esta relacionado con otra tabla en la base de datos",
		// "Error !!", Messagebox.OK, Messagebox.ERROR);
		// }catch(RuntimeException r){
		// log.error(r.getMessage(), r);
		// Messagebox.show(r.getMessage(),"Error !!", Messagebox.OK,
		// Messagebox.ERROR);
		// }
	}

	public void agregarDetalleDeHorario(String dia) throws Exception {
		Map<String, Object> parameters = new HashMap();
		Component componente = Executions.createComponents(
				"/pages/detalles_horarios.zul", this, parameters);
		final Window window = (Window) componente;
		window.setMode("modal");
	}

	public void guardarDetalleDeHorario() throws Exception {
		if (validarForm()) {
//			String hora_inicio = dspInicioHoras.getText()
//					+ ":"
//					+ (dspInicioMinutos.getText().length() == 1 ? "0"
//							+ dspInicioMinutos.getText() : dspInicioMinutos
//							.getText()) + " "
//					+ lbxAmPmInicio.getSelectedItem().getValue().toString();
//			String hora_final = dspFinalHoras.getText()
//					+ ":"
//					+ (dspFinalMinutos.getText().length() == 1 ? "0"
//							+ dspFinalMinutos.getText() : dspFinalMinutos
//							.getText()) + " "
//					+ lbxAmPmFinal.getSelectedItem().getValue().toString();

			//log.info("inicio horas : " + hora_inicio);
			//log.info("final horas : " + hora_final);

			Detalles_horarios detalle = new Detalles_horarios();

			detalle.setCodigo_empresa(empresa.getCodigo_empresa());
			detalle.setCodigo_sucursal(sucursal.getCodigo_sucursal());
			detalle.setCreacion_date(new Timestamp((new Date()).getTime()));
			detalle.setCreacion_user(usuarios.getCodigo());
			// detalle.setDescanso(false);
			// detalle.setDia_semana(getDia_semana());
			// detalle.setHora_inicio(hora_inicio);
			// detalle.setHora_final(hora_final);
			// detalle.setNombre_dia(getNombre_dia());
			// detalle.setPosicion(getPosicion());
			// detalle.setPosicion_relativa(getPosicion_relativa());

//			TreeMap<Integer, Detalles_horarios> mapa_detalles = null;

//			if (parent_horarios.getHorario_clases()
//					.containsKey(getDia_semana())) {
//				mapa_detalles = parent_horarios.getHorario_clases().get(
//						getDia_semana());
//				mapa_detalles.put(posicion, detalle);
//			} else {
//				mapa_detalles = new TreeMap<Integer, Detalles_horarios>();
//				mapa_detalles.put(posicion, detalle);
//				parent_horarios.getHorario_clases().put(getDia_semana(),
//						mapa_detalles);
//			}
//			parent_horarios.dibujarHorarioPorDia(getDia_semana());
			detach();
		}
	}

	public void setNombre_dia(String nombre_dia) {
		this.nombre_dia = nombre_dia;
	}

	public String getNombre_dia() {
		return nombre_dia;
	}

	public void setDia_semana(String dia_semana) {
		this.dia_semana = dia_semana;
	}

	public String getDia_semana() {
		return dia_semana;
	}

	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	public Integer getPosicion() {
		return posicion;
	}

	public void setPosicion_relativa(Integer posicion_relativa) {
		this.posicion_relativa = posicion_relativa;
	}

	public Integer getPosicion_relativa() {
		return posicion_relativa;
	}

	@Override
	public void init() {
//		parent_horarios = (HorariosAction) getParent();
		initData();
		listarCombos();
	}

	private void initData() {
		Map<String, Object> parameters = (Map<String, Object>) Executions.getCurrent().getArg();
//		String codigo_grado = (String) parameters.get("codigo_grado");
//		String codigo_grupo = (String) parameters.get("codigo_grupo");
//		String anio = (String) parameters.get("anio");
		String nombre_dia = (String) parameters.get("nombre_dia");
		String dia_semana = (String) parameters.get("dia_semana");
		Integer posicion = (Integer) parameters.get("posicion");
		Integer posicion_relativa = (Integer) parameters
				.get("posicion_relativa");

		setNombre_dia(nombre_dia);
		setDia_semana(dia_semana);
		setPosicion(posicion);
		setPosicion_relativa(posicion_relativa);

		tbxNombreDia.setValue(getNombre_dia().toUpperCase());
	}
}
