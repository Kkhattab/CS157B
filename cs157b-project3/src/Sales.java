import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Sales {
	@Id
	private String Date;
	private String ProductName;
	private Integer Quantity;
	private Double UnitCost;
	private Double TotalCost;
	
	public String getDate() {
		return Date;
	}
	
	public void setDate(String s) {
		Date = s;
	}
	
	public String getProductName() {
		return ProductName;
	}
	
	public void setProductName(String s) {
		ProductName = s;
	}
	
	public Integer getQuantity() {
		return Quantity;
	}
	
	public void setQuantity(Integer i) {
		Quantity = i;
	}
	
	public Double getUnitCost() {
		return UnitCost;
	}
	
	public void setUnitCost(Double d) {
		UnitCost = d;
	}
	
	public Double getTotalCost() {
		return TotalCost;
	}
	
	public void setTotalCost(Double d) {
		TotalCost = d;
	}
}
