package drzhark.mocreatures.entity.witchery_integration;

import drzhark.mocreatures.MoCreatures;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.world.World;

public class MoCEntityWerewolfMinecraftComesAliveVillagerWitchery extends EntityVillager {

	private boolean isTransforming;
	private int transformCounter;
	
	public MoCEntityWerewolfMinecraftComesAliveVillagerWitchery(World world)
	{
		super(world);
	}
	
	public MoCEntityWerewolfMinecraftComesAliveVillagerWitchery(World world, int hairColor, int profession, int skinID)
	{
		super(world);
		
		setHairColor(hairColor);
		setProfession(profession);
		setSkinID(skinID);
		
	}
	
	@Override
	protected void entityInit()
    {
        super.entityInit();
        dataWatcher.addObject(17, Integer.valueOf(0)); //int hairColor
        dataWatcher.addObject(18, Integer.valueOf(0)); //int skinID
        
    }
	
	@Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        if (!worldObj.isRemote)
        {
            if (IsNight() && (rand.nextInt(250) == 0) && !isTransforming)
            {
                isTransforming = true;
            }
        }
        
        if (isTransforming && (rand.nextInt(3) == 0))
        {
            transformCounter++;
            if ((transformCounter % 2) == 0)
            {
                posX += 0.29999999999999999D;
                posY += transformCounter / 30;
                attackEntityFrom(DamageSource.causeMobDamage(this), 0);
            }
            if ((transformCounter % 2) != 0)
            {
                posX -= 0.29999999999999999D;
            }
            if (transformCounter > 30)
            {
                Transform();
                transformCounter = 0;
                isTransforming = false;
            }
        }
    }
	
	@Override
    public boolean attackEntityFrom(DamageSource damageSource, float damageTaken)
    {
    	
        Entity entityThatAttackedThisCreature = damageSource.getEntity();
        
        if (entityThatAttackedThisCreature != null && !(entityThatAttackedThisCreature instanceof EntityPlayer))
        {		
	        if (MoCreatures.isWitcheryLoaded && EntityList.getEntityString(entityThatAttackedThisCreature).equals("witchery.witchhunter"))
	        {	//fixes bug with Witchery witch hunter silver bolt damage
	        	damageTaken = 5;
	        	damageSource = DamageSource.generic;
	        }
        }
        
        return super.attackEntityFrom(damageSource, damageTaken);
    }
	
	private void Transform()
    {
        if (deathTime > 0) { return; }
        
        isTransforming = false;
        
        MoCEntityWerewolfWitchery werewolf = new MoCEntityWerewolfWitchery(worldObj, getHairColor() + 1, getProfession(), getSkinID());
        werewolf.copyLocationAndAnglesFrom(this);
        setDead();
        werewolf.worldObj.spawnEntityInWorld(werewolf); 
    }
	
	public boolean IsNight()
    {
        return !worldObj.isDaytime();
    }
	
	private int getHairColor()
	{
		return dataWatcher.getWatchableObjectInt(17);
	}
	
	private void setHairColor(int hairColor)
    {
        dataWatcher.updateObject(17, Integer.valueOf(hairColor));
    }


    private int getSkinID()
    {
        return dataWatcher.getWatchableObjectInt(18);
    }
	
    private void setSkinID(int skinID)
    {
        dataWatcher.updateObject(18, Integer.valueOf(skinID));
    }
	
	@Override
	public void useRecipe(MerchantRecipe merchantRecipie)
    {
        merchantRecipie.incrementToolUses();
    }
	
	
    public ResourceLocation getTexture()
    {
    	
    	StringBuilder fileName = new StringBuilder("");
    	
    	switch (getHairColor())
        {
	        case 0: //black hair
	            fileName.append("black_hair");
	            break;
	        case 1: //white hair
	        	fileName.append("white_hair");
	            break;
	            
	        case 2: //brown hair
	        	fileName.append("brown_hair");
	            break;
	
	        default:
	        	fileName.append("brown_hair");
	            break;
        }
    	
    	switch (getProfession())
        {
	        case 0: //farmer
	            fileName.append("_farmer_");
	            
	            break;
	        case 1: //librarian
	        	fileName.append("_librarian_");
	            break;
	            
	        case 2: //priest
	        	fileName.append("_priest_");
	            break;
	            
	        case 3: //blacksmith
	        	fileName.append("_blacksmith_");
	            break;
	            
	        case 4: //butcher
	        	fileName.append("_butcher_");
	            break;
	
	        default:
	        	fileName.append("_farmer_");
	            break;
        }
    	
        fileName.append(getSkinID());
        
        fileName.append(".png");
        
        return new ResourceLocation("mocreatures" + ":" + "textures/models/minecraft_comes_alive_witchery_werewolf_villager/" + fileName);
    }
	
	@Override
	public void func_110297_a_(ItemStack p_110297_1_) //makes the villager not play sounds when trading
    {
    }
	
	@Override
    protected String getLivingSound()
    {
        return null;
    }

	@Override
    protected String getHurtSound()
    {
        return "game.neutral.hurt";
    }

    @Override
    protected String getDeathSound()
    {
        return "game.neutral.die";
    }

}
