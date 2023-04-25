package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.model.exceptions.InvalidBarcodeException;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.model.exceptions.ProductNotFoundException;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;

import java.util.List;

@RequiredArgsConstructor
public class BarCodeScannerService {

	private final ReceiptsRepository receipts;
	private final LcdDisplay lcdDisplay;
	private final DataBase repository;

	public Product scan(final ProductBarcode productBarcode) {
		final Product product = checkBarcodeExists(productBarcode);
		checkProductIsValid(product);

		receipts.addToReceipt(product);
		lcdDisplay.printProduct(product);
		return product;
	}

	private Product checkBarcodeExists(final ProductBarcode productBarcode) {
		if (repository.find(productBarcode).isEmpty()) {
			lcdDisplay.printErrorMessage(new InvalidBarcodeException("Invalid bar-code"));
			lcdDisplay.printMessage("Invalid bar-code");
		}
		return repository.find(productBarcode).get();
	}

	private void checkProductIsValid(final Product product) {
		if (product.getProductPrice() == null || product.getProductName() == null) {
			lcdDisplay.printErrorMessage(new ProductNotFoundException("Product not found"));
		}
	}

	public List<Product> findAll() {
		return receipts.findAll();
	}

}
