package uoc.tfm.vmejia.speedrun.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uoc.tfm.vmejia.speedrun.GameState;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.instance.Arena;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

public class ArenaCommand implements CommandExecutor {

    private SpeedRun minigame;

    public ArenaCommand(SpeedRun minigame){
        this.minigame = minigame;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            Player player = (((Player) sender).getPlayer());

            if(args.length == 1 && args[0].equalsIgnoreCase("list")){
                player.sendMessage(ChatColor.GREEN+"Estas son las arenas disponibles");
                for(Arena arena: minigame.getArenaManager().getArenas()){
                    player.sendMessage(
                            ChatColor.GREEN+" - " +
                            arena.getId() + "(" +
                            arena.getState().name() + ")"
                    );
                }
            } else if (args.length == 1 && args[0].equalsIgnoreCase("leave")){
                Arena arena = minigame.getArenaManager().getArena(player);
                if (arena != null){
                    arena.removePlayer(player);
                    player.sendMessage(ChatColor.RED+"Has salido de la arena " + arena.getId());
                } else {
                    player.sendMessage(ChatColor.RED+"No estás en ninguna arena.");
                }
            } else  if (args.length == 2 && args[0].equalsIgnoreCase("join")){
                // Unir el jugador a una arena
                if(minigame.getArenaManager().getArena(player) != null){
                    player.sendMessage(ChatColor.RED+"Ya estás jugando en una arena.");
                    return false;
                }

                int id;
                try{
                    id = Integer.parseInt(args[1]);
                } catch (NumberFormatException e){
                    player.sendMessage(ChatColor.RED+"El id no es un número válido");
                    return false;
                }

                if(id >= 0 && id < minigame.getArenaManager().getArenas().size()){
                    Arena arena = minigame.getArenaManager().getArena(id);
                    if(arena.getState().equals(GameState.RECRUITING) || arena.getState().equals(GameState.COUNTDOWN)){

                        //if(arena.canJoin()){
                        if(true){
                            arena.addPlayer(player);
                            player.sendMessage(ChatColor.GREEN+"Ahora está en la arena "+arena.getId());
                        } else {
                            player.sendMessage(ChatColor.RED+"No puedes conectarte a esta arena ahora. El mapa se está cargando");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED+"No puedes conectarte a esta arena ahora");
                    }
                } else
                {
                    player.sendMessage(ChatColor.RED+"El id no corresponde a ninguna arena.");
                }
            } else {
                player.sendMessage(ChatColor.RED+"Uso NO válido: Estas son las opciones");
                player.sendMessage(ChatColor.RED+" - /arena list");
                player.sendMessage(ChatColor.RED+" - /arena leave");
                player.sendMessage(ChatColor.RED+" - /arena join <id>");
            }
        }

        return false;
    }
}
