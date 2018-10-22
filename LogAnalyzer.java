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
    private int[] yearCounts;
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
      dayCounts = new int[29];
      monthCounts = new int[13];
      yearCounts = new int[5];
      // Create the reader to obtain the data.
      reader = new LogfileReader();
   }

    public LogAnalyzer(String webLog)
    {

      hourCounts = new int[24];
      dayCounts = new int[29];
      monthCounts = new int[13];
      yearCounts = new int[5];

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

    public void totalAccessesPerMonth()
    {

       System.out.println("Month: Count");
       for(int month = 1; month < monthCounts.length; month++) {
           System.out.println(month + ": " + monthCounts[month]);
       }

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


    public int quietestDay()
    {
       int index = busiestDay();

       for(int day = 1; day < dayCounts.length; day++)
       {
        if(dayCounts[index] > dayCounts[day])
        {
           index = day;
        }
       }
       return index;
    }


    /**
     * Analyze the hourly access data from the log file.
     */
    public void test()
    {


    }
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
        reader.reset();
    }
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
        reader.reset();
    }

   public void analyzeMonthlyData()
   {
      while(reader.hasNext()) {
           LogEntry entry = reader.next();
           int month = entry.getMonth();
           monthCounts[month+1]++;
      }
      reader.reset();
   }

   public void analyzeYearlyData()
   {
      while(reader.hasNext()) {
           LogEntry entry = reader.next();
           int year = entry.getYear();
           yearCounts[year-2014]++;
      }

      reader.reset();
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

    public void printYearlyCounts()
    {
        System.out.println("year: Count");
        int yearDisplay = 2014;
        for(int year = 0; year < yearCounts.length; year++) {
            System.out.println(yearDisplay + ": " + yearCounts[year]);
            yearDisplay++;
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
