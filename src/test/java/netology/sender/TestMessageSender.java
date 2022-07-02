package netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMessageSender {

    @ParameterizedTest
    @MethodSource("source")
    public void testSend(String expectedMessage,
                         GeoService geoService,LocalizationService localizationService, String ip) {
        MessageSenderImpl sender = new MessageSenderImpl(geoService,localizationService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        String result = sender.send(headers);
        assertEquals(expectedMessage, result);
    }

    public static Stream<Arguments> source() {
        GeoService geoServiceRU = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceRU.byIp(Mockito.any()))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationServiceRU = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceRU.locale(Mockito.any()))
                .thenReturn("Добро пожаловать");

        GeoService geoServiceUSA = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceUSA.byIp(Mockito.any()))
                .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

        LocalizationService localizationServiceEN = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceEN.locale(Mockito.any()))
                .thenReturn("Welcome");

        GeoService geoServiceGer = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceGer.byIp(Mockito.any()))
                .thenReturn(new Location("New York", Country.USA, " 10th Avenue", 32));

        return Stream.of(
                Arguments.of("Добро пожаловать", geoServiceRU, localizationServiceRU,"172.0.32.11"),
                Arguments.of("Welcome",geoServiceUSA, localizationServiceEN,"96.44.183.149"),
                Arguments.of("Welcome", geoServiceGer, localizationServiceEN, "0"));
    }


    @Test
    public void testSend(){
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(Mockito.any()))
                .thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Mockito.any()))
                .thenReturn("Добро пожаловать");

        MessageSenderImpl sender = new MessageSenderImpl(geoService,localizationService);

        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        String result = sender.send(headers);

        String expected = "Добро пожаловать";
        Assertions.assertEquals(expected, result);
    }


}
