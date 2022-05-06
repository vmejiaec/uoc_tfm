package uoc.tfm.vmejia.speedrun;


import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class CrearNPC implements CommandExecutor {

    private Citnpc citnpc;

    public CrearNPC(Citnpc citnpc){this.citnpc = citnpc;}

    @Override
    public boolean onCommand(CommandSender sender, Command command,  String label,  String[] args) {
        // coordenadas
        int npcx = 278;
        int npcy = 65;
        int npcz = -82;

        World world = Bukkit.getWorld("world");
        Location location = new Location(world,npcx,npcy,npcz);

        Player player = (Player) sender;

        NPC npc = CitizensAPI.getNPCRegistry()
                .createNPC(EntityType.PLAYER,"npcVictor",player.getLocation());

        npc.addTrait(CitizensAPI.getTraitFactory().getTrait("mytraitname"));

        return false;
    }
}
