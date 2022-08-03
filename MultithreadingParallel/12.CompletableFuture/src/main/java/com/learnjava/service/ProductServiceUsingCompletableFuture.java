package com.learnjava.service;

import com.learnjava.domain.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService,
                                                InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public static void main(String[] args) {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        InventoryService inventoryService = new InventoryService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(
                productInfoService,
                reviewService, inventoryService);
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(
                productId));

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(
                productId));

        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review))
                .join();


        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }
    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();

        CompletableFuture<ProductInfo> productInfoCompletableFuture =
                CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                        .thenApply(productInfo -> {
                            List<ProductOption> optionList = updateProductInfo(productInfo);
                            productInfo.setProductOptions(optionList);
                            return productInfo;
                        });

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(
                productId));

        Product product = productInfoCompletableFuture.thenCombine(reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review))
                .join();


        stopWatch.stop();
        log("Total Time Taken : " + stopWatch.getTime());
        return product;
    }

    private List<ProductOption> updateProductInfo(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> completableFutures = productInfo.getProductOptions()
                .stream()
                .map(productOption -> {
                    return CompletableFuture.supplyAsync(() -> inventoryService.retrieveInventory(productOption))
                            .thenApply(inventory -> {
                                productOption.setInventory(inventory);
                                return productOption;
                            });
                })
                .collect(Collectors.toList());
        return completableFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public CompletableFuture<Product> retrieveProductDetails_approach2(String productId) {

        CompletableFuture<ProductInfo> productInfoCompletableFuture = CompletableFuture.supplyAsync(() -> productInfoService.retrieveProductInfo(
                productId));

        CompletableFuture<Review> reviewCompletableFuture = CompletableFuture.supplyAsync(() -> reviewService.retrieveReviews(
                productId));

        return productInfoCompletableFuture.thenCombine(reviewCompletableFuture,
                        (productInfo, review) -> new Product(productId, productInfo, review));


    }
}
