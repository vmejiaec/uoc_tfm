package uoc.tfm.vmejia.speedrun.var;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Escena {
    // Elementos de la escena
    public Base baseNPC; // base del NPC agente
    public Base basePlayer; // base del player
    public Almacen almacenIzq;
    public Almacen almacenDer;
    public Agente agente;
    public Agente jugador;

    // Posiciones y caminos de la escena
    // La position de las bases
    public Vector vecBaseNPC = new Vector();
    public Vector vecBasePlayer = new Vector();
    // La posición de los almacenes
    public Vector vecAlmacen1 = new Vector();
    public Vector vecAlmacen2 = new Vector();

    // los caminos de la base a los almacenes
    public List<Vector> caminoAlm1 = new ArrayList<Vector>(){{add(vecBaseNPC);add(vecAlmacen1);}};
    public List<Vector> caminoAlm2 = new ArrayList<Vector>(){{add(vecBaseNPC);add(vecAlmacen2);}};
    public List<Vector> camino = new ArrayList<>();

    public Cofre getCofre(Location location) {
        if (almacenIzq.cofre.pos.equals(location)) return almacenIzq.cofre;
        if (almacenDer.cofre.pos.equals(location)) return almacenDer.cofre;
        if (basePlayer.cofrepan.pos.equals(location)) return basePlayer.cofrepan;
        if (basePlayer.cofregalleta.pos.equals(location)) return basePlayer.cofregalleta;
        if (basePlayer.cofrepastel.pos.equals(location)) return basePlayer.cofrepastel;
        if (baseNPC.cofrepan.pos.equals(location)) return baseNPC.cofrepan;
        if (baseNPC.cofregalleta.pos.equals(location)) return baseNPC.cofregalleta;
        if (baseNPC.cofrepastel.pos.equals(location)) return baseNPC.cofrepastel;
        return null;
    }
}
