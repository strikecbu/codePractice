package com.learnjava.service;

import com.learnjava.domain.Product;
import org.junit.jupiter.api.Test;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceUsingCompletableFutureTest {
    private final ProductInfoService productInfoService = new ProductInfoService();
    private final ReviewService reviewService = new ReviewService();
    private final InventoryService inventoryService = new InventoryService();
    private final ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(
            productInfoService,
            reviewService,
            inventoryService);

    @Test
    void retrieveProductDetails() {
        String productId = "ABC1234";
        Product product = productService.retrieveProductDetails(productId);
        assertNotNull(product);
        assertTrue(product.getProductInfo()
                .getProductOptions()
                .size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void retrieveProductDetails_approach2() {
        stopWatch.start();
        String productId = "ABC1234";
        Product product = productService.retrieveProductDetails_approach2(productId)
                .join();
        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        assertNotNull(product);
        assertTrue(product.getProductInfo()
                .getProductOptions()
                .size() > 0);
        assertNotNull(product.getReview());

    }

    @Test
    void retrieveProductDetailsWithInventory() {
        String productId = "ABC1234";
        Product product = productService.retrieveProductDetailsWithInventory(productId);
        assertNotNull(product);
        assertTrue(product.getProductInfo()
                .getProductOptions()
                .size() > 0);
        assertNotNull(product.getReview());
        product.getProductInfo().getProductOptions().forEach(option -> {
            assertNotNull(option.getInventory());
        });
    }
}
