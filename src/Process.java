import java.text.DecimalFormat;

public class Process {

    //variables
    private int arrivalTime; //the arrival time of a process
    private String id; //the id of the process
    private int serviceTime; //the service time of a process
    private int priority; //the process's priority
    private int finishTime; //the finish time
    private double TAT; //the turn around time
    private double nTAT;

    //process constructor
    public Process(String aTime, String ID, String sTime, String prior){ // Constructs duplicates.Process data structure
        arrivalTime = Integer.parseInt(aTime.trim());
        id = ID;
        serviceTime = Integer.parseInt(sTime.trim());
        priority = Integer.parseInt(prior.trim());
    }

    //getters
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
        TAT = finishTime - arrivalTime;
        nTAT = TAT/serviceTime;
    } //sets turnaround time and normalized turnaround time

    public double getnTAT(){
        DecimalFormat df = new DecimalFormat("###.###");
        nTAT = Double.parseDouble(df.format(nTAT));
        return (nTAT);
    } //gets normalized turnaround time
}