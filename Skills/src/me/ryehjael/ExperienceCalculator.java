package me.ryehjael;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

public class ExperienceCalculator {

	public void addExperience(Player p, String skill, int xp) {
		Objective o = p.getScoreboard().getObjective(skill);
		if (o == null) {
			System.out.println("Objective Does Not Exist for skill");
			return;
		}
		Score score = o.getScore(p);
		score.setScore(score.getScore() + xp);
		int newXP = score.getScore() + xp;
		p.setExp(0.0F);
		p.setLevel(0);
		p.giveExp(newXP);
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		Bukkit.getServer().dispatchCommand(console, "execute " + p.getName().toString()
				+ " ~ ~ ~ /title @p actionbar [\"\", {\"text\":\" " + skill + " \"}]  ");
	}

	public int getSkillLevel(Player p, String skill) {
		Objective o = p.getScoreboard().getObjective(skill);
		if (o == null) {
			System.out.println("Objective Does Not Exist for skill");
			return 0;
		}
		int score = o.getScore(p).getScore();
		p.setExp(0.0F);
		p.setLevel(0);
		p.giveExp(score);
		return p.getLevel();

	}
	
	public int closest(int of, List<Integer> in) {
	    int min = Integer.MAX_VALUE;
	    int closest = of;

	    for (int v : in) {
	        final int diff = Math.abs(v - of);

	        if (diff < min) {
	            min = diff;
	            closest = v;
	        }
	    }

	    return closest;
	}
}
