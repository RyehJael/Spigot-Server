package com.civai.ryehjael;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.bukkit.plugin.java.JavaPlugin;

import com.civai.ryehjael.Commands.CommandGetStats;
import com.civai.ryehjael.Commands.CommandNewCiv;
import com.civai.ryehjael.Traits.TraitSurvivalist;

import net.citizensnpcs.api.CitizensAPI;

public class Main extends JavaPlugin {
	
	WorldTimeManager wtm = new WorldTimeManager();
	
	public void onEnable() {
		// apply sqlite database
//		createNewDatabase("Chronos");
//		connect();
//		InsertApp.loadDB();

		// check if Citizens is present and enabled.

		if (getServer().getPluginManager().getPlugin("Citizens") == null
				|| getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
			getLogger().log(java.util.logging.Level.SEVERE, "Citizens 2.0 not found or not enabled");
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		// register plugin commands
		this.getCommand("newciv").setExecutor(new CommandNewCiv());
		this.getCommand("getstats").setExecutor(new CommandGetStats());

		// Register traits with Citizens.
		net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(
				net.citizensnpcs.api.trait.TraitInfo.create(TraitSurvivalist.class).withName("survivalist"));
		

	}
	
	public void onDisable() {
		wtm.forceStop();
		System.out.println("force stopping timer");
		
	}

//	public static void createNewDatabase(String fileName) {
//
//		String url = "jdbc:sqlite:C:/sqlite/db/" + fileName;
//
//		try (Connection conn = DriverManager.getConnection(url)) {
//			if (conn != null) {
//				DatabaseMetaData meta = conn.getMetaData();
//				System.out.println("The driver name is " + meta.getDriverName());
//				System.out.println("A new database has been created.");
//			}
//
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		}
//	}
//
//	public static void connect() {
//		Connection conn = null;
//		try {
//			// db parameters
//			String url = "jdbc:sqlite:C:/sqlite/db/Chronos.db";
//			// create a connection to the database
//			conn = DriverManager.getConnection(url);
//
//			System.out.println("Connection to SQLite has been established.");
//
//		} catch (SQLException e) {
//			System.out.println(e.getMessage());
//		} finally {
//			try {
//				if (conn != null) {
//					conn.close();
//				}
//			} catch (SQLException ex) {
//				System.out.println(ex.getMessage());
//			}
//		}
//
//	}
//
}
