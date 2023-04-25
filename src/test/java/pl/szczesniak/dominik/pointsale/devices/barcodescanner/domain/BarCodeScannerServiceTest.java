package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductPrice;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ProductScannerServiceTestConfiguration.productScannerService;

class BarCodeScannerServiceTest {

	private BarCodeScannerService tut;
	private DataBase dataBase;

	@BeforeEach
	void setUp() {
		dataBase = mock(DataBase.class);
		tut = productScannerService(dataBase);
	}

	@Test
	void should_find_product() {
		// given
		final Product product = new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
		when(dataBase.exists(product.getProductBarcode())).thenReturn(true);
		when(dataBase.find(product.getProductBarcode())).thenReturn(Optional.of(new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5))));

		// when
		final Product foundProduct = tut.scan(product.getProductBarcode()).get();

		// then
		assertThat(foundProduct).isEqualTo(product);
	}

	@Test
	void should_return_empty_when_barcode_not_found() {
		// given
		final Product product = new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
		when(dataBase.exists(product.getProductBarcode())).thenReturn(false);

		// when
		final Optional<Product> scannedProduct = tut.scan(product.getProductBarcode());

		// then
		assertThat(scannedProduct).isEmpty();

	}

	@Test
	void should_not_add_to_receipt_when_barcode_does_not_have_valid_product() {
		// given
		final ProductBarcode productBarcode = new ProductBarcode(5);
		when(dataBase.exists(productBarcode)).thenReturn(true);
		when(dataBase.find(productBarcode)).thenReturn(Optional.of(new Product(null, null, new ProductBarcode(5))));

		// when
		tut.scan(productBarcode);

		// then
		assertThat(tut.findAll()).isEmpty();
	}

	@Test
	void should_find_all_previously_scanned_products() {
		// given
		final Product product1 = new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
		final Product product2 = new Product(new ProductName("Apple"), new ProductPrice(0.89f), new ProductBarcode(12));
		final Product product3 = new Product(new ProductName("Glasses"), new ProductPrice(15.89f), new ProductBarcode(9385));
		when(dataBase.exists(product1.getProductBarcode())).thenReturn(true);
		when(dataBase.exists(product2.getProductBarcode())).thenReturn(true);
		when(dataBase.exists(product3.getProductBarcode())).thenReturn(true);
		when(dataBase.find(product1.getProductBarcode())).thenReturn(Optional.of(product1));
		when(dataBase.find(product2.getProductBarcode())).thenReturn(Optional.of(product2));
		when(dataBase.find(product3.getProductBarcode())).thenReturn(Optional.of(product3));

		tut.scan(product1.getProductBarcode()).get();
		tut.scan(product2.getProductBarcode()).get();
		tut.scan(product3.getProductBarcode()).get();

		// when
		final List<Product> products = tut.findAll();

		// then
		assertThat(products).hasSize(3).extracting(Product::getProductBarcode)
				.containsExactlyInAnyOrder(product1.getProductBarcode(), product2.getProductBarcode(), product3.getProductBarcode());
	}

	@Test
	void should_have_no_products_when_previously_nothing_scanned() {
		// when
		final List<Product> products = tut.findAll();

		// then
		assertThat(products).isEmpty();
	}

}