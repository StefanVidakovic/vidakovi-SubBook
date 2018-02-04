package vidakovi.vidakovi_subbook;

import java.util.ArrayList;

import vidakovi.vidakovi_subbook.Subscription;


class SubscriptionList {
    public ArrayList<Subscription> subList;

    public SubscriptionList(){
        this.subList = new ArrayList<Subscription>();
    }

    public Double getTotalMonthlyCharges(){
        Double total = new Double(0.0);
        for(Subscription sub : this.subList){
            total = total.doubleValue()+sub.getMonthlyCharge().doubleValue();
        }
        return total;
    }

    public void add(Subscription sub){
        this.subList.add(sub);
    }
}
