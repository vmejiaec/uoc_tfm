package uoc.tfm.vmejia.speedrun.instance;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import uoc.tfm.vmejia.speedrun.GameState;
import uoc.tfm.vmejia.speedrun.SpeedRun;
import uoc.tfm.vmejia.speedrun.manager.ConfigManager;

public class Countdown extends BukkitRunnable {

    private SpeedRun minigame;
    private Arena arena;
    private int countdownSeconds;

    public Countdown(SpeedRun minigame, Arena arena){
        this.minigame = minigame;
        this.arena = arena;
        this.countdownSeconds = ConfigManager.getCountdownSeconds();
    }

    public void start(){
        arena.setState(GameState.COUNTDOWN);
        runTaskTimer(minigame,0,20);
    }

    @Override
    public void run() {
        if(countdownSeconds == 0){
            cancel();
            arena.start();
            return;
        }

        if(countdownSeconds <=10 || countdownSeconds % 15 == 0){
            //El mensaje con la cuenta para el inicio del juego
            arena.sendMessage(
                    ChatColor.GREEN + "El juego empezará en " + countdownSeconds +
                    "segundo" +
                    (countdownSeconds == 1 ? "" :"s") + "."  // para añadir la s del plural en segundos
            );
        }

        arena.sendTitle(
            ChatColor.GREEN + "" + countdownSeconds +
                    "segundo" +
                    (countdownSeconds == 1 ? "" :"s") + ".",
            ChatColor.GRAY + "hasta que el juego empiece."
        );

        --countdownSeconds;
    }

}
