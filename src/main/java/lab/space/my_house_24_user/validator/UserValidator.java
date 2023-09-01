package lab.space.my_house_24_user.validator;

import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
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
    public void uniqueEmail(String email, Long id, BindingResult result, String object){
        Locale locale = LocaleContextHolder.getLocale();
        String emailResponse;
        if (locale.toLanguageTag().equals("uk")) emailResponse = "Користувач із цією поштою вже існує";
        else emailResponse = "User with this email already exist";
        Optional<User> user = userRepository.findUserByEmail(email);
        if (id!=0){
            if (user.isPresent()) {
                if (user.get().getId()!=id) {
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


}
