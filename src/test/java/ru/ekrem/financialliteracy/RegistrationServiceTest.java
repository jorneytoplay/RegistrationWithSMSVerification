package ru.ekrem.financialliteracy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ekrem.financialliteracy.dao.PhoneSmsDAO;
import ru.ekrem.financialliteracy.entity.PhoneSms;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class RegistrationServiceTest {



    @MockBean
    PhoneSmsDAO phoneSmsDAO;

    @Test
    public void confirmPhoneTest() {
        String expectedPhone = "1234567890";
        Long expectedCode = 123456L;

        PhoneSms mockPhoneSms = new PhoneSms();
        mockPhoneSms.setPhone(expectedPhone);
        mockPhoneSms.setCode(expectedCode);

        when(phoneSmsDAO.findByPhoneAndCode(expectedPhone, expectedCode)).thenReturn(mockPhoneSms);

        PhoneSms actualPhoneSms = phoneSmsDAO.findByPhoneAndCode(expectedPhone, expectedCode);

        assertNotNull(actualPhoneSms);
        assertEquals(expectedPhone, actualPhoneSms.getPhone());
        assertEquals(expectedCode, actualPhoneSms.getCode());
    }
}
