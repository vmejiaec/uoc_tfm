package vmejiaec.com.citnpc.var;

public class Receta {
    public String nombre;
    public Cofre ingredientes;

    public Receta(){
        this.ingredientes = new Cofre();
    }

    public Receta(String nombre,int inv_cacao,int inv_huevo, int inv_leche, int inv_trigo){
        this.nombre = nombre;
        this.ingredientes = new Cofre(0,inv_cacao, inv_huevo, inv_leche, inv_trigo);
    }
}
