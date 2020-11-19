package cat.yoink.xanax.main.module.modules.misc;

import cat.yoink.xanax.main.event.events.TickEvent;
import cat.yoink.xanax.main.module.Category;
import cat.yoink.xanax.main.module.Module;
import cat.yoink.xanax.main.setting.NumberSetting;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import team.stiff.pomelo.impl.annotated.handler.annotation.Listener;

import java.util.HashMap;
import java.util.Map;

public final class Replenish extends Module
{
    private final NumberSetting threshold = addSetting(new NumberSetting("Threshold", 20, 1, 63, 1));
    private final NumberSetting tickDelay = addSetting(new NumberSetting("TickDelay", 4, 0, 20, 1));
    private int delayStep = 0;

    public Replenish()
    {
        super("Replenish", Category.MISC);
    }

    private Map<Integer, ItemStack> getInventory()
    {
        return getInventorySlots(9, 35);
    }

    private Map<Integer, ItemStack> getHotbar()
    {
        return getInventorySlots(36, 44);
    }

    private Map<Integer, ItemStack> getInventorySlots(int current, int last)
    {
        int c = current;
        Map<Integer, ItemStack> fullInventorySlots = new HashMap<>();
        while (c <= last)
        {
            fullInventorySlots.put(c, mc.player.inventoryContainer.getInventory().get(c));
            c++;
        }
        return fullInventorySlots;
    }

    @Listener
    public void onTick(TickEvent event)
    {
        if (delayStep < tickDelay.getValue())
        {
            delayStep++;
            return;
        }
        else delayStep = 0;

        int[] slots = findReplenishableHotbarSlot();
        if (slots == null) return;

        int inventorySlot = slots[0];
        int hotbarSlot = slots[1];

        mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, hotbarSlot, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, inventorySlot, 0, ClickType.PICKUP, mc.player);
    }

    private int[] findReplenishableHotbarSlot()
    {
        int[] returnPair = {0, 0};
        for (Map.Entry<Integer, ItemStack> hotbarSlot : getHotbar().entrySet())
        {
            ItemStack stack = hotbarSlot.getValue();
            int inventorySlot = findCompatibleInventorySlot(stack);
            if (stack.isEmpty() || stack.getItem() == Items.AIR || !stack.isStackable() || stack.getCount() >= stack.getMaxStackSize() || stack.getCount() > threshold.getValue() || inventorySlot == -1) continue;

            returnPair[0] = inventorySlot;
            returnPair[1] = hotbarSlot.getKey();
        }
        return returnPair;
    }

    private int findCompatibleInventorySlot(ItemStack hotbarStack)
    {
        int inventorySlot = -1;
        int smallestStackSize = 999;
        for (Map.Entry<Integer, ItemStack> entry : getInventory().entrySet())
        {
            ItemStack inventoryStack = entry.getValue();
            if (inventoryStack.isEmpty() || inventoryStack.getItem() == Items.AIR || !isCompatibleStacks(hotbarStack, inventoryStack)) continue;
            int currentStackSize = mc.player.inventoryContainer.getInventory().get(entry.getKey()).getCount();
            if (smallestStackSize > currentStackSize)
            {
                smallestStackSize = currentStackSize;
                inventorySlot = entry.getKey();
            }
        }
        return inventorySlot;
    }

    private boolean isCompatibleStacks(ItemStack stack1, ItemStack stack2)
    {
        if (!stack1.getItem().equals(stack2.getItem()) || !stack1.getDisplayName().equals(stack2.getDisplayName())) return false;
        return stack1.getItemDamage() == stack2.getItemDamage();
    }
}
