import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class PriceMergerTest {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static Date convertFromString2Date(String strDate) {
        return Date.from(LocalDateTime.parse(strDate, dtf).atZone(ZoneId.systemDefault()).toInstant());
    }

    @Test
    public void testMergePrices() {
        PriceMerger priceMerger = new PriceMerger();

        Price price1 = new Price(1, "122856", 1, 1, convertFromString2Date("01.01.2013 00:00:00"), convertFromString2Date("31.01.2013 23:59:59"), 11000);
        Price price2 = new Price(2, "122856", 2, 1, convertFromString2Date("10.01.2013 00:00:00"),convertFromString2Date("20.01.2013 23:59:59") , 99000);
        Price price3 = new Price(3, "6654", 1, 2, convertFromString2Date("01.01.2013 00:00:00"), convertFromString2Date("31.01.2013 00:00:00"), 5000);

        Price newPrice1 = new Price(4, "122856", 1, 1, convertFromString2Date("20.01.2013 00:00:00"), convertFromString2Date("20.02.2013 23:59:59"), 11000);
        Price newPrice2 = new Price(5, "122856", 2, 1, convertFromString2Date("15.01.2013 00:00:00"), convertFromString2Date("25.01.2013 23:59:59"), 92000);
        Price newPrice3 = new Price(6, "6654", 1, 2, convertFromString2Date("12.01.2013 00:00:00"), convertFromString2Date("13.01.2013 00:00:00"), 4000);

        List<Price> existingPrices = Arrays.asList(price1, price2, price3);
        List<Price> newPrices = Arrays.asList(newPrice1, newPrice2, newPrice3);

        List<Price> mergedPrices = priceMerger.mergePrices(existingPrices,newPrices);

        Assert.assertEquals(5, mergedPrices.size());

        // Проверка первого объединенного объекта
        Assert.assertEquals(price1.getBegin(), mergedPrices.get(0).getBegin());
        Assert.assertEquals(price1.getEnd(), mergedPrices.get(0).getEnd());
        Assert.assertEquals(price1.getValue(), mergedPrices.get(0).getValue());

        // Проверка второго объединенного объекта
        Assert.assertEquals(price2.getBegin(), mergedPrices.get(1).getBegin());
        Assert.assertEquals(price2.getEnd(), mergedPrices.get(1).getEnd());
        Assert.assertEquals(price2.getValue(), mergedPrices.get(1).getValue());

        // Проверка третьего объединенного объекта
        Assert.assertEquals(price3.getBegin(), mergedPrices.get(2).getBegin());
        Assert.assertEquals(price3.getEnd(), mergedPrices.get(2).getEnd());
        Assert.assertEquals(price3.getValue(), mergedPrices.get(2).getValue());

        // Проверка четвертого объединенного объекта
        Assert.assertEquals(newPrice2.getBegin(), mergedPrices.get(3).getBegin());
        Assert.assertEquals(newPrice2.getEnd(), mergedPrices.get(3).getEnd());
        Assert.assertEquals(newPrice2.getValue(), mergedPrices.get(3).getValue());

        // Проверка пятого объединенного объекта
        Assert.assertEquals(newPrice3.getBegin(), mergedPrices.get(4).getBegin());
        Assert.assertEquals(newPrice3.getEnd(), mergedPrices.get(4).getEnd());
        Assert.assertEquals(newPrice3.getValue(), mergedPrices.get(4).getValue());
    }
}