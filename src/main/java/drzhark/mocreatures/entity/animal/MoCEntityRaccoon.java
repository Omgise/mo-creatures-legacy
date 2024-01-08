package drzhark.mocreatures.entity.animal;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class MoCEntityRaccoon extends MoCEntityTameableAnimal{

    public MoCEntityRaccoon(World world)
    {
        super(world);
        setSize(0.8F, 0.8F);
        texture = "raccoon.png";
        setMoCAge(70 + rand.nextInt(30));
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
    }
    
    @Override
    protected boolean canDespawn()
    {
        return !getIsTamed() && this.ticksExisted > 2400;
    }

    @Override
    protected void attackEntity(Entity entity, float f)
    {
        if (attackTime <= 0 && (f < 2.0D) && (entity.boundingBox.maxY > boundingBox.minY) && (entity.boundingBox.minY < boundingBox.maxY))
        {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
            if (!(entity instanceof EntityPlayer))
            {
                MoCTools.destroyDrops(this, 3D);
            }
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i)
    {
        if (super.attackEntityFrom(damagesource, i))
        {
            Entity entity = damagesource.getEntity();
            if ((riddenByEntity == entity) || (ridingEntity == entity)) { return true; }
            if ((entity != this) && (worldObj.difficultySetting.getDifficultyId() > 0))
            {
                entityToAttack = entity;
            }
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean interact(EntityPlayer entityplayer)
    {
        if (super.interact(entityplayer)) { return false; }
        ItemStack itemstack = entityplayer.inventory.getCurrentItem();
        if ((itemstack != null) && (isItemEdible(itemstack.getItem()))) //((itemstack.getItem() == MoCreatures.rawTurkey.itemID)))
        {
            if (--itemstack.stackSize == 0)
            {
                entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
            }

            if (MoCreatures.isServer())
            {
                MoCTools.tameWithName(entityplayer, this);
            }
            this.setHealth(getMaxHealth());

            if (MoCreatures.isServer() && !getIsAdult() && (getMoCAge() < 100))
            {
                setMoCAge(getMoCAge() + 1);
            }

            return true;
        }
        return false;
    }
    
    @Override
    public boolean entitiesToIgnore(Entity entity) //don't hunt the following mobs below
    {
        return (super.entitiesToIgnore(entity) //including the mobs specified in parent file
                    || (entity instanceof MoCEntityRaccoon)
                    ||(!getIsAdult() && ((entity.width > 0.5D) || (entity.height > 0.5D)))
                    || (entity instanceof MoCEntityFox)
                    );
    }
    

    @Override
    protected Entity findPlayerToAttack()
    {
        if ((rand.nextInt(80) == 0) && (worldObj.difficultySetting.getDifficultyId() > 0))
        {
            EntityLivingBase entityliving = getClosestEntityLiving(this, 8D);
            return entityliving;
        }
        else
        {
            return null;
        }
    }

    @Override
    protected String getDeathSound()
    {
        return "mocreatures:raccoondying";
    }

    @Override
    protected Item getDropItem()
    {
        return MoCreatures.fur;
    }

    @Override
    protected String getHurtSound()
    {
        return "mocreatures:raccoonhurt";
    }

    @Override
    protected String getLivingSound()
    {
        return "mocreatures:raccoongrunt";
    }

    @Override
    public int nameYOffset()
    {
        return -30;
    }

    @Override
    public double roperYOffset()
    {
        return 1.15D;
    }

    @Override
    public float getSizeFactor() 
    {   
        return 0.8F * (float)getMoCAge() * 0.01F;
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (MoCreatures.isServer())
        {
            if (!getIsAdult() && rand.nextInt(300) == 0)
            {
                setMoCAge(getMoCAge() + 1);
                if (getMoCAge() >= 100)
                {
                    setAdult(true);
                }
            }
        }
    }
    
    @Override
    public int getTalkInterval()
    {
        return 400;
    }

    @Override
    public int getMaxSpawnedInChunk()
    {
        return 2;
    }
}