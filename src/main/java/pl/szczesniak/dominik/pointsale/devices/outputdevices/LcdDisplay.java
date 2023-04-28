package pl.szczesniak.dominik.pointsale.devices.outputdevices;

import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.products.domain.Product;

@RequiredArgsConstructor
public class LcdDisplay {

	public void printMessage(final String message) {
		System.out.println(message);
	}

	public void printProduct(final Product product) {
		System.out.println(product.getProductName().getValue() + " " + product.getProductPrice().getValue());
	}

	public void printPrice(final float price) {
		System.out.println("|---|");
		System.out.println("|LCD|");
		System.out.println("|---|");
		System.out.printf("Price to pay: " + "%.2f", price);
		System.out.println();
		System.out.println();
	}

}
