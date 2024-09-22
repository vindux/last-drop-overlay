package com.example;

import com.google.inject.Inject;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NPC;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.NpcLootReceived;
import net.runelite.client.game.ItemStack;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.Collection;

@Slf4j
@PluginDescriptor(name = "Last Loot Overlay")
public class LastLootPlugin extends Plugin {
	@Inject private LastLootOverlay overlay;
	@Inject private OverlayManager overlayManager;
	@Getter private LastLoot lastLoot = null;

	@Override
	protected void startUp() {
		lastLoot = null;
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() {
		lastLoot = null;
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onNpcLootReceived(final NpcLootReceived npcLootReceived)
	{
		final NPC npc = npcLootReceived.getNpc();
		final Collection<ItemStack> items = npcLootReceived.getItems();
		final String name = npc.getName();
		final int combat = npc.getCombatLevel();
		lastLoot = new LastLoot(name, combat,  items);
		log.info("Loot received: {}", lastLoot);
	}

}
