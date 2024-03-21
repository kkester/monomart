package mart.mono.services;

import lombok.RequiredArgsConstructor;
import mart.mono.models.Catalog;
import mart.mono.repositories.CatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public List<Catalog> getAll() {
        return catalogRepository.findAll();
    }
}
