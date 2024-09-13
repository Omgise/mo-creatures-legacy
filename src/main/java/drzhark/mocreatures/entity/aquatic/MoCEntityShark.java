package drzhark.mocreatures.entity.aquatic;

import java.util.List;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.animal.MoCEntityHorse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityShark extends MoCEntityTameableAquatic {
	
    public MoCEntityShark(World world)
    {
        super(world);
        texture = "shark.png";
        setSize(1.5F, 0.8F);
        setMoCAge(100 + rand.nextInt(100));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
    }
    
    @Override
    public boolean isPredator()
    {
    	return true;
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
    }
    
    @Override
    protected boolean canDespawn()
    {
        return !getIsTamed() && ticksExisted > 2400;
    }

    @Override
    protected void attackEntity(Entity entity, float distanceToEntity)
    {
        if ((distanceToEntity < 3.5D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY) && (getMoCAge() >= 100))
        {
            if (entity instanceof EntityPlayer && ((EntityPlayer) entity).ridingEntity != null)
            {
                Entity entityThatPlayerIsRiding = ((EntityPlayer)entity).ridingEntity;
                
                if (entityThatPlayerIsRiding instanceof MoCEntityDolphin) 
                {
                    return; //don't attack players who are riding a dolphin
                }
                
                if (checkPlayerIsRidingBoat((EntityPlayer) entity) && (worldObj.difficultySetting.getDifficultyId() > 2)) //if player is riding boat and is on hard difficulty 
                {
                	if (distanceToEntity < 2D && rand.nextInt(100) < 10) //10% chance to hit player's boat
                	{ 
                		if (rand.nextInt(100) < 10) //10% chance to break players boat each hit
                		{
	                        for (int index = 0; index < 2; ++index)
	                        {
	                        	entityThatPlayerIsRiding.func_145778_a(Items.stick, 1, 0.0F);
	                        }
	                        
							worldObj.playSoundAtEntity(entityThatPlayerIsRiding, "mob.zombie.woodbreak", 1, 1);  //play door break sound
	                        
	                        entityThatPlayerIsRiding.setDead();
                		}
                		else
                		{
                			worldObj.playSoundAtEntity(entityThatPlayerIsRiding, "mob.zombie.wood", 1, 1); //play door hit sound
                			return;
                		};
                        
                        
                	}
                	else {return;}
                }
            }
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float damageTaken)
    {
        if (super.attackEntityFrom(damageSource, damageTaken) && (worldObj.difficultySetting.getDifficultyId() > 0))
        {
            Entity entityThatAttackedThisCreature = damageSource.getEntity();
            if ((riddenByEntity == entityThatAttackedThisCreature) || (ridingEntity == entityThatAttackedThisCreature)) { return true; }
            
            if (entityThatAttackedThisCreature != this
            	&& !( //don't attack back if the attacking mob is one of the following mobs below
            			entityThatAttackedThisCreature instanceof EntityMob   //this also stops sharks from fighting Guardians from the Village Names mod
                    )
            	)   
            {
                entityToAttack = entityThatAttackedThisCreature;
                return true;
            }
            return false;
        }
        else
        {
            return false;
        }
    }

    @Override
    protected void dropFewItems(boolean flag, int x)
    {
        int dropChance = rand.nextInt(100);
        if (dropChance < 90)
        {
            int amountOfTeethToDrop = rand.nextInt(3) + 1;
            
            for (int index = 0; index < amountOfTeethToDrop; index++)
            {
                entityDropItem(new ItemStack(MoCreatures.sharkTeeth, 1, 0), 0.0F);
            }
        }
        else if ((worldObj.difficultySetting.getDifficultyId() > 0) && (getMoCAge() > 150) && dropChance < 40)
        {
            int amountOfEggsToDrop = rand.nextInt(3);
            for (int index1 = 0; index1 < amountOfEggsToDrop; index1++)
            {
                entityDropItem(new ItemStack(MoCreatures.mocegg, 1, 11), 0.0F);
            }
        }
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if ((worldObj.difficultySetting.getDifficultyId() > 0) && (getMoCAge() >= 100))
        {
            EntityPlayer closestEntityPlayer = worldObj.getClosestVulnerablePlayerToEntity(this, 16D);
            
            if
            (
            		(closestEntityPlayer != null)
            		&& closestEntityPlayer.isInWater()
            		&& !getIsTamed()
            		&& !(closestEntityPlayer.ridingEntity instanceof MoCEntityDolphin)
            		&& !(checkPlayerIsRidingBoat(closestEntityPlayer) && (worldObj.difficultySetting.getDifficultyId() < 3) && (rand.nextInt(100) > 40)) //40% chance to target players in boat if world difficulty is below hard
            )
            {
            	return closestEntityPlayer;
            }
            
            if (rand.nextInt(200) == 0)  // hunting cooldown between each prey
            {
                EntityLivingBase entityLiving = getClosestEntityLivingThatCanBeHunted(this, 16D);
                if ((entityLiving != null) && !(entityLiving instanceof EntityPlayer)) { return entityLiving; }
            }
        }
        
        if (MoCreatures.proxy.specialPetsDefendOwner)
        {
	        if (getIsTamed()) //defend owner if they are attacked by an entity
	    	{
	    		EntityPlayer ownerOfEntityThatIsOnline = MinecraftServer.getServer().getConfigurationManager().func_152612_a(getOwnerName());
	    		
	    		if (ownerOfEntityThatIsOnline != null)
	    		{
	    			double distanceToOwner = MoCTools.getSqDistanceTo(this, ownerOfEntityThatIsOnline.posX, ownerOfEntityThatIsOnline.posY, ownerOfEntityThatIsOnline.posZ);
	    			
	    			if (distanceToOwner < 20.0D)
	    			{
		    			EntityLivingBase entityThatAttackedOwner = ownerOfEntityThatIsOnline.getAITarget();
		    			
		    			if (entityThatAttackedOwner != null)
		    			{
		    				return entityThatAttackedOwner;
		    			}
	    			}
	    		}
	    	}
        }
        return null;
    }
    
    private boolean checkPlayerIsRidingBoat(EntityPlayer player)
    {
    	Entity entityThatPlayerIsRiding = player.ridingEntity;
    	
    	if (
    			entityThatPlayerIsRiding instanceof EntityBoat
    			|| (
    					MoCreatures.isEtFuturumRequiemLoaded
		    			&&
		    				(
		    					EntityList.getEntityString(entityThatPlayerIsRiding).equals("etfuturum.new_boat")
		    					|| EntityList.getEntityString(entityThatPlayerIsRiding).equals("etfuturum.chest_boat")
		    				)
    				)
    		)
    	{
    		return true;
    	}
    	
    	return false;
    }

    public EntityLivingBase getClosestEntityLivingThatCanBeHunted(Entity entity, double distance)
    {
        double currentMinimumDistance = -1D;
        EntityLivingBase entityLiving = null;
        List entitiesNearbyList = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(distance, distance, distance));
        
        int iterationLength = entitiesNearbyList.size();
        
        if (iterationLength > 0)
        {
	        for (int index = 0; index < iterationLength; index++)
	        {
	            Entity entityNearby = (Entity) entitiesNearbyList.get(index);
	            
	            if (!(entityNearby instanceof EntityLivingBase)
	            		|| (!((entityNearby instanceof MoCEntityAquatic) || (entityNearby instanceof MoCEntityTameableAquatic)) && !(entityNearby.isInWater()) // don't attack if mob is not aquatic and not in water
	                    || (entityNearby instanceof MoCEntityShark) // don't attack mobs below as well
	                    || (entityNearby == entity.riddenByEntity) 
	                    || (entityNearby == entity.ridingEntity)
	                    || (entityNearby instanceof IMob || entityNearby instanceof EntityMob || entityNearby instanceof MoCEntityMob) // don't attack if creature is a mob (eg: slime)
	                    || (entityNearby instanceof MoCEntityDolphin || entityNearby instanceof MoCEntityJellyFish)
	                    || (getIsTamed() && (entityNearby instanceof IMoCEntity) && ((IMoCEntity)entityNearby).getIsTamed() ) 
	                    || ((entityNearby instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses)) 
	                    || ((entityNearby instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves)))
	            	)
	            	
	            	{ continue;}
	            
	            double overallDistanceSquared = entityNearby.getDistanceSq(entity.posX, entity.posY, entity.posZ);
	            
	            if (((distance < 0.0D) || (overallDistanceSquared < (distance * distance))) && ((currentMinimumDistance == -1D) || (overallDistanceSquared < currentMinimumDistance)) && ((EntityLivingBase) entityNearby).canEntityBeSeen(entity))
	            {
	                currentMinimumDistance = overallDistanceSquared;
	                entityLiving = (EntityLivingBase) entityNearby;
	            }
	        }
        }
        
        return entityLiving;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!worldObj.isRemote)
        {
            if (!getIsAdult() && (rand.nextInt(50) == 0))
            {
                setMoCAge(getMoCAge() + 1);
                if (getMoCAge() >= 200)
                {
                    setAdult(true);
                }
            }
        }
    }

    @Override
    public boolean shouldRenderName()
    {
        return getDisplayName();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readEntityFromNBT(nbtTagCompound);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeEntityToNBT(nbtTagCompound);
    }

    @Override
    public boolean isMyHealFood(ItemStack itemstack)
    {
    	if (itemstack != null)
    	{
	    	Item item = itemstack.getItem();
	    	
	    	List<String> oreDictionaryNameArray = MoCTools.getOreDictionaryEntries(itemstack);
	    	
	    	return
	    		(
        			(item == Items.fish && itemstack.getItemDamage() != 3) //any vanilla mc raw fish except a pufferfish
        			|| oreDictionaryNameArray.contains("listAllfishraw")
        		);
    	}
    	return false;
    }
}