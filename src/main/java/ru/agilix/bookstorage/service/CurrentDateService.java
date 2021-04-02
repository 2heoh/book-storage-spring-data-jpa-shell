package ru.agilix.bookstorage.service;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CurrentDateService implements DateService {
    @Override
    public Timestamp getCurrentDate() {
        return new Timestamp(System.currentTimeMillis());
    }
}
