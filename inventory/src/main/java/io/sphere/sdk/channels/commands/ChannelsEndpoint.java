package io.sphere.sdk.channels.commands;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.http.JsonEndpoint;

final class ChannelsEndpoint {
    static JsonEndpoint<Channel> ENDPOINT = JsonEndpoint.of(Channel.typeReference(), "/channels");
}