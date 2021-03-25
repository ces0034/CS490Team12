public class Process {
    private int arrivalTime;
    private String id;
    private int serviceTime;
    private int priority;
    private int finishTime;
    private double TAT;
    private double nTAT;

    public Process(String aTime, String ID, String sTime, String prior){ // Constructs duplicates.Process data structure

        arrivalTime = Integer.parseInt(aTime.trim());
        id = ID;
        serviceTime = Integer.parseInt(sTime.trim());
        priority = Integer.parseInt(prior.trim());
    }

    public int getArrivalTime(){ // returns arrival time
        return arrivalTime;
    } //gets arrival time

    public String getId(){ // returns id
        return id;
    } //gets id

    public int getServiceTime(){ // returns service time
        return serviceTime;
    } //gets service time

    public int getPriority(){ // returns priority
        return priority;
    } //gets priority

    public int getFinishTime(){
        return finishTime;
    } //gets finish time

    public void setFinishTime(int time){
        finishTime = time;
    } //sets finish time

    public double getTAT(){
        return TAT;
    } //gets turnaround time

    public void setTAT(int time){
        TAT = time - finishTime;
        nTAT = TAT/arrivalTime;
    } //sets turnaround time and normalized turnaround time

    public double getnTAT(){
        return nTAT;
    } //gets normalized turnaround time
}