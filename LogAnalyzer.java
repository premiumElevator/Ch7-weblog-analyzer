/**
 * Read web server data and analyse hourly access patterns.
 *
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    {
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[32];
        monthCounts = new int[12];
        // Create the reader to obtain the data.
        reader = new LogfileReader();
    }

    public LogAnalyzer(String webLog)
    {

      hourCounts = new int[24];
      dayCounts = new int[32];
      monthCounts = new int[12];

      reader = new LogfileReader(webLog);
    }

    public int numberOfAccesses()
    {
        int total = 0;

        for(int hour = 0; hour < hourCounts.length; hour++)
        {
          total = total + hourCounts[hour];
        }

        return total;
    }

    public int busiestTwoHours()
    {
      int index = 0;

      for(int hour = 0; hour <hourCounts.length-1; hour++)
      {

        if((hourCounts[hour]+hourCounts[hour + 1]) > ((hourCounts[index]+hourCounts[index + 1])))
        {
          index = hour;
        }

      }
      return hourCounts[index];
    }

    public int busiestDay()
    {
      int index = 0;

      for(int day = 0; day < dayCounts.length; day++)
      {
        if(dayCounts[day] > dayCounts[index])
        {
          index = day;
        }
      }
      return index;
    }

    public int busiestHour()
    {
      int index = 0;

      for(int hour = 0; hour < hourCounts.length; hour++)
      {
        if(hourCounts[hour] > hourCounts[index])
        {
          index = hour;
        }
      }
      return index;
    }

    public int quietestHour()
    {
      int index = busiestHour();

      for(int hour = 0; hour < hourCounts.length; hour++)
      {
        if(hourCounts[index] > hourCounts[hour])
        {
          index = hour;
        }
      }
      return index;
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
    }


    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    public void printDailyCounts()
    {
        System.out.println("Day: Count");
        for(int day = 1; day < dayCounts.length; day++) {
            System.out.println(day + ": " + dayCounts[day]);
        }
    }

    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
