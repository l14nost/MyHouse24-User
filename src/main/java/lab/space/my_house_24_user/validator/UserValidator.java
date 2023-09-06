package lab.space.my_house_24_user.validator;

import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.enums.UserStatus;
import lab.space.my_house_24_user.repository.UserRepository;
import lab.space.my_house_24_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    private final UserService userService;
    public void uniqueEmail(String email, Long id, BindingResult result, String object){
        Locale locale = LocaleContextHolder.getLocale();
        String emailResponse;
        if (locale.toLanguageTag().equals("uk")) emailResponse = "Користувач із цією поштою вже існує";
        else emailResponse = "User with this email already exist";
        Optional<User> user = userRepository.findUserByEmail(email);
        if (id!=0){
            if (user.isPresent()) {
                if (!user.get().getId().equals(id)) {
                    result.addError(new FieldError(object, "email", emailResponse));
                }
            }
        }
        else{
            if (user.isPresent()){
                result.addError(new FieldError(object, "email", emailResponse));
            }
        }
    }

    public void ageValid(LocalDate date, BindingResult result,String object){
        Locale locale = LocaleContextHolder.getLocale();
        String ageResponse;
        if (locale.toLanguageTag().equals("uk")) ageResponse = "Дата народження не може бути у майбутньому";
        else ageResponse = "The date of birth cannot be in future";
        if (date.isAfter(LocalDate.now())){
            result.addError(new FieldError(object, "date", ageResponse));
        }
    }

    public void passwordMatch(String password, String confirmPassword, BindingResult result, String object){
        Locale locale = LocaleContextHolder.getLocale();
        String passwordResponse;
        String passwordLengthResponse;
        if (locale.toLanguageTag().equals("uk")) {
            passwordResponse = "Паролі не співпадають";
            passwordLengthResponse = "Розмір має бути більше ніж 4";
        }
        else{
            passwordResponse = "Passwords don't match";
            passwordLengthResponse = "Size must be more than 4";
        }
        if (!password.equals(confirmPassword)){
            result.addError(new FieldError(object, "password", passwordResponse));
            result.addError(new FieldError(object, "confirmPassword", passwordResponse));

        }
        else {
            if (password.length()<4){
                result.addError(new FieldError(object, "password", passwordLengthResponse));
                result.addError(new FieldError(object, "confirmPassword", passwordLengthResponse));
            }
        }
    }

    public void checkCurrentPassword(String password, BindingResult result, String object){
        String response;
        if (LocaleContextHolder.getLocale().toLanguageTag().equals("uk")) {
            response = "Пароль не співпадає";
        }
        else {
            response = "Password doesn't match";
        }
        User user = userService.findById(userService.getCurrentUser());
        BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!cryptPasswordEncoder.matches(password, user.getPassword())){
            result.addError(new FieldError(object, "currentPassword", response));
        }


    }


    public void checkConfirm(Boolean confirm, BindingResult result, String userAddRequest) {
        if (confirm == null || !confirm){
            result.addError(new FieldError(userAddRequest, "confirm", "Must be specified"));
        }
    }

    public void existByEmail(String email, BindingResult result, String object){
        if (!userRepository.existsByEmail(email)){
            result.addError(new FieldError(object, "email", "This email doesn't exist"));
        }

    }

    public void checkStatus(String email, BindingResult result, String object){
        User user = userService.findUserByEmail(email);
        if (!user.getUserStatus().equals(UserStatus.ACTIVE)){
            result.addError(new FieldError(object, "email", "User must be activated"));
        }

    }
}
