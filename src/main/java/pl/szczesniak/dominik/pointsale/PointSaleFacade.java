package pl.szczesniak.dominik.pointsale;

import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerService;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.Printer;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;

import java.util.List;

@RequiredArgsConstructor
public class PointSaleFacade {

	final private ProductScannerService productScannerService;
	final private LcdDisplay lcdDisplay;
	final private Printer printer;

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
		printer.printReceipt(products);
	}

}
