package com.example.weatherapp;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.RecordedRequest;

public class TestWeatherAppDispatcher extends Dispatcher {

    @Override
    public MockResponse dispatch(RecordedRequest recordedRequest) throws InterruptedException {
        String sample = "{\"city\":{\"id\":6167865,\"name\":\"Toronto\",\"coord\":{\"lon\":-79.416298,\"lat\":43.700111},\"country\":\"CA\",\"population\":0},\"cod\":\"200\"," +
                "\"message\":0.0107,\"cnt\":7,\"list\":[{\"dt\":1456246800,\"temp\":{\"day\":0.56,\"min\":-100,\"max\":100,\"night\":-4.3,\"eve\":-2.36,\"morn\":0.56},\"pressure" +
                "\":1005.09,\"humidity\":50,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":3.02,\"deg\":87,\"clouds\":12,\"snow" +
                "\":0.02},{\"dt\":1456333200,\"temp\":{\"day\":1.98,\"min\":-1.23,\"max\":1.98,\"night\":1.26,\"eve\":1.15,\"morn\":-1.23},\"pressure\":990.9,\"humidity\":87,\"weather\":" +
                "[{\"id\":602,\"main\":\"Snow\",\"description\":\"heavy snow\",\"icon\":\"13d\"}],\"speed\":6.8,\"deg\":69,\"clouds\":92,\"rain\":21.17,\"snow\":16.74},{\"dt\":1456419600,\"temp" +
                "\":{\"day\":1.09,\"min\":-8.86,\"max\":1.19,\"night\":-8.86,\"eve\":-1.73,\"morn\":0.75},\"pressure\":973.1,\"humidity\":81,\"weather\":[{\"id\":601,\"main\":\"Snow\",\"description\":" +
                "\"snow\",\"icon\":\"13d\"}],\"speed\":8.15,\"deg\":352,\"clouds\":88,\"snow\":4.13},{\"dt\":1456506000,\"temp\":{\"day\":-6.37,\"min\":-13.46,\"max\":-4.95,\"night\":-9.75,\"eve\":-8.4," +
                "\"morn\":-13.46},\"pressure\":995.93,\"humidity\":71,\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}],\"speed\":4.89,\"deg\":312,\"clouds\":0}," +
                "{\"dt\":1456592400,\"temp\":{\"day\":0.05,\"min\":-4.18,\"max\":1.09,\"night\":1.09,\"eve\":1.09,\"morn\":-4.18},\"pressure\":994.79,\"humidity\":0,\"weather\":[{\"id\":600,\"main\":\"Snow\"," +
                "\"description\":\"light snow\",\"icon\":\"13d\"}],\"speed\":11.11,\"deg\":238,\"clouds\":93,\"rain\":0.37,\"snow\":1.2},{\"dt\":1456678800,\"temp\":{\"day\":-2.92,\"min\":-6.14,\"max\":-0.39,\"" +
                "night\":-6.14,\"eve\":-4.89,\"morn\":-0.39},\"pressure\":994.75,\"humidity\":0,\"weather\":[{\"id\":601,\"main\":\"Snow\",\"description\":\"snow\",\"icon\":\"13d\"}],\"speed\":5.87,\"deg\":87,\"" +
                "clouds\":100,\"rain\":3.83,\"snow\":4.05},{\"dt\":1456765200,\"temp\":{\"day\":-6.5,\"min\":-9.71,\"max\":-6.5,\"night\":-9.71," +
                "\"eve\":-7.55,\"morn\":-7.02},\"pressure\":989.58,\"humidity\":0,\"weather\":[{\"id\":602,\"main\":\"Snow\",\"description\":\"heavy snow\",\"icon\":\"13d\"}],\"speed\":9.49,\"deg\":43,\"clouds\":100,\"snow\":22.08}]}";
        return new MockResponse().setBody(sample);
    }
}
