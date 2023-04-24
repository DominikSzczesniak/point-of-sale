package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.model;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.DataBase;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.lcddisplay.LcdService;

public class ProductScannerServiceTestConfiguration {

	static ProductScannerService productScannerService(final DataBase dataBase) {
		return new ProductScannerService(new InMemoryReceiptsRepository(), new LcdService(), dataBase);
	}

}
