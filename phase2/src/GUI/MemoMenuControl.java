package GUI;

import CalendarSystem.Event;
import CalendarSystem.Memo;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MemoMenuControl extends Controller {

    @FXML
    private Button createMemo;
    @FXML
    private Button returnToMenu;
    @FXML
    private TableView<Memo> memoTable;
    @FXML
    MenuItem editMemo;
    @FXML
    MenuItem deleteMemo;
    @FXML
    TableColumn<Memo, String> memoContent;
    @FXML
    ComboBox<Event> events;
    @FXML
    TextField newMemoNote;


    @FXML
    private void goBackToMenu() throws IOException {
        setScreen("MainMenuScene.fxml", returnToMenu);
    }

    @Override
    protected void initScreen() {
        //Populate memoTable
        memoContent.setCellValueFactory(new PropertyValueFactory<>("note"));
        ObservableList<Memo> memoTableItems = FXCollections.observableArrayList();
        memoTableItems.addAll(getCalendar().getMyMemos().getMemos());
        memoTable.setItems(memoTableItems);
        for (Event e : getCalendar().getMyEvents()) {
            events.getItems().add(e);
        }
        initSelectionSettings();
    }

    private void initSelectionSettings() {

        memoTable.setPlaceholder(new Label("No Memos Found"));
        memoTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        memoTable.getSelectionModel().getSelectedItems().addListener((ListChangeListener<Memo>) change -> {
            if (change.getList().size() == 1) {
                deleteMemo.setVisible(true);
                editMemo.setVisible(true);
            } else if (change.getList().size() > 1) {
                deleteMemo.setVisible(true);
                editMemo.setVisible(false);
            } else {
                deleteMemo.setVisible(false);
                editMemo.setVisible(false);
            }
        });
    }

    @FXML
    private void editMemo() throws IOException {

        Memo memo = memoTable.getSelectionModel().getSelectedItem();

        Label instructions = new Label("Memo note:");
        TextField note = new TextField(memo.getNote());
        Button save = new Button("Save");
        PopUp editMemo = new PopUp("Edit Memo", getTheme(), 9.0, 40, 50, 10, 20);
        editMemo.getContent().addAll(instructions, note, save);
        save.setOnAction(e -> {
            try {
                if (!note.getText().equals("")) {
                    memo.setNote(note.getText());
                }
            } catch (NullPointerException nullp) {
            }
            editMemo.exit();
        });
        getCalendarManager().saveToFile();
        editMemo.display();
    }

    @FXML
    private void deleteMemos() throws IOException {
        ArrayList<Memo> items = new ArrayList<>(memoTable.getSelectionModel().getSelectedItems());
        for (Memo m : items) {
            getCalendar().deleteMemo(m);
            memoTable.getItems().remove(m);
        }
        getCalendarManager().saveToFile();
    }

    @FXML
    public void createNewMemo() throws IOException {
        String note = newMemoNote.getText();

        List<Event> l = new ArrayList<>();
        l.add(events.getValue());
        events.getValue().getMemos().add(new Memo(note));


        getCalendarManager().saveToFile();

//        if (selectedEvent == null) {
//            selectedEvent = "";
//        }
//        if (!selectedEvent.equals("") &&
//                !memoOptions.getItems().contains(selectedMemo)) {
//            getCalendar().getMyMemos().createMemo(eventsToAdd, selectedMemo);
//            memoOptions.getItems().add(selectedMemo);
//        } else if (memoOptions.getItems().contains(selectedMemo)) {
//            for (Memo m : getCalendar().getMyMemos().getMemos()) {
//                if (m.getNote().equals(selectedMemo)) {
//                    for (Event e : eventsToAdd) {
//                        e.getMemos().add(m);
//                    }
//                    break;
//                }
//            }
//        }


    }
}
