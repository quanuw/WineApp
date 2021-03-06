package com.example.wineapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddWineFragment.OnAddWineListener} interface
 * to handle interaction events.
 * Use the {@link AddWineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddWineFragment extends Fragment {

    private static final String TAG = "AddWineFragment";

    private OnAddWineListener mListener;

    private EditText nameEditText;
    private EditText yearEditText;
    private EditText brandEditText;
    private EditText costEditText;
    private Spinner grapeSpinner;
    private RatingBar ratingRatingBar;
    private Button addWineButton;

    public AddWineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddWineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddWineFragment newInstance() {
        AddWineFragment fragment = new AddWineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Called to do initial creation of a fragment. Called after onAttach() and before
     * onCreateView(LayoutInflater, ViewGroup, Bundle).
     *
     * @params A bundle savedInstanceState.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    /**
     * Called when it's time for the fragment to draw its UI for the first time.
     *
     * @param LayoutInflater inflater that will inflate the fragment's view.
     * @param ViewGroup container for the fragment UI to attach to.
     * @param Bundle savedInstanceState if non-null will allow the fragment to save its state.
     * @return A View that is the root of the fragment's layout.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_add_wine, container, false);

        addWineButton = rootView.findViewById(R.id.addWineButton);

        nameEditText = rootView.findViewById(R.id.name);

//        yearEditText = rootView.findViewById(R.id.brand);
//        yearEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        brandEditText = rootView.findViewById(R.id.brand);

        costEditText = rootView.findViewById(R.id.cost);
        costEditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        // Drop down for grapes
        final Spinner grapeSpinner = rootView.findViewById(R.id.grapeType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.grapes_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        grapeSpinner.setPrompt("Grape Type");
        grapeSpinner.setAdapter(adapter);
        ratingRatingBar = rootView.findViewById(R.id.rating);

        //Add wine to database and refresh the wine list.
        addWineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString();

                String brand = brandEditText.getText().toString();

                double cost = 0;
                // Check empty input.
                if (costEditText.getText().toString().trim().length() != 0) {
                    cost = Double.parseDouble(costEditText.getText().toString());
                }

                String grape = grapeSpinner.getSelectedItem().toString();
                Log.d(TAG, name);
//                String year = yearEditText.getText().toString();
                double rating = ratingRatingBar.getRating();

                Wine newWine = new Wine(
                        1,
                        name,
                        brand,
                        Wine.Color.RED,
                        cost,
                        grape,
                        rating
                );

                // Check for name input.
                if (name.trim().length() == 0) {
                    Toast.makeText(
                            getContext(),
                            "Please provide wine name.",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            getContext(),
                            "ADDING WINE: " + name + " " + rating + " stars beep boop...",
                            Toast.LENGTH_SHORT
                    ).show();
                    DataManager dm = ((MainActivity) getActivity()).dm;
                    dm.insertWine(newWine);
                    dm.printWineList(dm.selectAll());
                    getFragmentManager().popBackStack();
                    /** TODO: This will reload the list from the db. Would it be better
                     *  to update the list directly? e.g. list.add(wine) adapter.notifyDataSetChanged().
                     */
                    ((MainActivity) getActivity()).loadWineListLayout();
                }
            }
        });

        return rootView;
    }

    /**
     * Checks if an EditText object only contains an empty String.
     *
     * @param EditText editText.
     *
     * @return True if EditText is an empty String. False otherwise.
     */
    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    /**
     * Called when fragment is first attached to its context. onCreate(Bundle) will be called
     * after.
     *
     * @param Context context.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAddWineListener) {
            mListener = (OnAddWineListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement WineCardInteractionListener");
        }
    }

    /**
     * Called when the fragment is no longer attached to its activity. Called after onDestroy().
     */
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Called when the fragment is no longer in use. Called after onStop() and before
     * onDetach().
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        setRetainInstance(true);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAddWineListener {
        // TODO: Update argument type and name
        void onAddWine();
    }

}
