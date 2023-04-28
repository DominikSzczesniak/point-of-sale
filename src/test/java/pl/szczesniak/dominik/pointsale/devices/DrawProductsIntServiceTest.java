package pl.szczesniak.dominik.pointsale.devices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.BarCodeScannerService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.persistence.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.products.domain.Product;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductPrice;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DrawProductsIntServiceTest {

	private DrawProductsService tut;
	private InMemoryReceiptsRepository receipts;
	private BarCodeScannerService scanner;

	@BeforeEach
	void setUp() {
		scanner = mock(BarCodeScannerService.class);
		receipts = new InMemoryReceiptsRepository();
		tut = new DrawProductsService(receipts);
	}

	@Test
	void should_have_no_products_when_previously_nothing_scanned() {
		// when
		final List<Product> products = tut.findAll();

		// then
		assertThat(products).isEmpty();
	}
	@Test
	void should_find_all_previously_scanned_products() {
		// given
		final ProductBarcode productBarcode1 = new ProductBarcode(5);
		final ProductBarcode productBarcode2 = new ProductBarcode(4);
		final ProductBarcode productBarcode3 = new ProductBarcode(1213);
		when(scanner.scan(productBarcode1)).thenReturn(Optional.of(new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5))));
		when(scanner.scan(productBarcode2)).thenReturn(Optional.of(new Product(new ProductName("Cola"), new ProductPrice(2.89f), new ProductBarcode(4))));
		when(scanner.scan(productBarcode3)).thenReturn(Optional.of(new Product(new ProductName("Fork"), new ProductPrice(0.89f), new ProductBarcode(1213))));
		receipts.addToReceipt(scanner.scan(productBarcode1).get());
		receipts.addToReceipt(scanner.scan(productBarcode2).get());
		receipts.addToReceipt(scanner.scan(productBarcode3).get());

		// when
		final List<Product> products = tut.findAll();

		// then
		assertThat(products).hasSize(3).extracting(Product::getProductBarcode)
				.containsExactlyInAnyOrder(productBarcode1, productBarcode2, productBarcode3);
	}

}