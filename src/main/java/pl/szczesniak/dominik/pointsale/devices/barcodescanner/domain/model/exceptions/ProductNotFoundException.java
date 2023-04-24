package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.model.exceptions;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(final String message) {
		super(message);
	}
}
