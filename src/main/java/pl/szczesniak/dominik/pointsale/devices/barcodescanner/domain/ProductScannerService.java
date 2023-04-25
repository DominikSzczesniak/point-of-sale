package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.model.exceptions.InvalidBarcodeException;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.model.exceptions.ProductNotFoundException;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;

import java.util.List;

@RequiredArgsConstructor
public class ProductScannerService {

	private final ReceiptsRepository receipts;
	private final LcdDisplay lcdDisplay;
	private final DataBase repository;

	public Product scan(final ProductBarcode productBarcode) { // TODO: 3 razy wyciagam z repo;; resolveScannedProductStatus, booleany z tych checkow i jesli true to x, variable scannedproductstatus
		checkBarcodeExists(productBarcode);
		checkBarcodeHasProduct(productBarcode);

		final Product product = repository.find(productBarcode).get();
		receipts.addToReceipt(product);
		lcdDisplay.printProduct(product);
		return product;
	}

	private void checkBarcodeExists(final ProductBarcode productBarcode) {
		if (repository.find(productBarcode).isEmpty()) {
			lcdDisplay.printErrorMessage(new InvalidBarcodeException("Invalid bar-code"));
//			throw new InvalidBarcodeException("Invalid bar-code");
		}
	}

	private void checkBarcodeHasProduct(final ProductBarcode productBarcode) {
		if (repository.find(productBarcode)
				.stream()
				.anyMatch(product -> product.getProductName() == null || product.getProductPrice() == null)) {
			lcdDisplay.printErrorMessage(new ProductNotFoundException("Product not found"));
		}
	}

//	public Product scan(final ProductBarcode productBarcode) { // TODO: 3 razy wyciagam z repo;; resolveScannedProductStatus, booleany z tych checkow i jesli true to x, variable scannedproductstatus
//		final Optional<Product> product = repository.find(productBarcode);
//		checkBarcodeExists(product);
//		checkBarcodeHasProduct(product.get());
//
//		final Product product = repository.find(productBarcode).get();
//		receipts.addToReceipt(product.get());
//		lcdService.printProduct(product.get());
//		return product.get();
//	}
//
//	private void checkBarcodeExists(final Optional<Product> product) {
//		if (product.isEmpty()) {
//			lcdService.printErrorMessage(new InvalidBarcodeException("Invalid bar-code"));
//		}
//	}
//
//	private void checkBarcodeHasProduct(final Product product) {
//		if (product.getProductName() == null || product.getProductPrice() == null) {
//			lcdService.printErrorMessage(new ProductNotFoundException("Product not found"));
//		}
//	}

	public void addToReceipt(final Product product) {
		receipts.addToReceipt(product);
	}

	public List<Product> findAll() {
		return receipts.findAll();
	}

}
