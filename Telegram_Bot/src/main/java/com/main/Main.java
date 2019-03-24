package com.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.bot.SamSang;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();
        ApplicationContext context = new GenericXmlApplicationContext("appCTX.xml");
        
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(context.getBean("SamSang_bot", SamSang.class));
           
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}