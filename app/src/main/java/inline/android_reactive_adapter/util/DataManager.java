package inline.android_reactive_adapter.util;


import java.util.ArrayList;

import inline.android_reactive_adapter.model.User;
import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by Viram Purohit on 10/10/2017.
 */

public class DataManager {

    ArrayList<User> userList;
    public DataManager(ArrayList<User> users) {
        userList = users;
    }

    public Observable<ArrayList<User>> userDetails(){

        return Observable.fromArray(userList);//Emitted data from ArrayList
    }
    public Observable<ArrayList<User>> addUser(){
        User user = new User();
        user.fname = "First name "+(userList.size() +1);
        user.lname = "Last name "+(userList.size() +1);

        userList.add(user);
        return Observable.fromArray(userList);
    }
}
