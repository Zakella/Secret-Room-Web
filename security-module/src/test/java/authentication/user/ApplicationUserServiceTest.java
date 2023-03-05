package authentication.user;

import com.secretroom.authentication.security.ApplicationPasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static com.secretroom.authentication.user.ApplicationUserRole.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import com.secretroom.authentication.user.ApplicationUserRepository;
import com.secretroom.authentication.user.ApplicationUserService;
import com.secretroom.authentication.user.ApplicationUser;

@ExtendWith(MockitoExtension.class)
class ApplicationUserServiceTest {

    @Mock
    private ApplicationUserRepository applicationUserRepository;

    @Mock
    private ApplicationPasswordEncoder passwordEncoder;

    private ApplicationUserService underTest;


    @BeforeEach
    void setUp() {
        underTest = new ApplicationUserService(applicationUserRepository, passwordEncoder);
    }

    @Test
    void itShouldLReturnUserIfExists() {

        //Given

        ApplicationUser expectedUser = new ApplicationUser(
                "Slava",
                "Zapolchi",
                "zap@gmail.com",
                "1234",
                USER

        );

        when(applicationUserRepository.findByEmail(expectedUser.getEmail()))
                .thenReturn(Optional.of(expectedUser));

        //when
        UserDetails actualUser = underTest.loadUserByUsername(expectedUser.getEmail());

        //then
        assertEquals(actualUser, expectedUser);


    }

    @Test
    void itShouldLThrowExceptionIfUserNotExists() {

        //Given

        ApplicationUser expectedUser = new ApplicationUser(
                "Slava",
                "Zapolchi",
                "zap@gmail.com",
                "1234",
                USER

        );

        String USER_NOT_FOUND_MSG =
                "user with email %s not found";


        when(applicationUserRepository.findByEmail(expectedUser.getEmail()))
                .thenReturn(Optional.empty());

        //then

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class,
                () -> underTest.loadUserByUsername(expectedUser.getEmail()));

        assertEquals(usernameNotFoundException.getMessage(),
                String.format(USER_NOT_FOUND_MSG, expectedUser.getEmail()));

    }


    @Test
    void itShouldThrowExceptionWhenSignUpUser() {
        //Given
        ApplicationUser expectedUser = new ApplicationUser(
                "Slava",
                "Zapolchi",
                "zap@gmail.com",
                "1234",
                USER

        );

        //When
        when(applicationUserRepository.findByEmail(expectedUser.getEmail()))
                .thenReturn(Optional.of(expectedUser));
        //Then

        ResponseEntity response = underTest.signUpUser(expectedUser);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("User with email already exists", response.getBody());

    }

    @Test
    void itShouldSaveSignUpUser() {
        //Given
        ApplicationUser expectedUser = new ApplicationUser(
                "Slava",
                "Zapolchi",
                "zap@gmail.com",
                "1234",
                USER

        );

        //When
        when(applicationUserRepository.findByEmail(expectedUser.getEmail()))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(expectedUser.getPassword())).thenReturn("encodedpassword");
        //Then

        ResponseEntity<String> response = underTest.signUpUser(expectedUser);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User successfully signed up", response.getBody());


    }
}