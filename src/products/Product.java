package products;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import controller.Constants.ProductType;
import controller.Constants.Specification;

public abstract class Product {
	
	/**
	 * Minimaltolerierbare Qualität
	 */
	protected int minimumQuality;
	
	protected ProductType productType;
	
	/**
	 * Bestmögliche Qualität
	 */
	protected int initialQuality;
	
	/**
	 * Lieferdatum. Zeitpunkt, an dem die Ware die optimale Qualität hatte.
	 */
	protected Date delivered;
	
	protected Date expiryDate;

	/**
	 * Tage ab Lieferung, nach denen das Verfallsdatum erreicht ist.
	 */
	private Integer daysUntilExpiry;

	/**
	 * Produktlinie (Gouda, Limburger, Beajaulais, Tütenwein)
	 */
	protected Specification specification;
	
	/**
	 * 
	 * @param productType 
	 * @param initialQuality Qualität, in der geliefert wurde
	 * @param basePrice Preis am Tag der Lieferung
	 * @param delivered Lieferdatum
	 * @param daysUntilExpiry  
	 */
	public Product(ProductType productType, Specification specification, int initialQuality, Date delivered, Integer daysUntilExpiry) {
		super();
		this.productType = productType;
		this.specification = specification;
		this.initialQuality = initialQuality;
		this.delivered = delivered;
		this.daysUntilExpiry = daysUntilExpiry;
	}

	/**
	 * Calculates quality, 
	 */
	public abstract int getQuality(Date current);
	
	
	public abstract double getPrice();
	
	
	public String getName() {
		return specification.getName();
	}
	
	/**
	 * Checks if quality is still good (enough) to be stored/offered
	 * @param current - current date
	 */
	public boolean isAcceptableQuality(Date current) {
		return getQuality(current) - minimumQuality >= 0;
	}
	
	/**
	 * @param current - current date
	 * @return true if not acceptable to be offered
	 */
	public boolean isUnacceptableQuality(Date current) {
		return !isAcceptableQuality(current);
	}

	public Date getDelivered() {
		return delivered;
	}
	
	public String getLabel() {
		return productType.getName() + "("+  getName() ;
	}
	
	public Date getExpiryDate() {
		if(daysUntilExpiry == null) { // Es gibt Produkte, die nicht verfallen
			return null;
		}
		LocalDate localDate = delivered.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate expiryDate = localDate.plusDays(daysUntilExpiry);
		return Date.from(expiryDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}

