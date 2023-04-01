package com.example.qriffic;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class defines a player profile
 */
public class PlayerProfile {

    private String username;
    private String uniqueID;
    private String email;
    private String phoneNum;
    private int highScore;
    private int lowScore;
    private int totalScore;
    private int totalScanned;
    private HashMap<String, QRCode> captured;
    private ArrayList<fetchListener> listeners;

    /**
     * This is an empty constructor for a PlayerProfile object
     * (Required for Firestore Custom Object Translation)
     */
    public PlayerProfile() {
        this.captured = new HashMap<String, QRCode>();
        this.listeners = new ArrayList<fetchListener>();
        // Initial values
        // They will be overwritten when the first QR Code is scanned
        this.highScore = -1;
        this.lowScore = -1;
        this.totalScore = 0;
        this.totalScanned = 0;
    }

    /**
     * This is a constructor for a PlayerProfile object
     * @param username
     * The player's username as a String
     * @param uniqueID
     * The player's unique ID as a string
     * @param email
     * The player's email address as a string
     * @param phoneNum
     * The player's phone number as a string
     * @param captured
     * The player's captured QRCodes as a HashMap of QRCode objects
     */
    public PlayerProfile(String username, String uniqueID, String email, String phoneNum,
                         HashMap<String, QRCode> captured) {
        this.username = username;
        this.uniqueID = uniqueID;
        this.email = email;
        this.phoneNum = phoneNum;
        this.highScore = -1;
        this.lowScore = -1;
        this.totalScore = 0;
        this.totalScanned = 0;
        this.captured = captured;
        this.listeners = new ArrayList<fetchListener>();

        for (QRCode qr : captured.values()) {
            if (qr.getScore() > highScore) {
                highScore = qr.getScore();
            }
            if (qr.getScore() < lowScore) {
                lowScore = qr.getScore();
            }
        }
    }

    /**
     * This is a constructor for a PlayerProfile object
     * @param username
     * The player's username as a String
     * @param uniqueID
     * The player's unique ID as a string
     * @param email
     * The player's email address as a string
     * @param phoneNum
     * The player's phone number as a string
     * @param captured
     * The player's captured QRCodes as an ArrayList of QRCode objects
     */
    public PlayerProfile(String username, String uniqueID, String email, String phoneNum,
                         ArrayList<QRCode> captured) {
        this.username = username;
        this.uniqueID = uniqueID;
        this.email = email;
        this.phoneNum = phoneNum;
        this.highScore = -1;
        this.lowScore = -1;
        this.totalScore = 0;
        this.totalScanned = 0;
        this.captured = new HashMap<String, QRCode>();
        this.listeners = new ArrayList<fetchListener>();

        for (QRCode qr : captured) {
            this.captured.put(qr.getIdHash(), qr);
            if (qr.getScore() > highScore) {
                highScore = qr.getScore();
            }
            if (qr.getScore() < lowScore) {
                lowScore = qr.getScore();
            }
        }

    }

    /**
     * This method adds a fetchListener to the PlayerProfile object
     *
     * This block references the following web page:
     * Link: https://programming.guide/java/create-a-custom-event.html
     * Author: Unavailable
     * Date: 10/03/2023
     *
     * @param toAdd
     */
    public void addListener(fetchListener toAdd) {
        listeners.add(toAdd);
    }

    /**
     * This methods removes a fetchListener from the PlayerProfile object
     * @param toRemove
     * The fetchListener to remove
     */
    public void removeListener(fetchListener toRemove) {
        listeners.remove(toRemove);
    }

    /**
     * This method calls all onFetchComplete() listeners
     *
     * This block references the following web page:
     * Link: https://programming.guide/java/create-a-custom-event.html
     * Author: Unavailable
     * Date: 10/03/2023
     *
     */
    public void fetchComplete() {
        for (fetchListener fl : listeners)
            fl.onFetchComplete();
    }

    /**
     * This method calls all onFetchFailure() listeners
     *
     * This block references the following web page:
     * Link: https://programming.guide/java/create-a-custom-event.html
     * Author: Unavailable
     * Date: 10/03/2023
     */
    public void fetchFailed() {
        for (fetchListener fl : listeners)
            fl.onFetchFailure();
    }

    /**
     * This method returns the username of a PlayerProfile object
     * @return
     * The player's username as a string
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method sets the username of a PlayerProfile object
     * @param username
     * The player's username as a string
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method returns the uniqueID of a PlayerProfile object
     * @return
     * The player's uniqueID as a string
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * This method sets the uniqueID of a PlayerProfile object
     * @param uniqueID
     * The player's uniqueID as a string
     */
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    /**
     * This method returns the email address of a PlayerProfile object
     * @return
     * The email address as a string
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method sets the email address of a PlayerProfile object
     * @param email
     * The email address as a string
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method returns the phone number of a PlayerProfile object
     * @return
     * The phone number as a string
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * This method sets the phone number of a PlayerProfile object
     * @param phoneNum
     * The phone number as a string
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    /**
     * This method returns the high score of a PlayerProfile object
     * @return
     * The high score as an integer
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * This method sets the high score of a PlayerProfile object
     * @param highScore
     * The high score as an integer
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * This method returns the high score of a PlayerProfile object
     * @return
     * The high score as an integer
     */
    public int getLowScore() {
        return lowScore;
    }

    /**
     * This method sets the high score of a PlayerProfile object
     * @param lowScore
     * The high score as an integer
     */
    public void setLowScore(int lowScore) {
        this.lowScore = lowScore;
    }

    /**
     * This method returns the list of captured QRCodes of a PlayerProfile object as a HashMap
     * @return
     * The captured QRCodes as a HashMap of QRCode objects
     */
    public HashMap<String, QRCode> getCaptured() {
        return captured;
    }

    public int getTotalScore() {return totalScore;}

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalScanned() {return totalScanned;}

    public void setTotalScanned(int totalScanned) {
        this.totalScanned = totalScanned;
    }

    /**
     * This method adds a QRCode object to the list of captured QRCodes of a PlayerProfile object
     * @param qrCode
     * The QRCode object to be added to the ArrayList of QRCode objects
     */
    public void addQRCode(QRCode qrCode) {
        this.captured.put(qrCode.getIdHash(), qrCode);
        this.updateHighScore(qrCode.getScore());
        this.updateLowScore(qrCode.getScore());
        this.totalScore += qrCode.getScore();
        this.totalScanned += 1;
    }

    /**
     * This method deletes a QRCode object from the list of captured QRCodes of a
     * PlayerProfile object
     * @param delQrCode
     * The QRCode object to be deleted from the captured
     */
    public void deleteQRCode(QRCode delQrCode) {
        QRCode qrCode = this.captured.get(delQrCode.getIdHash());

        // If QR code does not exist in captured
        if (qrCode == null) {
            Log.e("deleteUpdate", "QR Code does not exist");
            return;
        }

        this.totalScanned -= 1;
        this.totalScore -= qrCode.getScore();
        this.captured.remove(delQrCode.getIdHash());

        if (this.totalScanned == 0) {
            this.highScore = -1;
            this.lowScore = -1;
            return;
        }

        if (this.lowScore == qrCode.getScore()) {
            this.lowScore = -1;
            for (QRCode qr : this.captured.values()) {
                if (this.lowScore == -1 || this.lowScore > qr.getScore()) {
                    this.lowScore = qr.getScore();
                }
            }
        }

        if (this.highScore == qrCode.getScore()) {
            this.highScore = -1;
            for (QRCode qr : this.captured.values()) {
                if (this.highScore == -1 || this.highScore < qr.getScore()) {
                    this.highScore = qr.getScore();
                }
            }
        }
    }

    /**
     * This method deletes a QRCode object from the list of captured QRCodes of a
     * PlayerProfile object
     * @param idHash
     * The idHash of the QRCode object to be deleted from captured
     */
    public void deleteQRCode(String idHash) {
        QRCode qrCode = this.captured.get(idHash);
        Log.d("TESTPRINT", "idHash: " + idHash + " qrCode: " + qrCode + " captured: " + this.captured);

        // If QR code does not exist in captured
        if (qrCode == null) {
            Log.e("deleteUpdate", "QR Code does not exist");
            return;
        }

        this.totalScanned -= 1;
        this.totalScore -= qrCode.getScore();
        this.captured.remove(idHash);

        if (this.totalScanned == 0) {
            this.highScore = -1;
            this.lowScore = -1;
            return;
        }

        if (this.lowScore == qrCode.getScore()) {
            this.lowScore = -1;
            for (QRCode qr : this.captured.values()) {
                if (this.lowScore == -1 || this.lowScore > qr.getScore()) {
                    this.lowScore = qr.getScore();
                }
            }
        }

        if (this.highScore == qrCode.getScore()) {
            this.highScore = -1;
            for (QRCode qr : this.captured.values()) {
                if (this.highScore == -1 || this.highScore < qr.getScore()) {
                    this.highScore = qr.getScore();
                }
            }
        }
    }

    /**
     * This method updates the high score of a PlayerProfile object
     * @param score
     * The score to be compared to the current high score
     */
    public void updateHighScore(int score) {
        this.highScore = Math.max(highScore, score);
    }

    /**
     * This method updates the low score of a PlayerProfile object
     * @param score
     * The score to be compared to the current low score
     */
    public void updateLowScore(int score) {
        if (this.lowScore == -1) {
            this.lowScore = score;
        }
        this.lowScore = Math.min(lowScore, score);
    }

    public QRCode getBestQR() {
        for (QRCode qr : captured.values()) {
            if (qr.getScore() == this.highScore) {
                return qr;
            }
        }
        return null;
    }
}