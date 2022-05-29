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

    public static int inv_Cacao(Base base){
        return base.cofrepastel.inv_cacao + base.cofrepan.inv_cacao+base.cofregalleta.inv_cacao;
    }

    public static int inv_Huevo(Base base){
        return base.cofrepastel.inv_huevo + base.cofrepan.inv_huevo+base.cofregalleta.inv_huevo;
    }

    public static int inv_Leche(Base base){
        return base.cofrepastel.inv_leche + base.cofrepan.inv_leche+base.cofregalleta.inv_leche;
    }

    public static int inv_Trigo(Base base){
        return base.cofrepastel.inv_trigo + base.cofrepan.inv_trigo+base.cofregalleta.inv_trigo;
    }

    public static int inv_Productos(Base base){
        return base.cofrepastel.inv + base.cofrepan.inv + base.cofregalleta.inv;
    }
}
