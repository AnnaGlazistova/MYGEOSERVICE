package netology.i18n;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLocalizationServiceImp{

    LocalizationServiceImpl sut;

    @BeforeEach
    public void initTest() {
        sut = new LocalizationServiceImpl();
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testLocale(Country country, String expectedMessage) {
        String result = sut.locale(country);
        assertEquals(expectedMessage, result);
    }

    public static Stream<Arguments> source() {
        return Stream.of(Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"));
    }
}

