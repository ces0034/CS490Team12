public class Process {
    private int arrivalTime;
    private String id;
    private int serviceTime;
    private int priority;

    public Process(String aTime, String ID, String sTime, String prior){ // Constructs Process data structure

        arrivalTime = Integer.parseInt(aTime.trim());
        id = ID;
        serviceTime = Integer.parseInt(sTime.trim());
        priority = Integer.parseInt(prior.trim());
    }

    public int getArrivalTime(){ // returns arrival time
        return arrivalTime;
    }

    public String getId(){ // returns id
        return id;
    }

    public int getServiceTime(){ // returns service time
        return serviceTime;
    }

    public int getPriority(){ // returns priority
        return priority;
    }
}