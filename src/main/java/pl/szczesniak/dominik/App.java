package pl.szczesniak.dominik;

import pl.szczesniak.dominik.pointsale.PointSaleFacade;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.LcdDisplay;
import pl.szczesniak.dominik.pointsale.devices.outputdevices.Printer;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductPrice;

import java.util.List;
import java.util.Optional;

public class App {
    public static void main( String[] args ) {

        ProductScannerService productScannerService = new ProductScannerService(new InMemoryReceiptsRepository(), new LcdDisplay(), productBarcode -> Optional.empty());

        LcdDisplay lcdDisplay = new LcdDisplay();
        Printer printer = new Printer();

        PointSaleFacade facade = new PointSaleFacade(productScannerService, lcdDisplay, printer);

        facade.addToReceipt(new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5)));
        facade.addToReceipt(new Product(new ProductName("asd"), new ProductPrice(5), new ProductBarcode(123)));
        facade.addToReceipt(new Product(new ProductName("qwe"), new ProductPrice(16.89f), new ProductBarcode(1)));
        List<Product> all = facade.findAll();
        facade.printReceipt(all);
    }

}
