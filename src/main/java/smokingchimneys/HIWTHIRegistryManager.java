package smokingchimneys;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = HomeIsWhereTheHearthIs.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class HIWTHIRegistryManager {	
	
	public static Block brick_chimney, cobblestone_chimney, mossy_cobblestone_chimney, stone_chimney, smooth_stone_chimney,
	stone_brick_chimney, cracked_stone_brick_chimney, mossy_stone_brick_chimney, granite_chimney,
	andesite_chimney, diorite_chimney, polished_granite_chimney, polished_andesite_chimney,
	polished_diorite_chimney, nether_brick_chimney, red_nether_brick_chimney, smooth_sandstone_chimney,
	smooth_red_sandstone_chimney;
	
	public static Block brick_chimney_top, cobblestone_chimney_top, mossy_cobblestone_chimney_top, stone_chimney_top, smooth_stone_chimney_top,
						stone_brick_chimney_top, cracked_stone_brick_chimney_top, mossy_stone_brick_chimney_top, granite_chimney_top,
						andesite_chimney_top, diorite_chimney_top, polished_granite_chimney_top, polished_andesite_chimney_top,
						polished_diorite_chimney_top, nether_brick_chimney_top, red_nether_brick_chimney_top, smooth_sandstone_chimney_top,
						smooth_red_sandstone_chimney_top;
	
	public static Block brick_fireplace;
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		brick_chimney = registerBlock(new ChimneyBlock(Blocks.BRICKS), "brick_chimney");
		cobblestone_chimney = registerBlock(new ChimneyBlock(Blocks.COBBLESTONE), "cobblestone_chimney");
		mossy_cobblestone_chimney = registerBlock(new ChimneyBlock(Blocks.MOSSY_COBBLESTONE), "mossy_cobblestone_chimney");
		stone_chimney = registerBlock(new ChimneyBlock(Blocks.STONE), "stone_chimney");
		smooth_stone_chimney = registerBlock(new ChimneyBlock(Blocks.SMOOTH_STONE), "smooth_stone_chimney");
		stone_brick_chimney = registerBlock(new ChimneyBlock(Blocks.STONE_BRICKS), "stone_brick_chimney");
		cracked_stone_brick_chimney = registerBlock(new ChimneyBlock(Blocks.CRACKED_STONE_BRICKS), "cracked_stone_brick_chimney");
		mossy_stone_brick_chimney = registerBlock(new ChimneyBlock(Blocks.MOSSY_STONE_BRICKS), "mossy_stone_brick_chimney");
		granite_chimney = registerBlock(new ChimneyBlock(Blocks.GRANITE), "granite_chimney");
		andesite_chimney = registerBlock(new ChimneyBlock(Blocks.ANDESITE), "andesite_chimney");
		diorite_chimney = registerBlock(new ChimneyBlock(Blocks.DIORITE), "diorite_chimney");
		polished_granite_chimney = registerBlock(new ChimneyBlock(Blocks.POLISHED_GRANITE), "polished_granite_chimney");
		polished_andesite_chimney = registerBlock(new ChimneyBlock(Blocks.POLISHED_ANDESITE), "polished_andesite_chimney");
		polished_diorite_chimney = registerBlock(new ChimneyBlock(Blocks.POLISHED_DIORITE), "polished_diorite_chimney");
		nether_brick_chimney = registerBlock(new ChimneyBlock(Blocks.NETHER_BRICKS), "nether_brick_chimney");
		red_nether_brick_chimney = registerBlock(new ChimneyBlock(Blocks.RED_NETHER_BRICKS), "red_nether_brick_chimney");
		smooth_sandstone_chimney = registerBlock(new ChimneyBlock(Blocks.SMOOTH_SANDSTONE), "smooth_sandstone_chimney");
		smooth_red_sandstone_chimney = registerBlock(new ChimneyBlock(Blocks.SMOOTH_RED_SANDSTONE), "smooth_red_sandstone_chimney");
		
		brick_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.BRICKS), "brick_chimney_top");
		cobblestone_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.COBBLESTONE), "cobblestone_chimney_top");
		mossy_cobblestone_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.MOSSY_COBBLESTONE), "mossy_cobblestone_chimney_top");
		stone_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.STONE), "stone_chimney_top");
		smooth_stone_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.SMOOTH_STONE), "smooth_stone_chimney_top");
		stone_brick_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.STONE_BRICKS), "stone_brick_chimney_top");
		cracked_stone_brick_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.CRACKED_STONE_BRICKS), "cracked_stone_brick_chimney_top");
		mossy_stone_brick_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.MOSSY_STONE_BRICKS), "mossy_stone_brick_chimney_top");
		granite_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.GRANITE), "granite_chimney_top");
		andesite_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.ANDESITE), "andesite_chimney_top");
		diorite_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.DIORITE), "diorite_chimney_top");
		polished_granite_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.POLISHED_GRANITE), "polished_granite_chimney_top");
		polished_andesite_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.POLISHED_ANDESITE), "polished_andesite_chimney_top");
		polished_diorite_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.POLISHED_DIORITE), "polished_diorite_chimney_top");
		nether_brick_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.NETHER_BRICKS), "nether_brick_chimney_top");
		red_nether_brick_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.RED_NETHER_BRICKS), "red_nether_brick_chimney_top");
		smooth_sandstone_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.SMOOTH_SANDSTONE), "smooth_sandstone_chimney_top");
		smooth_red_sandstone_chimney_top = registerBlock(new ChimneyTopBlock(Blocks.SMOOTH_RED_SANDSTONE), "smooth_red_sandstone_chimney_top");
		
		brick_fireplace = registerBlock(new FireplaceBlock(Blocks.BRICKS), "brick_fireplace");
	}
	
	public static Block registerBlock(Block chimney, String name) {
		chimney.setRegistryName(new ResourceLocation(HomeIsWhereTheHearthIs.MOD_ID, name));
		ForgeRegistries.BLOCKS.register(chimney);
		
		BlockItem chimney_item = new BlockItem(chimney, new Item.Properties().group(HomeIsWhereTheHearthIs.HEARTH_ITEMGROUP));
		chimney_item.setRegistryName(new ResourceLocation(HomeIsWhereTheHearthIs.MOD_ID, name));
		ForgeRegistries.ITEMS.register(chimney_item);
		
		return chimney;
	}
	
	public static void setRenderTypes() {
		RenderTypeLookup.setRenderLayer(brick_fireplace, RenderType.getCutout());
	}
}
