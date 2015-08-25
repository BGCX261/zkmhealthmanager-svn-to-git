package healthmanager.controller.reportes;

import healthmanager.controller.ZKWindow;
import healthmanager.modelo.bean.Administradora;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Foot;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Toolbarbutton;

import com.framework.factory.Generador4505Factory;
import com.framework.macros.BandboxRegistrosMacro;
import com.framework.macros.impl.BandboxRegistrosIMG;
import com.framework.util.FormularioUtil;
import com.framework.util.MensajesUtil;
import com.framework.util.Utilidades;

public class Resolucion4505Action extends ZKWindow {

	@View
	private Toolbarbutton btImprimir;
	@View
	private Toolbarbutton btNew;
	@View
	private Toolbarbutton btCancel;
	@View
	private Listbox lbxFormato;
	@View
	private Checkbox chkTrabajarMese;

	@View
	private Listbox lbxSeparador;

	@View
	private Datebox dtbxFechaIncio;
	@View
	private Datebox dtbxFechaFinal;
	@View
	private Label lbCodigo_administradora;
	@View
	private Toolbarbutton btnLimpiarAseguradora;
	@View(isMacro = true)
	private BandboxRegistrosMacro tbxCodigo_administradora;
	@View
	private Textbox tbxInfoAseguradora;
	@View
	private Foot fotProceso;
	
	
	@View private Timer tmTemporizador;
	@View private Timer tmVisualizador;
	@View private Progressmeter pmProceso;
	
	private long total;
	private long posicion;

	private static final String SEPARADOR_4505 = "|";

	public static final SimpleDateFormat formatFecha = new SimpleDateFormat(
			"yyyyMMddhhmmss");

	@Override
	public void init() throws Exception {
		inicializarCompoenentes();
		inicializarTimer();
	}
	
	private void inicializarTimer(){
		tmTemporizador.addEventListener(Events.ON_TIMER,
				new EventListener<Event>() {
					@Override
					public void onEvent(Event arg0) throws Exception {
						log.info("hola2"); 
                       inicializarProceso();
                       tmTemporizador.stop();
                       tmVisualizador.stop();
						FormularioUtil.deshabilitarComponentes(
								Resolucion4505Action.this, false);
						fotProceso.setVisible(false); 	
//						Clients.clearBusy(Resolucion4505Action.this); 
					}
		});
	}

	private void inicializarCompoenentes() {
		parametrizarBandBox();
		inicializarCombos();
	}

	private void inicializarCombos() {
		// Cargamos los tipos de separador registrados en la tabla elemento
		Utilidades.listarElementoListbox(lbxSeparador, false,
				getServiceLocator());

		// Seleccionamos el separador por defecto de la resolucion 4505
		Utilidades.setValueFrom(lbxSeparador, SEPARADOR_4505);
	}

	private void parametrizarBandBox() {
		parametrizarBandBoxAdministradora();
	}

	public void limpiar() {
		FormularioUtil.limpiarComponentes(this);
	}

	private void parametrizarBandBoxAdministradora() {
		tbxCodigo_administradora.inicializar(tbxInfoAseguradora,
				btnLimpiarAseguradora);
		tbxCodigo_administradora
				.setBandboxRegistrosIMG(new BandboxRegistrosIMG<Administradora>() {

					@Override
					public void renderListitem(Listitem listitem,
							Administradora registro) {
						listitem.appendChild(new Listcell(registro.getCodigo()));
						listitem.appendChild(new Listcell(registro.getNombre()));
						listitem.appendChild(new Listcell(registro.getNit()));
					}

					@Override
					public List<Administradora> listarRegistros(
							String valorBusqueda, Map<String, Object> parametros) {
						parametros.put("codigo_empresa", getEmpresa()
								.getCodigo_empresa());
						parametros.put("codigo_sucursal", getSucursal()
								.getCodigo_sucursal());
						parametros.put("tercerizada", "N");
						parametros.put("paramTodo", "paramTodo");
						parametros.put("value", valorBusqueda);
						return getServiceLocator().getAdministradoraService()
								.listar(parametros);
					}

					@Override
					public boolean seleccionarRegistro(Bandbox bandbox,
							Textbox textboxInformacion, Administradora registro) {
						bandbox.setValue(registro.getCodigo());
						textboxInformacion.setValue(registro.getNit() + " "
								+ registro.getNombre());
						return true;
					}

					@Override
					public void limpiarSeleccion(Bandbox bandbox) {

					}
				});

		// Habilitamos busqueda
		tbxCodigo_administradora.setReadonly(false);
	}

	public void generar(){
		FormularioUtil.deshabilitarComponentes(this, true); 
//	    fotProceso.setVisible(true);
//		tmTemporizador.start();
//		
//		while (true) { 
//			if(total > 0){
//				pmProceso.setValue((int)((posicion) * 100 / total));
//			}
//		}
		inicializarProceso();
		FormularioUtil.deshabilitarComponentes(this, false); 
	}
	
	//generar 4505
	public void inicializarProceso() {
		final File archivo_guardado = getDirectorio();
		try {
			if (validar()) {
				final String formato = lbxFormato.getSelectedItem().getValue();
				String separador = lbxSeparador.getSelectedItem().getValue();
				Administradora administradora = tbxCodigo_administradora
						.getRegistroSeleccionado();
				// generamos la 4505
				new Generador4505Factory().generarArchivoResolucion4505(
						administradora, dtbxFechaIncio.getValue(),
						dtbxFechaFinal.getValue(), archivo_guardado, separador,
						formato, chkTrabajarMese.isChecked(), getEmpresa(),
						getServiceLocator()); 
			}
		} catch (Exception e) {
			if (archivo_guardado != null) {
				archivo_guardado.delete();
			}
			MensajesUtil.mensajeError(e, null, this);
		}
	}
	

	private boolean validar() {
		boolean validar = true;
		String msj = "Los campos marcados * son obligatorios";
		try {
			FormularioUtil.validarCamposObligatorios(tbxCodigo_administradora,
					dtbxFechaFinal, dtbxFechaIncio);
		} catch (Exception e) {
			return false;
		}
		
		if(validar){
			if(dtbxFechaIncio.getValue().compareTo(dtbxFechaFinal.getValue()) > 0){
				validar = false;
				msj = "La fecha de inicio no puede ser mayor que la fecha final";
			}
		}
		
		if(!validar){
			MensajesUtil.mensajeAlerta("Advertencia", msj); 
		}
		
		return validar;
	}

	private File getDirectorio() {
		File file = new File(Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath("")
				+ "/Files");
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(file.getAbsolutePath() + "/Temp");
		if (!file.exists()) {
			file.mkdir();
		}

		file = new File(file.getAbsolutePath() + "/"
				+ empresa.getCodigo_empresa() + ""
				+ sucursal.getCodigo_sucursal() + usuarios.getCodigo()
				+ formatFecha.format(Calendar.getInstance().getTime()));
		if (!file.exists()) {
			file.mkdir();
		}
		return file;
	}

}
