package healthmanager.exception;

public class ValidacionRunTimeException extends HealthmanagerException {

	public ValidacionRunTimeException(String mensaje) {
		super(mensaje);
	}
	
	public ValidacionRunTimeException(String mensaje,Throwable causa) {
		super(mensaje,causa);
	}
}
