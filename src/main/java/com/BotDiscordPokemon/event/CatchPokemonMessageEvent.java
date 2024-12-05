package com.BotDiscordPokemon.event;

import com.BotDiscordPokemon.listener.EventListener;
import com.BotDiscordPokemon.listener.MessageListener;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CatchPokemonMessageEvent extends MessageListener implements EventListener<MessageCreateEvent> {

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return processPokemon(event.getMessage());
    }
}
