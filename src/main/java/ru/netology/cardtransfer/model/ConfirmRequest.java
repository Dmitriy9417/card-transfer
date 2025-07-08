package ru.netology.cardtransfer.model;

import lombok.Getter;

@Getter
public class ConfirmRequest {
    private String operationId;
    private String code;

}