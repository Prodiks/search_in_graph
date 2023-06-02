package main_app.search_in_graph;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.input.KeyEvent;
public class MainController implements Initializable {

    private final int _matrixRowCount = 10;
    private final int _matrixColumnCount = 10;
    private final int _minVecCount = 3;
    private Graph _gpaph;
    private MessageHandler _messageHandler;
    private int _currentVertexCount;
    private TextField[][] _matrixFields;

    @FXML
    private Button _searchButton;
    @FXML
    private Button _clearButton;
    @FXML
    private GridPane _matrixGridPane;
    @FXML
    private Slider _slider;
    @FXML
    private TextField _countVerticlesField;
    @FXML
    private Label _labelError;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        _matrixFields = new TextField[_matrixRowCount][_matrixColumnCount];
        for (int i = 0; i < _matrixRowCount; i++) {
            for (int j = 0; j < _matrixColumnCount; j++) {
                TextField textField = new TextField();
                textField.setMinSize(0, 0);
                textField.setPrefSize(40, 40);
                textField.setOnKeyTyped(event -> handleMatrixKeyTyped(event, textField));
                if (i == j) {
                    textField.setText("0");
                    textField.setEditable(false);
                }

                _matrixFields[i][j] = textField;
                _matrixGridPane.setMargin(textField, new Insets(3, 3, 3, 3));
                _matrixGridPane.add(textField, j + 1, i + 1);
            }
        }

        addListenerToSlider();

        _messageHandler = new MessageHandler(_labelError);
        _messageHandler.showMessage(MessageHandler.MessageCodes.WAIT_INPUT);
    }

    private void addListenerToSlider()
    {
        _slider.valueProperty().addListener((observable, oldValue, newValue) -> {
            _currentVertexCount = Double.valueOf(_slider.getValue()).intValue();
            _countVerticlesField.setText(String.valueOf(_currentVertexCount));

            for (int i = 0; i < _matrixRowCount; i++) {
                for (int j = 0; j < _matrixColumnCount; j++) {
                    boolean isFieldDisable = (i >= _currentVertexCount) || (j >= _currentVertexCount);
                    _matrixFields[i][j].setDisable(isFieldDisable);
                }
            }
        });
    }

    private void handleMatrixKeyTyped(KeyEvent event, TextField textField)
    {
        _messageHandler.hideMessage();
        try {
            if (Integer.parseInt(textField.getText()) < 0) {
                _messageHandler.showError(MessageHandler.ErrorCodes.INVALID_MATRIX_VALUE);
                textField.setText("");
            }
        }
        catch(NumberFormatException ex) {
            _messageHandler.showError(MessageHandler.ErrorCodes.INVALID_MATRIX_VALUE);
            textField.setText("");
        }
    }

    @FXML
    private void handleCountVecFiledChanged()
    {
        try {
            int newVecCount = Integer.parseInt(_countVerticlesField.getText());
            if(newVecCount < _minVecCount) {
                _messageHandler.showError(MessageHandler.ErrorCodes.INVALID_COUNT_VEC_VALUE);
                _currentVertexCount = _minVecCount;
            } else if (newVecCount > _matrixRowCount) {
                _messageHandler.showError(MessageHandler.ErrorCodes.INVALID_COUNT_VEC_VALUE);
                _currentVertexCount = _matrixRowCount;
            } else {
                _messageHandler.hideMessage();
                _currentVertexCount = newVecCount;
            }
            _slider.setValue(_currentVertexCount);
        }
        catch(NumberFormatException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void handleSearchButtonClick()
    {
        int[][] weightMatrix = getWeightMatrix();
        if(weightMatrix == null) {
            return;
        }
        _gpaph = new Graph(weightMatrix);
    }

    @FXML
    private void handleClearButtonClick()
    {
        for (int i = 0; i < _matrixRowCount; i++) {
            for (int j = 0; j < _matrixColumnCount; j++) {
                if(i != j) {
                    _matrixFields[i][j].setText("");
                }
            }
        }
        _messageHandler.hideMessage();
    }

    private int[][] getWeightMatrix()
    {
        int countZeroes = 0;
        int[][] matrix = new int [_currentVertexCount][_currentVertexCount];
        for (int i = 0; i < _currentVertexCount; i++) {
            for (int j = 0; j < _currentVertexCount; j++) {
                String matrixFiledValue = _matrixFields[i][j].getText().strip();
                if(matrixFiledValue == "") {
                    _matrixFields[i][j].setText(matrixFiledValue = "0");
                }

                try {
                    matrix[i][j] = Integer.parseInt(matrixFiledValue);
                    if(matrix[i][j] == 0) {
                        countZeroes++;
                    }
                }
                catch(NumberFormatException ex) {
                    _messageHandler.showError(MessageHandler.ErrorCodes.INVALID_COUNT_VEC_VALUE);
                    return null;
                }
            }
        }

        if(countZeroes == _currentVertexCount * _currentVertexCount) {
            _messageHandler.showError(MessageHandler.ErrorCodes.MATRIX_EMPTY);
            return null;
        } else {
            _messageHandler.hideMessage();
            return matrix;
        }
    }
}