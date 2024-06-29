package edu.sru.cpsc.webshopping.domain.billing;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {CardTypeTest.class})
public class CardTypeTest {

    @Test 
    public void getCardType() {
        CardType cardType = new CardType();
        cardType.setCardType("Visa");
        assertEquals("Visa", cardType.getCardType());
    }
    
}
