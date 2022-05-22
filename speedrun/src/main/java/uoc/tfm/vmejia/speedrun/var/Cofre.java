package uoc.tfm.vmejia.speedrun.var;

import org.bukkit.Location;

public class Cofre {
    public String nombre;
    public int inv;
    public int inv_cacao;
    public int inv_huevo;
    public int inv_leche;
    public int inv_trigo;
    public Location pos;

    public Receta receta;

    public Cofre() {
        this.inv = 0;
        this.inv_cacao = 0;
        this.inv_huevo = 0;
        this.inv_leche = 0;
        this.inv_trigo = 0;
        this.receta = new Receta();
    }

    public Cofre(
            String nombre,
            int inv,
            int inv_cacao,
            int inv_huevo,
            int inv_leche,
            int inv_trigo) {
        this.nombre = nombre;
        this.inv = inv;
        this.inv_cacao = inv_cacao;
        this.inv_huevo = inv_huevo;
        this.inv_leche = inv_leche;
        this.inv_trigo = inv_trigo;
    }
}

