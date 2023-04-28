package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.pointsale.products.domain.Product;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductPrice;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerServiceTestConfiguration.productScannerService;

class BarCodeScannerServiceTest {

	private BarCodeScannerService tut;
	private ProductsRepository repository;

	@BeforeEach
	void setUp() {
		repository = mock(ProductsRepository.class);
		tut = productScannerService(repository);
	}

	@Test
	void scanned_product_should_equal_previously_given_product() {
		// given
		final Product product = randomProduct();
		when(repository.exists(product.getProductBarcode())).thenReturn(true);
		when(repository.find(product.getProductBarcode())).thenReturn(Optional.of(product));

		// when
		final Product scannedProduct = tut.scan(product.getProductBarcode()).get();

		// then
		assertThat(scannedProduct).isEqualTo(product);
	}

	@Test
	void should_return_empty_when_barcode_not_found() {
		// given
		final ProductBarcode createdProductBarcode = randomBarcode();
		when(repository.exists(createdProductBarcode)).thenReturn(false);

		// when
		final Optional<Product> scannedProduct = tut.scan(createdProductBarcode);

		// then
		assertThat(scannedProduct).isEmpty();
	}

	private static Product randomProduct() {
		return new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
	}

	private static ProductBarcode randomBarcode() {
		return new ProductBarcode(14213);
	}

}