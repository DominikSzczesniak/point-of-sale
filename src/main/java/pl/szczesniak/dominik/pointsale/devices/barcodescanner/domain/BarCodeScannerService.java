package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class BarCodeScannerService {

	private final ReceiptsRepository receipts;
	private final LcdDisplay lcdDisplay;
	private final DataBase repository;

	public Optional<Product> scan(final ProductBarcode productBarcode) {
		if (!barcodeExists(productBarcode)) {
			lcdDisplay.printErrorMessage("Invalid bar-code");
			return Optional.empty();
		}
		final Optional<Product> product = repository.find(productBarcode);
		addToReceipt(product.get());
		return product;
	}

	public boolean barcodeExists(final ProductBarcode productBarcode) {
		return repository.exists(productBarcode);
	}

	private void addToReceipt(final Product product) {
		if (product.getProductName() == null || product.getProductPrice() == null) {
			lcdDisplay.printErrorMessage("Product not found");
			return;
		}
		receipts.addToReceipt(product);
		lcdDisplay.printProduct(product);
	}

	public List<Product> findAll() {
		return receipts.findAll();
	}

}
