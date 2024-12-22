package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntitySnail extends MoCEntityAmbient
{
    public MoCEntitySnail(World world)
    {
        super(world);
        setSize(0.2F, 0.2F);
        //texture = MoCreatures.proxy.MODEL_TEXTURE + "snaila.png";
    }

    @Override
    protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(22, Byte.valueOf((byte) 0));
    }

    @Override
	protected void applyEntityAttributes()
    {
      super.applyEntityAttributes();
      getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(getMoveSpeed());
      getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
    }

    @Override
    protected boolean isMovementCeased()
    {
        return (getIsHiding());
    }

    @Override
    public float getMoveSpeed()
    {
        return 0.15F;
    }

    @Override
    public void selectType()
    {
        if (getType() == 0)
        {
            setType(rand.nextInt(6)+1);
        }
    }

    @Override
    public ResourceLocation getTexture()
    {
        switch (getType())
        {
        case 1:
            return MoCreatures.proxy.getTexture("snaila.png");
        case 2:
            return MoCreatures.proxy.getTexture("snailb.png");
        case 3:
            return MoCreatures.proxy.getTexture("snailc.png");
        case 4:
            return MoCreatures.proxy.getTexture("snaild.png");
        case 5:
            return MoCreatures.proxy.getTexture("snaile.png");
        case 6:
            return MoCreatures.proxy.getTexture("snailf.png");
        default:
            return MoCreatures.proxy.getTexture("snaila.png");
        }
    }

    public boolean getIsHiding()
    {
        return (dataWatcher.getWatchableObjectByte(22) == 1);
    }

    public void setIsHiding(boolean flag)
    {
        byte input = (byte) (flag ? 1 : 0);
        dataWatcher.updateObject(22, Byte.valueOf(input));
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!worldObj.isRemote)
        {
            EntityLivingBase entityLiving = MoCTools.getScaryEntity(this, 3D);
            if ((entityLiving != null) && entityLiving.height > 0.5F && entityLiving.width > 0.5F && canEntityBeSeen(entityLiving))
            {
                if (!getIsHiding())
                {
                    setIsHiding(true);
                }
                setPathToEntity(null);
            }
            else
            {
                setIsHiding(false);
            }
            /**
             * snails without a shell won't hide
             */
            if (getIsHiding() && getType() > 4)
            {
                setIsHiding(false);
            }
        }
    }

    @Override
    protected void fall(float f)
    {
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        if (getIsHiding())
        {
            prevRenderYawOffset = renderYawOffset = rotationYaw = prevRotationYaw;
        }
    }

    @Override
    public boolean isOnLadder()
    {
        return isCollidedHorizontally;
    }

    public boolean climbing()
    {
        return !onGround && isOnLadder();
    }

    @Override
    public void jump()
    {
    }
    
    @Override
    protected void dropFewItems(boolean hasEntityBeenHitByPlayer, int levelOfLootingEnchantmentUsedToKillThisEntity)
    {  
        if (MoCreatures.proxy.slimyInsectsAndJellyfishDropSlimeballs)
        {
        	int randomAmount = rand.nextInt(2);
        	
        	dropItem(Items.slime_ball, randomAmount);
        }
    }
}