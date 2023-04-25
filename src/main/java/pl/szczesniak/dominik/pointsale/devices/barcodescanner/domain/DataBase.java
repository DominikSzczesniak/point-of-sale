package pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain;

import pl.szczesniak.dominik.pointsale.product.domain.Product;
import pl.szczesniak.dominik.pointsale.product.domain.model.ProductBarcode;

import java.util.Optional;

interface DataBase {

	Optional<Product> find(final ProductBarcode productBarcode);

	boolean exists(ProductBarcode productBarcode);

}
