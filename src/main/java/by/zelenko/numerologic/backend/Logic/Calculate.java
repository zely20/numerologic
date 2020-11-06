package by.zelenko.numerologic.backend.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Calculate {

    private Map<String, Integer> result = new HashMap<>();

    public static void main(String[] args) {
        Calculate calc = new Calculate();
        calc.calculate("28/12/1725");
    }

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
        this.result.put("firstNumber", firstNumber);
        if (firstNumber < 10) {
            this.result.put("secondNumber", firstNumber);
        } else {
            this.result.put("secondNumber", sumAfterSplit(firstNumber));
        }
        if (afterParser.get(0) == 0) {
            thirdNumber = firstNumber - afterParser.get(1) * 2;
            this.result.put("thirdNumber", thirdNumber);
        } else {
            thirdNumber = firstNumber - afterParser.get(0) * 2;
            this.result.put("thirdNumber", thirdNumber);
        }
        if(thirdNumber < 10) {
            this.result.put("fourthNumber", thirdNumber);
        } else {
            this.result.put("fourthNumber", sumAfterSplit(thirdNumber));
        }
        System.out.println(result);


        //запуск внутреннего квадрата
        this.result.putAll(innerSquare(afterParser, this.result));
        //запкск внешний квадрат
        this.result.putAll(outSquare(this.result));

        System.out.println(result);
        //поиск числа судьбы
        if(firstNumber < 10) {
            this.result.put("fateNumber", firstNumber);
        } else {
            Integer fateNumber = firstNumber;
            while (fateNumber > 9){
                fateNumber = sumAfterSplit(fateNumber);
                if(fateNumber == 11){
                    break;
                }
            }
            this.result.put("fateNumber",fateNumber);
        }
        return result;
    }

    //внешний квдрат
    private Map <String, Integer>  outSquare(Map <String, Integer> map) {
        Map <String, Integer> result = new HashMap<>();
        result.put("target", sumForOutSquare(map,"character","health", "luck"));
        result.put("family", sumForOutSquare(map,"energy","logic", "trust"));
        result.put("habit", sumForOutSquare(map,"interest","work", "memory"));
        result.put("temperament", sumForOutSquare(map,"interest","logic", "luck"));
        result.put("bit", sumForOutSquare(map,"health","logic", "work"));
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
                    case 1: result.put("character", resultForMap);
                    break;
                    case 2: result.put("energy", resultForMap);
                        break;
                    case 3: result.put("interest", resultForMap);
                        break;
                    case 4: result.put("health", resultForMap);
                        break;
                    case 5: result.put("logic", resultForMap);
                        break;
                    case 6: result.put("work", resultForMap);
                        break;
                    case 7: result.put("luck", resultForMap);
                        break;
                    case 8: result.put("trust", resultForMap);
                        break;
                    case 9: result.put("memory", resultForMap);
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
