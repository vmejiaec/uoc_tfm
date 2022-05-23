package uoc.tfm.vmejia.speedrun.var;

public class Agente {
    public String nombre;
    public Cofre bolsa;
    // destino
    public  enum destinotipo {ALALMACEN1, ALALMACEN2}
    public destinotipo destino;
    // misión
    public MaterialModelo.tipo producto;
    public MaterialModelo.tipo material;
    public int cantidad;


    public Agente(String nombre){
        this.nombre = nombre;
        this.bolsa = new Cofre();
    }
}
