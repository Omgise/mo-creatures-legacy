package drzhark.mocreatures;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import drzhark.mocreatures.achievements.MoCAchievements;
import drzhark.mocreatures.block.MoCBlockDirt;
import drzhark.mocreatures.block.MoCBlockGrass;
import drzhark.mocreatures.block.MoCBlockLeaves;
import drzhark.mocreatures.block.MoCBlockLog;
import drzhark.mocreatures.block.MoCBlockPlanks;
import drzhark.mocreatures.block.MoCBlockStone;
import drzhark.mocreatures.block.MoCBlockTallGrass;
import drzhark.mocreatures.client.MoCClientTickHandler;
import drzhark.mocreatures.client.MoCCreativeTabs;
import drzhark.mocreatures.client.events.MoCClientWitcheryPlayerWolfAndWerewolfReplacement;
import drzhark.mocreatures.client.events.MoCRenderHorseJumpBarEvent;
import drzhark.mocreatures.client.handlers.MoCKeyHandler;
import drzhark.mocreatures.command.CommandMoCPets;
import drzhark.mocreatures.command.CommandMoCSpawn;
import drzhark.mocreatures.command.CommandMoCTP;
import drzhark.mocreatures.dimension.BiomeGenWyvernLair;
import drzhark.mocreatures.dimension.WorldProviderWyvernEnd;
import drzhark.mocreatures.entity.ambient.MoCEntityAnt;
import drzhark.mocreatures.entity.ambient.MoCEntityBee;
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import drzhark.mocreatures.entity.ambient.MoCEntityCrab;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;
import drzhark.mocreatures.entity.ambient.MoCEntityFly;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import drzhark.mocreatures.entity.ambient.MoCEntityRoach;
import drzhark.mocreatures.entity.ambient.MoCEntitySnail;
import drzhark.mocreatures.entity.animal.MoCEntityBear;
import drzhark.mocreatures.entity.animal.MoCEntityBigCat;
import drzhark.mocreatures.entity.animal.MoCEntityBird;
import drzhark.mocreatures.entity.animal.MoCEntityBoar;
import drzhark.mocreatures.entity.animal.MoCEntityBunny;
import drzhark.mocreatures.entity.animal.MoCEntityCrocodile;
import drzhark.mocreatures.entity.animal.MoCEntityDeer;
import drzhark.mocreatures.entity.animal.MoCEntityDuck;
import drzhark.mocreatures.entity.animal.MoCEntityElephant;
import drzhark.mocreatures.entity.animal.MoCEntityEnt;
import drzhark.mocreatures.entity.animal.MoCEntityFox;
import drzhark.mocreatures.entity.animal.MoCEntityGoat;
import drzhark.mocreatures.entity.animal.MoCEntityHorse;
import drzhark.mocreatures.entity.animal.MoCEntityKitty;
import drzhark.mocreatures.entity.animal.MoCEntityKomodoDragon;
import drzhark.mocreatures.entity.animal.MoCEntityMole;
import drzhark.mocreatures.entity.animal.MoCEntityMouse;
import drzhark.mocreatures.entity.animal.MoCEntityOstrich;
import drzhark.mocreatures.entity.animal.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.animal.MoCEntityRaccoon;
import drzhark.mocreatures.entity.animal.MoCEntitySnake;
import drzhark.mocreatures.entity.animal.MoCEntityTurkey;
import drzhark.mocreatures.entity.animal.MoCEntityTurtle;
import drzhark.mocreatures.entity.animal.MoCEntityWyvern;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityPiranha;
import drzhark.mocreatures.entity.aquatic.MoCEntityRay;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import drzhark.mocreatures.entity.aquatic.MoCEntitySmallFish;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityFishBowl;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityMammothPlatform;
import drzhark.mocreatures.entity.item.MoCEntityThrowableBlockForGolem;
import drzhark.mocreatures.entity.monster.MoCEntityBigGolem;
import drzhark.mocreatures.entity.monster.MoCEntityFlameWraith;
import drzhark.mocreatures.entity.monster.MoCEntityHellRat;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import drzhark.mocreatures.entity.monster.MoCEntityMiniGolem;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.entity.monster.MoCEntityRat;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;
import drzhark.mocreatures.entity.monster.MoCEntityWWolf;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import drzhark.mocreatures.entity.witchery_integration.MoCEntityWerewolfMinecraftComesAliveVillagerWitchery;
import drzhark.mocreatures.entity.witchery_integration.MoCEntityWerewolfVillagerWitchery;
import drzhark.mocreatures.entity.witchery_integration.MoCEntityWerewolfWitchery;
import drzhark.mocreatures.item.ItemBuilderHammer;
import drzhark.mocreatures.item.ItemStaffPortal;
import drzhark.mocreatures.item.ItemStaffTeleport;
import drzhark.mocreatures.item.MoCItem;
import drzhark.mocreatures.item.MoCItemArmor;
import drzhark.mocreatures.item.MoCItemEgg;
import drzhark.mocreatures.item.MoCItemFishBowl;
import drzhark.mocreatures.item.MoCItemFood;
import drzhark.mocreatures.item.MoCItemHayStack;
import drzhark.mocreatures.item.MoCItemHorseAmulet;
import drzhark.mocreatures.item.MoCItemHorseSaddle;
import drzhark.mocreatures.item.MoCItemKittyBed;
import drzhark.mocreatures.item.MoCItemLitterBox;
import drzhark.mocreatures.item.MoCItemPetAmulet;
import drzhark.mocreatures.item.MoCItemRecord;
import drzhark.mocreatures.item.MoCItemSpawnEgg;
import drzhark.mocreatures.item.MoCItemSugarLump;
import drzhark.mocreatures.item.MoCItemTurtleSoup;
import drzhark.mocreatures.item.MoCItemWeapon;
import drzhark.mocreatures.item.MoCItemWhip;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.utils.MoCLog;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.StatCollector;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.ShapelessOreRecipe;

@Mod(modid = "MoCreatures", name = "Mo' Creatures Legacy", version = "1.2")
public class MoCreatures {

    @Instance("MoCreatures")
    public static MoCreatures instance;

    @SidedProxy(clientSide = "drzhark.mocreatures.client.MoCClientProxy", serverSide = "drzhark.mocreatures.MoCProxy")
    public static MoCProxy proxy;
    public static final CreativeTabs MOC_CREATIVE_TAB = new MoCCreativeTabs(CreativeTabs.creativeTabArray.length, "MoCreaturesTab");
    public MoCPetMapData mapData;
    
    
    
    
    public static boolean isBiomesOPlentyLoaded;
    
    public static boolean isEtFuturumRequiemLoaded;
    
    public static boolean isExoticBirdsLoaded;
    
    public static boolean isFoodExpansionLoaded;
    
    public static boolean isGregTech6Loaded;
    
    public static boolean isImprovingMinecraftLoaded;
    
    public static boolean isJustAnotherSpawnerLoaded;
    
    public static boolean isLotsOfFoodLoaded;
    
    public static boolean isMinecraftComesAliveLoaded;
    
    public static boolean isMobConfinementLoaded;
    
    public static boolean isMutantCreaturesLoaded;
    
    public static boolean isNovacraftLoaded;
    
    public static boolean isPalmsHarvestLoaded;
    
    private static boolean isThaumcraftLoaded;
    
    public static boolean isTwilightForestLoaded;
    
    public static boolean isWitcheryLoaded;
    
    public static boolean isBalkansWeaponsModLoaded;

    
    
    public static final GameProfile MOC_FAKE_PLAYER = new GameProfile(UUID.fromString("6E379B45-1111-2222-3333-2FE1A88BCD66"), "[MoCreatures]");

    /**
     * ITEMS
     */
    static int mocEntityID = 7256; // used internally, does not need to be configured by users
    public static int wyvernLairDimensionID; //17;

    public static Block mocStone;
    public static Block mocGrass;
    public static Block mocDirt;
    public static Block mocLeaf;
    public static Block mocLog;
    public static Block mocPlank;
    public static Block mocTallGrass;

    public static ArrayList<String> multiBlockNames = new ArrayList<String>();
    public static BiomeGenBase WyvernLairBiome;
    public static Item staffPortal;
    public static Item staffTeleport;
    public static Item builderHammer;

    static final ArmorMaterial CROC_ARMOR = EnumHelper.addArmorMaterial("crocARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static final ArmorMaterial FUR_ARMOR = EnumHelper.addArmorMaterial("furARMOR", 15, new int[] { 1, 3, 2, 1 }, 12);
    static final ArmorMaterial HIDE_ARMOR = EnumHelper.addArmorMaterial("hideARMOR", 15, new int[] { 1, 3, 2, 1 }, 12);
    static final ArmorMaterial SCORP_DIRT_ARMOR = EnumHelper.addArmorMaterial("scorpDirtARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static final ArmorMaterial SCORP_FROST_ARMOR = EnumHelper.addArmorMaterial("scorpFrostARMOR", 18, new int[] { 2, 7, 6, 2 }, 12);
    static final ArmorMaterial SCORP_NETHER_ARMOR = EnumHelper.addArmorMaterial("scorpNetherARMOR", 20, new int[] { 3, 7, 6, 3 }, 15);
    static final ArmorMaterial SCORP_CAVE_ARMOR = EnumHelper.addArmorMaterial("scorpCaveARMOR", 15, new int[] { 2, 6, 5, 2 }, 12);
    static final ArmorMaterial SILVER_ARMOR = EnumHelper.addArmorMaterial("silverARMOR", 15, new int[] { 2, 6, 5, 2 }, 15);
    
    static final ToolMaterial SILVER_WEAPON = EnumHelper.addToolMaterial("SILVER", 0, 250, 6.0F, 4, 15);
    static final ToolMaterial SCORP_WEAPON = EnumHelper.addToolMaterial("scorpWeapon", 0, 250, 6.0F, 2.0F, 14);
    static final ToolMaterial SHARK_WEAPON = EnumHelper.addToolMaterial("sharkWeapon", 0, 250, 6.0F, 2.0F, 14);
    
    public static Item mocegg;
    
    public static Item bigcatClaw;
    public static Item whip;
    
    public static Item medallion;
    public static Item kittybed;
    public static Item litterbox;
    public static Item woolball;
    public static Item petFood;
    
    
    public static Item hideReptile;
    public static Item plateReptile;
    public static Item helmetReptile;
    public static Item legsReptile;
    public static Item bootsReptile;
    
    public static Item fishbowlEmpty;
    public static Item fishbowlWater;
    public static Item fishbowlFishy1;
    public static Item fishbowlFishy2;
    public static Item fishbowlFishy3;
    public static Item fishbowlFishy4;
    public static Item fishbowlFishy5;
    public static Item fishbowlFishy6;
    public static Item fishbowlFishy7;
    public static Item fishbowlFishy8;
    public static Item fishbowlFishy9;
    public static Item fishbowlFishy10;

    public static Item fur;
    public static Item helmetFur;
    public static Item chestFur;
    public static Item legsFur;
    public static Item bootsFur;

    public static Item nunchaku;
    public static Item sai;
    public static Item bo;
    public static Item katana;
    public static Item sharkSword;
    public static Item silverSword;
    
    public static Item essenceDarkness;
    public static Item essenceFire;
    public static Item essenceUndead;
    public static Item essenceLight;
    
    public static Item amuletBone;
    public static Item amuletBoneFull;
    public static Item amuletGhost;
    public static Item amuletGhostFull;
    public static Item amuletFairy;
    public static Item amuletFairyFull;
    public static Item amuletPegasus;
    public static Item amuletPegasusFull;
    

    public static Item heartDarkness;
    public static Item heartFire;
    public static Item heartundead;
    
    public static Item omelet;
    public static Item turtleRaw;
    public static Item turtleSoup;
    public static Item ostrichRaw;
    public static Item ostrichCooked;
    public static Item turkeyRaw;
    public static Item turkeyCooked;
    public static Item ratRaw;
    public static Item ratCooked;
    public static Item ratBurger;
    public static Item crabRaw;
    public static Item crabCooked;
    
    public static Item unicornHorn;
    
    public static Item staffUnicorn;
    public static Item staffDiamond;
    public static Item staff;
    public static Item staffEnder;
    
    public static Item recordShuffle;

    public static Item hide;
    public static Item chestHide;
    public static Item helmetHide;
    public static Item legsHide;
    public static Item bootsHide;
    

    public static Item chitin;
    public static Item chitinCave;
    public static Item chitinFrost;
    public static Item chitinNether;

    public static Item scorpSwordDirt;
    public static Item scorpSwordCave;
    public static Item scorpSwordFrost;
    public static Item scorpSwordNether;

    public static Item scorpPlateDirt;
    public static Item scorpHelmetDirt;
    public static Item scorpLegsDirt;
    public static Item scorpBootsDirt;
    public static Item scorpPlateFrost;
    public static Item scorpHelmetFrost;
    public static Item scorpLegsFrost;
    public static Item scorpBootsFrost;
    public static Item scorpPlateNether;
    public static Item scorpHelmetNether;
    public static Item scorpLegsNether;
    public static Item scorpBootsNether;
    public static Item scorpHelmetCave;
    public static Item scorpPlateCave;
    public static Item scorpLegsCave;
    public static Item scorpBootsCave;

    public static Item scorpStingDirt;
    public static Item scorpStingFrost;
    public static Item scorpStingCave;
    public static Item scorpStingNether;

    
    public static Item tusksWood;
    public static Item tusksIron;
    public static Item tusksDiamond;
    public static Item elephantChest;
    public static Item elephantGarment;
    public static Item elephantHarness;
    public static Item elephantHowdah;
    public static Item mammothPlatform;
    
    public static Item scrollFreedom;
    public static Item scrollOfSale;
    public static Item scrollOfOwner;

    public static Item sharkTeeth;
    public static Item fishNet;
    
    public static Item craftedSaddle;
    public static Item haystack;
    public static Item horseArmorCrystal;
    public static Item sugarLump;
    public static Item key;
    public static Item petAmulet;
    
    
    public static Item achievementIconKillWraith;
    public static Item achievementIconKillOgre;
    public static Item achievementIconKillWerewolf;
    public static Item achievementIconKillBigGolem;
    
    public static Item achievementIconBatHorse;
    public static Item achievementIconDarkPegasus;
    public static Item achievementIconFairyHorse;
    public static Item achievementIconNightmareHorse;
    public static Item achievementIconGhostHorse;
    public static Item achievementIconPegasus;
    public static Item achievementIconTier2Horse;
    public static Item achievementIconTier3Horse;
    public static Item achievementIconTier4Horse;
    public static Item achievementIconUndeadHorse;
    public static Item achievementIconUnicorn;
    public static Item achievementIconZebra;
    public static Item achievementIconZorse;
    public static Item achievementIconZonkey;
    
    public static Item achievementIconIndiana;
    public static Item achievementIconTameBigCat;
    public static Item achievementIconTameKitty;
    public static Item achievementIconKittyBed;
    public static Item achievementIconBreedKitty;
    
    
    public static Item achievementIconTameBird;
    public static Item achievementIconFeedSnakeWithLiveMouse;
    public static Item achievementIconTamePanda;
    public static Item achievementIconTameScorpion;
    
    public static Item achievementIconOstrichHelmet;
    public static Item achievementIconOstrichChest;
    public static Item achievementIconOstrichFlag;
    public static Item achievementIconWyvernOstrich;
    public static Item achievementIconNetherOstrich;
    public static Item achievementIconUndeadOstrich;
    public static Item achievementIconUnihornOstrich;
    
    public static Item achievementIconTameDolphin;
    
    public static Item spawnEgg;
	
	static int entitySpawnEggSubId = mocEntityID;
	
	public static HashMap entityEggs = new LinkedHashMap();
    

    public static MoCPlayerTracker tracker;
    public static Map<String, MoCEntityData> mocEntityMap = new TreeMap<String, MoCEntityData>(String.CASE_INSENSITIVE_ORDER);
    public static Map<Class<? extends EntityLiving>, MoCEntityData> entityMap = new HashMap<Class<? extends EntityLiving>, MoCEntityData>();
    public static Map<Integer, Class<? extends EntityLiving>> instaSpawnerMap = new HashMap<Integer, Class<? extends EntityLiving>>();
    public static List<String> listOfModsSupportedForBiomeSpawningIntegration = new ArrayList<String>();
    public static final String CATEGORY_ITEM_IDS = "item-ids";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {   
        isBiomesOPlentyLoaded = Loader.isModLoaded("BiomesOPlenty");
        
        isEtFuturumRequiemLoaded = Loader.isModLoaded("etfuturum");
        
        isExoticBirdsLoaded = Loader.isModLoaded("exoticbirds");
        
        isFoodExpansionLoaded = GameRegistry.findItem("FoodExpansion", "ItemHorseMeat") != null; //have to use this method over the normal way to detect the Food Expansion mod since it's mod ID is not properly registered
        
        isGregTech6Loaded = Loader.isModLoaded("gregtech");
        
        isImprovingMinecraftLoaded = Loader.isModLoaded("imc");
        
        isJustAnotherSpawnerLoaded = Loader.isModLoaded("JustAnotherSpawner");
        
        isLotsOfFoodLoaded = Loader.isModLoaded("LotsOfFood");
        
        isMinecraftComesAliveLoaded = Loader.isModLoaded("MCA");
        
        isMobConfinementLoaded = Loader.isModLoaded("ayamitsu.mobconfinement");
        
        isMutantCreaturesLoaded = Loader.isModLoaded("MutantCreatures");
        
        isNovacraftLoaded = Loader.isModLoaded("nova_craft");
        
        isPalmsHarvestLoaded = Loader.isModLoaded("harvestcraft");
        
        isThaumcraftLoaded = Loader.isModLoaded("Thaumcraft");

        isTwilightForestLoaded = Loader.isModLoaded("TwilightForest");
        
        if (isThaumcraftLoaded) {MoCThaumcraftAspects.addThaumcraftAspects();};   
        
        isWitcheryLoaded = Loader.isModLoaded("witchery");
        
        if (isWitcheryLoaded && proxy.generateWolfAltarReplacementMod)
        {
        	String modName = "/wolf-altar-replacement-1.0.jar";
        	
        	InputStream originalFileInputStream = getClass().getResourceAsStream(modName);
        	
        	String modsFolderDirectory = event.getModConfigurationDirectory().getAbsolutePath() + "/../mods/";
        	
        	String targetAbsolutePathForCopiedFile = modsFolderDirectory + modName;
        	
        	copyFile(originalFileInputStream, targetAbsolutePathForCopiedFile);
        	
        	proxy.mocSettingsConfig.get(MoCProxy.CATEGORY_MOC_MOD_INTEGRATION_SETTINGS, "generateWolfAltarReplacementMod").set(false);
        	
        	proxy.mocSettingsConfig.save();
        }
        
        isBalkansWeaponsModLoaded = Loader.isModLoaded("weaponmod");
        
        
        
        
        MoCMessageHandler.init();
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        proxy.ConfigInit(event);
        proxy.initTextures();
        InitItems();
        AddRecipes();
        proxy.registerRenderers();
        proxy.registerRenderInformation();
        if (!isServer())
        {
            FMLCommonHandler.instance().bus().register(new MoCClientTickHandler());
            FMLCommonHandler.instance().bus().register(new MoCKeyHandler());
            MinecraftForge.EVENT_BUS.register(new MoCClientWitcheryPlayerWolfAndWerewolfReplacement());
            MinecraftForge.EVENT_BUS.register(new MoCRenderHorseJumpBarEvent(Minecraft.getMinecraft()));
        }
        FMLCommonHandler.instance().bus().register(new MoCPlayerTracker());
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        DimensionManager.registerProviderType(wyvernLairDimensionID, WorldProviderWyvernEnd.class, true);
        
        MoCAchievements.initilization();     
    }
    
    public static boolean copyFile(InputStream source , String destination) {
        boolean succeess = true;

        System.out.println("Copying ->" + source + "\n\tto ->" + destination);

        try
        {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        }
        catch (IOException ex)
        {
        	System.out.println("[Mo' Creatures]: ERROR - Couldn't create file " + "'" + destination + "'");
            succeess = false;
        }

        return succeess;

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        DimensionManager.registerDimension(wyvernLairDimensionID, wyvernLairDimensionID);
        // ***MUST REGISTER BIOMES AT THIS POINT TO MAKE SURE OUR ENTITIES GET ALL BIOMES FROM DICTIONARY****
        WyvernLairBiome = new BiomeGenWyvernLair(proxy.wyvernBiomeID);
        
        listOfModsSupportedForBiomeSpawningIntegration.add("minecraft");
        listOfModsSupportedForBiomeSpawningIntegration.add("biomesop");
        listOfModsSupportedForBiomeSpawningIntegration.add("extrabiomes");
        listOfModsSupportedForBiomeSpawningIntegration.add("highlands");
        listOfModsSupportedForBiomeSpawningIntegration.add("ted80"); //Realistic World Gen (Original - ted80 fork)
        listOfModsSupportedForBiomeSpawningIntegration.add("etfuturum");
        listOfModsSupportedForBiomeSpawningIntegration.add("Netherlicious");
        listOfModsSupportedForBiomeSpawningIntegration.add("rwg.biomes"); //Realistic World Gen (GregTech New Horizons Fork)
        
        registerEntities();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event)
    {
        proxy.initGUI();
        event.registerServerCommand(new CommandMoCTP());
        event.registerServerCommand(new CommandMoCPets());
        event.registerServerCommand(new CommandMoCSpawn());
    }

    @EventHandler
    public void serverStarted(FMLServerStartedEvent event)
    {
    }

    public void registerEntities()
    {
        registerEntityAndSpawnEgg(MoCEntityBunny.class, "Bunny", 12623485, 9141102);//, 0x05600, 0x006500);
        registerEntityAndSpawnEgg(MoCEntitySnake.class, "Snake", 14020607, 13749760);//, 0x05800, 0x006800);
        registerEntityAndSpawnEgg(MoCEntityTurtle.class, "Turtle", 14772545, 9320590);//, 0x04800, 0x004500);
        registerEntityAndSpawnEgg(MoCEntityBird.class, "Bird", 14020607, 14020607);// 0x03600, 0x003500);
        registerEntityAndSpawnEgg(MoCEntityMouse.class, "Mouse", 14772545, 0);//, 0x02600, 0x002500);
        registerEntityAndSpawnEgg(MoCEntityTurkey.class, "Turkey", 14020607, 16711680);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityHorse.class, "Horse", 12623485, 15656192);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityHorseMob.class, "HorseMob", 16711680, 9320590);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityOgre.class, "Ogre", 16711680, 65407);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityBoar.class, "Boar", 14772545, 9141102);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityBear.class, "Bear", 14772545, 1);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityDuck.class, "Duck", 14772545, 15656192);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityBigCat.class, "BigCat", 12623485, 16622);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityDeer.class, "Deer", 14772545, 33023);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityWWolf.class, "WWolf", 16711680, 13749760);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityWraith.class, "Wraith", 16711680, 0);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityFlameWraith.class, "FlameWraith", 16711680, 12623485);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityFox.class, "Fox", 14772545, 5253242);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityWerewolf.class, "Werewolf", 16711680, 7434694);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityShark.class, "Shark", 33023, 9013643);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityDolphin.class, "Dolphin", 33023, 15631086);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityFishy.class, "Fishy", 33023, 65407);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityKitty.class, "Kitty", 12623485, 5253242);//, 0x2600, 0x052500);
        registerEntity(MoCEntityKittyBed.class, "KittyBed");
        registerEntity(MoCEntityLitterBox.class, "LitterBox");
        registerEntityAndSpawnEgg(MoCEntityRat.class, "Rat", 12623485, 9141102);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityHellRat.class, "HellRat", 16711680, 14772545);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityScorpion.class, "Scorpion", 16711680, 6053069);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityCrocodile.class, "Crocodile", 16711680, 65407);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityRay.class, "Ray", 33023, 9141102);//14772545, 9141102);
        registerEntityAndSpawnEgg(MoCEntityJellyFish.class, "JellyFish", 33023, 14772545);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityGoat.class, "Goat", 7434694, 6053069);//, 0x2600, 0x052500);
        registerEntity(MoCEntityEgg.class, "Egg");//, 0x2600, 0x052500);
        registerEntity(MoCEntityFishBowl.class, "FishBowl");//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityOstrich.class, "Ostrich", 14020607, 9639167);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityBee.class, "Bee", 65407, 15656192);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityFly.class, "Fly", 65407, 1);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityDragonfly.class, "Dragonfly", 65407, 14020607);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityFirefly.class, "Firefly", 65407, 9320590);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityCricket.class, "Cricket", 65407, 16622);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntitySnail.class, "Snail", 65407, 14772545);//, 0x2600, 0x052500);
        registerEntityAndSpawnEgg(MoCEntityButterfly.class, "Butterfly", 65407, 7434694);//, 0x22600, 0x012500);
        registerEntity(MoCEntityThrowableBlockForGolem.class, "ThrowableBlockForGolem");
        registerEntityAndSpawnEgg(MoCEntityBigGolem.class, "BigGolem", 16711680, 16622);
        registerEntity(MoCEntityPetScorpion.class, "PetScorpion");
        
        
        
        registerEntity(MoCEntityMammothPlatform.class, "MoCPlatform");
        registerEntityAndSpawnEgg(MoCEntityElephant.class, "Elephant", 14772545, 23423);
        registerEntityAndSpawnEgg(MoCEntityKomodoDragon.class, "KomodoDragon", 16711680, 23423);
        registerEntityAndSpawnEgg(MoCEntityWyvern.class, "Wyvern", 14772545, 65407);
        registerEntityAndSpawnEgg(MoCEntityRoach.class, "Roach", 65407, 13749760);
        registerEntityAndSpawnEgg(MoCEntityMaggot.class, "Maggot", 65407, 9141102);
        registerEntityAndSpawnEgg(MoCEntityCrab.class, "Crab", 65407, 13749760);
        registerEntityAndSpawnEgg(MoCEntityRaccoon.class, "Raccoon", 14772545, 13749760);
        registerEntityAndSpawnEgg(MoCEntityMiniGolem.class, "MiniGolem", 16711680, 13749760);
        registerEntityAndSpawnEgg(MoCEntitySilverSkeleton.class, "SilverSkeleton", 16711680, 33023);
        registerEntityAndSpawnEgg(MoCEntityAnt.class, "Ant", 65407, 12623485);
        registerEntityAndSpawnEgg(MoCEntityMediumFish.class, "MediumFish", 33023, 16622);
        registerEntityAndSpawnEgg(MoCEntitySmallFish.class, "SmallFish", 33023, 65407);
        registerEntityAndSpawnEgg(MoCEntityPiranha.class, "Piranha", 33023, 16711680);
        registerEntityAndSpawnEgg(MoCEntityMole.class, "Mole", 14020607, 16711680);
        
        if (proxy.enableEnts)
        {
        	registerEntityAndSpawnEgg(MoCEntityEnt.class, "Ent", 12623485, 16711680);
        }
        
        int entityIdForRegisteringVanillaExtensionUnderVanillaMinecraftLabel = 117;  //from testing popular mods, the free entity ids appear to be 117, 118
        
        
    	if (isWitcheryLoaded && proxy.replaceWitcheryWerewolfEntities)
        {
        	registerEntity(MoCEntityWerewolfWitchery.class, "WerewolfWitchery");
            
        	if (isMinecraftComesAliveLoaded && proxy.useHumanModelAndMCAVillagerTexturesForWitcheryHumanWerewolfEntities)
        	{
        		
        		if (proxy.tryToRegisterVanillaExtensionsUnderVanillaMinecraftLabel)
            	{
            		EntityList.addMapping(MoCEntityWerewolfMinecraftComesAliveVillagerWitchery.class, "WerewolfMinecraftComesAliveVillagerWitchery", entityIdForRegisteringVanillaExtensionUnderVanillaMinecraftLabel);
            	}
            	else {registerEntity(MoCEntityWerewolfMinecraftComesAliveVillagerWitchery.class, "WerewolfMinecraftComesAliveVillagerWitchery");}
        	}
        	
        	
        	if (proxy.tryToRegisterVanillaExtensionsUnderVanillaMinecraftLabel)
        	{
        		EntityList.addMapping(MoCEntityWerewolfVillagerWitchery.class, "WerewolfVillagerWitchery", entityIdForRegisteringVanillaExtensionUnderVanillaMinecraftLabel++);
        	}
        	else {registerEntity(MoCEntityWerewolfVillagerWitchery.class, "WerewolfVillagerWitchery");}
        }
        
        
        
        /**
         * NAMES OF COLORS USED AND THIER DECIMAL COLOUR VALUES
         * 
         * fucsia  - 16711680
         * orange curuba  - 14772545
         * gris claro  - 9141102
         * gris medio  - 9013643
         * rosado  - 15631086
         * rosado claro  - 12623485
         * azul oscuro  - 2037680
         * azul mas oscuro - 205
         * amarillo - 15656192
         * marron claro - 13749760
         * verde claro esmeralda - 65407
         * azul oscuro - 30091 
         * azul oscuro 2  - 2372490
         * blanco azulado  - 14020607
         * azul oscuro  - 16622
         * marron claro rosado - 12623485
         * azul bse huevos acuaticos - 5665535
         * azul brillane - 33023
         * morado fucsia - 9320590
         * lila - 7434694
         * morado lila - 6053069
         */
    	
    	
    	/**
    	* REGISTER ENTITY SPAWNS
    	* ======================
    	*/
    	
        // ambients
        mocEntityMap.put("Ant", new MoCEntityData("Ant", 4, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityAnt.class, 2, 1, 4), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));
        mocEntityMap.put("Bee", new MoCEntityData("Bee", 3, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityBee.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE))));
        mocEntityMap.put("Butterfly", new MoCEntityData("Butterfly", 3, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityButterfly.class, 2, 1, 3), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE))));
        mocEntityMap.put("Crab", new MoCEntityData("Crab", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityCrab.class, 4, 1, 2), new ArrayList(Arrays.asList(Type.BEACH))));
        mocEntityMap.put("Cricket", new MoCEntityData("Cricket", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityCricket.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.PLAINS, Type.SAVANNA))));
        mocEntityMap.put("Dragonfly", new MoCEntityData("Dragonfly", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityDragonfly.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.RIVER, Type.SWAMP))));
        mocEntityMap.put("Firefly", new MoCEntityData("Firefly", 3, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityFirefly.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.SWAMP))));
        mocEntityMap.put("Fly", new MoCEntityData("Fly", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityFly.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.SAVANNA, Type.FOREST, Type.JUNGLE, Type.SWAMP))));
        mocEntityMap.put("Maggot", new MoCEntityData("Maggot", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityMaggot.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.SWAMP))));
        mocEntityMap.put("Snail", new MoCEntityData("Snail", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntitySnail.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.SWAMP))));
        mocEntityMap.put("Roach", new MoCEntityData("Roach", 2, EnumCreatureType.ambient, new SpawnListEntry(MoCEntityRoach.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.SWAMP))));
        
        // creatures
        mocEntityMap.put("Bear", new MoCEntityData("Bear", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBear.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.MOUNTAIN, Type.JUNGLE, Type.SNOWY))));
        mocEntityMap.put("BigCat", new MoCEntityData("BigCat", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBigCat.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.SAVANNA, Type.JUNGLE, Type.SNOWY))));
        mocEntityMap.put("Bird", new MoCEntityData("Bird", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBird.class, 15, 2, 3), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.PLAINS))));
        mocEntityMap.put("Boar", new MoCEntityData("Boar", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBoar.class, 8, 2, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
        mocEntityMap.put("Bunny", new MoCEntityData("Bunny", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityBunny.class, 8, 2, 3), new ArrayList(Arrays.asList(Type.FOREST))));
        mocEntityMap.put("Crocodile", new MoCEntityData("Crocodile", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityCrocodile.class, 5, 1, 2), new ArrayList(Arrays.asList(Type.SWAMP))));
        mocEntityMap.put("Deer", new MoCEntityData("Deer", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityDeer.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.JUNGLE))));
        mocEntityMap.put("Duck", new MoCEntityData("Duck", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityDuck.class, 10, 1, 2), new ArrayList(Arrays.asList(Type.RIVER))));
        mocEntityMap.put("Elephant", new MoCEntityData("Elephant", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityElephant.class, 4, 1, 1), new ArrayList(Arrays.asList(Type.SAVANNA, Type.JUNGLE, Type.SNOWY))));
        
        if (proxy.enableEnts)
        {
        	mocEntityMap.put("Ent", new MoCEntityData("Ent", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityEnt.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.FOREST))));
        }
        
        
        mocEntityMap.put("Fox", new MoCEntityData("Fox", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityFox.class, 5, 1, 1), new ArrayList(Arrays.asList(Type.FOREST, Type.SNOWY))));
        mocEntityMap.put("Goat", new MoCEntityData("Goat", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityGoat.class, 8, 1, 3), new ArrayList(Arrays.asList(Type.MOUNTAIN))));
        mocEntityMap.put("Kitty", new MoCEntityData("Kitty", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityKitty.class, 4, 1, 2), new ArrayList(Arrays.asList(Type.FOREST))));
        mocEntityMap.put("KomodoDragon", new MoCEntityData("KomodoDragon", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityKomodoDragon.class, 4, 1, 2), new ArrayList(Arrays.asList(Type.SWAMP))));
        mocEntityMap.put("Mole", new MoCEntityData("Mole", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityMole.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.PLAINS))));
        mocEntityMap.put("Mouse", new MoCEntityData("Mouse", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityMouse.class, 7, 1, 2), new ArrayList(Arrays.asList(Type.FOREST, Type.HILLS, Type.PLAINS))));
        mocEntityMap.put("Ostrich", new MoCEntityData("Ostrich", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityOstrich.class, 4, 1, 1), new ArrayList(Arrays.asList(Type.SAVANNA))));
        mocEntityMap.put("Raccoon", new MoCEntityData("Raccoon", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityRaccoon.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.FOREST))));
        mocEntityMap.put("Snake", new MoCEntityData("Snake", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntitySnake.class, 2, 1, 2), new ArrayList(Arrays.asList(Type.SANDY, Type.FOREST, Type.JUNGLE, Type.SWAMP))));
        mocEntityMap.put("Turkey", new MoCEntityData("Turkey", 2, EnumCreatureType.creature, new SpawnListEntry(MoCEntityTurkey.class, 8, 1, 2), new ArrayList(Arrays.asList(Type.PLAINS))));
        mocEntityMap.put("Turtle", new MoCEntityData("Turtle", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityTurtle.class, 6, 1, 2), new ArrayList(Arrays.asList(Type.SWAMP))));
        mocEntityMap.put("Horse", new MoCEntityData("Horse", 4, EnumCreatureType.creature, new SpawnListEntry(MoCEntityHorse.class, 8, 1, 4), new ArrayList(Arrays.asList(Type.PLAINS, Type.SAVANNA))));
        mocEntityMap.put("Wyvern", new MoCEntityData("Wyvern", 3, EnumCreatureType.creature, new SpawnListEntry(MoCEntityWyvern.class, 8, 1, 3), new ArrayList()));
        
        // water creatures
        mocEntityMap.put("Dolphin", new MoCEntityData("Dolphin", 2, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityDolphin.class, 1, 1, 1), new ArrayList(Arrays.asList(Type.BEACH, Type.OCEAN, Type.SWAMP))));
        mocEntityMap.put("Fishy", new MoCEntityData("Fishy", 4, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityFishy.class, 12, 1, 4), new ArrayList(Arrays.asList(Type.BEACH, Type.OCEAN))));
        mocEntityMap.put("JellyFish", new MoCEntityData("JellyFish", 2, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityJellyFish.class, 2, 0, 3), new ArrayList(Arrays.asList(Type.OCEAN))));
        mocEntityMap.put("Ray", new MoCEntityData("Ray", 1, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityRay.class, 1, 0, 1), new ArrayList(Arrays.asList(Type.SWAMP, Type.OCEAN))));
        mocEntityMap.put("Shark", new MoCEntityData("Shark", 1, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityShark.class, 1, 0, 1), new ArrayList(Arrays.asList(Type.OCEAN))));
        mocEntityMap.put("MediumFish", new MoCEntityData("MediumFish", 2, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityMediumFish.class, 4, 0, 3), new ArrayList(Arrays.asList(Type.OCEAN, Type.RIVER, Type.SWAMP))));
        mocEntityMap.put("Piranha", new MoCEntityData("Piranha", 4, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntityPiranha.class, 10, 1, 4), new ArrayList(Arrays.asList(Type.JUNGLE))));
        mocEntityMap.put("SmallFish", new MoCEntityData("SmallFish", 4, EnumCreatureType.waterCreature, new SpawnListEntry(MoCEntitySmallFish.class, 6, 1, 4), new ArrayList(Arrays.asList(Type.OCEAN, Type.RIVER, Type.SWAMP))));
        
        // monsters
        mocEntityMap.put("BigGolem", new MoCEntityData("BigGolem", 1, EnumCreatureType.monster, new SpawnListEntry(MoCEntityBigGolem.class, 3, 1, 1), new ArrayList(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("FlameWraith", new MoCEntityData("FlameWraith", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityFlameWraith.class, 5, 1, 1), new ArrayList(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("HellRat", new MoCEntityData("HellRat", 4, EnumCreatureType.monster, new SpawnListEntry(MoCEntityHellRat.class, 6, 1, 4), new ArrayList(Arrays.asList(Type.NETHER))));
        mocEntityMap.put("HorseMob", new MoCEntityData("HorseMob", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityHorseMob.class, 8, 1, 3), new ArrayList(Arrays.asList(Type.PLAINS, Type.SAVANNA, Type.NETHER))));
        mocEntityMap.put("MiniGolem", new MoCEntityData("MiniGolem", 2, EnumCreatureType.monster, new SpawnListEntry(MoCEntityMiniGolem.class, 6, 1, 1), new ArrayList(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Ogre", new MoCEntityData("Ogre", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityOgre.class, 8, 1, 1), new ArrayList(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Rat", new MoCEntityData("Rat", 2, EnumCreatureType.monster, new SpawnListEntry(MoCEntityRat.class, 7, 1, 2), new ArrayList(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Scorpion", new MoCEntityData("Scorpion", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityScorpion.class, 6, 1, 1), new ArrayList(Arrays.asList(Type.SANDY, Type.SNOWY, Type.NETHER))));
        mocEntityMap.put("SilverSkeleton", new MoCEntityData("SilverSkeleton", 4, EnumCreatureType.monster, new SpawnListEntry(MoCEntitySilverSkeleton.class, 6, 1, 4), new ArrayList(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("Werewolf", new MoCEntityData("Werewolf", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityWerewolf.class, 8, 1, 4), new ArrayList(Arrays.asList(Type.CONIFEROUS))));
        mocEntityMap.put("Wraith", new MoCEntityData("Wraith", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityWraith.class, 1, 1, 1), new ArrayList(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
        mocEntityMap.put("WWolf", new MoCEntityData("WWolf", 3, EnumCreatureType.monster, new SpawnListEntry(MoCEntityWWolf.class, 8, 1, 4), new ArrayList(Arrays.asList(Type.CONIFEROUS))));

        
        
        if (!isJustAnotherSpawnerLoaded) //only use built-in entity spawn config data if the Just Another Spawner mod is NOT installed
        {
        	proxy.readMocConfigValues();  //perform initial update for entity data based on config file spawn list entries
        	
	        // update the spawn data for all entities based on initial updates from config file
	        for (MoCEntityData entityData : mocEntityMap.values())
	        {
	        	String entityClassFilePath = "";
	    		
	    		if (entityData.getType() == EnumCreatureType.ambient)
	    		{
	    			entityClassFilePath = "drzhark.mocreatures.entity.ambient.";
	    		}
	    		
	    		if (entityData.getType() == EnumCreatureType.creature)
	    		{
	    			entityClassFilePath = "drzhark.mocreatures.entity.animal.";
	    		}
	    		
	    		if (entityData.getType() == EnumCreatureType.waterCreature)
	    		{
	    			entityClassFilePath = "drzhark.mocreatures.entity.aquatic.";	
	    		}
	    		
	    		if (entityData.getType() == EnumCreatureType.monster)
	    		{
	    			entityClassFilePath = "drzhark.mocreatures.entity.monster.";	
	    		}
	        	
	        	String fullyQualifiedEntityClass = entityClassFilePath + "MoCEntity" + entityData.getEntityName();
	        	
	        	try
	        	{
	        		Class<?> entityClass = Class.forName(fullyQualifiedEntityClass);
	        		
	        		mocEntityMap.replace
	        		(
	        				entityData.getEntityName(),
			           		new MoCEntityData
			           		(
			           			entityData.getEntityName(),
			           			entityData.getMaxInChunk(),
			           			entityData.getType(),
			           			new SpawnListEntry(entityClass, entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn()),
			           			entityData.getBiomeTypes(),
			           			entityData.getCanSpawn()
			           		)
			        );
	        	}
	        	catch (ClassNotFoundException ex)
	        	{
	        		System.out.println("[Mo' Creatures]: ERROR - Tried to register an entity spawn entry but could not find the following Entity Class file: " + fullyQualifiedEntityClass);
	        	}
	        }
        }
        
        
        for (MoCEntityData entityData : mocEntityMap.values())
        {
        	if ((!entityData.getCanSpawn()) || entityData.getFrequency() == 0) {continue;} //don't try to register spawns for an entity that is set to not spawn in the config file
        	
            if (entityData.getEntityName().equals("Wyvern")) {continue;} //don't register spawns for wyvern in normal biomes
            
            SpawnListEntry spawnEntry = entityData.getSpawnListEntry();
            
//			FOR DEBUG Purposes:            
//            	System.out.println("============================");
//           	 	System.out.println(spawnEntry);
            
            for (BiomeDictionary.Type biomeType : entityData.getBiomeTypes())
            {
                for (BiomeGenBase biome : BiomeDictionary.getBiomesForType(biomeType))
                {
//        			FOR DEBUG Purposes:  
//                		System.out.println(biome.getClass().getName());
                	
                    boolean canEntitySpawnInThisBiome = false;
                    for (String supportedBiomeMod : listOfModsSupportedForBiomeSpawningIntegration)
                    {
                    	
                        if (biome.getClass().getName().contains(supportedBiomeMod))
                        {
                            canEntitySpawnInThisBiome = true;
                            break;
                        }
                    }
                    if (!biome.getSpawnableList(entityData.getType()).contains(spawnEntry) && canEntitySpawnInThisBiome)
                    {
                        biome.getSpawnableList(entityData.getType()).add(spawnEntry); //registers the spawnlist entry for that entity in that biome
                    }
                }
            }
        }
    }

    /**
     * Used to register entities that are NOT supposed to have a spawn egg
     * 
     * @param entityClass
     * @param entityName
     */
    protected void registerEntity(Class<? extends Entity> entityClass, String entityName)
    {
        if (proxy.debug) 
        {
            MoCLog.logger.info("registerEntity " + entityClass + " with Mod ID " + mocEntityID);
        }
        EntityRegistry.registerModEntity(entityClass, entityName, mocEntityID, instance, 128, 1, true);
        mocEntityID++;
    }
    
    
    public static int getSpawnEggItemSubId()
	{
		++entitySpawnEggSubId;
		return entitySpawnEggSubId;
	}

    /**
     * Used to register entities that are supposed to have a spawn egg
     * 
     * @param entityClass
     * @param entityName
     */
    private void registerEntityAndSpawnEgg(Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor)
    {
        if (proxy.debug) 
        {
          MoCLog.logger.info("registerEntity " + entityClass + " with Mod ID " + mocEntityID);
        }
        
        EntityRegistry.registerModEntity(entityClass, entityName, mocEntityID, instance, 128, 1, true);
        mocEntityID++;
        
        int subId = getSpawnEggItemSubId();
        EntityList.IDtoClassMapping.put(subId, entityClass);
		entityEggs.put(subId, new EntityList.EntityEggInfo(subId, eggColor, eggDotsColor));
    }

    protected void InitItems()
    {
        wyvernLairDimensionID = proxy.wyvernDimension;//17

        recordShuffle = new MoCItemRecord("recordshuffle");
        craftedSaddle = new MoCItemHorseSaddle("horsesaddle");

        sharkTeeth = new MoCItem("sharkteeth");
        haystack = new MoCItemHayStack("haystack");
        sugarLump = new MoCItemSugarLump("sugarlump");
        mocegg = new MoCItemEgg("mocegg");
        bigcatClaw = new MoCItem("bigcatclaw");
        whip = new MoCItemWhip("whip");

        medallion = new MoCItem("medallion");
        kittybed = new MoCItemKittyBed("kittybed");
        litterbox = new MoCItemLitterBox("kittylitter");
        woolball = new MoCItem("woolball");

        petFood = new MoCItem("petfood");
        builderHammer = new ItemBuilderHammer("builderhammer");

        hideReptile = new MoCItem("reptilehide");
        helmetReptile = new MoCItemArmor("reptilehelmet", CROC_ARMOR, 4, 0);
        plateReptile = new MoCItemArmor("reptileplate", CROC_ARMOR, 4, 1);
        legsReptile = new MoCItemArmor("reptilelegs", CROC_ARMOR, 4, 2);
        bootsReptile = new MoCItemArmor("reptileboots", CROC_ARMOR, 4, 3);
        
        fishbowlEmpty = new MoCItemFishBowl("bowlempty", 0);
        fishbowlWater = new MoCItemFishBowl("bowlwater", 11);
        fishbowlFishy1 = new MoCItemFishBowl("bowlfish1", 1);
        fishbowlFishy2 = new MoCItemFishBowl("bowlfish2", 2);
        fishbowlFishy3 = new MoCItemFishBowl("bowlfish3", 3);
        fishbowlFishy4 = new MoCItemFishBowl("bowlfish4", 4);
        fishbowlFishy5 = new MoCItemFishBowl("bowlfish5", 5);
        fishbowlFishy6 = new MoCItemFishBowl("bowlfish6", 6);
        fishbowlFishy7 = new MoCItemFishBowl("bowlfish7", 7);
        fishbowlFishy8 = new MoCItemFishBowl("bowlfish8", 8);
        fishbowlFishy9 = new MoCItemFishBowl("bowlfish9", 9);
        fishbowlFishy10 = new MoCItemFishBowl("bowlfish10", 10);

        fur = new MoCItem("fur");
        omelet = new MoCItemFood("omelet", 4, 0.6F, false);
        turtleRaw = new MoCItemFood("turtleraw", 2, 0.3F, false);
        turtleSoup = new MoCItemTurtleSoup("turtlesoup", 6, 0.6F, false);

        nunchaku = new MoCItemWeapon("nunchaku", ToolMaterial.IRON);
        sai = new MoCItemWeapon("sai", ToolMaterial.IRON);
        bo = new MoCItemWeapon("bo", ToolMaterial.IRON);
        katana = new MoCItemWeapon("katana", ToolMaterial.IRON);
        sharkSword = new MoCItemWeapon("sharksword", SHARK_WEAPON);

        key = new MoCItem("key");
        essenceDarkness = new MoCItem("essencedarkness");
        essenceFire = new MoCItem("essencefire");
        amuletBone = new MoCItemHorseAmulet("amuletbone");
        amuletBoneFull = new MoCItemHorseAmulet("amuletbonefull");
        amuletGhost = new MoCItemHorseAmulet("amuletghost");
        amuletGhostFull = new MoCItemHorseAmulet("amuletghostfull");
        amuletFairy = new MoCItemHorseAmulet("amuletfairy");
        amuletFairyFull = new MoCItemHorseAmulet("amuletfairyfull");
        amuletPegasus = new MoCItemHorseAmulet("amuletpegasus");
        amuletPegasusFull = new MoCItemHorseAmulet("amuletpegasusfull");

        essenceUndead = new MoCItem("essenceundead");
        essenceLight = new MoCItem("essencelight");

        chestFur = new MoCItemArmor("furchest", FUR_ARMOR, 4, 1);
        helmetFur = new MoCItemArmor("furhelmet", FUR_ARMOR, 4, 0);
        legsFur = new MoCItemArmor("furlegs", FUR_ARMOR, 4, 2);
        bootsFur = new MoCItemArmor("furboots", FUR_ARMOR, 4, 3);

        heartDarkness = new MoCItem("heartdarkness");
        heartFire = new MoCItem("heartfire");
        heartundead = new MoCItem("heartundead");
        ostrichRaw = new MoCItemFood("ostrichraw", 2, 0.3F, false).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
        ostrichCooked = new MoCItemFood("ostrichcooked", 6, 0.6F, false);
        unicornHorn = new MoCItem("unicornhorn");

        fishNet = new MoCItemPetAmulet("fishnet");
        horseArmorCrystal = new MoCItem("horsearmorcrystal");

        turkeyRaw = new MoCItemFood("turkeyraw", 3, 0.3F, false).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
        turkeyCooked = new MoCItemFood("turkeycooked", 8, 0.6F, false);
        
        hide = new MoCItem("hide");
        chestHide = new MoCItemArmor("hidechest", HIDE_ARMOR, 4, 1);
        helmetHide = new MoCItemArmor("hidehelmet", HIDE_ARMOR, 4, 0);
        legsHide = new MoCItemArmor("hidelegs", HIDE_ARMOR, 4, 2);
        bootsHide = new MoCItemArmor("hideboots", HIDE_ARMOR, 4, 3);
        
        ratRaw = new MoCItemFood("ratraw", 2, 0.3F, false).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
        ratCooked = new MoCItemFood("ratcooked", 4, 0.6F, false);
        ratBurger = new MoCItemFood("ratburger", 8, 0.6F, false);

        
        chitin = new MoCItem("chitin");
        chitinCave = new MoCItem("chitinblack");
        chitinFrost = new MoCItem("chitinfrost");
        chitinNether = new MoCItem("chitinnether");

        scorpSwordDirt = new MoCItemWeapon("scorpsworddirt", SCORP_WEAPON, 1, false);
        scorpSwordFrost = new MoCItemWeapon("scorpswordfrost", SCORP_WEAPON, 2, false);
        scorpSwordNether = new MoCItemWeapon("scorpswordnether", SCORP_WEAPON, 3, false);
        scorpSwordCave = new MoCItemWeapon("scorpswordcave", SCORP_WEAPON, 4, false);

        scorpHelmetDirt = new MoCItemArmor("scorphelmetdirt", SCORP_DIRT_ARMOR, 4, 0);
        scorpPlateDirt = new MoCItemArmor("scorpplatedirt", SCORP_DIRT_ARMOR, 4, 1);
        scorpLegsDirt = new MoCItemArmor("scorplegsdirt", SCORP_DIRT_ARMOR, 4, 2);
        scorpBootsDirt = new MoCItemArmor("scorpbootsdirt", SCORP_DIRT_ARMOR, 4, 3);

        scorpHelmetFrost = new MoCItemArmor("scorphelmetfrost", SCORP_FROST_ARMOR, 4, 0);
        scorpPlateFrost = new MoCItemArmor("scorpplatefrost", SCORP_FROST_ARMOR, 4, 1);
        scorpLegsFrost = new MoCItemArmor("scorplegsfrost", SCORP_FROST_ARMOR, 4, 2);
        scorpBootsFrost = new MoCItemArmor("scorpbootsfrost", SCORP_FROST_ARMOR, 4, 3);

        scorpHelmetNether = new MoCItemArmor("scorphelmetnether", SCORP_NETHER_ARMOR, 4, 0);
        scorpPlateNether = new MoCItemArmor("scorpplatenether", SCORP_NETHER_ARMOR, 4, 1);
        scorpLegsNether = new MoCItemArmor("scorplegsnether", SCORP_NETHER_ARMOR, 4, 2);
        scorpBootsNether = new MoCItemArmor("scorpbootsnether", SCORP_NETHER_ARMOR, 4, 3);
        
        scorpHelmetCave = new MoCItemArmor("scorphelmetcave", SCORP_CAVE_ARMOR, 4, 0);
        scorpPlateCave = new MoCItemArmor("scorpplatecave", SCORP_CAVE_ARMOR, 4, 1);
        scorpLegsCave = new MoCItemArmor("scorplegscave", SCORP_CAVE_ARMOR, 4, 2);
        scorpBootsCave = new MoCItemArmor("scorpbootscave", SCORP_CAVE_ARMOR, 4, 3);

        scorpStingDirt = new MoCItemWeapon("scorpstingdirt", ToolMaterial.GOLD, 1, true);
        scorpStingFrost = new MoCItemWeapon("scorpstingfrost", ToolMaterial.GOLD, 2, true);
        scorpStingNether = new MoCItemWeapon("scorpstingnether", ToolMaterial.GOLD, 3, true);
        scorpStingCave = new MoCItemWeapon("scorpstingcave", ToolMaterial.GOLD, 4, true);
        

        scrollFreedom = new MoCItem("scrolloffreedom");
        scrollOfSale = new MoCItem("scrollofsale");
        scrollOfOwner = new MoCItem("scrollofowner");

        tusksWood = new MoCItemWeapon("tuskswood", ToolMaterial.WOOD);
        tusksIron = new MoCItemWeapon("tusksiron", ToolMaterial.IRON);
        tusksDiamond = new MoCItemWeapon("tusksdiamond", ToolMaterial.EMERALD);
        
        elephantHarness = new MoCItem("elephantharness");
        elephantChest = new MoCItem("elephantchest");
        elephantGarment = new MoCItem("elephantgarment");
        elephantHowdah = new MoCItem("elephanthowdah");
        mammothPlatform = new MoCItem("mammothplatform");

        crabRaw = new MoCItemFood("crabraw", 2, 0.3F, false).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F);
        crabCooked = new MoCItemFood("crabcooked", 6, 0.6F, false);
        
        silverSword = new MoCItemWeapon("silversword", SILVER_WEAPON);

        multiBlockNames.add ("WyvernLair");
        multiBlockNames.add("OgreLair");

        staffPortal = new ItemStaffPortal("staffportal");
        staffTeleport = new ItemStaffTeleport("staffteleport");
        
        petAmulet = new MoCItemPetAmulet("petamulet", 1);
        
        
        
        
        achievementIconKillWraith = new MoCItem("achievement_icon_kill_wraith");
        achievementIconKillOgre = new MoCItem("achievement_icon_kill_ogre");
        achievementIconKillWerewolf = new MoCItem("achievement_icon_kill_werewolf");
        achievementIconKillBigGolem = new MoCItem("achievement_icon_kill_big_golem");
        
        
        achievementIconBatHorse = new MoCItem("achievement_icon_bat_horse");
        achievementIconDarkPegasus = new MoCItem("achievement_icon_dark_pegasus");
        achievementIconFairyHorse = new MoCItem("achievement_icon_fairy_horse");
        achievementIconNightmareHorse = new MoCItem("achievement_icon_nightmare_horse");
        achievementIconGhostHorse = new MoCItem("achievement_icon_ghost_horse");
        achievementIconPegasus = new MoCItem("achievement_icon_pegasus");
        achievementIconTier2Horse = new MoCItem("achievement_icon_tier2_horse");
        achievementIconTier3Horse = new MoCItem("achievement_icon_tier3_horse");
        achievementIconTier4Horse = new MoCItem("achievement_icon_tier4_horse");
        achievementIconUndeadHorse = new MoCItem("achievement_icon_undead_horse");
        achievementIconUnicorn = new MoCItem("achievement_icon_unicorn");
        achievementIconZebra = new MoCItem("achievement_icon_zebra");
        achievementIconZorse = new MoCItem("achievement_icon_zorse");
        achievementIconZonkey = new MoCItem("achievement_icon_zonkey");
        
        achievementIconIndiana = new MoCItem("achievement_icon_indiana");
        achievementIconTameBigCat = new MoCItem("achievement_icon_tame_big_cat");
        achievementIconTameKitty = new MoCItem("achievement_icon_tame_kitty");
        achievementIconKittyBed = new MoCItem("achievement_icon_kitty_bed");
        achievementIconBreedKitty = new MoCItem("achievement_icon_breed_kitty");
        achievementIconTameBird = new MoCItem("achievement_icon_tame_bird");
        achievementIconFeedSnakeWithLiveMouse = new MoCItem("achievement_icon_feed_snake_with_live_mouse");
        achievementIconTamePanda = new MoCItem("achievement_icon_tame_panda");
        achievementIconTameScorpion = new MoCItem("achievement_icon_tame_scorpion");
        
        achievementIconOstrichHelmet = new MoCItem("achievement_icon_ostrich_helmet");
        achievementIconOstrichChest = new MoCItem("achievement_icon_ostrich_chest");
        achievementIconOstrichFlag = new MoCItem("achievement_icon_ostrich_flag");
        
        achievementIconWyvernOstrich = new MoCItem("achievement_icon_wyvern_ostrich");
        achievementIconNetherOstrich = new MoCItem("achievement_icon_nether_ostrich");
        achievementIconUndeadOstrich = new MoCItem("achievement_icon_undead_ostrich");
        achievementIconUnihornOstrich = new MoCItem("achievement_icon_unihorn_ostrich");
        
        achievementIconTameDolphin = new MoCItem("achievement_icon_tame_dolphin");

        
        //new blocks
        mocStone = new MoCBlockStone("MoCStone").setHardness(1.5F).setResistance(10.0F).setStepSound(Block.soundTypeStone);
        mocGrass = new MoCBlockGrass("MoCGrass").setHardness(0.5F).setStepSound(Block.soundTypeGrass);
        mocDirt = new MoCBlockDirt("MoCDirt").setHardness(0.6F).setStepSound(Block.soundTypeGravel);
        
        
        //non terrain generator blocks
        mocLeaf = new MoCBlockLeaves("MoCLeaves").setHardness(0.2F).setLightOpacity(1).setStepSound(Block.soundTypeGrass);
        mocLog = new MoCBlockLog("MoCLog").setHardness(2.0F).setStepSound(Block.soundTypeWood);
        
        
        mocTallGrass = new MoCBlockTallGrass("MoCTallGrass", true).setHardness(0.0F).setStepSound(Block.soundTypeGrass);     
        mocPlank = new MoCBlockPlanks("MoCWoodPlank").setHardness(2.0F).setResistance(5.0F).setStepSound(Block.soundTypeWood);

        //wyvern lair block harvest settings
        mocDirt.setHarvestLevel("shovel", 0, 0); 
        mocGrass.setHarvestLevel("shovel", 0, 0); 
        mocStone.setHarvestLevel("pickaxe", 1, 0);
        
        
        spawnEgg = new MoCItemSpawnEgg();

        proxy.mocSettingsConfig.save();
    }

    private void AddRecipes()
    {
        GameRegistry.addSmelting(MoCreatures.crabRaw, new ItemStack(MoCreatures.crabCooked, 1), 0F);
        
        GameRegistry.addSmelting(MoCreatures.ratRaw, new ItemStack(MoCreatures.ratCooked, 1), 0F);

        GameRegistry.addSmelting(MoCreatures.ostrichRaw, new ItemStack(MoCreatures.ostrichCooked, 1), 0F);

        GameRegistry.addSmelting(MoCreatures.turkeyRaw, new ItemStack(MoCreatures.turkeyCooked, 1), 0F);

        GameRegistry.addSmelting(MoCreatures.mocegg, new ItemStack(MoCreatures.omelet, 1), 0F);

        GameRegistry.addSmelting(Items.egg, new ItemStack(MoCreatures.omelet, 1), 0F);

        GameRegistry.addShapelessRecipe(new ItemStack(scrollFreedom, 1), new Object[] { Items.paper, Items.feather, Items.redstone });
        
        GameRegistry.addShapelessRecipe(new ItemStack(scrollFreedom, 1), new Object[] { scrollOfSale, Items.redstone });
        
        GameRegistry.addShapelessRecipe(new ItemStack(scrollOfSale, 1), new Object[] { Items.paper, Items.feather });
        
        
        GameRegistry.addShapelessRecipe(new ItemStack(Items.leather, 1), new Object[] { hide });

        GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1), new Object[] { fur });

        GameRegistry.addShapelessRecipe(new ItemStack(scorpSwordNether, 1), new Object[] { Items.diamond_sword, scorpStingNether, scorpStingNether, scorpStingNether });

        GameRegistry.addShapelessRecipe(new ItemStack(scorpSwordFrost, 1), new Object[] { Items.diamond_sword, scorpStingFrost, scorpStingFrost, scorpStingFrost });

        GameRegistry.addShapelessRecipe(new ItemStack(scorpSwordCave, 1), new Object[] { Items.diamond_sword, scorpStingCave, scorpStingCave, scorpStingCave });

        GameRegistry.addShapelessRecipe(new ItemStack(scorpSwordDirt, 1), new Object[] { Items.diamond_sword, scorpStingDirt, scorpStingDirt, scorpStingDirt });

        GameRegistry.addShapelessRecipe(new ItemStack(turtleSoup, 1), new Object[] { new ItemStack(turtleRaw, 1), new ItemStack(Items.bowl, 1) });

        GameRegistry.addShapelessRecipe(new ItemStack(essenceLight, 1), new Object[] { essenceUndead, essenceFire, essenceDarkness });

        GameRegistry.addRecipe(new ItemStack(fishNet, 1), new Object[] { " # ", "S#S", "#S#", Character.valueOf('#'), Items.string, Character.valueOf('S'), sharkTeeth });
        
        GameRegistry.addRecipe(new ItemStack(tusksWood, 1), new Object[] { "X  ", "XR ", "XXX", Character.valueOf('X'), Blocks.planks, Character.valueOf('R'), Items.lead  });
        
        GameRegistry.addRecipe(new ItemStack(tusksIron, 1), new Object[] { "X  ", "XR ", "XXX", Character.valueOf('X'), Items.iron_ingot, Character.valueOf('R'), Items.lead });
        
        GameRegistry.addRecipe(new ItemStack(tusksDiamond, 1), new Object[] { "X  ", "XR ", "XXX", Character.valueOf('X'), Items.diamond, Character.valueOf('R'), Items.lead });
        
        GameRegistry.addRecipe(new ItemStack(mammothPlatform, 1), new Object[] { "WRW", "PPP", "WRW",  Character.valueOf('W'), Blocks.log, Character.valueOf('R'), Items.lead ,  Character.valueOf('P'), Blocks.planks});
        
        GameRegistry.addRecipe(new ItemStack(elephantChest, 1), new Object[] { " W ", "CHC", " W ", Character.valueOf('H'), hide, Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 0), Character.valueOf('C'), Blocks.chest });
        
        GameRegistry.addRecipe(new ItemStack(elephantHarness, 1), new Object[] { "HWH", "IWI", "HWH", Character.valueOf('H'), hide, Character.valueOf('W'), new ItemStack(Blocks.wool, 1, 0), Character.valueOf('I'), Items.iron_ingot });
        
        GameRegistry.addRecipe(new ItemStack(elephantHowdah, 1), new Object[] { "SRS", "RYR", "SRS", Character.valueOf('S'), Items.stick, Character.valueOf('R'), new ItemStack(Blocks.wool, 1, 14), Character.valueOf('Y'), new ItemStack(Blocks.wool, 1, 4) });

        GameRegistry.addRecipe(new ItemStack(elephantGarment, 1), new Object[] { "pyg", "RMR", "BYB", Character.valueOf('R'), new ItemStack(Blocks.wool, 1, 14), Character.valueOf('Y'), new ItemStack(Blocks.wool, 1, 4), 
        Character.valueOf('B'), new ItemStack(Blocks.wool, 1, 11), Character.valueOf('M'), medallion,
        Character.valueOf('p'), new ItemStack(Items.dye, 1, 9), Character.valueOf('y'), new ItemStack(Items.dye, 1, 11),
        Character.valueOf('g'), new ItemStack(Items.dye, 1, 10)
        });

        GameRegistry.addRecipe(new ItemStack(ratBurger, 1), new Object[] { "SB ", "GRG", " B ", Character.valueOf('R'), ratCooked, Character.valueOf('B'), Items.bread, Character.valueOf('S'), Items.pumpkin_seeds, Character.valueOf('G'), Items.wheat_seeds });

        GameRegistry.addRecipe(new ItemStack(scorpPlateFrost, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), chitinFrost });

        GameRegistry.addRecipe(new ItemStack(scorpHelmetFrost, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), chitinFrost });

        GameRegistry.addRecipe(new ItemStack(scorpLegsFrost, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), chitinFrost });

        GameRegistry.addRecipe(new ItemStack(scorpBootsFrost, 1), new Object[] { "X X", "X X", Character.valueOf('X'), chitinFrost });

        GameRegistry.addRecipe(new ItemStack(scorpPlateNether, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), chitinNether });

        GameRegistry.addRecipe(new ItemStack(scorpHelmetNether, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), chitinNether });

        GameRegistry.addRecipe(new ItemStack(scorpLegsNether, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), chitinNether });

        GameRegistry.addRecipe(new ItemStack(scorpBootsNether, 1), new Object[] { "X X", "X X", Character.valueOf('X'), chitinNether });

        GameRegistry.addRecipe(new ItemStack(scorpPlateCave, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), chitinCave });

        GameRegistry.addRecipe(new ItemStack(scorpHelmetCave, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), chitinCave });

        GameRegistry.addRecipe(new ItemStack(scorpLegsCave, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), chitinCave });

        GameRegistry.addRecipe(new ItemStack(scorpBootsCave, 1), new Object[] { "X X", "X X", Character.valueOf('X'), chitinCave });

        GameRegistry.addRecipe(new ItemStack(scorpPlateDirt, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), chitin });

        GameRegistry.addRecipe(new ItemStack(scorpHelmetDirt, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), chitin });

        GameRegistry.addRecipe(new ItemStack(scorpLegsDirt, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), chitin });

        GameRegistry.addRecipe(new ItemStack(scorpBootsDirt, 1), new Object[] { "X X", "X X", Character.valueOf('X'), chitin });

        GameRegistry.addRecipe(new ItemStack(chestHide, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), hide });

        GameRegistry.addRecipe(new ItemStack(helmetHide, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), hide });

        GameRegistry.addRecipe(new ItemStack(legsHide, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), hide });

        GameRegistry.addRecipe(new ItemStack(bootsHide, 1), new Object[] { "X X", "X X", Character.valueOf('X'), hide });

        GameRegistry.addRecipe(new ItemStack(horseArmorCrystal, 1), new Object[] { "  D", "CDC", "DCD", Character.valueOf('D'), Items.diamond, Character.valueOf('C'), Blocks.glass });

        GameRegistry.addShapelessRecipe(new ItemStack(essenceLight, 1), new Object[] { new ItemStack(essenceUndead, 1), new ItemStack(essenceFire, 1), new ItemStack(essenceDarkness, 1)});

        GameRegistry.addShapelessRecipe(new ItemStack(essenceUndead, 1), new Object[] { new ItemStack(Items.rotten_flesh, 1), new ItemStack(heartundead, 1), new ItemStack(Items.glass_bottle, 1)});

        GameRegistry.addShapelessRecipe(new ItemStack(essenceFire, 1), new Object[] { new ItemStack(Items.blaze_powder, 1), new ItemStack(heartFire, 1), new ItemStack(Items.glass_bottle, 1)});

        GameRegistry.addShapelessRecipe(new ItemStack(essenceFire, 1), new Object[] { new ItemStack(Blocks.fire, 1), new ItemStack(heartFire, 1), new ItemStack(Items.glass_bottle, 1)});

        GameRegistry.addShapelessRecipe(new ItemStack(essenceDarkness, 1), new Object[] { new ItemStack(Items.ender_pearl, 1), new ItemStack(heartDarkness, 1), new ItemStack(Items.glass_bottle, 1)});

        GameRegistry.addRecipe(new ItemStack(chestFur, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(helmetFur, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(legsFur, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(bootsFur, 1), new Object[] { "X X", "X X", Character.valueOf('X'), fur });

        GameRegistry.addRecipe(new ItemStack(key, 1), new Object[] { "  #", " # ", "X  ", Character.valueOf('#'), Items.stick, Character.valueOf('X'), Items.iron_ingot, });

        GameRegistry.addRecipe(new ItemStack(petAmulet, 1), new Object[] { "X X", " Z ", "X X", Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), Items.diamond });

        GameRegistry.addRecipe(new ItemStack(amuletBone, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Items.bone, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), Items.ender_pearl });

        GameRegistry.addRecipe(new ItemStack(amuletGhost, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Items.bone, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), Items.ghast_tear });

        GameRegistry.addRecipe(new ItemStack(amuletFairy, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Blocks.fire, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), unicornHorn });

        GameRegistry.addRecipe(new ItemStack(amuletFairy, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Blocks.fire, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), essenceLight });

        GameRegistry.addRecipe(new ItemStack(amuletPegasus, 1), new Object[] { "#X#", "XZX", "#X#", Character.valueOf('#'), Blocks.fire, Character.valueOf('X'), Items.gold_nugget, Character.valueOf('Z'), Items.diamond });

        GameRegistry.addRecipe(new ItemStack(sharkSword, 1), new Object[] { "#X#", "#X#", " X ", Character.valueOf('#'), sharkTeeth, Character.valueOf('X'), Items.stick, });

        GameRegistry.addRecipe(new ItemStack(fishbowlEmpty, 1), new Object[] { "# #", "# #", "###", Character.valueOf('#'), Blocks.glass, });

        if (isGregTech6Loaded)
        {
        	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(petFood, 4), new Object[] {"listAllfishraw", "listAllmeatraw"}));
        }
        else if (isPalmsHarvestLoaded)
        {
        	GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(petFood, 4), new Object[] {"listAllfishraw", Items.porkchop}));
        }
        else
        {
        	GameRegistry.addShapelessRecipe(new ItemStack(petFood, 4), new Object[] { new ItemStack(Items.fish, 1), new ItemStack(Items.porkchop, 1) });
        }
        
        
        
        GameRegistry.addRecipe(new ItemStack(woolball, 1), new Object[] { " # ", "# #", " # ", Character.valueOf('#'), Items.string, });

        GameRegistry.addRecipe(new ItemStack(litterbox, 1), new Object[] { "###", "#X#", "###", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), Blocks.sand, });

        GameRegistry.addRecipe(new ItemStack(medallion, 1), new Object[] { "# #", "XZX", " X ", Character.valueOf('#'), Items.leather, Character.valueOf('Z'), Items.diamond, Character.valueOf('X'), Items.gold_ingot, });

        GameRegistry.addRecipe(new ItemStack(medallion, 1), new Object[] { "# #", " X ", Character.valueOf('#'), Items.leather, Character.valueOf('X'), Items.gold_ingot, });

        GameRegistry.addRecipe(new ItemStack(whip, 1), new Object[] { "#X#", "X X", "# Z", Character.valueOf('#'), bigcatClaw, Character.valueOf('X'), Items.leather, Character.valueOf('Z'), Items.iron_ingot });

        GameRegistry.addRecipe(new ItemStack(craftedSaddle, 1), new Object[] { "XXX", "X#X", "# #", Character.valueOf('#'), Items.iron_ingot, Character.valueOf('X'), Items.leather });

        GameRegistry.addRecipe(new ItemStack(haystack, 1), new Object[] { "XXX", "XXX", Character.valueOf('X'), Items.wheat });

        GameRegistry.addRecipe(new ItemStack(Items.wheat, 6), new Object[] { "X", Character.valueOf('X'), haystack });

        GameRegistry.addRecipe(new ItemStack(sugarLump, 1), new Object[] { "XX", "##", Character.valueOf('X'), Items.sugar, Character.valueOf('#'), Items.sugar });

        GameRegistry.addRecipe(new ItemStack(craftedSaddle, 1), new Object[] { "X", "#", Character.valueOf('X'), Items.saddle, Character.valueOf('#'), Items.iron_ingot });

        GameRegistry.addRecipe(new ItemStack(Items.chainmail_chestplate, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), sharkTeeth });

        GameRegistry.addRecipe(new ItemStack(Items.chainmail_helmet, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), sharkTeeth });

        GameRegistry.addRecipe(new ItemStack(Items.chainmail_leggings, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), sharkTeeth });

        GameRegistry.addRecipe(new ItemStack(Items.chainmail_boots, 1), new Object[] { "X X", "X X", Character.valueOf('X'), sharkTeeth });

        GameRegistry.addRecipe(new ItemStack(plateReptile, 1), new Object[] { "X X", "XXX", "XXX", Character.valueOf('X'), hideReptile });

        GameRegistry.addRecipe(new ItemStack(helmetReptile, 1), new Object[] { "XXX", "X X", Character.valueOf('X'), hideReptile });

        GameRegistry.addRecipe(new ItemStack(legsReptile, 1), new Object[] { "XXX", "X X", "X X", Character.valueOf('X'), hideReptile });

        GameRegistry.addRecipe(new ItemStack(bootsReptile, 1), new Object[] { "X X", "X X", Character.valueOf('X'), hideReptile });

        for (int index = 0; index < 16; index++)
        {
            GameRegistry.addShapelessRecipe(new ItemStack(kittybed, 1, index), new Object[] { new ItemStack(Items.dye, 1, index), new ItemStack(kittybed, 1) });

            ItemStack kittyBedItemStack = new ItemStack(kittybed, 1, index);
            GameRegistry.addRecipe(kittyBedItemStack, new Object[] { "###", "#X#", "Z  ", Character.valueOf('#'), Blocks.planks, Character.valueOf('X'), new ItemStack(Blocks.wool, 1, MoCTools.colorize(index)), Character.valueOf('Z'), Items.iron_ingot, });
            String kittyBedTypeColourNamePrefix = ItemDye.field_150923_a[index];
            LanguageRegistry.addName(kittyBedItemStack, (StatCollector.translateToLocal("item.kittybed." + kittyBedTypeColourNamePrefix + ".name")));
        }
        
        for (int i = 0; i < multiBlockNames.size(); i++) 
        {
            GameRegistry.addShapelessRecipe(new ItemStack(mocPlank, 4, i), new Object[] { new ItemStack(mocLog, 1, i)});
        }
        
        GameRegistry.addRecipe(new ItemStack(staffPortal, 1), new Object[] { "  E", " U ", "R  ", Character.valueOf('E'), Items.ender_eye, Character.valueOf('U'), unicornHorn, Character.valueOf('R'), Items.blaze_rod });
        
        GameRegistry.addRecipe(new ItemStack(staffPortal, 1), new Object[] { "  E", " U ", "R  ", Character.valueOf('E'), Items.ender_eye, Character.valueOf('U'), essenceLight, Character.valueOf('R'), Items.blaze_rod });
    }

    public static void showCreaturePedia(EntityPlayer player, String s)
    {
        //TODO 4FIX        mc.displayGuiScreen(new MoCGUIPedia(s, 256, 256));
    }

    public static void showCreaturePedia(String s)
    {
        //TODO 4FIX        EntityPlayer entityPlayer = mc.thePlayer;
        //showCreaturePedia(entityPlayer, s);
    }

    public static void updateSettings()
    {
        proxy.readGlobalConfigValues();
    }

    public static boolean isServer()
    {
        return (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER);
    }
}