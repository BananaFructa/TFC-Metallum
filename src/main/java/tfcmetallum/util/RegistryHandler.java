package tfcmetallum.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryModifiable;

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.IForgeable;
import net.dries007.tfc.api.capability.forge.IForgeableMeasurableMetal;
import net.dries007.tfc.api.recipes.AlloyRecipe;
import net.dries007.tfc.api.recipes.BlastFurnaceRecipe;
import net.dries007.tfc.api.recipes.BloomeryRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.ItemType;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockMetalLamp;
import net.dries007.tfc.objects.items.metal.ItemMetal;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.fuel.FuelManager;
import tfcmetallum.ConfigTFCM;
import tfcmetallum.TFCMetallum;
import tfcmetallum.objects.ArmorMaterialsTFCM;
import tfcmetallum.objects.ToolMaterialsTFCM;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.Metal.ItemType.INGOT;
import static net.dries007.tfc.types.DefaultMetals.*;
import static net.dries007.tfc.util.forge.ForgeRule.*;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = TFCMetallum.MODID)
public final class RegistryHandler
{
    //Metals
    public static final ResourceLocation ALUMINIUM = new ResourceLocation(MOD_ID, "aluminium");
    public static final ResourceLocation CONSTANTAN = new ResourceLocation(MOD_ID, "constantan");
    public static final ResourceLocation ELECTRUM = new ResourceLocation(MOD_ID, "electrum");
    public static final ResourceLocation TITANIUM = new ResourceLocation(MOD_ID, "titanium");

    /*public static final ResourceLocation ALUMINIUM_BRASS = new ResourceLocation(MOD_ID, "aluminium_brass");
    public static final ResourceLocation ANTIMONY = new ResourceLocation(MOD_ID, "antimony");
    public static final ResourceLocation ARDITE = new ResourceLocation(MOD_ID, "ardite");
    public static final ResourceLocation COBALT = new ResourceLocation(MOD_ID, "cobalt");
    public static final ResourceLocation CONSTANTAN = new ResourceLocation(MOD_ID, "constantan");
    public static final ResourceLocation ELECTRUM = new ResourceLocation(MOD_ID, "electrum");
    public static final ResourceLocation INVAR = new ResourceLocation(MOD_ID, "invar");
    public static final ResourceLocation MANYULLYN = new ResourceLocation(MOD_ID, "manyullyn");
    public static final ResourceLocation MITHRIL = new ResourceLocation(MOD_ID, "mithril");
    public static final ResourceLocation OSMIUM = new ResourceLocation(MOD_ID, "osmium");
    public static final ResourceLocation TITANIUM = new ResourceLocation(MOD_ID, "titanium");
    public static final ResourceLocation TUNGSTEN = new ResourceLocation(MOD_ID, "tungsten");
    public static final ResourceLocation TUNGSTEN_STEEL = new ResourceLocation(MOD_ID, "tungsten_steel");
    public static final ResourceLocation NICKEL_SILVER = new ResourceLocation(MOD_ID, "nickel_silver"); // Copper + zinc + nickel
    public static final ResourceLocation RED_ALLOY = new ResourceLocation(MOD_ID, "red_alloy"); // Copper + redstone (although not obtainable with just TFC + metallum
    public static final ResourceLocation BORON = new ResourceLocation(MOD_ID, "boron");
    public static final ResourceLocation FERROBORON = new ResourceLocation(MOD_ID, "ferroboron"); // steel + boron
    public static final ResourceLocation THORIUM = new ResourceLocation(MOD_ID, "thorium");
    public static final ResourceLocation LITHIUM = new ResourceLocation(MOD_ID, "lithium");
    public static final ResourceLocation MANGANESE = new ResourceLocation(MOD_ID, "manganese");
    public static final ResourceLocation MAGNESIUM = new ResourceLocation(MOD_ID, "magnesium");
    public static final ResourceLocation BERYLLIUM = new ResourceLocation(MOD_ID, "beryllium");
    public static final ResourceLocation BERYLLIUM_COPPER = new ResourceLocation(MOD_ID, "beryllium_copper"); // Copper + beryllium + aluminium
    public static final ResourceLocation ZIRCONIUM = new ResourceLocation(MOD_ID, "zirconium");
    public static final ResourceLocation ZIRCALOY = new ResourceLocation(MOD_ID, "zircaloy"); // zirconium + tin
    public static final ResourceLocation HSLA_STEEL = new ResourceLocation(MOD_ID, "hsla_steel"); // steel + manganese
    public static final ResourceLocation MAGNESIUM_DIBORIDE = new ResourceLocation(MOD_ID, "magnesium_diboride"); // magnesium + steel
    public static final ResourceLocation TOUGH = new ResourceLocation(MOD_ID, "tough"); // Ferroboron + lithium
    public static final ResourceLocation URANIUM = new ResourceLocation(MOD_ID, "uranium");*/

    //Ores
    //public static final ResourceLocation NATIVE_ARDITE = new ResourceLocation(MOD_ID, "native_ardite");
    //public static final ResourceLocation NATIVE_OSMIUM = new ResourceLocation(MOD_ID, "native_osmium");
    public static final ResourceLocation BAUXITE = new ResourceLocation(MOD_ID, "bauxite"); // aluminium / titanium
    //public static final ResourceLocation WOLFRAMITE = new ResourceLocation(MOD_ID, "wolframite"); // tungsten
    //public static final ResourceLocation COBALTITE = new ResourceLocation(MOD_ID, "cobaltite"); // cobalt
    //public static final ResourceLocation STIBNITE = new ResourceLocation(MOD_ID, "stibnite"); // antimony
    public static final ResourceLocation RUTILE = new ResourceLocation(MOD_ID, "rutile"); // titanium
    //public static final ResourceLocation TETRABORATE = new ResourceLocation(MOD_ID, "tetraborate"); // boron
    //public static final ResourceLocation SPODUMENE = new ResourceLocation(MOD_ID, "spodumene"); // lithium
    //public static final ResourceLocation THORIANITE = new ResourceLocation(MOD_ID, "thorianite"); // thorium
    //public static final ResourceLocation PYROLUSITE = new ResourceLocation(MOD_ID, "pyrolusite"); // manganese
    //public static final ResourceLocation MAGNESITE = new ResourceLocation(MOD_ID, "magnesite"); //magnesium
    //public static final ResourceLocation BERYL = new ResourceLocation(MOD_ID, "beryl"); //beryllium
    //public static final ResourceLocation ZIRCON = new ResourceLocation(MOD_ID, "zircon"); // zirconium

    //Ore without TFC-M metals
    //public static final ResourceLocation CHROMITE = new ResourceLocation(MOD_ID, "chromite"); // chrome
    //public static final ResourceLocation VILLIAUMITE = new ResourceLocation(MOD_ID, "villiaumite");
    //public static final ResourceLocation RHODOCHROSITE = new ResourceLocation(MOD_ID, "rhodochrosite");
    //public static final ResourceLocation FLUORITE = new ResourceLocation(MOD_ID, "fluorite");
    //public static final ResourceLocation CAROBBIITE = new ResourceLocation(MOD_ID, "carobbiite");
    //public static final ResourceLocation ARSENIC = new ResourceLocation(MOD_ID, "arsenic");

    @SubscribeEvent
    public static void onPreRegisterMetal(TFCRegistryEvent.RegisterPreBlock<Metal> event)
    {
        IForgeRegistry<Metal> r = event.getRegistry();
        r.registerAll(
                new Metal(ALUMINIUM, Metal.Tier.TIER_III, true, 0.3f, 660, 0xFFD9FBFC, ToolMaterialsTFCM.ALUMINIUM, ArmorMaterialsTFCM.ALUMINIUM),
                new Metal(CONSTANTAN, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFD28874, null, null),
                new Metal(ELECTRUM, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFDFB950, null, null),
                new Metal(TITANIUM, Metal.Tier.TIER_VI, true, 0.3f, 1700, 0xFFC2C4CC, null,null)
        );

        /*r.registerAll(
                new Metal(ANTIMONY, Metal.Tier.TIER_I, true, 0.25f, 630, 0xFFE7E7F5, null, null),
                new Metal(LITHIUM, Metal.Tier.TIER_II, true, 0.25f, 630, 0xC9CBC3, null, null),
                new Metal(CONSTANTAN, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFD28874, null, null),
                new Metal(ELECTRUM, Metal.Tier.TIER_II, true, 0.5f, 1200, 0xFFDFB950, null, null),
                new Metal(NICKEL_SILVER, Metal.Tier.TIER_II, true, 0.35f, 1450, 0xFFA4A4A5, ToolMaterialsTFCM.NICKEL_SILVER, ArmorMaterialsTFCM.NICKEL_SILVER),
                new Metal(RED_ALLOY, Metal.Tier.TIER_II, true, 0.35f, 1080, 0xFFDA6E6E, null, null),
                new Metal(MITHRIL, Metal.Tier.TIER_II, true, 0.35f, 940, 0xFF8ADAF6, ToolMaterialsTFCM.MITHRIL, ArmorMaterialsTFCM.MITHRIL),
                new Metal(INVAR, Metal.Tier.TIER_III, true, 0.35f, 1450, 0xFFAAAA9E, ToolMaterialsTFCM.INVAR, ArmorMaterialsTFCM.INVAR),
                new Metal(ALUMINIUM, Metal.Tier.TIER_III, true, 0.3f, 660, 0xFFD9FBFC, ToolMaterialsTFCM.ALUMINIUM, ArmorMaterialsTFCM.ALUMINIUM),
                new Metal(ALUMINIUM_BRASS, Metal.Tier.TIER_III, true, 0.3f, 630, 0xFFDCDABE, null, null),
                new Metal(MAGNESIUM, Metal.Tier.TIER_III, true, 0.30f, 650, 0xFF978195, null, null),
                new Metal(MANGANESE, Metal.Tier.TIER_III, true, 0.29f, 1250, 0xFF9397A8, null, null),
                new Metal(BORON, Metal.Tier.TIER_III, true, 0.3f, 630, 0xFF252525, ToolMaterialsTFCM.BORON, ArmorMaterialsTFCM.BORON),
                new Metal(MAGNESIUM_DIBORIDE, Metal.Tier.TIER_III, true, 0.35f, 2000, 0xFF46391E, null, null),
                new Metal(THORIUM, Metal.Tier.TIER_III, true, 0.3f, 630, 0xFF3D4548, null, null),
                new Metal(URANIUM, Metal.Tier.TIER_III, true, 0.3f, 3000, 0xFF3A6724, null, null),
                new Metal(ARDITE, Metal.Tier.TIER_III, true, 0.3f, 1050, 0xFFF09614, null, null),
                new Metal(COBALT, Metal.Tier.TIER_III, true, 0.3f, 1495, 0xFF6CA6E5, ToolMaterialsTFCM.COBALT, ArmorMaterialsTFCM.COBALT),
                new Metal(MANYULLYN, Metal.Tier.TIER_IV, true, 0.3f, 1550, 0xFFB052C0, ToolMaterialsTFCM.MANYULLYN, ArmorMaterialsTFCM.MANYULLYN),
                new Metal(HSLA_STEEL, Metal.Tier.TIER_V, true, 0.35f, 2000, 0xFF3F4180, null, null),
                new Metal(FERROBORON, Metal.Tier.TIER_V, true, 0.3f, 3000, 0xFF4B4B4B, null, null),
                new Metal(BERYLLIUM, Metal.Tier.TIER_VI, true, 0.35f, 1300, 0xFFE4EADA, null, null),
                new Metal(BERYLLIUM_COPPER, Metal.Tier.TIER_VI, true, 0.35f, 1500, 0xFFEAAE90, ToolMaterialsTFCM.BERYLLIUM_COPPER, ArmorMaterialsTFCM.BERYLLIUM_COPPER),
                new Metal(OSMIUM, Metal.Tier.TIER_VI, true, 0.35f, 3025, 0xFFB8D8DE, ToolMaterialsTFCM.OSMIUM, ArmorMaterialsTFCM.OSMIUM),
                new Metal(TITANIUM, Metal.Tier.TIER_VI, true, 0.3f, 1700, 0xFFC2C4CC, ToolMaterialsTFCM.TITANIUM, ArmorMaterialsTFCM.TITANIUM),
                new Metal(TUNGSTEN, Metal.Tier.TIER_VI, true, 0.2f, 3400, 0xFF41454B, ToolMaterialsTFCM.TUNGSTEN, ArmorMaterialsTFCM.TUNGSTEN),
                new Metal(TUNGSTEN_STEEL, Metal.Tier.TIER_VI, true, 0.2f, 3695, 0xFF565F6E, ToolMaterialsTFCM.TUNGSTEN_STEEL, ArmorMaterialsTFCM.TUNGSTEN_STEEL),
                new Metal(ZIRCONIUM, Metal.Tier.TIER_VI, true, 0.35f, 1500, 0xFF747527, null, null),
                new Metal(ZIRCALOY, Metal.Tier.TIER_VI, true, 0.35f, 1500, 0xFF43423A, ToolMaterialsTFCM.ZIRCALOY, ArmorMaterialsTFCM.ZIRCALOY),
                new Metal(TOUGH, Metal.Tier.TIER_VI, true, 0.3f, 3000, 0xFF3F2B61, null, null)
        );*/
    }


    @SubscribeEvent
    public static void onPreRegisterOre(TFCRegistryEvent.RegisterPreBlock<Ore> event)
    {
        IForgeRegistry<Ore> r = event.getRegistry();
        r.registerAll(new Ore(BAUXITE, ALUMINIUM, false),new Ore(RUTILE, TITANIUM, false));
        /*r.registerAll(
                //  Ores which *could* be melted directly if it's temperature is met
                new Ore(STIBNITE, ANTIMONY, true),
                new Ore(SPODUMENE, LITHIUM, true),

                // Ores which we add tools, armor and textures inside TFC realm, but can't be melted directly (processing by other mods required)
                new Ore(NATIVE_ARDITE, ARDITE, false),
                new Ore(NATIVE_OSMIUM, OSMIUM, false),
                new Ore(BAUXITE, ALUMINIUM, false),
                new Ore(WOLFRAMITE, TUNGSTEN, false),
                new Ore(COBALTITE, COBALT, false),
                new Ore(RUTILE, TITANIUM, false),
                new Ore(THORIANITE, THORIUM, false),
                new Ore(PYROLUSITE, MANGANESE, false),
                new Ore(MAGNESITE, MAGNESIUM, false),
                new Ore(ZIRCON, ZIRCONIUM, false),

                // Ores without metals registered inside TFC
                new Ore(CHROMITE),
                new Ore(VILLIAUMITE),
                new Ore(RHODOCHROSITE),
                new Ore(FLUORITE),
                new Ore(CAROBBIITE),
                new Ore(ARSENIC)
        );*/
    }

    @SubscribeEvent
    public static void onRegisterAlloyRecipe(RegistryEvent.Register<AlloyRecipe> event)
    {
        IForgeRegistry<AlloyRecipe> r = event.getRegistry();
        r.registerAll(
                new AlloyRecipe.Builder(CONSTANTAN).add(COPPER, 0.4, 0.6).add(NICKEL, 0.4, 0.6).build(),
                new AlloyRecipe.Builder(ELECTRUM).add(GOLD, 0.4, 0.6).add(SILVER, 0.4, 0.6).build()
                /*new AlloyRecipe.Builder(MITHRIL).add(COPPER, 0.88, 0.92).add(ANTIMONY, 0.08, 0.12).build(),
                new AlloyRecipe.Builder(INVAR).add(WROUGHT_IRON, 0.6, 0.7).add(NICKEL, 0.3, 0.4).build(),
                new AlloyRecipe.Builder(ALUMINIUM_BRASS).add(ALUMINIUM, 0.65, 0.85).add(COPPER, 0.15, 0.35).build(),
                new AlloyRecipe.Builder(MANYULLYN).add(COBALT, 0.4, 0.6).add(ARDITE, 0.4, 0.6).build(),
                new AlloyRecipe.Builder(TUNGSTEN_STEEL).add(TUNGSTEN, 0.02, 0.18).add(STEEL, 0.72, 0.98).build(),
                new AlloyRecipe.Builder(NICKEL_SILVER).add(COPPER, 0.50, 0.65).add(ZINC, 0.1, 0.3).add(NICKEL, 0.1, 0.3).build(),
                new AlloyRecipe.Builder(FERROBORON).add(STEEL, 0.4, 0.6).add(BORON, 0.4, 0.6).build(),
                new AlloyRecipe.Builder(HSLA_STEEL).add(STEEL, 0.4, 0.6).add(MANGANESE, 0.4, 0.6).build(),
                new AlloyRecipe.Builder(MAGNESIUM_DIBORIDE).add(BORON, 0.4, 0.6).add(MAGNESIUM, 0.2, 0.4).build(),
                new AlloyRecipe.Builder(BERYLLIUM_COPPER).add(BERYLLIUM, 0.3, 0.6).add(COPPER, 0.3, 0.6).add(ALUMINIUM, 0.1, 0.3).build(),
                new AlloyRecipe.Builder(ZIRCALOY).add(ZIRCONIUM, 0.72, 0.98).add(TIN, 0.2, 0.4).build(),
                new AlloyRecipe.Builder(TOUGH).add(FERROBORON, 0.4, 0.6).add(LITHIUM, 0.4, 0.6).build()*/
        );
    }

    @SuppressWarnings("ConstantConditions")
    @SubscribeEvent
    public static void onRegisterBloomeryRecipeEvent(RegistryEvent.Register<BloomeryRecipe> event)
    {
        IForgeRegistry<BloomeryRecipe> registry = event.getRegistry();
        if (ConfigTFCM.RECIPES.aluminium)
        {
            registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(ALUMINIUM), FuelManager::isItemBloomeryFuel));
        }
        /*if (ConfigTFCM.RECIPES.ardite)
        {
            registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(ARDITE), FuelManager::isItemBloomeryFuel));
        }
        if (ConfigTFCM.RECIPES.cobalt)
        {
            registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(COBALT), FuelManager::isItemBloomeryFuel));
        }
        if (ConfigTFCM.RECIPES.thorium)
        {
            registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(THORIUM), FuelManager::isItemBloomeryFuel));
        }
        if (ConfigTFCM.RECIPES.manganese)
        {
            registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(MANGANESE), FuelManager::isItemBloomeryFuel));
        }
        if (ConfigTFCM.RECIPES.magnesium)
        {
            registry.register(new BloomeryRecipe(TFCRegistries.METALS.getValue(MAGNESIUM), FuelManager::isItemBloomeryFuel));
        }*/
    }

    @SubscribeEvent
    public static void onRegisterBlastFurnaceRecipeEvent(RegistryEvent.Register<BlastFurnaceRecipe> event)
    {
        IForgeRegistry<BlastFurnaceRecipe> registry = event.getRegistry();
        if (ConfigTFCM.RECIPES.titanium)
        {
            Metal titanium = TFCRegistries.METALS.getValue(TITANIUM);
            if (titanium != null)
            {
                registry.register(new BlastFurnaceRecipe(titanium, titanium, IIngredient.of("dustFlux")));
            }
        }
        /*if (ConfigTFCM.RECIPES.osmium)
        {
            Metal osmium = TFCRegistries.METALS.getValue(OSMIUM);
            if (osmium != null)
            {
                registry.register(new BlastFurnaceRecipe(osmium, osmium, IIngredient.of("dustFlux")));
            }
        }
        if (ConfigTFCM.RECIPES.titanium)
        {
            Metal titanium = TFCRegistries.METALS.getValue(TITANIUM);
            if (titanium != null)
            {
                registry.register(new BlastFurnaceRecipe(titanium, titanium, IIngredient.of("dustFlux")));
            }
        }
        if (ConfigTFCM.RECIPES.tungsten)
        {
            Metal tungsten = TFCRegistries.METALS.getValue(TUNGSTEN);
            if (tungsten != null)
            {
                registry.register(new BlastFurnaceRecipe(tungsten, tungsten, IIngredient.of("dustFlux")));
            }
        }
        if (ConfigTFCM.RECIPES.beryllium)
        {
            Metal beryllium = TFCRegistries.METALS.getValue(BERYLLIUM);
            if (beryllium != null)
            {
                registry.register(new BlastFurnaceRecipe(beryllium, beryllium, IIngredient.of("dustFlux")));
            }
        }
        if (ConfigTFCM.RECIPES.zirconium)
        {
            Metal zirconium = TFCRegistries.METALS.getValue(ZIRCONIUM);
            if (zirconium != null)
            {
                registry.register(new BlastFurnaceRecipe(zirconium, zirconium, IIngredient.of("dustFlux")));
            }
        }*/
    }

    @SubscribeEvent
    public static void onRegisterAnvilRecipeEvent(RegistryEvent.Register<AnvilRecipe> event)
    {
        IForgeRegistry<AnvilRecipe> r = event.getRegistry();
        if (ConfigTFCM.RECIPES.aluminium)
        {
            Metal aluminium = TFCRegistries.METALS.getValue(ALUMINIUM);
            if (aluminium != null)
            {
                r.register(new AnvilRecipe(new ResourceLocation(TFCMetallum.MODID, "aluminium_bloom"),
                        getBloomIngredient(aluminium),
                        new ItemStack(ItemMetal.get(aluminium, INGOT)),
                        Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
            }
        }
        /*if (ConfigTFCM.RECIPES.ardite)
        {
            Metal ardite = TFCRegistries.METALS.getValue(ARDITE);
            if (ardite != null)
            {
                r.register(new AnvilRecipe(new ResourceLocation(TFCMetallum.MODID, "ardite_bloom"),
                        getBloomIngredient(ardite),
                        new ItemStack(ItemMetal.get(ardite, INGOT)),
                        Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
            }
        }
        if (ConfigTFCM.RECIPES.cobalt)
        {
            Metal cobalt = TFCRegistries.METALS.getValue(COBALT);
            if (cobalt != null)
            {
                r.register(new AnvilRecipe(new ResourceLocation(TFCMetallum.MODID, "cobalt_bloom"),
                        getBloomIngredient(cobalt),
                        new ItemStack(ItemMetal.get(cobalt, INGOT)),
                        Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
            }
        }
        if (ConfigTFCM.RECIPES.thorium)
        {
            Metal thorium = TFCRegistries.METALS.getValue(THORIUM);
            if (thorium != null)
            {
                r.register(new AnvilRecipe(new ResourceLocation(TFCMetallum.MODID, "thorium_bloom"),
                        getBloomIngredient(thorium),
                        new ItemStack(ItemMetal.get(thorium, INGOT)),
                        Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
            }
        }
        if (ConfigTFCM.RECIPES.magnesium)
        {
            Metal magnesium = TFCRegistries.METALS.getValue(MAGNESIUM);
            if (magnesium != null)
            {
                r.register(new AnvilRecipe(new ResourceLocation(TFCMetallum.MODID, "magnesium_bloom"),
                        getBloomIngredient(magnesium),
                        new ItemStack(ItemMetal.get(magnesium, INGOT)),
                        Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
            }
        }
        if (ConfigTFCM.RECIPES.manganese)
        {
            Metal manganese = TFCRegistries.METALS.getValue(MANGANESE);
            if (manganese != null)
            {
                r.register(new AnvilRecipe(new ResourceLocation(TFCMetallum.MODID, "manganese_bloom"),
                        getBloomIngredient(manganese),
                        new ItemStack(ItemMetal.get(manganese, INGOT)),
                        Metal.Tier.TIER_II, null, HIT_LAST, HIT_SECOND_LAST, HIT_THIRD_LAST));
            }
        }*/
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        // Register "aluminum" ore dict (because american mod-dev people are dumb, the international standard is Alumin"i"um, period)
        // And yes, we have to run it here because some mods are dumber: instead of querying ore dictionary at run time they do after item registration.
        Metal aluminium = TFCRegistries.METALS.getValue(ALUMINIUM);
        if (aluminium != null)
        {
            for (ItemType type : ItemType.values())
            {
                Item metalItem = ItemMetal.get(aluminium, type);
                if (metalItem != null && !(metalItem instanceof ItemBlockMetalLamp))
                {
                    if (type == Metal.ItemType.DOUBLE_INGOT)
                    {
                        OreDictionary.registerOre(OreDictionaryHelper.toString("ingot", "double", "aluminum"), metalItem);
                    }
                    else if (type == Metal.ItemType.DOUBLE_SHEET)
                    {
                        OreDictionary.registerOre(OreDictionaryHelper.toString( "sheet", "double", "aluminum"), metalItem);
                    }
                    else if (!type.isToolItem())
                    {
                        OreDictionary.registerOre(OreDictionaryHelper.toString( type, "aluminum"), metalItem);
                        if (type == Metal.ItemType.SHEET && ConfigTFC.General.MISC.dictionaryPlates)
                        {
                            OreDictionary.registerOre(OreDictionaryHelper.toString( "plate", "aluminum"), metalItem);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterQuernRecipeEvent(RegistryEvent.Register<QuernRecipe> event)
    {
        /*IForgeRegistry<QuernRecipe> r = event.getRegistry();

        Metal uranium = TFCRegistries.METALS.getValue(URANIUM);
        Metal boron = TFCRegistries.METALS.getValue(BORON);
        if (uranium != null)
        {
            r.register(new QuernRecipe(IIngredient.of("gemPitchblende"), new ItemStack(ItemMetal.get(uranium, ItemType.DUST), 4)).setRegistryName("uranium_dust"));
        }
        if (boron != null)
        {
            IForgeRegistryModifiable<QuernRecipe> modRegistry = (IForgeRegistryModifiable<QuernRecipe>) TFCRegistries.QUERN;
            modRegistry.remove(new ResourceLocation(MOD_ID, "borax"));
            r.register(new QuernRecipe(IIngredient.of("gemBorax"), new ItemStack(ItemMetal.get(boron, ItemType.DUST), 4)).setRegistryName("boron_dust"));
        }

        r.register(new QuernRecipe(IIngredient.of("gemFluorite"), new ItemStack(ItemPowder.get(Powder.FLUX), 6)).setRegistryName("fluorite_flux"));*/
    }

    private static IIngredient<ItemStack> getBloomIngredient(Metal metal)
    {
        return x ->
        {
            if (x.getItem() == ItemsTFC.REFINED_BLOOM)
            {
                IForgeable cap = x.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
                if (cap instanceof IForgeableMeasurableMetal)
                {
                    return ((IForgeableMeasurableMetal) cap).getMetal() == metal && ((IForgeableMeasurableMetal) cap).getMetalAmount() == 100;
                }
            }
            return false;
        };
    }
}
