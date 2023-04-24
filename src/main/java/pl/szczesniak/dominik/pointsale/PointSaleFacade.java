package pl.szczesniak.dominik.pointsale;

import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerService;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.lcddisplay.LcdService;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.printer.PrinterService;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;

import java.util.List;

@RequiredArgsConstructor
public class PointSaleFacade {

	final private ProductScannerService productScannerService;
	final private LcdService lcdService;
	final private PrinterService printerService;

	public void scan(final ProductBarcode productBarcode) {
		 productScannerService.scan(productBarcode);
	}

	public void addToReceipt(final Product product) {
		productScannerService.addToReceipt(product);
	}

	public List<Product> findAll() {
		return productScannerService.findAll();
	}

	public void printReceipt(final List<Product> products) {
		printerService.printReceipt(products);
	}

}
