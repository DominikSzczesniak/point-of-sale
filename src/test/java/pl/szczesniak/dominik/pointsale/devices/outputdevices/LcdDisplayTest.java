package pl.szczesniak.dominik.pointsale.devices.outputdevices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.DataBase;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.model.exceptions.InvalidBarcodeException;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductPrice;

import java.util.Optional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LcdDisplayTest {

	private LcdDisplay tut;
	private ProductScannerService scanner;
	private DataBase dataBase;

	@BeforeEach
	void setUp() {
		tut = mock(LcdDisplay.class);
		dataBase = mock(DataBase.class);
		scanner = new ProductScannerService(new InMemoryReceiptsRepository(), tut, dataBase);
	}

	@Test
	void should_perform_print_product_method_when_found_product() {
		// given
		final Product product = new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
		when(dataBase.find(product.getProductBarcode())).thenReturn(Optional.of(product));

		// when
		scanner.scan(product.getProductBarcode());

		// then
		verify(tut).printProduct(product);
	}

	@Test
	void should_perform_print_error_message_method_when_barcode_not_found() {
		// given
		final Product product = new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
		final InvalidBarcodeException exceptionToBeThrown = new InvalidBarcodeException("Invalid bar-code");

		// when
//		final Throwable thrown = catchThrowable(() -> scanner.scan(product.getProductBarcode())); TODO: nie wiem zle ten test

		// then
		verify(tut, times(1)).printErrorMessage(exceptionToBeThrown);
	}

}