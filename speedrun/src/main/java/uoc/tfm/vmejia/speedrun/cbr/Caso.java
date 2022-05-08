package uoc.tfm.vmejia.speedrun.cbr;

import uoc.tfm.vmejia.speedrun.var.Almacen;
import uoc.tfm.vmejia.speedrun.var.Cofre;

public class Caso {
    public String a_objetivo;

    public Almacen alm1;
    public Almacen alm2;
    public Cofre cofre_galleta;
    public Cofre cofre_pan;
    public Cofre cofre_pastel;

    public Caso(String a_objetivo) {
        this.a_objetivo = a_objetivo;
        alm1 = new Almacen();
    }
}
