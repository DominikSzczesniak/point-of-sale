package pl.szczesniak.dominik.pointsale.product.domain.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class ProductBarcode {

	@NonNull
	int value;

}
