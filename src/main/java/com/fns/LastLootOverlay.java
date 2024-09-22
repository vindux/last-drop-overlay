package com.fns;

import com.google.inject.Inject;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.ItemStack;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.*;
import net.runelite.client.ui.overlay.components.ComponentOrientation;

import java.awt.*;
import java.awt.image.BufferedImage;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY;

public class LastLootOverlay extends OverlayPanel {
	private final LastLootPlugin plugin;
	private final ItemManager itemManager;

	@Inject
	public LastLootOverlay(LastLootPlugin plugin, ItemManager itemManager) {
		super(plugin);
		this.plugin = plugin;
		this.itemManager = itemManager;
		setPosition(OverlayPosition.TOP_LEFT);
		setPriority(PRIORITY_HIGH);
		panelComponent.setBorder(new Rectangle(2, 2, 2, 2));
		addMenuEntry(RUNELITE_OVERLAY, "Reset Last Loot", "Last Loot Overlay", e -> plugin.reset());
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		if (plugin.getLastLoot() == null) {
			return null;
		}

		final String name = plugin.getLastLoot().getName();
		final int combat = plugin.getLastLoot().getCombatLevel();
		final String text = name + " (lvl-" + combat + ")";
		final TitleComponent titleComponent = TitleComponent.builder().text(text).build();

		PanelComponent lootPanelComp = new PanelComponent();
		lootPanelComp.setOrientation(ComponentOrientation.HORIZONTAL);
		for (ItemStack item : plugin.getLastLoot().getItems()) {
			lootPanelComp.getChildren().add(new ImageComponent(getImage(item.getId(), item.getQuantity())));
		}

		final SplitComponent lootSplit = SplitComponent.builder()
				.first(titleComponent)
				.second(lootPanelComp)
				.orientation(ComponentOrientation.VERTICAL)
				.build();
		panelComponent.getChildren().add(lootSplit);
		panelComponent.getChildren().add(LineComponent.builder().build());
		panelComponent.getChildren().add(LineComponent.builder().build());
		panelComponent.getChildren().add(LineComponent.builder().build());

		var textWidth = graphics.getFontMetrics().stringWidth(text);

		int width = Math.max(textWidth + 30, lootPanelComp.getChildren().size() * 37);
		int height = 1;
		panelComponent.setPreferredSize(new Dimension(width, height));
		return super.render(graphics);
	}

	private BufferedImage getImage(int itemID, int amount)
	{
		BufferedImage image = itemManager.getImage(itemID, amount, true);
		return image;
	}
}
