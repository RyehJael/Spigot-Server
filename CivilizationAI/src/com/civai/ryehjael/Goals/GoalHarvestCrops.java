package com.civai.ryehjael.Goals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ItemStack;

import com.civai.ryehjael.CivNPC.CivNPC;
import com.civai.ryehjael.CivNPC.NPCState;
import com.civai.ryehjael.Traits.TraitSurvivalist;

import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.ai.GoalSelector;
import net.citizensnpcs.api.npc.NPC;

public class GoalHarvestCrops implements Goal {
	NPC npc;
	NPCState state;
	CivNPC civNPC = new CivNPC();

	public GoalHarvestCrops(NPC npc, NPCState state) {
		this.npc = npc;
		this.state = state;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void run(GoalSelector selector) {
		if (state.getState().equals("harvesting")) {
			Block block = npc.getEntity().getLocation().getBlock();
			World world = npc.getEntity().getWorld();
			int y = block.getLocation().getBlockY() - 1;

			for (int j = 0; j < (3); j++) {
				int x = block.getLocation().getBlockX(), z = block.getLocation().getBlockZ(), dx = 0, dz = 1,
						length = 1, lenIt = 0, iterations = 25;
				for (int i = 0; i < iterations; i++) {
					Location location = new Location(world, x, y, z);
					if (world.getBlockAt(location).getType().equals(Material.CROPS)
							&& world.getBlockAt(location).getData() == 7) {
						location.getBlock().breakNaturally();
						location.getBlock().setType(Material.CROPS);
						location.getWorld().playSound(location, Sound.BLOCK_GRASS_BREAK, 1f, 1f);
						state.setIdle();
						npc.getTrait(TraitSurvivalist.class).runIdleGoal();
						selector.finishAndRemove();
						break;
					}
					x += dx;
					z += dz;
					lenIt++;

					if (lenIt == length) {
						lenIt = 0;
						int buffer = dx;
						dx = -dz;
						dz = buffer;
						if (dz == 0) {
							length++;
						}
					}

				}
				y++;
			}
			state.setIdle();
			selector.finishAndRemove();
		}

	}

	@Override
	public boolean shouldExecute(GoalSelector selector) {
		if (state.getState().equals("harvesting")) {
			return true;
		}
		return false;
	}

}
