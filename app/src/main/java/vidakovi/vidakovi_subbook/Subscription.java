package vidakovi.vidakovi_subbook;

import java.io.Serializable;
import java.util.regex.*;

class Subscription implements Serializable {
    private String name;
    private String startDate;
    private Double monthlyCharge;
    private String comment;

    public Subscription(String name,String startDate,String monthlyCharge){
        this.name = name;
        this.startDate = startDate;
        this.monthlyCharge = Double.valueOf(monthlyCharge);
    }

    public Subscription(String name,String startDate,String monthlyCharge,String comment){
        this.name = name;
        this.startDate = startDate;
        this.monthlyCharge = Double.valueOf(monthlyCharge);
        this.comment = comment;
    }

    public boolean checkName(String name){
        if(name.length()>20){
            return false;
        }
        return true;
    }

    public boolean checkStartDate(String startDate){
        String regex = "[0-9]{4}"+"-"+"[0-9]{2}"+"-"+"[0-9]{1,2}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(startDate);
        if(m.matches()){
            return true;
        }
        return false;
    }

    public boolean checkMonthlyCharge(String monthlyCharge){
        String regex = "[0-9]+"+"\\."+"[0-9]{2}";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(monthlyCharge);
        if(m.matches()){
            return true;
        }
        return false;
    }
    public String getName(){
        return this.name;
    }

    public String getStartDate(){
        return this.startDate;
    }

    public Double getMonthlyCharge(){
        return this.monthlyCharge;
    }

    public String getComment(){
        return this.comment;
    }

    public void setName(String name){
        if(this.checkName(name)){
            this.name = name;
        }
    }

    public void setStartDate(String startDate){
        if(this.checkStartDate(startDate)){
            this.startDate = startDate;
        }
    }

    public void setMonthlyCharge(String monthlyCharge){
        if(this.checkMonthlyCharge(monthlyCharge)){
            this.monthlyCharge = Double.valueOf(monthlyCharge);
        }
    }

    public void setComment(String comment){
        this.comment = comment;
    }
}
