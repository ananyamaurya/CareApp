package com.example.ananya.careapp;

/**
 * Created by Ananya on 29-03-2018.
 */

public class TotalCount {
    long CountPat,CountHos,CountAtt,CountDoc;
    public TotalCount(){

    }
    public TotalCount(long countAtt,long countDoc,long countHos,long countPat){
        CountAtt =countAtt;
        CountDoc = countDoc;
        CountHos = countHos;
        CountPat = countPat;
    }

    public long getCountPat() {
        return CountPat;
    }

    public void setCountPat(long countPat) {
        CountPat = countPat;
    }

    public long getCountHos() {
        return CountHos;
    }

    public void setCountHos(long countHos) {
        CountHos = countHos;
    }

    public long getCountAtt() {
        return CountAtt;
    }

    public void setCountAtt(long countAtt) {
        CountAtt = countAtt;
    }

    public long getCountDoc() {
        return CountDoc;
    }

    public void setCountDoc(long countDoc) {
        CountDoc = countDoc;
    }
}
