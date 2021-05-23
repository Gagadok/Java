package websearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Gagadok
 */
public class WebSearch {

    public static void main(String[] args) {
        System.out.println("String Root (url), String Terms (term1, term2, ... , termN); int Depth; int PagesLimit");
        Scanner in = new Scanner(System.in);
        String[] input = in.nextLine().split(",");
        List<String> list = new ArrayList<>(Arrays.asList(input));

        String root = list.get(0);

        list.remove(root);
        String[] depLim = list.get(list.size() - 1).split(";");
        list.remove(list.get(list.size() - 1));

        int depth, pagesLimit;

        if (depLim.length == 3 && !depLim[1].isEmpty() && !depLim[2].isEmpty()) {
            depth = Integer.parseInt(depLim[1].trim());
            pagesLimit = Integer.parseInt(depLim[2].trim());
            list.add(depLim[0]);

            String[] terms = list.toArray(new String[list.size()]);

            WebCrawler wb = new WebCrawler(root, terms, depth, pagesLimit);
            try {
                wb.algoritm();
            } catch (IOException ex) {
            }
        } else {
            String[] terms = list.toArray(new String[list.size()]);

            WebCrawler wb = new WebCrawler(root, terms);
            try {
                wb.algoritm();
            } catch (IOException ex) {
            }
        }
    }
}
