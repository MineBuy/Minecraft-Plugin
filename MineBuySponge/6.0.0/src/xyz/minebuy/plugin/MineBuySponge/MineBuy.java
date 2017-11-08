package xyz.minebuy.plugin.MineBuySponge;

import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import com.google.inject.Inject;

import xyz.minebuy.MineBuyAPI.MineBuyAPI;
import xyz.minebuy.MineBuyAPI.MineBuyExpection;

@Plugin(id = "minebuy", name = "MineBuy Sponge", version = "Alpha-1", description = "The Official Plugin that interegates with MineBuy", url = "https://minebuy.xyz/", authors = { "MineBuy Java Team" })
public class MineBuy {

	@Inject
	private Logger logger;

	@Inject
	private Game game;

	@Inject
	@ConfigDir(sharedRoot = false)
	private Path configDir = null;

	private static MineBuy instance;
	
	private static MineBuyAPI api = null;

	public static URL url;

	public static boolean disabled = false;

	@Listener
	public void onServerInitialization(GamePreInitializationEvent event) {
		instance = this;
		log(Level.INFO, "Initializing NamelessMC " + getClass().getAnnotation(Plugin.class).version());

		// Load API
		long interval = 30L;

		game.getScheduler().createTaskBuilder().execute(() -> {
			try {
				api = new MineBuyAPI(url);
			} catch (MineBuyExpection e) {
				log(Level.WARNING, e.getMessage());
				return;
			}
		}
			).delay(0, TimeUnit.SECONDS).interval(interval, TimeUnit.SECONDS);
	}

	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		// load listeners commnads etc
		log(Level.INFO, "Successfully loaded!");
	}

	@Listener
	public void onPluginReload(GameReloadEvent event) {
		try {
			api = new MineBuyAPI(url);
		} catch (MineBuyExpection e) {
			log(Level.WARNING, e.getMessage());
			return;
		}
		log(Level.INFO, "Successfully reloaded!");
	}

	@Listener
	public void onServerDisabling(GameStoppingServerEvent event) {}

	public static MineBuy getInstance() {
		return instance;
	}

	public static Game getGame() {
		return MineBuy.getInstance().game;
	}

	public Logger getLogger() {
		return logger;
	}

	public static void log(Level level, String message) {
		Logger logger = MineBuy.getInstance().getLogger();
		switch (level) {
			case DEBUG:
				logger.debug(message);
				break;
			case TRACE:
				logger.trace(message);
				break;
			case INFO:
				logger.info(message);
				break;
			case WARNING:
				logger.warn(message);
				break;
			case ERROR:
				logger.error(message);
				break;
			default:
				break;
		}
	}

	public static Path getDirectory() {
		return MineBuy.getInstance().configDir;
	}

	public enum Level {
			DEBUG, TRACE, INFO, WARNING, ERROR;
	}
	
	public static MineBuyAPI getAPI() {
		return api;
	}
}