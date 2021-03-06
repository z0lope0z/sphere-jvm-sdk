package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDslImpl;
import io.sphere.sdk.models.Versioned;

import java.util.List;

import static java.util.Arrays.asList;

/**
 {@doc.gen list actions}
 */
public class ChannelUpdateCommand extends UpdateCommandDslImpl<Channel> {
    public ChannelUpdateCommand(final Versioned<Channel> versioned, final List<? extends UpdateAction<Channel>> updateActions) {
        super(versioned, updateActions, ChannelsEndpoint.ENDPOINT);
    }

    public static ChannelUpdateCommand of(final Versioned<Channel> versioned, final List<? extends UpdateAction<Channel>> updateActions) {
        return new ChannelUpdateCommand(versioned, updateActions);
    }

    public static ChannelUpdateCommand of(final Versioned<Channel> versioned, final UpdateAction<Channel> updateAction) {
        return of(versioned, asList(updateAction));
    }
}
