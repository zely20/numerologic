package by.zelenko.numerologic.backend.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculate {
    private Map<String, Integer> result = new HashMap<>();
    private final static String FIRST_NUMBER = "firstNumber";
    private final static String SECOND_NUMBER = "secondNumber";
    private final static String THIRD_NUMBER = "thirdNumber";
    private final static String FOURTH_NUMBER = "fourthNumber";
    private final static String FATE_NUMBER = "fateNumber";
    private final static String TARGET = "target";
    private final static String CHARACTER = "character";
    private final static String HEALTH = "health";
    private final static String LUCK = "luck";
    private final static String FAMILY = "family";
    private final static String ENERGY = "energy";
    private final static String LOGIC = "logic";
    private final static String TRUST = "trust";
    private final static String HABIT = "habit";
    private final static String INTEREST = "interest";
    private final static String WORK = "work";
    private final static String MEMORY = "memory";
    private final static String TEMPERAMENT = "temperament";
    private final static String BIT = "bit";

    private ArrayList<Integer> parseDate(String date) {
        ArrayList<Integer> result = new ArrayList<>();
        String[] temp = date.split("/");
        for (String num : temp) {
            for (String n : num.split("")) {
                result.add(Integer.valueOf(n));
            }
        }
        return result;
    }

    public  Map<String, Integer> calculate(String date) {
        Integer firstNumber = 0;
        Integer thirdNumber = 0;
        ArrayList<Integer> afterParser = parseDate(date);
        for (Integer num : afterParser) {
            firstNumber += num;
        }
        System.out.println(firstNumber);
        this.result.put(FIRST_NUMBER, firstNumber);
        if (firstNumber < 10) {
            this.result.put(SECOND_NUMBER, firstNumber);
        } else {
            this.result.put(SECOND_NUMBER, sumAfterSplit(firstNumber));
        }

        if (afterParser.get(0) == 0) {
            thirdNumber = firstNumber - afterParser.get(1) * 2;
            if(thirdNumber < 0){
                thirdNumber = Math.abs(thirdNumber);
            }
            this.result.put(THIRD_NUMBER, thirdNumber);
        } else {
            thirdNumber = firstNumber - afterParser.get(0) * 2;
            if(thirdNumber < 0){
                thirdNumber = Math.abs(thirdNumber);
            }
            this.result.put(THIRD_NUMBER, thirdNumber);
        }
        if(thirdNumber < 10) {
            this.result.put(FOURTH_NUMBER, thirdNumber);
        } else {
            this.result.put(FOURTH_NUMBER, sumAfterSplit(thirdNumber));
        }

        //запуск внутреннего квадрата
        this.result.putAll(innerSquare(afterParser, this.result));
        //запкск внешний квадрат
        this.result.putAll(outSquare(this.result));

        //поиск числа судьбы
        if(firstNumber < 10) {
            this.result.put(FATE_NUMBER, firstNumber);
        } else {
            Integer fateNumber = firstNumber;
            while (fateNumber > 9){
                fateNumber = sumAfterSplit(fateNumber);
                if(fateNumber == 11){
                    break;
                }
            }
            this.result.put(FATE_NUMBER,fateNumber);
        }
        return result;
    }

    //внешний квдрат
    private Map <String, Integer>  outSquare(Map <String, Integer> map) {
        Map <String, Integer> result = new HashMap<>();
        result.put(TARGET, sumForOutSquare(map,CHARACTER,HEALTH, LUCK));
        result.put(FAMILY, sumForOutSquare(map,ENERGY,LOGIC, TRUST));
        result.put(HABIT, sumForOutSquare(map,INTEREST,WORK, MEMORY));
        result.put(TEMPERAMENT, sumForOutSquare(map,INTEREST,LOGIC, LUCK));
        result.put(BIT, sumForOutSquare(map,HEALTH,LOGIC, WORK));
        return result;
    }

    private Integer sumForOutSquare(Map <String, Integer> map, String one, String two, String tree){
        Integer result = 0;
        if(map.containsKey(one)){
            result += getCountsOfDigits(map.get(one));
        }
        if(map.containsKey(two)){
            result += getCountsOfDigits(map.get(two));
        }
        if(map.containsKey(tree)){
            result += getCountsOfDigits(map.get(tree));
        }
        return result;
    }

    //нутренний квадрат
    private Map <String, Integer>  innerSquare(ArrayList<Integer> dateAfterParser, Map <String, Integer> map){
        Map <String, Integer> result = new HashMap<>();
        for (int i = 1; i <= 9; i++) {
            int count = 0;
            for(Integer num : dateAfterParser){
                if(num == i){
                    count++;
                }
            }
            for(Integer num : map.values()){
                Integer temp = num % 10;
                Integer temp2 = num / 10;
                if(temp == i){
                    count++;
                }
                if (temp2 == i){
                    count++;
                }
            }
            if (count != 0) {
                Integer resultForMap = i;
                int factor = 1 * i;
                for (int j = 1; j < count; j++) {
                    factor = factor * 10;
                    resultForMap += factor;
                }
                switch (i){
                    case 1: result.put(CHARACTER, resultForMap);
                    break;
                    case 2: result.put(ENERGY, resultForMap);
                        break;
                    case 3: result.put(INTEREST, resultForMap);
                        break;
                    case 4: result.put(HEALTH, resultForMap);
                        break;
                    case 5: result.put(LOGIC, resultForMap);
                        break;
                    case 6: result.put(WORK, resultForMap);
                        break;
                    case 7: result.put(LUCK, resultForMap);
                        break;
                    case 8: result.put(TRUST, resultForMap);
                        break;
                    case 9: result.put(MEMORY, resultForMap);
                        break;
                    default: ;
                        break;

                }
            }
        }
        return result;
    }

    private Integer getCountsOfDigits(Integer number) {
        int count = (number == 0) ? 1 : 0;
        while (number != 0) {
            count++;
            number /= 10;
        }
        return count;
    }


    private Integer sumAfterSplit(Integer number) {
        Integer result = 0;
        Integer temp = number % 10;
        System.out.println(temp);
        Integer temp2 = number / 10;
        System.out.println(temp2);
        result = temp + temp2;
        return result;
    }

    public Map<String, Integer> getResult() {
        return result;
    }
}
