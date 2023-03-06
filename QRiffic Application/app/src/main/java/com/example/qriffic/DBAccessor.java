package com.example.qriffic;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class DBAccessor {

    private FirebaseFirestore db;
    private CollectionReference playersColRef;
    private CollectionReference qrColRef;
    private CollectionReference mapColRef;
    private CollectionReference testColRef;

    /**
     * This is the constructor for the DBAccessor class
     */
    public DBAccessor() {
        this.db = FirebaseFirestore.getInstance();
        this.playersColRef = db.collection("Players");
        this.qrColRef = db.collection("QRs");
        this.mapColRef = db.collection("Map");
        this.testColRef = db.collection("TestCol");
    }

    /**
     * This method adds/sets a PlayerProfile object to the database
     * @param player
     * The PlayerProfile object to be added
     */
    public void setPlayer(PlayerProfile player) {
        String name = player.getUsername();
        playersColRef.document(name).set(player);
    }

    /**
     * This method returns a PlayerProfile object from the database
     * @param name
     * The username of the Player
     * @return
     * The PlayerProfile object
     */
    public PlayerProfile getPlayer(String name) {
        final PlayerProfile[] player = {new PlayerProfile()};
        playersColRef.document(name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                player[0] = documentSnapshot.toObject(PlayerProfile.class);
            }
        });
        return player[0];
    }

    // setQR is a WIP
    /**
     * This method adds a QRCode object to the database
     * @param qr
     * The QRCode object to be added
     */
    public void setQR(String name, QRCode qr) {
        qrColRef.document(name).set(qr);
    }

    /**
     * This method returns a QRCode object from the database
     * @param name
     * The username of the Player
     * @param hash
     * The hash of the QRCode
     * @return
     * The QRCode object
     */
    public QRCode getQR(String name, String hash) {
        final QRCode[] qr = {new QRCode()};
        qrColRef.document(name).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                PlayerProfile player = documentSnapshot.toObject(PlayerProfile.class);
                ArrayList<QRCode> hashes = player.getCaptured();
                for (QRCode qrCode : hashes) {
                    if (qrCode.getIdHash().equals(hash)) {
                        qr[0] = qrCode;
                    }
                }
            }
        });
        return qr[0];
    }




}
