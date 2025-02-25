package drzhark.mocreatures.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

public class MoCClientTickHandler {

    private void onTickInGame()
    {
    }

    boolean inMenu;
    int lastTickRun;

    private void onTickInGui(GuiScreen curScreen)
    {
        // handle GUI tick stuff here
        inMenu = true;
        lastTickRun = 0;
    }

    @SubscribeEvent
    public void tickEnd(ClientTickEvent event)
    {
        if (event.type.equals(Type.CLIENT))
        {
            GuiScreen curScreen = Minecraft.getMinecraft().currentScreen;
            if (curScreen != null)
            {
                onTickInGui(curScreen);
            }
            else
            {
                inMenu = false;
                onTickInGame();
            }
        }
    }
}