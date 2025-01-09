package hu.mywebshop.service;

import hu.mywebshop.entity.Product;
import hu.mywebshop.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(prod -> {
                    prod.setName(updatedProduct.getName());
                    prod.setDescription(updatedProduct.getDescription());
                    prod.setPrice(updatedProduct.getPrice());
                    prod.setWeight(updatedProduct.getWeight());
                    prod.setCategory(updatedProduct.getCategory());
                    return productRepository.save(prod);
                })
                .orElseThrow(() -> new RuntimeException("Termék nem található!"));
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> listProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Termék nem található!"));
    }
    
    // Egyszerű ár szerinti szűrés, paraméterekkel
    public Page<Product> filterByPrice(double min, double max, Pageable pageable) {
        // Itt lehetne custom query vagy Specifications
        // Pl. productRepository.findByPriceBetween(min, max, pageable)
        // Demóként most az összeset lekérjük:
        return productRepository.findAll(pageable); 
    }
}
