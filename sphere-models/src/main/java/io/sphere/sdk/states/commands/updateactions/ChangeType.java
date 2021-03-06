package io.sphere.sdk.states.commands.updateactions;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.states.State;
import io.sphere.sdk.states.StateType;

public class ChangeType extends UpdateAction<State> {
    private final StateType type;

    private ChangeType(final StateType type) {
        super("changeType");
        this.type = type;
    }

    public static ChangeType of(final StateType type) {
        return new ChangeType(type);
    }

    public StateType getType() {
        return type;
    }
}
