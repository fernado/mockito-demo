package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class M19_AliasesForBehaviorDrivenDevelopment {

    @Test
    void action019() {
        Seller seller = mock(Seller.class);
        Shop shop = new Shop(seller);

        // org.mockito.BDDMockito#given()
        // org.mockito.BDDMockito#willReturn()
        // given
        given(seller.askForBread()).willReturn(new Bread());

        //when
        Goods goods = shop.buyBread();

        // org.junit.jupiter.api.Assertions#assertNotNull()
        // org.junit.jupiter.api.Assertions#assertTrue()
        // then
        assertNotNull(goods);
        assertTrue(goods.containsBread());
    }

    class Bread {
    }
    class Seller {
        public Bread askForBread() {
            return new Bread();
        }
    }
    class Goods {
        private List<Object> items;
        public Goods() {
            this.items = new ArrayList<>();
        }
        public void addItem(Object item) {
            this.items.add(item);
        }
        public boolean containsBread() {
            return items.stream().anyMatch(e -> e instanceof Bread);
        }
    }

    class Shop {
        private Seller seller;
        public Shop(Seller seller) {
            this.seller = seller;
        }
        public Goods buyBread() {
            Goods goods = new Goods();
            Bread bread = seller.askForBread();
            goods.addItem(bread);
            return goods;
        }
    }
}
