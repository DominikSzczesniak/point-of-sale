package pl.szczesniak.dominik.pointsale.devices.outputdevices;

import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ReceiptsRepository;
import pl.szczesniak.dominik.pointsale.products.domain.Product;

import java.util.List;

@RequiredArgsConstructor
public class LcdDisplay {

	private final ReceiptsRepository receipts;

	public List<Product> findAll() {
		return receipts.findAll();
	}

	public void printMessage(final String message) {
		System.out.println(message);
	}

	public void printProduct(final Product product) {
		System.out.println(product.getProductName().getValue() + " " + product.getProductPrice().getValue());
	}

	public void printPriceToPay() {
		List<Product> products = findAll();
		float price = 0;
		for (Product product : products) {
			price += product.getProductPrice().getValue();
		}
		System.out.println("|---|");
		System.out.println("|LCD|");
		System.out.println("|---|");
		System.out.printf("Price to pay: " + "%.2f", price);
		System.out.println();
		System.out.println();
	}

}
