package ru.netology.cardtransfer.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.cardtransfer.model.ConfirmRequest;
import ru.netology.cardtransfer.model.ConfirmResponse;
import ru.netology.cardtransfer.model.TransferRequest;
import ru.netology.cardtransfer.model.TransferResponse;
import ru.netology.cardtransfer.service.TransferLogService;

import java.util.UUID;

@RestController
public class TransferController {
    // Пример вычисления комиссии: 1% от суммы (можно изменить)
private final float comissionPercentage= 0.01F;
    @Autowired
    private TransferLogService logService;

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@Valid @RequestBody TransferRequest request) {
        int commission = (int) Math.ceil(request.amount.value * comissionPercentage);

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

        ConfirmResponse response = new ConfirmResponse();
        response.operationId = request.getOperationId();
        return ResponseEntity.ok(response);
    }
}
