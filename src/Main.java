import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int listSize;
        while (true) {
            if(scanner.hasNextInt()) {
                listSize = scanner.nextInt();
                break;
            } else{
                System.out.println("Александр Владимирович, введите, пожалуйста, число");
                scanner.next();
            }
        }
        ArrayList<Integer> priceDinner = new ArrayList<>();    //список для цен на обеды
        ArrayList<Integer> dinnerTicket = new ArrayList<>();   //список для индексов с ценами >100
        int tickets = 0;
        int minPrice;
        int sumPrice = 0;
        for(int i = 0; i<listSize; i++){
            while (true) {
                if(scanner.hasNextInt()) {
                    priceDinner.add(scanner.nextInt());
                    sumPrice += priceDinner.get(i);         //считаем сумму всех обедов
                    if(priceDinner.get(i) > 100){         //если ввели больше 100, прибавляем купон, и запоминаем индекс
                        tickets++;
                        dinnerTicket.add(i);
                    }
                    break;
                } else{
                    System.out.println("Александр Владимирович, введите, пожалуйста, число");
                    scanner.next();
                }
            }
        }
        minPrice = sumPrice;
        System.out.println(getMinPrice(priceDinner, dinnerTicket, sumPrice, tickets, minPrice));
    }

    private static int getMinPrice(ArrayList<Integer> priceDinner, ArrayList<Integer> dinnerTicket, int sumPrice, int tickets, int minPrice) {
        if(dinnerTicket.isEmpty() || tickets == 0){               //если индексы или купоны закончились, возвращаем минимум
            return minPrice;
        }
        ArrayList<Integer> dinnerToMin = new ArrayList<>();                 //список для цен после индекса цены >100
        for(int i = dinnerTicket.getLast() + 1; i < priceDinner.size(); i++ ){           //с индекса цены >100 добавляем цены
            dinnerToMin.add(priceDinner.get(i));
        }
        dinnerToMin.sort(Collections.reverseOrder());                //сортируем в обратном порядке
        int dinnerToDelete = 0;
        for(int i = 0; i < tickets && i < dinnerToMin.size(); i++){           //считаем сумму максимальных цен после цены >100 (не включительно) в кол-ве купонов/размерности списка(если билетов больше, чем цен)
            dinnerToDelete += dinnerToMin.get(i);
        }
        int newMinPrice = sumPrice - dinnerToDelete;                 //"удаляем" максимальную сумму для удаления из общей цены
        if(newMinPrice < minPrice){                               //если получилось меньше, меняем минимум
            minPrice = newMinPrice;
        }
        dinnerTicket.removeLast();                     //удаляем индекс последней цены >100, чтобы работать со след. подмассивом
        return getMinPrice(priceDinner, dinnerTicket, sumPrice, tickets-1, minPrice);   //уменьшем кол-во купонов на 1, т.к. теперь мы рассматриваем
                                                                                               //подмассив начиная с более ранней цены >100
    }
}
