package pl.szczesniak.dominik.pointsale.devices.outputdevices;

import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ReceiptsRepository;
import pl.szczesniak.dominik.pointsale.products.domain.Product;

import java.util.List;

@RequiredArgsConstructor
public class Printer {

	private final ReceiptsRepository receipts;

	public List<Product> findAll() {
		return receipts.findAll();
	}

	public float printReceipt() {
		printListOfProducts(receipts.findAll());
		return printPriceToPay(receipts.findAll());
	}

	private void printListOfProducts(final List<Product> products) {
		products.forEach(product -> {
			System.out.print(product.getProductName().getValue() + ": ");
			System.out.println(product.getProductPrice().getValue());
		});
	}

	private float printPriceToPay(final List<Product> products) {
		float price = 0;
		for (Product product : products) {
			price += product.getProductPrice().getValue();
		}
		System.out.println("|-------|");
		System.out.println("|Printer|");
		System.out.println("|-------|");
		System.out.printf("Price to pay: " + "%.2f", price);
		System.out.println();
		System.out.println();
		return price;
	}

}
