package vmejiaec.com.citnpc.var;

public class Agente {
    public String nombre;
    public Cofre cofre;
    public Agente(String nombre){
        this.nombre = nombre;
        this.cofre = new Cofre();
    }
}
