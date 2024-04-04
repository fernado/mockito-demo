package pr.iceworld.fernando;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.mockito.Mockito.*;

public class M04_VerifyingExactNumberOfInvocations {

    @Test
    void action004_001() {
        LinkedList<String> mockedList = mock(LinkedList.class);

        // using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        // org.mockito.Mockito#times()
        // following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        // exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        // org.mockito.Mockito#never()
        // verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        // org.mockito.Mockito#atMostOnce()
        // org.mockito.Mockito#atLeastOnce()
        // org.mockito.Mockito#atLeast()
        // org.mockito.Mockito#atMost()
        // verification using atLeast()/atMost()
        verify(mockedList, atMostOnce()).add("once");
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");
    }


    @Test
    void action004_002() {
        class Address {
            public Address(Integer id) {
                this.id = id;
            }

            private Integer id;

            public Integer getId() {
                return this.id;
            }

        }

        class Order {
            private Integer id;
            private Address address;

            public Address getAddress() {
                return address;
            }

            public Order(Address address) {
                this.address = address;
            }

            public void addOrAddress(Address address) {
                if (address.getId() == null) {
                    noticeAddMerchant(address);
                } else {
                    noticeUpdateMerchant(address);
                }
            }

            private void noticeAddMerchant(Address address) {
            }

            private void noticeUpdateMerchant(Address address) {
                // update
                address.getId();
            }
        }

        Order orderMock = mock(Order.class);
        Address address = new Address(5);
        orderMock.addOrAddress(address);
        verify(orderMock, times(1)).addOrAddress(address);
        verify(orderMock, times(1)).addOrAddress(any());

        Address addressMock = mock(Address.class);
        orderMock.addOrAddress(addressMock);
        Integer aId = addressMock.getId();
        verify(orderMock, times(1)).addOrAddress(addressMock);
        verify(addressMock, times(1)).getId();
    }
}
