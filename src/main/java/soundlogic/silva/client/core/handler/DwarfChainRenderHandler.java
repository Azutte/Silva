package soundlogic.silva.client.core.handler;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import soundlogic.silva.common.core.handler.DwarvenChainHandler;
import soundlogic.silva.common.core.handler.DwarvenChainHandler.LeashProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DwarfChainRenderHandler {

	private static final int[] color1 = new int[]{154,154,154};
	private static final int[] color2 = new int[]{ 86, 86, 86};
	
	@SubscribeEvent
	public void renderLiving(RenderLivingEvent.Pre event) {
		if ( event.entity instanceof EntityCreature) {
			LeashProperties props = DwarvenChainHandler.getChainForEntity((EntityCreature) event.entity);
			props.searchTick(event.entity, true);
			if(props==null || props.getEntity() == null || !props.getActive())
				return;
			float f2=ClientTickHandler.partialTicks; //partial ticks?
			float f1 = event.entity.prevRotationYaw + (event.entity.rotationYaw - event.entity.prevRotationYaw) * f2;
			renderChain((EntityLiving) event.entity, props.getEntity(), event.x, event.y, event.z, f1, f2);
		}
	}

    protected void renderChain(EntityLiving entity, Entity attachedTo, double x, double y, double z, float partialTicks, float rotation)
    {
        if (attachedTo != null)
        {
            y -= (1.6D - (double)entity.height) * 0.5D;
            Tessellator tessellator = Tessellator.instance;
            double d3 = this.func_110828_a((double)attachedTo.prevRotationYaw, (double)attachedTo.rotationYaw, (double)(rotation * 0.5F)) * 0.01745329238474369D;
            double d4 = this.func_110828_a((double)attachedTo.prevRotationPitch, (double)attachedTo.rotationPitch, (double)(rotation * 0.5F)) * 0.01745329238474369D;
            double d5 = Math.cos(d3);
            double d6 = Math.sin(d3);
            double d7 = Math.sin(d4);

            if (attachedTo instanceof EntityHanging)
            {
                d5 = 0.0D;
                d6 = 0.0D;
                d7 = -1.0D;
            }

            double d8 = Math.cos(d4);
            double d9 = this.func_110828_a(attachedTo.prevPosX, attachedTo.posX, (double)rotation) - d5 * 0.7D - d6 * 0.5D * d8;
            double d10 = this.func_110828_a(attachedTo.prevPosY + (double)attachedTo.getEyeHeight() * 0.7D, attachedTo.posY + (double)attachedTo.getEyeHeight() * 0.7D, (double)rotation) - d7 * 0.5D - 0.25D;
            double d11 = this.func_110828_a(attachedTo.prevPosZ, attachedTo.posZ, (double)rotation) - d6 * 0.7D + d5 * 0.5D * d8;
            double d12 = this.func_110828_a((double)entity.prevRenderYawOffset, (double)entity.renderYawOffset, (double)rotation) * 0.01745329238474369D + (Math.PI / 2D);
            d5 = Math.cos(d12) * (double)entity.width * 0.4D;
            d6 = Math.sin(d12) * (double)entity.width * 0.4D;
            double d13 = this.func_110828_a(entity.prevPosX, entity.posX, (double)rotation) + d5;
            double d14 = this.func_110828_a(entity.prevPosY, entity.posY, (double)rotation);
            double d15 = this.func_110828_a(entity.prevPosZ, entity.posZ, (double)rotation) + d6;
            x += d5;
            z += d6;
            double d16 = (double)((float)(d9 - d13));
            double d17 = (double)((float)(d10 - d14));
            double d18 = (double)((float)(d11 - d15));
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_CULL_FACE);
            boolean flag = true;
            double d19 = 0.025D;
            tessellator.startDrawing(5);
            int i;
            float f2;

            for (i = 0; i <= 24; ++i)
            {
                if (i % 2 == 0)
                {
                    tessellator.setColorOpaque(color1[0], color1[1], color1[2]);
                }
                else
                {
                    tessellator.setColorOpaque(color2[0], color2[1], color2[2]);
                }

                f2 = (float)i / 24.0F;
                tessellator.addVertex(x + d16 * (double)f2 + 0.0D, y + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F), z + d18 * (double)f2);
                tessellator.addVertex(x + d16 * (double)f2 + 0.025D, y + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F) + 0.025D, z + d18 * (double)f2);
            }

            tessellator.draw();
            tessellator.startDrawing(5);

            for (i = 0; i <= 24; ++i)
            {
                if (i % 2 == 0)
                {
                    tessellator.setColorOpaque(color1[0], color1[1], color1[2]);
                }
                else
                {
                    tessellator.setColorOpaque(color2[0], color2[1], color2[2]);
                }

                f2 = (float)i / 24.0F;
                tessellator.addVertex(x + d16 * (double)f2 + 0.0D, y + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F) + 0.025D, z + d18 * (double)f2);
                tessellator.addVertex(x + d16 * (double)f2 + 0.025D, y + d17 * (double)(f2 * f2 + f2) * 0.5D + (double)((24.0F - (float)i) / 18.0F + 0.125F), z + d18 * (double)f2 + 0.025D);
            }

            tessellator.draw();
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_CULL_FACE);
        }
    }
    private double func_110828_a(double p_110828_1_, double p_110828_3_, double p_110828_5_)
    {
        return p_110828_1_ + (p_110828_3_ - p_110828_1_) * p_110828_5_;
    }
}
