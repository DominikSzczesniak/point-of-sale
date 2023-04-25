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
	private DataBase repository;

	@BeforeEach
	void setUp() {
		repository = mock(DataBase.class);
		tut = productScannerService(repository);
	}

	@Test
	void should_find_product() {
		// given
		final Product product = randomProduct();
		when(repository.exists(product.getProductBarcode())).thenReturn(true);
		when(repository.find(product.getProductBarcode())).thenReturn(Optional.of(product));

		// when
		final Product foundProduct = tut.scan(product.getProductBarcode()).get();

		// then
		assertThat(foundProduct).isEqualTo(product);
	}

	@Test
	void should_have_no_products_when_previously_nothing_scanned() {
		// when
		final List<Product> products = tut.findAll();

		// then
		assertThat(products).isEmpty();
	}

	@Test
	void should_add_scanned_product_to_receipt() {
		// given
		final Product product = randomProduct();
		when(repository.exists(product.getProductBarcode())).thenReturn(true);
		when(repository.find(product.getProductBarcode())).thenReturn(Optional.of(new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5))));
		tut.scan(product.getProductBarcode()).get();

		// when
		final List<Product> products = tut.findAll();

		// then
		assertThat(products).hasSize(1);
	}

	@Test
	void should_return_empty_when_barcode_not_found() {
		// given
		final ProductBarcode createdProductBarcode = new ProductBarcode(14213);
		when(repository.exists(createdProductBarcode)).thenReturn(false);

		// when
		final Optional<Product> scannedProduct = tut.scan(createdProductBarcode);

		// then
		assertThat(scannedProduct).isEmpty();
	}

	@Test
	void should_not_add_to_receipt_when_barcode_not_found() {
		// given
		final ProductBarcode createdProductBarcode = new ProductBarcode(123);
		when(repository.exists(createdProductBarcode)).thenReturn(false);
		tut.scan(createdProductBarcode);

		// when
		final List<Product> products = tut.findAll();

		// then
		assertThat(products).isEmpty();
	}

	@Test
	void should_not_add_to_receipt_when_barcode_does_not_have_valid_product() {
		// given
		final ProductBarcode productBarcode = new ProductBarcode(5);
		when(repository.exists(productBarcode)).thenReturn(true);
		when(repository.find(productBarcode)).thenReturn(Optional.of(new Product(null, null, new ProductBarcode(5))));

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
		when(repository.exists(product1.getProductBarcode())).thenReturn(true);
		when(repository.exists(product2.getProductBarcode())).thenReturn(true);
		when(repository.exists(product3.getProductBarcode())).thenReturn(true);
		when(repository.find(product1.getProductBarcode())).thenReturn(Optional.of(product1));
		when(repository.find(product2.getProductBarcode())).thenReturn(Optional.of(product2));
		when(repository.find(product3.getProductBarcode())).thenReturn(Optional.of(product3));

		tut.scan(product1.getProductBarcode());
		tut.scan(product2.getProductBarcode());
		tut.scan(product3.getProductBarcode());

		// when
		final List<Product> products = tut.findAll();

		// then
		assertThat(products).hasSize(3).extracting(Product::getProductBarcode)
				.containsExactlyInAnyOrder(product1.getProductBarcode(), product2.getProductBarcode(), product3.getProductBarcode());
	}

	private static Product randomProduct() {
		return new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
	}

}