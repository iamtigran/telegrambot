package io.proj3ct.tmbot.service;

import io.proj3ct.tmbot.config.BotConfig;
import io.proj3ct.tmbot.deadlines.Deadlines;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

   final BotConfig config;

   static final String HELP_TEXT = "This bot is created to demonstrate Spring capabilities\n\n" +
           "You can execute from main menu on the left or typing a command\n\n" +
           "Type /start to see a welcome message\n\n" +
           "Type /deadlines to see deadlines\n\n" +
           "Type /mydata to see data stored about yourself\n\n" +
           "Type /help to see this message again\n\n"+
           "Type /pss to see timetable of problem solving sessions";



    //Constructor
   public  TelegramBot(BotConfig config){

       this.config = config;
       List<BotCommand> listOfCommands = new ArrayList<>();
       listOfCommands.add(new BotCommand("/start","Start a new session"));
       listOfCommands.add(new BotCommand("/deadlines","Get all deadlines"));
       listOfCommands.add(new BotCommand("/thanks","Thank the Bot for its hard work"));
       listOfCommands.add(new BotCommand("/mydata","Get your data stored"));
       listOfCommands.add(new BotCommand("/deletedata","Delete your data"));
       listOfCommands.add(new BotCommand("/help","Info how to use this bot"));
       listOfCommands.add(new BotCommand("/setting","Set your preferences"));
       listOfCommands.add(new BotCommand("/pss","Problem Solving Session schedule"));

       try{
           this.execute(new SetMyCommands(listOfCommands,new BotCommandScopeDefault(),null));
       }
       catch (TelegramApiException e){
           log.error("Error setting bot's command list: "+ e.getMessage());
       }


   }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

       if(update.hasMessage() && update.getMessage().hasText()){
           long chatId = update.getMessage().getChatId();

           String messageText = update.getMessage().getText();
           switch (messageText){
               case "/start":
                   startCommandReceived(chatId,update.getMessage().getChat().getFirstName());
                break;

               case"/deadlines":
                   deadlinesCommandReceived(chatId,update.getMessage().getChat().getFirstName());
                   break;

               case "/pss":
                   pssCommandReceived(chatId,update.getMessage().getChat().getFirstName());
                   break;

               case "/thanks":
                   thankYouWasSaid(chatId,update.getMessage().getChat().getFirstName());
                   break;

               case "/help":
                   sendMessage(chatId,HELP_TEXT);
                   break;


               default: sendMessage(chatId,"Sorry, the command doesn't exist!");
           }

       }

    }


    private void startCommandReceived(long chatId, String name){
       String answer = "Hi "+name+", nice to meet you!";
       log.info("Replied to user!:" +name);

       sendMessage(chatId,answer);

    }

    private void sendMessage(long chatId, String textToSend){
        SendMessage message  = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
        }
        catch (TelegramApiException e){

            log.error("Error occurred: "+ e.getMessage());


        }

    }


    //Deadlines
    public  void deadlinesCommandReceived(long chatId, String name)  {
      // Deadlines deadline = new Deadlines();

       String answer = "Hi, dear "+name+", here are your deadlines: ";
       String deadlines = "Deadlines \n" + "Spring break! ";
       sendMessage(chatId,answer);
       sendMessage(chatId,deadlines);
        log.info("deadlines were told to user!:" +name);


    }

    public  void pssCommandReceived(long chatId, String name)  {
        // Deadlines deadline = new Deadlines();

        String pss =
                "Intro to Algorithms:\n" +
                "Saturdays, 10:00 - 11:30, room 307E\n"+
                "Tuesdays, Thursdays: 12:00 - 12:30, room 330W (for ind. needs)\n"+
                "Tuesdays, Thursdays: 10:30 - 11:45, room 207E\n\n"+
                "Cybersecurity: \n" +
                "Thursday 13:45-14:45\n\n"+
                "Special topics in cloud computing: \n"+
                "No PSS, we have Labs \n\n"+
                "Operating systems: \n"+
                "Wednesday 18:00-20:00, Friday 18:00 – 20:00 - Online";




        sendMessage(chatId,pss);
        log.info("pss were told to user!:" +name);


    }

    public  void thankYouWasSaid(long chatId, String name)  {
        String answer = "It is my pleasure, dear "+name+", to serve you: ";
        sendMessage(chatId,answer);
        log.info(name + " told thank you!");



    }
}
