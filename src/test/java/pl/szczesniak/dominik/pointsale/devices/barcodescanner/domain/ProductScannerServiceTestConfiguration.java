package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.persistence.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;

public class ProductScannerServiceTestConfiguration {

	static BarCodeScannerService productScannerService(final DataBase dataBase) {
		return new BarCodeScannerService(new InMemoryReceiptsRepository(), new LcdDisplay(), dataBase);
	}

}
