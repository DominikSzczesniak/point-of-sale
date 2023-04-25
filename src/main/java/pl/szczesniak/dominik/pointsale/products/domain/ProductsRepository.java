package pl.szczesniak.dominik.pointsale.products.domain;

import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;

import java.util.Optional;

public interface ProductsRepository {

	Optional<Product> find(final ProductBarcode productBarcode);

	boolean exists(ProductBarcode productBarcode);

}
