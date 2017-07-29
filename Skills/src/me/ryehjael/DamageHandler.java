package me.ryehjael;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageHandler {
	
	public void damageHandler(EntityDamageByEntityEvent e) {
		ExperienceCalculator calc = new ExperienceCalculator();
		ItemCheck ic = new ItemCheck();

		if (e.getDamager() instanceof Player) {
			Player p = (Player) e.getDamager();
			double finalDmg = e.getDamage();
			int dmg = (int)Math.round(finalDmg);
			calc.addExperience(p, "Attack", dmg);
			ic.dependabilityHandler(p);
		} 
	}
}