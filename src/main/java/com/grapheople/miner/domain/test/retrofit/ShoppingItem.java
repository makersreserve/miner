package com.grapheople.miner.domain.test.retrofit;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class ShoppingItem {

    @JsonProperty("channel_item_id")
    private Long id = 0L;

    @JsonProperty("name")
    private String name = "";

    @JsonProperty("model_name")
    private String modelName = "";

    @JsonProperty("standard_price")
    private String standardPrice = "";

    @JsonProperty("channel_fee_rate")
    private String channelFeeRate = "";

    @JsonProperty("channel_discount_rate")
    private String channelDiscountRate = "";

    @JsonProperty("channel_discount_amount")
    private String channelDiscountAmount = "";

    @JsonProperty("price")
    private String price = "";

    @JsonProperty("brand_id")
    private Long brandId = 0L;

    @JsonProperty("seller_id")
    private Long sellerId = 0L;

    @JsonProperty("released_at")
    private String releasedAt = "";

    @JsonProperty("expired_at")
    private String expiredAt = "";

    @JsonProperty("has_option")
    private boolean hasOption = false;

    @JsonProperty("is_limit_sale_count")
    private boolean limitSaleCount = false;

    @JsonProperty("limit_sale_count_per_user")
    private Long limitSaleCountPerUser = 0L;

    @JsonProperty("limit_sale_count_per_order")
    private Long limitSaleCountPerOrder = 0L;

    @JsonProperty("stock_count")
    private Long stockCount = 0L;

    @JsonProperty("sold_count")
    private Long soldCount = 0L;

    @JsonProperty("total_sold_count")
    private Long totalSoldCount = 0L;

    @JsonProperty("remain_count")
    private Long remainCount = 0L;

    @JsonProperty("standard_category_id")
    private String standardCategoryId = "";

    @JsonProperty("standard_category_code")
    private String standardCategoryCode = "";

    @JsonProperty("created_at")
    private String createdAt = "";

    @JsonProperty("created_by")
    private String createdBy = "";

    @JsonProperty("modified_at")
    private String modifiedAt = "";

    @JsonProperty("modified_by")
    private String modifiedBy = "";

    @JsonProperty("sale_status")
    private String saleStatus = "";

    @JsonProperty("expiry_days")
    private String expiryDays = "";

    @JsonProperty("enable_drop")
    private String enableDrop = "";

    @JsonProperty("is_display")
    private boolean display = false;

    // 기본 썸네일 이미지
    @JsonProperty("image_url")
    private String imageUrl = "";

    // 메인 노출용 이미지
    @JsonProperty("description_image_url")
    private String mainImageUrl = "";

    // 메인 노출용 이미지2
    @JsonProperty("additional5_image_url")
    private String mainImageUrl2 = "";

    // 상품상세 스와이프 이미지 1/4
    @JsonProperty("additional1_image_url")
    private String swipeImageUrl1 = "";

    // 상품상세 스와이프 이미지 2/4
    @JsonProperty("additional2_image_url")
    private String swipeImageUrl2 = "";

    // 상품상세 스와이프 이미지 3/4
    @JsonProperty("additional3_image_url")
    private String swipeImageUrl3 = "";

    // 상품상세 스와이프 이미지 4/4
    @JsonProperty("additional4_image_url")
    private String swipeImageUrl4 = "";

    @JsonProperty("sale_status_id")
    private Integer saleStatusId = 0;

    @JsonProperty("is_soldout")
    private boolean soldout = false;

    @JsonProperty("sale_status_label")
    private String saleStatusLabel = "";

    @JsonProperty("brand_name")
    private String brandName = "";

    @JsonProperty("fee_rate")
    private String feeRate = "";

    @JsonProperty("exchange_brand_name")
    private String exchangeBrandName = "";

    @JsonProperty("category_name")
    private String categoryName = "";

    @JsonProperty("total_discount_rate")
    private String totalDiscountRate = "";

    @JsonProperty("return_shipping_fee")
    private Long returnShippingFee = 0L;

    @JsonProperty("exchange_shipping_fee")
    private Long exchangeShippingFee = 0L;
}
