package drzhark.mocreatures.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.animal.MoCEntityElephant;
import drzhark.mocreatures.entity.animal.MoCEntityHorse;
import drzhark.mocreatures.entity.animal.MoCEntityOstrich;
import drzhark.mocreatures.entity.animal.MoCEntityPetScorpion;
import drzhark.mocreatures.entity.animal.MoCEntityWyvern;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAppear;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandMoCSpawn extends CommandBase {

    private static List<String> commands = new ArrayList<String>();
    private static List aliases = new ArrayList<String>();
    private static List tabCompletionStrings = new ArrayList<String>();

    static {
        commands.add("/mocspawn <horse|ostrich|scorpion|elephant|wyvern> <int>");
        aliases.add("mocspawn");
        tabCompletionStrings.add("horse");
        tabCompletionStrings.add("ostrich");
        tabCompletionStrings.add("scorpion");
        tabCompletionStrings.add("elephant");
        tabCompletionStrings.add("dolphin");
        tabCompletionStrings.add("wyvern");
    }

    @Override
	public String getCommandName()
    {
        return "mocspawn";
    }

    @Override
	public List getCommandAliases()
    {
        return aliases;
    }
    
    /**
     * Adds the strings available in this command to the given list of tab completion options.
     */
    @Override
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return getListOfStringsMatchingLastWord(par2ArrayOfStr, (String[])tabCompletionStrings.toArray(new String[tabCompletionStrings.size()]));
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
	public int getRequiredPermissionLevel()
    {
        return 2;
    }

    @Override
	public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "commands.mocspawn.usage";
    }

    @Override
	public void processCommand(ICommandSender iCommandSender, String[] stringArray)
    {
        if (stringArray.length == 2)
        {
            String entityType = stringArray[0];
            int type = 0;
            try 
            {
                type = Integer.parseInt(stringArray[1]);
            }
            catch (NumberFormatException exception)
            {
                iCommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "ERROR:" + EnumChatFormatting.WHITE + "The spawn type " + type + " for " + entityType + " is not a valid type."));
                return;
            }

            String playerName = iCommandSender.getCommandSenderName();
            EntityPlayerMP player = FMLCommonHandler.instance().getMinecraftServerInstance().getConfigurationManager().func_152612_a(playerName);
            
            MoCEntityTameableAnimal specialEntity = null;
            
            MoCEntityTameableAquatic specialWaterEntity = null;
            
            if (entityType.equalsIgnoreCase("horse"))
            {
                specialEntity = new MoCEntityHorse(player.worldObj);
                specialEntity.setAdult(true);
            }
            else if (entityType.equalsIgnoreCase("ostrich"))
            {
                specialEntity = new MoCEntityOstrich(player.worldObj);
                specialEntity.setAdult(true);
            }
            else if (entityType.equalsIgnoreCase("scorpion"))
            {
                specialEntity = new MoCEntityPetScorpion(player.worldObj);
                specialEntity.setMoCAge(120);
            }
            else if (entityType.equalsIgnoreCase("elephant"))
            {
                specialEntity = new MoCEntityElephant(player.worldObj);
                specialEntity.setAdult(true);
            }
            else if (entityType.equalsIgnoreCase("dolphin"))
            {
            	specialWaterEntity = new MoCEntityDolphin(player.worldObj);
            	specialWaterEntity.setMoCAge(150);
            }
            else if (entityType.equalsIgnoreCase("wyvern"))
            {
                specialEntity = new MoCEntityWyvern(player.worldObj);
                specialEntity.setMoCAge(180);;
            }
            else
            {
                iCommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "ERROR:" + EnumChatFormatting.WHITE + "The entity spawn type " + entityType + " is not a valid type."));
                return;
            }
            
            double distance = 3D;
            double newPosY = player.posY;
            double newPosX = player.posX - (distance * Math.cos((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = player.posZ - (distance * Math.sin((MoCTools.realAngle(player.rotationYaw - 90F)) / 57.29578F));
            
            
            if(specialEntity != null)
            {
	            specialEntity.setPosition(newPosX, newPosY, newPosZ);
	            specialEntity.setTamed(true);
	            specialEntity.setOwner("NoOwner");
	            specialEntity.setName("Rename_Me");
	            specialEntity.setType(type);
            }
            if(specialWaterEntity != null)
            {
	            specialWaterEntity.setPosition(newPosX, newPosY, newPosZ);
	            specialWaterEntity.setTamed(true);
	            specialWaterEntity.setOwner("NoOwner");
	            specialWaterEntity.setName("Rename_Me");
	            specialWaterEntity.setType(type);
            }
            
            
            if (entityType.equalsIgnoreCase("horse"))
            {
                specialEntity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(((MoCEntityHorse) specialEntity).calculateMaxHealth()); //set max health to the new type
                specialEntity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(((MoCEntityHorse) specialEntity).getCustomSpeed());
            }
            else if (entityType.equalsIgnoreCase("ostrich"))
            {
                specialEntity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(((MoCEntityOstrich) specialEntity).calculateMaxHealth());
            }
            else if (entityType.equalsIgnoreCase("elephant"))
            {
                specialEntity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(((MoCEntityElephant) specialEntity).calculateMaxHealth()); //set max health to the new type
                specialEntity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(((MoCEntityElephant) specialEntity).getCustomSpeed());
            }
            else if (entityType.equalsIgnoreCase("dolphin"))
            {
                specialWaterEntity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
            }
            else if (entityType.equalsIgnoreCase("wyvern"))
            {
                specialEntity.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(((MoCEntityWyvern) specialEntity).getType() >= 5 ? 80.0D : 40.0D);
            }
            

            if (
            		(entityType.equalsIgnoreCase("horse") && (type < 1 || type > 67))
            		|| (entityType.equalsIgnoreCase("ostrich") && (type < 1 || type > 8))
            		|| (entityType.equalsIgnoreCase("scorpion") && (type < 1 || type > 5))
            		|| (entityType.equalsIgnoreCase("elephant") && (type < 1 || type > 4))
            		|| (entityType.equalsIgnoreCase("dolphin") && (type < 1 || type > 6))
            		|| (entityType.equalsIgnoreCase("wyvern") && (type < 1 || type > 12))
            	)
            {
                iCommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "ERROR:" + EnumChatFormatting.WHITE + "The spawn type " + type + " for " + entityType + " is not a valid type."));
                return;
            }
            
            if (specialEntity != null)
            {
            	specialEntity.setHealth(specialEntity.getMaxHealth());
            	player.worldObj.spawnEntityInWorld(specialEntity);
            	MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAppear(specialEntity.getEntityId()), new TargetPoint(player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, 64));
            	MoCTools.playCustomSound(specialEntity, "appearmagic", player.worldObj);
            }
            
            if (specialWaterEntity != null)
            {
            	specialWaterEntity.setHealth(specialWaterEntity.getMaxHealth());
            	player.worldObj.spawnEntityInWorld(specialWaterEntity);
            	MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAppear(specialWaterEntity.getEntityId()), new TargetPoint(player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, 64));
            	MoCTools.playCustomSound(specialWaterEntity, "appearmagic", player.worldObj);
            }
        }
        else
        {
            iCommandSender.addChatMessage(new ChatComponentTranslation(EnumChatFormatting.RED + "/mocspawn <entity> <type as a number>"));
        }
    }

    /**
     * Returns a sorted list of all possible commands for the given ICommandSender.
     */
    protected List getSortedPossibleCommands(ICommandSender par1ICommandSender)
    {
        Collections.sort(commands);
        return commands;
    }

    public void sendCommandHelp(ICommandSender sender)
    {
        sender.addChatMessage(new ChatComponentTranslation("\u00a72Listing MoCreatures commands"));
        for (int i = 0; i < commands.size(); i++)
        {
            sender.addChatMessage(new ChatComponentTranslation(commands.get(i)));
        }
    }
}