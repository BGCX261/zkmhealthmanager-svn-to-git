/**
 * 
 */
package healthmanager.exception;

/**
 * @author ferney
 *
 */
public class HealthmanagerException  extends RuntimeException{
	
	public HealthmanagerException(String mensaje) {
		// TODO Auto-generated constructor stub
		super(mensaje);
	}
	
	public HealthmanagerException(String mensaje,Throwable causa) {
		// TODO Auto-generated constructor stub
		super(mensaje,causa);
	}

}
