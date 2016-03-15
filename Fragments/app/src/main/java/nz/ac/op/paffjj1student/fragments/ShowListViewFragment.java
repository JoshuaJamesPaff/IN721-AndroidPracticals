package nz.ac.op.paffjj1student.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class ShowListViewFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){



        View fragmentView = inflater.inflate(R.layout.spinner_fragment, container, false);

        ListView lvCities = (ListView) fragmentView.findViewById(R.id.listViewCities);

        Resources resourceMachine = getResources();

        //creates string array and gets string using resourceID
        String[] cityArray = resourceMachine.getStringArray(R.array.cities);

        //array adapter created and sets string array to list view -- switched out "this" for getActivity() because its a fragment
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, cityArray);

        //sets adapter to listview
        lvCities.setAdapter(itemsAdapter);

        //returns fragment
        return fragmentView;
    }

}
