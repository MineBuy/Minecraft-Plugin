package xyz.minebuy.plugin.MineBuyBungee;

import java.net.URL;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import xyz.minebuy.MineBuyAPI.MineBuyAPI;
import xyz.minebuy.MineBuyAPI.MineBuyExpection;

public class MineBuy extends JavaPlugin {

	public static URL url;
	public static boolean https;

	private static MineBuy instance;
	private static MineBuyAPI api = null;

	@Override
	public void onLoad() {
		instance = this;
	}

	@Override
	public void onEnable() {

		// Load API
		long interval = 30 * 20L;

		getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
			try {
				api = new MineBuyAPI(url);
			} catch (MineBuyExpection e) {
				getLogger().log(Level.SEVERE, e.getMessage());
				return;
			}
		}, 0, interval);
	}

	@Override
	public void onDisable() {

	}

	public static MineBuy getInstance() {
		return instance;
	}

	public static MineBuyAPI getAPI() {
		return api;
	}

}