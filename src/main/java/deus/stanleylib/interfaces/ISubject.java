package deus.stanleylib.interfaces;

import deus.stanleylib.core.PlayerTemperatureObserver;
import deus.stanleylib.core.enums.PlayerTemperatureState;

public interface ISubject {
	void stanley_lib$removeObserver(PlayerTemperatureObserver observer);
	void stanley_lib$registerObserver(PlayerTemperatureObserver observer);
	void stanley_lib$notifyObservers();
	PlayerTemperatureState stanley_lib$getState();
}
