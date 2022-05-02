package uoc.tfm.vmejia.speedrun.instance;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import uoc.tfm.vmejia.speedrun.GameState;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Arena {
    private int id;
    private Location spawn;
    private List<UUID> players;
    private GameState state;
    private Countdown countdown;
    private Game game;
    private SpeedRun minigame;
    private boolean canJoin;

    public Arena(SpeedRun minigame, int id, Location spawn){
        this.id = id;
        this.spawn = spawn;
        this.state = GameState.RECRUITING;
        this.players = new ArrayList<>();
        this.countdown = new Countdown(minigame, this);
        this.game = new Game(this);
        this.minigame = minigame;
        this.canJoin = true;
    }

    /* INFO */
    public int getId() {return id;}
    public List<UUID> getPlayers() {return players;}
    public Location getSpawn() {return spawn;}
    public GameState getState(){return state;}
    public Game getGame(){ return game;}
    public void setState(GameState state) {this.state = state;}
    public World getWorld(){return spawn.getWorld();}
    public void toggleCanJoin(){this.canJoin = !this.canJoin;}
    public boolean canJoin(){return this.canJoin;}

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

            String worldName = spawn.getWorld().getName();
            //Bukkit.unloadWorld(spawn.getWorld(),false);
            World world = Bukkit.createWorld(new WorldCreator(worldName) );
            world.setAutoSave(false);
        }

        sendTitle("","");
        state = GameState.RECRUITING;
        countdown.cancel();
        countdown = new Countdown(minigame, this);
        game = new Game(this);
    }

    /* PLAYERS */

    public void addPlayer(Player player){
        players.add(player.getUniqueId());
        player.teleport(spawn);

        if (state.equals(GameState.RECRUITING) && players.size() >= ConfigManager.getRequiredPlayers()){
            countdown.start();
        }
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


}
