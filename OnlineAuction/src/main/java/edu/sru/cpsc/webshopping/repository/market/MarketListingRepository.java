package edu.sru.cpsc.webshopping.repository.market;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.user.User;
import edu.sru.cpsc.webshopping.domain.widgets.Widget;

public interface MarketListingRepository extends CrudRepository<MarketListing, Long> {
	List<MarketListing> findBySeller(User user);

	MarketListing findByWidgetSold(Widget widget);

	String findNameById(Long id);

	Page<MarketListing> findAll(Pageable pageable);

	Page<MarketListing> findAllByIsDeletedNotInAndQtyAvailableNotIn(Collection<?> isDeleted, Collection<?> quantityZero,
			Pageable pageable);

	Page<MarketListing> findAllByIsDeletedNotInAndQtyAvailableNotInAndPricePerItemBetweenAndQtyAvailableBetween(
			Collection<?> isDeleted, Collection<?> quantityZero, double startPrice, double endPrice,
			long startQuantity, long endQuantity, Pageable pageable);
}
