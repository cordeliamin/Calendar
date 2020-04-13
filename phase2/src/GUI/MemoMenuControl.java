package GUI;

import CalendarSystem.Event;
import CalendarSystem.Memo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

public class MemoMenuControl extends Controller {

    @FXML private Button createMemo;
    @FXML private Button returnToMenu;
    @FXML private TableView<Memo> memoTable;

    @FXML private void goBackToMenu() throws IOException {
        setScreen("MainMenuScene.fxml", returnToMenu);
    }

    @FXML private void editMemo() throws IOException{

        Memo memo = memoTable.getSelectionModel().getSelectedItem();

        Label instructions = new Label("Memo note:");
        TextField note = new TextField(memo.getNote());
        Button save = new Button("Save");
        PopUp editMemo = new PopUp("Edit Memo", getTheme(), 9.0, 40, 50, 10, 20);
        editMemo.getContent().addAll(instructions, note, save);
        save.setOnAction(e -> {
            try{
                if (!note.getText().equals("")) {
                    memo.setNote(note.getText());
                }
            } catch (NullPointerException nullp) {}
            editMemo.exit();
        });
        getCalendarManager().saveToFile();
        editMemo.display();
    }

    @FXML private void deleteMemos() throws IOException{
        ArrayList<Memo> items = new ArrayList<>(memoTable.getSelectionModel().getSelectedItems());
        for (Memo m : items) {
            getCalendar().deleteMemo(m);
            memoTable.getItems().remove(m);
        }
        getCalendarManager().saveToFile();
    }


}
