package client.scenes;
//
//import client.utils.OurServerUtils;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//import javafx.util.Pair;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//

class MainTaskListCtrlTest {

//    @Test
//    public void testInitialize() {
//        // Set up the mocks
//        Stage mockPrimaryStage = Mockito.mock(Stage.class);
//
//        TaskListCtrl mockTaskListCtrl = Mockito.mock(TaskListCtrl.class);
//        Parent mockTaskListParent = Mockito.mock(Parent.class);
//
//        ServerConnectCtrl mockServerConnectCtrl = Mockito.mock(ServerConnectCtrl.class);
//        Parent mockServerConnectParent = Mockito.mock(Parent.class);
//        Scene mockServerConnectScene = new Scene(mockServerConnectParent);
//
//        AdminSceneCtrl mockAdminSceneCtrl = Mockito.mock(AdminSceneCtrl.class);
//        Parent mockAdminOverviewParent = Mockito.mock(Parent.class);
//
//        OverviewOfBoardsCtrl mockOverviewOfBoardsCtrl = Mockito.mock(OverviewOfBoardsCtrl.class);
//        Parent mockOverviewOfBoardsParent = Mockito.mock(Parent.class);
//
//        // Create the MainTaskListCtrl and call initialize()
//        MainTaskListCtrl mainTaskListCtrl = new MainTaskListCtrl();
//        mainTaskListCtrl.initialize(mockPrimaryStage,
//                new Pair<>(mockTaskListCtrl, mockTaskListParent),
//                new Pair<>(mockServerConnectCtrl, mockServerConnectParent),
//                new Pair<>(mockAdminSceneCtrl, mockAdminOverviewParent),
//                new Pair<>(mockOverviewOfBoardsCtrl, mockOverviewOfBoardsParent));
//
//        // Verify that the correct methods were called on the mocks
//        verify(mockPrimaryStage).setScene(mockServerConnectScene);
//        verify(mockPrimaryStage).show();
//    }
//
//    @Test
//    public void testShowTaskListView() {
//        // Set up the mocks
//        Stage mockPrimaryStage = Mockito.mock(Stage.class);
//        TaskListCtrl mockTaskListCtrl = Mockito.mock(TaskListCtrl.class);
//        Parent mockTaskListParent = Mockito.mock(Parent.class);
//        Scene mockTaskListScene = Mockito.mock(Scene.class);
//
//        // Configure the mock objects
//        when(mockTaskListParent.getScene()).thenReturn(mockTaskListScene);
//        doAnswer(invocation -> {
//            mockTaskListCtrl.setTaskListCtrlBoard(mockTaskListScene);
//            return null;
//        }).when(mockPrimaryStage).setScene(any(Scene.class));
//
//        // Create the MainTaskListCtrl and call showTaskListView()
//        MainTaskListCtrl mainTaskListCtrl = new MainTaskListCtrl();
//        mainTaskListCtrl.initialize(mockPrimaryStage, new Pair<>(mockTaskListCtrl, mockTaskListParent),
//                null, null, null);
//        mainTaskListCtrl.showTaskListView();
//
//        // Verify that the correct methods were called on the mocks
//        verify(mockPrimaryStage).setTitle("Task List");
//        verify(mockPrimaryStage).setScene(mockTaskListScene);
//    }




}