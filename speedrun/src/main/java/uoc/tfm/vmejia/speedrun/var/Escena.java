package uoc.tfm.vmejia.speedrun.var;

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
    public Vector vecbase1 = new Vector(152, 4, -105);
    // La posición de los almacenes
    public Vector vecAlmacen1 = new Vector(172, 4, -103);
    public Vector vecAlmacen2 = new Vector(172, 4, -90);

    // los caminos de la base a los almacenes
    public List<Vector> caminoAlm1 = new ArrayList<Vector>(){{add(vecbase1);add(vecAlmacen1);}};
    public List<Vector> caminoAlm2 = new ArrayList<Vector>(){{add(vecbase1);add(vecAlmacen2);}};
    public List<Vector> camino = new ArrayList<>();

    public void iniciaLocaciones(Arena arena){
        System.out.println("Inicializa las Posiciones de la arena: "+arena.getId());
        vecbase1 = arena.getNPCSpawn().toVector();
        vecAlmacen1 = arena.getAlmacen1().toVector();
        vecAlmacen2 = arena.getAlmacen2().toVector();

        caminoAlm1 = new ArrayList<Vector>(){{add(vecbase1);add(vecAlmacen1);}};
        caminoAlm2 = new ArrayList<Vector>(){{add(vecbase1);add(vecAlmacen2);}};

        camino = caminoAlm2;
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
