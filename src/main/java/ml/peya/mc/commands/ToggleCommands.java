package ml.peya.mc.commands;

import ml.peya.mc.*;
import net.minecraft.command.*;
import net.minecraft.util.*;

import java.util.*;

public class ToggleCommands extends CommandBase
{
    @Override
    public String getCommandName()
    {
        return "rawchat";
    }

    @Override
    public String getCommandUsage(ICommandSender iCommandSender)
    {
        return "rawchat [on|off]";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args)
    {
        if (args.length == 0)
            PeyangRawChatViewer.isRaw = !PeyangRawChatViewer.isRaw;
        else
            PeyangRawChatViewer.isRaw = args[0].equals("on");
        Player.sendMessage( PeyangRawChatViewer.getRawPrefix() +
                EnumChatFormatting.WHITE + " RawMode " + (PeyangRawChatViewer.isRaw ? "Enabled!": "Disabled!"), sender);
    }

    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender p_addTabCompletionOptions_1_, String[] p_addTabCompletionOptions_2_, BlockPos p_addTabCompletionOptions_3_)
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("on");
        list.add("off");
        return list;
    }
}
