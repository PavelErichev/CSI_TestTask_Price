import java.util.ArrayList;
import java.util.List;

public class PriceMerger {
    public List<Price> mergePrices(List<Price> existingPrices, List<Price> newPrices) {
        List<Price> mergedPrices = new ArrayList<>(existingPrices);

        for (Price newPrice : newPrices) {
            boolean isMerged = false;
            // Проверяем, совпадает ли новая цена с любой существующей ценой по коду продукта, номеру и отправлению
            for (Price existingPrice : existingPrices) {
                if (isPricesIdentical(existingPrice,newPrice)) {
                    // Если новая цена перекрывает существующую цену и имеет одинаковую стоимость
                    if (existingPrice.getBegin().compareTo(newPrice.getEnd()) <= 0 &&
                            existingPrice.getEnd().compareTo(newPrice.getBegin()) >= 0) {
                        if (existingPrice.getValue() == newPrice.getValue()) {
                            // Расширяем дату окончания существующей цены, чтобы она соответствовала новой цене
                            existingPrice.setEnd(newPrice.getEnd());
                        } else {
                            // Если значения отличаются, разделяем существующую цену на начало новой цены
                            existingPrice.setEnd(newPrice.getBegin());
                            mergedPrices.add(newPrice);
                        }
                        isMerged = true;
                        break;
                    }
                }
            }
            // Если новая цена не была объединена с любой существующей ценой, добавляем ее в объединенный список
            if (!isMerged) {
                mergedPrices.add(newPrice);
            }
        }
        return mergedPrices;
    }

    // Вспомогательный метод для проверки, идентичны ли две цены по коду продукта, номеру и отправлению
    private boolean isPricesIdentical(Price existingPrice, Price newPrice) {
        return  existingPrice.getProductCode().equals(newPrice.getProductCode()) &&
                existingPrice.getNumber() == newPrice.getNumber() &&
                existingPrice.getDepart() == newPrice.getDepart();
    }
}