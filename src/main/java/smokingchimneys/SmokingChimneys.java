package smokingchimneys;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SmokingChimneys.MOD_ID)
public class SmokingChimneys {
	public static final String MOD_ID = "smokingchimneys";
	public static final ChimneysItemGroup CHIMNEYS_ITEMGROUP = new ChimneysItemGroup("smokingchimneys");
	
	public SmokingChimneys() {
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
	}
	
	private void commonSetup(FMLCommonSetupEvent event) {

	}
}

class ChimneysItemGroup extends ItemGroup {

	public ChimneysItemGroup(String label) {
		super(label);
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(ChimneyTopBlock.brick_chimney_top);
	}
}