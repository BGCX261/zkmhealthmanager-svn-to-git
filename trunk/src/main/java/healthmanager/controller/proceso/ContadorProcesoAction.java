package healthmanager.controller.proceso;

import healthmanager.controller.ZKWindow;

import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Label;
import org.zkoss.zul.Progressmeter;
import org.zkoss.zul.Timer;

public class ContadorProcesoAction extends ZKWindow {
	
	@View private Timer time;
	@View private Caption ctnTitulo;
	@View private Progressmeter pgmBarraProceso;
	@View private Label lbInformacionProceso;
	
//	private int bandera = 1;
	
	private IOnEjecutar onEjecutar;
	private Comunicador comunicador;
//	private List list;

	@Override
	public void init() throws Exception {
		Events.echoEvent("onAddNameEvent", time, null);
		getParent().getDesktop().enableServerPush(true);
//		time.addEventListener(Events.ON_TIMER, new EventListener<Event>() {
//			@Override
//			public void onEvent(Event arg0) throws Exception {
//				getOnEjecutar().ejecutar(getComunicador(), bandera, list);
//				time.stop();
//				Clients.clearBusy(getParent());
//				setVisible(false); 
//			} 
//		});
	}
	
	private Comunicador getComunicador(){
		if(comunicador == null){
			comunicador = new Comunicador();
		}
		return comunicador;
	}
	
	public void _ejecutar(final int bandera, final List list){
		if(getOnEjecutar() != null){
//			this.bandera = bandera;
//			this.list = list;
			setVisible(true); 
//			time.start();
			new Thread(new Runnable() {
				@Override
				public void run() {
					getOnEjecutar().ejecutar(getComunicador(), bandera, list);
				}
			}).start();
		}
	}
	
	public IOnEjecutar getOnEjecutar() {
		return onEjecutar;
	}

	public void setOnEjecutar(IOnEjecutar onEjecutar) {
		this.onEjecutar = onEjecutar;
	}

	public interface IOnEjecutar{
		void ejecutar(Comunicador comunicador, int bandera, List list);
	}
	 
	public class Comunicador {
		int total;

		public void setValor(int valor) {
			try {
				if (valor <= 100 && valor >= 0){
					Executions.activate(getParent().getDesktop());
					pgmBarraProceso.setValue(valor);
					Executions.deactivate(getParent().getDesktop());
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void setTotal(int total) {
			this.total = total;
		}

		public void setDescripcionValor(String descripcion) {
			Clients.showBusy(getParent(), descripcion); 
			lbInformacionProceso.setValue(descripcion);
		}

		public void setValorDescripcionDefault(int valor) {
			setValor(valor);
			setDescripcionValor(((valor * total) / 100) + "%");
		}
	}

}
