package ru.netology.cardtransfer.service;


import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransferLogService {

    private static final String LOG_FILE = "transfer.log";
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void logTransfer(String from, String to, int amount, String currency, int commission, String result) {
        String dateTime = LocalDateTime.now().format(FORMAT);
        String logLine = String.format(
                "%s | FROM: %s | TO: %s | AMOUNT: %d %s | COMMISSION: %d %s | RESULT: %s%n",
                dateTime, from, to, amount, currency, commission, currency, result
        );
        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(logLine);
        } catch (IOException e) {
            // Можно добавить лог в консоль
            e.printStackTrace();
        }
    }
}
