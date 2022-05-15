package uoc.tfm.vmejia.speedrun.instance;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.*;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

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

    public Arena(SpeedRun minigame, int id,
                 Location playerSpawn, Location sign, Location signExit,
                 Location npcSpawn, Location almacen1, Location almacen2
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
    public void setState(GameState state) {
        this.state = state;
        updateSign("Arena "+id,state.name(), "",state == GameState.LIVE ? "Players: "+players.size():"");
    }


    /* GAME */

    public void start(){
        game.start();
    }

    public void reset(){

        if (state == GameState.LIVE){
            this.canJoin = false;

            Location location = ConfigManager.getLobbySpawn();
            for(UUID uuid: players){
                Bukkit.getPlayer(uuid).teleport(location);
            }
            players.clear();

            String worldName = playerSpawn.getWorld().getName();
            //Bukkit.unloadWorld(spawn.getWorld(),false);
            World world = Bukkit.createWorld(new WorldCreator(worldName) );
            world.setAutoSave(false);
        }
        setState(GameState.RECRUITING);
        sendTitle("","");
        countdown.cancel();
        countdown = new Countdown(minigame, this);
        game = new Game(this);
    }

    /* PLAYERS */

    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(playerSpawn);

        NPC npc = minigame.getNPC();
        System.out.println("NPC desde la arena "+npc.getName());

        if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
    }

    public void addNPC(NPC npc){
        players.add(npc.getUniqueId());
        npc.teleport(npcSpawn, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    public void removePlayer(Player player){
        players.remove(player.getUniqueId());
        player.teleport(ConfigManager.getLobbySpawn());
        player.sendTitle("","");

        // Si estamos en espera de más jugadores, no se hecha fuera a los que ya están conectados esperando
        if(state.equals(GameState.COUNTDOWN) && players.size() < ConfigManager.getRequiredPlayers()){
            sendMessage(ChatColor.RED + "No hay suficientes jugadores. El conteo se ha detenido.");
            reset();
            return;
        }

        // Si el juego está en ejecución, no se hecha fuera a los que ya están jugando
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
            Bukkit.getPlayer(uuid).sendMessage(message);
        }
    }

    public void sendTitle(String title, String subtitle){
        for(UUID uuid:players){
            Bukkit.getPlayer(uuid).sendTitle(title, subtitle);
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

}