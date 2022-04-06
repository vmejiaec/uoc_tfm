package vmejiaec.com.citnpc.var;

public class CtrlAgente {
    public static void Toma(Agente agente, Almacen almacen){
        // Quita del almacen
        if (CtrlCofre.Retiro(almacen.cofre, agente.material, agente.cantidad)){
            // Pone en el cofre del agente
            CtrlCofre.Deposito(agente.bolsa,agente.material,agente.cantidad);
        }
    }

    public static void Deja(Agente agente, Cofre cofre){
        // Quita del cofre del agente
        if(CtrlCofre.Retiro(agente.bolsa, agente.material, agente.cantidad)){
            // Deja en el cofre
            CtrlCofre.Deposito(cofre, agente.material, agente.cantidad);
        }
    }
}
