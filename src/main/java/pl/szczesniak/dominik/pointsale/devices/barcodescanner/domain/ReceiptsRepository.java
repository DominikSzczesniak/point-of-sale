package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import pl.szczesniak.dominik.pointsale.products.domain.Product;

import java.util.List;

public interface ReceiptsRepository {

	void addToReceipt(final Product product);

	List<Product> findAll();

}
