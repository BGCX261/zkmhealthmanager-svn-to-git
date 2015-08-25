package com.framework.constraints;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.CustomConstraint;

public class RestriccionComponente implements Constraint, CustomConstraint {

	private ConstraintsCreados constraint;

	public RestriccionComponente(ConstraintsCreados constraint) {
		this.constraint = constraint;
	}

	@Override
	public void validate(Component component, Object valor)
			throws WrongValueException {
		if (constraint == ConstraintsCreados.CAMPO_VACIO) {
			if (valor != null) {
				if (valor.toString().isEmpty()) {
					throw new WrongValueException(component,
							"Valor del componente es obligatorio. ID_COMPONENTE ===> "
									+ component.getId());
				}
			} else {
				throw new WrongValueException(component,
						"Valor del componente es obligatorio. ID_COMPONENTE ===> "
								+ component.getId());
			}
		}
	}

	@Override
	public void showCustomError(Component comp, WrongValueException ex) {
	}

	public enum ConstraintsCreados {
		CAMPO_VACIO
	}

}
