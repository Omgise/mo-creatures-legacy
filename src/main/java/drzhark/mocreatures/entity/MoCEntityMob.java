package drzhark.mocreatures.entity;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.animal.MoCEntityHorse;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHealth;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public abstract class MoCEntityMob extends EntityMob implements IMoCEntity//, IEntityAdditionalSpawnData
{
    protected boolean divePending;
    protected int maxHealth;
    private PathEntity entitypath;
    public EntityLiving roper;
    protected float moveSpeed;
    protected String texture;
    private boolean hasKilledPrey = false;

    public MoCEntityMob(World world)
    {
        super(world);
        setTamed(false);
        texture = "blank.jpg";
    }

    @Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(getMoveSpeed());
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(getAttackStrength());
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
    }

    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData entityLivingData)
    {
        selectType();
        return super.onSpawnWithEgg(entityLivingData);
    }

    @Override
	public ResourceLocation getTexture()
    {
        return MoCreatures.proxy.getTexture(texture);
    }

    protected double getAttackStrength() 
    {
        return 2D;
    }

    /**
     * Put your code to choose a texture / the mob type in here. Will be called
     * by default MocEntity constructors.
     */
    @Override
    public void selectType()
    {
        setType(1);
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(15, Byte.valueOf((byte) 0)); // isAdult - 0 false 1 true
        dataWatcher.addObject(16, Byte.valueOf((byte) 0)); // isTamed - 0 false 1 true
        dataWatcher.addObject(17, String.valueOf("")); // displayName empty string by default
        dataWatcher.addObject(18, Integer.valueOf(0)); // int ageTicks
        dataWatcher.addObject(19, Integer.valueOf(0)); // int type
    }

    @Override
	public void setType(int i)
    {
        dataWatcher.updateObject(19, Integer.valueOf(i));
    }

    @Override
    public int getType()
    {
        return dataWatcher.getWatchableObjectInt(19);
    }

    public boolean getShouldDisplayName()
    {
        return (getName() != null && !getName().equals(""));
    }

    @Override
    public boolean getIsAdult()
    {
        return (dataWatcher.getWatchableObjectByte(15) == 1);
    }

    @Override
    public boolean getIsTamed()
    {
        return (dataWatcher.getWatchableObjectByte(16) == 1);
    }

    @Override
    public String getName()
    {
        return dataWatcher.getWatchableObjectString(17);
    }

    @Override
	public int getMoCAge()
    {
        return dataWatcher.getWatchableObjectInt(18);
    }

    @Override
	public void setMoCAge(int i)
    {
        dataWatcher.updateObject(18, Integer.valueOf(i));
    }

    @Override
    public void setAdult(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(15, Byte.valueOf(input));
    }

    @Override
    public void setName(String name)
    {
        dataWatcher.updateObject(17, String.valueOf(name));
    }

    @Override
    public void setTamed(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(16, Byte.valueOf(input));
    }

    public boolean getCanSpawnHereLiving()
    {
        return worldObj.checkNoEntityCollision(boundingBox) && worldObj.getCollidingBoundingBoxes(this, boundingBox).size() == 0 && !worldObj.isAnyLiquid(boundingBox);
    }

    public boolean getCanSpawnHereCreature()
    {
        int xCoordinate = MathHelper.floor_double(posX);
        int yCoordinate = MathHelper.floor_double(boundingBox.minY);
        int zCoordinate = MathHelper.floor_double(posZ);
        return getBlockPathWeight(xCoordinate, yCoordinate, zCoordinate) >= 0.0F;
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return (MoCreatures.entityMap.get(getClass()).getFrequency() > 0 && super.getCanSpawnHere());
    }

    public boolean getCanSpawnHereMob()
    {
        int xCoordinate = MathHelper.floor_double(posX);
        int yCoordinate = MathHelper.floor_double(boundingBox.minY);
        int zCoordinate = MathHelper.floor_double(posZ);
        if (worldObj.getSavedLightValue(EnumSkyBlock.Sky, xCoordinate, yCoordinate, zCoordinate) > rand.nextInt(32)) { return false; }
        int l = worldObj.getBlockLightValue(xCoordinate, yCoordinate, zCoordinate);
        if (worldObj.isThundering())
        {
            int i1 = worldObj.skylightSubtracted;
            worldObj.skylightSubtracted = 10;
            l = worldObj.getBlockLightValue(xCoordinate, yCoordinate, zCoordinate);
            worldObj.skylightSubtracted = i1;
        }
        return l <= rand.nextInt(8);
    }

    @Override
	public boolean shouldEntityBeIgnored(Entity entity)
    {
        return 
        	(
        		(!(entity instanceof EntityLiving)) 
                || entity instanceof EntityMob
                || entity instanceof MoCEntityEgg
                || (entity instanceof EntityPlayer && getIsTamed()) 
                || (entity instanceof MoCEntityKittyBed) || (entity instanceof MoCEntityLitterBox) 
                || (getIsTamed() && (entity instanceof MoCEntityAnimal && ((MoCEntityAnimal) entity).getIsTamed())) 
                || ((entity instanceof EntityWolf) && !(MoCreatures.proxy.attackWolves)) 
                || ((entity instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses))
                || (entity instanceof MoCEntityAnimal || entity instanceof MoCEntityAmbient)
        	);
    }
    

    public boolean isPredator()
    {
    	return false;
    }
    
    @Override
	public void onKillEntity(EntityLivingBase entityLiving)
    {
    	if (isPredator() && MoCreatures.proxy.destroyDrops)
    	{
    		if (!(entityLiving instanceof EntityPlayer) && !(entityLiving instanceof EntityMob))
    		{
	    		hasKilledPrey = true;
    		}
    	}
    }

    @Override
    public boolean checkSpawningBiome()
    {
        return true;
    }

    @Override
    public void onLivingUpdate()
    {
        if (MoCreatures.isServer() && forceUpdates() && rand.nextInt(1000) == 0)
        {
            MoCTools.forceDataSync(this);
        }

        if (MoCreatures.isServer() && getIsTamed() && rand.nextInt(200) == 0)
        {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(getEntityId(), getHealth()), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 64));
        }
        
        if (isPredator() && hasKilledPrey) 
        {
        	if (MoCreatures.proxy.destroyDrops) //destroy the drops of the prey
        	{
            	List entitiesNearbyList = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox.expand(3, 3, 3));

            	int iterationLength = entitiesNearbyList.size();
            	
            	if (iterationLength > 0)
            	{
	                for (int index = 0; index < iterationLength; index++)
	                {
	                    Entity entityNearby = (Entity) entitiesNearbyList.get(index);
	                    if (!(entityNearby instanceof EntityItem))
	                    {
	                        continue;
	                    }
	                    
	                    EntityItem entityItem = (EntityItem) entityNearby;
	                    
	                    if ((entityItem != null) && (entityItem.age < 5)) //targeting entityItem with age below 5 makes sure that the predator only eats the items that are dropped from the prey
	                    {
	                        entityItem.setDead();
	                    }
	                }
            	}
        	}
            
        	heal(5);
    		
            hasKilledPrey = false;
        }
        
        moveSpeed = getMoveSpeed();
        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float damageTaken)
    {
        if (MoCreatures.isServer() && getIsTamed())
        {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(getEntityId(), getHealth()), new TargetPoint(worldObj.provider.dimensionId, posX, posY, posZ, 64));
        }
        return super.attackEntityFrom(damageSource, damageTaken);
    }

    /**
     * Boolean used to select pathfinding behavior
     * 
     * @return
     */
    public boolean isFlyer()
    {
        return false;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbtTagCompound)
    {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean("Tamed", getIsTamed());
        nbtTagCompound.setBoolean("Adult", getIsAdult());
        nbtTagCompound.setInteger("Age", getMoCAge());
        nbtTagCompound.setString("Name", getName());
        nbtTagCompound.setInteger("TypeInt", getType());

    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbtTagCompound)
    {
        super.readEntityFromNBT(nbtTagCompound);
        setTamed(nbtTagCompound.getBoolean("Tamed"));
        setAdult(nbtTagCompound.getBoolean("Adult"));
        setMoCAge(nbtTagCompound.getInteger("Age"));
        setName(nbtTagCompound.getString("Name"));
        setType(nbtTagCompound.getInteger("TypeInt"));

    }

    @Override
    protected void fall(float f)
    {
        if (!isFlyer())
        {
            super.fall(f);
        }
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        if (isFlyer())
        {
            EntityPlayer entityPlayer = worldObj.getClosestVulnerablePlayerToEntity(this, 20D);
            if ((entityPlayer != null) && canEntityBeSeen(entityPlayer))
            {
                return entityPlayer;
            }
            else
            {
                return null;
            }
        }
        else
        {
            return super.findPlayerToAttack();
        }
    }

    @Override
    public boolean isOnLadder()
    {
        if (isFlyer())
        {
            return false;
        }
        else
        {
            return super.isOnLadder();
        }
    }

    @Override
    public void moveEntityWithHeading(float f, float f1)
    {
        if (!isFlyer())
        {
            super.moveEntityWithHeading(f, f1);
            return;
        }

        if (handleWaterMovement())
        {
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.80000001192092896D;
            motionY *= 0.80000001192092896D;
            motionZ *= 0.80000001192092896D;
        }
        else if (handleLavaMovement())
        {
            moveFlying(f, f1, 0.02F);
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.5D;
            motionY *= 0.5D;
            motionZ *= 0.5D;
        }
        else
        {
            float f2 = 0.91F;
            if (onGround)
            {
                f2 = 0.5460001F;
                Block block = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if (block != Blocks.air)
                {
                    f2 = block.slipperiness * 0.91F;
                }
            }
            float f3 = 0.162771F / (f2 * f2 * f2);
            moveFlying(f, f1, onGround ? 0.1F * f3 : 0.02F);
            f2 = 0.91F;
            if (onGround)
            {
                f2 = 0.5460001F;
                Block block = worldObj.getBlock(MathHelper.floor_double(posX), MathHelper.floor_double(boundingBox.minY) - 1, MathHelper.floor_double(posZ));
                if (block != Blocks.air)
                {
                    f2 = block.slipperiness * 0.91F;
                }
            }
            moveEntity(motionX, motionY, motionZ);
            motionX *= f2;
            motionY *= f2;
            motionZ *= f2;
            if (isCollidedHorizontally)
            {
                motionY = 0.20000000000000001D;
            }
            if (entityToAttack != null && entityToAttack.posY < posY && rand.nextInt(30) == 0)
            {
                motionY = -0.25D;
            }
        }
        prevLimbSwingAmount = limbSwingAmount;
        double d2 = posX - prevPosX;
        double d3 = posZ - prevPosZ;
        float f4 = MathHelper.sqrt_double((d2 * d2) + (d3 * d3)) * 4.0F;
        if (f4 > 1.0F)
        {
            f4 = 1.0F;
        }

        limbSwingAmount += (f4 - limbSwingAmount) * 0.4F;
        limbSwing += limbSwingAmount;
    }

    @Override
    protected void updateEntityActionState()
    {
        if (!isFlyer())
        {
            super.updateEntityActionState();
            return;
        }

        hasAttacked = false;
        float f = 16F;
        if (entityToAttack == null)
        {
            entityToAttack = findPlayerToAttack();
            if (entityToAttack != null)
            {
                entitypath = worldObj.getPathEntityToEntity(this, entityToAttack, f, true, false, false, true);
            }
        }
        else if (!entityToAttack.isEntityAlive())
        {
            entityToAttack = null;
        }
        else
        {
            float f1 = entityToAttack.getDistanceToEntity(this);
            if (canEntityBeSeen(entityToAttack))
            {
                attackEntity(entityToAttack, f1);
            }
        }
        if (!hasAttacked && (entityToAttack != null) && ((entitypath == null) || (rand.nextInt(10) == 0)))
        {
            entitypath = worldObj.getPathEntityToEntity(this, entityToAttack, f, true, false, false, true);
        }
        else if (((entitypath == null) && (rand.nextInt(80) == 0)) || (rand.nextInt(80) == 0))
        {
            boolean flag = false;
            int j = -1;
            int k = -1;
            int l = -1;
            float f2 = -99999F;
            for (int i1 = 0; i1 < 10; i1++)
            {
                int j1 = MathHelper.floor_double((posX + rand.nextInt(13)) - 6D);
                int k1 = MathHelper.floor_double((posY + rand.nextInt(7)) - 3D);
                int l1 = MathHelper.floor_double((posZ + rand.nextInt(13)) - 6D);
                float f3 = getBlockPathWeight(j1, k1, l1);
                if (f3 > f2)
                {
                    f2 = f3;
                    j = j1;
                    k = k1;
                    l = l1;
                    flag = true;
                }
            }

            if (flag)
            {
                entitypath = worldObj.getEntityPathToXYZ(this, j, k, l, 10F, true, false, false, true);
            }
        }
        int i = MathHelper.floor_double(boundingBox.minY);
        boolean flag1 = handleWaterMovement();
        boolean flag2 = handleLavaMovement();
        rotationPitch = 0.0F;
        if ((entitypath == null) || (rand.nextInt(100) == 0))
        {
            super.updateEntityActionState();
            entitypath = null;
            return;
        }
        //TODO 4FIX test!
        Vec3 vectorThreeDimensional = entitypath.getPosition(this); //client

        //vectorThreeDimensional vectorThreeDimensional = entitypath.getPosition(this); //server
        for (double d = width * 2.0F; (vectorThreeDimensional != null) && (vectorThreeDimensional.squareDistanceTo(posX, vectorThreeDimensional.yCoord, posZ) < (d * d));)
        {
            entitypath.incrementPathIndex();
            if (entitypath.isFinished())
            {
                vectorThreeDimensional = null;
                entitypath = null;
            }
            else
            {
                //vectorThreeDimensional = entitypath.getPosition(this); //server
                //TODO 4FIX test!
                vectorThreeDimensional = entitypath.getPosition(this);
            }
        }

        isJumping = false;
        if (vectorThreeDimensional != null)
        {
            double d1 = vectorThreeDimensional.xCoord - posX;
            double d2 = vectorThreeDimensional.zCoord - posZ;
            double d3 = vectorThreeDimensional.yCoord - i;
            float angleInDegreesToNewLocation = (float) ((Math.atan2(d2, d1) * 180D) / Math.PI) - 90F;
            float amountOfDegreesToChangeRotationYawBy = angleInDegreesToNewLocation - rotationYaw;
            moveForward = (float)getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
            for (; amountOfDegreesToChangeRotationYawBy < -180F; amountOfDegreesToChangeRotationYawBy += 360F)
            {
            }
            for (; amountOfDegreesToChangeRotationYawBy >= 180F; amountOfDegreesToChangeRotationYawBy -= 360F)
            {
            }
            if (amountOfDegreesToChangeRotationYawBy > 30F)
            {
                amountOfDegreesToChangeRotationYawBy = 30F;
            }
            if (amountOfDegreesToChangeRotationYawBy < -30F)
            {
                amountOfDegreesToChangeRotationYawBy = -30F;
            }
            rotationYaw += amountOfDegreesToChangeRotationYawBy;
            if (hasAttacked && (entityToAttack != null))
            {
                double xDistance = entityToAttack.posX - posX;
                double zDistance = entityToAttack.posZ - posZ;
                float previousRotationYaw = rotationYaw;
                rotationYaw = (float) ((Math.atan2(zDistance, xDistance) * 180D) / Math.PI) - 90F;
                float angleInDegreesBetweenPreviousAndNewRotationYaw = (((previousRotationYaw - rotationYaw) + 90F) * (float) Math.PI) / 180F;
                moveStrafing = -MathHelper.sin(angleInDegreesBetweenPreviousAndNewRotationYaw) * moveForward * 1.0F;
                moveForward = MathHelper.cos(angleInDegreesBetweenPreviousAndNewRotationYaw) * moveForward * 1.0F;
            }
            if (d3 > 0.0D)
            {
                isJumping = true;
            }
        }
        if (entityToAttack != null)
        {
            faceEntity(entityToAttack, 30F, 30F);
        }
        if (isCollidedHorizontally)
        {
            isJumping = true;
        }
        if ((rand.nextFloat() < 0.8F) && (flag1 || flag2))
        {
            isJumping = true;
        }
    }

    /**
     * Used to synchronize the attack animation between server and client
     * 
     * @param attackType
     */
    @Override
    public void performAnimation(int attackType)
    {
    }

    public float getMoveSpeed()
    {
        return 0.7F;
    }

    @Override
    public int nameYOffset()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public double roperYOffset()
    {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean shouldRenderName()
    {
        return getShouldDisplayName() && (riddenByEntity == null);
    }

    @Override
    public Entity getRoper()
    {
        return roper;
    }

    @Override
    public boolean updateMount()
    {
        return false;
    }

    @Override
    public boolean forceUpdates()
    {
        return false;
    }

    protected Vec3 findPossibleShelter()
    {
        Random var1 = getRNG();

        for (int var2 = 0; var2 < 10; ++var2)
        {
            int var3 = MathHelper.floor_double(posX + var1.nextInt(20) - 10.0D);
            int var4 = MathHelper.floor_double(boundingBox.minY + var1.nextInt(6) - 3.0D);
            int var5 = MathHelper.floor_double(posZ + var1.nextInt(20) - 10.0D);

            if (!worldObj.canBlockSeeTheSky(var3, var4, var5) && getBlockPathWeight(var3, var4, var5) < 0.0F) { return Vec3.createVectorHelper(var3, var4, var5); }
        }

        return null;
    }

    @Override
    public void makeEntityJump()
    {
        //TODO
    }

    @Override
    public void makeEntityDive()
    {
        divePending = true;
    }

    @Override
    protected boolean canDespawn()
    {
        return !getIsTamed();
    }

    @Override
    public void setDead()
    {
        // Server check required to prevent tamed entities from being duplicated on client-side
        if (MoCreatures.isServer() && (getIsTamed()) && getHealth() > 0 && !MoCreatures.isMobConfinementLoaded)   // the "!MoCreatures.isMobConfinementLoaded" allows setDead() to work on tamed creatures if the Mob Confinement mod is loaded. This is so that the mob confinement items don't duplicate tamed creatures when they try to store them.
        {
        	return;
        }
        super.setDead();
    }

    @Override
    public float getSizeFactor()
    {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset()
    {
        return 0F;
    }

    protected void getPathOrWalkableBlock(Entity entity, float f)
    {
        PathEntity pathEntity = worldObj.getPathEntityToEntity(this, entity, 16F, true, false, false, true);
        if ((pathEntity == null) && (f > 12F))
        {
            int i = MathHelper.floor_double(entity.posX) - 2;
            int j = MathHelper.floor_double(entity.posZ) - 2;
            int k = MathHelper.floor_double(entity.boundingBox.minY);
            for (int l = 0; l <= 4; l++)
            {
                for (int i1 = 0; i1 <= 4; i1++)
                {
                    if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && worldObj.getBlock(i + l, k - 1, j + i1).isNormalCube() && !worldObj.getBlock(i + l, k, j + i1).isNormalCube() && !worldObj.getBlock(i + l, k + 1, j + i1).isNormalCube())
                    {
                        setLocationAndAngles((i + l) + 0.5F, k, (j + i1) + 0.5F, rotationYaw, rotationPitch);
                        return;
                    }
                }

            }
        }
        else
        {
            setPathToEntity(pathEntity);
        }
    }

    @Override
    public String getOwnerName()
    {
        return "";
    }

    @Override
    public void setOwner(String par1Str)
    {
    }

    @Override
    public void setArmorType(byte i) {}

    public byte getArmorType() 
    {        
        return 0;
    }
    
    @Override
    public void dismountEntity() {}

    @Override
    public int pitchRotationOffset() 
    {
        return 0;
    }

    @Override
    public int rollRotationOffset() 
    {
        return 0;
    }

    @Override
    public int yawRotationOffset()
    {
        return 0;
    }

    @Override
    public float getAdjustedZOffset()
    {
        return 0F;
    }
    
    @Override
    public float getAdjustedXOffset()
    {
        return 0F;
    }

    @Override
    public void riderIsDisconnecting(boolean flag)
    {
    }
}