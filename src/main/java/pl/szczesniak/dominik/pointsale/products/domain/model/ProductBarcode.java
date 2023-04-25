package pl.szczesniak.dominik.pointsale.products.domain.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class ProductBarcode {

	@NonNull
	int value;

}
