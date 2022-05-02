package uoc.tfm.vmejia.speedrun.instance;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import uoc.tfm.vmejia.speedrun.GameState;

import java.util.HashMap;
import java.util.UUID;

public class Game {
    private Arena arena;
    private HashMap<UUID, Integer> points;

    public  Game(Arena arena){
        this.arena = arena;
        this.points = new HashMap<>();
    }

    public void start(){
        arena.setState(GameState.LIVE);
        arena.sendMessage(ChatColor.GREEN+"El juego ha comenzado! ...");

        // Inicializa el marcador de cada jugador registrado en la arena
        for(UUID uuid: arena.getPlayers()){
            points.put(uuid,0);
        }
    }

    // Aumenta el marcador de un jugador
    public void addPoint(Player player){
        int playerPoints = points.get(player.getUniqueId())+1;

        // Verifica que el puntaje sea el ganador
        if(playerPoints == 3){
            arena.sendMessage(ChatColor.GOLD + player.getName()+" HA GANADO LA PARTIDA!!");
            // Se resetea la arena para volver a empezar el juego
            arena.reset();
            return;
        }

        // Registra el nuevo marcador
        points.replace(player.getUniqueId(),playerPoints);
        // notifica al jugador de los puntos ganados
        player.sendMessage(ChatColor.GREEN + player.getName()+ "+1 PUNTO!!" );
    }
}
