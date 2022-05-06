package uoc.tfm.vmejia.speedrun.ctrl;

import uoc.tfm.vmejia.speedrun.var.*;

public class CtrlAgente {
    public static void Toma(Agente agente, Almacen almacen){
        // Quita del almacen
        System.out.print("Operación: TOMA");
        System.out.print("Retiro: " + almacen.nombre + " " + agente.material + " " + agente.cantidad);
        if (CtrlCofre.Retiro(almacen.cofre, agente.material, agente.cantidad)){
            // Pone en el cofre del agente
            System.out.println(" - Deposito: " );
            CtrlCofre.Deposito(agente.bolsa,agente.material,agente.cantidad);
        }
    }

    public static void Deja(Agente agente, Cofre cofre){
        // Quita del cofre del agente
        System.out.print("Operación: DEJA");
        System.out.print("Retiro de la bolsa: " + agente.material + " " + agente.cantidad);
        if(CtrlCofre.Retiro(agente.bolsa, agente.material, agente.cantidad)){
            // Deja en el cofre
            System.out.print(" - Deposito: ");
            CtrlCofre.Deposito(cofre, agente.material, agente.cantidad);
        }
    }

    public static void Configurar(Agente agente, String casoid){
        switch (casoid){
            case "sc_caso1" :
                //Ir al Almacen 1 y traer Material 1-1 y poner en el cofre 1
                agente.destino = Agente.destinotipo.ALALMACEN1;
                agente.material = Material.tipo.CACAO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.PAN;
                break;
            case "sc_caso2":
                //Ir al Almacen 1 y traer Material 1-3 y poner en el cofre 1
                agente.destino = Agente.destinotipo.ALALMACEN1;
                agente.material = Material.tipo.LECHE;
                agente.cantidad = 5;
                agente.producto = Material.tipo.PAN;
                break;
            case "sc_caso3":
                //Ir al Almacen 1 y traer Material 1-1 y poner en el cofre 1
                agente.destino = Agente.destinotipo.ALALMACEN1;
                agente.material = Material.tipo.CACAO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.PAN;
                break;
            case "sc_caso4":
                //Ir al Almacen 2 y traer Material 2-3 y poner en el cofre 1
                agente.destino = Agente.destinotipo.ALALMACEN2;
                agente.material = Material.tipo.LECHE;
                agente.cantidad = 5;
                agente.producto = Material.tipo.PAN;
                break;
            case "sc_caso5":
                //Ir al Almacen 1 y traer Material 1-2 y poner en el cofre 2
                agente.destino = Agente.destinotipo.ALALMACEN1;
                agente.material = Material.tipo.HUEVO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.GALLETA;
                break;
            case "sc_caso6":
                //Ir al Almacen 2 y traer Material 2-4 y poner en el cofre 2
                agente.destino = Agente.destinotipo.ALALMACEN2;
                agente.material = Material.tipo.TRIGO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.GALLETA;
                break;
            case "sc_caso7":
                //Ir al Almacen 2 y traer Material 2-4 y poner en el cofre 2
                agente.destino = Agente.destinotipo.ALALMACEN2;
                agente.material = Material.tipo.TRIGO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.GALLETA;
                break;
            case "sc_caso8":
                //Ir al Almacen 2 y traer Material 2-1 y poner en el cofre 2
                agente.destino = Agente.destinotipo.ALALMACEN2;
                agente.material = Material.tipo.CACAO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.GALLETA;
                break;
            case "sc_caso9":
                //Ir al Almacen 1 y traer Material 1-2 y poner en el cofre 3
                agente.destino = Agente.destinotipo.ALALMACEN1;
                agente.material = Material.tipo.HUEVO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.PASTEL;
                break;
            case "sc_caso10":
                //Ir al Almacen 2 y traer Material 2-4 y poner en el cofre 3
                agente.destino = Agente.destinotipo.ALALMACEN2;
                agente.material = Material.tipo.TRIGO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.PASTEL;
                break;
            case "sc_caso11":
                //Ir al Almacen 2 y traer Material 2-3 y poner en el cofre 3
                agente.destino = Agente.destinotipo.ALALMACEN2;
                agente.material = Material.tipo.LECHE;
                agente.cantidad = 5;
                agente.producto = Material.tipo.PASTEL;
                break;
            case "sc_caso12":
                //Ir al Almacen 2 y traer Material 2-2 y poner en el cofre 3
                agente.destino = Agente.destinotipo.ALALMACEN2;
                agente.material = Material.tipo.HUEVO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.PASTEL;
                break;
            case "sc_caso13":
                //Ir al Almacen 1 y traer Material 1-2 y poner en el cofre 3
                agente.destino = Agente.destinotipo.ALALMACEN1;
                agente.material = Material.tipo.HUEVO;
                agente.cantidad = 5;
                agente.producto = Material.tipo.PASTEL;
                break;
        }
    }
}
