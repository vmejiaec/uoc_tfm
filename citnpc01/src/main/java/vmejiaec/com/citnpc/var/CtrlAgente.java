package vmejiaec.com.citnpc.var;

public class CtrlAgente {
    public static void Toma(Agente agente, Almacen almacen, Material.tipo material, int cantidad){
        // Quita del almacen
        if (CtrlCofre.Retiro(almacen.cofre, material, cantidad)){
            // Pone en el cofre del agente
            CtrlCofre.Deposito(agente.cofre,material, cantidad);
        }
    }

    public static void Deja(Agente agente, Cofre cofre, Material.tipo material, int cantidad){
        // Quita del cofre del agente
        if(CtrlCofre.Retiro(agente.cofre, material, cantidad)){
            // Deja en el cofre
            CtrlCofre.Deposito(cofre, material, cantidad);
        }
    }
}
