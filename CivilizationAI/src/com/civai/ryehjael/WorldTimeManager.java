package com.civai.ryehjael;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.World;

import com.civai.ryehjael.Events.HourPassedEvent;

public class WorldTimeManager extends Thread {
	
	int hours = 0;
	int days = 1;
	int months = 1;
	int years = 0;
	private static boolean run = true;

	public WorldTimeManager() {
		final Timer timer = new Timer();
		final TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (!run) {
					timer.cancel();
					timer.purge();
				}
				hours++;
				if (hours == 24) {
					for (World world :Bukkit.getServer().getWorlds()) {
						if (world.getTime() != 0) {
							world.setTime(0);
						}
					}
					hours = 0;
					days++;
				}
				if (days == 31) {
					days = 1;
					months++;
				}
				if (months == 12) {
					months = 1;
					years++;
				}
//				System.out.println("Date: " + months + "/" + days + "/" + years + " " + hours + ":00");
				HourPassedEvent hourPass = new HourPassedEvent();
				Bukkit.getServer().getPluginManager().callEvent(hourPass);
				
			}

		};
		timer.schedule(task, 50000, 50000);
	}
	
	public void forceStop() {
		run = false;
	}
}
