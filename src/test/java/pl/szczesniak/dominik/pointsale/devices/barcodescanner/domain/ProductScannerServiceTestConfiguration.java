package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.persistence.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.products.domain.ProductsRepository;

class ProductScannerServiceTestConfiguration {

	static BarCodeScannerService productScannerService(final ProductsRepository productsRepository) {
		return new BarCodeScannerService(new InMemoryReceiptsRepository(), productsRepository);
	}

}
