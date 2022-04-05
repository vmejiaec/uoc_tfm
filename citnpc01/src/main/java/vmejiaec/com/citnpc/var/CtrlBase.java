package vmejiaec.com.citnpc.var;

public class CtrlBase {
    // Procesa el inventario del cofre para obtener los productos
    public static void procesa(Base base){
        CtrlCofre.Procesar(base.cofrepan);
        CtrlCofre.Procesar(base.cofregalleta);
        CtrlCofre.Procesar(base.cofrepastel);
    }
}
