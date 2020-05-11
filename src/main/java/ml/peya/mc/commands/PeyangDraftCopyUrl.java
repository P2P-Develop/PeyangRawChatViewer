package ml.peya.mc.commands;

import ml.peya.mc.*;
import net.minecraft.client.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

import java.awt.*;
import java.awt.datatransfer.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.*;

public class PeyangDraftCopyUrl extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "zzzpeyangdraftcopysandopenurl";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "zzzpeyangdraftcopysandopenurl <json>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if (args.length == 0)
            return;
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringBuilder json = new StringBuilder();
        for(String value: args)
            json.append(value);
        StringSelection val = new StringSelection(json.toString());
        clipboard.setContents(val, val);
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(PeyangRawChatViewer.getRawPrefix() + " " + EnumChatFormatting.GREEN + "Copied!"));
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }


}
