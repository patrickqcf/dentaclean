package org.dentaclean.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResponse {
    private Integer code;
    private String mensagem;

    public static SuccessResponse aply(String mensagem) {
        return new SuccessResponse(HttpStatus.OK.value(), mensagem);
    }
}
