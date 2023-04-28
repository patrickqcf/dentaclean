package org.dentaclean.api.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {

    private Integer code;
    private String uuid;
    private String mensagem;

    public static SuccessResponse aply(String mensagem, String uuid) {
        return new SuccessResponse(HttpStatus.ACCEPTED.value(), uuid, mensagem);
    }
}
