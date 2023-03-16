import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

    static List<List<String>> readTrain;
    static List<List<String>> readTest;
    static List<List<String>> userTest;
    static List<Sample> shortestDistances;

    public static void setK(int k) {
        Main.k = k;
    }

    static int k;

    static double correctPredictions;

    public static void main(String[] args) throws IOException
    {
      readTrain = new ArrayList<>();
        readTest = new ArrayList<>();
     readTrainFile("train.txt");
     readTestFile("test.txt");

     //   System.out.println("4.7,3.2,1.6,0.2,Iris-setosa".split(",").length);
       // System.out.println(readTrain.get(0).get(0));

    userMenu();

    }

    public static void KNN( )
    {

        double a=0;

        shortestDistances = new ArrayList<>(); // distancees<data, distance> -> order pick 1st k items

        List<Sample> distances = new ArrayList<>();
        String predicted;

     //   System.out.println(readTest.get(0).get(1));
        for (int i = 0; i <readTest.size() ; i++)//readtest.len
        {
            for (List<String> strings : readTrain)
            {
               // System.out.println(i+"=i");

                for (int j = 0; j < readTest.get(i).size() - 1; j++)
                { // sqrt[(a1-x)^2 +(a2-x)^2+(a3-x)^2 +(a4-x)^2 ]


                    a += distance(Double.parseDouble(strings.get(j)), Double.parseDouble(readTest.get(i).get(j)));
                }
            //    System.out.println(a+"=a");
                distances.add(new Sample(strings.get(strings.size()-1), Math.sqrt(a)));
                a = 0;
            }
            Collections.sort(distances);

            for (int e = 0; e < k ; e++)
            {
                shortestDistances.add(distances.get(e));
            }
         //   System.out.println(shortestDistances);

            predicted = mostFrequentSample(shortestDistances);

            if (Objects.equals(predicted, readTest.get(i).get(readTest.get(i).size() - 1)))
            {
                correctPredictions++;
                System.out.println(readTest.get(i)+" predicted: "+predicted);//predicted sample
            }
            else
            {
                System.out.println(readTest.get(i) + " predicted: " + predicted+" FAIL");//predicted sample
            }
            distances.clear();
            shortestDistances.clear();
        }
        System.out.println("Accuracy: "+(correctPredictions/readTest.size())*100+" %");
       // System.out.println(correctPredictions);
    }

    public static double distance(double a, double x)
    {
        return Math.pow((a-x),2);
    }

    public static void readTrainFile(String path) throws IOException {
        readText(path, readTrain);
    }

    public static void readTestFile(String path) throws IOException {
        readText(path, readTest);
    }

    private static void readText(String path, List<List<String>> readTest) throws IOException {

            FileInputStream fstream = new FileInputStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;

            while ((strLine = br.readLine()) != null) {

                readTest.add((Arrays.asList(strLine.split(","))));
            }
            fstream.close();

    }

    public static String mostFrequentSample(List<Sample> closestNeigh)
    {
        Map.Entry<String, Integer> maxSample = null;
        Map<String,Integer> frequency = new HashMap<>();

        for (Sample sample : closestNeigh)
        {
            frequency.put(sample.data,0);
        }

        for (Sample sample:
             closestNeigh)
        {

            for (Map.Entry<String,Integer> entry : frequency.entrySet())
            {
                if(Objects.equals(sample.data, entry.getKey()))
                {
                    frequency.put(sample.data, entry.getValue()+1);
                }
            }
        }
        for (Map.Entry<String, Integer> entry : frequency.entrySet())
        {
            if (maxSample == null || entry.getValue().compareTo(maxSample.getValue()) > 0)
            {
                maxSample = entry;
            }
        }
        return maxSample.getKey();
    }

    public static void userMenu()
    {
        Scanner scan = new Scanner(System.in);  // Create a Scanner object
        System.out.println("... training file currently contains "+readTrain.size()+"samples of "+(readTrain.get(1).size()-1)+" sized attributes plus class name...");
        System.out.println("Choose:");
        System.out.println("1 -> choose K and run KNN from test.txt");
        System.out.println("2 -> write your own samples in form of: <4.0,4.2,5.3,(...),5.2,Name-of-sample> //without <>");
        System.out.println("3 -> quit :(");

        String choice = scan.nextLine();  // Read user input
        String userInput = null;

        switch (choice.trim())
        {
            case "1":
            {
                System.out.println("Option 1");
                System.out.println("Choose K: ");
               userInput = scan.nextLine();
                    setK(Integer.parseInt(userInput));
               KNN();
                break;
            }
            case "2":
            {
                System.out.println("Option 2");
                readTest.clear();

                System.out.println("Choose K: ");
                userInput = scan.nextLine();
                setK(Integer.parseInt(userInput));

                System.out.println("Write -> done, when done");
                System.out.println("Write -> quit, when tired");
                while(true)
                {

                    userInput = scan.next();
                    if(Objects.equals(userInput, "quit"))
                    {
                        System.out.println("Bye!");
                        break;
                    }
                    else if(Objects.equals(userInput,"done"))
                    {
                        KNN();
                        break;
                    }
                    else
                    {
                        if(checkInputSize(userInput))
                        {
                            readTest.add(Arrays.asList(userInput.split(",")));
                            System.out.println("More?");
                         //   System.out.println("readT"+ readTest.get(0).get(0));
                        }
                        else
                        {
                            System.out.println("Wrong number of attributes");
                        }
                    }
                    System.out.println("Your samples: "+readTest);
                }
                break;
            }
            case "3":
            {
                System.out.println("Bye!");
                break;
            }
            default:
            {
                System.out.println("Wrong input try again...");
            }
        }
    }

    public static boolean checkInputSize(String input)
    {
       // System.out.println(input.split(",").length == readTrain.get(0).size());
        return input.split(",").length == readTrain.get(0).size();
    }



}
