package drzhark.mocreatures.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelFirefly;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MoCRenderFirefly extends MoCRenderInsect {

    public MoCRenderFirefly(ModelBase modelBase)
    {
        super(modelBase);
        setRenderPassModel(new MoCModelFirefly());
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
    protected int shouldRenderPass(EntityLivingBase entityLiving, int par2, float par3)
    {
        return setTailBrightness((MoCEntityFirefly) entityLiving, par2, par3);
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityLiving, float par2)
    {
        MoCEntityFirefly firefly = (MoCEntityFirefly) entityLiving;
        if (firefly.getIsFlying())
        {
            rotateFirefly(firefly);
        }
        else if (firefly.climbing())
        {
            rotateAnimal(firefly);
        }

    }

    protected void rotateFirefly(MoCEntityFirefly entityfirefly)
    {
        GL11.glRotatef(40F, -1F, 0.0F, 0.0F);

    }

    /**
     * Sets the glowing belly
     */
    protected int setTailBrightness(MoCEntityFirefly entityLiving, int par2, float par3)
    {
        if (par2 != 0)
        {
            return -1;
        }
        else
        {
            bindTexture(MoCreatures.proxy.getTexture("fireflyglow.png"));
            float var4 = 1.0F;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
            char var5 = 61680;
            int var6 = var5 % 65536;
            int var7 = var5 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
            return 1;
        }
    }

    @Override
	protected ResourceLocation getEntityTexture(Entity entity) {
        return ((MoCEntityFirefly)entity).getTexture();
    }
}
