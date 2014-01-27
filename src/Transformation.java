import java.util.Enumeration;

// Transformation

public abstract class Transformation
{

   // Transform
   public abstract void transform(Vertex point);

   // Transform List
   public void transform(Enumeration e)
   {
      while (e.hasMoreElements()) transform((Vertex)e.nextElement());
   }
   
}
