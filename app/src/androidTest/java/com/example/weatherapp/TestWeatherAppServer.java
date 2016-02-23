package com.example.weatherapp;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class TestWeatherAppServer{

    MockWebServer server;

    public TestWeatherAppServer(){

        server = new MockWebServer();
        TestWeatherAppDispatcher dispatcher = new TestWeatherAppDispatcher();
        server.setDispatcher(dispatcher);
    }

    public MockWebServer getMockWebServer(){

        return server;
    }
}
