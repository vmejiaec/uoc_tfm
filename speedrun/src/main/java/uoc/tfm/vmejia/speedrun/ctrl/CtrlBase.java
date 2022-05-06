package uoc.tfm.vmejia.speedrun.ctrl;

import uoc.tfm.vmejia.speedrun.var.Base;

public class CtrlBase {
    // Procesa el inventario del cofre para obtener los productos
    public static void procesa(Base base){
        CtrlCofre.Procesar(base.cofrepan);
        CtrlCofre.Procesar(base.cofregalleta);
        CtrlCofre.Procesar(base.cofrepastel);
    }
}
