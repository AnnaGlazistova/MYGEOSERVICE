package netology.geo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestGeoServiceImp {

    GeoServiceImpl sut;

    @BeforeEach
    public void initTest() {
        sut = new GeoServiceImpl();
    }

    @Test
    public void testByCoordinates() {
        var expected = RuntimeException.class;
        assertThrows(expected, () -> sut.byCoordinates(2.3, 0.0));
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testByIp(String ip, Location expected) {
        Location result = sut.byIp(ip);
        assertEquals(expected, result);
    }

    public static Stream<Arguments> source() {
        return Stream.of(Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.0.1", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.0.0.1", new Location("New York", Country.USA, null, 0)),
                Arguments.of("666.0.0.1", null));
    }
}
