package lab.space.my_house_24_user.enums;

import lombok.RequiredArgsConstructor;

import java.util.Locale;

@RequiredArgsConstructor
public enum ArticleType {
    INCOME("Income", "Приход"),
    EXPENSE("Expense", "Витрата");
    private final String nameEn;
    private final String nameUk;

    public String getArticleType(Locale locale) {
        if (locale.getLanguage().equalsIgnoreCase("uk")) {
            return nameUk;
        }
        return nameEn;
    }
}
