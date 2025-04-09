package com.emarket.config;

import com.emarket.model.Clothing;
import com.emarket.model.ClothingSizeColor;
import com.emarket.model.Color;
import com.emarket.model.Size;
import com.emarket.repository.ClothingRepository;
import com.emarket.repository.ClothingSizeColorRepository;
import com.emarket.repository.ColorRepository;
import com.emarket.repository.SizeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final ClothingRepository clothingRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final ClothingSizeColorRepository clothingSizeColorRepository;

    @Override
    public void run(String... args) {
        if (clothingRepository.count() == 0) {
            Clothing c1 = new Clothing("Футболка", "Белая хлопковая футболка", 4.5f, 999.99f);
            Clothing c2 = new Clothing("Джинсы", "Синие джинсы slim fit", 4.8f, 2499.00f);
            Clothing c3 = new Clothing("Куртка", "Зимняя утеплённая куртка", 4.7f, 4999.00f);
            Clothing c4 = new Clothing("Платье", "Летнее платье с цветочным принтом", 4.3f, 1890.50f);
            Clothing c5 = new Clothing("Кроссовки", "Спортивные кроссовки для бега", 4.9f, 3290.00f);

            clothingRepository.saveAll(List.of(c1, c2, c3, c4, c5));
        }
        if (colorRepository.count() == 0) {
            colorRepository.saveAll(List.of(
                    new Color("Белый", "code"),
                    new Color("Чёрный", "code"),
                    new Color("Синий", "code")
            ));
        }
        if (sizeRepository.count() == 0) {
            sizeRepository.saveAll(List.of(
                    new Size("S"),
                    new Size("M"),
                    new Size("L")
            ));
        }

        // --- Связи ---
        if (clothingSizeColorRepository.count() == 0) {
            var clothingIterable = clothingRepository.findAll();
            var colorsIterable = colorRepository.findAll();
            var sizesIterable = sizeRepository.findAll();

            List<Clothing> clothingList = StreamSupport.stream(clothingIterable.spliterator(), false).toList();
            List<Color> colors = StreamSupport.stream(colorsIterable.spliterator(), false).toList();
            List<Size> sizes = StreamSupport.stream(sizesIterable.spliterator(), false).toList();
            // Примерные связи
            var links = List.of(
                    new ClothingSizeColor(clothingList.get(0), sizes.get(1), colors.get(0)), // футболка, M, белый
                    new ClothingSizeColor(clothingList.get(1), sizes.get(2), colors.get(2)), // джинсы, L, синий
                    new ClothingSizeColor(clothingList.get(2), sizes.get(1), colors.get(1)), // куртка, M, черный
                    new ClothingSizeColor(clothingList.get(3), sizes.get(0), colors.get(0)), // платье, S, белый
                    new ClothingSizeColor(clothingList.get(4), sizes.get(2), colors.get(1))  // кроссовки, L, черный
            );

            clothingSizeColorRepository.saveAll(links);
        }
    }
}
