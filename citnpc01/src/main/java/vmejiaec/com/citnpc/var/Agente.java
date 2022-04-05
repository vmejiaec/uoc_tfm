package vmejiaec.com.citnpc.var;

public class Agente {
    public String nombre;
    public Cofre cofre;
    // misión
    public Material.tipo material;
    public int cantidad;

    public Agente(String nombre){
        this.nombre = nombre;
        this.cofre = new Cofre();
    }
}
