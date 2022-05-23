package uoc.tfm.vmejia.speedrun.util;

import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.PufferFish;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlCofre;
import uoc.tfm.vmejia.speedrun.instance.Arena;
import uoc.tfm.vmejia.speedrun.manager.EscenaManager;
import uoc.tfm.vmejia.speedrun.var.*;

import java.util.ArrayList;

public class UtilEscena {
    public static void iniciaLocaciones(Escena escena, Arena arena){
        System.out.println("Inicializa las Posiciones de la arena: "+arena.getId());
        escena.vecBaseNPC = arena.getNPCSpawn().toVector();
        escena.vecBasePlayer = arena.getPlayerSpawn().toVector();
        escena.vecAlmacen1 = arena.getAlmacen1().toVector();
        escena.vecAlmacen2 = arena.getAlmacen2().toVector();

        escena.caminoAlm1 = new ArrayList<Vector>(){{add(escena.vecBaseNPC);add(escena.vecAlmacen1);}};
        escena.caminoAlm2 = new ArrayList<Vector>(){{add(escena.vecBaseNPC);add(escena.vecAlmacen2);}};

        escena.camino = escena.caminoAlm2;

        // Actualiza las posiciones de los cofres
        World world = arena.getWorld();
        // Decide la orientación de la base respecto a los cofres
        Vector posrelBaseCofrePan = new Vector(1,0,-2);
        Vector posrelBaseCofreGalleta = new Vector(1,0,0);
        Vector posrelBaseCofrePastel = new Vector(1,0,2);
        Vector posrelAlmacen = new Vector(-1,1,0);
        boolean signo = escena.vecAlmacen1.getX() > escena.vecBaseNPC.getX()  ;
        if (signo){
            posrelBaseCofrePan = new Vector(-1,0,2);
            posrelBaseCofreGalleta = new Vector(-1,0,0);
            posrelBaseCofrePastel = new Vector(-1,0,-2);
            posrelAlmacen = new Vector(1,1,0);
        }
        // Calcula dónde estan los cofres de bases y almacenes
        Vector posBaseNPCCofrePan = escena.vecBaseNPC.clone().add(posrelBaseCofrePan) ;
        Vector posBaseNPCCofreGalleta = escena.vecBaseNPC.clone().add(posrelBaseCofreGalleta) ;
        Vector posBaseNPCCofrePastel = escena.vecBaseNPC.clone().add(posrelBaseCofrePastel) ;
        Vector posBasePlayerCofrePan = escena.vecBasePlayer.clone().add(posrelBaseCofrePan) ;
        Vector posBasePlayerCofreGalleta = escena.vecBasePlayer.clone().add(posrelBaseCofreGalleta) ;
        Vector posBasePlayerCofrePastel = escena.vecBasePlayer.clone().add(posrelBaseCofrePastel) ;
        Vector posAlmacen1 = escena.vecAlmacen1.clone().add(posrelAlmacen);
        Vector posAlmacen2 = escena.vecAlmacen2.clone().add(posrelAlmacen);

        // Localiza los cofres
        System.out.println("World: "+world);
        if(world != null){
            escena.baseNPC.cofrepan.pos = UtilLocation.getLoc(world,posBaseNPCCofrePan);
            escena.baseNPC.cofregalleta.pos = UtilLocation.getLoc(world,posBaseNPCCofreGalleta);
            escena.baseNPC.cofrepastel.pos = UtilLocation.getLoc(world,posBaseNPCCofrePastel);

            escena.basePlayer.cofrepan.pos = UtilLocation.getLoc(world,posBasePlayerCofrePan);
            escena.basePlayer.cofregalleta.pos = UtilLocation.getLoc(world,posBasePlayerCofreGalleta);
            escena.basePlayer.cofrepastel.pos = UtilLocation.getLoc(world,posBasePlayerCofrePastel);

            escena.almacenIzq.cofre.pos = UtilLocation.getLoc(world,posAlmacen1);
            escena.almacenDer.cofre.pos = UtilLocation.getLoc(world,posAlmacen2);

            // Publicamos resultados de posiciones para control
            System.out.println("baseNPC.cofrepan.pos "+ escena.baseNPC.cofrepan.pos);
            System.out.println("baseNPC.cofregalleta.pos "+ escena.baseNPC.cofregalleta.pos);
            System.out.println("baseNPC.cofrepastel.pos "+ escena.baseNPC.cofrepastel.pos);

            System.out.println("basePlayer.cofrepan.pos "+ escena.basePlayer.cofrepan.pos);
            System.out.println("basePlayer.cofregalleta.pos "+ escena.basePlayer.cofregalleta.pos);
            System.out.println("basePlayer.cofrepastel.pos "+ escena.basePlayer.cofrepastel.pos);

            System.out.println("almacenIzq.cofre.pos "+ escena.almacenIzq.cofre.pos);
            System.out.println("almacenDer.cofre.pos "+ escena.almacenDer.cofre.pos);
        }
    }

    public static void inicializarAlamcenesYCofres(Escena escena, Arena arena, EscenaManager escenaManager){

        FileConfiguration config = arena.getMinigame().getConfig();
        arena.setAlmacen1(
                new Almacen(
                        "Almacen1",
                        config.getInt("arenas."+arena.getId()+".almacen1.inv_cacao"),
                        config.getInt("arenas."+arena.getId()+".almacen1.inv_huevo"),
                        config.getInt("arenas."+arena.getId()+".almacen1.inv_leche"),
                        config.getInt("arenas."+arena.getId()+".almacen1.inv_trigo")
                )
        );

        arena.setAlmacen2(
                new Almacen(
                        "Almacen2",
                        config.getInt("arenas."+arena.getId()+".almacen2.inv_cacao"),
                        config.getInt("arenas."+arena.getId()+".almacen2.inv_huevo"),
                        config.getInt("arenas."+arena.getId()+".almacen2.inv_leche"),
                        config.getInt("arenas."+arena.getId()+".almacen2.inv_trigo")
                )
        );

        // Inicializa los almacenes
        if (arena != null) {
            escena.almacenIzq = arena.getalmAlmacen1();
            escena.almacenIzq.cofre.nombre="Almacen 1";
            escena.almacenDer = arena.getalmAlmacen2();
            escena.almacenDer.cofre.nombre="Almacen 2";
        }
        // Inicializa la base del NPC
        escena.baseNPC = new Base("pan");
        escena.baseNPC.cofrepan = new Cofre();
        escena.baseNPC.cofrepan.nombre="base NPC cofre pan";
        escena.baseNPC.cofrepan.receta = escenaManager.getRecetaPan();
        escena.baseNPC.cofregalleta = new Cofre();
        escena.baseNPC.cofregalleta.nombre="base NPC cofre galleta";
        escena.baseNPC.cofregalleta.receta = escenaManager.getRecetaGalleta();
        escena.baseNPC.cofrepastel = new Cofre();
        escena.baseNPC.cofrepastel.nombre="base NPC cofre pastel";
        escena.baseNPC.cofrepastel.receta = escenaManager.getRecetaPastel();
        // Inicializa la base del player
        escena.basePlayer = new Base("pan");
        escena.basePlayer.cofrepan = new Cofre();
        escena.basePlayer.cofrepan.nombre="base Player cofre pan";
        escena.basePlayer.cofrepan.receta = escenaManager.getRecetaPan();
        escena.basePlayer.cofregalleta = new Cofre();
        escena.basePlayer.cofregalleta.nombre="base Player cofre galleta";
        escena.basePlayer.cofregalleta.receta = escenaManager.getRecetaGalleta();
        escena.basePlayer.cofrepastel = new Cofre();
        escena.basePlayer.cofrepastel.nombre="base Player cofre pastel";
        escena.basePlayer.cofrepastel.receta = escenaManager.getRecetaPastel();
        // Inicializa el agente
        escena.agente = new Agente("npc");
        escena.agente.destino = Agente.destinotipo.ALALMACEN1;
        escena.agente.producto = MaterialModelo.tipo.PAN;
        escena.agente.material = MaterialModelo.tipo.CACAO;
        escena.agente.cantidad = 10;
        // Inicializa el jugador
        escena.jugador = new Agente("jugador");
        escena.jugador.material = MaterialModelo.tipo.HUEVO;
        escena.jugador.cantidad = 10;
        // Publica la condición inicial
        System.out.println(UtilAlmacen.publica(escena.almacenIzq));
        System.out.println(UtilAlmacen.publica(escena.almacenDer));

        // Asocia la arena con la escena
        arena.setEscena(escena);
    }

    public static void PublicaContenidoCofres(Escena escena) {
        CtrlCofre.PublicaContenido(escena.baseNPC.cofrepan);
        CtrlCofre.PublicaContenido(escena.baseNPC.cofregalleta);
        CtrlCofre.PublicaContenido(escena.baseNPC.cofrepastel);

        CtrlCofre.PublicaContenido(escena.basePlayer.cofrepan);
        CtrlCofre.PublicaContenido(escena.basePlayer.cofregalleta);
        CtrlCofre.PublicaContenido(escena.basePlayer.cofrepastel);

        CtrlCofre.PublicaContenido(escena.almacenIzq.cofre);
        CtrlCofre.PublicaContenido(escena.almacenDer.cofre);
    }


    public static void inicializarJugador(Player player) {
        // borramos el inventario del player
        Inventory inventory = player.getInventory();
        int n = inventory.getContents().length;
        for (int i=0; i < n ; i++){
            ItemStack content = inventory.getContents()[i];
            if (content == null) continue;
            inventory.removeItem (content);
        }
    }

    public static boolean JugadorInventarioVacio(Player player){
        // confirmo si el jugador está vacío
        Inventory inventory = player.getInventory();
        return true;
    }
}








