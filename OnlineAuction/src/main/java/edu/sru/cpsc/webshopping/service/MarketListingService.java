package edu.sru.cpsc.webshopping.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.repository.market.MarketListingRepository;

/**
 * This service is responsible for the pagination 
 * of the market listings on the browse page.
 * @author Wolfgang
 */

@Service
public class MarketListingService {

	Collection<Boolean> isDeleted = Arrays.asList(Boolean.TRUE);
	Collection<Long> quantityZero = Arrays.asList(Long.valueOf(0));
	double startPrice = 0;
	double endPrice = Double.MAX_VALUE;
	long startQuantity = 1;
	long endQuantity = Long.MAX_VALUE;
	
	@Autowired
	MarketListingRepository marketListingRepository;

	/**
	 * Returns a page of market listings corresponding to the current page number
	 * @param pageNum
	 * @return
	 * @author Wolfgang
	 */
	public Page<MarketListing> findPage(int pageNum) {
		Pageable pageable = PageRequest.of(pageNum - 1, 4);
//		Collection<Boolean> isDeleted = new ArrayList<Boolean>();
//		Collection<Long> quantityZero = new ArrayList<Long>();
//		isDeleted.add(Boolean.TRUE);
//		quantityZero.add(Long.valueOf(0));
		return marketListingRepository.findAllByIsDeletedNotInAndQtyAvailableNotIn(isDeleted, quantityZero, pageable);
	}
	
	/**
	 * Returns a page of market listings corresponding to the current page number.
	 * Applies a filter based on price and quantity.
	 * @param pageNum
	 * @param allParams
	 * @param values
	 * @return
	 * @author Wolfgang
	 */
	public Page<MarketListing> findPageWithFilter(int pageNum, Map<String, String> allParams, ArrayList<Object> values) {
		Pageable pageable = PageRequest.of(pageNum - 1, 4);
		double startPrice;
		double endPrice;
		long startQuantity;
		long endQuantity;
		
		if(allParams.get("startPrice") == "") {
			startPrice = this.startPrice;
			values.add("");
		}
		else {
			startPrice = Double.valueOf(allParams.get("startPrice"));
			values.add(startPrice);
		}		
		
		if(allParams.get("endPrice") == "") {
			endPrice = this.endPrice;
			values.add("");
		}
		else {
			endPrice = Double.valueOf(allParams.get("endPrice"));
			values.add(endPrice);
		}	
		
		if(allParams.get("startQty") == "") {
			startQuantity = this.startQuantity;
			values.add("");
		}
		else {
			startQuantity = Long.valueOf(allParams.get("startQty"));
			values.add(startQuantity);
		}	
		
		if(allParams.get("endQty") == "") {
			endQuantity = this.endQuantity;
			values.add("");
		}
		else {
			endQuantity = Long.valueOf(allParams.get("endQty"));
			values.add(endQuantity);
		}	
		
//		System.out.println(String.format("%s\n%s\n%s\n%s", startPrice, endPrice, startQuantity, endQuantity));
		
		/*
		 * Really long repository query ensuring the listing is not deleted, the quantity of items is not 0,
		 * and that the listing price and quantity are between the set variables.
		 * (If no beginning or end is set to the filters, it just uses the maximum and minimum values)
		 */
		return marketListingRepository
				.findAllByIsDeletedNotInAndQtyAvailableNotInAndPricePerItemBetweenAndQtyAvailableBetween(isDeleted,
						quantityZero, startPrice, endPrice, startQuantity, endQuantity, pageable);
	}
}
