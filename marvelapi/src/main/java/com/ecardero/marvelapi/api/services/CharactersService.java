package com.ecardero.marvelapi.api.services;

import com.ecardero.marvelapi.api.exceptions.AuthorizationException;
import com.ecardero.marvelapi.api.exceptions.EntityNotFoundException;
import com.ecardero.marvelapi.api.exceptions.QueryException;
import com.ecardero.marvelapi.api.exceptions.RateLimitException;
import com.ecardero.marvelapi.api.objects.Character;
import com.ecardero.marvelapi.api.objects.Comic;
import com.ecardero.marvelapi.api.objects.Event;
import com.ecardero.marvelapi.api.objects.ref.DataWrapper;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by Enrique Cardero on 04/12/2016.
 */

public interface CharactersService {
    @GET("v1/public/characters")
    Call<DataWrapper<Character>> listCharacter();

    @GET("v1/public/characters")
    Call<DataWrapper<Character>> listCharacter(@QueryMap Map<String, String> options);

    @GET("v1/public/characters/{characterId}")
    Call<DataWrapper<Character>> getCharacter(@Path("characterId") int characterId) throws AuthorizationException, QueryException, RateLimitException, EntityNotFoundException;

    @GET("v1/public/characters/{characterId}/comics")
    Call<DataWrapper<Comic>> getCharacterComics(@Path("characterId") int characterId) throws AuthorizationException, QueryException, RateLimitException, EntityNotFoundException;

    @GET("v1/public/characters/{characterId}/comics")
    Call<DataWrapper<Comic>> getCharacterComics(@Path("characterId") int characterId, @QueryMap Map<String, String> options) throws AuthorizationException, QueryException, RateLimitException, EntityNotFoundException;

    @GET("v1/public/characters/{characterId}/events")
    Call<DataWrapper<Event>> getCharacterEvents(@Path("characterId") int characterId) throws AuthorizationException, QueryException, RateLimitException, EntityNotFoundException;

    @GET("v1/public/characters/{characterId}/events")
    Call<DataWrapper<Event>> getCharacterEvents(@Path("characterId") int characterId, @QueryMap Map<String, String> options) throws AuthorizationException, QueryException, RateLimitException, EntityNotFoundException;

    /*
    @GET("v1/public/characters/{characterId}/series")


    @GET("v1/public/characters/{characterId}/series")


    @GET("v1/public/characters/{characterId}/stories")


    @GET("v1/public/characters/{characterId}/stories")
    */

}
