package com.framework.util.secuencia_hora;

import java.util.Calendar;
import java.util.Date;

public class ArquitectoVisualizarHoraUtils {

//	private static Logger log = Logger
//			.getLogger(ArquitectoVisualizarHoraUtils.class);

	public static String visualizar(Hora horaIncio, int secuencia,
			Hora horaFinal) throws Exception{
		return visualizar(horaIncio, secuencia, horaFinal, null);
	}

	public static String visualizar(Hora horaIncio, int secuencia,
			Hora horaFinal, EventoSecuenciaHoras secuenciaHoras) throws Exception{
		StringBuilder salida = new StringBuilder("<html><table width='100%'>");
		if (horaIncio.compareTo(horaFinal) < 0) {
			do {
				String hora_en_incremento = horaIncio.incrementar(secuencia);
				if (compararEliminarDifereciaSegundos(horaIncio, horaFinal)) {
					salida.append("<tr><th> " + hora_en_incremento
							+ " </th></tr>");
//					//log.info("Hora: " + hora_en_incremento); 
					if (secuenciaHoras != null)
						secuenciaHoras.rangoHora(horaIncio.getCalendarAcarreo()
								.getTime(), horaIncio.getCalendar().getTime());
				}
//				//log.info("Hora incio: " + horaIncio.getCalendar().getTime());  
//				//log.info("Hora final: " + horaFinal.getCalendar().getTime());
//				//log.info("---------------------");
			} while (horaFinal.compareTo(horaIncio) > 0);
		} else {
			//log.info("Fechas incorrectas: " + horaIncio.toString() + " - "
					//+ horaFinal.toString());
		}
		salida.append("</table></html>");
		return salida.toString();
	}

	private static boolean compararEliminarDifereciaSegundos(Hora horaInicio,
			Hora horafin) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(horafin.getCalendar().getTime());
		calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) + 1);
		return horaInicio.compareTo(calendar) < 0;
	}

	public interface EventoSecuenciaHoras {
		public void rangoHora(Date horaInicial, Date horaFinal) throws Exception;
	}
}
