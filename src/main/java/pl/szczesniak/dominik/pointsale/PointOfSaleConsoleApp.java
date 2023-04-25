package pl.szczesniak.dominik.pointsale;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.Printer;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class PointOfSaleConsoleApp {

	private final Scanner scan = new Scanner(System.in);
	private final Printer printer = new Printer();
	private final LcdDisplay lcdDisplay = new LcdDisplay();
	private final ProductScannerService productScannerService = new ProductScannerService(
			new InMemoryReceiptsRepository(),
			lcdDisplay,
			productBarcode -> Optional.empty()
	);

	public PointOfSaleConsoleApp() {
		System.out.println("Please scan all the products, when you're done enter exit.");
	}

	public void run() {
		System.out.println("Enter Barcode");
		String barcode = scan.nextLine();
		if ("exit".equals(barcode)) {
			final List<Product> products = productScannerService.findAll();
			printer.printReceipt(products);
			lcdDisplay.printPriceToPay(products);
		}
		productScannerService.scan(new ProductBarcode(Integer.parseInt(barcode)));

		if (!"exit".equals(barcode)) {
			run();
		}
	}

}
