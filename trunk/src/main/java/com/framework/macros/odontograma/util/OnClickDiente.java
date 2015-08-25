package com.framework.macros.odontograma.util;

import com.framework.macros.odontograma.DienteMacro;
import com.framework.macros.odontograma.DienteMacro.SECTOR;
import com.framework.macros.odontograma.util.ISectorDiente.TypeSector;

public interface OnClickDiente {
   public void onClick(DienteMacro dienteMacro);
   public void onChangeStateDiente(TypeSector antes, TypeSector ahora, boolean isAdulto, DienteMacro dienteMacro, SECTOR sector);
   public void onDespuesDelClick(DienteMacro dienteMacro);
}
