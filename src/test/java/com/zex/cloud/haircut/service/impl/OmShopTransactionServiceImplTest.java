package com.zex.cloud.haircut.service.impl;

import com.zex.cloud.haircut.HaircutApplicationTests;
import com.zex.cloud.haircut.service.IOmShopTransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class OmShopTransactionServiceImplTest extends HaircutApplicationTests {

    @Autowired
    private IOmShopTransactionService iOmShopTransactionService;
    @Test
    void currentShop() {

        iOmShopTransactionService.currentShop(1253968239117996034L);
    }
}