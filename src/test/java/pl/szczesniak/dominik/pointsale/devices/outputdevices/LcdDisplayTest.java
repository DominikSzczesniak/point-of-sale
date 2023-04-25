package pl.szczesniak.dominik.pointsale.devices.outputdevices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.BarCodeScannerService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.DataBase;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.persistence.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductPrice;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LcdDisplayTest {

	private LcdDisplay tut;
	private BarCodeScannerService scanner;
	private DataBase repository;

	@BeforeEach
	void setUp() {
		tut = mock(LcdDisplay.class);
		repository = mock(DataBase.class);
		scanner = new BarCodeScannerService(new InMemoryReceiptsRepository(), tut, repository);
	}

	@Test
	void should_perform_print_product_method_when_found_product() {
		// given
		final Product product = new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
		when(repository.exists(product.getProductBarcode())).thenReturn(true);
		when(repository.find(product.getProductBarcode())).thenReturn(Optional.of(product));

		// when
		scanner.scan(product.getProductBarcode());

		// then
		verify(tut).printProduct(product);
	}

	@Test
	void should_perform_print_error_message_method_when_barcode_not_found() {
		// given
		final Product product = new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
		when(repository.exists(product.getProductBarcode())).thenReturn(false);

		// when
		scanner.scan(product.getProductBarcode());

		// then
		verify(tut).printErrorMessage("Invalid bar-code");
	}

	@Test
	void should_perform_print_error_message_method_when_product_has_no_productname() {
		// given
		final Product product = new Product(null, new ProductPrice(1.89f), new ProductBarcode(5));
		when(repository.exists(product.getProductBarcode())).thenReturn(true);
		when(repository.find(product.getProductBarcode())).thenReturn(Optional.of(product));

		// when
		scanner.scan(product.getProductBarcode());

		// then
		verify(tut).printErrorMessage("Product not found");
	}

}