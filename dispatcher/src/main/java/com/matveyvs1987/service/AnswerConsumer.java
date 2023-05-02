package com.matveyvs1987.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface AnswerConsumer {
    void consume (SendMessage sendMessage);
}
