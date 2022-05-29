package uoc.tfm.vmejia.speedrun.instance;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlBase;
import uoc.tfm.vmejia.speedrun.var.Escena;

public class GUI {
    public static void publicarMarcadorInicial(Player player){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        Objective obj = board.registerNewObjective("testboard","dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName(ChatColor.GREEN.toString()+ChatColor.BOLD+"-- Marcador --");

        int n = 1;

        ChatColor coloPlayer = ChatColor.GREEN;
        ChatColor coloNPC = ChatColor.AQUA;

        Team inv_Leche_Player = board.registerNewTeam("inv_Leche_Player");
        inv_Leche_Player.addEntry(ChatColor.YELLOW.toString());
        inv_Leche_Player.setPrefix(coloPlayer+" Leche: ");
        inv_Leche_Player.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.YELLOW.toString()).setScore(n);
        n++;

        Team inv_Trigo_Player = board.registerNewTeam("inv_Trigo_Player");
        inv_Trigo_Player.addEntry(ChatColor.GOLD.toString());
        inv_Trigo_Player.setPrefix(coloPlayer+" Trigo: ");
        inv_Trigo_Player.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.GOLD.toString()).setScore(n);
        n++;

        Team inv_Huevo_Player = board.registerNewTeam("inv_Huevo_Player");
        inv_Huevo_Player.addEntry(ChatColor.GREEN.toString());
        inv_Huevo_Player.setPrefix(coloPlayer+" Huevo: ");
        inv_Huevo_Player.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.GREEN.toString()).setScore(n);
        n++;

        Team inv_Cacao_Player = board.registerNewTeam("inv_Cacao_Player");
        inv_Cacao_Player.addEntry(ChatColor.BLUE.toString());
        inv_Cacao_Player.setPrefix(coloPlayer+" Cacao: ");
        inv_Cacao_Player.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.BLUE.toString()).setScore(n);
        n++;

        Team puntosPlayer = board.registerNewTeam("productosPlayer");
        puntosPlayer.addEntry(ChatColor.BOLD.toString());
        puntosPlayer.setPrefix(coloPlayer+" Productos: ");
        puntosPlayer.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.BOLD.toString()).setScore(n);
        n++;

        Score namePlayer = obj.getScore(coloPlayer+player.getName());
        namePlayer.setScore(n);
        n++;

        Score space3 = obj.getScore("-------------");
        space3.setScore(n);
        n++;

        Team inv_Leche_NPC = board.registerNewTeam("inv_Leche_NPC");
        inv_Leche_NPC.addEntry(ChatColor.GRAY.toString());
        inv_Leche_NPC.setPrefix(coloNPC+" Leche: ");
        inv_Leche_NPC.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.GRAY.toString()).setScore(n);
        n++;

        Team inv_Trigo_NPC = board.registerNewTeam("inv_Trigo_NPC");
        inv_Trigo_NPC.addEntry(ChatColor.AQUA.toString());
        inv_Trigo_NPC.setPrefix(coloNPC+" Trigo: ");
        inv_Trigo_NPC.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.AQUA.toString()).setScore(n);
        n++;

        Team inv_Huevo_NPC = board.registerNewTeam("inv_Huevo_NPC");
        inv_Huevo_NPC.addEntry(ChatColor.BLACK.toString());
        inv_Huevo_NPC.setPrefix(coloNPC+" Huevo: ");
        inv_Huevo_NPC.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.BLACK.toString()).setScore(n);
        n++;

        Team inv_Cacao_NPC = board.registerNewTeam("inv_Cacao_NPC");
        inv_Cacao_NPC.addEntry(ChatColor.DARK_AQUA.toString());
        inv_Cacao_NPC.setPrefix(coloNPC+" Cacao: ");
        inv_Cacao_NPC.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.DARK_AQUA.toString()).setScore(n);
        n++;

        Team puntosNPC = board.registerNewTeam("productosNPC");
        puntosNPC.addEntry(ChatColor.RED.toString());
        puntosNPC.setPrefix(coloNPC+" Productos: ");
        puntosNPC.setSuffix(ChatColor.YELLOW+"0");
        obj.getScore(ChatColor.RED.toString()).setScore(n);
        n++;

        Score nameNPC = obj.getScore(coloNPC+"NPC");
        nameNPC.setScore(n);
        n++;

        player.setScoreboard(board);
    }

    public static void PublicarMarcador(Player player, Escena escena){
        int inv_Leche_Player = CtrlBase.inv_Leche(escena.basePlayer);
        int inv_Huevo_Player = CtrlBase.inv_Huevo(escena.basePlayer);
        int inv_Cacao_Player = CtrlBase.inv_Cacao(escena.basePlayer);
        int inv_Trigo_Player = CtrlBase.inv_Trigo(escena.basePlayer);
        int productosPlayer = CtrlBase.inv_Productos(escena.basePlayer);;

        int inv_Leche_NPC = CtrlBase.inv_Leche(escena.baseNPC);
        int inv_Huevo_NPC = CtrlBase.inv_Huevo(escena.baseNPC);
        int inv_Cacao_NPC = CtrlBase.inv_Cacao(escena.baseNPC);
        int inv_Trigo_NPC = CtrlBase.inv_Trigo(escena.baseNPC);
        int productosNPC = CtrlBase.inv_Productos(escena.baseNPC);;

        PonerMarcador(player,"inv_Leche_Player",inv_Leche_Player);
        PonerMarcador(player,"inv_Huevo_Player",inv_Huevo_Player);
        PonerMarcador(player,"inv_Cacao_Player",inv_Cacao_Player);
        PonerMarcador(player,"inv_Trigo_Player",inv_Trigo_Player);
        PonerMarcador(player,"productosPlayer",productosPlayer);

        PonerMarcador(player,"inv_Leche_NPC",inv_Leche_NPC);
        PonerMarcador(player,"inv_Huevo_NPC",inv_Huevo_NPC);
        PonerMarcador(player,"inv_Cacao_NPC",inv_Cacao_NPC);
        PonerMarcador(player,"inv_Trigo_NPC",inv_Trigo_NPC);
        PonerMarcador(player,"productosNPC",productosNPC);
    }

    public static void PonerMarcador(Player player, String team, int valor){
        String color = ChatColor.YELLOW.toString();
        player.getScoreboard().getTeam(team).setSuffix(color+valor);
    }

    public static void PublicarLugar(Player player, String lugar){
        if (lugar.equals("LOBBY")){
            System.out.println("Ayuda para el LOBBY");
            player.setPlayerListHeaderFooter(
                    ChatColor.GREEN+ "Te encuentras en "+lugar,
                    "Para empezar a jugar, elije una area \n Da click derecho en uno de los letreros");
        } else {
            System.out.println("Ayuda para la ARENA");
            player.setPlayerListHeaderFooter(
                    ChatColor.GREEN+ "Te encuentras en la "+ lugar,
                    "Toma los ingredientes de los almacenes \n Llévalos hasta los cofres de tu base \n No pierdas de vista el marcador");
        }

    }

    public static void PublicarGanador(Player player){
        player.sendTitle(
                ChatColor.RED+"HAS GANADO!",
                ChatColor.GREEN+"Felicitaciones "+player.getName(),
                20,
                100,
                20);
    }

}
