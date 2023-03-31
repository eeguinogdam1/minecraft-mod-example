package es.mariaanasanz.ut7.mods.impl;

import es.mariaanasanz.ut7.mods.base.*;
import net.minecraft.client.multiplayer.chat.report.ReportEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.*;

@Mod(DamMod.MOD_ID)
public class ExampleMod extends DamMod implements IBlockBreakEvent, IServerStartEvent,
        IItemPickupEvent, ILivingDamageEvent, IUseItemEvent, IFishedEvent,
        IInteractEvent, IMovementEvent {

    public ExampleMod(){
        super();
    }

    @Override
    public String autor() {
        return "Javier Jorge Soteras";
    }

    @Override
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = event.getPlayer().getLevel();
        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos);
        System.out.println("Bloque destruido en la posicion "+pos);
        BlockPos posi = pos;
        if (state.getBlock().getName().getString().trim().toLowerCase().endsWith("log")){
            System.out.println("has roto un tronco, tronco");
            Player player = event.getPlayer();
            ItemStack heldItem = player.getMainHandItem();
            if(esArbol(event)){
                if (heldItem.getItem().getName(heldItem).getString().trim().toLowerCase().endsWith("axe")) {
                    while (state.getBlock().getName().getString().trim().toLowerCase().endsWith("log") ||
                            state.getBlock().getName().getString().trim().toLowerCase().endsWith("leaves")) {
                        for (int xi = 0; xi < 5; xi++) {
                            BlockPos poso = new BlockPos(posi.getX() + xi, posi.getY(), posi.getZ());
                            BlockState stato = event.getLevel().getBlockState(poso);
                            if(stato.getBlock().getName().getString().trim().toLowerCase().endsWith("log") ||
                                    stato.getBlock().getName().getString().trim().toLowerCase().endsWith("leaves")){
                                level.destroyBlock(poso , true);
                            }
                            for (int zi = 0; zi < 5; zi++) {
                                BlockPos posu = new BlockPos(posi.getX() + xi, posi.getY(), posi.getZ() + zi);
                                BlockState statu = event.getLevel().getBlockState(posu);
                                if(statu.getBlock().getName().getString().trim().toLowerCase().endsWith("log") ||
                                        statu.getBlock().getName().getString().trim().toLowerCase().endsWith("leaves")) {
                                    level.destroyBlock(posu, true);
                                }
                            }
                            for (int zq = 0; zq < 5; zq++) {
                                BlockPos posu = new BlockPos(posi.getX() + xi, posi.getY(), posi.getZ() - zq);
                                BlockState statu = event.getLevel().getBlockState(posu);
                                if(statu.getBlock().getName().getString().trim().toLowerCase().endsWith("log") ||
                                        statu.getBlock().getName().getString().trim().toLowerCase().endsWith("leaves")) {
                                    level.destroyBlock(posu, true);
                                }
                            }
                        }
                        for (int xd = 0; xd < 5; xd++) {
                            BlockPos poso = new BlockPos(posi.getX() - xd, posi.getY(), posi.getZ());
                            BlockState stato = event.getLevel().getBlockState(poso);
                            if(stato.getBlock().getName().getString().trim().toLowerCase().endsWith("log") ||
                                    stato.getBlock().getName().getString().trim().toLowerCase().endsWith("leaves")){
                                level.destroyBlock(poso , true);
                            }
                            for (int zd = 0; zd < 5; zd++) {
                                BlockPos posu = new BlockPos(posi.getX() - xd, posi.getY(), posi.getZ() - zd);
                                BlockState statu = event.getLevel().getBlockState(posu);
                                if(statu.getBlock().getName().getString().trim().toLowerCase().endsWith("log") ||
                                        statu.getBlock().getName().getString().trim().toLowerCase().endsWith("leaves")) {
                                    level.destroyBlock(posu, true);
                                }
                            }
                            for (int zq = 0; zq < 5; zq++) {
                                BlockPos posu = new BlockPos(posi.getX() - xd, posi.getY(), posi.getZ() + zq);
                                BlockState statu = event.getLevel().getBlockState(posu);
                                if(statu.getBlock().getName().getString().trim().toLowerCase().endsWith("log") ||
                                        statu.getBlock().getName().getString().trim().toLowerCase().endsWith("leaves")) {
                                    level.destroyBlock(posu, true);
                                }
                            }
                        }

                        level.destroyBlock(posi, true);
                        System.out.println(posi);
                        System.out.println("se rompe");

                        state = event.getLevel().getBlockState(posi.above());
                        posi = posi.above();
                    }
                }
            }
        }
    }

    public boolean esArbol(BlockEvent.BreakEvent event){
        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos.above());
        while (state.getBlock().getName().getString().trim().toLowerCase().endsWith("log") ||
                state.getBlock().getName().getString().trim().toLowerCase().endsWith("leaves")) {
            System.out.println("hay algo");
            if (state.getBlock().getName().getString().trim().toLowerCase().endsWith("leaves")){
                return true;
            }
            state = event.getLevel().getBlockState(pos.above());
            pos = pos.above();
        }
        return false;
    }

    @Override
    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        LOGGER.info("Server starting");
    }

    @Override
    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        LOGGER.info("Item recogido");
        System.out.println("Item recogido");
    }

    @Override
    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        System.out.println("evento LivingDamageEvent invocado "+event.getEntity().getClass()+" provocado por "+event.getSource().getEntity());
    }

    @Override
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        System.out.println("evento LivingDeathEvent invocado "+event.getEntity().getClass()+" provocado por "+event.getSource().getEntity());

    }

    @Override
    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event) {
        LOGGER.info("evento LivingEntityUseItemEvent invocado "+event.getEntity().getClass());
    }


    @Override
    @SubscribeEvent
    public void onPlayerFish(ItemFishedEvent event) {
        System.out.println("¡Has pescado un pez!");
    }

    @Override
    @SubscribeEvent
    public void onPlayerTouch(PlayerInteractEvent.RightClickBlock event) {
        System.out.println("¡Has hecho click derecho!");
        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos);
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();
        if (ItemStack.EMPTY.equals(heldItem)) {
            System.out.println("La mano esta vacia");
            if (state.getBlock().getName().getString().trim().toLowerCase().endsWith("log")) {
                System.out.println("¡Has hecho click sobre un tronco!");
            }
        }
    }

    @Override
    @SubscribeEvent
    public void onPlayerWalk(MovementInputUpdateEvent event) {
        if(event.getEntity() instanceof Player){
            if(event.getInput().down){
                System.out.println("down"+event.getInput().down);
            }
            if(event.getInput().up){
                System.out.println("up"+event.getInput().up);
            }
            if(event.getInput().right){
                System.out.println("right"+event.getInput().right);
            }
            if(event.getInput().left){
                System.out.println("left"+event.getInput().left);
            }
        }
    }
}
