package mate.academy.javabot.sevice;

import mate.academy.javabot.data.Meals;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class MateAcademyBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "recipeNevenchenovBot";
    }

    @Override
    public String getBotToken() {
        return "2100676697:AAGk4J3V2gPY3goSL3whNJap1WyGCEP2Mko";
    }

    @Override
    public void onUpdateReceived(Update update) {
        formMenuKeys();
        reply(update);
    }

    private void reply(Update update){
        Message message = update.getMessage();
        String userRequest = message.getText();
        String reply = analize(userRequest);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Hi! I've received from You\n" + " \"" + userRequest + "\". \n" + reply);
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.enableMarkdown(true);
        ReplyKeyboardMarkup keybord = formMenuKeys();
        sendMessage.setReplyMarkup(keybord);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String analize(String userRequest) {
        String response = "Choose option, please:";
        if(userRequest.equalsIgnoreCase("breakfast")){
            response = "Eat " + getMenu(Meals.BREAKFAST);
        }
        if(userRequest.equalsIgnoreCase("dinner")){
            response = "Eat " + getMenu(Meals.DINNER);
        }
        if(userRequest.equalsIgnoreCase("lunch")){
            response = "Eat " + getMenu(Meals.LUNCH);
        }if(userRequest.equalsIgnoreCase("supper")){
            response = "Eat " + getMenu(Meals.SUPPER);
        }
        return response;
    }

    private String getMenu(Meals FoodSchedule){
        return "'Some recipe for " + FoodSchedule + "'";
    }

    private ReplyKeyboardMarkup formMenuKeys(){
        ReplyKeyboardMarkup rkm = new ReplyKeyboardMarkup();
        rkm.setSelective(true);
        rkm.setResizeKeyboard(true);
        rkm.setOneTimeKeyboard(false);

        List<KeyboardRow> keysRows = new ArrayList<>();
        KeyboardRow firstKeysRow = new KeyboardRow();
        KeyboardRow secondKeysRow = new KeyboardRow();
        firstKeysRow.add(String.valueOf(Meals.BREAKFAST));
        firstKeysRow.add(String.valueOf(Meals.DINNER));
        secondKeysRow.add(String.valueOf(Meals.LUNCH));
        secondKeysRow.add(String.valueOf(Meals.SUPPER));
        keysRows.add(firstKeysRow);
        keysRows.add(secondKeysRow);

        rkm.setKeyboard(keysRows);
        return rkm;
    }
}
