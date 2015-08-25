package healthmanager.controller;

import java.util.List;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listitem;

public class SeleccionadorAction extends GeneralComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -635612481484370952L;

	@View
	private Listbox lbxSeleccionar;

	@SuppressWarnings("rawtypes")
	private OnSeleccionador onSeleccionar;

	@Override
	public void init() throws Exception {
	}

	public interface OnSeleccionador<T> {
		String getTitulo();

		List<T> getListado();

		void onSeleccionar(T t);

		Listhead getListhead();

		void agregar(Listitem listitem);
	}

	@SuppressWarnings("rawtypes")
	public OnSeleccionador getOnSeleccionar() {
		return onSeleccionar;
	}

	@SuppressWarnings("rawtypes")
	public void setOnSeleccionar(OnSeleccionador onSeleccionar) {
		this.onSeleccionar = onSeleccionar;
		inicializarListado();
	}

	private void inicializarListado() {
		if (onSeleccionar != null) {
			String titulo = onSeleccionar.getTitulo();
			if (titulo != null && !titulo.trim().isEmpty()) {
				setTitle(titulo);
			}

			Listhead listhead = onSeleccionar.getListhead();
			if (listhead != null) {
				lbxSeleccionar.appendChild(listhead);
			}

			List list = onSeleccionar.getListado();
			for (Object object : list) {
				Listitem listitem = new Listitem();
				listitem.setValue(object);
				onSeleccionar.agregar(listitem);
				lbxSeleccionar.appendChild(listitem);
			}

			lbxSeleccionar.addEventListener(Events.ON_SELECT,
					new EventListener<Event>() {
						@SuppressWarnings("unchecked")
						@Override
						public void onEvent(Event arg0) throws Exception {
							onSeleccionar.onSeleccionar(lbxSeleccionar
									.getSelectedItem().getValue());
							detach();
						}
					});
		}
	}

}
