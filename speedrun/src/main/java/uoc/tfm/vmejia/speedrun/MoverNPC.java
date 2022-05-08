package uoc.tfm.vmejia.speedrun;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class MoverNPC implements CommandExecutor {
    private SpeedRun speed;

    public MoverNPC(SpeedRun speed){this.speed = speed;}

    @Override
    public boolean onCommand( CommandSender sender, Command command,String label,String[] args) {

        Player player = (Player) sender;

        NPC npc = CitizensAPI.getNPCRegistry().getById(0);
        npc.getNavigator().setTarget(player.getLocation());
        return false;
    }
}
