package vmejiaec.com.citnpc.var;

public class Base {
    public String objetivo;
    public Cofre cofrepan ;
    public Cofre cofregalleta;
    public Cofre cofrepastel ;

    public Base(String objetivo){
        this.objetivo = objetivo;
    }
    public Base(String objetivo, Cofre cofrepan, Cofre cofregalleta, Cofre cofrepastel){
        this.objetivo = objetivo;
        this.cofrepan = cofrepan;
        this.cofregalleta = cofregalleta;
        this.cofrepastel = cofrepastel;
    }
}
