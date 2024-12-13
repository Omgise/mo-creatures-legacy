package drzhark.mocreatures.entity.monster;

import java.util.Iterator;
import java.util.List;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntityRat extends MoCEntityMob {
    public MoCEntityRat(World world)
    {
        super(world);
        setSize(0.5F, 0.5F);
    }

    @Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            int typeChance = rand.nextInt(100);
            if (typeChance <= 65)
            {
                setType(1);
            }
            else if (typeChance <= 98)
            {
                setType(2);
            }
            else
            {
                setType(3);
            }
        }
    }

    @Override
    protected double getAttackStrength() 
    {
        return 1D;
    }
    
    @Override
    public ResourceLocation getTexture()
    {
        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("ratb.png");
        case 2:
            return MoCreatures.proxy.getTexture("ratbl.png");
        case 3:
            return MoCreatures.proxy.getTexture("ratw.png");

        default:
            return MoCreatures.proxy.getTexture("ratb.png");
        }
    }

    @Override
    protected void attackEntity(Entity entity, float distanceToEntity)
    {
        float brightness = getBrightness(1.0F);
        if ((brightness > 0.5F) && (rand.nextInt(100) == 0))
        {
            entityToAttack = null;
            return;
        }
        else
        {
            super.attackEntity(entity, distanceToEntity);
            return;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float damageTaken)
    {
        if (super.attackEntityFrom(damageSource, damageTaken))
        {
            Entity entityThatAttackedThisCreature = damageSource.getEntity();
            if (entityThatAttackedThisCreature instanceof EntityPlayer)
            {
                entityToAttack = entityThatAttackedThisCreature;
            }
            if ((entityThatAttackedThisCreature instanceof EntityArrow) && (((EntityArrow) entityThatAttackedThisCreature).shootingEntity != null))
            {
                entityThatAttackedThisCreature = ((EntityArrow) entityThatAttackedThisCreature).shootingEntity;
            }
            if (entityThatAttackedThisCreature instanceof EntityLiving)
            {
                //TODO 4FIX TEST
                List entitiesNearbyList = worldObj.getEntitiesWithinAABB(MoCEntityRat.class, AxisAlignedBB.getBoundingBox(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D).expand(16D, 4D, 16D));
                Iterator iterator = entitiesNearbyList.iterator();
                do
                {
                    if (!iterator.hasNext())
                    {
                        break;
                    }
                    Entity entityNearby = (Entity) iterator.next();
                    
                    MoCEntityRat entityRatNearby = (MoCEntityRat) entityNearby;
                    
                    if ((entityRatNearby != null) && (entityRatNearby.entityToAttack == null))
                    {
                        entityRatNearby.entityToAttack = entityThatAttackedThisCreature;
                    }
                } while (true);
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean climbing()
    {
        return !onGround && isOnLadder();
    }

    @Override
    protected Entity findPlayerToAttack()
    {
        float brightness = getBrightness(1.0F);
        if (brightness < 0.5F) 
        {
            EntityPlayer entityPlayer = worldObj.getClosestVulnerablePlayerToEntity(this, 16D);
            
            return entityPlayer != null && canEntityBeSeen(entityPlayer) ? entityPlayer : null;
        }
        return null;
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:ratdying";
    }

    @Override
    protected Item getDropItem()
    {
        return MoCreatures.ratRaw;
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:rathurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "mocreatures:ratgrunt";
    }

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
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
}