package com.example.qriffic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentUserSearchedProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentUserSearchedProfile extends Fragment {

    DBAccessor dba = new DBAccessor();

    private ListView profileListView;
    private ArrayList<QRCode> dataList;
    private QRCodeAdapter qrAdapter;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<QRCode> qrList;

    //initialize variables
    private String username;


    public FragmentUserSearchedProfile() {
        // Required empty public constructor
    }
//
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment UserProfile.
     */
    public static FragmentUserSearchedProfile newInstance() {
        FragmentUserSearchedProfile fragment = new FragmentUserSearchedProfile();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        Bundle bundle = getArguments();


        //initialize the text views
        TextView tvUsername = view.findViewById(R.id.user_name);
        TextView tvEmail = view.findViewById(R.id.profile_email);
        TextView tvPhoneNum = view.findViewById(R.id.profile_phone);
        TextView noScanned = view.findViewById(R.id.user_scanned);
        TextView totalScore = view.findViewById(R.id.user_score);
        TextView highScore = view.findViewById(R.id.topQRName2);
        TextView lowScore = view.findViewById(R.id.topQRName3);
        TextView topQRName = view.findViewById(R.id.topQRName);
        TextView botQRName = view.findViewById(R.id.botQRName);

        //initialize an array list of QR codes
        qrList = new ArrayList<>();

        username = bundle.getString("username").replaceAll("[^a-zA-Z0-9!]", "");

        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.addListener(new fetchListener() {
            @Override
            public void onFetchComplete() {
                qrList = (ArrayList<QRCode>) playerProfile.getCaptured();

                ArrayList<QRCode> QRAdapterList = new ArrayList<QRCode>();

                //find total score
                long totalScoreInt = 0;

                //create array for lowest score
                long[] lowScoreArray = new long[qrList.size()];
                String[] NameArray = new String[qrList.size()];

                //make a dictionary for the scores and names
                HashMap<String, Integer> NameMap = new HashMap<>();

                for (int i = 0; i < qrList.size(); i++) {
                    NameMap.put(qrList.get(i).getName(), qrList.get(i).getScore());
                    totalScoreInt += qrList.get(i).getScore();
                    lowScoreArray[i] = qrList.get(i).getScore();
                    NameArray[i] = qrList.get(i).getName();

                }

                totalScore.setText(String.valueOf(totalScoreInt));

                dataList = new ArrayList<QRCode>();
                qrAdapter = new QRCodeAdapter(getContext(), dataList);

                for (int i = 0; i < qrList.size(); i++) {
                    dataList.add(new QRCode(NameArray[i], lowScoreArray[i]));
                }

                qrAdapter.notifyDataSetChanged();
                System.out.println("dataList"+dataList);
                System.out.println("qrAdapter"+qrAdapter);

                profileListView = view.findViewById(R.id.profileList);
                profileListView.setAdapter(qrAdapter);

                if (qrList.size() > 0) {
                    lowScore.setText(String.valueOf(playerProfile.getLowScore()));
                    highScore.setText(String.valueOf(playerProfile.getHighScore()));
                    totalScore.setText(String.valueOf(totalScoreInt));

                    //set images for highest and lowest score
                    String highurl = "https://www.gravatar.com/avatar/" + playerProfile.getHighScore() + "?s=55&d=identicon&r=PG%22";
                    Glide.with(getContext())
                        .load(highurl)
                        .centerCrop()
                        .error(R.drawable.ic_launcher_background)
                        .into((ImageView) view.findViewById(R.id.imageTop));

                    String lowurl = "https://www.gravatar.com/avatar/" + playerProfile.getLowScore() + "?s=55&d=identicon&r=PG%22";
                    Glide.with(getContext())
                        .load(lowurl)
                        .centerCrop()
                        .error(R.drawable.ic_launcher_background)
                        .into((ImageView) view.findViewById(R.id.imageBot));
                } else {
                    playerProfile.setLowScore(-1);
                    playerProfile.setHighScore(-1);
                    lowScore.setText("N/A");
                    highScore.setText("N/A");
                }

                //set name for lowest score and  highest score
                for (QRCode qrCode : qrList) {
                    if (qrCode.getScore() == playerProfile.getHighScore()) {
                        topQRName.setText(qrCode.getName());
                    }

                    if (qrCode.getScore() == playerProfile.getLowScore()) {
                        botQRName.setText(qrCode.getName());
                    }

                }

                //set the text views to the user data
                tvUsername.setText(playerProfile.getUsername());
                tvEmail.setText(playerProfile.getEmail());
                tvPhoneNum.setText(playerProfile.getPhoneNum());

                //find number of QR codes in the list
                Integer numQRCodes = qrList.size();
                noScanned.setText(numQRCodes.toString());

            }

            @Override
            public void onFetchFailure() {

            }
        });

        //navigate to QR detail page by clicking on top, bot, or listview
        ImageView imageTop = view.findViewById(R.id.imageTop);
        ImageView imageBot = view.findViewById(R.id.imageBot);

        imageTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("QRID", topQRName.getText().toString());
                Navigation.findNavController(v).navigate(R.id.action_fragmentUserSearchedProfile_to_nav_QRDetail,bundle);
            }
        });

        imageBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("QRID", botQRName.getText().toString());
                Navigation.findNavController(v).navigate(R.id.action_fragmentUserSearchedProfile_to_nav_QRDetail,bundle);
            }
        });

        profileListView = view.findViewById(R.id.profileList);
        profileListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                QRCode qrCode = qrList.get(position);
                bundle.putString("QRID", qrCode.getIdHash());
                Toast.makeText(getContext(), qrCode.getIdHash(), Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigate(R.id.action_fragmentUserSearchedProfile_to_nav_QRDetail,bundle);
            }
        });
        dba.getPlayer(playerProfile, username);

        return view;
    }

}