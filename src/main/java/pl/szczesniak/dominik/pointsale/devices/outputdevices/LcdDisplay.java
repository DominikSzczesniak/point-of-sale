package pl.szczesniak.dominik.pointsale.devices.outputdevices;

import pl.szczesniak.dominik.pointsale.product.domain.Product;

import java.util.List;

public class LcdDisplay {

	public void printErrorMessage(final RuntimeException runtimeException) {
		throw runtimeException;
	}

	public void printMessage(final String message) {
		System.out.println(message);
	}

	public void printProduct(final Product product) {
		System.out.println(product.getProductName().getValue() + " " + product.getProductPrice().getValue());
	}

	public void printPriceToPay(final List<Product> products) {
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
