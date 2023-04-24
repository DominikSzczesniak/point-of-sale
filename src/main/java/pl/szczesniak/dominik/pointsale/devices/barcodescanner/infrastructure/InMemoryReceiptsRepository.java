package pl.szczesniak.dominik.pointsale.devices.barcodescanner.infrastructure;

import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ReceiptsRepository;
import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;

import java.util.HashMap;
import java.util.List;

public class InMemoryReceiptsRepository implements ReceiptsRepository {

	private final HashMap<ProductBarcode, Product> products = new HashMap<>();

	@Override
	public void addToReceipt(final Product product) {
		products.put(product.getProductBarcode(), product);
	}

	@Override
	public List<Product> findAll() {
		return products.values().stream().toList();
	}

}
