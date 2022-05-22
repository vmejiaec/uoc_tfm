package uoc.tfm.vmejia.speedrun.var;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.util.Vector;
import uoc.tfm.vmejia.speedrun.instance.Arena;
import uoc.tfm.vmejia.speedrun.manager.EscenaManager;
import uoc.tfm.vmejia.speedrun.util.UtilAlmacen;
import uoc.tfm.vmejia.speedrun.util.UtilLocation;

import java.util.ArrayList;
import java.util.List;

public class Escena {
    // Elementos de la escena
    public Base baseNPC; // base del NPC agente
    public Base basePlayer; // base del player
    public Almacen almacenIzq;
    public Almacen almacenDer;
    public Agente agente;

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

}
