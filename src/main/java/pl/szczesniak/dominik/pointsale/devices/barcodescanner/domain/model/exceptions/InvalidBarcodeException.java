package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.model.exceptions;

public class InvalidBarcodeException extends RuntimeException {

	public InvalidBarcodeException(final String message) {
		super(message);
	}
}
