import java.util.*;

public class Debater implements Comparable<Debater>{
    
    // private float PmLo; = 0
    // private float PmMo; = 1
    // private float MgMo; = 2
    // private float MgLo; = 3

    // key 
    // PmLo + MgMo = 0 + 2 = 2 + 0
    // PmMo + MgLo = 1 + 3 = 3 + 1

    private float [] positions = new float [4];
    private Debater partner; 
    private int position;

    //Generates the probabilty of winning in a particular speaker position 
    //for the current debater
    public Debater (){

        int sum = 0;
        
        Random r = new Random();
        for (int i=0; i < 3; i++){
            positions[i] = r.nextInt((100 - sum) / (3 - i)) + 1;
            sum+= positions[i];

        }

        positions[3] = 100-sum;

        for (int i=0; i < 4; i++){
            positions[i] = positions[i]/100;
        }

    }

    // private float PmLo; = 0
    // private float PmMo; = 1
    // private float MgMo; = 2
    // private float MgLo; = 3

    public Debater (float PmLo, float PmMo, float MgMo, float MgLo){
        positions[0] = PmLo;
        positions[1] = PmMo;
        positions[2] = MgMo;
        positions[3] = MgLo; 
    }

    public float getPmLo(){
        return positions[0];
    }

    public float getPmMo(){
        return positions[1];
    }

    public float getMgMo(){
        return positions[2];
    }

    public float getMgLo(){
        return positions[3];
    }

    public float getData(int pos){
        return positions[pos];
    }

    public void setPartner (Debater partner){
        this.partner = partner;
    }

    public Debater getPartner (){
        return this.partner;
    }

    public float [] getPositions (){
        return positions;
    }

    public void setPosition(int position){
        if (position == -1){
            System.out.println("invalid position");
        }
        this.position = position; 
    }

    public int getPositon(){
        return position;

    }

    // private float PmLo; = 0
    // private float PmMo; = 1
    // private float MgMo; = 2
    // private float MgLo; = 3

    public float getPWin(){

        //Make sure that a partner and positon are assigned before
        //calculating the Pwin of a team
        if (partner == null || position == -1){
            return 0;

        }

        if (position == 0){
            return positions[0] * partner.getMgMo();
            
        }

        if (position == 1){

            return positions[1] * partner.getMgLo();
            
        }

        if (position == 2){
            return positions[2] * partner.getPmLo();
            
        }

        if (position == 3){
            return positions[3] * partner.getPmMo();
            
        }

        return 0;

    }

    @Override
    public int compareTo(Debater o) {

        return (int) (this.positions[this.position] - o.getData(this.position));
       
    }

    public String toString (){
        if (this.partner != null){
            return "PARTNERS: \n " + Arrays.toString(positions) + " " + position + "\n" + Arrays.toString(partner.getPositions()) + " " + partner.getPositon() + "\n"; 
        }
        return Arrays.toString(positions); 
    }
}