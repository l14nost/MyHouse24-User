package lab.space.my_house_24_user.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum JobTitle {
    DIRECTOR("Director", "Директор"),
    MANAGER("Manager", "Менеджер"),
    ACCOUNTANT("Accountant", "Бухгалтер"),
    ELECTRIC("Electrician", "Електрик"),
    PLUMBER("Plumber", "Сантехнік");
    private final String nameEn;
    private final String nameUk;

    public String getJobTitle(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
