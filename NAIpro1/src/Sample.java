public class Sample implements Comparable<Sample>{
    String data ;
   double distance;
    public Sample(String data, double distance)
    {
        this.data= data;
        this.distance = distance;
    }
    @Override
    public String toString() {
        return "Sample{" +
                "data='" + data + '\'' +
                ", distance=" + distance +
                '}';
    }
    @Override
    public int compareTo(Sample o) {
        double compareDist = ((Sample) o).distance;
        if(this.distance - compareDist >0)
        {
            return 1;
        }
        else
            return -1;
    }
    //if diff>0 return 1 else return -1
}
