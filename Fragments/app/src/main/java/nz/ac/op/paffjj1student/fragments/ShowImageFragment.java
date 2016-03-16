package nz.ac.op.paffjj1student.fragments;

//imports for fragment
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Fragment;


public class ShowImageFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View fragmentView = inflater.inflate(R.layout.image_fragment, container, false);
        return fragmentView;

    }



}
