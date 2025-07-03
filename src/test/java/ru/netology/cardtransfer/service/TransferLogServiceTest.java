package ru.netology.cardtransfer.service;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class TransferLogServiceTest {

    @Test
    void logTransfer_ShouldWriteLogFile() throws Exception {
        // Arrange
        TransferLogService service = new TransferLogService();
        String logFile = "transfer.log";
        File file = new File(logFile);
        if (file.exists()) file.delete();

        // Act
        service.logTransfer("A", "B", 500, "RUR", 5, "SUCCESS");

        // Assert
        assertTrue(file.exists());
        String content = new String(Files.readAllBytes(Paths.get(logFile)));
        assertTrue(content.contains("FROM: A"));
        assertTrue(content.contains("TO: B"));
        assertTrue(content.contains("AMOUNT: 500 RUR"));
        assertTrue(content.contains("COMMISSION: 5 RUR"));
        assertTrue(content.contains("SUCCESS"));

        // Clean up
        file.delete();
    }
}
