package pl.szczesniak.dominik.pointsale.products.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductBarcode;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductName;
import pl.szczesniak.dominik.pointsale.products.domain.model.ProductPrice;

@ToString
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode(of = {"productBarcode"})
public class Product {

	private final ProductName productName;
	private final ProductPrice productPrice;
	private final ProductBarcode productBarcode;

}
