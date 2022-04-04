package vmejiaec.com.citnpc.var;

public class Almacen {
    public int inv_cacao;
    public int inv_huevo;
    public int inv_leche;
    public int inv_trigo;

    public Almacen() {}

    public Almacen (
            int inv_cacao,
            int inv_huevo,
            int inv_leche,
            int inv_trigo) {
        this.inv_cacao = inv_cacao;
        this.inv_huevo = inv_huevo;
        this.inv_leche = inv_leche;
        this.inv_trigo = inv_trigo;
    }
}

