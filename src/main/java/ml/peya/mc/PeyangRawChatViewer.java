package ml.peya.mc;

import ml.peya.mc.commands.*;
import ml.peya.mc.exception.*;
import net.minecraft.client.*;
import net.minecraft.crash.*;
import net.minecraft.event.*;
import net.minecraft.util.*;
import net.minecraftforge.client.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraftforge.fml.relauncher.*;

import javax.swing.text.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.util.*;
import java.util.logging.*;
import java.util.regex.*;

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
        ClientCommandHandler.instance.registerCommand(new PeyangDraftCopyUrl());
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
        if (e.type != 0)
            return;
        //logger.info(String.valueOf(!isRaw));
        IChatComponent chatComponent = e.message;
        //logger.info(e.message.getFormattedText());
        logger.info(IChatComponent.Serializer.componentToJson(chatComponent));
        String json = IChatComponent.Serializer.componentToJson(chatComponent);
        json = json.replace("§", "[Section]");
        ChatComponentText response = new ChatComponentText(getRawPrefix() + EnumChatFormatting.WHITE + " " + json);
        ChatStyle style = response.getChatStyle();

        IChatComponent hoverTxt = new ChatComponentText(EnumChatFormatting.YELLOW + "Click to send !");
        HoverEvent hoverEvt = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverTxt);

        ClickEvent clickEvt = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/zzzpeyangdraftcopysandopenurl " + String.join(" ", splitByLength(json)));
        style.setChatHoverEvent(hoverEvt);
        style.setChatClickEvent(clickEvt);

        response.setChatStyle(style);

        Minecraft.getMinecraft().thePlayer.addChatMessage(response);
    }

    public static String getRawPrefix()
    {
        return EnumChatFormatting.AQUA + "[" +
                EnumChatFormatting.BLUE + "RAWCHAT" +
                EnumChatFormatting.AQUA + "]";
    }

    public static ArrayList<String> splitByLength(String str)
    {
        Matcher matcher = Pattern.compile(".{1,30}").matcher(str);
        ArrayList<String> response = new ArrayList<>();
        while(matcher.find())
            response.add(str.substring(matcher.start(), matcher.end()));
        return response;
    }
}
