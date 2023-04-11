package client.components;

import commons.ListOfCards;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListContainerTest {



   private static ListOfCards l = new ListOfCards("name");

   // test for the setListofCards method
   @Test
   public void setListOfCardsTest() {
       String name = "abc";
       l.setTitle(name);
       assertEquals(name, l.getTitle());
   }
}