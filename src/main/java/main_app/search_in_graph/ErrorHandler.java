package main_app.search_in_graph;

import java.util.HashMap;
import java.util.Map;

public class ErrorHandler
{
    public enum ErrorCodes {
        NONE,
        INVALID_COUNT_VEC_VALUE,
        INVALID_MATRIX_VALUE,
        MATRIX_EMPTY,
        PATH_NOT_FOUND
    }
    Map<ErrorCodes, String> errors_dict = new HashMap<>();
    errors_dict.put(NONE);

    // СДЕЛАТЬ ЕДИНЫЙ КЛАСС ДЛЯ ОШИБОК И ДРУГИХ СООБЩЕНИЙ
}
