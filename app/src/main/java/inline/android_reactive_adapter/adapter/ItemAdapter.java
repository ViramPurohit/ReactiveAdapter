package inline.android_reactive_adapter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import inline.android_reactive_adapter.MainActivity;
import inline.android_reactive_adapter.R;
import inline.android_reactive_adapter.model.User;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Viram Purohit on 10/10/2017.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.View_Holder> implements Observer<ArrayList<User>> {

    ArrayList<User> arrayList;
    LayoutInflater layoutInflater;
    Context context;

    private Disposable disposable;
    private String LOG_TAG = "ItemAdapter";

    public  interface ItemAddListener{
        public void onItemAdded();
    }

    ItemAddListener itemAddListener;
    public ItemAdapter(Context context, ItemAddListener addListener) {
        itemAddListener = addListener;
        arrayList = new ArrayList<>();
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ItemAdapter.View_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  layoutInflater.inflate(R.layout.list_item,parent,false);

        return new View_Holder(view);
    }

    @Override
    public void onBindViewHolder(View_Holder holder, int position) {

        holder.FName.setText(arrayList.get(position).fname);
        holder.LName.setText(arrayList.get(position).lname);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
        itemAddListener.onItemAdded();
    }

    @Override
    public void onNext(ArrayList<User> value) {
        arrayList = new ArrayList<>();
        arrayList = value;

        Log.e(LOG_TAG, "onNext "+arrayList.size());
    }

    @Override
    public void onError(Throwable e) {
        Log.e(LOG_TAG, e.getMessage());
        Log.e(LOG_TAG, "onError ");

    }

    @Override
    public void onComplete() {
        Log.e(LOG_TAG, "onComplete ");
        this.notifyDataSetChanged();
    }

    public class View_Holder extends RecyclerView.ViewHolder{

       public TextView FName,LName;
        public View_Holder(View itemView) {
            super(itemView);
            FName = itemView.findViewById(R.id.fname);
            LName = itemView.findViewById(R.id.lname);
        }
    }
}
