package smokingchimneys;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HomeIsWhereTheHearthIs.MOD_ID)
public class HomeIsWhereTheHearthIs {
	public static final String MOD_ID = "homeiswherethehearthis";
	public static final HearthItemGroup HEARTH_ITEMGROUP = new HearthItemGroup("homeiswherethehearthis");
	
	public HomeIsWhereTheHearthIs() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
	}
	
	private void commonSetup(FMLCommonSetupEvent event) {

	}
}

class HearthItemGroup extends ItemGroup {

	public HearthItemGroup(String label) {
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ChimneyTopBlock.brick_chimney_top);
	}
}