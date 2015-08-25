package healthmanager.controller;

import healthmanager.exception.ValidacionRunTimeException;
import healthmanager.modelo.bean.Paciente;
import healthmanager.modelo.service.PacienteService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.framework.util.MensajesUtil;

import contaweb.modelo.bean.Caja;
import contaweb.modelo.service.CajaService;

public class RecibosCajaAction extends ZKWindow {

	// Componentes //
	@View
	private Listbox lbxParameter;
	@View
	private Textbox tbxValue;
	@View
	private Groupbox groupboxConsulta;
	@View
	private Rows rowsResultado;
	@View
	private Grid gridResultado;
	
	@View private Datebox dtbxFecha_inicial;
	@View private Datebox dtbxFecha_final;

	@Override
	public void init() throws Exception {
       listarParameter();
	}

	public void listarParameter() {
		lbxParameter.getChildren().clear();
		
		Listitem listitem = new Listitem();
		listitem.setValue("caja.codigo_recibo");
		listitem.setLabel("Código Recibo");
		lbxParameter.appendChild(listitem);
		
		listitem = new Listitem();
		listitem.setValue("ter.nro_identificacion || ' ' || ter.apellido1 || ' ' || ter.apellido2 || ' ' || ter.nombre1 || ' ' || ter.nombre2");
		listitem.setLabel("Paciente");
		lbxParameter.appendChild(listitem);
		
		if (lbxParameter.getItemCount() > 0) {
			lbxParameter.setSelectedIndex(0);
		}
	}
	
	
	public void buscarDatos() throws Exception {
		try {
			String parameter = lbxParameter.getSelectedItem().getValue()
					.toString();
			String value = tbxValue.getValue().toUpperCase().trim();

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("parameter", parameter);
			parameters.put("value", "%" + value + "%");
			parameters.put("codigo_empresa", getUsuarios().getCodigo_empresa());
			parameters.put("codigo_sucursal", getUsuarios()
					.getCodigo_sucursal());
			
			parameters.put("fecha_in", dtbxFecha_inicial.getValue());
			parameters.put("fecha_fn", dtbxFecha_final.getValue());
			
			// filtramos los recibos de caja
//			parameters.put("tipo_recibo_in", new String[]{
//					Recibo_cajaAction.TIPO_CAJA_COPAGO,
//					Recibo_cajaAction.TIPO_CAJA_CUOTA_MODERADORA,
//					Recibo_cajaAction.TIPO_CAJA_PARTICULAR
//			});

			getServiceLocator().getServicio(CajaService.class).setLimit(
					"LIMIT 25");

			List<Caja> lista_datos = getServiceLocator().getServicio(
					CajaService.class).listar(parameters);
			rowsResultado.getChildren().clear();

			for (Caja caja : lista_datos) {
				rowsResultado.appendChild(crearFilas(caja));
			}

			gridResultado.setVisible(true);
			gridResultado.setMold("paging");
			gridResultado.setPageSize(20);

			gridResultado.applyProperties();
			gridResultado.invalidate();
			gridResultado.setVisible(true);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), "Mensaje de Error !!",
					Messagebox.OK, Messagebox.ERROR);
		}
	}

	public Row crearFilas(final Caja caja) throws Exception {
		Row fila = new Row();

		Hbox hbox = new Hbox();
		Space space = new Space();

		String nombre_afiliado = "* NO ENCONTRADO";
		String apellido_afiliado = "* NO ENCONTRADO";
		String documento_afiliado = "* NO ENCONTRADO";
		String tipo_doc_afiliado = "* NO ENCONTRADO";
		Paciente paciente = new Paciente();
		paciente.setCodigo_empresa(caja.getCodigo_empresa());
		paciente.setCodigo_sucursal(caja.getCodigo_sucursal());
		paciente.setNro_identificacion(caja.getCodigo_tercero());
		paciente = getServiceLocator().getServicio(PacienteService.class)
				.consultar(paciente);

		if (paciente != null) {
			nombre_afiliado = paciente.getNombre1() + " "
					+ paciente.getNombre2();
			apellido_afiliado = paciente.getApellido1() + " "
					+ paciente.getApellido2();
			tipo_doc_afiliado = paciente.getTipo_identificacion();
			documento_afiliado = paciente.getDocumento();
		}
		final Paciente paciente_final = paciente;

		fila.setStyle("text-align: justify;nowrap:nowrap");
		/* Seccion cotizante */

		fila.appendChild(new Label(caja.getCodigo_recibo() + ""));
		fila.appendChild(new Label(formatFecha.format(caja.getFecha()) + ""));
		fila.appendChild(new Label(tipo_doc_afiliado + ""));
		fila.appendChild(new Label(documento_afiliado + ""));
		fila.appendChild(new Label(nombre_afiliado + ""));
		fila.appendChild(new Label(apellido_afiliado + ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Imprimir");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener<Event>() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				try {
					mostar(caja, paciente_final);
				} catch (Exception e) {
					MensajesUtil.mensajeAlerta("Advertencia",
							"" + e.getMessage());
				}
			}
		});
		hbox.appendChild(img);
		fila.appendChild(hbox);

		return fila;
	}

	protected void mostar(Caja caja, Paciente paciente) throws Exception {
		if(paciente == null){
			throw new ValidacionRunTimeException("El recibo no se puede mostar por que el paciente no existe");
		}
		
		Map parametros = new HashMap();
		parametros.put("caja", caja);
		parametros.put("paciente", paciente);
		parametros.put("cuota_moderadora", caja.getValor_recibo());
		parametros.put("solo_imprimir", "solo_imprimir");
		
		Component componente = Executions.createComponents(
				"/pages/caja_cuota_moderadora.zul", this, parametros);
		Window win = (Window) componente;
		win.setWidth("100%");
		win.setHeight("100%");
		win.setMode("modal");
	}
}
