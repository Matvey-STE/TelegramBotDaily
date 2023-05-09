package com.matveyvs1987.service.Impl;

import com.matveyvs1987.UploadFileException;
import com.matveyvs1987.dao.RawDataDAO;
import com.matveyvs1987.entity.AppDocument;
import com.matveyvs1987.entity.AppPhoto;
import com.matveyvs1987.entity.RawData;
import com.matveyvs1987.service.FileService;
import com.matveyvs1987.service.MainService;
import com.matveyvs1987.service.ProducerService;
//todo make sure that all objects in common directory add to git like com.matveyvs1987
import com.matveyvs1987.dao.AppUserDAO;

import com.matveyvs1987.entity.AppUser;
import com.matveyvs1987.service.ServiceCommand;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static com.matveyvs1987.entity.enums.UserState.BASIC_STATE;
import static com.matveyvs1987.entity.enums.UserState.WAIT_FOR_EMAIL_STATE;
import static com.matveyvs1987.service.ServiceCommand.*;


@Service
@Log4j
public class MainServiceImpl implements MainService {
    private final RawDataDAO rawDataDAO;
    private final ProducerService producerService;
    private final AppUserDAO appUserDAO;
    private final FileService fileService;
    @Autowired
    public MainServiceImpl(RawDataDAO rawDataDAO, ProducerService producerService, AppUserDAO appUserDAO, FileService fileService) {
        this.rawDataDAO = rawDataDAO;
        this.producerService = producerService;
        this.appUserDAO = appUserDAO;
        this.fileService = fileService;
    }

    @Override
    public void processTextMessage(Update update) {
        saveRawData(update);

        var appUser = findOrSaveAppUser(update);
        var userState = appUser.getUserState();
        var text = update.getMessage().getText();
        var output = "";

        var serviceCommand = ServiceCommand.fromValue(text);

        if (CANCEL.equals(serviceCommand)){
            output = cancelProcess(appUser);
        } else if (BASIC_STATE.equals(userState)){
            output = processServiceCommands(appUser, text);
        } else if (WAIT_FOR_EMAIL_STATE.equals(userState)){
            // todo add email validation
        } else {
            log.error("Unknown user state " + appUser);
            output = "Unknown error. Write /cancel and try again!";
        }
        var chatId = update.getMessage().getChatId();
        sendAnswer(output,chatId);
    }

    @Override
    public void processDocMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var charId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(charId, appUser)){
            return;
        }
        try{
            AppDocument doc = fileService.processDoc(update.getMessage());
            // todo add link to file
            var answer = "Document download successfully! " +
                    "Link to download : http://test.ru/get-photo/777";
            sendAnswer(answer,charId);
        } catch (UploadFileException e){
            log.error(e);
            String error = "Unfortunately, faile to download file, please try again later.";
            sendAnswer(error,charId);
        }
    }
    @Override
    public void processPhotoMessage(Update update) {
        saveRawData(update);
        var appUser = findOrSaveAppUser(update);
        var charId = update.getMessage().getChatId();
        if (isNotAllowToSendContent(charId, appUser)){
            return;
        }
        try {
            // todo add link to photo
            AppPhoto photo = fileService.processPhoto(update.getMessage());
            var answer = "Picture download successfully! Link to download : http://test.ru/get-photo/777";
            sendAnswer(answer,charId);
        } catch (UploadFileException e){
            log.error(e);
            String error = "Unfortunately, faile to download file, please try again later.";
            sendAnswer(error,charId);
        }
    }

    private boolean isNotAllowToSendContent(Long charId, AppUser appUser) {
        var userState = appUser.getUserState();
        if (!appUser.getIsActive()){
            var error = "Please registrate and activate your account";
            sendAnswer(error,charId);
            return true;
        } else if (!BASIC_STATE.equals(userState)){
            var error = "Cancel current command with /cancel for sending files";
            sendAnswer(error,charId);
            return true;
        }
        return false;

    }
    private void sendAnswer(String output, Long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);
        producerService.producerAnswer(sendMessage);
    }

    private String processServiceCommands(AppUser appUser, String cmd) {
        var serviceCommand = ServiceCommand.fromValue(cmd);
        if (REGISTRATION.equals(serviceCommand)){
            //todo add registration
            return "Temporary unavailiable";
        } else if (HELP.equals(serviceCommand)){
            return help();
        } else if (START.equals(serviceCommand)){
            return "Hello user, to show available commands plese use /help";
        } else {
            return "Unknown command. Use /help command";
        }

    }

    private String help() {
        return "List of available commands\n"
                + "/cancel - cancel current command\n"
                + "/registration - registration of user.";
    }

    private String cancelProcess(AppUser appUser) {
        appUser.setUserState(BASIC_STATE);
        appUserDAO.save(appUser);
        return "Command cancelled";
    }

    private AppUser findOrSaveAppUser(Update update){
        User telegramUser = update.getMessage().getFrom();
        AppUser persistentAppUser = appUserDAO.findAppUserByTelegramUserId(telegramUser.getId());
        if (persistentAppUser == null){
            AppUser trinsientAppUser = AppUser.builder()
                    .telegramUserId(telegramUser.getId())
                    .userName(telegramUser.getUserName())
                    .firstName(telegramUser.getFirstName())
                    .lastName(telegramUser.getLastName())
                    //todo change value by default waiting to add email registartion
                    .isActive(true)
                    .userState(BASIC_STATE)
                    .build();
            return appUserDAO.save(trinsientAppUser);
        }
        return persistentAppUser;
    }
    private void saveRawData(Update update){
        RawData rawData = RawData.builder()
                .event(update)
                .build();
        rawDataDAO.save(rawData);
    }
}
