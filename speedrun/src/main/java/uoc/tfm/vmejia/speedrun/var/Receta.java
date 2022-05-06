package uoc.tfm.vmejia.speedrun.var;

public class Receta {
    public String nombre;
    public int ingr_cacao;
    public int ingr_huevo;
    public int ingr_leche;
    public int ingr_trigo;

    public Receta(){ }

    public Receta(String nombre,int inv_cacao,int inv_huevo, int inv_leche, int inv_trigo){
        this.nombre = nombre;
        this.ingr_cacao = inv_cacao;
        this.ingr_huevo = inv_huevo;
        this.ingr_leche = inv_leche;
        this.ingr_trigo = inv_trigo;
    }
}
