package vmejiaec.com.citnpc.var;

public class Almacen {
    public String nombre;
    public Cofre cofre;

    public Almacen() {}

    public Almacen (String nombre) {
        this.nombre = nombre;
        this.cofre = new Cofre();
    }

    public Almacen (String nombre,int inv_cacao,int inv_huevo, int inv_leche, int inv_trigo) {
        this.nombre = nombre;
        this.cofre = new Cofre(0,inv_cacao, inv_huevo, inv_leche, inv_trigo);
    }
}

