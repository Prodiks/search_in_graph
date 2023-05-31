package main_app.search_in_graph;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;
public class MainController implements Initializable
{
//    MainController()
//    {
//
//    }
    private final int matrix_row_count = 10;
    private final int matrix_column_count = 10;

    private TextField[][] matrix_fields;

    @FXML
    private Button search_button;

    @FXML
    private Button clear_button;

    @FXML
    private GridPane matrix_grid_pane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        matrix_fields = new TextField[matrix_row_count + 1][matrix_column_count + 1];
        for (int i = 1; i <= matrix_row_count; i++) {
            for (int j = 1; j <= matrix_column_count; j++) {
                TextField text_field = new TextField();
                text_field.setPrefSize(40, 30);

                matrix_fields[i][j] = text_field;
                matrix_fields[i][j].setOnKeyTyped(event -> handleMatrixKeyTyped(event, text_field));

                matrix_grid_pane.setMargin(text_field, new Insets(5, 5, 5, 5));
                matrix_grid_pane.add(text_field, j, i);
            }
        }
    }

    private void handleMatrixKeyTyped(KeyEvent event, TextField textField)
    {
        if(textField.getText().startsWith("-")) {
            textField.setText("-1");
            System.out.println("axaxa");
        }
        System.out.println("heheh " + textField.getText());
        for (int i = 1; i <= matrix_row_count; i++) {
            int count_ones = 0;
            for (int j = 1; j <= matrix_column_count; j++) {
                if((matrix_fields[i][j].getText() == "1") || (matrix_fields[i][j].getText() == "-1")) {
                    count_ones++;
                }
            }
            if(count_ones == 2) {
                for (int j = 1; j <= matrix_column_count; j++) {
                    if((matrix_fields[i][j].getText() == "1") || (matrix_fields[i][j].getText() == "-1")) {
                        continue;
                    }
                    matrix_fields[i][j].setText("0");
                }
            } else if(count_ones > 2) {
                System.out.println("error");
            }
        }
    }

    @FXML
    private void handleSearchButtonClick()
    {
    }

    @FXML
    private void handleClearButtonClick()
    {
        for (int i = 1; i <= matrix_row_count; i++) {
            for (int j = 1; j <= matrix_column_count; j++) {
                matrix_fields[i][j].setText("");
            }
        }
    }
}