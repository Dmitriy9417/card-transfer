package ru.netology.cardtransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.netology.cardtransfer.model.*;
import ru.netology.cardtransfer.service.TransferLogService;

import java.util.UUID;

@RestController
public class TransferController {

    @Autowired
    private TransferLogService logService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest request) {
        // Пример вычисления комиссии: 1% от суммы (можно изменить)
        int commission = (int) Math.ceil(request.amount.value * 0.01);

        // Считаем результат успешным
        String operationId = UUID.randomUUID().toString();

        logService.logTransfer(
                request.cardFromNumber,
                request.cardToNumber,
                request.amount.value,
                request.amount.currency,
                commission,
                "SUCCESS, operationId=" + operationId
        );

        TransferResponse response = new TransferResponse();
        response.operationId = operationId;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<?> confirm(@RequestBody ConfirmRequest request) {
        // Просто логируем подтверждение
//        logService.logTransfer(
//                "-", // from
//                "-", // to
//                0,   // amount
//                "-", // currency
//                0,   // commission
//                "CONFIRM, operationId=" + request.operationId
//        );

        ConfirmResponse response = new ConfirmResponse();
        response.operationId = request.operationId;
        return ResponseEntity.ok(response);
    }
}
