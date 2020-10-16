package com.example.basicmediaplayer;

import android.content.Context;
import java.util.ArrayList;

public class PlayListBuilder {
    ArrayList<Integer> voicePlaylist;
    public ArrayList<Integer> buildPlaylist(int queueNumber, Context voicePlayerContext){
        //build a playlist based on the queue number
        voicePlaylist = new ArrayList<>();
        String[] splitNum = String.valueOf(queueNumber).split("");
        int rawResourceId;

        //loop through each digit in reverse while fetching the audio files
        for(int digit=splitNum.length-1;digit>=1;digit--){//loop through each digit in reverse
            rawResourceId = voicePlayerContext.getResources().getIdentifier("ch"+splitNum[digit], "raw", voicePlayerContext.getPackageName());
            voicePlaylist.add(0,rawResourceId);
        }
        //把 [百] 和 [十] mp3檔加進去
        if(voicePlaylist.size()==3) { //three digit number
            voicePlaylist.add(1, R.raw.ch100);
            if (splitNum[3].equals("0") && splitNum[2].equals("0")) { //for numbers ending in '00'
                //remove "00" from playlist
                voicePlaylist.remove(voicePlaylist.size() - 1);
                voicePlaylist.remove(voicePlaylist.size() - 1);

            }else if(splitNum[3].equals("0")){ //for numbers ending in '0' only
                //replace last 0 with ten
                voicePlaylist.remove(voicePlaylist.size() - 1);
                voicePlaylist.add(3, R.raw.ch10);
            }else if(!splitNum[2].equals("0")){ //for all numbers besides 101-109
                voicePlaylist.add(3, R.raw.ch10);
            }

        }else if (voicePlaylist.size()==2){ //two digit number
            voicePlaylist.add(1, R.raw.ch10);
            if(splitNum[1].equals("1")){ //for numbers 10-19
                voicePlaylist.remove(0);
            }if(splitNum[2].equals("0")){ //for 20..90
                voicePlaylist.remove(voicePlaylist.size() - 1);
            }
        }
        voicePlaylist.add(0,R.raw.customer); //來賓
        voicePlaylist.add(R.raw.number);//號
        return voicePlaylist;
    }

}
