package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.persistence.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;

public class ProductScannerServiceTestConfiguration {

	public static BarCodeScannerService barCodeScannerService(final ProductsRepository productsRepository, final LcdDisplay lcdDisplay) {
		return new BarCodeScannerService(new InMemoryReceiptsRepository(), productsRepository, lcdDisplay);
	}

}
