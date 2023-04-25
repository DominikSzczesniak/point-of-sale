package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.persistence.InMemoryReceiptsRepository;

class ProductScannerServiceTestConfiguration {

	static BarCodeScannerService productScannerService(final DataBase dataBase) {
		return new BarCodeScannerService(new InMemoryReceiptsRepository(), dataBase);
	}

}
