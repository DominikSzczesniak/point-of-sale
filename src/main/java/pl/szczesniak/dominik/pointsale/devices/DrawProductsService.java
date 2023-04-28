package pl.szczesniak.dominik.pointsale.devices;

import lombok.RequiredArgsConstructor;
import pl.szczesniak.dominik.pointsale.devices.barcodescanner.domain.ReceiptsRepository;
import pl.szczesniak.dominik.pointsale.products.domain.Product;

import java.util.List;

@RequiredArgsConstructor
public class DrawProductsService {

	private final ReceiptsRepository receipts;

	public List<Product> findAll() {
		return receipts.findAll();
	}

}
