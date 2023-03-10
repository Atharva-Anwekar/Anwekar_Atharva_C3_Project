import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    static Restaurant restaurant;

    @BeforeAll
    public static void setUp() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        restaurant.addToMenu("Chicken soup", 149);
        restaurant.addToMenu("Pizza", 239);
    }
    //REFACTOR ALL THE REPEATED LINES OF CODE

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        // Arrange
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        LocalTime currentTime = LocalTime.parse("13:00:00");
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTime);
        //Act
        boolean isOpenOrNot = spiedRestaurant.isRestaurantOpen();
        //Assert
        assertTrue(isOpenOrNot);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        // Arrange
        Restaurant spiedRestaurant = Mockito.spy(restaurant);
        LocalTime currentTime = LocalTime.parse("23:00:00");
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(currentTime);
        //Act
        boolean isOpenOrNot = spiedRestaurant.isRestaurantOpen();
        //Assert
        assertFalse(isOpenOrNot);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class, () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void calculateOrderValue_should_return_total_value_of_the_order(){
        int expectedOrderValue = 388;
        List<String> itemList = new ArrayList<>(Arrays.asList("Chicken soup","Pizza"));
        int totalCalculatedOrderValue = restaurant.calculateTotalOrderValue(itemList);
        assertThat(totalCalculatedOrderValue,equalTo(expectedOrderValue));
    }
}