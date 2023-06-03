package main_app.search_in_graph;

import java.util.*;

public class Graph
{
    class PathNode
    {
        private int _vertex;
        private int _weight;

        PathNode(int vertex, int weight)
        {
            _vertex = vertex;
            _weight = weight;
        }

        int getVertex()
        {
            return _vertex;
        }

        int getWeight()
        {
            return _weight;
        }
    }
    private int[][] _weights;
    private int _countVerticles;
    int[] _distances;
    int[] _previous;
    boolean[] _visited;
    int _startVertex;
    int _targetVertex;
    Vector<PathNode> _path;
    int _pathWeight;

    Graph(int[][] values)
    {
        _countVerticles = values.length;
        _weights = new int[_countVerticles][_countVerticles];
        for(int i = 0; i < _countVerticles; i++) {
            for(int j = 0; j < _countVerticles; j++) {
                _weights[i][j] = values[i][j];
            }
        }

        _distances = new int[_countVerticles];
        _previous = new int[_countVerticles];
        _visited = new boolean[_countVerticles];
        _path = new Vector<PathNode>();
        _pathWeight = 0;
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

    public Vector<PathNode> searchPath(int vecFrom, int vecTo)
    {
        _startVertex = vecFrom;
        _targetVertex = vecTo;
        _path = new Vector<>();
        _pathWeight = 0;

        dijkstraAlgorithm();
        printSolution();

        if(!updateSearchedPath(_targetVertex)) {
            _path = null;
        }
        return _path;
    }

    public int getPathWeight()
    {
        return _pathWeight;
    }

    public void dijkstraAlgorithm() {
        int numVertices = _weights.length;

        Arrays.fill(_distances, Integer.MAX_VALUE);
        Arrays.fill(_previous, -1);
        _distances[_startVertex] = 0;

        for (int i = 0; i < numVertices - 1; i++) {
            int minVertex = findMinVertex();
            _visited[minVertex] = true;

            for (int j = 0; j < numVertices; j++) {
                if (_weights[minVertex][j] != 0 && !_visited[j] && _distances[minVertex] != Integer.MAX_VALUE) {
                    int newDistance = _distances[minVertex] + _weights[minVertex][j];
                    if (newDistance < _distances[j]) {
                        _distances[j] = newDistance;
                        _previous[j] = minVertex;
                    }
                }
            }
        }
    }

    private boolean updateSearchedPath(int currentVertex)
    {
        if (currentVertex == _startVertex) {
            _path.add(new PathNode(_startVertex, 0));
            _pathWeight = 0;
        } else if (_previous[currentVertex] == -1) {
            return false;
        } else {
            updateSearchedPath(_previous[currentVertex]);

            int arcWeight;
            if(_path.size() == 0) {
                arcWeight = 0;
            } else {
                int prevVertex = _path.get(_path.size() - 1).getVertex();
                arcWeight = _weights[prevVertex][currentVertex];
            }

            _path.add(new PathNode(currentVertex, arcWeight));
            _pathWeight += arcWeight;
        }

        return true;
    }

    private int findMinVertex() {
        int minVertex = -1;
        for (int i = 0; i < _distances.length; i++) {
            if (!_visited[i] && (minVertex == -1 || _distances[i] < _distances[minVertex])) {
                minVertex = i;
            }
        }
        return minVertex;
    }

    private void printSolution() {
        System.out.println("Vertex\tDistance\tPath");

        for (int i = 0; i < _distances.length; i++) {
            System.out.print(i + "\t\t" + _distances[i] + "\t\t");
            printPath(i);
            System.out.println();
        }
    }

    private void printPath(int currentVertex) {
        if (currentVertex == _startVertex) {
            System.out.print(_startVertex);
        } else if (_previous[currentVertex] == -1) {
            System.out.print("No path");
        } else {
            printPath(_previous[currentVertex]);
            System.out.print(" -> " + currentVertex);
        }
    }
}
