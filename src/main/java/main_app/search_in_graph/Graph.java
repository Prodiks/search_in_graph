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

    public void print()
    {
        for(int i = 0; i < _countVerticles; i++) {
            for(int j = 0; j < _countVerticles; j++) {
                System.out.print(_weights[i][j] + " ");
            }
            System.out.println();
        }
    }

//    public int[] search(int vecFrom, int vecTo)
//    {
//
//    }
}
