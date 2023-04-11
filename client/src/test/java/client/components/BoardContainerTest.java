//package client.components;
//
//import client.TaskListMain;
//import client.scenes.MainTaskListCtrl;
//import client.scenes.TaskListCtrl;
//import client.utils.HelperFXinit;
//import client.utils.OurServerUtils;
//import commons.Board;
//import commons.Card;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.Button;
//import javafx.scene.layout.HBox;
//import javafx.scene.text.Text;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//class BoardContainerTest extends HelperFXinit {
//
//    public static OurServerUtils utils = mock(OurServerUtils.class);
//    public static MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
//    public static TaskListCtrl taskListCtrl = mock(TaskListCtrl.class);
//    public static Board board = mock(Board.class);
//    public static Board board2 = mock(Board.class);
//
////    @Test
////    public void testConstructor(){
////        BoardContainer boardContainer = new BoardContainer(board, utils, mainCtrl, taskListCtrl);
////        assertEquals(boardContainer.getBoard(), board);
////        assertEquals(boardContainer.getServer(), utils);
////        assertEquals(boardContainer.getMainCtrl(), mainCtrl);
////        assertEquals(boardContainer.getTaskListCtrl(), taskListCtrl);
////        HBox hbox = new HBox();
////        boardContainer.setParent(hbox);
////        assertEquals(hbox, boardContainer.getParentOfThis());
////    }
//
//    @Test
//    public void setBoardTest(){
//        BoardContainer boardContainer = new BoardContainer(board, utils, mainCtrl, taskListCtrl);
//        boardContainer.setBoard(board2);
//        assertEquals(board2, boardContainer.getBoard());
//
//    }
//
//    @Test
//    public void getBoardTest(){
//        BoardContainer boardContainer = new BoardContainer(board, utils, mainCtrl, taskListCtrl);
//        boardContainer.setBoard(board2);
//        assertEquals(board2, boardContainer.getBoard());
//    }
//
//    @Test
//    public void setBoardNameTextFieldTest(){
//        BoardContainer boardContainer = new BoardContainer(board, utils, mainCtrl, taskListCtrl);
//        Text boardNameTextField = new Text("abc");
//        boardContainer.setBoardNameTextField(boardNameTextField);
//        assertEquals(boardNameTextField, boardContainer.getBoardNameTextField());
//    }
//
//}