package com.matveyvs1987.service;

import com.matveyvs1987.entity.AppDocument;
import com.matveyvs1987.entity.AppPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegaramMessage);
}
