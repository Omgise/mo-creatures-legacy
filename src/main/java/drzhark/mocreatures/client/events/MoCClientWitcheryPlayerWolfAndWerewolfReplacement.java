package drzhark.mocreatures.client.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelWerewolf;
import drzhark.mocreatures.client.model.MoCModelWerewolfHuman;
import drzhark.mocreatures.client.model.MoCModelWolf;
import drzhark.mocreatures.client.renderer.entity.MoCRenderWerewolfPlayerWitchery;
import drzhark.mocreatures.client.renderer.entity.MoCRenderWolfPlayerWitchery;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class MoCClientWitcheryPlayerWolfAndWerewolfReplacement {
	
	final Render renderPlayerWolf = new MoCRenderWolfPlayerWitchery(new MoCModelWolf(), 0.7F);
    final Render renderPlayerWerewolf = new MoCRenderWerewolfPlayerWitchery(new MoCModelWerewolfHuman(), new MoCModelWerewolf(), 0.7F);
    
    @SubscribeEvent
	public void onRenderPlayerPre(RenderPlayerEvent.Pre event)
    {
    	if (MoCreatures.isWitcheryLoaded)
    	{
    		float modelYOffset = -1.625F;
    		
    		float modelRotationPitchOffset = 0.0625F;
    		
    		EntityPlayer playerThatIsOnClientSide = Minecraft.getMinecraft().thePlayer;
    		
    		double xPositionForModel = 0;
    		
    		double yPositionForModel = modelYOffset;
    		
    		double zPositionForModel = 0;
    		
    		float rotationYawForModel = 0;
    		
    		float rotationPitchForModel = modelRotationPitchOffset;
    		
    		if (event.entity != playerThatIsOnClientSide)
    		{	
    			/*
    			 * TODO: Fix rendering bugs for player wolf/werewolf forms on other players on servers:
    			 * 
    			 *     - Fix model position while the client side player is moving around the other player that is in wolf/werewolf form
	    		 *     - Fix model rotationYaw and rotationPitch glitches for the other player that is in wolf/werewolf form
    			 */
    			
    			//the position operations get the other player's relative position to the player that is on the client side
	    		xPositionForModel = -(playerThatIsOnClientSide.posX - event.entity.posX);
	    		
	    		yPositionForModel = -(playerThatIsOnClientSide.posY - event.entity.posY);
	    		
	    		zPositionForModel = -(playerThatIsOnClientSide.posZ - event.entity.posZ);
	    		
	    		rotationYawForModel = event.entity.rotationYaw;
	    		
	    		rotationPitchForModel = event.entity.rotationPitch;
    		}
    		
    		
    		
    		if (MoCreatures.proxy.replaceWitcheryPlayerWerewolf && MoCTools.isPlayerInWerewolfForm(event.entityPlayer) && !(event.entityPlayer.isPotionActive(Potion.invisibility)))
    		{ 			
				renderPlayerWerewolf.doRender(event.entity, xPositionForModel, yPositionForModel, zPositionForModel, rotationYawForModel, rotationPitchForModel);
    		}
    		
    		if (MoCreatures.proxy.replaceWitcheryPlayerWolf && MoCTools.isPlayerInWolfForm(event.entityPlayer) && !(event.entityPlayer.isPotionActive(Potion.invisibility)))
    		{
				renderPlayerWolf.doRender(event.entity, xPositionForModel, yPositionForModel, zPositionForModel, rotationYawForModel, rotationPitchForModel);
    		}
    	}

	}

}
