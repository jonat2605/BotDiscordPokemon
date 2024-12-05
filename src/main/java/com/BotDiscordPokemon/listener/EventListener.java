package com.BotDiscordPokemon.listener;

import discord4j.core.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public interface EventListener<T extends Event>{

    Logger logger = LoggerFactory.getLogger(EventListener.class);

    Class<T> getEventType();
    Mono<Void> execute(T event);

    default Mono<Void> handleError(Throwable error) {
        logger.error("Unable to process event{}", getEventType().getSimpleName(), error);
        return Mono.empty();
    }

}
