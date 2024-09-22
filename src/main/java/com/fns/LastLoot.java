package com.fns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.client.game.ItemStack;

import java.util.Collection;

@Getter
@RequiredArgsConstructor
public class LastLoot {
	private final String name;
	private final int combatLevel;
	private final Collection<ItemStack> items;

	@Override
	public String toString() {
		return "LastLoot{" +
			"name='" + name +
			"', combatLevel='" + combatLevel +
			"', items='" + items +
			"'}";
	}
}
