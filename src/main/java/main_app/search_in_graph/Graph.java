package main_app.search_in_graph;

public class Graph
{
    private int[][] _weights;
    private int _countVerticles;

    Graph(int[][] values)
    {
        _countVerticles = values.length;
        _weights = new int[_countVerticles][_countVerticles];
        for(int i = 0; i < _countVerticles; i++) {
            for(int j = 0; j < _countVerticles; j++) {
                _weights[i][j] = values[i][j];
            }
        }
    }

    public void setWeight(int v1, int v2, int value)
    {
        _weights[v1][v2] = value;
    }

    public void setWeights(int[][] values)
    {
        for(int i = 0; i < _countVerticles; i++) {
            for(int j = 0; j < _countVerticles; j++) {
                _weights[i][j] = values[i][j];
            }
        }
    }

//    public int[] search(int verFrom, int vecTo)
//    {
//
//    }
}
