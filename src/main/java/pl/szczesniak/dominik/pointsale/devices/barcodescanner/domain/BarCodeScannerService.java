package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;
import pl.szczesniak.dominik.pointsale.products.domain.Product;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BarCodeScannerService {

	private final ReceiptsRepository receipts;
	private final ProductsRepository repository;
	private final LcdDisplay lcdDisplay;

	public Optional<Product> scan(final ProductBarcode productBarcode) {
		if (!barcodeExists(productBarcode)) {
			lcdDisplay.printErrorMessage("Invalid bar-code");
			return Optional.empty();
		}
		final Optional<Product> product = repository.find(productBarcode);
		addToReceipt(product.get());
		return product;
	}

	private boolean barcodeExists(final ProductBarcode productBarcode) {
		return repository.exists(productBarcode);
	}

	private void addToReceipt(final Product product) {
		if (product.getProductName() == null || product.getProductPrice() == null) {
			lcdDisplay.printErrorMessage("Product not found");
			return;
		}
		lcdDisplay.printProduct(product);
		receipts.addToReceipt(product);
	}

}
