package com.matveyvs1987.service;

import com.matveyvs1987.entity.AppDocument;
import com.matveyvs1987.entity.AppPhoto;
import com.matveyvs1987.service.enums.LinkType;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {
    AppDocument processDoc(Message telegramMessage);
    AppPhoto processPhoto(Message telegaramMessage);
    String generateLink(Long docId, LinkType linkType);
}
