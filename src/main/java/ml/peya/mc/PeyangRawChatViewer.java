package ml.peya.mc;

import ml.peya.mc.commands.*;
import ml.peya.mc.exception.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.crash.*;
import net.minecraft.util.*;
import net.minecraftforge.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

import java.awt.*;
import java.util.logging.*;

@Mod(modid = PeyangRawChatViewer.MOD_ID,
        name = PeyangRawChatViewer.MOD_NAME,
        version = PeyangRawChatViewer.MOD_VERSION,
        acceptedMinecraftVersions = PeyangRawChatViewer.MOD_ACCEPTED_MC_VERSIONS,
        useMetadata = true)
public class PeyangRawChatViewer
{
    public static final String MOD_ID = "peyangrawchatviewer";
    public static final String MOD_NAME = "PeyangRawChatViewer";
    public static final String MOD_VERSION = "1.1";
    public static final String MOD_ACCEPTED_MC_VERSIONS = "[1.8,)";
    public static Logger logger;
    public static boolean isRaw;
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        if(e.getSide() == Side.SERVER)
        {
            System.out.println("This mod has not working in server.");
            Minecraft.getMinecraft().crashed(new CrashReport("This Mod(PRCV) Is Not Server Side Compatible.",
                    new BadSideException("This Mod(PML) Is Not Server Side Compatible. Require Client Side.")));
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        ClientCommandHandler.instance.registerCommand(new ToggleCommands());
        logger = Logger.getLogger(MOD_ID);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void chatReceive(ClientChatReceivedEvent e)
    {
        if(e.message.getFormattedText().startsWith(getRawPrefix()))
            return;
        //logger.info(e.message.getFormattedText());
        if (!isRaw)
            return;
        //logger.info(String.valueOf(!isRaw));
        IChatComponent chatComponent = e.message;

        //logger.info(e.message.getFormattedText());
        logger.info(IChatComponent.Serializer.componentToJson(chatComponent));
        String json = IChatComponent.Serializer.componentToJson(chatComponent).replace("§", "[Section]");
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(getRawPrefix() + EnumChatFormatting.WHITE + " " + json));
    }

    public static String getRawPrefix()
    {
        return EnumChatFormatting.AQUA + "[" +
                EnumChatFormatting.BLUE + "RAWCHAT" +
                EnumChatFormatting.AQUA + "]";
    }
}
