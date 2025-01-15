import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
public class Matcher {

    public static void main(String [] args){


        // Test all even numbers up until 128
        // The heap runs out of memory when we try 
        // to check passed 20
        for (int i=4; i < 20; i+=2){
            int success = 0;
            int fail = 0;

            int success2 = 0;
            int fail2 = 0;
            
            //Run 10 unique tests
            for (int j=0; j < 20; j++){
                Debater [] maxPaired = createTeam(i); 
                Debater [] solution = duplicateTeam(maxPaired);
                Debater [] maxPosition = duplicateTeam(maxPaired);

                maxPaired = maxPaired(maxPaired);
                maxPosition = maxPosition(maxPosition);

                //Solution runs out of heap space at 20
                solution = solution (solution);

                if ((Math.round(Pwin(maxPaired) * 1000.0) / 1000.0) == (Math.round(Pwin(solution) * 1000.0) / 1000.0)){
                    success++;
                }
                else{
                    fail++;
                }

                if ((Math.round(Pwin(maxPosition) * 1000.0) / 1000.0) == (Math.round(Pwin(solution) * 1000.0) / 1000.0)){
                    success2++;
                }
                else{
                    fail2++;
                }
            }

            System.out.println("TEAM SIZE: " + i);
            System.out.println("Max Paired Successes Rate: " + (success/20.0)*100 + "%");
            System.out.println("Max Position Success Rate: " + (success2/20.0)*100 + "%\n");

        }        

    }

    public static Debater [] duplicateTeam (Debater [] debaters){
        Debater [] result = new Debater[debaters.length];
        for (int i=0; i < debaters.length; i++){
            result[i] = new Debater (debaters[i].getPmLo(), debaters[i].getPmMo(), debaters[i].getMgMo(), debaters[i].getMgLo());
            
        }

        return result;
        
    }

    public static Debater [] createTeam (int size){

        Debater [] debaters = new Debater [size];
        for (int i= 0; i < size; i++){
            debaters[i] = new Debater();

        }

        return debaters;

    }

    //Generates all possible pairings and then calculates the highest possible Pwin for each 
    //set of pairings
    public static Debater [] solution(Debater [] debaters){
        int n = debaters.length;
        int [] arr = new int [n];

        //Set up code for pairing computations
        for (int i=0; i < n; i++){
            arr[i] = i;
        }
        List <Integer> list = IntStream.of(arr).boxed().collect(Collectors.toList());
        Set<Integer> set = new LinkedHashSet<Integer>(list);
        ArrayList<List<List<Integer>>> results = new ArrayList<List<List<Integer>>>();
        compute(set, new ArrayList<List<Integer>>(), results);
        float [] Pwins = new float [results.size()];
        //System.out.println(results.size());

        int count = 0;
        for (List<List<Integer>> result : results)
        {
            //System.out.println(result);
            for (int i=0; i < n/2; i++){
                float max = 0;
                Debater p1 = debaters[result.get(i).get(0)];
                Debater p2 = debaters[result.get(i).get(1)];

                if (p1.getPmLo()*p2.getMgMo() > max){
                    max = p1.getPmLo()*p2.getMgMo();
                }

                if (p2.getPmLo()*p1.getMgMo() > max){
                    max = p2.getPmLo()*p1.getMgMo();
                }

                if (p1.getPmMo()*p2.getMgLo() > max){
                    max = p1.getPmMo()*p2.getMgLo();
                }

                if (p2.getPmMo()*p1.getMgLo() > max){
                    max = p2.getPmMo()*p1.getMgLo() ;
                }

                Pwins[count]+=max;

            }
            count++;
        }



        //Find the highest Pwin from all the pairings
        float max = Pwins[0];
        int index = 0;
        for (int i=0; i < Pwins.length; i++){
            if (Pwins[i] > max){
                max = Pwins[i];
                index = i;
            }
        }
        //System.out.println("best pairing: " + max);

        Debater [] partners = new Debater [n/2];
        for (int i=0; i < results.get(index).size(); i++){
            max = 0;
            Debater p1 = debaters[results.get(index).get(i).get(0)];
            Debater p2 =  debaters[results.get(index).get(i).get(1)];
            int pos1 = -1;
            int pos2 = -2;

            if (p1.getPmLo()*p2.getMgMo() > max){
                pos1 = 0;
                pos2 = 2;
                max = p1.getPmLo()*p2.getMgMo();
            }

            if (p2.getPmLo()*p1.getMgMo() > max){
                pos2 = 0;
                pos1 = 2;
                max = p2.getPmLo()*p1.getMgMo();
            }

            if (p1.getPmMo()*p2.getMgLo() > max){
                pos1 = 1;
                pos2 = 3;
                max = p1.getPmMo()*p2.getMgLo();
            }

            if (p2.getPmMo()*p1.getMgLo() > max){
                pos2 = 1;
                pos1 = 3;
                max = p2.getPmMo()*p1.getMgLo() ;
            }



            p1.setPartner(p2);
            p1.setPosition(pos1);
            p2.setPartner(p1);
            p2.setPosition(pos2);
            partners[i] = p1;
            
        }
        return partners;
    }

    //Compute all possible sets of pairs
    //https://stackoverflow.com/questions/21761749/all-combinations-of-pairs-within-one-set
    //Generates the n!/[2^(n/2)*(n/2)!] different pairings
    private static void compute(Set<Integer> set, List<List<Integer>> currentResults, List<List<List<Integer>>> results)
    {
        if (set.size() < 2)
        {
            results.add(new ArrayList<List<Integer>>(currentResults));
            return;
        }
        List<Integer> list = new ArrayList<Integer>(set);
        Integer first = list.remove(0);
        for (int i=0; i<list.size(); i++)
        {
            Integer second = list.get(i);
            Set<Integer> nextSet = new LinkedHashSet<Integer>(list);
            nextSet.remove(second);

            List<Integer> pair = Arrays.asList(first, second);
            currentResults.add(pair);
            compute(nextSet, currentResults, results);
            currentResults.remove(pair);
        }
    }

    // Generates all possible pairings 
    // Finds the pairing with the highest Pwin and pairs those two debaters
    // Removes the paired debaters from the set
    public static Debater [] maxPaired(Debater [] debaters){

        int n = debaters.length;
        Debater [][] pairs = new Debater [(n*(n-1)/2)][2];
        Debater [] paired = new Debater [n/2];
        int count = 0;

        //Create all possible pairings
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                //A debater cannot be paird to themselves
                if (i != j){
                    pairs [count][0] = debaters[i];
                    pairs [count][1] = debaters[j];
                    count++;

                }
            }
        }

        int bestPair = 0; 
        float bestPwin = 0;
        int pos0 = -1;
        int pos1 = -1;

        //n/2 operations
        for (int k=0; k < paired.length; k++){
            bestPwin = 0;
            pos0 = -1;
            pos1 = -1;

            //n^2 operations
            for (int i=0; i < pairs.length; i++){

                if (pairs[i] == null){
                    continue;
                }

                if (pairs[i][0].getPartner() != null || pairs[i][1].getPartner() != null){
                    continue;
                }
                
                if (pairs[i][0].getPmLo()*pairs[i][1].getMgMo() > bestPwin){
                    bestPwin = pairs[i][0].getPmLo()*pairs[i][1].getMgMo();
                    bestPair = i;
                    pos0 = 0;
                    pos1 = 2;
                }

                if (pairs[i][0].getMgMo()*pairs[i][1].getPmLo() > bestPwin){
                    bestPwin = pairs[i][0].getMgMo()*pairs[i][1].getPmLo();
                    bestPair = i;
                    pos0 = 2;
                    pos1 = 0;
                }

                if (pairs[i][0].getPmMo()*pairs[i][1].getMgLo() > bestPwin){
                    bestPwin = pairs[i][0].getPmMo()*pairs[i][1].getMgLo();
                    bestPair = i;
                    pos0 = 1;
                    pos1 = 3;
                }

                if (pairs[i][0].getMgLo()*pairs[i][1].getPmMo() > bestPwin){
                    bestPwin = pairs[i][0].getMgLo()*pairs[i][1].getPmMo();
                    bestPair = i;
                    pos0 = 3;
                    pos1 = 1;
                }

                //n^2 operations
                for (int j = 0; j < pairs.length; j++){
                    
                    //If it is matched just skip it
                    if (pairs[j] == null ){
                        continue;
                    }

                    if (pairs[j][0].getPartner() != null || pairs[j][1].getPartner() != null){
                        continue;
                    }
                    

                    if (pairs[j][0].getPmLo()*pairs[j][1].getMgMo() > bestPwin){
                        bestPwin = pairs[j][0].getPmLo()*pairs[j][1].getMgMo();
                        bestPair = j;
                        pos0 = 0;
                        pos1 = 2;
                    }
        
                    if (pairs[j][0].getMgMo()*pairs[j][1].getPmLo() > bestPwin){
                        bestPwin = pairs[j][0].getMgMo()*pairs[j][1].getPmLo();
                        bestPair = j;
                        pos0 = 2;
                        pos1 = 0;
                    }
        
                    if (pairs[j][0].getPmMo()*pairs[i][1].getMgLo() > bestPwin){
                        bestPwin = pairs[j][0].getPmMo()*pairs[j][1].getMgLo();
                        bestPair = j;
                        pos0 = 1;
                        pos1 = 3;
                    }
        
                    if (pairs[j][0].getMgLo()*pairs[j][1].getPmMo() > bestPwin){
                        bestPwin = pairs[j][0].getMgLo()*pairs[j][1].getPmMo();
                        bestPair = j;
                        pos0 = 3;
                        pos1 = 1;
                    }
                }
            }

            //Set the debaters as partners
            pairs[bestPair][0].setPartner(pairs[bestPair][1]);
            pairs[bestPair][0].setPosition(pos0);

            pairs[bestPair][1].setPartner(pairs[bestPair][0]);
            pairs[bestPair][1].setPosition(pos1);

            //Add the debaters to the set of paired debaters
            paired[k] = pairs[bestPair][0];

            //Remove the combination
            pairs[bestPair] = null;
        }

        //O(n/2 * n^2 * n^2) = O(n^5)
        return paired;

    }

    //Finds the max Pwin of any position in the set and matches it with its max compliment
    public static Debater [] maxPosition(Debater [] debaters){

        int n = debaters.length;
        Debater [] paired = new Debater [n/2];
        int count = 0;

        //Need to do two passes to make sure a set doesnt get missed
        for (int k=0; k < 2; k++){
            for (int i=0; i < n; i++){

                if (debaters[i] == null){
                    continue; 
                }

                float maxPos = 0;
                int maxD = i;
                int pos = -1;

                //Find the strongest position in the current debater if it exists
                if (debaters[i].getPmLo() > maxPos){
                    maxPos = debaters[i].getPmLo();
                    pos = 0;

                }

                if (debaters[i].getPmMo() > maxPos){ 
                    maxPos = debaters[i].getPmMo();
                    pos = 1;
                    
                }

                if (debaters[i].getMgMo() > maxPos){
                    maxPos = debaters[i].getMgMo();
                    pos = 2;
                    
                }

                if (debaters[i].getMgLo() > maxPos){ 
                    maxPos = debaters[i].getMgLo();
                    pos = 3; 
                }

                //Compare with every other avialable debater
                for (int j = 0; j < n; j++){
                    if (debaters[j] == null){
                        continue; 
                    }

                    //Find the absolute strongest position of the set
                    if (debaters[j].getPmLo() > maxPos){
                        maxPos = debaters[j].getPmLo();
                        maxD = j;
                        pos = 0;

                    }

                    if (debaters[j].getPmMo() > maxPos){ 
                        maxPos = debaters[j].getPmMo();
                        maxD = j;
                        pos = 1;
                        
                    }

                    if (debaters[j].getMgMo() > maxPos){
                        maxPos = debaters[j].getMgMo();
                        maxD = j;
                        pos = 2;
                        
                    }

                    if (debaters[j].getMgLo() > maxPos){ 
                        maxPos = debaters[j].getMgLo();
                        maxD = j;
                        pos = 3; 
                    }

                }
                
                //Helper function that finds and sets the compliment of the i-th strongest debater
                findSetCompliment(pos, maxD, debaters, paired, count);
                count++;
            }
        }

        return paired; 

    }
     
    public static void findSetCompliment (int pos, int maxD, Debater [] debaters, Debater [] paired, int count){
                int n  = debaters.length;
                int complimentD = 0;
                float maxCompliment = 0;
                int complimentPos = -1;

                for (int j = 0; j < n; j++){
                    //Skip if the current sell is empty
                    if (debaters[j] == null){
                        continue;
                    }

                    //A debater cannot be paired to themselves
                    if (j != maxD){
                        
                        //Check if the current debater is a strongger compliment
                        if (pos == 0 && debaters[j].getMgMo() > maxCompliment){
                            complimentD = j;
                            maxCompliment = debaters[j].getMgMo();
                            complimentPos = 2;
                        }

                        if (pos == 1 && debaters[j].getMgLo() > maxCompliment){
                            complimentD = j;
                            maxCompliment = debaters[j].getMgLo();
                            complimentPos = 3;
                        }

                        if (pos == 2 && debaters[j].getPmLo() > maxCompliment){
                            complimentD = j;
                            maxCompliment = debaters[j].getPmLo();
                            complimentPos = 0;
                        }

                        if (pos == 3 && debaters[j].getPmMo() > maxCompliment){
                            complimentD = j;
                            maxCompliment = debaters[j].getPmMo();
                            complimentPos = 1;
                        }
                        
                    }

                }

                //Set the debaters as partners
                debaters[maxD].setPartner(debaters[complimentD]);
                debaters[maxD].setPosition(pos);
                
                debaters[complimentD].setPartner(debaters[maxD]);
                debaters[complimentD].setPosition(complimentPos);;

                //Add the debaters to the set of paired debaters
                paired[count] = debaters[maxD];

                //Remove from the set
                debaters[maxD] = null;
                debaters[complimentD] = null; 

                return;

    }

    public static float Pwin (Debater [] debaters){

        float total = 0;
        for (int i=0; i < debaters.length; i++){
            total+=debaters[i].getPWin();
        }

        return total;
    }
    
}
