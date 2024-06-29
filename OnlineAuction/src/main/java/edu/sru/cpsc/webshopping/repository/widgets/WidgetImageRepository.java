package edu.sru.cpsc.webshopping.repository.widgets;

import java.sql.Blob;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.sru.cpsc.webshopping.domain.market.MarketListing;
import edu.sru.cpsc.webshopping.domain.widgets.WidgetImage;
import jakarta.transaction.Transactional;

public interface WidgetImageRepository extends CrudRepository<WidgetImage, Long>{
	List<WidgetImage> findByMarketListing(MarketListing marketListing);
	WidgetImage findFirstByMarketListing(MarketListing marketListing);
}
