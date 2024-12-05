package com.BotDiscordPokemon.listener;

import com.BotDiscordPokemon.service.ConsumePokemonApiService;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    @Autowired
    ConsumePokemonApiService consumePokemonApiService;

    public Mono<Void> processMessage(Message userMessage) {
        User chatUser = userMessage.getAuthor().get();
        return Mono.just(userMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().equalsIgnoreCase("!saludo"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Hola, " + chatUser.getUsername() + " como te encuentras, esto es un bot de prueba :)"))
                .then();
    }

    public Mono<Void> processPokemon(Message userMessage) {
        String command = userMessage.getContent().split("\\s")[0];
        String commandParams = userMessage.getContent().split("\\s")[1];
        return Mono.just(userMessage)
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .filter(message -> message.getContent().contains(command))
                .flatMap(Message::getChannel)
                .flatMap(channel -> {
                    String pokemonImage = consumePokemonApiService.getPokeApiPokemonImage(commandParams);
                    pokemonImage = pokemonImage.contains("Error") ? "Ha ocurrido un error al intentar traer la imagen del pokemon" : pokemonImage;
                    return channel.createMessage(pokemonImage);
                })
                .then();
    }

}
