package com.BotDiscordPokemon.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ConsumePokemonApiService {

    @Value("${api.pokemon}")
    private String pokeApiUrl;

    public String getPokeApiPokemonImage(String pokemon) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<String, Object> response = restTemplate.getForObject(pokeApiUrl + pokemon, Map.class);
            if (response != null) {
                Map<String, Object> spritesVar = (Map<String, Object>) response.get("sprites");
                return (String) spritesVar.get("front_default");
            } else
                return "Pokemon No Encontrado";
        } catch (RestClientException e) {
            System.out.println("Program get an exception when try to connect to poke api, Error: " + e.getMessage());
            return "Rest Client Error";
        } catch (ClassCastException e) {
            System.out.println("Error when try to catch the response object, Error: " + e.getMessage());
            return "ClassCast Error";
        }

    }

}
