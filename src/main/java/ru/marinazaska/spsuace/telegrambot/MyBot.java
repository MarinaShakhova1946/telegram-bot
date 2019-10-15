package ru.marinazaska.spsuace.telegrambot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {

    public static final String USERNAME = "@Mashas_SKA_Bot";
    public static final String TOKEN = "725156186:AAGJ_40zF7T5pFg50uruFTz_iz-Cqui1jnc";

    public MyBot(DefaultBotOptions botOptions) {
        super(botOptions);
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
            botOptions.setProxyHost("177.8.226.254");
            botOptions.setProxyPort(8080);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
            telegramBotsApi.registerBot(new MyBot(botOptions));
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    public static SendMessage sendInlineKeyBoardMessage(long chatId) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();

        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Jokerit").setSwitchInlineQueryCurrentChat("Team \"Jokerit\" is very strong"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("SKA").setUrl("ska.ru"));
        rowList.add(keyboardButtonsRow1);

        List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Avangard").setSwitchInlineQuery("Team \"Avangard\" is very cool"));
        keyboardButtonsRow1.add(new InlineKeyboardButton().setText("Ak Bars").setCallbackData("Team \"Ak Bars\" is very technical"));
        rowList.add(keyboardButtonsRow2);

        inlineKeyboardMarkup.setKeyboard(rowList);
        return new SendMessage().setChatId(chatId).setText("What do you choose?").setReplyMarkup(inlineKeyboardMarkup);
    }

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();
        SendMessage sendMessage = new SendMessage().setChatId((update.getMessage().getChatId()));
        if (update.getMessage().getText().equals("Team")) {
            try {
                execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else {
            if (update.getMessage().getText().equals("/hello")) {
                sendMessage.setText(String.valueOf(new HelloCommand()));
            } else if (update.getMessage().getText().equals("Who is the champion?")) {
                sendMessage.setText("SKA IS THE BEST!!!");
            } else {
                sendMessage.setText("If you like hockey, ask who is the champion:)");
            }
            try {
                execute(sendMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public String getBotToken() {
        return TOKEN;
    }
}


