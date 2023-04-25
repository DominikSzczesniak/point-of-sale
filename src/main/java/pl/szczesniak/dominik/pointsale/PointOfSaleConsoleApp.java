package pl.szczesniak.dominik.pointsale;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.BarCodeScannerService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.BarCodeScannerServiceConfiguration;
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
	private final BarCodeScannerService barCodeScannerService = new BarCodeScannerServiceConfiguration().barCodeScannerService();

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
		final List<Product> products = barCodeScannerService.findAll();
		printer.printReceipt(products);
		lcdDisplay.printPriceToPay(products);
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
