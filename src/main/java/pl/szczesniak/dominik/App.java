package pl.szczesniak.dominik;

import pl.szczesniak.dominik.pointsale.PointSaleFacade;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.lcddisplay.LcdService;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.printer.PrinterService;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductPrice;

import java.util.List;
import java.util.Optional;

public class App {
    public static void main( String[] args ) {

        ProductScannerService productScannerService = new ProductScannerService(new InMemoryReceiptsRepository(), new LcdService(), productBarcode -> Optional.empty());

        LcdService lcdService = new LcdService();
        PrinterService printerService = new PrinterService();

        PointSaleFacade facade = new PointSaleFacade(productScannerService, lcdService, printerService);

        facade.addToReceipt(new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5)));
        facade.addToReceipt(new Product(new ProductName("asd"), new ProductPrice(5), new ProductBarcode(123)));
        facade.addToReceipt(new Product(new ProductName("qwe"), new ProductPrice(16.89f), new ProductBarcode(1)));
        List<Product> all = facade.findAll();
        facade.printReceipt(all);
    }

}
