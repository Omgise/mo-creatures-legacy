package drzhark.mocreatures.entity;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public interface IMoCEntity {

    public void riderIsDisconnecting(boolean flag);// = false;

    public boolean forceUpdates();

    public void selectType();

    public String getName();

    public void setName(String name);

    public boolean getIsTamed();

    public void setTamed(boolean flag);

    public boolean getIsAdult();

    public void setAdult(boolean flag);

    public boolean checkSpawningBiome();

    public boolean getCanSpawnHere();

    /**
     * Used to synchronize animations between server and clients
     * 
     * @param i
     *            = animationType
     */
    public void performAnimation(int i);

    public boolean shouldRenderName();

    public int nameYOffset();

    /**
     * Used to ajust the Yoffset when using ropes
     * 
     * @return
     */
    public double roperYOffset();

    /**
     * The entity holding the rope
     * 
     * @return
     */
    public Entity getRoper();

    public boolean updateMount();

    /**
     * method used to sync jump client/server
     */
    public void makeEntityJump();

    public void makeEntityDive();

    public float getSizeFactor();

    public float getAdjustedYOffset();

    public String getOwnerName();

    public void setOwner(String username);

    public void setArmorType(byte i);
    
    public int getType();

    public void setType(int i);

    public void dismountEntity();

    public int rollRotationOffset();

    public int pitchRotationOffset();

    public void setMoCAge(int i);

    public int getMoCAge();

    public int yawRotationOffset();

    public float getAdjustedZOffset();

    public float getAdjustedXOffset();

    public ResourceLocation getTexture();
    
    /**
     * Tells the creature not to hunt any of the entities that are returned with this function.
     * This is used within the findPlayerToAttack function
     *
     * @param entity
     * @return
     */
    public boolean shouldEntityBeIgnored(Entity entityNearby);

    
    /**
     * Used in MoCTools.getScaryEntity to specify what kind of entity to look for
     *
     * @param entity
     * @return
     */
	public boolean entitiesThatAreScary(Entity entityNearby);
}