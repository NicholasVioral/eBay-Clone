package edu.sru.cpsc.webshopping.domain.market;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class MarketListingCSVModel {

  private long widgetId;
  private BigDecimal pricePerItem;
  private BigDecimal auctionPrice;
  private long qtyAvailable;

}
