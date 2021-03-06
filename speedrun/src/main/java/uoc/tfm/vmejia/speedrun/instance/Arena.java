package uoc.tfm.vmejia.speedrun.instance;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;
import uoc.tfm.vmejia.speedrun.util.UtilEscena;
import uoc.tfm.vmejia.speedrun.var.Almacen;
import uoc.tfm.vmejia.speedrun.var.Escena;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {
    private int id;
    private Location playerSpawn;
    private List<UUID> players;
    private GameState state;
    private Countdown countdown;
    private Game game;
    private SpeedRun minigame;
    private boolean canJoin;
    private Location sign;
    private Location signExit;
    private Location npcSpawn;
    private Location almacen1, almacen2;
    private Almacen almAlmacen1, almAlmacen2;
    private Escena escena;
    private Player player;

    public Arena(SpeedRun minigame, int id,
                 Location playerSpawn, Location sign, Location signExit,
                 Location npcSpawn, Location almacen1, Location almacen2,
                 Almacen almAlmacen1, Almacen almAlmacen2
    ){
        this.id = id;
        this.playerSpawn = playerSpawn;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(minigame, this);
        this.game = new Game(this);
        this.minigame = minigame;
        this.canJoin = true;
        this.sign = sign;
        this.signExit = signExit;
        this.npcSpawn = npcSpawn;
        this.almacen1 = almacen1;
        this.almacen2 = almacen2;

        this.almAlmacen1 = almAlmacen1;
        this.almAlmacen2 = almAlmacen2;

        setState(GameState.RECRUITING);
    }

    /* INFO */
    public int getId() {return id;}
    public List<UUID> getPlayers() {return players;}
    public Location getPlayerSpawn() {return playerSpawn;}
    public GameState getState(){return state;}
    public Game getGame(){ return game;}
    public World getWorld(){return playerSpawn.getWorld();}
    public void toggleCanJoin(){this.canJoin = !this.canJoin;}
    public boolean canJoin(){return this.canJoin;}
    public Location getSignLocation(){return sign;}
    public Location getSignExitLocation(){return signExit;}
    public SpeedRun getMinigame(){return minigame;}
    public Location getNPCSpawn(){return this.npcSpawn;}
    public Location getAlmacen1(){return this.almacen1;}
    public Location getAlmacen2(){return this.almacen2;}
    public void setAlmacen1(Almacen almacen1){this.almAlmacen1 = almacen1;}
    public void setAlmacen2(Almacen almacen2){this.almAlmacen2 = almacen2;}
    public Player getPlayer(){return this.player;};
    public Almacen getalmAlmacen1(){return almAlmacen1;}
    public Almacen getalmAlmacen2(){return almAlmacen2;}

    public void setState(GameState state) {
        this.state = state;
        updateSign("Arena "+id,state.name(), "",state == GameState.LIVE ? "Players: "+players.size():"");
    }


    /* GAME */

    public void start(){
        System.out.println("---------------------- EL JUEGO EN LA ARENA DA INICIO --------------------------");
        game.start();
    }

    public void reset(){
        System.out.println("---------------------- EL JUEGO EN LA ARENA DA RE-INICIO --------------------------");
        if (state == GameState.LIVE){
            this.canJoin = false;

            // Coloca al jugador en el lobby
            Location location = ConfigManager.getLobbySpawn();
            for(UUID uuid: players){
                if(Bukkit.getPlayer(uuid) != null){
                    Bukkit.getPlayer(uuid).teleport(location);
                }
            }
            // Borra la lista de jugadores
            players.clear();
            // Evita que cualquier cambio en la arena sea guardado
            String worldName = playerSpawn.getWorld().getName();
            World world = Bukkit.createWorld(new WorldCreator(worldName) );
            world.setAutoSave(false);
        }
        setState(GameState.RECRUITING);
        sendTitle("Reinicio","Reinicio");
        countdown.cancel();
        countdown = new Countdown(minigame, this);
        game = new Game(this);
    }

    /* PLAYERS */

    public void addPlayer(Player player){
        // Se coloca al player en la arena
        players.add(player.getUniqueId());
        this.player = player;
        player.teleport(playerSpawn);

        // Lanza sonido de entrada a la arena
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_SCREAM, 2.0f, 1.0f);

        // Inicializa al player, borra su inventario personal
        UtilEscena.inicializarJugador(player);

        // Luego de a??adir al jugador, se a??ade al NPC
        NPC npc = minigame.getNPC();
        System.out.println("NPC desde la arena "+npc.getName());
        players.add(npc.getUniqueId());
        npc.teleport(npcSpawn, PlayerTeleportEvent.TeleportCause.PLUGIN);

        // Da inicio al conteo para iniciar el juego
        if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("GAME OVER","Saliste de la arena");
        // Publica el lugar
        GUI.PublicarLugar(player, "LOBBY");

        // Inicializa al player, borra su inventario personal
        UtilEscena.inicializarJugador(player);

        // Se remueve al NPC
        NPC npc = minigame.getNPC();
        System.out.println("NPC desde la arena "+npc.getName());
        players.remove(npc.getUniqueId());

        // Si estamos en espera de m??s jugadores, no se hecha fuera a los que ya est??n conectados esperando
        if(state.equals(GameState.COUNTDOWN) && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "No hay suficientes jugadores. El conteo se ha detenido.");
            reset();
            return;
        }

        // Si el juego est?? en ejecuci??n, no se hecha fuera a los que ya est??n jugando
        if(state.equals(GameState.LIVE) && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "No hay suficientes jugadores para continuar la partida.");
            reset();
            return;
        }

        if(state == GameState.LIVE){
            updateSign("Arena "+id,state.name(), "","Players: "+players.size());
        }
    }

    /* TOOLS */
    public void sendMessage(String message){
        for(UUID uuid:players){
            if( Bukkit.getPlayer(uuid) != null) {
                Bukkit.getPlayer(uuid).sendMessage(message);
            }
        }
    }

    public void sendTitle(String title, String subtitle){
        for(UUID uuid:players){
            if( Bukkit.getPlayer(uuid) != null) {
                Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
            }
        }
    }

    public void updateSign(String line1,String line2,String line3,String line4){
        // System.out.println(" - sign: " + sign);
        Sign signBlock = (Sign) sign.getBlock().getState();
        signBlock.setLine(0,line1);
        signBlock.setLine(1,line2);
        signBlock.setLine(2,line3);
        signBlock.setLine(3,line4);
        signBlock.update();
    }

    public void setEscena(Escena escena) {
        this.escena = escena;
    }

    public  Escena getEscena(){
        return this.escena;
    }
}