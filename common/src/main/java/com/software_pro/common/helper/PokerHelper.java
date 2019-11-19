package com.software_pro.common.helper;

import com.software_pro.common.entity.Poker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Collections;

public class PokerHelper {
    //发牌函数
    //打印函数
    //重载比较
    private static Comparator<Poker> pokerComparator = new Comparator<Poker>() {
        @Override
        public int compare(Poker o1, Poker o2) {
            return o1.getLevel() - o2.getLevel();
        }
    };

    public static void sortPoker(List<Poker>pokers){
        Collections.sort(pokers,pokerComparator);
    }

    public static List<List<Poker>> distributePoker(){
        List<Poker> basePokers = new ArrayList<Poker>(54);
        for (int i=1;i<=4;i++) {    //type 1---4 ♠ , ♣, ♥, ♦
            for(int j=1; j<=13; j++){   //level A 2 3 4 5 6 7 8 9 10 j q k
                Poker poker = new Poker(i,j);
                basePokers.add(poker);
            }
        }
        Poker bigboss = new Poker(5,1);
        Poker smallboss = new Poker(5,0);
        basePokers.add(bigboss);
        basePokers.add(smallboss);
        Collections.shuffle(basePokers);

        //
        List<List<Poker>> pokersList = new ArrayList<List<Poker>>();
        List<Poker> pokers1 = new ArrayList<>(17);
        pokers1.addAll(basePokers.subList(0, 17));
        List<Poker> pokers2 = new ArrayList<>(17);
        pokers2.addAll(basePokers.subList(17, 34));
        List<Poker> pokers3 = new ArrayList<>(17);
        pokers3.addAll(basePokers.subList(34, 51));
        List<Poker> pokers4 = new ArrayList<>(3);
        pokers4.addAll(basePokers.subList(51, 54));
        pokersList.add(pokers1);
        pokersList.add(pokers2);
        pokersList.add(pokers3);
        pokersList.add(pokers4);

        return pokersList;
    }

    public static void printPoker(List<Poker>pokers){
        for(Poker poker:pokers){
            System.out.println("花色:"+poker.getType()+"--等级:"+poker.getLevel());
        }
    }
}
