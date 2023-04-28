package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.szczesniak.dominik.pointsale.devices.DrawProductsService;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure.persistence.InMemoryReceiptsRepository;
import pl.szczesniak.dominik.pointsale.products.domain.Product;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductPrice;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BarCodeScannerServiceIntTest {

	private BarCodeScannerService tut;
	private ProductsRepository repository;
	private DrawProductsService drawProductsService;
	private InMemoryReceiptsRepository receipts;

	@BeforeEach
	void setUp() {
		receipts = new InMemoryReceiptsRepository();
		drawProductsService = new DrawProductsService(receipts);
		repository = mock(ProductsRepository.class);
		tut = new BarCodeScannerService(receipts, repository);
	}

	@Test
	void should_add_scanned_product_to_receipt() {
		// given
		final Product product = new Product(new ProductName("Water"), new ProductPrice(1.89f), new ProductBarcode(5));
		when(repository.exists(product.getProductBarcode())).thenReturn(true);
		when(repository.find(product.getProductBarcode())).thenReturn(Optional.of(product));
		tut.scan(product.getProductBarcode()).get();

		// when
		final List<Product> products = drawProductsService.findAll();

		// then
		assertThat(products).hasSize(1).contains(product);
	}

	@Test
	void should_not_add_to_receipt_when_barcode_does_not_have_valid_product() {
		// given
		final ProductBarcode productBarcode1 = new ProductBarcode(532);
		final ProductBarcode productBarcode2 = new ProductBarcode(5423);
		when(repository.exists(productBarcode1)).thenReturn(true);
		when(repository.exists(productBarcode2)).thenReturn(true);
		when(repository.find(productBarcode1)).thenReturn(Optional.of(new Product(new ProductName("Fork"), null, productBarcode1)));
		when(repository.find(productBarcode2)).thenReturn(Optional.of(new Product(null, new ProductPrice(421.21f), productBarcode2)));

		// when
		tut.scan(productBarcode1);
		tut.scan(productBarcode2);

		// then
		assertThat(drawProductsService.findAll()).isEmpty();
	}

	@Test
	void should_not_add_to_receipt_when_barcode_not_found() {
		// given
		final ProductBarcode createdProductBarcode = new ProductBarcode(123);
		when(repository.exists(createdProductBarcode)).thenReturn(false);

		// when
		tut.scan(createdProductBarcode);
		final List<Product> products = drawProductsService.findAll();

		// then
		assertThat(products).isEmpty();
	}

}