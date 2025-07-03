package ru.netology.cardtransfer.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.cardtransfer.model.TransferRequest;
import ru.netology.cardtransfer.model.TransferRequest.Amount;
import ru.netology.cardtransfer.model.TransferResponse;
import ru.netology.cardtransfer.service.TransferLogService;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

    @Mock
    TransferLogService logService;

    @InjectMocks
    TransferController controller;

    @Test
    void transfer_ShouldCallLogService_AndReturnOperationId() {
        // given
        TransferRequest request = new TransferRequest();
        request.cardFromNumber = "1111";
        request.cardToNumber = "2222";
        Amount amount = new Amount();
        amount.value = 10000; // 100 рублей (пример)
        amount.currency = "RUR";
        request.amount = amount;

        // when
        ResponseEntity<?> response = controller.transfer(request);

        // then
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof TransferResponse);
        String operationId = ((TransferResponse) response.getBody()).operationId;
        assertNotNull(operationId);

        // verify logService called with correct params
        verify(logService, times(1)).logTransfer(
                eq("1111"),
                eq("2222"),
                eq(10000),
                eq("RUR"),
                eq(100), // комиссия (1%)
                startsWith("SUCCESS, operationId=")
        );
    }
}
