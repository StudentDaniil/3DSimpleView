package com.cgvsu.objreader;

import com.cgvsu.model.Polygon;

import java.util.ArrayList;

public class Triangulation {

    public static ArrayList<Polygon> triangulatePolygons(final ArrayList<Polygon> initialList) {
        ArrayList<Polygon> onlyTrianglePolygons = new ArrayList<>();
        for (Polygon polygon : initialList) {
            for (int i = 0; i < polygon.getVertexIndices().size() - 2; i++) {
                Polygon triangle = new Polygon();

                ArrayList<Integer> vertexIndices = new ArrayList<>();
                vertexIndices.add(polygon.getVertexIndices().get(0));
                vertexIndices.add(polygon.getVertexIndices().get(i + 1));
                vertexIndices.add(polygon.getVertexIndices().get(i + 2));
                triangle.setVertexIndices(vertexIndices);

                if (polygon.getTextureVertexIndices().size() != 0) {
                    ArrayList<Integer> textureVertexIndices = new ArrayList<>();
                    textureVertexIndices.add(polygon.getTextureVertexIndices().get(0));
                    textureVertexIndices.add(polygon.getTextureVertexIndices().get(i + 1));
                    textureVertexIndices.add(polygon.getTextureVertexIndices().get(i + 2));
                    triangle.setTextureVertexIndices(textureVertexIndices);
                }

                if (polygon.getNormalIndices().size() != 0) {
                    ArrayList<Integer> normalVertexIndices = new ArrayList<>();
                    normalVertexIndices.add(polygon.getNormalIndices().get(0));
                    normalVertexIndices.add(polygon.getNormalIndices().get(i + 1));
                    normalVertexIndices.add(polygon.getNormalIndices().get(i + 2));
                    triangle.setNormalIndices(normalVertexIndices);
                }

                onlyTrianglePolygons.add(triangle);
            }
        }
        return onlyTrianglePolygons;
    }
}
