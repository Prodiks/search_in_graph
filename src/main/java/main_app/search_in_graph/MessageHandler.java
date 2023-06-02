package main_app.search_in_graph;

import java.util.HashMap;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class MessageHandler
{
    public enum ErrorCodes {
        NONE,
        INVALID_COUNT_VEC_VALUE,
        INVALID_MATRIX_VALUE,
        MATRIX_EMPTY
    }

    public enum MessageCodes {
        NONE,
        WAIT_INPUT,
        SUCCESS_SEARCH,
        PATH_NOT_FOUND
    }

    HashMap<ErrorCodes, String> _errorsDict;
    HashMap<MessageCodes, String> _messageDict;

    Label _label;

    MessageHandler(Label label)
    {
        _label = label;

        _errorsDict = new HashMap();
        _errorsDict.put(ErrorCodes.NONE,                    "Нет ошибки");
        _errorsDict.put(ErrorCodes.INVALID_COUNT_VEC_VALUE, "Введено неверное количество вершин");
        _errorsDict.put(ErrorCodes.INVALID_MATRIX_VALUE,    "Введен неверный вес");
        _errorsDict.put(ErrorCodes.MATRIX_EMPTY,            "Матрица не заполнена");

        _messageDict = new HashMap();
        _messageDict.put(MessageCodes.NONE,           "Нет сообщения");
        _messageDict.put(MessageCodes.WAIT_INPUT,     "Введите матрицу весов");
        _messageDict.put(MessageCodes.SUCCESS_SEARCH, "Поиск успешно выполнен");
        _messageDict.put(MessageCodes.PATH_NOT_FOUND, "Путь не обнаружен");
    }

    public void showError(ErrorCodes errorCode)
    {
        updateLabel(_errorsDict.get(errorCode), Color.RED);
    }

    public void showMessage(MessageCodes messageCode)
    {
        updateLabel(_messageDict.get(messageCode), Color.BLUE);
    }

    public void hideMessage()
    {
        updateLabel("", Color.WHITE);
    }
    
    private void updateLabel(String message, Color color)
    {
        _label.setTextFill(color);
        _label.setText(message);

        if(message.length() > 0) {
            System.out.println(message);
        }
    }
}
