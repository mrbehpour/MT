package ir.saa.android.mt.pojos;

import android.arch.lifecycle.MutableLiveData;

/**
 * Created by h.eskandari on 7/17/2018.
 */

public class Pojo1  {
    public String fullname;
    public Integer age;
    public String description;

    public Pojo1(String fullname,Integer age,String description){
        this.fullname =fullname;
        this.age = age;
        this.description =description;
    }
}
