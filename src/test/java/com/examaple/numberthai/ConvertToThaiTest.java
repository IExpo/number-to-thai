package com.examaple.numberthai;

import com.example.numberthai.ConvertToThai;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertToThaiTest {

    @ParameterizedTest(name = "#{index} → \"{1}\" ➜ \"{0}\"")
    @MethodSource("testCases")
    void format_shouldReturnExpectedThaiText(String expected, Object input) {

        String actual = switch (input) {
            case String s -> ConvertToThai.format(s);
            case Integer i -> ConvertToThai.format(i);
            case Long l -> ConvertToThai.format(l);
            case Double d -> ConvertToThai.format(d);
            case null, default -> ConvertToThai.format(String.valueOf(input));
        };
        assertEquals(expected, actual);
    }


    private static Stream<Arguments> testCases() {
        return Stream.of(
                Arguments.of("ศูนย์บาทถ้วน", 0.00000000),
                Arguments.of("สิบแปดล้านสามแสนสามหมื่นสามพันสามร้อยยี่สิบสองบาทสองสตางค์", 18333322.02),
                Arguments.of("หนึ่งพันสองร้อยยี่สิบสองบาทยี่สิบสองสตางค์", 1222.216),
                Arguments.of("ศูนย์บาทสิบเอ็ดสตางค์", 0.11),
                Arguments.of("ลบหนึ่งบาทยี่สิบสองสตางค์", -1.2222),
                Arguments.of("แปดร้อยแปดสิบสองบาทยี่สิบสามสตางค์", 0882.2312),
                Arguments.of("หนึ่งพันสองร้อยยี่สิบสามบาทเก้าสตางค์", 1223.092),
                Arguments.of("หนึ่งร้อยยี่สิบสามล้านสองแสนหนึ่งหมื่นสี่พันหนึ่งร้อยยี่สิบสี่บาทยี่สิบสองสตางค์", 123214124.22),
                Arguments.of("สองพันสองร้อยยี่สิบสามบาทห้าสตางค์", 2223.05),
                Arguments.of("ศูนย์บาทถ้วน", 0E-8),
                Arguments.of("สองพันสองร้อยยี่สิบเอ็ดบาทถ้วน", 2221),
                Arguments.of("สองร้อยยี่สิบสามบาทถ้วน", 223),
                Arguments.of("สองพันสองร้อยบาทถ้วน", 2200),
                Arguments.of("ลบหนึ่งพันสองร้อยสามสิบสี่บาทห้าสิบสี่สตางค์", "-1,234.54"),
                Arguments.of("หนึ่งพันสองร้อยสามสิบสี่บาทห้าสิบสี่สตางค์", "1,234.54"),
                Arguments.of("หนึ่งพันสองร้อยสามสิบสี่บาทห้าสิบสี่สตางค์", "1,234.54฿")
        );
    }
}
