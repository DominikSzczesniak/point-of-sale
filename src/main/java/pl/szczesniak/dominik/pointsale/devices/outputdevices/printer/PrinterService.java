package pl.szczesniak.dominik.pointsale.devices.outputdevices.printer;

import pl.szczesniak.dominik.pointsale.product.domain.Product;

import java.util.List;

public class PrinterService {

	public void printReceipt(final List<Product> products) {
		printListOfProducts(products);
		printPriceToPay(products);
	}

	private static void printListOfProducts(final List<Product> products) {
		products
				.forEach(product -> {
					System.out.print(product.getProductName().getValue() + ": ");
					System.out.println(product.getProductPrice().getValue());
				});
	}

	private static void printPriceToPay(final List<Product> products) {
		float price = 0;
		for (Product product : products) {
			price += product.getProductPrice().getValue();
		}
		System.out.printf("Price to pay: " + "%.2f", price);
	}

}
