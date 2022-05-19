package uoc.tfm.vmejia.speedrun.var;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;
import uoc.tfm.vmejia.speedrun.instance.Arena;
import uoc.tfm.vmejia.speedrun.manager.EscenaManager;
import uoc.tfm.vmejia.speedrun.util.UtilAlmacen;

import java.util.ArrayList;
import java.util.List;

public class Escena {
    // Elementos de la escena
    public Base baseAgente; // base del NPC agente
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

    public void iniciaLocaciones(Arena arena){
        System.out.println("Inicializa las Posiciones de la arena: "+arena.getId());
        vecBaseNPC = arena.getNPCSpawn().toVector();
        vecBasePlayer = arena.getPlayerSpawn().toVector();
        vecAlmacen1 = arena.getAlmacen1().toVector();
        vecAlmacen2 = arena.getAlmacen2().toVector();

        caminoAlm1 = new ArrayList<Vector>(){{add(vecBaseNPC);add(vecAlmacen1);}};
        caminoAlm2 = new ArrayList<Vector>(){{add(vecBaseNPC);add(vecAlmacen2);}};

        camino = caminoAlm2;

        // Actualiza las posiciones de los cofres
        World world = arena.getWorld();
        // Decide la orientación de la base respecto a los cofres
        Vector posrelBaseCofrePan = new Vector(1,0,-2);
        Vector posrelBaseCofreGalleta = new Vector(1,0,0);
        Vector posrelBaseCofrePastel = new Vector(1,0,2);
        Vector posrelAlmacen = new Vector(-1,1,0);
        boolean signo = vecAlmacen1.getX() > vecBaseNPC.getX()  ;
        if (signo){
            posrelBaseCofrePan = new Vector(-1,0,2);
            posrelBaseCofreGalleta = new Vector(-1,0,0);
            posrelBaseCofrePastel = new Vector(-1,0,-2);
            posrelAlmacen = new Vector(1,1,0);
        }
        // Calcula dónde deben estar los cofres
        Vector v1 = new Vector(1,1,1);
        Vector v2 = new Vector(1,-1,2);
        Vector v3 = v1.add(v2);
        almacenDer.cofre.pos =  new Location(
                world,
                1,
                1,
                1
        );
    }

    public void inicializarAlamcenesYCofres(Arena arena, EscenaManager escenaManager){
        // Inicializa los almacenes
        if (arena != null) {
            almacenIzq = arena.getalmAlmacen1();
            almacenDer = arena.getalmAlmacen2();
        }
        // Inicializa la base del NPC
        baseAgente = new Base("pan");
        baseAgente.cofrepan = new Cofre();
        baseAgente.cofrepan.receta = escenaManager.getRecetaPan();
        baseAgente.cofregalleta = new Cofre();
        baseAgente.cofregalleta.receta = escenaManager.getRecetaGalleta();
        baseAgente.cofrepastel = new Cofre();
        baseAgente.cofrepastel.receta = escenaManager.getRecetaPastel();
        // Inicializa la base del player
        basePlayer = new Base("pan");
        basePlayer.cofrepan = new Cofre();
        basePlayer.cofrepan.receta = escenaManager.getRecetaPan();
        basePlayer.cofregalleta = new Cofre();
        basePlayer.cofregalleta.receta = escenaManager.getRecetaGalleta();
        basePlayer.cofrepastel = new Cofre();
        basePlayer.cofrepastel.receta = escenaManager.getRecetaPastel();
        // Inicializa el agente
        agente = new Agente("npc");
        agente.destino = Agente.destinotipo.ALALMACEN1;
        agente.producto = Material.tipo.PAN;
        agente.material = Material.tipo.CACAO;
        agente.cantidad = 10;
        // Publica la condición inicial
        System.out.println(UtilAlmacen.publica(almacenIzq));
        System.out.println(UtilAlmacen.publica(almacenDer));
    }


}
