package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.products.domain.Product;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BarCodeScannerService {

	private final ReceiptsRepository receipts;
	private final ProductsRepository repository;

	public Optional<Product> scan(final ProductBarcode productBarcode) {
		if (!barcodeExists(productBarcode)) {
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
			return;
		}
		receipts.addToReceipt(product);
	}

//	public List<Product> findAll() {
//		return receipts.findAll();
//	}

}
