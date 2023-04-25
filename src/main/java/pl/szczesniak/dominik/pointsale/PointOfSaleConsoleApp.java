package pl.szczesniak.dominik.pointsale;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.BarCodeScannerService;
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
	private final BarCodeScannerService barCodeScannerService = new BarCodeScannerService(
			new InMemoryReceiptsRepository(),
			lcdDisplay,
			productBarcode -> Optional.empty()
	);

	public PointOfSaleConsoleApp() {
		System.out.println("|----------------------------------------------------------|");
		System.out.println("|Please scan all the products, when you're done enter exit.|");
		System.out.println("|----------------------------------------------------------|");
	}

	public void run() {
		System.out.println("Enter Barcode");
		final String barcode = scan.nextLine();
		final boolean isBarcode = Character.isDigit(barcode.charAt(0));

		exitAndPrintReceipt(barcode, isBarcode);

		if (!"exit".equals(barcode)) {
			barCodeScannerService.scan(new ProductBarcode(Integer.parseInt(barcode)));
			run();
		}
	}

	private void exitAndPrintReceipt(final String barcode, final boolean isBarcode) {
		if (!isBarcode && "exit".equals(barcode)) {
			final List<Product> products = barCodeScannerService.findAll();
			printer.printReceipt(products);
			lcdDisplay.printPriceToPay(products);
		}
	}

}
