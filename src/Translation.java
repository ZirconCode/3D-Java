import java.util.*;

// Moves a Vertex

public class Translation extends Transformation
{
	
   // Variables
   private double _x;
   private double _y;
   private double _z;

   // Constructors
   public Translation(double x, double y, double z)
   {
      _x = x;
      _y = y;
      _z = z;
   }

   public Translation(Vertex point)
   {
      _x = point.x;
      _y = point.y;
      _z = point.z;
   }
   
   // Translate/Move a Vertex
   public void transform(Vertex point)
   {
      if (point == null) return;

      point.x = _x+point.x;
      point.y = _y+point.y;
      point.z = _z+point.z;
   }
   
}
