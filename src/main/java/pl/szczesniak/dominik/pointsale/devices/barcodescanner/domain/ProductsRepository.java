package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import pl.szczesniak.dominik.pointsale.products.domain.Product;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;

import java.util.Optional;

interface ProductsRepository {

	Optional<Product> find(final ProductBarcode productBarcode);

	boolean exists(ProductBarcode productBarcode);

}
