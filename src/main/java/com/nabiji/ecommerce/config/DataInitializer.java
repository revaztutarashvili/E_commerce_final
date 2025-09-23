package com.nabiji.ecommerce.config;

import com.nabiji.ecommerce.entity.Branch;
import com.nabiji.ecommerce.entity.Inventory;
import com.nabiji.ecommerce.entity.Product;
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.repository.InventoryRepository;
import com.nabiji.ecommerce.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final BranchRepository branchRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;

    public DataInitializer(BranchRepository branchRepository, ProductRepository productRepository, InventoryRepository inventoryRepository) {
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (branchRepository.count() == 0) {
            logger.info("Database is empty. Initializing data...");
            createBranches();
            createProducts();
            createInventory();
            logger.info("Data initialization complete.");
        } else {
            logger.info("Database already contains data. Skipping initialization.");
        }
    }

    private void createBranches() {
        logger.info("Creating branches...");
        List.of(
                createBranch("Saburtalo", "Saburtalo Address"),
                createBranch("Varketili", "Varketili Address"),
                createBranch("Gldani", "Gldani Address"),
                createBranch("Isani", "Isani Address")
        ).forEach(branchRepository::save);
        logger.info("Branches created successfully.");
    }

    private void createProducts() {
        logger.info("Creating products...");
        List.of(
                createProduct("Apple", "Fresh red apples"),
                createProduct("Peach", "Juicy peaches"),
                createProduct("Orange", "Sweet oranges"),
                createProduct("Wild Berry", "A mix of wild berries"),
                createProduct("Pineapple", "Tropical pineapple"),
                createProduct("Tomato", "Ripe red tomatoes")
        ).forEach(productRepository::save);
        logger.info("Products created successfully.");
    }

    private void createInventory() {
        logger.info("Creating initial inventory...");
        List<Branch> branches = branchRepository.findAll();
        List<Product> products = productRepository.findAll();
        BigDecimal basePrice = new BigDecimal("1.00");
        int initialQuantity = 10;

        Map<String, BigDecimal> markups = Map.of(
                "Saburtalo", new BigDecimal("1.00"),
                "Varketili", new BigDecimal("2.00"),
                "Gldani", new BigDecimal("3.00"),
                "Isani", new BigDecimal("4.00")
        );

        for (Branch branch : branches) {
            for (Product product : products) {
                Inventory inventory = new Inventory();
                inventory.setBranch(branch);
                inventory.setProduct(product);
                inventory.setQuantity(initialQuantity);

                BigDecimal price = basePrice.add(markups.getOrDefault(branch.getName(), BigDecimal.ZERO));
                inventory.setPrice(price);

                inventoryRepository.save(inventory);
            }
        }
        logger.info("Initial inventory of {} items created for {} branches.", products.size(), branches.size());
    }

    private Branch createBranch(String name, String address) {
        Branch branch = new Branch();
        branch.setName(name);
        branch.setAddress(address);
        return branch;
    }

    private Product createProduct(String name, String description) {
        Product product = new Product();
        product.setName(name);
        product.setDescription(description);
        return product;
    }
}