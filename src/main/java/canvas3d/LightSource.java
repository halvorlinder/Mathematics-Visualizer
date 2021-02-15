package canvas3d;

import math3d.Vector3;

public class LightSource extends GameObject{
    private Mesh lightBulb;
    public LightSource(Vector3 position){
        super(position);
        //lightBulb = Mesh.fromFile("light.obj", position);
    }

    public double getBrightness(Vector3 middlePolygon, Vector3 normalPolygon){
        Vector3 delta = Vector3.subtract(position, middlePolygon);
        if(delta.getX() == 0d && delta.getY() == 0d && delta.getZ() == 0d)
            return 1;
        double dot = delta.normalized().dot(normalPolygon.normalized());

        //System.out.println(dot);
        if(dot <= 0)
            return 0;
        else return dot;
    }

    @Override
    public void update(){
        //fix annet sted
        position = Vector3.rotateY(position, 0.001*CanvasRenderer3D.deltaTime);
    }
}
