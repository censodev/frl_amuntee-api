package com.printway.business.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;
import com.printway.business.repositories.ConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TelegramServiceImpl implements TelegramService {
    @Value("${telegram.bot.reply-timeout}")
    private int botReplyTimeout;

    @Autowired
    private ConfigRepository configRepository;

    @Autowired
    private FacebookService facebookService;

    TelegramBot bot;

    private interface Command {
        String START = "/start";
        String ADACCOUNTS = "/adaccounts";
        String ADACCOUNTS_DISABLED = "/adaccountsdisabled";
        String ADACCOUNTS_THRESHOLD = "/adaccountsthreshold";
    }

    @PostConstruct
    protected void postConstruct() throws Exception {
        var config = configRepository.findFirstByStatus(1);
        if (config == null)
            throw new Exception("Config is not set");
        bot = new TelegramBot(config.getTelegramBotToken());
        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                GetUpdates getUpdates = new GetUpdates().limit(100).offset(0).timeout(0);
                var updatesResponse = bot.execute(getUpdates);
                updatesResponse.updates().stream()
                        .filter(update -> {
                            var date = update.message().date();
                            var now = new Date().toInstant().getEpochSecond();
                            return now - date <= botReplyTimeout;
                        }).forEach(update -> {
                            var chatId = update.message().chat().id();
                            bot.execute(waitingMessage(chatId));
                            try {
                                var message = getMessage(chatId, update.message().text());
                                bot.execute(message);
                            } catch (Exception e) {
                                e.printStackTrace();
                                bot.execute(errMessage(chatId, e.getMessage()));
                            }
                        });
            }
        },0, botReplyTimeout * 1000);
    }

    private SendMessage getMessage(Object chatId, String command) throws Exception {
        switch (command) {
            case Command.ADACCOUNTS:
                return adaccountsMessage(chatId);
            case Command.ADACCOUNTS_DISABLED:
                return adaccountsDisabledMessage(chatId);
            case Command.ADACCOUNTS_THRESHOLD:
                return adaccountsThresholdMessage(chatId);
            case Command.START:
            default:
                return startMessage(chatId);
        }
    }

    private SendMessage waitingMessage(Object chatId) {
        return new SendMessage(chatId, "Please wait a minute...");
    }

    private SendMessage errMessage(Object chatId, String message) {
        return new SendMessage(chatId, "Process run failed! " + message);
    }

    private SendMessage startMessage(Object chatId) {
        var text = "Commands:\n" +
                Command.ADACCOUNTS + ": Get all ads accounts\n" +
                Command.ADACCOUNTS_DISABLED + ": Get disabled ads accounts\n" +
                Command.ADACCOUNTS_THRESHOLD + ": Get ads accounts reach threshold\n";
        return new SendMessage(chatId, text);
    }
    
    private SendMessage adaccountsMessage(Object chatId) throws Exception {
        var text = facebookService.fetchAdAccounts().toTable();
        return new SendMessage(chatId, text)
                .parseMode(ParseMode.Markdown)
                .disableWebPagePreview(true)
                .disableNotification(false);
    }

    private SendMessage adaccountsDisabledMessage(Object chatId) throws Exception {
        var acc = facebookService.fetchAdAccounts();
        acc.setData(acc.getData().stream().filter(i -> i.getAccountStatus() != 1).collect(Collectors.toList()));
        var text = acc.toTable();
        return new SendMessage(chatId, text)
                .parseMode(ParseMode.Markdown)
                .disableWebPagePreview(true)
                .disableNotification(false);
    }

    private SendMessage adaccountsThresholdMessage(Object chatId) throws Exception {
        var acc = facebookService.fetchAdAccounts();
        acc.setData(acc.getData().stream().filter(i -> i.getAmountSpent().equals(i.getSpendCap())).collect(Collectors.toList()));
        var text = acc.toTable();
        return new SendMessage(chatId, text)
                .parseMode(ParseMode.Markdown)
                .disableWebPagePreview(true)
                .disableNotification(false);
    }
}
