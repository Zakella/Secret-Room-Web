package authentication.registration;

import com.secretroom.authentication.registration.EmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class EmailValidatorTest {

    private EmailValidator underTest;

    @BeforeEach
    void setUp() {
        underTest = new EmailValidator();
    }

    @Test
    void itShouldNotValidateEmailWhenItsNull() {
        //Given
        String expectedMessage = "email can be null!";
        //When

        //Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> underTest.validate(null));

        assertEquals(expectedMessage, exception.getMessage());

    }

    @Test
    void itShouldNotValidateEmailWhenNotFormat() {
        //Given
        List<String> checkList = Arrays.asList("zakellagmail.com",
                "1zakellagmail.com",
                "афвавы",
                "@maa;/"
        );

        checkList.forEach(email -> checkInvalidEmails(email));
    }

    @Test
    void itShouldValidateEmail() {
        //Given
        List<String> checkList = Arrays.asList("zakella@gmail.com",
                "joe.doe@.gmail.com",
                "donny11333@mail.ru",
                "Modr.zap@post.com"
        );

        checkList.forEach(
                email -> assertDoesNotThrow(() -> underTest.validate(email)));
    }

    void checkInvalidEmails(String email) {

        String expectedMessage = String.format("invalid email %s is valid!", email);
        //When

        //Then
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> underTest.validate(email));

        assertEquals(expectedMessage, exception.getMessage());


    }


}