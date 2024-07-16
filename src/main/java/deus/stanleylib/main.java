package deus.stanleylib;

import deus.stanleylib.core.PlayerTemperatureObserver;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class main implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "stanleylib";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	static final int TICKS_PER_SECOND = 20;

	/**
	 * The minimum temperature a player can have.
	 */
	public static final double MIN_TEMPERATURE = -50.0;

	/**
	 * The maximum temperature a player can have.
	 */
	public static final double MAX_TEMPERATURE = 50.0;

	/**
	 * The maximum temperature a player can have.
	 */
	public static final double DEFAULT_TEMPERATURE = 36.5;

	/**
	 * The quenty of time (in s) to update player temperature
	 */
	public static final int NEEDED_TIME_TO_UPDATE = 50;

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
