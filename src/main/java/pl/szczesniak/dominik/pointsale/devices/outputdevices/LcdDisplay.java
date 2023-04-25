package pl.szczesniak.dominik.pointsale.devices.outputdevices;

import pl.szczesniak.dominik.pointsale.product.domain.Product;

public class LcdDisplay {

	public void printErrorMessage(final RuntimeException runtimeException) {
		throw runtimeException;
	}

	public void printProduct(final Product product) {
		System.out.println(product.getProductName().getValue() + " " + product.getProductPrice().getValue());
	}

}