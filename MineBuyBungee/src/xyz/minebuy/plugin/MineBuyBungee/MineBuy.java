package xyz.minebuy.plugin.MineBuyBungee;

import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import net.md_5.bungee.api.plugin.Plugin;
import xyz.minebuy.MineBuyAPI.MineBuyAPI;
import xyz.minebuy.MineBuyAPI.MineBuyExpection;

public class MineBuy extends Plugin {

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
		long interval = 30L;
		
		getProxy().getScheduler().schedule(this, () ->{
			try {
				api = new MineBuyAPI(url);
			} catch (MineBuyExpection e) {
				getLogger().log(Level.SEVERE, e.getMessage());
				return;
			}	
		}, 0, interval, TimeUnit.SECONDS);
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