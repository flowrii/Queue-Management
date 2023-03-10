public class Client implements Comparable<Client>{
    private int id;
    private int tArrival;
    private int tService;

    public Client(int id, int tArrival, int tService) {
        this.id = id;
        this.tArrival = tArrival;
        this.tService = tService;
    }

    public Client(){

    }

    public String toString(){
        String s="(" + this.getId() + ", " + this.gettArrival() + ", " + this.gettService() + "); ";
        return s;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int gettArrival() {
        return tArrival;
    }

    public void settArrival(int tArrival) {
        this.tArrival = tArrival;
    }

    public int gettService() {
        return tService;
    }

    public void settService(int tService) {
        this.tService = tService;
    }

    @Override
    public int compareTo(Client o) {
        if(this.tArrival>o.tArrival)
            return 1;
        else if(this.tArrival==o.tArrival)
            return 0;
        else
            return -1;
    }
}
