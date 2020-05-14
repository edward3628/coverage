package continous;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Cov implements Comparable<Cov> {
    Integer eff;
    Integer term;

    public Cov(int eff, int term) {
        this.eff = eff;
        this.term= term;
    }

    public Integer getEff() {
        return eff;
    }

    @Override
    public String toString() {
        return "Cov(" + eff + "," + term + ")";
    }

    @Override
    public int compareTo(Cov o) {
        return this.getEff().compareTo(o.getEff());
    }
}

class Result {
    Cov cov;
    List<Cov> list;
    public Result(Cov cov, List<Cov> list) {
        this.cov = cov;
        this.list = list;
    }
}

public class CovCal {
    static Result getContCov(List<Cov> coverages){
        List<List<Cov>> contList = new ArrayList<>();
        List<Cov> tmpList = new ArrayList<>();

        Collections.sort(coverages);

        for (int i=0; i< coverages.size(); i++){
            List<Cov> list= new ArrayList<>();
            Cov start= coverages.get(i);
            int startVal = start.eff;
            int termVal = start.term;
            list.add(start);
            for (int j= i + 1 ; j< coverages.size(); j++) {
                Cov next = coverages.get(j);
                if ((termVal + 1) == next.eff ){
                    list.add(next);
                    termVal = next.term;
                } else if (termVal>= next.eff) {
                    if (termVal < next.term) {
                        termVal = next.term;
                    }
                    list.add(next);
                } else {
                    break;
                }
            }
            tmpList.add(i, new Cov(startVal, termVal));
            contList.add(i,list);
        }

        int index=0;
        int max = 0;
        for (int i=0; i< tmpList.size(); i++) {
            int diff = tmpList.get(i).term - tmpList.get(i).eff;
            if (diff > max) {
                max = diff;
                index = i;
            }
        }

        return new Result(tmpList.get(index), contList.get(index));
    }


    static void output(List<Cov> coveragesCase, int caseNum) {
        System.out.println("Scenario " + caseNum + ": a series of coverage data input: " + coveragesCase);
        Result aResult = getContCov(coveragesCase);
        System.out.println("Longest Continuous Coverage List: " + aResult.list);
        System.out.println("Consolidated Longest Continuous Coverage : " + aResult.cov);
    }

    public static void main(String[] arg){

        List<Cov>  coveragesCase1 = new ArrayList<>();
        coveragesCase1.add(new Cov(1, 20));
        coveragesCase1.add(new Cov(21, 30));
        coveragesCase1.add(new Cov(15, 25));
        coveragesCase1.add(new Cov(28, 40));
        coveragesCase1.add(new Cov(50, 60));
        coveragesCase1.add(new Cov(61, 200));
        output(coveragesCase1, 1);

        List<Cov>  coveragesCase2 = new ArrayList<>();
        coveragesCase2.add(new Cov(1, 20));
        coveragesCase2.add(new Cov(21, 30));
        coveragesCase2.add(new Cov(15, 25));
        coveragesCase2.add(new Cov(28, 40));
        coveragesCase2.add(new Cov(50, 60));
        coveragesCase2.add(new Cov(61, 80));
        output(coveragesCase2, 2);
    }
}
