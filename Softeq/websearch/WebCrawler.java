package websearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static java.util.stream.Collectors.toMap;

/**
 *
 * @author Gagadok
 */
public class WebCrawler {

    public static Deque<String> queue = new LinkedList<>();
    public static String regex = "http[s]*://(\\w+\\.)*(\\w+)(/(-?\\w+)*)*";
    public static StringBuilder outputCSV = new StringBuilder();
    public static StringBuilder outputTop10CSV = new StringBuilder();
    public static Set<String> sites = new HashSet<>();
    public static Map<String, Integer> top10Sites = new LinkedHashMap<>();

    public static int pagesLimit = 10000;
    public static int depth = 8;
    public static String root;
    public static String[] terms;

    public WebCrawler(String Root, String[] Terms) {
        root = Root;
        terms = Terms;
    }

    public WebCrawler(String Root, String[] Terms, int Depth, int PagesLimit) {
        root = Root;
        terms = Terms;
        depth = Depth;
        pagesLimit = PagesLimit;
    }

    public static void algoritm() throws IOException {

        // Adding the root site to the queue and to the general list of sites
        queue.add(root);
        sites.add(root);
        BufferedReader br = null;
        int numberOfSites = 1;
        int current_depth = 0;
        String last_site = root;
        Map<String, Integer> count_terms = new HashMap<>();

        // Creating the first line for CSV files
        CSVfirstLine(terms);

        // While the queue of sites is not empty-we view them.
        while (!queue.isEmpty()) {
            count_terms.clear();
            // We take the site from the queue.
            String crawledUrl = queue.poll();
            System.out.println("\n======= Site scaned : " + crawledUrl + " =======");
            System.out.printf("Number of sites scanned : %d (max %d); Depth : %d (max %d)\n", numberOfSites - 1,
                    pagesLimit, current_depth, depth);

            // Checking the depth.
            if (current_depth > depth) {
                createCSV();
                return;
            }
            // Checking the number of verified sites.
            if (numberOfSites > pagesLimit) {
                createCSV();
                return;
            }

            boolean ok = false;
            URL url = null;

            // Checking the links.
            while (!ok) {
                try {
                    url = new URL(crawledUrl);
                    br = new BufferedReader(new InputStreamReader(url.openStream()));
                    ok = true;
                    numberOfSites++;
                } catch (MalformedURLException e) {
                    System.out.println("*** Malformed URL : " + crawledUrl);
                    crawledUrl = queue.poll();
                    ok = false;
                } catch (IOException ioe) {
                    System.out.println("*** IOException for URL : " + crawledUrl);
                    crawledUrl = queue.poll();
                    ok = false;
                }
            }

            StringBuilder sb = new StringBuilder();
            String tmp = null;

            // Reading text from the site.
            while ((tmp = br.readLine()) != null) {
                sb.append(tmp);
            }

            tmp = sb.toString();

            // Search for links using a regular expression.
            if (current_depth < depth) {
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(tmp);

                while (matcher.find()) {
                    String w = matcher.group();

                    if (!sites.contains(w)) {
                        System.out.println("Sited added for queue : " + w);
                        sites.add(w);
                        queue.add(w);
                    }
                }
            }

            // Counting terms.
            int termsLen = terms.length;
            for (int i = 0; i < termsLen; i++) {
                count_terms.put(terms[i], count(tmp, terms[i]));
            }

            // Checking the last site in the queue.
            if (crawledUrl.equals(last_site)) {
                current_depth++;
                while (!validUrl(queue.getLast()) && !queue.isEmpty()) {
                    queue.removeLast();
                }
                if (!queue.isEmpty()) {
                    last_site = queue.getLast();
                }
            }

            showResults(count_terms, crawledUrl, terms);
        }

        if (br != null) {
            br.close();
        }
    }

    public static void showResults(Map<String, Integer> count_terms, String url, String[] terms) {
        int termsLen = terms.length;
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        int total = 0;
        for (int i = 0; i < termsLen; i++) {
            sb.append(",");
            sb.append(count_terms.get(terms[i]).toString());
            total += count_terms.get(terms[i]);
        }
        sb.append(",");
        outputCSV.append(sb);
        outputCSV.append(Integer.toString(total));
        outputCSV.append('\n');
        top10Sites.put(sb.toString(), total);
    }

    public static int count(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }

    public static boolean validUrl(String Url) throws IOException {
        URL url = null;
        BufferedReader br = null;
        try {
            url = new URL(Url);
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            if (br != null) {
                br.close();
            }
            return true;
        } catch (MalformedURLException e) {
            System.out.println("*** Malformed URL : " + Url);
            if (br != null) {
                br.close();
            }
            return false;
        } catch (IOException ioe) {
            System.out.println("*** IOException for URL : " + Url);
            if (br != null) {
                br.close();
            }
            return false;
        }
    }

    public static void CSVfirstLine(String[] str) {
        int strLen = str.length;
        outputCSV.append("Site");
        for (int i = 0; i < strLen; i++) {
            outputCSV.append(",");
            outputCSV.append(str[i]);
        }
        outputCSV.append(",");
        outputCSV.append("Total");
        outputCSV.append('\n');
        outputTop10CSV.append(outputCSV);
    }

    public static void createCSV() throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(new File("All stat report.csv"))) {
            writer.write(outputCSV.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try (PrintWriter writer = new PrintWriter(new File("Top 10 pages report.csv"))) {
            Map<String, Integer> sorted = top10Sites
                    .entrySet()
                    .stream()
                    .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));
            sorted.entrySet().stream().limit(10).forEach(entry -> {
                outputTop10CSV.append(entry.getKey());
                outputTop10CSV.append(Integer.toString(entry.getValue()));
                outputTop10CSV.append('\n');

            });
            writer.write(outputTop10CSV.toString());
            writer.close();
            System.out.println("All stat report.csv and Top 10 pages report.csv were created");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
