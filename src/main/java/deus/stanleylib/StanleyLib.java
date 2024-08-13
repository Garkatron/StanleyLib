package deus.stanleylib;

import deus.stanleylib.config.ConfigHandler;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class StanleyLib implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {

    public static final String MOD_ID = "stanleylib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static ConfigHandler MOD_CONFIG = new ConfigHandler();;

	static final int TICKS_PER_SECOND = 20;

	//public static IStanleySettings options;

	/**
	 * The quenty of time (in s) to update player temperature
	 */
	public static final int NEEDED_TIME_TO_UPDATE = 1;

	public static int secondsToTicks(int seconds) {

		return seconds * TICKS_PER_SECOND;

	}


	@Override
    public void onInitialize() {
        LOGGER.info("StanleyLib initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}

	@Override
	public void initNamespaces() {

	}
}
