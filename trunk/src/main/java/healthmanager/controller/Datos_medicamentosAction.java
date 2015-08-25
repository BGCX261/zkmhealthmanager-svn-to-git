/*
 * datos_medicamentosAction.java
 * 
 * Generado Automaticamente .
 * Luis Miguel Hernandez Perez 
 */
package healthmanager.controller;

import healthmanager.exception.HealthmanagerException;
import healthmanager.modelo.bean.Datos_medicamentos;
import healthmanager.modelo.bean.Elemento;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;

import com.framework.util.MensajesUtil;

public class Datos_medicamentosAction extends ZKWindow {


	// Componentes //
	private Textbox tbxAccion;
	@View
	private Borderlayout groupboxEditar;

	@View
	private Textbox tbxNro_factura;
	@View
	private Textbox tbxConsecutivo;
	@View
	private Textbox tbxCodigo_bodega;
	@View
	private Textbox tbxCodigo_medicamento;
	@View
	private Textbox tbxCodigo_lote;
	@View
	private Intbox ibxUnidades;
	@View
	private Doublebox dbxValor_unitario;
	@View
	private Doublebox dbxValor_total;
	@View
	private Textbox tbxCancelo_copago;
	@View
	private Datebox dtbxDelete_date;
	@View
	private Textbox tbxDelete_user;
	@View
	private Doublebox dbxValor_real;
	@View
	private Doublebox dbxDescuento;
	@View
	private Doublebox dbxIncremento;
	@View
	private Intbox ibxCantidad_entregada;

	@Override
	public void init() {
		listarCombos();
	}

	public void listarCombos() {

	}

	public void listarElementoListbox(Listbox listbox, boolean select) {
		listbox.getChildren().clear();
		Listitem listitem;
		if (select) {
			listitem = new Listitem();
			listitem.setValue("");
			listitem.setLabel("-- seleccione --");
			listbox.appendChild(listitem);
		}

		String tipo = listbox.getName();
		List<Elemento> elementos = this.getServiceLocator()
				.getElementoService().listar(tipo);

		for (Elemento elemento : elementos) {
			listitem = new Listitem();
			listitem.setValue(elemento.getCodigo());
			listitem.setLabel(elemento.getDescripcion());
			listbox.appendChild(listitem);
		}
		if (listbox.getItemCount() > 0) {
			listbox.setSelectedIndex(0);
		}
	}

	// Convertimos todos las valores de textbox a mayusculas
	public void setUpperCase() {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				Textbox textbox = (Textbox) abstractComponent;
				if (!(textbox instanceof Combobox)) {
					((Textbox) abstractComponent)
							.setText(((Textbox) abstractComponent).getText()
									.trim().toUpperCase());
				}
			}
		}
	}

	// Limpiamos los campos del formulario //
	public void limpiarDatos() throws Exception {
		Collection<Component> collection = groupboxEditar.getFellows();
		for (Component abstractComponent : collection) {
			if (abstractComponent instanceof Textbox) {
				if (!((Textbox) abstractComponent).getId().equals("tbxValue")) {
					((Textbox) abstractComponent).setValue("");
				}
			}
			if (abstractComponent instanceof Listbox) {
				if (!((Listbox) abstractComponent).getId().equals(
						"lbxParameter")) {
					if (((Listbox) abstractComponent).getItemCount() > 0) {
						((Listbox) abstractComponent).setSelectedIndex(0);
					}
				}
			}
			if (abstractComponent instanceof Doublebox) {
				((Doublebox) abstractComponent).setText("0.00");
			}
			if (abstractComponent instanceof Intbox) {
				((Intbox) abstractComponent).setText("");
			}
			if (abstractComponent instanceof Datebox) {
				((Datebox) abstractComponent).setValue(new Date());
			}
			if (abstractComponent instanceof Checkbox) {
				((Checkbox) abstractComponent).setChecked(false);
			}
			if (abstractComponent instanceof Radiogroup) {
				((Radio) ((Radiogroup) abstractComponent).getFirstChild())
						.setChecked(true);
			}
		}
	}

	// Metodo para validar campos del formulario //
	public boolean validarForm() throws Exception {

		tbxNro_factura
				.setStyle("text-transform:uppercase;background-color:white");
		tbxConsecutivo
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_bodega
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_medicamento
				.setStyle("text-transform:uppercase;background-color:white");
		tbxCodigo_lote
				.setStyle("text-transform:uppercase;background-color:white");
		ibxUnidades.setStyle("background-color:white");
		dbxValor_unitario.setStyle("background-color:white");
		dbxValor_total.setStyle("background-color:white");
		tbxCancelo_copago
				.setStyle("text-transform:uppercase;background-color:white");
		dtbxDelete_date.setStyle("background-color:white");
		tbxDelete_user
				.setStyle("text-transform:uppercase;background-color:white");
		dbxValor_real.setStyle("background-color:white");
		dbxDescuento.setStyle("background-color:white");
		dbxIncremento.setStyle("background-color:white");
		ibxCantidad_entregada.setStyle("background-color:white");

		boolean valida = true;

		if (tbxNro_factura.getText().trim().equals("")) {
			tbxNro_factura
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxConsecutivo.getText().trim().equals("")) {
			tbxConsecutivo
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxCodigo_bodega.getText().trim().equals("")) {
			tbxCodigo_bodega
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxCodigo_medicamento.getText().trim().equals("")) {
			tbxCodigo_medicamento
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (tbxCodigo_lote.getText().trim().equals("")) {
			tbxCodigo_lote
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (ibxUnidades.getText().trim().equals("")) {
			ibxUnidades.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (dbxValor_unitario.getText().trim().equals("")) {
			dbxValor_unitario.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (dbxValor_total.getText().trim().equals("")) {
			dbxValor_total.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (tbxCancelo_copago.getText().trim().equals("")) {
			tbxCancelo_copago
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (dtbxDelete_date.getText().trim().equals("")) {
			dtbxDelete_date.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (tbxDelete_user.getText().trim().equals("")) {
			tbxDelete_user
					.setStyle("text-transform:uppercase;background-color:#F6BBBE");
			valida = false;
		}
		if (dbxValor_real.getText().trim().equals("")) {
			dbxValor_real.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (dbxDescuento.getText().trim().equals("")) {
			dbxDescuento.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (dbxIncremento.getText().trim().equals("")) {
			dbxIncremento.setStyle("background-color:#F6BBBE");
			valida = false;
		}
		if (ibxCantidad_entregada.getText().trim().equals("")) {
			ibxCantidad_entregada.setStyle("background-color:#F6BBBE");
			valida = false;
		}

		if (!valida) {
			Messagebox.show("Los campos marcados con (*) son obligatorios",
					usuarios.getNombres() + " recuerde que...", Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		return valida;
	}

	public Row crearFilas(Object objeto, Component componente) throws Exception {
		Row fila = new Row();

		final Datos_medicamentos datos_medicamentos = (Datos_medicamentos) objeto;

		Hbox hbox = new Hbox();
		Space space = new Space();

		fila.setStyle("text-align: justify;nowrap:nowrap");
		fila.appendChild(new Label(datos_medicamentos.getNro_factura() + ""));
		fila.appendChild(new Label(datos_medicamentos.getConsecutivo() + ""));
		fila.appendChild(new Label(datos_medicamentos.getCodigo_bodega() + ""));
		fila.appendChild(new Label(datos_medicamentos.getCodigo_medicamento()
				+ ""));
		fila.appendChild(new Label(datos_medicamentos.getCodigo_lote() + ""));
		fila.appendChild(new Label(datos_medicamentos.getUnidades() + ""));
		fila.appendChild(new Label(datos_medicamentos.getValor_unitario() + ""));
		fila.appendChild(new Label(datos_medicamentos.getValor_total() + ""));
		fila.appendChild(new Label(datos_medicamentos.getCancelo_copago() + ""));
		fila.appendChild(new Label(datos_medicamentos.getDelete_date() + ""));
		fila.appendChild(new Label(datos_medicamentos.getDelete_user() + ""));
		fila.appendChild(new Label(datos_medicamentos.getValor_real() + ""));
		fila.appendChild(new Label(datos_medicamentos.getDescuento() + ""));
		fila.appendChild(new Label(datos_medicamentos.getIncremento() + ""));
		fila.appendChild(new Label(datos_medicamentos.getCantidad_entregada()
				+ ""));

		hbox.appendChild(space);

		Image img = new Image();
		img.setSrc("/images/editar.gif");
		img.setTooltiptext("Editar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				mostrarDatos(datos_medicamentos);
			}
		});
		hbox.appendChild(img);

		img = new Image();
		img.setSrc("/images/borrar.gif");
		img.setTooltiptext("Eliminar");
		img.setStyle("cursor: pointer");
		img.addEventListener("onClick", new EventListener() {
			@Override
			public void onEvent(Event arg0) throws Exception {
				Messagebox.show(
						"Esta seguro que desea eliminar este registro? ",
						"Eliminar Registro", Messagebox.YES + Messagebox.NO,
						Messagebox.QUESTION,
						new org.zkoss.zk.ui.event.EventListener() {
							public void onEvent(Event event) throws Exception {
								if ("onYes".equals(event.getName())) {
									// do the thing
									eliminarDatos(datos_medicamentos);
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

	// Metodo para guardar la informacion //
	public void guardarDatos() throws Exception {
		try {
			setUpperCase();
			if (validarForm()) {
				// Cargamos los componentes //

				Datos_medicamentos datos_medicamentos = new Datos_medicamentos();
				datos_medicamentos.setCodigo_empresa(empresa
						.getCodigo_empresa());
				datos_medicamentos.setCodigo_sucursal(sucursal
						.getCodigo_sucursal());
				datos_medicamentos.setNro_factura(tbxNro_factura.getValue());
				datos_medicamentos.setConsecutivo(tbxConsecutivo.getValue());
				datos_medicamentos
						.setCodigo_bodega(tbxCodigo_bodega.getValue());
				datos_medicamentos.setCodigo_medicamento(tbxCodigo_medicamento
						.getValue());
				datos_medicamentos.setCodigo_lote(tbxCodigo_lote.getValue());
				datos_medicamentos
						.setUnidades((ibxUnidades.getValue() != null ? ibxUnidades
								.getValue() : 0));
				datos_medicamentos.setValor_unitario((dbxValor_unitario
						.getValue() != null ? dbxValor_unitario.getValue()
						: 0.00));
				datos_medicamentos
						.setValor_total((dbxValor_total.getValue() != null ? dbxValor_total
								.getValue() : 0.00));
				datos_medicamentos.setCancelo_copago(tbxCancelo_copago
						.getValue());
				datos_medicamentos.setCreacion_date(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				datos_medicamentos.setUltimo_update(new Timestamp(
						new GregorianCalendar().getTimeInMillis()));
				datos_medicamentos.setDelete_date(new Timestamp(dtbxDelete_date
						.getValue().getTime()));
				datos_medicamentos.setCreacion_user(usuarios.getCodigo()
						.toString());
				datos_medicamentos.setUltimo_user(usuarios.getCodigo()
						.toString());
				datos_medicamentos.setDelete_user(tbxDelete_user.getValue());
				datos_medicamentos
						.setValor_real((dbxValor_real.getValue() != null ? dbxValor_real
								.getValue() : 0.00));
				datos_medicamentos
						.setDescuento((dbxDescuento.getValue() != null ? dbxDescuento
								.getValue() : 0.00));
				datos_medicamentos
						.setIncremento((dbxIncremento.getValue() != null ? dbxIncremento
								.getValue() : 0.00));
				datos_medicamentos.setCantidad_entregada((ibxCantidad_entregada
						.getValue() != null ? ibxCantidad_entregada.getValue()
						: 0));

				if (tbxAccion.getText().equalsIgnoreCase("registrar")) {
					getServiceLocator().getDatos_medicamentosService().crear(
							datos_medicamentos);

				} else {
					int result = getServiceLocator()
							.getDatos_medicamentosService().actualizar(
									datos_medicamentos);
					if (result == 0) {
						throw new Exception(
								"Lo sentimos pero los datos a modificar no se encuentran en base de datos");
					}
				}

				Messagebox
						.show("Los datos se guardaron satisfactoriamente",
								"Informacion ..", Messagebox.OK,
								Messagebox.INFORMATION);

			}

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}

	}

	// Metodo para colocar los datos del objeto que se consulta en la vista //
	public void mostrarDatos(Object obj) throws Exception {
		Datos_medicamentos datos_medicamentos = (Datos_medicamentos) obj;
		try {
			tbxNro_factura.setValue(datos_medicamentos.getNro_factura());
			tbxConsecutivo.setValue(datos_medicamentos.getConsecutivo());
			tbxCodigo_bodega.setValue(datos_medicamentos.getCodigo_bodega());
			tbxCodigo_medicamento.setValue(datos_medicamentos
					.getCodigo_medicamento());
			tbxCodigo_lote.setValue(datos_medicamentos.getCodigo_lote());
			ibxUnidades.setValue(datos_medicamentos.getUnidades());
			dbxValor_unitario.setValue(datos_medicamentos.getValor_unitario());
			dbxValor_total.setValue(datos_medicamentos.getValor_total());
			tbxCancelo_copago.setValue(datos_medicamentos.getCancelo_copago());
			dtbxDelete_date.setValue(datos_medicamentos.getDelete_date());
			tbxDelete_user.setValue(datos_medicamentos.getDelete_user());
			dbxValor_real.setValue(datos_medicamentos.getValor_real());
			dbxDescuento.setValue(datos_medicamentos.getDescuento());
			dbxIncremento.setValue(datos_medicamentos.getIncremento());
			ibxCantidad_entregada.setValue(datos_medicamentos
					.getCantidad_entregada());

			// Mostramos la vista //
			tbxAccion.setText("modificar");

		} catch (Exception e) {
			MensajesUtil.mensajeError(e, "", this);
		}
	}

	public void eliminarDatos(Object obj) throws Exception {
		Datos_medicamentos datos_medicamentos = (Datos_medicamentos) obj;
		try {
			int result = getServiceLocator().getDatos_medicamentosService()
					.eliminar(datos_medicamentos);
			if (result == 0) {
				throw new Exception(
						"Lo sentimos pero los datos a eliminar no se encuentran en base de datos");
			}

			Messagebox.show("Informacion se eliminó satisfactoriamente !!",
					"Information", Messagebox.OK, Messagebox.INFORMATION);
		} catch (HealthmanagerException e) {
			MensajesUtil
					.mensajeError(
							e,
							"Este objeto no se puede eliminar por "
									+ "que esta relacionado con otra tabla en la base de datos",
							this);
		} catch (RuntimeException r) {
			MensajesUtil.mensajeError(r, "", this);
		}
	}
}