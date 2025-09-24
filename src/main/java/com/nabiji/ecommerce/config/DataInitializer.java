package com.nabiji.ecommerce.config;

import com.nabiji.ecommerce.entity.Branch;
import com.nabiji.ecommerce.entity.Inventory;
import com.nabiji.ecommerce.entity.Product;
import com.nabiji.ecommerce.repository.BranchRepository;
import com.nabiji.ecommerce.repository.InventoryRepository;
import com.nabiji.ecommerce.repository.ProductRepository;
import com.nabiji.ecommerce.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.nabiji.ecommerce.entity.User;
import com.nabiji.ecommerce.enums.Role;
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
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(BranchRepository branchRepository,
                           ProductRepository productRepository,
                           InventoryRepository inventoryRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.branchRepository = branchRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (branchRepository.count() == 0) {
            logger.info("Database is empty. Initializing data...");
            createAdminUserIfNotFound();
            createBranches();
            createProducts();
            createInventory();
            logger.info("Data initialization complete.");
        } else {
            logger.info("Database already contains data. Skipping initialization.");
        }
    }

    private void createAdminUserIfNotFound() {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin")); // პაროლი: admin123
            admin.setRole(Role.ADMIN);
            admin.setActive(true);
            userRepository.save(admin);
            logger.info("Default administrator account created: admin / admin");
        }
    }

    private void createBranches() {
        logger.info("Creating branches...");
        List.of(
                createBranch("Saburtalo", "Tbilisi, S.tsintsadze st #26"),
                createBranch("Varketili", "Tbilisi, Javakheti st. #193"),
                createBranch("Gldani", "Tbilisi, shaurmebi st. #31"),
                createBranch("Isani", "Tbilisi, ketevan tsamebuli Avenue #201")
        ).forEach(branchRepository::save);
        logger.info("Branches created successfully.");
    }

    private void createProducts() {
        logger.info("Creating products...");
        List.of(
                createProduct("Apple", "Fresh red apples (200 Kilocalories)"),
                createProduct("Peach", "Juicy peaches (212 Kilocalories)"),
                createProduct("Orange", "Sweet oranges (90 Kilocalories)"),
                createProduct("Wild Berry", "A mix of wild berries (139 Kilocalories)"),
                createProduct("Pineapple", "Tropical pineapple (421 Kilocalories)"),
                createProduct("Tomato", "Ripe red tomatoes (89 Kilocalories)")
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