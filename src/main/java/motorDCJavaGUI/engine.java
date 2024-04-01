package motorDCJavaGUI;

public class engine {
    private boolean state;
    private String direction;
    public engine(boolean state,String direction){
        this.state=state;
        this.direction=direction;
    }
    public boolean getState(){
        return this.state;
    }
    public String getDirection(){
        return this.direction;
    }
    public void setState(boolean newState){
        this.state=newState;
    }
    public void setDirection(String newDirection){
        this.direction=newDirection;
    }
}
