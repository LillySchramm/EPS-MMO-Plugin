package de.epsdev.plugins.MMO.events;

import de.epsdev.plugins.MMO.data.DataManager;
import de.epsdev.plugins.MMO.data.output.Err;
import de.epsdev.plugins.MMO.data.output.Out;
import de.epsdev.plugins.MMO.data.player.User;
import de.epsdev.plugins.MMO.data.regions.cites.houses.House;
import de.epsdev.plugins.MMO.npc.mobs.Base_Mob;
import de.epsdev.plugins.MMO.schedulers.Static_Effect_Scheduler;
import de.epsdev.plugins.MMO.tools.Vec3i;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntity;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.material.Door;


public class e_PlayerInteract implements Listener {

    @EventHandler
    void onInteract(PlayerInteractEvent e){

        Player player = e.getPlayer();
        User user = DataManager.onlineUsers.get(player.getUniqueId().toString());

        new Base_Mob().display(player);

        Block block = e.getClickedBlock();
        BlockFace blockFace = e.getBlockFace();


        Vec3i blockPos = new Vec3i(0,0,0);

        try{
            blockPos = new Vec3i(block.getLocation());
        }catch (Exception exception){}

        Material blockMaterial = null;

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){

            if(user.onRightObj == null){
                if(block.getType().toString().contains("DOOR")) {
                    if (!(player.getGameMode() == GameMode.CREATIVE && user.rank.canManageHouses)) {
                        e.setCancelled(true);

                        House house = DataManager.getHouseByDoor(blockPos);

                        if(house != null){
                            Out.printToConsole(blockFace.toString());

                            if(house.currentOwner_UUID.equalsIgnoreCase(user.UUID)){
                                Location location = null;
                                Block b = null;

                                if(blockFace == BlockFace.NORTH){
                                    b = block.getRelative(BlockFace.SOUTH,2);
                                    location = b.getLocation();
                                }else if(blockFace == BlockFace.EAST) {
                                    b = block.getRelative(BlockFace.WEST,2);
                                    location = b.getLocation();
                                }else if(blockFace == BlockFace.SOUTH){
                                    b = block.getRelative(BlockFace.NORTH,2);
                                    location = b.getLocation();
                                }else if(blockFace == BlockFace.WEST){
                                    b = block.getRelative(BlockFace.EAST, 2);
                                    location = b.getLocation();
                                }

                                location.setY(house.getDoorByPosition(blockPos).y);
                                player.teleport(location);
                            }

                        }

                    }

                }

                if(block.getType() == Material.WALL_SIGN){

                    House house = DataManager.getHouseBySign(blockPos);

                    if(house != null){
                        house.showRentMenu(player);
                    }
                    e.setCancelled(true);
                }

            }else {
                user.onRightObj.interact(e);
            }
        }
    }

    @EventHandler
    void onEntityInteract(PlayerInteractAtEntityEvent e){
        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();

        Static_Effect_Scheduler.showArmorStandConfig(player, entity.getEntityId());
    }

    @EventHandler
    void onRightNPC(RightClickNPC e){
        Player player = e.player.getPlayer();
        User user = e.user;

        if(player.getGameMode() == GameMode.SURVIVAL){
            Out.printToPlayer(player, "Hi there.", e.npc);
        }else if(player.getGameMode() == GameMode.CREATIVE){
            if(user.rank.canManageNPC){
                e.npc.showManageGUI(player);
            }else {
                Err.rankError(player);
            }
        }

    }
}
