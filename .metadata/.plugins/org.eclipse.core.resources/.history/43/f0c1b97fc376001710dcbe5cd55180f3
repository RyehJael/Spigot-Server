package me.ryehjael;

import org.bukkit.event.player.PlayerFishEvent;

public class FishingHandler {

	public void fishingHandler(PlayerFishEvent e) {
		if (e.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
			System.out.println(e.getCaught().getType().getTypeId());
		}
	}
}
