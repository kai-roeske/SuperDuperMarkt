package controller.print;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import controller.Constants;
import controller.DateTimeUtils;
import products.Clock;
import products.Product;

/**
 * Kennt bestimmte Arten von Print-Befehlen und verarbeitet diese. Übernimmt die Formatierung und delegiert das 
 * Drucken an einen Writer, den er injiziert bekommt.
 */
public class ProductPrinter {
	
    private final Clock clock;
    private final OutputWriter writer;

    public ProductPrinter(Clock clock, OutputWriter writer) {
        this.clock = clock;
        this.writer = writer;
    }

    /**
     * Befehl, die einleitenden Informationen auszugeben
     */
    public void printInition() {
        writer.write(Constants.productDisplayStartMsg);
    }

    /**
     * Befehl, die Tages-Überschrift auszugeben
     */
    public void printDayHeader(Date date) {
        String prefix = date.equals(clock.getInitialDate()) ? "Startdatum " : "Heutiges Datum: ";
        writer.write(prefix + DateTimeUtils.formatDate(date));
        writer.write("---------------------------");
    }

    /**
     * Befehl, den tagesabhängigen Status eines Produkts auszugeben.
     * Formatiert eine Statusmeldung, die die Qualität, den Preis und gegebenenfalls einen Hinweis, dass die Ware aussortiert werden muss, 
     * enthält. Delegiert die Meldung an das Ausgabewerkzeug.
     * @param product Das Produkt, dessen Status ausgegeben werden soll
     * @param date Das Datum, für das der Status des Produkts ausgegeben werden soll
     */
    public void printProductStatus(Product product, Date date) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-30s", product.getLabel() + "):"))  
        .append(Constants.deliveredOn + " ").append( DateTimeUtils.formatDate(product.getDelivered()) + ", ")
        .append(Constants.price).append(": ")
          .append(BigDecimal.valueOf(product.getPrice()).setScale(2, RoundingMode.CEILING)).append(" €, ")
          .append(Constants.quality).append(": ")
          .append(product.getQuality(date) < 10 ? " " : "") 
          .append(product.getQuality(date));

        if (product.getExpiryDate() != null) {
            sb.append(", ").append(Constants.expiryDate).append(" ")
              .append(DateTimeUtils.formatDate(product.getExpiryDate()));
        }

        sb.append(getDisposalMessage(product, date));

        writer.write(sb.toString());
    }
    
    /**
     * Formatiert eine Meldung für das Aussortieren von Produkten. Abhängig von Art und Zustand des Produkts
     * @param product
     * @param date
     * @return die formatierte Meldung
     */
    private String getDisposalMessage(Product product, Date date) {
        boolean badQuality = product.isUnacceptableQuality(date);
        boolean expired =  product.getExpiryDate() != null && product.getExpiryDate().before(date);
        StringBuilder msg = new StringBuilder();
        if (badQuality || expired) {
            msg.append(" -> ").append(Constants.disposeMsg);
            if (badQuality) {
                msg.append(" ").append(Constants.qualityNotAcceptable);
            }
            if (expired) {
                if (badQuality) {
                	msg.append(", ");
                }
                msg.append(Constants.expired);
            }
        }
        return msg.toString();
    }

    public void printBlankLine() {
        writer.write("");
    }
}
