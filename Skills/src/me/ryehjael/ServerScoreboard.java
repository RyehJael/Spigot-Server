package me.ryehjael;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

public class ServerScoreboard
{
  String[] dummyObjectives = { "Woodcutting", "AttackDamage", "Armor", "Toughness", "AttackSpeed", "Knockback", "Luck", "MaxHealth", "MovementSpeed", "Mining", "Fishing",
		  "Talis", "Attack"};
  
  public void ScoreboardConfigure()
  {
    Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
    for (int i = 0; i < this.dummyObjectives.length; i++) {
      if (sb.getObjective(this.dummyObjectives[i]) == null) {
        sb.registerNewObjective(this.dummyObjectives[i], "dummy");
      }
    }
  }
}
