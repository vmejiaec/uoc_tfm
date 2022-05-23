package uoc.tfm.vmejia.speedrun;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import uoc.tfm.vmejia.speedrun.ctrl.CtrlCofre;
import uoc.tfm.vmejia.speedrun.instance.Arena;
import uoc.tfm.vmejia.speedrun.util.UtilChest;
import uoc.tfm.vmejia.speedrun.util.UtilLocation;
import uoc.tfm.vmejia.speedrun.var.Agente;
import uoc.tfm.vmejia.speedrun.var.Cofre;
import uoc.tfm.vmejia.speedrun.var.Escena;
import uoc.tfm.vmejia.speedrun.var.MaterialModelo;

import java.util.Arrays;
import java.util.HashMap;

public class Events implements Listener {

    private SpeedRun minigame;
    private Location cofreLocation;

    public Events(SpeedRun minigame){
        this.minigame = minigame;
    }

    @EventHandler
    public void PlayerClickOnChest(InventoryClickEvent event){
        System.out.println("Evento de click en cofre --- ---");
        if (event.getClick().isRightClick()){
            System.out.println("Botón derecho y cancelado");
            event.setCancelled(true);
        }

        InventoryAction action = event.getAction();
        // Controla el tipo de acción
        if (
                action.equals(InventoryAction.PLACE_ALL)  ||
                action.equals(InventoryAction.PLACE_ONE)  ||
                action.equals(InventoryAction.PLACE_SOME) ||
                action.equals(InventoryAction.PICKUP_ALL) ||
                action.equals(InventoryAction.PICKUP_HALF)||
                action.equals(InventoryAction.PICKUP_ONE) ||
                action.equals(InventoryAction.PICKUP_SOME)
        ) {
            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getClickedInventory();
            int slotId = event.getSlot();
            System.out.println(Arrays.toString(inventory.getContents()));
            System.out.println("getSlot: "+ slotId);
            System.out.println("getAction: "+ action);
            ItemStack itemStack = inventory.getItem(slotId);
            System.out.println("itemStack: "+ itemStack);
            Location clickLocation = event.getClickedInventory().getLocation();
            System.out.println("Loc: "+clickLocation);
            // El cofre
            Arena arena = minigame.getArenaManager().getArena(player);
            Escena escena = arena.getEscena();
            Agente jugador = escena.jugador;
            Cofre cofre = escena.getCofre(cofreLocation);
            if(cofre != null){
                System.out.println("Cofre encontrado: "+cofre.nombre);
            } else {
                System.out.println("Cofre no encontrado xxxx");
            }
            // Decide qué hacer segun si es almacen o base
            if(cofre.nombre.contains("Almacen")){
                System.out.print("Estoy en Almacen");
                if(event.getSlotType().equals(InventoryType.SlotType.CONTAINER)){
                    // Averiguo qué material dio click

                    switch (itemStack.getType()){
                        case EGG:
                            jugador.material = MaterialModelo.tipo.HUEVO;
                            break;
                        case COCOA_BEANS:
                            jugador.material = MaterialModelo.tipo.CACAO;
                            break;
                        case MILK_BUCKET:
                            jugador.material = MaterialModelo.tipo.LECHE;
                            break;
                        case WHEAT:
                            jugador.material = MaterialModelo.tipo.TRIGO;
                            break;
                    }
                    // Quito del cofre del almacen y pongo en el jugador
                    if (CtrlCofre.Retiro(cofre, jugador.material , jugador.cantidad)){
                        // Pone en el cofre del agente
                        System.out.println(" - Deposito: " );
                        CtrlCofre.Deposito(jugador.bolsa,jugador.material,jugador.cantidad);
                    }
                    // publico el cofre del almacen
                    CtrlCofre.PublicaContenido(cofre);
                    // Publica la bolsa en el jugador
                    
                }
            } else if(cofre.nombre.contains("base")){
                System.out.print("Estoy en la base");
                if(event.getSlotType().equals(InventoryType.SlotType.QUICKBAR)){
                    // Quito del jugador y pongo en el cofre
                }
            }

        }

        event.setCancelled(true);

        System.out.println("Fin del evento click -- --");
    }

    @EventHandler
    public void PlayerRightClick(org.bukkit.event.player.PlayerInteractEvent event){
        if(event.getHand().equals(EquipmentSlot.HAND) && event.hasBlock())
        {
            System.out.print("Evento click - - Inicia");
            Block bloque = event.getClickedBlock();

            if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                return;
            }

            System.out.println("   Bloque tipo: "+bloque.getType());

            if(bloque.getType().equals(Material.CHEST)){
                System.out.print("Si es un cofre!!");

                Location bloqueLoc = bloque.getLocation();
                System.out.print(UtilLocation.ToString(bloqueLoc));

                this.cofreLocation = bloqueLoc;
            }
        }
        System.out.print("Evento click - - Fin");
    }

}
