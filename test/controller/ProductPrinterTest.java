package controller;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.text.ParseException;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import controller.print.OutputWriter;
import controller.print.ProductPrinter;
import products.Cheese;
import products.Wine;
import products.types.CheeseType;
import products.types.WineType;
import testutils.FakeClock;

public class ProductPrinterTest {

    @Test
    @DisplayName("Qualität wird ausgegeben")
    void testPrintsQuality() {
        OutputWriter writer = mock(OutputWriter.class);
        FakeClock clock = new FakeClock("09.06.2025");
        Wine wine = new Wine(WineType.beaujolais, 45, clock.getCurrentDate());
        ProductPrinter printer = new ProductPrinter(clock, writer);

        printer.printProductStatus(wine, clock.getCurrentDate());
        verify(writer).write(contains("Qualität"));
    }
    
    @Test
    @DisplayName("Käse wird als 'abgelaufen' markiert, wenn Verfallsdatum überschritten")
    void testPrintsExpired() throws ParseException {
        OutputWriter writer = mock(OutputWriter.class);
        FakeClock clock = new FakeClock("09.06.2025");
        Cheese cheese = new Cheese(CheeseType.quark, (byte) 100, DateTimeUtils.toDate("20.04.2025"), clock);
        clock.advanceDays(1000);
        ProductPrinter printer = new ProductPrinter(clock, writer);
        printer.printProductStatus(cheese, clock.getCurrentDate());
        verify(writer).write(contains(Constants.disposeMsg));
    }
    
    @Test
    @DisplayName("Wein wird nicht als 'abgelaufen' markiert")
    void testWineIsNotPrintedExpired() {
        FakeClock clock = new FakeClock("01.06.2025");
        Date delivered = clock.getCurrentDate();
        Wine wine = new Wine(WineType.tuetenwein, 20, delivered);

        clock.advanceDays(500); // Wein ist altlaber darf nicht ablaufen

        OutputWriter writer = mock(OutputWriter.class);
        ProductPrinter printer = new ProductPrinter(clock, writer);

        printer.printProductStatus(wine, clock.getCurrentDate());

        verify(writer).write(argThat(output ->
            !output.contains(Constants.expired)
        ));
    }
}
