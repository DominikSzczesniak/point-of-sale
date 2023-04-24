package pl.szczesniak.dominik.pointsale.devices.outputdevices.lcddisplay;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerService;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductPrice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LcdServiceTest {

	private LcdService tut;
	private ProductScannerService scanner;

	@BeforeEach
	void setUp() {
		scanner = mock(ProductScannerService.class);
		tut = new LcdService();
	}

	@Test
	void should_print_product_name_and_price() {
		// given
		final Product product = new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
//		when(scanner.scan(new ProductBarcode(5))).thenCallRealMethod();
//		Product product1 = scanner.scan(new ProductBarcode(5));
		scanner.scan(product.getProductBarcode());
		verify(scanner, times(1)).printProduct(product); // INTEGRATION TESTY?
		// when

		// then
	}
}