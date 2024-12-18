package drzhark.mocreatures.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityTameableAquatic extends MoCEntityAquatic implements IMoCTameable
{
    public MoCEntityTameableAquatic(World world)
    {
        super(world);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(30, -1); // PetId    
    }

    @Override
	public int getOwnerPetId()
    {
        return dataWatcher.getWatchableObjectInt(30);
    }

    @Override
	public void setOwnerPetId(int i)
    {
        dataWatcher.updateObject(30, i);
    }

    @Override
	public boolean interact(EntityPlayer entityPlayer)
    {
        ItemStack itemStack = entityPlayer.getHeldItem();
        //before ownership check 
        if (
        		itemStack != null
        		&& getIsTamed() &&
        		itemStack.getItem() == MoCreatures.scrollOfOwner
                && MoCTools.isThisPlayerAnOP(entityPlayer)
        	)
        {
            if (--itemStack.stackSize == 0)
            {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                if (getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, getOwnerPetId());//getOwnerPetId());
                }
                setOwner("");
                
            }
            return true;
        }
        
        //if the player interacting is not the owner, do nothing!
        if (
        		MoCreatures.proxy.enableStrictOwnership
        		&& getOwnerName() != null
        		&& !(
        				getOwnerName().equals("")
        			)
        		&& !entityPlayer.getCommandSenderName().equals(getOwnerName())
        		&& !MoCTools.isThisPlayerAnOP((entityPlayer))
        	) 
        {
            return true; 
        }
        
        //Do not interact with player if the player is in wolf or werewolf from the Witchery mod
        if (MoCTools.isPlayerInWolfForm(entityPlayer) || MoCTools.isPlayerInWerewolfForm(entityPlayer))   
        {
            return true; 
        }

        //changes name
        if (MoCreatures.isServer() && itemStack != null && getIsTamed() && (itemStack.getItem() == MoCreatures.medallion))
        {
            if (MoCTools.tameWithName(entityPlayer, this))
            {
                return true;
            }
            return false;
        }
        
        //sets it free, untamed
        if (
        		itemStack != null
        		&& getIsTamed() 
                && itemStack.getItem() == MoCreatures.scrollFreedom
        		&& getOwnerName().length() > 0
                && (
                		entityPlayer.getCommandSenderName().equals(getOwnerName())
                		|| MoCTools.isThisPlayerAnOP(entityPlayer)
                	)
        	)
        {
            if (--itemStack.stackSize == 0)
            {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                if (getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, getOwnerPetId());//getOwnerPetId());
                }
                setOwner("");
                setName("");
                dropMyStuff();
                setTamed(false);
            }

            return true;
        }

        //removes owner, any other player can claim it by renaming it
        if (
        		itemStack != null
        		&& getIsTamed() 
                && itemStack.getItem() == MoCreatures.scrollOfSale
                && getOwnerName().length() > 0
                && (
                		entityPlayer.getCommandSenderName().equals(getOwnerName())
                		|| MoCTools.isThisPlayerAnOP(entityPlayer)
                	)
        	)
        {
            if (--itemStack.stackSize == 0)
            {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            if (MoCreatures.isServer())
            {
                if (getOwnerPetId() != -1) // required since getInteger will always return 0 if no key is found
                {
                    MoCreatures.instance.mapData.removeOwnerPet(this, getOwnerPetId());//getOwnerPetId());
                }
                setOwner("");
            }
            return true;
        }

        if ((itemStack != null) && getIsTamed() && isMyHealFood(itemStack))
        {
            if (--itemStack.stackSize == 0)
            {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            playSound("mocreatures:eating", 1.0F, 1.0F + ((rand.nextFloat() - rand.nextFloat()) * 0.2F));
            if (MoCreatures.isServer())
            {
                heal(5);
            }
            return true;
        }
        
        //stores in fishnet
        if (itemStack != null && itemStack.getItem() == MoCreatures.fishNet && itemStack.getItemDamage() == 0 && canBeTrappedInNet()) 
        {
        	//if the player using the amulet is not the owner
	        if (getOwnerName().length() > 0 && !(getOwnerName().equals(entityPlayer.getCommandSenderName())) && MoCreatures.instance.mapData != null)
	        {
	        	return false;
	        }
        	
            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            if (MoCreatures.isServer())
            {
                MoCPetData petData = MoCreatures.instance.mapData.getPetData(getOwnerName());
                if (petData != null)
                {
                    petData.setInAmulet(getOwnerPetId(), true);
                }
                dropMyStuff();
                MoCTools.dropAmuletWithNewPetInformation(this, 1);
                isDead = true;
            }

            return true;
        }
        return super.interact(entityPlayer);
    }

    // Fixes despawn issue when chunks unload and duplicated mounts when disconnecting on servers
    @Override
    public void setDead()
    {
        if (MoCreatures.isServer() && getIsTamed() && getHealth() > 0 && !riderIsDisconnecting  && !MoCreatures.isMobConfinementLoaded)   // the "!MoCreatures.isMobConfinementLoaded" allows setDead() to work on tamed creatures if the Mob Confinement mod is loaded. This is so that the mob confinement items don't duplicate tamed creatures when they try to store them.
        {
            return;
        }
        super.setDead();
    }

    /**
     * Play the taming effect, will either be hearts or smoke depending on status
     */
    @Override
	public void playTameEffect(boolean par1)
    {
        String particleName = "heart";

        if (!par1)
        {
            particleName = "smoke";
        }

        for (int index = 0; index < 7; ++index)
        {
            double xVelocity = rand.nextGaussian() * 0.02D;
            double yVelocity = rand.nextGaussian() * 0.02D;
            double zVelocity = rand.nextGaussian() * 0.02D;
            worldObj.spawnParticle(particleName, posX + rand.nextFloat() * width * 2.0F - width, posY + 0.5D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, xVelocity, yVelocity, zVelocity);
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeEntityToNBT(nbtTagCompound);
        if (getOwnerPetId() != -1)
            nbtTagCompound.setInteger("PetId", getOwnerPetId());
        if (this instanceof IMoCTameable && getIsTamed() && MoCreatures.instance.mapData != null)
        {
            MoCreatures.instance.mapData.updateOwnerPet(this, nbtTagCompound);
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey("PetId"))
            setOwnerPetId(nbtTagCompound.getInteger("PetId"));
        if (getIsTamed() && nbtTagCompound.hasKey("PetId"))
        {
            MoCPetData petData = MoCreatures.instance.mapData.getPetData(getOwnerName());
            if (petData != null)
            {
                NBTTagList tag = petData.getOwnerRootNBT().getTagList("TamedList", 10);
                for (int i = 0; i < tag.tagCount(); i++)
                {
                    NBTTagCompound nbt = tag.getCompoundTagAt(i);
                    if (nbt.getInteger("PetId") == nbtTagCompound.getInteger("PetId"))
                    {
                        // update amulet flag
                        nbt.setBoolean("InAmulet", false);
                        // check if cloned and if so kill
                        if (nbt.hasKey("Cloned"))
                        {
                            // entity was cloned
                            nbt.removeTag("Cloned"); // clear flag
                            setTamed(false);
                            setDead();
                        }
                    }
                }
            }
            else // no pet data was found, mocreatures.dat could of been deleted so reset petId to -1
            {
                setOwnerPetId(-1);
            }
        }
    }

    /**
     * If the rider should be dismounted from the entity when the entity goes under water
     *
     * @param rider The entity that is riding
     * @return if the entity should be dismounted when under water
     */
    @Override
	public boolean shouldDismountInWater(Entity rider){
        return !getIsTamed();
    }

    public boolean isBreedingItem(ItemStack itemStack)
    {
        return false;
    }

    // Override to fix heart animation on clients
    @Override
	@SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte par1)
    {
        if (par1 == 2)
        {
            limbSwingAmount = 1.5F;
            hurtResistantTime = maxHurtResistantTime;
            hurtTime = (maxHurtTime = 10);
            attackedAtYaw = 0.0F;
            playSound(getHurtSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            attackEntityFrom(DamageSource.generic, 0.0F);
        }
        else if (par1 == 3)
        {
            playSound(getDeathSound(), getSoundVolume(), (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
            setHealth(0.0F);
            onDeath(DamageSource.generic);
        }
    }

    @Override
    public float getPetHealth() {
        return getHealth();
    }

    @Override
    public boolean isRiderDisconnecting() {
        return riderIsDisconnecting;
    }
}