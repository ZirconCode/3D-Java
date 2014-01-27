
// Sorts Triangles By Depth of Average Point

public class TriangleSort
{
	
   public void sort(Object [] rgo)
   {
      sort(rgo, 0, rgo.length - 1);
   }

   private void sort(Object [] rgo, int nLow0, int nHigh0)
   {
      int nLow = nLow0;
      int nHigh = nHigh0;

      Object oMid;

      if (nHigh0 > nLow0)
      {
         oMid = rgo[ (nLow0 + nHigh0) / 2 ];

         while(nLow <= nHigh)
         {
            while((nLow < nHigh0) && lessThan(rgo[nLow], oMid))
               ++nLow;

            while((nLow0 < nHigh) && lessThan(oMid, rgo[nHigh]))
               --nHigh;

            if(nLow <= nHigh)
            {
               swap(rgo, nLow++, nHigh--);
            }
         }

         if(nLow0 < nHigh) sort(rgo, nLow0, nHigh);

         if(nLow < nHigh0) sort(rgo, nLow, nHigh0);
      }
   }

   private void swap(Object [] rgo, int i, int j)
   {
      Object o;

      o = rgo[i]; 
      rgo[i] = rgo[j];
      rgo[j] = o;
   }

   protected boolean lessThan(Object o1, Object o2)
   {
      Triangle ts1 = (Triangle)o1;
      Triangle ts2 = (Triangle)o2;

      double z1 = (ts1.a.z + ts1.b.z + ts1.c.z) / 3.0;
      double z2 = (ts2.a.z + ts2.b.z + ts2.c.z) / 3.0;

      return z1 < z2;
   }
   
}