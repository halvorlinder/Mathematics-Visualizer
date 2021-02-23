package canvas3d;

import math3d.Vector3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Mesh extends Render3D{
    protected double currentScale;
    protected double scaleX = 1;
    protected double scaleY = 1;
    protected double scaleZ = 1;

    //TODO vector arrow mesh based on magnitude
    public Mesh(Vector3 position){
        super(position);
    }

    public Mesh(){
        super();
    }

    public Mesh(String path, Vector3 position, double scale){
        super(position);

        currentScale = scale;

        try(Scanner sc = new Scanner(new File(getClass().getResource(path).toExternalForm().replace("file:/", "")))){

            List<Vector3> vertices = new ArrayList<>();
            List<Triangle> triangles = new ArrayList<>();
            while(sc.hasNextLine()){
                String[] line = sc.nextLine().split(" ");

                if(line[0].equals("v"))
                    vertices.add(Vector3.scale(new Vector3(Double.parseDouble(line[1]), Double.parseDouble(line[2]), Double.parseDouble(line[3])), scale));

                else if(line[0].equals("f")){
                    try {
                        Triangle triangle = new Triangle(vertices.get(Integer.parseInt(line[1])-1), vertices.get(Integer.parseInt(line[2])-1), vertices.get(Integer.parseInt(line[3])-1));
                        triangles.add(triangle);
                    }
                    catch (Exception e){
                        System.out.println(Arrays.toString(line));
                        System.out.println(vertices.size());
                        //e.printStackTrace();
                    }
                }
            }
            this.vertices = vertices.toArray(new Vector3[0]);
            this.triangles = triangles.toArray(new Triangle[0]);
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void setScale(double scale){
        for (Vector3 vertex : vertices) {
            vertex.scale(scale / currentScale);
        }
        currentScale = scale;
    }



    @Override
    public void beforeRender() {

    }

    @Override
    public Object getMath() {
        return null;
    }
}
