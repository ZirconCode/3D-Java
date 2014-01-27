
// Algortihms

public class Algorithms
{
	
   // Get Center/Mean of Triangle
   public static Vertex getCentroid(Triangle t)
   {
      return new Vertex(
         (t.a.x + t.b.x + t.c.x) / 3.0,
         (t.a.y + t.b.y + t.c.y) / 3.0,
         (t.a.z + t.b.z + t.c.z) / 3.0 );
   }

   // Creates a Cube
   public static Scenery CreateCube(double h, double l, double w)
   {
	   Scenery Cube = new Scenery();
	   
	   Vertex p1 = new Vertex(0,0,0);
	   Vertex p2 = new Vertex(0,l,0);
	   Vertex p3 = new Vertex(0,0,w);
	   Vertex p4 = new Vertex(0,l,w);
	   Vertex p5 = new Vertex(h,0,0);
	   Vertex p6 = new Vertex(h,l,0);
	   Vertex p7 = new Vertex(h,0,w);
	   Vertex p8 = new Vertex(h,l,w);
	   
	   Cube.add(new Triangle(p4, p2, p1));
	   Cube.add(new Triangle(p1, p3, p4));
	   Cube.add(new Triangle(p3, p7, p4));
	   Cube.add(new Triangle(p4, p7, p8));
	   Cube.add(new Triangle(p4, p8, p6));
	   Cube.add(new Triangle(p6, p2, p4));
	   Cube.add(new Triangle(p1, p5, p3));
	   Cube.add(new Triangle(p5, p7, p3));
	   Cube.add(new Triangle(p5, p1, p2));
	   Cube.add(new Triangle(p5, p2, p6));
	   Cube.add(new Triangle(p5, p6, p8));
	   Cube.add(new Triangle(p5, p8, p7));
   
	   return Cube;
   }
   
   // Gets the Normal
   public static Vertex getNormal(Triangle t)
   {
      Vertex point = new Vertex();

      point.x =  (t.a.y - t.b.y) * (t.a.z + t.b.z) +
                 (t.b.y - t.c.y) * (t.b.z + t.c.z) +
                 (t.c.y - t.a.y) * (t.c.z + t.a.z) ;

      point.y =  (t.a.z - t.b.z) * (t.a.x + t.b.x) +
                 (t.b.z - t.c.z) * (t.b.x + t.c.x) +
                 (t.c.z - t.a.z) * (t.c.x + t.a.x) ;

      point.z =  (t.a.x - t.b.x) * (t.a.y + t.b.y) +
                 (t.b.x - t.c.x) * (t.b.y + t.c.y) +
                 (t.c.x - t.a.x) * (t.c.y + t.a.y) ;

      double l = point.length();

      point.x = point.x / l;
      point.y = point.y / l;
      point.z = point.z / l;

      return point;
   }

   // Gets Distance
   public static Vertex getLine(Vertex point1, Vertex point2)
   {
	   Vertex point = new Vertex (point1.x - point2.x,
			                      point1.y - point2.y,
			                      point1.z - point2.z );
     
      return point.unitVector();
   }

   // 
   public static double dot(Vertex point1, Vertex point2)
   {
      return (point1.x * point2.x +
    		  point1.y * point2.y +
    		  point1.z * point2.z  );
   }
   
}