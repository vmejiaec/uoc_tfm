package uoc.tfm.vmejia.speedrun.ctrl;

import uoc.tfm.vmejia.speedrun.var.Base;

import java.util.UUID;

public class CtrlBase {
    // Procesa el inventario del cofre para obtener los productos
    public static boolean procesa(Base base){
        boolean res;
        res =  CtrlCofre.Procesar(base.cofrepan);
        res = res || CtrlCofre.Procesar( base.cofregalleta);
        res = res || CtrlCofre.Procesar( base.cofrepastel);
        return res;
    }
}
