package com.example;

import net.fabricmc.api.ClientModInitializer;

public class ExampleModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This code runs on the client when the game starts.
        System.out.println("Hello from the client entrypoint!");
    }
}