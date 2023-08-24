package org.dentaclean;


import io.qameta.allure.Attachment;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.junit4.Tag;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import static io.qameta.allure.SeverityLevel.CRITICAL;
import static org.junit.Assert.assertTrue;


@Feature("Teste")
@Severity(value = CRITICAL)
public class Validation {

    @Test
    @DisplayName("Validando")
    @Tag("API")
    @Severity(CRITICAL)
    public void teste(){
        assertTrue(true);
    }
}
