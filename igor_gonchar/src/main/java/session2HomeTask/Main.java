package session2HomeTask;

import java.io.IOException;
import java.util.*;

/**
 * Created by i.gonchar on 12/22/2015.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        TextGenerator tg = new TextGenerator();
        tg.setTestForParsing(tg.setTextFromFile("D:\\Pragramming\\Java\\Intelij Projects\\proff29\\igor_gonchar\\src\\main\\resources\\UnitTests_equals_hashCode.txt"));
        String aaa = tg.getTestForParsing();
        Set<String> list = tg.getWordsByFrequency(2);
        System.out.println("-----------");
        System.out.println(list);
        System.out.println(tg.finalSort(aaa));
        System.out.println("---------");
        tg.printAcs();
        System.out.println("--------");
        tg.printDesc();

    }
}