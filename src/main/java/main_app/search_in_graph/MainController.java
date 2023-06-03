package main_app.search_in_graph;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.Vector;

import javafx.scene.input.KeyEvent;
public class MainController implements Initializable {

    private final int _matrixRowCount = 10;
    private final int _matrixColumnCount = 10;
    private final int _minVecCount = 3;
    private int _vecFrom;
    private int _vecTo;
    private Graph _graph;
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
    @FXML
    private TextField _vecFromField;
    @FXML
    private TextField _vecToField;
    @FXML
    private TextArea _pathArea;
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
        _currentVertexCount = 10;

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
    private void handleCountVecFieldChanged()
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
        _messageHandler.hideMessage();

        int[][] weightMatrix = getWeightMatrix();
        if(weightMatrix == null) {
            return;
        }

        _vecTo = updateVecFieldValue(_vecFromField, MessageHandler.ErrorCodes.VEC_FROM_INVALID);
        _vecFrom = updateVecFieldValue(_vecToField, MessageHandler.ErrorCodes.VEC_TO_INVALID);
        if((_vecFrom == -1) || (_vecTo == -1)) {
            return;
        }

        _graph = new Graph(weightMatrix);
        Vector<Graph.PathNode> path = _graph.searchPath(_vecTo, _vecFrom);

        updatePathArea(path, _graph.getPathWeight());
    }

    private void updatePathArea(Vector<Graph.PathNode> path, int pathWeight)
    {
        if(path == null) {
            _pathArea.setText("Путь не обнаружен");
            _messageHandler.showMessage(MessageHandler.MessageCodes.PATH_NOT_FOUND);
            return;
        }

        _pathArea.setText("Вершины  | Вес\n");

        for(int i = 0; i < path.size(); i++) {
            String col1 = completeSpaces("e" + (path.get(i).getVertex() + 1), 16);
            String col2 = path.get(i).getWeight() + " ед.";
            String text = col1 + "| " + col2;
            _pathArea.setText(_pathArea.getText() + text + "\n");
        }

        _pathArea.setText(_pathArea.getText() + "\nОбщий вес пути: " + pathWeight + " ед.");
    }

    private String completeSpaces(String str, int size)
    {
        while(str.length() < size) {
            str += " ";
        }

        return str;
    }

    @FXML
    private void handleVecFromFieldTyped()
    {
        updateVecFieldValue(_vecFromField, MessageHandler.ErrorCodes.VEC_FROM_INVALID);
    }

    @FXML
    private void handleVecToFieldTyped()
    {
        updateVecFieldValue(_vecToField, MessageHandler.ErrorCodes.VEC_TO_INVALID);
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

        _vecFromField.setText("");
        _vecToField.setText("");
        _pathArea.setText("" );
        _messageHandler.hideMessage();
    }

    private int[][] getWeightMatrix()
    {
        _messageHandler.hideMessage();

        int countZeroes = 0;
        int[][] matrix = new int [_currentVertexCount][_currentVertexCount];
        for (int i = 0; i < _currentVertexCount; i++) {
            for (int j = 0; j < _currentVertexCount; j++) {
                String matrixFieldValue = _matrixFields[i][j].getText().strip();
                if(matrixFieldValue == "") {
                    _matrixFields[i][j].setText(matrixFieldValue = "0");
                }

                try {
                    matrix[i][j] = Integer.parseInt(matrixFieldValue);
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
            return matrix;
        }
    }

    private int updateVecFieldValue(TextField textField, MessageHandler.ErrorCodes errorCode)
    {
        _messageHandler.hideMessage();

        int newVec = -1;
        try {
            newVec = Integer.parseInt(textField.getText());
        }
        catch(NumberFormatException ex) {
            textField.setText("");
            _messageHandler.showError(errorCode);
            return -1;
        }

        if((newVec > _currentVertexCount) || (newVec < 1)) {
            _messageHandler.showError(errorCode);
            return -1;
        } else {
            return newVec - 1;
        }
    }
}