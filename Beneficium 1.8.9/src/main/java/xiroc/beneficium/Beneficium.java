package xiroc.beneficium;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import xiroc.beneficium.item.ItemTalismanOfTheBenefactor;
import xiroc.beneficium.proxy.ServerProxy;
import xiroc.beneficium.util.EventManager;

@Mod(modid = "beneficium", name = "Beneficium", version = "0.1.0", acceptedMinecraftVersions = "1.8, 1.8.1, 1.8.2, 1.8.3, 1.8.4, 1.8.5, 1.8.6, 1.8.7, 1.8.8, 1.8.9")
public class Beneficium {

	@SidedProxy(serverSide = "xiroc.beneficium.proxy.ServerProxy", clientSide = "xiroc.beneficium.proxy.ClientProxy")
	public static ServerProxy proxy;
	
	private static Configuration config;

	public static final Logger logger = LogManager.getLogger("Beneficium");
	public static float XP_MULTIPLIER;
	public static float XP_MULTIPLIER_DIVINE;

	public static final Item talismanOfTheBenefactor = new ItemTalismanOfTheBenefactor();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModMetadata data = event.getModMetadata();
		data.autogenerated = false;
		data.name = EnumChatFormatting.GOLD + "Beneficium";
		data.authorList.add("XIROC");
		data.version = "0.1.0";
		data.url = "coming soon";
		data.description = "";
		MinecraftForge.EVENT_BUS.register(new EventManager());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		config = new Configuration(new File(Loader.instance().getConfigDir(), "Beneficium.cfg"));
		config.load();
		XP_MULTIPLIER = config.getFloat("XP_MULTIPLIER_BASE", "xp_multipliers", 5.0F, 1.0F, 8192F, "The xp multiplier for the basic talisman.");
		XP_MULTIPLIER_DIVINE = config.getFloat("XP_MULTIPLIER_DIVINE", "xp_multipliers", 15.0F, 1.0F, 8192F	, "The xp multiplier for the divine talisman.");
		GameRegistry.registerItem(talismanOfTheBenefactor, "talisman");
		GameRegistry.addRecipe(new ItemStack(talismanOfTheBenefactor), "gdg", "dbd", "gdg", 'g', Items.gold_ingot, 'd',
				Items.diamond, 'b', Items.emerald);
		if(config.hasChanged()) config.save();
		proxy.load();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {}

}