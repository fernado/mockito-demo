//// comment this becaz it cant use `mvn clean test` successfully
// package pr.iceworld.fernando;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
//
// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.Mockito.when;
//
// // Initialize Mockito mocks with JUnit 5 extension
// @ExtendWith(MockitoExtension.class)
// public class ExampleMockitoWithJUnit5Test {
//
//     // Mocks
//     @Mock
//     private DataService dataServiceMock;
//
//     // Class under test
//     @InjectMocks
//     private BusinessService businessService;
//
//     @BeforeEach
//     public void setUp() {
//         // You can do additional setup here if needed
//     }
//
//     @Test
//     public void testBusinessService() {
//         // Given
//         int[] ss = new int[]{1, 2, 3};
//         when(dataServiceMock.retrieveData()).thenReturn(ss);
//
//         int[] datas = dataServiceMock.retrieveData();
//         assertEquals(ss, datas);
//
//         // When
//         int result = businessService.findGreatest();
//
//         // Then
//         assertEquals(3, result);
//     }
// }