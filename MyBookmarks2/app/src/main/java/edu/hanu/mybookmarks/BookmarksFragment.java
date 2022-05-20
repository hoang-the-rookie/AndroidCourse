package edu.hanu.mybookmarks;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BookmarksFragment extends Fragment {

    LinearLayout mp3, bluezone, baomoi, vnexpress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);

//        get ref
        mp3 = view.findViewById(R.id.opt_mp3);
        bluezone = view.findViewById(R.id.opt_bluezone);
        baomoi = view.findViewById(R.id.opt_baomoi);
        vnexpress = view.findViewById(R.id.opt_vnexpress);

//        handle click
        mp3.setOnClickListener(v -> redirect("mp3"));
        bluezone.setOnClickListener(v -> redirect("bluezone"));
        baomoi.setOnClickListener(v -> redirect("baomoi"));
        vnexpress.setOnClickListener(v -> redirect("vnexpress"));

        return view;
    }

    private void redirect(String type){

//        define link
        String link = "";

        switch (type){
            case "mp3":
                link = getResources().getString(R.string.mp3_link);
                break;
            case "baomoi":
                link = getResources().getString(R.string.baomoi_link);
                break;
            case "bluezone":
                link = getResources().getString(R.string.bluezone_link);
                break;
            case "vnexpress":
                link = getResources().getString(R.string.vnexpress_link);
                break;
            default: break;
        }

//      send link
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment webViewFragment = new WebViewFragment();

        Bundle args = new Bundle();
        args.putString("URL", link);
        webViewFragment.setArguments(args);

        manager.beginTransaction()
                .replace(R.id.fl_main, webViewFragment)
                .addToBackStack(null)
                .commit();

    }
}