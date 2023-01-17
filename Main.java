// https://github.com/netology-code/jd-homeworks/blob/video/synchronization/task1/README.md

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        Runnable logic = () -> {
            int count = countR(generateRoute("RLRFR", 100));
            synchronized (sizeToFreq) {
                if (sizeToFreq.containsKey(count)) {
                    sizeToFreq.put(count, sizeToFreq.get(count) + 1);
                } else {
                    sizeToFreq.put(count, 1);
                }
            }
        };

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(logic);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        findingMax(sizeToFreq);
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> kv : sizeToFreq.entrySet()) {
            System.out.println("- " + kv.getKey() + " (" + kv.getValue() + " раз)");
        }
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static int countR(String string) {
        int counter = -1;
        int next = 0;
        while (next >= 0) {
            next = string.indexOf('R', next + 1);
            counter++;
        }
        return counter;
    }

    public static void findingMax(Map map) {
        int mostFrequent = 0;
        int times = 0;
        mostFrequent = Collections.max(sizeToFreq.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        times = Collections.max(sizeToFreq.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue();
        System.out.println("Самое частое количество повторений " + mostFrequent + " (встретилось " + times + " раз)");
    }
}