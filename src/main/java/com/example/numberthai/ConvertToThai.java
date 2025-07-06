package com.example.numberthai;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

public class ConvertToThai {

    private static final String[] SCALE_TH = {"ล้าน", "สิบ", "ร้อย", "พัน", "หมื่น", "แสน", ""};
    private static final String[] DIGIT_TH = {"ศูนย์", "หนึ่ง", "สอง", "สาม", "สี่", "ห้า", "หก", "เจ็ด", "แปด",
            "เก้า"};
    private static final String[] SYMBOLS_TH = {"ลบ", "บาท", "ถ้วน", "สตางค์", "ยี่", "เอ็ด", ",", " ", "฿"};
    private static final String[] ZERO_SYMBOLS_TH = {"ศูนย์"};

    public static String format(double amount) {
        return toThaiBahtText(BigDecimal.valueOf(amount));
    }

    public static String format(int amount) {
        return toThaiBahtText(BigDecimal.valueOf(amount));
    }

    public static String format(long amount) {
        return toThaiBahtText(BigDecimal.valueOf(amount));
    }

    public static String format(String amount) {
        String sanitized = amount;
        for (String element : SYMBOLS_TH) {
            sanitized = sanitized.replace(element, "");
        }
        return toThaiBahtText(new BigDecimal(sanitized.trim()));
    }

    private static String toThaiBahtText(BigDecimal amount) {
        StringBuilder builder = new StringBuilder();
        BigDecimal absolute = amount.abs();
        int precision = absolute.precision();
        int scale = absolute.scale();
        int roundedPrecision = ((precision - scale) + 2);
        MathContext mc = new MathContext(roundedPrecision, RoundingMode.HALF_UP);
        BigDecimal rounded = absolute.round(mc);
        BigDecimal[] compound = rounded.divideAndRemainder(BigDecimal.ONE);
        boolean negativeAmount = (-1 == amount.compareTo(BigDecimal.ZERO));

        compound[0] = compound[0].setScale(0);
        compound[1] = compound[1].movePointRight(2);

        if (negativeAmount) {
            builder.append(SYMBOLS_TH[0]);
        }

        builder.append(getNumberText(compound[0].toBigIntegerExact()));
        if (compound[0].compareTo(BigDecimal.ZERO) == 0) {
            builder.append(ZERO_SYMBOLS_TH[0]);
        }

        builder.append(SYMBOLS_TH[1]);

        if (0 == compound[1].compareTo(BigDecimal.ZERO)) {
            builder.append(SYMBOLS_TH[2]);
        } else {
            builder.append(getNumberText(compound[1].toBigIntegerExact()));
            builder.append(SYMBOLS_TH[3]);
        }
        return builder.toString();
    }

    private static String getNumberText(BigInteger number) {
        StringBuilder builder = new StringBuilder();
        char[] digits = number.toString().toCharArray();

        for (int index = digits.length; index > 0; --index) {
            int digit = Integer.parseInt(String.valueOf(digits[digits.length
                    - index]));
            String digitText = DIGIT_TH[digit];
            int scaleIdx = ((1 < index) ? ((index - 1) % 6) : 6);

            if ((1 == scaleIdx) && (2 == digit)) {
                digitText = SYMBOLS_TH[4];
            }

            if (1 == digit) {
                switch (scaleIdx) {
                    case 0:
                    case 6:
                        builder.append((index < digits.length) ? SYMBOLS_TH[5] : digitText);
                        break;
                    case 1:
                        break;
                    default:
                        builder.append(digitText);
                        break;
                }
            } else if (0 == digit) {
                if (0 == scaleIdx) {
                    builder.append(SCALE_TH[scaleIdx]);
                }
                continue;
            } else {
                builder.append(digitText);
            }
            builder.append(SCALE_TH[scaleIdx]);
        }
        return builder.toString();
    }

    private ConvertToThai() {
    }
}
