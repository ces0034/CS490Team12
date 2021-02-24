public class Process {
    private int arrivalTime;
    private String id;
    private int serviceTime;
    private int priority;

    public Process(String aTime, String ID, String sTime, String prior){

        arrivalTime = Integer.parseInt(aTime.trim());
        id = ID;
        serviceTime = Integer.parseInt(sTime.trim());
        priority = Integer.parseInt(prior.trim());
    }

    public int getArrivalTime() {
        return arrivalTime;
    }
}

