package deus.stanleylib.core;

import deus.stanleylib.core.enums.PlayerTemperatureState;
import deus.stanleylib.interfaces.IObserver;
import deus.stanleylib.interfaces.ISubject;

public class PlayerTemperatureObserver implements IObserver {
	private PlayerTemperatureState state;
	private ISubject subject;

	public void ConcreteObserver(ISubject subject) {
		this.subject = subject;
		this.subject.stanley_lib$registerObserver(this);
	}

	@Override
	public void stanley_lib$update() {
		this.state = subject.stanley_lib$getState();
		System.out.println("Updated state: " + state);
	}
}
