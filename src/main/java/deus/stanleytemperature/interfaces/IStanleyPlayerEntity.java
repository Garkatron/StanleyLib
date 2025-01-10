package deus.stanleytemperature.interfaces;

import deus.stanleytemperature.enums.PlayerTemperatureState;
import net.minecraft.core.block.Block;

public interface IStanleyPlayerEntity extends IPlayerEntity {
	/**
	 * Gets the current temperature of the player.
	 *
	 * @return The current temperature as a float.
	 */
	double stanley$getPlayerTemperature();

	/**
	 * Gets the previous temperature of the player.
	 *
	 * @return The current temperature as a float.
	 */
	double stanley$getPlayerPreviousTemperature();


	/**
	 * Sets the player's temperature to a specific value.
	 *
	 * @param temperature The new temperature value.
	 */
	void stanley$setPlayerTemperature(double temperature);

	/**
	 * Increases the player's temperature by a specified amount.
	 *
	 * @param amount The amount to increase the temperature by.
	 */
	void stanley$increasePlayerTemperature(double amount);

	/**
	 * Decreases the player's temperature by a specified amount.
	 *
	 * @param amount The amount to decrease the temperature by.
	 */
	void stanley$decreasePlayerTemperature(double amount);

	/**
	 * Checks if the player is overheating.
	 *
	 * @return True if the player is overheating, false otherwise.
	 */
	boolean stanley$isPlayerOverheating();

	/**
	 * Checks if the player is freezing.
	 *
	 * @return True if the player is freezing, false otherwise.
	 */
	boolean stanley$isPlayerFreezing();

	/**
	 * Resets the player's temperature to the default value.
	 **/
	void stanley$resetPlayerTemperature();

	/**
	 * Kills the player's by freezing.
	 **/
	void stanley$killByFreezing();

	/**
	 * Kills the player's by overheating.
	 **/
	void stanley$killByOverheating();

	/**
	 * Hurt's the player by freezing
	 *
	 * @param amount The amount to hurt the player.
	 **/
	void stanley$hurtByFreezing(int amount);

	/**
	 * Hurt's the player by freezing
	 *
	 * @param amount The amount to hurt the player.
	 **/
	void stanley$hurtByOverheating(int amount);

	/**
	 * Hurt's the player by cold
	 *
	 * @param amount The amount to hurt the player.
	 **/
	void stanley$hurtByCold(int amount);

	/**
	 * Hurt's the player by heat
	 *
	 * @param amount The amount to hurt the player.
	 **/
	void stanley$hurtByHeat(int amount);

	/**
	 * Update's player temperature
	 **/
	void stanley$updateTemperature();

	/**
	 * Sets the player's temperature state to a specific value.
	 *
	 * @param state The new temperature value.
	 **/
	void stanley$setTemperatureState(PlayerTemperatureState state);

	/**
	 * Get the player's temperature state.
	 **/
	PlayerTemperatureState stanley$getState();

	/**
	 * Get a heat source near of the player
	 **/
	Block stanley$getHeatSource();

}
