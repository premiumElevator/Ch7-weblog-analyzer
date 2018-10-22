/**
 * Read web server data and analyse hourly access patterns.
 *
 * @author Peter Basily
 * @version    2018.10.22
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
      dayCounts = new int[31];
      monthCounts = new int[13];
      yearCounts = new int[5];
      // Create the reader to obtain the data.
      reader = new LogfileReader();
   }

  /**
  *@param webLog the name of the file in "filename.txt" format
  *
  */
    public LogAnalyzer(String webLog)
    {

      // Create the array object to hold the hourly, daily, monthly, and yearly
      // access counts.


      hourCounts = new int[24];
      dayCounts = new int[29];
      monthCounts = new int[13];
      yearCounts = new int[5];

      reader = new LogfileReader(webLog);
    }

    /**
    *@return numberOfAccesses returns total number of accesses using the monthCounts
    *
    */

    public int numberOfAccesses()
    {
        int total = 0;

        for(int month = 1; month < monthCounts.length; month++)
        {
          total = total + monthCounts[month];
        }

        return total;
    }
    /**
    *totalAccessesPerMonth() method prints the month and the amount of visits
    *used to analyze both specific year and total log
    */
    public void totalAccessesPerMonth()
    {

       System.out.println("Month: Count");
       for(int month = 1; month < monthCounts.length; month++) {
           System.out.println(month + ": " + monthCounts[month]);
       }

    }

    /**
    *@return busiestTwoHours() returns the total number of hits in the first of the 2 busiest hours
    *as per book instructions.
    */

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

    /**
    @return busiestHour() returns an int value of the busiest hour used for both specific year or in general.
    */
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

    /**
    @return quietestHour() returns an int value of the quietest hour used for both specific year or in general.
    */
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
    @return busiestDay() returns an int value of the busiest day used for both specific year or general.
    */
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

   /**
   @return quietestDay() returns an int value of the quietest day used for both specific year or general.
   */

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
    @return busiestMonth() returns an int value of the busiest month used for both specific year or general.
    */
    public int busiestMonth()
    {
       int index = 1;

       for(int month = 1; month < monthCounts.length; month++)
       {
           if(monthCounts[month] > monthCounts[index])
           {
               index = month;
           }
       }
      return index;
    }
    /**
    @return quietestMonth() returns an int value of the quietest month used for both specific year or general.
    */
    public int quietestMonth()
    {
       int index = busiestMonth();

       for(int month = 1; month < monthCounts.length; month++)
       {
          if(monthCounts[index] > monthCounts[month])
          {
             index = month;
          }
       }
       return index;
    }


    /**
    * Analyze the hourly access data from the log file.
    *used only for total log file, not specific year.
     */

    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
        reader.reset();
    }

    /**
    *Analyzes the daily access from a log file.
    *used only for total log file, not specific year.
    */
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dayCounts[day]++;
        }
        reader.reset();
    }

  /**
  * Analyzes the monthly access from a log file.
  *used only for total log file, not specific year.
  */
   public void analyzeMonthlyData()
   {
      while(reader.hasNext()) {
           LogEntry entry = reader.next();
           int month = entry.getMonth();
           monthCounts[month]++;
      }
      reader.reset();
   }

   /**
   *Analyzes specific year.
   */
   public void analyzeSpecificYear(int year)
   {

     while(reader.hasNext())
     {
       LogEntry entry = reader.next();

       while(year == entry.getYear())
       {
         int month = entry.getMonth();
         monthCounts[month]++;
         int day = entry.getDay();
         dayCounts[day]++;
         int hour = entry.getHour();
         hourCounts[hour]++;

         if(!reader.hasNext())  //required for 2018, once it reaches the end of 2018, it will break instead of advance into null.
         {
           break;
         }
         entry = reader.next();
       }

     }
     reader.reset();
   }


   /**
   * Analyzes the yearly access from a log file.
   *used only for total log file, not specific year.
   */
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
   * @return the average visits per month of a specific year.
   */
   public int averagePerMonthPerYear(int year)
   {
     analyzeSpecificYear(year);

     int average = numberOfAccesses()/12;

     return average;

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

    /**
     * Print the daily counts.
     * These should have been set with a prior
     * call to analyzedailyData.
     *for testing purposes
     */
    public void printDailyCounts()
    {
        System.out.println("Day: Count");
        for(int day = 1; day < dayCounts.length; day++) {
            System.out.println(day + ": " + dayCounts[day]);
        }
    }

    /**
     * Print the yearly counts.
     * These should have been set with a prior
     * call to analyzeYearlyData.
     *for testing purposes
     */
    public void printYearlyCounts()
    {
        System.out.println("year: Count");
        LogEntry entry = reader.next();
        int yearDisplay = entry.getYear();
        for(int year = 0; year < yearCounts.length; year++) {
            System.out.println(yearDisplay + ": " + yearCounts[year]);
            yearDisplay++;
        }
        reader.reset();
    }
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
