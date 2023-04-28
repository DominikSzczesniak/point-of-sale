package pl.szczesniak.dominik.pointsale;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.BarCodeScannerService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.BarCodeScannerServiceConfiguration;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ReceiptsRepository;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.persistence.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.Printer;
import pl.szczesniak.dominik.pointsale.products.domain.Product;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;

import java.util.Optional;
import java.util.Scanner;

public class PointOfSaleConsoleApp {

	private final Scanner scan = new Scanner(System.in);
	private final ReceiptsRepository repository = new InMemoryReceiptsRepository();
	private final Printer printer = new Printer(repository);
	private final LcdDisplay lcdDisplay = new LcdDisplay();
	private final BarCodeScannerService barCodeScannerService = new BarCodeScannerServiceConfiguration().barCodeScannerService(repository);

	public PointOfSaleConsoleApp() {
		System.out.println("|----------------------------------------------------------|");
		System.out.println("|Please scan all the products, when you're done enter exit.|");
		System.out.println("|----------------------------------------------------------|");
	}

	public void run() {
		System.out.println("Enter Barcode");
		final String barcode = scan.nextLine();

		if (customerWantsToExit(barcode)) {
			exitAndPrintReceipt();
		}

		if (!"exit".equals(barcode)) {
			scanProduct(barcode);
			run();
		}
	}

	private static boolean customerWantsToExit(final String barcode) {
		return !Character.isDigit(barcode.charAt(0)) && "exit".equals(barcode);
	}

	private void exitAndPrintReceipt() {
		float price = printer.printReceipt();
		lcdDisplay.printPrice(price);
	}

	private void scanProduct(final String barcode) {
		final Optional<Product> scannedProduct = barCodeScannerService.scan(new ProductBarcode(Integer.parseInt(barcode)));
		if (scannedProduct.isEmpty()) {
			lcdDisplay.printMessage("Invalid bar-code");
		} else if (scannedProduct.get().getProductPrice() == null || scannedProduct.get().getProductName() == null) {
			lcdDisplay.printMessage("Product not found");
		} else {
			lcdDisplay.printProduct(scannedProduct.get());
		}
	}

}
