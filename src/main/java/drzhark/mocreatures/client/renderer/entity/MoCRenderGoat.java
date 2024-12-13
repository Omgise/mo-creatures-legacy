package drzhark.mocreatures.client.renderer.entity;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelGoat;
import drzhark.mocreatures.entity.animal.MoCEntityGoat;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MoCRenderGoat extends RenderLiving {

    @Override
	protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return ((MoCEntityGoat)par1Entity).getTexture();
    }

    public MoCRenderGoat(ModelBase modelBase, float f)
    {
        super(modelBase, f);
        tempGoat = (MoCModelGoat) modelBase;
    }

    @Override
    protected void preRenderCallback(EntityLivingBase entityLiving, float f)
    {
        GL11.glTranslatef(0.0F, depth, 0.0F);
        stretch((MoCEntityGoat) entityLiving);

    }

    @Override
    public void doRender(EntityLiving entityLiving, double x, double y, double z, float rotationYaw, float rotationPitch)
    {
        MoCEntityGoat entityGoat = (MoCEntityGoat) entityLiving;
        tempGoat.typeInt = entityGoat.getType();
        tempGoat.entityAge = entityGoat.getMoCAge() * 0.01F;
        tempGoat.bleat = entityGoat.getBleating();
        tempGoat.attacking = entityGoat.getAttacking();
        tempGoat.legMov = entityGoat.legMovement();
        tempGoat.earMov = entityGoat.earMovement();
        tempGoat.tailMov = entityGoat.tailMovement();
        tempGoat.eatMov = entityGoat.mouthMovement();

        super.doRender(entityGoat, x, y, z, rotationYaw, rotationPitch);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entityGoat.getName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealthMode(entityLiving);
        //boolean flag2 = MoCreatures.proxy.getdisplayPetIcons();
        if (entityGoat.shouldRenderName())
        {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entityGoat.getDistanceToEntity(renderManager.livingPlayer);
            if (f4 < 16F)
            {
                String s = "";
                s = (new StringBuilder()).append(s).append(entityGoat.getName()).toString();
                float f5 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                GL11.glPushMatrix();
                GL11.glTranslatef((float) x + 0.0F, (float) y + f5, (float) z);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
                GL11.glDisable(2896 /* GL_LIGHTING */);
                Tessellator tessellator = Tessellator.instance;
                byte byte0 = (byte) (-15 + (-40 * entityGoat.getMoCAge() * 0.01F));
                if (flag1)
                {
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    if (!flag)
                    {
                        byte0 += 8;
                    }
                    tessellator.startDrawingQuads();
                    // might break SSP
                    float f6 = entityGoat.getHealth();
                    // maxhealth is always 30 for dolphins so we do not need to use a datawatcher
                    float f7 = entityGoat.getMaxHealth();
                    float f8 = f6 / f7;
                    float f9 = 40F * f8;
                    tessellator.setColorRGBA_F(0.7F, 0.0F, 0.0F, 1.0F);
                    tessellator.addVertex(-20F + f9, -10 + byte0, 0.0D);
                    tessellator.addVertex(-20F + f9, -6 + byte0, 0.0D);
                    tessellator.addVertex(20D, -6 + byte0, 0.0D);
                    tessellator.addVertex(20D, -10 + byte0, 0.0D);
                    tessellator.setColorRGBA_F(0.0F, 0.7F, 0.0F, 1.0F);
                    tessellator.addVertex(-20D, -10 + byte0, 0.0D);
                    tessellator.addVertex(-20D, -6 + byte0, 0.0D);
                    tessellator.addVertex(f9 - 20F, -6 + byte0, 0.0D);
                    tessellator.addVertex(f9 - 20F, -10 + byte0, 0.0D);
                    tessellator.draw();
                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                }
                if (flag)
                {
                    GL11.glDepthMask(false);
                    GL11.glDisable(2929 /* GL_DEPTH_TEST */);
                    GL11.glEnable(3042 /* GL_BLEND */);
                    GL11.glBlendFunc(770, 771);
                    GL11.glDisable(3553 /* GL_TEXTURE_2D */);
                    tessellator.startDrawingQuads();
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.addVertex(-i - 1, -1 + byte0, 0.0D);
                    tessellator.addVertex(-i - 1, 8 + byte0, 0.0D);
                    tessellator.addVertex(i + 1, 8 + byte0, 0.0D);
                    tessellator.addVertex(i + 1, -1 + byte0, 0.0D);
                    tessellator.draw();
                    GL11.glEnable(3553 /* GL_TEXTURE_2D */);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
                    GL11.glEnable(2929 /* GL_DEPTH_TEST */);
                    GL11.glDepthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
                    GL11.glDisable(3042 /* GL_BLEND */);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                }
                GL11.glEnable(2896 /* GL_LIGHTING */);
                GL11.glPopMatrix();
            }
        }
        if (entityGoat.roper != null)
        {
            y -= 0.5D / entityGoat.getMoCAge() * 0.01F;
            Tessellator tessellator = Tessellator.instance;
            float f4 = ((entityGoat.roper.prevRotationYaw + ((entityGoat.roper.rotationYaw - entityGoat.roper.prevRotationYaw) * rotationPitch * 0.5F)) * (float) Math.PI) / 180F;
            float f6 = ((entityGoat.roper.prevRotationPitch + ((entityGoat.roper.rotationPitch - entityGoat.roper.prevRotationPitch) * rotationPitch * 0.5F)) * (float) Math.PI) / 180F;
            double d3 = MathHelper.sin(f4);
            double d4 = MathHelper.cos(f4);
            double d5 = MathHelper.sin(f6);
            double d6 = MathHelper.cos(f6);
            double d7 = (entityGoat.roper.prevPosX + ((entityGoat.roper.posX - entityGoat.roper.prevPosX) * rotationPitch)) - (d4 * 0.69999999999999996D) - (d3 * 0.5D * d6);
            double d8 = (entityGoat.roper.prevPosY + ((entityGoat.roper.posY - entityGoat.roper.prevPosY) * rotationPitch)) - (d5 * 0.5D);
            double d9 = ((entityGoat.roper.prevPosZ + ((entityGoat.roper.posZ - entityGoat.roper.prevPosZ) * rotationPitch)) - (d3 * 0.69999999999999996D)) + (d4 * 0.5D * d6);
            double d10 = entityGoat.prevPosX + ((entityGoat.posX - entityGoat.prevPosX) * rotationPitch);
            double d11 = entityGoat.prevPosY + ((entityGoat.posY - entityGoat.prevPosY) * rotationPitch) + 0.25D;
            double d12 = entityGoat.prevPosZ + ((entityGoat.posZ - entityGoat.prevPosZ) * rotationPitch);
            double d13 = (float) (d7 - d10);
            double d14 = (float) (d8 - d11);
            double d15 = (float) (d9 - d12);
            GL11.glDisable(3553 /* GL_TEXTURE_2D */);
            GL11.glDisable(2896 /* GL_LIGHTING */);
            for (double d16 = 0.0D; d16 < 0.029999999999999999D; d16 += 0.01D)
            {
                tessellator.startDrawing(3);
                tessellator.setColorRGBA_F(0.5F, 0.4F, 0.3F, 1.0F);
                int j = 16;
                for (int k = 0; k <= j; k++)
                {
                    float f12 = (float) k / (float) j;
                    tessellator.addVertex(x + (d13 * f12) + d16, y + (d14 * ((f12 * f12) + f12) * 0.5D) + ((((float) j - (float) k) / (j * 0.75F)) + 0.125F), z + (d15 * f12));
                }

                tessellator.draw();
            }

            GL11.glEnable(2896 /* GL_LIGHTING */);
            GL11.glEnable(3553 /* GL_TEXTURE_2D */);
        }
    }

    protected void stretch(MoCEntityGoat entityGoat)
    {
        GL11.glScalef(entityGoat.getMoCAge() * 0.01F, entityGoat.getMoCAge() * 0.01F, entityGoat.getMoCAge() * 0.01F);
    }

    private final MoCModelGoat tempGoat;
    float depth = 0F;
}
