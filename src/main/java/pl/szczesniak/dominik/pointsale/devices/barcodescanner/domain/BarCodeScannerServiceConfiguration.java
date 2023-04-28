package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;
import pl.szczesniak.dominik.pointsale.products.domain.Product;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;

import java.util.Optional;

public class BarCodeScannerServiceConfiguration {

	public BarCodeScannerService barCodeScannerService(final ReceiptsRepository repository) {
		return new BarCodeScannerService(repository, new ProductsRepository() {
			@Override
			public Optional<Product> find(final ProductBarcode productBarcode) {
				return Optional.empty();
			}

			@Override
			public boolean exists(final ProductBarcode productBarcode) {
				return false;
			}
		}, new LcdDisplay());
	}

}
