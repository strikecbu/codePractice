package com.learnjava.service;

import com.learnjava.domain.Inventory;
import com.learnjava.domain.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceErrorHandlTest {

    @Mock
    private ProductInfoService productInfoService;

    @Mock
    private ReviewService reviewService;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private ProductServiceUsingCompletableFuture productService;


    @Test
    public void test_handle_review_error() {
        when(reviewService.retrieveReviews(any())).thenThrow(new RuntimeException("No one needs inventory"));
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();

        String productId = "AAA123";
        Product product = productService.retrieveProductDetailsWithInventory(productId);

        assertEquals(0, product.getReview().getNoOfReviews());
    }

    @Test
    public void test_handle_productInfo_error() {
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();
        when(productInfoService.retrieveProductInfo(any())).thenThrow(new RuntimeException("Product info is lost"));

        String productId = "AAA123";
        assertThrows(RuntimeException.class, () -> productService.retrieveProductDetailsWithInventory(productId));
    }

    @Test
    public void test_handle_inventory_error() {
        when(reviewService.retrieveReviews(any())).thenCallRealMethod();
        when(productInfoService.retrieveProductInfo(any())).thenCallRealMethod();
        when(inventoryService.retrieveInventory(any())).thenThrow(new RuntimeException("inventory is down"));
        String productId = "AAA123";
        Product product = productService.retrieveProductDetailsWithInventory(productId);

        product.getProductInfo().getProductOptions().forEach(productOption -> {
            Inventory inventory = productOption.getInventory();
            assertNotNull(inventory);
            assertEquals(1,inventory.getCount());
        });
    }
}
