package drzhark.mocreatures.achievements;

import cpw.mods.fml.common.FMLCommonHandler;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.AchievementPage;

public class MoCAchievements {

/*
 * ============================= NOTICE ====================================
 * 
 * Do NOT convert the fields in this class to camel case.
 * 
 * Snake case is used ON PURPOSE FOR FIELDS IN THIS CLASS to differentiate
 * from the multiple very similar fields that are called from other classes.
 * 
 * 
 * =========================================================================
 * 
 */
	
	
public static Achievement craft_saddle;

public static Achievement tier2_horse;
public static Achievement tier3_horse;
public static Achievement tier4_horse;
public static Achievement zebra;
public static Achievement zebra_record;
public static Achievement zorse;
public static Achievement zonkey;

public static Achievement heart_undead;
public static Achievement essence_undead;
public static Achievement undead_horse;
public static Achievement amulet_bone;

public static Achievement ghost_horse;
public static Achievement amulet_ghost;

public static Achievement heart_fire;
public static Achievement essence_fire;
public static Achievement nightmare_horse;

public static Achievement heart_darkness;
public static Achievement essence_darkness;
public static Achievement bat_horse;
public static Achievement crystal_horse_armor;

public static Achievement essence_light;
public static Achievement pegasus;
public static Achievement unicorn;

public static Achievement amulet_sky;
public static Achievement dark_pegasus;


public static Achievement fairy_horse;
public static Achievement amulet_fairy;


public static Achievement wyvern_portal_staff;
public static Achievement wyvern_egg;

public static Achievement tame_elephant;
public static Achievement mount_elephant;
public static Achievement mammoth_platform;
public static Achievement elephant_garment;
public static Achievement elephant_tusks;
public static Achievement elephant_chest;
public static Achievement elephant_howdah;

public static Achievement craft_medallion;
public static Achievement tame_kitty;
public static Achievement kitty_litter_box;
public static Achievement kitty_litter;
public static Achievement kitty_bed;
public static Achievement pet_food;
public static Achievement breed_kitty;
public static Achievement wool_ball;

public static Achievement tame_big_cat;

public static Achievement big_cat_claw;
public static Achievement craft_whip;
public static Achievement indiana;

public static Achievement shark_tooth;
public static Achievement fish_net;
public static Achievement shark_sword;

public static Achievement catch_fish_in_fish_bowl;

public static Achievement tame_bird;
public static Achievement feed_snake_with_live_mouse;
public static Achievement tame_panda;
public static Achievement tame_scorpion;

public static Achievement ostrich_egg;
public static Achievement ostrich_helmet;
public static Achievement ostrich_chest;
public static Achievement ostrich_flag;

public static Achievement wyvern_ostrich;
public static Achievement nether_ostrich;
public static Achievement undead_ostrich;
public static Achievement unihorn_ostrich;

public static Achievement tame_dolphin;


public static Achievement pet_amulet;

public static Achievement milk_goat;
public static Achievement cook_omelette;
public static Achievement cook_turkey;
public static Achievement cook_ostrich;
public static Achievement cook_rat;
public static Achievement rat_burger;
public static Achievement cook_crab;
public static Achievement cook_turtle;

public static Achievement kill_wraith;
public static Achievement kill_ogre;
public static Achievement kill_werewolf;
public static Achievement kill_big_golem;
public static Achievement leonardo;
public static Achievement raphael;
public static Achievement donatello;
public static Achievement michelangelo;
public static Achievement silver_sword;
public static Achievement get_fur;
public static Achievement fur_armor;
public static Achievement get_hide;
public static Achievement hide_armor;
public static Achievement get_reptile_hide;
public static Achievement reptile_armor;
public static Achievement get_scorpion_material;
public static Achievement scorpion_sword;
public static Achievement scorpion_armor;



	
public static void initilization() 
{
	
	FMLCommonHandler.instance().bus().register(new MoCAchievementEvents());
	
	
	craft_saddle = new Achievement("achievement.MoCreatures.craft_saddle", "MoCreatures.craft_saddle", 0, 0, new ItemStack(MoCreatures.craftedSaddle), (Achievement)null).initIndependentStat().registerStat();
	
	
	tier2_horse = new Achievement("achievement.MoCreatures.tier2_horse", "MoCreatures.tier2_horse", 7, 0, MoCreatures.achievementIconTier2Horse, (Achievement)null).initIndependentStat().registerStat();
	tier3_horse = new Achievement("achievement.MoCreatures.tier3_horse", "MoCreatures.tier3_horse", 9, 0, MoCreatures.achievementIconTier3Horse, tier2_horse).registerStat();
	tier4_horse = new Achievement("achievement.MoCreatures.tier4_horse", "MoCreatures.tier4_horse", 11, 0, MoCreatures.achievementIconTier4Horse, tier3_horse).registerStat();
	zebra = new Achievement("achievement.MoCreatures.zebra", "MoCreatures.zebra", 13, 0, MoCreatures.achievementIconZebra, tier4_horse).registerStat();
	zebra_record = new Achievement("achievement.MoCreatures.zebra_record", "MoCreatures.zebra_record", 13, -3, new ItemStack(MoCreatures.recordShuffle), zebra).registerStat();
	zonkey = new Achievement("achievement.MoCreatures.zonkey", "MoCreatures.zonkey", 12, -2, new ItemStack(MoCreatures.achievementIconZonkey), zebra).registerStat();
	zorse = new Achievement("achievement.MoCreatures.zorse", "MoCreatures.zorse", 15, 0, MoCreatures.achievementIconZorse, zebra).registerStat();
	
	heart_undead = new Achievement("achievement.MoCreatures.heart_undead", "MoCreatures.heart_undead", 15, 6, new ItemStack(MoCreatures.heartundead), AchievementList.buildSword).registerStat();
	essence_undead = new Achievement("achievement.MoCreatures.essence_undead", "MoCreatures.essence_undead", 15, 4, new ItemStack(MoCreatures.essenceUndead), heart_undead).registerStat();
	undead_horse = new Achievement("achievement.MoCreatures.undead_horse", "MoCreatures.undead_horse", 15, 2, MoCreatures.achievementIconUndeadHorse, essence_undead).registerStat();
	amulet_bone = new Achievement("achievement.MoCreatures.amulet_bone", "MoCreatures.amulet_bone", 13, 2, new ItemStack(MoCreatures.amuletBone), undead_horse).registerStat();
	
	
	ghost_horse = new Achievement("achievement.MoCreatures.ghost_horse", "MoCreatures.ghost_horse", 15, 8, MoCreatures.achievementIconGhostHorse, (Achievement)null).initIndependentStat().registerStat();
	amulet_ghost = new Achievement("achievement.MoCreatures.amulet_ghost", "MoCreatures.amulet_ghost", 13, 8, new ItemStack(MoCreatures.amuletGhost), ghost_horse).registerStat();
	
	
	
	heart_fire = new Achievement("achievement.MoCreatures.heart_fire", "MoCreatures.heart_fire", 17, 6, new ItemStack(MoCreatures.heartFire), AchievementList.buildSword).registerStat();
	essence_fire = new Achievement("achievement.MoCreatures.essence_fire", "MoCreatures.essence_fire", 17, 4, new ItemStack(MoCreatures.essenceFire), heart_fire).registerStat();
	nightmare_horse =  new Achievement("achievement.MoCreatures.nightmare_horse", "MoCreatures.nightmare_horse", 17, 2, MoCreatures.achievementIconNightmareHorse, essence_fire).registerStat();
	
	
	heart_darkness = new Achievement("achievement.MoCreatures.heart_darkness", "MoCreatures.heart_darkness", 17, -6, new ItemStack(MoCreatures.heartDarkness), AchievementList.buildSword).registerStat();
	essence_darkness = new Achievement("achievement.MoCreatures.essence_darkness", "MoCreatures.essence_darkness", 17, -4, new ItemStack(MoCreatures.essenceDarkness), heart_darkness).registerStat();
	bat_horse = new Achievement("achievement.MoCreatures.bat_horse", "MoCreatures.bat_horse", 17, -2, MoCreatures.achievementIconBatHorse, essence_darkness).registerStat();
	crystal_horse_armor = new Achievement("achievement.MoCreatures.crystal_horse_armor", "MoCreatures.crystal_horse_armor", 15, -2, new ItemStack(MoCreatures.horseArmorCrystal), bat_horse).registerStat();
	
	
	essence_light = new Achievement("achievement.MoCreatures.essence_light", "MoCreatures.essence_light", 19, 0, new ItemStack(MoCreatures.essenceLight), (Achievement)null).initIndependentStat().registerStat();
	pegasus = new Achievement("achievement.MoCreatures.pegasus", "MoCreatures.pegasus", 21, -2, MoCreatures.achievementIconPegasus, essence_light).registerStat();
	unicorn = new Achievement("achievement.MoCreatures.unicorn", "MoCreatures.unicorn", 21, 2, MoCreatures.achievementIconUnicorn, essence_light).registerStat();
	
	
	amulet_sky = new Achievement("achievement.MoCreatures.amulet_sky", "MoCreatures.amulet_sky", 21, -6, new ItemStack(MoCreatures.amuletPegasus), pegasus).registerStat();
	dark_pegasus = new Achievement("achievement.MoCreatures.dark_pegasus", "MoCreatures.dark_pegasus", 23, -4, MoCreatures.achievementIconDarkPegasus, pegasus).registerStat();
	
	fairy_horse = new Achievement("achievement.MoCreatures.fairy_horse", "MoCreatures.fairy_horse", 23, 0, MoCreatures.achievementIconFairyHorse, essence_light).setSpecial().registerStat();
	amulet_fairy = new Achievement("achievement.MoCreatures.amulet_fairy", "MoCreatures.amulet_fairy", 25, 0, new ItemStack(MoCreatures.amuletFairy), fairy_horse).registerStat();
	
	
	wyvern_portal_staff = new Achievement("achievement.MoCreatures.wyvern_portal_staff", "MoCreatures.wyvern_portal_staff", 19, 8, new ItemStack(MoCreatures.staffPortal), essence_light).registerStat();
	wyvern_egg = new Achievement("achievement.MoCreatures.wyvern_egg", "MoCreatures.wyvern_egg", 21, 8, new ItemStack(MoCreatures.mocegg), wyvern_portal_staff).setSpecial().registerStat();
	
	
	
	
	
	
	
	

	
	
	
	tame_elephant = new Achievement("achievement.MoCreatures.tame_elephant", "MoCreatures.tame_elephant", 7, -5, new ItemStack(MoCreatures.sugarLump), (Achievement)null).initIndependentStat().registerStat();
	mount_elephant = new Achievement("achievement.MoCreatures.mount_elephant", "MoCreatures.mount_elephant", 9, -5, new ItemStack(MoCreatures.elephantHarness), tame_elephant).registerStat();
	mammoth_platform = new Achievement("achievement.MoCreatures.mammoth_platform", "MoCreatures.mammoth_platform", 11, -7, new ItemStack(MoCreatures.mammothPlatform), mount_elephant).registerStat();
	elephant_garment = new Achievement("achievement.MoCreatures.elephant_garment", "MoCreatures.elephant_garment", 11, -6, new ItemStack(MoCreatures.elephantGarment), mount_elephant).registerStat();
	elephant_tusks = new Achievement("achievement.MoCreatures.elephant_tusks", "MoCreatures.elephant_tusks", 11, -5, new ItemStack(MoCreatures.tusksIron), mount_elephant).registerStat();
	elephant_chest = new Achievement("achievement.MoCreatures.elephant_chest", "MoCreatures.elephant_chest", 11, -4, new ItemStack(MoCreatures.elephantChest), mount_elephant).registerStat();
	elephant_howdah = new Achievement("achievement.MoCreatures.elephant_howdah", "MoCreatures.elephant_howdah", 13, -6, new ItemStack(MoCreatures.elephantHowdah), elephant_garment).registerStat();
	
	
	craft_medallion = new Achievement("achievement.MoCreatures.craft_medallion", "MoCreatures.craft_medallion", 2, 4, new ItemStack(MoCreatures.medallion), (Achievement)null).initIndependentStat().registerStat();
	tame_kitty = new Achievement("achievement.MoCreatures.tame_kitty", "MoCreatures.tame_kitty", 4, 6, new ItemStack(MoCreatures.achievementIconTameKitty), craft_medallion).registerStat();
	kitty_litter_box = new Achievement("achievement.MoCreatures.kitty_litter_box", "MoCreatures.kitty_litter_box", 3, 8, new ItemStack(MoCreatures.litterbox), tame_kitty).registerStat();
	kitty_litter = new Achievement("achievement.MoCreatures.kitty_litter", "MoCreatures.kitty_litter", 3, 10, new ItemStack(Blocks.sand), kitty_litter_box).registerStat();
	wool_ball = new Achievement("achievement.MoCreatures.wool_ball", "MoCreatures.wool_ball", 4, 8, new ItemStack(MoCreatures.woolball), tame_kitty).registerStat();
	kitty_bed = new Achievement("achievement.MoCreatures.kitty_bed", "MoCreatures.kitty_bed", 5, 8, new ItemStack(MoCreatures.achievementIconKittyBed), tame_kitty).registerStat();
	pet_food = new Achievement("achievement.MoCreatures.pet_food", "MoCreatures.pet_food", 5, 10, new ItemStack(MoCreatures.petFood), kitty_bed).registerStat();
	breed_kitty = new Achievement("achievement.MoCreatures.breed_kitty", "MoCreatures.breed_kitty", 7, 8, new ItemStack(MoCreatures.achievementIconBreedKitty), kitty_bed).registerStat();
	
	
	tame_big_cat = new Achievement("achievement.MoCreatures.tame_big_cat", "MoCreatures.tame_big_cat", 0, 6, new ItemStack(MoCreatures.achievementIconTameBigCat), craft_medallion).registerStat();
	
	big_cat_claw = new Achievement("achievement.MoCreatures.big_cat_claw", "MoCreatures.big_cat_claw", -2, 4, new ItemStack(MoCreatures.bigcatClaw), AchievementList.buildSword).registerStat();
	craft_whip = new Achievement("achievement.MoCreatures.craft_whip", "MoCreatures.craft_whip", -2, 6, new ItemStack(MoCreatures.whip), big_cat_claw).registerStat();
	indiana = new Achievement("achievement.MoCreatures.indiana", "MoCreatures.indiana", 0, 8, new ItemStack(MoCreatures.achievementIconIndiana), craft_whip).registerStat();
	
	
	shark_tooth = new Achievement("achievement.MoCreatures.shark_tooth", "MoCreatures.shark_tooth", -6, 4, new ItemStack(MoCreatures.sharkTeeth), AchievementList.buildSword).registerStat();
	fish_net = new Achievement("achievement.MoCreatures.fish_net", "MoCreatures.fish_net", -5, 6, new ItemStack(MoCreatures.fishNet), shark_tooth).registerStat();
	shark_sword = new Achievement("achievement.MoCreatures.shark_sword", "MoCreatures.shark_sword", -7, 6, new ItemStack(MoCreatures.sharkSword), shark_tooth).registerStat();
	
	catch_fish_in_fish_bowl = new Achievement("achievement.MoCreatures.catch_fish_in_fish_bowl", "MoCreatures.catch_fish_in_fish_bowl", -9, 4, new ItemStack(MoCreatures.fishbowlFishy1), (Achievement)null).initIndependentStat().registerStat();
	
	pet_amulet = new Achievement("achievement.MoCreatures.pet_amulet", "MoCreatures.pet_amulet", 6, 4, new ItemStack(MoCreatures.petAmulet), (Achievement)null).initIndependentStat().registerStat();
	
	
	
	tame_bird = new Achievement("achievement.MoCreatures.tame_bird", "MoCreatures.tame_bird", -7, 2, new ItemStack(MoCreatures.achievementIconTameBird), (Achievement)null).initIndependentStat().registerStat();
	
	feed_snake_with_live_mouse = new Achievement("achievement.MoCreatures.feed_snake_with_live_mouse", "MoCreatures.feed_snake_with_live_mouse", -9, 2, new ItemStack(MoCreatures.achievementIconFeedSnakeWithLiveMouse), (Achievement)null).initIndependentStat().registerStat();
	
	tame_panda = new Achievement("achievement.MoCreatures.tame_panda", "MoCreatures.tame_panda", -11, 2,  new ItemStack(MoCreatures.achievementIconTamePanda), (Achievement)null).initIndependentStat().registerStat();
	
	tame_scorpion = new Achievement("achievement.MoCreatures.tame_scorpion", "MoCreatures.tame_scorpion", -13, 2,  new ItemStack(MoCreatures.achievementIconTameScorpion), (Achievement)null).initIndependentStat().registerStat();
	
	ostrich_egg = new Achievement("achievement.MoCreatures.ostrich_egg", "MoCreatures.ostrich_egg", -15, 2, new ItemStack(MoCreatures.mocegg), (Achievement)null).initIndependentStat().registerStat();
	ostrich_helmet = new Achievement("achievement.MoCreatures.ostrich_helmet", "MoCreatures.ostrich_helmet", -16, 4, new ItemStack(MoCreatures.achievementIconOstrichHelmet), ostrich_egg).registerStat();
	ostrich_chest = new Achievement("achievement.MoCreatures.ostrich_chest", "MoCreatures.ostrich_chest", -14, 4, new ItemStack(MoCreatures.achievementIconOstrichChest), ostrich_egg).registerStat();
	ostrich_flag = new Achievement("achievement.MoCreatures.ostrich_flag", "MoCreatures.ostrich_flag", -12, 4, new ItemStack(MoCreatures.achievementIconOstrichFlag), ostrich_chest).registerStat();
	
	tame_dolphin = new Achievement("achievement.MoCreatures.tame_dolphin", "MoCreatures.tame_dolphin", -17, 2,  new ItemStack(MoCreatures.achievementIconTameDolphin), (Achievement)null).initIndependentStat().registerStat();
	
	wyvern_ostrich = new Achievement("achievement.MoCreatures.wyvern_ostrich", "MoCreatures.wyvern_ostrich", -14, 6, new ItemStack(MoCreatures.achievementIconWyvernOstrich), ostrich_egg).registerStat();
	undead_ostrich = new Achievement("achievement.MoCreatures.undead_ostrich", "MoCreatures.undead_ostrich", -14, 7, new ItemStack(MoCreatures.achievementIconUndeadOstrich), ostrich_egg).registerStat();
	unihorn_ostrich = new Achievement("achievement.MoCreatures.unihorn_ostrich", "MoCreatures.unihorn_ostrich", -16, 7, new ItemStack(MoCreatures.achievementIconUnihornOstrich), ostrich_egg).registerStat();
	nether_ostrich = new Achievement("achievement.MoCreatures.nether_ostrich", "MoCreatures.nether_ostrich", -16, 6, new ItemStack(MoCreatures.achievementIconNetherOstrich), ostrich_egg).registerStat();
	
	milk_goat = new Achievement("achievement.MoCreatures.milk_goat", "MoCreatures.milk_goat", -7, 0, new ItemStack(Items.milk_bucket), (Achievement)null).initIndependentStat().registerStat();
	cook_omelette = new Achievement("achievement.MoCreatures.cook_omelette", "MoCreatures.cook_omelette", -8, -1, new ItemStack(MoCreatures.omelet), AchievementList.buildFurnace).registerStat();
	cook_turkey = new Achievement("achievement.MoCreatures.cook_turkey", "MoCreatures.cook_turkey", -8, 0, new ItemStack(MoCreatures.turkeyCooked), AchievementList.buildFurnace).registerStat();
	cook_ostrich = new Achievement("achievement.MoCreatures.cook_ostrich", "MoCreatures.cook_ostrich", -9, -1, new ItemStack(MoCreatures.ostrichCooked), AchievementList.buildFurnace).registerStat();
	cook_rat = new Achievement("achievement.MoCreatures.cook_rat", "MoCreatures.cook_rat", -10, -1, new ItemStack(MoCreatures.ratCooked), AchievementList.buildFurnace).registerStat();
	rat_burger = new Achievement("achievement.MoCreatures.rat_burger", "MoCreatures.rat_burger", -12, -1, new ItemStack(MoCreatures.ratBurger), cook_rat).registerStat();
	cook_crab = new Achievement("achievement.MoCreatures.cook_crab", "MoCreatures.cook_crab", -9, 0, new ItemStack(MoCreatures.crabCooked), AchievementList.buildFurnace).registerStat();
	cook_turtle = new Achievement("achievement.MoCreatures.cook_turtle", "MoCreatures.cook_turtle", -10, 0, new ItemStack(MoCreatures.turtleSoup), AchievementList.buildSword).registerStat();
	
	
	kill_wraith = new Achievement("achievement.MoCreatures.kill_wraith", "MoCreatures.kill_wraith", -7, -3, new ItemStack(MoCreatures.achievementIconKillWraith), AchievementList.buildSword).registerStat();
	kill_ogre = new Achievement("achievement.MoCreatures.kill_ogre", "MoCreatures.kill_ogre", -7, -4, new ItemStack(MoCreatures.achievementIconKillOgre), AchievementList.buildSword).registerStat();
	kill_werewolf = new Achievement("achievement.MoCreatures.kill_werewolf", "MoCreatures.kill_werewolf", -8, -3, new ItemStack(MoCreatures.achievementIconKillWerewolf), AchievementList.buildSword).registerStat();
	kill_big_golem = new Achievement("achievement.MoCreatures.kill_big_golem", "MoCreatures.kill_big_golem", -8, -4, new ItemStack(MoCreatures.achievementIconKillBigGolem), AchievementList.buildSword).setSpecial().registerStat();
	
	leonardo = new Achievement("achievement.MoCreatures.leonardo", "MoCreatures.leonardo", -7, -7, new ItemStack(MoCreatures.katana), AchievementList.buildSword).registerStat();
	raphael = new Achievement("achievement.MoCreatures.raphael", "MoCreatures.raphael", -7, -6, new ItemStack(MoCreatures.sai), AchievementList.buildSword).registerStat();
	donatello = new Achievement("achievement.MoCreatures.donatello", "MoCreatures.donatello", -8, -7, new ItemStack(MoCreatures.bo), AchievementList.buildSword).registerStat();
	michelangelo = new Achievement("achievement.MoCreatures.michelangelo", "MoCreatures.michelangelo", -8, -6, new ItemStack(MoCreatures.nunchaku), AchievementList.buildSword).registerStat();
	
	
	silver_sword = new Achievement("achievement.MoCreatures.silver_sword", "MoCreatures.silver_sword", -10, -7, new ItemStack(MoCreatures.silverSword), AchievementList.buildSword).setSpecial().registerStat();
	
	
	
	get_fur = new Achievement("achievement.MoCreatures.get_fur", "MoCreatures.get_fur", 3, -5, new ItemStack(MoCreatures.fur), AchievementList.buildSword).registerStat(); 
	fur_armor = new Achievement("achievement.MoCreatures.fur_armor", "MoCreatures.fur_armor", 3, -7, new ItemStack(MoCreatures.helmetFur), get_fur).registerStat(); 
	
	get_hide = new Achievement("achievement.MoCreatures.get_hide", "MoCreatures.get_hide", 1, -5, new ItemStack(MoCreatures.hide), AchievementList.buildSword).registerStat();
	hide_armor = new Achievement("achievement.MoCreatures.hide_armor", "MoCreatures.hide_armor", 1, -7, new ItemStack(MoCreatures.helmetHide), get_hide).registerStat(); 
	
	get_reptile_hide = new Achievement("achievement.MoCreatures.get_reptile_hide", "MoCreatures.get_reptile_hide", -1, -5, new ItemStack(MoCreatures.hideReptile), AchievementList.buildSword).registerStat(); 
	reptile_armor = new Achievement("achievement.MoCreatures.reptile_armor", "MoCreatures.reptile_armor", -1, -7, new ItemStack(MoCreatures.helmetReptile), get_reptile_hide).registerStat(); 
	
	
	get_scorpion_material = new Achievement("achievement.MoCreatures.get_scorpion_material", "MoCreatures.get_scorpion_material", -3, -5, new ItemStack(MoCreatures.chitin), AchievementList.buildSword).registerStat();
	scorpion_sword = new Achievement("achievement.MoCreatures.scorpion_sword", "MoCreatures.scorpion_sword", -4, -6, new ItemStack(MoCreatures.scorpSwordDirt), get_scorpion_material).registerStat();
	scorpion_armor = new Achievement("achievement.MoCreatures.scorpion_armor", "MoCreatures.scorpion_armor", -3, -7, new ItemStack(MoCreatures.scorpHelmetDirt), get_scorpion_material).registerStat();
	
	
	AchievementPage.registerAchievementPage(new AchievementPage(StatCollector.translateToLocal("achievements.MoCreaturesTab"), new Achievement[]{
					craft_saddle,
					
					tier2_horse,
					tier3_horse,
					tier4_horse,
					zebra,
					zebra_record,
					zonkey,
					zorse,
					
					heart_undead,
					essence_undead,
					undead_horse,
					amulet_bone,
					
					ghost_horse,
					amulet_ghost,
					
					heart_fire,
					essence_fire,
					nightmare_horse,
					
					heart_darkness,
					essence_darkness,
					bat_horse,
					crystal_horse_armor,
					
					essence_light,
					pegasus,
					unicorn,
					
					amulet_sky,
					dark_pegasus,
					
					fairy_horse,
					amulet_fairy,
					
					wyvern_portal_staff,
					wyvern_egg,
					
					tame_elephant,
					mount_elephant,
					mammoth_platform,
					elephant_garment,
					elephant_tusks,
					elephant_chest,
					elephant_howdah,
					
					craft_medallion,
					tame_kitty,
					kitty_litter_box,
					kitty_litter,
					kitty_bed,
					pet_food,
					breed_kitty,
					wool_ball,
					
					tame_big_cat,
					
					big_cat_claw,
					craft_whip,
					indiana,
					
					shark_tooth,
					fish_net,
					shark_sword,
					
					catch_fish_in_fish_bowl,
					
					tame_bird,
					feed_snake_with_live_mouse,
					tame_panda,
					
					tame_scorpion,
					ostrich_egg,
					ostrich_helmet,
					ostrich_chest,
					ostrich_flag,
					
					wyvern_ostrich,
					undead_ostrich,
					unihorn_ostrich,
					nether_ostrich,
					
					tame_dolphin,
					
					pet_amulet,
					
					milk_goat,
					cook_omelette,
					cook_turkey,
					cook_ostrich,
					cook_rat,
					rat_burger,
					cook_crab,
					cook_turtle,
					
					kill_wraith,
					kill_ogre,
					kill_werewolf,
					kill_big_golem,
					
					leonardo,
					raphael,
					donatello,
					michelangelo,
					silver_sword,
					
					get_fur,
					fur_armor,
					
					get_hide,
					hide_armor,
					
					get_reptile_hide,
					reptile_armor,
					
					get_scorpion_material,
					scorpion_sword,
					scorpion_armor
	}));
}


}

